package com.campusConnect.authService.security;

import com.campusConnect.authService.dto.LoginDTO;
import com.campusConnect.authService.dto.SignUpRequestDTO;
import com.campusConnect.authService.dto.UserDTO;
import com.campusConnect.authService.entity.User;
import com.campusConnect.authService.exception.ResourceNotFoundException;
import com.campusConnect.authService.repository.UserRepository;
import com.campusConnect.authService.service.EmailService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final EmailService emailService;

    public UserDTO signUp(SignUpRequestDTO signUpRequestDto){

        User user = userRepository.findByUsername(signUpRequestDto.getUsername()).orElse(null);

        if(user!=null){
            throw  new RuntimeException("User is already present with same username");
        }

        User newUser=modelMapper.map(signUpRequestDto,User.class);
        newUser.setPassword(passwordEncoder.encode(signUpRequestDto.getPassword()));
        String token = UUID.randomUUID().toString();
        newUser.setVerificationToken(token);

        newUser =userRepository.save(newUser);

        emailService.sendVerificationEmail(newUser.getEmail(), token);

        return modelMapper.map(newUser, UserDTO.class);
    }

    public String[] login(LoginDTO loginDto){
        Authentication authentication= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getIdentifier(),loginDto.getPassword()
        ));

        User user=(User) authentication.getPrincipal();

        String[] arr=new String[2];

        arr[0]= jwtService.generateAccessToken(user);
        arr[1]= jwtService.generateRefreshToken(user);
        return arr;
    }

    public String refreshToken(String refreshToken){
        Long id= jwtService.getUserIdFromToken(refreshToken);

        User user = userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User not found with id: "+id));
        return jwtService.generateAccessToken(user);
    }

    @Transactional
    public void changePassword(String oldPassword, String newPassword) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!passwordEncoder.matches(oldPassword, currentUser.getPassword())) {
            throw new BadCredentialsException("Invalid old password");
        }
        if (passwordEncoder.matches(newPassword, currentUser.getPassword())) {
            throw new IllegalArgumentException("New password cannot be the same as old password");
        }
        currentUser.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(currentUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                currentUser,
                currentUser.getPassword(),
                currentUser.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Transactional
    public void verifyEmail(String token) {
        User user = userRepository.findByVerificationToken(token)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid verification token"));
        user.setEmailVerified(true);
        user.setVerificationToken(null);
        userRepository.save(user);
    }

    @Transactional
    public void forgotPassword(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
        String token = UUID.randomUUID().toString();
        user.setResetToken(token);
        user.setResetTokenExpiry(LocalDateTime.now().plusMinutes(15)); // 15 minutes expiry
        userRepository.save(user);
        emailService.sendResetEmail(user.getEmail(), token);
    }

    @Transactional
    public void resetPassword(String token, String newPassword) {
        User user = userRepository.findByResetToken(token)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid reset token"));
        if (user.getResetTokenExpiry().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Reset token has expired");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetToken(null);
        user.setResetTokenExpiry(null);
        userRepository.save(user);
    }
}

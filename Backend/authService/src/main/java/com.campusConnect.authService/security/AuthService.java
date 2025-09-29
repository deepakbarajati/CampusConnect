package com.campusConnect.authService.security;

import com.campusConnect.authService.dto.LoginDTO;
import com.campusConnect.authService.dto.SignUpRequestDTO;
import com.campusConnect.authService.dto.UserDTO;
import com.campusConnect.authService.entity.User;
import com.campusConnect.authService.entity.enums.Role;
import com.campusConnect.authService.exception.ResourceNotFoundException;
import com.campusConnect.authService.repository.UserRepository;
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


@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public UserDTO signUp(SignUpRequestDTO signUpRequestDto){

        User user = userRepository.findByUsername(signUpRequestDto.getUsername()).orElse(null);

        if(user!=null){
            throw  new RuntimeException("User is already present with same email id");
        }

        User newUser=modelMapper.map(signUpRequestDto,User.class);
        newUser.setRole(Role.ADMIN);//TODO Dynamic Role set
        newUser.setPassword(passwordEncoder.encode(signUpRequestDto.getPassword()));

        newUser =userRepository.save(newUser);

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

}

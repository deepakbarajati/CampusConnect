package com.campusConnect.authService;

import com.campusConnect.authService.dto.SignUpRequestDTO;
import com.campusConnect.authService.entity.User;
import com.campusConnect.authService.entity.enums.Role;
import com.campusConnect.authService.repository.UserRepository;
import com.campusConnect.authService.security.AuthService;
import com.campusConnect.authService.service.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSignUp_Success() {
        // Arrange
        SignUpRequestDTO request = new SignUpRequestDTO();
        request.setUsername("testuser");
        request.setEmail("test@example.com");
        request.setPassword("password");
        request.setRole(Role.STUDENT);

        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.empty());
        when(modelMapper.map(request, User.class)).thenReturn(user);
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Act
        var result = authService.signUp(request);

        // Assert
        assertNotNull(result);
        verify(emailService).sendVerificationEmail(eq("test@example.com"), anyString());
    }

    @Test
    void testSignUp_UserAlreadyExists() {
        // Arrange
        SignUpRequestDTO request = new SignUpRequestDTO();
        request.setUsername("existinguser");

        when(userRepository.findByUsername("existinguser")).thenReturn(Optional.of(new User()));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> authService.signUp(request));
        assertEquals("User is already present with same username", exception.getMessage());
    }
}

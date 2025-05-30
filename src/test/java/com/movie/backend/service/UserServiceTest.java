package com.movie.backend.service;


import com.cloudinary.Cloudinary;
import com.movie.backend.dto.RoleDTO;
import com.movie.backend.dto.UserDTO;
import com.movie.backend.entity.ERole;
import com.movie.backend.entity.User;
import com.movie.backend.exception.UserException;
import com.movie.backend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private SettingService settingService;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private MailService mailService;

    @Mock
    private PasswordEncoder passwordEncoder ;



    @Mock
    private Cloudinary cloudinary;

    @InjectMocks
    private UserService userService;

    private User user;

    private UserDTO userDTO;
    @BeforeEach
    void setUp() {

        user = User.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .password("encodedPassword123")
                .role(ERole.CUSTOMER)
                .status(true)
                .build();

        userDTO = new UserDTO(1L, "John", "Doe", "John Doe",
                "john.doe@example.com", "password123", true,
                "photo.jpg", "/images/photo.jpg", "01231414" ,  "verif123",
                "forgot123", ERole.CUSTOMER.name());
    }

    @Test
    void saveUser_ShouldCreateNewUser_WhenUserDoesNotExist() {
        // Given
        when(userRepository.findByEmail(userDTO.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(userDTO.getPassword())).thenReturn("encodedPassword123");
        when(userRepository.save(any(User.class))).thenReturn(user);

        // When
        User savedUser = userService.saveUser(userDTO, null);

        // Then
        assertNotNull(savedUser);
        assertEquals(userDTO.getEmail(), savedUser.getEmail());
        assertEquals("encodedPassword123", savedUser.getPassword());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void get_ShouldReturnUserDTO_WhenUserExists() {
        // Given
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(modelMapper.map(user, UserDTO.class)).thenReturn(userDTO);

        // When
        UserDTO result = userService.get(1L);

        // Then
        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getEmail(), result.getEmail());
        verify(userRepository, times(1)).findById(1L);
        verify(modelMapper, times(1)).map(user, UserDTO.class);
    }

    @Test
    void get_ShouldThrowException_WhenUserNotFound() {
        // Given
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        UserException exception = assertThrows(UserException.class, () -> userService.get(1L));
        assertEquals("User not found", exception.getMessage());
        verify(userRepository, times(1)).findById(1L);
        verify(modelMapper, never()).map(any(), eq(UserDTO.class));
    }


}

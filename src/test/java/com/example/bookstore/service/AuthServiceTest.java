package com.example.bookstore.service;

import com.example.bookstore.dto.RegisterRequest;
import com.example.bookstore.entity.Role;
import com.example.bookstore.entity.User;
import com.example.bookstore.repository.RoleRepository;
import com.example.bookstore.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AuthServiceTest {

    @Test
    void shouldRegisterUser() {
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        RoleRepository roleRepository = Mockito.mock(RoleRepository.class);

        AuthService authService = new AuthService(userRepository, roleRepository);

        RegisterRequest request = new RegisterRequest();
        request.setUsername("user1");
        request.setEmail("user1@test.com");
        request.setPassword("pass");

        Role role = new Role();
        role.setId(1L);
        role.setName("ROLE_USER");

        Mockito.when(userRepository.existsByUsername("user1")).thenReturn(false);
        Mockito.when(userRepository.existsByEmail("user1@test.com")).thenReturn(false);
        Mockito.when(roleRepository.findByName("ROLE_USER")).thenReturn(Optional.of(role));

        Mockito.when(userRepository.save(Mockito.any(User.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        User result = authService.register(request);

        assertEquals("user1", result.getUsername());
        assertEquals("user1@test.com", result.getEmail());
        assertEquals("pass", result.getPassword());
        assertEquals(role, result.getRole());

        Mockito.verify(userRepository).save(Mockito.any(User.class));
    }

    @Test
    void shouldThrowExceptionWhenUsernameExists() {
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        RoleRepository roleRepository = Mockito.mock(RoleRepository.class);

        AuthService authService = new AuthService(userRepository, roleRepository);

        RegisterRequest request = new RegisterRequest();
        request.setUsername("user1");
        request.setEmail("user1@test.com");
        request.setPassword("pass");

        Mockito.when(userRepository.existsByUsername("user1")).thenReturn(true);

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> authService.register(request)
        );

        assertEquals("Username already exists", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenEmailExists() {
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        RoleRepository roleRepository = Mockito.mock(RoleRepository.class);

        AuthService authService = new AuthService(userRepository, roleRepository);

        RegisterRequest request = new RegisterRequest();
        request.setUsername("user1");
        request.setEmail("user1@test.com");
        request.setPassword("pass");

        Mockito.when(userRepository.existsByUsername("user1")).thenReturn(false);
        Mockito.when(userRepository.existsByEmail("user1@test.com")).thenReturn(true);

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> authService.register(request)
        );

        assertEquals("Email already exists", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenRoleNotFound() {
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        RoleRepository roleRepository = Mockito.mock(RoleRepository.class);

        AuthService authService = new AuthService(userRepository, roleRepository);

        RegisterRequest request = new RegisterRequest();
        request.setUsername("user1");
        request.setEmail("user1@test.com");
        request.setPassword("pass");

        Mockito.when(userRepository.existsByUsername("user1")).thenReturn(false);
        Mockito.when(userRepository.existsByEmail("user1@test.com")).thenReturn(false);
        Mockito.when(roleRepository.findByName("ROLE_USER")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> authService.register(request)
        );

        assertEquals("Role not found", exception.getMessage());
    }
}
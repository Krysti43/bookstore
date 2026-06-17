package com.example.bookstore.controller;

import com.example.bookstore.dto.RegisterRequest;
import com.example.bookstore.entity.User;
import com.example.bookstore.service.AuthService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AuthControllerTest {

    @Test
    void shouldRegisterUser() {
        AuthService authService = Mockito.mock(AuthService.class);
        AuthController controller = new AuthController(authService);

        RegisterRequest request = new RegisterRequest();
        request.setUsername("user1");
        request.setEmail("user1@test.com");
        request.setPassword("pass");

        User user = new User();
        user.setUsername("user1");

        Mockito.when(authService.register(request))
                .thenReturn(user);

        User result = controller.register(request);

        assertEquals("user1", result.getUsername());
    }
}
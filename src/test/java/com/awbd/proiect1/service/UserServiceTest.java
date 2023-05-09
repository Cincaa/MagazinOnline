package com.awbd.proiect1.service;


import com.awbd.proiect1.domain.User;
import com.awbd.proiect1.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    private final Long id = 1L;

    @Test
    public void findById() {

        Throwable exception = assertThrows(RuntimeException.class, () -> userService.findById(id));
        assertEquals("User not found!", exception.getMessage());

        User user = new User();
        user.setId(id);

        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        User userResult = userService.findById(id);
        assertEquals(userResult, user);
        verify(userRepository, times(2)).findById(id);
    }
}

package org.gil.mgm.users.api.service;

import org.gil.mgm.users.api.entity.UserEntity;
import org.gil.mgm.users.api.exception.ResourceNotFoundException;
import org.gil.mgm.users.api.model.User;
import org.gil.mgm.users.api.repository.UserRepository;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private static final ModelMapper MODEL_MAPPER = new ModelMapper();
    private static final EasyRandom easyRandom = new EasyRandom();

    @Nested
    @DisplayName("Tests for getUserById method")
    class GetUserByIdTests {

        @Test
        @DisplayName("Should return User when found by ID")
        void shouldReturnUserWhenFound() {
            UserEntity userEntity = easyRandom.nextObject(UserEntity.class);
            Long userId = userEntity.getId();

            when(userRepository.findById(userId))
                    .thenReturn(Optional.of(userEntity));

            User result = userService.getUserById(userId);
            assertNotNull(result);
            assertEquals(userEntity.getId(), result.getId());
            verify(userRepository).findById(userId);
        }

        @Test
        @DisplayName("Should throw ResourceNotFoundException when User is not found")
        void shouldThrowExceptionWhenUserNotFound() {
            Long userId = easyRandom.nextObject(Long.class);
            when(userRepository.findById(userId))
                    .thenReturn(Optional.empty());

            assertThrows(ResourceNotFoundException.class, () -> userService.getUserById(userId));
            verify(userRepository).findById(userId);
        }

    }
}
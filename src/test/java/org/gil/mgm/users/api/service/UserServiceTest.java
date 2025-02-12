package org.gil.mgm.users.api.service;

import org.gil.mgm.users.api.entity.UserEntity;
import org.gil.mgm.users.api.exception.ResourceNotFoundException;
import org.gil.mgm.users.api.exception.ValidationException;
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

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

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


    @Nested
    @DisplayName("Tests for getAllUsers method")
    class GetAllUsersTests {

        @Test
        @DisplayName("Should return all users mapped correctly")
        void shouldReturnAllUsers() {
            List<UserEntity> userEntities = easyRandom.objects(UserEntity.class, 5).toList();
            when(userRepository.findAll()).thenReturn(userEntities);

            List<User> users = userService.getAllUsers();

            assertEquals(userEntities.size(), users.size());
            verify(userRepository).findAll();
        }
    }

    @Nested
    @DisplayName("Tests for getUsersByDateRange method")
    class GetUsersByDateRangeTests {

        @Test
        @DisplayName("Should return users within date range")
        void shouldReturnUsersWithinDateRange() {
            LocalDate start = LocalDate.now().minusDays(10);
            LocalDate end = LocalDate.now();
            List<UserEntity> userEntities = easyRandom.objects(UserEntity.class, 3).toList();

            when(userRepository.findByDateCreatedBetween(start, end)).thenReturn(userEntities);

            List<User> users = userService.getUsersByDateRange(start, end);

            assertEquals(userEntities.size(), users.size());
            verify(userRepository).findByDateCreatedBetween(start, end);
        }

        @Test
        @DisplayName("Should throw ValidationException when start date is after end date")
        void shouldThrowExceptionWhenStartDateAfterEndDate() {
            LocalDate start = LocalDate.now();
            LocalDate end = LocalDate.now().minusDays(1);

            assertThrows(ValidationException.class, () -> userService.getUsersByDateRange(start, end));
            verifyNoInteractions(userRepository);
        }
    }

    @Nested
    @DisplayName("Tests for getUsersByProfession method")
    class GetUsersByProfessionTests {

        @Test
        @DisplayName("Should return users by profession")
        void shouldReturnUsersByProfession() {
            String profession = "Software Engineer";
            List<UserEntity> userEntities = easyRandom.objects(UserEntity.class, 4).toList();

            when(userRepository.findByProfession(profession)).thenReturn(userEntities);

            List<User> users = userService.getUsersByProfession(profession);

            assertEquals(userEntities.size(), users.size());
            verify(userRepository).findByProfession(profession);
        }
    }


}
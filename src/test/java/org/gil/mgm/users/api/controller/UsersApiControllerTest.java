package org.gil.mgm.users.api.controller;

import org.gil.mgm.users.api.exception.ResourceNotFoundException;
import org.gil.mgm.users.api.model.User;
import org.gil.mgm.users.api.service.UserService;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.gil.mgm.users.api.exception.ErrorCodes.INTERNAL_EXCEPTION_ERROR_CODE;
import static org.gil.mgm.users.api.exception.ErrorCodes.getFormattedErrorMessage;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(UsersApiController.class)
public class UsersApiControllerTest {

    @InjectMocks
    private UsersApiController UsersApiController;

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    private final EasyRandom generator = new EasyRandom();

    private static final String USERS_ENDPOINT = "/users";

    @Nested
    @DisplayName("GET " + USERS_ENDPOINT + "/{userId}")
    class GetUserById {

        @Test
        @DisplayName("Should return HTTP 200 OK with the user by id")
        void shouldReturnUserById() throws Exception {
            User user = generator.nextObject(User.class);
            Long userId = user.getId();
            when(userService.getUserById(userId)).thenReturn(user);

            mockMvc.perform(get(USERS_ENDPOINT + "/" + userId)
                            .header("correlationId", UUID.randomUUID().toString())
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.id").value(user.getId()))
                    .andExpect(jsonPath("$.firstname").value(user.getFirstname()))
                    .andExpect(jsonPath("$.lastname").value(user.getLastname()))
                    .andExpect(jsonPath("$.email").value(user.getEmail()))
                    .andExpect(jsonPath("$.profession").value(user.getProfession()))
                    .andExpect(jsonPath("$.dateCreated").value(user.getDateCreated().toString()))
                    .andExpect(jsonPath("$.country").value(user.getCountry()))
                    .andExpect(jsonPath("$.city").value(user.getCity()))
                    .andReturn();

            verify(userService).getUserById(userId);
        }

        @Test
        @DisplayName("Should return HTTP 404 Not Found when user is not found")
        void shouldReturnNotFoundWhenUserNotFound() throws Exception {
            Long userId = generator.nextLong();
            when(userService.getUserById(userId)).thenThrow(ResourceNotFoundException.class);

            mockMvc.perform(get(USERS_ENDPOINT + "/" + userId)
                            .header("correlationId", UUID.randomUUID().toString())
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());

            verify(userService).getUserById(userId);
        }

        @Test
        @DisplayName("Should return HTTP 500 Internal Server Error on service failure")
        void shouldReturnInternalServerErrorOnException() throws Exception {
            String exceptionMessage = "Unexpected Error";
            Long userId = generator.nextLong();
            when(userService.getUserById(userId)).thenThrow(new RuntimeException(exceptionMessage));

            mockMvc.perform(get(USERS_ENDPOINT + "/" + userId)
                            .header("correlationId", UUID.randomUUID().toString())
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isInternalServerError())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.code").value(INTERNAL_EXCEPTION_ERROR_CODE))
                    .andExpect(jsonPath("$.message").value(getFormattedErrorMessage(INTERNAL_EXCEPTION_ERROR_CODE, exceptionMessage)))
                    .andReturn();

            verify(userService).getUserById(userId);
        }
    }

    @Nested
    @DisplayName("GET " + USERS_ENDPOINT)
    class GetUsers {

        @Test
        @DisplayName("Should return all users when no filters are provided")
        void shouldReturnAllUsers() throws Exception {
            List<User> users = generator.objects(User.class, 5).toList();
            when(userService.getAllUsers()).thenReturn(users);

            mockMvc.perform(get(USERS_ENDPOINT)
                            .header("correlationId", UUID.randomUUID().toString())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.length()").value(users.size()));

            verify(userService).getAllUsers();
        }

        @Test
        @DisplayName("Should return users filtered by profession")
        void shouldReturnUsersByProfession() throws Exception {
            String profession = "Software Engineer";
            List<User> users = generator.objects(User.class, 3).toList();
            when(userService.getUsersByProfession(profession)).thenReturn(users);

            mockMvc.perform(get(USERS_ENDPOINT)
                            .header("correlationId", UUID.randomUUID().toString())
                            .param("profession", profession)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.length()").value(users.size()));

            verify(userService).getUsersByProfession(profession);
        }

        @Test
        @DisplayName("Should return users filtered by date range")
        void shouldReturnUsersByDateRange() throws Exception {
            LocalDate startDate = LocalDate.now().minusDays(10);
            LocalDate endDate = LocalDate.now();
            List<User> users = generator.objects(User.class, 4).toList();
            when(userService.getUsersByDateRange(startDate, endDate)).thenReturn(users);

            mockMvc.perform(get(USERS_ENDPOINT)
                            .header("correlationId", UUID.randomUUID().toString())
                            .param("startDate", startDate.toString())
                            .param("endDate", endDate.toString())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.length()").value(users.size()));

            verify(userService).getUsersByDateRange(startDate, endDate);
        }

        @Test
        @DisplayName("Should return 400 Bad Request when only one date is provided")
        void shouldReturnBadRequestWhenOneDateProvided() throws Exception {
            LocalDate startDate = LocalDate.now().minusDays(5);

            mockMvc.perform(get(USERS_ENDPOINT)
                            .header("correlationId", UUID.randomUUID().toString())
                            .param("startDate", startDate.toString())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());

            verifyNoInteractions(userService);
        }
    }

}
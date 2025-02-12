package org.gil.mgm.users.api.service;

import org.gil.mgm.users.api.entity.UserEntity;
import org.gil.mgm.users.api.exception.ResourceNotFoundException;
import org.gil.mgm.users.api.exception.ValidationException;
import org.gil.mgm.users.api.model.User;
import org.gil.mgm.users.api.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private static final ModelMapper MODEL_MAPPER = new ModelMapper();
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .map(user -> MODEL_MAPPER.map(user, User.class))
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> MODEL_MAPPER.map(user, User.class))
                .collect(Collectors.toList());
    }

    public List<User> getUsersByDateRange(LocalDate start, LocalDate end) {
        if(start.isAfter(end)) {
            throw new ValidationException("Start date cannot be after end date");
        }

        return userRepository.findByDateCreatedBetween(start, end)
                .stream()
                .map(user -> MODEL_MAPPER.map(user, User.class))
                .collect(Collectors.toList());
    }

    public List<User> getUsersByProfession(String profession) {
        return userRepository.findByProfession(profession)
                .stream()
                .map(user -> MODEL_MAPPER.map(user, User.class))
                .collect(Collectors.toList());
    }

    public User createUser(User userModel) {
        UserEntity userEntity = MODEL_MAPPER.map(userModel, UserEntity.class);
        userEntity = userRepository.save(userEntity);
        return MODEL_MAPPER.map(userEntity, User.class);
    }

}


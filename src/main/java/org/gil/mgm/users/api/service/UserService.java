package org.gil.mgm.users.api.service;

import org.gil.mgm.users.api.exception.ResourceNotFoundException;
import org.gil.mgm.users.api.model.User;
import org.gil.mgm.users.api.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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

}


package com.example.wellnesstracker.service.user;


import com.example.wellnesstracker.dto.user.UpdateUserDto;
import com.example.wellnesstracker.dto.user.UserDto;
import com.example.wellnesstracker.model.Auth;
import com.example.wellnesstracker.model.User;
import com.example.wellnesstracker.repository.AuthRepository;
import com.example.wellnesstracker.repository.UserRepository;
import com.example.wellnesstracker.service.user.error.UserNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

    public final AuthRepository authRepository;
    private final UserRepository userRepository;

    @Autowired
    public UserService(AuthRepository authRepository, UserRepository userRepository) {
        this.authRepository = authRepository;
        this.userRepository = userRepository;
    }

    public UserDto getUserByUserName(String userName) {
        Auth auth = authRepository.findByUsername(userName).orElseThrow(() -> UserNotFound.createWithUsername(userName));
        User user = userRepository.findById(auth.getUserId()).orElseThrow(() -> UserNotFound.createWithId(auth.getUserId()));
        return mapToUserDto(user);
    }

    public UserDto getUserById(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> UserNotFound.createWithId(userId));
        return mapToUserDto(user);
    }

    public UserDto getCurrentUser(String userId) {
        return getUserById(userId);
    }

    @Transactional
    public UserDto updateUser(String userId, UpdateUserDto updateDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> UserNotFound.createWithId(userId));

        // Update only non-null fields
        if (updateDto.getName() != null) {
            user.setName(updateDto.getName());
        }
        if (updateDto.getSurname() != null) {
            user.setSurname(updateDto.getSurname());
        }
        if (updateDto.getEmail() != null) {
            user.setEmail(updateDto.getEmail());
        }
        if (updateDto.getGender() != null) {
            user.setGender(updateDto.getGender());
        }
        if (updateDto.getHeight() != null) {
            user.setHeight(updateDto.getHeight());
        }
        if (updateDto.getWeight() != null) {
            user.setWeight(updateDto.getWeight());
        }

        User updatedUser = userRepository.save(user);
        return mapToUserDto(updatedUser);
    }

    @Transactional
    public void deleteUser(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> UserNotFound.createWithId(userId));

        // Delete user
        userRepository.delete(user);

        // Delete associated auth record
        authRepository.findByUserId(userId).ifPresent(authRepository::delete);
    }

    public boolean existsByUsername(String username) {
        return authRepository.findByUsername(username).isPresent();
    }

    public boolean existsByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public Optional<UserDto> getUserByEmail(String email) {
        return userRepository.findByEmail(email).map(this::mapToUserDto);
    }
    private UserDto mapToUserDto(User user) {
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getSurname(),
                user.getEmail(),
                user.getGender(),
                user.getHeight(),
                user.getWeight()
        );
    }
}

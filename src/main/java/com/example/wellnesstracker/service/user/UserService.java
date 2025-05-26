package com.example.wellnesstracker.service.user;


import com.example.wellnesstracker.dto.user.UserDto;
import com.example.wellnesstracker.model.Auth;
import com.example.wellnesstracker.model.User;
import com.example.wellnesstracker.repository.AuthRepository;
import com.example.wellnesstracker.repository.UserRepository;
import com.example.wellnesstracker.service.user.error.UserNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return new UserDto(user.getId(), user.getName(), user.getEmail());
    }

}

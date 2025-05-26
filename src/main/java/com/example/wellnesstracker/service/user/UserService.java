package com.example.wellnesstracker.service.user;


import com.example.wellnesstracker.dto.UserDto;
import com.example.wellnesstracker.model.Auth;
import com.example.wellnesstracker.model.User;
import com.example.wellnesstracker.repository.AuthRepository;
import com.example.wellnesstracker.service.user.error.UserNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public final AuthRepository authRepository;

    @Autowired
    public UserService(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public UserDto getUserByUserName(String userName) {
        Auth auth = authRepository.findByUsername(userName).orElseThrow(() -> UserNotFound.createWithUsername(userName));
        User user = auth.getUser();
        return new UserDto(user.getId(), user.getName(), user.getEmail());
    }

}

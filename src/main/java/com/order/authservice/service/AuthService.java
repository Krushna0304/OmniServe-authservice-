package com.order.authservice.service;

import com.order.authservice.dto.UserCredentialsDto;
import com.order.authservice.entity.UserCredentials;
import com.order.authservice.repository.UserCredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

    @Autowired
    private UserCredentialsRepository userCredentialsRepository;

    public UserCredentialsDto createUser(UserCredentialsDto userCredentialsDto) throws Exception {

        UserCredentials userCredentials = UserCredentials.builder()
                .username(userCredentialsDto.username())
                .password(userCredentialsDto.password())
                .userRole(userCredentialsDto.userRole())
                .build();

        userCredentialsRepository.save(userCredentials);

        return userCredentialsDto;
    }

    public String login(UserCredentialsDto userCredentialsDto) throws ResponseStatusException {
        UserCredentials userCredentials = userCredentialsRepository.findByUsername(userCredentialsDto.username())
             .orElseThrow(() ->  new ResponseStatusException(HttpStatus.UNAUTHORIZED,"User not found"));

        if(userCredentials.getPassword().equals(userCredentialsDto.password()) && userCredentials.getUserRole().toString().equals(userCredentialsDto.userRole())) {
            //return JWT token
            return "Login successful";
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Invalid credentials");
        }
    }
}

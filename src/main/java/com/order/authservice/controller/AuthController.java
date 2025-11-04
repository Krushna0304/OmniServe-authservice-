package com.order.authservice.controller;

import com.order.authservice.dto.UserCredentialsDto;
import com.order.authservice.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    //create-user
    @PostMapping("/create-user")
    public ResponseEntity<?> createUser(@RequestBody UserCredentialsDto userCredentialsDto)
    {
        try{
            return new ResponseEntity<>(authService.createUser(userCredentialsDto), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserCredentialsDto userCredentialsDto)
    {
        try{
            return new ResponseEntity<>(authService.login(userCredentialsDto), HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getReason(), e.getStatusCode());
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

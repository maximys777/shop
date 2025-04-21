package com.maximys777.shop.controllers;

import com.maximys777.shop.dto.request.UserLoginRequest;
import com.maximys777.shop.dto.response.AuthResponse;
import com.maximys777.shop.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("") // добавить ссылку для API
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestParam String userName,
                                           @RequestParam String userPassword,
                                           @RequestParam String userGmail) {
        String response = authService.register(userName, userPassword, userGmail);
        if (response.contains("существует")) {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody UserLoginRequest request) {
        AuthResponse authResponse = authService.login(request);
        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }
}

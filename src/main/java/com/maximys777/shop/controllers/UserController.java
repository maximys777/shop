package com.maximys777.shop.controllers;

import com.maximys777.shop.dto.response.UserResponse;
import com.maximys777.shop.entities.UserEntity;
import com.maximys777.shop.globalmapper.GlobalMapper;
import com.maximys777.shop.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("") // добавить ссылку на профиль пользователя
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserResponse> getCurrentUser() {
        UserEntity user = userService.getCurrentUser();
        return ResponseEntity.ok(GlobalMapper.mapToUserResponse(user));
    }
}

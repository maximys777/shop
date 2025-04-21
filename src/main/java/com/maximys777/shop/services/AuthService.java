package com.maximys777.shop.services;

import com.maximys777.shop.config.JwtGenerator;
import com.maximys777.shop.dto.request.UserLoginRequest;
import com.maximys777.shop.dto.response.AuthResponse;
import com.maximys777.shop.entities.RoleEnum;
import com.maximys777.shop.entities.UserEntity;
import com.maximys777.shop.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtGenerator jwtGenerator;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public String register(String userName, String userPassword, String userGmail) {
        if (userRepository.existsByUserGmail(userGmail)) {
            return "Аккаунт с такой электронной почтой уже существует.";
        }

        UserEntity user = new UserEntity();
        user.setUserName(userName);
        user.setUserPassword(passwordEncoder.encode(userPassword));
        user.setUserGmail(userGmail);
        user.setUserRole(RoleEnum.USER);
        userRepository.save(user);
        return "Пользователь успешно зарегистрирован";
    }

    public AuthResponse login(UserLoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUserGmail(), request.getUserPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateJwt(authentication);
        return new AuthResponse(token);
    }
}

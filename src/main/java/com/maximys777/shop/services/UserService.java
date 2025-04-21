package com.maximys777.shop.services;

import com.maximys777.shop.entities.UserEntity;
import com.maximys777.shop.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@AllArgsConstructor
@Log4j2
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    public UserEntity getCurrentUser() {
        String gmail = getCurrentUserGmail();

        log.info("Получена информация о пользователе: {}", gmail);
        return userRepository.findByUserGmail(gmail).orElseThrow(() ->
                new UsernameNotFoundException("Вы не вошли в аккаунт"));
    }

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        UserEntity e = userRepository.findByUserGmail(userEmail).orElseThrow(() ->
                new UsernameNotFoundException("Пользователь с такой почтой не найден."));

        return new User(e.getUserGmail(),
                e.getUserPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(e.getUserRole().name())));
    }

    private String getCurrentUserGmail() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }
}

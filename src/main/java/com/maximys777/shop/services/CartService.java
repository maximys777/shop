package com.maximys777.shop.services;

import com.maximys777.shop.dto.response.CartResponse;
import com.maximys777.shop.entities.CartEntity;
import com.maximys777.shop.entities.UserEntity;
import com.maximys777.shop.globalexceptions.CartNotFoundException;
import com.maximys777.shop.globalmapper.GlobalMapper;
import com.maximys777.shop.repositories.CartRepository;
import com.maximys777.shop.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final UserRepository userRepository;

    @Transactional
    public CartResponse getCartFromCurrentUser() {
        String gmail = getCurrentUserGmail();

        UserEntity entity = userRepository.findByUserGmail(gmail)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден."));

        CartEntity cart = cartRepository.findByUser(entity)
                .orElseThrow(() -> new CartNotFoundException("Корзина не найдена."));

        return GlobalMapper.mapToCartResponse(cart);
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

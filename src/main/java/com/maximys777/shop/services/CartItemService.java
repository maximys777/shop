package com.maximys777.shop.services;

import com.maximys777.shop.dto.request.CartItemRequest;
import com.maximys777.shop.dto.response.CartItemResponse;
import com.maximys777.shop.entities.CartEntity;
import com.maximys777.shop.entities.CartItemEntity;
import com.maximys777.shop.entities.ProductEntity;
import com.maximys777.shop.entities.UserEntity;
import com.maximys777.shop.globalexceptions.CartNotFoundException;
import com.maximys777.shop.globalexceptions.ProductNotFoundException;
import com.maximys777.shop.globalmapper.GlobalMapper;
import com.maximys777.shop.repositories.CartItemRepository;
import com.maximys777.shop.repositories.CartRepository;
import com.maximys777.shop.repositories.ProductRepository;
import com.maximys777.shop.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Transactional
public class CartItemService {
    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public CartItemResponse addProductToCart(CartItemRequest request) {
        UserEntity user  = getCurrentUser();

        CartEntity cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new CartNotFoundException("Корзина не найдена."));

        ProductEntity product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ProductNotFoundException("Товар не найден."));

        var existingItemOpt = cart.getItem().stream()
                .filter(item -> item.getProduct().getProductId().equals(request.getProductId()))
                .findFirst();

        var itemQuantity = request.getCartItemQuantity();

        CartItemEntity cartItem;

        if (existingItemOpt.isPresent()) {
            cartItem = existingItemOpt.get();
            cartItem.setCartItemQuantity(cartItem.getCartItemQuantity() + itemQuantity);
            cartItem.setCartItemPrice(
                    product.getProductPrice().multiply(BigDecimal.valueOf(cartItem.getCartItemQuantity()))
            );
        } else {
            cartItem = new CartItemEntity();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setCartItemQuantity(itemQuantity);
            cartItem.setCartItemPrice(product.getProductPrice().multiply(BigDecimal.valueOf(itemQuantity)));
            cart.getItem().add(cartItem);
        }
        cartItemRepository.save(cartItem);

        return GlobalMapper.mapToCartItemResponse(cartItem);
    }

    public CartItemResponse updateCartItem(Long cartItemId, CartItemRequest request) {
        UserEntity user = getCurrentUser();

        CartEntity cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new CartNotFoundException("Корзина не найдена."));

        CartItemEntity item = cart.getItem().stream()
                .filter(i -> i.getProduct().getProductId().equals(cartItemId))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException("Товар не найден в корзине."));

        BigDecimal basePrice = item.getProduct().getProductPrice();

        item.setCartItemQuantity(request.getCartItemQuantity());
        item.setCartItemPrice(basePrice.multiply(BigDecimal.valueOf(request.getCartItemQuantity())));

        cartRepository.save(cart);

        return GlobalMapper.mapToCartItemResponse(item);
    }

    public void deleteFromCart(Long cartItemId) {
        UserEntity user = getCurrentUser();

        CartEntity cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new CartNotFoundException("Корзина не найдена."));

        CartItemEntity item = cart.getItem().stream()
                .filter(i -> i.getProduct().getProductId().equals(cartItemId))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException("Товар не найден в корзине."));

        cart.getItem().remove(item);
        cartItemRepository.delete(item);
    }

    public void clearCart() {
        UserEntity user = getCurrentUser();

        CartEntity cart = cartRepository.findByUser(user)
                        .orElseThrow(() -> new CartNotFoundException("Корзина не найдена."));

        cart.getItem().clear();
        cartRepository.save(cart);
    }


    private UserEntity getCurrentUser() {
        String email = getCurrentUserGmail();

        return userRepository.findByUserGmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден."));
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

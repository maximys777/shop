package com.maximys777.shop.controllers;

import com.maximys777.shop.dto.request.CartItemRequest;
import com.maximys777.shop.dto.response.CartItemResponse;
import com.maximys777.shop.services.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart-item")
@RequiredArgsConstructor
public class CartItemController {
    private final CartItemService cartItemService;

    @PostMapping("/add")
    public ResponseEntity<CartItemResponse> addCartItem(@RequestBody CartItemRequest cartItemRequest) {
        return ResponseEntity.ok(cartItemService.addProductToCart(cartItemRequest));
    }

    @PutMapping("/edit/{cartItemId}")
    public ResponseEntity<CartItemResponse> updateCartItem(@PathVariable Long cartItemId, @RequestBody CartItemRequest cartItemRequest) {
        return ResponseEntity.ok(cartItemService.updateCartItem(cartItemId, cartItemRequest));
    }

    @DeleteMapping("/delete/{cartItemId}")
    public ResponseEntity<CartItemResponse> deleteCartItem(@PathVariable Long cartItemId) {
        cartItemService.deleteFromCart(cartItemId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/clear")
    public ResponseEntity<CartItemResponse> clearCartItem() {
        cartItemService.clearCart();
        return ResponseEntity.ok().build();
    }
}

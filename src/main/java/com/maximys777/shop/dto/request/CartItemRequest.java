package com.maximys777.shop.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CartItemRequest {
    private Long productId;
    private int cartItemQuantity;
}

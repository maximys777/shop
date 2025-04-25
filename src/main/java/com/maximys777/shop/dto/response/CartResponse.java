package com.maximys777.shop.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class CartResponse {
    private Long cartId;
    private Long userId;
    private List<CartItemResponse> cartItems;
}

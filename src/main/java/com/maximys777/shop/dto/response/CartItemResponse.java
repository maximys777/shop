package com.maximys777.shop.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class CartItemResponse {
    private Long cartItemId;
    private int cartItemQuantity;
    private BigDecimal cartItemPrice;
    private ProductResponse product;
}

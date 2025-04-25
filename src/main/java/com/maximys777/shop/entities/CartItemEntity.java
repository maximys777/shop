package com.maximys777.shop.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "cart_item")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_id")
    private Long cartItemId;

    @Column(name = "cart_item_quantity", nullable = false)
    private int cartItemQuantity;

    @Column(name = "cart_item_price", nullable = false)
    private BigDecimal cartItemPrice;

    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false)
    private CartEntity cart;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;
}

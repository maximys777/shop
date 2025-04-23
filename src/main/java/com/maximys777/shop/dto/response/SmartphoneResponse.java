package com.maximys777.shop.dto.response;

import com.maximys777.shop.entities.ProductCategoryEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
@AllArgsConstructor
public class SmartphoneResponse {
    private Long productId;
    private String productImage;
    private String productTitle;
    private String productBrand;
    private String productDescription;
    private BigDecimal productPrice;
    private String productAvailable;
    private ProductCategoryEnum productCategoryEnum;

    private String smartphoneModel;
    private String smartphoneOs;
    private String smartphoneStorage;
    private Integer smartphoneRam;
    private String smartphoneColor;
    private Integer batteryCapacity;
    private String batteryUnit;
    private Integer smartphoneModelYear;
}

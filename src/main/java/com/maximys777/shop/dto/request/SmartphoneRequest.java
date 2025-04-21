package com.maximys777.shop.dto.request;

import com.maximys777.shop.entities.ProductCategoryEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SmartphoneRequest {
    private String productImage;
    private String productTitle;
    private String productBrand;
    private String productDescription;
    private BigDecimal productPrice;
    private Boolean productAvailable;
    private ProductCategoryEnum productCategoryEnum;

    private String smartphoneModel;
    private String smartphoneOs;
    private Integer smartphoneStorage;
    private Integer smartphoneRam;
    private String smartphoneColor;
    private Integer batteryCapacity;
    private String batteryUnit;
    private Integer smartphoneModelYear;
}

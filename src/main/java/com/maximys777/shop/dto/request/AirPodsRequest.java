package com.maximys777.shop.dto.request;

import com.maximys777.shop.entities.ProductCategoryEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AirPodsRequest {
    private String productImage;
    private String productTitle;
    private String productBrand;
    private String productDescription;
    private BigDecimal productPrice;
    private Boolean productAvailable;
    private ProductCategoryEnum productCategoryEnum;

    private String AirPodsModel;
    private String AirPodsColor;
    private Integer AirPodsModelYear;
    private Integer AirPodsWorkTime;
}

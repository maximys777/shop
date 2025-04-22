package com.maximys777.shop.dto.response;

import com.maximys777.shop.entities.ProductCategoryEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Setter;

import java.math.BigDecimal;

@Builder
@Setter
@AllArgsConstructor
public class AirPodsResponse {
    private Long productId;
    private String productImage;
    private String productTitle;
    private String productBrand;
    private String productDescription;
    private BigDecimal productPrice;
    private String productAvailable;
    private ProductCategoryEnum productCategoryEnum;

    private String AirPodsModel;
    private String AirPodsColor;
    private Integer AirPodsModelYear;
    private Integer AirPodsWorkTime;
}

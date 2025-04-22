package com.maximys777.shop.dto.response;

import com.maximys777.shop.entities.ProductCategoryEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Setter;

import java.math.BigDecimal;

@Builder
@Setter
@AllArgsConstructor
public class LaptopResponse {
    private Long productId;
    private String productImage;
    private String productTitle;
    private String productBrand;
    private String productDescription;
    private BigDecimal productPrice;
    private String productAvailable;
    private ProductCategoryEnum productCategoryEnum;

    private String laptopModel;
    private Double laptopDiagonal;
    private String laptopProcessor;
    private String laptopOs;
    private Integer laptopRam;
    private Integer laptopStorage;
    private String laptopGraphCard;
    private String laptopColor;
    private Double laptopBattery;
    private Integer laptopModelYear;
}

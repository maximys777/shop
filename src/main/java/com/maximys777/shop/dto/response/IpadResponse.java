package com.maximys777.shop.dto.response;

import com.maximys777.shop.entities.ProductCategoryEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Setter;

import java.math.BigDecimal;

@Builder
@Setter
@AllArgsConstructor
public class IpadResponse {
    private Long productId;
    private String productImage;
    private String productTitle;
    private String productBrand;
    private String productDescription;
    private BigDecimal productPrice;
    private Boolean productAvailable;
    private ProductCategoryEnum productCategoryEnum;

    private String ipadModel;
    private String ipadColor;
    private String ipadProcessor;
    private Integer ipadCapacity;
    private String ipadOs;
    private Integer ipadDiagonal;
    private Integer ipadModelYear;
}

package com.maximys777.shop.dto.request;

import com.maximys777.shop.entities.ProductCategoryEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class IpadRequest {
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

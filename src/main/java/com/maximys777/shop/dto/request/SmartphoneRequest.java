package com.maximys777.shop.dto.request;

import com.maximys777.shop.entities.ProductCategoryEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SmartphoneRequest {
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

package com.maximys777.shop.globalmapper;

import com.maximys777.shop.dto.response.*;
import com.maximys777.shop.entities.*;

public class GlobalMapper {
    public static SmartphoneResponse mapToSmartphoneResponse(SmartphoneEntity entity) {
        return SmartphoneResponse.builder()
                .productId(entity.getProductId())
                .productImage(entity.getProductImage())
                .productTitle(entity.getProductTitle())
                .productBrand(entity.getProductBrand())
                .productDescription(entity.getProductDescription())
                .productPrice(entity.getProductPrice())
                .productAvailable(entity.getProductAvailable())
                .productCategoryEnum(entity.getProductCategoryEnum())
                .smartphoneModel(entity.getSmartphoneModel())
                .smartphoneOs(entity.getSmartphoneOs())
                .smartphoneStorage(entity.getSmartphoneStorage())
                .smartphoneRam(entity.getSmartphoneRam())
                .smartphoneColor(entity.getSmartphoneColor())
                .batteryCapacity(entity.getBatteryCapacity())
                .batteryUnit(entity.getBatteryUnit())
                .smartphoneModelYear(entity.getSmartphoneModelYear())
                .build();
    }

    public static LaptopResponse mapToLaptopResponse(LaptopEntity entity) {
        return LaptopResponse.builder()
                .productId(entity.getProductId())
                .productImage(entity.getProductImage())
                .productTitle(entity.getProductTitle())
                .productBrand(entity.getProductBrand())
                .productDescription(entity.getProductDescription())
                .productPrice(entity.getProductPrice())
                .productAvailable(entity.getProductAvailable())
                .productCategoryEnum(entity.getProductCategoryEnum())
                .laptopModel(entity.getLaptopModel())
                .laptopDiagonal(entity.getLaptopDiagonal())
                .laptopProcessor(entity.getLaptopProcessor())
                .laptopOs(entity.getLaptopOs())
                .laptopRam(entity.getLaptopRam())
                .laptopStorage(entity.getLaptopStorage())
                .laptopGraphCard(entity.getLaptopGraphCard())
                .laptopColor(entity.getLaptopColor())
                .laptopBattery(entity.getLaptopBattery())
                .laptopModelYear(entity.getLaptopModelYear())
                .build();
    }

    public static IpadResponse mapToIpadResponse(IpadEntity entity) {
        return IpadResponse.builder()
                .productId(entity.getProductId())
                .productImage(entity.getProductImage())
                .productTitle(entity.getProductTitle())
                .productBrand(entity.getProductBrand())
                .productDescription(entity.getProductDescription())
                .productPrice(entity.getProductPrice())
                .productAvailable(entity.getProductAvailable())
                .productCategoryEnum(entity.getProductCategoryEnum())
                .ipadModel(entity.getIpadModel())
                .ipadColor(entity.getIpadColor())
                .ipadProcessor(entity.getIpadProcessor())
                .ipadCapacity(entity.getIpadCapacity())
                .ipadOs(entity.getIpadOs())
                .ipadDiagonal(entity.getIpadDiagonal())
                .ipadModelYear(entity.getIpadModelYear())
                .build();
    }

    public static AirPodsResponse mapToAirPodsResponse(AirPodsEntity entity) {
        return AirPodsResponse.builder()
                .productId(entity.getProductId())
                .productImage(entity.getProductImage())
                .productTitle(entity.getProductTitle())
                .productBrand(entity.getProductBrand())
                .productDescription(entity.getProductDescription())
                .productPrice(entity.getProductPrice())
                .productAvailable(entity.getProductAvailable())
                .productCategoryEnum(entity.getProductCategoryEnum())
                .AirPodsModel(entity.getAirPodsModel())
                .AirPodsColor(entity.getAirPodsColor())
                .AirPodsModelYear(entity.getAirPodsModelYear())
                .AirPodsWorkTime(entity.getAirPodsWorkTime())
                .build();
    }

    public static UserResponse mapToUserResponse(UserEntity entity) {
        return UserResponse.builder()
                .userName(entity.getUserName())
                .userGmail(entity.getUserGmail())
                .userCreatedAt(entity.getUserCreatedAt())
                .userRole(entity.getUserRole().name())
                .build();
    }
}

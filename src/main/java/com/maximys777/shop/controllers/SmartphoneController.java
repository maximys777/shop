package com.maximys777.shop.controllers;

import com.maximys777.shop.dto.response.SmartphoneResponse;
import com.maximys777.shop.entities.ProductCategoryEnum;
import com.maximys777.shop.services.SmartphoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/smartphone")
@RequiredArgsConstructor
public class SmartphoneController {
    private final SmartphoneService smartphoneService;

    @PostMapping("/create")
    public ResponseEntity<SmartphoneResponse> create(
            @RequestParam("productImage") MultipartFile productImage,
            @RequestParam("productTitle") String productTitle,
            @RequestParam("productBrand") String productBrand,
            @RequestParam("productDescription") String productDescription,
            @RequestParam("productPrice")BigDecimal productPrice,
            @RequestParam("productAvailable") String productAvailable,
            @RequestParam("productCategoryEnum") ProductCategoryEnum productCategoryEnum,
            @RequestParam("smartphoneModel") String smartphoneModel,
            @RequestParam("smartphoneOs") String smartphoneOs,
            @RequestParam("smartphoneStorage") Integer smartphoneStorage,
            @RequestParam("smartphoneRam") Integer smartphoneRam,
            @RequestParam("smartphoneColor") String smartphoneColor,
            @RequestParam("batteryCapacity") Integer batteryCapacity,
            @RequestParam("batteryUnit") String batteryUnit,
            @RequestParam("smartphoneModelYear") Integer smartphoneModelYear

            ) {
        SmartphoneResponse response = smartphoneService.create(productImage, productTitle,
                                                               productBrand, productDescription,
                                                               productPrice, productAvailable,
                                                               productCategoryEnum, smartphoneModel,
                                                               smartphoneOs, smartphoneStorage,
                                                               smartphoneRam, smartphoneColor,
                                                               batteryCapacity, batteryUnit, smartphoneModelYear);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/edit/{productId}")
    public ResponseEntity<SmartphoneResponse> update(@PathVariable Long productId,
                                                     @RequestParam(value = "productImage", required = false) MultipartFile productImage,
                                                     @RequestParam(value = "productTitle", required = false) String productTitle,
                                                     @RequestParam(value = "productBrand", required = false) String productBrand,
                                                     @RequestParam(value = "productDescription", required = false) String productDescription,
                                                     @RequestParam(value = "productPrice", required = false)BigDecimal productPrice,
                                                     @RequestParam(value = "productAvailable", required = false) String productAvailable,
                                                     @RequestParam(value = "productCategoryEnum", required = false) ProductCategoryEnum productCategoryEnum,
                                                     @RequestParam(value = "smartphoneModel", required = false) String smartphoneModel,
                                                     @RequestParam(value = "smartphoneOs", required = false) String smartphoneOs,
                                                     @RequestParam(value = "smartphoneStorage", required = false) Integer smartphoneStorage,
                                                     @RequestParam(value = "smartphoneRam", required = false) Integer smartphoneRam,
                                                     @RequestParam(value = "smartphoneColor", required = false) String smartphoneColor,
                                                     @RequestParam(value = "batteryCapacity", required = false) Integer batteryCapacity,
                                                     @RequestParam(value = "batteryUnit", required = false) String batteryUnit,
                                                     @RequestParam(value = "smartphoneModelYear", required = false) Integer smartphoneModelYear) {

        SmartphoneResponse updateSmartphone = smartphoneService.update(productId, productImage, productTitle, productBrand, productDescription, productPrice, productAvailable, productCategoryEnum, smartphoneModel, smartphoneOs, smartphoneStorage, smartphoneRam, smartphoneColor, batteryCapacity, batteryUnit, smartphoneModelYear);
        return ResponseEntity.ok(updateSmartphone);
    }

    @GetMapping("/get-by-id/{productId}")
    public ResponseEntity<SmartphoneResponse> getById(@PathVariable Long productId) {
        return ResponseEntity.ok(smartphoneService.getById(productId));
    }

    @GetMapping
    public ResponseEntity<List<SmartphoneResponse>> getAllSmartphone(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "smartphoneModel") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection
    ) {
        return ResponseEntity.ok(smartphoneService.getAllSmartphone(page, size, sortBy, sortDirection));
    }

    @GetMapping("/{productTitle}")
    public ResponseEntity<List<SmartphoneResponse>> searchByProductTitle(
            @PathVariable String productTitle,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "smartphoneModel") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection
    ) {
        return ResponseEntity.ok(smartphoneService.searchByTitle(productTitle, page, size, sortBy, sortDirection));
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<SmartphoneResponse> deleteById(@PathVariable Long productId) {
        smartphoneService.deleteById(productId);
        return ResponseEntity.ok().build();
    }
}

package com.maximys777.shop.controllers;

import com.maximys777.shop.dto.response.AirPodsResponse;
import com.maximys777.shop.entities.ProductCategoryEnum;
import com.maximys777.shop.services.AirPodsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/airpods")
@RequiredArgsConstructor
public class AirPodsController {
    private final AirPodsService airPodsService;

    @PostMapping("/create")
    public ResponseEntity<AirPodsResponse> create(
            @RequestParam("productImage") MultipartFile productImage,
            @RequestParam("productTitle") String productTitle,
            @RequestParam("productBrand") String productBrand,
            @RequestParam("productDescription") String productDescription,
            @RequestParam("productPrice")BigDecimal productPrice,
            @RequestParam("productAvailable") String productAvailable,
            @RequestParam("productCategoryEnum")ProductCategoryEnum productCategoryEnum,
            @RequestParam("AirPodsModel") String AirPodsModel,
            @RequestParam("AirPodsColor") String AirPodsColor,
            @RequestParam("AirPodsModelYear") Integer AirPodsModelYear,
            @RequestParam("AirPodsWorkTime") Integer AirPodsWorkTime
            ) {
        AirPodsResponse response = airPodsService.create(productImage, productTitle,
                                                         productBrand, productDescription,
                                                         productPrice, productAvailable,
                                                         productCategoryEnum, AirPodsModel,
                                                         AirPodsColor, AirPodsModelYear, AirPodsWorkTime);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/edit/{productId}")
    public ResponseEntity<AirPodsResponse> update(@PathVariable Long productId,
                                                  @RequestParam(value = "productImage", required = false) MultipartFile productImage,
                                                  @RequestParam(value = "productTitle", required = false) String productTitle,
                                                  @RequestParam(value = "productBrand", required = false) String productBrand,
                                                  @RequestParam(value = "productDescription", required = false) String productDescription,
                                                  @RequestParam(value = "productPrice", required = false)BigDecimal productPrice,
                                                  @RequestParam(value = "productAvailable", required = false) String productAvailable,
                                                  @RequestParam(value = "productCategoryEnum", required = false)ProductCategoryEnum productCategoryEnum,
                                                  @RequestParam(value = "AirPodsModel", required = false) String AirPodsModel,
                                                  @RequestParam(value = "AirPodsColor", required = false) String AirPodsColor,
                                                  @RequestParam(value = "AirPodsModelYear", required = false) Integer AirPodsModelYear,
                                                  @RequestParam(value = "AirPodsWorkTime", required = false) Integer AirPodsWorkTime) {
    AirPodsResponse updateAirPods = airPodsService.update(productId, productImage, productTitle,
                                                          productBrand, productDescription, productPrice,
                                                          productAvailable, productCategoryEnum, AirPodsModel,
                                                          AirPodsColor, AirPodsModelYear, AirPodsWorkTime);
    return ResponseEntity.ok(updateAirPods);
    }

    @GetMapping("/get-by-id/{productId}")
    public ResponseEntity<AirPodsResponse> getById(@PathVariable Long productId) {
        return ResponseEntity.ok(airPodsService.findById(productId));
    }

    @GetMapping
    public ResponseEntity<List<AirPodsResponse>> getAllAirPods(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "AirPodsModel") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection
    ) {
        return ResponseEntity.ok(airPodsService.findAllAirPods(page, size, sortBy, sortDirection));
    }

    @GetMapping("/{productTitle}")
    public ResponseEntity<List<AirPodsResponse>> getByTitle(@PathVariable String productTitle,
                                                      @RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "10") int size,
                                                      @RequestParam(defaultValue = "AirPodsModel") String sortBy,
                                                      @RequestParam(defaultValue = "ASC") String sortDirection) {
        return ResponseEntity.ok(airPodsService.searchByTitle(productTitle, page, size, sortBy, sortDirection));
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<AirPodsResponse> deleteById(@PathVariable Long productId) {
        airPodsService.deleteById(productId);
        return ResponseEntity.ok().build();
    }
}

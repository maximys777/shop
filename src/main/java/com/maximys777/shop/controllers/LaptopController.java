package com.maximys777.shop.controllers;

import com.maximys777.shop.dto.response.LaptopResponse;
import com.maximys777.shop.entities.ProductCategoryEnum;
import com.maximys777.shop.services.LaptopService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/laptop")
@RequiredArgsConstructor
public class LaptopController {
    private final LaptopService laptopService;

    @PostMapping("/create")
    public ResponseEntity<LaptopResponse> create(
            @RequestParam("productImage") MultipartFile productImage,
            @RequestParam("productTitle") String productTitle,
            @RequestParam("productBrand") String productBrand,
            @RequestParam("productDescription") String productDescription,
            @RequestParam("productPrice") BigDecimal productPrice,
            @RequestParam("productAvailable") String productAvailable,
            @RequestParam("productCategoryEnum") ProductCategoryEnum productCategoryEnum,
            @RequestParam("laptopModel") String laptopModel,
            @RequestParam("laptopDiagonal") Double laptopDiagonal,
            @RequestParam("laptopProcessor") String laptopProcessor,
            @RequestParam("laptopOs") String laptopOs,
            @RequestParam("laptopRam") Integer laptopRam,
            @RequestParam("laptopStorage") String laptopStorage,
            @RequestParam("laptopGraphCard") String laptopGraphCard,
            @RequestParam("laptopColor") String laptopColor,
            @RequestParam("laptopBattery") String laptopBattery,
            @RequestParam("laptopModelYear") Integer laptopModelYear
    ) {
        LaptopResponse response = laptopService.create(productImage, productTitle, productBrand,
                                                       productDescription, productPrice, productAvailable,
                                                       productCategoryEnum, laptopModel, laptopDiagonal,
                                                       laptopProcessor, laptopOs, laptopRam,
                                                       laptopStorage, laptopGraphCard, laptopColor,
                                                       laptopBattery, laptopModelYear);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/edit/{productId}")
    public ResponseEntity<LaptopResponse> edit(@PathVariable Long productId,
                                               @RequestParam(value = "productImage", required = false) MultipartFile productImage,
                                               @RequestParam(value = "productTitle", required = false) String productTitle,
                                               @RequestParam(value = "productBrand", required = false) String productBrand,
                                               @RequestParam(value = "productDescription", required = false) String productDescription,
                                               @RequestParam(value = "productPrice", required = false) BigDecimal productPrice,
                                               @RequestParam(value = "productAvailable", required = false) String productAvailable,
                                               @RequestParam(value = "productCategoryEnum", required = false) ProductCategoryEnum productCategoryEnum,
                                               @RequestParam(value = "laptopModel", required = false) String laptopModel,
                                               @RequestParam(value = "laptopDiagonal", required = false) Double laptopDiagonal,
                                               @RequestParam(value = "laptopProcessor", required = false) String laptopProcessor,
                                               @RequestParam(value = "laptopOs", required = false) String laptopOs,
                                               @RequestParam(value = "laptopRam", required = false) Integer laptopRam,
                                               @RequestParam(value = "laptopStorage", required = false) String laptopStorage,
                                               @RequestParam(value = "laptopGraphCard", required = false) String laptopGraphCard,
                                               @RequestParam(value = "laptopColor", required = false) String laptopColor,
                                               @RequestParam(value = "laptopBattery", required = false) String laptopBattery,
                                               @RequestParam(value = "laptopModelYear", required = false) Integer laptopModelYear) {
        LaptopResponse updateLaptop = laptopService.update(productId, productImage, productTitle, productBrand,
                                                           productDescription, productPrice, productAvailable, productCategoryEnum,
                                                           laptopModel, laptopDiagonal, laptopProcessor, laptopOs,
                                                           laptopRam, laptopStorage, laptopGraphCard, laptopColor, laptopBattery, laptopModelYear);
        return ResponseEntity.ok(updateLaptop);
    }

    @GetMapping("/get-by-id/{productId}")
    public ResponseEntity<LaptopResponse> getById(@PathVariable Long productId) {
        return ResponseEntity.ok(laptopService.getById(productId));
    }

    @GetMapping
    public ResponseEntity<List<LaptopResponse>> getAllLaptop(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "LaptopModel") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection
    ) {
        return ResponseEntity.ok(laptopService.findAllLaptop(page, size, sortBy, sortDirection));
    }

    @GetMapping("/{productTitle}")
    public ResponseEntity<List<LaptopResponse>> getByTitle(
            @PathVariable String productTitle,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "LaptopModel") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection) {
        return ResponseEntity.ok(laptopService.searchByTitle(productTitle, page, size, sortBy, sortDirection));
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<LaptopResponse> deleteById(@PathVariable Long productId) {
        laptopService.deleteById(productId);
        return ResponseEntity.ok().build();
    }

}

package com.maximys777.shop.controllers;

import com.maximys777.shop.dto.response.IpadResponse;
import com.maximys777.shop.entities.ProductCategoryEnum;
import com.maximys777.shop.services.IpadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/ipad")
@RequiredArgsConstructor
public class IpadController {
    private final IpadService ipadService;

    @PostMapping("/create")
    public ResponseEntity<IpadResponse> create(
            @RequestParam("productImage") MultipartFile productImage,
            @RequestParam("productTitle") String productTitle,
            @RequestParam("productBrand") String productBrand,
            @RequestParam("productDescription") String productDescription,
            @RequestParam("productPrice") BigDecimal productPrice,
            @RequestParam("productAvailable") String productAvailable,
            @RequestParam("productCategoryEnum") ProductCategoryEnum productCategoryEnum,
            @RequestParam("ipadModel") String ipadModel,
            @RequestParam("ipadColor") String ipadColor,
            @RequestParam("ipadProcessor") String ipadProcessor,
            @RequestParam("ipadCapacity") String ipadCapacity,
            @RequestParam("ipadOs") String ipadOs,
            @RequestParam("ipadDiagonal") Double ipadDiagonal,
            @RequestParam("ipadModelYear") Integer ipadModelYear
    ) {
        IpadResponse response = (ipadService.create(productImage, productTitle, productBrand, productDescription,
                                                    productPrice, productAvailable, productCategoryEnum, ipadModel,
                                                    ipadColor, ipadProcessor, ipadCapacity, ipadOs,
                                                    ipadDiagonal, ipadModelYear));
        return ResponseEntity.ok(response);
    }

    @PutMapping("/edit/{productId}")
    public ResponseEntity<IpadResponse> edit(@PathVariable Long productId,
                                             @RequestParam(value = "productImage", required = false) MultipartFile productImage,
                                             @RequestParam(value = "productTitle", required = false) String productTitle,
                                             @RequestParam(value = "productBrand", required = false) String productBrand,
                                             @RequestParam(value = "productDescription", required = false) String productDescription,
                                             @RequestParam(value = "productPrice", required = false) BigDecimal productPrice,
                                             @RequestParam(value = "productAvailable", required = false) String productAvailable,
                                             @RequestParam(value = "productCategoryEnum", required = false) ProductCategoryEnum productCategoryEnum,
                                             @RequestParam(value = "ipadModel", required = false) String ipadModel,
                                             @RequestParam(value = "ipadColor", required = false) String ipadColor,
                                             @RequestParam(value = "ipadProcessor", required = false) String ipadProcessor,
                                             @RequestParam(value = "ipadCapacity", required = false) String ipadCapacity,
                                             @RequestParam(value = "ipadOs", required = false) String ipadOs,
                                             @RequestParam(value = "ipadDiagonal", required = false) Double ipadDiagonal,
                                             @RequestParam(value = "ipadModelYear", required = false) Integer ipadModelYear) {
        IpadResponse ipadUpdate = ipadService.update(productId, productImage, productTitle, productBrand, productDescription,
                                                     productPrice, productAvailable, productCategoryEnum, ipadModel, ipadColor,
                                                     ipadProcessor, ipadCapacity, ipadOs, ipadDiagonal, ipadModelYear);
        return ResponseEntity.ok(ipadUpdate);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<IpadResponse> getById(@PathVariable Long productId) {
        return ResponseEntity.ok(ipadService.getById(productId));
    }

    @GetMapping
    public ResponseEntity<List<IpadResponse>> getAllIpad(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "AirPodsModel") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection
    ) {
        return ResponseEntity.ok(ipadService.getAllIpad(page, size, sortBy, sortDirection));
    }

    @GetMapping("/{productTitle}")
    public ResponseEntity<List<IpadResponse>> getByTitle(@PathVariable String productTitle,
                                                   @RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "10") int size,
                                                   @RequestParam(defaultValue = "AirPodsModel") String sortBy,
                                                   @RequestParam(defaultValue = "ASC") String sortDirection) {
        return ResponseEntity.ok(ipadService.searchByTitle(productTitle, page, size, sortBy, sortDirection));
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<IpadResponse> deleteById(@PathVariable Long productId) {
        ipadService.deleteById(productId);
        return ResponseEntity.ok().build();
    }
}

package com.maximys777.shop.controllers;

import com.maximys777.shop.dto.response.ProductResponse;
import com.maximys777.shop.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/get-by-id")
    public ResponseEntity<ProductResponse> getProductById(@RequestParam Long productId) {
        return ResponseEntity.ok(productService.findByProductId(productId));
    }

    @GetMapping("/")
    public ResponseEntity<List<ProductResponse>> getAllProducts(@RequestParam int page,
                                                                @RequestParam int size,
                                                                @RequestParam String sortBy,
                                                                @RequestParam String sortDirection) {
        return ResponseEntity.ok(productService.findAllProduct(page, size, sortBy, sortDirection));
    }

    @GetMapping("/get-by-title")
    public ResponseEntity<List<ProductResponse>> getProductByTitle(@RequestParam String productTitle,
                                                             @RequestParam int page,
                                                             @RequestParam int size,
                                                             @RequestParam String sortBy,
                                                             @RequestParam String sortDirection) {
        return ResponseEntity.ok(productService.searchByTitle(productTitle, page, size, sortBy, sortDirection));
    }
}

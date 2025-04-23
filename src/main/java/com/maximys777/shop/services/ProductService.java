package com.maximys777.shop.services;

import com.maximys777.shop.dto.response.ProductResponse;
import com.maximys777.shop.entities.ProductEntity;
import com.maximys777.shop.globalexceptions.NotFoundException;
import com.maximys777.shop.globalmapper.GlobalMapper;
import com.maximys777.shop.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public ProductResponse findByProductId(Long productId) {
        return productRepository.findById(productId)
                .map(GlobalMapper::mapToProductResponse)
                .orElseThrow(() -> new NotFoundException("Товар не найден."));
    }

    public List<ProductResponse> findAllProduct(int page, int size, String sortBy, String sortDirection) {
            PageRequest pageRequest = PageRequest.of(
                    page,
                    size,
                    Sort.Direction.fromString(sortDirection),
                    sortBy
            );
        Page<ProductEntity> productPage = productRepository.findAll(pageRequest);

        return productPage.stream()
                .map(GlobalMapper::mapToProductResponse)
                .collect(Collectors.toList());
    }

    public List<ProductResponse> searchByTitle(String productTitle, int page, int size, String sortBy, String sortDirection) {
        PageRequest pageRequest = PageRequest.of(
                page,
                size,
                Sort.Direction.fromString(sortDirection),
                sortBy
        );
        Page<ProductEntity> productPage = productRepository.findByProductTitleContainingIgnoreCase(productTitle, pageRequest);

        return productPage.stream()
                .map(GlobalMapper::mapToProductResponse)
                .collect(Collectors.toList());
    }
}

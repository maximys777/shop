package com.maximys777.shop.repositories;

import com.maximys777.shop.entities.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    Page<ProductEntity> findByProductTitleContainingIgnoreCase(String productTitle, Pageable pageable);
}

package com.maximys777.shop.repositories;

import com.maximys777.shop.entities.LaptopEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LaptopRepository extends JpaRepository<LaptopEntity, Long> {
    Page<LaptopEntity> findByProductTitleContainingIgnoreCase(String productTitle, Pageable pageable);
}

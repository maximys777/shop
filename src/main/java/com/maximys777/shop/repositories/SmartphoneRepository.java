package com.maximys777.shop.repositories;

import com.maximys777.shop.entities.SmartphoneEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SmartphoneRepository extends JpaRepository<SmartphoneEntity, Long> {
    Page<SmartphoneEntity> findByProductTitleContainingIgnoreCase(String productTitle, Pageable pageable);
}

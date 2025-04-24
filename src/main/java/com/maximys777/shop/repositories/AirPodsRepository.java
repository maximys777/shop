package com.maximys777.shop.repositories;

import com.maximys777.shop.entities.AirPodsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirPodsRepository extends JpaRepository<AirPodsEntity, Long> {
    Page<AirPodsEntity> findByProductTitleContainingIgnoreCase(String productTitle, Pageable pageable);
}

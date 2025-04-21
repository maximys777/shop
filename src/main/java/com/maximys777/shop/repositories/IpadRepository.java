package com.maximys777.shop.repositories;

import com.maximys777.shop.entities.IpadEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IpadRepository extends JpaRepository<IpadEntity, Long> {
    Page<IpadEntity> findByProductTitleContainingIgnoreCase(String productTitle, Pageable pageable);
}

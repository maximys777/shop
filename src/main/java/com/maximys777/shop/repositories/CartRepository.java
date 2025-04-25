package com.maximys777.shop.repositories;

import com.maximys777.shop.entities.CartEntity;
import com.maximys777.shop.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, Long> {
    Optional<CartEntity> findByUser(UserEntity user);
}

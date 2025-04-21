package com.maximys777.shop.repositories;

import com.maximys777.shop.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    boolean existsByUserGmail(String userGmail);

   Optional<UserEntity> findByUserGmail(String userEmail);
}

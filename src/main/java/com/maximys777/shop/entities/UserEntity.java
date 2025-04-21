package com.maximys777.shop.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "user_gmail", nullable = false)
    private String userGmail;

    @Column(name = "user_password", nullable = false)
    private String userPassword;

    @CreationTimestamp
    @Column(name = "user_created_at", nullable = false)
    private LocalDateTime userCreatedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role", nullable = false)
    private RoleEnum userRole;
}

package com.order.authservice.entity;

import com.order.authservice.enums.UserRole;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Builder
@Entity
@Data
public class UserCredentials {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String userId;

    @Column(unique = true,nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(20) CHECK (user_role IN ('USER', 'SUPPLIER', 'CARRIER')) DEFAULT USER", nullable = false)
    private UserRole userRole;
}

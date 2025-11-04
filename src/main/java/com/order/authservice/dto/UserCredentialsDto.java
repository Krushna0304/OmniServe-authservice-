package com.order.authservice.dto;

import com.order.authservice.enums.UserRole;

public record UserCredentialsDto(
        String username,
        String password,
        UserRole userRole
) {
}

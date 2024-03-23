package com.server.dto.user;

public record UserRegistrationDto(
        String name,
        String phone,
        String password
) {
}

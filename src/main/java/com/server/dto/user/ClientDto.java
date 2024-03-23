package com.server.dto.user;

import com.server.entity.user.Gender;

public record ClientDto(
        String name,
        String email,
        Gender gender
) {
}

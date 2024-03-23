package com.server.dto.user;

import com.server.entity.user.Gender;

public record RequestClientDto(
        int id,
        String name,
        String email,
        String phone,
        Gender gender
) {
}

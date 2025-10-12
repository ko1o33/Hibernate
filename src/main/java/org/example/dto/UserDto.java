package org.example.dto;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    private String name;

    private String email;

    private int age;
}

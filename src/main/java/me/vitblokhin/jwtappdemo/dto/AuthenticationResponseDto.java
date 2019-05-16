package me.vitblokhin.jwtappdemo.dto;

import lombok.Data;

@Data
public class AuthenticationResponseDto {
    private String username;
    private String token;
} // class AuthenticationResponseDto

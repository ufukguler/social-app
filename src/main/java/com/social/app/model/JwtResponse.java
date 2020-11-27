package com.social.app.model;

import lombok.Data;


@Data
public class JwtResponse {
    private final String token;
    private final String username;
    private final String issuedAt;
    private final String expireAt;
}

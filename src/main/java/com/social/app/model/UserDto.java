package com.social.app.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Getter
@Setter
public class UserDto {
    @NotBlank(message = "username can not be blank")
    @Size(min = 3, max = 16, message = "username must be between 3-55 characters long")
    private String username;

    @NotBlank(message = "password can not be blank")
    @Size(min = 3, max = 16, message = "password must be between 3-55 characters long")
    private String password;
}

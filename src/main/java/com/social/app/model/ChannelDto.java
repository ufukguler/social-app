package com.social.app.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class ChannelDto {
    @NotBlank
    @Size(min = 3, max = 16)
    private String name;
}

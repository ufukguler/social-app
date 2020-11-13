package com.social.app.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class NotificationDto implements Serializable {
    @NotBlank(message = "message can not be blank")
    @Size(min = 3, max = 255, message = "message must be between 3-255 characters long")
    private String message;
}

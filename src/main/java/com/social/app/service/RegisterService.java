package com.social.app.service;

import com.social.app.model.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;

public interface RegisterService {
    ResponseEntity<?> save(UserDto userDto, Errors errors);
    /*
    User delete(UserDto userDto);
     */

}

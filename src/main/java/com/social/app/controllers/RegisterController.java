package com.social.app.controllers;

import com.social.app.model.UserDto;
import com.social.app.service.RegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/register")
@RequiredArgsConstructor
public class RegisterController {

    private final RegisterService registerService;

    @PostMapping
    @ResponseBody
    public ResponseEntity<?> create(@Valid @RequestBody UserDto userDto, Errors errors) {
        return ResponseEntity.ok(registerService.save(userDto, errors));
    }
}

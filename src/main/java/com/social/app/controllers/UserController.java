package com.social.app.controllers;

import com.social.app.service.ChannelService;
import com.social.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ChannelService channelService;

    @GetMapping
    @ResponseBody
    public ResponseEntity<?> listAll() {
        return ResponseEntity.ok(userService.findAll());
    }


}

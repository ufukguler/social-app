package com.social.app.controllers;

import com.social.app.model.NotificationDto;
import com.social.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    @ResponseBody
    public ResponseEntity<?> listAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{id}/info")
    @ResponseBody
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @PostMapping("/notifications")
    @ResponseBody
    public ResponseEntity<?> getNotifications() {
        return ResponseEntity.ok(userService.getNotifications());
    }

    @PostMapping("/share")
    @ResponseBody
    public ResponseEntity<?> share(@RequestBody @Valid NotificationDto message) {
        return ResponseEntity.ok(userService.shareOnChannel(message));
    }
}

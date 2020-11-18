package com.social.app.controllers;

import com.social.app.entity.User;
import com.social.app.model.NotificationDto;
import com.social.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

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

    @GetMapping("/subscribed") // bad naming
    @ResponseBody
    public ResponseEntity<?> findSubscribedChannels(Principal principal) {
        Optional<User> optionalUser = userService.findByName(principal.getName());
        if (!optionalUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not exist!");
        }
        Long userId = optionalUser.get().getId();
        return ResponseEntity.ok(userService.findSubscribedChannelsById(userId));

    }
}

package com.social.app.service.impl;

import com.social.app.entity.User;
import com.social.app.model.UserDto;
import com.social.app.repository.RoleRepository;
import com.social.app.repository.UserRepository;
import com.social.app.service.RegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RegisterServiceImpl implements RegisterService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<?> save(UserDto userDto, Errors errors) {
        if (userRepository.findByUsername(userDto.getUsername()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already in use!");
        }

        if (errors.hasErrors()) {
            List<String> errorList = new ArrayList<>();
            errors.getAllErrors().forEach(o -> errorList.add(o.getDefaultMessage()));
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.join(",  ", errorList));
        }

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));
        return ResponseEntity.ok(userRepository.save(user));
    }

/*
    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public User delete(UserDto userDto) {
        if (!userRepository.findByUsername(userDto.getUsername()).isPresent()) {
            throw new IllegalArgumentException("username does not exist!");
        }
        User user = userRepository.findByUsername(userDto.getUsername()).get();
        user.setActive(false);
        user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));
        return userRepository.save(user);
    }

 */
}

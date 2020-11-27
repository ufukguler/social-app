package com.social.app.controllers;

import com.social.app.auth.JwtTokenUtil;
import com.social.app.auth.JwtUserDetailsService;
import com.social.app.model.JwtResponse;
import com.social.app.model.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;

@RestController
@RequestMapping("/api/login")
@RequiredArgsConstructor
@CrossOrigin
@Slf4j
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final JwtUserDetailsService userDetailsService;

    /**
     * @param authenticationRequest
     * @return
     * @throws Exception
     */
    @PostMapping
    public ResponseEntity<?> createAuthenticationToken(@RequestBody UserDto authenticationRequest) throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:ss");
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        final String username = jwtTokenUtil.getUsernameFromToken(token);
        final String issuedAt = simpleDateFormat.format((jwtTokenUtil.getExpirationDateFromToken(token).getTime() - (5 * 60 * 60 * 1000)));
        final String expireAt = simpleDateFormat.format(jwtTokenUtil.getExpirationDateFromToken(token));
        return ResponseEntity.ok(new JwtResponse(token, username, issuedAt, expireAt));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
package com.rbs.retail.billing.controllers;

import com.rbs.retail.billing.dto.AuthDto;
import com.rbs.retail.billing.impls.CustomUserDetailsService;
import com.rbs.retail.billing.response.AuthResponse;
import com.rbs.retail.billing.services.UserService;
import com.rbs.retail.billing.utils.JwtUtil;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    @SecurityRequirements
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthDto request) throws Exception {

        authenticate(request.getEmail(), request.getPassword());

        final UserDetails userDetails = customUserDetailsService.loadUserByUsername(request.getEmail());

        final String jwtToken = jwtUtil.generateToken(userDetails);

        String role = userService.getUserRole(request.getEmail());

        AuthResponse authResponse = new AuthResponse();

        authResponse.setEmail(request.getEmail());
        authResponse.setToken(jwtToken);
        authResponse.setRole(role);

        return ResponseEntity.status(HttpStatus.OK).body(authResponse);

    }

    private void authenticate(String email, String password) throws Exception {

        try{

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (DisabledException e) {
            throw new Exception("User disabled");
        } catch (BadCredentialsException e){

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email or password is incorrect");
        }
    }

    @SecurityRequirements
    @PostMapping("/encode")
    public String encodePassword(@RequestBody Map<String, String> request){

        return passwordEncoder.encode(request.get("password"));

    }
}

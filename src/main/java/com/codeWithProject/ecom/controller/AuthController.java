package com.codeWithProject.ecom.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import com.codeWithProject.ecom.dto.AuthenticationRequest;
import com.codeWithProject.ecom.dto.SignupRequestDto;
import com.codeWithProject.ecom.dto.UserDto;
import com.codeWithProject.ecom.entity.User;
import com.codeWithProject.ecom.helper.ApiResponse;
import com.codeWithProject.ecom.helper.ResponseHelper;
import com.codeWithProject.ecom.repository.UserRepository;
import com.codeWithProject.ecom.service.AuthService;
import com.codeWithProject.ecom.util.JwtUtil;

import jakarta.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final AuthService authService;
    private final ResponseHelper responseHelper;  // inject helper

    @PostMapping("/authenticate")
    public ResponseEntity<ApiResponse<String>> createAuthToken(@RequestBody AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        User user = userRepository.findFirstByEmail(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtUtil.generateToken(request.getUsername());
        ApiResponse<String> response = responseHelper.success("Login successful", token);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<ApiResponse<UserDto>> signUp(@Valid @RequestBody SignupRequestDto signupRequestDto) {
        log.info("Sign Up attempt for email: {}", signupRequestDto.getEmail());

        if (authService.hasUserWithEmail(signupRequestDto.getEmail())) {
            log.warn("Sign Up failed, User already exists: {}", signupRequestDto.getEmail());
            ApiResponse<UserDto> response = responseHelper.failure("Email already present", null);
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }

        UserDto createdUser = authService.createUser(signupRequestDto);
        log.info("User Created Successfully: {}", createdUser.getId());

        ApiResponse<UserDto> response = responseHelper.success("User Created Successfully", createdUser);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

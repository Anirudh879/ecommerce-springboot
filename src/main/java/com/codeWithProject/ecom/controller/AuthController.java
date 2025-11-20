package com.codeWithProject.ecom.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {
	
	private final AuthenticationManager authenticationManager;
	private final UserRepository userRepository;
	private final JwtUtil jwtUtil;
	private final AuthService authService;
	
	@PostMapping("/authenticate")
	public ResponseEntity<ApiResponse<String>> createAuthToken(@RequestBody AuthenticationRequest authenticationRequest) {
		authenticationManager.authenticate(
		new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
		final User user = userRepository.findFirstByEmail(authenticationRequest.getUsername()).orElseThrow(()-> new RuntimeException("User not found"));
		final String token = jwtUtil.generateToken(authenticationRequest.getUsername());
		ApiResponse<String> response = ResponseHelper.success("login successfully", token);
		return new ResponseEntity<ApiResponse<String>>(response, HttpStatus.ACCEPTED);
	}
	
	
	@PostMapping("/sign-up")
	public ResponseEntity<ApiResponse<UserDto>> singupUser(@Valid @RequestBody SignupRequestDto signupRequestDto){
		log.info("Sign Up attempt for email",signupRequestDto.getEmail());
		
		//check for user already exist
		if(authService.hasUserWithEmail(signupRequestDto.getEmail())) {
			log.warn("Sign Up failed,User already exist",signupRequestDto.getEmail());
			ApiResponse<UserDto> response = ResponseHelper.failure("Email already present",null);
			return new ResponseEntity<>(response,HttpStatus.NOT_ACCEPTABLE);
		}
		
		//create new user
		UserDto createdUser = authService.createUser(signupRequestDto);
		log.info("User Created Successfully",createdUser.getId());
		
		ApiResponse<UserDto> response = ResponseHelper.success("User Created Successfully",createdUser);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	
}

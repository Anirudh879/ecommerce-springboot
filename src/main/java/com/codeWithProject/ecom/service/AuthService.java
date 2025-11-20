package com.codeWithProject.ecom.service;

import com.codeWithProject.ecom.dto.SignupRequestDto;
import com.codeWithProject.ecom.dto.UserDto;

public interface AuthService {
	
	UserDto createUser(SignupRequestDto signUpRequestDto);
	
	Boolean hasUserWithEmail(String email);
	
}

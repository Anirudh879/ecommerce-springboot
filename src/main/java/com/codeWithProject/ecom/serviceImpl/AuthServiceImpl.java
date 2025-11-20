package com.codeWithProject.ecom.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.codeWithProject.ecom.dto.SignupRequestDto;
import com.codeWithProject.ecom.dto.UserDto;
import com.codeWithProject.ecom.entity.User;
import com.codeWithProject.ecom.enums.UserRole;
import com.codeWithProject.ecom.repository.UserRepository;
import com.codeWithProject.ecom.service.AuthService;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class AuthServiceImpl implements AuthService{
	
	@Autowired
	private UserRepository userRepository;
	
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	public UserDto createUser(SignupRequestDto signUpRequestDto) {
		
		log.info("Crating User with email:{}",signUpRequestDto.getEmail());
		try {
			log.debug("Recived signUpRequestDto",signUpRequestDto);
			User user = new User();
			user.setEmail(signUpRequestDto.getEmail());
			user.setPassword(new BCryptPasswordEncoder().encode(signUpRequestDto.getPassword()));
			user.setName(signUpRequestDto.getName());
			user.setRole(UserRole.CUSTOMER);
			log.debug("User entity before saving to user",user);
			
			User createdUser = userRepository.save(user);
			log.info("User successfully created with ID:{}",createdUser.getId());
			
			UserDto userDto = new UserDto();
			userDto.setId(createdUser.getId());
			userDto.setName(createdUser.getName());
			userDto.setEmail(createdUser.getEmail());
			userDto.setRole(createdUser.getRole());
			
			log.debug("Returning UserDto:{}",userDto);
			
			return userDto;
			
		}catch(Exception e) {
			log.error("Error occur while creating user with email {}:{}",signUpRequestDto.getEmail(),e.getMessage(),e);
			throw e;
		}
		
	}
	
	public Boolean hasUserWithEmail(String email) {
			return userRepository.findFirstByEmail(email).isPresent();
	}
	
}

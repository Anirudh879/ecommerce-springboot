package com.codeWithProject.ecom.dto;



import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignupRequestDto {
	
	@NotBlank(message="name can't be blank")
	@Email(message = "Invalid email format")
	private String email;
	
	@NotBlank(message = "Password can't be blank")
    @Size(min = 6, max = 20, message = "Password must be between 6 and 20 characters")
	private String password;
	
	@NotBlank(message = "Name can't be blank")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
	private String name;
}

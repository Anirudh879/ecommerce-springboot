package com.codeWithProject.ecom.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codeWithProject.ecom.entity.*;

public interface UserRepository extends JpaRepository<User,Long>{
	 Optional<User> findFirstByEmail(String email);
}

package com.api.benchfitness.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.benchfitness.dto.UserRegisterRequest;
import com.api.benchfitness.dto.UserRegistrationResponse;
import com.api.benchfitness.models.User;
import com.api.benchfitness.services.UserService;

@RestController
@RequestMapping("/public")
public class UserRegistrationController {

	@Autowired
	UserService userService;

	public UserRegistrationController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/register")
	public ResponseEntity<UserRegistrationResponse> registerUser(@Validated @RequestBody UserRegisterRequest request) {
		User newUser = userService.registerNewUser(request.getOwner(), request.getDescription());

		UserRegistrationResponse responseDto = new UserRegistrationResponse(newUser.getOwner(), newUser.getApiKey());

		return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
	}
}

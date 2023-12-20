package com.ecommerce.user.controller;

import java.util.List;

import com.ecommerce.user.payloads.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.user.dto.UserDTO;
import com.ecommerce.user.service.UserService;

@RequestMapping("/api/v1/user")
@RestController
public class UserController {

	@Autowired
	private UserService userService;
	
	@PostMapping("/save")
	public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO){
		CommonResponse commonResponse = userService.saveUser(userDTO);
		return ResponseEntity.created(null).body(commonResponse);
	}
	
	@GetMapping("/get-all")
	public ResponseEntity<?> getAllUser(){
		CommonResponse commonResponse = userService.getAllUser();
		return ResponseEntity.ok(commonResponse);
	}
	
	@GetMapping("/get/{id}")
	public ResponseEntity<?> getUserById(@PathVariable long id){
		CommonResponse commonResponse = userService.getUserById(id);
		return ResponseEntity.ok(commonResponse);
	}
}

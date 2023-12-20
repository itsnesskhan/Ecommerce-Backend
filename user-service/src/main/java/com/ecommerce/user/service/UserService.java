package com.ecommerce.user.service;

import java.util.List;

import com.ecommerce.user.dto.UserDTO;
import com.ecommerce.user.payloads.CommonResponse;

public interface UserService {

	CommonResponse saveUser(UserDTO userDTO);
	
	CommonResponse getAllUser();
	
	CommonResponse getUserById(Long id);
	
}

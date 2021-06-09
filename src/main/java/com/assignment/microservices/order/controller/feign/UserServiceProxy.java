package com.assignment.microservices.order.controller.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.assignment.microservices.order.dao.User;




@FeignClient(name="user-service",url="localhost:8002")
public interface UserServiceProxy {

	
	
	 @GetMapping("/user/{id}")
	  public ResponseEntity<User> getUserById(@PathVariable(value = "id") Long userId);
	  
	
}

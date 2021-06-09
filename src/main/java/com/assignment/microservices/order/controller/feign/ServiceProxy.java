package com.assignment.microservices.order.controller.feign;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.assignment.microservices.order.dao.Services;



@FeignClient(name="services-descriptor",url="localhost:8001")
public interface ServiceProxy {

	 @GetMapping("/service/{id}")
	    public ResponseEntity <Services> getServiceById(@PathVariable(value = "id") Long serviceId);
	     
}

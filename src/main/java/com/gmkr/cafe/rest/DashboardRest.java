package com.gmkr.cafe.rest;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(path = "/dashboard")
@CrossOrigin(origins = {"http://localhost:8080"})
public interface DashboardRest {

	@GetMapping(path = "/details")
	ResponseEntity<Map<String, Object>> getCount();
}

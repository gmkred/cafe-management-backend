package com.gmkr.cafe.rest;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gmkr.cafe.wrapper.UserWrapper;

@RequestMapping(path = "/user")
@CrossOrigin(origins = {"http://localhost:8080"})
public interface UserRest {
	@PostMapping(path = "/signUp")
	public ResponseEntity<String> signUp(@RequestBody Map<String, String> requestMap);

	@PostMapping(path = "/login")
	public ResponseEntity<String> login(@RequestBody Map<String, String> requestMap);

	@GetMapping(path = "/get")
	public ResponseEntity<List<UserWrapper>> getAllUser();

	@PostMapping(path = "/update")
	public ResponseEntity<String> update(@RequestBody Map<String, String> requestMap);

	@GetMapping(path = "/checkToken")
	public ResponseEntity<String> checkToken();

	@PostMapping(path = "/changePassword")
	public ResponseEntity<String> changePassword(@RequestBody Map<String, String> requestMap);

	@PostMapping(path = "/forgotPassword")
	public ResponseEntity<String> forgotPassword(@RequestBody Map<String, String> requestMap);
}

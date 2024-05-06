package com.gmkr.cafe.rest;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gmkr.cafe.wrapper.ProductWrapper;

@RequestMapping(path = "/product")
@CrossOrigin(origins = {"http://localhost:8080"})
public interface ProductRest {

	@PostMapping(path = "/add")
	public ResponseEntity<String> addNewProduct(@RequestBody Map<String, String> requestMap);

	@GetMapping(path = "/get")
	public ResponseEntity<List<ProductWrapper>> getAllProduct();

	@PostMapping(path = "/update")
	public ResponseEntity<String> updateProduct(@RequestBody Map<String, String> requestMap);

	@PostMapping(path = "/delete/{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable Integer id);

	@PostMapping(path = "/updateStatus")
	public ResponseEntity<String> updateStatus(@RequestBody Map<String, String> requstMap);

	@GetMapping(path = "/getByCategory/{id}")
	public ResponseEntity<List<ProductWrapper>> getByCategory(@PathVariable Integer id);

	@GetMapping(path = "getById/{id}")
	public ResponseEntity<ProductWrapper> getProductById(@PathVariable Integer id);

	
	
}

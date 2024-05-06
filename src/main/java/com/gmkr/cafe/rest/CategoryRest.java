package com.gmkr.cafe.rest;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gmkr.cafe.pojo.Category;

@RequestMapping("/category")
@CrossOrigin(origins = {"http://localhost:8080"})
public interface CategoryRest {

	@PostMapping(path = "/add")
	public ResponseEntity<String> addNewCategory(@RequestBody Map<String, String> requestMap);

	@GetMapping(path = "/get")
	public ResponseEntity<List<Category>> getAllCategory(@RequestParam(required = false) String filterValue);

	@PostMapping(path = "/update")
	public ResponseEntity<String> updateCategory(@RequestBody(required = true) Map<String, String> requestMap);
}

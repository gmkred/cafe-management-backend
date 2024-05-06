package com.gmkr.cafe.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.gmkr.cafe.wrapper.ProductWrapper;

public interface ProductService {

	public ResponseEntity<String> addNewProduct(Map<String, String> requestMap);

	public ResponseEntity<List<ProductWrapper>> getAllProduct();

	public ResponseEntity<String> updateProduct(Map<String, String> requestMap);

	public ResponseEntity<String> deleteProduct(Integer id);

	public ResponseEntity<String> updateStatus(Map<String, String> requstMap);

	public ResponseEntity<List<ProductWrapper>> getByCategory(Integer id);

	public ResponseEntity<ProductWrapper> getProductById(Integer id);

}

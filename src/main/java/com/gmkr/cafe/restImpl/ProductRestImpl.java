package com.gmkr.cafe.restImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.gmkr.cafe.contants.CafeContants;
import com.gmkr.cafe.pojo.Product;
import com.gmkr.cafe.rest.ProductRest;
import com.gmkr.cafe.service.ProductService;
import com.gmkr.cafe.utils.CafeUtils;
import com.gmkr.cafe.wrapper.ProductWrapper;

@RestController
public class ProductRestImpl implements ProductRest {
	@Autowired
	ProductService productService;

	@Override
	public ResponseEntity<String> addNewProduct(Map<String, String> requestMap) {
		try {
			return productService.addNewProduct(requestMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeContants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<List<ProductWrapper>> getAllProduct() {
		try {
			return productService.getAllProduct();
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return new ResponseEntity<List<ProductWrapper>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<String> updateProduct(Map<String, String> requestMap) {

		try {
			return productService.updateProduct(requestMap);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return CafeUtils.getResponseEntity(CafeContants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<String> deleteProduct(Integer id) {

		try {
			return productService.deleteProduct(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeContants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<String> updateStatus(Map<String, String> requstMap) {
		try {
			return productService.updateStatus(requstMap);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeContants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<List<ProductWrapper>> getByCategory(Integer id) {
		try {
			return productService.getByCategory(id);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ResponseEntity<List<ProductWrapper>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<ProductWrapper> getProductById(Integer id) {
		try {

			return productService.getProductById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<ProductWrapper>(new ProductWrapper(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}

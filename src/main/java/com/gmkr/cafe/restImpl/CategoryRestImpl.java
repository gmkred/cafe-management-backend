package com.gmkr.cafe.restImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;

import com.gmkr.cafe.contants.CafeContants;
import com.gmkr.cafe.pojo.Category;
import com.gmkr.cafe.rest.CategoryRest;
import com.gmkr.cafe.service.CategoryService;
import com.gmkr.cafe.utils.CafeUtils;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class CategoryRestImpl implements CategoryRest {

	@Autowired
	private CategoryService categoryService;

	@Override
	public ResponseEntity<String> addNewCategory(Map<String, String> requestMap) {

		try {
			log.error("addNewCategory entered {} ");
			return categoryService.addNewCategory(requestMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeContants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<List<Category>> getAllCategory(String filterValue) {

		try {
			return categoryService.getAllCategory(filterValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<List<Category>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<String> updateCategory(Map<String, String> requestMap) {
		try {
			return categoryService.updateCategory(requestMap);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}

		return CafeUtils.getResponseEntity(CafeContants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}

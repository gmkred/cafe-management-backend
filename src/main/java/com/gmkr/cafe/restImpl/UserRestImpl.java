package com.gmkr.cafe.restImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import com.gmkr.cafe.contants.CafeContants;
import com.gmkr.cafe.pojo.User;
import com.gmkr.cafe.rest.UserRest;
import com.gmkr.cafe.service.UserService;
import com.gmkr.cafe.utils.CafeUtils;
import com.gmkr.cafe.wrapper.UserWrapper;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
public class UserRestImpl implements UserRest {

	public static final Logger LOGGER = LoggerFactory.getLogger(UserRestImpl.class);
	@Autowired
	private UserService userService;

	@Override
	@ApiResponses({ @ApiResponse(responseCode = "200", content = {
			@Content(schema = @Schema(implementation = User.class), mediaType = "application/json") }) })
	public ResponseEntity<String> signUp(Map<String, String> requestMap) {

		try {
			return userService.signUp(requestMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeContants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@Override
	public ResponseEntity<String> login(Map<String, String> requestMap) {

		LOGGER.info("login method invoked : user email = " + requestMap.get("email"));
		try {
			return userService.login(requestMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ResponseEntity<List<UserWrapper>> getAllUser() {

		try {
			return userService.getAllUser();
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return new ResponseEntity<List<UserWrapper>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<String> update(Map<String, String> requestMap) {
		try {

			return userService.update(requestMap);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return CafeUtils.getResponseEntity(CafeContants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<String> checkToken() {
		try {
			return userService.checkToken();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeContants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@Override
	public ResponseEntity<String> changePassword(Map<String, String> requestMap) {
		try {

			return userService.changePassword(requestMap);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return CafeUtils.getResponseEntity(CafeContants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<String> forgotPassword(Map<String, String> requestMap) {
		try {

			return userService.forgotPassword(requestMap);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// TODO Auto-generated method stub
		return CafeUtils.getResponseEntity(CafeContants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}

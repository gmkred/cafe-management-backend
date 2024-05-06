package com.gmkr.cafe.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.gmkr.cafe.contants.CafeContants;
import com.gmkr.cafe.dao.UserDao;
import com.gmkr.cafe.jwt.CustomerUserDetailsService;
import com.gmkr.cafe.jwt.JwtFilter;
import com.gmkr.cafe.jwt.JwtUtil;
import com.gmkr.cafe.pojo.User;
import com.gmkr.cafe.service.UserService;
import com.gmkr.cafe.utils.CafeUtils;
import com.gmkr.cafe.utils.EmailUtils;
import com.gmkr.cafe.wrapper.UserWrapper;
import com.google.common.base.Strings;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	CustomerUserDetailsService customerUserDetailsService;

	@Autowired
	JwtUtil jwtUtil;
	@Autowired
	JwtFilter filter;
	@Autowired
	EmailUtils emailUtils;

	@Override
	public ResponseEntity<String> signUp(Map<String, String> requestMap) {
		log.info("inside signUp {}", requestMap);
		try {
			if (validateSignUpMap(requestMap)) {

				User user = userDao.findByEmailId(requestMap.get("email"));
				if (Objects.isNull(user)) {

					User savedUser = userDao.save(getUserFromMap(requestMap));
					return CafeUtils.getResponseEntity("Successfully registered", HttpStatus.OK);
				} else {
					return CafeUtils.getResponseEntity("Email already exists", HttpStatus.BAD_REQUEST);
				}
			} else {
				return CafeUtils.getResponseEntity(CafeContants.INVALID_DATA, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
		return CafeUtils.getResponseEntity(CafeContants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private boolean validateSignUpMap(Map<String, String> requestMap) {

		if (requestMap.containsKey("name") && requestMap.containsKey("contactNumber") && requestMap.containsKey("email")
				&& requestMap.containsKey("password")) {
			return true;
		}
		return false;
	}

	private User getUserFromMap(Map<String, String> requestMap) {
		User user = new User();
		user.setName(requestMap.get("name"));
		user.setContactNumber(requestMap.get("contactNumber"));
		user.setEmail(requestMap.get("email"));
		user.setPassword(requestMap.get("password"));
		user.setStatus("false");
		user.setRole("user");

		return user;

	}

	@Override
	public ResponseEntity<String> login(Map<String, String> requestMap) {

		log.info("Inside login {} email : " + requestMap.get("email") + " password : " + requestMap.get("password"));
		try {
			Authentication auth = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(requestMap.get("email"), requestMap.get("password")));
			if (auth.isAuthenticated()) {
				if (customerUserDetailsService.getUserDetails().getStatus().equalsIgnoreCase("true")) {

					return new ResponseEntity<String>(
							"{\"token\":\""
									+ jwtUtil.generateToken(customerUserDetailsService.getUserDetails().getEmail(),
											customerUserDetailsService.getUserDetails().getRole())
									+ "\"}",
							HttpStatus.OK);
				}
			} else {
				return new ResponseEntity<String>("{\"message\":\"" + "Wait for admin approval" + "\"}",
						HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("{}", e);

		}

		return new ResponseEntity<String>("{\"token\":\"" + "Bad Credentials." + "\"}", HttpStatus.BAD_REQUEST);
	}

	@Override
	public ResponseEntity<List<UserWrapper>> getAllUser() {

		try {

			if (filter.isAdmin()) {

				return new ResponseEntity<List<UserWrapper>>(userDao.getAllUser(), HttpStatus.OK);
			} else {
				return new ResponseEntity<List<UserWrapper>>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ResponseEntity<List<UserWrapper>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<String> update(Map<String, String> requestMap) {

		try {

			if (filter.isAdmin()) {
				Optional<User> optional = userDao.findById(Integer.parseInt(requestMap.get("id")));
				if (!optional.isEmpty()) {
					userDao.updateStatus(requestMap.get("status"), Integer.parseInt(requestMap.get("id")));
					sendMailToAllAdmin(requestMap.get("status"), optional.get().getEmail(), userDao.getAllAdmin());
					return CafeUtils.getResponseEntity("User status Updated successfully", HttpStatus.OK);

				} else {
					return CafeUtils.getResponseEntity("User id doesn't exists", HttpStatus.OK);
				}

			} else {
				return CafeUtils.getResponseEntity(CafeContants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeContants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private void sendMailToAllAdmin(String status, String user, List<String> allAdmin) {
		allAdmin.remove(filter.getCurrentUser());
		if (status != null && status.equalsIgnoreCase("true")) {
			emailUtils.sendSimpleMessage(filter.getCurrentUser(), "Account Approved",
					"User :-  " + user + " \n is approved by \nADMIN :" + filter.getCurrentUser(), allAdmin);
		} else {
			emailUtils.sendSimpleMessage(filter.getCurrentUser(), "Account Disabled",
					"User :-  " + user + " \n is disabled by \nADMIN :" + filter.getCurrentUser(), allAdmin);
		}
	}

	@Override
	public ResponseEntity<String> checkToken() {

		return CafeUtils.getResponseEntity("true", HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> changePassword(Map<String, String> requestMap) {

		try {
			User userObj = userDao.findByEmail(filter.getCurrentUser());
			if (!userObj.equals(null)) {
				if (userObj.getPassword().equals(requestMap.get("oldPassword"))) {
					userObj.setPassword(requestMap.get("newPassword"));
					userDao.save(userObj);
					return CafeUtils.getResponseEntity("Password updated successfully", HttpStatus.OK);
				}
				return CafeUtils.getResponseEntity("Incorrect old password", HttpStatus.BAD_REQUEST);
			}
			return CafeUtils.getResponseEntity(CafeContants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return CafeUtils.getResponseEntity(CafeContants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<String> forgotPassword(Map<String, String> requestMap) {
		try {
			User user = userDao.findByEmail(requestMap.get("email"));
			if (!Objects.isNull(user) && !Strings.isNullOrEmpty(user.getEmail())) {
				emailUtils.forgotMail(user.getEmail(), "Credentials by Cafe Management System", user.getPassword());
			}
			return CafeUtils.getResponseEntity("Check your mail for credentials", HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return CafeUtils.getResponseEntity(CafeContants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}

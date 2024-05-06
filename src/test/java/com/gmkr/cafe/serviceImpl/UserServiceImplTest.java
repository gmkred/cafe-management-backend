package com.gmkr.cafe.serviceImpl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import com.gmkr.cafe.dao.UserDao;
import com.gmkr.cafe.jwt.CustomerUserDetailsService;
import com.gmkr.cafe.jwt.JwtFilter;
import com.gmkr.cafe.jwt.JwtUtil;
import com.gmkr.cafe.pojo.User;
import com.gmkr.cafe.service.UserService;
import com.gmkr.cafe.utils.EmailUtils;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

//	@Mock
//	private UserDao userDao;
//
//	@Mock
//	private AuthenticationManager authenticationManager;
//
//	@Mock
//	private CustomerUserDetailsService customerUserDetailsService;
//
//	@Mock
//	private JwtUtil jwtUtil;
//
//	@Mock
//	private JwtFilter filter;
//
//	@Mock
//	private EmailUtils emailUtils;
//
//	@InjectMocks
//	private UserService userService;
//
//	@Test
//	public void testSignUp_SuccessfulRegistration() {
//		Map<String, String> requestMap = createValidSignUpRequestMap();
//
//		when(userDao.findByEmailId(anyString())).thenReturn(null);
//		when(userDao.save(any())).thenReturn(createMockUser());
//
//		ResponseEntity<String> responseEntity = userService.signUp(requestMap);
//
//		verify(userDao, times(1)).findByEmailId(anyString());
//		verify(userDao, times(1)).save(any());
//
//		assert responseEntity.getStatusCode() == HttpStatus.OK;
//		assert responseEntity.getBody().equals("Successfully registered");
//	}
//
//	@Test
//	public void testSignUp_EmailAlreadyExists() {
//		Map<String, String> requestMap = createValidSignUpRequestMap();
//
//		when(userDao.findByEmailId(anyString())).thenReturn(createMockUser());
//
//		ResponseEntity<String> responseEntity = userService.signUp(requestMap);
//
//		verify(userDao, times(1)).findByEmailId(anyString());
//		verify(userDao, never()).save(any());
//
//		assert responseEntity.getStatusCode() == HttpStatus.BAD_REQUEST;
//		assert responseEntity.getBody().equals("Email already exists");
//	}
//
//	@Test
//	public void testSignUp_InvalidData() {
//		Map<String, String> requestMap = new HashMap<>();
//
//		ResponseEntity<String> responseEntity = userService.signUp(requestMap);
//
//		verify(userDao, never()).findByEmailId(anyString());
//		verify(userDao, never()).save(any());
//
//		assert responseEntity.getStatusCode() == HttpStatus.BAD_REQUEST;
//		assert responseEntity.getBody().equals("Invalid data");
//	}
//
//	@Test
//	public void testLogin_SuccessfulLogin() {
//		Map<String, String> requestMap = createValidLoginRequestMap();
//
//		when(authenticationManager.authenticate(any())).thenReturn(createMockAuthentication());
//		when(customerUserDetailsService.getUserDetails()).thenReturn((User) createMockUserDetails());
//		when(customerUserDetailsService.getUserDetails().getStatus()).thenReturn("true");
//		when(jwtUtil.generateToken(anyString(), anyString())).thenReturn("mockToken");
//
//		ResponseEntity<String> responseEntity = userService.login(requestMap);
//
//		verify(authenticationManager, times(1)).authenticate(any());
//		verify(customerUserDetailsService, times(2)).getUserDetails();
//		verify(jwtUtil, times(1)).generateToken(anyString(), anyString());
//
//		assert responseEntity.getStatusCode() == HttpStatus.OK;
//		assert responseEntity.getBody().contains("mockToken");
//	}
//
//	// Add additional test cases for other scenarios and methods as needed
//
//	private Map<String, String> createValidSignUpRequestMap() {
//		Map<String, String> requestMap = new HashMap<>();
//		requestMap.put("name", "John Doe");
//		requestMap.put("contactNumber", "1234567890");
//		requestMap.put("email", "john.doe@example.com");
//		requestMap.put("password", "password123");
//		return requestMap;
//	}
//
//	private Map<String, String> createValidLoginRequestMap() {
//		Map<String, String> requestMap = new HashMap<>();
//		requestMap.put("email", "john.doe@example.com");
//		requestMap.put("password", "password123");
//		return requestMap;
//	}
//
//	private User createMockUser() {
//		User user = new User();
//		user.setId(1);
//		user.setName("John Doe");
//		user.setContactNumber("1234567890");
//		user.setEmail("john.doe@example.com");
//		user.setPassword("password123");
//		user.setStatus("false");
//		user.setRole("user");
//		return user;
//	}
//
//	private Authentication createMockAuthentication() {
//		// Implement mock authentication as needed
//		return null;
//	}
//
//	private UserDetails createMockUserDetails() {
//		// Implement mock user details as needed
//		return null;
//	}
}

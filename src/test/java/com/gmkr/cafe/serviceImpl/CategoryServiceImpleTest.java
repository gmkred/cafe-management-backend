package com.gmkr.cafe.serviceImpl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.gmkr.cafe.dao.CategoryDao;
import com.gmkr.cafe.jwt.JwtFilter;
import com.gmkr.cafe.pojo.Category;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImpleTest {

	@Mock
	private CategoryDao categoryDao;

	@Mock
	private JwtFilter jwtFilter;

	@InjectMocks
	private CategoryServiceImple categoryService;

	@Test
	public void testAddNewCategory_Admin_SuccessfulAddition() {
		Map<String, String> requestMap = createValidCategoryMap();
		when(jwtFilter.isAdmin()).thenReturn(true);
		when(categoryDao.save(any())).thenReturn(createMockCategory());
//actual method is called here
		ResponseEntity<String> responseEntity = categoryService.addNewCategory(requestMap);
		verify(jwtFilter, times(1)).isAdmin();
		verify(categoryDao, times(1)).save(any());
		assert responseEntity.getStatusCode() == HttpStatus.OK;
		System.out.println(responseEntity.getBody());
		assert responseEntity.getBody().equals("{\"message\":\"Category Added Successfully\"}");
	}

	@Test
	@DisplayName("add category - not admin")
	public void testAddNewCategory_NotAdmin_UnauthorizedAccess() {
		Map<String, String> requestMap = createValidCategoryMap();
		when(jwtFilter.isAdmin()).thenReturn(false);
		ResponseEntity<String> responseEntity = categoryService.addNewCategory(requestMap);
		verify(jwtFilter, times(1)).isAdmin();
		verify(categoryDao, never()).save(any());
		assert responseEntity.getStatusCode() == HttpStatus.UNAUTHORIZED;
		System.out.println(responseEntity.getBody());
		assert responseEntity.getBody().equals("{\"message\":\"Unauthorized Access\"}");
	}

	@Test
	public void testGetAllCategory_FilterTrue_Successful() {
		String filterValue = "true";

		when(categoryDao.getAllCategory()).thenReturn(createMockCategories());

		ResponseEntity<List<Category>> responseEntity = categoryService.getAllCategory(filterValue);

		verify(categoryDao, times(1)).getAllCategory();

		assert responseEntity.getStatusCode() == HttpStatus.OK;
		assert responseEntity.getBody().size() == 2; // Adjust based on your mock data
	}

	@Test
	public void testGetAllCategory_FilterFalse_Successful() {
		String filterValue = "false";

		when(categoryDao.findAll()).thenReturn(createMockCategories());

		ResponseEntity<List<Category>> responseEntity = categoryService.getAllCategory(filterValue);

		verify(categoryDao, times(1)).findAll();

		assert responseEntity.getStatusCode() == HttpStatus.OK;
		assert responseEntity.getBody().size() == 2; // Adjust based on your mock data
	}

	@Test
	public void testGetAllCategory_Exception_InternalServerError() {
		String filterValue = "true";

		when(categoryDao.getAllCategory()).thenThrow(new RuntimeException("Simulating an exception"));

		ResponseEntity<List<Category>> responseEntity = categoryService.getAllCategory(filterValue);

		verify(categoryDao, times(1)).getAllCategory();

		assert responseEntity.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR;
		assert responseEntity.getBody().isEmpty();
	}

	@Test
	public void testUpdateCategory_Admin_ValidInput_SuccessfulUpdate() {
		Map<String, String> requestMap = createValidUpdateCategoryMap();

		when(jwtFilter.isAdmin()).thenReturn(true);
		when(categoryDao.findById(anyInt())).thenReturn(Optional.of(createMockCategory()));
		when(categoryDao.save(any())).thenReturn(createMockCategory());

		ResponseEntity<String> responseEntity = categoryService.updateCategory(requestMap);

		verify(jwtFilter, times(1)).isAdmin();
		verify(categoryDao, times(1)).findById(anyInt());
		verify(categoryDao, times(1)).save(any());

		assert responseEntity.getStatusCode() == HttpStatus.OK;
		assert responseEntity.getBody().equals("{\"message\":\"Category Updated Successfully\"}");
	}

	@Test
	public void testUpdateCategory_Admin_InvalidInput_InvalidData() {
		Map<String, String> requestMap = createInvalidUpdateCategoryMap();

		when(jwtFilter.isAdmin()).thenReturn(true);

		ResponseEntity<String> responseEntity = categoryService.updateCategory(requestMap);

		verify(jwtFilter, times(1)).isAdmin();
		verify(categoryDao, never()).findById(anyInt());
		verify(categoryDao, never()).save(any());

		assert responseEntity.getStatusCode() == HttpStatus.BAD_REQUEST;
		assert responseEntity.getBody().equals("{\"message\":\"Invalid data\"}");
	}

	@Test
	public void testUpdateCategory_NotAdmin_UnauthorizedAccess() {
		Map<String, String> requestMap = createValidUpdateCategoryMap();

		when(jwtFilter.isAdmin()).thenReturn(false);

		ResponseEntity<String> responseEntity = categoryService.updateCategory(requestMap);

		verify(jwtFilter, times(1)).isAdmin();
		verify(categoryDao, never()).findById(anyInt());
		verify(categoryDao, never()).save(any());

		assert responseEntity.getStatusCode() == HttpStatus.UNAUTHORIZED;
		assert responseEntity.getBody().equals("{\"message\":\"Unauthorized Access\"}");
	}

	// Add more test cases for updateCategory as needed

	private Map<String, String> createValidUpdateCategoryMap() {
		Map<String, String> requestMap = new HashMap<>();
		requestMap.put("id", "1");
		requestMap.put("name", "UpdatedCategory");
		return requestMap;
	}

	private Map<String, String> createInvalidUpdateCategoryMap() {
		Map<String, String> requestMap = new HashMap<>();
		// Missing 'id' in the request
		requestMap.put("name", "UpdatedCategory");
		return requestMap;
	}

	private List<Category> createMockCategories() {
		List<Category> categories = new ArrayList<>();
		categories.add(new Category(1, "Category1"));
		categories.add(new Category(2, "Category2"));
		return categories;
	}

	private Map<String, String> createValidCategoryMap() {
		Map<String, String> requestMap = new HashMap<>();
		requestMap.put("id", "1");
		requestMap.put("name", "TestCategory");
		return requestMap;
	}

	private Category createMockCategory() {
		Category category = new Category();
		category.setId(1);
		category.setName("TestCategory");
		return category;
	}

}

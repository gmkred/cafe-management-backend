package com.gmkr.cafe.restImpl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.*;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import com.gmkr.cafe.contants.CafeContants;
import com.gmkr.cafe.jwt.JwtUtil;
import com.gmkr.cafe.pojo.Category;
import com.gmkr.cafe.service.CategoryService;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS) // using this annotation to make beforeAll method a non static method
class CategoryRestImplTest {

	@MockBean
	private CategoryService categoryService;

//	@InjectMocks
//	private CategoryRestImpl categoryRestController;

	@Autowired
	private MockMvc mockMvc;

//	public CategoryRestImplTest() {
//		this.mockMvc = MockMvcBuilders.standaloneSetup(categoryRestController).build();
//	}
	String validJwtToken;

	@BeforeAll
	public void pre() {
		validJwtToken = new JwtUtil().generateToken("mallireddy1999916@gmail.com", "admin");

	}

	@Test
	void addNewCategory_Success() throws Exception {

//		Map<String, String> requestMap = createValidCategoryMap();

		when(categoryService.addNewCategory(any())).thenReturn(ResponseEntity.ok("Category Added Successfully"));

		mockMvc.perform(post("/category/add").contentType(MediaType.APPLICATION_JSON)
				.content("{ \"name\": \"TestCategory\" }").header("Authorization", "Bearer " + validJwtToken))
				.andExpect(status().isOk());
	}

	@Test
	void addNewCategory_InternalServerError() throws Exception {
//		Map<String, String> requestMap = createValidCategoryMap();

		when(categoryService.addNewCategory(any())).thenReturn(
				ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(CafeContants.SOMETHING_WENT_WRONG));
//415 - unsupported media type(if we provide incorrect mediaType in content type)
		mockMvc.perform(post("/category/add").contentType(MediaType.APPLICATION_JSON)
				.content("{ \"name\": \"TestCategory\" }").header("Authorization", "Bearer " + validJwtToken))
				.andExpect(status().isInternalServerError());
	}

	@Test
	void getAllCategory_Success() throws Exception {
		when(categoryService.getAllCategory(eq(null))).thenReturn(ResponseEntity.ok(createMockCategories()));

		mockMvc.perform(get("/category/get").header("Authorization", "Bearer " + validJwtToken))
				.andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].name", equalTo("Category1")));
	}

	@Test
	void getAllCategory_InternalServerError() throws Exception {
		when(categoryService.getAllCategory(eq(null)))
				.thenReturn(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ArrayList<>()));

		mockMvc.perform(get("/category/get").header("Authorization", "Bearer " + validJwtToken))
				.andExpect(status().isInternalServerError());
	}

	@Test
	void updateCategory_Success() throws Exception {
//		Map<String, String> requestMap = createValidCategoryMap();

		when(categoryService.updateCategory(any())).thenReturn(ResponseEntity.ok("Category Updated Successfully"));

		mockMvc.perform(post("/category/update").contentType(MediaType.APPLICATION_JSON)
				.content("{ \"name\": \"TestCategory\" }").header("Authorization", "Bearer " + validJwtToken))
				.andExpect(status().isOk());
		verify(categoryService, times(1)).updateCategory(any());
	}

	@Test
	void updateCategory_InternalServerError() throws Exception {
//		Map<String, String> requestMap = createValidCategoryMap();

		when(categoryService.updateCategory(any())).thenReturn(
				ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(CafeContants.SOMETHING_WENT_WRONG));

		mockMvc.perform(post("/category/update").contentType(MediaType.APPLICATION_JSON)
				.content("{ \"name\": \"TestCategory\" }").header("Authorization", "Bearer " + validJwtToken))
				.andExpect(status().isInternalServerError());
	}

//	private Map<String, String> createValidCategoryMap() {
//		Map<String, String> requestMap = new HashMap<>();
//		requestMap.put("name", "TestCategory");
//		return requestMap;
//	}

	private List<Category> createMockCategories() {
		List<Category> categories = new ArrayList<>();
		categories.add(new Category(1, "Category1"));
		categories.add(new Category(2, "Category2"));
		return categories;
	}
}

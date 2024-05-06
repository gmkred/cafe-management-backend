package com.gmkr.cafe.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gmkr.cafe.pojo.Category;

@ExtendWith(MockitoExtension.class)
class CategoryDaoTest {
	@Mock
	private CategoryDao categoryDao;

	@Test
	void getAllCategory_Success() {
		// Arrange
		List<Category> expectedCategories = Arrays.asList(new Category(1, "Category1"), new Category(2, "Category2"));

		when(categoryDao.getAllCategory()).thenReturn(expectedCategories);

		// Act
		List<Category> actualCategories = categoryDao.getAllCategory();

		// Assert
		assertEquals(expectedCategories.size(), actualCategories.size());
		for (int i = 0; i < expectedCategories.size(); i++) {
			assertEquals(expectedCategories.get(i), actualCategories.get(i));
		}

		// Verify that the getAllCategory method was called
		verify(categoryDao, times(1)).getAllCategory();
	}

}

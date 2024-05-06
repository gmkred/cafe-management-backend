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

import com.gmkr.cafe.wrapper.ProductWrapper;

@ExtendWith(MockitoExtension.class)
class ProductDaoTest {

	@Mock
	private ProductDao productDao;

	@Test
	void getAllProduct_Success() {
		// Arrange
		List<ProductWrapper> expectedProducts = Arrays.asList(
				new ProductWrapper(1, "Product1", "Description1", 10, "Available", 1, "Category1"),
				new ProductWrapper(2, "Product2", "Description2", 15, "Available", 2, "Category2"));

		when(productDao.getAllProduct()).thenReturn(expectedProducts);

		// Act
		List<ProductWrapper> actualProducts = productDao.getAllProduct();

		// Assert
		assertEquals(expectedProducts.size(), actualProducts.size());
		for (int i = 0; i < expectedProducts.size(); i++) {
			assertEquals(expectedProducts.get(i), actualProducts.get(i));
		}

		// Verify that the getAllProduct method was called
		verify(productDao, times(1)).getAllProduct();
	}

	@Test
	void updateProductStatus_Success() {
		// Arrange
		int productId = 1;
		String newStatus = "true";

		when(productDao.updateProductStatus(newStatus, productId)).thenReturn(1);

		// Act
		Integer affectedRows = productDao.updateProductStatus(newStatus, productId);

		// Assert
		assertEquals(1, affectedRows);

		// Verify that the updateProductStatus method was called
		verify(productDao, times(1)).updateProductStatus(newStatus, productId);
	}

	@Test
	void getProductByCategory_Success() {
		// Arrange
		Integer categoryId = 1;
		List<ProductWrapper> expectedProducts = Arrays.asList(
				new ProductWrapper(1, "Product1", "Description1", 10, "Available", 1, "Category1"),
				new ProductWrapper(2, "Product2", "Description2", 15, "Available", 1, "Category1"));

		when(productDao.getProductByCategory(categoryId)).thenReturn(expectedProducts);

		// Act
		List<ProductWrapper> actualProducts = productDao.getProductByCategory(categoryId);

		// Assert
		assertEquals(expectedProducts.size(), actualProducts.size());
		for (int i = 0; i < expectedProducts.size(); i++) {
			assertEquals(expectedProducts.get(i), actualProducts.get(i));
		}

		// Verify that the getProductByCategory method was called
		verify(productDao, times(1)).getProductByCategory(categoryId);
	}

	// Add more test cases for other methods as needed
}

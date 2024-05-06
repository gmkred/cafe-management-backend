package com.gmkr.cafe.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

import com.gmkr.cafe.pojo.Product;
import com.gmkr.cafe.wrapper.ProductWrapper;

public interface ProductDao extends JpaRepository<Product, Integer> {

	List<ProductWrapper> getAllProduct();

	@Transactional
	@Modifying
	Integer updateProductStatus(@Param("status") String status, @Param("id") int id);

	List<ProductWrapper> getProductByCategory(@Param("id") Integer id);

	ProductWrapper getProductById(@Param("id") Integer id);

}

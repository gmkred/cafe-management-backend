package com.gmkr.cafe.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gmkr.cafe.pojo.Category;

public interface CategoryDao extends JpaRepository<Category, Integer> {
	List<Category> getAllCategory();
}

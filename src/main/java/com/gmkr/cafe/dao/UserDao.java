package com.gmkr.cafe.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

import com.gmkr.cafe.pojo.User;
import com.gmkr.cafe.wrapper.UserWrapper;

public interface UserDao extends JpaRepository<User, Integer> {

	public User findByEmailId(@Param("email") String email);

	public List<UserWrapper> getAllUser();

	public List<String> getAllAdmin();

//Whenever we write any query to update then we have to mention @Transactional and modifying
	@Transactional
	@Modifying
	public Integer updateStatus(@Param("status") String status, @Param("id") Integer id);

	User findByEmail(String email);
}

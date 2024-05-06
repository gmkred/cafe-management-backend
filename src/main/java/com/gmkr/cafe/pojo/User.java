package com.gmkr.cafe.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;

@NamedQuery(name = "User.findByEmailId", query = "Select u from User u where u.email =:email")
//	                                                            public UserWrapper(Integer id, String name, String email, String contactNumber, String password) {

@NamedQuery(name = "User.getAllUser", query = "Select new com.gmkr.cafe.wrapper.UserWrapper(u.id,u.name,u.email,u.contactNumber,u.password,u.status) from User u where u.role='user'")
@NamedQuery(name = "User.getAllAdmin", query = "Select u.email from User u where u.role='admin'")
@NamedQuery(name = "User.updateStatus", query = "Update User u set u.status =:status where u.id=:id")

@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "user")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	@Column(name = "name")
	private String name;
	@Column(name = "contactNumber")
	private String contactNumber;
	@Column(name = "email")
	private String email;
	@Column(name = "password")
	private String password;
	@Column(name = "status")
	private String status;
	@Column(name = "role")
	private String role;
}

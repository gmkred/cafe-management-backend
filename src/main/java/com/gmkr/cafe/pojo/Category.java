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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NamedQuery(name = "Category.getAllCategory", query = "Select c from Category c where c.id in (select p.category from Product p where p.status='true')")

@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "category")
@AllArgsConstructor
@NoArgsConstructor
public class Category implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	@Column(name = "name")
	private String name;

}

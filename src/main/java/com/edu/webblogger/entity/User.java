package com.edu.webblogger.entity;

import java.util.List;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(nullable = false, unique = false)
	private String name;
	@Column(nullable = false, unique = true)
	private String email;
	@Column(nullable = false, unique = true)
	private long mobile;
	@Column(nullable = false, unique = false)
	private String password;
	//@Enumerated(EnumType.STRING)
	@Column(nullable = false, unique = false)
	private Role role;
	@Column(nullable = false, unique = false)
	private boolean blocked;
	
	// when a UserDTO entity is removed, all associated WebBlogDTO entities in the webBlogs list will also be removed automatically
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Blog> blogs;

}

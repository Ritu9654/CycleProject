package com.cycle.entities;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int uid;

	@NotBlank(message = "Name field is required !!")
	@Size(min = 2, max = 20, message = "min 2 and max 20 characters are allowed !!")
	private String name;
	@Column(unique = true)
	private String email;
	private Date dob;
	private String role;
	private String password;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true)
	private List<Orders> OrderList = new ArrayList<>();

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Orders> getOrderList() {
		return OrderList;
	}

	public void setOrderList(List<Orders> orderList) {
		OrderList = orderList;
	}

	public User(int uid,
			@NotBlank(message = "Name field is required !!") @Size(min = 2, max = 20, message = "min 2 and max 20 characters are allowed !!") String name,
			String email, Date dob, String role, String password, List<Orders> orderList) {
		super();
		this.uid = uid;
		this.name = name;
		this.email = email;
		this.dob = dob;
		this.role = role;
		this.password = password;
		OrderList = orderList;
	}

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	

}

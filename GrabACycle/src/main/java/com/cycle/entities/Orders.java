package com.cycle.entities;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
//@AllArgsConstructor
//@NoArgsConstructor
//@Data
//@Audited
public class Orders {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int oid;

	private int quantity;
	private Date orderDate;
	private int totalAmount;

	@ManyToOne
	private Cycle cycle;

	@ManyToOne
	private User user;

	public int getOid() {
		return oid;
	}

	public void setOid(int oid) {
		this.oid = oid;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public int getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(int totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Cycle getCycle() {
		return cycle;
	}

	public void setCycle(Cycle cycle) {
		this.cycle = cycle;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Orders(int oid, int quantity, Date orderDate, int totalAmount, Cycle cycle, User user) {
		super();
		this.oid = oid;
		this.quantity = quantity;
		this.orderDate = orderDate;
		this.totalAmount = totalAmount;
		this.cycle = cycle;
		this.user = user;
	}

	public Orders() {
		super();
		// TODO Auto-generated constructor stub
	}

}

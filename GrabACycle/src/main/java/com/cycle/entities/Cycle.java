package com.cycle.entities;

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
import javax.persistence.Table;

@Entity
//@AllArgsConstructor
//@NoArgsConstructor
//@Data
//@Audited
@Table(name = "Cycle_Details")
public class Cycle {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer cid;
	private String name;
	private String model;
	private String type;
	@Column(length = 1000)
	private String description;
	private String imageurl;
	private int quantity;
	private int price;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "cycle", orphanRemoval = true)
	private List<Orders> cycleOrderList = new ArrayList<>();

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImageurl() {
		return imageurl;
	}

	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public List<Orders> getCycleOrderList() {
		return cycleOrderList;
	}

	public void setCycleOrderList(List<Orders> cycleOrderList) {
		this.cycleOrderList = cycleOrderList;
	}

	public Cycle(Integer cid, String name, String model, String type, String description, String imageurl, int quantity,
			int price, List<Orders> cycleOrderList) {
		super();
		this.cid = cid;
		this.name = name;
		this.model = model;
		this.type = type;
		this.description = description;
		this.imageurl = imageurl;
		this.quantity = quantity;
		this.price = price;
		this.cycleOrderList = cycleOrderList;
	}

	public Cycle() {
		super();
		// TODO Auto-generated constructor stub
	}

}

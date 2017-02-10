package com.dgut.lor.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.dgut.lor.util.DateRecord;
import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class RepairGoods extends DateRecord{
	User seller;
	String brand;//品牌商
	String type;//型号
	@JsonIgnore
	@ManyToOne(optional=false)
	public User getSeller() {
		return seller;
	}
	public void setSeller(User seller) {
		this.seller = seller;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}

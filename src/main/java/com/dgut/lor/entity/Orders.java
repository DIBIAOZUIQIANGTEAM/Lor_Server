package com.dgut.lor.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.dgut.lor.util.DateRecord;


@Entity
public class Orders extends DateRecord{
	User buyer;
	User seller;
	
	String goods;
	double price;
	String note;
	boolean isPayOnline;
	int state;
	String workTime;
	String realName;
	String address;
	String phone;
	
	@ManyToOne(optional=false)
	public User getBuyer() {
		return buyer;
	}
	public void setBuyer(User buyer) {
		this.buyer = buyer;
	}
	@ManyToOne(optional=false)
	public User getSeller() {
		return seller;
	}
	public void setSeller(User seller) {
		this.seller = seller;
	}
	
	public String getGoods() {
		return goods;
	}
	public void setGoods(String goods) {
		this.goods = goods;
	}

	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public boolean getIsPayOnline() {
		return isPayOnline;
	}
	public void setIsPayOnline(boolean isPayOnline) {
		this.isPayOnline = isPayOnline;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getWorkTime() {
		return workTime;
	}
	public void setWorkTime(String workTime) {
		this.workTime = workTime;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	

	
}

package com.dgut.lor.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.dgut.lor.util.BaseEntity;
import com.dgut.lor.util.CreateDateRecord;
import com.dgut.lor.util.DateRecord;


@Entity
public class OrdersProgress extends CreateDateRecord{
	
	String title;
	String content;
	Orders orders;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@ManyToOne(optional=false)
	public Orders getOrders() {
		return orders;
	}
	public void setOrders(Orders orders) {
		this.orders = orders;
	}
	
}

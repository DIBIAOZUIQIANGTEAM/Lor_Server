package com.dgut.lor.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.dgut.lor.util.BaseEntity;
import com.dgut.lor.util.CreateDateRecord;
import com.dgut.lor.util.DateRecord;

/*
 * 订单进度表
 */
@Entity
public class OrdersProgress extends CreateDateRecord{
	
	String title;//进度标题
	String content;//具体描述
	Orders orders;//关联订单
	
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

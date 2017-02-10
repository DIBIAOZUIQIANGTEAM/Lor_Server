package com.dgut.lor.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.dgut.lor.util.DateRecord;


@Entity
public class Seller extends DateRecord{
	
	User user;
	String title;
	float star;
	int turnover;
	double price;
	String repairsTypes;
	String city;
	String tradeTypes;
	int minimums;
	
	@ManyToOne(optional=false)
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public float getStar() {
		return star;
	}
	public void setStar(float star) {
		this.star = star;
	}
	public int getTurnover() {
		return turnover;
	}
	public void setTurnover(int turnover) {
		this.turnover = turnover;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getRepairsTypes() {
		return repairsTypes;
	}
	public void setRepairsTypes(String repairsTypes) {
		this.repairsTypes = repairsTypes;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getTradeTypes() {
		return tradeTypes;
	}
	public void setTradeTypes(String tradeTypes) {
		this.tradeTypes = tradeTypes;
	}
	public int getMinimums() {
		return minimums;
	}
	public void setMinimums(int minimums) {
		this.minimums = minimums;
	}

}

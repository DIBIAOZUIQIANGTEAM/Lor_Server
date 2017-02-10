package com.dgut.lor.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.dgut.lor.util.DateRecord;


@Entity
public class Records extends DateRecord{
	double coin;
	User user;
	String cause;
	
	public double getCoin() {
		return coin;
	}
	public void setCoin(double coin) {
		this.coin = coin;
	}
	
	
	@ManyToOne(optional=false)
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	public String getCause() {
		return cause;
	}
	public void setCause(String cause) {
		this.cause = cause;
	}

}
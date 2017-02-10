package com.dgut.lor.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.dgut.lor.util.CreateDateRecord;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class User extends CreateDateRecord{
	String account;
	String passwordHash;
	String name;
	String avatar;
	String email;
	double coin;
	
	@Column(unique=true)
	public String getAccount() {
		return account;
	}
	@JsonIgnore
	public String getPasswordHash() {
		return passwordHash;
	}
	public String getName() {
		return name;
	}
	public String getAvatar() {
		return avatar;
	}
	
	public void setAccount(String account) {
		this.account = account;
	}
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public double getCoin() {
		return coin;
	}
	public void setCoin(double coin) {
		this.coin = coin;
	}
	
	
}

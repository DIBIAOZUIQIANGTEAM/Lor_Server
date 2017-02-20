package com.dgut.lor.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.dgut.lor.util.BaseEntity;
import com.dgut.lor.util.DateRecord;

@Entity

public class Advertisement extends DateRecord{

	Seller seller;
	String imgurl;
	int type;
	

	public String getImgurl() {
		return imgurl;
	}
	@ManyToOne(optional=false)
	public Seller getSeller() {
		return seller;
	}
	public void setSeller(Seller seller) {
		this.seller = seller;
	}
	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	
}

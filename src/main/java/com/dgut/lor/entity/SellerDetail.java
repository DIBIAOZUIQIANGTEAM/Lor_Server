package com.dgut.lor.entity;

/*
 * 用于解析百度地图数据平台返回的商家信息
 */
public class SellerDetail {

	int status;
	Poi poi;

	String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Poi getPoi() {
		return poi;
	}

	public void setPoi(Poi poi) {
		this.poi = poi;
	}

}

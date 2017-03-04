package com.dgut.lor.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import com.dgut.lor.util.DateRecord;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;


/*
 * 商家信息bean
 * 不在本地数据库
 * 用于结合SellerDetail封装从百度地图api返回的数据
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Poi{
	
	String title;//店名
	int turnover;//交易量
	String repairsTypes;//维修类型：如空调，电脑
	String address;//商家地字
	List location;//经纬度
	int tradeTypes;//服务方式：1上门+邮寄  2邮寄     ！！！这个可能不保留 只做上门
	double  service;//人工服务费（每次交易最少收取费用）
	double coin;//余额数量
	String worktime;//营业时间
	String	hotline;//热线电话
	String notice;//商家公告
	
	
	
	public String getHotline() {
		return hotline;
	}
	public void setHotline(String hotline) {
		this.hotline = hotline;
	}
	public double getService() {
		return service;
	}
	public void setService(double service) {
		this.service = service;
	}
	public String getWorktime() {
		return worktime;
	}
	public void setWorktime(String worktime) {
		this.worktime = worktime;
	}
	public String getNotice() {
		return notice;
	}
	public void setNotice(String notice) {
		this.notice = notice;
	}
	public double getCoin() {
		return coin;
	}
	public void setCoin(double coin) {
		this.coin = coin;
	}
	String account;
	String avatar;
	
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public int getTurnover() {
		return turnover;
	}
	public void setTurnover(int turnover) {
		this.turnover = turnover;
	}

	public String getRepairsTypes() {
		return repairsTypes;
	}
	public void setRepairsTypes(String repairsTypes) {
		this.repairsTypes = repairsTypes;
	}

	public int getTradeTypes() {
		return tradeTypes;
	}
	public void setTradeTypes(int tradeTypes) {
		this.tradeTypes = tradeTypes;
	}
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public List getLocation() {
		return location;
	}
	public void setLocation(List location) {
		this.location = location;
	}
	
	
}

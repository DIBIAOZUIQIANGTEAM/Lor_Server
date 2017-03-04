package com.dgut.lor.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.dgut.lor.util.DateRecord;

/*
 * 订单表
 */
@Entity
public class Orders extends DateRecord {

	User buyer;// 保存买家信息
	User seller;// 保存商家信息

	String goods;// 商品名称（通常是品牌+型号）
	double price;// 基础服务费（商家信息设置，在百度地图数据平台上）
	String note;// 备注
	boolean isPayOnline;// 1在线支付，2货到付款
	int state;// 1创建等待接单 2接单等待处理 3正在上门 4买家确认完成 5取消订单，申请退款 6拒绝退款 7同意退款

	String workTime;// 上门时间
	String realName;// 联系人姓名
	String address;// 上门地址
	String phone;// 联系电话

	@ManyToOne(optional = false)
	public User getBuyer() {
		return buyer;
	}

	public void setBuyer(User buyer) {
		this.buyer = buyer;
	}

	@ManyToOne(optional = false)
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

package com.dgut.lor.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.dgut.lor.util.DateRecord;


/**
 * @author ���
 *������
 */
@Entity
public class Orders extends DateRecord{
	User buyer;//������
	Goods goods;//��Ʒ
	Contact contact;//��ϵ��Ϣ
	double price;//�۸�
	int quantity;//��Ʒ����
	String note;
	boolean isPayOnline;
	int state;//״̬��1��ʾ�������ύ��δ�ӵ���2��ʾ�Ѿ��ӵ���3��ʾ�����ͣ�4��ʾȷ���ջ���������ɣ�5��ʾ�Ѿ����ۣ� 6��ʾ����ȡ�� ��7��ʾ����ͬ��ȡ��8��ʾ�����ܾ�ȡ�� 
	
	
	@ManyToOne(optional=false)
	public User getBuyer() {
		return buyer;
	}
	public void setBuyer(User buyer) {
		this.buyer = buyer;
	}
	
	@ManyToOne(optional=false)
	public Goods getGoods() {
		return goods;
	}
	public void setGoods(Goods goods) {
		this.goods = goods;
	}
	
	@ManyToOne(optional=false)
	public Contact getContact() {
		return contact;
	}
	public void setContact(Contact contact) {
		this.contact = contact;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public boolean isPayOnline() {
		return isPayOnline;
	}
	public void setPayOnline(boolean isPayOnline) {
		this.isPayOnline = isPayOnline;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}


	
}

package com.dgut.lor.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.dgut.lor.util.DateRecord;


@Entity
public class Goods extends DateRecord{
	User publishers;//������
	String title;//����
	String content;//����
	String albums;//��Ʒͼ��
	int quantity;//��Ʒ����
	double price;//�۸�
	
	@ManyToOne(optional=false)
	public User getPublishers() {
		return publishers;
	}
	public void setPublishers(User publishers) {
		this.publishers = publishers;
	}
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
	public String getAlbums() {
		return albums;
	}
	public void setAlbums(String albums) {
		this.albums = albums;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}


	
}

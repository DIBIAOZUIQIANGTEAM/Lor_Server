package com.dgut.lor.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.dgut.lor.util.CreateDateRecord;
import com.dgut.lor.util.DateRecord;


/**
 * @author ���
 *˽����Ϣ��
 */
@Entity
public class Message extends CreateDateRecord{
	
	User sender;//������
	User receiver;//������
	String content;//����
	String picture;//ͼƬ��ַ
	int type;//ͼ�������ж� 1Ϊͼ 0Ϊ��
	
	@ManyToOne(optional=false)
	public User getSender() {
		return sender;
	}
	public void setSender(User sender) {
		this.sender = sender;
	}
	@ManyToOne(optional=false)
	public User getReceiver() {
		return receiver;
	}
	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}

	
}

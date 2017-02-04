package com.dgut.lor.service;

import org.springframework.data.domain.Page;

import com.dgut.lor.entity.Goods;
import com.dgut.lor.entity.Message;
import com.dgut.lor.entity.Subscriber;
import com.dgut.lor.entity.User;

public interface IMessageService {
	Message save(Message message);

	Page<Message> getMessageByUserId(User currentUser, User receiver,
			int page);
	
}

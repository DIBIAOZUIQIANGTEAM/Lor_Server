package com.dgut.lor.service;


import org.springframework.data.domain.Page;

import com.dgut.lor.entity.Records;
import com.dgut.lor.entity.Subscriber;
import com.dgut.lor.entity.User;


public interface ISubscribeService {

	 Records save(Records records) ;

	 Page<Records> getRecordsByUserId(User currentUser, int page);

	boolean checkSubscribed(Integer id, int publishers_id);

	void addSubscriber(User me, User publishers);

	void removeSubscriber(User me, User publishers);

	int countSubscribers(int publishers_id);

	Page<Subscriber> getPublishersByUserId(User currentUser, int i);

	Page<Subscriber> getSubscribersByUserId(User currentUser, int i);

}

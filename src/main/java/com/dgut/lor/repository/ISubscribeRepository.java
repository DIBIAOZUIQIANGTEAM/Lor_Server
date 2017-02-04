package com.dgut.lor.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.dgut.lor.entity.Records;
import com.dgut.lor.entity.Subscriber;
import com.dgut.lor.entity.User;
import com.dgut.lor.entity.Subscriber.Key;

@Repository
public interface ISubscribeRepository extends PagingAndSortingRepository<Subscriber, Subscriber.Key>{

	@Query("select count(*) from Subscriber subscriber where subscriber.id.publishers.id = ?1")
	int subscribeCountsOfSubscriber(int publishers_id);

	@Query("select count(*) from Subscriber subscriber where subscriber.id.subscribers.id = ?1 and subscriber.id.publishers.id = ?2")
	int checkSubscribeExsists(Integer id, int publishers_id);

	@Query("from Subscriber subscriber where subscriber.id.publishers.id = ?1")
	Page<Subscriber> getPublishersByUserId(Integer id, Pageable pageable);

	@Query("from Subscriber subscriber where subscriber.id.subscribers.id = ?1")
	Page<Subscriber> getSubscribersByUserId(Integer id, Pageable pageable);
	
	
}
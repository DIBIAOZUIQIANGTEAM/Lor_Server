package com.dgut.lor.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.dgut.lor.entity.Admin;
import com.dgut.lor.entity.Advertisement;
import com.dgut.lor.entity.Goods;
import com.dgut.lor.entity.Likes;
import com.dgut.lor.entity.Likes.Key;

@Repository
public interface IAdvertisementRepository extends PagingAndSortingRepository<Advertisement, Integer>{

	
	
}

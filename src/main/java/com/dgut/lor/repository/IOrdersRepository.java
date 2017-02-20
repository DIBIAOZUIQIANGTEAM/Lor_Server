package com.dgut.lor.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.dgut.lor.entity.Orders;

@Repository
public interface IOrdersRepository extends PagingAndSortingRepository<Orders, Integer>{
	
	@Query("from Orders orders where orders.buyer.id = ?1")	
	Page<Orders> findOrdersPageByBuyerId(Integer id, Pageable pageable);

}

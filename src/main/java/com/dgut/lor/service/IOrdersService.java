package com.dgut.lor.service;

import org.springframework.data.domain.Page;

import com.dgut.lor.entity.Orders;

public interface IOrdersService {
	Orders save(Orders orders);


	Page<Orders> findOrdersPageByBuyerId(Integer id, int page);



	Orders findOne(int orders_id);

}

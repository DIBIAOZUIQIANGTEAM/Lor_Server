package com.dgut.lor.service;

import org.springframework.data.domain.Page;

import com.dgut.lor.entity.Goods;
import com.dgut.lor.entity.Orders;
import com.dgut.lor.entity.User;

public interface IOrdersService {
	Orders save(Orders orders);


	Page<Orders> findOrdersPageByBuyerId(Integer id, int page);


	Page<Orders> findOrdersPageByUserId(Integer id, int publishers_id, int page);


	Orders findOne(int orders_id);

}

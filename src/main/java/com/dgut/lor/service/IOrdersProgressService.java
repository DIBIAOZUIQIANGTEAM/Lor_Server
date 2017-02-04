package com.dgut.lor.service;

import org.springframework.data.domain.Page;

import com.dgut.lor.entity.Goods;
import com.dgut.lor.entity.Orders;
import com.dgut.lor.entity.OrdersProgress;
import com.dgut.lor.entity.User;

public interface IOrdersProgressService {
	
	OrdersProgress save(OrdersProgress progress);


	Page<OrdersProgress> findOrdersProgressPageByOrdersId(Integer orders_id, int page);



}

package com.dgut.lor.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dgut.lor.entity.Goods;
import com.dgut.lor.entity.Orders;
import com.dgut.lor.entity.OrdersComment;
import com.dgut.lor.entity.User;

public interface IOrdersCommentService {
	OrdersComment save(OrdersComment ordersComment);
	Page<OrdersComment> getOrderComment(int orderCommentId,int page);
	Page<OrdersComment> findAllByGoodsId(Integer goodsId,int page);
}

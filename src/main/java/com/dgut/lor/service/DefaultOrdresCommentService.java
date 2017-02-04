package com.dgut.lor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dgut.lor.entity.Orders;
import com.dgut.lor.entity.OrdersComment;
import com.dgut.lor.entity.User;
import com.dgut.lor.repository.IOrdersCommentRepository;
import com.dgut.lor.repository.IOrdersRepository;

@Component
@Service
@Transactional
public class DefaultOrdresCommentService implements IOrdersCommentService {

	@Autowired
	IOrdersCommentRepository ordersCommentRepo;

	@Override
	public OrdersComment save(OrdersComment ordersComment) {
		return ordersCommentRepo.save(ordersComment);
	}

	@Override
	public Page<OrdersComment> getOrderComment(int orderCommentId,int page) {
		Sort sort = new Sort(Direction.DESC,"createDate");
		PageRequest pageRequest = new PageRequest(page, 10, sort);
		return ordersCommentRepo.findAllByOrdersCommentId(orderCommentId, pageRequest);
	}

	@Override
	public Page<OrdersComment> findAllByGoodsId(Integer goodsId,int page) {
		Sort sort = new Sort(Direction.DESC,"createDate");
		PageRequest pageRequest = new PageRequest(page, 10, sort);
		return ordersCommentRepo.findAllByGoodsId(goodsId, pageRequest);
	}


}

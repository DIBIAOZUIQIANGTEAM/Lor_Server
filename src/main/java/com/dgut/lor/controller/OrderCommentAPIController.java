package com.dgut.lor.controller;

import java.io.File;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dgut.lor.entity.Contact;
import com.dgut.lor.entity.Goods;
import com.dgut.lor.entity.Orders;
import com.dgut.lor.entity.OrdersComment;
import com.dgut.lor.entity.OrdersProgress;
import com.dgut.lor.entity.User;
import com.dgut.lor.service.IContactService;
import com.dgut.lor.service.IGoodsService;
import com.dgut.lor.service.IOrdersCommentService;
import com.dgut.lor.service.IOrdersProgressService;
import com.dgut.lor.service.IOrdersService;
import com.dgut.lor.service.IUserService;

@RestController
@RequestMapping("/api/orderscomment")
public class OrderCommentAPIController {

	@Autowired
	IUserService userService;

	@Autowired
	IOrdersCommentService orderCommentService;
	
	@Autowired
	IOrdersService orderService;
	
	/**
	 * �ҵ���ǰ�û�
	 * 
	 * @param request
	 * @return user
	 */
	public User getCurrentUser(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		Integer uid = (Integer) session.getAttribute("uid");
		return userService.findById(uid);
	}

	/**
	 *  �����Ʒ����
	 * @param orders_id
	 * @param comments
	 * @return
	 */
	@RequestMapping(value="/addcomment",method=RequestMethod.POST)
	public OrdersComment addOrderComment(
			@RequestParam int orders_id,
			@RequestParam String comments) {
		OrdersComment ordersComment = new OrdersComment();
		
		Orders orders = orderService.findOne(orders_id);
		orders.setState(5);
		orderService.save(orders);
		
		ordersComment.setOrders(orderService.findOne(orders_id));
		ordersComment.setComments(comments);
		
		return orderCommentService.save(ordersComment);
	}
	
	/**
	 * ��ȡ��Ʒ����
	 * @return
	 */
	@RequestMapping(value="/getcomment",method=RequestMethod.POST)
	public Page<OrdersComment> getOrderComment(
			@RequestParam int goodsId,
			@RequestParam int page){
		return orderCommentService.findAllByGoodsId(goodsId, page);
	}
}

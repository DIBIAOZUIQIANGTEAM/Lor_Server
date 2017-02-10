package com.dgut.lor.controller;

import java.io.File;

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

import com.dgut.lor.entity.Orders;
import com.dgut.lor.entity.OrdersProgress;
import com.dgut.lor.entity.Records;
import com.dgut.lor.entity.User;
import com.dgut.lor.service.IOrdersProgressService;
import com.dgut.lor.service.IOrdersService;
import com.dgut.lor.service.IRecordsService;
import com.dgut.lor.service.IUserService;

@RestController
@RequestMapping("/api/orders/progress")
public class OrdersProgressAPIController {

	@Autowired
	IUserService userService;

	@Autowired
	IOrdersService ordersService;
	

	@Autowired
	IOrdersProgressService ordersProgressService;
	
	@Autowired
	IRecordsService recordsService;

	public User getCurrentUser(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		Integer uid = (Integer) session.getAttribute("uid");
		return userService.findById(uid);
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public OrdersProgress addOrdersProgress(@RequestParam int orders_id,
			@RequestParam String  content, @RequestParam String title, @RequestParam int state,
		 HttpServletRequest request) {

		Orders orders=new Orders();
		orders.setId(orders_id);
		OrdersProgress progress = new OrdersProgress();
		progress.setContent(content);
		progress.setTitle(title);
		progress.setOrders(orders);
		progress = ordersProgressService.save(progress);
		orders=ordersService.findOne(orders_id);
		orders.setState(state);
		orders=	ordersService.save(orders);
		
			if(state==7)
			{
				User buyer = orders.getBuyer();
				User user=	userService.save(buyer);
			
				addRecords(user,"完成了订单("+orders.getId()+") 支出服务费",orders.getPrice());
			}
			if(state==4)
			{
				User seller = orders.getSeller();
				User user=	userService.save(seller);
				addRecords(user,"完成了订单("+orders.getId()+") 获得服务费",orders.getPrice());
			}
		return progress;
	}

	@RequestMapping(value = "/byOrdersId/{page}", method = RequestMethod.POST)
	public Page<OrdersProgress> findOrdersProgressPageByOrdersId(@PathVariable int page,@RequestParam int orders_id,
			HttpServletRequest request) {
		return ordersProgressService.findOrdersProgressPageByOrdersId(orders_id, page);
	}

	public Records addRecords(User user, String cause, double coin
			) {
		Records records = new Records();
		records.setCause(cause);
		records.setUser(user);
		records.setCoin(coin);
		records = recordsService.save(records);
		return records;

	}
	
	
}

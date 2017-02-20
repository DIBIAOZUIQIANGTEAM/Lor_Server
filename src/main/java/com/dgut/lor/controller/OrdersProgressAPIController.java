package com.dgut.lor.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dgut.lor.entity.Orders;
import com.dgut.lor.entity.OrdersProgress;
import com.dgut.lor.entity.Records;
import com.dgut.lor.entity.User;
import com.dgut.lor.service.IOrdersProgressService;
import com.dgut.lor.service.IOrdersService;
import com.dgut.lor.service.IRecordsService;
import com.dgut.lor.service.IUserService;
import com.dgut.lor.util.JsonUtils;

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
	public JSONObject addOrdersProgress(@RequestParam int orders_id,
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
		if(progress!=null)
			return JsonUtils.toJson(1, "成功", progress);
		return JsonUtils.toJson(2, "失败", "");
	}

	@RequestMapping(value = "/byOrdersId", method = RequestMethod.POST)
	public JSONObject findOrdersProgressPageByOrdersId(@RequestParam int page,@RequestParam int orders_id,
			HttpServletRequest request) {
		Page<OrdersProgress> progressPage=ordersProgressService.findOrdersProgressPageByOrdersId(orders_id, page);
		
		return  JsonUtils.toJson(1, "成功", progressPage);
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

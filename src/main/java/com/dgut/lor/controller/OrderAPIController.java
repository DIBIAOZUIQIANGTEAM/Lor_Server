package com.dgut.lor.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

/*
 * 订单创建查询修改api
 * entity:Orders
 */
@RestController
@RequestMapping("/api/orders")
public class OrderAPIController {

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

	
	/*
	 * 创建新订单
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public JSONObject addOrdres(@RequestParam String seller_account,
			@RequestParam String goods, @RequestParam String workTime,
			@RequestParam String realName, @RequestParam String address,
			@RequestParam String phone, @RequestParam double price,
			@RequestParam String note, @RequestParam boolean isPayOnline,
			HttpServletRequest request) {

		User seller = userService.findByAccount(seller_account);
		Orders orders = new Orders();
		orders.setBuyer(getCurrentUser(request));
		orders.setSeller(seller);
		orders.setGoods(goods);
		orders.setWorkTime(workTime);
		orders.setRealName(realName);
		orders.setAddress(address);
		orders.setPhone(phone);
		orders.setPrice(price);
		orders.setNote(note);
		orders.setIsPayOnline(isPayOnline);
		orders.setState(1);
		orders = ordersService.save(orders);
		//插入一条订单进度
		addOrdersProgress(orders.getId(), "等待商家接单", "订单创建成功");

		User buyer = orders.getBuyer();
		User user = userService.save(buyer);
		//插入一条消费纪录
		addRecords(user, "创建订单(" + orders.getId() + ")支付服务费", orders.getPrice());

		return JsonUtils.toJson(1, "创建订单成功", orders);
	}

	
	/*
	 * 获取在线用户自身的订单，分页获取
	 */
	@RequestMapping(value = "/my", method = RequestMethod.POST)
	public JSONObject findOrdersPageByBuyerId(@RequestParam int page,
			HttpServletRequest request) {

		User user = getCurrentUser(request);
		Page<Orders> orderPage = ordersService.findOrdersPageByBuyerId(
				user.getId(), page);
		if (orderPage != null)
			return JsonUtils.toJson(1, "查找成功", orderPage);
		else {
			return JsonUtils.toJson(2, "不存在", "");
		}
	}

	/*
	 * 根据orders_id查订单详情
	 */
	@RequestMapping(value = "/getOrders", method = RequestMethod.POST)
	public JSONObject findOneByOrdersId(@RequestParam int orders_id) {
		System.out.println("查找orders_id:" + orders_id);
		Orders orders = ordersService.findOne(orders_id);
		if (ordersService.findOne(orders_id) != null) {
			return JsonUtils.toJson(1, "查找成功", orders);
		} else {
			return JsonUtils.toJson(2, "查找失败", "");
		}
	}
	
	

	public OrdersProgress addOrdersProgress(int orders_id, String content,
			String title) {
		Orders orders = new Orders();
		orders.setId(orders_id);
		OrdersProgress progress = new OrdersProgress();
		progress.setContent(content);
		progress.setTitle(title);
		progress.setOrders(orders);
		progress = ordersProgressService.save(progress);
		return progress;
	}

	public Records addRecords(User user, String cause, double coin) {
		Records records = new Records();
		records.setCause(cause);
		records.setUser(user);
		records.setCoin(coin);
		records = recordsService.save(records);
		return records;

	}
}

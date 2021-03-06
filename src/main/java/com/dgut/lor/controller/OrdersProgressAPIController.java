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

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.PushPayload;

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
 * 订单进度相关api  
 * entity:OrdersProgress
 */
@RestController
@RequestMapping("/api/orders/progress")
public class OrdersProgressAPIController {

	// 极光推送appkey，订单进度改变时，调用推送api通知客户端
	private static final String MASTER_SECRET = "0209d339aeb4034e040b27cb";
	private static final String APP_KEY = "722c6d173c859f33536f05e1";

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
	 * 查询订单
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public JSONObject addOrdersProgress(@RequestParam int orders_id,
			@RequestParam String content, @RequestParam String title,
			@RequestParam int state, HttpServletRequest request)
			throws APIConnectionException, APIRequestException {

		Orders orders = null;
		orders = ordersService.findOne(orders_id);
		orders.setState(state);
		orders = ordersService.save(orders);
		OrdersProgress progress = new OrdersProgress();
		progress.setContent(content);
		progress.setTitle(title);
		progress.setOrders(orders);
		progress = ordersProgressService.save(progress);
		
		//推送
		JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY, null,
				ClientConfig.getInstance());
		PushPayload payload = buildPushObject_all_all_alert();
		PushResult result = jpushClient.sendPush(payload);

		//交易完成，添加消费纪录
		if (state == 7) {
			User buyer = orders.getBuyer();
			User user = userService.save(buyer);
			addRecords(user, "完成了订单(" + orders.getId() + ") 支出服务费",
					orders.getPrice());
		}
		if (state == 4) {
			User seller = orders.getSeller();
			User user = userService.save(seller);
			addRecords(user, "完成了订单(" + orders.getId() + ") 获得服务费",
					orders.getPrice());
		}
		
		if (progress != null)
			return JsonUtils.toJson(1, "成功", progress);
		return JsonUtils.toJson(2, "失败", "");
	}

	/*
	 * 测试
	 */
	public static PushPayload buildPushObject_all_all_alert() {
		return PushPayload.alertAll("13172837508");
	}

	/*
	 * 查询订单纪录，按时间排序一次性取出，反正数量不会大于10，默认第0页取出
	 */
	@RequestMapping(value = "/byOrdersId", method = RequestMethod.POST)
	public JSONObject findOrdersProgressPageByOrdersId(@RequestParam int page,
			@RequestParam int orders_id, HttpServletRequest request) {
		Page<OrdersProgress> progressPage = ordersProgressService
				.findOrdersProgressPageByOrdersId(orders_id, page);

		return JsonUtils.toJson(1, "成功", progressPage);
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

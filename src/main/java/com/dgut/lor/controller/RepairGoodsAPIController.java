package com.dgut.lor.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.dgut.lor.entity.RepairGoods;
import com.dgut.lor.entity.User;
import com.dgut.lor.service.IRepairGoodsService;
import com.dgut.lor.service.IUserService;
import com.dgut.lor.util.JsonUtils;

@RestController
@RequestMapping("/api/repairGoods")
public class RepairGoodsAPIController {

	@Autowired
	IUserService userService;

	@Autowired
	IRepairGoodsService goodsService;

	@RequestMapping(value = "/me", method = RequestMethod.GET)
	public User getCurrentUser(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		Integer uid = (Integer) session.getAttribute("uid");
		return userService.findById(uid);
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public JSONObject addGoods(@RequestParam String seller_account,
			@RequestParam String brand, @RequestParam String type) {
		User user=userService.findByAccount(seller_account);
		
		RepairGoods goods=new RepairGoods();
		goods.setBrand(brand);
		goods.setType(type);
		goods.setSeller(user);

		goods=goodsService.save(goods);
		if (goods != null) {
			return JsonUtils.toJson(1, "保存成功", goods);
		}
		return JsonUtils.toJson(2, "保存失败", "");
	
	}

	@RequestMapping(value = "/bySellerID", method = RequestMethod.GET)
	public JSONObject getGoodsPage(@RequestParam String seller_account) {
		Iterable<RepairGoods> goodsList= goodsService.findRepairGoods(seller_account);
		if (goodsList != null) {
			return JsonUtils.toJson(1, "查询成功", goodsList);
		}
		return JsonUtils.toJson(2, "查询不到数据", "");
	}
}

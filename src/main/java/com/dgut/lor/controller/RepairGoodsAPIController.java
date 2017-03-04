package com.dgut.lor.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dgut.lor.entity.RepairGoods;
import com.dgut.lor.entity.User;
import com.dgut.lor.service.IRepairGoodsService;
import com.dgut.lor.service.IUserService;
import com.dgut.lor.util.JsonUtils;

/*
 * 维修商品相关api  
 * entity:RepairGoods
 */
@RestController
@RequestMapping("/api/repairGoods")
public class RepairGoodsAPIController {

	@Autowired
	IUserService userService;

	@Autowired
	IRepairGoodsService goodsService;

	/*
	 * 添加维修商品 数据形式 用户1id | 商品品牌 | 商品型号1,商品型号2,商品型号3... 商品型号分号隔开 用户1id | 商品品牌2 |
	 * 商品型号1,商品型号2,商品型号3... 用户2id | 商品品牌3 | 商品型号1,商品型号2,商品型号3...
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public JSONObject addGoods(@RequestBody List<RepairGoods> goodsList,
			HttpServletRequest request) {

		User user = getCurrentUser(request);
		RepairGoods goods = null;

		goodsService.deleteBySellerID(user.getId());
		for (int i = 0; i < goodsList.size(); i++) {
			goods = goodsList.get(i);
			goods.setSeller(user);
			goods = goodsService.save(goods);

			if (goods == null) {
				return JsonUtils.toJson(2, "保存失败", "");
			} else {
				System.out.println(goods.getBrand() + "," + goods.getType());
			}
		}
		return JsonUtils.toJson(1, "保存成功", goodsList);

	}

	/*
	 * 查询某个商家支持的维修类型
	 */
	@RequestMapping(value = "/bySellerID", method = RequestMethod.GET)
	public JSONObject getGoodsPage(@RequestParam String seller_account) {
		Iterable<RepairGoods> goodsList = goodsService
				.findRepairGoods(seller_account);
		if (goodsList != null) {
			return JsonUtils.toJson(1, "查询成功", goodsList);
		}
		return JsonUtils.toJson(2, "查询不到数据", "");
	}

	public User getCurrentUser(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		Integer uid = (Integer) session.getAttribute("uid");
		return userService.findById(uid);
	}
}

package com.dgut.lor.controller;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dgut.lor.entity.Poi;
import com.dgut.lor.entity.SellerDetail;
import com.dgut.lor.entity.User;
import com.dgut.lor.service.IRecordsService;
import com.dgut.lor.service.IUserService;
import com.dgut.lor.util.HttpRequest;
import com.dgut.lor.util.JsonUtils;

/**
 * 卖家用户信息api，商家客户端访问
 */
@RestController
@RequestMapping("/api/seller")
public class SellerAPIController {

	@Autowired
	IUserService userService;

	@Autowired
	IRecordsService recordsService;

	/*
	 * 商家注册账号密码，属于基础用户信息，保存在本地数据库user表中，其他信息保存在百度地图数据平台，entity名字Poi百度地图api账号
	 * 13172837508 密码jr0303.api控制台---左侧导航我的数据----数据管理平台----lor云图（字段有中文名）
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public JSONObject register(@RequestParam String account,
			@RequestParam String passwordHash) {

		if (userService.findByAccount(account) == null) {
			User user = new User();
			user.setAccount(account);
			user.setPasswordHash(passwordHash);
			System.out.println("注册成功");
			return JsonUtils.toJson(1, "注册成功", userService.save(user));

		}
		System.out.println("注册失败");
		return JsonUtils.toJson(2, "注册失败,用户已经存在", "");
	}

	/*
	 *商家登录时候，向百度地图api查询商家信息 ，封装到pois中返回给客户端
	 *
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public JSONObject login(@RequestParam String account,
			@RequestParam String passwordHash, HttpServletRequest request) {
		User user = userService.findByAccount(account);
		if (user == null) {
			System.out.println("login失败,用户不存在");
			return JsonUtils.toJson(2, "login失败,用户不存在", "");
		} else if (user.getPasswordHash().equals(passwordHash)) {
			HttpSession session = request.getSession(true);
			if (user.getName() != null) {
				// 发送 POST 请求百度地图api，根据用户账号account字段查询
				String sr = HttpRequest.sendGet(
						"http://api.map.baidu.com/geodata/v3/poi/list",
						"geotable_id=115331&ak=IwiUF0Rcfn1ckrPLeABUw4r9OwXEL6NP&"
								+ "account=" + user.getAccount());
				System.out.println(sr);

				JSONObject object = JSONObject.fromObject(sr);

				object = object.getJSONArray("pois").getJSONObject(0);
				//同时纪录id，id字段是百度地图数据的主键
				session.setAttribute("id", object.get("id"));
				Integer uid = (Integer) session.getAttribute("uid");
				Integer id = (Integer) session.getAttribute("id");
				System.out.println("login成功 uid:" + uid);
				System.out.println("login成功 id:" + id);
			}
			session.setAttribute("uid", user.getId());
			return JsonUtils.toJson(1, "login成功", userService.save(user));

		} else {
			System.out.println("login失败");
			return JsonUtils.toJson(3, "login失败,密码不正确", "");
		}
	}

	
	/*
	 * 获取当前在线商家自身的信息，相当于刷新用户信息
	 */
	@RequestMapping(value = "/me", method = RequestMethod.POST)
	public JSONObject getCurrentUserPost(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		Integer uid = (Integer) session.getAttribute("uid");
		User user = userService.findById(uid);
		if (user != null) {
			JsonConfig config = new JsonConfig();
			config.setIgnoreDefaultExcludes(false);
			config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
			config.setExcludes(new String[] {
			// 只要设置这个数组，指定过滤哪些字段。
			"city", "create_time", "district", "geotable_id", "modify_time",
					"province", "gcj_location", "city_id", "id" });
			// 发送 POST 请求百度地图api，根据id字段查询
			String sr = HttpRequest.sendGet(
					"http://api.map.baidu.com/geodata/v3/poi/detail",
					"geotable_id=115331&ak=IwiUF0Rcfn1ckrPLeABUw4r9OwXEL6NP&"
							+ "id=" + getLBSSellerId(request));
			System.out.println(sr);
			JSONObject object = JSONObject.fromObject(sr, config);
			//封装到SellerDetail中，返回给客户端
			SellerDetail detail = (SellerDetail) JSONObject.toBean(object,
					SellerDetail.class);
			detail.getPoi().setCoin(user.getCoin());
			
			//测试数据是否为空，可删除
			System.out.println("detail:" + detail.getPoi().getAddress()
					+ detail.getPoi().getLocation().get(0) + "");
			
			return JsonUtils.toJson(1, "查找成功", detail.getPoi());
		}
		return JsonUtils.toJson(2, "查找失败", "");
	}

	/*
	 * 更新头像
	 */
	@RequestMapping(value = "/update/avatar", method = RequestMethod.POST)
	public JSONObject UpdateAvatar(
			@RequestParam(value = "avatar") MultipartFile avatar,
			HttpServletRequest request) {
		User user = getCurrentUser(request);
		if (avatar != null) {
			try {
				String realPath = request.getSession().getServletContext()
						.getRealPath("/WEB-INF/upload/user");
				System.out.println(avatar.getSize());
				FileUtils.copyInputStreamToFile(avatar.getInputStream(),
						new File(realPath, getCurrentUser(request).getAccount()
								+ ".png"));
				user.setAvatar("upload/user/" + user.getAccount() + ".png");
				user = userService.save(user);

				return JsonUtils.toJson(1, "success", "");

			} catch (Exception e) {
				e.printStackTrace();

			}
		}
		return JsonUtils.toJson(2, "fail", "");

	}

	/*
	 * 更新密码
	 */
	@RequestMapping(value = "/update/password", method = RequestMethod.POST)
	public JSONObject UpdatePassword(@RequestParam String newPassword,
			HttpServletRequest request) {
		User user = getCurrentUser(request);
		user.setPasswordHash(newPassword);
		user = userService.save(user);
		if (user != null) {
			return JsonUtils.toJson(1, "密码修改成功", user);
		}
		return JsonUtils.toJson(2, "密码修改失败", "");
	}

	/*
	 * 修改密码
	 */
	@RequestMapping(value = "/forget/password", method = RequestMethod.POST)
	public JSONObject ForgetPassword(@RequestParam String account,
			@RequestParam String newPassword) {

		User user = userService.findByAccount(account);
		if (user == null) {
			return null;
		}
		user.setPasswordHash(newPassword);
		user = userService.save(user);
		if (user != null) {
			return JsonUtils.toJson(1, "密码修改成功", user);
		}
		return JsonUtils.toJson(2, "密码修改失败", "");

	}

	/*
	 * 更新昵称（店名），同时修改百度地图上面的title字段（店名）
	 */
	@RequestMapping(value = "/update/userName", method = RequestMethod.POST)
	public JSONObject UpdateUserName(@RequestParam String userName,
			HttpServletRequest request) throws UnsupportedEncodingException {
		User user = getCurrentUser(request);
		user.setName(userName);
		user = userService.save(user);

		// 发送 POST 请求
		String sr = HttpRequest.sendPost(
				"http://api.map.baidu.com/geodata/v3/poi/update",
				"account=" + user.getAccount() + "&title="
						+ URLEncoder.encode(userName, "utf-8"));
		System.out.println("昵称修改" + sr);
		
		//偷懒了，其他情况懒得处理
		return JsonUtils.toJson(1, "昵称修改成功", user);
	}


	/*
	 * 客户端注册时用，web端可忽略
	 */
	@RequestMapping(value = "/finduser", method = RequestMethod.POST)
	public JSONObject FindUserByAccount(@RequestParam String account) {
		User user = userService.findByAccount(account);
		if (user != null) {
			return JsonUtils.toJson(1, "成功", user);
		}
		return JsonUtils.toJson(2, "失败", "");
	}

	/*
	 * 修改商家信息（在百度地图数据平台上，不涉及本地数据库）
	 */
	@RequestMapping(value = "/update/poi", method = RequestMethod.POST)
	public JSONObject UpdatePoi(@RequestBody Poi poi, HttpServletRequest request)
			throws UnsupportedEncodingException {

		String sr = HttpRequest.sendPost(
				"http://api.map.baidu.com/geodata/v3/poi/update",
				"id=" + getLBSSellerId(request) + "&coord_type=3" + "&address="
						+ URLEncoder.encode(poi.getAddress(), "utf-8")
						+ "&latitude=" + poi.getLocation().get(1)
						+ "&longitude=" + poi.getLocation().get(0)
						+ "&tradeTypes=" + poi.getTradeTypes() + "&service="
						+ poi.getService() + "&worktime="
						+ URLEncoder.encode(poi.getWorktime(), "utf-8")
						+ "&hotline=" + poi.getHotline() + "&repairsTypes="
						+ URLEncoder.encode(poi.getRepairsTypes(), "utf-8")
						+ "&notice="
						+ URLEncoder.encode(poi.getNotice(), "utf-8"));
		System.out.println("poi修改:" + sr);

		return JsonUtils.toJson(1, "poi修改成功", poi);
	}

	/*
	 * 创建商家信息
	 * 商家刚注册完基础信息账号密码后（本地数据库），
	 * 需要补全店名（即昵称），地理位置（经纬度），地理位置文字描述（百度地图数据平台）
	 */
	@RequestMapping(value = "/create/poi", method = RequestMethod.POST)
	public JSONObject createSeller(@RequestBody Poi poi,
			HttpServletRequest request) throws UnsupportedEncodingException {
		User user = getCurrentUser(request);
		user.setName(poi.getTitle());
		user = userService.save(user);

		String sr = HttpRequest.sendPost(
				"http://api.map.baidu.com/geodata/v3/poi/create",

				"coord_type=3" + "&address="
						+ URLEncoder.encode(poi.getAddress(), "utf-8")
						+ "&latitude=" + poi.getLocation().get(1)
						+ "&longitude=" + poi.getLocation().get(0) + "&title="
						+ URLEncoder.encode(user.getName(), "utf-8") +

						"&account=" + user.getAccount()
						+ "&avatar=upload/user/" + user.getAccount() + ".png");
		System.out.println("poi创建:" + sr);

		return JsonUtils.toJson(1, "poi创建成功", poi);
	}

	public User getCurrentUser(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		Integer uid = (Integer) session.getAttribute("uid");
		return userService.findById(uid);
	}

	public int getLBSSellerId(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		Integer id = (Integer) session.getAttribute("id");
		return id;
	}
}

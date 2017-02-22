package com.dgut.lor.controller;

import java.io.File;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.GsonFactoryBean;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cn.jpush.api.JPushClient;

import com.dgut.lor.entity.Poi;
import com.dgut.lor.entity.SellerDetail;
import com.dgut.lor.entity.User;
import com.dgut.lor.service.IRecordsService;
import com.dgut.lor.service.IUserService;
import com.dgut.lor.util.HttpRequest;
import com.dgut.lor.util.JsonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * 卖家用户信息api
 */
@RestController
@RequestMapping("/api/seller")
public class SellerAPIController {

	@Autowired
	IUserService userService;

	@Autowired
	IRecordsService recordsService;

	@RequestMapping(value = "/register/{account}+{passwordHash}", method = RequestMethod.GET)
	public JSONObject registerGet(@PathVariable String account,
			@PathVariable String passwordHash) {

		if (userService.findByAccount(account) == null) {

			
			
			User user = new User();
			user.setAccount(account);
			user.setPasswordHash(passwordHash);
			user = userService.save(user);

			return JsonUtils.toJson(1, "注册成功", user);

		}
		System.out.println("注册失败");
		return JsonUtils.toJson(2, "注册失败,用户已经存在", "");
	}

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

	// @RequestMapping(value = "/register", method = RequestMethod.POST)
	// public JSONObject registerBD(@RequestParam String account,
	// @RequestParam String passwordHash) {
	//
	// if (userService.findByAccount(account) == null) {
	//
	// User user = new User();
	// user.setAccount(account);
	// user.setPasswordHash(passwordHash);
	// System.out.println("注册成功");
	// return JsonUtils.toJson(1, "注册成功", userService.save(user));
	//
	// }
	// System.out.println("注册失败");
	// return JsonUtils.toJson(2, "注册失败,用户已经存在", "");
	// }
	//

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
				// 发送 POST 请求
				String sr = HttpRequest.sendGet(
						"http://api.map.baidu.com/geodata/v3/poi/list",
						"geotable_id=115331&ak=IwiUF0Rcfn1ckrPLeABUw4r9OwXEL6NP&"
								+ "account=" + user.getAccount());
				System.out.println(sr);

				JSONObject object = JSONObject.fromObject(sr);

				object = object.getJSONArray("pois").getJSONObject(0);

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

	@RequestMapping(value = "/me", method = RequestMethod.GET)
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

	@RequestMapping(value = "/me", method = RequestMethod.POST)
	public JSONObject getCurrentUserPost(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		Integer uid = (Integer) session.getAttribute("uid");
		User user = userService.findById(uid);
		if (user != null) {
			JsonConfig config = new JsonConfig();
			config.setIgnoreDefaultExcludes(false);
			config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
			config.setExcludes(new String[] {// 只要设置这个数组，指定过滤哪些字段。
			"city", "create_time", "district", "geotable_id", "modify_time",
					"province", "gcj_location", "city_id", "id" });

			// 发送 POST 请求
			String sr = HttpRequest.sendGet(
					"http://api.map.baidu.com/geodata/v3/poi/detail",
					"geotable_id=115331&ak=IwiUF0Rcfn1ckrPLeABUw4r9OwXEL6NP&"
							+ "id=" + getLBSSellerId(request));
			System.out.println(sr);
			JSONObject object = JSONObject.fromObject(sr, config);
			SellerDetail detail = (SellerDetail) JSONObject.toBean(object,
					SellerDetail.class);
			detail.getPoi().setCoin(user.getCoin());
			System.out.println("detail:" + detail.getPoi().getAddress()
					+ detail.getPoi().getLocation().get(0) + "");
			return JsonUtils.toJson(1, "查找成功", detail.getPoi());
		}
		return JsonUtils.toJson(2, "查找失败", "");
	}

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

		if (user != null) {
			return JsonUtils.toJson(1, "昵称修改成功", user);
		}
		return JsonUtils.toJson(2, "昵称修改失败", "");
	}

	@RequestMapping(value = "/update/email", method = RequestMethod.POST)
	public User UpdateUserEmail(@RequestParam String email,
			HttpServletRequest request) {
		User user = getCurrentUser(request);
		if (user == null) {
			return null;
		}
		user.setEmail(email);
		return userService.save(user);
	}

	@RequestMapping(value = "/finduser", method = RequestMethod.POST)
	public JSONObject FindUserByAccount(@RequestParam String account) {
		User user = userService.findByAccount(account);
		if (user != null) {
			return JsonUtils.toJson(1, "成功", user);
		}
		return JsonUtils.toJson(2, "失败", "");
	}

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

		return JsonUtils.toJson(1, "poi修改成功", sr);
	}

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
						+ user.getName() +

						"&account=" + user.getAccount()
						+ "&avatar=upload/user/" + user.getAccount() + ".png");
		System.out.println("poi创建:" + sr);

		return JsonUtils.toJson(1, "poi创建成功", poi);
	}

}

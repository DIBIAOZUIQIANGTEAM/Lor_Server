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

import com.dgut.lor.entity.Records;
import com.dgut.lor.entity.User;
import com.dgut.lor.service.IRecordsService;
import com.dgut.lor.service.IUserService;
import com.dgut.lor.util.JsonUtils;

@RestController
@RequestMapping("/api/rec")
public class RecordsAPIController {

	@Autowired
	IUserService userService;

	@Autowired
	IRecordsService recordsService;

	//
	// @Autowired
	// ISubscribeService subscribeService;

	/**
	 * �ҵ���ǰ�û�
	 * 
	 * @param request
	 * @return user
	 */
	@RequestMapping(value = "/me", method = RequestMethod.GET)
	public User getCurrentUser(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		Integer uid = (Integer) session.getAttribute("uid");
		return userService.findById(uid);
	}

	public Records addRecords(User user, String cause, double coin,
			HttpServletRequest request) {
		User me = getCurrentUser(request);
		Records records = new Records();
		records.setCause(cause);
		records.setUser(me);
		records.setCoin(coin);

		records = recordsService.save(records);

		return records;

	}



	@RequestMapping(value ="/records" ,method = RequestMethod.POST)
	public JSONObject getRecordsByUserId(@RequestParam int page, HttpServletRequest request) {
		
		Page<Records> rePage=recordsService.getRecordsByUserId(getCurrentUser(request), page);
		if(rePage!=null){
			return JsonUtils.toJson(1, "成功", rePage);
		}else{
			return JsonUtils.toJson(2, "失败", "");
		}
		 
	}

	@RequestMapping(value = "/records/recharge", method = RequestMethod.POST)
	public Records recharge(@RequestParam double coin,
			@RequestParam String cause, HttpServletRequest request) {

		return addRecords(getCurrentUser(request), cause, coin, request);
	}

	@RequestMapping("/search/{keyword}")
	public Page<User> searchUserWithKeyword(@PathVariable String keyword,
			@RequestParam(defaultValue = "0") int page) {
		return userService.searchUserWithKeyword(keyword, page);
	}

}

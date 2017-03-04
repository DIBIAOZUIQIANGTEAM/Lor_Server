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


/*
 * 消费纪录相关api  
 * entity:Records
 */
@RestController
@RequestMapping("/api/rec")
public class RecordsAPIController {

	@Autowired
	IUserService userService;

	@Autowired
	IRecordsService recordsService;

	@RequestMapping(value = "/me", method = RequestMethod.GET)
	public User getCurrentUser(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		Integer uid = (Integer) session.getAttribute("uid");
		return userService.findById(uid);
	}

	/*
	 * 分页取出消费纪录
	 */
	@RequestMapping(value ="/records" ,method = RequestMethod.POST)
	public JSONObject getRecordsByUserId(@RequestParam int page, HttpServletRequest request) {
		
		Page<Records> rePage=recordsService.getRecordsByUserId(getCurrentUser(request), page);
		if(rePage!=null){
			return JsonUtils.toJson(1, "成功", rePage);
		}else{
			return JsonUtils.toJson(2, "失败", "");
		}
		 
	}

}

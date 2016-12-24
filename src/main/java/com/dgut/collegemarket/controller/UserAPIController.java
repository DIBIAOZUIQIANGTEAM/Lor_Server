package com.dgut.collegemarket.controller;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dgut.collegemarket.entity.Records;
import com.dgut.collegemarket.entity.User;
import com.dgut.collegemarket.service.IRecordsService;
import com.dgut.collegemarket.service.IUserService;


@RestController
@RequestMapping("/api/user")
public class UserAPIController {

	@Autowired
	IUserService userService;
	
	@Autowired
	IRecordsService recordsService;

	@RequestMapping(value = "/hello", method=RequestMethod.GET)
	public @ResponseBody String hello(){
		return "HELLO  CollegeMarket!";
	}
	
	/**
	 * 找到当前用户
	 * @param request
	 * @return user
	 */
	@RequestMapping(value="/me", method=RequestMethod.GET)
	public User getCurrentUser(HttpServletRequest request){
		HttpSession session = request.getSession(true);
		Integer uid = (Integer) session.getAttribute("uid");
		return userService.findById(uid);
	}
		
	/**
	 * 用户上传头像
	 * @param avatar
	 * @param request
	 * @return	user
	 */
	@RequestMapping(value="/update/avatar", method=RequestMethod.POST)
	public void UpdateAvatar(
			MultipartFile avatar,
			HttpServletRequest request){
		if(avatar!=null){
			try{
				String realPath = request.getSession().getServletContext().getRealPath("/WEB-INF/upload/user");
				FileUtils.copyInputStreamToFile(avatar.getInputStream(), new File(realPath,getCurrentUser(request).getId() + ".png"));
				
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 用户修改密码
	 * @param avatar
	 * @param request
	 * @return	user
	 */
	@RequestMapping(value="/update/password", method=RequestMethod.POST)
	public User UpdatePassword(
			@RequestParam String newpassword,
			HttpServletRequest request){
		User user = getCurrentUser(request);
		user.setPasswordHash(newpassword);
		return userService.save(user);
	}

	/**
	 * 用户忘记密码
	 * @param avatar
	 * @param request
	 * @return	user  user/forget/password
	 */
	@RequestMapping(value="/forget/password", method=RequestMethod.POST)
	public User ForgetPassword(
			@RequestParam String account,
			@RequestParam String newpassword){
		
		System.out.println(account);
		System.out.println(newpassword);
		User user = userService.findByAccount(account);
		if(user==null){
			return null;
		}
		user.setPasswordHash(newpassword);
		return userService.save(user);
	}
}

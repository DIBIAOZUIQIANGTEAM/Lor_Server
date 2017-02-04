package com.dgut.lor.controller;

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

import com.dgut.lor.entity.Contact;
import com.dgut.lor.entity.Goods;
import com.dgut.lor.entity.Orders;
import com.dgut.lor.entity.User;
import com.dgut.lor.service.IContactService;
import com.dgut.lor.service.IUserService;


@RestController
@RequestMapping("/api/contact")
public class ContactAPIController {

	@Autowired
	IUserService userService;
	
	@Autowired
	IContactService contactService;
	
	
	/**
	 * �ҵ���ǰ�û�
	 * @param request
	 * @return user
	 */
	public User getCurrentUser(HttpServletRequest request){
		HttpSession session = request.getSession(true);
		Integer uid = (Integer) session.getAttribute("uid");
		return userService.findById(uid);
	}
		
	@RequestMapping(value="/add", method=RequestMethod.POST)
	 	public Contact register(
	 			@RequestParam String name,
				@RequestParam String phone,
	 			@RequestParam String school,
	 			@RequestParam String sushe,
	 			HttpServletRequest request){
	 		
	 		Contact contact = new Contact();
	 		contact.setUser(getCurrentUser(request));
	 		contact.setName(name);
	 		contact.setPhone(phone);
	 		contact.setSchool(school);
	 		contact.setSushe(sushe);
	 		
	 		return contactService.save(contact);
	 }
	
	
	
	@RequestMapping(value = "/my/{page}")
	public Page<Contact> getContactByUserId(@PathVariable int page,HttpServletRequest request) {
		return contactService.getContactByUserId(getCurrentUser(request),page);
	}
	
}

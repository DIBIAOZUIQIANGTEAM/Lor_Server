package com.dgut.lor.controller;

import java.io.File;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dgut.lor.entity.Message;
import com.dgut.lor.entity.Subscriber;
import com.dgut.lor.entity.User;
import com.dgut.lor.service.IMessageService;
import com.dgut.lor.service.IUserService;

@RestController
@RequestMapping("/api/message")
public class MessageAPIController {

	@Autowired
	IUserService userService;

	@Autowired
	IMessageService messageService;

	/**
	 * �ҵ���ǰ�û�
	 * 
	 * @param request
	 * @return user
	 */
	public User getCurrentUser(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		Integer uid = (Integer) session.getAttribute("uid");
		return userService.findById(uid);
	}

	/**
	 * ����һ��˽��
	 * 
	 * @param sender
	 * @param content
	 * @param receiver
	 * @param picture
	 * @return Message
	 */
	@RequestMapping(value = "/send", method = RequestMethod.POST)
	public Message addMessage(
			@RequestParam int receiver_id,
//			@RequestParam Date createDate,
			@RequestParam String content, 
//			MultipartFile picture,
			HttpServletRequest request) {

		
		Message message = new Message();
		message.setReceiver(userService.findById(receiver_id));
		message.setContent(content);
		message.setSender(getCurrentUser(request));
		message = messageService.save(message);

//		if (picture != null) {
//			try {
//				String realPath = request.getSession().getServletContext()
//						.getRealPath("/WEB-INF/upload/message");
//				FileUtils.copyInputStreamToFile(picture.getInputStream(),
//						new File(realPath, message.getId() + ".png"));
//				message.setPicture("upload/message/" + message.getId() + ".png");
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}

		return messageService.save(message);
	}

	public boolean checkExist(User receiver) {
		User user = new User();
		if (receiver.equals(user.getName())) {
			return true;
		} else {
			return false;
		}
	}
	
	@RequestMapping(value ="/all/{page}" ,method = RequestMethod.POST)
	public Page<Message> getSubscribersByUserId(@RequestParam int receiver_id,@PathVariable int page,
			HttpServletRequest request) {
		
		return messageService.getMessageByUserId(getCurrentUser(request),userService.findById(receiver_id), page);
	}

}

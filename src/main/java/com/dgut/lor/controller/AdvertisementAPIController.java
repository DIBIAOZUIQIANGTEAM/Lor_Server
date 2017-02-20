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

import com.dgut.lor.entity.Advertisement;
import com.dgut.lor.entity.User;
import com.dgut.lor.service.IAdvertisementService;


@RestController
@RequestMapping("/api/goods")
public class AdvertisementAPIController {

//	@Autowired
//	IAdvertisementService advertisementService;
//
//	
//	@RequestMapping(value = "/all/advertisement/{page}")
//	public Page<Advertisement> getAdvertisementPage(@PathVariable int page) {
//		return advertisementService.getAdvertisements(page);
//	}
	
}

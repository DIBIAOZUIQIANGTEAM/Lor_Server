package com.dgut.collegemarket.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class APIController {


	@RequestMapping(value = "/hello2", method=RequestMethod.GET)
	public @ResponseBody String hello2(){
		return "HELLO  CollegeMarket2!";
	}
	
	
	
	
	
	
}

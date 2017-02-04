package com.dgut.lor.service;

import java.util.Date;

import org.springframework.data.domain.Page;

import com.dgut.lor.entity.Sign;
import com.dgut.lor.entity.User;


public interface ISignService {
	Sign save(Sign sign);
	Page<Sign> getAllSignsByPage(int pageNum);
	int countTodaySigns(String date);
	int rankingToday(Date todaydate,Date mydate);
	Sign findByUserId(int userId,Date todaydate);
//	Sign findByUserId(int userId,String  todaydate);
}

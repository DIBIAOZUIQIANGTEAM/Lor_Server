package com.dgut.lor.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.dgut.lor.entity.Records;
import com.dgut.lor.entity.User;


public interface IRecordsService {

	 Records save(Records records) ;

	 Page<Records> getRecordsByUserId(User currentUser, int page);
	

}

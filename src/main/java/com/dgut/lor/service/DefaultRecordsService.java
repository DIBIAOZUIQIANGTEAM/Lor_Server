package com.dgut.lor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dgut.lor.entity.Records;
import com.dgut.lor.entity.User;
import com.dgut.lor.repository.IRecordsRepository;


@Component
@Service
@Transactional
public class DefaultRecordsService implements IRecordsService{

	@Autowired
	IRecordsRepository recordsRepo;


	@Override
	public Records save(Records records) {
		return recordsRepo.save(records);
		
	}

	@Override
	public Page<Records> getRecordsByUserId(User currentUser, int page) {
		
		
		Sort sort = new Sort(Direction.DESC, "createDate");
		PageRequest pageRequest = new PageRequest(page, 50, sort);
		
		
		return recordsRepo.getRecordsByUserId(currentUser.getId(),pageRequest);
	}

	
}

package com.dgut.lor.service;

import org.springframework.data.domain.Page;

import com.dgut.lor.entity.Advertisement;
import com.dgut.lor.entity.User;


public interface IAdvertisementService {
	Page<Advertisement> getAdvertisements(int page);
}

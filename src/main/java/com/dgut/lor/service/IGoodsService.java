package com.dgut.lor.service;

import org.springframework.data.domain.Page;

import com.dgut.lor.entity.Goods;
import com.dgut.lor.entity.User;

public interface IGoodsService {
	Goods save(Goods goods);

	Page<Goods> getGoodsPage(int i);

	Goods findOne(int goods_id);
	
}

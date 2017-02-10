package com.dgut.lor.service;

import org.hibernate.mapping.List;
import org.springframework.data.domain.Page;

import com.dgut.lor.entity.RepairGoods;
import com.dgut.lor.entity.User;

public interface IRepairGoodsService {
	RepairGoods save(RepairGoods goods);

	Iterable<RepairGoods> findRepairGoods(String seller_account);
	
}

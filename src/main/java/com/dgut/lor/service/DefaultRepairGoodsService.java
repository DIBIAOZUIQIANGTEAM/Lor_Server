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

import com.dgut.lor.entity.RepairGoods;
import com.dgut.lor.repository.IRepairGoodsRepository;

@Component
@Service
@Transactional
public class DefaultRepairGoodsService implements IRepairGoodsService {

	@Autowired
	IRepairGoodsRepository goodsRepo;

	@Override
	public Iterable<RepairGoods> findRepairGoods(String seller_account) {
	
		return goodsRepo.findRepairGoods(seller_account);
	}


	@Override
	public RepairGoods save(RepairGoods goods) {
		return goodsRepo.save(goods);
	}
	
	@Override
	public void deleteBySellerID(int seller_id) {
		 goodsRepo.deleteBySellerID( seller_id);
	}
}

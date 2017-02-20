package com.dgut.lor.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.dgut.lor.entity.RepairGoods;

@Repository
public interface IRepairGoodsRepository extends PagingAndSortingRepository<RepairGoods, Integer>{

	
	@Query("from RepairGoods repairGoods where repairGoods.seller.account = ?1")
	Iterable<RepairGoods> findRepairGoods(String seller_account);
	
	@Modifying  
	@Query("delete from RepairGoods repairGoods where repairGoods.seller.id= ?1")
	void deleteBySellerID(int seller_id);


}

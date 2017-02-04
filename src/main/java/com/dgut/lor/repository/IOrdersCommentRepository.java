package com.dgut.lor.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.dgut.lor.entity.Admin;
import com.dgut.lor.entity.Goods;
import com.dgut.lor.entity.Orders;
import com.dgut.lor.entity.OrdersComment;
import com.dgut.lor.entity.PostComment;

@Repository
public interface IOrdersCommentRepository extends PagingAndSortingRepository<OrdersComment, Integer>{
	
	@Query("from OrdersComment orderscomment where orderscomment.orders.id = ?1")
	public Page<OrdersComment> findAllByOrdersCommentId(Integer ordersCommentId,Pageable pageable);
	@Query("from OrdersComment orderscomment where orderscomment.orders.goods.id = ?1")
	public Page<OrdersComment> findAllByGoodsId(Integer goodsId,Pageable pageable);
}

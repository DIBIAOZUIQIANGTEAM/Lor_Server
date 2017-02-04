package com.dgut.lor.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.dgut.lor.entity.Admin;
import com.dgut.lor.entity.Goods;
import com.dgut.lor.entity.Likes;
import com.dgut.lor.entity.Likes.Key;

@Repository
public interface ILikeRepository extends PagingAndSortingRepository<Likes, Integer>{

	
	@Query("select count(*) from Likes likes where likes.id.post.id = ?1")
	int countLikes(int postId);
	
	@Query("select count(*) from Likes likes where likes.id = ?1")
	int judgeLikes(Key id);
}

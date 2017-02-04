package com.dgut.lor.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dgut.lor.entity.Likes;
import com.dgut.lor.entity.Post;
import com.dgut.lor.entity.User;
import com.dgut.lor.entity.Likes.Key;


public interface ILikesService {
	public int countLikes(int postId);
	public Likes save(Likes likes);
	public Likes findOne(Key id);
	public void delete(Likes likes);
	public boolean judge(Key id);
}

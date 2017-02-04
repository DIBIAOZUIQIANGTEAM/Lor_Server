package com.dgut.lor.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dgut.lor.entity.Post;
import com.dgut.lor.entity.User;


public interface IPostService {
	public Page<Post> getPosts(Integer page);
	public Post save(Post post);
	public Post findOne(Integer id);

	public Page<Post> findPostPageByUserId(int publishers_id, int page);
}

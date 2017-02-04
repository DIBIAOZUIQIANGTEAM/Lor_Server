package com.dgut.lor.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dgut.lor.entity.Post;
import com.dgut.lor.entity.PostComment;
import com.dgut.lor.entity.User;


public interface IPostCommentService {
	public Page<PostComment> getPostCommentsByPostId(Integer postId,Integer page);
	public PostComment save(PostComment post);
	public PostComment findOne(Integer id);
}

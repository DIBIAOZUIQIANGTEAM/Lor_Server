package com.dgut.lor.service;

import javax.servlet.http.HttpSession;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dgut.lor.entity.Likes;
import com.dgut.lor.entity.Post;
import com.dgut.lor.entity.Likes.Key;
import com.dgut.lor.repository.ILikeRepository;
import com.dgut.lor.repository.IPostRepository;



@Component
@Service
@Transactional
public class DefaultLikesService implements ILikesService {


	@Autowired
	ILikeRepository iLikesResp;

	@Override
	public int countLikes(int postId) {
		return iLikesResp.countLikes(postId);
	}

	@Override
	public Likes save(Likes likes) {
		return iLikesResp.save(likes);
	}

	@Override
	public Likes findOne(Key id) {
		return null;
	}

	@Override
	public void delete(Likes likes) {
		iLikesResp.delete(likes);
	}

	@Override
	public boolean judge(Key id) {
		boolean isLike = false;
		if(iLikesResp.judgeLikes(id)>0){
			isLike = true;
		}
		return isLike;
	
	}

	
	
	

}

package com.dgut.lor.service;

import org.springframework.data.domain.Page;

import com.dgut.lor.entity.User;


public interface IUserService {
	User save(User user);
	void login(String account, String passwordHash);
	User getCurrentUser();
	boolean changePassword(String newPasswordHash);
	void logout();
	User findById(Integer id);
	User findByAccount(String account);
	User findByEmail(String email);
	Page<User> searchUserWithKeyword(String keyword, int page);
}

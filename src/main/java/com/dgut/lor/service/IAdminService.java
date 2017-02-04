package com.dgut.lor.service;

import java.util.List;

import com.dgut.lor.entity.Admin;

public interface IAdminService {
	Admin create(String account, String passwordHash);

	List<Admin> list();
	
	void login(String account, String passwordHash);
	Admin getCurrentAdmin();
	boolean changePassword(String newPasswordHash);
	void logout();
}

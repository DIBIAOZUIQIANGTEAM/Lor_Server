package com.dgut.lor.service;

import org.springframework.data.domain.Page;

import com.dgut.lor.entity.Contact;
import com.dgut.lor.entity.User;


public interface IContactService {
	Contact save(Contact contact);

	Page<Contact> getContactByUserId(User currentUser, int page);

	Contact findOne(int contact_id);

}

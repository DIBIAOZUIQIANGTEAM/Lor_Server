package com.dgut.lor.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.dgut.lor.entity.Admin;

@Repository
public interface IAdminRepository extends PagingAndSortingRepository<Admin, Integer>{

	@Query("from Admin admin where admin.id = ?1")
	Admin getAdimin(Integer id);
}

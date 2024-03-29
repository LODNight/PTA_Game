package com.pta.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.pta.entities.Role;

public interface RoleRepository extends CrudRepository<Role, Integer>{
	
	@Query("from Role where name = 'ROLE_MEMBER' or name = 'ROLE_ADMIN' order by id desc")
	public List<Role> findAllNew();
	
}

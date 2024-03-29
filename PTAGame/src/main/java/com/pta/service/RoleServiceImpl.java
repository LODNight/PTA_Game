package com.pta.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pta.entities.Role;
import com.pta.repositories.RoleRepository;

@Service
public class RoleServiceImpl implements RoleService{
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public Iterable<Role> findAll() {
		return roleRepository.findAllNew();
	}
	


}

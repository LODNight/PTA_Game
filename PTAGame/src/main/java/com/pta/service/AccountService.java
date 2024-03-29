package com.pta.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.pta.entities.Account;

public interface AccountService extends UserDetailsService{

	public Iterable<Account> findAll();
	public Account find(int id);
	public Account findByEmail(String email);
	public Iterable<Account> findByRoleAdminMember();
	public Iterable<Account> findByRoleAdmin();
	public Iterable<Account> findByRoleMember();
	
	
	// LOGIN
	public Account login(String email, String password);
	// CRUD
	public boolean save(Account account);
	public boolean edit(int id);
	public boolean delete(int id);
	// Find Pasword
	public String findPassword(int id);
	
	
}

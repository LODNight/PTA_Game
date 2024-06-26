package com.pta.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.pta.entities.Account;
import com.pta.repositories.AccountRepository;

@Service
public class AccountServiceImpl implements AccountService{
	@Autowired
	private AccountRepository accountRepository;
	
	@Override
	public Iterable<Account> findAll() {
		return accountRepository.findAll();
	}

	@Override
	public Account find(int id) {
		return accountRepository.findById(id).get();
	}
	
	@Override
	public Account findByEmail(String email) {
		return accountRepository.findByEmail(email);
	}
	
	@Override
	public Account login(String email, String password) {
		try {
			return accountRepository.login(email, password, 1);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public boolean save(Account account) {
		try {
			accountRepository.save(account);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean edit(int id) {
		return false;
	}

	@Override
	public boolean delete(int id) {
		try {
			accountRepository.delete(find(id));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public String findPassword(int id) {
		return accountRepository.findPassword(id);
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Account account = accountRepository.findByEmail(email);
		if (account == null) {
			throw new UsernameNotFoundException("Email Not Found");
		} else {
			List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
			authorities.add(new SimpleGrantedAuthority(account.getRole().getName()));
			return new User(email, account.getPassword(), authorities);
		}
	}

	@Override
	public Iterable<Account> findByRoleAdmin() {
		return accountRepository.findByAdmin();
	}

	@Override
	public Iterable<Account> findByRoleMember() {
		return accountRepository.findByMember();
	}

	@Override
	public Iterable<Account> findByRoleAdminMember() {
		return accountRepository.findAdminMember();
	}


}

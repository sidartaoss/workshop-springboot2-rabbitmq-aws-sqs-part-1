package com.rollingstone.spring.service;

import org.springframework.data.domain.Page;

import com.rollingstone.spring.model.Account;
import com.rollingstone.spring.model.AccountDTO;

public interface AccountService {

	Account save(Account account);
	
	void update(Account account);
	
	void delete(long id);
	
	AccountDTO getAccount(long id);
	
	Page<Account> getAccountsByPage(Integer pageNumber, Integer pageSize);
}

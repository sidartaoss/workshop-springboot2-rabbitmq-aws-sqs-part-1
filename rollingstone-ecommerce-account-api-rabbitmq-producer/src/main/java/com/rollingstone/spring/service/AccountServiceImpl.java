package com.rollingstone.spring.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.rollingstone.spring.dao.AccountDaoRepository;
import com.rollingstone.spring.model.Account;
import com.rollingstone.spring.model.AccountDTO;

@Service
public class AccountServiceImpl implements AccountService {
	
	private static final Logger LOG = LoggerFactory.getLogger(AccountServiceImpl.class);

	private AccountDaoRepository accountDaoRepository;

	public AccountServiceImpl(AccountDaoRepository accountDaoRepository) {
		this.accountDaoRepository = accountDaoRepository;
	}

	@Override
	public Account save(Account account) {
		return this.accountDaoRepository.save(account);
	}

	@Override
	public void update(Account account) {
		this.accountDaoRepository.save(account);
	}

	@Override
	public void delete(long id) {
		this.accountDaoRepository.deleteById(id);
	}
	
	@Override
	public AccountDTO getAccount(long id) {
		LOG.info("Inside getAccount method");
		AccountDTO accountDTO = this.accountDaoRepository.getAccountByID(id);
		if (accountDTO == null) {
			LOG.info("accountDTO is null");
		} else {
			LOG.info("accountDTO is not null");
		}
		return accountDTO;
	}
	
	@Override
	public Page<Account> getAccountsByPage(Integer pageNumber, Integer pageSize) {
		Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("accountNumber").descending());
		return this.accountDaoRepository.findAll(pageable);
	}

}

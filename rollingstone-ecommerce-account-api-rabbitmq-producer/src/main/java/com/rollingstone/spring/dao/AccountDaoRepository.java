package com.rollingstone.spring.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.rollingstone.spring.model.Account;
import com.rollingstone.spring.model.AccountDTO;

public interface AccountDaoRepository extends PagingAndSortingRepository<Account, Long> {

	@Query(name = "AccountDaoRepository.getAccountByID", nativeQuery = true)
	AccountDTO getAccountByID(@Param("id") long id);
	
	Page<Account> findAll(Pageable pageable);
}

package com.rollingstone.events;

import org.springframework.context.ApplicationEvent;

import com.rollingstone.spring.model.AccountDTO;

public class AccountEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;
	
	private String eventType;
	private AccountDTO account;

	public AccountEvent(Object source, String eventType, AccountDTO account) {
		super(source);
		this.eventType = eventType;
		this.account = account;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public AccountDTO getAccount() {
		return account;
	}

	public void setAccount(AccountDTO account) {
		this.account = account;
	}

	@Override
	public String toString() {
		return "AccountEvent [eventType=" + eventType + ", account=" + account + "]";
	}

}

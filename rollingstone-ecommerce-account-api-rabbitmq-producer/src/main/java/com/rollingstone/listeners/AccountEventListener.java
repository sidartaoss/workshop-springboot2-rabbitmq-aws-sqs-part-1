package com.rollingstone.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.rollingstone.events.AccountEvent;

@Component
public class AccountEventListener {

	private static final Logger LOG = LoggerFactory.getLogger(AccountEventListener.class);
	
	private RabbitTemplate rabbitTemplate;
	private Exchange exchange;
	private AccountMessageSender accountMessageSender;

	@Value("${account.exchange.name}")
	private String accountExchangeName;
	@Value("${account.queue.name}")
	private String accountQueueName;
	@Value("${account.routing.key}")
	private String accountRoutingKey;
	
	public AccountEventListener(RabbitTemplate rabbitTemplate, Exchange exchange,
			AccountMessageSender accountMessageSender) {
		super();
		this.rabbitTemplate = rabbitTemplate;
		this.exchange = exchange;
		this.accountMessageSender = accountMessageSender;
	}
	
	@EventListener
	public void onApplicationEvent(AccountEvent accountEvent) {
		LOG.info("Received Account Event: {} ", accountEvent.getEventType());
		LOG.info("Received Account from Account Event: {} ", accountEvent.getAccount().toString());
		LOG.info("Account created with ID: {} and First Name: {} and Last Name: {} ", 
				accountEvent.getAccount().getAccountNumber(),
				accountEvent.getAccount().getFirstName(),
				accountEvent.getAccount().getLastName());
		
		this.accountMessageSender.sendMessage(rabbitTemplate, accountExchangeName, accountRoutingKey, accountEvent.getAccount());
		
	}
	
}

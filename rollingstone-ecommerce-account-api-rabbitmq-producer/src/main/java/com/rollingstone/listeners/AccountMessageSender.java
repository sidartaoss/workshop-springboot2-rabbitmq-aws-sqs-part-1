package com.rollingstone.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class AccountMessageSender {

	private static final Logger LOG = LoggerFactory.getLogger(AccountMessageSender.class);
	
	public void sendMessage(RabbitTemplate rabbitTemplate, String accountExchange, String accountRoutingKey, Object accountData) {
		LOG.info("Sending message to the Account Queue using accountRoutingKey {}, Message = {} ", accountRoutingKey, accountData);
		
		rabbitTemplate.convertAndSend(accountExchange, accountRoutingKey, accountData);
		
		LOG.info("The Account Message was sent");
	}
}

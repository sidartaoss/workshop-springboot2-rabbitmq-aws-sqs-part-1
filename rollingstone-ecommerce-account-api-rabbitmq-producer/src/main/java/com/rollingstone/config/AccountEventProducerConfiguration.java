package com.rollingstone.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

@Configuration
public class AccountEventProducerConfiguration {

	@Value("${account.exchange.name}")
	private String accountExchangeName;

	@Value("${account.queue.name}")
	private String accountQueueName;

	@Value("${account.routing.key}")
	private String accountRoutingKeyName;

	@Bean
	public DirectExchange getAccountDirectExchange() {
		return new DirectExchange(accountExchangeName);
	}

	@Bean
	public Queue getAccountQueue() {
		return new Queue(accountQueueName);
	}

	@Bean
	public Binding bindAccountQueueForExchange() {
		return BindingBuilder.bind(getAccountQueue()).to(getAccountDirectExchange()).with(accountRoutingKeyName);
	}

	@Bean
	public Jackson2JsonMessageConverter producerJackson2JsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	@Bean
	public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
		return new MappingJackson2MessageConverter();
	}

	@Bean
	public DefaultMessageHandlerMethodFactory messageHandlerMethodFactory() {
		DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
		factory.setMessageConverter(consumerJackson2MessageConverter());
		return factory;
	}

	public void configureRabbitListeners(final RabbitListenerEndpointRegistrar registrar) {
		registrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
	}
}

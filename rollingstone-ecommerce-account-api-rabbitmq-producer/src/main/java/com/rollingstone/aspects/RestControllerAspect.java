package com.rollingstone.aspects;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;

@Aspect
@Component
public class RestControllerAspect {

	private static final Logger LOG = LoggerFactory.getLogger(RestControllerAspect.class);
	
	private Counter accountCreatedCounter = Metrics.counter("com.rollingstone.account.created");
	
	@Before("execution(public * com.rollingstone.spring.controller.*Controller.*(..))")
	public void generalAllMethodAspect() {
		LOG.info("All method calls invoke this general aspect method");
	}
	
	@AfterReturning("execution(public * com.rollingstone.spring.controller.*Controller.createAccount*(..))")
	public void getCalledOnAccountSave() {
		LOG.info("This Aspect method is called only on Account Save");
		accountCreatedCounter.increment();
	}
}

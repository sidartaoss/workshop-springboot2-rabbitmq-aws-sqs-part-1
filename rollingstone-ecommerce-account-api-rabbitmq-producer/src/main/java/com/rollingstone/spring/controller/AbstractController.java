package com.rollingstone.spring.controller;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import com.rollingstone.exceptions.HTTP400Exception;
import com.rollingstone.exceptions.HTTP404Exception;
import com.rollingstone.exceptions.RestAPIExceptionInfo;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;

public abstract class AbstractController implements ApplicationEventPublisherAware {

	protected static final Logger LOG = LoggerFactory.getLogger(AbstractController.class);

	protected ApplicationEventPublisher eventPublisher;
	protected static final String DEFAULT_PAGE_SIZE = "20";
	protected static final String DEFAULT_PAGE_NUMBER = "0";

	Counter http400ExceptionCounter = Metrics.counter("com.rollingstone.AccountController.HTTP400");

	Counter http404ExceptionCounter = Metrics.counter("com.rollingstone.AccountController.HTTP404");

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(HTTP400Exception.class)
	public @ResponseBody RestAPIExceptionInfo handleBadRequestException(HTTP400Exception exception, WebRequest request,
			HttpServletResponse response) {
		LOG.info("Received Bad Request Exception Info: {}", exception.getLocalizedMessage());

		this.http400ExceptionCounter.increment();

		return new RestAPIExceptionInfo(exception.getLocalizedMessage(),
				"This request did not have the correct parameters");

	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(HTTP404Exception.class)
	public @ResponseBody RestAPIExceptionInfo handleResourceNotFoundException(HTTP404Exception exception,
			WebRequest request, HttpServletResponse response) {
		LOG.info("Received Resource Not Found Exception Info: {}", exception.getLocalizedMessage());

		this.http404ExceptionCounter.increment();

		return new RestAPIExceptionInfo(exception.getLocalizedMessage(), "The requested resource was not found");

	}
	
	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher eventPublisher) {
		this.eventPublisher = eventPublisher;
	}
	
	public static <T> T checkResourceFound(final T resource) {
		if (resource == null) {
			throw new HTTP404Exception("Resource not found");
		}
		return resource;
	}
}

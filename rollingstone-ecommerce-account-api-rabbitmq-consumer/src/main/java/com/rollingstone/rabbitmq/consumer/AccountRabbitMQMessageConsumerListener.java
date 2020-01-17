package com.rollingstone.rabbitmq.consumer;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.rollingstone.config.ApplicationConstant;
import com.rollingstone.model.AccountDTO;

@Service
public class AccountRabbitMQMessageConsumerListener {

	private static final Logger LOG = LoggerFactory.getLogger(AccountRabbitMQMessageConsumerListener.class);
	
	@Autowired
	private JavaMailSender sender;
	
	@Value("${spring.mail.username}")
	private String emailReceiver;
	
	@RabbitListener(queues = "${account.queue.name}")
	public void receiveMessageForApi(final AccountDTO accountDTO) {
		
		LOG.info("Received AccountDTO message: {} ", accountDTO.toString());
		
		try {
			this.sendEmail(accountDTO);
		} catch (HttpClientErrorException e) {
			if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
				LOG.error("Error happened sending email");
				try {
					Thread.sleep(ApplicationConstant.MESSAGE_RETRY_DELAY);
				} catch (InterruptedException e1) {
					LOG.error("Error: {}. Go ahead. ", e1.getLocalizedMessage());
				}
				LOG.info("Throwing exception so that message will be requeued in the queue.");
				throw new RuntimeException();
			} else {
				throw new AmqpRejectAndDontRequeueException(e);
			}
		}
		
		LOG.info("Email Sent");
	}

	public String sendEmail(AccountDTO accountDTO) {
		MimeMessage mimeMessage = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
		

		try {
			helper.setTo(this.emailReceiver);
			helper.setText("Dear " + accountDTO.getAccountName() + ",\n\n Your account has been created "
					+ "with the Account Number " + accountDTO.getAccountNumber() + ".");
			helper.setSubject("account created");
		} catch (MessagingException e) {
			LOG.error("Error sending email: {} ", e.getLocalizedMessage());
			return "Error sending email";
		}
			
		this.sender.send(mimeMessage);
		return "Email Sent Successfully";

	}
}

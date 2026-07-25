package com.waseel.eligibility;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

import com.waseel.eligibility.client.portal.PortalSwitchClient;
import com.waseel.eligibility.client.portal.model.TransactionWrapper;
import com.waseel.eligibility.client.settings.SettingsServiceClient;
import com.waseel.eligibility.entity.WslGeninfo;
import com.waseel.eligibility.model.PortalSettings;
import com.waseel.eligibility.repository.ClaimRepository;
import com.waseel.eligibility.service.EligibilityGenerator;
import com.waseel.eligibility.service.EligibilityResponseHandler;

@RabbitListener(queues = "eligibility-queue")
public class EligibilityTaskReceiver {

	private static final Logger logger = LoggerFactory.getLogger(EligibilityTaskReceiver.class);

	@Autowired
	ClaimRepository claimRepo;
	@Autowired
	EligibilityGenerator eligibilityGenerator;
	@Autowired
	PortalSwitchClient portalClient;
	@Autowired
	EligibilityResponseHandler eligibilityResponseHandler;
	@Autowired
	AmqpTemplate amqpTemplate;
	@Autowired
	private AmqpAdmin amqpAdmin;
	@Autowired
	private SettingsServiceClient settingsServiceClient;

	@RabbitHandler
	public void receive(Long claimid) throws InterruptedException {
		logger.info("recieved claim [" + claimid + "] for eligibility.");
		Optional<WslGeninfo> claim = claimRepo.findById(claimid);
		TransactionWrapper request = null;
		TransactionWrapper response = null;
		
		if (claim.isPresent()) {
			PortalSettings portalUser = null;
			
			try {
				portalUser = settingsServiceClient.getPortalUser(claim.get().getProviderid());
				if (portalUser == null) {
					throw new IllegalStateException("PortalUser is Not Fetched from Settings Service!");
				}
				request = eligibilityGenerator.generate(claim.get(), portalUser);
				logger.info("request prepared to be sent to portal: ", request);
				response = portalClient.send(request);
				logger.info("response recieved from portal: ", response);
			} catch (Exception e) {
				logger.error("Eligibility submission failed", e);
				throw new AmqpRejectAndDontRequeueException(e.getMessage());
			} finally {
				String statusAndDesc = eligibilityResponseHandler.handleResponse(claim.get(), response);
				createTopicQueue("eligibility");
				amqpTemplate.convertAndSend("eligibility", claim.get().getProviderid(), claimid + ":" + statusAndDesc);
			}
		} else {
			logger.error("Could not find claim with id [" + claimid+"]");
		}
	}

	private void createTopicQueue(String topic) {
		Exchange ex = ExchangeBuilder.topicExchange(topic).durable(true).build();
		amqpAdmin.declareExchange(ex);
	}
}

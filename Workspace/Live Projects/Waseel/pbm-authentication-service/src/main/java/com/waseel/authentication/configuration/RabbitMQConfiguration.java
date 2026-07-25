package com.waseel.authentication.configuration;

import org.springframework.amqp.core.Queue;


//@Configuration
public class RabbitMQConfiguration {
	//@Bean
	public Queue auditTrailQueue() {
		return new Queue("audit-trail-queue");
	}
}

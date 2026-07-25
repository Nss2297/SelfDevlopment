package com.waseel.eligibility.configuration;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.waseel.eligibility.EligibilityTaskReceiver;

@Configuration
public class RabbitMQConfiguration {
	
	@Bean
	public Queue eligibilityQueue() {
		return new Queue("eligibility-queue");
	}
	
	@Bean
	public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
			ConnectionFactory rabbitConnectionFactory) {
		SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
		factory.setConnectionFactory(rabbitConnectionFactory);
//		factory.setAdviceChain(retryInterceptor());
		factory.setPrefetchCount(1);
		return factory;
	}
	
	@SuppressWarnings("unused")
	private static class ReceiverConfig {

		@Bean
		public EligibilityTaskReceiver eligibilityReceiver() {
			return new EligibilityTaskReceiver();
		}
	}
	
}
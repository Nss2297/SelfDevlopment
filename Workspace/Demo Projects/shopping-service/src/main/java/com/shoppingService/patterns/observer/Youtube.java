package com.shoppingService.patterns.observer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Youtube {

	public static void main(String[] args) {
		Channel channel = new Channel("Self learning");

		Subscriber subscriber1 = new Subscriber("subscriber1");
		Subscriber subscriber2 = new Subscriber("subscriber2");
		Subscriber subscriber3 = new Subscriber("subscriber3");
		Subscriber subscriber4 = new Subscriber("subscriber4");
		Subscriber subscriber5 = new Subscriber("subscriber5");
		Subscriber subscriber6 = new Subscriber("subscriber6");

		subscriber1.subscribeChannel(channel);
		subscriber2.subscribeChannel(channel);
		subscriber3.subscribeChannel(channel);
		subscriber4.subscribeChannel(channel);
		subscriber5.subscribeChannel(channel);
		subscriber6.subscribeChannel(channel);

		channel.subscribers(subscriber1);
		channel.subscribers(subscriber2);
		channel.subscribers(subscriber3);
		channel.subscribers(subscriber4);
		channel.subscribers(subscriber5);
		channel.subscribers(subscriber6);

		channel.uploadVideo("Design Patterns");
		channel.unsbuscribeChannel(subscriber1);
		log.info("");
		channel.uploadVideo("Composition");
		log.info("");
		log.info("{}", subscriber1.getSubscriberName());
		log.info("{}", subscriber1.getChannel().getChannelName());
		log.info("{}", subscriber1.getChannel().getTitle());
	}
}

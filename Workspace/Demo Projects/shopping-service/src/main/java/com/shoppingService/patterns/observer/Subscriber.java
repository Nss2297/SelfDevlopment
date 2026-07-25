package com.shoppingService.patterns.observer;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@ToString
public class Subscriber implements Observer {

	private String subscriberName;
	private Channel channel;

//	public void notifyUser(String videoName) {
//	log.info("Hello, {} {} has uploaded video on {}.", this.subscriberName, this.channel.getChannelName(),
//			videoName);
//	}
	@Override
	public void notifyUser(String videoName) {
		log.info("Hello, {} {} has uploaded video on {}.", this.subscriberName, this.channel.getChannelName(),
				videoName);
	}

	public Subscriber(String subscriberName) {
		super();
		this.subscriberName = subscriberName;
	}


	@Override
	public void subscribeChannel(Channel channel) {
		this.channel = channel;
	}

//	public void subscribeChannel(Channel channel) {
//		this.channel = channel;
//	}
}

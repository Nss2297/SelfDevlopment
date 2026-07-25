package com.shoppingService.patterns.observer;

public interface Observer {
	void notifyUser(String videoName);

	void subscribeChannel(Channel channel);
}

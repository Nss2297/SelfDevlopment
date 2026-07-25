package com.shoppingService.patterns.observer;

public interface Subject {

	void subscribers(Subscriber subscriber);

	void unsbuscribeChannel(Subscriber subscriber);

	void uploadVideo(String title);

	void notifySubscribers(String title);
}

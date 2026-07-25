package com.shoppingService.patterns.observer;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Channel implements Subject {

	private String channelName;
	private String title;
	private List<Subscriber> subscribers = new ArrayList<>();

//	public void subscribers(Subscriber subscriber) {
//		this.subscribers.add(subscriber);
//	}
//
//	public void unsbuscribeChannel(Subscriber subscriber) {
//		this.subscribers.remove(subscriber);
//	}
//
//	public void uploadVideo(String title) {
//		this.title = title;
//		notifySubscribers(title);
//	}
//
//	public void notifySubscribers(String videoName) {
//		for (Subscriber subscriber : subscribers) {
//			subscriber.notifyUser(videoName);
//		}
//	}

	public Channel(String channelName) {
		super();
		this.channelName = channelName;
	}

	@Override
	public void subscribers(Subscriber subscriber) {
		this.subscribers.add(subscriber);
	}

	@Override
	public void unsbuscribeChannel(Subscriber subscriber) {
		this.subscribers.remove(subscriber);
	}

	@Override
	public void uploadVideo(String title) {
		this.title = title;
		notifySubscribers(title);
	}

	@Override
	public void notifySubscribers(String videoName) {
		for (Subscriber subscriber : subscribers) {
			subscriber.notifyUser(videoName);
		}
	}

}

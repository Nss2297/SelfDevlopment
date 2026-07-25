package com.shoppingService.interview.javalogical;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QueueUtilizingArray {

	private static final Logger log = LoggerFactory.getLogger(QueueUtilizingArray.class);

	private Object[] queue;
	private int frontIndex;
	private int rearIndex;
	private int capacity;

	public QueueUtilizingArray(int capacity) {
		super();
		this.capacity = capacity;
		this.queue = new Object[capacity];
		this.frontIndex = -1;
		this.rearIndex = -1;
	}

	public void enqueue(Object element) {
		if (!isFull()) {
			frontIndex = frontIndex != 0 ? ++frontIndex : frontIndex;
			++rearIndex;
			queue[rearIndex] = element;
		}
	}

	public void dequeue() {
		if (!isEmpty()) {
			var element = queue[frontIndex];
			queue[frontIndex] = null;
			++frontIndex;
			log.info("Dequeue performed on the queue, [{}] element removed.", element);
		}
	}

	public void listQueueElements() {
		if (!isEmpty()) {
			for (int index = frontIndex; index <= rearIndex; index++) {
				log.info("[{}]", queue[index]);
			}
		}
	}

	boolean isFull() {
		if (null != queue && rearIndex == capacity - 1) {
			log.info("Queue is full.");
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	boolean isEmpty() {
		if (null == queue || (-1 == frontIndex && -1 == rearIndex)) {
			log.info("Queue is empty.");
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	public static void main(String[] args) {
		log.info("Implementing queue utilizing Array.");
		log.info("====================================");
		QueueUtilizingArray queue = new QueueUtilizingArray(4);
		log.info("Adding elements in the queue.");
		queue.enqueue("1");
		queue.enqueue("b");
		queue.enqueue("3");
		queue.enqueue("d");
		log.info("====================================");
		log.info("Listing all elements in the queue.");
		queue.listQueueElements();
		log.info("====================================");
		log.info("Removing elements in the queue.");
		queue.dequeue();
		queue.dequeue();
		queue.dequeue();
		log.info("====================================");
		log.info("Listing all elements in the queue.");
		queue.listQueueElements();
	}
}

package com.shoppingService.collections;

public class QueueByArray {

	private int capacity;
	private int front;
	private int rear;
	private int queueArray[];
	private int currentSize;

	public QueueByArray(int capacity) {
		super();
		this.capacity = capacity;
		this.front = -1;
		this.rear = -1;
		this.queueArray = new int[capacity];
		this.currentSize = 0;
	}

	// underflow
	public boolean queueIsEmpty() {
		return (front == -1 && rear == -1);
	}

	// overflow
	public boolean queueIsFull() {
		return (rear == (queueArray.length - 1));
	}

	// enqueue
	public void enqueue(int element) {
		if (queueIsFull()) {
			System.out.println("Queue is full.");
			return;
		} else {
			if (rear == -1 && front == -1) {
				rear = 0;
				front = 0;
				queueArray[rear] = element;
				currentSize = currentSize + 1;
			} else {
				queueArray[++rear] = element;
			}
		}
	}

	// dequeue
	public int dequeue(int element) {
		if (queueIsEmpty()) {
			System.out.println("Queue is empty.");
			return -1;
		} else {
			if (front == capacity - 1) {
				front = 0;
			}
			int result = queueArray[front++];
			if (front == capacity) {
				front = 0;
			}
			currentSize--;
			return result;
		}
	}

	public static void main(String args[]) {
		QueueByArray queueByArray = new QueueByArray(3);
		queueByArray.enqueue(0);
		queueByArray.enqueue(1);
		queueByArray.enqueue(2);
		queueByArray.dequeue(0);
		queueByArray.dequeue(0);
		queueByArray.dequeue(0);
		for (int element : queueByArray.queueArray) {
			System.out.println(element);
		}
	}
}

package com.shoppingService.collections;

public class StackByArray {

	private int capacity;
	private int top;
	private int stackArray[];
	
	public StackByArray(int capacity) {
		this.capacity = capacity;
		this.top = -1;
		this.stackArray = new int[capacity];
	}

	//Is empty
	public boolean stackIsEmpty() {
		return (top==-1);
	}
	
	//Is full
	public boolean stackIsFull() {
		return (top==stackArray.length-1);
	}
	
	//Push 
	public void pushElementInStack(int element) {
		if(stackIsFull()) {
			System.out.println("Stack is full.");
			return;
		}else {
			int index =top+1; 
			stackArray[index]=element;
			this.top=index;
		}
	}
	
	//pop
	public int popElementFromStack() {
		if(stackIsEmpty()) {
			System.out.println("Stack is empty.");
			return -1;
		}else {
			return stackArray[top--];
		}
	}
	
	//peek
	public int peekStackEelement() {
		return stackArray[top];
	}
	
public static void main(String args[]) {
	StackByArray stack = new StackByArray(5);
	stack.pushElementInStack(0);
	stack.pushElementInStack(1);
	stack.pushElementInStack(2);
	stack.pushElementInStack(3);
	stack.pushElementInStack(4);
	
	System.out.println("Stack elements");
	for (int element : stack.stackArray) {
		System.out.println(element);
	}
	System.out.println("-----------------------------------------------");
	System.out.println("Top element:- " + stack.peekStackEelement());
	System.out.println("Pop Stack elements");
	stack.popElementFromStack();
	stack.popElementFromStack();
	stack.popElementFromStack();
	stack.popElementFromStack();
	stack.popElementFromStack();
}
}

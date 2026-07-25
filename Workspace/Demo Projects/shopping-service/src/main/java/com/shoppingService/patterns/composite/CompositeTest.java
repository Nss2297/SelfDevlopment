package com.shoppingService.patterns.composite;

import com.shoppingService.patterns.composite.ComputerParts.Component;
import com.shoppingService.patterns.composite.ComputerParts.Composite;

public class CompositeTest {
	public static void main(String[] args) {
		ComputerParts computerParts = new ComputerParts();
		Component cpu = computerParts.new Leaf("CPU", 1000);
		Component ram = computerParts.new Leaf("RAM", 2000);
		Component mouse = computerParts.new Leaf("Mouse", 3000);
		Component monitor = computerParts.new Leaf("Monitor", 4000);

		Composite hd = computerParts.new Composite("Hard Drive");
		Composite mb = computerParts.new Composite("Mother Board");
		mb.addComponent(cpu);
		mb.addComponent(ram);
		Composite cabinet = computerParts.new Composite("Cabinet");
		cabinet.addComponent(hd);
		cabinet.addComponent(mb);
		Composite peripheral = computerParts.new Composite("Peri");
		peripheral.addComponent(mouse);
		peripheral.addComponent(monitor);
		Composite pc = computerParts.new Composite("PC");
		pc.addComponent(peripheral);
		pc.addComponent(cabinet);

		pc.showPrice();
		System.out.println("");
		mb.showPrice();
		System.out.println("");
		cabinet.showPrice();
		System.out.println("");
		peripheral.showPrice();
	}
}

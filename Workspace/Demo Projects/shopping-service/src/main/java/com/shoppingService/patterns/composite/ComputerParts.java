package com.shoppingService.patterns.composite;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ComputerParts {

	interface Component {
		void showPrice();
	}

	@AllArgsConstructor
	class Leaf implements Component {
		String name;
		int price;

		@Override
		public void showPrice() {
			log.info("{} : {}", name, price);
		}
	}

	class Composite implements Component {
		String name;
		List<Component> components = new ArrayList<>();

		public Composite(String name) {
			super();
			this.name = name;
		}

		public void addComponent(Component component) {
			components.add(component);
		}

		@Override
		public void showPrice() {
			log.info("{}", name);
			components.stream().forEach(Component::showPrice);
		}

	}
}

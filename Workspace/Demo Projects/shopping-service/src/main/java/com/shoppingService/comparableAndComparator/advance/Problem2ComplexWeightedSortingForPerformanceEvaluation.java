package com.shoppingService.comparableAndComparator.advance;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class Problem2ComplexWeightedSortingForPerformanceEvaluation {
	@Getter
	@AllArgsConstructor
	private static class Developer {
		private String name;
		private Double codeQuality;
		private Double deliverySpeed;
		private Double teamWork;

		public String toString() {
			return this.name + "(" + this.codeQuality + "," + this.deliverySpeed + "," + this.teamWork + ")";
		}
	}

	public static void main(String[] args) {
		List<Developer> devs = Arrays.asList(new Developer("Alice", 9.0, 8.5, 9.5), new Developer("Bob", 9.5, 7.0, 8.0),
				new Developer("Charlie", 8.0, 9.0, 9.0));
		System.out.println(devs);
		devs.sort(Comparator
				.<Developer>comparingDouble(
						d -> 0.6 * d.getCodeQuality() + 0.3 * d.getDeliverySpeed() + 0.1 * d.getTeamWork())
				.reversed().thenComparing(d -> d.getName()));
		System.out.println(devs);
	}
}

package com.shoppingService.comparableAndComparator.intermidiate;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class SortFlightsByDepartureDurationAscPriceAsc {
	@Getter
	@AllArgsConstructor
	private static class Flight {
		private String name;
		private LocalTime date;
		private Integer price;

		public String toString() {
			return this.name + "(" + this.date + ")" + this.price;
		}
	}

	public static void main(String[] args) {
		List<Flight> flights = Arrays.asList(new Flight("DEL", LocalTime.of(8, 0), 6000),
				new Flight("DEL", LocalTime.of(8, 0), 6500), new Flight("DEL", LocalTime.of(6, 0), 5000));
		System.out.println(flights);
		flights.sort(Comparator.comparing(Flight::getDate).thenComparing(Flight::getPrice));
		System.out.println(flights);
	}
}

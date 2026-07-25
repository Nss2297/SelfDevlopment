package com.shoppingService.comparableAndComparator.advance;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class Problem4HierarchicalSortingCustomTieBreak {
	@Getter
	@AllArgsConstructor
	private static class Task {
		private String project;
		private int priority;
		private LocalDate dueDate;

		public String toString() {
			return this.project + "(" + this.priority + ", " + this.dueDate + ")";
		}
	}

	public static void main(String[] args) {
		List<Task> tasks = Arrays.asList(new Task("Alpha", 2, LocalDate.of(2025, 10, 10)),
				new Task("Alpha", 2, LocalDate.of(2025, 10, 5)), new Task("Beta", 1, LocalDate.of(2025, 9, 30)),
				new Task("Alpha", 1, LocalDate.of(2025, 10, 12)));
		System.out.println(tasks);
		tasks.sort(Comparator.comparing(Task::getProject).thenComparing(Task::getPriority)
				.thenComparing(Task::getDueDate, Comparator.reverseOrder()));
		System.out.println(tasks);
	}
}

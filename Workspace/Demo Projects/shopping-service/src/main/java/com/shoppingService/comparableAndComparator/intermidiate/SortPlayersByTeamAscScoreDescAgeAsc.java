package com.shoppingService.comparableAndComparator.intermidiate;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class SortPlayersByTeamAscScoreDescAgeAsc {
	@Getter
	@AllArgsConstructor
	private static class Player {
		private String group;
		private String team;
		private Integer score;
		private Integer age;

		public String toString() {
			return this.group + "-" + this.team + "-" + this.score + "-" + this.age;
		}
	}

	public static void main(String[] args) {
		List<Player> players = Arrays.asList(new Player("A", "India", 50, 29), new Player("B", "India", 70, 25),
				new Player("C", "Australia", 80, 30), new Player("D", "Australia", 60, 27));
		System.out.println(players);
		players.sort(Comparator.comparing(Player::getTeam)
				.thenComparing(Comparator.comparingInt(Player::getScore).reversed()).thenComparing(Player::getAge));
		System.out.println(players);
	}
}

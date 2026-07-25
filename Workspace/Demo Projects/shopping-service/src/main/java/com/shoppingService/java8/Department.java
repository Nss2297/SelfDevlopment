package com.shoppingService.java8;

import java.util.List;

public class Department {

    private Long id;
    private String name;

    private List<Integer> bonuses;

    private int bonus;

    public Department(Long id, String name, List<Integer> bonuses,int bonus) {
        this.id = id;
        this.name = name;
        this.bonuses = bonuses;
        this.bonus = bonus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getBonuses() {
        return bonuses;
    }

    public void setBonuses(List<Integer> bonuses) {
        this.bonuses = bonuses;
    }

    public int getBonus() {
        return bonus;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }
}

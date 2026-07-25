package com.shoppingService.SystemDesign.LLD.DesignPatterns.Structural.Decorator;

public class VeggiesTopping extends ToppingDecorator {
    public VeggiesTopping(BasePizza basePizza) {
        super(basePizza);
    }

    @Override
    public String getDescription() {
        return basePizza.getDescription() + " + Veggies";
    }

    @Override
    public double getCost() {
        return basePizza.getCost() + 30;
    }
}

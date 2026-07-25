package com.shoppingService.SystemDesign.LLD.DesignPatterns.Structural.Decorator;

public class PepperoniTopping extends ToppingDecorator{
    public PepperoniTopping(BasePizza basePizza) {
        super(basePizza);
    }

    @Override
    public String getDescription() {
        return basePizza.getDescription() + " + Pepperoni";
    }

    @Override
    public double getCost() {
        return basePizza.getCost() + 50;
    }
}

package com.shoppingService.SystemDesign.LLD.DesignPatterns.Structural.Decorator;

public abstract class ToppingDecorator implements  BasePizza{
    BasePizza basePizza;

    public ToppingDecorator(BasePizza basePizza) {
        this.basePizza = basePizza;
    }
}

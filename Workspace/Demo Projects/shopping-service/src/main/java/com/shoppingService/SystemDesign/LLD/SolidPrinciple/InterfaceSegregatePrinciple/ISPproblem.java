package com.shoppingService.SystemDesign.LLD.SolidPrinciple.InterfaceSegregatePrinciple;

interface RestaurantEmployee{
    void prepareFood();
    void takeOrder();
    void serveFoodAndDrinks();
    void cleanTheKitchen();
}
class Waiter implements RestaurantEmployee{

    @Override
    public void prepareFood() {
        throw new AssertionError("prepare food.");
    }

    @Override
    public void takeOrder() {
        System.out.println("take order.");
    }

    @Override
    public void serveFoodAndDrinks() {
        System.out.println("serve food and drinks.");
    }

    //these are unnecessary methods no need of them
    @Override
    public void cleanTheKitchen() {
        throw new AssertionError("clean the kitchen");
    }
}

public class ISPproblem {
}

package com.shoppingService.SystemDesign.LLD.SolidPrinciple.InterfaceSegregatePrinciple;

interface WaiterTasks {
    void takeOrder();

    void serveFoodAndDrinks();
}

interface ChefTasks {
    void prepareFood();

    void decideTheMenue();
}

interface MaintainanceTasks {
    void cleanTheKitchen();

    void restockTheGrocessaries();
}

class Chef implements ChefTasks {
    @Override
    public void prepareFood() {
        System.out.println("prepare food.");
    }

    @Override
    public void decideTheMenue() {
        System.out.println("decide the menue.");
    }
}

class Waiter1 implements WaiterTasks {
    @Override
    public void takeOrder() {
        System.out.println("take order.");
    }

    @Override
    public void serveFoodAndDrinks() {
        System.out.println("serve food and drinks.");
    }
}

public class ISPsolution {
}

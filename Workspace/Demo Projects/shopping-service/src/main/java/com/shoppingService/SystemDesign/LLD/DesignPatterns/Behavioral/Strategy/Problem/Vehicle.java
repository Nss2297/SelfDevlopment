package com.shoppingService.SystemDesign.LLD.DesignPatterns.Behavioral.Strategy.Problem;

public class Vehicle {
    public void drive(){
        System.out.print("\n" + this.getClass().getSimpleName() + ": ");
        System.out.println("Driving Capability: Normal");
    }
}

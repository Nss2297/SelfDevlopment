package com.shoppingService.SystemDesign.LLD.DesignPatterns.Behavioral.Strategy.Solution;

public class SportsDrive implements DriveStrategy{
    @Override
    public void drive() {
        System.out.println("Driving Capability: Sports");
    }
}

package com.shoppingService.SystemDesign.LLD.DesignPatterns.Behavioral.Strategy.Solution;

public class EvDrive implements DriveStrategy{
    @Override
    public void drive() {
        System.out.println("Driving Capability: Electric");
    }
}

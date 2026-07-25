package com.shoppingService.SystemDesign.LLD.DesignPatterns.Behavioral.Strategy.Solution;

public class VehicleT {
    DriveStrategy driveStrategy;

    public VehicleT(DriveStrategy driveStrategy) {
        this.driveStrategy = driveStrategy;
    }

    public void driveVehicle() {
        System.out.print("\n" + this.getClass().getSimpleName() + ":");
        driveStrategy.drive();
    }
}

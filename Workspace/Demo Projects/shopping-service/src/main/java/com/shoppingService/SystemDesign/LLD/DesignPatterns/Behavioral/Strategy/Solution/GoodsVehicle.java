package com.shoppingService.SystemDesign.LLD.DesignPatterns.Behavioral.Strategy.Solution;

public class GoodsVehicle extends VehicleT {
    public GoodsVehicle(DriveStrategy driveStrategy) {
        super(driveStrategy);
    }
}

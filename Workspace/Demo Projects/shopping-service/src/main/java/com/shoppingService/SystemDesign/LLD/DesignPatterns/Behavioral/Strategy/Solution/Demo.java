package com.shoppingService.SystemDesign.LLD.DesignPatterns.Behavioral.Strategy.Solution;

public class Demo {
    public static void main(String[] args) {
        System.out.println("###### Strategy Design Pattern ######");
        System.out.println("###### Example: Vehicle Drive Modes ######");
        VehicleT vehicle = new SportsVehicle(new SportsDrive());
        vehicle.driveVehicle();
        vehicle = new OffRoadVehicle(new SportsDrive());
        vehicle.driveVehicle();
        vehicle = new HybridVehicle(new EvDrive());
        vehicle.driveVehicle();
        vehicle = new GoodsVehicle(new NormalDrive());
        vehicle.driveVehicle();
    }
}

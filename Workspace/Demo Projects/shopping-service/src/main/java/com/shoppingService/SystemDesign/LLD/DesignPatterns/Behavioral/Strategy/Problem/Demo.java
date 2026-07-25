package com.shoppingService.SystemDesign.LLD.DesignPatterns.Behavioral.Strategy.Problem;

public class Demo {
    public static void main(String[] args) {
        Vehicle vehicle = new Vehicle();
        vehicle = new OffRoadVehicle();
        vehicle.drive();
        vehicle = new PassengerVehicle();
        vehicle.drive();
        vehicle = new SportsVehicle();
        vehicle.drive();
    }
}

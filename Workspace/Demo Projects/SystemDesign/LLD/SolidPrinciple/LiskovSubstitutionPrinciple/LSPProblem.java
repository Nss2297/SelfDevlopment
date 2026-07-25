package com.shoppingService.SystemDesign.LLD.SolidPrinciple.LiskovSubstitutionPrinciple;

import java.util.ArrayList;
import java.util.List;

class Vehicle {
    public Integer getNumberOfWheels() {
        return 2;
    }

    public Boolean hasEngine() {
        return true;
    }
}

class MotorCycle extends Vehicle {
}

class Bicycle extends Vehicle {
    @Override
    public Boolean hasEngine() {
        return null;
    }
}

class Car extends Vehicle {
    @Override
    public Integer getNumberOfWheels() {
        return 4;
    }
}

public class LSPProblem {
    public static void main(String[] args) {
        List<Vehicle> list = new ArrayList<>();
        list.add(new MotorCycle());
        list.add(new Car());
        list.add(new Bicycle());
        for (Vehicle vehicle : list) {
            System.out.println(vehicle.hasEngine().toString());
        }
    }
}

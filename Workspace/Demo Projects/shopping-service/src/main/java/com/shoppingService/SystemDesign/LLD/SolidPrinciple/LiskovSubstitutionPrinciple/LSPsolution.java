package com.shoppingService.SystemDesign.LLD.SolidPrinciple.LiskovSubstitutionPrinciple;


import java.util.ArrayList;
import java.util.List;

class Vehicle1 {
    public Integer getNumberOfWheels() {
        return 2;
    }
}

class Engine extends Vehicle {
    public Boolean hasEngine() {
        return true;
    }
}

class MotorCycle1 extends Engine {
}

class Bicycle1 extends Vehicle {
    @Override
    public Boolean hasEngine() {
        return null;
    }
}

class Car1 extends Engine {
    @Override
    public Integer getNumberOfWheels() {
        return 4;
    }
}

public class LSPsolution {
    public static void main(String[] args) {
        List<Engine> list = new ArrayList<>();
        list.add(new MotorCycle1());
        list.add(new Car1());
//        list.add(new Bicycle1());compile time error is raise
        for (Engine engine : list) {
            System.out.println(engine.hasEngine().toString());
        }
    }
}

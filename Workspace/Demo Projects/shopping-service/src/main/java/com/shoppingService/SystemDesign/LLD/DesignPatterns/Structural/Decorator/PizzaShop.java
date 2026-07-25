package com.shoppingService.SystemDesign.LLD.DesignPatterns.Structural.Decorator;

import com.fasterxml.jackson.databind.ser.Serializers;

public class PizzaShop {
    public static void main(String[] args) {
        BasePizza pizza1 =new PlainPizza();
        System.out.println("Order 1: " + pizza1.getDescription() + " = Rs." + pizza1.getCost());

        BasePizza pizza2=new ExtraCheeseTopping(new PlainPizza());
        System.out.println("Order 2: " + pizza2.getDescription() + " = Rs." + pizza2.getCost());

        BasePizza pizza3 =new VeggiesTopping(new ExtraCheeseTopping(new PlainPizza()));
        System.out.println("Order 3: " + pizza3.getDescription() + " = Rs." + pizza3.getCost());

        BasePizza pizza4 =new PepperoniTopping(new ExtraCheeseTopping(new PlainPizza()));
        System.out.println("Order 4: " + pizza4.getDescription() + " = Rs." + pizza4.getCost());

        BasePizza pizza5 =new MushroomTopping(new PepperoniTopping(new ExtraCheeseTopping(new PlainPizza())));
        System.out.println("Order 5: " + pizza5.getDescription() + " = Rs." + pizza5.getCost());

        BasePizza pizza6=new Farmhouse();
        System.out.println("Order 6: " + pizza6.getDescription() + " = Rs." + pizza6.getCost());

        BasePizza pizza7=new MushroomTopping(new ExtraCheeseTopping(new Farmhouse()));
        System.out.println("Order 7: " + pizza7.getDescription() + " = Rs." + pizza7.getCost());

        BasePizza pizza8=new TandooriPaneerDelight();
        System.out.println("Order 8: " + pizza8.getDescription() + " = Rs." + pizza8.getCost());

        BasePizza basePizza9=new ChickenDominator();

    }
}

package com.shoppingService.SystemDesign.LLD.SolidPrinciple.SingleResponsibilityPrinciple;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
class Marker{
    private String name;
    private String color;
    private int price;

    public Marker(String name, String color, int price) {
        this.name = name;
        this.color = color;
        this.price = price;
    }
}

@Getter
@Setter
class Invoice{
    private Marker marker;
    private int quantity;

    public Invoice(Marker marker, int quantity) {
        this.marker = marker;
        this.quantity = quantity;
    }

    //calculate price
    public int calculatePrice(){
        return this.marker.getPrice()*this.quantity;
    }

    //database operations
    public void saveInvoice(){
        //logic
    }

    public void printInvoice(){
        //logic
    }
}

//any modification in the Invoice class will change this class frequently at production
public class SRPproblem {
}

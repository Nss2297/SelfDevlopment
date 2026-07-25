package com.shoppingService.SystemDesign.LLD.SolidPrinciple.OpenClosePrinciple;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

class InvoiceT1{
}
@NoArgsConstructor
@AllArgsConstructor
class InvoiceDao{
    InvoiceT1 invoice;

public void saveToDb(){
    System.out.println("Save to DB.");
}

public void saveFile(){
    System.out.println("Save file.");
}
//Here every time we need a new method we need to modify the InvoieDao class.
}
public class OCPproblem {
}

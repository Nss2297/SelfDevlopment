package com.shoppingService.SystemDesign.LLD.SolidPrinciple.SingleResponsibilityPrinciple;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class MarkerT {
    private String name;
    private String color;
    private int price;
}

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class InvoiceT {
    private Marker marker;
    private int quantity;

    //calculate price
    public int calculatePrice() {
        return marker.getPrice() * quantity;
    }
}

class InvoiceDao {
    InvoiceT invoice;

    //database operations
    public void saveInvoice(InvoiceT invoice) {
        System.out.println("DB logic");
    }
}

class PrintInvoice {
    InvoiceT invoiceT;

    public void printInvoice(InvoiceT invoice) {
        System.out.println("Save file.");
    }
}

public class SRPsolution {
    public static void main(String[] args) {
        InvoiceT invoice = new InvoiceT(new Marker("name", "color", 34), 10);
        System.out.println(invoice.calculatePrice());
        InvoiceDao invoiceDao = new InvoiceDao();
        invoiceDao.saveInvoice(invoice);
        PrintInvoice printInvoice = new PrintInvoice();
        printInvoice.printInvoice(invoice);
    }
}

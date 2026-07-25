package com.shoppingService.SystemDesign.LLD.SolidPrinciple.OpenClosePrinciple;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

interface InvoiceDaoT {
    void save();
}

class InvoiceT2 {
}

@NoArgsConstructor
@AllArgsConstructor
class SaveFile implements InvoiceDaoT {
    InvoiceT2 invoiceT2;

    @Override
    public void save() {
        System.out.println("Save file.");
    }
}

@NoArgsConstructor
@AllArgsConstructor
class SaveTODb implements InvoiceDaoT {
    InvoiceT2 invoiceT2;

    @Override
    public void save() {
        System.out.println("Save to db.");
    }
}

@NoArgsConstructor
@AllArgsConstructor
class SaveTOMongoDb implements InvoiceDaoT {
    InvoiceT2 invoiceT2;

    @Override
    public void save() {
        System.out.println("Save to mongo DB.");
    }
}

public class OCPsolution {
    public static void main(String[] args) {
        SaveFile saveFile = new SaveFile();
        saveFile.save();
        SaveTODb saveTODb = new SaveTODb();
        saveTODb.save();
        SaveTOMongoDb saveTOMongoDb = new SaveTOMongoDb();
        saveTOMongoDb.save();
    }
}


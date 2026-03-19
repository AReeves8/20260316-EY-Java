package com.skillstorm.producer_consumer;

public record ProductRecord(
    String name,
    String sku, 
    double price, 
    int quantity, 
    String category
) {

    public static ProductRecord parseRow(String lineData) {
        String[] fields = lineData.split(","); 

        // making sure we have the right number of fields
        if(fields.length != 5) {
            throw new IllegalArgumentException("Number of fields is incorrect.\n");
        }

        // validate data
        String name = fields[0];
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be blank.\n");
        }

        String sku = fields[1];
        if (sku.isEmpty()) {
            throw new IllegalArgumentException("SKU cannot be blank.\n");
        }

        double price;
        try {
            price = Double.parseDouble(fields[2]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Price is invalid number.\n");
        }

        int quantity;
        try {
            quantity = Integer.parseInt(fields[3]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Quanity is invalid number.\n");
        }

        String category = fields[4];
        if (category.isEmpty()) {
            throw new IllegalArgumentException("Category cannot be blank.\n");
        }
        
        // if no exceptions are thrown, create the new product and return it
        return new ProductRecord(name, sku, price, quantity, category);
    }


}

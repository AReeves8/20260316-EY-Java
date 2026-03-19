package com.skillstorm.factory;

import java.time.LocalDate;

class PerishableProduct implements Product {
    private final String    name;
    private final String    sku;
    private final double    price;
    private final LocalDate expiryDate;

    PerishableProduct(String name, String sku, double price) {
        this.name       = name;
        this.sku        = sku;
        this.price      = price;
        this.expiryDate = LocalDate.now().plusDays(7);
    }

    @Override public String getName()  { return name; }
    @Override public String getSku()   { return sku; }
    @Override public double getPrice() { return price; }
    @Override public String getType()  { return "Perishable"; }

    @Override
    public String getHandlingInstructions() {
        return "Climate-controlled Zone C — expires " + expiryDate;
    }
}

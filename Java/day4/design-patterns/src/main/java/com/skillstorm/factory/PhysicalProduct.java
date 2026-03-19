package com.skillstorm.factory;

class PhysicalProduct implements Product {
    private final String name;
    private final String sku;
    private final double price;

    PhysicalProduct(String name, String sku, double price) {
        this.name  = name;
        this.sku   = sku;
        this.price = price;
    }

    @Override public String getName()  { return name; }
    @Override public String getSku()   { return sku; }
    @Override public double getPrice() { return price; }
    @Override public String getType()  { return "Physical"; }

    @Override
    public String getHandlingInstructions() {
        return "Standard warehousing — Zone A/B";
    }
}

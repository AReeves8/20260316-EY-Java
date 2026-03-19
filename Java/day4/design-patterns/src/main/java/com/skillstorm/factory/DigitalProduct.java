package com.skillstorm.factory;

class DigitalProduct implements Product {
    private final String name;
    private final String sku;
    private final double price;

    DigitalProduct(String name, String sku, double price) {
        this.name  = name;
        this.sku   = sku;
        this.price = price;
    }

    @Override public String getName()  { return name; }
    @Override public String getSku()   { return sku; }
    @Override public double getPrice() { return price; }
    @Override public String getType()  { return "Digital"; }

    @Override
    public String getHandlingInstructions() {
        return "No physical storage — digital delivery only";
    }
}

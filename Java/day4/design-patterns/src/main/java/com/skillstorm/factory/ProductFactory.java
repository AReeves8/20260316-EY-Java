package com.skillstorm.factory;

public class ProductFactory {

    private ProductFactory() {}   // not instantiable — static utility only

    /**
     * Creates the appropriate Product based on the type string.
     * Returns null for unrecognized types (could also throw IllegalArgumentException).
     */
    public static Product create(String type, String name, String sku, double price) {
        if (type == null) return null;
        return switch (type.toLowerCase()) {
            case "physical"   -> new PhysicalProduct(name, sku, price);
            case "digital"    -> new DigitalProduct(name, sku, price);
            case "perishable" -> new PerishableProduct(name, sku, price);
            default -> {
                System.out.println("  [ProductFactory] Unknown type: '" + type + "' — returning null");
                yield null;
            }
        };
    }
}
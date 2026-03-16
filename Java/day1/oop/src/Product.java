/**
 * Object Oriented Programming (OOP)
 * 
 *      we use classes to build out objects
 * 
 */

/**
 * Encapsulation
 *      - wrap related state and behavior into a single object
 *      - control how that state and behavior is accessed and manipulated
 * 
 *      - access variables
 *          - private - same class only 
 *          - protected - same CLASS or any SUBCLASS
 *          - public - anyone anywhere
 *          - default (no access modifier specified) - Package-private only classes in the same package
 */


public class Product implements Storable {

    /** Instance Variables */
    private String name;
    private String sku;
    private double price;
    private int quantity;

    /** Constructors */
    public Product() {
        this("DEFAULT NAME", "SKU-0000", 0.0, 1);
    }

    public Product(String name, String sku, double price, int quantity) {
        // this refers to the specific object that is currently being manipulated in memory
        this.price = price;
        this.name = name;
        this.sku = sku;
        this.quantity = quantity;
    }

    /** Getters and Setters */

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getSku() {
        return sku;
    }
    public void setSku(String sku) {
        this.sku = sku;
    }

    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {

        if(price < 0) {
            // throw some excpetion or set some default value
            this.price = 0;
        } 
        else {
            this.price = price;
        }
    }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }



    public int getShelfLocation() {
        return (int) (price * quantity * MIN_SHELF_SPACE); 
    }

    public String getLabel() {
        return getStorageLabel(); 
    }
}

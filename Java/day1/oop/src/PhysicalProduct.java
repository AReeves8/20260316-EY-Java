
/**
 * INHERITANCE
 *      - passing down state and behavior from one class to another
 *      - extends: classes
 *      - implements: interfaces
 *          
 *      - can only extend one class but can implement many interfaces
 */


public class PhysicalProduct extends AbstractProduct implements Storable{

    private double weight;

    public PhysicalProduct(double weight) {

        // this() will call the other constructor in this class - CONSTRUCTOR CHAINING
        // this ALSO has to be the first line if you're using it. 
        this(1, weight);


        System.out.println("PhysicalProduct(weight)");
    }


    public PhysicalProduct(int productCount, double weight) {
        /**
         * super calls a constructor/field/methods in the parent class
         *      it has to be the first line of your constructor IF you're using it
         *          
         *      CANNOT call super() and this() in the same constructor
         * 
         *      super() will be called implicitly if you don't explicitly call super() or this()
         */
        super(productCount);
        this.weight = weight;

        System.out.println("PhysicalProduct(productCount, weight)");
    }

    public double getWeight() {
        return weight;
    }
    public void setWeight(double weight) {
        this.weight = weight;
    }

    /** METHOD OVERLOADING - compile time - polymorphism */
    public void setWeight(double weight, double offset) {
        this.weight = (weight + offset);
    }

    // implementing parent abstract methods
    @Override
    public void activate() {
        System.out.println("Product is activated");
    }

    @Override
    public int getShelfLocation() {
        return (int) (weight * MIN_SHELF_SPACE);
    }



    /**
     * METHOD OVERRIDING - ppolymorphism
     *      - changing the implementation from a parent method that has an implemention already
     *      - runtime
     */
    @Override
    public String getStorageLabel() {
        return "Location B";
    }

    

}

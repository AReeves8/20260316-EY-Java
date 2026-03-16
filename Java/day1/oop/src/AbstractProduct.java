
/**
 * ABSTRACTION
 * 
 *      Abstract Classes
 *          - classes that can contain abstract methods
 *          - abstract methods are methods you can define now and implement later, in child classes
 *          - cannot be instantiated with new
 *          - extends keyword for inheritance
 */
public abstract class AbstractProduct {


    private int productCount;

    public AbstractProduct(int productCount) {
        this.productCount = productCount;

        System.out.println("AbstractProduct(productCount)");
    }

    public int getProductCount() {
        return productCount;
    }
    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }

    // abstract methods will not have a body but will need to be implemented in the child class
    public abstract void activate();


    private void deactivate() {
        System.out.println("product is deactivated!");
    }
    
}

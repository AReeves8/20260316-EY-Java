
/**
 * ABSTRACTION
 * 
 *  INTERFACES
 *      - gives a contract of what a class can do
 *      - provides "default" behavoiors that a class can implement and modify
 *      - all methods are intrinsically public and abstract
 *      - any instance variables are "public static final"
 *      - uses implements keyword for inheritance
 *  
 * interfaces usualy have the 'able' suffix
 */
public interface Storable {

    // can define variables to be used by child classes
    double MIN_SHELF_SPACE = 0.1;


    // define methods without providing their implementation
    int getShelfLocation();



    // interfaces can have DEFAULT methods - default implementations that can be changed
    default String getStorageLabel() {
        return "Location A";
    }
}

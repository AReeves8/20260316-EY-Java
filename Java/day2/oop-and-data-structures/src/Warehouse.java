
/**
 * Java Records
 *      - immutable data classes
 *          - every field is final
 *              - 'final' means that the value cannot be changed when it is set
 *  
 *              - 'static' means that the property or object will be used in the same memory location, even if its value is changed
 *                  - can access static members without creating an object because they are loaded into memory as soon as the class is
 * 
 *              - techincally, to create a constant, you need both final and static
 * 
 *      - auto-generates constructors, getters, equals(), hashcode(), toString()
 *          - no setters because immutable
 * 
 */

public record Warehouse(
    String name, 
    String location, 
    int area, 
    int numStorageLocations
) {

    // you kinda have constructors.. but they are just used for validation
    // called Compact Constructors
    public Warehouse {
        
        // Compact Constructors are used to validate and format data where neessary
        
        if(name == null) {
            throw new IllegalArgumentException("Name cannot be null.");
        }

        // ternary operator
        // some_condition ? value_if_true : value_if_false
        location = ((location == null) ? "UNKNOWN" : location.toUpperCase().trim()); 
    }


    // can have methods in your records, they are implicitly public
    int calculateWarehouseRating() {
        return (area * numStorageLocations) / 1000;
    }

    // cannot manually define setters because fields are final
    // void setArea(int newArea) {
    //     area = newArea;
    // }
}

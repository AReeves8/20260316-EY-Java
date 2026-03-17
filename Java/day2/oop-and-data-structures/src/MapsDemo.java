import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class MapsDemo {

    public static void main(String[] args) {
        hashMapsDemo();
        treeMapDemo();
    }


    public static void hashMapsDemo() {
        /**
         * HashMaps
         *      - combo of a Map and a Hash Table
         *      
         *      - Maps
         *          - operate key-value pairs
         *              - every key must be unique and each key corresponds to a value
         *          - access data in constant time O(1)
         *          
         * 
         *      - Hash Tables 
         *          - unsorted way to store data
         *          - data is stored and accessed according to some hashing function that gives a predictable result for some value
         * 
         *          - uses hashcode() and equals() functions to do the hashing and during collision evaluation
         * 
         *          - collision strategies
         *              grow the data structure, manually move the value to the next open spot, etc.
         * 
         *      
         * 
         *      
         *      - HashMap
         *          - secondary LinkedList for collision strategy
         * 
         *              []
         *              [7] o -> o -> o -> o
         *              []
         *              []
         */

        Map<String, Double> priceBook = new HashMap<>();
        priceBook.put("Laptop", 999.99);                // .put to insert new key-value pairs
        priceBook.put("1 stick of RAM", 999999.99);
        priceBook.put("Stick of Gum", 0.99);
        System.out.println(priceBook);

        // .get returns the value for some given key
        System.out.println(priceBook.get("Laptop"));

        // can pull out just your keys or just your values
        Set<String> productNames = priceBook.keySet();
        Collection<Double> productPrices = priceBook.values();

        // enhanced for loop (for-each)
        for(Double price : priceBook.values()) {
            System.out.println(price);
        }

    }

    public static void treeMapDemo() {
        /**
         * Tree Map
         *      - sorted Map
         *          - sorted by KEYS
         *              - only works if your key has some Natural Order
         * 
         *      - sorting objects with no natural order
         *          - Comparable interface and compareTo method
         */

        TreeMap<Integer, String> fruits = new TreeMap<>();
        fruits.put(1, "Banana");
        fruits.put(100, "Banana");
        fruits.put(2, "Banana");
        fruits.put(9879834, "Banana");
        fruits.put(3, "Banana");
        System.out.println(fruits);

        TreeMap<WarehouseClass, Integer> warehouseProducts = new TreeMap<>();
        warehouseProducts.put(new WarehouseClass("Dallas Hub", "Dallas", 10000, 1000), 543);
        warehouseProducts.put(new WarehouseClass("Austin Hub", "Austin", 10000, 3256), 897);
        warehouseProducts.put(new WarehouseClass("San Antonio Hub", "San Antonio", 10000, 90), 76);
        System.out.println(warehouseProducts);
    }

}
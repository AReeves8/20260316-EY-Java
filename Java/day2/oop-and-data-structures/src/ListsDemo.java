import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ListsDemo {

    public static void main(String[] args) {
        arrayListsDemo();
        linkedListDemo();
    }



    public static void arrayListsDemo() {
        /**
         * ArrayLists
         *      - are backed by an array
         *      - memory can be allocated dynamically, so it can grow and resize without manual changes
         *          - if the array exceeds its limit, then it will automatically create a new one with double the current size
         *              - then everything from the orignal is copied over and the new one takes its place
         *              - SLOW
         */

        // Programming to the Interface
        // Polymorphism - Covariance - child objects can be instantiated in place of a parent object
        List<String> names = new ArrayList<>();     // default capacity of ArrayList is 10
        names.add("Austin");        // .add inserts elements
        names.add("Aiden");
        names.add(names.size() - 1, "Jacob");     // can insert at specific index
        names.add("Alejandro");
        names.add("Minh");
        names.add("Aidan");
        names.add("Brian");
        names.add("Bradley");
        names.add("Jon");
        names.add("Joaquin");
        names.add("Ahmed");     // inserting this one will require the remaking of the underying array

        System.out.println(names);

        // replace a value at an index with .set()
        names.set(10, names.get(10).toUpperCase());     // use .get() instead of []
        System.out.println(names);

        // remove a value with .remove
        names.remove(names.indexOf("Austin"));      // indexOf() to find index of a certain value
        System.out.println(names);

        List<String> firstFive = names.subList(0, 5);
        System.out.println(firstFive);

        // addAll() to copy values from existing list
        List<String> namesCopy = new ArrayList<>();
        namesCopy.addAll(names);     

        System.out.println(namesCopy);
    }

    public static void linkedListDemo() {
        /**
         * LinkedList
         *      - practically funciton the same as ArrayList
         *          - both inherit from List
         *      - much faster for adding and removing elements from the end - O(1)
         *          - add/remove from middle is O(n)
         *      - accessing elements is slower
         * 
         *      o -> o -> o -> o -> o -> o
         */

        LinkedList<String> fruits = new LinkedList<>();
        fruits.add("banana");
        fruits.add("apple");
        fruits.add("strawberry");
        fruits.add("grape");
        fruits.add("orange");
        fruits.addFirst("pineapple");
        System.out.println(fruits);

    }


}

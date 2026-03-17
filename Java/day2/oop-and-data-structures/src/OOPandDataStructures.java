public class OOPandDataStructures {
    public static void main(String[] args) throws Exception {
        
        // can instantiate records like objects
        Warehouse warehouse1 = new Warehouse("Dallas Hub", "Dallas", 10000, 1000);

        // getters for records are just the name of the fields
        System.out.println("The " + warehouse1.name() + " is " + warehouse1.area() + " square feet.");

        // records cannot be modified but you can create new objects if you need to change them 
        // generally you should avoid changing them though
        warehouse1 = new Warehouse(warehouse1.name(), warehouse1.location(), 20000, warehouse1.numStorageLocations());
        System.out.println("The " + warehouse1.name() + " is " + warehouse1.area() + " square feet.");
        System.out.println("It has a rating of " + warehouse1.calculateWarehouseRating());

        Warehouse warehouse2 = new Warehouse("Arizona Office", null, 0, 0);
        System.out.println(warehouse2.toString());


        /**
         * Control Flow
         */

        if(warehouse2.location().equals("UNKNOWN")) {
            warehouse2 = new Warehouse(warehouse2.name(), "PHEONIX", warehouse2.area(), warehouse2.numStorageLocations());
        }
        else if(warehouse2.numStorageLocations() > 1000) {
            System.out.println("That's a big warehouse!");
        }
        else if(warehouse2.numStorageLocations() <= 1000) {
            System.out.println("That's a small warehouse!");
        }
        else {
            System.out.println("This runs if nothing else is true above.");
        }

        // being able to switch on String is possible in Java 8+
        switch(warehouse2.location()) {
            case "DALLAS" :         // can use fall-through for multiple cases running the same block of code
            case "AUSTIN" :
                System.out.println("Warehouse is in Texas!");
                break;      // prevents fall-through
            case "PHEONIX" : 
                System.out.println("Warehouse is in Arizona!");
                break;
            case "NEW YORK CITY" :
                System.out.println("Warehouse is in New York!");
                break;
            default :
                System.out.println("Warehouse is located somewhere else!");
        }


        // Java 14+ - enchance switch statements - don't have to worry about fall-through at all
        System.out.println("ENHANCED SWITCH RESULTS: ");
        switch(warehouse2.location()) {
            case "AUSTIN" -> System.out.println("Warehouse is in Texas!");
            case "PHEONIX" -> {         // use curly-braced blocks for larger code blocks
                System.out.println("Warehouse is in Arizona!");
            }
            case "NEW YORK CITY" -> System.out.println("Warehouse is in New York!");
            default -> System.out.println("Warehouse is located somewhere else!");
        }
    }
}

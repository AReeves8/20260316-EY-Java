public class Arrays {

    /**
     * have a fixed size
     * 0-based indexed
     * store value in congruent memory locations
     * 
     */

    public static void main(String[] args) {

        String[] locations = new String[5];     // create an array of size 5
        locations[0] = "Dallas";
        locations[1] = "Jacksonville";
        locations[2] = "Orlando";
        locations[3] = "Lima";
        locations[4] = "Minneapolis";

        // loops are used to process arrays
        for(int i = 0; i < locations.length; i++) {     // length is a property not a method
            locations[i] = (i+1) + ". " + locations[i];
            System.out.println(locations[i]);
            //System.out.println(locations[i+1]);   // ArrayIndexOutOfBoundsException
        }

        // 2D ARRAYS
        String[][] shelfLocations = new String[3][3];
        shelfLocations[0][0] = "Location A";
        shelfLocations[0][1] = "Location B";
        shelfLocations[0][2] = "Location C";
        shelfLocations[1][0] = "Location D";
        shelfLocations[1][1] = "Location E";
        shelfLocations[1][2] = "Location F";
        shelfLocations[2][0] = "Location G";
        shelfLocations[2][1] = "Location H";
        shelfLocations[2][2] = "Location I";

        // use nested loops to process 2D arrays
        for(int i = 0; i < shelfLocations.length; i++) {
            for(int j = 0; j < shelfLocations[i].length; j++) {
                System.out.println(shelfLocations[i][j]);
            }
        }

        // Jagged Array - when you define a multi-dimensional array but only give the first a definitive size
        String[][] jaggedArray1 = new String[3][];
        //String[][] jaggedArray2 = new String[][3];      // this will not compile
        //jaggedArray1[0][0] = "Location A";              // throws NulPointerException until the 2d array is initialized
        String[][][][] veryJaggedArray = new String[2][14][][];  // can't skip any dimensions but can initialize only a few

        // need to initialize 2d before using it
        jaggedArray1[0] = new String []{"Location A", "Location B", "Location C"};
        jaggedArray1[1] = new String[5];    // 2d arrays do not all need to be the same size
        jaggedArray1[2] = new String[10];

        // use nested loops to process 2D arrays
        for(int i = 0; i < jaggedArray1.length; i++) {
            System.out.println("OUTER LOOP " + (i+1)); // need to put i+1 in parenthesis so they are added first before string concatenation happens
            for(int j = 0; j < jaggedArray1[i].length; j++) {
                System.out.println("\tINNER LOOP " + (j+1));
                System.out.println("\t\t" + jaggedArray1[i][j]);
            }
        }


        double[] productPrices = {20.00, 5, 145.50};
        System.out.println(productPrices.toString());

        System.out.println("Before Discount");
        for(int i = 0; i < productPrices.length; i++) {
            System.out.println(productPrices[i]);
        }
        double discount = 0.1;
        applyBulkDiscount(productPrices, discount); 
        System.out.println("After Discount");
        for(int i = 0; i < productPrices.length; i++) {
            System.out.println(productPrices[i]);
        }

        // if you want a new array rather than manipulating the old one
        double[] newPrices = applyBulkDiscountNewArray(productPrices, discount);

    }

    public static void applyBulkDiscount(double[] prices, double discountPercentage) {

        // arrays are passed by reference, so you can manipulate the array that was given and see changes reflected. 
        for(int i = 0; i < prices.length; i++) {
            prices[i] = prices[i] - (prices[i] * discountPercentage);
        }
    }

    // return type doesn't affect method overloading - need to change the signature in another way
    public static double[] applyBulkDiscountNewArray(double[] prices, double discountPercentage) {

        // if you want the array to be passed by value instead, you have to copy it to a new array because it will always be pass by reference.
        double[] newPrices = prices;
        for(int i = 0; i < prices.length; i++) {

            // make sure you're not altering the original array if you want to leave it unaffected
            newPrices[i] = prices[i] - (prices[i] * discountPercentage);
        }
        return newPrices;
    }

}

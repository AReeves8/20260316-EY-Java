public class OOPDemo {

    public static void main(String[] args) throws Exception {

        /** Primitive Data Types */
        int num = 189762;               // 32-bit       default: 0
        long bigNum = 3409837425L;      // requires L suffix; 64-bit        default: 0L
        short smallNum = 100;           // 16-bit       default: 0
        byte reallySmallNum = 5;        // 8-bit (-128 - 127)       default: 0
        float decNum = 3.14159f;        // requires f suffix; 32-bit        default: 0.0f
        double bigDecNum = 3.1415926535897932462;   // 64-bit               default: 0.0
        boolean isTrue = true;          // default: false
        char singleLetter = 'a';        // single letter in single quotes  default: ''


        /** Object Data Types */
        Integer objectNum = 1234;       // wrapper class for the primitive type
        String text = "Hello, my name is Austin!";      // default: ""
        String text2 = "Hello, my name is Austin!";
        String textObject = new String("Hello, my name is Austin!");

        /**
         * == vs .equals() for objects
         *      == compares objects to each other, including memory locations
         *          - if one object is the same as another object
         * 
         *      .equals compares the value of the objects
         * 
         * 
         * 
         *      SOMETIMES == will be correct but it isn't reliable so use .equals to be sure
         * 
         */
        boolean stringsEqual1 = (text == "Hello, my name is Austin!");                  // should be false - because == checks objects
        boolean stringsEqual2 = (text == textObject);                                   // false - because == checks objects
        boolean stringsEqual3 = (text.equals("Hello, my name is Austin!"));   // true - because .equals checks values
        System.out.println("stringsEqual1: " + stringsEqual1);
        System.out.println("stringsEqual2: " + stringsEqual2);
        System.out.println("stringsEqual3: " + stringsEqual3);

        /** Autoboxing - primitive -> wrapper automatically */
        Integer wrappedNum = num;
        System.out.println("Wrapped Num: " + wrappedNum);

        /** Unboxing - wrapper -> primitive as needed */
        int unboxedNum = wrappedNum;
        System.out.println("unboxed Num: " + unboxedNum);

        String numString = "1234";  
        int inputValue = Integer.parseInt(numString);       // wrapper classes contain many useful methods
        System.out.println("Num String: " + inputValue);


        /** CASTING: widening */
        short twelve = 12;
        int widerTwelve = twelve;           // widening can happen implicitly
        double decTwelve = widerTwelve;
        System.out.println("Widened Twelve: " + decTwelve);
        
        
        /** CASTING: narrowing */
        // can encure data loss because bits are being removed
        double price = 9.99;
        int wholePrice = (int) price;     // requires explicit casting
        System.out.println("Whole Price: " + wholePrice);   // removes extra bits - no rounding occurs

        Product product = new Product();
        System.out.println(product.getLabel());



        PhysicalProduct physicalProduct = new PhysicalProduct(34.5);

    }
}

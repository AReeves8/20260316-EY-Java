package fileIO;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

public class FileIO {

    private static int numExpectedFields;

    public static void main(String[] args) {
        
        basicFileIOExample();
        advancedFileIOExample();
        
    }

    public static void basicFileIOExample() {
        InputStream inputStream;
        OutputStream outputStream;
        
        /**
         * Try-Catch-Finally
         *      try - is where you put "risky" code that could throw an exception
         *      catch - where you handle excpetions
         *          - each block can only handle one execption but you can have multiple blocks
         *              - put the most specific exceptions first, and most general exceptions last
         *      finally - runs no matter what, regardless of an exception being thrown or not
         * 
         * 
         *  Throwable
                  ├── Error                        ← do NOT catch these
                  │     ├── OutOfMemoryError          (JVM ran out of heap)
                  │     ├── StackOverflowError        (infinite recursion)
                  │     └── AssertionError            (assert statement failed)
                  │
                  └── Exception
                        │
                        ├── IOException              ← CHECKED
                        │    └─ FileNotFoundException
                        ├── SQLException             ← CHECKED
                        ├── ParseException           ← CHECKED
                        │
                        │   [Our custom checked:]
                        ├── InvalidProductDataException ← CHECKED
                        │
                        └── RuntimeException         ← UNCHECKED (compiler silent)
                              ├── NullPointerException
                              ├── IllegalArgumentException
                              ├── IllegalStateException
                              ├── ArrayIndexOutOfBoundsException
                              ├── ClassCastException
                              ├── NumberFormatException
                              ├── UnsupportedOperationException
                              │
                              └── [Our custom unchecked:]
                                  DuplicateSkuException ← UNCHECKED


            CHECKED vs UNCHECKED
                - checked: compiler can see that it will cause an issue
                - unchecked: occurs at runtime, compiler cannot warn you about them
         */
        try {

            /**
             * InputStream - read data in as bytes. Really useful for binary files like images
             * OutputStream - write byte data to some file
             */
            inputStream = new FileInputStream("src\\fileIO\\helloWorldInput.txt");  // using backslashes for windows
            outputStream = new FileOutputStream("src\\fileIO\\helloWorldOutput.txt");

            int byteData;
            // .read() returns -1 when it reaches EOF
            while((byteData = inputStream.read()) != -1) {
                char c = (char) byteData;
                System.out.println(c);

                // .write overrides file contents or creates a new file if it doesn't exist
                outputStream.write(byteData);   // streams work with bytes
            }

            // closing both input and output streams
            inputStream.close();
            outputStream.close();

        } catch (FileNotFoundException e) {
            System.out.println("File was unable to be opened. " + e.getMessage());
        } catch (IOException e) {
            // THIS IS AN ISSUE SINCE THE FILES ARE STILL NOT CLOSED.
            System.out.println("File was unable to be closed. " + e.getMessage());
        } 
        catch (Exception e) {
            System.out.println("Something went wrong. Oops. " + e.getMessage());
        } finally {
            // we would prefer to close files in a finally just in case some exception 
            // is thrown before they can be closed in the try block

            //inputStream.close();
        }
    }

    public static void advancedFileIOExample() {

        List<Product> validProducts = new LinkedList<>();
        List<String> errors = new LinkedList<>();

        /**
         * Scanner vs BufferedReader
         *      - both wrap around an input stream
         *      - buffered reader reads entire lines at a time
         *      - scanner is better for parsing out data within individual lines
         */

        // Class Loader is more flexible than creating a FileInputStream
        // returns null if file is not found, rather than an exception
        InputStream inputStream = FileIO.class.getClassLoader().getResourceAsStream("fileIO\\products.csv");
        if(inputStream == null) {
            System.out.println("File was unable to be opened.");
            return; // exit if file cannot be opened
        }

        /**
         * Try With Resources
         *      - similar to a try-catch, but it will auto-close anything that implements Closable
         * 
         *      - don't have to worry about calling .close()
         */
        try(BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {

            // readline returns an entire line from the input stream
            String headerRow = br.readLine();   // taking out the header row of the CSV
            calculateNumExpectedFields(headerRow);

            // reading through and parsing data
            String line;
            int lineNumber = 0;
            while((line = br.readLine()) != null) {     // null for EOF
                lineNumber++;
                
                try {
                    Product newProduct = parseRow(line, lineNumber);
                    validProducts.add(newProduct);
                } catch(InvalidProductDataException e) {
                    errors.add(e.getLineNumber() + ": " + e.getMessage());
                }
                
            }   

            // write results to file
            generateProductsReport(validProducts, errors);


        } catch(IOException e) {
            System.out.println("IO EXCEPTION OCCURED\n" + e.getMessage());
        }
    }

    private static int calculateNumExpectedFields(String lineData) {
        String[] fields = lineData.split(",");  // splits a string into many substrings based on a delimeter
        numExpectedFields = fields.length;
        return numExpectedFields;
    }

    /**
     * throw and throws
     *      - throw: "I am throwing this exception right now"
     *      - throws: "Watch out, I could throw this exception"
     * 
     *      - throws makes it so that the method doesn't have to handle an exception that it is throwing
     */
    private static Product parseRow(String lineData, int lineNumber) throws InvalidProductDataException {
        String[] fields = lineData.split(","); 

        // making sure we have the right number of fields
        if(fields.length != numExpectedFields) {
            throw new InvalidProductDataException("Number of fields is incorrect.\n" + lineData , lineNumber);
        }

        // validate data
        String name = fields[0];
        if (name.isEmpty()) {
            throw new InvalidProductDataException("Name cannot be blank.\n" + lineData, lineNumber);
        }

        String sku = fields[1];
        if (sku.isEmpty()) {
            throw new InvalidProductDataException("SKU cannot be blank.\n" + lineData, lineNumber);
        }

        double price;
        try {
            price = Double.parseDouble(fields[2]);
        } catch (NumberFormatException e) {
            throw new InvalidProductDataException("Price is invalid number.\n" + lineData, lineNumber);
        }

        int quantity;
        try {
            quantity = Integer.parseInt(fields[3]);
        } catch (NumberFormatException e) {
            throw new InvalidProductDataException("Quanity is invalid number.\n" + lineData, lineNumber);
        }

        String category = fields[4];
        if (category.isEmpty()) {
            throw new InvalidProductDataException("Category cannot be blank.\n" + lineData, lineNumber);
        }
        
        // if no exceptions are thrown, create the new product and return it
        return new Product(name, sku, price, quantity, category);

    }

    private static void generateProductsReport(List<Product> products, List<String> errors) {

        /**
         * PrintWriter wraps around an OutputStream to provide more utility
         */

        try(PrintWriter writer = new PrintWriter(new FileOutputStream("src\\fileIO\\ProductsReport.txt"))) {

            writer.println("=== VALID PRODUCTS ===");
            for(Product product : products) {
                writer.println(product.toString());
                // PrintWriter doesn't throw exceptions if it encounters a write error, only sets this flag to true
                if(writer.checkError()) {
                    System.out.println("An IO error occurred trying to write to the file.");
                }
            }

            writer.println("\n\n=== ERRORS ===");
            for(String err : errors) {
                writer.println(err);
                // PrintWriter doesn't throw exceptions if it encounters a write error, only sets this flag to true
                if(writer.checkError()) {
                    System.out.println("An IO error occurred trying to write to the file.");
                }
            }
        }
        catch(FileNotFoundException e) {
            System.out.println("File could not be found.\n" + e.getMessage());
        }

    }
}

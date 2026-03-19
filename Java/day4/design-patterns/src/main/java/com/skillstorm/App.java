package com.skillstorm;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;

import com.skillstorm.factory.Product;
import com.skillstorm.factory.ProductFactory;
import com.skillstorm.producer_consumer.ProductRecord;
import com.skillstorm.singleton.WarehouseRegistry;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        // Singleton Examples
        warehouseRegistryTest1();
        warehouseRegistryTest2();

        // Factory Examples
        productFactoryTest();

        // Producer-Consumer Examples
        producerConsumerTest();
    }

    public static void warehouseRegistryTest1() {
        WarehouseRegistry registry = WarehouseRegistry.getInstance();
        registry.registerWarehouse("Warehouse 1", "Dallas");
        registry.registerWarehouse("Warehouse 2", "Pheonix");
        registry.registerWarehouse("Warehouse 3", "New York City");

        System.out.println("--- WAREHOUSE TEST 1 ---");
        System.out.println(registry.getWarehouses());
    }

    public static void warehouseRegistryTest2() {
        WarehouseRegistry registry = WarehouseRegistry.getInstance();
        registry.registerWarehouse("Warehouse 4", "Los Angeles");
        registry.registerWarehouse("Warehouse 5", "Jacksonville");
        registry.registerWarehouse("Warehouse 6", "Dallas");        // won't be added since its a dupe from test1

        System.out.println("--- WAREHOUSE TEST 2 ---");
        System.out.println(registry.getWarehouses());           // will show all warehouses from both tests
    }

    public static void productFactoryTest() {

        Set<Product> products = new HashSet<>();    // Sets are like lists except they cannot store duplicates

        // The caller provides a type string — no 'new PhysicalProduct(...)' here.
        // The factory decides what to construct and validates the input.
        products.add(ProductFactory.create("physical", "Laptop Pro 15", "LPRO-001", 1299.99));
        products.add(ProductFactory.create("digital", "PhotoEditor Pro", "PHED-010", 149.99));
        products.add(ProductFactory.create("perishable", "Greek Yogurt 500g", "YOGT-020", 2.49));
        products.add(ProductFactory.create("furniture", "Office Chair", "CHRR-030", 349.99));   // unknown type

    
        // Polymorphism — same loop works for every product type
        for(Product product : products) {
            if(product != null) {
                System.out.println("--- " + product.getClass().getSimpleName()  + " ---");
                System.out.println("\t" + product.getName() + ": Intructions: " + product.getHandlingInstructions());
            }
        }
    }

    public static void producerConsumerTest() {

        // queue to hold lines that have been read in and are waiting to be parsed
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(5);
        List<ProductRecord> validProducts = new LinkedList<>();
        List<String> errors = new LinkedList<>();
        String doneSignal = "---DONE---";

        // producer will read in data
        Thread producer = new Thread( () -> {
            InputStream inputStream = App.class.getClassLoader().getResourceAsStream("com/skillstorm/producer_consumer/products.csv");
            if(inputStream == null) {
                System.out.println("File was unable to be opened.");
                return; // exit if file cannot be opened
            }

            try(BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                String headerRow = br.readLine();   // taking out the header row of the CSV
            
                // reading in data and adding to queue
                String line;
                while((line = br.readLine()) != null) {     // null for EOF

                    System.out.println("PRODUCER IS READING A NEW LINE");

                    try {
                        queue.put(line);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }   

                // adding the done signal once it has finished
                try {
                    queue.put(doneSignal);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            } catch(IOException e) {
                System.out.println("IO EXCEPTION OCCURED\n" + e.getMessage());
            }
        }, "Producer Thread");

        // consumer will process that data
        Thread consumer = new Thread( () -> {
            while (true) {
                
                String line;
                try {
                    line = queue.take();

                    System.out.println("CONSUMER IS PARSING A NEW LINE");

                    // break when done
                    if (line.equals(doneSignal)) break;

                    try {
                        ProductRecord product = ProductRecord.parseRow(line);
                        validProducts.add(product);

                    } catch (IllegalArgumentException e) {
                        // Invalid row — log the error and keep processing the rest
                        errors.add(line);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "Consumer Thread");

        // start both threads 
        producer.start();
        consumer.start();

        // wait for both to finish before writing the final summary
        try {
            producer.join();
            consumer.join();
        } catch (InterruptedException e) {
            
        }

        // write results to file
        try(PrintWriter writer = new PrintWriter(new FileOutputStream("src\\main\\java\\com\\skillstorm\\producer_consumer\\ProductsReport.txt"))) {

            writer.println("=== VALID PRODUCTS ===");
            for(ProductRecord product : validProducts) {
                writer.println(product.toString());
            }

            writer.println("\n\n=== ERRORS ===");
            for(String err : errors) {
                writer.println(err);
            }
        }
        catch(FileNotFoundException e) {
            System.out.println("File could not be found.\n" + e.getMessage());
        }
    }
}

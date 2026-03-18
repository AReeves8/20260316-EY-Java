package multithreading;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

public class MultithreadingLaundry {

    public static void main(String[] args) {
        doingLaundry();
    }


    public static void doingLaundry() {

        /**
         * ArrayBlockingQueue
         *      - queue (FIFO) implemented with arrays
         *      - thread safe
         *          - auto sync'd across threads
         *          - only allows one thread to access it at a time
         * 
         *      - really useful when you don't need shared data to live a long time
         *      - better for manipulating data because you have to first remove the item in order to alter it
         */
        ArrayBlockingQueue<String> dryingBasket = new ArrayBlockingQueue<>(3);
        ArrayBlockingQueue<String> foldingBasket = new ArrayBlockingQueue<>(3);

        List<String> dirtyItems = List.of("tshirt", "jacket", "hoodie", "jeans", "socks", "underwear", "button-up");

        /**
         * SynchronizedList - thread safe version of a list
         *      - useful when you have longer lasting data you want to be shared between threads
         *      - better for data access, less used for data manipulation
         */
        List<String> foldedClothes = Collections.synchronizedList(new LinkedList<>());
        final String DONE_SIGNAL = "DONE";

        // making threads
        Thread washerThread = new Thread(new Washer(dirtyItems, dryingBasket, DONE_SIGNAL), "WASHER");
        Thread dryerThread = new Thread(new Dryer(dryingBasket, foldingBasket, DONE_SIGNAL), "DRYER");
        Thread folderThread = new Thread(new Folder(foldingBasket, foldedClothes, DONE_SIGNAL), "FOLDER");


        /**
         * Thread States
         *      New - thread is created, but not started
         *      Runnable - thread has been started/is running/waiting for CPU time (OS determines)
         *      Blocked - waiting to aquire a lock
         *      Waiting - waiting from the wait() -> only broken by notify()
         *      Timed_Waiting - waiting from sleep()
         *      Terminated - when run() has finished
         *
         * Synchronized Blocks
         *      synchronized keyword
         *      they lock a block of code when one thread enters it, 
         *      preventing other threads from entering the block, 
         *      until the first thread has finished
         */
        washerThread.start();
        dryerThread.start();
        folderThread.start();

        // puts main execution thread into waiting state for these threads to finish
        try{ 
            washerThread.join();
            dryerThread.join();
            folderThread.join();
        } catch(InterruptedException e) {
            System.out.println(e.getMessage());
        }
        
    }
}

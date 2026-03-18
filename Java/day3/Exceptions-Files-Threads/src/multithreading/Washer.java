package multithreading;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

public class Washer implements Runnable{

    private List<String> dirtyItems;                    // dirty clothes that are waiting to be washed
    private ArrayBlockingQueue<String> dryerbasket;     // washed clothes that are waiting to be dried
    private String doneSignal;                          // poison pill - other threads will wait until they see this signal


    public Washer(List<String> dirtyItems, ArrayBlockingQueue<String> dryerbasket, String doneSignal) {
        this.dirtyItems = dirtyItems;
        this.dryerbasket = dryerbasket;
        this.doneSignal = doneSignal;
    }

    /**
     * Runnable allows threads to be created for this class. 
     * 
     * run() is what is executed when the thread is running
     * 
     */
    @Override
    public void run() {
        try {

            for(String item : dirtyItems) {

                // making sure we don't wash the done signal
                if(!item.equals(doneSignal)) {
                    System.out.println("WASHER has begun washing " + item);

                    // simulating the time it takes to wash the item
                    Thread.sleep(3000);
                }

                // once the item is washed, add it to the drying basket to wait for the dryer
                dryerbasket.put(item);
            }

            System.out.println(Thread.currentThread().getName() + " HAS COMPLETED");
            dryerbasket.put(doneSignal);    // adding doneSignal to dryer so it can look for it

        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}

package multithreading;

import java.util.concurrent.ArrayBlockingQueue;

public class Dryer implements Runnable {
    ArrayBlockingQueue<String> dryingBasket;    // clothes that are waiting to be dried
    ArrayBlockingQueue<String> foldingBasket;   // clothes that are dried and waiting to be folded
    private String doneSignal;                  // poison pill - other threads will wait until they see this signal
    
    public Dryer(ArrayBlockingQueue<String> dryingBasket, ArrayBlockingQueue<String> foldingBasket, String doneSignal) {
        this.dryingBasket = dryingBasket;
        this.foldingBasket = foldingBasket;
        this.doneSignal = doneSignal;
    }

    @Override
    public void run() {

        try {

            while(true) {

                // take() will wait for an item to be available if necessary whereas poll would just return null
                String item = dryingBasket.take();

                // exiting loop whenever the done signal is reached
                if(item.equals(doneSignal)) {
                    foldingBasket.put(item);
                    break;
                }
                System.out.println("DRYER has begun drying " + item);

                // simulating drying time
                Thread.sleep(5000);

                // once the item is dried, add it to the basket to wait to be folded
                foldingBasket.put(item);
            }
            System.out.println(Thread.currentThread().getName() + " HAS COMPLETED");

        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

}

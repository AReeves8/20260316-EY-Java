package multithreading;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

public class Folder implements Runnable{
    
    ArrayBlockingQueue<String> foldingBasket;   // clothes that are dried and waiting to be folded
    List<String> foldedClothes;                 // clothes that have been folded
    private String doneSignal;                  // poison pill - other threads will wait until they see this signal
    
    public Folder(ArrayBlockingQueue<String> foldingBasket, List<String> foldedClothes, String doneSignal) {
        this.foldingBasket = foldingBasket;
        this.foldedClothes = foldedClothes;
        this.doneSignal = doneSignal;
    }

    @Override
    public void run() {

        try {
            while(true) {

                // take() will wait for an item to be available if necessary whereas poll would just return null
                String item = foldingBasket.take();
                
                // exiting loop whenever the done signal is reached
                if(item.equals(doneSignal)) {
                    break;
                }
                System.out.println("FOLDER has begun folding " + item);

                // simulating folding time
                Thread.sleep(1500);

                // once the item is dried, add it to the basket to wait to be folded
                foldedClothes.add(item);
            }
            System.out.println(Thread.currentThread().getName() + " HAS COMPLETED");

        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}

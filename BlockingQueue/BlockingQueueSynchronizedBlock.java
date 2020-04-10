import java.util.*;

public class BlockingQueueSynchronizedBlock{
    
    int maxSize = 0;
    LinkedList<Integer> queue = null;
    int count = 0;
    
    public BlockingQueueSynchronizedBlock(int size){
        maxSize = size;
        queue = new LinkedList<Integer>();
    }
    
    public synchronized void enqueue(int value) throws InterruptedException {
        while(queue.size() == maxSize){
            wait();
        }
        queue.add(value);
        notifyAll();
    }
    
    public synchronized int dequeue() throws InterruptedException {
        while(queue.size() == 0){
            wait();
        }
        int current = queue.remove();
        notifyAll();
        return current;
    }
    
    
    public static void main(String[] args){
        try{
            BlockingQueueSynchronizedBlock queue = new BlockingQueueSynchronizedBlock(10);
            
            Thread enqueue = new Thread(() ->{
               for(int i = 0; i < 50 ;i++)
                   try{
                   queue.enqueue(i);
                    System.out.println("enqueue value :" + i);
                    Thread.sleep(100);
                   }catch(InterruptedException e){
                       e.printStackTrace();
                   }
            });
            
            Thread dequeue = new Thread(() -> {
                while(true){
                    try{
                        System.out.println("dequeue value :" + queue.dequeue());    
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }
                }
            });
            
            enqueue.start();
            dequeue.start();
            
            enqueue.join();
            dequeue.join();
        }catch(Exception e){
            System.out.println("exception while running main method");
        }
    }
}

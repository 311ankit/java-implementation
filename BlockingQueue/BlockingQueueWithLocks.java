import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BlockingQueueWithLocks<T>{
    
    private Queue<T> queue = new LinkedList<T>();
    private int capacity;
    private Lock lock = new ReentrantLock();
    private Condition notFull = lock.newCondition();
    private Condition notEmpty = lock.newCondition();
    
    public BlockingQueueWithLocks(int capacity){
        this.capacity = capacity;
    }
    
    public void add(T element) throws InterruptedException{
        lock.lock();
        try{
            while(queue.size() == capacity){
                notFull.await();
            }
            queue.add(element);
            notEmpty.signal();
        }finally{
            lock.unlock();
        }
    }
    
    
    public T poll() throws InterruptedException{
        lock.lock();
        try{
            while(queue.isEmpty()){
                notEmpty.await();
            }
            T item = queue.remove();
            notFull.signal();
            return item;
        }finally{
            lock.unlock();
        }
    }
    
    public static void main(String args[]){
        
        BlockingQueueWithLocks<Integer> queue = new BlockingQueueWithLocks<Integer>(10);
        
        try{
        Thread enqueue = new Thread(() ->{
               for(int i = 0; i < 50 ;i++)
                   try{
                   queue.add(i);
                    System.out.println("enqueue value :" + i);
                    Thread.sleep(100);
                   }catch(InterruptedException e){
                       e.printStackTrace();
                   }
            });
            
            Thread dequeue = new Thread(() -> {
                while(true){
                    try{
                        System.out.println("dequeue value :" + queue.poll());    
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
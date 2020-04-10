import java.util.*;
import java.util.concurrent.*;

class Producer implements Runnable{
    
    Buffer buffer;
    int producerNumber;
    Counter count;
    
    public Producer(Buffer buffer, int producerNumber,  Counter count){
        this.buffer = buffer;
        this.producerNumber = producerNumber;
        this.count = count;
    }
    
    public void run(){
            try{
                while(true){
                    int currentcount = count.inc();
                    buffer.add(currentcount);                   
                    System.out.println("Producer: " + producerNumber + ", produced count: " + currentcount);
                    Thread.sleep(1000);            
                }
            }catch(InterruptedException e){
                e.printStackTrace();
            }   
        }
    }   

class Consumer implements Runnable{
    
    Buffer buffer;
    int consumerNumber;

    public Consumer(Buffer buffer, int consumerNumber){
        this.buffer = buffer;
        this.consumerNumber = consumerNumber;
    }
    
    public void run(){
            try{
                while(true){
                    int count = buffer.poll();                   
                    System.out.println("Consumer: " + consumerNumber + ", consumed count: " + count);
                    Thread.sleep(500);
                }
            }catch(InterruptedException e){
                e.printStackTrace();
            }   
        }
}

class Counter{
    private int count = 0;    
    public synchronized int inc(){
        return count++;
    }
}

public class MultipleProducerConsumer{
    
    public static void main(String[] args){
        
        Buffer buffer = new Buffer(3);
        Counter count = new Counter();
       
        ExecutorService pes = Executors.newFixedThreadPool(3);
        ExecutorService ces = Executors.newFixedThreadPool(3);
        
        Producer p1 = new Producer(buffer, 1, count);
        Producer p2 = new Producer(buffer, 2, count);
        pes.submit(p1);
        pes.submit(p2);
        
        ces.submit(new Consumer(buffer, 1));
        ces.submit(new Consumer(buffer, 2));
        
        pes.shutdown();
        ces.shutdown();
    }
}

class Buffer{
    
    LinkedList<Integer> buffer;
    int size = 0;
    
    public Buffer(int size){
        buffer = new LinkedList<Integer>();
        this.size = size;
    }
    
    public void add(int count) throws InterruptedException{
        synchronized(this){
            while(buffer.size() >= size)
                wait();
            buffer.add(count);
            notify();
        }
    }
    
    public int poll() throws InterruptedException{
        synchronized(this){
            while(buffer.size() == 0)
                wait();
            int count = buffer.poll();
            notify();
            return count;
        }  
    }
}

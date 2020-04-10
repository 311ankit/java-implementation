import java.util.*;

public class ProducerConsumer{
    
    public static void main(String[] args) throws InterruptedException{
        
        Buffer buffer = new Buffer(3);
        
        Thread producer = new Thread(() ->{
            try{
                int value = 0;
                while(true){
                    buffer.add(value);
                    System.out.println("produced value: " + value);
                    value++;
                    Thread.sleep(100);
                }
            }catch(InterruptedException e){
                e.printStackTrace();
            }

        });
        
        Thread consumer = new Thread(() ->{
            try{
                while(true){
                    int currentValue = buffer.poll();
                    System.out.println("consumed value: " + currentValue);
                    Thread.sleep(100);
                }
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        });
    
    producer.start();
    consumer.start();
    
    producer.join();
    consumer.join();
    }
}


class Buffer{
    
    LinkedList<Integer> buffer;
    int size = 0;
    
    public Buffer(int size){
        buffer = new LinkedList<Integer>();
        this.size = size;
    }
    
    public void add(int value) throws InterruptedException{
        synchronized(this){
            while(buffer.size() >= size)
                wait();
            buffer.add(value);
            notify();
        }
    }
    
    public int poll() throws InterruptedException{
        synchronized(this){
            while(buffer.size() == 0)
                wait();
            int value = buffer.poll();
            notify();
            return value;
        }  
    }
}
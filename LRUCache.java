import java.util.Map;
import java.util.HashMap;

public class LRUCache{
    
    
    private int capacity;
    private Map<Integer, DoublyLinkListNode> cache;
    private DoublyLinkListNode head;
    private DoublyLinkListNode tail;
    
    
    public LRUCache(int capacity){
        
        this.capacity = capacity;
        cache = new HashMap<>(capacity);
    }
    
    
    public int get(int key){
        
        if(cache.containsKey(key)){
            DoublyLinkListNode node = cache.get(key);
            moveToFront(node);
            return node.value;
        }
        return -1;
    }
    
    public void put(int key, int value){
        if(cache.containsKey(key)){
            DoublyLinkListNode node = cache.get(key);
            node.value = value;
            moveToFront(node);
            return;
        }
        
        DoublyLinkListNode node = new DoublyLinkListNode(key, value);
        
        if(cache.size() == capacity){
            cache.remove(tail.key);
            removeNode(tail);
        }
        
        cache.put(key, node);
        addFirst(node);
    }
    
    
    private void moveToFront(DoublyLinkListNode node){
        removeNode(node);
        addFirst(node);
    }
    
    private void removeNode(DoublyLinkListNode node){
        DoublyLinkListNode prevNode = node.prev;
        DoublyLinkListNode nextNode = node.next;
        
        if(prevNode != null)
            prevNode.next = nextNode;
        else
            head = nextNode;
        
        if(nextNode != null)
            nextNode.prev = prevNode;
        else
            tail = prevNode;
        
    }
    
    private void addFirst(DoublyLinkListNode node){
        
        node.next = head;
        node.prev = null;
        
        if(head != null)
            head.prev = node;
        
        head = node;
        
        if(tail == null)
            tail = node;
    }
}
import java.util.Scanner;

public class LRUCacheTest{
    
   public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);

        int capacity = keyboard.nextInt();
        LRUCache cache = new LRUCache(capacity);

        while (true) {
            String[] commandLine = keyboard.nextLine().trim().split("\\s");
            String command = commandLine[0];
            if (command.isEmpty()) {
                continue;
            }
            switch (command) {
            case "get": {
                int num = Integer.parseInt(commandLine[1]);
                System.out.println(cache.get(num));
                break;
            }
            case "put": {
                int key = Integer.parseInt(commandLine[1]);
                int value = Integer.parseInt(commandLine[2]);
                cache.put(key, value);
                break;
            }
            case "exit": {
                return;
            }
            default:
                System.out.println("Invalid command");
            }
        }
    }
}

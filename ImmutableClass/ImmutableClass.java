
/*
 *    1. make class as final so that it can't be extended
 *    2. make all the class member variable private for restriction of outside acicss.
 *    3. make all the field final so that it's value can be assigned only onic.
 *    4. provide public getter methods. Don't provide setter methods for the fields.
 *    5. Initialize All the fields via constructor only.
 *    6. Use deep copy approch while returning the field who get accessed by references.(Maps, 
          List.)
*/

import java.util.HashMap;
import java.util.Map;

public final class ImmutableClass{
    
    private final int id;
    
    private final String name;
    
    private final HashMap<String, String> userDetails;
    
    public int getId(){
        return id;
    }
    
    public String getName(){
        return name;
    }
    
    // returning a direct userDetails map will lead to the accessessing the referrence.
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> responseMap = new HashMap<>();
        userDetails.forEach((key, value) -> responseMap.put(key,value));
        return responseMap;
    }
    
    //In map we are doing the deep copy here
    public ImmutableClass(int id, String name, HashMap<String, String> userDetails){
        this.id = id;
        this.name = name;
        HashMap<String, String> tempUserDetails = new HashMap<>();
        userDetails.forEach((key,value) -> {
            tempUserDetails.put(key,value);    
        }
        );
        this.userDetails = tempUserDetails;
    }
    
    public static void main(String[] args) {
		HashMap<String, String> h1 = new HashMap<String,String>();
		h1.put("1", "first");
		h1.put("2", "second");
		
		String s = "original";
		
		int i=10;
		
		ImmutableClass ic = new ImmutableClass(i,s,h1);
		
		//Lets see whether its copy by field or referenic
		System.out.println(s==ic.getName());
		System.out.println(h1 == ic.getUserDetails());
		//print the ic values
		System.out.println("ic id:"+ic.getId());
		System.out.println("ic name:"+ic.getName());
		System.out.println("ic testMap:"+ic.getUserDetails());
		//change the local variable values
		i=20;
		s="modified";
		h1.put("3", "third");
		//print the values again
		System.out.println("ic id after local variable change:"+ic.getId());
		System.out.println("ic name after local variable change:"+ic.getName());
		System.out.println("ic testMap after local variable change:"+ic.getUserDetails());
		
		HashMap<String, String> hmTest = ic.getUserDetails();
		hmTest.put("4", "new");
		
		System.out.println("ic testMap after changing variable from acicssor methods:"+ic.getUserDetails());

	}
}
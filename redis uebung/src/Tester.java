import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import redis.clients.jedis.Jedis;


public class Tester {
	 private static final String filePath = "/Users/kai/Desktop/EclipseWorkspace/yelp_dataset/business.json";
	 
	 public static void main(String[] args){
	  try {
	   
//	   //Connecting to Redis server on localhost 
//	       Jedis jedis = new Jedis("localhost"); 
//	       System.out.println("Redis connection Ping... "+jedis.ping()); 
	        
	        
	   // read the json file
	   BufferedReader reader = new BufferedReader(new FileReader(filePath));
	   String newLine = "";
	   
	   JSONParser parser = new JSONParser();
	   JSONObject jsonObject = null;
	   
	   //while(newLine != null){
	   //erst mal nur X lines, später alles
	   int i;
	   for (i=0; i<5; i++){
	    
	    newLine = reader.readLine();
	    jsonObject = (JSONObject) parser.parse(newLine);
	    
	    System.out.println("Read File line: " + newLine);
	    
//	    jedis.set("business_id", newLine);
//	    System.out.println("Redis get: " + jedis.get("business_id"));
//	    
	    
	    
	    //here
	    
	    i++; //delete wenn final
	   }
	   

	   
	   reader.close();
	  } catch (FileNotFoundException ex) {
	   ex.printStackTrace();
	  } catch (IOException ex) {
	   ex.printStackTrace();
	  } catch (ParseException ex) {
	   ex.printStackTrace();
	  } catch (NullPointerException ex) {
	   ex.printStackTrace();
	  }
	 }
}

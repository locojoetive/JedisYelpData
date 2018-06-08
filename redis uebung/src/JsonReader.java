import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import redis.clients.jedis.Jedis;

public class JsonReader {
	
	private static final String filePath = "/home/hjk/Documents/Uni/BigData/data/dataset/business.json";
	
	
	public static void main(String[] args) {
		

		try {
			// reading the json file line by line
			BufferedReader breader = new BufferedReader(new FileReader(filePath));
			
			// Parsing and creating a JsonObject
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = null;
			
			//connecting to jedis
			Jedis jedis = new Jedis("localhost");
			
			Json2Redis j2r = new Json2Redis();
			
			String newLine="";
			int b = 0;
//			while(newLine != null) {
			while(b<5) {
				
				newLine = breader.readLine();
				jsonObject = (JSONObject) jsonParser.parse(newLine);

				j2r.loadBusiness(jsonObject, jedis);
				
//				j2r.loadBusinessHmap(jsonObject, jedis);
				
//
				
//				jedis.geoadd("business:"+businessId+":coordinates", (double) jsonObject.get("longitude"), (double) jsonObject.get("latitude"));
//
				
				b++;
				
			}

//
//			// get a String from the JSON object
//			for (int i=0; i<jsonArray.size(); i++) {
//				HashMap hm = new HashMap();
//				HashMap hm2 = new HashMap();
//				JSONObject jsonObject = (JSONObject)jsonArray.get(i);
//				
//				String name = (String) jsonObject.get("name");
//				String businessId = (String) jsonObject.get("business_id");
//				
//				
//				hm.put("name", (String) jsonObject.get("name"));
//				hm.put("neighborhood", (String) jsonObject.get("neighborhood"));
//				hm.put("address", (String) jsonObject.get("address"));
//				hm.put("city", (String) jsonObject.get("city"));
//				hm.put("state", (String) jsonObject.get("state"));
//				hm.put("postal code", (String) jsonObject.get("postal_code"));
//				hm2.put("latitude", (double) jsonObject.get("latitude"));
//				hm2.put("longitude", (double) jsonObject.get("longitude"));
//				hm.put("stars", ((Double)jsonObject.get("stars")).toString());
//				hm.put("review count", (long) jsonObject.get("review_count"));
//				hm.put("is open", (long) jsonObject.get("is_open"));
//				
//				jedis.hmset(businessId, hm);	
//				jedis.geoadd("coordinates", (double) jsonObject.get("longitude"), (double) jsonObject.get("latitude"), businessId);
//				jedis.sadd(businessId+":"))
//				
//				
//			}
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
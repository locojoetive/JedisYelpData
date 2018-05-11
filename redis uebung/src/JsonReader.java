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
	
	private static final String filePath = "C:\\Users\\hks_e\\Desktop\\businessSMALL.json";
	
	public static void main(String[] args) {


		try {
			// read the json file
			FileReader reader = new FileReader(filePath);

			JSONParser jsonParser = new JSONParser();
			JSONArray jsonArray = (JSONArray) jsonParser.parse(reader);
			
			Jedis jedis = new Jedis("localhost");
			

			// get a String from the JSON object
			for (int i=0; i<jsonArray.size(); i++) {
				HashMap hm = new HashMap();
				HashMap hm2 = new HashMap();
				JSONObject jsonObject = (JSONObject)jsonArray.get(i);
				String businessId = (String) jsonObject.get("business_id");
				hm.put("name", (String) jsonObject.get("name"));
				hm.put("neighborhood", (String) jsonObject.get("neighborhood"));
				hm.put("address", (String) jsonObject.get("address"));
				hm.put("city", (String) jsonObject.get("city"));
				hm.put("state", (String) jsonObject.get("state"));
				hm.put("postal code", (String) jsonObject.get("postal_code"));
				hm2.put("latitude", (double) jsonObject.get("latitude"));
				hm2.put("longitude", (double) jsonObject.get("longitude"));
				hm2.put("stars", (double) jsonObject.get("stars"));
				hm2.put("review count", (long) jsonObject.get("review_count"));
				hm2.put("is open", (long) jsonObject.get("is_open"));
				
				jedis.hmset(businessId, hm);	
				jedis.geoadd("coordinates", (double) jsonObject.get("longitude"), (double) jsonObject.get("latitude"), businessId);
				
			}

//			// get a number from the JSON object
//			long id =  (long) jsonObject.get("id");
//			System.out.println("The id is: " + id);
//
//			// get an array from the JSON object
//			JSONArray lang= (JSONArray) jsonObject.get("languages");
//			
//			// take the elements of the json array
//			for(int i=0; i<lang.size(); i++){
//				System.out.println("The " + i + " element of the array: "+lang.get(i));
//			}
//			Iterator i = lang.iterator();
//
//			// take each value from the json array separately
//			while (i.hasNext()) {
//				JSONObject innerObj = (JSONObject) i.next();
//				System.out.println("language "+ innerObj.get("lang") + 
//						" with level " + innerObj.get("knowledge"));
//			}
//			// handle a structure into the json object
//			JSONObject structure = (JSONObject) jsonObject.get("job");
//			System.out.println("Into job structure, name: " + structure.get("name"));

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
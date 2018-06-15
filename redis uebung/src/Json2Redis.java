import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;

import redis.clients.jedis.Jedis;

public class Json2Redis {

//	loads the complete business in to Redis with key as "business:"asdfasdf123:singleAttribute
	public void loadBusiness(JSONObject jsonObject, Jedis connection) {
		
		List<String> categories = new ArrayList<String>();
		
		String businessId = (String) jsonObject.get("business_id");
		categories = (List<String>) jsonObject.get("categories");
		
		connection.set("business:"+businessId+":name", (String) jsonObject.get("name"));
		connection.set("business:"+businessId+":neighborhood", (String) jsonObject.get("neighborhood"));
		connection.set("business:"+businessId+":address", (String) jsonObject.get("address"));
		connection.set("business:"+businessId+":city", (String) jsonObject.get("city"));
		connection.set("business:"+businessId+":state", (String) jsonObject.get("state"));
		connection.set("business:"+businessId+":postal_code", (String) jsonObject.get("postal_code"));
		connection.set("business:"+businessId+":stars", jsonObject.get("state").toString());
		connection.set("business:"+businessId+":review_count", jsonObject.get("review_count").toString());
		connection.set("business:"+businessId+":is_open", jsonObject.get("is_open").toString());
		
		
//	if categories exist => load categories as set
		for (int n = 0; n<categories.size(); n++) {
			connection.sadd("business:"+businessId+":categories", categories.get(n).toString());
		}
		
		HashMap hm = new HashMap();
		JSONObject hours =  (JSONObject) jsonObject.get("hours");
		
//	creates a Hashmap containing non-Null elements		
		hm = this.loadHours(hours);
		Object[] dayList = hm.keySet().toArray();
// 	adds hours as sorted set with key as business:businessId:hours:weekday 		
		for (int n = 0; n<hm.size(); n++) {
			connection.sadd("business:"+businessId+":hours:"+dayList[n], hm.get(dayList[n]).toString());
		}
		
	
	}



// 	load business as Hashmap
	public void loadBusinessHmap(JSONObject jsonObject, Jedis connection) {
		HashMap hm = new HashMap();
		HashMap<String, Double> hm2 = new HashMap();
		String businessId = (String) jsonObject.get("business_id");
		
		hm.put("name", (String) jsonObject.get("name"));
		hm.put("neighborhood", (String) jsonObject.get("neighborhood"));
		hm.put("address", (String) jsonObject.get("address"));
		hm.put("city", (String) jsonObject.get("city"));
		hm.put("state", (String) jsonObject.get("state"));
		hm.put("postal_code", (String) jsonObject.get("postal_code"));
		hm.put("stars", (jsonObject.get("stars")).toString());
		hm.put("review_count", jsonObject.get("review_count").toString());
		hm.put("is_open", jsonObject.get("is_open").toString());
		
		connection.hmset(businessId, hm);
	}
	
	
//	creates a Hashmap out of the business hours removing all null elements
	public HashMap loadHours(JSONObject hours) {
		HashMap hm = new HashMap();
		
		if (hours.containsKey("Monday"))
			hm.put("Monday", hours.get("Monday"));
		if (hours.containsKey("Tuesday"))
			hm.put("Tuesday", hours.get("Tuesday"));
		if (hours.containsKey("Wednesday"))
			hm.put("Wednesday", hours.get("Wednesday"));
		if (hours.containsKey("Thursday"))
			hm.put("Thursday", hours.get("Thursday"));
		if (hours.containsKey("Friday"))
			hm.put("Friday", hours.get("Friday"));
		if (hours.containsKey("Saturday"))
			hm.put("Saturday", hours.get("Saturday"));
		if (hours.containsKey("Sunday"))
			hm.put("Sunday", hours.get("Sunday"));		
		
		return hm;
	}
	
	public void loadAttributes(JSONObject jsonObject, Jedis connection) {
		String businessId = (String) jsonObject.get("business_id");
		JSONObject attributes = (JSONObject) jsonObject.get("attributes");
		Iterator<Map.Entry> itr1 = attributes.entrySet().iterator();
		   while (itr1.hasNext()) {
		       Map.Entry pair = itr1.next();
		       String t  = (String) pair.getValue().toString();
		        String f  = (String) pair.getKey().toString();
		             
		       if(pair.getValue() instanceof JSONObject){
		         
		         JSONObject use3 = (JSONObject) pair.getValue();
		         Iterator<Map.Entry> itr2 = use3.entrySet().iterator();
		         while (itr2.hasNext()) {
		             Map.Entry pair2 = itr2.next();
		             String t2  = (String) pair2.getValue().toString();
		             String f2  = (String) pair2.getKey().toString();         
		             
		             connection.hset("business:" + businessId + ":attributes:" + f, f2, t2);   
		             connection.hset("business:" + businessId + ":attributes", f, t);
		         }
		        }
		       else {
		        connection.hset("business:" + businessId + ":attributes", f, t);
		       }
		            
		   }
	
	}
}

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONObject;

import redis.clients.jedis.Jedis;

public class Json2Redis {

	
	//
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
		
	}
	
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
	
	
}

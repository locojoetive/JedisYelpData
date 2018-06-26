import org.json.simple.JSONObject;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

public class AutoComp {
	Jedis connection = null;
	
	AutoComp(Jedis jedis){
		this.connection = jedis;
	}
	
	public void loadAutoCompName(JSONObject jsonObject) {
		String name = jsonObject.get("name").toString();
		String businessid = jsonObject.get("business_id").toString();
		
		
		if(connection.smembers(name).size()==1) {
			System.out.println(name);
		}
		
		connection.sadd(name, businessid);
		
	}
	
}

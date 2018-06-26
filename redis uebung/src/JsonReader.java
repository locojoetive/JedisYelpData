import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

public class JsonReader {
	
	private static final String filePath = "/home/hjk/Documents/Uni/BigData/data/dataset/business.json";
	
	
	public static void main(String[] args) {
		

		try {
			
			// reading the json file line by line
			BufferedReader breader = new BufferedReader(new FileReader(filePath));
			// Parsing and creating a JsonObject
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = null;
			
			Jedis jedis = new Jedis("localhost");
			//connecting to the Redis-cluster
	        Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
	        jedisClusterNodes.add(new HostAndPort("127.0.0.1", 30001));
	        jedisClusterNodes.add(new HostAndPort("127.0.0.1", 30002));
	        jedisClusterNodes.add(new HostAndPort("127.0.0.1", 30003));
	        jedisClusterNodes.add(new HostAndPort("127.0.0.1", 30004));
	        jedisClusterNodes.add(new HostAndPort("127.0.0.1", 30005));
	        jedisClusterNodes.add(new HostAndPort("127.0.0.1", 30006));
			JedisCluster jedisCluster = new JedisCluster(jedisClusterNodes);
			
			Json2Redis j2r = new Json2Redis(jedis);
			AutoComp autocomp = new AutoComp(jedis);

			SearchBar searchBar = new SearchBar();
			
			searchBar.createGUI(jedis);
			
			//loads the Business in line for line
//			String newLine=breader.readLine();
//			while(newLine != null) {
//				jsonObject = (JSONObject) jsonParser.parse(newLine);
//				j2r.loadBusiness(jsonObject);
//				autocomp.loadAutoCompName(jsonObject);
//				
//				newLine = breader.readLine();	
//			}
			
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
//		} catch (ParseException ex) {
//			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}

	}

}
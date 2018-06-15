import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


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
			
			String newLine=breader.readLine();
			
			while(newLine != null) {

				jsonObject = (JSONObject) jsonParser.parse(newLine);
				j2r.loadBusiness(jsonObject, jedis);
				j2r.loadAttributes(jsonObject, jedis);

				
				newLine = breader.readLine();	
			}


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
import redis.clients.jedis.Jedis;

//run redis-server in terminal / cmd to start database server

public class RedisConnect {

	public void connectRedis() {
		try{
			Jedis jedis = new Jedis("localhost");
			System.out.println("Connection Successful");
			System.out.println("Server ping" + jedis.ping());
			
			// random
			//System.out.println("List push key value " + jedis.lpush("Max", "Karl"));
			//System.out.println("List pop Max: " + jedis.lpop("Max"));
			
			
			
			
		} catch(Exception e)
		{
			System.out.println(e);
		}

	}

}
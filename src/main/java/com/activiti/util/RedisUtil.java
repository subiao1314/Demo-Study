package com.activiti.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

public class RedisUtil {
	
	static ResourceBundle rbBundle = ResourceBundle.getBundle("redis");
	
	@SuppressWarnings("resource")
	public static ShardedJedis getRedisClient(){
		try {
			JedisPoolConfig config = new JedisPoolConfig();
			JedisShardInfo shardInfo = new JedisShardInfo(rbBundle.getString("redis.host"), rbBundle.getString("redis.port"));
			shardInfo.setPassword(rbBundle.getString("redis.password"));
			List<JedisShardInfo> shardInfos = Arrays.asList(shardInfo);
			ShardedJedisPool jedisPool = new ShardedJedisPool(config, shardInfos);
			ShardedJedis jedis = jedisPool.getResource();
			return jedis;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static void closeJedis(ShardedJedis jedis, boolean flag){
		if (flag) {
			jedis.close();
			jedis.resetState();
		} else {
			jedis.close();
		}
	}
	
	public static String setKeyValue(String key, String value){
		ShardedJedis jedis = getRedisClient();
		if (jedis == null) {
			return null;
		}
		boolean flag = true;
		try {
			String result = jedis.set(key, value);
			return result;
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		} finally {
			closeJedis(jedis, flag);
		}
		return null;
	}
	
	public static void main(String[] args) {
		Map<String, Object> m = new HashMap<String, Object>(2);
		int size = m.entrySet().size();
		m.put("hello", size);
		System.out.println(m.get("hello"));
	}
}

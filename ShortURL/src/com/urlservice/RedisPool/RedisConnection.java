package com.urlservice.RedisPool;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisConnection {

	private static JedisPool pool = null;

	public static void init(String host, int port, int timeOut, int dbNum) {
		if (pool == null) {
			JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
			pool = new JedisPool(jedisPoolConfig, host, port, timeOut, null, dbNum);

			System.err.println(
					"Create Pool:" + pool.getClass().getName() + "," + pool.getNumIdle() + "," + pool.getNumWaiters());
		}
	}

	public static Jedis getResource() {
		return pool.getResource();
	}

	public static void close() {
		if (pool != null) {
			pool.close();
		}
	}

}

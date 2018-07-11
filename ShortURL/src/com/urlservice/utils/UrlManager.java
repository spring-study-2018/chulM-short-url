package com.urlservice.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.urlservice.RedisPool.RedisConnection;

import redis.clients.jedis.Jedis;

public class UrlManager {

	private String genertateKey(String user, String orgURL) {

		Map<String, String> userMap = new HashMap<>();

		Jedis jedis = RedisConnection.getResource();
		String urlKey = jedis.hget(user, orgURL);

		if (urlKey == null) {
			String value = UUID.randomUUID().toString();
			userMap.put(orgURL, value);
			jedis.hmset(user, userMap);
			urlKey = value;
		} 
		// 데이터의 만료기간을 하루로 설정
		jedis.expire(user, (60 * 60 * 24));
		jedis.close();
		return urlKey;
	}

	public String generateURL(String user, String orgURL, String serverDomain) {

		String uuid = genertateKey(user, orgURL);

		StringBuffer shortURL = new StringBuffer();
		shortURL.append(serverDomain).append(uuid);

		return shortURL.toString();

	}
}

package com.example.service.redis;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import com.example.utils.Base62Utils;

@Configuration
public class RedisSession {

	@Resource(name="redisTemplate")
	private RedisTemplate<String, Object> redisTemplate;
	
	private String key;
	private String url;
	
	private int seq=0;

	/**
	 * 
	 * @param url(request url)
	 * @return key
	 */
	public String save(String url) {
		if(url==null) {
			return null;
		}
		//임시 시퀀스 로직 변경 후 삭제 
		seq++;
		
		key =(String)redisTemplate.opsForValue().get(url);
		
		if(key==null) {
			//db에서 데이터를 조회해서 시퀀스를 얻어온다
			//없으면 시퀀스 생성 후 db에 저장 
			/*
			 * db select and get seq
			 * 
			 */
			key = Base62Utils.encode(seq);
			System.out.println(key);
			redisTemplate.opsForValue().append(url, key);
			//데이터의 세션 만료는 하루 
			redisTemplate.expire(url, 24, TimeUnit.HOURS);
		}

		return key;
	}
	
	
	public String getKey() {
		return key;
	}
	public String getUrl() {
		return url;
	}
}

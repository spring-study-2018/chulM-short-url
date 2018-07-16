package com.example.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.service.redis.RedisSession;
import com.example.utils.ShortUrlUtils;

@RestController
public class ShortUrlController {

	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private RedisSession redisSession;
	
	@RequestMapping("/create")
	public String createShortUrl(@RequestParam("url") String url) {
		logger.info("request url: "+url);
		String key = redisSession.save(url);
		
		
		//exception is error page link 
		StringBuffer buffer = new StringBuffer();
		buffer.append(ShortUrlUtils.HOST).append(ShortUrlUtils.COLON)
			  .append(ShortUrlUtils.PORT).append(ShortUrlUtils.SEPERATOR).append(key);
		      
		return buffer.toString();
	}
	
}

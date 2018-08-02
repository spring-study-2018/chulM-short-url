package com.example.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.service.redis.RedisService;
import com.example.service.repository.MysqlService;
import com.example.utils.ShortUrlUtils;

@Controller
public class ShortUrlController {

	@Value("${server.address}")
	public String HOST;
	@Value("${server.port}")
	private String PORT;

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Resource(name = "mysqlService")
	private MysqlService mysql;

	@Resource(name = "redisService")
	private RedisService redis;

	// change regex
	@RequestMapping(value = "/{shortParam}", method = RequestMethod.GET)
	public String linkedShortUrl(@PathVariable String shortParam) {
		String linkedUrl = null;
		linkedUrl = redis.getValue(shortParam);
		
		logger.info("linking url : {}", linkedUrl);
		return "redirect:" + linkedUrl;
	}

	@ResponseBody
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String createShortUrl(@RequestParam("url") String url) {

		String key = redis.getKey(url);
		logger.trace("[" + getClass().getName() + "]:" + "generate key :" + key);

		StringBuffer buffer = new StringBuffer();
		buffer.append(HOST).append(ShortUrlUtils.COLON).append(PORT)
			  .append(ShortUrlUtils.SEPERATOR).append(key);

		return key;
	}
	// exception is error page link

}

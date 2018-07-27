package com.example.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.service.redis.RedisDao;
import com.example.service.repository.MysqlDao;
import com.example.utils.ShortUrlUtils;
import com.example.vo.UrlVO;

@RestController
public class ShortUrlController {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private RedisDao redisDao;
	
	@Autowired
	private MysqlDao mysqlDao;
	
	@RequestMapping("/create")
	public String createShortUrl(@RequestParam("url") String url) {
		logger.info("request url: " + url);

		String key = redisDao.getKey(url);

		if (key == null) {
			try {
				UrlVO urlVO = mysqlDao.selectUrl(url);
				key = urlVO.getKey();

				// not found url from mysql database
			} catch (EmptyResultDataAccessException e) {

				mysqlDao.insertUrl(url);
				UrlVO urlVO = mysqlDao.selectForLastUrl();
				key = redisDao.saveUrl(urlVO);
			}
		}
		StringBuffer buffer = new StringBuffer();
		buffer.append(ShortUrlUtils.HOST).append(ShortUrlUtils.COLON).append(ShortUrlUtils.PORT)
				.append(ShortUrlUtils.SEPERATOR).append(key);

		return buffer.toString();
	}
	//exception is error page link 
}

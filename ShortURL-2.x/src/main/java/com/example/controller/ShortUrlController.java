package com.example.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.service.redis.RedisDao;
import com.example.service.repository.MysqlDao;
import com.example.utils.Base62Utils;
import com.example.utils.ShortUrlUtils;
import com.example.vo.UrlVO;

@Controller
public class ShortUrlController {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private RedisDao redisDao;
	
	@Autowired
	private MysqlDao mysqlDao;
	
	//change regex 
	@RequestMapping(value="/{shortParam}", method=RequestMethod.GET)
	public String linkedShortUrl(@PathVariable String shortParam) {
		String linkedUrl = null;
		 linkedUrl = redisDao.getValue(shortParam);
		if(linkedUrl==null) {
			int seq = Base62Utils.decode(shortParam);
			try {
				UrlVO urlVO = mysqlDao.selectUrl(seq);
				//not catch
				linkedUrl = urlVO.getUrl();
			}catch (EmptyResultDataAccessException e) {
				//error link
		
			}
		}
		logger.info("linking url : {}" ,linkedUrl);
		return "redirect:"+linkedUrl;
	}
	
	@ResponseBody
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public String createShortUrl(@RequestParam("url") String url) {
		logger.info("request url: " + url);

		String key = redisDao.getKey(url);
		logger.trace("["+getClass().getName()+"]:"+"fist key step :" + key);
		if (key == null) {
			try {
				UrlVO urlVO = mysqlDao.selectUrl(url);
				key=Base62Utils.encode(urlVO.getSeq());
				logger.trace("["+getClass().getName()+"]:"+"second key step :" + key);
				
				// not found url from mysql database
			} catch (EmptyResultDataAccessException e) {
				logger.warn("exception occured : " + e.getMessage());
				mysqlDao.insertUrl(url);
				UrlVO urlVO = mysqlDao.selectForLastUrl();
				key = redisDao.saveUrl(urlVO);
				logger.trace("["+getClass().getName()+"]"+"third key step :" + key);
				
			}
		}
		StringBuffer buffer = new StringBuffer();
		buffer.append(ShortUrlUtils.HOST).append(ShortUrlUtils.COLON).append(ShortUrlUtils.PORT)
				.append(ShortUrlUtils.SEPERATOR).append(key);

		return buffer.toString();
	}
	//exception is error page link 
}

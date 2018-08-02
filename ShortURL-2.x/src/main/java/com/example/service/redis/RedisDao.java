package com.example.service.redis;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import com.example.utils.Base62Utils;
import com.example.utils.ShortUrlUtils;
import com.example.vo.UrlVO;

@Configuration
public class RedisDao {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource(name = "redisTemplate")
	private RedisTemplate<String, Object> redisTemplate;

	
	/**
	 * 
	 * @param url(request url)
	 * @return key
	 */
	public String getKey(String url) {
		if (url == null) {
			return null;
		}
	
		String key = (String) redisTemplate.opsForValue().get(url);
		return key;

	}
	
	public String getValue(String shortParam) {
		ValueOperations<String,Object> values = redisTemplate.opsForValue();
		Set<String> keys=values.getOperations().keys("*");
		String linkUrl = null;
		for (String key : keys) {
			if(shortParam.equals(values.get(key))) {
				linkUrl = (String)key;
				break;
			}
		}
		return linkUrl;
	}

	public String saveUrl(UrlVO urlVO) {
//		String key = Base62Utils.encode(urlVO.getSeq());
		String key = new Base62Utils().encode(urlVO.getSeq());
		urlVO.setKey(key);
		logger.debug("[" + getClass().getName() + "]" + " : " + " Generate Key = " + key);

		redisTemplate.opsForValue().append(urlVO.getUrl(), urlVO.getKey());
		// 데이터의 세션 만료는 하루
		redisTemplate.expire(urlVO.getUrl(), 24, TimeUnit.HOURS);
		logger.trace("[" + getClass().getName() + "]" + " : " + " save Redis Session");

		return urlVO.getKey();
	}
}

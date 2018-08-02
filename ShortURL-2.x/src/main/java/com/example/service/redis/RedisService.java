package com.example.service.redis;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.example.service.repository.MysqlService;
import com.example.utils.Base62Utils;
import com.example.vo.UrlVO;

@Service("redisService")
public class RedisService {

	@Autowired
	private RedisDao redisDao;

	@Resource(name = "mysqlService")
	private MysqlService mysql;

	public String getKey(String url) {

		String key = "";
		key = redisDao.getKey(url);
		System.err.println("-----_>" + url);
		if (key == null) {
			try {
				UrlVO urlVO = mysql.selectUrl(url);
				// key=Base62Utils.encode(urlVO.getSeq());
				key = new Base62Utils().encode(urlVO.getSeq());
				// not found url from mysql database
			} catch (EmptyResultDataAccessException e) {
				//
				int result = mysql.insertUrl(url);
				UrlVO urlVO = mysql.selectUrl(url);
				key = redisDao.saveUrl(urlVO);
			}
		}

		return key;
	}

	public String getValue(String shortParam) {
		String linkedUrl = redisDao.getValue(shortParam);
		if (linkedUrl == null) {
			// int seq = Base62Utils.decode(shortParam);
			int seq = new Base62Utils().decode(shortParam);
			try {
				UrlVO urlVO = mysql.selectUrl(seq);
				// not catch
				linkedUrl = urlVO.getUrl();
			} catch (EmptyResultDataAccessException e) {
				// error link
				return "not found";
			}
		}
		return linkedUrl;
	}
}

package com.example.service.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.vo.UrlVO;

@Service("mysqlService")
public class MysqlService {

	@Autowired
	private MysqlDao mysqlDao;
	
	public UrlVO selectUrl(int seq) {
		return mysqlDao.selectUrl(seq);
	}
	public UrlVO selectUrl(String url) {
		return mysqlDao.selectUrl(url);
	}
	
	public int insertUrl(String url) {
		return mysqlDao.insertUrl(url);
	}
	
	public UrlVO selectForLastUrl() {
		return mysqlDao.selectForLastUrl();
	}
}

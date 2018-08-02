package com.example.service.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.vo.UrlVO;

@Transactional
@Repository
public class MysqlDao {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public int insertUrl(String url) {
		String sql = "INSERT INTO T_SHORTURL(url) VALUES(?)";
		Object[] param = {url};
		int[] type = {Types.VARCHAR};
		int update = jdbcTemplate.update(sql, param,type);

//		logger.info("["+getClass().getName()+"]"+" : " +  " Insert Query =" + sql +", Result =" + update );

		return update;
	}

	public UrlVO selectUrl(String url) {
		String sql = "SELECT * FROM T_SHORTURL WHERE URL=?";
		return jdbcTemplate.queryForObject(sql,new Object[] {url}, new RowMapper<UrlVO>() {
			@Override
			public UrlVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				UrlVO urlVO = new UrlVO(rs.getInt(1), rs.getString(2));
				return urlVO;
			}
		});
	}
	
	public UrlVO selectUrl(int seq) {
		String sql = "SELECT * FROM T_SHORTURL WHERE seq=?";
		return jdbcTemplate.queryForObject(sql,new Object[] {seq}, new RowMapper<UrlVO>() {
			@Override
			public UrlVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				UrlVO urlVO = new UrlVO(rs.getInt(1), rs.getString(2));
				return urlVO;
			}
		});
	}
	
	public UrlVO selectForLastUrl() {
		String sql = "SELECT * FROM T_SHORTURL ORDER BY SEQ DESC LIMIT 1";
		return jdbcTemplate.queryForObject(sql, new RowMapper<UrlVO>() {
			@Override
			public UrlVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				UrlVO urlVO = new UrlVO(rs.getInt(1), rs.getString(2));
				return urlVO;
			}
		});
	}
}

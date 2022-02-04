package com.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.dao.ILoginDao;

@Repository
public class loginDaoimpl implements ILoginDao {

	@Autowired
	JdbcTemplate jdbc;
	
	@Override
	public boolean validateuser(String username, String password) {
		int count=jdbc.queryForObject("select count(*) from usrlogin where username=? and password=?",Integer.class,username,password);
		if(count==1)
		{
			return true;
		}
		else 
		return false;
	}

	
}


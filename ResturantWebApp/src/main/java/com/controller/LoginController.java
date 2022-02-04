package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dao.ILoginDao;

@Controller
public class LoginController {

	@Autowired
	ILoginDao loginDao;
	@GetMapping("/login")
	public String getloginpage() {
		return"login";
	}
	@PostMapping("/dologin")
	public String checkUser(@RequestParam("username") String username,@RequestParam("password") String password) {
		boolean loginflag=loginDao.validateuser(username, password);
		if(loginflag==true)
		{
			return "success";
		}else return "failure";
		
	}
}

package com.activiti.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.activiti.entity.ActUser;

public interface UserService {
	
	public Map<String, Object> doLogin(String name, String pwd, HttpSession session);
	
	public List<ActUser> queryAllUser();

}

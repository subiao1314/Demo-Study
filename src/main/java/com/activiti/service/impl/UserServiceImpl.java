package com.activiti.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.activiti.dao.ActUserMapper;
import com.activiti.entity.ActUser;
import com.activiti.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private ActUserMapper actUserMapper;
	
	@Override
	public Map<String, Object> doLogin(String name, String pwd, HttpSession session) {
		Map<String, Object> m = new HashMap<String, Object>();
		
		ActUser actUser = actUserMapper.selectByName(name);
		
		if (actUser == null) {
			m.put("succ", false);
			m.put("msg", "用户名或密码错误");
		} else {
			if (actUser.getPassword().equals(pwd)) {
				m.put("succ", true);
				m.put("msg", "登录成功");
				session.setAttribute("USER", actUser);
			} else {
				m.put("succ", false);
				m.put("msg", "用户名或密码错误");
			}
		}
		
		return m;
	}
	
	@Override
	public List<ActUser> queryAllUser(){
		return actUserMapper.queryAllUser();
	}

}

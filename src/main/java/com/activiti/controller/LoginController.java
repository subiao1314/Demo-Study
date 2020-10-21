package com.activiti.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.activiti.service.UserService;

@Controller
public class LoginController extends BaseController {
	
	@Autowired
	private UserService userService; 

	/**
	 * 跳转到登录页
	 * @return
	 */
	@RequestMapping("/login")
	public String showLoginPage(){
		return "/login/index";
	}
	
	/**
	 * 登录
	 * @param name
	 * @param pwd
	 * @return
	 */
	@RequestMapping("/login/doLogin")
	@ResponseBody
	public Map<String, Object> doLogin(String name, String pwd, HttpSession session){
		Map<String, Object> map = new HashMap<String, Object>();
		map = userService.doLogin(name, pwd, session);
		return map;
	}
	
	/**
	 * 登出
	 * @param session
	 * @return
	 */
	@RequestMapping("/logout")
	@ResponseBody
	public Map<String, Object> doLogout(HttpSession session){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			session.invalidate();
			map.put("succ", true);
			map.put("msg", "退出成功");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("succ", false);
			map.put("msg", "退出失败");
		}
		return map;
	}
	
	/**
	 * 跳转到登录页面
	 * @return
	 */
	@RequestMapping("/web/index")
	public ModelAndView showIndexPage(){
		return basePage("index/home", "index_", null);
	}
}

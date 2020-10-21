package com.activiti.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.servlet.ModelAndView;

public class BaseController {
	
	/**
	 * 返回页面的路径
	 * @param pageUrl
	 * @return
	 */
	public ModelAndView basePage(String pageUrl){
		return basePage(pageUrl, null, null, null);
	}
	
	/**
	 * 返回页面的路径、以及需要传给页面的数据
	 * @param pageUrl
	 * @param map
	 * @return
	 */
	public ModelAndView basePage(String pageUrl, Map<String, Object> map){
		return basePage(pageUrl, null, null, map);
	}
	
	/**
	 * 返回页面的路径、页面名称（唯一）、以及需要传给页面的数据
	 * @param pageUrl
	 * @param pageName
	 * @param map
	 * @return
	 */
	public ModelAndView basePage(String pageUrl, String pageName, Map<String, Object> map){
		return basePage(pageUrl, pageName, null, map);
	}

	/**
	 * 页面跳转汇总于这一个方法，每次返回的页面都是basePage.jsp
	 * @param pageUrl:需要跳转的jsp的路径
	 * @param pageName:跳转的jsp的名称，用于前端进行当前菜单的显示
	 * @param modalUrl:模态框的路径
	 * @param map:页面加载需要给页面返回的数据
	 * @return
	 */
	public ModelAndView basePage(String pageUrl, String pageName, String modalUrl, Map<String, Object> map){
		ModelAndView mad = new ModelAndView();
		Map<String, Object> m = new HashMap<String, Object>();
		mad.setViewName("/basePage");
		m.put("_pageUrl", pageUrl);
		if(pageName != null){
			m.put("_pageName", pageName);
		}
		if(modalUrl != null){
			m.put("_modalUrl", modalUrl);
		}
		if(map != null){
			for(String item : map.keySet()){
				m.put(item, map.get(item));
			}
		}
		mad.addAllObjects(m);
		return mad;
	}
}

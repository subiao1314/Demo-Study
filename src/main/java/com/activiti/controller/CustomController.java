package com.activiti.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.activiti.controller.pojo.ProcStartPojo;
import com.activiti.controller.pojo.UrlRedirectPojo;
import com.activiti.entity.ActBizLeave;
import com.activiti.entity.ActBizUrl;
import com.activiti.entity.ActUser;
import com.activiti.interceptor.MyAnnotation;
import com.activiti.service.BusinessService;
import com.activiti.service.ModelService;
import com.activiti.service.UserService;

@Controller
@RequestMapping("/web")
public class CustomController extends BaseController {
	
	@Autowired
	private ModelService modelService;
	
	@Autowired
	private UserService userService;

	@Autowired
	private BusinessService businessService;
	
	/**
	 * 跳转到我能启动的流程列表页
	 * @return
	 */
	@MyAnnotation(role="2")
	@RequestMapping("/myProc")
	public ModelAndView showMyProcList(){
		return basePage("myProc/list", "canStartProc_", null);
	}
	
	@RequestMapping("/getAllUsers")
	@ResponseBody
	public Map<String, Object> getAllUsers(){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<ActUser> userList = userService.queryAllUser();
			map.put("userList", userList);
			map.put("succ", true);
			map.put("msg", "查询成功");
		} catch (Exception e) {
			map.put("succ", false);
			map.put("msg", "查询失败");
		}
		return map;
	}
	
	/**
	 * 查询已部署的流程列表
	 * 
	 * @return
	 */
	@RequestMapping("/myProc/getMyProcList")
	@ResponseBody
	public Map<String, Object> getMyProcList() {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<String> procDesList = modelService.getProcDefList();
			map.put("myProcList", procDesList);
			map.put("succ", true);
			map.put("msg", "查询成功");
		} catch (Exception e) {
			map.put("succ", false);
			map.put("msg", "查询失败");
		}
		return map;
	}
	
	/**
	 * 跳转到新增表单页
	 * @return
	 */
	@RequestMapping("/myProc/form")
	public ModelAndView showMyProcForm(String name){
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("key", name);
		return basePage("myProc/form", "canStartProc_", m);
	}
	
	/**
	 * 通过流程定义的key进行启动流程
	 * 
	 * @param key
	 * @return
	 */
	@RequestMapping("/procDef/getStart")
	@ResponseBody
//	public Map<String, Object> getStart(String key, String bizId, Map<String, Object> variable, HttpSession session) {
	public Map<String, Object> getStart(@RequestBody ProcStartPojo pojo, HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		String key = pojo.getKey();
		String bizId = pojo.getBizId();
		Map<String, Object> variable = pojo.getVariable();
		map = modelService.getStart(key, bizId, variable, session);
		return map;
	}
	
	/**
	 * 通过流程定义的key进行启动流程,并给第一个节点赋予并发
	 * 
	 * @param key
	 * @return
	 */
	@RequestMapping("/procDef/getStartConcurrency")
	@ResponseBody
//	public Map<String, Object> getStart(String key, String bizId, Map<String, Object> variable, HttpSession session) {
	public Map<String, Object> getStartConcurrency(@RequestBody ProcStartPojo pojo, HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		String key = pojo.getKey();
		String bizId = pojo.getBizId();
		Map<String, Object> variable = pojo.getVariable();
		String names = (String) variable.get("users");
		String[] nameList = names.split(",");
		variable.put("users", Arrays.asList(nameList));
		map = modelService.getStart(key, bizId, variable, session);
		return map;
	}
	
	/**
	 * 跳转到我的代办流程
	 * 
	 * @return
	 */
	@RequestMapping("/myUndeal")
	public ModelAndView showMyUndealPage() {
		return basePage("myUndeal/list", "myUndeal_", null);
	}
	
	/**
	 * 跳转到处理我的流程的页面
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/myUndeal/form")
	public String showDealMyProcPage(String procInstId, String taskId, RedirectAttributes model) throws IOException{
		UrlRedirectPojo pojo = new UrlRedirectPojo();
		ActBizLeave actBizLeave = modelService.getBizByProcInstId(procInstId);
		pojo.setBizId(actBizLeave.getId().toString());
		pojo.setProcId(procInstId);
		pojo.setTaskId(taskId);
		Task task = modelService.findTaskBy(taskId);
		String task_id = task.getTaskDefinitionKey();
		String prco_id = task.getProcessDefinitionId();
		List<ActBizUrl> urls = businessService.queryUrlListByProcIdAndTaskId(prco_id, task_id);
//		response.sendRedirect(request.getContextPath() + url + "?args=" + pojo);
		model.addFlashAttribute("pojo", pojo);
		return "redirect:/"+urls.get(0).getTaskUrl();
	}
	
	@RequestMapping("/myUndeal/showForm")
	public ModelAndView showDealMyProcPage(@ModelAttribute("pojo") UrlRedirectPojo pojo){
		Map<String, Object> map = new HashMap<String, Object>();
		//modelService.sendAssigneeTo(taskId, "lisi");
		ActBizLeave actBizLeave = modelService.getBizByProcInstId(pojo.getProcId());
		map.put("procInstId", pojo.getProcId());
		map.put("taskId", pojo.getTaskId());
		map.put("startDate", actBizLeave.getStartDate());
		map.put("endDate", actBizLeave.getEndDate());
		map.put("days", actBizLeave.getDays());
		map.put("discrition", actBizLeave.getReason());
		return basePage("myUndeal/form", "myUndeal_", map);
	}

	/**
	 * 查询我的代办流程(单个user)
	 * 
	 * @return
	 */
	@RequestMapping("/myUndeal/getMyUndealList")
	@ResponseBody
	public Map<String, Object> getMyUndealList(HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		ActUser user = (ActUser) session.getAttribute("USER");
		if (user!=null && user.getUserName()!=null) {
			try {
				List<String> tasks = modelService.getMyUndealList(user.getUserName());
				map.put("taskList", tasks);
				map.put("succ", true);
				map.put("msg", "查询成功");
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				map.put("succ", false);
				map.put("msg", "查询失败");
			}
		} else {
			map.put("succ", false);
			map.put("msg", "查询失败");
		}

		return map;
	}
	
	/**
	 * 查询我的代办流程(多个user)
	 * 
	 * @return
	 */
	@RequestMapping("/myUndeal/getCandicateTaskList")
	@ResponseBody
	public Map<String, Object> getCandicateTaskList(HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		ActUser user = (ActUser) session.getAttribute("USER");
		if (user!=null && user.getUserName()!=null) {
			try {
				//List<String> tasks = modelService.getMyUndealList(user.getUserName());
				List<String> tasks = modelService.getCandicateTaskList(user.getUserName());
				map.put("taskList", tasks);
				map.put("succ", true);
				map.put("msg", "查询成功");
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				map.put("succ", false);
				map.put("msg", "查询失败");
			}
		} else {
			map.put("succ", false);
			map.put("msg", "查询失败");
		}

		return map;
	}
	
	/**
	 * 抢占task
	 * 
	 * @return
	 */
	@RequestMapping("/myUndeal/claim")
	@ResponseBody
	public Map<String, Object> claimTask(String taskId, HttpSession session){
		Map<String, Object> map = new HashMap<String, Object>();
		ActUser user = (ActUser) session.getAttribute("USER");
		try {
			modelService.claimTask(taskId, user.getUserName());
			map.put("succ", true);
			map.put("msg", "抢占成功");
		} catch (Exception e) {
			map.put("succ", false);
			map.put("msg", "抢占失败");
		}
		return map;
	}

	/**
	 * 确认流程
	 * 
	 * @param taskId
	 * @return
	 */
	@RequestMapping("/myUndeal/conformTask")
	@ResponseBody
	public Map<String, Object> conformTask(@RequestBody ProcStartPojo pojo) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		String taskId = pojo.getTaskId();
		Map<String, Object> variable = pojo.getVariable();
		
		map = modelService.conformTask(taskId, variable);
		return map;
	}

}

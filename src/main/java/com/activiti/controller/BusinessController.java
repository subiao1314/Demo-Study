package com.activiti.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.activiti.entity.ActBizTaskResult;
import com.activiti.service.BusinessService;

@Controller
@RequestMapping("/biz")
public class BusinessController extends BaseController {
	
	@Autowired
	private BusinessService businessService;

	/**
	 * 保存业务数据
	 * @param startDate
	 * @param endDate
	 * @param days
	 * @param reason
	 * @return
	 */
	@RequestMapping("/commitBiz")
	@ResponseBody
	public Map<String, Object> commitBiz(String startDate, String endDate, Long days, String reason){
		Map<String, Object> map = new HashMap<String, Object>();
		map = businessService.insertBiz(startDate, endDate, days, reason);
		return map;
	}
	
	/**
	 * 获取流程实例id为procInstId的所有ACT_BIZ_TASK_RESULT的结果列表
	 * @param procInstId
	 * @return
	 */
	@RequestMapping("/getAllProcHis")
	@ResponseBody
	public Map<String, Object> getAllProcHis(String procInstId){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<ActBizTaskResult> procHisList = businessService.queryAllProcHis(procInstId);
			map.put("procHisList", procHisList);
			map.put("succ", true);
			map.put("msg", "查询成功");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("succ", false);
			map.put("msg", "查询失败");
		}
		return map;
	}
	
	/**
	 * 保存审批结果
	 * @param agree
	 * @param suggestion
	 * @param taskId
	 * @param procInstId
	 * @return
	 */
	@RequestMapping("/commitBizResult")
	@ResponseBody
	public Map<String, Object> commitBizResult(String agree, String suggestion, String taskId, String procInstId){
		Map<String, Object> map = new HashMap<String, Object>();
		map = businessService.insertBizResult(agree, suggestion, taskId, procInstId);
		return map;
	}
}

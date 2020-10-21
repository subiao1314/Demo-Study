package com.activiti.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.activiti.dao.ActBizLeaveMapper;
import com.activiti.dao.ActBizTaskResultMapper;
import com.activiti.dao.ActBizUrlMapper;
import com.activiti.entity.ActBizLeave;
import com.activiti.entity.ActBizTaskResult;
import com.activiti.entity.ActBizUrl;
import com.activiti.service.BusinessService;

@Service
public class BusinessServiceImpl implements BusinessService {
	
	@Autowired
	private ActBizLeaveMapper actBizLeaveMapper;
	@Autowired
	private ActBizTaskResultMapper actBizTaskResultMapper;
	@Autowired
	private ActBizUrlMapper actBizUrlMapper;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private TaskService taskService;

	/**
	 * 保存业务数据
	 */
	@Override
	public Map<String, Object> insertBiz(String startDate, String endDate, Long days, String reason) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			ActBizLeave actBizLeave = new ActBizLeave();
			actBizLeave.setDays(days);;
			actBizLeave.setStartDate(startDate);
			actBizLeave.setEndDate(endDate);
			actBizLeave.setCreateTime(new Date());
			actBizLeave.setReason(reason);
			actBizLeave.setUpdateTime(new Date());
			actBizLeaveMapper.insertSelective(actBizLeave);
			map.put("bizId", actBizLeave.getId());
			map.put("succ", true);
			map.put("msg", "提交成功");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("succ", false);
			map.put("msg", "提交失败");
		}
		return map;
	}

	/**
	 * 查询流程实例id为procInstId的所有ACT_BIZ_TASK_RESULT的结果列表
	 */
	@Override
	public List<ActBizTaskResult> queryAllProcHis(String procInstId) {
		return actBizTaskResultMapper.queryAllProcHis((long) Integer.parseInt(procInstId));
	}

	/**
	 * 保存审批结果数据
	 */
	@Override
	public Map<String, Object> insertBizResult(String agree, String suggestion, String taskId, String procInstId) {
		ActBizTaskResult actBizTaskResult = new ActBizTaskResult();
		Map<String, Object> map = new HashMap<String, Object>();
		
		try {
			HistoricProcessInstance hpi = historyService.createHistoricProcessInstanceQuery().processInstanceId(procInstId).singleResult();
			Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
			
			actBizTaskResult.setBizId((long) Integer.parseInt(hpi.getBusinessKey()));
			actBizTaskResult.setProcId((long) Integer.parseInt(procInstId));
			actBizTaskResult.setTaskId(taskId);
			actBizTaskResult.setTaskName(task.getName());
			actBizTaskResult.setAssignee(task.getAssignee());
			actBizTaskResult.setDescribtion(suggestion);
			actBizTaskResult.setCreateTime(new Date());
			actBizTaskResult.setIsAgree(agree);
			
			actBizTaskResultMapper.insertSelective(actBizTaskResult);
			map.put("succ", true);
			map.put("msg", "提交成功");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("succ", false);
			map.put("msg", "提交失败");
		}
		
		return map;
	}

	/**
	 * 根据部署ID查询改流程的url列表
	 */
	@Override
	public List<ActBizUrl> queryUrlListBy(String deploy_id) {
		return actBizUrlMapper.queryUrlListBy(deploy_id);
	}

	@Override
	public void insertUrlList(List<ActBizUrl> urlList) {
		String deployId = urlList.get(0).getDeployId();
		List<ActBizUrl> exitUrlList = actBizUrlMapper.queryUrlListBy(deployId);
		if (exitUrlList.size() > 0) {
			for (int j=0; j<exitUrlList.size(); j++) {
				actBizUrlMapper.updateByPrimaryKeySelective(exitUrlList.get(j));
			}
		}
		for (int i=0; i<urlList.size(); i++) {
			actBizUrlMapper.insertSelective(urlList.get(i));
		}
	}

	@Override
	public List<ActBizUrl> queryUrlListByProcIdAndTaskId(String procId, String taskId) {
		return actBizUrlMapper.queryUrlListByProcIdAndTaskId(procId, taskId);
	}

}

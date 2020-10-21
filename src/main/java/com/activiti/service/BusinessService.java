package com.activiti.service;

import java.util.List;
import java.util.Map;

import com.activiti.entity.ActBizTaskResult;
import com.activiti.entity.ActBizUrl;

public interface BusinessService {

	//保存业务数据
	public Map<String, Object> insertBiz(String startDate, String endDate, Long days, String reason);
	//查询流程实例id为procInstId的所有ACT_BIZ_TASK_RESULT的结果列表
	public List<ActBizTaskResult> queryAllProcHis(String procInstId);
	//保存审批结果数据
	public Map<String, Object> insertBizResult(String agree, String suggestion, String taskId, String procInstId);
	//根据部署ID查询改流程的url列表
	public List<ActBizUrl> queryUrlListBy(String deploy_id);
	//插入流程url的列表
	public void insertUrlList(List<ActBizUrl> urlList);
	//根据流程ID和任务节点ID查询改流程的url列表
	public List<ActBizUrl> queryUrlListByProcIdAndTaskId(String procId, String taskId);
}

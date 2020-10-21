package com.activiti.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.activiti.engine.repository.Model;
import org.activiti.engine.task.Task;

import com.activiti.entity.ActBizLeave;

public interface ModelService {

	//查询所有的model
	public List<Model> getModelList();
	//通过model ID，部署流程
	public Map<String, Object> getDeploy(String modelId);
	//查询所有已部署的流程
	public List<String> getProcDefList();
	//启动流程
	public Map<String, Object> getStart(String key, String bizId, Map<String, Object> variable, HttpSession session);
	//查询所有属于assignee的代办流程(单个user节点)
	public List<String> getMyUndealList(String assignee);
	//查询所有属于assignee的代办流程(多节点，抢占)
	public List<String> getCandicateTaskList(String assignee);
	//完成自己的代办节点
	public Map<String, Object> conformTask(String taskId, Map<String, Object> variable);
	//设置单个流程变量
	public boolean setVariable(String taskId, String name, Object value);
	//以map的形式设置流程变量
	public boolean setVariables(String taskId, Map<String, Object> map);
	//获取单个流程变量
	public Object getVariable(String taskId, String name);
	//获取某个流程的所有流程变量
	public Map<String, Object> getAllVaribles(String taskId);
	//获取业务信息
	public ActBizLeave getBizByProcInstId(String procInstId);
	//抢占task
	public void claimTask(String taskId, String assignee);
	//转发（将自己的待办交由别人完成）
	public void sendAssigneeTo(String taskId, String assignee);
	//获取task对象
	public Task findTaskBy(String taskId);
}

package com.activiti.controller.pojo;

import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class ProcStartPojo {

	private String key;
	
	private String bizId;
	
	private Map<String, Object> variable;
	
	private String taskId;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getBizId() {
		return bizId;
	}

	public void setBizId(String bizId) {
		this.bizId = bizId;
	}

	public Map<String, Object> getVariable() {
		return variable;
	}

	public void setVariable(Map<String, Object> variable) {
		this.variable = variable;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	
	
}

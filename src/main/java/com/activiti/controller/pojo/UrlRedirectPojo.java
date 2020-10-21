package com.activiti.controller.pojo;

import java.util.List;

import com.activiti.entity.ActBizUrl;

public class UrlRedirectPojo {

	private String procId;
	private String bizId;
	private String taskId;
	private List<ActBizUrl> urlList;

	public String getProcId() {
		return procId;
	}

	public void setProcId(String procId) {
		this.procId = procId;
	}

	public String getBizId() {
		return bizId;
	}

	public void setBizId(String bizId) {
		this.bizId = bizId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public List<ActBizUrl> getUrlList() {
		return urlList;
	}

	public void setUrlList(List<ActBizUrl> urlList) {
		this.urlList = urlList;
	}

}

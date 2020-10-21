package com.activiti.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.activiti.dao.ActBizLeaveMapper;
import com.activiti.dao.ActBizTaskResultMapper;
import com.activiti.entity.ActBizLeave;
import com.activiti.entity.ActBizTaskResult;
import com.activiti.entity.ActUser;
import com.activiti.service.ModelService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Service
public class ModelServiceImpl implements ModelService {

	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private ActBizLeaveMapper actBizLeaveMapper;
	@Autowired
	private ActBizTaskResultMapper actBizTaskResultMapper;

	/**
	 * 查询所有的model
	 */
	@Override
	public List<Model> getModelList() {
		List<Model> modelList = repositoryService.createModelQuery().list();
		return modelList;
	}

	/**
	 * 通过model ID，部署流程
	 */
	@Override
	public Map<String, Object> getDeploy(String modelId) {
		Map<String, Object> map = new HashMap<String, Object>();
		Model modelData = repositoryService.getModel(modelId);
		try {
			ObjectNode modelNode = (ObjectNode) new ObjectMapper()
					.readTree(repositoryService.getModelEditorSource(modelData.getId()));
			byte[] bpmnBytes = null;
			BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
			bpmnBytes = new BpmnXMLConverter().convertToXML(model);
			String proccessName = modelData.getName() + ".bpmn20.xml";
			Deployment deployment = repositoryService.createDeployment().name(modelData.getName())
					.addString(proccessName, new String(bpmnBytes)).deploy();
			if (deployment != null && deployment.getId() != null) {
				map.put("succ", true);
				map.put("msg", "部署成功");
			} else {
				map.put("succ", false);
				map.put("msg", "部署失败");
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			map.put("succ", false);
			map.put("msg", "部署失败");
		} catch (IOException e) {
			e.printStackTrace();
			map.put("succ", false);
			map.put("msg", "部署失败");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("succ", false);
			map.put("msg", "部署失败");
		}

		return map;
	}

	/**
	 * 查询所有已部署的流程
	 */
	@Override
	public List<String> getProcDefList() {
		List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().latestVersion().list();
		List<String> listString = new ArrayList<String>();
		/*
		 * if (processDefinitions!=null && processDefinitions.size()>0) {
		 * Map<String, ProcessDefinition> map = new HashMap<String,
		 * ProcessDefinition>(); for (ProcessDefinition processDefinition :
		 * processDefinitions) { map.put(processDefinition.getKey(),
		 * processDefinition); } Collection<ProcessDefinition> valueCollection =
		 * map.values(); list = new ArrayList<>(valueCollection); }
		 */
		// 因为不能将流程定义以列表的形式返回给controller，所以在这里对自己想要的数据进行封装
		if (list != null && list.size() > 0) {
			for (ProcessDefinition pd : list) {
				String s = null;
				if (pd.getDescription() == null) {
					s = pd.getName() + "-" + pd.getKey() + "-" + pd.getVersion() + "-" + " " + "-"
							+ pd.getDeploymentId();
				} else {
					s = pd.getName() + "-" + pd.getKey() + "-" + pd.getVersion() + "-" + pd.getDescription() + "-"
							+ pd.getDeploymentId();
				}
				listString.add(s);
			}
		}

		return listString;
	}

	/**
	 * 启动流程
	 */
	@Override
	public Map<String, Object> getStart(String key, String bizId, Map<String, Object> variable, HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		ActBizTaskResult actBizTaskResult = new ActBizTaskResult();
		
		try {
			// runtimeService.startProcessInstanceByKey(key);
			ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(key, bizId, variable);// 通过流程的key、业务ID和流程变量进行流程的启动
			System.out.println(processInstance.getId() + "---" + processInstance.getProcessInstanceId());
			// 启动成功后，在 ACT_BIZ_TASK_RESULT 表中添加申请人的信息
			actBizTaskResult.setBizId((long) Integer.parseInt(bizId));
			actBizTaskResult.setProcId((long) Integer.parseInt(processInstance.getProcessInstanceId()));
			actBizTaskResult.setTaskName(key);
			actBizTaskResult.setAssignee(((ActUser) session.getAttribute("USER")).getUserName());
			actBizTaskResult.setDescribtion("流程申请人");
			actBizTaskResult.setCreateTime(new Date());
			actBizTaskResult.setIsAgree("1");
			actBizTaskResultMapper.insertSelective(actBizTaskResult);
			map.put("succ", true);
			map.put("msg", "流程启动成功");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("succ", false);
			map.put("msg", "流程启动失败");
		}
		return map;
	}

	/**
	 * 查询所有属于assignee的代办流程
	 */
	@Override
	public List<String> getMyUndealList(String assignee) {
		List<String> tasks = new ArrayList<String>();
		List<Task> taskList = taskService.createTaskQuery().taskAssignee(assignee).orderByTaskCreateTime().desc().list();

		if (taskList != null && taskList.size() > 0) {
			for (Task task : taskList) {
				String s = null;
				s = task.getAssignee() + "-" + task.getCreateTime() + "-" + task.getId() + "-"
						+ task.getProcessInstanceId();
				tasks.add(s);
			}
		}

		return tasks;
	}

	/**
	 * 完成自己的代办节点
	 */
	@Override
	public Map<String, Object> conformTask(String taskId, Map<String, Object> variable) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		try {
//			Map<String, Object> m = new HashMap<String, Object>();
//			m.put("person", person);
			// taskService.complete(taskId);
			Object count = getVariable(taskId, "count");
			if (count!=null && variable.get("count")!=null) {
				variable.put("count", Integer.parseInt((variable.get("count")).toString()) + 1);
			}
			taskService.complete(taskId, variable);
			map.put("succ", true);
			map.put("msg", "确认成功");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("succ", false);
			map.put("msg", "确认失败");
		}

		return map;
	}

	/**
	 * 设置单个流程变量
	 */
	@Override
	public boolean setVariable(String taskId, String name, Object value) {
		try {
			taskService.setVariable(taskId, name, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 以map的形式设置流程变量
	 */
	@Override
	public boolean setVariables(String taskId, Map<String, Object> map) {
		try {
			taskService.setVariables(taskId, map);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 获取单个流程变量
	 */
	@Override
	public Object getVariable(String taskId, String name) {

		try {
			Object value = taskService.getVariable(taskId, name);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取某个流程的所有流程变量
	 */
	@Override
	public Map<String, Object> getAllVaribles(String taskId) {
		try {
			Map<String, Object> map = taskService.getVariables(taskId);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取业务信息
	 */
	@Override
	public ActBizLeave getBizByProcInstId(String procInstId) {
		// Execution execution =
		// runtimeService.createExecutionQuery().processInstanceId(procInstId).singleResult();
		HistoricProcessInstance hpi = historyService.createHistoricProcessInstanceQuery().processInstanceId(procInstId)
				.singleResult();
		ActBizLeave actBizLeave = actBizLeaveMapper.selectByPrimaryKey((long) Integer.parseInt(hpi.getBusinessKey()));
		return actBizLeave;
	}
	
	/**
	 * 查询所有属于assignee的代办流程(多节点,抢占)
	 */
	@Override
	public List<String> getCandicateTaskList(String assignee) {
		List<String> tasks = new ArrayList<String>();
		List<Task> taskList = taskService.createTaskQuery().taskCandidateUser(assignee).orderByTaskCreateTime().desc().list();
		
		if (taskList != null && taskList.size() > 0) {
			for (Task task : taskList) {
				String s = null;
				s = task.getAssignee() + "-" + task.getCreateTime() + "-" + task.getId() + "-"
						+ task.getProcessInstanceId();
				tasks.add(s);
			}
		}
		
		return tasks;
	}

	/**
	 * 抢占task(签收任务)
	 */
	@Override
	public void claimTask(String taskId, String assignee) {
		taskService.claim(taskId, assignee);
//		taskService.unclaim(taskId);
	}

	/**
	 * 转发（将自己的待办交由别人完成）
	 */
	@Override
	public void sendAssigneeTo(String taskId, String assignee) {
		taskService.setAssignee(taskId, assignee);
	}

	@Override
	public Task findTaskBy(String taskId) {
		return taskService.createTaskQuery().taskId(taskId).singleResult();
	}
}

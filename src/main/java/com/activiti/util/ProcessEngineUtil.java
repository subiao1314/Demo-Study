package com.activiti.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.activiti.controller.pojo.ProcStartPojo;
import com.activiti.entity.ActBizTaskResult;
import com.activiti.entity.ActUser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class ProcessEngineUtil {
	
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private TaskService taskService;

	/**
	 * 跳转到画流程图的modeler界面
	 * @param name
	 * @param key
	 * @param description
	 * @param request
	 * @param response
	 * @return
	 */
	public Map<String, Object> showModelPage(@RequestParam("name") String name, @RequestParam("key") String key,
			@RequestParam(value = "description", required = false) String description, HttpServletRequest request,
			HttpServletResponse response){
		
		Map<String, Object> m = new HashMap<String, Object>();

		try {
			ObjectMapper objectMapper = new ObjectMapper();
			ObjectNode editorNode = objectMapper.createObjectNode();
			editorNode.put("id", "canvas");
			editorNode.put("resourceId", "canvas");
			ObjectNode stencilSetNode = objectMapper.createObjectNode();
			stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
			editorNode.set("stencilset", stencilSetNode);
			Model modelData = repositoryService.newModel();

			ObjectNode modelObjectNode = objectMapper.createObjectNode();
			modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, name);
			modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
			modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
			modelData.setMetaInfo(modelObjectNode.toString());
			modelData.setName(name);
			modelData.setKey(key);

			repositoryService.saveModel(modelData);
			repositoryService.addModelEditorSource(modelData.getId(), editorNode.toString().getBytes("utf-8"));

			//这里是跳转的jsp地址，该跳转由前端来执行
			// response.sendRedirect(request.getContextPath() +
			// "/modeler.html?modelId=" + modelData.getId());

			m.put("succ", true);
			m.put("id", modelData.getId());

		} catch (Exception e) {
			e.printStackTrace();
			m.put("succ", false);
			m.put("msg", "操作失败，请重试");
		}

		return m;
	}
	
	/**
	 * 获取还未部署的流程列表
	 * 
	 * @param modelId
	 * @return
	 */
	public Map<String, Object> getModelList() {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<Model> modelList = repositoryService.createModelQuery().list();
			map.put("modelList", modelList);
			map.put("succ", true);
			map.put("msg", "查询成功");
		} catch (Exception e) {
			map.put("succ", false);
			map.put("msg", "查询失败");
		}
		return map;
	}
	
	/**
	 * 通过modelID进行流程的部署
	 * 
	 * @param modelId
	 * @return
	 */
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
	 * 查询已部署的流程列表
	 * 
	 * @return
	 */
	public Map<String, Object> getProcDefList() {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<String> procDesList = new ArrayList<String>();
			List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().latestVersion().list();
			//这里之所以要进行字符串的拼接，而不直接返回ProcessDefinition的列表，是因为ProcessDefinition该对象不能直接被调用,但可以试用其属性
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
					procDesList.add(s);
				}
			}
			map.put("procDefList", procDesList);
			map.put("succ", true);
			map.put("msg", "查询成功");
		} catch (Exception e) {
			map.put("succ", false);
			map.put("msg", "查询失败");
		}
		return map;
	}
	
	/**
	 * 读取流程图png图片
	 * 
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/getImage", method = RequestMethod.GET)
	public void image(HttpServletResponse response, String deploymentId) throws IOException {

		// 从仓库中找需要展示的文件
		// String deploymentId = "7513";
		List<String> names = repositoryService.getDeploymentResourceNames(deploymentId);
		String imageName = null;
		for (String name : names) {
			if (name.indexOf(".png") >= 0) {
				imageName = name;
			}
		}
		// 通过部署ID和文件名称得到文件的输入流
		InputStream in = repositoryService.getResourceAsStream(deploymentId, imageName);
		byte[] b = new byte[1024];
		int len = -1;
		while ((len = in.read(b, 0, 1024)) != -1) {
			response.getOutputStream().write(b, 0, len);
		}
		in.close();

	}
	
	/**
	 * 读取流程图xml配置文件
	 * 
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/resource", method = RequestMethod.GET)
	public void xmlResource(HttpServletResponse response, String deploymentId) throws IOException {

		// 从仓库中找需要展示的文件
		// String deploymentId = "7513";
		List<String> names = repositoryService.getDeploymentResourceNames(deploymentId);
		String xmlName = null;
		for (String name : names) {
			if (name.indexOf(".xml") >= 0) {
				xmlName = name;
			}
		}
		// 通过部署ID和文件名称得到文件的输入流
		InputStream in = repositoryService.getResourceAsStream(deploymentId, xmlName);
		byte[] b = new byte[1024];
		int len = -1;
		while ((len = in.read(b, 0, 1024)) != -1) {
			response.getOutputStream().write(b, 0, len);
		}
		in.close();

	}
	
	/**
	 * 通过流程定义的key进行启动流程
	 * 
	 * @param key
	 * @return
	 */
	public Map<String, Object> getStart(@RequestBody ProcStartPojo pojo, HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		String key = pojo.getKey();
		String bizId = pojo.getBizId();
		Map<String, Object> variable = pojo.getVariable();
		ActBizTaskResult actBizTaskResult = new ActBizTaskResult();
		
		try {
			ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(key, bizId, variable);// 通过流程的key、业务ID和流程变量进行流程的启动
			System.out.println(processInstance.getId() + "---" + processInstance.getProcessInstanceId());
			// 启动成功后，在 ACT_BIZ_TASK_RESULT 表中添加申请人的信息(业务层面的entity)
			actBizTaskResult.setBizId((long) Integer.parseInt(bizId));
			actBizTaskResult.setProcId((long) Integer.parseInt(processInstance.getProcessInstanceId()));
			actBizTaskResult.setTaskName(key);
			actBizTaskResult.setAssignee(((ActUser) session.getAttribute("USER")).getUserName());
			actBizTaskResult.setDescribtion("流程申请人");
			actBizTaskResult.setCreateTime(new Date());
			actBizTaskResult.setIsAgree("1");
			//actBizTaskResultMapper.insertSelective(actBizTaskResult);
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
	 * 通过流程定义的key进行启动流程,并给第一个节点赋予并发和结束条件
	 * 
	 * @param key
	 * @return
	 */
	public Map<String, Object> getStartConcurrency(@RequestBody ProcStartPojo pojo, HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		String key = pojo.getKey();
		String bizId = pojo.getBizId();
		Map<String, Object> variable = pojo.getVariable();
		
		String names = (String) variable.get("users");
		String[] nameList = names.split(",");
		variable.put("users", Arrays.asList(nameList));
		
		ActBizTaskResult actBizTaskResult = new ActBizTaskResult();
		
		try {
			// runtimeService.startProcessInstanceByKey(key);
			ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(key, bizId, variable);// 通过流程的key、业务ID和流程变量进行流程的启动
			System.out.println(processInstance.getId() + "---" + processInstance.getProcessInstanceId());
			// 启动成功后，在 ACT_BIZ_TASK_RESULT 表中添加申请人的信息(业务层面的entity)
			actBizTaskResult.setBizId((long) Integer.parseInt(bizId));
			actBizTaskResult.setProcId((long) Integer.parseInt(processInstance.getProcessInstanceId()));
			actBizTaskResult.setTaskName(key);
			actBizTaskResult.setAssignee(((ActUser) session.getAttribute("USER")).getUserName());
			actBizTaskResult.setDescribtion("流程申请人");
			actBizTaskResult.setCreateTime(new Date());
			actBizTaskResult.setIsAgree("1");
			//actBizTaskResultMapper.insertSelective(actBizTaskResult);
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
	 * 查询我的代办流程(单个user,直接查ACT_RU_TASK表)
	 * 
	 * @return
	 */
	public Map<String, Object> getMyUndealList(HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		ActUser user = (ActUser) session.getAttribute("USER");
		if (user!=null && user.getUserName()!=null) {
			try {
				List<String> tasks = new ArrayList<String>();
				List<Task> taskList = taskService.createTaskQuery().taskAssignee(user.getUserName()).orderByTaskCreateTime().desc()
						.list();

				if (taskList != null && taskList.size() > 0) {
					for (Task task : taskList) {
						String s = null;
						s = task.getAssignee() + "-" + task.getCreateTime() + "-" + task.getId() + "-"
								+ task.getProcessInstanceId();
						tasks.add(s);
					}
				}
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
	 * 查询我的代办流程(多个user抢占一个任务节点)
	 * 
	 * @return
	 */
	public Map<String, Object> getCandicateTaskList(HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		ActUser user = (ActUser) session.getAttribute("USER");
		if (user!=null && user.getUserName()!=null) {
			try {
				List<String> tasks = new ArrayList<String>();
				List<Task> taskList = taskService.createTaskQuery().taskCandidateUser(user.getUserName()).orderByTaskCreateTime().desc().list();
				
				if (taskList != null && taskList.size() > 0) {
					for (Task task : taskList) {
						String s = null;
						s = task.getAssignee() + "-" + task.getCreateTime() + "-" + task.getId() + "-"
								+ task.getProcessInstanceId();
						tasks.add(s);
					}
				}
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
	public Map<String, Object> claimTask(String taskId, HttpSession session){
		Map<String, Object> map = new HashMap<String, Object>();
		ActUser user = (ActUser) session.getAttribute("USER");
		try {
			taskService.claim(taskId, user.getUserName());
			map.put("succ", true);
			map.put("msg", "抢占成功");
		} catch (Exception e) {
			map.put("succ", false);
			map.put("msg", "抢占失败");
		}
		return map;
	}
	
	/**
	 * 完成自己的代办节点
	 */
	public Map<String, Object> conformTask(String taskId, Map<String, Object> variable) {
		Map<String, Object> map = new HashMap<String, Object>();

		try {
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
	public Map<String, Object> getAllVaribles(String taskId) {
		try {
			Map<String, Object> map = taskService.getVariables(taskId);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
}

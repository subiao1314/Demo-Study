package com.activiti.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.activiti.controller.pojo.UrlRedirectPojo;
import com.activiti.entity.ActBizUrl;
import com.activiti.service.BusinessService;
import com.activiti.service.ModelService;
import com.activiti.util.Dom4jTextUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Controller
@RequestMapping("/web")
public class ProcessController extends BaseController {

	@Autowired
	private RepositoryService repositoryService;

	@Autowired
	private ModelService modelService;

	@Autowired
	private BusinessService businessService;

	/**
	 * 跳转到首页
	 * 
	 * @return
	 */
	@RequestMapping("/create")
	public ModelAndView showIndexPage() {
		return basePage("create/index", "create_", "create/create_modal", null);
	}

	/**
	 * 跳转到画流程图的页面
	 * 
	 * @param name
	 * @param key
	 * @param description
	 * @param request
	 * @param response
	 */
	@RequestMapping("/create/getModeler")
	@ResponseBody
	public Map<String, Object> getModeler(@RequestParam("name") String name, @RequestParam("key") String key,
			@RequestParam(value = "description", required = false) String description, HttpServletRequest request,
			HttpServletResponse response) {
		// ProcessEngine processEngine =
		// ProcessEngines.getDefaultProcessEngine();
		// repositoryService = processEngine.getRepositoryService();

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
	 * 跳转到流程model页面（流程还未部署）
	 * 
	 * @return
	 */
	@RequestMapping("/model")
	public ModelAndView showModelListPage() {
		return basePage("model/list", "model_", null);
	}

	/**
	 * 获取还未部署的流程列表
	 * 
	 * @param modelId
	 * @return
	 */
	@RequestMapping("/model/getModelList")
	@ResponseBody
	public Map<String, Object> getModelList() {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<Model> modelList = modelService.getModelList();
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
	@RequestMapping("/model/getDeploy")
	@ResponseBody
	public Map<String, Object> getDeploy(String modelId) {
		Map<String, Object> m = new HashMap<String, Object>();
		m = modelService.getDeploy(modelId);
		return m;
	}

	/**
	 * 跳转到已部署的流程列表页
	 * 
	 * @return
	 */
	@RequestMapping("/procDef")
	public ModelAndView showProcDefPage() {
		return basePage("procDef/list", "procDef_", null);
	}

	/**
	 * 查询已部署的流程列表
	 * 
	 * @return
	 */
	@RequestMapping("/procDef/getProcDefList")
	@ResponseBody
	public Map<String, Object> getProcDefList() {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<String> procDesList = modelService.getProcDefList();
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
	 * 跳转到查看图片页面
	 * 
	 * @throws IOException
	 */
	@RequestMapping("/image")
	public ModelAndView showImagePage(String id) throws IOException {
		// response.sendRedirect(request.getContextPath() +
		// "/index/image.html?deploymentId=" + id);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("deploymentId", id);
		ModelAndView mad = new ModelAndView();
		mad.setViewName("/create/image");
		mad.addAllObjects(map);
		return mad;
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
	public void xmlResource(HttpServletResponse response, String id) throws IOException {

		// 从仓库中找需要展示的文件
		// String deploymentId = "7513";
		List<String> names = repositoryService.getDeploymentResourceNames(id);
		String xmlName = null;
		for (String name : names) {
			if (name.indexOf(".xml") >= 0) {
				xmlName = name;
			}
		}
		// 通过部署ID和文件名称得到文件的输入流
		InputStream in = repositoryService.getResourceAsStream(id, xmlName);
		byte[] b = new byte[1024];
		int len = -1;
		while ((len = in.read(b, 0, 1024)) != -1) {
			response.getOutputStream().write(b, 0, len);
		}
		in.close();

	}
	
	/**
	 * 跳转到配置url页面
	 * @param id
	 * @return
	 */
	@RequestMapping("/procDef/url")
	public ModelAndView showUserTaskUrlPage(String id){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("procId", id);
		return basePage("procDef/urlList", "procDef_", map);
	}
	
	/**
	 * 提交流程url的列表
	 * @param urlList
	 * @return
	 */
	@RequestMapping("/procDef/url/commitUrlList")
	@ResponseBody
	public Map<String, Object> insertUrlList(@RequestBody UrlRedirectPojo pojo){
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			businessService.insertUrlList(pojo.getUrlList());
			map.put("succ", true);
			map.put("msg", "提交成功");
		} catch (Exception e) {
			map.put("succ", false);
			map.put("msg", "提交失败");
		}
		return map;
	}
	
	/**
	 * 根据部署ID查询改流程的url列表
	 * @param procId
	 * @return
	 */
	@RequestMapping("/procDef/url/queryUrl")
	@ResponseBody
	public Map<String, Object> queryUrlList(String procId){
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			List<ActBizUrl> urlList = businessService.queryUrlListBy(procId);
			map.put("urlList", urlList);
			map.put("succ", true);
			map.put("msg", "查询成功");
		} catch (Exception e) {
			map.put("succ", false);
			map.put("msg", "查询失败");
		}
		return map;
	}
	
	/**
	 * 根据部署ID查询流程定义的ID
	 * @param procId
	 * @return
	 */
	@RequestMapping("/procDef/url/procName")
	@ResponseBody
	public Map<String, Object> getProcNameByDeployId(String procId){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String procName = repositoryService.createProcessDefinitionQuery().deploymentId(procId).singleResult().getId();
			map.put("procName", procName);
			map.put("succ", true);
			map.put("msg", "查询成功");
		} catch (Exception e) {
			map.put("succ", false);
			map.put("msg", "查询失败");
		}
		return map;
	}
	
	/**
	 * 解析xml文件的内容
	 * 
	 * @param id
	 */
	@RequestMapping("/procDef/url/xmlText")
	@ResponseBody
	public Map<String, Object> getXmlElement(String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 从仓库中找需要展示的文件
		// String deploymentId = "7513";
		try {
			List<String> names = repositoryService.getDeploymentResourceNames(id);
			String xmlName = null;
			for (String name : names) {
				if (name.indexOf(".xml") >= 0) {
					xmlName = name;
				}
			}
			// 通过部署ID和文件名称得到文件的输入流
			InputStream in = repositoryService.getResourceAsStream(id, xmlName);
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] b = new byte[1024];
			int len = -1;
			while ((len = in.read(b, 0, 1024)) != -1) {
				baos.write(b, 0, len);
			}
			byte[] in_b = baos.toByteArray();
			String text = new String(in_b);
			
			List<List<String>> aList = Dom4jTextUtil.getElementBy(text, "userTask");
			
			map.put("usertaskList", aList);
			map.put("succ", true);
			map.put("msg", "查询成功");
		} catch (Exception e) {
			map.put("succ", false);
			map.put("msg", "查询失败");
		}
		return map;
	}

}

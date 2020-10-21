activityIdList = [];
var ActivitiRest = {
	options: {},
	getProcessDefinitionByKey: function(processDefinitionKey, callback) {
		var url = Lang.sub(this.options.processDefinitionByKeyUrl, {processDefinitionKey: processDefinitionKey});
		
		$.ajax({
			url: url,
			dataType: 'jsonp',
			cache: false,
			async: true,
			success: function(data, textStatus) {
				cosole.log(1);
				console.log(data);
				var processDefinition = data;
				if (!processDefinition) {
					console.error("Process definition '" + processDefinitionKey + "' not found");
				} else {
				  callback.apply({processDefinitionId: processDefinition.id});
				}
			}
		}).done(function(data, textStatus) {
			console.log("ajax done");
		}).fail(function(jqXHR, textStatus, error){
			console.error('Get diagram layout['+processDefinitionKey+'] failure: ', textStatus, 'error: ', error, jqXHR);
		});
	},
	
	getProcessDefinition: function(processDefinitionId, callback) {
		var url = Lang.sub(this.options.processDefinitionUrl, {processDefinitionId: processDefinitionId});
		
		$.ajax({
			url: url,
			dataType: 'jsonp',
			cache: false,
			async: true,
			success: function(data, textStatus) {
				console.log(2);
				console.log(data);
				var processDefinitionDiagramLayout = data;
				
				//把所有节点（除去开始和结束）的ID放入一个数组中
				var activities = processDefinitionDiagramLayout.activities;
				var idList = [];
				for(var i=0;i<activities.length;i++){
					var property = activities[i].properties;
					var type = property.type;
					if (type.indexOf("start")<0 && type.indexOf("end")<0) {
						idList.push(activities[i].activityId);
					}
				}
				activityIdList = idList;
				console.log(activityIdList);
				
				if (!processDefinitionDiagramLayout) {
					console.error("Process definition diagram layout '" + processDefinitionId + "' not found");
					return;
				} else {
					callback.apply({processDefinitionDiagramLayout: processDefinitionDiagramLayout});
				}
			}
		}).done(function(data, textStatus) {
			console.log("ajax done");
		}).fail(function(jqXHR, textStatus, error){
			console.log('Get diagram layout['+processDefinitionId+'] failure: ', textStatus, jqXHR);
		});
	},
	
	getHighLights: function(processInstanceId, callback) {
		var url = Lang.sub(this.options.processInstanceHighLightsUrl, {processInstanceId: processInstanceId});
		
		$.ajax({
			url: url,
			dataType: 'jsonp',
			cache: false,
			async: true,
			success: function(data, textStatus) {
				console.log("ajax returned data");
				console.log(data);
				var highLight = data;
				
				//将this.activityIdList改为存放历史activiti的节点id
				var currentActivitiId = highLight.activities;
				var idList = activityIdList;
				console.log(idList);
				var hisIdList = [];
				for (var i=0;i<idList.length;i++) {
					if (idList[i] == currentActivitiId[0]) {
						break;
					}
					hisIdList.push(idList[i]);
				}
				activityIdList = hisIdList;
				console.log(activityIdList);
				//ProcessDiagramCanvas pdc = new ProcessDiagramCanvas();
				//pdc.highLightHisActivities(activityIdList);
				var highLights = [];
				highLights.push(activityIdList);
				highLights.push(highLight);
				
				if (!highLights) {
					console.log("highLights not found");
					return;
				} else {
					callback.apply({highLights: highLights});
				}
			}
		}).done(function(data, textStatus) {
			console.log("ajax done");
		}).fail(function(jqXHR, textStatus, error){
		  console.log('Get HighLights['+processInstanceId+'] failure: ', textStatus, jqXHR);
		});
	}
};
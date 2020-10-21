<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<aside class="right-side">
	<!-- Content Header (Page header) -->
	<section class="content-header">
		<h1>
			ProcDef <small>list</small>
		</h1>
	</section>
	
	<section class="content">
	
		<input type="hidden" value="${key}" id="key" />
	
		<div class="row">
			<div class="col-lg-3 col-xs-6">
				<div class="col-lg-4 col-xs-6">
					开始时间
				</div>
				<div class="col-lg-8 col-xs-6">
					<input type="text" class="form-control" id="startDate" />
				</div>
			</div>
			<div class="col-lg-3 col-xs-6">
				<div class="col-lg-4 col-xs-6">
					结束时间
				</div>
				<div class="col-lg-8 col-xs-6">
					<input type="text" class="form-control" id="endDate" />
				</div>
			</div>
		</div>
		
		<div class="row mt20">
			<div class="col-lg-3 col-xs-6">
				<div class="col-lg-4 col-xs-6">
					请假天数
				</div>
				<div class="col-lg-8 col-xs-6">
					<input type="text" class="form-control" id="days" />
				</div>
			</div>
			<div class="col-lg-3 col-xs-6">
				<div class="col-lg-4 col-xs-6">
					请假事由
				</div>
				<div class="col-lg-8 col-xs-6">
					<textarea class="form-control" id="reason"></textarea>
				</div>
			</div>
		</div>
		<div class="row mt20" id="single">
			<div class="col-lg-3 col-xs-6">
				<div class="col-lg-4 col-xs-6">
					领导审批
				</div>
				<div class="col-lg-8 col-xs-6">
					<select class="form-control" id="groups">
						<option value="">请选择上级</option>
					</select>
				</div>
			</div>
		</div>
		<div class="row mt20" id="batch">
			<div class="col-lg-3 col-xs-6">
				<div class="col-lg-4 col-xs-6">
					领导审批
				</div>
				<div class="col-lg-8 col-xs-6" id="checkbox_group">
					
				</div>
			</div>
		</div>
		<div class="row mt20">
			<div class="col-lg-6 col-xs-12 text-center">
				<a href="javascript:;" class="mybtn btn-save" id="form_commit">提交</a>
			</div>
		</div>
		
	</section>
	
	<script>
	
	$(function(){
		if($("#key").val() == "测试多个user抢占节点"){
			$("#single").hide();
		}
		if($("#key").val() == "请假"){
			$("#batch").hide();
		}
		if($("#key").val() == "并发"){
			$("#single").hide();
		}
		if($("#key").val() == "多个user都执行某个节点"){
			$("#single").hide();
		}
		if($("#key").val() == "多个user都执行带条件"){
			$("#single").hide();
		}
		getAllUsers();
		
		init_exTable();
		
		$("#form_commit").on("click", function(){
			commit()
		});
	})
	
	function init_exTable(){
	    $('#startDate').datetimepicker({
			language: 'zh-CN',//显示中文
			format: 'yyyy-mm-dd',//显示格式
			minView: "month",//设置只显示到月份
			initialDate: new Date(),//初始化当前日期
			autoclose: true,//选中自动关闭
			todayBtn: true//显示今日按钮
	   	}).on("changeDate",function(){
	   		var startTime = $('#startDate').val();
	   		var start=new Date(startTime.replace("-", "/").replace("-", "/"));
	   		var endTime = $('#endDate').val();
	   		var end=new Date(endTime.replace("-", "/").replace("-", "/")); 
	   		if(startTime > endTime){
	   			$('#endDate').val("");
	   		}
	        $("#endDate").datetimepicker("setStartDate",startTime);
	    });
	    
	    $('#endDate').datetimepicker({
			language: 'zh-CN',//显示中文
			format: 'yyyy-mm-dd',//显示格式hh:ii:ss
			minView: "month",//设置只显示到月份
			initialDate: new Date(),//初始化当前日期
			autoclose: true,//选中自动关闭
			todayBtn: true//显示今日按钮
	   	}).on("changeDate",function(){
	   		var startTime = $('#startDate').val();
	   		var start=new Date(startTime.replace("-", "/").replace("-", "/"));
	   		var endTime = $('#endDate').val();
	   		var end=new Date(endTime.replace("-", "/").replace("-", "/"));
	   		if(startTime!=null || startTime!=""){
	   			$("#startDate").datetimepicker("setEndDate",endTime);
	   		}
	   		if(startTime > endTime){
	   			$('#startDate').val("");
		        $("#startDate").datetimepicker("setEndDate",endTime);
	   		}
	    });
	}
	
	function getAllUsers(){
		$.ajax({
			url: "web/getAllUsers",
			type: "post",
			dataType: "json",
			success: function(data){
				console.log(data);
				if(data.succ){
					var optionContent = $("#groups");
					var checkboxContent = $("#checkbox_group");
					var list = data.userList;
					for (var i=0;i<list.length;i++) {
						var option = $("<option value="+list[i].userName+">"+list[i].userName+"</option>");
						optionContent.append(option);
						var span = $("<span class='mr10'><input type='checkbox' data-name='"+list[i].userName+"' /> "+list[i].userName+"</span>");
						checkboxContent.append(span);
					}
				}
			}
		});
	}
	
	function commit(){
		var start = $("#startDate").val();
		var end = $("#endDate").val();
		var days = $("#days").val();
		var reason = $("#reason").val();
		var person = $("#groups option:selected").val();
		
		if (start == "") {
			alert("开始时间不能为空");
			return;
		}
		if (end == "") {
			alert("结束时间不能为空");
			return;
		}
		if (days == "") {
			alert("请假天数不能为空");
			return;
		}
		if (reason == "") {
			alert("请假事由不能为空");
			return;
		}
		if($("#key").val() == "测试多个user抢占节点"){
			var flag = false;
			for (var i=0;i<$("#checkbox_group input").length;i++) {
				if ($("#checkbox_group input").eq(i).prop("checked")) {
					flag = true;
				}
			}
			if (flag == false) {
				alert("上级领导不能为空");
				return;
			}
		} else if($("#key").val()=="请假"){
			if (person == "") {
				alert("上级领导不能为空");
				return;
			}
		} else if($("#key").val()=="多个user都执行某个节点" || $("#key").val()=="多个user都执行带条件" || $("#key").val() == "并发"){
			var flag = false;
			for (var i=0;i<$("#checkbox_group input").length;i++) {
				if ($("#checkbox_group input").eq(i).prop("checked")) {
					flag = true;
				}
			}
			if (flag == false) {
				alert("上级领导不能为空");
				return;
			}
		}
		
		var data = {
			startDate: start,
			endDate: end,
			days: days,
			reason: reason
		};
		
		$.ajax({
			url: "biz/commitBiz",
			type: "post",
			data: data,
			dataType: "json",
			success: function(data){
				console.log(data);
				alert(data.msg);
				if (data.succ) {
					startProc(data.bizId);
				}
			}
		});
	}
	
	function startProc(bizId){
		var type = $("#key").val();
		var data;
		var url;
		if(type == "测试多个user抢占节点"){
			var str = "";
			var nameList = $("#checkbox_group input");
			for (var i=0;i<nameList.length;i++) {
				if (nameList.eq(i).prop("checked")) {
					str = str + nameList.eq(i).data("name") + ",";
				}
			}
			str = str.substring(0, str.length-1);
			data = {
				key: type,
				bizId: bizId,
				variable: {
					users: str
				}
			};
			url = "web/procDef/getStart";
		} else if (type=="请假") {
			data = {
				key: type,
				bizId: bizId,
				variable: {
					person: $("#groups option:selected").val()
				}
			};
			url = "web/procDef/getStart";
		} else if(type=="多个user都执行某个节点" || type=="多个user都执行带条件" || type=="并发"){
			var str = "";
			var nameList = $("#checkbox_group input");
			for (var i=0;i<nameList.length;i++) {
				if (nameList.eq(i).prop("checked")) {
					str = str + nameList.eq(i).data("name") + ",";
				}
			}
			str = str.substring(0, str.length-1);
			data = {
				key: type,
				bizId: bizId,
				variable: {
					users: str
				}
			};
			url = "web/procDef/getStartConcurrency";
		}
		
		$.ajax({
			url: url,
			type: "post",
			dataType : 'json',
			data: JSON.stringify(data),
		    contentType: "application/json; charset=utf-8",
			success: function(data){
				console.log(data);
				alert(data.msg);
				if (data.succ) {
					var location = (window.location+'').split('/');
                    var basePath = location[0]+'//'+location[2]+'/'+location[3];                        
                   	window.location.href=basePath + "/web/myProc";
				}
			}
		});
	}
	</script>
</aside>

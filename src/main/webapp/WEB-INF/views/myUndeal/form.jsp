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
	
		<input type="hidden" value="${taskId}" id="taskId" />
		<input type="hidden" value="${procInstId}" id="procInstId" />
	
		<div class="row">
			<div class="col-lg-3 col-xs-6">
				<div class="col-lg-4 col-xs-6">
					开始时间
				</div>
				<div class="col-lg-8 col-xs-6">
					<input type="text" class="form-control" id="startDate" readonly value="${startDate}" />
				</div>
			</div>
			<div class="col-lg-3 col-xs-6">
				<div class="col-lg-4 col-xs-6">
					结束时间
				</div>
				<div class="col-lg-8 col-xs-6">
					<input type="text" class="form-control" id="endDate" readonly value="${endDate}" />
				</div>
			</div>
		</div>
		
		<div class="row mt20">
			<div class="col-lg-3 col-xs-6">
				<div class="col-lg-4 col-xs-6">
					请假天数
				</div>
				<div class="col-lg-8 col-xs-6">
					<input type="text" class="form-control" id="days" readonly value="${days}" />
				</div>
			</div>
			<div class="col-lg-3 col-xs-6">
				<div class="col-lg-4 col-xs-6">
					请假事由
				</div>
				<div class="col-lg-8 col-xs-6">
					<textarea class="form-control" id="reason" readonly>${discrition}</textarea>
				</div>
			</div>
		</div>
		<div class="row mt20">
			<div class="col-lg-3 col-xs-6">
				<div class="col-lg-4 col-xs-6">
					是否同意
				</div>
				<div class="col-lg-8 col-xs-6">
					<span><input type="radio" /> 是</span>
					<span><input type="radio" /> 否</span>
				</div>
			</div>
		</div>
		<div class="row mt20">
			<div class="col-lg-3 col-xs-6">
				<div class="col-lg-4 col-xs-6">
					审批意见
				</div>
				<div class="col-lg-8 col-xs-6">
					<textarea class="form-control" id="suggestion"></textarea>
				</div>
			</div>
		</div>
		<div class="row mt20">
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
		<div class="row mt20">
			<div class="col-lg-6 col-xs-12 text-center">
				<a href="javascript:;" class="mybtn btn-save" id="form_commit">提交</a>
			</div>
		</div>
		
		<div class="row mt20">
			<div class="col-lg-10 col-xs-10">
				<b>以下为该流程完成的节点</b>
			</div>
		</div>
		
		<div class="row mt10">
			<div class="col-lg-10 col-xs-10">
				<div class="box">
                     <div class="box-body table-responsive no-padding">
                         <table class="table table-hover">
	                         <thead>
	                         	<tr>
	                                <th>#</th>
	                                <th>操作人</th>
	                                <th>是否同意</th>
	                                <th>审批意见</th>
	                            </tr>
	                         </thead>
                             <tbody id="procHisList">
	                             
	                         </tbody>
                         </table>
                     </div><!-- /.box-body -->
                 </div>
			</div>
		</div>
		
	</section>
	
	<script>
	
	$(function(){
		getAllUsers();
		getAllProcHis();
		
		$("#form_commit").on("click", function(){
			commit();
		});
		
	})
	
	function getAllUsers(){
		$.ajax({
			url: "web/getAllUsers",
			type: "post",
			dataType: "json",
			success: function(data){
				console.log(data);
				if(data.succ){
					var content = $("#groups");
					var list = data.userList;
					for (var i=0;i<list.length;i++) {
						var option = $("<option value="+list[i].userName+">"+list[i].userName+"</option>");
						content.append(option);
					}
				}
			}
		});
	}
	
	function commit(){
		var agree = "1";
		var suggestion = $("#suggestion").val();
		var person = $("#groups option:selected").val();
		
		if (person == "") {
			alert("请选择上级审批人");
			return;
		}
		
		var data = {
			agree: agree,
			suggestion: suggestion,
			taskId: $("#taskId").val(),
			procInstId: $("#procInstId").val()
		};
		
		$.ajax({
			url: "biz/commitBizResult",
			type: "post",
			data: data,
			dataType: "json",
			success: function(data){
				console.log(data);
				alert(data.msg);
				if (data.succ) {
					doComplete();
				}
			}
		});
	}
	
	function doComplete(){
		var data = {
			taskId: $("#taskId").val(),
			variable: {
				person: $("#groups option:selected").val(),
				count: 1
			}
		};
		
		$.ajax({
			url: "web/myUndeal/conformTask",
			type: "post",
			data: JSON.stringify(data),
		    contentType: "application/json; charset=utf-8",
			dataType: "json",
			success: function(data){
				console.log(data);
				alert(data.msg);
				if (data.succ) {
					var location = (window.location+'').split('/');
                    var basePath = location[0]+'//'+location[2]+'/'+location[3];                        
                   	window.location.href=basePath + "/web/myUndeal";
				}
			}
		});
	}
	
	function getAllProcHis(){
		var data = {
			procInstId: $("#procInstId").val()
		};
		
		$.ajax({
			url: "biz/getAllProcHis",
			type: "post",
			data: data,
			dataType: "json",
			success: function(data){
				console.log(data);
				if(data.succ){
					var content = $("#procHisList");
					var list = data.procHisList;
					if(list.length>0){
						for (var i=0;i<list.length;i++) {
							var tr = $("<tr></tr>");
							var td0 = $("<td>"+(i+1)+"</td>");
							var td1 = $("<td>"+list[i].assignee+"</td>");
							var td2;
							if(list[i].isAgree == "1"){
								td2 = $("<td>同意</td>");
							} else {
								td2 = $("<td>不同意</td>");
							}
							var td3 = $("<td>"+list[i].describtion+"</td>");
							tr.append(td0).append(td1).append(td2).append(td3);
							content.append(tr);
						}
					}
				}
			}
		});
	}
	</script>
</aside>

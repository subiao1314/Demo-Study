<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<aside class="right-side">
	<!-- Content Header (Page header) -->
	<section class="content-header">
		<h1>
			ProcDef <small>urlList</small>
		</h1>
	</section>
	
	<section class="content">
		<input type="hidden" value="${procId}" id="procId" />
		<div class="row">
			<div class="col-lg-11 col-xs-12">
				<div class="box">
                     <div class="box-body table-responsive no-padding">
                         <table class="table table-hover">
	                         <thead>
	                         	<tr>
	                                <th>#</th>
	                                <th>流程实例名称</th>
	                                <th>流程部署ID</th>
	                                <th>xml中task的ID</th>
	                                <th>xml中task的名称</th>
	                                <th>task对应的url</th>
	                            </tr>
	                         </thead>
                             <tbody id="procDefList">
	                             
	                         </tbody>
                         </table>
                     </div><!-- /.box-body -->
                 </div>
			</div>
			<div class="col-lg-6 col-xs-6 text-center">
				<a href="javascript:;" class="mybtn btn-save" id="form_commit">提交</a>
				<a href="javascript:;" class="mybtn btn-cancel ml20">取消</a>
			</div>
		</div>
		
	</section>
	
	<script>
	
	$(function(){
		queryUrlList();
		
		$(".btn-cancel").on("click", function(){
			var location = (window.location+'').split('/');
            var basePath = location[0]+'//'+location[2]+'/'+location[3];                        
           	window.location.href=basePath + "/web/procDef";
		});
		
		$(".btn-save").on("click", function(){
			commitUrlList();
		});
		
	})
	
	function queryUrlList(){
		var data = {
			procId: $("#procId").val()
		};
		
		$.ajax({
			url: "web/procDef/url/queryUrl",
			type: "post",
			data: data,
			dataType: "json",
			success: function(data){
				console.log(data);
				if(data.succ){
					if (data.urlList.length>0) {
						var content = $("#procDefList");
						var list = data.urlList;
						for (var i=0;i<list.length;i++) {
							var arr = list[i];
							var tr = $("<tr></tr>");
							var td0 = $("<td>"+(i+1)+"</td>");
							var td1 = $("<td>"+arr.procId+"</td>");
							var td2 = $("<td>"+arr.deployId+"</td>");
							var td3 = $("<td>"+arr.taskId+"</td>");
							var td4 = $("<td>"+arr.taskName+"</td>");
							var td5 = $("<td><input type='text' class='form-control urlInput' value='"+arr.taskUrl+"' /></td>");
							tr.append(td0).append(td1).append(td2).append(td3).append(td4).append(td5);
							content.append(tr);
						}
					} else {
						getProcName();
					}
				}
			}
		});
	}
	
	function getProcName(){
		var data = {
			procId: $("#procId").val()
		};
		
		$.ajax({
			url: "web/procDef/url/procName",
			type: "post",
			data: data,
			dataType: "json",
			success: function(data){
				console.log(data);
				var procName = data.procName;
				getXmlText(procName);
			}
		});
	}
	
	function getXmlText(procId){
		
		var data = {
			id: $("#procId").val()
		};
		
		$.ajax({
			url: "web/procDef/url/xmlText",
			type: "post",
			data: data,
			dataType: "json",
			success: function(data){
				console.log(data);
				if(data.succ && data.usertaskList.length>0){
					var content = $("#procDefList");
					var list = data.usertaskList;
					for (var i=0;i<list.length;i++) {
						var arr = list[i];
						var tr = $("<tr></tr>");
						var td0 = $("<td>"+(i+1)+"</td>");
						var td1 = $("<td>"+procId+"</td>");
						var td2 = $("<td>"+$('#procId').val()+"</td>");
						var td3 = $("<td>"+arr[0]+"</td>");
						var td4 = $("<td>"+arr[1]+"</td>");
						var td5 = $("<td><input type='text' class='form-control urlInput' /></td>");
						tr.append(td0).append(td1).append(td2).append(td3).append(td4).append(td5);
						content.append(tr);
					}
				}
			}
		});
	}
	
	function commitUrlList(){
		var ins = $(".urlInput");
		for(var i=0;i<ins.length;i++){
			if(ins.eq(i).val() == ""){
				alert("url不能设置为空");
				return;
			}
		}
		var urlList = [];
		var trs = $("#procDefList tr");
		for(var i=0; i<trs.length;i++){
			var td = trs.eq(i).find("td");
			var url = {
				procId: td.eq(1).text(),
				deployId: td.eq(2).text(),
				taskId: td.eq(3).text(),
				taskName: td.eq(4).text(),
				taskUrl: td.eq(5).find("input").eq(0).val()
			}
			urlList.push(url);
		}
		
		console.log(urlList);
		
		var data = {
			urlList: urlList
		};
		
		$.ajax({
			url: "web/procDef/url/commitUrlList",
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
                   	window.location.href=basePath + "/web/procDef";
				}
			}
		});
	}
	</script>
</aside>

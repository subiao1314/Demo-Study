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
	
		<div class="row">
			<div class="col-lg-12 col-xs-12">
				<div class="box">
                     <div class="box-body table-responsive no-padding">
                         <table class="table table-hover">
	                         <thead>
	                         	<tr>
	                                <th>#</th>
	                                <th>操作人</th>
	                                <th>流程创建时间</th>
	                                <th>用户类型</th>
	                                <th>操作</th>
	                            </tr>
	                         </thead>
                             <tbody id="myUndealList">
	                             
	                         </tbody>
                         </table>
                     </div><!-- /.box-body -->
                 </div>
			</div>
		</div>
		
	</section>
	
	<script>
	
	$(function(){
		singleTask();
		
		batchTask();
		
		$("#myUndealList").on("click", ".conformTask", function(){
			var procInstId = $(this).data("procinstid");
			var taskId = $(this).data("id");
			var location = (window.location+'').split('/');
            var basePath = location[0]+'//'+location[2]+'/'+location[3];                        
           	window.location.href=basePath + "/web/myUndeal/form?procInstId=" + procInstId + "&taskId=" + taskId;
		});
		
		$("#myUndealList").on("click", ".claimTask", function(){
			var taskId = $(this).data("id");
			claimTask(taskId);
		});
		
	})
	
	function singleTask(){
		$.ajax({
			url: "web/myUndeal/getMyUndealList",
			type: "post",
			dataType: "json",
			success: function(data){
				console.log(data);
				if(data.succ){
					var content = $("#myUndealList");
					var list = data.taskList;
					for (var i=0;i<list.length;i++) {
						var arr = list[i].split("-");
						var tr = $("<tr></tr>");
						var td0 = $("<td>"+i+"</td>");
						var td1 = $("<td>"+arr[0]+"</td>");
						var td2 = $("<td>"+arr[1]+"</td>");
						var td3 = $("<td>单个用户</td>");
						var td4 = $("<td><span class='conformTask' data-id='"+arr[2]+"' data-procinstid='"+arr[3]+"'>处理</span><a class='ml10' target='_blank' href='ViewPorcess/getCurrentImage?processInstanceId="+arr[3]+"'>当前png</a></td>");
						tr.append(td0).append(td1).append(td2).append(td3).append(td4);
						content.append(tr);
					}
				}
			}
		});
	}
	
	function batchTask(){
		$.ajax({
			url: "web/myUndeal/getCandicateTaskList",
			type: "post",
			dataType: "json",
			success: function(data){
				console.log(data);
				if(data.succ){
					var content = $("#myUndealList");
					var list = data.taskList;
					for (var i=0;i<list.length;i++) {
						var arr = list[i].split("-");
						var tr = $("<tr></tr>");
						var td0 = $("<td>"+(i+1)+"</td>");
						var td1 = $("<td>暂无</td>");
						var td2 = $("<td>"+arr[1]+"</td>");
						var td3 = $("<td>多个用户</td>");
						var td4 = $("<td><span class='claimTask' data-id='"+arr[2]+"'>抢占</span></td>");
						tr.append(td0).append(td1).append(td2).append(td3).append(td4);
						content.append(tr);
					}
				}
			}
		});
	}
	
	function claimTask(taskId){
		var data = {
			taskId: taskId
		};
		$.ajax({
			url: "web/myUndeal/claim",
			type: "post",
			data: data,
			dataType: "json",
			success: function(data){
				console.log(data);
				alert(data.msg);
				if(data.succ){
					var location = (window.location+'').split('/');
		            var basePath = location[0]+'//'+location[2]+'/'+location[3];                        
		           	window.location.href=basePath + "/web/myUndeal";
				}
			}
		});
	}
		
	</script>
</aside>

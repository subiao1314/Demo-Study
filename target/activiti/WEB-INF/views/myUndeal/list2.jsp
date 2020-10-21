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
		test();
		
		$("#myUndealList").on("click", ".conformTask", function(){
			var _this = $(this);
			var taskId = _this.data("id");
			var data = {
				taskId: taskId
			};
			$.ajax({
				url: "web/davidUndeal/conformTask",
				type: "post",
				data: data,
				dataType: "json",
				success: function(data){
					console.log(data);
					alert(data.msg);
					if(data.succ){
						_this.parents("tr").eq(0).remove();
					}
				}
			});
		});
		
	})
	
	function test(){
		
		var data = {
			assignee: "david"
		};
		
		$.ajax({
			url: "web/myUndeal/getMyUndealList",
			type: "post",
			data: data,
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
						var td3 = $("<td><span class='conformTask' data-id='"+arr[2]+"'>确认</span></td>");
						tr.append(td0).append(td1).append(td2).append(td3);
						content.append(tr);
					}
				}
			}
		});
	}
		
	</script>
</aside>

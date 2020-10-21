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
	                                <th>名称</th>
	                                <th>key</th>
	                                <th>版本</th>
	                                <th>描述</th>
	                                <th>操作</th>
	                            </tr>
	                         </thead>
                             <tbody id="procDefList">
	                             
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
		
		$("#procDefList").on("click", ".startProc", function(){
			var key = $(this).data("id");
			var data = {
				key: key
			};
			$.ajax({
				url: "web/procDef/getStart",
				type: "post",
				data: data,
				dataType: "json",
				success: function(data){
					console.log(data);
					alert(data.msg);
				}
			});
		});
		
	})
	
	function test(){
		
		$.ajax({
			url: "web/procDef/getProcDefList",
			type: "post",
			dataType: "json",
			success: function(data){
				console.log(data);
				if(data.succ){
					var content = $("#procDefList");
					var list = data.procDefList;
					for (var i=0;i<list.length;i++) {
						var arr = list[i].split("-");
						var tr = $("<tr></tr>");
						var td0 = $("<td>"+i+"</td>");
						var td1 = $("<td>"+arr[0]+"</td>");
						var td2 = $("<td>"+arr[1]+"</td>");
						var td3 = $("<td>"+arr[2]+"</td>");
						var td4 = $("<td>"+arr[3]+"</td>");
						var td5 = $("<td><a href='web/image?id="+arr[4]+"' target='_blank'>查看png</a><a class='ml10' target='_blank' href='web/resource?id="+arr[4]+"'>查看xml</a><a class='ml10' href='web/procDef/url?id="+arr[4]+"'>配置url</a></td>");
						tr.append(td0).append(td1).append(td2).append(td3).append(td4).append(td5);
						content.append(tr);
					}
				}
			}
		});
	}
	/* <span class='startProc ml10' data-id='"+arr[1]+"'>启动</span> */
	</script>
</aside>

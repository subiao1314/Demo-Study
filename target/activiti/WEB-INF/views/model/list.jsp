<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<aside class="right-side">
	<!-- Content Header (Page header) -->
	<section class="content-header">
		<h1>
			Model <small>list</small>
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
	                                <th>描述</th>
	                                <th>操作</th>
	                            </tr>
	                         </thead>
                             <tbody id="modelList">
	                             
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
		
		$("#modelList").on("click", ".procDeploy", function(){
			var modelId = $(this).data("id");
			var data = {
				modelId: modelId
			};
			$.ajax({
				url: "web/model/getDeploy",
				type: "post",
				data: data,
				dataType: "json",
				success: function(data){
					console.log(data);
					alert(data.msg);
				}
			});
		});
		
		$("#modelList").on("click", ".reDesign", function(){
			var modelId = $(this).data("id");
			var location = (window.location+'').split('/');
            var basePath = location[0]+'//'+location[2]+'/'+location[3];                        
           	window.location.href=basePath + "/modeler.html?modelId=" + modelId;
		});
		
	})
	
	function test(){
		
		$.ajax({
			url: "web/model/getModelList",
			type: "post",
			dataType: "json",
			success: function(data){
				console.log(data);
				if(data.succ){
					var content = $("#modelList");
					var list = data.modelList;
					for (var i=0;i<list.length;i++) {
						var tr = $("<tr></tr>");
						var td0 = $("<td>"+i+"</td>");
						var td1 = $("<td>"+list[i].name+"</td>");
						var td2 = $("<td>"+list[i].key+"</td>");
						var ev = eval("("+list[i].metaInfo+")");
						var td3 = $("<td>"+ev.description+"</td>");
						var td4 = $("<td><span class='procDeploy' data-id='"+list[i].id+"'>部署</span><span class='reDesign ml5' data-id='" + list[i].id + "'>设计</span></td>");
						tr.append(td0).append(td1).append(td2).append(td3).append(td4);
						content.append(tr);
					}
				}
			}
		});
	}
		
	</script>
</aside>

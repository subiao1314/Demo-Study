<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<aside class="right-side">
	<!-- Content Header (Page header) -->
	<section class="content-header">
		<h1>
			Activiti <small>Create page</small>
		</h1>
	</section>
	
	<section class="content">
		
		<div class="row text-center" style="margin-top: 50px;">
			<a href="javascript:;" data-toggle="modal" data-target="#addProc">新建流程</a>
		</div>
	</section>
	
	<script>
	
	$(function(){
	
		$("#addProc .btn-save").on("click", function(){
			var name = $("#addProc .nameInput").val();
			var key = $("#addProc .keyInput").val();
			var discription = $("#addProc .disInput").val();
			
			if(name == ""){
				alert("流程名字不能为空");
				return;
			}
			if(key == ""){
				alert("流程关键词不能为空");
				return;
			}
			
			var data = {
				name: name,
				key: key,
				description: discription
			}
			
			$.ajax({
				url: "web/create/getModeler",
				type: "post",
				data: data,
				success: function(data){
					if (data.succ) {
						var location = (window.location+'').split('/');
	                    var basePath = location[0]+'//'+location[2]+'/'+location[3];                        
	                   	window.location.href=basePath + "/modeler.html?modelId=" + data.id;
					} else {
						alert(data.msg);
					}
				}
			});
		})
		
		$("#addProc").on("hidden.bs.modal", function(){
			$("#addProc .nameInput").val("");
			$("#addProc .keyInput").val("");
			$("#addProc .disInput").val("");
		});
		
	})
		
	</script>
</aside>

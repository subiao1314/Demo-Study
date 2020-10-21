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
				<h4>系统有以下流程供您选择:</h4>
			</div>
		</div>
		
		<div class="row">
			<div class="col-lg-12 col-xs-12">
				<ul id="myProc">
					
				</ul>
			</div>
		</div>
		
	</section>
	
	<script>
	
	$(function(){
		test();
		
		$("#myProc").on("click", ".procName", function(){
			var name = $(this).data("name");
			var location = (window.location+'').split('/');
            var basePath = location[0]+'//'+location[2]+'/'+location[3];                        
           	window.location.href=basePath + "/web/myProc/form?name=" + name;
		});
		
	})
	
	function test(){
		
		$.ajax({
			url: "web/myProc/getMyProcList",
			type: "post",
			dataType: "json",
			success: function(data){
				console.log(data);
				if(data.succ){
					var content = $("#myProc");
					var list = data.myProcList;
					for (var i=0;i<list.length;i++) {
						var arr = list[i].split("-");
						var proc = $("<li><a href='javascript:;' class='procName' data-name='"+arr[1]+"'>"+arr[1]+"</a></li>");
						content.append(proc);
					}
				}
			}
		});
	}
	/* <span class='startProc ml10' data-id='"+arr[1]+"'>启动</span> */
	</script>
</aside>

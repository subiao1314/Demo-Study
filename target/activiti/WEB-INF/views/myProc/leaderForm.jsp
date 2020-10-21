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
					<input type="text" class="form-control" id="startDate" />
				</div>
			</div>
			<div class="col-lg-3 col-xs-6">
				<div class="col-lg-4 col-xs-6">
					请假事由
				</div>
				<div class="col-lg-8 col-xs-6">
					<textarea class="form-control"></textarea>
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
						
					</select>
				</div>
			</div>
		</div>
		<div class="row mt20">
			<div class="col-lg-6 col-xs-12 text-center">
				<a href="javascript:;" class="mybtn btn-save">提交</a>
			</div>
		</div>
		
	</section>
	
	<script>
	
	$(function(){
		
		
		
		
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
					var list = data.procDefList;
					for (var i=0;i<list.length;i++) {
						var arr = list[i].split("-");
						var proc = $("<li><a href='javascript:;' class='procName' data-name='"+arr[1]+"'>"+arr[1]+"</a></li>");
						content.append(proc);
					}
				}
			}
		});
	}
	</script>
</aside>

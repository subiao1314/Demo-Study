<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- 新增题库 -->
<div class="modal fade addProc" id="addProc" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header text-center">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">添加流程</h4>
      </div>
      <div class="modal-body">
        <div class="row">
        	<div class="col-sm-3 text-right">请输入流程名称</div>
        	<div class="col-sm-7"><input class="form-control nameInput" type="text" placeholder="请输入流程名称" /></div>
        </div>
        <div class="row mt20">
        	<div class="col-sm-3 text-right">请输入流程关键词</div>
        	<div class="col-sm-7">
        		<input class="form-control keyInput" type="text" placeholder="请输入流程关键词" />
        	</div>
        </div>
        <div class="row mt20">
        	<div class="col-sm-3 text-right">请输入流程描述</div>
        	<div class="col-sm-7">
        		<textarea class="form-control disInput" placeholder="请输入流程描述"></textarea>
        	</div>
        </div>
      </div>
      <div class="modal-footer text-center">
        <a href="javascript:;" class="mybtn btn-save">确定</a>
		<a href="javascript:;" class="mybtn btn-cancel ml10" data-dismiss="modal">取消</a>
      </div>
    </div>
  </div>
</div>
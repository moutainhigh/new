<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!-- 页面CSS -->
<div class="page-bar">
	<ul class="page-breadcrumb">
        <li>
            <i class="fa fa-home"></i>
            <a href="<c:url value="/admin/index/default" />">首页</a>
            <i class="fa fa-angle-right"></i>
        </li>
        <li>
            <span>项目进度</span>
            <i class="fa fa-angle-right"></i>
        </li>
        <li>
            <a href="<c:url value="/project/archives/get" />">项目档案</a>
            <i class="fa fa-angle-right"></i>
        </li>
        <li>
            <span>档案编辑</span>
        </li>
	</ul>
	<div class="page-toolbar">
		<div class="btn-group pull-right">
			<div class="mt-action-buttons">
				<div class="btn-group btn-group-circle">
					<button type="button" class="btn btn-outline blue-hoki active" onclick="javascript:history.go(-1);">返回</button>
					<span></span>
				</div>
			</div>
		</div>
	</div>
</div>
	<!-- BEGIN SAMPLE FORM PORTLET-->
	<div class="portlet box blue-hoki">
		<div class="portlet-title">
			<div class="caption">
				<i class="fa fa-edit"></i> ${empty entity?'添加':'编辑'}
			</div>
			<div class="actions">
			</div>
		</div>
		<div class="portlet-body form">
			<%--<c:if test="${empty entity}">
				<form id="addForm" class="form-horizontal" action="<c:url value="/project/archives/post" />" method="POST" autocomplete="off" >
			</c:if>
            <c:if test="${!empty entity}">
				<form id="addForm" class="form-horizontal" action="<c:url value="/project/archives/put" />" method="POST" autocomplete="off" >
			</c:if>--%>
            <form id="addForm" class="form-horizontal" action="#" method="POST" autocomplete="off" >
				<input type="hidden" name="projectId" value="${entity.projectId}"/>
				<input type="hidden" id="id_parentId" name="parentId" value="${entity.parentId}"/>
				<div class="form-body">
					<div class="form-group">
                        <label class="col-md-1 control-label">项目编码：</label>
                        <div class="col-md-2">
                            <input type="text" class="form-control spinner" value="${entity.projectCode}" disabled="disabled" />
                        </div>
                        <label class="col-md-1 control-label">项目名称：</label>
                        <div class="col-md-2">
                            <input type="text" class="form-control spinner" name="projectName" value="${entity.projectName}" placeholder="项目名称" />
                        </div>
					</div>
                    <div class="form-group">
                        <label class="col-md-1 control-label">上级项目：</label>
                        <div class="col-md-2">
                            <div class="input-group">
                                <input class="form-control" type="text" id="id_parentName" placeholder="${empty entity||entity.isParent eq 1?'选择上级项目':'一级项目不能选择'}" readonly="readonly" value="${entity.parentName}">
                                <c:if test="${empty entity||entity.isParent eq 1}">
                                    <span class="input-group-btn"><button class="btn blue" type="button" id="id_select_parent">选择</button></span>
                                </c:if>
                            </div>
                        </div>
                        <label class="col-md-1 control-label">所属区域：</label>
                        <div class="col-md-2">
                            <select name="areaId" class="form-control" >
                                <c:forEach items="${projectAreaList}" var="projectArea">
                                    <option value="${projectArea.id}" <c:if test="${projectArea.id eq entity.areaId}">selected</c:if> >${projectArea.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
					<div class="form-group hide">
						<label class="col-md-1 control-label">计划开始日期：</label>
						<div class="col-md-2">
                            <input type="text" name="startDate" onclick="laydate({istime: true, format: 'YYYY-MM-DD'})" value="${fn:substring(entity.startDate,0,10)}" class="form-control laydate-icon" readonly="readonly" />
						</div>
                        <label class="col-md-1 control-label">计划截至日期：</label>
                        <div class="col-md-2">
                            <input type="text" name="endDate" onclick="laydate({istime: true, format: 'YYYY-MM-DD'})" value="${fn:substring(entity.endDate,0,10)}" class="form-control laydate-icon" readonly="readonly" />
                        </div>
					</div>
					<div class="form-group">
                        <label class="col-md-1 control-label">是否末级项目：</label>
                        <div class="col-md-2">
                            <select name="isPlan" class="form-control" <c:if test="${entity.isCompile eq 2}">disabled</c:if>>
                                <option value="2" <c:if test="${2 eq entity.isPlan}">selected</c:if> >是</option>
                                <option value="1" <c:if test="${1 eq entity.isPlan}">selected</c:if> >否</option>
                            </select>
                            <c:if test="${entity.isOn eq 2}">
                                <input type="hidden" name="isPlan" value="${entity.isPlan}"/>
                            </c:if>
                        </div>
                        <label class="col-md-1 control-label hide">单据日期：</label>
                        <div class="col-md-2 hide">
                            <%
                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                String date= format.format(new Date());
                            %>
                            <c:set var="defalutdata" value="<%=date%>"/>
                            <input type="text" name="documentsDate" onclick="laydate({istime: true, format: 'YYYY-MM-DD'})" value="${empty entity?defalutdata:(fn:substring(entity.documentsDate,0,10))}" class="form-control laydate-icon" readonly="readonly" />
                        </div>
					</div>
					<div class="form-group">
						<label  class="col-md-1 control-label">备注：</label>
						<div class="form-div-content col-md-6">
						 <textarea name="remark" style="height:100px;font-size:14px;width: 300px;">${entity.remark}</textarea>
						</div>
					</div>
				</div>
				<div class="form-actions">
					<div class="row">
						<div class="col-md-offset-1 col-md-10">
							<button class="btn btn-lg blue" type="submit" id="subBtn" style="margin-right: 60px;">保  存</button>
                            <a class="btn btn-lg grey-salsa" href="<c:url value="/project/archives/get"/>" >返回列表</a>
						</div>
					</div>
				</div>
			</form>
	</div>
</div>
	<!-- END SAMPLE FORM PORTLET-->
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/jquery-validation/js/jquery.validate.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/jquery-validation/js/additional-methods.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/laydate/laydate.js" />"></script>
<script type="text/javascript">

	$(function(){
        //初始化form验证
		handleValidation();
        //选择上级项目
        $("#id_select_parent").click(function () {
            layer.open({
                title:'选择项目',
                type: 2,
                area: ['1000px', '530px'],
                fixed: false, //不固定
                maxmin: true,
                content: '<c:url value="/project/archives/getparent"/> '
            });
        });

	});

</script>
<script type="text/javascript">

	var handleValidation = function() {
		var form1 = $('#addForm');
		var error1 = $('.alert-danger', form1);
		var success1 = $('.alert-success', form1);
		form1.validate({
			errorElement: 'span', //default input error message container
			errorClass: 'help-block help-block-error', // default input error message class
			focusInvalid: false, // do not focus the last invalid input
			ignore: "", // validate all fields including form hidden input
			rules: {
                projectName: {
					required: true
				},
                documentsDate: {
					required: true
				}
			},
			messages: {
                projectName: {
                    required: '不能为空'
                },
                documentsDate: {
                    required:  '不能为空'
                }
			},
			invalidHandler: function (event, validator) { //display error alert on form submit
				success1.hide();
				error1.show();
			},
			highlight: function (element) { // hightlight error inputs
				$(element).closest('.form-group').addClass('has-error'); // set error class to the control group
			},
			unhighlight: function (element) { // revert the change done by hightlight
				$(element).closest('.form-group').removeClass('has-error'); // set error class to the control group
			},
			success: function (label) {
				label.closest('.form-group').removeClass('has-error'); // set success class to the control group
			},
			submitHandler: function (form) {
				success1.show();
				error1.hide();
                layer.confirm("确定提交吗？", {icon: 3, title:'提交提示'}, function(){
                    layer.closeAll();
                    var url;
                    if (${empty entity}){
                        url = '<c:url value="/project/archives/post" />';
                    } else {
                        url = '<c:url value="/project/archives/put" />';
                    }
                    $.ajax({
                        url: url,
                        type: 'POST',
                        data:$('#addForm').serialize(),
                        dataType: 'json',
                        success: function(data){
                            layer.closeAll('loading');
                            data = JSON.parse(data);
                            if (data.code=="0"){
                                layer.msg(data.msg,{icon: 1});
                                window.setTimeout(function () {
                                    location.href='<c:url value="/project/archives/get"/>';
                                },1000);
                            } else {
                                layer.msg(data.msg,{icon: 2});
                            }
                        },
                        beforeSend:function () {layer.load(0,{shade: [0.3,'#000']});},
                        error: function(){layer.closeAll('loading');}
                    });
                    /*form.submit();
                    $('#subBtn').attr('disabled',true);
                    layer.close(index);*/
                });
			}
		});
	}


</script>

<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/tlds/lycore.tld" prefix="ly"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!-- 开始全局CSS -->
<link href="<c:url value="/resources/admin/global/plugins/font-awesome/css/font-awesome.min.css" />" rel="stylesheet" type="text/css" />
<link href="<c:url value="/resources/admin/global/plugins/simple-line-icons/simple-line-icons.min.css" />" rel="stylesheet" type="text/css" />
<link href="<c:url value="/resources/admin/global/plugins/bootstrap/css/bootstrap.min.css" />" rel="stylesheet" type="text/css" />
<link href="<c:url value="/resources/admin/global/plugins/uniform/css/uniform.default.css" />" rel="stylesheet" type="text/css" />
<link href="<c:url value="/resources/admin/global/plugins/bootstrap-switch/css/bootstrap-switch.min.css" />" rel="stylesheet" type="text/css" />
<!-- 结束全局CSS -->

<!-- 开始主题CSS -->
<link href="<c:url value="/resources/admin/global/css/components.min.css" />" rel="stylesheet" id="style_components" type="text/css" />
<link href="<c:url value="/resources/uploadify/css/uploadify.css" />" rel="stylesheet" type="text/css" />
<style>
    .actions * {
        margin-top: 0px;
    }
    body {
        font-size: 10px;
    }
    .select-delete{
        background: url(<c:url value="/resources/uploadify/img/uploadify-cancel.png"/> ) 0 0 no-repeat;
        float: right;
        text-indent: -9999px;
        width: 16px;
    }
    tr td,tr th{
        font-size: small !important;
    }
    .plan-check{
        background-color: #a0a5a8!important;
    }
</style>
	<!-- BEGIN SAMPLE FORM PORTLET-->
<div>
    <div class="portlet light" style="margin-bottom: -38px;">
        <div class="portlet-title" style="min-height:29px;">
            <div class="caption" style="line-height: 10px;">
                <span class="caption-subject font-green sbold uppercase">完成情况</span>
            </div>
        </div>
    </div>
    <div class="portlet-body form">
        <form class="form-horizontal" action="#" method="POST" autocomplete="off" >
        <div class="form-body">
            <div class="form-group">
                <label class="col-md-2 control-label">提交时间：</label>
                <div class="col-md-2">
                    <input type="text" disabled="disabled" onclick="laydate({istime: true, format: 'YYYY-MM-DD'})" value="${fn:substring(entity.completeDate,0,10)}" class="form-control" readonly="readonly" />
                </div>
            </div>
            <div class="form-group">
                <label  class="col-md-2 control-label">完成情况描述：</label>
                <div class="form-div-content col-md-6">
                    <textarea name="completeRemark" style="height:30px;font-size:14px;width: 400px;" readonly="readonly">${entity.completeRemark}</textarea>
                </div>
            </div>
            <div class="form-group">
                <label  class="col-md-2 control-label">超期完成情况说明：</label>
                <div class="form-div-content col-md-6">
                    <textarea name="noCompleteRemark" style="height:30px;font-size:14px;width: 400px;" readonly="readonly">${entity.noCompleteRemark}</textarea>
                </div>
            </div>
            <div class="form-group">
                <label  class="col-md-2 control-label">活动附件：</label>
                <div class="form-div-content col-md-6">
                    <div id="queue">
                        <c:forEach items="${projectPlanAccessoryList}" var="e">
                            <div class="uploadify-queue-item" id="select_aid_${e.id}">
                                <div class="">
                                </div>
                                <span class="fileName">${e.title}</span>
                                <a class="select-see" href="javascript:;" data-url="${applicationScope.commConfig['img_projectplan_url']}${e.url}" data-title="${e.title}">查看</a>
                                <div class="uploadify-progress">
                                    <div class="uploadify-progress-bar" style="width: 100%;"></div>
                                </div>
                            </div>
                        </c:forEach>
                        <c:if test="${empty projectPlanAccessoryList}">
                            <label>无活动附件</label>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
            <div class="form-actions" style="background-color:transparent;padding:0px;">
            </div>
        </form>
    </div>
</div>
<c:if test="${!empty checkLogList}">
    <div>
        <div class="portlet light" style="margin-bottom: -38px;">
            <div class="portlet-title" style="min-height:29px;">
                <div class="caption" style="line-height: 10px;">
                    <span class="caption-subject font-green sbold uppercase">审核记录</span>
                </div>
            </div>
        </div>
        <div class="portlet-body">
            <div class="table-scrollable fixed-table-container">
                <table class="table table-striped table-bordered table-advance table-hover" id="tlist">
                    <thead>
                    <tr>
                        <th>类型</th>
                        <th>状态</th>
                        <th>完成情况</th>
                        <th>未完成情况</th>
                        <th>审核情况</th>
                        <th>审核时间</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${checkLogList}" var="e" varStatus="status">
                        <tr class="${e.type eq 2?'plan-check':''}">
                            <td>${e.typeName}</td>
                            <td>${e.statusName}</td>
                            <td>${e.completeRemark}</td>
                            <td>${e.noCompleteRemark}</td>
                            <td>${e.remark}</td>
                            <td >${fn:substring(e.checkDate,0,10)}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</c:if>
<div>
    <div class="portlet light" style="margin-bottom: -38px;">
        <div class="portlet-title" style="min-height:29px;">
            <div class="caption" style="line-height: 10px;">
                <span class="caption-subject font-green sbold uppercase">审核完成情况</span>
            </div>
        </div>
    </div>
		<div class="portlet-body form">
            <form id="addForm" class="form-horizontal" action="#" method="POST" autocomplete="off" >
				<input type="hidden" name="compileId" value="${entity.compileId}"/>
				<input type="hidden" name="projectId" value="${entity.projectId}"/>
				<input type="hidden" name="templateId" value="${entity.templateId}"/>
				<div class="form-body">
                    <div class="form-group">
                        <label class="col-md-2 control-label">是否通过：</label>
                        <div class="col-md-2">
                            <select name="status" class="form-control" id="id_status">
                                <option value="2" >是</option>
                                <option value="1" >否</option>
                            </select>
                        </div>
                    </div>
					<div class="form-group">
						<label  class="col-md-2 control-label">审核情况：</label>
						<div class="form-div-content col-md-6">
						 <textarea name="remark" id="id_remark" style="height:30px;font-size:14px;width: 400px;">审核通过</textarea>
						</div>
					</div>
                    <div class="form-group" id="id_completeDate">
                        <label class="col-md-2 control-label">完成时间：</label>
                        <div class="col-md-2">
                            <%
                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                String date= format.format(new Date());
                            %>
                            <c:set var="defalutdata" value="<%=date%>"/>
                            <input type="text" name="completeDate" onclick="laydate({istime: true, format: 'YYYY-MM-DD'})" value="${defalutdata}" class="form-control laydate-icon" readonly="readonly" />
                        </div>
					</div>
				</div>
				<div class="form-actions" style="background-color:transparent;">
					<div class="row">
						<div class="col-md-offset-1 col-md-10">
							<button class="btn btn-sm blue" type="button" id="subBtn" style="margin-right: 60px;">提  交</button>
						</div>
					</div>
				</div>
			</form>
	</div>
</div>

	<!-- END SAMPLE FORM PORTLET-->
<script src="<c:url value="/resources/admin/global/plugins/jquery.min.js" />" type="text/javascript"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/jquery-validation/js/jquery.validate.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/jquery-validation/js/additional-methods.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/layer/layer.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/laydate/laydate.js" />"></script>
<script type="text/javascript">

	$(function(){

	    $('.select-see').on('click',function () {
            parent.open($(this).data("url"),$(this).data("title"),"fullscreen=1");
        });

        $('#subBtn').on('click',function () {
            data_submit();
        });

        $('#id_status').on('change',function () {
            if ($(this).val()==1) {
                $('#id_remark').val('审核未通过');
                $('#id_completeDate').addClass('hide');
            } else if ($(this).val()==2) {
                $('#id_remark').val('审核通过');
                $('#id_completeDate').removeClass('hide');
            }
        });

        var data_submit = function () {
            var index = parent.layer.confirm("确定提交吗？", {icon: 3, title:'提交提示'}, function(){
                parent.layer.close(index);
                $.ajax({
                    url:  '<c:url value="/project/plan/checkeput" />',
                    type: 'POST',
                    data:$('#addForm').serialize(),
                    dataType: 'json',
                    success: function(data){
                        parent.layer.closeAll('loading');
                        data = JSON.parse(data);
                        if (data.code=="0"){
                            parent.layer.msg(data.msg,{icon: 1});
                            window.setTimeout(function () {
                                parent.location.reload(true);
                            },1000);
                        } else {
                            parent.layer.msg(data.msg,{icon: 2});
                        }
                    },
                    beforeSend:function () {parent.layer.load(0,{shade: [0.3,'#000']});},
                    error: function(){parent.layer.closeAll('loading');}
                });
            });
        }

    });

</script>


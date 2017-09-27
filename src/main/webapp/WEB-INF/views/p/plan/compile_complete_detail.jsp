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
                <span class="caption-subject font-green sbold uppercase">查看完成情况</span>
            </div>
        </div>
    </div>
		<div class="portlet-body form">
            <form id="addForm" class="form-horizontal" action="#" method="POST" autocomplete="off">
				<input type="hidden" name="compileId" value="${entity.compileId}"/>
				<input type="hidden" name="projectId" value="${entity.projectId}"/>
				<div class="form-body">
                    <div class="form-group">
                        <%--<label class="col-md-2 control-label">是否完成：</label>
                        <div class="col-md-2">
                            <select class="form-control" disabled="disabled">
                                <option value="2" <c:if test="${2 eq entity.isComplete}">selected</c:if> >是</option>
                                <option value="3" <c:if test="${3 eq entity.isComplete}">selected</c:if> >否</option>
                            </select>
                        </div>--%>
                        <label class="col-md-2 control-label">完成时间：</label>
                        <div class="col-md-2">
                            <input type="text" disabled="disabled" onclick="laydate({istime: true, format: 'YYYY-MM-DD'})" value="${fn:substring(entity.completeDate,0,10)}" class="form-control laydate-icon" readonly="readonly" />
                        </div>
                    </div>
					<div class="form-group">
						<label  class="col-md-2 control-label">完成情况描述：</label>
						<div class="form-div-content col-md-6">
						 <textarea name="completeRemark" style="height:60px;font-size:14px;width: 400px;" readonly="readonly">${entity.completeRemark}</textarea>
						</div>
					</div>
                    <div class="form-group">
                        <label  class="col-md-2 control-label">超期完成情况说明：</label>
                        <div class="form-div-content col-md-6">
                            <textarea name="noCompleteRemark" style="height:60px;font-size:14px;width: 400px;" readonly="readonly">${entity.noCompleteRemark}</textarea>
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
	<!-- END SAMPLE FORM PORTLET-->
<script src="<c:url value="/resources/admin/global/plugins/jquery.min.js" />" type="text/javascript"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/layer/layer.js" />"></script>
<script type="text/javascript">

	$(function(){

	    $('.select-see').on('click',function () {
            parent.open($(this).data("url"),$(this).data("title"),"fullscreen=1");
        });

    });

</script>


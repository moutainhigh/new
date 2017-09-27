<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/tlds/lycore.tld" prefix="ly"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<style>
    .actions * {
        margin-top: 0px;
    }
    body {
        font-size: 10px;
    }
    tr td,tr th{
        font-size: small !important;
    }
    .stateC{
        position:relative;
        display:inline-block;
        width:20px;
        height:20px;
        border-radius: 50% !important;
        -webkit-border-radius: 50%;
        -moz-border-radius: 50%;
        -o-border-radius: 50%;
        -ms-border-radius: 50%;
        vertical-align:middle;
    }
    .not_Begin{
        background-color:#cecece;
    }
    .s_Normal{
        background-color:#328a79;
    }
    .s_Delay{
        background-color:#f73a41;
    }
    .hook:after{
        position:absolute;
        top: 10%;
        left: 30%;
        content:"";
        width: 25%;
        height: 50%;
        border: solid #fff;
        border-width: 0 2px 2px 0;
        transform: rotate(45deg);
        -ms-transform:rotate(45deg);
        -moz-transform:rotate(45deg);
        -webkit-transform:rotate(45deg);
        -o-transform:rotate(45deg);
    }
    label{
        font-size: small;
    }
</style>
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
            <a href="<c:url value="/project/plan/planget" />">进度维护</a>
            <i class="fa fa-angle-right"></i>
        </li>
        <li>
            <span>进度维护详情</span>
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
<!-- BEGIN EXAMPLE TABLE PORTLET-->
<div class="portlet light" id="form_wizard_1">
    <div class="portlet-title">
        <div class="caption">
            <span class="caption-subject font-green sbold uppercase">进度维护</span>
        </div>
    </div>
    <div class="portlet-title">
        <div class="actions" style="float: left;">
            <label>项目名称：</label>
            <input type="text" class="form-control input-inline input-sm" value="${projectArchives.projectName}" disabled="disabled"/>&nbsp;&nbsp;&nbsp;&nbsp;
            <label>版本：</label>
            <input type="text" class="form-control input-inline input-sm" value="${projectArchives.versions}" disabled="disabled" style="width: 30px;"/>
        </div>
    </div>
    <div class="portlet-body" >
        <div class="table-scrollable fixed-table-container" style="height: 460px;overflow:auto;">
            <table class="table table-striped table-bordered table-advance table-hover" id="tlist" >
                <thead>
                    <tr>
                        <th >状态</th>
                        <th >序号</th>
                        <th >阶段</th>
                        <th >任务名称</th>
                        <th >关键节点</th>
                        <th >计划开始日期</th>
                        <th >计划截至日期</th>
                        <th >主办部门</th>
                        <th >预警人</th>
                        <th >权重</th>
                        <th >操作</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${projectPlanCompileList}" var="e" varStatus="status">
                        <tr>
                            <td style="text-align: center !important;" >
                                <span class="${e.completeStatus eq 1?'stateC s_Normal hook':(e.completeStatus eq 2?'stateC s_Delay hook':(e.completeStatus eq 3?'stateC s_Delay':(e.completeStatus eq 4?'stateC not_Begin':'')))}"></span>
                            </td>
                            <td >${e.templateId}</td>
                            <td style="width: 100px;">${e.stepName}</td>
                            <td style="width: 300px;">${e.taskName}</td>
                            <%--<td class="bs-checkbox" style="text-align: center !important;" ><span class="${e.iskeyNode eq 2?'stateC s_Delay':'stateC not_Begin'}"></span></td>--%>
                            <td style="<c:if test="${e.iskeyNode eq 2}" >color: red;</c:if>" >${e.iskeyNodeName}</td>
                            <td >${fn:substring(e.startDate,0,10)}</td>
                            <td >${fn:substring(e.endDate,0,10)}</td>
                            <td  title="${e.department}" >${e.shortDepartment} </td>
                            <td  title="${e.warnName}">${e.shortWarnName}</td>
                            <td >${e.weight}</td>
                            <td >
                                <c:if test="${e.isComplete eq 1}">
                                    <button type="button" class="btn btn-xs blue-hoki form_edit" data-pid="${e.projectId}" data-cid="${e.compileId}">完成</button>
                                </c:if>
                                <c:if test="${e.isComplete eq 3}" >
                                    <button type="button" class="btn btn-xs green-meadow form_edit" data-pid="${e.projectId}" data-cid="${e.compileId}">编辑</button>
                                </c:if>
                                <c:if test="${e.isComplete eq 4}" >
                                    <button type="button" class="btn btn-xs yellow form_edit" data-pid="${e.projectId}" data-cid="${e.compileId}">更正</button>
                                </c:if>
                                <c:if test="${e.isComplete ne 1}">
                                    <button type="button" class="btn btn-xs grey-cascade form_dtl" data-pid="${e.projectId}" data-cid="${e.compileId}">查看</button>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="row">
            <div class="col-md-7 col-sm-7 text-right" >
                <span class="stateC not_Begin"></span> 未超期未完成
                <%--<span class="stateC s_Normal"></span>--%>
                <span class="stateC s_Delay"></span> 超期未完成
                <span class="stateC s_Normal hook"></span> 按时完成
                <span class="stateC s_Delay hook"></span> 未按时完成
                <a class="btn grey-salsa" href="<c:url value="/project/plan/planget"/>" >返回列表</a>
            </div>
        </div>
    </div>
</div>

<!-- END EXAMPLE TABLE PORTLET-->
<script type="text/javascript">
    //选择上级项目
    $(".form_edit").click(function () {
        layer.open({
            title:'编辑完成情况',
            type: 2,
            area: ['1000px', '530px'],
            fixed: false, //不固定
            maxmin: true,
            content: '<c:url value="/project/plan/completeedit/"/> '+$(this).data('pid')+'/'+$(this).data('cid')
        });
    });

    //选择上级项目
    $(".form_dtl").click(function () {
        layer.open({
            title:'查看完成情况',
            type: 2,
            area: ['1000px', '530px'],
            fixed: false, //不固定
            maxmin: true,
            content: '<c:url value="/project/plan/completedtl/"/> '+$(this).data('pid')+'/'+$(this).data('cid')
        });
    });
</script>
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
<style>
    .actions * {
        margin-top: 0px;
    }
    body {
        font-size: 10px;
    }
</style>
<!-- BEGIN EXAMPLE TABLE PORTLET-->
<div class="portlet light" id="form_wizard_1">
    <div class="portlet-title">
        <div class="caption">
            <span class="caption-subject font-green sbold uppercase">选择项目</span>
       	</div>
    </div>
    <div class="portlet-title">
        <div class="actions">
            <label>项目名称：</label>
            <input id="id_s1" class="form-control input-inline input-sm" type="text" value="${entity.s1}" placeholder="项目名称"/>
            <label>所属区域：</label>
            <select id="id_i1" class="form-control input-inline input-sm" style="width: 200px;" autocomplete="off">
                <option value="0">请选择区域</option>
                <c:forEach items="${projectAreaList}" var="projectArea">
                    <option value="${projectArea.id}" <c:if test="${projectArea.id eq entity.i1}">selected</c:if> >${projectArea.name}</option>
                </c:forEach>
            </select>
            <button class="btn btn-sm green" onclick="search();"><i class="fa fa-search"></i> 搜索 </button>
        </div>
    </div>
    <div class="portlet-body">
        <div class="table-scrollable fixed-table-container">
        <table class="table table-striped table-bordered table-advance table-hover" id="tlist">
            <thead>
                <tr>
                    <th>选择</th>
                    <th>项目编码</th>
                    <th>项目名称</th>
                    <%--<th>计划开始日期</th>
                    <th>计划截至日期</th>--%>
                    <th>所属区域</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${page.content}" var="e" varStatus="status">
                    <tr>
                        <td class="bs-checkbox"><label class="mt-radio mt-radio-single mt-radio-outline"><input name="btSelectItem" type="radio" value="${e.projectId}" data-title="${e.projectName}"><span></span></label></td>
                        <td>${e.projectCode}</td>
                        <td>${e.projectName}</td>
                        <%--<td>${fn:substring(e.startDate,0,10)}</td>
                        <td>${fn:substring(e.endDate,0,10)}</td>--%>
                        <td>${e.areaName}</td>
                    </tr>
                </c:forEach>
                <c:if test="${empty page.content}">
                	<tr><td colspan="20">没有对应的数据</td></tr>
                </c:if>
            </tbody>
        </table>
        </div>
        <div class="row">
	    	<div class="col-md-7 col-sm-7 text-left">
                <ly:pageTag link="/project/archives/getparent${ly:toString(entity,'i1,s1,dt,dtn')}&pageNo=" page="${page}"></ly:pageTag>
	    	</div>
    	</div>
        <div class="row">
            <div class="col-md-7 col-sm- text-right" >
                <button type="button" class="btn blue" id="id_transmit" style="margin-right: 20px;">确定</button>
                <button type="button" class="btn default" id="id_cancel">取消</button>
            </div>
        </div>
    </div>
</div>
<!-- END EXAMPLE TABLE PORTLET-->
<script src="<c:url value="/resources/admin/global/plugins/jquery.min.js" />" type="text/javascript"></script>
<script type="text/javascript">
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    $(function () {
        //给父页面传值
        $('#id_transmit').on('click', function(){
            if (${empty page.content}){
                parent.layer.msg('您还未选择项目');
            } else {
                var val=$('input:radio[name="btSelectItem"]:checked').val();
                if (val==null) {
                    parent.layer.msg('您还未选择项目');
                } else {
                    var title=$('input:radio[name="btSelectItem"]:checked').data('title');
                    parent.$('#id_parentId').val(val);
                    parent.$('#id_parentName').val(title);
                    parent.layer.close(index);
                }
           }
        });

        $('#id_cancel').on('click', function(){
            parent.layer.close(index);
        });
    });

    function search() {
        window.location.href = "<c:url value="/project/archives/getparent"/>"+ "${ly:toString(entity,'pageSize,dt,dtn')}"+"&s1="+$.trim($("#id_s1").val())+"&i1="+$("#id_i1").val();
    }

</script>
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
            <a href="<c:url value="/project/archives/get" />">项目档案</a>
        </li>
    </ul>
</div>
<!-- BEGIN EXAMPLE TABLE PORTLET-->
<div class="portlet light" id="form_wizard_1">
    <div class="portlet-title">
        <div class="caption">
        	<i class="icon-layers font-green"></i>
            <span class="caption-subject font-green sbold uppercase">项目档案</span>
       	</div>
        <div class="actions">
            <a class="btn sbold blue" href="<c:url value="/project/archives/edit"/>"><i class="fa fa-plus-circle"></i>增加</a>
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
            <%--<a class="btn btn-sm blue-hoki" href="<c:url value="/admin/shop/item/xls"/>${ly:toString(entity,'pageSize,l1,l2,s1')}"><i class="fa fa-download"></i> 导出Excel</a>--%>
        </div>
    </div>
    <div class="portlet-body">
        <div class="table-scrollable fixed-table-container">
        <table class="table table-striped table-bordered table-advance table-hover" id="tlist">
            <thead>
                <tr>
                    <th>项目编码</th>
                    <th>项目名称</th>
                    <th>所属区域</th>
                    <th>上级项目</th>
                    <th>当前版本</th>
                    <th>是否末级项目</th>
                    <th width="22%">操作</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${page.content}" var="e" varStatus="status">
                    <tr>
                        <td>${e.projectCode}</td>
                        <td>${e.projectName}</td>
                        <td>${e.areaName}</td>
                        <td>${e.parentName}</td>
                        <td>${e.versions}</td>
                        <td>${e.isPlanName}</td>
                        <td>
                            <a class="btn btn-xs green-meadow" href="<c:url value="/project/archives/edit?id="/>${e.projectId}"><i class="fa fa-edit"></i> 编辑 </a>
                            <c:if test="${e.isCompile eq 1&&e.associatedNum eq 0}">
                                <button type="button" class="btn btn-xs red-sunglo" onclick="del(${e.projectId},'${e.projectName}')"><i class="fa fa-times"></i> 删除 </button>
                            </c:if>
                            <c:if test="${e.isCompile eq 2&&e.isComplete eq 1}">
                                <button type="button" class="btn btn-xs blue-hoki" onclick="ok(${e.projectId},'${e.projectName}')"><i class="fa fa-check"></i> 完成 </button>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty page.content}">
                	<tr><td colspan="20">没有对应的数据</td></tr>
                </c:if>
            </tbody>
        </table>
        </div>
        <div class="row">
	    	<div class="col-md-5 col-sm-5">
	    		<ly:pageSizeTag link="/project/archives/get${ly:toString(entity,'i1,s1,dt,dtn')}" page="${page}"></ly:pageSizeTag>
	    	</div>
	    	<div class="col-md-7 col-sm-7 text-right">
                <ly:pageTag link="/project/archives/get${ly:toString(entity,'i1,s1,dt,dtn')}&pageNo=" page="${page}"></ly:pageTag>
	    	</div>
    	</div>
    </div>
</div>
<!-- END EXAMPLE TABLE PORTLET-->
<script type="text/javascript">

    function search() {
        window.location.href = "<c:url value="/project/archives/get"/>"+ "${ly:toString(entity,'pageSize,dt,dtn')}"+"&s1="+$.trim($("#id_s1").val())+"&i1="+$("#id_i1").val();
    }

    function del(id,t) {
        layer.confirm("确定删除【"+t+"】吗？", {icon: 3, title:'删除提示'}, function(){
            layer.closeAll();
            $.ajax({
                url: '<c:url value="/project/archives/del/" />'+id,
                type: 'POST',
                data:'',
                dataType: 'json',
                success: function(data){
                    layer.closeAll('loading');
                    data = JSON.parse(data);
                    if (data.code=="0"){
                        layer.msg(data.msg,{icon: 1});
                        window.setTimeout(function () {
                            location.reload(true);
                        },1000);
                    } else {
                        layer.msg(data.msg,{icon: 2});
                    }
                },
                beforeSend:function () {layer.load(0,{shade: [0.3,'#000']});},
                error: function(){layer.closeAll('loading');}
            });
        });
    }

    function ok(id,t) {
        layer.confirm("确定完成【"+t+"】吗？", {icon: 3, title:'完成操作提示'}, function(){
            layer.closeAll();
            $.ajax({
                url: '<c:url value="/project/archives/complete/" />'+id,
                type: 'POST',
                data:'',
                dataType: 'json',
                success: function(data){
                    layer.closeAll('loading');
                    data = JSON.parse(data);
                    if (data.code=="0"){
                        layer.msg(data.msg,{icon: 1});
                        window.setTimeout(function () {
                            location.reload(true);
                        },1000);
                    } else {
                        layer.msg(data.msg,{icon: 2});
                    }
                },
                beforeSend:function () {layer.load(0,{shade: [0.3,'#000']});},
                error: function(){layer.closeAll('loading');}
            });
        });
    }

</script>
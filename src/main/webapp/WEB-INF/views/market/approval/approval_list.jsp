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
            <span>月度立项</span>
            <i class="fa fa-angle-right"></i>
        </li>
        <li>
            <a href="<c:url value="/market/approval/get" />">立项审批</a>
            <i class="fa fa-angle-right"></i>
        </li>
    </ul>
</div>
<!-- BEGIN EXAMPLE TABLE PORTLET-->
<div class="portlet light" id="form_wizard_1">
    <div class="portlet-title">
        <div class="caption">
        	<i class="icon-layers font-green"></i>
            <span class="caption-subject font-green sbold uppercase">立项审批</span>
       	</div>
        <div class="actions">
            <label>状态：</label>
            <select id="id_i1" class="form-control input-inline input-sm" style="width: 200px;" autocomplete="off">
                <option value="" >选择状态</option>
                <option value="1" <c:if test="${1 eq entity.i1}">selected</c:if> >申请中</option>
                <option value="2" <c:if test="${2 eq entity.i1}">selected</c:if> >已归档</option>
                <option value="3" <c:if test="${3 eq entity.i1}">selected</c:if> >已撤销</option>
            </select>
            <label>所属公司：</label>
            <select id="id_i2" class="form-control input-inline input-sm" style="width: 200px;" autocomplete="off">
                <option value="" >选择所属公司</option>
                <c:forEach items="${companyList}" var="company">
                    <option value="${company.companyId}" <c:if test="${company.companyId eq entity.i2}">selected</c:if> >${company.name}</option>
                </c:forEach>
            </select>
			<button class="btn btn-sm green" onclick="search();"><i class="fa fa-search"></i> 搜索 </button>
        </div>
    </div>
    <div class="portlet-body">
        <div class="table-scrollable fixed-table-container">
        <table data-toggle="table" data-height="460" class="table table-striped table-bordered table-advance table-hover" id="tlist">
            <thead>
                <tr>
                    <th>立项标题</th>
                    <th>公司名称</th>
                    <th>项目名称</th>
                    <th>状态</th>
                    <th>申请人姓名</th>
                    <th>归属年份</th>
                    <th>归属月份</th>
                    <th>申请金额</th>
                    <th>审批金额</th>
                    <th>剩余金额</th>
                    <th>创建时间</th>
                    <%--<th width="22%">操作</th>--%>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${page.content}" var="e" varStatus="status">
                    <tr>
                        <td>${e.title}</td>
                        <td>${e.companyName}</td>
                        <td>${e.projectName}</td>
                        <td>${e.statusName}</td>
                        <td>${e.applyName}</td>
                        <td>${e.year}</td>
                        <td>${e.month}</td>
                        <td>${e.applyMoney}</td>
                        <td>${e.finalMoney}</td>
                        <td>${e.balanceMoney}</td>
                        <td>${fn:substring(e.createDate,0 ,19)}</td>
                        <%--<td>
                            <a class="btn btn-xs green-meadow" href="<c:url value="/project/archives/edit?id="/>${e.projectId}"><i class="fa fa-edit"></i> 编辑 </a>
                            <c:if test="${e.isCompile eq 1&&e.associatedNum eq 0}">
                                <button type="button" class="btn btn-xs red-sunglo" onclick="del(${e.projectId},'${e.projectName}')"><i class="fa fa-times"></i> 删除 </button>
                            </c:if>
                            <c:if test="${e.isCompile eq 2&&e.isComplete eq 1}">
                                <button type="button" class="btn btn-xs blue-hoki" onclick="ok(${e.projectId},'${e.projectName}')"><i class="fa fa-check"></i> 完成 </button>
                            </c:if>
                        </td>--%>
                    </tr>
                </c:forEach>
                <%--<c:if test="${empty page.content}">
                	<tr><td colspan="20">没有对应的数据</td></tr>
                </c:if>--%>
            </tbody>
        </table>
        </div>
        <div class="row">
	    	<div class="col-md-5 col-sm-5">
	    		<ly:pageSizeTag link="/market/approval/get${ly:toString(entity,'i1,i2,dt,dtn')}" page="${page}"></ly:pageSizeTag>
	    	</div>
	    	<div class="col-md-7 col-sm-7 text-right">
                <ly:pageTag link="/market/approval/get${ly:toString(entity,'i1,i2,dt,dtn')}&pageNo=" page="${page}"></ly:pageTag>
	    	</div>
    	</div>
    </div>
</div>
<!-- END EXAMPLE TABLE PORTLET-->
<script type="text/javascript">

    function search() {
        window.location.href = "<c:url value="/market/approval/get"/>"+ "${ly:toString(entity,'pageSize,dt,dtn')}"+"&i2="+$("#id_i2").val()+"&i1="+$("#id_i1").val();
    }

</script>
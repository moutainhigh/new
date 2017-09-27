<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/tlds/lycore.tld" prefix="ly"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<div class="page-bar">
    <ul class="page-breadcrumb">
        <li>
            <i class="fa fa-home"></i>
            <a href="<c:url value="/admin/index/default" />">首页</a>
            <i class="fa fa-angle-right"></i>
        </li>
        <li>
            <span>报表管理</span>
            <i class="fa fa-angle-right"></i>
        </li>
        <li>
            <span>冲帐未完成</span>
        </li>
    </ul>
</div>
<!-- BEGIN EXAMPLE TABLE PORTLET-->
<div class="portlet light" id="form_wizard_1">
    <div class="portlet-title">
        <div class="caption">
        	<i class="icon-layers font-green"></i>
            <span class="caption-subject font-green sbold uppercase">冲帐未完成统计表</span>
       	</div>
        <div class="actions">
            <label>借款日期：</label>
            <input type="text" id="id_d3" class="form-control input-inline form_datetime" style="height: 30px;width: 160px;" value="${fn:substring(entity.d3,0,10)}" readonly="readonly" onclick="laydate({istime: true, format: 'YYYY-MM-DD'})" autocomplete="off"/> -
            <input type="text" id="id_d4" class="form-control input-inline form_datetime" style="height: 30px;width: 160px;" value="${fn:substring(entity.d4,0,10)}" readonly="readonly" onclick="laydate({istime: true, format: 'YYYY-MM-DD'})" autocomplete="off"/>
            <button class="btn btn-sm green" onclick="search();"><i class="fa fa-search"></i> 搜索 </button>
        </div>
    </div>
    <div class="portlet-body">
        <div class="table-scrollable">
        <table data-toggle="table" data-height="500" class="table table-striped table-bordered table-advance table-hover" id="tlist">
            <thead>
                <tr>
                    <th>流水号</th>
                    <th>借款事由</th>
                    <th>申请日期</th>
                    <th>借款人</th>
                    <th>借款金额</th>
                    <th>已冲账金额</th>
                    <th>冲账中金额</th>
                    <th>待冲账金额</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${page.content}" var="e" varStatus="status">
                    <tr>
                        <td>${e.serialsNumber}</td>
                        <td>${e.remark}</td>
                        <td>${fn:substring(e.applyDate,0,10)}</td>
                        <td>${e.applyName}</td>
                        <td>${e.loanMoney}</td>
                        <td>${e.repayMoney}</td>
                        <td>${e.balanceMoney}</td>
                        <td>${e.waitbalanceMoney}</td>
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
	    		<ly:pageSizeTag link="/market/report/outofbalance${ly:toString(entity,'d3,d4,dt,dtn')}" page="${page}"></ly:pageSizeTag>
	    	</div>
	    	<div class="col-md-7 col-sm-7 text-right">
                <ly:pageTag link="/market/report/outofbalance${ly:toString(entity,'d3,d4,dt,dtn')}&pageNo=" page="${page}"></ly:pageTag>
	    	</div>
    	</div>
    </div>
</div>
<!-- END EXAMPLE TABLE PORTLET-->
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/laydate/laydate.js" />"></script>
<script type="text/javascript">

    function search() {
        window.location.href = "<c:url value="/market/report/outofbalance"/>"+"${ly:toString(entity,'pageSize,dt,dtn')}"+"&d3="+$.trim($('#id_d3').val())+"&d4="+$('#id_d4').val();
    }


</script>
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
            <span>实际发生明细</span>
        </li>
    </ul>
</div>
<!-- BEGIN EXAMPLE TABLE PORTLET-->
<div class="portlet light" id="form_wizard_1">
    <div class="portlet-title">
        <div class="caption">
        	<i class="icon-layers font-green"></i>
            <span class="caption-subject font-green sbold uppercase">实际发生明细列表</span>
       	</div>
        <div class="actions">
            <label>业务类别：</label>
            <select id="id_i1" class="form-control input-inline input-sm" style="width: 120px;align-content: center;" autocomplete="off">
                <option value="0"> 请选择 </option>
                <option value="1" <c:if test="${entity.i1 eq 1}">selected</c:if> >单次合同</option>
                <option value="2" <c:if test="${entity.i1 eq 2}">selected</c:if> >框架合同</option>
                <option value="3" <c:if test="${entity.i1 eq 3}">selected</c:if> >费用报销</option>
                <option value="4" <c:if test="${entity.i1 eq 4}">selected</c:if> >差旅费报销</option>
            </select>
            <label>发生日期：</label>
            <input type="text" id="id_d3" class="form-control input-inline form_datetime" style="height: 30px;width: 160px;" value="${fn:substring(entity.d3,0,10)}" readonly="readonly" onclick="laydate({istime: true, format: 'YYYY-MM-DD'})" autocomplete="off"/> -
            <input type="text" id="id_d4" class="form-control input-inline form_datetime" style="height: 30px;width: 160px;" value="${fn:substring(entity.d4,0,10)}" readonly="readonly" onclick="laydate({istime: true, format: 'YYYY-MM-DD'})" autocomplete="off"/>
            <input id="id_s1" class="form-control input-inline input-sm" style="width: 150px;" type="text" value="${entity.s1}" placeholder="合同名称/报销事由" autocomplete="off" />
            <button class="btn btn-sm green" onclick="search();"><i class="fa fa-search"></i> 搜索 </button>
        </div>
    </div>
    <div class="portlet-body">
        <div class="table-scrollable">
        <table data-toggle="table" data-height="500" class="table table-striped table-bordered table-advance table-hover" id="tlist">
            <thead>
                <tr>
                    <th>流水号</th>
                    <th>业务类别</th>
                    <th>合同名称/报销事由</th>
                    <th>公司</th>
                    <th>项目</th>
                    <th>归属科目</th>
                    <th>发生日期</th>
                    <th>发生金额</th>
                    <th>经办人</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${page.content}" var="e" varStatus="status">
                    <tr>
                        <td>${e.contractCode}</td>
                        <td>${e.businessTypeName}</td>
                        <td>${e.contractName}</td>
                        <td>${e.companyName}</td>
                        <td>${e.projectName}</td>
                        <td>${e.secondName} - ${e.threeName}</td>
                        <td>${fn:substring(e.currentDate,0,10)}</td>
                        <td>${e.money}</td>
                        <td>${e.agentName}</td>
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
	    		<ly:pageSizeTag link="/market/report/happendetail${ly:toString(entity,'i1,s1,d3,d4,dt,dtn')}" page="${page}"></ly:pageSizeTag>
	    	</div>
	    	<div class="col-md-7 col-sm-7 text-right">
                <ly:pageTag link="/market/report/happendetail${ly:toString(entity,'i1,s1,d3,d4,dt,dtn')}&pageNo=" page="${page}"></ly:pageTag>
	    	</div>
    	</div>
    </div>
</div>
<!-- END EXAMPLE TABLE PORTLET-->
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/laydate/laydate.js" />"></script>
<script type="text/javascript">

    function search() {
        window.location.href = "<c:url value="/market/report/happendetail"/>"+"${ly:toString(entity,'pageSize,dt,dtn')}"+"&i1="+$.trim($('#id_i1').val())+"&s1="+$('#id_s1').val()+"&d3="+$.trim($('#id_d3').val())+"&d4="+$('#id_d4').val();
    }


</script>
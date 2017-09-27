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
            <span>台账管理</span>
            <i class="fa fa-angle-right"></i>
        </li>
        <li>
            <a href="<c:url value="/market/ledger/contractlist" />">合同台账</a>
            <i class="fa fa-angle-right"></i>
        </li>
    </ul>
</div>
<!-- BEGIN EXAMPLE TABLE PORTLET-->
<div class="portlet light" id="form_wizard_1">
    <div class="portlet-title">
        <div class="caption">
        	<i class="icon-layers font-green"></i>
            <span class="caption-subject font-green sbold uppercase">合同台账</span>
       	</div>
        <div class="actions">
            <label>合同类别：</label>
            <select id="id_i1" class="form-control input-inline input-sm" style="width: 120px;align-content: center;" autocomplete="off">
                <option value="0"> 请选择 </option>
                <option value="1" <c:if test="${entity.i1 eq 1}">selected</c:if> >单次合同</option>
                <option value="2" <c:if test="${entity.i1 eq 2}">selected</c:if> >框架合同</option>
            </select>
            <label>申请日期：</label>
            <input type="text" id="id_d3" class="form-control input-inline form_datetime" style="height: 30px;width: 160px;" value="${fn:substring(entity.d3,0,10)}" readonly="readonly" onclick="laydate({istime: true, format: 'YYYY-MM-DD'})" autocomplete="off"/> -
            <input type="text" id="id_d4" class="form-control input-inline form_datetime" style="height: 30px;width: 160px;" value="${fn:substring(entity.d4,0,10)}" readonly="readonly" onclick="laydate({istime: true, format: 'YYYY-MM-DD'})" autocomplete="off"/>
            <input id="id_s1" class="form-control input-inline input-sm" style="width: 150px;" type="text" value="${entity.s1}" placeholder="合同编号/合同名称" autocomplete="off" />
            <button class="btn btn-sm green" onclick="search();"><i class="fa fa-search"></i> 搜索 </button>
        </div>
    </div>
    <div class="portlet-body">
        <div class="table-scrollable fixed-table-container">
        <table data-toggle="table" data-height="460" class="table table-striped table-bordered table-advance table-hover" id="tlist">
            <thead>
                <tr>
                    <th>合同类别</th>
                    <th>合同编号</th>
                    <th>合同名称</th>
                    <th>经办人</th>
                    <th>申请日期</th>
                    <th>合同金额</th>
                    <th>对方单位</th>
                    <th>城市公司</th>
                    <th>所属项目</th>
                    <th width="22%">操作</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${page.content}" var="e" varStatus="status">
                    <tr>
                        <td>${e.businessTypeName}</td>
                        <td>${e.contractCode}</td>
                        <td>${e.contractName}</td>
                        <td>${e.agentName}</td>
                        <td>${fn:substring(e.applyDate,0 ,10)}</td>
                        <td>${e.contractMoney}</td>
                        <td>${e.bunit}</td>
                        <td>${e.companyName}</td>
                        <td>${e.projectName}</td>
                        <td>
                            <c:if test="${e.type eq 1}">
                                <a class="btn btn-xs grey-cascade" href="javascript:marketLedger('<c:url value="/market/contract/marketledger/"/>${e.contractId}');" data-url="<c:url value="/market/contract/marketledger/"/>${e.contractId}"><i class="fa fa-file-text-o"></i> 详情 </a>
                            </c:if>
                            <c:if test="${e.type eq 2}">
                                <a class="btn btn-xs grey-cascade" href="javascript:marketLedger('<c:url value="/market/contract/frameworkledger/"/>${e.contractId}');" data-url="<c:url value="/market/contract/frameworkledger/"/>${e.contractId}"><i class="fa fa-file-text-o"></i> 详情 </a>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        </div>
        <div class="row">
	    	<div class="col-md-5 col-sm-5">
	    		<ly:pageSizeTag link="/market/ledger/contractlist${ly:toString(entity,'s1,i1,d3,d4,dt,dtn')}" page="${page}"></ly:pageSizeTag>
	    	</div>
	    	<div class="col-md-7 col-sm-7 text-right">
                <ly:pageTag link="/market/ledger/contractlist${ly:toString(entity,'s1,i1,d3,d4,dt,dtn')}&pageNo=" page="${page}"></ly:pageTag>
	    	</div>
    	</div>
    </div>
</div>
<!-- END EXAMPLE TABLE PORTLET-->
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/laydate/laydate.js" />"></script>
<script type="text/javascript">

    function search() {
        window.location.href = "<c:url value="/market/ledger/contractlist"/>"+ "${ly:toString(entity,'pageSize,dt,dtn')}"+"&s1="+$("#id_s1").val()+"&i1="+$("#id_i1").val()+"&d3="+$("#id_d3").val()+"&d4="+$("#id_d4").val();
    }

    function marketLedger(url) {
        layer.open({
            title:'台账',
            type: 2,
            area: ['50%', '86%'],
            fixed: false, //不固定
            maxmin: true,
            content: url
        });
    }

</script>
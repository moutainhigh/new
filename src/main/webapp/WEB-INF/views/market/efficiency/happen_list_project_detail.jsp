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
<link href="<c:url value="/resources/admin/global/plugins/bootstrap-table/bootstrap-table.min.css" />" rel="stylesheet" type="text/css" />
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
            <span class="caption-subject font-green sbold uppercase">费用实际发生明细</span>
        </div>
    </div>
    <div class="portlet-title">
        <div class="caption">
            <label>科目名称：</label>
            <input type="text" class="form-control input-inline input-sm" id="id_companyName" value="${threename}" disabled="disabled" style="width: 200px;"/>&nbsp;
            <label>月份：</label>
            <input type="text" class="form-control input-inline input-sm search-icon" id="id_searchProject" value="${belongmonth}" disabled="disabled" style="width: 130px;"/>&nbsp;
        </div>
    </div>
    <div class="portlet-body">
        <div class="table-scrollable">
            <table data-toggle="table" data-height="500" class="table table-striped table-bordered table-advance table-hover" id="tlist">
                <thead>
                    <tr>
                        <th>序号</th>
                        <th>所属公司</th>
                        <th>所属项目</th>
                        <th>业务名称</th>
                        <th>业务类型</th>
                        <th>业务总金额</th>
                        <th>对应本科目发生金额</th>
                        <th>经办人</th>
                        <th>经办日期</th>
                    </tr>
                </thead>
                <tbody>
                <c:set var="allmoney" value="0"/>
                <c:forEach items="${marketCostContractList}" var="e" varStatus="status">
                    <tr>
                        <td>${status.count}</td>
                        <td>${e.companyName}</td>
                        <td>${e.projectName}</td>
                        <td>
                            <c:if test="${1 eq e.type}">
                                <a href="javascript:;" class="market-ledger-a" data-url="<c:url value="/market/contract/marketledger/"/>${e.contractId}">${e.contractName}</a>
                            </c:if>
                            <c:if test="${2 eq e.type}">
                                <a href="javascript:;" class="market-ledger-a" data-url="<c:url value="/market/contract/frameworkledger/"/>${e.contractId}">${e.contractName}</a>
                            </c:if>
                            <c:if test="${3 eq e.type||4 eq e.type}">
                                <a href="javascript:;" class="market-ledger-a" data-url="<c:url value="/market/contract/payledger/"/>${e.contractId}">${e.contractName}</a>
                            </c:if>
                        </td>
                        <td>${e.businessTypeName}</td>
                        <td>${e.contractMoney}</td>
                        <td>${e.money}</td>
                        <td>${e.agentName}</td>
                        <td>${fn:substring(e.applyDate,0,10)}</td>
                        <c:set var="allmoney" value="${allmoney+e.money}"/>
                    </tr>
                </c:forEach>
                <c:if test="${!empty marketCostContractList}">
                    <tr>
                        <td>合计</td>
                        <td>-</td>
                        <td>-</td>
                        <td>-</td>
                        <td>-</td>
                        <td>-</td>
                        <td>${allmoney}</td>
                        <td>-</td>
                        <td>-</td>
                    </tr>
                </c:if>
                </tbody>
            </table>
        </div>
    </div>
</div>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/jquery.min.js" />" ></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/layer/layer.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/bootstrap-table/bootstrap-table.js"/>"></script>
<script type="text/javascript">

    $(function () {
        //费用实际发生明细
        $(".market-ledger-a").on("click",function () {
            var _url = $(this).data("url");
            layer.open({
                title:'台账',
                type: 2,
                area: ['50%', '95%'],
                fixed: false, //不固定
                maxmin: true,
                content: _url
            });
        });
    });

</script>
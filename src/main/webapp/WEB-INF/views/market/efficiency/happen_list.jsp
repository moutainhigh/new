<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
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
    .search-icon {
        border: 1px solid #C6C6C6;
        background-image: url(<c:url value="/resources/admin/global/img/search.png"/> );
        line-height: 34px;
        padding-right: 20px;
        background-repeat: no-repeat;
        background-position: right center;
        cursor: pointer;
    }
    tr td,tr th{
        font-size: small !important;
    }
    label{
        font-size: small;
    }
    .form_update{
        height: 25px;
        line-height: inherit!important;
        width: 100px;
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
            <span>项目费效分析</span>
            <i class="fa fa-angle-right"></i>
        </li>
        <li>
            <a href="<c:url value="/market/efficiency/happen/get" />">费用实际发生</a>
        </li>
    </ul>
    <%--<div class="page-toolbar">
        <div class="btn-group pull-right">
            <div class="mt-action-buttons">
                <div class="btn-group btn-group-circle">
                    <button type="button" class="btn btn-outline blue-hoki active" onclick="javascript:history.go(-1);">返回</button>
                    <span></span>
                </div>
            </div>
        </div>
    </div>--%>
</div>
<!-- BEGIN EXAMPLE TABLE PORTLET-->
<div class="portlet light" id="form_wizard_1">
    <div class="portlet-title">
        <div class="caption">
            <span class="caption-subject font-green sbold uppercase">费用实际发生</span>
        </div>
    </div>
    <div class="portlet-title">
        <div class="caption">
            <label>所属公司：</label>
            <select id="id_i1" class="form-control input-inline input-sm" style="width: 200px;" autocomplete="off">
                <c:forEach items="${companyList}" var="company">
                    <option value="${company.companyId}" <c:if test="${company.companyId eq entity.i1}">selected</c:if> >${company.name}</option>
                </c:forEach>
            </select>
            <label>年度：</label>
            <select id="id_i2" class="form-control input-inline input-sm" style="width: 200px;" autocomplete="off">
                <c:forEach items="${yearList}" var="year">
                    <option value="${year.year}" <c:if test="${year.year eq entity.i2}">selected</c:if> >${year.year}</option>
                </c:forEach>
            </select>
            <button class="btn btn-sm green" onclick="search();"><i class="fa fa-search"></i> 搜索 </button>
        </div>
        <div class="actions" >
            <label>单位：</label>
            <label class="mt-radio">
                <input type="radio" name="optionsRadios" value="元" checked="checked" data-title="yuan"> 元
                <span></span>
            </label>
            <label class="mt-radio">
                <input type="radio" name="optionsRadios" value="万元" data-title="wanyuan"> 万元
                <span></span>
            </label>
        </div>
    </div>
    <div class="portlet-body" >
        <div class="table-scrollable fixed-table-container" >
            <table class="table table-striped table-bordered table-advance table-hover" id="tlist" >
                <thead>
                    <tr>
                        <th data-field="projectName">项目名称</th>
                        <th data-field="happenMoney">实际发生合计</th>
                        <th data-field="happenMoney01">1月</th>
                        <th data-field="happenMoney02">2月</th>
                        <th data-field="happenMoney03">3月</th>
                        <th data-field="happenMoney04">4月</th>
                        <th data-field="happenMoney05">5月</th>
                        <th data-field="happenMoney06">6月</th>
                        <th data-field="happenMoney07">7月</th>
                        <th data-field="happenMoney08">8月</th>
                        <th data-field="happenMoney09">9月</th>
                        <th data-field="happenMoney10">10月</th>
                        <th data-field="happenMoney11">11月</th>
                        <th data-field="happenMoney12">12月</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${marketCostProjectBudgets}" var="e" varStatus="status">
                        <tr>
                            <form action="#">
                                <td ><a href="javascript:;" data-projectid="${e.projectId}" data-title="${e.projectName}" class="efficiency-happen-project">${e.projectName}</a></td>
                                <td class="efficiency-happen-td-money" data-yuan="${e.happenMoney}" data-wanyuan="" >${e.happenMoney}</td>
                                <td class="efficiency-happen-td-money" data-yuan="${e.happenMoney01}" data-wanyuan="" >${e.happenMoney01}</td>
                                <td class="efficiency-happen-td-money" data-yuan="${e.happenMoney02}" data-wanyuan="" >${e.happenMoney02}</td>
                                <td class="efficiency-happen-td-money" data-yuan="${e.happenMoney03}" data-wanyuan="" >${e.happenMoney03}</td>
                                <td class="efficiency-happen-td-money" data-yuan="${e.happenMoney04}" data-wanyuan="" >${e.happenMoney04}</td>
                                <td class="efficiency-happen-td-money" data-yuan="${e.happenMoney05}" data-wanyuan="" >${e.happenMoney05}</td>
                                <td class="efficiency-happen-td-money" data-yuan="${e.happenMoney06}" data-wanyuan="" >${e.happenMoney06}</td>
                                <td class="efficiency-happen-td-money" data-yuan="${e.happenMoney07}" data-wanyuan="" >${e.happenMoney07}</td>
                                <td class="efficiency-happen-td-money" data-yuan="${e.happenMoney08}" data-wanyuan="" >${e.happenMoney08}</td>
                                <td class="efficiency-happen-td-money" data-yuan="${e.happenMoney09}" data-wanyuan="" >${e.happenMoney09}</td>
                                <td class="efficiency-happen-td-money" data-yuan="${e.happenMoney10}" data-wanyuan="" >${e.happenMoney10}</td>
                                <td class="efficiency-happen-td-money" data-yuan="${e.happenMoney11}" data-wanyuan="" >${e.happenMoney11}</td>
                                <td class="efficiency-happen-td-money" data-yuan="${e.happenMoney12}" data-wanyuan="" >${e.happenMoney12}</td>
                            </form>
                        </tr>
                    </c:forEach>
                    <%--<c:if test="${empty marketCostProjectBudgets}">
                        <tr><td colspan="20">没有对应的数据</td></tr>
                    </c:if>--%>
                </tbody>
            </table>
        </div>
        <div class="row">
        </div>
    </div>
</div>
<script type="text/javascript">

    $(function () {
        $.each($('.efficiency-happen-td-money'),function (k,v) {
            var _obj = $(v);
            _obj.data("wanyuan",(parseFloat(_obj.data("yuan"))/10000).toFixed(2));
        })
        $('input:radio[name="optionsRadios"]').on("change",function () {
            var _dateTitle = $(this).data("title");
            $("td.efficiency-happen-td-money").each(function (k,v) {
                var _obj = $(v);
                _obj.html(_obj.data(_dateTitle));
            })
        });

        //费用实际发生
        $(".efficiency-happen-project").on("click",function () {
            layer.open({
                title:'选择【'+$(this).data("title")+'】项目',
                type: 2,
                area: ['99%', '99%'],
                fixed: false, //不固定
                maxmin: true,
                content: '<c:url value="/market/efficiency/happen/projecthappen/"/> '+$(this).data("projectid")+'/'+$('#id_i2').val()
            });
        });
    });
    
    function search() {
        window.location.href = "<c:url value="/market/efficiency/happen/get"/>"+"?i2="+$("#id_i2").val()+"&i1="+$("#id_i1").val();
    }



</script>
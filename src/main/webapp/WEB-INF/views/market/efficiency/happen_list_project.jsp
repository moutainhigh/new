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
<link href="<c:url value="/resources/treegrid/css/jquery.treegrid.css" />" rel="stylesheet" id="style_components" type="text/css" />
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
            <span class="caption-subject font-green sbold uppercase">费用实际发生</span>
        </div>
    </div>
    <div class="portlet-title">
        <div class="caption">
            <label>项目名称：</label>
            <input type="text" class="form-control input-inline input-sm" id="id_companyName" value="${project.companyName}-${project.name}" disabled="disabled" style="width: 200px;"/>&nbsp;
            <label>预算年度：</label>
            <input type="text" class="form-control input-inline input-sm search-icon" id="id_searchProject" value="${year}" disabled="disabled" style="width: 130px;"/>&nbsp;
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
            <table class="table table-striped table-bordered table-advance table-hover tree" id="tlist" >
                <thead>
                    <tr>
                        <th data-field="projectName">费用科目名称</th>
                        <th data-field="happenMoney">全年费用实际发生</th>
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
                    <c:forEach items="${marketCostProjectBudgetItemList}" var="e" varStatus="status">
                        <tr class="treegrid-${e.firstId}">
                            <form action="#">
                                <td>${e.firstName}</td>
                                <td class="efficiency-happen-td-money" data-yuan="${e.allHappenMoney}" data-wanyuan="" >${e.allHappenMoney}</td>
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
                        <c:forEach items="${e.marketCostProjectBudgetItemListCopy}" var="e2" varStatus="status">
                            <tr class="treegrid-${e2.secondId} treegrid-parent-${e.firstId}">
                                <form action="#">
                                    <td >${e2.secondName}</td>
                                    <td class="efficiency-happen-td-money" data-yuan="${e2.allHappenMoney}" data-wanyuan="" >${e2.allHappenMoney}</td>
                                    <td class="efficiency-happen-td-money" data-yuan="${e2.happenMoney01}" data-wanyuan="" >${e2.happenMoney01}</td>
                                    <td class="efficiency-happen-td-money" data-yuan="${e2.happenMoney02}" data-wanyuan="" >${e2.happenMoney02}</td>
                                    <td class="efficiency-happen-td-money" data-yuan="${e2.happenMoney03}" data-wanyuan="" >${e2.happenMoney03}</td>
                                    <td class="efficiency-happen-td-money" data-yuan="${e2.happenMoney04}" data-wanyuan="" >${e2.happenMoney04}</td>
                                    <td class="efficiency-happen-td-money" data-yuan="${e2.happenMoney05}" data-wanyuan="" >${e2.happenMoney05}</td>
                                    <td class="efficiency-happen-td-money" data-yuan="${e2.happenMoney06}" data-wanyuan="" >${e2.happenMoney06}</td>
                                    <td class="efficiency-happen-td-money" data-yuan="${e2.happenMoney07}" data-wanyuan="" >${e2.happenMoney07}</td>
                                    <td class="efficiency-happen-td-money" data-yuan="${e2.happenMoney08}" data-wanyuan="" >${e2.happenMoney08}</td>
                                    <td class="efficiency-happen-td-money" data-yuan="${e2.happenMoney09}" data-wanyuan="" >${e2.happenMoney09}</td>
                                    <td class="efficiency-happen-td-money" data-yuan="${e2.happenMoney10}" data-wanyuan="" >${e2.happenMoney10}</td>
                                    <td class="efficiency-happen-td-money" data-yuan="${e2.happenMoney11}" data-wanyuan="" >${e2.happenMoney11}</td>
                                    <td class="efficiency-happen-td-money" data-yuan="${e2.happenMoney12}" data-wanyuan="" >${e2.happenMoney12}</td>
                                </form>
                            </tr>
                            <c:forEach items="${e2.marketCostProjectBudgetItemList}" var="e3" varStatus="status">
                                <tr class="treegrid-${e3.threeId} treegrid-parent-${e2.secondId}">
                                    <form action="#">
                                        <td >${e3.threeName}</td>
                                        <td class="efficiency-happen-td-money" data-yuan="${e3.allHappenMoney}" data-wanyuan="" >${e3.allHappenMoney}</td>
                                        <td class="efficiency-happen-td-money-a" data-yuan="${e3.happenMoney01}" data-wanyuan="" ><a href="javascript:;" data-month="${year}-01" data-code="${e3.code}" data-title="${e3.threeName}" data-money="${e3.happenMoney01}" data-projectid="${e3.projectId}">${e3.happenMoney01}</a></td>
                                        <td class="efficiency-happen-td-money-a" data-yuan="${e3.happenMoney02}" data-wanyuan="" ><a href="javascript:;" data-month="${year}-02" data-code="${e3.code}" data-title="${e3.threeName}" data-money="${e3.happenMoney02}" data-projectid="${e3.projectId}">${e3.happenMoney02}</a></td>
                                        <td class="efficiency-happen-td-money-a" data-yuan="${e3.happenMoney03}" data-wanyuan="" ><a href="javascript:;" data-month="${year}-03" data-code="${e3.code}" data-title="${e3.threeName}" data-money="${e3.happenMoney03}" data-projectid="${e3.projectId}">${e3.happenMoney03}</a></td>
                                        <td class="efficiency-happen-td-money-a" data-yuan="${e3.happenMoney04}" data-wanyuan="" ><a href="javascript:;" data-month="${year}-04" data-code="${e3.code}" data-title="${e3.threeName}" data-money="${e3.happenMoney04}" data-projectid="${e3.projectId}">${e3.happenMoney04}</a></td>
                                        <td class="efficiency-happen-td-money-a" data-yuan="${e3.happenMoney05}" data-wanyuan="" ><a href="javascript:;" data-month="${year}-05" data-code="${e3.code}" data-title="${e3.threeName}" data-money="${e3.happenMoney05}" data-projectid="${e3.projectId}">${e3.happenMoney05}</a></td>
                                        <td class="efficiency-happen-td-money-a" data-yuan="${e3.happenMoney06}" data-wanyuan="" ><a href="javascript:;" data-month="${year}-06" data-code="${e3.code}" data-title="${e3.threeName}" data-money="${e3.happenMoney06}" data-projectid="${e3.projectId}">${e3.happenMoney06}</a></td>
                                        <td class="efficiency-happen-td-money-a" data-yuan="${e3.happenMoney07}" data-wanyuan="" ><a href="javascript:;" data-month="${year}-07" data-code="${e3.code}" data-title="${e3.threeName}" data-money="${e3.happenMoney07}" data-projectid="${e3.projectId}">${e3.happenMoney07}</a></td>
                                        <td class="efficiency-happen-td-money-a" data-yuan="${e3.happenMoney08}" data-wanyuan="" ><a href="javascript:;" data-month="${year}-08" data-code="${e3.code}" data-title="${e3.threeName}" data-money="${e3.happenMoney08}" data-projectid="${e3.projectId}">${e3.happenMoney08}</a></td>
                                        <td class="efficiency-happen-td-money-a" data-yuan="${e3.happenMoney09}" data-wanyuan="" ><a href="javascript:;" data-month="${year}-09" data-code="${e3.code}" data-title="${e3.threeName}" data-money="${e3.happenMoney09}" data-projectid="${e3.projectId}">${e3.happenMoney09}</a></td>
                                        <td class="efficiency-happen-td-money-a" data-yuan="${e3.happenMoney10}" data-wanyuan="" ><a href="javascript:;" data-month="${year}-10" data-code="${e3.code}" data-title="${e3.threeName}" data-money="${e3.happenMoney10}" data-projectid="${e3.projectId}">${e3.happenMoney10}</a></td>
                                        <td class="efficiency-happen-td-money-a" data-yuan="${e3.happenMoney11}" data-wanyuan="" ><a href="javascript:;" data-month="${year}-11" data-code="${e3.code}" data-title="${e3.threeName}" data-money="${e3.happenMoney11}" data-projectid="${e3.projectId}">${e3.happenMoney11}</a></td>
                                        <td class="efficiency-happen-td-money-a" data-yuan="${e3.happenMoney12}" data-wanyuan="" ><a href="javascript:;" data-month="${year}-12" data-code="${e3.code}" data-title="${e3.threeName}" data-money="${e3.happenMoney12}" data-projectid="${e3.projectId}">${e3.happenMoney12}</a></td>
                                    </form>
                                </tr>
                            </c:forEach>
                        </c:forEach>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="row">
        </div>
    </div>
</div>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/jquery.min.js" />" ></script>
<script type="text/javascript" src="<c:url value="/resources/treegrid/js/jquery.treegrid.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/treegrid/js/jquery.cookie.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/layer/layer.js"/>"></script>
<script type="text/javascript">

    $(function () {
        $.each($('.efficiency-happen-td-money'),function (k,v) {
            var _obj = $(v);
            _obj.data("wanyuan",(parseFloat(_obj.data("yuan"))/10000).toFixed(2));
        });
        $.each($('.efficiency-happen-td-money-a'),function (k,v) {
            var _obj = $(v);
            _obj.data("wanyuan",(parseFloat(_obj.data("yuan"))/10000).toFixed(2));
        });
        $('input:radio[name="optionsRadios"]').on("change",function () {
            var _dateTitle = $(this).data("title");
            $("td.efficiency-happen-td-money").each(function (k,v) {
                var _obj = $(v);
                _obj.html(_obj.data(_dateTitle));
            })
            $("td.efficiency-happen-td-money-a").each(function (k,v) {
                var _obj = $(v);
                _obj.find("a").html(_obj.data(_dateTitle));
            })
        });
        $('.tree').treegrid({
            'initialState': 'collapsed'
        });
        //费用实际发生明细
        $(".efficiency-happen-td-money-a a").on("click",function () {
            var _money = $(this).data("money");
            if (_money>0) {
                var _month = $(this).data("month");
                var _code = $(this).data("code");
                var _title = $(this).data("title");
                var _projectid = $(this).data("projectid");
                layer.open({
                    title:'【'+_title+'】发生明细',
                    type: 2,
                    area: ['99%', '99%'],
                    fixed: false, //不固定
                    maxmin: true,
                    content: '<c:url value="/market/efficiency/happen/happendetail/"/>'+_projectid+'/'+_code+'/'+_month+'/'+_title
                });
            }
        });
    });

</script>
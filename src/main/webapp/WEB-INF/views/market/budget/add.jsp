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
        width: 80px;
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
            <span>营销费用预算</span>
            <i class="fa fa-angle-right"></i>
        </li>
        <li>
            <a href="<c:url value="/market/budget/get" />">年度预算编制</a>
            <i class="fa fa-angle-right"></i>
        </li>
        <li>
            <span>预算编制</span>
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
            <span class="caption-subject font-green sbold uppercase">预算编制</span>
        </div>
    </div>
    <div class="portlet-title">
        <div class="actions" style="float: left;">
            <input type="hidden" id="id_companyId" value="${company.companyId}"/>
            <input type="hidden" id="id_year" value="${year}"/>
            <input type="hidden" id="id_projectId" value=""/>
            <label>集团/区域：</label>
            <input type="text" class="form-control input-inline input-sm" id="id_companyName" value="${company.name}" disabled="disabled" style="width: 120px;"/>&nbsp;
            <label>项目：</label>
            <input type="text" class="form-control input-inline input-sm search-icon" id="id_searchProject" value="" readonly="readonly" style="width: 130px;"/>&nbsp;
            <label>预算金额：</label>
            <input type="text" class="form-control input-inline input-sm" id="id_allInputMoney" value="0.00" disabled="disabled" style="width: 120px;"/>&nbsp;
            <label>年度：</label>
            <input type="text" class="form-control input-inline input-sm" value="${year}" disabled="disabled" style="width: 50px;"/>
            <label>是否项目独立预算：</label>
            <input type="text" class="form-control input-inline input-sm" value="" id="id_isOwnName" disabled="disabled" style="width: 50px;"/>
            <label>编制人：</label>
            <input type="text" class="form-control input-inline input-sm" value="${userName}" disabled="disabled" style="width: 120px;"/>&nbsp;
            <label>编制日期：</label>
            <%
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String date= format.format(new Date());
            %>
            <input type="text" class="form-control input-inline input-sm" value="<%=date%>" disabled="disabled" style="width: 90px;"/>&nbsp;
        </div>
    </div>
    <div class="portlet-body" >
        <div class="table-scrollable fixed-table-container" >
            <table data-toggle="table" data-height="460" class="table table-striped table-bordered table-advance table-hover" id="tlist" >
                <thead>
                    <tr>
                        <th >科目编码</th>
                        <th >一级科目</th>
                        <th >二级科目</th>
                        <th >三级科目</th>
                        <th >年初预算</th>
                        <th >1月</th>
                        <th >2月</th>
                        <th >3月</th>
                        <th >4月</th>
                        <th >5月</th>
                        <th >6月</th>
                        <th >7月</th>
                        <th >8月</th>
                        <th >9月</th>
                        <th >10月</th>
                        <th >11月</th>
                        <th >12月</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${marketCostItemList}" var="e" varStatus="status">
                        <tr>
                            <form action="#">
                                <td >${e.code}
                                    <input type="hidden" name="code" value="${e.code}"/>
                                    <input type="hidden" name="firstCode" value="${e.firstCode}"/>
                                    <input type="hidden" name="secondCode" value="${e.secondCode}"/>
                                </td>
                                <td>${e.firstName}</td>
                                <td>${e.secondName}</td>
                                <td>${e.name}</td>
                                <td ><input type="text" name="allPlanMoney" class="form-control input-inline input-sm form_update input-allmoney" value="0" readonly="readonly"/></td>
                                <td ><input type="text" name="planMoney01" class="form-control input-inline input-sm form_update input-money" value="0" /></td>
                                <td ><input type="text" name="planMoney02" class="form-control input-inline input-sm form_update input-money" value="0" /></td>
                                <td ><input type="text" name="planMoney03" class="form-control input-inline input-sm form_update input-money" value="0" /></td>
                                <td ><input type="text" name="planMoney04" class="form-control input-inline input-sm form_update input-money" value="0" /></td>
                                <td ><input type="text" name="planMoney05" class="form-control input-inline input-sm form_update input-money" value="0" /></td>
                                <td ><input type="text" name="planMoney06" class="form-control input-inline input-sm form_update input-money" value="0" /></td>
                                <td ><input type="text" name="planMoney07" class="form-control input-inline input-sm form_update input-money" value="0" /></td>
                                <td ><input type="text" name="planMoney08" class="form-control input-inline input-sm form_update input-money" value="0" /></td>
                                <td ><input type="text" name="planMoney09" class="form-control input-inline input-sm form_update input-money" value="0" /></td>
                                <td ><input type="text" name="planMoney10" class="form-control input-inline input-sm form_update input-money" value="0" /></td>
                                <td ><input type="text" name="planMoney11" class="form-control input-inline input-sm form_update input-money" value="0" /></td>
                                <td ><input type="text" name="planMoney12" class="form-control input-inline input-sm form_update input-money" value="0" /></td>
                            </form>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="row">
            <div class="col-md-7 col-sm- text-right" >
                <button type="button" class="btn blue" id="id_transmit" style="margin-right: 20px;">保存</button>
                <a class="btn grey-salsa" href="<c:url value="/market/budget/get?i1=${company.companyId}&i2=${year}"/>" >返回列表</a>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    var b;
    $(function(){
        //选择上级项目
        $("#id_searchProject").click(function () {
           layer.open({
                title:'选择【'+$('#id_companyName').val()+'】项目',
                type: 2,
                area: ['1000px', '620px'],
                fixed: false, //不固定
                maxmin: true,
                content: '<c:url value="/market/budget/getproject/"/> '+$('#id_companyId').val()+'/'+$('#id_year').val()
            });
        });

        $('.input-money').on('blur',function () {
            var tr = $(this).parent().parent();
            var tbody = tr.parent();
            var inputMoney = tr.find(".input-allmoney").eq(0);
            var allInputMoney = $('#id_allInputMoney');
            var allTrMoney = 0;
            var allMoney = 0;
            tr.find(".input-money").each(function (v,t) {
                var thisVal = $.trim($(this).val());
                if (isNaN(thisVal)) {
                    $(this).val(0);
                }
                allTrMoney = parseFloat(isNaN(thisVal)?0:thisVal)+parseFloat(allTrMoney);
            });
            inputMoney.val(parseFloat(allTrMoney).toFixed(2));
            tbody.find(".input-allmoney").each(function (v,t) {
                var thisVal = $.trim($(this).val());
                allMoney = parseFloat(isNaN(thisVal)?0:thisVal)+parseFloat(allMoney);
            });
            $('#id_allInputMoney').val(parseFloat(allMoney).toFixed(2));
        });

        $('#id_transmit').on('click',function () {
            var projectId = $.trim($('#id_projectId').val());
            if (projectId!=''){
                var data = {};
                data.empListStr=encodeEmpData();
                data.projectId=projectId;
                data.companyId=$('#id_companyId').val();
                data.year=$('#id_year').val();
                if (!b) {
                    layer.confirm("确定保存吗？", {icon: 3, title:'保存提示'}, function(){
                    layer.closeAll();
                    $.ajax({
                        url: '<c:url value="/market/budget/put"/>',
                        type: 'POST',
                        data:data,
                        dataType: 'json',
                        success: function(data){
                            layer.closeAll('loading');
                            data = JSON.parse(data);
                            if (data.code=="0"){
                                layer.msg(data.msg,{icon: 1});
                                    window.setTimeout(function () {
                                    location.href='<c:url value="/market/budget/get?i1=" />'+'${company.companyId}'+'&i2='+${year};
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
            } else {
                layer.msg('您还没有选择项目');
            }
        });

        var encodeEmpData = function () {
            var objJson = new Array();
            b = false;
            $('#tlist').find('tr:gt(0)').each(function (k, v) {
                var serializeObj={};
                var array=$(v).find('form:eq(0)').serializeArray();
                $(array).each(function(){
                    if (this.name!='code'&&this.name!='firstCode'&&this.name!='secondCode'&&(this.value==''||isNaN(this.value))) {
                        layer.msg('预算费用不能为空且必为数字');
                        b=true;
                        return false;
                    }
                    serializeObj[this.name]=this.value;
                });
                if (b){
                    return false;
                }
                objJson.push(serializeObj);
            });
            return JSON.stringify(objJson);
        }

    });



</script>
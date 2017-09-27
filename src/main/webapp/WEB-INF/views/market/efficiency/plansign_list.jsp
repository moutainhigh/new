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
            <span>营销费用预算</span>
            <i class="fa fa-angle-right"></i>
        </li>
        <li>
            <a href="<c:url value="/market/efficiency/plansign/get" />">年度签约预算</a>
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
            <span class="caption-subject font-green sbold uppercase">年度签约预算</span>
        </div>
    </div>
    <div class="portlet-title">
        <div class="actions" style="float: left;">
            <input type="hidden" id="id_year" value="${entity.i2}"/>
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
    </div>
    <div class="portlet-body" >
        <div class="table-scrollable fixed-table-container" >
            <table data-toggle="table" data-height="460" class="table table-striped table-bordered table-advance table-hover" id="tlist" >
                <thead>
                    <tr>
                        <th >项目名称</th>
                        <th >签约金额合计</th>
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
                    <c:forEach items="${marketCostProjectSignList}" var="e" varStatus="status">
                        <c:if test="${e.type eq 1}">
                            <tr>
                                <form action="#">
                                    <td >合计
                                        <input type="hidden" name="signId" value="${empty e.signId?0:e.signId}"/>
                                        <input type="hidden" name="companyId" value="${e.companyId}"/>
                                        <input type="hidden" name="projectId" value="${e.projectId}"/>
                                        <input type="hidden" name="type" value="${e.type}"/>
                                    </td>
                                    <td ><input type="text" name="signMoney" id="input-allmoney" class="form-control input-inline input-sm form_update input-default" data-title="${empty e.signMoney?0:e.signMoney}" value="${empty e.signMoney?0:e.signMoney}" readonly="readonly"/></td>
                                    <td ><input type="text" name="signMoney01" id="input-money1" class="form-control input-inline input-sm form_update input-default" data-title="${empty e.signMoney01?0:e.signMoney01}" value="${empty e.signMoney01?0:e.signMoney01}" readonly="readonly"/></td>
                                    <td ><input type="text" name="signMoney02" id="input-money2" class="form-control input-inline input-sm form_update input-default" data-title="${empty e.signMoney02?0:e.signMoney02}" value="${empty e.signMoney02?0:e.signMoney02}" readonly="readonly"/></td>
                                    <td ><input type="text" name="signMoney03" id="input-money3" class="form-control input-inline input-sm form_update input-default" data-title="${empty e.signMoney03?0:e.signMoney03}" value="${empty e.signMoney03?0:e.signMoney03}" readonly="readonly"/></td>
                                    <td ><input type="text" name="signMoney04" id="input-money4" class="form-control input-inline input-sm form_update input-default" data-title="${empty e.signMoney04?0:e.signMoney04}" value="${empty e.signMoney04?0:e.signMoney04}" readonly="readonly"/></td>
                                    <td ><input type="text" name="signMoney05" id="input-money5" class="form-control input-inline input-sm form_update input-default" data-title="${empty e.signMoney05?0:e.signMoney05}" value="${empty e.signMoney05?0:e.signMoney05}" readonly="readonly"/></td>
                                    <td ><input type="text" name="signMoney06" id="input-money6" class="form-control input-inline input-sm form_update input-default" data-title="${empty e.signMoney06?0:e.signMoney06}" value="${empty e.signMoney06?0:e.signMoney06}" readonly="readonly"/></td>
                                    <td ><input type="text" name="signMoney07" id="input-money7" class="form-control input-inline input-sm form_update input-default" data-title="${empty e.signMoney07?0:e.signMoney07}" value="${empty e.signMoney07?0:e.signMoney07}" readonly="readonly"/></td>
                                    <td ><input type="text" name="signMoney08" id="input-money8" class="form-control input-inline input-sm form_update input-default" data-title="${empty e.signMoney08?0:e.signMoney08}" value="${empty e.signMoney08?0:e.signMoney08}" readonly="readonly"/></td>
                                    <td ><input type="text" name="signMoney09" id="input-money9" class="form-control input-inline input-sm form_update input-default" data-title="${empty e.signMoney09?0:e.signMoney09}" value="${empty e.signMoney09?0:e.signMoney09}" readonly="readonly"/></td>
                                    <td ><input type="text" name="signMoney10" id="input-money10" class="form-control input-inline input-sm form_update input-default" data-title="${empty e.signMoney10?0:e.signMoney10}" value="${empty e.signMoney10?0:e.signMoney10}" readonly="readonly"/></td>
                                    <td ><input type="text" name="signMoney11" id="input-money11" class="form-control input-inline input-sm form_update input-default" data-title="${empty e.signMoney11?0:e.signMoney11}" value="${empty e.signMoney11?0:e.signMoney11}" readonly="readonly"/></td>
                                    <td ><input type="text" name="signMoney12" id="input-money12" class="form-control input-inline input-sm form_update input-default" data-title="${empty e.signMoney12?0:e.signMoney12}" value="${empty e.signMoney12?0:e.signMoney12}" readonly="readonly"/></td>
                                </form>
                            </tr>
                        </c:if>
                        <c:if test="${e.type eq 2}">
                            <tr>
                                <form action="#">
                                    <td >${e.projectName}
                                        <input type="hidden" name="signId" value="${empty e.signId?0:e.signId}"/>
                                        <input type="hidden" name="companyId" value="${e.companyId}"/>
                                        <input type="hidden" name="projectId" value="${e.projectId}"/>
                                        <input type="hidden" name="type" value="${e.type}"/>
                                    </td>
                                    <td ><input type="text" name="signMoney" class="form-control input-inline input-sm form_update input-allmoney input-default" data-title="${empty e.signMoney?0:e.signMoney}" value="${empty e.signMoney?0:e.signMoney}" readonly="readonly"/></td>
                                    <td ><input type="text" name="signMoney01" class="form-control input-inline input-sm form_update input-money input-default" data-title="${empty e.signMoney01?0:e.signMoney01}" value="${empty e.signMoney01?0:e.signMoney01}" readonly="readonly"/></td>
                                    <td ><input type="text" name="signMoney02" class="form-control input-inline input-sm form_update input-money input-default" data-title="${empty e.signMoney02?0:e.signMoney02}" value="${empty e.signMoney02?0:e.signMoney02}" readonly="readonly"/></td>
                                    <td ><input type="text" name="signMoney03" class="form-control input-inline input-sm form_update input-money input-default" data-title="${empty e.signMoney03?0:e.signMoney03}" value="${empty e.signMoney03?0:e.signMoney03}" readonly="readonly"/></td>
                                    <td ><input type="text" name="signMoney04" class="form-control input-inline input-sm form_update input-money input-default" data-title="${empty e.signMoney04?0:e.signMoney04}" value="${empty e.signMoney04?0:e.signMoney04}" readonly="readonly"/></td>
                                    <td ><input type="text" name="signMoney05" class="form-control input-inline input-sm form_update input-money input-default" data-title="${empty e.signMoney05?0:e.signMoney05}" value="${empty e.signMoney05?0:e.signMoney05}" readonly="readonly"/></td>
                                    <td ><input type="text" name="signMoney06" class="form-control input-inline input-sm form_update input-money input-default" data-title="${empty e.signMoney06?0:e.signMoney06}" value="${empty e.signMoney06?0:e.signMoney06}" readonly="readonly"/></td>
                                    <td ><input type="text" name="signMoney07" class="form-control input-inline input-sm form_update input-money input-default" data-title="${empty e.signMoney07?0:e.signMoney07}" value="${empty e.signMoney07?0:e.signMoney07}" readonly="readonly"/></td>
                                    <td ><input type="text" name="signMoney08" class="form-control input-inline input-sm form_update input-money input-default" data-title="${empty e.signMoney08?0:e.signMoney08}" value="${empty e.signMoney08?0:e.signMoney08}" readonly="readonly"/></td>
                                    <td ><input type="text" name="signMoney09" class="form-control input-inline input-sm form_update input-money input-default" data-title="${empty e.signMoney09?0:e.signMoney09}" value="${empty e.signMoney09?0:e.signMoney09}" readonly="readonly"/></td>
                                    <td ><input type="text" name="signMoney10" class="form-control input-inline input-sm form_update input-money input-default" data-title="${empty e.signMoney10?0:e.signMoney10}" value="${empty e.signMoney10?0:e.signMoney10}" readonly="readonly"/></td>
                                    <td ><input type="text" name="signMoney11" class="form-control input-inline input-sm form_update input-money input-default" data-title="${empty e.signMoney11?0:e.signMoney11}" value="${empty e.signMoney11?0:e.signMoney11}" readonly="readonly"/></td>
                                    <td ><input type="text" name="signMoney12" class="form-control input-inline input-sm form_update input-money input-default" data-title="${empty e.signMoney12?0:e.signMoney12}" value="${empty e.signMoney12?0:e.signMoney12}" readonly="readonly"/></td>
                                </form>
                            </tr>
                        </c:if>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="row">
            <div class="col-md-7 col-sm- text-right" >
                <c:if test="${length>1}" >
                    <button type="button" class="btn green" id="id_edit" style="margin-right: 20px;">编辑</button>
                    <button type="button" class="btn blue" id="id_cancel" style="margin-right: 20px;display: none;">取消</button>
                    <button type="button" class="btn green" id="id_transmit" style="margin-right: 20px;display: none;">保存</button>
                </c:if>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    var b;
    $(function(){

        $('#id_edit').on('click',function () {
            $(this).hide();
            $('#id_transmit').show();
            $('#id_cancel').show();
            $('.input-money').prop("readonly",false);
        });

        $('#id_cancel').on('click',function () {
            $(this).hide();
            $('#id_transmit').hide();
            $('#id_edit').show();
            $('.input-default').each(function (v,t) {
                $(this).prop("readonly", true).val($(this).data('title'));
            });
        });

        $('.input-money').on('blur',function () {
            if(!$(this).prop("readonly")){
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
                $('#input-allmoney').val(parseFloat(allMoney).toFixed(2));
                var tdMoney = 0;
                var index = tr.find('.input-money').index($(this));
                tbody.find("tr:gt(0)").each(function (v,t) {
                    var thisVal = $.trim($(this).find('.input-money').eq(index).val());
                    tdMoney = parseFloat(isNaN(thisVal)?0:thisVal)+parseFloat(tdMoney);
                });
                $('#input-money'+(parseInt(index)+1)).val(parseFloat(tdMoney).toFixed(2));
            }
        });

        $('#id_transmit').on('click',function () {
            var data = {};
            data.empListStr=encodeEmpData();
            data.admListStr=encodeEmpData1();
            data.year=$('#id_year').val();
            if (!b) {
                layer.confirm("确定保存吗？", {icon: 3, title:'保存提示'}, function(){
                layer.closeAll();
                $.ajax({
                    url: '<c:url value="/market/efficiency/plansign/put"/>',
                    type: 'POST',
                    data:data,
                    dataType: 'json',
                    success: function(data){
                        layer.closeAll('loading');
                        data = JSON.parse(data);
                        if (data.code=="0"){
                            layer.msg(data.msg,{icon: 1});
                            window.setTimeout(function () {
                                window.location.reload(true);
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
        });

        var encodeEmpData = function () {
            var objJson = new Array();
            b = false;
            $('#tlist').find('tr:gt(1)').each(function (k, v) {
                var serializeObj={};
                var array=$(v).find('form:eq(0)').serializeArray();
                $(array).each(function(){
                    if (this.value==''||isNaN(this.value)) {
                        layer.msg('签约金额不能为空且必为数字');
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

        var encodeEmpData1 = function () {
            var objJson = new Array();
            b = false;
            $('#tlist').find('tr:eq(1)').each(function (k, v) {
                var serializeObj={};
                var array=$(v).find('form:eq(0)').serializeArray();
                $(array).each(function(){
                    if (this.value==''||isNaN(this.value)) {
                        layer.msg('签约金额不能为空且必为数字');
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

    function search() {
        window.location.href = "<c:url value="/market/efficiency/plansign/get"/>"+"?i2="+$("#id_i2").val()+"&i1="+$("#id_i1").val();
    }

</script>
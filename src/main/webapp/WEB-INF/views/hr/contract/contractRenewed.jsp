<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<link rel="stylesheet" type="text/css" href="<c:url value="/resources/admin/global/plugins/bootstrap-datepicker/css/bootstrap-datepicker3.min.css" />"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/webuploader/css/webuploader.css" />">
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/hr/css/hr.css" />"/>

<style>
    .datepicker-dropdown{
        z-index: 9999;
    }
</style>
<h1 class="page-title">合同续签</h1>
<div class="panel panel-default">
    <div class="panel-heading">
        <h3 class="panel-title">合同续签前</h3>
    </div>
    <div class="panel-body">
        <form  role="form" >
            <div class="form-body">
                <div class="row">
                    <div class="col-md-3">
                        <div class="form-group">
                            <label>合同编号</label>
                            <input type="text" class="form-control " name="contractNo" value="${entity.contractNo}">
                        </div>
                    </div>
                    <div class="col-md-3">
                        <label>人员编号</label>
                        <input type="text" class="form-control " value="${entity.empNo}" name="empNo">
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label>人员姓名</label>
                            <input type="text" class="form-control " name="empName" value="${entity.empName}">
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label>所属公司</label>
                            <input type="text" class="form-control " name="companyName" value="${entity.companyName}">
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-3">
                        <div class="form-group">
                            <label>合同期限类型</label>
                            <select class="form-control" name="periodType">
                                <c:if test="${entity.periodType eq 1}"><option value="1">固定期限</option></c:if>
                                <c:if test="${entity.periodType eq 2}"><option value="2">无固定期限</option></c:if>
                                <c:if test="${entity.periodType eq 3}"><option value="3">以完成一定工作任务期限</option></c:if>
                            </select>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label>合同期限</label>
                            <input type="text" class="form-control " name="periodCount" value="${entity.periodCount}">
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label>合同开始日期</label>
                            <input type="text" class="form-control" name="contractStartTime" value="<fmt:formatDate value="${entity.contractStartTime}" pattern="yyyy-MM-dd"/>">
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label>合同截至日期</label>
                            <input type="text" class="form-control" name="contractEndTime" value="<fmt:formatDate value="${entity.contractEndTime}" pattern="yyyy-MM-dd"/>">
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-3">
                        <div class="form-group">
                            <label>是否试用</label>
                            <select class="form-control" name="isProb">
                                <c:if test="${entity.isProb eq 1}"><option value="1">是</option></c:if>
                                <c:if test="${entity.isProb eq 0}"><option value="0">否</option></c:if>
                            </select>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label>试用期限</label>
                            <input type="text" class="form-control " name="probCount" value="${entity.probCount}">
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label>试用开始日期</label>
                            <input type="text" class="form-control" name="probStartTime" value="<fmt:formatDate value="${entity.probStartTime}" pattern="yyyy-MM-dd"/>">
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label>试用结束日期</label>
                            <input type="text" class="form-control" name="probEndTime" value="<fmt:formatDate value="${entity.probEndTime}" pattern="yyyy-MM-dd"/>">
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-3">
                        <div class="form-group">
                            <label>合同/协议</label>
                            <select class="form-control" name="contractType">
                                <c:if test="${entity.contractType eq 0}"><option value="0">物业合同</option></c:if>
                                <c:if test="${entity.contractType eq 1}"><option value="1">劳动合同</option></c:if>
                                <c:if test="${entity.contractType eq 2}"><option value="2">聘用协议</option></c:if>
                            </select>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label>是否签署保密协议</label>
                            <select class="form-control" name="isSecret">
                                <c:if test="${entity.isSecret eq 1}"><option value="1">是</option></c:if>
                                <c:if test="${entity.isSecret eq 0}"><option value="0">否</option></c:if>
                            </select>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label>合同主体单位</label>
                            <select class="form-control" name="contractCompany">
                                <c:forEach items="${contractCompany}" var="data">
                                    <c:if test="${entity.contractCompany eq data.dictDataValue}"><option value="${data.dictDataValue}">${data.dictDataKey}</option></c:if>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-9">
                        <div class="form-group">
                            备注<input type="text"  class="form-control " name="note" value="${entity.note}">
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </div>
    <div class="panel-heading">
        <h3 class="panel-title">合同续签后</h3>
    </div>
    <div class="panel-body">
        <form  role="form"  class="js_base_form">
            <input type="hidden" value="${entity.id}" name="id">
            <div class="form-body">
                <div class="row">
                    <div class="col-md-3">
                        <div class="form-group">
                            <label>合同期限类型</label>
                            <select class="form-control" name="periodType">
                                <option value="" >请选择</option>
                                <option value="1">固定期限</option>
                                <option value="2">无固定期限</option>
                                <option value="3">以完成一定工作任务期限</option>
                            </select>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label>合同期限</label>
                            <input type="text" class="form-control " name="periodCount" value="">
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label>合同开始日期</label>
                            <input type="text" class="form-control js-date" name="contractStartTime" value="">
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label>合同截至日期</label>
                            <input type="text" class="form-control js-date" name="contractEndTime" value="">
                        </div>
                    </div>
                </div>
                <div class="row">
                    <%--<div class="col-md-3">--%>
                        <%--<div class="form-group">--%>
                            <%--<label>合同/协议</label>--%>
                            <%--<select class="form-control" name="contractType">--%>
                                <%--<option value="">请选择</option>--%>
                                <%--<option value="0">劳动合同</option>--%>
                                <%--<option value="1">聘用协议</option>--%>
                                <%--<option value="2">实习协议</option>--%>
                            <%--</select>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <%--<div class="col-md-3">--%>
                        <%--<div class="form-group">--%>
                            <%--<label>是否签署保密协议</label>--%>
                            <%--<select class="form-control" name="isSecret">--%>
                                <%--<option value="">请选择</option>--%>
                                <%--<option value="1" >是</option>--%>
                                <%--<option value="0" >否</option>--%>
                            <%--</select>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label>续签日期</label>
                            <input type="text" class="form-control js-date" name="operDate" value="">
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-9">
                        <div class="form-group">
                            备注<input type="text"  class="form-control " name="note" value="">
                        </div>
                    </div>
                </div>
            </div>
            <c:if test="${not empty entity}">
            <button type="button" class="btn blue js_save" data-action="save">确定</button>
            </c:if>
            <a href="<c:url value="/hr/contract/list"/>" class="btn btn-default">返回列表</a>
        </form>
    </div>
</div>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.zh-CN.js" />"></script>

<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/jquery-validation/js/jquery.validate.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/jquery-validation/js/additional-methods.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/jquery-validation/js/messages_zh.min.js" />"></script>

<script>

    $(document).ready(function () {

        <c:if test="${empty entity}">
            layer.alert('未找到生效的合同');
        </c:if>

        var oldInfo = {
            id:'<c:url value="${entity.id}"/>'
        };

        var timeInitArgs = {
            todayBtn : "linked",
            autoclose : true,
            language: 'zh-CN',
            format : "yyyy-mm-dd",
            todayHighlight : true
        };

        $('input.js-date').datepicker(timeInitArgs);

        $('button.js-emp-search').on('click',function () {
            var _psnNo = $(this).parent().prev('input').val();
            if (_psnNo){
                $.post('<c:url value="/hr/findEmpByEmpNo"/>',{empNo:_psnNo}, function (result) {
                    if (result.success) {
                        var obj = result.rdata;
                        $.post('<c:url value="/hr/contract/checkContractRegister"/>',{empId:obj.id}, function (result) {
                            if (result.success){
                                $('input[name=empId]').val(obj.id);
                                $('input[name=empName]').val(obj.empName);
                                $('input[name=company]').val(obj.company);
                                $('input[name=companyName]').val(obj.companyStr);
                            }else{
                                layer.msg('该人员已经有签订且未终止的合同',{time:2000});
                            }
                        }, 'json');
                    }else{
                        layer.msg('未找到人员',{time:1500});
                    }
                }, 'json');
            }
        });

        var validateForm = $('form.js_base_form');
        validateForm.validate({
            errorElement : 'span',
            errorClass : 'help-block help-block-error',
            rules : {
                periodType: {
                    required : true
                },isProb : {
                    required : true
                },contractType : {
                    required : true
                },isSecret : {
                    required : true
                },contractCompany : {
                    required : true
                },operDate : {
                    required : true
                }
            },
            messages : {

            },
            highlight : function(element) {
                $(element).parent().addClass('has-error');
            },
            unhighlight : function(element) {
                $(element).parent().removeClass('has-error');
            },
            success : function(label) {
                label.remove();
            },errorPlacement: function(error, element) {
                element.parents('.form-group').append(error);
            }
        });



        $('button.js_save').on('click',function () {
            if (validateForm.valid()){
                $.post('<c:url value="/hr/contract/postContractRenewed"/>',validateForm.serialize(), function (result) {
                    if (result.success) {
                        layer.msg("操作成功",function () {
                            window.location.reload();
                        });
                    }else{
                        layer.msg('操作失败',{time:800});
                    }
                }, 'json');
            }
        });

    });

</script>
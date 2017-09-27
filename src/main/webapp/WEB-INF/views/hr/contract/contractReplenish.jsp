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
<div class="portlet box blue-hoki">
    <div class="portlet-title">
        <div class="caption">
            <i class="fa fa-gift"></i>
            合同补录
        </div>
    </div>
    <div class="portlet-body">
        <form  role="form"  class="js_base_form">
            <div class="form-body">
                <div class="row">
                    <div class="col-md-3">
                        <div class="form-group">
                            <label>操作类型</label>
                            <select class="form-control" name="operType">
                                <option value="" >请选择</option>
                                <option value="2">变更</option>
                                <option value="3">续签</option>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-3">
                        <div class="form-group">
                            <label>合同编号</label>
                            <input type="text" class="form-control " name="contractNo" value="">
                        </div>
                    </div>
                    <div class="col-md-3">
                        <label>人员编号</label>
                        <input type="text" class="form-control " value="${employee.empNo}" name="empNo" readonly>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label>人员姓名</label>
                            <input type="hidden" class="form-control " name="empId" value="${employee.id}" readonly>
                            <input type="text" class="form-control " name="empName" value="${employee.empName}" readonly>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label>所属公司</label>
                            <input type="hidden" class="form-control " name="company" value="${employee.company}" readonly>
                            <input type="text" class="form-control " name="companyName" value="${employee.companyStr}" readonly>
                        </div>
                    </div>
                </div>
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
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>是否试用</label>
                                <select class="form-control" name="isProb">
                                    <option value="" >请选择</option>
                                    <option value="1" >是</option>
                                    <option value="0" >否</option>
                                </select>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>试用期限</label>
                                    <input type="text" class="form-control " name="probCount" value="">
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>试用开始日期</label>
                                <input type="text" class="form-control js-date" name="probStartTime" value="">
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>试用结束日期</label>
                                <input type="text" class="form-control js-date" name="probEndTime" value="">
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>合同/协议</label>
                                <select class="form-control" name="contractType">
                                    <option value="">请选择</option>
                                    <option value="0">劳动合同</option>
                                    <option value="1">聘用协议</option>
                                    <option value="2">实习协议</option>
                                </select>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>是否签署保密协议</label>
                                <select class="form-control" name="isSecret">
                                    <option value="">请选择</option>
                                    <option value="1" >是</option>
                                    <option value="0" >否</option>
                                </select>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>合同主体单位</label>
                                <select class="form-control" name="contractCompany">
                                    <option value="">请选择</option>
                                    <c:forEach items="${contractCompany}" var="data">
                                        <option value="${data.dictDataValue}">${data.dictDataKey}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>签订日期</label>
                                <input type="text" class="form-control js-date" name="operDate" value="">
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
            <c:if test="${empty entity}">
            <button type="button" class="btn green js_save" data-action="save">确定</button>
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

        var timeInitArgs = {
            todayBtn : "linked",
            autoclose : true,
            language: 'zh-CN',
            format : "yyyy-mm-dd",
            todayHighlight : true
        };

        $('input.js-date').datepicker(timeInitArgs);


        var validateForm = $('form.js_base_form');
        validateForm.validate({
            errorElement : 'span',
            errorClass : 'help-block help-block-error',
            rules : {
                contractNo : {
                    required : true
                },empNo: {
                    required : true
                },empName: {
                    required : true
                },companyName : {
                    required : true
                },operType : {
                    required : true
                },periodType: {
                    required : true
//                },periodCount: {
//                    required : true
//                },contractStartTime : {
//                    required : true
//                },contractEndTime : {
//                    required : true
                },isProb : {
                    required : true
//                },probCount : {
//                    required : true
//                },probStartTime : {
//                    required : true
//                },probEndTime : {
//                    required : true
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
                $.post('<c:url value="/hr/contract/postContractReplenish"/>',validateForm.serialize(), function (result) {
                    if (result.success) {
                        layer.msg("操作成功",function () {
                            window.location.href='<c:url value="/hr/contract/list"/>';
                        });
                    }else{
                        layer.msg('操作失败:'+result.rmsg,{time:2000});
                    }
                }, 'json');
            }
        });

    });

</script>
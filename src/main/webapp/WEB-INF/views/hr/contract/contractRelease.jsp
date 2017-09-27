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
<h1 class="page-title">合同解除</h1>
<div class="panel panel-default">
    <div class="panel-heading">
        <h3 class="panel-title">合同续签前</h3>
    </div>
    <div class="panel-body">
        <form  role="form"  class="js_base_form">
            <input type="hidden" value="${entity.id}" name="id">
            <div class="form-body">
                <div class="row">
                    <div class="col-md-3">
                        <div class="form-group">
                            <label>合同编号</label>
                            <input type="text" class="form-control " value="${entity.contractNo}" readonly>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <label>人员编号</label>
                        <input type="text" class="form-control " value="${entity.empNo}"  readonly>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label>人员姓名</label>
                            <input type="text" class="form-control " value="${entity.empName}" readonly>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label>所属公司</label>
                            <input type="text" class="form-control " value="${entity.companyName}" readonly>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-3">
                        <div class="form-group">
                            <label>解除提出方</label>
                            <select class="form-control" name="propose">
                                <option value="">请选择</option>
                                <option value="0">用人单位</option>
                                <option value="1">劳动者</option>
                            </select>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label>解除原因</label>
                            <select class="form-control" name="reason">
                                <option value="">请选择</option>
                                <option value="0">劳动者在试用期间被证明不符合录用条件</option>
                                <option value="1">劳动者严重违反用人单位的规章制度</option>
                                <option value="2">员工个人辞职</option>
                            </select>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label>解除日期</label>
                            <input type="text" class="form-control js-date" name="operDate" value="">
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label>解除合同证明编号</label>
                            <input type="text" class="form-control " name="proveContractNo" value="">
                        </div>
                    </div>
                </div>
                <div class="row">

                    <div class="col-md-3">
                        <div class="form-group">
                            <label>经济补偿金</label>
                            <input type="text" class="form-control " name="compensate" value="">
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label>赔偿金</label>
                            <input type="text" class="form-control " name="indemnify" value="">
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-6">
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
                propose: {
                    required : true
                },reason : {
                    required : true
                },operDate : {
                    required : true
                },proveContractNo : {
                    required : true
                },compensate : {
                    required : true
                },indemnify : {
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
                $.post('<c:url value="/hr/contract/postContractRelease"/>',validateForm.serialize(), function (result) {
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
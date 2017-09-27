<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link type="text/css" href="/resources/admin/global/plugins/select2-4.0.3/css/select2.min.css"  rel="stylesheet">

<link rel="stylesheet" type="text/css" href="<c:url value="/resources/admin/global/plugins/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" />" media="screen"/>


<style>
    .datepicker-dropdown{
        z-index: 9999;
    }
</style>

<div class="portlet box blue-hoki">
    <div class="portlet-title">
        <div class="caption">外出数据补录</div>
    </div>
    <div class="portlet-body">
        <div class="row">
            <div class="col-md-6">
                <form  role="form" class="js_base_form" id="materialAddForm">
                    <div class="form-body">
                        <div class="row">
                            <div class="col-md-4 ">
                                <div class="form-group">
                                    <label>姓名：</label>
                                    <input type="text" class="form-control" name="username" value="${entity.username}">
                                </div>
                            </div>

                            <div class="col-md-4 ">
                                <div class="form-group">
                                    <label>手机号：${currRepositoryId}</label>
                                    <input type="text" class="form-control" name="telephone" value="${entity.telephone}">
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="form-group">
                                    <label>员工编号：</label>
                                    <input type="text" class="form-control" name="userNum" value="${entity.userNum}" <c:if test="${not empty entity}">readonly</c:if>>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-4">
                            <div class="form-group">
                                <label>身份证号码：</label>
                                <input type="text" class="form-control" name="idCard" value="${entity.idCard}" <c:if test="${not empty entity}">readonly</c:if>>
                            </div>
                        </div>
                        <div class="col-md-4 ">
                            <div class="form-group">
                                <label>单位：</label>
                                <input type="text" class="form-control" name="company" value="${entity.company}" <c:if test="${not empty entity}">readonly</c:if>>
                            </div>
                        </div>
                        <div class="col-md-4 ">
                            <div class="form-group">
                                <label>部门：</label>
                                <input type="text" class="form-control" name="department" value="${entity.department}" <c:if test="${not empty entity}">readonly</c:if>>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-4">
                            <div class="form-group">
                                <label>外出地点：</label>
                                <input type="text" class="form-control" name="location_detail" value="${entity.location_detail}" <c:if test="${not empty entity}">readonly</c:if>>
                            </div>
                        </div>
                        <div class="col-md-4 ">
                            <div class="form-group">
                                <label>外出时间：</label>
                                <input type="text" class="form-control" name="startTime"
                                       value="<fmt:formatDate value="${entity.startTime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate>" <c:if test="${not empty entity}">readonly</c:if>>
                            </div>
                        </div>
                        <div class="col-md-4 ">
                            <div class="form-group">
                                <label>返回时间：</label>
                                <input type="text" class="form-control" name="endTime" value="<fmt:formatDate value="${entity.endTime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate>" <c:if test="${not empty entity}">readonly</c:if>>
                            </div>
                        </div>
                    </div>
                    <div class="row">

                        <div class="col-md-12">
                            <div class="form-group">
                                <label>外出摘要：</label>
                                <input type="text" class="form-control" name="notes" value="${entity.notes}" <c:if test="${not empty entity}">readonly</c:if>>
                            </div>
                        </div>

                    </div>
                    <button type="button" class="btn green js_save_btn" data-action="save">保存</button>
                    <a type="button" class="btn default" href="<c:url value='/admin/sign/toUnusualPage'/>">返回列表</a>

                </form>
            </div>
        </div>
    </div>
</div>
</div>


<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/select2-4.0.3/js/select2.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/jquery-validation/js/jquery.validate.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/jquery-validation/js/additional-methods.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/jquery-validation/js/messages_zh.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.zh-CN.js" />"></script>


<script>

    $(function () {


        var dataObj = {
            id:'${entity.id}'
        };


        $('input[name=startLeave]').datetimepicker({
            todayBtn : "linked",
            autoclose : true,
            language: 'zh-CN',
            format : "yyyy-mm-dd hh:ii:ss",
            todayHighlight : true,
            endDate : new Date()
        });

        $('input[name=endLeave]').datetimepicker({
            todayBtn : "linked",
            autoclose : true,
            language: 'zh-CN',
            format : "yyyy-mm-dd hh:ii:ss",
            todayHighlight : true,
            endDate : new Date()
        });


        var validateForm = $('form.js_base_form');
        validateForm.validate({
            errorElement : 'span',
            errorClass : 'help-block help-block-error',
            rules : {
                username : {
                    required : true
                },
                telephone : {
                    minlength:11
                }
            },
            highlight : function(element) {
                $(element).parents('div.form-group').addClass('has-error');
            },
            unhighlight : function(element) {
                $(element).parents('div.form-group').removeClass('has-error');
            },
            success : function(label) {
                label.remove();
            },errorPlacement: function(error, element) {
                element.parents('div.form-group').append(error);
            }
        });


        var _index=null;
        $('button.js_save_btn').on('click',function () {
            if(validateForm.valid()){
                var objArr = $('form.js_base_form').serializeArray();
                for(var e in objArr){
                    var  obj = objArr[e];
                    dataObj[obj.name] = obj.value;
                }
                var url = null;
                if(dataObj.id == ''){
                    url = '<c:url value="/admin/sign/egressInsert"/>';
                }else{
                    url = '<c:url value="/admin/sign/egressUpdate"/>';
                }
                layer.confirm("确定提交？",function (c) {
                    layer.close(c);
                    _index = layer.load(2);
                    $.post(url, dataObj, function (result) {
                        layer.close(_index);
                        if (result.success) {
                            layer.msg('操作成功',{time:800},function () {
                                <%--window.location.href='<c:url value="/admin/inventory/receipt/index"/>';--%>
                                window.location.reload();
                            });
                        }else{
                            layer.msg('操作失败：'+result.rmsg,{time:2000});
                        }
                    }, 'json');
                });
            }
        });

    });

</script>


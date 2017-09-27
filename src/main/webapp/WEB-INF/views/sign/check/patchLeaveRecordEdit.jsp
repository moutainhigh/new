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
        <div class="caption">请假数据补录</div>
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
                                    <label>请假天数：</label>
                                    <input type="text" class="form-control" name="leaveDayCount" value="${entity.leaveDayCount}" <c:if test="${not empty entity}">readonly</c:if>>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-4">
                            <div class="form-group">
                                <label>换休(<font color="red">请假中包含的换休天数</font>)：</label>
                                <input type="text" class="form-control" name="restChanged" value="${entity.restChanged}" <c:if test="${not empty entity}">readonly</c:if>>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="form-group">
                                <label>职位：</label>
                                <input type="text" class="form-control" name="position" value="${entity.position}" <c:if test="${not empty entity}">readonly</c:if>>
                            </div>
                        </div>
                        <div class="col-md-4 ">
                            <div class="form-group">
                                <label>单位：</label>
                                <input type="text" class="form-control" name="company" value="${entity.company}" <c:if test="${not empty entity}">readonly</c:if>>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-4 ">
                            <div class="form-group">
                                <label>部门：</label>
                                <input type="text" class="form-control" name="department" value="${entity.department}" <c:if test="${not empty entity}">readonly</c:if>>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="form-group">
                                <label>请假类型：</label>
                                <c:choose>
                                    <c:when test="${not empty entity}">
                                        <input type="text" class="form-control" name="leaveType" value="${entity.leaveType}" readonly>
                                    </c:when>
                                    <c:otherwise>
                                        <select name="leaveType" class="form-control">
                                            <option value="">请选择请假类型</option>
                                            <option value="-1135316255604717531">事假</option>
                                            <option value="8779689809439049634">婚假</option>
                                            <option value="4699807474016863501">产假</option>
                                            <option value="-3265967495408038335">其它</option>
                                            <option value="5705361015223765326">病假</option>
                                            <option value="3914859601377381015">年假</option>
                                            <option value="-7949396643966726286">丧假</option>
                                            <option value="9086439688191230057">陪护假</option>
                                            <option value="-2312192423498395678">工伤假</option>
                                            <option value="6611264508042233045">探亲假</option>
                                        </select>
                                    </c:otherwise>
                                </c:choose>
                                <%--<input type="text" class="form-control" name="leaveType" value="${entity.leaveType}" <c:if test="${not empty entity}">readonly</c:if>>--%>
                            </div>
                        </div>
                        <div class="col-md-4 ">
                            <div class="form-group">
                                <label>开始请假时间：</label>
                                <input type="text" class="form-control" name="startLeave"
                                       value="<fmt:formatDate value="${entity.startLeave}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate>" <c:if test="${not empty entity}">readonly</c:if>>
                            </div>
                        </div>

                    </div>
                    <div class="row">
                        <div class="col-md-4 ">
                            <div class="form-group">
                                <label>结束请假时间：</label>
                                <input type="text" class="form-control" name="endLeave" value="<fmt:formatDate value="${entity.endLeave}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate>" <c:if test="${not empty entity}">readonly</c:if>>
                            </div>
                        </div>
                        <div class="col-md-8">
                            <div class="form-group">
                                <label>请假事由：</label>
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

    var chooseMaterial = null;
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
                    required : true,
                    minlength:11
                },
                leaveDayCount : {
                    required : true,
                },
                leaveType : {
                    required : true,
                },
                startLeave : {
                    required : true,
                },
                endLeave : {
                    required : true,
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
                    url = '<c:url value="/admin/sign/leaveInsert"/>';
                }else{
                    url = '<c:url value="/admin/sign/leaveUpdate"/>';
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


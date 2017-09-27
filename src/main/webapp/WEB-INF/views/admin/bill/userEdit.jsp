<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<div class="row">
    <div class="col-md-6">
        <div class="portlet light ">
            <div class="portlet-title">
                <div class="caption">
                    <span class="caption-subject font-green-sharp bold uppercase">帐号管理</span>
                </div>
            </div>
            <div class="portlet-body form">
                <form id="user_edit_form" role="form">
                    <div class="form-body">
                    <input type="hidden" value="${entity.userId}" name="userId">
                    <div class="form-group"><label class="control-label">姓名：</label><input type="text" class="form-control" name="name" value="${entity.name}"></div>
                    <div class="form-group"><label class="control-label">登陆帐号：</label><input type="text" class="form-control" name="username" value="${entity.username}" <c:if test="${not empty entity}">readonly</c:if>></div>
                    <div class="form-group"><label class="control-label">密码：</label><input type="password" class="form-control" name="password" value="${entity.password}"></div>
                    <div class="form-group">
                        <label class="control-label">所属系统：</label>
                        <div>
                        <c:choose>
                            <c:when test="${empty entity}">
                                <c:forEach var="role" items="${roles}">
                                    <label class="control-label bill"><input type="radio" name="regSource" value="${role.key}">${role.key}</label>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <c:forEach var="role" items="${roles}">
                                    <label class="control-label ${role.key}"><input type="radio" name="regSource" value="${role.key}" checked>${role.key}</label>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label">角色：</label>
                        <div class="js_role">
                            <c:choose>
                                <c:when test="${empty entity}">
                                    <c:forEach var="role" items="${roles}">
                                        <c:forEach var="r" items="${role.value}">
                                            <label class="control-label ${role.key}"><input type="checkbox" name="roles" data-role="${r.regSystem}" value="${r.roleId}"> ${r.name} </label>
                                        </c:forEach>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <c:forEach var="role" items="${roles[entity.regSource]}">
                                        <label class="control-label ${role.regSystem}"><input type="checkbox" name="roles" data-role="${role.regSystem}" value="${role.roleId}" <c:if test="${entity.hasRole(role.roleId)}">checked</c:if>> ${role.name} </label>
                                    </c:forEach>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                    </div>
                    <div class="form-actions">
                        <button type="button" class="btn btn-primary js_save_btn">保存</button>
                        <a href="<c:url value="/admin/bill/userList"/>" class="btn btn-default">返回</a>
                    </div>
                </form>
            </div>

        </div>
    </div>
</div>

<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/jquery-validation/js/jquery.validate.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/jquery-validation/js/additional-methods.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/jquery-validation/js/messages_zh.min.js" />"></script>
<script src="<c:url value="/resources/admin/global/plugins/layer/layer.js"/>"></script>
<script type="text/javascript">

    $(function () {

        var form = $('#user_edit_form');
        form.validate({
            errorElement : 'span',
            errorClass : 'help-block help-block-error',
            focusInvalid : false,
            rules : {
                username : {
                    required : true
                },
                name : {
                    required : true
                },
                password : {
                    required : true
                },regSource:{
                    required : true
                },roles:{
                    required : true
                }
            },
            messages : {
                username : {
                    required : "登陆名必填"
                }
                ,name : {
                    required : "用户名称必填"
                },
                password : {
                    required : "密码必填"
                }
            },
            invalidHandler : function(event, validator) {

            },
            highlight : function(element) {
                $(element).parents('.form-group').addClass('has-error');
            },
            unhighlight : function(element) {
                $(element).parents('.form-group').removeClass('has-error');
            },errorPlacement: function(error, element) {
                element.parents('.form-group').append(error);
            },
            success : function(label) {
                label.parents('.form-group').removeClass('has-error');
            },
            submitHandler : function(form) {
                var _index = layer.load(2);
                if ($(form).find('input[name=userId]').val()){
                    $.post('<c:url value="/admin/bill/user/update"/>',$(form).serialize(),function (result) {
                        layer.close(_index);
                        if (result==0){
                            layer.msg("修改失败");
                        }else {
                            layer.msg('修改成功',{anim:false,time:1000},function () {
                                window.location.reload();
                            });
                        }
                    },'json');
                }else {
                    $.post('<c:url value="/admin/bill/user/rregistration"/>',$(form).serialize(),function (result) {
                        layer.close(_index);
                        if (result==0){
                            layer.msg("保存失败");
                        }else if(result==2){
                            layer.msg("用户名重复");
                        }else {
                            layer.msg('保存成功',{anim:false,time:1000},function () {
                                window.location.reload();
                            });
                        }
                    },'json');
                }
            }
        });

        /**
         * 保存用户
         */
        $('button.js_save_btn').on('click',function () {
            form.submit();
        });

    });


</script>


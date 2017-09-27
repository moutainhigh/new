<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="portlet box blue-hoki">
    <div class="portlet-title">
        <div class="caption">
            <i class="icon-settings"></i>
            个人设置
        </div>

    </div>
    <div class="portlet-body form">
        <form class="form-horizontal" id="editForm" method="post" autocomplete="off">
			<div class="form-body">
                <div class="form-group">
                    <label class="col-md-2 control-label">原始密码：</label>
                    <div class="col-md-4">
                        <input type="password" name="oldPassword"  value="" class="form-control">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-2 control-label">新密码：</label>
                    <div class="col-md-4">
                        <input type="password" id="js_password" name="password" value="" class="form-control">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-2 control-label">重复密码：</label>
                    <div class="col-md-4">
                        <input type="password" name="repeatPassword" value="" class="form-control">
                    </div>
                </div>
            </div>
            <div class="form-actions">
	            <div class="row">
	                <div class="col-md-offset-2 col-md-10">
	                    <button class="btn green js_submit" type="button">提  交</button>
	                    <a class="btn default" href="javascript:history.back(-1);">返回</a>
	                </div>
	            </div>
             </div>
        </form>
    </div>
</div>


<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/jquery-validation/js/jquery.validate.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/jquery-validation/js/additional-methods.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/jquery-validation/js/messages_zh.min.js" />"></script>


<script type="text/javascript">

    $(function(){

        var form = $('#editForm');
        form.validate({
            errorElement : 'span',
            errorClass : 'help-block help-block-error',
            focusInvalid : false,
            ignore : "",
            rules : {
                oldPassword : {
                    required : true
                },
                password : {
                    required : true,
                    minlength: 5
                },
                repeatPassword:{
                    required : true,
                    equalTo: "#js_password"
                }
            },
            messages : {
                oldPassword : {
                    required : "原始密码不能为空"
                },password : {
                    required: "请输入新密码",
                    minlength: "密码长度不能小于 5 个字符"
                },repeatPassword : {
                    required: "请输入重复密码",
                    minlength: "密码长度不能小于 5 个字符",
                    equalTo: "两次密码输入不一致"
                }
            },
            invalidHandler : function(event, validator) {

            },
            highlight : function(element) {
                $(element).closest('.form-group').addClass('has-error');
            },
            unhighlight : function(element) {
                $(element).closest('.form-group').removeClass('has-error');
            },
            success : function(label) {
                label.closest('.form-group').removeClass('has-error');
            },
            submitHandler : function(form) {
                layer.confirm("你确定提交吗！",function () {
                    $.post('<c:url value="/admin/user/updatePassword"/>',$(form).serialize(),function (result) {
                        if (result=='success'){
                            layer.msg('修改成功',{anim:false,time:1000},function () {
                                window.location.reload();
                            });
                        }else {
                            layer.msg(result,{anim:false,time:1000});
                        }
                    },'json');
                });
            }
        });


        $('button.js_submit').on('click',function () {
            form.submit();
        });
        


    });
</script>
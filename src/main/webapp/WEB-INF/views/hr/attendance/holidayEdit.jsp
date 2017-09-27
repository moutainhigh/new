<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<link rel="stylesheet" type="text/css" href="<c:url value="/resources/admin/global/plugins/bootstrap-datepicker/css/bootstrap-datepicker3.min.css" />"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/hr/css/hr.css" />"/>


<div class="portlet box blue-hoki">
    <div class="portlet-title">
        <div class="caption">
            <i class="fa fa-gift"></i>
            假日管理
        </div>
    </div>
    <div class="portlet-body">
        <div class="row">
            <div class="col-md-6 ">
                <form  role="form" class="js_base_form">
                    <div class="form-body">
                        <div class="row">
                            <div class="col-md-12 ">
                                <div class="form-group">
                                    <label>节假日名称：</label>
                                    <input type="text" class="form-control" name="name" value="${entity.name}">
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6 ">
                                <div class="form-group">
                                    <label>开始日期：</label>
                                    <div class="input-group">
                                        <input type="text" class="form-control" name="startTime"  value="<fmt:formatDate value="${entity.startTime}" pattern="yyyy-MM-dd"/>">
                                        <span class="input-group-btn">
                                        <button class="btn default" type="button">
                                            <i class="fa fa-clock-o"></i>
                                        </button>
                                    </span>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-6 ">
                                <div class="form-group">
                                    <label>结束日期：</label>
                                    <div class="input-group">
                                        <input type="text" class="form-control " name="endTime"  value="<fmt:formatDate value="${entity.endTime}" pattern="yyyy-MM-dd"/>">
                                        <span class="input-group-btn">
                                        <button class="btn default" type="button">
                                            <i class="fa fa-clock-o"></i>
                                        </button>
                                    </span>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                        <div class="col-md-12 ">
                            <div class="form-group">
                                <label>持续时间（天数）：</label>
                                <input type="text" class="form-control" name="duration" value="${entity.duration}">
                            </div>
                        </div>
                    </div>

                    </div>
                    <button type="button" class="btn green js_save_chg" data-action="save">保存</button>
                    <a type="button" class="btn default" href="<c:url value="/hr/atte/holidayList"/>">返回列表</a>
                </form>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.zh-CN.js" />"></script>

<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/jquery-validation/js/jquery.validate.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/jquery-validation/js/additional-methods.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/jquery-validation/js/messages_zh.min.js" />"></script>


<script>

    $(document).ready(function () {

        var startDate = null, endDate=null;
        $('input[name=startTime]').datepicker({
            todayBtn : "linked",
            autoclose : true,
            language: 'zh-CN',
            format : "yyyy-mm-dd",
            todayHighlight : true
        }).on('changeDate',function(e){
            startDate = e.date.getTime();
            var time = new Date(startDate);
            $('input[name=endTime]').datepicker('setStartDate',time);
            if (null!=startDate&&null!=endDate){
               $('input[name=duration]').val(Math.floor((endDate-startDate)/((24*3600*1000)))+1);
            }
        });

        $('input[name=endTime]').datepicker({
            todayBtn : "linked",
            autoclose : true,
            language: 'zh-CN',
            format : "yyyy-mm-dd"
        }).on('changeDate',function(e){
            endDate = e.date.getTime();
            var time = new Date(endDate);
            $('input[name=startTime]').datepicker('setEndDate',time);
            if (null!=startDate&&null!=endDate){
                $('input[name=duration]').val(Math.floor((endDate-startDate)/((24*3600*1000)))+1);
            }
        });

        var validateForm = $('form.js_base_form');
        validateForm.validate({
            errorElement : 'span',
            errorClass : 'help-block help-block-error',
            rules : {
                name : {
                    required : true
                },startTime: {
                    required : true
                },endTime : {
                    required : true
                },duration: {
                    required : true,
                    digits:true
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

        var dataObj={id:'${entity.id}'};
        var _index=null;
        $('button.js_save_chg').on('click',function () {
            if(validateForm.valid()&&null==_index){
                var objArr = $('form.js_base_form').serializeArray();
                for(var e in objArr){
                    var  obj = objArr[e];
                    dataObj[obj.name] = obj.value;
                }
                _index = layer.load(2);
                $.post('<c:url value="/hr/atte/postHolidayEdit"/>', dataObj, function (result) {
                    if (result.success) {
                        layer.close(_index);
                        layer.msg('操作成功',{time:800},function () {
                            window.location.reload();
                        });
                    }else{
                        layer.close(_index);
                        layer.msg('操作失败：'+result.rmsg,{time:2000});
                    }
                }, 'json');
            }
        });

    });

</script>
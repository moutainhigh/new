<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<link rel="stylesheet" type="text/css" href="<c:url value="/resources/admin/global/plugins/bootstrap-timepicker/css/bootstrap-timepicker.min.css" />"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/hr/css/hr.css" />"/>


<div class="portlet box blue-hoki">
    <div class="portlet-title">
        <div class="caption">
            <i class="fa fa-gift"></i>
            班次管理
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
                                    <label>班次名称：</label>
                                    <input type="text" class="form-control" name="schName" value="${entity.schName}">
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6 ">
                                <div class="form-group">
                                    <label>上班时间：</label>
                                    <div class="input-group bootstrap-timepicker timepicker">
                                        <input type="text" class="form-control js_timepicker" name="startTime"  value="${empty entity ? '9:00:00' : entity.startTime}">
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
                                    <label>下班时间：</label>
                                    <div class="input-group bootstrap-timepicker timepicker">
                                        <input type="text" class="form-control js_timepicker" name="endTime"  value="${empty entity ? '18:00:00' : entity.endTime}">
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
                            <div class="col-md-6 ">
                                <div class="form-group">
                                    <label>开始签到时间：</label>
                                    <div class="input-group bootstrap-timepicker timepicker">
                                        <input type="text" class="form-control js_timepicker" name="checkInTime1" value="${empty entity ? '7:00:00' : entity.checkInTime1}">
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
                                    <label>开始签退时间：</label>
                                    <div class="input-group bootstrap-timepicker timepicker">
                                    <input type="text" class="form-control js_timepicker" name="checkOutTime1"  value="${empty entity ? '18:00:00' : entity.checkOutTime1}">
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
                            <div class="col-md-6 ">
                                <div class="form-group">
                                    <label>结束签到时间:	</label>
                                    <div class="input-group bootstrap-timepicker timepicker">
                                    <input type="text" class="form-control js_timepicker" name="checkInTime2"  value="${empty entity ? '10:00:00' : entity.checkInTime2}" >
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
                                    <label>结束签退时间：</label>
                                    <div class="input-group bootstrap-timepicker timepicker">
                                    <input type="text" class="form-control js_timepicker" name="checkOutTime2"  value="${empty entity ? '23:00:00' : entity.checkOutTime2}" >
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
                            <div class="col-md-6 ">
                                <div class="form-group">
                                    <label>必须签到：	</label>
                                    <select name="mustCheckIn"  class="form-control">
                                        <option value="1" <c:if test="${entity.mustCheckIn eq 1 }">selected</c:if>>是</option>
                                        <option value="0" <c:if test="${entity.mustCheckIn eq 0 }">selected</c:if>>否</option>
                                    </select>
                                </div>
                            </div>
                            <div class="col-md-6 ">
                                <div class="form-group">
                                    <label>必须签退：</label>
                                    <select name="mustCheckOut"  class="form-control">
                                        <option value="1" <c:if test="${entity.mustCheckOut eq 1 }">selected</c:if>>是</option>
                                        <option value="0" <c:if test="${entity.mustCheckOut eq 0 }">selected</c:if>>否</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6 ">
                                <div class="form-group">
                                    <label>允许迟到分钟数：</label>
                                    <input type="text" class="form-control" name="lateMinutes" value="${entity.lateMinutes}">
                                </div>
                            </div>
                            <div class="col-md-6 ">
                                <div class="form-group">
                                    <label>允许早退分钟数：</label>
                                    <input type="text" class="form-control" name="earlyMinutes" value="${entity.earlyMinutes}">
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6 ">
                                <div class="form-group">
                                    <label>工作时间(分钟)：	</label>
                                    <input type="text" class="form-control" name="workTime" value="${entity.workTime}">
                                </div>
                            </div>
                            <div class="col-md-6 ">
                                <div class="form-group">
                                    <label>计为工作日数：</label>
                                    <input type="text" class="form-control" name="workDay" value="${entity.workDay}">
                                </div>
                            </div>
                        </div>
                    </div>
                    <button type="button" class="btn green js_save_chg" data-action="save">保存</button>
                    <a type="button" class="btn default" href="<c:url value="/hr/atte/shiftList"/>">返回列表</a>
                </form>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/bootstrap-timepicker/js/bootstrap-timepicker.min.js" />"></script>

<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/jquery-validation/js/jquery.validate.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/jquery-validation/js/additional-methods.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/jquery-validation/js/messages_zh.min.js" />"></script>


<script>

    $(document).ready(function () {

        $('input.js_timepicker').timepicker({
            showSeconds:true,
            showMeridian:false
        }).parent(".input-group").on("click", ".input-group-btn", function (t) {
            t.preventDefault(), $(this).prev('input').timepicker("showWidget")
        })

        var checkObj = function (obj) {
            var errorMsg={
                schName:"班次名称不能为空",
                startTime:"上班时间不能为空",
                endTime:"下班时间不能为空",
                checkInTime1:"开始签到时间不能为空",
                checkInTime2:"结束签到时间不能为空",
                checkOutTime1:"开始签退时间不能为空",
                checkOutTime2:"结束签退时间不能为空",
                lateMinutes:"允许迟到分钟数不能为空",
                earlyMinutes:"允许早退分钟数不能为空",
                workTime:"工作时间分钟数不能为空",
                workDay:"计为工作日数不能为空",
            }
            var flag = true;
            for(var e in errorMsg){
                if(!obj[e]){
                    layer.msg(errorMsg[e],{time:800});
                    flag = false;
                    break;
                }
            }
            return flag;
        }

        var validateForm = $('form.js_base_form');
        validateForm.validate({
            errorElement : 'span',
            errorClass : 'help-block help-block-error',
            rules : {
                schName : {
                    required : true
                },startTime: {
                    required : true
                },endTime : {
                    required : true
                },checkInTime1: {
                    required : true
                },checkInTime2: {
                    required : true
                },checkOutTime1: {
                    required : true
                },checkOutTime2 : {
                    required : true
                },lateMinutes : {
                    required : true,
                    digits:true
                },earlyMinutes : {
                    required : true,
                    digits:true
                },workTime : {
                    required : true,
                    digits:true
                },workDay : {
                    required : true,
                    number:true
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
                $.post('<c:url value="/hr/atte/postShiftEdit"/>', dataObj, function (result) {
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
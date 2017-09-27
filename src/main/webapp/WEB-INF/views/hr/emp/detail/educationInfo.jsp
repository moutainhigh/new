<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link href="<c:url value="/resources/admin/global/plugins/bootstrap/css/bootstrap.min.css" />" rel="stylesheet" type="text/css" />
<link href="<c:url value="/resources/admin/global/css/components.min.css "/>" rel="stylesheet" id="style_components" type="text/css">
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/admin/global/plugins/bootstrap-datepicker/css/bootstrap-datepicker3.min.css" />"/>
<link type="text/css" href="/resources/admin/global/plugins/select2/css/select2.min.css"  rel="stylesheet">
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/admin/global/plugins/font-awesome/css/font-awesome.min.css" />"/>

<style>
    .form-control{
        margin-top: 3px;
        border: #d3d6db 1px solid;
        color: #999;
        border-radius: 2px !important;
        -webkit-border-radius: 2px !important;
        -moz-border-radius: 2px !important;
        -o-border-radius: 2px !important;
        -ms-border-radius: 2px !important;
        height:28px;
        padding: 0px 12px;
        line-height: 26px;
    }
    .input-group-btn .btn{
        margin-top: 3px;
    }
    .js_tab_control>li>a{
        padding-left: 9px;
        padding-right: 9px;
    }
</style>

<div class="js_oper">
    <c:if test="${enableEdit}">
        <button type="button" class="export-btn btn btn-transparent grey-salsa btn-outline btn-circle btn-sm active" data-action="add"><i class="fa fa-plus"></i> 新增</button>
        <button type="button" class="export-btn btn btn-transparent grey-salsa btn-outline btn-circle btn-sm" data-action="del"><i class="fa fa-trash-o"></i> 删除</button>
        <button type="button" class="export-btn btn btn-transparent grey-salsa btn-outline btn-circle btn-sm hidden" data-action="save"><i class="fa fa-share"></i> 保存</button>
    </c:if>
</div>
<form id="editForm">
    <div class="table-scrollable">
        <table class="table table-bordered table-hover" id="js_table">
            <thead>
                    <tr>
                        <td data-type="checkbox" data-class="js_checkbox" data-name="id" style="width: 3%;">序号</td>
                        <td data-type="datetime" data-validate="validate" data-name="admissionDate" class="col-md-1">入学日期</td>
                        <td data-type="datetime" data-validate="validate" data-name="graduationDate" class="col-md-1">毕业日期</td>
                        <td data-type="text" data-validate="validate" data-name="school" class="col-md-2">学校</td>
                        <td data-type="text" data-validate="validate" data-name="profession" class="col-md-1">专业</td>
                        <td data-type="select" data-validate="validate" data-name="education" class="col-md-1">学历
                            <select  class="hidden">
                                <option value="">请选择</option>
                                <c:forEach items="${education}" var="pro"><option value="${pro.dictDataValue}">${pro.dictDataKey}</option></c:forEach>
                            </select>
                        </td>
                        <td data-type="select" data-name="degree" class="col-md-1">学位
                            <select  class="hidden">
                                <c:forEach items="${degree}" var="pro"><option value="${pro.dictDataValue}">${pro.dictDataKey}</option></c:forEach>
                            </select>
                        </td>
                        <td data-type="datetime" data-name="degreeDate" class="col-md-1">学位授予日期</td>
                        <td data-type="text" data-name="degreeOrg" class="col-md-2">学位授予单位</td>
                        <td data-type="text" data-name="note" class="col-md-1">备注</td>
                        <td data-type="checkbox" data-class="js_top_edu" data-name="topEducation" class="col-md-1">是否最高学历</td>
                    </tr>
                    <c:forEach items="${detailList}" var="info">
                        <tr>
                            <td><input type="checkbox" class="checkbox js_checkbox" value="${info.id}"></td>
                            <td><fmt:formatDate value="${info.admissionDate}" pattern="yyyy-MM-dd"/></td>
                            <td><fmt:formatDate value="${info.graduationDate}" pattern="yyyy-MM-dd"/></td>
                            <td>${info.school}</td>
                            <td>${info.profession}</td>
                            <td><select name="education">
                                    <c:forEach items="${education}" var="pro"><c:if test="${info.education eq pro.dictDataValue}"><option value="${pro.dictDataValue}" selected>${pro.dictDataKey}</option></c:if></c:forEach>
                                </select>
                            </td>
                            <td><select name="degree">
                                    <c:forEach items="${degree}" var="pro"><c:if test="${info.degree eq pro.dictDataValue}"><option value="${pro.dictDataValue}" selected>${pro.dictDataKey}</option></c:if></c:forEach>
                                </select>
                            </td>
                            <td><fmt:formatDate value="${info.degreeDate}" pattern="yyyy-MM-dd"/></td>
                            <td>${info.degreeOrg}</td>
                            <td>${info.note}</td>
                            <td data-top="${info.topEducation}">${info.topEducation eq 1?'是':'否'}</td>
                        </tr>
                    </c:forEach>
            </thead>
        </table>
    </div>
</form>

<script src="<c:url value="/resources/admin/global/plugins/jquery.min.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/admin/global/plugins/bootstrap/js/bootstrap.min.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/admin/global/plugins/layer/layer.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/select2/js/select2.full.min.js" />"></script>


<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.zh-CN.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/hr/emp/detail/common.js" />"></script>

<script>
    $(function () {

        var parent = window.parent;
        var empId = "${empId}";
        var delUrl = '<c:url value="/hr/delEducationInfo"/>';
        tableOper.init('#js_table',parent,empId,delUrl,function (tb,validate) {
            var _arr = new Array();
            var errorCount = 0;
            tb.find('tr:gt(0)').each(function (k, v) {
                if ($(v).find('td:eq(0) input').prop('checked')) {
                    var tempFlag = true;
                    var obj = {};
                    obj.empId = empId;
                    $(v).find('td').each(function (i, e) {
                        var td = tb.find('tr:eq(0)').find('td:eq('+i+')');
                        var _type = $(td).data('type');
                        var o =null;
                        if (_type == 'datetime'||_type == 'text'||_type == 'checkbox'){
                            o = $(e).find('input');
                        }else if(_type == 'select'){
                            o = $(e).find('select');
                        }
                        if (i == 0) {
                            var val = o.val();
                            if (val) {
                                obj.id = val;
                            } else {
                                obj.id = '';
                            }
                        } else {
                            var _name = o.attr('name');
                            if (_name) {
                                if (_name == "education") {
                                    if (!validate(o)) {
                                        errorCount++;
                                    }
                                } else if (_name == "topEducation") {
                                   if(o.prop('checked')){
                                       o.val(1);
                                   }else{
                                       o.val(0);
                                   }
                                }
                                obj[_name] = o.val();
                            }
                        }
                    });
                    _arr.push(obj);
                }
            });
            if (_arr.length > 0 && errorCount == 0) {
                var _index = layer.load(2);
                $.post('<c:url value="/hr/saveEducationInfo"/>', {detailArray: JSON.stringify(_arr)}, function (result) {
                    layer.close(_index);
                    if (result.success) {
                        layer.msg('操作成功!', function () {
                            self.location.reload();
                        })
                    } else {
                        layer.msg(result.msg);
                    }
                }, 'json');
            }
        },function (check, tr) {
            if (check.parents('tr').find('td:last').data('top')==1){
                tr.find('td:last').find('input').prop('checked',true);
            }
        });

        $('#js_table').on('blur','input.validate',function () {
            tableOper.validate(this);
        }).on('click','input.js_top_edu',function () {
            var flag = false;
            $('#js_table tr:gt(0):visible').find('td:last').each(function (k, v) {
                if($(v).data('top')==1){
                    flag=true;
                    return false;
                }
            });
            if(flag||$('#js_table input.js_top_edu:checked').length>1){
                layer.msg('最高学历只能有一个');
                $(this).prop('checked',false);
            }
        });

    });


</script>
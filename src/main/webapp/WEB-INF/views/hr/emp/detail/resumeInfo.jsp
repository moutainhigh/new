<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link href="<c:url value="/resources/admin/global/plugins/bootstrap/css/bootstrap.min.css" />" rel="stylesheet" type="text/css" />
<link href="<c:url value="/resources/admin/global/css/components.min.css "/>" rel="stylesheet" id="style_components" type="text/css">
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/admin/global/plugins/bootstrap-datepicker/css/bootstrap-datepicker3.min.css" />"/>
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
    .table-bordered > tbody > tr > td{
        border: none;
        padding: 3px;
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
                        <td data-type="datetime" data-validate="validate" data-name="startTime" class="col-md-1">履历起始日期</td>
                        <td data-type="datetime" data-name="endTime" class="col-md-1">履历终止日期</td>
                        <td data-type="text" data-validate="validate" data-name="company" class="col-md-2">工作单位</td>
                        <td data-type="text" data-validate="validate" data-name="duty" class="col-md-1">担任职务</td>
                        <td data-type="text" data-name="phone" class="col-md-1">联系电话</td>
                        <td data-type="text" data-name="witness" class="col-md-1">证明人</td>
                        <td data-type="text" data-name="witnessPhone" class="col-md-1">证明人电话</td>
                        <td data-type="text" data-name="leaveReason" class="col-md-1">离职原因</td>
                    </tr>
                    <c:forEach items="${detailList}" var="info">
                        <tr>
                            <td><input type="checkbox" class="checkbox js_checkbox" value="${info.id}"></td>
                            <td><fmt:formatDate value="${info.startTime}" pattern="yyyy-MM-dd"/></td>
                            <td><fmt:formatDate value="${info.endTime}" pattern="yyyy-MM-dd"/></td>
                            <td>${info.company}</td>
                            <td>${info.duty}</td>
                            <td>${info.phone}</td>
                            <td>${info.witness}</td>
                            <td>${info.witnessPhone}</td>
                            <td>${info.leaveReason}</td>
                        </tr>
                    </c:forEach>
                </thead>
            </table>
        </div>
    </form>

<script src="<c:url value="/resources/admin/global/plugins/jquery.min.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/admin/global/plugins/bootstrap/js/bootstrap.min.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/admin/global/plugins/layer/layer.js" />"></script>


<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.zh-CN.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/hr/emp/detail/common.js" />"></script>

<script>
    $(function () {
        var parent = window.parent;
        var empId = "${empId}";
        var delUrl = '<c:url value="/hr/delResumeInfo"/>';
        tableOper.init('#js_table',parent,empId,delUrl,function (tb,validate) {
            var _arr = new Array();
            var errorCount = 0;
            tb.find('tr:gt(0)').each(function (k, v) {
                if ($(v).find('td:eq(0) input').prop('checked')) {
                    var obj = {};
                    obj.empId = empId;
                    $(v).find('td').each(function (i, e) {
                        var _input = $(e).find('input');
                        if (i == 0) {
                            var val = _input.val();
                            if (val) {
                                obj.id = val;
                            } else {
                                obj.id = '';
                            }
                        } else {
                            var _name = _input.attr('name');
                            if (_name) {
                                if (_name == "startTime") {
                                    if (!validate(_input)) {
                                        errorCount++;
                                    }
                                } else if (_name == "company") {
                                    if (!validate(_input)) {
                                        errorCount++;
                                    }
                                } else if (_name == "duty") {
                                    if (!validate(_input)) {
                                        errorCount++;
                                    }
                                }
                                obj[_name] = _input.val();
                            }
                        }
                    });
                    _arr.push(obj);
                }
            });
            if (_arr.length > 0 && errorCount == 0) {
                $.post('<c:url value="/hr/saveResumeInfo"/>', {detailArray: JSON.stringify(_arr)}, function (result) {
                    if (result.success) {
                        layer.msg('操作成功!', function () {
                            self.location.reload();
                        })
                    } else {
                        layer.msg(result.msg);
                    }
                }, 'json');
            }
        });

        $('#js_table').on('blur','input.validate',function () {
            tableOper.validate(this);
        });

    });


</script>
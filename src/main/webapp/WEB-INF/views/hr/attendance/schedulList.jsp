<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/hr/css/hr.css" />"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/admin/global/plugins/bootstrap-datepicker/css/bootstrap-datepicker3.min.css" />"/>


<div class="portlet light">
    <div class="portlet-title">
        <div class="caption">
            <i class="icon-layers font-green"></i>
            <span class="caption-subject font-green sbold uppercase">排班列表</span>
        </div>
    </div>
    <div class="portlet-body">
        <div class="row">
            <div class="col-md-12 table-responsive">
                <div class="actions form-inline">
                    <div class="form-group">
                        公司：<div class="input-group">
                        <input name="company" class="form-control input-inline input-sm" type="hidden"/>
                        <input type="text" readonly class="form-control input-sm" name="companyStr" value="">
                        <%--<span class="input-group-btn"><button type="button" class="btn btn-sm btn-primary js_comp_dept_choose">选择</button></span>--%>
                    </div>
                    </div>
                    <div class="form-group">
                        部门：<div class="input-group">
                        <input name="department" class="form-control input-inline input-sm" type="hidden"/>
                        <input type="text" readonly class="form-control input-sm" name="departmentStr" value="">
                        <span class="input-group-btn"><button type="button" class="btn btn-sm btn-primary js_comp_dept_choose">选择</button></span>
                    </div>
                    </div>
                    <div class="form-group">
                        日期：
                        <input name="startDate" class="form-control input-inline input-sm" type="text" value=""/>
                        - <input name="endDate" class="form-control input-inline input-sm" type="text" value=""/>
                    </div>
                    <div class="form-group">
                        <button class="btn btn-sm green js_search"><i class="fa fa-search"></i> 搜索 </button>
                    </div>
                </div>
                <table class="table table-striped table-bordered table-advance table-hover table-checkable " id="datatable_ajax">
                    <thead>
                    <tr role="row" class="heading">
                        <th rowspan="2">
                            <label class="mt-checkbox mt-checkbox-single mt-checkbox-outline">
                                <input type="checkbox" class="group-checkable" data-set="#sample_1_2 .checkboxes">
                                <span></span>
                            </label>
                        </th>
                        <%--<th rowspan="2">员工号</th>--%>
                        <th rowspan="2">姓名</th>
                        <th rowspan="2">单位</th>
                        <th rowspan="2">部门</th>
                        <th>${month}</th>
                        <c:forEach items="${dateList}" var="d">
                            <th>${fn:split(d,',')[0]}</th>
                        </c:forEach>
                    </tr>
                    <tr>
                        <th>星期</th>
                        <c:forEach items="${dateList}" var="d">
                            <th>${fn:split(d,',')[1]}</th>
                        </c:forEach>
                    </tr>
                    </thead>
                    <tbody></tbody>
                </table>
            </div>
        </div>
    </div>
</div>


<script src="<c:url value="/resources/admin/global/scripts/datatable.js" />"></script>
<script src="<c:url value="/resources/admin/global/plugins/datatables/datatables.min.js" />"></script>
<script src="<c:url value="/resources/admin/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.zh-CN.js" />"></script>
<script>

    $(document).ready(function() {
        var maxDate = '${fn:length(dateList)}';
    $('button.js_search').on('click',function () {
        $.post('<c:url value="/hr/atte/schedulListData"/>',{}, function (result) {
            var data = result.data;
            if (data) {
                $('#datatable_ajax tbody').empty();
                $(data).each(function (k, v) {
                    var _tr = $('<tr>');
                    _tr.append($('<td>').text((k+1)));
                    _tr.append($('<td>').text(v.empName));
                    _tr.append($('<td>').text(v.companyName));
                    _tr.append($('<td>').text(v.departmentName));
                    _tr.append($('<td>'));
                   $(v.details).each(function (i,e) {
                       if (e.shiftName == "公休"){
                           _tr.append($('<td>').text(e.shiftName).addClass('bg-green bg-font-green'));
                       }else if(e.shiftName == "上班"){
                           _tr.append($('<td>').text(e.shiftName));//.addClass('bg-green-seagreen')
                       }else{
                           _tr.append($('<td>').text(e.shiftName));
                       }
                   });
                   $('#datatable_ajax tbody').append(_tr);
                });
            }
        }, 'json');
    });

        var timeInitArgs = {
            todayBtn : "linked",
            autoclose : true,
            language: 'zh-CN',
            format : "yyyy-mm-dd"
        };

        $('input[name=startDate]').datepicker(timeInitArgs).on('changeDate',function(e){
            $('input[name=endDate]').datepicker('setStartDate',e.date);
        });

        $('input[name=endDate]').datepicker(timeInitArgs).on('changeDate',function(e){
            $('input[name=startDate]').datepicker('setEndDate',e.date);
        });

    });
</script>
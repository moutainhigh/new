<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/hr/css/hr.css" />"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/admin/global/plugins/bootstrap-datepicker/css/bootstrap-datepicker3.min.css" />"/>

<div class="row">
    <div class="col-md-12">
        <div class="portlet light">
            <div class="portlet-title">
                <div class="caption">
                    <i class="icon-social-dribbble font-green"></i>
                    <span class="caption-subject font-green bold uppercase">入职情况</span>
                </div>
                <div class="actions">
                    <div class="btn-group btn-group-devided" >
                        <a href="<c:url value="/hr/reports/empIndutyDaily"/>"  class="btn btn-transparent blue btn-outline btn-circle btn-sm active green">入职情况</a>
                        <a href="<c:url value="/hr/reports/empOutdutyDaily"/>"  class="btn btn-transparent blue btn-outline btn-circle btn-sm">离职情况</a>
                        <%--<a href="<c:url value="/hr/reports/inventoryDutyLv"/>"  class="btn btn-transparent blue btn-outline btn-circle btn-sm">职级</a>--%>
                        <a href="<c:url value="/hr/reports/empTransferDaily"/>"  class="btn btn-transparent blue btn-outline btn-circle btn-sm">调动情况</a>
                    </div>
                </div>
            </div>
            <div class="portlet-body">
                <div class="actions form-inline">
                    <div class="form-group">
                        时间选择：<input name="startTime" class="form-control input-inline input-sm js_date" type="text" value="${startDate}"/>- <input name="endTime" class="form-control input-inline input-sm js_date" type="text" value="${endDate}"/>
                    </div>
                    <div class="form-group">部门：<select name="department" class="form-control input-sm">
                        <option value="">请选择</option>
                        <c:forEach items="${departmentList}" var="dept">
                            <option value="${dept.id}">${dept.name}</option>
                        </c:forEach>
                    </select>
                    </div>
                    <div class="form-group">岗位序列：
                        <select name="jobSequence" class="form-control input-sm">
                            <option value="">请选择</option>
                            <option value="5" <c:if test="${jobSequenceId eq 5}">selected</c:if>>管理类</option>
                            <option value="6" <c:if test="${jobSequenceId eq 6}">selected</c:if>>工人类</option>
                        </select>
                    </div>
                    <div class="form-group">时时查询：
                        <select name="searchNow" class="form-control input-sm">
                            <option value="false" <c:if test="${!searchNow}">selected</c:if>>否</option>
                            <option value="true" <c:if test="${searchNow}">selected</c:if>>是</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <button class="btn btn-sm sbold blue js_search"><i class="fa fa-search"></i> 查询</button>
                        <button class="btn btn-sm sbold  js_download"><i class="fa fa-download"></i> 导出 </button>
                    </div>
                </div>
                <div class="table-responsive mar_t20">
                    <table class="table table-width table-center table-bordered table-advance table-striped th-weight" id="chgDataTable">
                        <thead>
                        <tr>
                            <th> 序号</th>
                            <th> 区域</th>
                            <th> 管理单位 </th>
                            <th> 管理部门 </th>
                            <th> 管理分组 </th>
                            <th> 人员编码 </th>
                            <th> 出生日期</th>
                            <th> 年龄 </th>
                            <th> 入司时间 </th>
                            <th> 用工状态 </th>
                            <th> 姓名 </th>
                            <th> 性别 </th>
                            <th> 岗位 </th>
                            <th> 身份证号码 </th>
                            <th> 职级 </th>
                            <th> 岗位序列 </th>
                            <th> 操作日期 </th>
                        </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.zh-CN.js" />"></script>
<script>
    $(document).ready(function () {

        var jobSequenceMap = {};
        <c:forEach items="${jobSequence}" var="entity">
        jobSequenceMap[${entity.dictDataValue}]='${entity.dictDataKey}';
        </c:forEach>

        var dutyLevelMap = {};
        <c:forEach items="${dutyLevel}" var="entity">
        dutyLevelMap[${entity.dictDataValue}]='${entity.dictDataKey}';
        </c:forEach>


        var timeInitArgs = {
            todayBtn : "linked",
            autoclose : true,
            language: 'zh-CN',
            format : "yyyy-mm-dd",
            todayHighlight : true
        };

        $('input.js_date').datepicker(timeInitArgs);

        var build = function (args,fn) {
            var _index;
            $.ajax({
                async: true,
                beforeSend: function () {
                    _index = layer.load(2);
                },
                complete: function () {
                    layer.close(_index);
                },
                type : 'POST' ,
                data:args.data==undefined?{}:args.data,
                url : args.url,
                success: function (result) {
                    if (result.success){
                        fn(result.rdataMap);
                    }else{
                        layer.msg(result.rmsg);
                    }
                }
            });
        }


        $('button.js_search').on('click',function () {
            var args = {
                url: '<c:url value="/hr/reports/getEmpChangeDailyData"/>',
                data:{
                    type:1,
                    jobSequence:$('select[name=jobSequence]').val(),
                    startTime:$('input[name=startTime]').val(),
                    endTime:$('input[name=endTime]').val(),
                    searchNow:$('select[name=searchNow]').val(),
                    companyId:'${companyId}',
                    searchAll:'${empty searchAll? false : searchAll }'
                }
            };
            build(args,function (data) {
                var _tb = $('#chgDataTable').find('tbody').empty();
                var _showUrl = '<c:url value="/hr/show/"/>';
                $(data.inDuty).each(function (k, v) {
                    var _tr = $('<tr>');
                    _tr.append('<td>'+(k+1)+'</td>');
                    _tr.append('<td>'+v.area+'</td>');
                    _tr.append('<td>'+v.companyStr+'</td>');
                    _tr.append('<td>'+v.departmentStr+'</td>');
                    _tr.append('<td>'+v.managerGroup+'</td>');
                    _tr.append('<td>'+v.empNo+'</td>');
                    _tr.append('<td>'+v.birthdate+'</td>');
                    _tr.append('<td>'+v.age+'</td>');
                    _tr.append('<td>'+v.joinCompDate+'</td>');
                    _tr.append('<td>'+v.isFormalStr+'</td>');
                    _tr.append('<td><a href="'+_showUrl+v.id+'" target="_blank">'+v.empName+'</a></td>');
                    _tr.append('<td>'+v.sexStr+'</td>');
                    _tr.append('<td>'+v.job+'</td>');
                    _tr.append('<td>'+v.idCard+'</td>');
                    _tr.append('<td>'+dutyLevelMap[v.dutyLevelId]+'</td>');
                    _tr.append('<td>'+jobSequenceMap[v.jobSequence]+'</td>');
                    _tr.append('<td>'+v.createtime+'</td>');
                    _tb.append(_tr);
                });
            });
        });

        var _initSearch = '${initSearch}';
        if(_initSearch == 'true'){
            $('button.js_search').trigger('click');
        }

        $('button.js_download').on('click',function () {
            $('#downloadForm').remove();
            var obj = {
                type:1,
                jobSequence:$('select[name=jobSequence]').val(),
                startTime:$('input[name=startTime]').val(),
                endTime:$('input[name=endTime]').val(),
                searchNow:$('select[name=searchNow]').val(),
                companyId:'${companyId}',
                searchAll:'${empty searchAll? false : searchAll }'
            };
            var _form = $('<form>');
            _form.attr('action','<c:url value="/hr/reports/exportEmpChangeDailyData"/>');
            _form.attr('id','downloadForm');
            _form.attr('method','post');
            _form.attr('target', '_blank');
            for(var e in obj){
                var _input = $('<input type="text" name="'+e+'" />');
                _input.val(obj[e]);
                _form.append(_input);
            }
            $(document).find('body').append(_form);
            $('#downloadForm').submit();
        });
    });


</script>
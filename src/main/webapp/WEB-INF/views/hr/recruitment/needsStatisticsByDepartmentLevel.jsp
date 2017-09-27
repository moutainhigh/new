<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<link rel="stylesheet" type="text/css" href="<c:url value="/resources/admin/global/plugins/bootstrap-datepicker/css/bootstrap-datepicker3.min.css" />"/>

<div class="portlet box blue-hoki">
    <div class="portlet-title">
        <div class="caption">招聘汇总(按层级统计)</div>
    </div>
    <div class="portlet-body">
        <form class="navbar-form navbar-left" role="search" id="materialSearchForm">
            <div class="form-group">
                日期：
                <div class="input-group date form_date" data-date="" data-date-format="yyyy-mm-dd" data-link-field="startDate" data-link-format="yyyy-mm-dd">
                    <input class="form-control" name="startDate" size="16" type="text" placeholder="开始时间">
                </div>
                -
                <div class="input-group date form_date" data-date="" data-date-format="yyyy-mm-dd" data-link-field="endDate" data-link-format="yyyy-mm-dd">
                    <input class="form-control" name="endDate" size="16" type="text" placeholder="结束时间">
                </div>
            </div>
            <div class="form-group">
                <button type="button" class="btn btn-default js_search">搜索</button>
                <button type="button" class="btn btn-default js_export">导出</button>
                <a href="javascript:history.go(-1);"><button type="button" class="btn btn-default">返回</button></a>
            </div>
        </form>
        <div id="myTabContent" class="tab-content">
            <div class="tab-pane fade in active" id="home">
                <div class="portlet-body">
                    <div class="row">
                        <div class="col-md-12">
                            <table class="table table-width table-striped table-bordered table-advance table-hover table-checkable " id="datatable_ajax">
                                <thead>
                                <tr>
                                    <th width="4%">层级</th>
                                    <th width="4%">汇总</th>
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
    </div>
</div>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.zh-CN.js" />"></script>
<script>

    $(function () {
        var _index = null;
        $('button.js_search').on('click',function () {
            var _startDate = $('input[name=startDate]').val();
            var _endDate = $('input[name=endDate]').val();
            if(_startDate&&_endDate){
                _index = layer.load(2);
                $.ajax({
                    async: false,
                    type: 'POST',
                    dataType: 'json',
                    data:{
                        startTime:_startDate,
                        endTime:_endDate
                    },
                    url: '<c:url value="/admin/hr/recruitmentNeeds/statisticsDataByDepartmentLevel"/>',
                    success: function (result) {
                        layer.close(_index);
                        if(result.success){
                            buildTb(result.rdata);
                        }else{
                            layer.msg(result.rmsg);
                        }
                    }
                });
            } else {
                layer.alert("请选择开始和结束日期", {icon:0});
            }
        });


        $('input[name=startDate]').datepicker({
            todayBtn : "linked",
            autoclose : true,
            language: 'zh-CN',
            format : "yyyy-mm-dd",
            todayHighlight : true,
            endDate : new Date()
        });

        $('input[name=endDate]').datepicker({
            todayBtn : "linked",
            autoclose : true,
            language: 'zh-CN',
            format : "yyyy-mm-dd",
            todayHighlight : true,
            endDate : new Date()
        });


        var entityKey = 'departmentLevel,manageGroupNum';
        var  buildTb = function (resultList) {
            var _body = $('#datatable_ajax tbody').empty();
            var _summation=0;
            $.each(resultList,function (k,v) {
                var _tr = $('<tr>');

                _tr = $('<tr>');
                $.each(entityKey.split(','),function (i, e) {
                    _tr.append($('<td>').text(v[e]));
                    if(e == 'manageGroupNum'){
                        if ($.isNumeric(v[e])) {
                            _summation+=v[e];
                        }
                    }
                });
                _body.append(_tr);
                if(k == resultList.length-1){
                    _tr = $('<tr>');
                    _tr.append($('<td>').text("总计"));
                    _tr.append($('<td>').text(_summation));
                    _body.append(_tr);
                }

            });
        }

        $("button.js_export").on('click',function () {
            var _startTime = $('input[name=startTime]').val();
            var _endTime= $('input[name=endTime]').val();
            $('#exportForm').remove();
            var _form = $('<form>');
            _form.attr('action','<c:url value="/admin/hr/recruitmentNeeds/exportDepartmentLevel"/>');
            _form.attr('id','exportForm');
            _form.attr('method','POST');
            _form.append($('<input>').attr('name',"startTime").val(_startTime));
            _form.append($('<input>').attr('name',"endTime").val(_endTime));
            $(document).find('body').append(_form);
            $('#exportForm').submit();
        });

    });



</script>
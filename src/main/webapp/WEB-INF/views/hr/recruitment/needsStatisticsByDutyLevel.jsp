<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<link rel="stylesheet" type="text/css" href="<c:url value="/resources/admin/global/plugins/bootstrap-datepicker/css/bootstrap-datepicker3.min.css" />"/>

<div class="portlet box blue-hoki">
    <div class="portlet-title">
        <div class="caption">招聘汇总(按职级统计)</div>
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
                                    <th>中心(公司)\职级</th>
                                    <th>总经理级</th>
                                    <th>副总经理级</th>
                                    <th>总监级</th>
                                    <th>副总监级</th>
                                    <th>经理级</th>
                                    <th>副经理级</th>
                                    <th>主管级</th>
                                    <th>主办级</th>
                                    <th>员级</th>
                                    <th>合计</th>
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
                    url: '<c:url value="/admin/hr/recruitmentNeeds/statisticsDataByDutyLevel"/>',
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


        var entityKey = 'company,topManage,subsidiaryTopManage,topInspector,subsidiaryTopInspector,manage,subsidiaryManage,charge,director,staff,summation';
        var  buildTb = function (resultList) {
            var _body = $('#datatable_ajax tbody').empty();
            var _topManage=0, _subsidiaryTopManage=0, _topInspector=0, _subsidiaryTopInspector=0,_manage=0,
                _subsidiaryManage=0,_charge=0,_director=0,_staff=0,_summation=0;
            $.each(resultList,function (k,v) {
                var _tr = $('<tr>');

                _tr = $('<tr>');
                $.each(entityKey.split(','),function (i, e) {
                    _tr.append($('<td>').text(v[e]));
                    if(e == 'topManage'){
                        if ($.isNumeric(v[e])) {
                            _topManage+=v[e];
                        }
                    }else if(e == 'subsidiaryTopManage'){
                        if ($.isNumeric(v[e])) {
                            _subsidiaryTopManage+=v[e];
                        }
                    }else if(e == 'topInspector'){
                        if ($.isNumeric(v[e])) {
                            _topInspector+=v[e];
                        }
                    }else if(e == 'subsidiaryTopInspector'){
                        if ($.isNumeric(v[e])) {
                            _subsidiaryTopInspector+=v[e];
                        }
                    }else if(e == 'manage'){
                        if ($.isNumeric(v[e])) {
                            _manage+=v[e];
                        }
                    }else if(e == 'subsidiaryManage'){
                        if ($.isNumeric(v[e])) {
                            _subsidiaryManage+=v[e];
                        }
                    }else if(e == 'charge'){
                        if ($.isNumeric(v[e])) {
                            _charge+=v[e];
                        }
                    }else if(e == 'director'){
                        if ($.isNumeric(v[e])) {
                            _director+=v[e];
                        }
                    }else if(e == 'staff'){
                        if ($.isNumeric(v[e])) {
                            _staff+=v[e];
                        }
                    }else if(e == 'summation'){
                        if ($.isNumeric(v[e])) {
                            _summation+=v[e];
                        }
                    }
                });
                _body.append(_tr);
                if(k == resultList.length-1){
                    _tr = $('<tr>');
                    _tr.append($('<td>').text("总计"));
                    _tr.append($('<td>').text(_topManage));
                    _tr.append($('<td>').text(_subsidiaryTopManage));
                    _tr.append($('<td>').text(_topInspector));
                    _tr.append($('<td>').text(_subsidiaryTopInspector));
                    _tr.append($('<td>').text(_manage));
                    _tr.append($('<td>').text(_subsidiaryManage));
                    _tr.append($('<td>').text(_charge));
                    _tr.append($('<td>').text(_director));
                    _tr.append($('<td>').text(_staff));
                    _tr.append($('<td>').text(_summation));
                    _body.append(_tr);
                }

            });
        }

        /**
         * 导出职级汇总
         */
        $("button.js_export").on('click',function () {
            var _startTime = $('input[name=startTime]').val();
            var _endTime= $('input[name=endTime]').val();
            $('#exportForm').remove();
            var _form = $('<form>');
            _form.attr('action','<c:url value="/admin/hr/recruitmentNeeds/exportDutyLevel"/>');
            _form.attr('id','exportForm');
            _form.attr('method','POST');
            _form.append($('<input>').attr('name',"startTime").val(_startTime));
            _form.append($('<input>').attr('name',"endTime").val(_endTime));
            $(document).find('body').append(_form);
            $('#exportForm').submit();
        });

    });



</script>
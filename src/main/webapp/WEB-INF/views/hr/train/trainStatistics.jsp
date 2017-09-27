<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/hr/css/hr.css" />"/>

<link rel="stylesheet" type="text/css" href="<c:url value="/resources/admin/global/plugins/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" />"/>

<style>
    iframe[src="about:blank"] {
        display: block;
    }
</style>

<link rel="stylesheet" type="text/css" href="<c:url value="/resources/hr/css/hr.css" />"/>
<div class="portlet box blue-hoki" id="form_wizard_1">
    <div class="portlet-title">
        <div class="caption">
            <i class="fa fa-gift"></i>
            培训学时分析
        </div>
        <div class="actions form-inline">
            <c:if test="${empty reportData}"><c:if test="${currYY eq year and currMM eq month}"><button class="btn btn-sm sbold blue js_build"><i class="fa fa-calculator"></i>立即统计</button></c:if></c:if>
            <c:if test="${! empty reportData}"><button class="btn btn-sm green blue js_rebuild"><i class="fa fa-refresh"></i>重新统计</button></c:if>
        </div>
    </div>
    <div class="portlet-body">
        <div class="row">
            <div class="col-md-12">
                <h3>月度</h3>
                <div class="actions form-inline">
                    <div class="form-group">
                        时间：<input class="form-control js_ym_select input-sm" value="" role="month">
                    </div>
                    <div class="form-group"><button class="btn btn-sm green js_search" data-action="month"><i class="fa fa-search"></i> 查询 </button></div>
                </div>
                <table class="table table-striped table-bordered table-advance table-hover mar_t20" id="month_table_ajax">
                    <thead>
                    <tr role="row" class="heading">
                        <th width="5%">板块</th>
                        <th width="5%">期末人数</th>
                        <th width="5%">当期总学时</th>
                        <th width="5%">人均学时</th>
                    </tr>
                    </thead>
                    <tbody>

                    </tbody>
                </table>
            </div>
            <div class="col-md-12">
                <h3>季度</h3>
                <div class="actions form-inline">
                    <div class="form-group">
                        年份：<input class="form-control js_ym_select input-sm" value="" role="quarter">
                        季度：
                        <select class="form-control input-sm">
                            <option value="1">1</option>
                            <option value="2">2</option>
                            <option value="3">3</option>
                            <option value="4">4</option>
                        </select>
                    </div>
                    <div class="form-group"><button class="btn btn-sm green js_search" data-action="quarter"><i class="fa fa-search"></i> 查询 </button></div>
                </div>
                <table class="table table-striped table-bordered table-advance table-hover mar_t20" id="quarter_table_ajax">
                    <thead>
                    <tr role="row" class="heading">
                        <th width="5%">板块</th>
                        <th width="5%">期末人数</th>
                        <th width="5%">当期总学时</th>
                        <th width="5%">人均学时</th>
                    </tr>
                    </thead>
                    <tbody>


                    </tbody>
                </table>
            </div>
            <div class="col-md-12">
                <h3>年度</h3>
                <div class="actions form-inline">
                    <div class="form-group">
                        时间：<input class="form-control js_ym_select input-sm" value="" role="year">
                    </div>
                    <div class="form-group"><button class="btn btn-sm green js_search" data-action="year"><i class="fa fa-search"></i> 查询 </button></div>
                </div>
                <table class="table table-striped table-bordered table-advance table-hover mar_t20" id="year_table_ajax">
                    <thead>
                    <tr role="row" class="heading">
                        <th width="5%">板块</th>
                        <th width="5%">期末人数</th>
                        <th width="5%">当期总学时</th>
                        <th width="5%">人均学时</th>
                    </tr>
                    </thead>
                    <tbody>


                    </tbody>
                </table>
            </div>
        </div>

    </div>
</div>

<script type="text/javascript" src="<c:url value="/resources/plugIn/My97DatePicker/WdatePicker.js" />"></script>
<script>

    $(document).ready(function () {
        var build = function (url) {
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
                url : url,
                success: function (result) {
                    if (result.success){
                        window.location.reload();
                    }
                }
            });
        }

        $('input.js_ym_select').on('focus',function () {
            if ($(this).attr('role')=='month'){
                WdatePicker({dateFmt:'yyyy-MM'})
            }else if ($(this).attr('role')=='quarter'){
                WdatePicker({dateFmt:'yyyy'})
            }else {
                WdatePicker({dateFmt:'yyyy'})
            }

        });

        $('button.js_search').on('click',function () {
            var _this = $(this);
            var _action = _this.data('action');
            var value = _this.parents('div.actions').find('input.js_ym_select').val();
            if (value){
                var data = {};
                if(_action=='month'){
                    data = {yearMonth: value};
                }else if(_action=='quarter'){
                    var _option = _this.parents('div.actions').find('select').val();
                    data = {year: value,quarter:_option};
                }else{
                    data = {year: value};
                }
                $.post('<c:url value="/hr/train/getTrainStatisticsOne"/>', data, function (result) {
                    if (result.success) {
                        if (result.rdata.length>0){
                            var _table = _this.parents('div.actions').next('table');
                            _table.find('tbody').empty();
                            var _arr = ['plate','empSum','studyTimeSum','perStudyTime'];
                            var _tr = buildTr(result.rdata,_arr);
                            _table.find('tbody').append(_tr);
                        }else{
                            var _table = _this.parents('div.actions').next('table');
                            _table.find('tbody').empty();
                            var _tr =$('<tr>');
                            _tr.append($('<td>').attr('colspan',5).text("没有数据"))
                            _table.find('tbody').append(_tr);
                        }
                    }
                }, 'json');
            }
        });

        var buildTr=function (entitys,_arr) {
            var trs=new Array();
            for (var i in entitys){
                var _tr = $('<tr>');
                var o;
                for (o in _arr){
                    if (_arr[o]=='perStudyTime'){
                        var  studyTimeSum = entitys[i]['studyTimeSum'];
                        var  empSum = entitys[i]['empSum'];
                        _tr.append($('<td>').text((studyTimeSum/empSum).toFixed(2)));
                    }else{
                        _tr.append($('<td>').text(entitys[i][_arr[o]]));
                    }
                }
                trs.push(_tr);
            }
            return trs;
        }

        $('button.js_build').on('click',function () {
            var _index = layer.confirm("是否立即统计当前培训情况？",function () {
                layer.close(_index);
                build('<c:url value="/hr/train/trainStatistics"/>');
            });
        });



    });

</script>
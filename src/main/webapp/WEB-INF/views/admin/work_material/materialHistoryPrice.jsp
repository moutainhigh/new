<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<link rel="stylesheet" type="text/css" href="<c:url value="/resources/admin/global/plugins/bootstrap-datepicker/css/bootstrap-datepicker3.min.css" />"/>


<style>
    td {
        text-align:center;
    }
</style>

<div class="portlet light">
    <div class="portlet-title">
        <div class="caption">
            <i class="icon-layers font-green"></i>
            <span class="caption-subject font-green sbold uppercase">分项分类历史价格</span>
        </div>
    </div>
    <div class="actions form-inline">
        <div class="form-group">
            <label for="startTime" class="col-md-4 control-label" style="position: relative;top: 4px;left: 5px;">开始时间:</label>
            <div class="input-group date form_date col-md-8" data-date="" data-date-format="yyyy-mm-dd" data-link-field="startTime" data-link-format="yyyy-mm-dd">
                <input class="form-control" id="startTime" name="startTime" size="16" type="text" value="${param.startTime}" readonly>
                <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
            </div>
        </div>
        <div class="form-group">
            <label for="endTime" class="col-md-4 control-label">结束时间:</label>
            <div class="input-group date form_date col-md-8" data-date="" data-date-format="yyyy-mm-dd" data-link-field="endTime" data-link-format="yyyy-mm-dd">
                <input class="form-control" id="endTime" name="endTime" size="16" type="text" value="${param.endTime}" readonly>
                <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
            </div>
        </div>
        <button type="button" class="btn btn-default js_search"><i class="fa fa-search"></i>搜索</button>
        <a href="<c:url value='/admin/workMaterialPrice/materialChartTable/${id}'/>" class="btn btn-outline grey-salsa" >图表</a>
    </div>
    <div class="portlet-body">
        <div class="row">
            <div class="col-md-8">
                <table class="table table-width table-striped table-bordered table-advance table-hover table-checkable " id="datatable_ajax">
                    <thead>
                    <tr role="row" class="heading" align="center">
                        <th colspan="2">分项分类名称：${material.name} 特征描述： ${material.specification}</th>
                    </tr>
                    <tr role="row" class="heading" align="center">
                        <th>时间</th>
                        <th>价格</th>
                    </tr>
                    </thead>
                </table>
                <a type="button" class="btn default" href="<c:url value='/admin/workMaterialPrice/index'/>">返回列表</a>
            </div>
        </div>
    </div>
</div>




<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.zh-CN.js" />"></script>

<script src="<c:url value="/resources/admin/global/scripts/datatable.js" />"></script>
<script src="<c:url value="/resources/admin/global/plugins/datatables/datatables.min.js" />"></script>
<script src="<c:url value="/resources/admin/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js" />"></script>

<script>
    $(function () {
        var startTime,endTime;
        $('input[name=startTime]').datepicker({
            todayBtn : "linked",
            autoclose : true,
            language: 'zh-CN',
            format : "yyyy-mm-dd",
            todayHighlight : true
        }).on('changeDate',function(e){
            startTime = e.date.getTime();
            $('input[name=endTime]').datepicker('setStartDate',new Date(startTime));
            if (null!=startTime && null!=endTime){
                $('input[name=duration]').val(Math.floor((endTime-startTime)/((24*3600*1000)))+1);
            }
        });

        $('input[name=endTime]').datepicker({
            todayBtn : "linked",
            autoclose : true,
            language: 'zh-CN',
            format : "yyyy-mm-dd"
        }).on('changeDate',function(e){
            endTime = e.date.getTime();
            $('input[name=startTime]').datepicker('setEndDate',new Date(endTime));
            if (null!=startTime && null!=endTime){
                $('input[name=duration]').val(Math.floor((endTime-startTime)/((24*3600*1000)))+1);
            }
        });


        var TableDatatablesAjax = function() {
            var  e = function(args) {
                var table = new Datatable;
                for(var i in args){
                    table.setAjaxParam(i, args[i]);
                }
                table.init({
                    src: $("#datatable_ajax"),
                    loadingMessage: "Loading...",
                    dataTable: {
                        bStateSave: !0,
                        lengthMenu: [[10, 20, 50, 100], [10, 20, 50, 100]],
                        searching:true,
                        pageLength: 10,
                        ajax: {
                            url: "<c:url value="/admin/workMaterialPrice/historyListData"/>"
                        },
                        order: [[1, "asc"]],
                        columns:[
                            {"data":"historyPriceTime","render" :function(data, type, full, meta ){
                                return data ==undefined?'':data;
                            }},
                            {"data":"price","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }}
                        ],
                        "initComplete":function () {
                        }
                    }
                });
                return table;
            };
            return {
                init: function(args) {
                    return e(args);
                }
            }
        } ();

        var table = TableDatatablesAjax.init({id:'${id}'});

        $('button.js_search').click(function () {
            table.clearAjaxParams();
            table.setAjaxParam('id','${id}');
            table.setAjaxParam('startTime',$('input[name=startTime]').val().trim());
            table.setAjaxParam('endTime',$('input[name=endTime]').val().trim());
            table.setAjaxParam('start',0);
            table.getDataTable().ajax.reload();
        });


    });
</script>
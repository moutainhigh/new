<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<link rel="stylesheet" type="text/css" href="<c:url value="/resources/admin/global/plugins/bootstrap-select/css/bootstrap-select.min.css" />" media="screen"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/admin/global/plugins/bootstrap-datepicker/css/bootstrap-datepicker3.min.css" />"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/hr/css/hr.css" />"/>

<style>
    .bootstrap-select{
        width:auto !important;
    }
</style>

<div class="portlet light">
    <div class="portlet-title">
        <div class="caption">
            <i class="icon-layers font-green"></i>
            <span class="caption-subject font-green sbold uppercase">招聘计划统计</span>
        </div>
    </div>

    <div class="portlet-body">
        <form class="navbar-form" name="search" role="search" id="materialSearchForm">
            <div class="form-group">
                招聘需求提出日期：
                <input name="startDate" class="form-control input-inline input-sm" type="text" value=""/>
                - <input name="endDate" class="form-control input-inline input-sm" type="text" value=""/>
            </div>

            <div class="form-group">
                <button type="button"  class="btn btn-default js_search">搜索</button>
                <button type="button" class="btn btn-default js_reset">重置</button>
                <button type="button" class="btn btn-default js_download">导出</button>
            </div>
        </form>

        <div class="row">
            <div class="col-md-12">
                <table class="table table-width table-striped table-bordered table-advance table-hover table-checkable th-weight" id="datatable_ajax">
                    <thead>
                    <tr role="row">
                        <th>序号</th>
                        <th>板块</th>
                        <th>空缺人数</th>
                        <th>填补人数</th>
                        <th>尚空缺人数</th>
                    </tr>
                    </thead>
                </table>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/datatables/datatables.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/scripts/datatable.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.zh-CN.js" />"></script>

<script>

    $(function () {

        $('input[name=startDate]').datepicker({
            todayBtn : "linked",
            autoclose : true,
            language: 'zh-CN',
            format : "yyyy-mm-dd",
            todayHighlight : true
        }).on('changeDate',function(e){
            var startTime = e.date;
            $('input[name=endDate]').datepicker('setStartDate',startTime);
        });

        $('input[name=endDate]').datepicker({
            todayBtn : "linked",
            autoclose : true,
            language: 'zh-CN',
            format : "yyyy-mm-dd",
            todayHighlight : true
        }).on('changeDate',function(e){
            var endTime = e.date;
            $('input[name=startDate]').datepicker('setEndDate',endTime);
        });

        var TableDatatablesAjax = function() {
            var  e = function() {
                var table = new Datatable;
                table.init({
                    src: $("#datatable_ajax"),
                    loadingMessage: "Loading...",
                    dataTable: {
                        dom: "<'table-responsive't><'row'>",
                        bStateSave: !0,
                        lengthMenu: [[10, 20, 50, 100], [10, 20, 50, 100]],
                        searching:true,
                        pageLength: 10,
                        ajax: {
                            url: "<c:url value="/hr/recruitmentPlan/statisticsData"/>"
                        },
                        order: [[1, "asc"]],
                        columns:[
                            {"data":"id","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"plat","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"vacancyCount","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"fileCount","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"vacancyCount2","render" : function(data, type, full, meta ){

                                return parseInt(full.vacancyCount) - parseInt(full.fileCount);
                            }}
                        ],fnDrawCallback: function(){
                            var api = this.api();
                            var startIndex= api.context[0]._iDisplayStart;//获取到本页开始的条数
                            api.column(0).nodes().each(function(cell, i) {
                                cell.innerHTML = startIndex + i + 1;
                            });
                        }
                    }
                });
                return table;
            };
            return {
                init: function() {
                    return e();
                }
            }
        } ();


        var table = TableDatatablesAjax.init();


        $('button.js_search').click(function () {
            table.clearAjaxParams();
            table.setAjaxParam('startDate',$('input[name=startDate]').val());
            table.setAjaxParam('endDate',$('input[name=endDate]').val());
            table.setAjaxParam('start',0);
            table.getDataTable().ajax.reload();
        });

        $('button.js_reset').click(function () {
            table.clearAjaxParams();
            table.setAjaxParam('start',0);
            $('input[name=startDate]').val('');
            $('input[name=endDate]').val('');
            table.getDataTable().ajax.reload();
        });

        $('button.js_download').on('click',function () {
            $('#downloadForm').remove();
            var _form = $('<form>');
            _form.attr('action','<c:url value="/hr/recruitmentPlan/downloadPlanStatisticsData"/>');
            _form.attr('id','downloadForm');
            _form.attr('method','post');
            _form.attr('target', '_blank');
            var _input = $('<input type="text" name="startDate" />').val($('input[name=startDate]').val());;
            _form.append(_input);
            _input = $('<input type="text" name="endDate" />').val($('input[name=endDate]').val());
            _form.append(_input);
            $(document).find('body').append(_form);
            $('#downloadForm').submit();
        });

    });
</script>
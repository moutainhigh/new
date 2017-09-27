<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
            <span class="caption-subject font-green sbold uppercase">招聘计划</span>
        </div>
        <div class="actions form-inline">
            <a href="<c:url value="/hr/recruitmentPlan/edit"/>"><button class="btn blue"><i class="fa fa-plus-circle"></i>新增</button></a>
        </div>
    </div>
    <div class="portlet-body">
    <form class="navbar-form" role="search">
        <div class="form-group">
            申请招聘日期：
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
                    <tr role="row" class="heading">
                        <th>序号</th>
                        <th>单位</th>
                        <th>部门</th>
                        <th>职位名称</th>
                        <th>职级</th>
                        <th>空缺人数</th>
                        <th>申请招聘日期</th>
                        <th>拟完成日期</th>
                        <th>填补人数</th>
                        <th>用人部<br>门需求</th>
                        <th>目前进<br>展情况</th>
                        <th>填补人员姓名</th>
                        <th>尚空缺<br>人数</th>
                        <th>拟入职日期</th>
                        <th>已入职日期</th>
                        <th>确定年度<br>薪酬（元）</th>
                        <th>直接汇报上<br>级岗位名称</th>
                        <th>相关操作</th>
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
                        bStateSave: !0,
                        lengthMenu: [[10, 20, 50, 100], [10, 20, 50, 100]],
                        searching:true,
                        pageLength: 10,
                        ajax: {
                            url: "<c:url value="/hr/recruitmentPlan/listData"/>"
                        },
                        order: [[1, "asc"]],
                        columns:[
                            {"data":"id"},
                            {"data":"companyName","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"departmentName","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"jobName","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"dutyLevelName","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"vacancyCount","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"askDate","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"overDate","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"fileCount","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"requirement","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"progress","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"fileName","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"vacancyCount2","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"planJoinDate","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"overJoinDate","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"payment","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"reportJobName","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"id","render" : function(data, type, full, meta ){
                                var editUrl = "<c:url value='/hr/recruitmentPlan/edit/'/>"+data;
                                return  '<a href="'+editUrl+'" class="btn btn-sm btn-outline grey-salsa" style="width: 45%" ><i class="fa fa-edit"></i> 编辑</a>'+
                                    '<button class="btn btn-sm btn-outline grey-salsa"  target="_blank" style="width: 45%" data-id="'+data+'"><i class="fa fa-close"></i> 删除</button>';
                            }}
                        ],"initComplete":function () {

                        },fnDrawCallback: function(){
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

        $('#datatable_ajax').on('click','button',function () {
            var _id = $(this).data('id');
            var _specificationId = $(this).data('specification');
            layer.confirm("确定要删除？",function (_index) {
                layer.close(_index);
                $.post('<c:url value="/hr/recruitmentPlan/delete"/>', {id:_id}, function (result) {
                    if (result.success) {
                        layer.msg('操作成功',{time:1000},function () {
                            table.getDataTable().ajax.reload();
                        });
                    }else {
                        layer.msg('删除失败！：'+result.rmsg,{time:2000});
                    }
                }, 'json');
            });
        });

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
            _form.attr('action','<c:url value="/hr/recruitmentPlan/downloadData"/>');
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
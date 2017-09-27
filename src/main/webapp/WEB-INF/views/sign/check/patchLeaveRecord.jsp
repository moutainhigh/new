<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<link rel="stylesheet" type="text/css" href="<c:url value="/resources/admin/global/plugins/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" />" />

<div class="portlet light">
    <div class="portlet-title">
        <div class="caption">
            <i class="icon-layers font-green"></i>
            <span class="caption-subject font-green sbold uppercase">请假信息补录</span>
        </div>
        <div class="actions form-inline">
            <a href="<c:url value="/admin/sign/toleaveEditPage"/>"><button class="btn green js_post_instore"><i class="fa fa-send"></i>新增请假</button></a>
        </div>
    </div>

    <form class="navbar-form navbar-left" role="search" id="materialSearchForm">
        <div class="form-group">
            日期：
            <div class="input-group date form_date" data-date="" data-date-format="yyyy-mm-dd" data-link-field="startTime" data-link-format="yyyy-mm-dd">
                <input class="form-control" name="startTime" size="16" type="text" placeholder="开始时间">
            </div>
            -
            <div class="input-group date form_date" data-date="" data-date-format="yyyy-mm-dd" data-link-field="endTime" data-link-format="yyyy-mm-dd">
                <input class="form-control" name="endTime" size="16" type="text" placeholder="结束时间">
            </div>
        </div>
        <div class="form-group">
            <button type="button" class="btn btn-default js_search">搜索</button>
            <button type="button" class="btn btn-default js_reset">重置</button>
        </div>
    </form>

    <div id="myTabContent" class="tab-content">
        <div class="tab-pane fade in active" id="home">
            <div class="portlet-body">
                <div class="row">
                    <div class="col-md-12">
                        <table class="table table-width table-striped table-bordered table-advance table-hover table-checkable " id="datatable_ajax">
                            <thead>
                            <tr role="row" class="heading">
                                <th width="2%">
                                    序号
                                </th>
                                <th width="4%">姓名</th>
                                <th width="4%">单位</th>
                                <th width="4%">部门</th>
                                <th width="4%">职位</th>
                                <th width="4%">身份证号码</th>
                                <th width="4%">手机</th>
                                <th width="4%">请假类型</th>
                                <th width="4%">开始日期</th>
                                <th width="4%">结束日期</th>
                                <th width="4%">请假天数</th>
                                <th width="5%">操作</th>
                            </tr>
                            </thead>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>


</div>

<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.zh-CN.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/scripts/datatable.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/datatables/datatables.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js" />"></script>

<script>
    $(function () {

        //设置开始时间
        $('input[name=startTime]').datetimepicker({
            format:"yyyy-mm-dd",
            language:  'zh-CN',
            weekStart: 1,
            todayBtn:  1,
            autoclose: 1,
            todayHighlight: 1,
            startView: 2,
            minView: 2,
            forceParse: 0
        });

        $('input[name=endTime]').datetimepicker({
            format:"yyyy-mm-dd",
            language:  'zh-CN',
            weekStart: 1,
            todayBtn:  1,
            autoclose: 1,
            todayHighlight: 1,
            startView: 2,
            minView: 2,
            forceParse: 0
        });

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
                        url: "<c:url value="/admin/sign/listLeavePatchData"/>"
                    },
                    order: [[1, "asc"]],
                    columns:[
                        {"data":"id", "targets": 0},
                        {"data":"username","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;
                        }},
                        {"data":"company","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;
                        }},
                        {"data":"department","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;
                        }},
                        {"data":"position","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;
                        }},
                        {"data":"idCard","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;
                        }},
                        {"data":"telephone","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;
                        }},
                        {"data":"leaveType","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;
                        }},
                        {"data":"startLeave","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;
                        }},
                        {"data":"endLeave","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;
                        }},
                        {"data":"leaveDayCount","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;
                        }},

                        {"data":"id","render" : function(data, type, full, meta ){
                            var editLeave = "<c:url value='/admin/sign/toLeaveEditPage/'/>"+data;
                            return '<a href="'+editLeave+'" class="btn btn-sm btn-outline grey-salsa" ><i class="fa fa-edit"></i> 补录</a>';
                        }}
                    ],fnDrawCallback: function(){
                        var api = this.api();
                        var startIndex= api.context[0]._iDisplayStart;//获取到本页开始的条数
                        api.column(0).nodes().each(function(cell, i) {
                            cell.innerHTML = startIndex + i + 1;
                        });
                    },
                    "initComplete":function () {
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

    $(document).ready(function() {
        var table = TableDatatablesAjax.init();

        $('button.js_search').click(function () {
            table.clearAjaxParams();
            table.setAjaxParam('startDate',$('input[name=startTime]').val().trim());
            table.setAjaxParam('endDate',$('input[name=endTime]').val().trim());
            table.getDataTable().ajax.reload();
        });


        $('button.js_reset').click(function () {
            table.clearAjaxParams();
            $('input[name=outStorageNo]').val('');
            $('input[name=startTime]').val('');
            $('input[name=endTime]').val('');
            table.setAjaxParam('start',0);
            table.getDataTable().ajax.reload();
        });

    });
</script>
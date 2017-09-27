<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<link rel="stylesheet" type="text/css" href="<c:url value="/resources/admin/global/plugins/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" />" />

<div class="portlet light">
    <div class="portlet-title">
        <div class="caption">
            <i class="icon-layers font-green"></i>
            <span class="caption-subject font-green sbold uppercase">调价列表</span>
        </div>
        <div class="actions form-inline">
            <a href="<c:url value="/admin/inventory/statistics/balanceEdit"/>" target="_blank" class="btn btn-primary">调价</a>
        </div>
    </div>

    <form class="navbar-form navbar-left" role="search" id="materialSearchForm">
        <div class="form-group">
            <select class="form-control" name="repositoryId">
                <option value="">请选择</option>
                <c:forEach items="${repositories}" var="r" varStatus="step">
                    <option value="${r.id}">${r.name}</option>
                </c:forEach>
            </select>
        </div>
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
                                <th width="4%">分类</th>
                                <th width="4%">材料名称</th>
                                <th width="4%">规格</th>
                                <th width="4%">总金额</th>
                                <th width="4%">税额</th>
                                <th width="4%">时间</th>
                                <th width="5%">相关操作</th>
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
                        url: "<c:url value="/admin/inventory/statistics/balanceListData"/>"
                    },
                    order: [[1, "asc"]],
                    columns:[
                        {"data":"id", "targets": 0},
                        {"data":"categoryName","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;
                        }},
                        {"data":"materialName","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;
                        }},
                        {"data":"specification","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;
                        }},
                        {"data":"sumPrice","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;
                        }},
                        {"data":"sumTax","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;
                        }},
                        {"data":"balanceDate","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;
                        }},
                        {"data":"id","render" : function(data, type, full, meta ){
                            var showDetail = "<c:url value='/admin/inventory/statistics/balanceEdit/'/>"+data;
                            return '<a href="'+showDetail+'" class="btn btn-sm btn-outline grey-salsa" ><i class="fa fa-edit"></i> 编辑</a>';
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
            table.setAjaxParam('repositoryId',$('select[name=repositoryId]').val().trim());
            table.setAjaxParam('startTime',$('input[name=startTime]').val().trim());
            table.setAjaxParam('endTime',$('input[name=endTime]').val().trim());
            table.getDataTable().ajax.reload();
        });


        $('button.js_reset').click(function () {
            table.clearAjaxParams();
            $('input[name=outStorageNo]').val('');
            $('select[name=checkUser] option:eq(0)').prop('selected',true);
            $('select[name=itemReceiver] option:eq(0)').prop('selected',true);
            $('select[name=repositoryId] option:eq(0)').prop('selected',true);
            $('input[name=startTime]').val('');
            $('input[name=endTime]').val('');
            $('input[name=itemReceiver]').val('');
            table.setAjaxParam('start',0);
            table.getDataTable().ajax.reload();
        });

    });
</script>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<link rel="stylesheet" type="text/css" href="<c:url value="/resources/admin/global/plugins/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" />" media="screen"/>
<link type="text/css" href="/resources/admin/global/plugins/select2-4.0.3/css/select2.min.css"  rel="stylesheet">

<div class="portlet light">
    <div class="portlet-title">
        <div class="caption">
            <i class="icon-layers font-green"></i>
            <span class="caption-subject font-green sbold uppercase">入库列表</span>
        </div>
    </div>
    <form class="navbar-form navbar-left" role="search">
        <div class="form-group">
            <input type="text" id="inStorageNum" name="inStorageNum" class="form-control"  placeholder="入库单号">
        </div>

        <div class="form-group">
            <input type="text" id="billNum" name="billNum" class="form-control"  placeholder="发票号">
        </div>

        <div class="form-group">
            <label>供货商：</label>
            <select class="form-control js_person" name="itemProviderName">
                <option value="">全部</option>
                <c:forEach items="${person2}" var="p">
                    <option value="${p.name}" >${p.name}</option>
                </c:forEach>
            </select>
        </div>

        <div class="form-group">
            <select class="form-control" name="repositoryId" placeholder="仓库名称">
                <option value="-1">请选择库房</option>
                <c:forEach items="${repositories}" var="r">
                    <option value="${r.id}">${r.name}</option>
                </c:forEach>
            </select>
        </div>

        <div class="form-group">
            <input type="text" name="materialName" class="form-control" value="" placeholder="材料名称">
        </div>
        <div class="form-group">
            <input type="text"  name="specification" class="form-control" value="" placeholder="规格">
        </div>

        <div class="form-group">
            <div class="input-group date form_date" data-date="" data-date-format="yyyy-mm-dd" data-link-field="startTime" data-link-format="yyyy-mm-dd">
                <input class="form-control" id="startTime" name="startTime" size="16" type="text" placeholder="开始时间">
            </div>
        </div>

        <div class="form-group">
            <div class="input-group date form_date" data-date="" data-date-format="yyyy-mm-dd" data-link-field="endTime" data-link-format="yyyy-mm-dd">
                <input class="form-control" id="endTime" name="endTime" size="16" type="text" placeholder="结束时间">
            </div>
        </div>
            <div class="form-group">
                <button type="button" id="buttonSearch1" class="btn btn-default">搜索</button>
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
                                <th width="4%">入库单号</th>
                                <th width="4%">入库日期</th>
                                <th width="4%">发票号</th>
                                <th width="4%">供货</th>
                                <th width="4%">库房名称</th>
                                <th width="4%">库管员</th>
                                <th width="4%">备注</th>
                                <th width="5%">收票人</th>
                                <th width="5%">收票日期</th>
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

<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/select2-4.0.3/js/select2.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.zh-CN.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/scripts/datatable.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/datatables/datatables.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js" />"></script>

<script>
    $(function () {

        $('select.js_person').select2();

        //设置开始时间
        $('#startTime').datetimepicker({
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

        $('#endTime').datetimepicker({
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
                        url: "<c:url value="/admin/inventory/inStorage/listData"/>"
                    },
                    order: [[1, "asc"]],
                    columns:[
                        {"data":"id","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;                        }},
                        {"data":"inStorageNum","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;
                        }},
                        {"data":"inStoreDate","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;
                        }},
                        {"data":"billNum","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;
                        }},
                        {"data":"itemProviderName","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;
                        }},
                        {"data":"repositoryName","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;
                        }},
                        {"data":"repositoryPersonName","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;
                        }},
                        {"data":"remark","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;
                        }},
                        {"data":"billReceiver","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;
                        }},
                        {"data":"billReceiveDate","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;
                        }},
                        {"data":"id","render" : function(data, type, full, meta ){
                            var showDetail = "<c:url value='/admin/inventory/InStorage/itemInStorageDetail/'/>"+data;
                            return '<a href="'+showDetail+'" class="btn btn-sm btn-outline grey-salsa" ><i class="fa fa-search-minus"></i> 查看</a>'+
                                '<button type="button" class="btn btn-sm btn-outline grey-salsa js_receive_bill"  data-id="'+data+'"><i class="fa fa-hand-paper-o"></i>收票</button>';
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
        /**
         * 收票
         */
        $('#datatable_ajax').on('click','button.js_receive_bill',function () {
            var _id = $(this).data('id');
            layer.open({
                type: 2,
                title: "",
                area: ['70%','60%'],
                content: '<c:url value="/admin/inventory/toReceiveBill/detailIndex/2/" />'+_id,
                end: function () {
                    table.getDataTable().draw( false);
                }
            });
        });

        $('#buttonSearch1').click(function () {
            table.clearAjaxParams();
            table.setAjaxParam('inStorageNum',$('#inStorageNum').val().trim());
            table.setAjaxParam('billNum',$('#billNum').val().trim());
            table.setAjaxParam('itemProviderName',$('select[name=itemProviderName]').val());
            table.setAjaxParam('startTime',$('#startTime').val().trim());
            table.setAjaxParam('endTime',$('#endTime').val().trim());
            table.setAjaxParam('repositoryId',$('select[name=repositoryId]').val());
            table.setAjaxParam('materialName',$('input[name=materialName]').val().trim());
            table.setAjaxParam('specification',$('input[name=specification]').val().trim());
            table.getDataTable().ajax.reload();
        });

        $('button.js_reset').click(function () {
            table.clearAjaxParams();
            $('input[name=inStorageNum]').val('');
            $('input[name=billNum]').val('');
            $('input[name=startTime]').val('');
            $('input[name=endTime]').val('');
            $('select[name=itemProviderName] option:eq(0)').prop('selected',true);
            $('select[name=repositoryId] option:eq(0)').prop('selected',true);
            $('input[name=materialName]').val('');
            $('input[name=specification]').val('');
            table.setAjaxParam('start',0);
            table.getDataTable().ajax.reload();
        });

    });
</script>
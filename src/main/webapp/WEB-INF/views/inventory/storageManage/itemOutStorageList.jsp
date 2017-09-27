<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<link type="text/css" href="/resources/admin/global/plugins/select2-4.0.3/css/select2.min.css"  rel="stylesheet">
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/admin/global/plugins/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" />" />

<div class="portlet light">
    <div class="portlet-title">
        <div class="caption">
            <i class="icon-layers font-green"></i>
            <span class="caption-subject font-green sbold uppercase">出库列表</span>
        </div>
        <div class="actions form-inline">
            <a href="<c:url value="/admin/inventory/outStorage/edit"/>"><button class="btn blue"><i class="fa fa-plus-circle"></i>新增</button></a>
        </div>
    </div>

    <form class="navbar-form navbar-left" role="search">

        <div class="form-group">
            <input type="text" name="outStorageNo" class="form-control"  placeholder="出库单号">
        </div>
        <div class="form-group">
            <select class="form-control" name="checkUser">
                <option value="-1">库管员</option>
                <c:forEach items="${person1}" var="p">
                    <option value="${p.name}">${p.name}</option>
                </c:forEach>
            </select>
        </div>
        <div class="form-group">
            <select class="form-control" name="itemReceiver">
                <option value="-1">领用人</option>
                <c:forEach items="${person7}" var="p">
                    <option value="${p.name}">${p.name}</option>
                </c:forEach>
            </select>
        </div>

        <div class="form-group">
            <select class="form-control" name="repositoryId">
                <option value="-1">选择仓库</option>
                <c:forEach items="${repositories}" var="r" varStatus="step">
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
                                <th width="4%">出库单号</th>
                                <th width="4%">出库日期</th>
                                <th width="4%">项目</th>
                                <th width="4%">栋号</th>
                                <th width="4%">库房名称</th>
                                <th width="4%">库管员</th>
                                <th width="4%">备注</th>
                                <th width="4%">收票人</th>
                                <th width="4%">收票时间</th>
                                <th width="6%">相关操作</th>
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

        $('select[name=itemReceiver]').select2();

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
                        url: "<c:url value="/admin/inventory/outStorage/listData"/>"
                    },
                    order: [[1, "asc"]],
                    columns:[
                        {"data":"id", "targets": 0},
                        {"data":"outStorageNo","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;
                        }},
                        {"data":"outStorageDate","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;
                        }},
                        {"data":"projectName","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;
                        }},
                        {"data":"projectHouseNum","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;
                        }},
                        {"data":"repositoryName","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;
                        }},
                        {"data":"checkUser","render" : function(data, type, full, meta ){
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
                            var showDetail = "<c:url value='/admin/inventory/outStorage/detail/'/>"+data;
                            return  '<a href="'+showDetail+'" class="btn btn-sm btn-outline grey-salsa" ><i class="fa fa-search-minus"></i> 查看</a>'+
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
                area: [ '70%','60%'],
                content: '<c:url value="/admin/inventory/toReceiveBill/detailIndex/1/" />'+_id,
                end: function () {
                   table.getDataTable().draw( false);
                }
            });
        });

        /**
         * 搜索
         */
        $('button.js_search').click(function () {
            table.clearAjaxParams();
            table.setAjaxParam('outStorageNo',$.trim($('input[name=outStorageNo]').val()));
            table.setAjaxParam('checkUser',$('select[name=checkUser]').val());
            table.setAjaxParam('itemReceiver',$('select[name=itemReceiver]').val());
            table.setAjaxParam('materialName',$.trim($('input[name=materialName]').val()));
            table.setAjaxParam('specification',$.trim($('input[name=specification]').val()));
            table.setAjaxParam('repositoryId',$('select[name=repositoryId]').val());
            table.setAjaxParam('startTime',$.trim($('input[name=startTime]').val()));
            table.setAjaxParam('endTime',$.trim($('input[name=endTime]').val()));
            table.getDataTable().ajax.reload();
        });


        $('button.js_reset').click(function () {
            table.clearAjaxParams();
            $('input[name=outStorageNo]').val('');
            $('select[name=checkUser] option:eq(0)').prop('selected',true);
            $('select[name=itemReceiver] option:eq(0)').prop('selected',true);
            $('select[name=repositoryId] option:eq(0)').prop('selected',true);
            $('input[name=materialName]').val('');
            $('input[name=specification]').val('');
            $('input[name=startTime]').val('');
            $('input[name=endTime]').val('');
            $('input[name=itemReceiver]').val('');
            table.setAjaxParam('start',0);
            table.getDataTable().ajax.reload();
        });

    });
</script>
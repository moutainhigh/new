<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<link rel="stylesheet" type="text/css" href="<c:url value="/resources/admin/global/plugins/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" />" media="screen"/>

<div class="portlet light">
    <div class="portlet-title">
        <div class="caption">
            <i class="icon-layers font-green"></i>
            <span class="caption-subject font-green sbold uppercase">退货列表</span>
        </div>
    </div>
    <form class="navbar-form navbar-left" role="search" id="materialSearchForm">
        <div class="form-group">
            <input type="text" name="materialName" class="form-control" value="" placeholder="材料名称">
        </div>
        &nbsp;
        <div class="form-group">
            日期：<input name="startTime" class="form-control input-inline input-sm js_date" type="text" value=""  placeholder="开始日期"/>- <input name="endTime" class="form-control input-inline input-sm js_date" type="text" placeholder="结束日期"/>
        </div>
        <button type="button" class="btn btn-default js_reset">重置</button>
        <button type="button"  class="btn btn-default js_search">搜索</button>

    </form>
    <div class="portlet-body">
        <div class="row">
            <div class="col-md-12">
                <table class="table table-width table-striped table-bordered table-advance table-hover table-checkable " id="datatable_ajax">
                    <thead>
                    <tr role="row" class="heading">
                        <th>
                            <label class="mt-checkbox mt-checkbox-single mt-checkbox-outline">
                                <input type="checkbox" class="group-checkable">
                            </label>
                        </th>
                        <th>材料名称</th>
                        <th>规格</th>
                        <th>计量单位</th>
                        <th>仓库</th>
                        <th>退货人</th>
                        <th>退货日期</th>
                        <th>退货数量</th>
                        <th>退货单价</th>
                        <th>退货金额</th>
                        <th>税额</th>
                        <th>不含税总额</th>
                        <th>退货原因</th>
                    </tr>
                    </thead>
                </table>
            </div>
        </div>
    </div>
</div>

<div class="modal fade js_tree_div " data-backdrop="static">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title">材料分类</h4>
            </div>
            <div class="modal-body">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-md-4">
                            <label>材料分类</label>
                            <ul id="categoryTree" class="ztree"></ul>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary js_save_btn">确定</button>
            </div>
        </div>
    </div>
</div>


<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/datatables/datatables.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/scripts/datatable.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.zh-CN.js" />"></script>

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
                            url: "<c:url value="/admin/inventory/rejected/rejectedListData"/>"
                        },
                        order: [[1, "asc"]],
                        columns:[
                            {"data":"id","render" : function(data, type, full, meta ){
                                return '<label class="mt-checkbox mt-checkbox-single mt-checkbox-outline"> <input type="checkbox" class="checkboxes js_checkBox" value="'+data+'" data-provider="'+full.itemProviderName+'" data-repository="'+full.repositoryId+'"> </label>';
                            }},
                            {"data":"materialName","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"specification","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"unit","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"repositoryName","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"returnedUser","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"returnedDate","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"num","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"price","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"sumPrice","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"tax","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"excTaxSumPrice","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"reason","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
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


        var table = TableDatatablesAjax.init();

        $('button.js_search').click(function () {
            table.clearAjaxParams();
            table.setAjaxParam('materialName',$.trim($('input[name=materialName]').val()));
            table.setAjaxParam('startTime',$.trim($('input[name=startTime]').val()));
            table.setAjaxParam('endTime',$.trim($('input[name=endTime]').val()));
            table.getDataTable().ajax.reload();
        });

        $('button.js_reset').click(function () {
            table.clearAjaxParams();
            $('input[name=materialName]').val('');
            $('input[name=startTime]').val('');
            $('input[name=endTime]').val('');
            table.getDataTable().ajax.reload();
        });



});
</script>
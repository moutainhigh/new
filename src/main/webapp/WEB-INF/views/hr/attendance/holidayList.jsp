<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/hr/css/hr.css" />"/>
<div class="portlet light">
    <div class="portlet-title">
        <div class="caption">
            <i class="icon-layers font-green"></i>
            <span class="caption-subject font-green sbold uppercase">假日列表</span>
        </div>
    </div>
    <div class="portlet-body">
        <div class="row">
            <div class="col-md-12">
                <table class="table table-width table-striped table-bordered table-advance table-hover table-checkable " id="datatable_ajax">
                    <thead>
                    <tr role="row" class="heading">
                        <th width="3%">
                            <label class="mt-checkbox mt-checkbox-single mt-checkbox-outline">
                                <input type="checkbox" class="group-checkable">
                                <span></span>
                            </label>
                        </th>
                        <th width="5%">节假日名称</th>
                        <th width="5%">开始日期</th>
                        <th width="5%">结束日期</th>
                        <th width="5%">持续时间（天数）</th>
                        <th width="7%">操作</th>
                    </tr>
                    </thead>
                </table>
            </div>
        </div>
    </div>
</div>


<script src="<c:url value="/resources/admin/global/scripts/datatable.js" />"></script>
<script src="<c:url value="/resources/admin/global/plugins/datatables/datatables.min.js" />"></script>
<script src="<c:url value="/resources/admin/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js" />"></script>
<script>

    var TableDatatablesAjax = function() {
        var  e = function() {
            var table = new Datatable;
            table.init({
                src: $("#datatable_ajax"),
                loadingMessage: "Loading...",
                dataTable: {
                    bStateSave: !0,
                    lengthMenu: [[10, 20, 50, 100], [10, 20, 50, 100]],
                    pageLength: 10,
                    ajax: {
                        url: "<c:url value="/hr/atte/holidayListData"/>"
                    },
                    order: [[1, "asc"]],
                    columns:[
                        {"data":"id","render" : function(data, type, full, meta ){
                            return '<label class="mt-checkbox mt-checkbox-single mt-checkbox-outline"> <input type="checkbox" class="checkboxes" value="'+data+'"> <span></span> </label>';
                        }},
                        {"data":"name","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;
                        }},
                        {"data":"startTime","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;
                        }},
                        {"data":"endTime","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;
                        }},
                        {"data":"duration","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;
                        }},
                        {"data":"id","render" : function(data, type, full, meta ){
                            var editUrl = "<c:url value='/hr/atte/holidayEdit/'/>"+data;
                            return '<a href="'+editUrl+'" class="btn btn-sm btn-outline grey-salsa"  target="_blank"><i class="fa fa-edit"></i> 编辑</a>'+
                                '<button class="btn btn-sm btn-outline grey-salsa"  target="_blank" data-id="'+data+'"><i class="fa fa-close"></i> 删除</button>';
                        }}
                    ],
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
        var _index=null;
        $('#datatable_ajax').on('click','button',function () {
            var _id = $(this).data('id');
            _index = layer.confirm("确定要删除该假日？",function () {
                $.post('<c:url value="/hr/atte/deleteHoliday"/>', {id:_id}, function (result) {
                    if (result.success) {
                        layer.close(_index);
                        layer.msg('操作成功',{time:800},function () {
                            table.getDataTable().ajax.reload();
                        });
                    }else{
                        layer.close(_index);
                        layer.msg('操作失败：'+result.rmsg,{time:2000});
                    }
                }, 'json');
            });
        });

    });
</script>
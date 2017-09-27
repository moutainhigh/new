<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>



<div class="portlet light">
    <div class="portlet-title">
        <div class="caption">
            <i class="icon-layers font-green"></i>
            <span class="caption-subject font-green sbold uppercase">材料单位列表</span>
        </div>
    </div>
    <form class="navbar-form navbar-left" role="search" id="materialSearchForm">
        <div class="form-group">
            <input type="text" name="dictGroup" class="form-control" value="" placeholder="类型">
        </div>
        &nbsp;
        <div class="form-group">
            <input name="dictValue" class="form-control input-inline input-sm js_date" type="text" value=""  placeholder="单位名称"/>
        </div>
        <button type="button" class="btn btn-default js_reset">重置</button>
        <button type="button"  class="btn btn-default js_search">搜索</button>
        &nbsp;&nbsp;&nbsp;
        <a href="/admin/inventory/dict/edit" type="button"  class="btn btn-default">新增</a>

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
                        <th>类型</th>
                        <th>单位名称</th>
                        <th>操作</th>
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

<script>

$(function () {

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
                            url: "<c:url value="/admin/inventory/dict/dictListData"/>"
                        },
                        order: [[1, "asc"]],
                        columns:[
                            {"data":"id","render" : function(data, type, full, meta ){
                                return '<label class="mt-checkbox mt-checkbox-single mt-checkbox-outline"> <input type="checkbox" class="checkboxes js_checkBox" value="'+data+'" data-provider="'+full.itemProviderName+'" data-repository="'+full.repositoryId+'"> </label>';
                            }},
                            {"data":"dictGroup","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"dictValue","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"id","render" : function(data, type, full, meta ){
                                var updateDict = "<c:url value='/admin/inventory/dict/toUpdateDictPage/'/>"+data;
                                return '<button class="btn btn-sm btn-outline grey-salsa"  target="_blank" data-id="'+data+'"><i class="fa fa-close"></i> 删除</button>'+
                                    '<a href="'+updateDict+'" class="btn btn-sm btn-outline grey-salsa" ><i class="fa fa-update"></i>修改</a>';
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


        var table = TableDatatablesAjax.init();

        $('#datatable_ajax').on('click','button',function () {
            var _id = $(this).data('id');
            var _specificationId = $(this).data('specification');
            layer.confirm("确定要删除该单位？",function (_index) {
                layer.close(_index);
                $.post('<c:url value="/admin/inventory/dict/deleteDict"/>', {id:_id}, function (result) {
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
            table.setAjaxParam('dictGroup',$.trim($('input[name=dictGroup]').val()));
            table.setAjaxParam('dictValue',$.trim($('input[name=dictValue]').val()));
            table.getDataTable().ajax.reload();
        });

        $('button.js_reset').click(function () {
            table.clearAjaxParams();
            $('input[name=dictGroup]').val('');
            $('input[name=dictValue]').val('');
            table.getDataTable().ajax.reload();
        });



});
</script>
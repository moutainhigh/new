<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<style>
    #materialSearchForm .row{
        margin-bottom: 5px;
    }
</style>

<div class="portlet light">
    <div class="portlet-title">
        <div class="caption">
            <i class="icon-layers font-green"></i>
            <span class="caption-subject font-green sbold uppercase">苗木列表</span>
        </div>
        <div class="actions form-inline">
            <a href="<c:url value="/admin/priceSeedling/materialEdit"/>"><button class="btn blue"><i class="fa fa-plus-circle"></i>新增</button></a>
        </div>
    </div>
    <form class="navbar-form navbar-left" role="search" id="materialSearchForm">
        <div class="row">
            <div class="form-group">
                <input type="hidden" value="" name="categoryId">
                <button type="button" class="btn btn-primary js_choose_category">选择类别</button>
            </div>
            <div class="form-group">
                <input type="text" name="mcode" class="form-control" value="" placeholder="苗木编码">
            </div>
            <div class="form-group">
                <input type="text" name="name" class="form-control" value="" placeholder="苗木名称">
            </div>
            <div class="form-group">
                <input type="text"  name="specification" class="form-control" value="" placeholder="冠幅">
            </div>
            <div class="form-group">
                <input type="text"  name="specification2" class="form-control" value="" placeholder="米径">
            </div>
            <div class="form-group">
                <input type="text"  name="specification3" class="form-control" value="" placeholder="高度">
            </div>
        </div>
        <div class="row">
            <div class="form-group">
                <input type="text"  name="specification4" class="form-control" value="" placeholder="分枝点">
            </div>
            <div class="form-group">
                <input type="text"  name="specification5" class="form-control" value="" placeholder="树形及要求">
            </div>
            <div class="form-group">
                <input type="text"  name="specification6" class="form-control" value="" placeholder="土球直径">
            </div>
            <div class="form-group">
                <input type="text"  name="specification7" class="form-control" value="" placeholder="土球厚度">
            </div>
            <div class="form-group">
                <input type="text"  name="extend" class="form-control" value="" placeholder="苗木特点">
            </div>
            <div class="form-group">
                <input type="text"  name="extend2" class="form-control" value="" placeholder="种植密度标准">
            </div>

            <button type="button"  class="btn btn-default js_search">搜索</button>
            <button type="button" class="btn btn-default js_reset">重置</button>
            <button type="button" id="batchDelete" class="btn btn-default">批量删除</button>
        </div>
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

                        <th>苗木类别</th>
                        <th>苗木编码</th>
                        <th>苗木名称</th>
                        <th>冠幅</th>
                        <th>米径</th>
                        <th>高度</th>
                        <th>分枝点</th>
                        <th>树形及要求</th>
                        <th>土球直径</th>
                        <th>土球厚度</th>
                        <th>苗木特点</th>
                        <th>种植密度标准</th>
                        <th>单位</th>
                        <th width="10%">相关操作</th>
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
                <h4 class="modal-title">苗木类别</h4>
            </div>
            <div class="modal-body">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-md-4">
                            <label>苗木类别</label>
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


<script>

$(function () {

        var treeObj = null;

        var initTree = function (_arr,treeId,callback,settings) {
            var _setting = {callback:{
                onClick:function (event, treeId, treeNode) {
                    if ("function" == typeof(callback)){
                        callback(event,treeId,treeNode);
                    }
                },onCheck:function (event, treeId, treeNode) {
                    if ("function" == typeof(callback)){
                        callback(event,treeId,treeNode);
                    }
                }
            },data:{
                key:{
                    children:"list"
                }
            },view:{
                expandSpeed:''
            }};
            if ( typeof settings == "object"){
                for(var k in settings){
                    var _set = _setting[k];
                    if(_set==undefined){
                        _setting[k]=settings[k];
                    }else{
                        var tmp = settings[k];
                        if ( typeof tmp == "object") {
                            for (var i in tmp) {
                                _set[i] = tmp[i];
                            }
                        }
                    }
                }
            }
            return $.fn.zTree.init($('#'+treeId), _setting, _arr);
        }


        $('button.js_choose_category').on('click',function () {
            if(!treeObj){
                $.ajax({
                    async: false,
                    type : 'POST' ,
                    dataType:'json',
                    url : '<c:url value="/admin/priceSeedlingCategory/tree"/>',
                    success: function (result) {
                        treeObj = initTree(result, 'categoryTree', function (event, treeId, treeNode) {

                        });
                        var nodes = treeObj.getNodes();
                        treeObj.expandNode(nodes[0],true,false,false);
                    }
                });
            }
            $('div.js_tree_div').modal('show');
        });

        $('button.js_save_btn').on('click',function () {
            var nodes = treeObj.getSelectedNodes();
            if(nodes.length>0){
                $('input[name=categoryId]').val(nodes[0].id);
                $('div.js_tree_div').modal('hide');
            }else{
                layer.msg('请选择类别');
            }
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
                            url: "<c:url value="/admin/priceSeedling/listData"/>"
                        },
                        order: [[1, "asc"]],
                        columns:[
                            {"data":"id","render" : function(data, type, full, meta ){
                                return '<label class="mt-checkbox mt-checkbox-single mt-checkbox-outline"> <input type="checkbox" class="checkboxes js_checkBox" value="'+data+'"> </label>';
                            }},
                            {"data":"categoryName","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"mcode","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"name","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"specification","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"specification2","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"specification3","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"specification4","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"specification5","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"specification6","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"specification7","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"extend","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"extend2","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"unit","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"id","render" : function(data, type, full, meta ){
                                var editUrl = "<c:url value='/admin/priceSeedling/materialEdit/'/>"+data;
                                return '<a href="'+editUrl+'" class="btn btn-sm btn-outline grey-salsa" ><i class="fa fa-edit"></i> 编辑</a>'+
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


        var table = TableDatatablesAjax.init();

        $('#datatable_ajax').on('click','button',function () {
            var _id = $(this).data('id');
            layer.confirm("确定要删除该苗木？",function (_index) {
                layer.close(_index);
                $.post('<c:url value="/admin/priceSeedling/deleteOne"/>', {id:_id}, function (result) {
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
            var _arr = $('#materialSearchForm').serializeArray();
            for (var i in _arr){
                table.setAjaxParam(_arr[i].name,$.trim(_arr[i].value));
            }
            table.getDataTable().ajax.reload();
        });

        $('button.js_reset').click(function () {
            table.clearAjaxParams();
            var _arr = $('#materialSearchForm').serializeArray();
            for (var i in _arr){
                $('input[name='+_arr[i].name+']').val('');
            }
            table.getDataTable().ajax.reload();
        });



        /*批量删除*/
        $('#batchDelete').click(function () {
            var ids = new Array();
            $("input.js_checkBox:checked").each(function (k, v) {
                ids.push(v.value);
            });
            if (ids.length==0){
                layer.msg('请至少选择一行数据', {time: 1000});
                return;
            }
            layer.confirm("确定要删除这些苗木？",function (_index) {
                $.post("/admin/priceSeedling/delete",{"ids":ids.join(',')},function (result) {
                    layer.close(_index);
                    if ("true"==result.success) {
                        layer.msg('操作成功',{time:1000},function () {
                            table.getDataTable().ajax.reload();
                        });
                    }else if("notTrue"==result.success){
                        layer.msg('请选择没有关联价格的苗木！：'+result.rmsg,{time:3000});
                    }else {
                        layer.msg('删除失败！：'+result.rmsg,{time:3000});
                    }
                });
            });
        });

});
</script>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="portlet light">
    <div class="portlet-title">
        <div class="caption">
            <i class="icon-layers font-green"></i>
            <span class="caption-subject font-green sbold uppercase">材料价格列表</span>
        </div>
    </div>
    <div class="portlet-body">
        <div class="actions form-inline">
            <div class="form-group">
                <button type="button" class="btn btn-primary js_choose_category">选择分类</button>
                <input type="hidden" name="categoryId" value="">
            </div>
            <div class="form-group">
                <input type="text" name="mcode" class="form-control"  placeholder="材料编码">
            </div>
            <div class="form-group">
                <input type="text"  name="name" class="form-control" placeholder="材料名称">
            </div>
            <div class="form-group">
                <input type="text"  name="specification" class="form-control" value="" placeholder="规格">
            </div>
            <button type="button" class="btn btn-default js_search">搜索</button>
            <button type="button" class="btn btn-default js_reset">重置</button>
        </div>
        <table class="table table-width table-striped table-bordered table-advance table-hover table-checkable " id="datatable_ajax">
            <thead>
            <tr role="row" class="heading">
                <th width="4%">材料分类</th>
                <th width="4%">材料编码</th>
                <th width="4%">材料名称</th>
                <th width="4%">规格</th>
                <th width="4%">单位</th>
                <th width="4%">材料价格</th>
                <th width="4%">价格时间</th>
                <th width="5%">相关操作</th>
            </tr>
            </thead>
        </table>
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


<script type="text/javascript" src="<c:url value="/resources/admin/global/scripts/datatable.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/datatables/datatables.min.js" />"></script>
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
                    url : '<c:url value="/admin/materialCategory/tree"/>',
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
                layer.msg('请选择分类');
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
                            url: "<c:url value="/admin/material/listData"/>",
                            type:'POST'
                        },
                        order: [[1, "asc"]],
                        columns:[
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
                            {"data":"unit","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"price","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"newTime","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"id","render" : function(data, type, full, meta ){
                                var editUrl = "<c:url value='/admin/materialPrice/materialEdit/'/>"+data;
                                var cityPriceUrl = "<c:url value='/admin/materialPrice/historyPrice/'/>"+data;
                                return '<a href="'+editUrl+'" class="btn btn-sm btn-outline grey-salsa" ><i class="fa fa-edit"></i> 调价</a>'+
                                    '<a href="'+cityPriceUrl+'" class="btn btn-sm btn-outline grey-salsa" ><i class="fa fa-search"></i> 历史价格</a>';
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

        $('button.js_search').click(function () {
            table.clearAjaxParams();
            table.setAjaxParam('mcode',$.trim($('input[name=mcode]').val()));
            table.setAjaxParam('categoryId',$.trim($('input[name=categoryId]').val()));
            table.setAjaxParam('name',$.trim($('input[name=name]').val()));
            table.setAjaxParam('specification',$.trim($('input[name=specification]').val()));
            table.setAjaxParam('start',0);
            table.getDataTable().ajax.reload();
        });

        $('button.js_reset').click(function () {
            table.clearAjaxParams();
            $('input[name=mcode]').val('');
            $('input[name=categoryId]').val('');
            $('input[name=name]').val('');
            $('input[name=specification]').val('');
            table.setAjaxParam('start',0);
            table.getDataTable().ajax.reload();
        });

    });

</script>
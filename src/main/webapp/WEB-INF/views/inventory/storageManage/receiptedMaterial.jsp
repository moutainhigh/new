<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link href="<c:url value="/resources/admin/global/plugins/font-awesome/css/font-awesome.min.css"/>" rel="stylesheet" type="text/css">
<link href="<c:url value="/resources/admin/global/plugins/bootstrap/css/bootstrap.min.css" />" rel="stylesheet" type="text/css" />
<link href="/resources/admin/global/css/plugins.min.css" rel="stylesheet" type="text/css">
<link href="<c:url value="/resources/admin/global/css/components.min.css"/>" rel="stylesheet" type="text/css">
<link href="<c:url value="/resources/admin/global/plugins/uniform/css/uniform.default.css"/>" rel="stylesheet" type="text/css">
<link href="<c:url value="/resources/plugIn/zTree/css/zTreeStyle/zTreeStyle.css"/>" rel="stylesheet" type="text/css">


<link rel="stylesheet" type="text/css" href="<c:url value="/resources/admin/global/plugins/bootstrap-datepicker/css/bootstrap-datepicker3.min.css" />"/>

<div class="portlet light">
    <div class="portlet-title">
        <div class="caption">
            <i class="icon-layers font-green"></i>
            <span class="caption-subject font-green sbold uppercase">收货材料列表</span>
        </div>
    </div>
    <div class="navbar-form navbar-left">

        <div class="form-group">
            <input type="hidden" value="" name="mcCode">
            <button type="button" class="btn btn-primary js_choose_category">选择分类</button>
        </div>
        <div class="form-group">
            <input type="text" name="mcode" class="form-control" value="" placeholder="材料编码">
        </div>
        <div class="form-group">
            <input type="text" name="materialName" class="form-control" value="" placeholder="材料名称">
        </div>
        <div class="form-group">
            <input type="text"  name="specification" class="form-control" value="" placeholder="规格">
        </div>

        <button type="button"  class="btn btn-default js_search">搜索</button>
        <button type="button" class="btn btn-default js_reset">重置</button>
    </div>
    <div class="portlet-body">
        <div class="row">
            <div class="col-md-12">
                <table class="table table-width table-striped table-bordered table-advance table-hover table-checkable " id="datatable_ajax">
                    <thead>
                    <tr role="row" class="heading">
                        <th width="3%">序号</th>
                        <%--<th width="4%">材料编码</th>--%>
                        <th width="4%">材料分类</th>
                        <th width="4%">材料名称</th>
                        <%--<th width="4%">别名</th>--%>
                        <th width="4%">规格</th>
                        <th width="4%">单位</th>
                        <th width="4%">收货日期</th>
                        <th width="4%">收货数量</th>
                        <th width="4%">收货单价</th>
                        <th width="4%">收货金额</th>
                        <th width="4%">库存数量</th>
                        <%--<th width="4%">收货Id</th>--%>
                        <th width="5%">相关操作</th>
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


<script src="<c:url value="/resources/admin/global/plugins/jquery.min.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/admin/global/scripts/app.min.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/admin/global/plugins/jquery.blockui.min.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/admin/global/plugins/uniform/jquery.uniform.min.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/admin/global/plugins/bootstrap/js/bootstrap.min.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/plugIn/zTree/js/jquery.ztree.core.js" />" type="text/javascript"></script>

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
                    url : '<c:url value="/admin/inventory/materialCategory/tree"/>',
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
                $('input[name=mcCode]').val(nodes[0].code);
                $('div.js_tree_div').modal('hide');
            }else{
                layer.msg('请选择分类');
            }
        });

        var TableDatatablesAjax = function() {
            var  e = function() {
                var table = new Datatable;
                table.setAjaxParam("repositoryId", window.parent.currRepository);
                table.init({
                    src: $("#datatable_ajax"),
                    loadingMessage: "Loading...",
                    dataTable: {
                        dom: "<'row'<'col-md-8 col-sm-12'><'col-md-4 col-sm-12'<'table-group-actions pull-right'>>r><'table-responsive't><'row' <'col-md-8 col-sm-12'pli><'col-md-4 col-sm-12'>>",
                        bStateSave: !0,
                        lengthMenu: [[10, 20, 50, 100], [10, 20, 50, 100]],
                        searching:true,
                        pageLength: 10,
                        ajax: {
                            url: "<c:url value="/admin/inventory/receipt/receiptMaterialListData"/>"
                        },
                        order: [[1, "asc"]],
                        columns:[
                            {"data":"id","render" : function(data, type, full, meta ){
                                return data;
                            }},
                            {"data":"categoryName","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
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
                            {"data":"receiptDate","render" : function(data, type, full, meta ){
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
                            {"data":"balanceNum","render" : function(data, type, full, meta ){
                                return (full.num-full.outStorageNum).toFixed(4);
                            }},
//                            {"data":"id","render" : function(data, type, full, meta ){
//                                return data;
//                            }},
                            {"data":"id","render" : function(data, type, full, meta ){
                                return   '<button class="btn btn-sm btn-outline grey-salsa"  target="_blank" data-id="'+data+'"  data-specification="'+full.specificationId+'"><i class="fa fa-plus-circle"></i> 添加至出库</button>';
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


        var listMapContainsKey= function(listMap,defKey,key) {
            var i = 0;
            var flag = false ;
            for(; i < listMap.length; i++) {
                if(listMap[i][defKey] == key){
                    flag = true;
                    break
                }
            }
            return flag;
        };

        var table = TableDatatablesAjax.init();


        var inStoreArray = window.parent.inStoreArray;
        $('#datatable_ajax').on('click','button',function () {
            var _tr = $(this).parents('tr');
            var _dataTb = table.getDataTable();
            var entity = _dataTb.row(_tr).data();
            if(!listMapContainsKey(inStoreArray,'id',entity.id)){
                inStoreArray.push(entity);
            }
        });

        $('button.js_search').click(function () {
            table.clearAjaxParams();
            table.setAjaxParam('mcode',$.trim($('input[name=mcode]').val()));
            table.setAjaxParam('mcCode',$.trim($('input[name=mcCode]').val()));
            table.setAjaxParam('materialName',$.trim($('input[name=materialName]').val()));
            table.setAjaxParam('repositoryId',$.trim($('select[name=repositoryId] option:selected').val()));
            table.setAjaxParam('specification',$.trim($('input[name=specification]').val()));
            table.setAjaxParam("repositoryId", window.parent.currRepository);
            table.setAjaxParam('start',0);
            table.getDataTable().ajax.reload();
        });

        $('button.js_reset').click(function () {
            table.clearAjaxParams();
            $('input[name=mcode]').val('');
            $('input[name=mcCode]').val('');
            $('input[name=materialName]').val('');
            $('input[name=specification]').val('');
            table.setAjaxParam("repositoryId", window.parent.currRepository);
            table.setAjaxParam('start',0);
            table.getDataTable().ajax.reload();
        });

    });
</script>
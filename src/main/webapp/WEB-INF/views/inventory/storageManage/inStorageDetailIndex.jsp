<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<link type="text/css" href="/resources/admin/global/plugins/select2-4.0.3/css/select2.min.css"  rel="stylesheet">
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/admin/global/plugins/bootstrap-datepicker/css/bootstrap-datepicker3.min.css" />"/>

<div class="portlet light">
    <div class="portlet-title">
        <div class="caption">
            <i class="icon-layers font-green"></i>
            <span class="caption-subject font-green sbold uppercase">入库明细</span>
        </div>
        <div class="actions form-inline">
            <a href="javascript:void(0)"><button class="btn btn-primary js_download">导出</button></a>
        </div>
    </div>
    <form class="navbar-form" role="search">
        <div class="row" style="margin-bottom: 5px">
            <div class="input-group">
                <input type="hidden" value="" name="mcCode">
                <input type="text" value="" class="form-control"  name="mcCodeTxt" readonly>
                <span class="input-group-btn"><button type="button" class="btn btn-primary js_choose_category">分类</button></span>
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
                <label>仓库：</label>
                <select class="form-control" name="repositoryId">
                    <option value="">全部</option>
                    <c:forEach items="${repositories}" var="r">
                        <option value="${r.id}">${r.name}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group">
                <input type="text" name="materialName" class="form-control" value="" placeholder="材料名称">
            </div>
            <div class="form-group">
                <input type="text" name="specification" class="form-control" value="" placeholder="材料规格">
            </div>

        </div>
        <div class="row">
            <div class="form-group">
                日期：<input name="startDate" class="form-control input-inline input-sm js_date" type="text" value=""  placeholder="开始日期"/>- <input name="endDate" class="form-control input-inline input-sm js_date" type="text" placeholder="结束日期"/>
            </div>
            <div class="form-group">
                <input type="text" name="inStorageNum" class="form-control" value="" placeholder="入库单号">
            </div>
            <div class="form-group">
                <input type="text" name="billNum" class="form-control" value="" placeholder="发票号">
            </div>
            <button type="button" class="btn btn-default js_reset">重置</button>
            <button type="button"  class="btn btn-default js_search">搜索</button>
        </div>



    </form>
    <div class="portlet-body">
        <div class="row">
            <div class="col-md-12 ">
                <table class="table table-width table-striped table-bordered table-advance table-hover table-checkable table-responsive" id="datatable_ajax">
                    <thead>
                    <tr role="row" class="heading">
                        <th>序号</th>
                        <th>入库单号</th>
                        <th>入库日期</th>
                        <th>库管员</th>
                        <th>仓库名称</th>
                        <th>发票号</th>
                        <th>供货商</th>
                        <th>分类</th>
                        <th>名称</th>
                        <th>规格</th>
                        <th>单位</th>
                        <th>入库数量</th>
                        <th>入库单价</th>
                        <th>税率</th>
                        <th>税额</th>
                        <th>不含税金额</th>
                        <th>金额</th>
                        <th>备注</th>
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


<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/select2-4.0.3/js/select2.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/datatables/datatables.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/scripts/datatable.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.zh-CN.js" />"></script>


<script>

$(function () {

    $('select.js_person').select2();

    var timeInitArgs = {
        todayBtn : "linked",
        autoclose : true,
        language: 'zh-CN',
        format : "yyyy-mm-dd",
        todayHighlight : true
    };

    $('input.js_date').datepicker(timeInitArgs);


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
            var  e = function(args) {
                var table = new Datatable;
                for(var i in args){
                    table.setAjaxParam(i,args[i]);
                }
                table.init({
                    src: $("#datatable_ajax"),
                    loadingMessage: "Loading...",
                    dataTable: {
                        bStateSave: !0,
                        lengthMenu: [[10, 20, 50, 100], [10, 20, 50, 100]],
                        searching:true,
                        pageLength: 10,
                        ajax: {
                            url: "<c:url value="/admin/inventory/inStorage/listDetailData"/>"
                        },
                        order: [[1, "asc"]],
                        columns:[
                            {"data":"id"},
                            {"data":"inStorageNum","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"inStoreDate","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"inStoreUser","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"repositoryName","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"billNum","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"itemProviderName","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"materialCategory","render" : function(data, type, full, meta ){
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
                            {"data":"price","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"num","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"taxRate","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"rateMoney","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"moneyNoRate","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"inStorageMoney","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"remark","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }}
                        ],fnDrawCallback: function(){
                            var api = this.api();
                            var startIndex= api.context[0]._iDisplayStart;//获取到本页开始的条数
                            api.column(0).nodes().each(function(cell, i) {
                                cell.innerHTML = startIndex + i + 1;
                            });

                            var num = 0;
                            var tax = 0;
                            var excTaxSumPrice = 0;
                            var sumPrice = 0;
                            api.column(11).nodes().each(function(cell, i) {
                                num+= parseFloat(cell.innerHTML);
                            });
                            api.column(14).nodes().each(function(cell, i) {
                                tax+= parseFloat(cell.innerHTML);
                            });
                            api.column(15).nodes().each(function(cell, i) {
                                excTaxSumPrice+= parseFloat(cell.innerHTML);
                            });
                            api.column(16).nodes().each(function(cell, i) {
                                sumPrice+= parseFloat(cell.innerHTML);
                            });
                            var tr = '<tr><td colspan="11"></td><td name="num">'+num.toFixed(4)+'</td><td colspan="2"></td><td name="inStorageTax">'+tax.toFixed(2)+'</td><td name="inStorageExcTaxSumPrice">'+excTaxSumPrice.toFixed(2)+'</td><td name="sumPrice">'+sumPrice.toFixed(2)+'</td><td></td></tr>';
                            table.getTable().append(tr);
                        },
                        "initComplete":function () {

                        }
                    }
                });
                return table;
            };
            return {
                init: function(args) {
                    return e(args);
                }
            }
        } ();


        var table = TableDatatablesAjax.init({inStorageStatus:0});

        $('button.js_search').click(function () {
            table.clearAjaxParams();
            table.setAjaxParam('mcCode',$.trim($('input[name=mcCode]').val()));
            table.setAjaxParam('itemProviderName',$.trim($('select[name=itemProviderName]').val()));
            table.setAjaxParam('repositoryId',$.trim($('select[name=repositoryId]').val()));
            table.setAjaxParam('materialName',$.trim($('input[name=materialName]').val()));
            table.setAjaxParam('specification',$('input[name=specification]').val().trim());
            table.setAjaxParam('startTime',$.trim($('input[name=startDate]').val()));
            table.setAjaxParam('endTime',$.trim($('input[name=endDate]').val()));
            table.setAjaxParam('inStorageNum',$.trim($('input[name=inStorageNum]').val()));
            table.setAjaxParam('billNum',$.trim($('input[name=billNum]').val()));
            table.setAjaxParam('projectHouseNum',$.trim($('input[name=projectHouseNum]').val()));
            table.getDataTable().ajax.reload();
        });

        $('button.js_reset').click(function () {
            table.clearAjaxParams();
            $('input[name=mcCode]').val('');
            $('select[name=itemProviderName] option:eq(0)').val('').prop('selected',true);
            $('select[name=repositoryId] option:eq(0)').prop('selected',true);
            $('input[name=materialName]').val('');
            $('input[name=specification]').val('');
            $('input[name=startTime]').val('');
            $('input[name=endTime]').val('');
            $('input[name=inStorageNum]').val('');
            $('input[name=billNum]').val('');
            $('input[name=projectHouseNum]').val('');
            table.getDataTable().ajax.reload();
        });



    var listContains= function(list,data) {
        var i = 0;
        var flag = false ;
        for(; i < list.length; i++) {
            if(list[i] === data){
                flag = true;
                break
            }
        }
        return flag;
    };

    var _arr = new Array();
    var _nameArr = new Array();
    var _repositoryArr = new Array();

    $('#datatable_ajax').on('click','button.js_plus',function () {
        var _this = $(this);
        var _value = _this.data('id');
        var _name = _this.data('provider');
        var _repository = _this.data('repository');
        if (!listContains(_nameArr, _name)) {
            _nameArr.push(_name);
        }
        if (!listContains(_repositoryArr, _repository)) {
            if (_repositoryArr.length>0){
                layer.msg('请选择同一个仓库的材料出库或入库');
                return;
            }
            _repositoryArr.push(_repository);
        }
        if (!listContains(_arr, _value)) {
            _arr.push(_value);
            _this.toggleClass('btn-primary').html('取消');
            _this.data('status',1);
            countData();
        }else{
            _this.toggleClass('btn-primary').html('选择');
            _this.data('status',0);
            removeData(_arr,_value);
            countData();
            var flag = false;
            $('button.js_plus').each(function () {
                var e = $(this);
               if(e.data('provider')==_name && e.data('status')==1){
                   flag = true;
                   return false;
               }
            });
            if(!flag){
                removeData(_nameArr,_name);
            }
        }
    });

    var removeData=function(_array,data){
        $(_array).each(function (k, v) {
            if (v === data){
                _array.splice(k,1);
                return false;
            }
        });
    }

    var countData = function () {
        var num = 0;
        var inStorageTax = 0;
        var inStorageExcTaxSumPrice = 0;
        var sumPrice = 0;
        var arr = table.getDataTable().data();
        $('button.js_plus').each(function (k, v) {
            if($(v).data('status')){
                var obj = arr[k];
                num+= parseFloat(obj.num);
                inStorageTax+= parseFloat(obj.tax);
                inStorageExcTaxSumPrice+= parseFloat(obj.excTaxSumPrice);
                sumPrice+= parseFloat(obj.sumPrice);
            }
        });
        $('td[name=num]').text(num.toFixed(4));
        $('td[name=inStorageTax]').text(inStorageTax.toFixed(2));
        $('td[name=inStorageExcTaxSumPrice]').text(inStorageExcTaxSumPrice.toFixed(2));
        $('td[name=sumPrice]').text(sumPrice.toFixed(2));
    }


    /**
     * 出库
     */
    $('button.js_post_outstorage').on('click',function () {
        if(_arr.length==0){
            layer.msg('请选择要入库的材料');
            return;
        }
        if (_repositoryArr.length>1){
            layer.msg('请选择同一个仓库的材料入库');
            return;
        }
        $('#downloadForm').remove();
        var _form = $('<form>');
        _form.attr('action','<c:url value="/admin/inventory/outStorage/edit"/>');
        _form.attr('id','downloadForm');
        _form.attr('method','post');
        _form.attr('target', '_blank');
        var _input = $('<input type="text" name="ids" />').val(_arr);
        _form.append(_input);
        _input = $('<input type="text" name="repositoryId" />').val(_repositoryArr[0]);
        _form.append(_input);
        $(document).find('body').append(_form);
        $('#downloadForm').submit();
    });


    /**
     * 入库
     */
    $('button.js_post_instore').on('click',function () {
        var flag = true;
        if(_arr.length==0){
            layer.msg('请选择要入库的材料');
            flag = false;
            return;
        }
        if (_nameArr.length>1){
            layer.msg('请选择相同供货商的材料');
            flag = false;
            return;
        }
        if (_repositoryArr.length>1){
            layer.msg('请选择同一个仓库的材料入库');
            flag = false;
            return;
        }
        if(flag&&_arr.length>0){
            $('#downloadForm').remove();
            var _form = $('<form>');
            _form.attr('action','<c:url value="/admin/inventory/inStorage/toInStorageAddPage"/>');
            _form.attr('id','downloadForm');
            _form.attr('method','post');
            var _input = $('<input type="text" name="ids" />');
            _input.val(_arr);
            _form.append(_input);
            $(document).find('body').append(_form);
            $('#downloadForm').submit();
        }
    });

    /**
     * 导出明细
     */
    $('button.js_download').on('click',function () {
        layer.confirm('确定导出？',function (_index) {
            layer.close(_index);
            $('#downloadForm').remove();
            var _form = $('<form>');
            _form.attr('action','<c:url value="/admin/inventory/inStorage/exportDetailData"/>');
            _form.attr('id','downloadForm');
            _form.attr('method','post');
            var _arr = $('form[role=search]').serializeArray();
            for(var i in _arr){
                var obj = _arr[i];
                if(obj.value != ''){
                    _form.append($('<input>').attr('name',obj.name).val($.trim(obj.value)));
                }
            }
            $(document).find('body').append(_form);
            $('#downloadForm').submit();
        });
    });

});
</script>
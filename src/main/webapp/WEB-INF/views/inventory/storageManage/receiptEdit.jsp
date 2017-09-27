<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link type="text/css" href="/resources/admin/global/plugins/select2-4.0.3/css/select2.min.css"  rel="stylesheet">
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/admin/global/plugins/bootstrap-datepicker/css/bootstrap-datepicker3.min.css" />"/>
<style>
    .datepicker-dropdown{
        z-index: 9999;
    }
</style>

<div class="portlet box blue-hoki">
    <div class="portlet-title">
        <div class="caption">收货管理</div>
    </div>
    <div class="portlet-body">
        <div class="row">
            <div class="col-md-6">
                <form  role="form" class="js_base_form" id="materialAddForm">
                    <div class="form-body">
                        <div class="row">
                            <div class="col-md-4 ">
                                <div class="form-group">
                                    <label>材料分类：</label>
                                    <input type="text" class="form-control" name="cateGoryName" value="${entity.categoryName}" readonly>
                                </div>
                            </div>
                            <div class="col-md-4 ">
                                <div class="form-group">
                                    <label>材料名称：</label>
                                    <input type="text" class="form-control" name="materialName" value="${entity.materialName}" readonly>
                                </div>
                            </div>
                            <div class="col-md-4 ">
                                <div class="form-group">
                                    <label>规格：</label>
                                    <c:choose>
                                        <c:when test="${empty entity.id}">
                                            <div class="input-group">
                                                <input type="text" class="form-control" name="specification" value="" readonly>
                                                <span class="input-group-btn">
                                            <button type="button" class="btn btn-primary js_choose_material">请选择</button>
                                        </span>
                                            </div>
                                        </c:when>
                                        <c:otherwise>
                                                <input type="text" class="form-control" name="specification" value="${entity.specification}" readonly>
                                        </span>
                                            </div>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-4">
                                <div class="form-group">
                                    <label>别名：</label>
                                    <input type="text" class="form-control" name="alias" value="${entity.materialAlias}" readonly>
                                </div>
                            </div>
                            <div class="col-md-4 ">
                                <div class="form-group">
                                    <label>单位：</label>
                                    <input type="text" class="form-control" name="unit" value="${entity.unit}" readonly>
                                </div>
                            </div>
                            <div class="col-md-4 ">
                                <div class="form-group">
                                    <label>仓库：${currRepositoryId}</label>
                                    <select class="form-control" name="repositoryId">
                                        <option value="">请选择</option>
                                        <c:forEach items="${repositories}" var="r">
                                            <c:choose>
                                                <c:when test="${empty entity}">
                                                    <option value="${r.id}">${r.name}</option>
                                                </c:when>
                                                <c:otherwise>
                                                    <c:if test="${entity.repositoryId eq r.id}"><option value="${r.id}" selected>${r.name}</option></c:if>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-4">
                                <div class="form-group">
                                    <label>收货人：</label>
                                    <select class="form-control js_person" name="receiptUser">
                                        <option value="">请选择或填写</option>
                                        <c:forEach items="${receiptUsers}" var="p">
                                            <option value="${p.id}" <c:if test="${p.name eq entity.receiptUserName}">selected</c:if>>${p.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="col-md-4 ">
                                <div class="form-group">
                                    <label>验货人：</label>
                                    <select class="form-control js_person" name="checkUser">
                                        <option value="">请选择或填写</option>
                                        <c:forEach items="${checkUsers}" var="p">
                                            <option value="${p.id}" <c:if test="${p.name eq entity.checkUserName}">selected</c:if>>${p.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="col-md-4 ">
                                <div class="form-group">
                                    <label>收货日期：</label>
                                    <c:choose>
                                        <c:when test="${empty entity}">
                                            <input type="text" class="form-control" name="receiptDate" value="<fmt:formatDate value="${now}" pattern="yyyy-MM-dd"/>">
                                        </c:when>
                                        <c:otherwise>
                                            <input type="text" class="form-control" name="receiptDate" value="<fmt:formatDate value="${entity.receiptDate}" pattern="yyyy-MM-dd"/>">
                                        </c:otherwise>
                                    </c:choose>

                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-4">
                                <div class="form-group">
                                    <label>数量：</label>
                                    <input type="text" class="form-control" name="num" value="${entity.num}">
                                </div>
                            </div>
                            <div class="col-md-4 ">
                                <div class="form-group">
                                    <label>单价：</label>
                                    <input type="text" class="form-control" name="price" value="${entity.price}">
                                </div>
                            </div>
                            <div class="col-md-4 ">
                                <div class="form-group">
                                    <label>总金额：</label>
                                    <input type="text" class="form-control" name="sumPrice" value="${entity.sumPrice}" readonly>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-4">
                                <div class="form-group">
                                    <label>税率(%)：</label>
                                    <input type="text" class="form-control" name="taxRate" value="${entity.taxRate}">
                                </div>
                            </div>
                            <div class="col-md-4 ">
                                <div class="form-group">
                                    <label>税额：</label>
                                    <input type="text" class="form-control" name="tax" value="${entity.tax}" readonly>
                                </div>
                            </div>
                            <%--<div class="col-md-4 ">--%>
                                <%--<div class="form-group">--%>
                                    <%--<label>不含税单价：</label>--%>
                                    <%--<input type="text" class="form-control" name="excTaxPrice" value="${entity.excTaxPrice}" readonly>--%>
                                <%--</div>--%>
                            <%--</div>--%>
                        </div>
                        <div class="row">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label>不含税总金额：</label>
                                    <input type="text" class="form-control" name="excTaxSumPrice" value="${entity.excTaxSumPrice}" readonly>
                                </div>
                            </div>
                            <div class="col-md-6 ">
                                <div class="form-group">
                                    <label>供货商：</label>
                                    <select class="form-control js_person" name="itemProvider">
                                        <option value="">请选择或填写</option>
                                        <c:forEach items="${person2}" var="p">
                                            <option value="${p.id}" <c:if test="${p.name eq entity.itemProviderName}">selected</c:if>>${p.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label>依据：</label>
                                    <%--<textarea class="form-control" name="note" >${entity.note}</textarea>--%>
                                    <select class="form-control js_person" name="noteId">
                                        <option value="">请选择或填写</option>
                                        <c:forEach items="${person11}" var="p">
                                            <option value="${p.id}" <c:if test="${p.name eq entity.note}">selected</c:if>>${p.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="col-md-6 ">
                                <div class="form-group">
                                    <label>备注：</label>
                                    <input type="text" class="form-control" name="remark" value="${entity.remark}" >
                                </div>
                            </div>
                        </div>
                    <c:if test="${editAble}">
                    <button type="button" class="btn green js_save_btn" data-action="save">保存</button>
                    </c:if>
                    <a type="button" class="btn default" href="<c:url value='/admin/inventory/receipt/index'/>">返回列表</a>
                </form>
            </div>
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
                            <label>分类</label>
                            <ul id="categoryTree" class="ztree"></ul>
                        </div>
                        <div class="col-md-4">
                            <label>材料</label>
                            <ul id="materialTree" class="ztree"></ul>
                        </div>
                        <div class="col-md-4">
                            <label>规格</label>
                            <ul id="speciTree" class="ztree"></ul>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary js_save_pop_btn">确定</button>
            </div>
        </div>
    </div>
</div>


<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/select2-4.0.3/js/select2.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/jquery-validation/js/jquery.validate.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/jquery-validation/js/additional-methods.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/jquery-validation/js/messages_zh.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.zh-CN.js" />"></script>


<script>

    var chooseMaterial = null;
    $(function () {


        var dataObj = {
            id:'${entity.id}',
            materialId:'${entity.materialId}',
            specificationId:'${entity.specificationId}',
            oldNum:'${entity.num}',
            receiptUserName:'${entity.receiptUserName}',
            checkUserName:'${entity.checkUserName}',
            itemProviderName:'${entity.itemProviderName}',
            note:'${entity.note}'
        };

        $('select.js_person').select2({
            tags: true,
            createTag: function (params) {
                var term = $.trim(params.term);
                if (term === '') {
                    return null;
                }
                return {
                    id: "-1",
                    text: term
                }
            }
        }).on('select2:select', function (evt) {
            var _name =  $(this).attr('name');
            if (_name=="receiptUser"){
                dataObj.receiptUserName = evt.params.data.text;
            }else if (_name=="checkUser"){
                dataObj.checkUserName = evt.params.data.text;
            }else if (_name=="itemProvider"){
                dataObj.itemProviderName = evt.params.data.text;
            }else if (_name=="noteId"){
                dataObj.note = evt.params.data.text;
            }
        });


        $('input[name=receiptDate]').datepicker({
            todayBtn : "linked",
            autoclose : true,
            language: 'zh-CN',
            format : "yyyy-mm-dd",
            todayHighlight : true,
            endDate : new Date()
        });



        var treeObj = {categoryTree:null,materialTree:null,speciTree:null};

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

        var getMaterialSpeci = function (_matId) {
            $.ajax({
                async: false,
                type: 'POST',
                dataType: 'json',
                url: '<c:url value="/admin/inventory/material/getMaterialSpecification"/>',
                data: {matId: _matId},
                success: function (result) {
                    treeObj.speciTree = initTree(result, 'speciTree', function (event, treeId, treeNode) {

                    },{data:{key:{name:'specification'}}});
                    var nodes = treeObj.speciTree.getNodes();
                    treeObj.speciTree.expandNode(nodes[0], true, false, false);
                }
            });
        }

        var getMaterial = function (_categoryId) {
            $.ajax({
                async: false,
                type : 'POST' ,
                dataType:'json',
                url : '<c:url value="/admin/inventory/material/getMaterials"/>',
                data:{categoryId:_categoryId},
                success: function (result) {
                    $.fn.zTree.destroy('speciTree');
                    treeObj.materialTree = initTree(result, 'materialTree', function (event, treeId, treeNode) {
                        getMaterialSpeci(treeNode.id);
                    });
                    var nodes = treeObj.materialTree.getNodes();
                    treeObj.materialTree.expandNode(nodes[0],true,false,false);
                }
            });
        }

        $('button.js_choose_material').on('click',function () {
            layer.open({
                type: 2,
                title: "",
                area:['80%','60%'],
                content: '<c:url value="/admin/inventory/material/getMaterialSpecification" />',
                end:function () {
                    if (null != chooseMaterial) {
                        $('input[name=cateGoryName]').val(chooseMaterial.cateGoryName);
                        $('input[name=materialName]').val(chooseMaterial.materialName);
                        $('input[name=specification]').val(chooseMaterial.specification);
                        $('input[name=alias]').val(chooseMaterial.alias);
                        $('input[name=unit]').val(chooseMaterial.unit);
                        dataObj.materialId = chooseMaterial.materialId;
                        dataObj.specificationId = chooseMaterial.specificationId;
                    }
                }
            });
        });

        $('button.js_save_pop_btn').on('click',function () {
            var nodes = treeObj.categoryTree.getSelectedNodes();
            if(nodes.length>0&&!nodes[0].isParent){
                $('input[name=categoryId]').val(nodes[0].id);
                $('input[name=cateGoryName]').val(nodes[0].name);
                var nodes2 = treeObj.materialTree.getSelectedNodes();
                if(nodes2.length>0&&!nodes2[0].isParent){
                    dataObj.materialId=nodes2[0].id;
                    $('input[name=materialName]').val(nodes2[0].name);
                    $('input[name=unit]').val(nodes2[0].unit);
                    $('input[name=alias]').val(nodes2[0].alias);
                    $('select[name=repositoryId] option[value="'+nodes2[0].repositoryId+'"]').prop('selected',true);
                    var nodes3 = treeObj.speciTree.getSelectedNodes();
                    if(nodes3.length>0&&!nodes3[0].isParent){
                        $('input[name=specification]').val(nodes3[0].specification);
                        $('div.js_tree_div').modal('hide');
                    }else{
                        layer.msg('请选择规格');
                    }
                }else{
                    layer.msg('请选择材料');
                }
            }else{
                layer.msg('请选择分类');
            }
        });


        var validateForm = $('form.js_base_form');
        validateForm.validate({
            errorElement : 'span',
            errorClass : 'help-block help-block-error',
            rules : {
                cateGoryName : {
                    required : true
                },materialName : {
                    required : true
                },specification : {
                    required : true
                },repositoryId: {
                    required : true
                },receiptUser: {
                    required : true
                },checkUser: {
                    required : true
                },receiptDate: {
                    required : true
                },num: {
                    required : true,
                    number:true
                },price: {
                    required : true,
                    number:true
                },sumPrice: {
                    required : true,
                    number:true
                },taxRate: {
                    required : true,
                    number:true
                },tax: {
                    required : true,
                    number:true
                },excTaxSumPrice: {
                    required : true,
                    number:true
                },itemProvider: {
                    required : true
                }
            },
            highlight : function(element) {
                $(element).parents('div.form-group').addClass('has-error');
            },
            unhighlight : function(element) {
                $(element).parents('div.form-group').removeClass('has-error');
            },
            success : function(label) {
                label.remove();
            },errorPlacement: function(error, element) {
                element.parents('div.form-group').append(error);
            }
        });

        /**
         * 计算总金额
         */
        var calculationSumPrice = function () {
            var _num = parseFloat($('input[name=num]').val());
            var _price = parseFloat($('input[name=price]').val());
            if (!isNaN(_num) && !isNaN(_price)){
                $('input[name=sumPrice]').val(parseFloat(_num*_price).toFixed(2));
            }
        }

        /**
         * 计算税额
         */
        var calculationTax = function () {
            var _taxRate = parseFloat($('input[name=taxRate]').val());
            var _excTaxSumPrice = parseFloat($('input[name=excTaxSumPrice]').val());
            if (!isNaN(_taxRate) && !isNaN(_excTaxSumPrice)){
                $('input[name=tax]').val((_excTaxSumPrice*_taxRate*0.01).toFixed(2));
            }
        }

        /**
         * 计算不含税总金额
         */
        var calculationExcTaxSumPrice = function () {
            var _sumPrice = parseFloat($('input[name=sumPrice]').val());
            var taxRate = parseFloat($('input[name=taxRate]').val());
            if (!isNaN(_sumPrice) && !isNaN(taxRate)){
                $('input[name=excTaxSumPrice]').val((_sumPrice/(1+taxRate*0.01)).toFixed(2));
            }
        }

        $('input[name=num]').on('change',function () {
            calculationSumPrice();
            calculationExcTaxSumPrice();
            calculationTax();
        });

        $('input[name=price]').on('change',function () {
            calculationSumPrice();
            calculationExcTaxSumPrice();
//            calculationExcTaxPrice();
            calculationTax();
        });

        $('input[name=taxRate]').on('change',function () {
            calculationExcTaxSumPrice();
//            calculationExcTaxPrice();
            calculationTax();
        });


        var _index=null;
        $('button.js_save_btn').on('click',function () {
            if(validateForm.valid()){
                var objArr = $('form.js_base_form').serializeArray();
                for(var e in objArr){
                    var  obj = objArr[e];
                    dataObj[obj.name] = obj.value;
                }
                var url = null;
                if(dataObj.id == ''){
                    url = '<c:url value="/admin/inventory/receipt/insert"/>';
                }else{
                    url = '<c:url value="/admin/inventory/receipt/update"/>';
                }
                layer.confirm("确定提交？",function (c) {
                    layer.close(c);
                    _index = layer.load(2);
                    $.post(url, dataObj, function (result) {
                        layer.close(_index);
                        if (result.success) {
                            layer.msg('操作成功',{time:800},function () {
                                <%--window.location.href='<c:url value="/admin/inventory/receipt/index"/>';--%>
                                window.location.reload();
                            });
                        }else{
                            layer.msg('操作失败：'+result.rmsg,{time:2000});
                        }
                    }, 'json');
                });
            }
        });

    });

</script>


<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<style>
    .js_specification{
        padding-bottom: 15px;
    }
</style>

<div class="portlet box blue-hoki">
    <div class="portlet-title">
        <div class="caption">
            <i class="fa fa-gift"></i>
            材料管理
        </div>
    </div>
    <div class="portlet-body">
        <div class="row">
            <div class="col-md-4 ">
                <form  role="form" class="js_base_form" id="materialAddForm">
                    <div class="form-body">
                        <div class="row">
                            <div class="col-md-12 ">
                                <div class="form-group">
                                    <label>材料分类：</label>
                                    <c:choose>
                                        <c:when test="${not empty entity.id}">
                                                <input type="text" class="form-control" name="name" value="${entity.categoryName}" readonly>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="input-group">
                                                <input type="text" class="form-control" name="cateGoryName" value="${entity.categoryName}" readonly>
                                                <span class="input-group-btn">
                                                <button type="button" class="btn btn-primary js_choose_category">请选择</button>
                                                </span>
                                            </div>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12 ">
                                <div class="form-group">
                                    <label>材料名称：</label>
                                    <input type="text" class="form-control" name="name" value="${entity.name}">
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6 ">
                                <div class="form-group">
                                    <label>别名：</label>
                                    <input type="text" class="form-control" name="alias" value="${entity.alias}">
                                </div>
                            </div>
                            <div class="col-md-6 ">
                                <div class="form-group">
                                    <label>品牌：</label>
                                    <input type="text" class="form-control" name="brand" value="${entity.brand}">
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6 ">
                                <div class="form-group">
                                    <label>单位：</label>
                                    <select class="form-control" name="unit">
                                        <c:forEach items="${units}" var="u">
                                            <option value="${u.dictKey}" <c:if test="${u.dictKey eq entity.unit}">selected</c:if>>${u.dictValue}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="col-md-6 ">
                                <div class="form-group">
                                    <label>项目：</label>
                                    <input type="text" class="form-control" value="${projectName}" readonly>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-3 text-center">
                                预算：
                            </div>
                            <div class="col-md-9">
                                规格：
                            </div>
                        </div>
                        <div class="row js_specification">
                            <div class="col-md-3">
                                <div class="form-group">
                                    <input class="form-control" name="budget" value="${entity.budget}"/>
                                </div>
                            </div>
                            <div class="col-md-9">
                                <div class="row">
                                    <div class="col-md-12 ">
                                        <c:if test="${not empty entity.id}">
                                            <div class="form-group">
                                                <input type="text" class="hide" name="specificationId" value="${entity.specificationId}">
                                                <input type="text" class="form-control" name="specification" value="${entity.specification}">
                                            </div>
                                        </c:if>
                                        <c:if test="${empty entity.id}">
                                            <div class="input-group">
                                                <input type="text" class="form-control" name="specification" value="">
                                                <span class="input-group-btn">
                                                <a href="javascript:void(0);" class="btn js_remove">
                                                    <i class="fa fa-times"></i> 删除
                                                </a>
                                                <a href="javascript:void(0);" class="btn js_add">
                                                    <i class="fa fa-plus"></i> 增加
                                                </a>
                                            </span>
                                            </div>
                                        </c:if>
                                    </div>
                                </div>
                            </div>
                        </div>


                        <div class="row">
                            <div class="col-md-12">
                                <div class="form-group">
                                    <label>材料备注：</label>
                                    <textarea class="form-control" name="remark" >${entity.remark}</textarea>
                                </div>
                            </div>
                        </div>

                    <button type="button" class="btn green js_save_chg" data-action="save">保存</button>
                    <a type="button" class="btn default" href="<c:url value='/admin/inventory/material/index'/>">返回列表</a>
                    </div>
                </form>
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


<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/jquery-validation/js/jquery.validate.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/jquery-validation/js/additional-methods.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/jquery-validation/js/messages_zh.min.js" />"></script>


<script>

    $(function () {

        var treeObj = null;
        var dataObj={id:'${entity.id}',categoryId:'${entity.categoryId}'};

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
            if(nodes.length>0&&!nodes[0].isParent){
                dataObj.categoryId=nodes[0].id;
                $('input[name=cateGoryName]').val(nodes[0].name);
                $('div.js_tree_div').modal('hide');
            }else{
                layer.msg('请选择一个根分类');
            }
        });


        var validateForm = $('form.js_base_form');
        validateForm.validate({
            errorElement : 'span',
            errorClass : 'help-block help-block-error',
            rules : {
                cateGoryName : {
                    required : true
                },name : {
                    required : true
                },unit : {
                    required : true
                },categoryId: {
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

        var _index=null;
        $('button.js_save_chg').on('click',function () {
            if(validateForm.valid()){
                var objArr = $('form.js_base_form').serializeArray();
                for(var e in objArr){
                    var  obj = objArr[e];
                    dataObj[obj.name] = obj.value;
                }
                if (!dataObj['categoryId']){
                    layer.msg('请选择分类');
                    return;
                }
                var url = null;
                var _specificationArr = new Array();
                if(dataObj.id == ''){
                    var _arr = new Array();
                    $('div.js_specification').each(function (k, v) {
                        var val = $.trim($(v).find('input[name=specification]').val());
                        var budget = $.trim($(v).find('input[name=budget]').val());
                        if (val!='' || budget!= ""){
                            var obj = {
                                specification:val,
                                budget:budget
                            };
                            _arr.push(obj);
                            if(!listContains(_specificationArr,val)){
                                _specificationArr.push(val);
                            }
                        }
                    });
                    if (_arr.length==0){
                        layer.msg('规格不能为空');
                        return;
                    }else if(_arr.length!=_specificationArr.length){
                        layer.msg('规格重复，请检查');
                        return;
                    }
                    dataObj.specificationArrayStr = JSON.stringify(_arr);
                    url = '<c:url value="/admin/inventory/material/insert"/>';
                }else{
                    url = '<c:url value="/admin/inventory/material/update"/>';
                }

                layer.confirm("确定提交？",function (c) {
                    layer.close(c);
                    _index = layer.load(2);
                    $.post(url, dataObj, function (result) {
                        layer.close(_index);
                        if (result.success) {
                            layer.msg('操作成功',{time:800},function () {
                                window.location.href='<c:url value="/admin/inventory/material/index"/>';
                            });
                        }else{
                            layer.msg('操作失败：'+result.rmsg,{time:2000});
                        }
                    }, 'json');
                });
            }
        });

        var htmlCodes = [
            '<div class="row js_specification">',
                '<div class="col-md-3">',
                    '<div class="form-group">',
                        '<input class="form-control" name="budget"/>',
                    '</div>',
                '</div>',
                '<div class="col-md-9">',
                    '<div class="row">',
                        '<div class="col-md-12 ">',
                            '<div class="input-group">',
                                '<input type="text" class="form-control" name="specification" value="">',
                                '<span class="input-group-btn">',
                                    '<a href="javascript:void(0);" class="btn js_remove">',
                                        '<i class="fa fa-times"></i> 删除',
                                    '</a>',
                                    '<a href="javascript:void(0);" class="btn js_add">',
                                        '<i class="fa fa-plus"></i> 增加',
                                    '</a>',
                                '</span>',
                            '</div>',
                        '</div>',
                    '</div>',
                '</div>',
            '</div>'
        ].join("");

        /**
         * 增加删除 规格操作
         */
        $('form.js_base_form').on('click','a.js_remove',function () {
            var _len = $('div.js_specification').length;
            if (_len>1){
                $(this).parents('div.js_specification').remove();
            }
        }).on('click','a.js_add',function () {
            $(this).parents('div.js_specification').after(htmlCodes);
        });

    });

</script>


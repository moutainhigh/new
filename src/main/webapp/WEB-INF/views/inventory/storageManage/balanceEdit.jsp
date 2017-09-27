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
        <div class="caption">金额调平</div>
    </div>
    <div class="portlet-body">
        <div class="row">
            <div class="col-md-6">
                <form  role="form" class="js_base_form" id="materialAddForm">
                    <div class="form-body">
                        <div class="row">
                            <div class="col-md-6 ">
                                <div class="form-group">
                                    <label>材料分类：</label>
                                    <input type="text" class="form-control" name="cateGoryName" value="${entity.categoryName}" readonly>
                                </div>
                            </div>
                            <div class="col-md-6 ">
                                <div class="form-group">
                                    <label>材料名称：</label>
                                    <input type="text" class="form-control" name="materialName" value="${entity.materialName}" readonly>
                                </div>
                            </div>
                        </div>
                    <div class="row">
                        <div class="col-md-6 ">
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
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <label>日期：</label>
                                <input type="text" class="form-control" name="balanceDate" value="<fmt:formatDate value="${entity.balanceDate}" pattern="yyyy-MM-dd"/>">
                            </div>
                        </div>
                        <div class="col-md-6 ">
                            <div class="form-group">
                                <label>仓库：${currRepositoryId}</label>
                                <select class="form-control" name="repositoryId">
                                    <c:if test="${empty entity}">
                                        <option value="">请选择</option>
                                    </c:if>
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
                        <div class="col-md-6 ">
                            <div class="form-group">
                                <label>税额：</label>
                                <input type="text" class="form-control" name="sumTax" value="${entity.sumTax}">
                            </div>
                        </div>
                        <div class="col-md-6 ">
                            <div class="form-group">
                                <label>总金额：</label>
                                <input type="text" class="form-control" name="sumPrice" value="${entity.sumPrice}">
                            </div>
                        </div>
                    </div>
                        <button type="button" class="btn green js_save_btn" data-action="save">保存</button>
                        <a type="button" class="btn default" href="<c:url value='/admin/inventory/statistics/balanceIndex'/>">返回列表</a>
                    </div>
                </form>
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
            specificationId:'${entity.specificationId}'
        };


        $('input[name=balanceDate]').datepicker({
            todayBtn : "linked",
            autoclose : true,
            language: 'zh-CN',
            format : "yyyy-mm-dd",
            todayHighlight : true,
            endDate : new Date()
        });


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
                        dataObj.materialId = chooseMaterial.materialId;
                        dataObj.specificationId = chooseMaterial.specificationId;
                        dataObj.specificationId = chooseMaterial.specificationId;
                    }
                }
            });
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
                },repositoryId : {
                    required : true
                },balanceDate: {
                    required : true
                },sumTax: {
                    required : true,
                    number:true
                },sumPrice: {
                    required : true,
                    number:true
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
                    url = '<c:url value="/admin/inventory/statistics/insertBalance"/>';
                }else{
                    url = '<c:url value="/admin/inventory/statistics/updateBalance"/>';
                }
                layer.confirm("确定提交？",function (c) {
                    layer.close(c);
                    _index = layer.load(2);
                    $.post(url, dataObj, function (result) {
                        layer.close(_index);
                        if (result.success) {
                            layer.msg('操作成功',{time:800},function () {
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


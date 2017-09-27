<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<div class="portlet box blue-hoki">
    <div class="portlet-title">
        <div class="caption">
            <i class="fa fa-gift"></i>
            材料价格管理
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
                                    <select class="form-control" disabled="disabled" name="categoryId" id="materialCatSelect">
                                        <c:if test="${not empty materialResult}">
                                            <option value="${materialResult.categoryId}">${materialResult.categoryName}</option>
                                        </c:if>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12 ">
                                <div class="form-group">
                                    <label>材料名称：</label>
                                    <input type="text" readonly="readonly" class="form-control" name="name" value="${materialResult.name}">
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12 ">
                                <div class="form-group">
                                    <label>规格：</label>
                                    <input type="text" readonly="readonly" class="form-control" name="specification" value="${materialResult.specification}">
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12 ">
                                <div class="form-group">
                                    <label>型号：</label>
                                    <input type="text" readonly="readonly" class="form-control" name="specification2" value="${materialResult.specification2}">
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12 ">
                                <div class="form-group">
                                    <label>产地：</label>
                                    <input type="text" readonly="readonly" class="form-control" name="area" value="${materialResult.area}">
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12 ">
                                <div class="form-group">
                                    <label>单位：</label>
                                    <input type="text" readonly="readonly" class="form-control" name="unit" value="${materialResult.unit}">
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12 ">
                                <div class="form-group">
                                    <label>材料价格：</label>
                                    <input type="text" class="form-control" name="price" value="${materialResult.price}">  <%--做一个可以输入的下拉,模糊查询--%>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12 ">
                                <div class="form-group">
                                    <label>历史价格：</label>
                                    <input type="text" readonly="readonly" id="historyPrice" class="form-control" name="historyPrice" value="${materialResult.historyPrice}">  <%--做一个可以输入的下拉,模糊查询--%>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6 ">
                                <div class="form-group">
                                    <label>材料备注：</label>
                                    <textarea class="form-control" name="remark" rows="3" style="width: 535px"></textarea>
                                </div>
                            </div>
                        </div>

                    <button type="button" class="btn green js_save_chg" data-action="save">保存</button>
                    <a type="button" class="btn default" href="<c:url value='/admin/materialPrice/index'/>">返回列表</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/jquery-validation/js/jquery.validate.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/jquery-validation/js/additional-methods.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/jquery-validation/js/messages_zh.min.js" />"></script>

<script>


    $(document).ready(function () {
        var validateForm = $('form.js_base_form');
        validateForm.validate({
            errorElement : 'span',
            errorClass : 'help-block help-block-error',
            rules : {
                price : {
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

        var dataObj={id:'${materialResult.id}'};
        var _index=null;
        $('button.js_save_chg').on('click',function () {
            if(validateForm.valid()){
                _index = layer.load(2);
                var objArr = $('form.js_base_form').serializeArray();
                for(var e in objArr){
                    var  obj = objArr[e];
                    dataObj[obj.name] = obj.value;
                }
                $.post('<c:url value="/admin/materialPrice/update"/>', dataObj, function (result) {
                    if (result.success) {
                        layer.close(_index);
                        layer.msg('操作成功',{time:800},function () {
                            window.location.href="/admin/materialPrice/index";
                        });
                    }else{
                        layer.close(_index);
                        layer.msg('操作失败：'+result.rmsg,{time:2000});
                    }

                }, 'json');
            }
        });

    });

</script>
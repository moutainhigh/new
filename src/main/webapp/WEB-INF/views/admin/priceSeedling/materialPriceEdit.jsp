<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link href="<c:url value="/resources/admin/global/plugins/font-awesome/css/font-awesome.min.css"/>" rel="stylesheet" type="text/css">
<link href="<c:url value="/resources/admin/global/plugins/bootstrap/css/bootstrap.min.css" />" rel="stylesheet" type="text/css" />
<link href="/resources/admin/global/css/plugins.min.css" rel="stylesheet" type="text/css">
<link href="<c:url value="/resources/admin/global/css/components.min.css"/>" rel="stylesheet" type="text/css">
<link href="<c:url value="/resources/admin/global/plugins/uniform/css/uniform.default.css"/>" rel="stylesheet" type="text/css">
<link href="<c:url value="/resources/plugIn/zTree/css/zTreeStyle/zTreeStyle.css"/>" rel="stylesheet" type="text/css">


<div class="portlet light">
    <div class="portlet-title">
        <div class="caption">
            苗木价格管理
        </div>
    </div>
    <div class="portlet-body">
        <div class="row">
            <div class="col-md-12 ">
                <form  role="form" class="js_base_form" id="materialAddForm">
                    <div class="form-body">
                        <div class="row">
                            <div class="col-md-3 ">
                                <div class="form-group">
                                    <label>苗木类别：</label>
                                    <select class="form-control" disabled="disabled" name="categoryId" id="materialCatSelect">
                                        <c:if test="${not empty materialResult}">
                                            <option value="${materialResult.categoryId}">${materialResult.categoryName}</option>
                                        </c:if>
                                    </select>
                                </div>
                            </div>
                            <div class="col-md-3 ">
                                <div class="form-group">
                                    <label>苗木名称：</label>
                                    <input type="text" readonly="readonly" class="form-control" name="name" value="${materialResult.name}">
                                </div>
                            </div>
                            <div class="col-md-3 ">
                                <div class="form-group">
                                    <label>冠幅：</label>
                                    <input type="text" readonly="readonly" class="form-control" name="specification" value="${materialResult.specification}">
                                </div>
                            </div>
                            <div class="col-md-3 ">
                                <div class="form-group">
                                    <label>米径：</label>
                                    <input type="text" readonly="readonly" class="form-control" name="specification2" value="${materialResult.specification2}">
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-3 ">
                                <div class="form-group">
                                    <label>高度：</label>
                                    <input type="text" readonly="readonly" class="form-control" name="specification3" value="${materialResult.specification3}">
                                </div>
                            </div>

                            <div class="col-md-3 ">
                                <div class="form-group">
                                    <label>分枝点：</label>
                                    <input type="text" readonly="readonly" class="form-control" name="specification4" value="${materialResult.specification4}">
                                </div>
                            </div>

                            <div class="col-md-3 ">
                                <div class="form-group">
                                    <label>树形及要求：</label>
                                    <input type="text" readonly="readonly" class="form-control" name="specification5" value="${materialResult.specification5}">
                                </div>
                            </div>

                            <div class="col-md-3 ">
                                <div class="form-group">
                                    <label>土球直径：</label>
                                    <input type="text" readonly="readonly" class="form-control" name="specification6" value="${materialResult.specification6}">
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-4 ">
                                <div class="form-group">
                                    <label>土球厚度：</label>
                                    <input type="text" readonly="readonly" class="form-control" name="specification7" value="${materialResult.specification7}">
                                </div>
                            </div>

                            <div class="col-md-4 ">
                                <div class="form-group">
                                    <label>苗木特点：</label>
                                    <input type="text" readonly="readonly" class="form-control" name="extend" value="${materialResult.extend}">
                                </div>
                            </div>

                            <div class="col-md-4 ">
                                <div class="form-group">
                                    <label>种植密度标准（暂估）：</label>
                                    <input type="text" readonly="readonly" class="form-control" name="extend2" value="${materialResult.extend2}">
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-4 ">
                                <div class="form-group">
                                    <label>单位：</label>
                                    <input type="text" readonly="readonly" class="form-control" name="unit" value="${materialResult.unit}">
                                </div>
                            </div>

                            <div class="col-md-4 ">
                                <div class="form-group">
                                    <label>历史价格：</label>
                                    <input type="text" readonly="readonly" id="historyPrice" class="form-control" name="historyPrice" value="${materialResult.historyPrice}">  <%--做一个可以输入的下拉,模糊查询--%>
                                </div>
                            </div>

                            <div class="col-md-4 ">
                                <div class="form-group">
                                    <label>苗木价格：</label>
                                    <input type="text" class="form-control" name="price" value="${materialResult.price}">  <%--做一个可以输入的下拉,模糊查询--%>
                                </div>
                            </div>

                        </div>
                        <div class="row">
                            <div class="col-md-6 ">
                                <div class="form-group">
                                    <label>苗木备注：</label>
                                    <textarea class="form-control" name="remark" rows="3" style="width: 535px"></textarea>
                                </div>
                            </div>
                        </div>

                    <button type="button" class="btn green js_save_chg" data-action="save">保存</button>
                    <%--<a type="button" class="btn default" href="<c:url value='/admin/priceSeedlingPrice/index'/>">返回列表</a>--%>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<script src="<c:url value="/resources/admin/global/plugins/jquery.min.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/admin/global/scripts/app.min.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/admin/global/plugins/jquery.blockui.min.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/admin/global/plugins/uniform/jquery.uniform.min.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/admin/global/plugins/bootstrap/js/bootstrap.min.js" />" type="text/javascript"></script>

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
                $.post('<c:url value="/admin/priceSeedlingPrice/update"/>', dataObj, function (result) {
                    if (result.success) {
                        layer.close(_index);
                        layer.msg('操作成功',{time:800},function () {
                            window.location.href="/admin/priceSeedlingPrice/index";
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
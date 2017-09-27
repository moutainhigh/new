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
        <div class="caption">退货管理</div>
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
                                    <input type="text" class="form-control" name="cateGoryName" value="${prItemDelivery.categoryName}" readonly>
                                </div>
                            </div>

                            <div class="col-md-4">
                                <div class="form-group">
                                    <label>退货人：</label>
                                    <select class="form-control js_person" name="returnedUser">
                                        <option value="">请选择或填写</option>
                                        <c:forEach items="${person6}" var="p">
                                            <option value="${p.name}">${p.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="col-md-4 ">
                                <div class="form-group">
                                    <label>退货日期：</label>
                                    <input type="text" class="form-control" name="returnedDate">
                                </div>
                            </div>

                        </div>
                        <div class="row">
                            <div class="col-md-4 ">
                                <div class="form-group">
                                    <label>材料名称：</label>
                                    <input type="text" class="form-control" name="materialName" value="${prItemDelivery.materialName}" readonly>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="form-group">
                                    <label>数量(<font color="red">可退数量为1~${prItemDelivery.balanceNum}</font>)：</label>
                                    <input type="text" class="form-control" name="num" value="${prItemDelivery.balanceNum}">
                                </div>
                            </div>
                            <div class="col-md-4 ">
                                <div class="form-group">
                                    <label>单价：</label>
                                    <input type="text" class="form-control" name="price" value="${prItemDelivery.price}" readonly>
                                </div>
                            </div>

                        </div>
                        <div class="row">
                            <div class="col-md-4">
                                <div class="form-group">
                                    <label>别名：</label>
                                    <input type="text" class="form-control" name="alias" value="${prItemDelivery.alias}" readonly>
                                </div>
                            </div>
                            <div class="col-md-4 ">
                                <div class="form-group">
                                    <label>总金额：</label>
                                    <input type="text" class="form-control" name="sumPrice" value="" readonly>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="form-group">
                                    <label>税率(%)：</label>
                                    <input type="text" class="form-control" name="taxRate" readonly value="${prItemDelivery.taxRate}">
                                </div>
                            </div>
                        </div>

                        <div class="row">

                            <div class="col-md-4 ">
                                <div class="form-group">
                                    <label>规格：</label>
                                    <input type="text" class="form-control" name="specification" value="${prItemDelivery.specification}" readonly>
                                </div>
                            </div>

                            <div class="col-md-4 ">
                                <div class="form-group">
                                    <label>税额：</label>
                                    <input type="text" class="form-control" name="tax" value="" readonly>
                                </div>
                            </div>
                            <div class="col-md-4 ">
                                <div class="form-group">
                                    <label>不含税单价：</label>
                                    <input type="text" class="form-control" name="excTaxPrice" value="${prItemDelivery.excTaxPrice}" readonly>
                                </div>
                            </div>

                        </div>

                        <div class="row">
                            <div class="col-md-4 ">
                                <div class="form-group">
                                    <label>单位：</label>
                                    <input type="text" class="form-control" name="unit" value="${prItemDelivery.unit}" readonly>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="form-group">
                                    <label>不含税总金额：</label>
                                    <input type="text" class="form-control" name="excTaxSumPrice" value="" readonly>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="form-group">
                                    <label>供货商：</label>
                                    <input type="text" class="form-control" name="itemProvider" value="${prItemDelivery.itemProviderName}" readonly/>
                                </div>
                            </div>

                        </div>
                        <div class="row">
                            <div class="col-md-4 ">
                                <div class="form-group">
                                    <label>退货仓库：</label>
                                    <input type="text" class="form-control" value="${prItemDelivery.repositoryName}" readonly/>
                                    <input type="hidden" class="form-control" name="repositoryId" value="${prItemDelivery.repositoryId}"/>
                                </div>
                            </div>
                            <div class="col-md-8">
                                <div class="form-group">
                                    <label>退货原因：</label>
                                    <textarea class="form-control" name="reason" >${entity.note}</textarea>
                                </div>
                            </div>

                        </div>
                    <button type="button" class="btn green js_save_btn" data-action="save">退货</button>
                    <a type="button" class="btn default" href="<c:url value='/admin/inventory/receipt/index'/>">返回列表</a>
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

    $(function () {

        var dataObj = {id:'${prItemDelivery.id}',materialId:'${prItemDelivery.mid}',specificationId:'${prItemDelivery.specificationId}',balanceNum:'${prItemDelivery.balanceNum}',deliveryNum:'${prItemDelivery.num}',deliveryId:'${prItemDelivery.deliveryId}'};

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
            if (_name=="returnedUser"){
                dataObj.returnedUserName = evt.params.data.text;
            }
        });


        $('input[name=returnedDate]').datepicker({
            todayBtn : "linked",
            autoclose : true,
            language: 'zh-CN',
            format : "yyyy-mm-dd",
            todayHighlight : true,
            endDate : new Date()
        });

        var validateForm = $('form.js_base_form');
        validateForm.validate({
            errorElement : 'span',
            errorClass : 'help-block help-block-error',
            rules : {
                returnedUser : {
                    required : true
                },num : {
                    required : true
                },returnedDate : {
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

        var _index=null;
        $('button.js_save_btn').on('click',function () {

            var _num = $("input[name=num]").val();
            if (_num <= 0 || _num > ${prItemDelivery.balanceNum}) {
                layer.alert("请输入正确的退货数量!",{"icon":0});
                return;
            }

            if(validateForm.valid()){
                var objArr = $('form.js_base_form').serializeArray();
                for(var e in objArr){
                    var  obj = objArr[e];
                    dataObj[obj.name] = obj.value;
                }
                var url = '<c:url value="/admin/inventory/rejected/insert"/>';

                layer.confirm("确定提交？",function (c) {
                    layer.close(c);
                    _index = layer.load(2);
                    $.post(url, dataObj, function (result) {
                        layer.close(_index);
                        if (result.success) {
                            layer.msg('操作成功',{time:800},function () {
                                window.location.href='<c:url value="/admin/inventory/receipt/index"/>';
                            });
                        }else{
                            layer.msg('操作失败：'+result.rmsg,{time:2000});
                        }
                    }, 'json');
                });
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
            var _sumPrice = parseFloat($('input[name=sumPrice]').val());
            if (!isNaN(_taxRate) && !isNaN(_sumPrice)){
                $('input[name=tax]').val(parseFloat(_taxRate/100*_sumPrice).toFixed(2));
            }
        }

        /**
         * 计算不含税单价
         */
        var calculationExcTaxPrice = function () {
            var _price = parseFloat($('input[name=price]').val());
            var _taxRate = parseFloat($('input[name=taxRate]').val());
            if (!isNaN(_price) && !isNaN(_taxRate)){
                $('input[name=excTaxPrice]').val(parseFloat(_price*(1-_taxRate/100)).toFixed(2));
            }
        }

        /**
         * 计算不含税总金额
         */
        var calculationExcTaxSumPrice = function () {
            var _sumPrice = parseFloat($('input[name=sumPrice]').val());
            var _tax = parseFloat($('input[name=tax]').val());
            if (!isNaN(_sumPrice) && !isNaN(_tax)){
                $('input[name=excTaxSumPrice]').val(_sumPrice-_tax);
            }
        }
        calculationSumPrice();
        calculationTax();
        calculationExcTaxSumPrice();

        $('input[name=num]').on('blur',function () {
            calculationSumPrice();
            calculationTax();
            calculationExcTaxPrice();
            calculationExcTaxSumPrice();
        });

    });

</script>


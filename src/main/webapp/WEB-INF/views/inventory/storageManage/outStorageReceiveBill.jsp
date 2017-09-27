<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<link href="<c:url value="/resources/admin/global/plugins/font-awesome/css/font-awesome.min.css" />" rel="stylesheet" type="text/css" />
<link href="<c:url value="/resources/admin/global/plugins/simple-line-icons/simple-line-icons.min.css" />" rel="stylesheet" type="text/css" />
<link href="<c:url value="/resources/admin/global/plugins/bootstrap/css/bootstrap.min.css" />" rel="stylesheet" type="text/css" />
<link href="<c:url value="/resources/admin/global/plugins/uniform/css/uniform.default.css" />" rel="stylesheet" type="text/css" />
<link href="<c:url value="/resources/admin/global/plugins/bootstrap-switch/css/bootstrap-switch.min.css" />" rel="stylesheet" type="text/css" />

<link href="<c:url value="/resources/admin/global/css/components.min.css "/>" rel="stylesheet" id="style_components" type="text/css">
<link href="<c:url value="/resources/admin/global/css/plugins.min.css" />" rel="stylesheet" type="text/css" />

<link type="text/css" href="/resources/admin/global/plugins/select2-4.0.3/css/select2.min.css"  rel="stylesheet">
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/admin/global/plugins/bootstrap-datepicker/css/bootstrap-datepicker3.min.css" />"/>
<style>
    .datepicker-dropdown{
        z-index: 9999;
    }
</style>

<div class="portlet  light ">
    <div class="portlet-title">
        <div class="caption">出库收票管理</div>
    </div>
    <div class="portlet-body">
        <div class="row">
            <div class="col-md-12">
                <div class="form-body">
                    <form  role="form" class="js_base_form" id="materialAddForm">
                    <div class="row">
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>收票人：</label>
                                <select class="form-control js_select2" name="oldBillReceiver">
                                    <option value="">请选择或填写</option>
                                    <c:forEach items="${person}" var="p">
                                        <option value="${p.name}" <c:if test="${entity.billReceiver eq p.name}">selected</c:if>>${p.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>收票日期：</label>
                                <c:choose>
                                    <c:when test="${empty entity.billReceiveDate}">
                                        <input type="text" class="form-control" name="billReceiveDate" value="<fmt:formatDate value="${now}" pattern="yyyy-MM-dd"/>">
                                    </c:when>
                                    <c:otherwise>
                                        <input type="text" class="form-control" name="billReceiveDate" value="<fmt:formatDate value="${entity.billReceiveDate}" pattern="yyyy-MM-dd"/>">
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </div>
                    </form>
                    <div class="row">
                        <div class="col-md-12" style="min-height: 58%">
                            <table class="table table-width table-striped table-bordered table-advance table-hover table-checkable " id="datatable_ajax">
                                <thead>
                                <tr role="row" class="heading">
                                    <th data-role="html">序号</th>
                                    <th data-role="html">材料名称</th>
                                    <th data-role="html">规格</th>
                                    <th data-role="html">计量单位</th>
                                    <th width="8%" data-name="use" data-role="text">用途</th>
                                    <th width="8%" data-name="num" data-role="text">出库数量</th>
                                    <th width="8%" data-name="price" data-role="text">出库单价</th>
                                    <th data-name="tax" data-role="text" data-readonly="readonly">出库税额</th>
                                    <th width="8%"data-name="excTaxPrice" data-role="text" data-readonly="readonly">出库不含税单价</th>
                                    <th width="8%" data-name="excTaxSumPrice" data-role="text" data-readonly="readonly">出库不含税金额</th>
                                    <th width="8%" data-name="sumPrice" data-role="text" data-readonly="readonly">出库含税金额</th>
                                    <th data-name="taxRate" data-role="text" data-readonly="readonly">税率</th>
                                    <th data-role="html">结存数量</th>
                                    <th data-role="html">结存不含税金额</th>
                                    <th data-role="html">结存含税金额</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${list}" var="outStorageList" varStatus="step">
                                    <tr>
                                        <td>${step.count}</td>
                                        <td>${outStorageList.materialName}</td>
                                        <td>${outStorageList.specification}</td>
                                        <td>${outStorageList.unit}</td>
                                        <td>${outStorageList.use}</td>
                                        <td data-name="num" data-value="${outStorageList.num}">${outStorageList.num}<c:set var="num" value="${num+outStorageList.num}"/></td>
                                        <td>${outStorageList.price}</td>
                                        <td data-name="tax" data-value="${outStorageList.tax}">${outStorageList.tax}</td>
                                        <td>${outStorageList.excTaxPrice}</td>
                                        <td data-name="excTaxSumPrice" data-value="${outStorageList.excTaxSumPrice}">${outStorageList.excTaxSumPrice}<c:set var="excTaxSumPrice" value="${excTaxSumPrice+outStorageList.excTaxSumPrice}"/></td>
                                        <td data-name="sumPrice" data-value="${outStorageList.sumPrice}">${outStorageList.sumPrice}<c:set var="sumPrice" value="${sumPrice+outStorageList.sumPrice}"/></td>
                                        <td>${outStorageList.taxRate}</td>
                                        <td>${outStorageList.balanceNum}</td>
                                        <td>${outStorageList.balanceExcTaxSumPrice}</td>
                                        <td>${outStorageList.balanceSumPrice}</td>
                                    </tr>
                                </c:forEach>
                                <tr class="js_sum">
                                    <td colspan="5"></td>
                                    <td role="num">${num}</td>
                                    <td></td>
                                    <td role="tax">${sumPrice - excTaxSumPrice}</td>
                                    <td></td>
                                    <td role="excTaxSumPrice">${excTaxSumPrice}</td>
                                    <td role="sumPrice">${sumPrice}</td>
                                    <td colspan="5"></td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>

                    <button type="button" class="btn green js_save_btn" data-action="save">保存</button>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="/resources/admin/global/plugins/jquery.min.js" type="text/javascript"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/select2-4.0.3/js/select2.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/jquery-validation/js/jquery.validate.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/jquery-validation/js/additional-methods.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/jquery-validation/js/messages_zh.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.zh-CN.js" />"></script>
<script src="<c:url value="/resources/admin/global/plugins/layer/layer.js"/>"></script>


<script>


    $(function () {
        var dataObj ={id:'${id}',billReceiver:'${entity.billReceiver}'};

        $('select.js_select2').select2({
            tags: true,
            createTag: function (params) {
                var term = $.trim(params.term);
                if (term === '') {
                    return null;
                }
                return {
                    id: "-1",
                    text: term,
                    title:term
                }
            }
        }).on('select2:select', function (evt) {
            var _name =  $(this).attr('name');
            if (_name=="oldBillReceiver"){
                dataObj.billReceiver = evt.params.data.text;
            }
        });

        $('input[name=billReceiveDate]').datepicker({
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
                oldBillReceiver : {
                    required : true
                },billReceiveDate : {
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


        var validate = function (obj) {
            if(obj.attr('validate')!=undefined){
                if (obj.find('input').val() == ''){
                    if (!obj.hasClass('has-error')){
                        obj.addClass('has-error').append('<span class="help-block help-block-error">这是必填字段</span>');
                    }
                    return false;
                }else{
                    obj.removeClass('has-error').children('span').remove();
                    return true;
                }
            }
            return true;
        }

        /**
         * 验证
         */
        $('#datatable_ajax').on('blur','input',function () {
            validate($(this).parent());
        });

        var _index=null;
        $('button.js_save_btn').on('click',function () {
            if(validateForm.valid()){
                dataObj['oldBillReceiver'] = $('select[name=oldBillReceiver]').val();
                dataObj['billReceiveDate'] = $('input[name=billReceiveDate]').val();
                layer.confirm("确定提交？",function (c) {
                    layer.close(c);
                    _index = layer.load(2);
                    $.post('<c:url value="/admin/inventory/toReceiveBill/saveBillReceiveInfo/1"/>', dataObj, function (result) {
                        layer.close(_index);
                        if (result.success) {
                            layer.msg('操作成功',{time:800});
                        }else{
                            layer.msg('操作失败：'+result.rmsg,{time:2000});
                        }
                    }, 'json');
                });
            }
        });

    });

</script>


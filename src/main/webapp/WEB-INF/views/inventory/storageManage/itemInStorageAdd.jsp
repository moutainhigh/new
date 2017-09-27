<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<link type="text/css" href="/resources/admin/global/plugins/select2-4.0.3/css/select2.min.css"  rel="stylesheet">
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/admin/global/plugins/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" />" media="screen"/>


<div class="portlet box blue-hoki">
    <div class="portlet-title">
        <div class="caption">入库管理</div>
    </div>
    <div class="portlet-body">

        <div class="row">
            <div class="col-md-12 ">
                <div class="form-body">
                    <form role="form" class="js_base_form">
                    <div class="row">
                        <div class="col-md-2">
                            <div class="form-group">
                                <label>发票号：</label>
                                <input type="text" class="form-control" name="billNum" value="${billNum}">
                            </div>
                        </div>
                        <div class="col-md-2">
                            <div class="form-group">
                                <div class="input-group date form_date" data-date="" data-date-format="yyyy-mm-dd" data-link-field="startTime" data-link-format="yyyy-mm-dd">
                                    <label>入库日期：</label>
                                    <input class="form-control" name="inStoreDate" size="16" type="text" placeholder="入库日期" value="<fmt:formatDate value="${now}" pattern="yyyy-MM-dd"/>">
                                </div>
                            </div>
                        </div>
                        <div class="col-md-2">
                            <div class="form-group">
                                <label>库管员：</label>
                                <select class="form-control js_person" name="repositoryPerson">
                                    <option value="">请选择或填写</option>
                                    <c:forEach items="${person1}" var="p">
                                        <option value="${p.name}">${p.name}</option>
                                    </c:forEach>
                                </select>
                                <input type="hidden" name="repositoryId" value="${list.get(0).repositoryId}"/>
                                <input type="hidden" name="repositoryName" value="${list.get(0).repositoryName}"/>
                                <input type="hidden" name="inStoreUser" value="${list.get(0).inStoreUser}"/>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-2">
                            <div class="form-group">
                                <label>供货商：</label>
                                <input type="text" class="form-control" name="itemProviderName" value="${list.get(0).itemProviderName}" readonly>
                            </div>
                        </div>
                        <div class="col-md-2">
                            <div class="form-group">
                                <label>入库库房：</label>
                                <input type="text" class="form-control" name="repositoryName" value="${list.get(0).repositoryName}" readonly>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="form-group">
                                <label>备注：</label>
                                <input type="text" class="form-control" name="remark">
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <table class="table table-striped table-bordered table-advance table-hover " id="datatable_ajax">
                                <thead>
                                <tr role="row" class="heading">
                                    <th>序号</th>
                                    <th>材料名称</th>
                                    <th>规格</th>
                                    <th>计量单位</th>
                                    <th>收货数量</th>
                                    <th>收货单价</th>
                                    <th>收货税率</th>
                                    <th>收货税额</th>
                                    <th>收货不含税金额</th>
                                    <th>收货含税金额</th>
                                    <th>入库数量</th>
                                    <th>入库单价</th>
                                    <th>税率（%）</th>
                                    <th>税额</th>
                                    <th>入库不含税金额</th>
                                    <th>入库含税金额</th>
                                    <th>价量来源</th>
                                    <th>备注</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${list}" var="itemDelivery" varStatus="m">
                                    <tr>
                                        <td>
                                                ${m.count}
                                            <input type="hidden" value="${itemDelivery.mid}"/>
                                            <input type="hidden" name="deliveryId" value="${itemDelivery.deliveryId}"/>
                                        </td>
                                        <td>${itemDelivery.materialName}</td>
                                        <td>${itemDelivery.specification}</td>
                                        <td>${itemDelivery.unit}</td>
                                        <td>${itemDelivery.num}</td>
                                        <td>${itemDelivery.price}</td>
                                        <td>${itemDelivery.taxRate}</td>
                                        <td>${itemDelivery.tax}<c:set var="tax" value="${tax+itemDelivery.tax}"/></td>
                                        <td>${itemDelivery.excTaxSumPrice}<c:set var="excTaxSumPrice" value="${excTaxSumPrice+itemDelivery.excTaxSumPrice}"/></td>
                                        <td>${itemDelivery.sumPrice}<c:set var="sumPrice" value="${sumPrice+itemDelivery.sumPrice}"/></td>
                                        <td>
                                            <input type="text" name="num" class="form-control" value="${itemDelivery.balanceNum}"/>
                                            <input type="hidden" name="hiddenBalanceNum" value="${itemDelivery.balanceNum}"/>
                                        </td>
                                        <td><input type="text" class="form-control" name="price" value="${itemDelivery.price}"/></td>
                                        <td><input type="text" class="form-control" name="taxRate" value="${itemDelivery.taxRate}"/></td>
                                        <td><input type="text" class="form-control" readonly="readonly" name="rateMoney" value="${itemDelivery.inStorageTax}"/></td>
                                        <td><input type="text" class="form-control" readonly="readonly" name="moneyNoRate" value="${itemDelivery.inStorageExcTaxSumPrice}"/></td>
                                        <td><input type="text" class="form-control" readonly="readonly" name="sumPrice" value="${itemDelivery.inStorageSumPrice}"/></td>
                                        <td><input type="text" class="form-control" name="priceSrc"/></td>
                                        <td><input type="text" class="form-control" name="remark"/></td>
                                    </tr>
                                </c:forEach>
                                <tr class="js_sum">
                                    <td colspan="13"></td>
                                    <td role="tax">${tax}</td>
                                    <td role="excTaxSumPrice">${excTaxSumPrice}</td>
                                    <td role="sumPrice">${sumPrice}</td>
                                    <td colspan="2"></td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    </form>

                    <button type="button" name="inStorageSubmit" class="btn green">确认入库</button>
                    <a type="button" class="btn default" href="<c:url value='/admin/inventory/receipt/index'/>">返回列表</a>
                </div>
            </div>
        </div>

    </div>
</div>

<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/select2-4.0.3/js/select2.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/jquery-validation/js/jquery.validate.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/jquery-validation/js/additional-methods.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/jquery-validation/js/messages_zh.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.zh-CN.js" />"></script>

<script>

    $(function () {
    //初始化时间
    $('input[name=inStoreDate]').datetimepicker({
        format:"yyyy-mm-dd",
        language:  'zh-CN',
        weekStart: 1,
        todayBtn:  1,
        autoclose: 1,
        todayHighlight: 1,
        startView: 2,
        minView: 2,
        forceParse: 0
    });
    //将表格数据转json字符串
    var encodeStoreData = function (obj) {
        var _empArr = new Array();
        $('tr.js_sum').prevAll().each(function (k, v) {
            var obj = {};
            obj.deliveryId = $(v).find('td:eq(0) input:eq(1)').val();
            obj.num = $(v).find('td:eq(10) input').val();
            obj.price = $(v).find('td:eq(11) input').val();
            obj.inStorageMoney=$(v).find('td:eq(15) input').val();
            obj.taxRate=$(v).find('td:eq(12) input').val();
            obj.rateMoney=$(v).find('td:eq(13) input').val();
            obj.moneyNoRate=$(v).find('td:eq(14) input').val();
            obj.priceSrc=$(v).find('td:eq(16) input').val();
            obj.remark=$(v).find('td:eq(17) input').val();
            _empArr.push(obj);
        });
        return _empArr;
    }

    var validate = function (obj) {
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

    var validateForm = $('form.js_base_form');
    validateForm.validate({
        errorElement : 'span',
        errorClass : 'help-block help-block-error',
        rules : {
            billNum: {
                required : true
            },itemProviderName: {
                required : true
            },inStoreDate: {
                required : true
            },repositoryPerson: {
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

    var data = {};
    var resp = false;
    $('button[name=inStorageSubmit]').on('click',function () {
        var _arr = encodeStoreData();
        if(_arr.length==0){
            return;
        }

        var _taxRate = $("input[name=taxRate]");
        for (var i = 0;i<_taxRate.length;i++) {
            var taxRate = _taxRate[i].value;
            if (taxRate == "") {
                layer.msg("税率不能为空");
                return;
            }
        }

        var _num = $("input[name=num]");
        for (var i = 0;i<_num.length;i++) {
            var num = _num[i].value;
            if (num == "" || isNaN(num) || num <= 0) {
                layer.msg("入库数量不能为空");
                return;
            }
        }

        var _price = $("input[name=price]");
        for (var i = 0;i<_price.length;i++) {
            var price = _price[i].value;
            if (price == "" || isNaN(price)) {
                layer.msg("入库单价不能为空");
                return;
            }
        }
        data.inStorageListStr=JSON.stringify(_arr);
        data.billNum = $('input[name=billNum]').val();
        data.remark = $('input[name=remark]').val();
        data.inStoreDate = $('input[name=inStoreDate]').val();
        data.repositoryPerson = $('select[name=repositoryPerson]').val();
        data.repositoryId = $('input[name=repositoryId]').val();
        if(validateForm.valid()){
            layer.confirm("确定提交？",function (c) {
                if(!resp){
                    resp = true;
                    var _index = layer.load(2);
                    layer.close(c);
                    $.post('<c:url value="/admin/inventory/inStorage/inStorageSubmit"/>', data, function (result) {
                        resp = false;
                        layer.close(_index);
                        if (result.success){
                            window.location.href="/admin/inventory/InStorage/itemInStorageDetail/"+result.rdata;
                        }else {
                            layer.msg(result.rmsg)
                        }
                    }, 'json');
                }
            });
        }
    });



    /**
     * 计算合计
     */
    var calculate = function () {
        var _allTax =0;
        var _allExcTaxSumPrice = 0;
        var _allSumPrice = 0;
        $('tr.js_sum').prevAll().each(function (k,v) {
            var _v = $(v);
            var _tax = parseFloat(_v.find('input[name=rateMoney]').val());
            var _sumPrice = parseFloat(_v.find('input[name=sumPrice]').val());
            var _excTaxSumPrice = parseFloat(_v.find('input[name=moneyNoRate]').val());
            _allTax+= !isNaN(_tax)?_tax:0;
            _allSumPrice += !isNaN(_sumPrice)?_sumPrice:0;
            _allExcTaxSumPrice += !isNaN(_excTaxSumPrice)?_excTaxSumPrice:0;
        });
        var _sumTr = $('tr.js_sum');
        _sumTr.find('td[role="tax"]').text(_allTax.toFixed(4));
        _sumTr.find('td[role="sumPrice"]').text(_allSumPrice.toFixed(2));
        _sumTr.find('td[role="excTaxSumPrice"]').text(_allExcTaxSumPrice.toFixed(2));
        _sumTr.removeClass('hide');
    }

    //输入税率
    $('input[name=taxRate]').on('blur',function () {
        var _this =$(this);
        var _tr =_this.parents('tr');
       var taxRate = parseInt(_this.val());
       if (!isNaN(taxRate)) {
           if (taxRate > 100 || taxRate <= 0 ) {
               _this.val('');
               return;
           }
           _this.val(taxRate);
           var inStorageMoney = parseFloat(_tr.find('input[name=sumPrice]').val());
           var moneyNoRate = inStorageMoney/(1+taxRate*0.01);
           var rateMoney = moneyNoRate*taxRate*0.01;
           _tr.find('input[name=moneyNoRate]').val(moneyNoRate.toFixed(2));
           _tr.find('input[name=rateMoney]').val(rateMoney.toFixed(2));
           calculate();
       }else{
           _this.val('');
       }
    });

    $('input[name=price]').on('blur',function () {
        var _this = $(this);
        var _tr = _this.parents('tr');
        var num = parseFloat(_tr.find('input[name=num]').val());
        var price = parseFloat(_this.val());
        if (!isNaN(num)&&!isNaN(price)) {
            var inStorageMoney = num * price;
            _tr.find('input[name=sumPrice]').val(inStorageMoney.toFixed(2));
        }else{
            _this.val('');
            return;
        }
        var taxRate =  parseInt(_tr.find('input[name=taxRate]').val());
        var moneyNoRate = inStorageMoney/(1+taxRate*0.01);
        var rateMoney = moneyNoRate*taxRate*0.01;
        _tr.find('input[name=moneyNoRate]').val(moneyNoRate.toFixed(2));
        _tr.find('input[name=rateMoney]').val(rateMoney.toFixed(2));
        calculate();
    });


    $('input[name=num]').on("blur",function () {
        var _this = $(this);
        var _tr = _this.parents('tr');
        var num =  parseFloat(_this.val());
        var price = parseFloat(_tr.find('input[name=price]').val());
        if(!isNaN(num)&&!isNaN(price)) {
            var _num = parseFloat(_tr.find('input[name=hiddenBalanceNum]').val());
            if (num > _num) {
                layer.msg("您输入的入库数量大于了余存数量!");
                _this.val("");
                return;
            }
            _this.val(num.toFixed(4));
            var inStorageMoney = num * price;
            _tr.find('input[name=sumPrice]').val(inStorageMoney.toFixed(2));
            var taxRate =  parseInt(_tr.find('input[name=taxRate]').val());
            var moneyNoRate = inStorageMoney/(1+taxRate*0.01);
            var rateMoney = moneyNoRate*taxRate*0.01;
            _tr.find('input[name=moneyNoRate]').val(moneyNoRate.toFixed(2));
            _tr.find('input[name=rateMoney]').val(rateMoney.toFixed(2));
            calculate();
        }else{
            _this.val('');
        }
    });


    $('select.js_person').select2({
        tags: true,
        createTag: function (params) {
            var term = $.trim(params.term);
            if (term === '') {
                return null;
            }
            return {
                id:"-1",
                text: term
            }
        }
    }).on('select2:select', function (evt) {
        var _name =  $(this).attr('name');
        if (_name=="repositoryPerson"){
            data.inStoreUser = evt.params.data.text;
        }
    });

    $('input[name=num]').trigger('blur');

});


</script>
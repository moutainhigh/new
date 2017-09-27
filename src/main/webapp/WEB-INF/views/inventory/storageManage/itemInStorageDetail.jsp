<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<link type="text/css" href="/resources/admin/global/plugins/select2-4.0.3/css/select2.min.css"  rel="stylesheet">

<div class="portlet light">
    <div class="portlet-title">
        <div class="caption">
            <i class="icon-layers font-green"></i>
            <span class="caption-subject font-green sbold uppercase">入库</span>
        </div>
    </div>
    <form role="form" class="js_base_form">
    <div class="row">
        <div class="form-group col-md-2">
            <label>入库单号：</label>
            <input type="text" name="inStorageNum" class="form-control" value="${inStorage.inStorageNum}" readonly>
        </div>

        <div class="form-group col-md-2">
            <label>仓库：</label>
            <input type="text" name="repositoryName" class="form-control"  value="${list.get(0).repositoryName}" readonly>
        </div>

        <div class="form-group col-md-2">
            <label>供货商：</label>
            <input type="text" name="itemProvider" class="form-control"  value="${inStorage.itemProviderName}" readonly>
        </div>

        <div class="form-group col-md-2">
            <label>库管员：</label>
            <select class="form-control js_person" name="repositoryPerson">
                <option value="">请选择或填写</option>
                <c:forEach items="${person1}" var="p">
                    <option value="${p.name }" <c:if test="${p.name eq inStorage.inStoreUser}">selected</c:if>>${p.name}</option>
                </c:forEach>
            </select>
        </div>
    </div>
    <div class="row">
        <div class="form-group col-md-2">
            <label>入库日期：</label>
            <input class="form-control" id="inStoreDate" name="inStoreDate" size="16" type="text" value="<fmt:formatDate value="${inStorage.inStoreDate}" pattern="yyyy-MM-dd"/>">
        </div>

        <div class="form-group col-md-2">
            <label>发票号：</label>
            <input type="text" name="billNum" class="form-control"  value="${inStorage.billNum}">
        </div>

        <div class="form-group col-md-4">
            <label>备注：</label>
            <input type="text" name="remark" class="form-control"  value="${inStorage.remark}">
        </div>

    </div>
    </form>

    <div id="myTabContent" class="tab-content">
        <div class="tab-pane fade in active" id="home">
            <div class="portlet-body">
                <div class="row">
                    <div class="col-md-12">
                        <table class="table table-width table-striped table-bordered table-advance table-hover table-checkable " id="datatable_ajax">
                            <thead>
                            <tr role="row" class="heading">
                                <th width="2%"  data-role="html">
                                    序号
                                </th>
                                <th width="4%" data-role="html">材料名称</th>
                                <th width="4%" data-role="html">规格</th>
                                <th data-role="html">计量单位</th>
                                <th data-role="html">收货数量</th>
                                <th data-role="html">收货单价</th>
                                <th data-role="html">收货税率</th>
                                <th data-role="html">收货税额</th>
                                <th data-role="html">收货不含税金额</th>
                                <th data-role="html">收货含税金额</th>
                                <th data-role="text" data-name="num">入库数量</th>
                                <th data-role="text" data-name="price">入库单价</th>
                                <th data-role="text" data-name="taxRate">税率（%）</th>
                                <th data-role="text" data-name="rateMoney" data-readonly="readonly">税额</th>
                                <th data-role="text" data-name="moneyNoRate" data-readonly="readonly">不含税金额</th>
                                <th data-role="text" data-name="inStorageMoney" data-readonly="readonly">入库含税金额</th>
                                <th data-role="text" data-name="priceSrc">价量来源</th>
                                <th data-role="text" data-name="remark">备注</th>
                                <th data-role="operate" width="9%">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${list}" var="InStorageList" varStatus="step">
                                    <tr>
                                        <td>${step.count}</td>
                                        <td>${InStorageList.materialName}</td>
                                        <td>${InStorageList.specification}</td>
                                        <td>${InStorageList.unit}</td>
                                        <td>${InStorageList.deliveryNum}<c:set var="deliveryNum" value="${deliveryNum+InStorageList.deliveryNum}"/></td>
                                        <td>${InStorageList.deliveryPrice}</td>
                                        <td>${InStorageList.deliveryTaxRate}</td>
                                        <td>${InStorageList.deliveryTax}<c:set var="deliveryTax" value="${deliveryTax+InStorageList.deliveryTax}"/></td>
                                        <td>${InStorageList.deliveryExcTaxSumPrice}<c:set var="deliveryExcTaxSumPrice" value="${deliveryExcTaxSumPrice+InStorageList.deliveryExcTaxSumPrice}"/></td>
                                        <td>${InStorageList.deliverySumPrice}<c:set var="deliverySumPrice" value="${deliverySumPrice+InStorageList.deliverySumPrice}"/></td>
                                        <td>${InStorageList.num}<c:set var="num" value="${num+InStorageList.num}"/></td>
                                        <td>${InStorageList.price}</td>
                                        <td>${InStorageList.taxRate}<c:set var="taxRate" value="${taxRate+InStorageList.taxRate}"/></td>
                                        <td>${InStorageList.rateMoney}<c:set var="rateMoney" value="${rateMoney+InStorageList.rateMoney}"/></td>
                                        <td>${InStorageList.moneyNoRate}<c:set var="moneyNoRate" value="${moneyNoRate+InStorageList.moneyNoRate}"/></td>
                                        <td>${InStorageList.inStorageMoney}<c:set var="inStorageMoney" value="${inStorageMoney+InStorageList.inStorageMoney}"/></td>
                                        <td>${InStorageList.priceSrc}</td>
                                        <td>${InStorageList.remark}</td>
                                        <td><button type="button" class="btn btn-sm btn-primary js_edit" data-role="edit" data-id="${InStorageList.id}"  data-pid ="${InStorageList.inStorageId}" data-did="${InStorageList.deliveryId}">编辑</button></td>
                                    </tr>
                                </c:forEach>
                                <tr>
                                    <td></td>
                                    <td></td>
                                    <td>${specification}</td>
                                    <td>${unit}</td>
                                    <td>${deliveryNum}</td>
                                    <td></td>
                                    <td>${deliveryTaxRate}</td>
                                    <td>${deliveryTax}</td>
                                    <td>${deliveryExcTaxSumPrice}</td>
                                    <td>${deliverySumPrice}</td>
                                    <td>${num}</td>
                                    <td></td>
                                    <td>${taxRate}</td>
                                    <td>${rateMoney}</td>
                                    <td>${moneyNoRate}</td>
                                    <td>${inStorageMoney}</td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <br/>
        <div class="row">
            <div class="form-group col-md-1">
                <a href="<c:url value="/admin/inventory/inStorage/toInStorageList"/>" class="btn btn-default" >返回列表</a>
            </div>

            <div class="form-group col-md-1">
                <input type="button" name="toPrintPage" class="btn btn-default" value="打印">
            </div>
            <div class="form-group col-md-1">
                <button type="button" class="form-control js_save_btn">保存</button>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/select2-4.0.3/js/select2.min.js" />"></script>
<script>
    $(function () {

        $('input[name=toPrintPage]').on('click', function () {
            $('#printForm').remove();
            var _form = $('<form>');
            _form.attr('action','<c:url value="/admin/inventory/inStorage/print/${inStorage.id}"/>');
            _form.attr('id','printForm');
            _form.attr('method','POST');
            _form.attr('target','_blank');
            $(document).find('body').append(_form);
            $('#printForm').submit();
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
                updateObj.inStoreUser = evt.params.data.text;
            }
        });

        var updateObj ={id:'${inStorage.id}'};
        $('button.js_save_btn').on('click',function () {
            var objArr = $('form.js_base_form').serializeArray();
            for(var e in objArr){
                var  obj = objArr[e];
                updateObj[obj.name] = obj.value;
            };
            layer.confirm("确定提交？",function (c) {
                layer.close(c);
                var _index = layer.load(2);
                $.post('<c:url value="/admin/inventory/inStorage/updateInStorage"/>', updateObj, function (result) {
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
        });

        var dataObj;
        $('#datatable_ajax').on('click','button.js_edit',function () {
            var _this = $(this);
            var _action = _this.data('role');
            if (_action == 'edit'){
                $('div.js_save_group').addClass('hide');
                $('button.js_search ').addClass('hide');
                dataObj = {};
                dataObj.id = _this.data('id');
                dataObj.inStorageId = _this.data('pid');
                dataObj.deliveryId = _this.data('did');
                addRow(_this);
            }else if(_action == 'save'){
                var _tr =_this.parents('tr');
                _this.parents('tr').find('input').each(function (k, v) {
                    dataObj[v.name] = v.value;
                });
                if(dataObj.num=='' || isNaN(dataObj.num) || dataObj.price=='' || isNaN(dataObj.price) || dataObj.taxRate=='' || isNaN(dataObj.taxRate)){
                    layer.msg('请输入正确的单价、数量、税率');
                    return;
                }
                layer.confirm("确定要修改？",function (_index) {
                    layer.close(_index);
                    $.ajax({
                        type: 'POST',
                        url: '<c:url value="/admin/inventory/inStorage/updateInStorageDetail"/>',
                        async: false,
                        data: dataObj,
                        dataType: "json",
                        success: function (result) {
                            if(result.success){
                                _tr.prev().show();
                                _tr.remove();
                                window.location.reload();
                            }else{
                                layer.msg(result.rmsg);
                            }
                        }
                    });
                });
            }
        });


        /**
         * 取消编辑
         */
        $('#datatable_ajax').on('click','button.js_cancel',function () {
            var _tr = $(this).parents('tr');
            _tr.prev().show();
            _tr.remove();
        });

        var addRow = function (_this) {
            var _parentTr = _this.parents('tr');
            var tr = $('<tr>');
            var tb = $('#datatable_ajax');
            tb.find('tr:eq(0) th').each(function (k, v) {
                var validate = $(v).data('validate');
                var _type = $(v).data('role');
                var name = $(v).data('name');
                var _readonly = $(v).data('readonly');
                var td;
                if (_type == 'text' ){
                    td = $('<td data-name="'+(name == undefined ? '' : name)+'"><input type="text" value="" class="form-control ' + (validate == undefined ? '' : validate) + '" name="' + (name == undefined ? '' : name) + '"></td>');
                    var _val = _parentTr.find('td').eq(k).text();
                    td.data('value',_val).find('input').val(_val);
                    if(_readonly == 'readonly'){
                        td.find('input').attr('readonly','readonly');
                    }
                }else if (_type == 'html'){
                    td = $('<td>').text(_parentTr.find('td').eq(k).text());
                }else if (_type == 'operate'){
                    td = $('<td><button class="btn btn-sm red js_edit" data-role="save">保存</button><button class="btn btn-sm js_cancel">取消</button></td>');
                }else{
                    td= $('<td>');
                }
                tr.append(td);
            });
            _parentTr.hide().after(tr);
            return tr;
        };

        /**
         * 数量 单价 输入框
         */
        $('#datatable_ajax').on('blur','input[name=num]',function () {
            var _tr = $(this).parents('tr');
            var _price =parseFloat(_tr.find('input[name=price]').val());
            var _taxRate =parseFloat(_tr.find('input[name=taxRate]').val());
            var _value = parseFloat($(this).val());
            if (!isNaN(_value)&&!isNaN(_price)&&!isNaN(_taxRate)){
                $(this).val(_value.toFixed(4));
            }else{
                return;
            }
            var _sumPrice = _value*parseFloat(_price);
            _tr.find('input[name=inStorageMoney]').val(_sumPrice.toFixed(2));
            var _excTaxSumPrice =_sumPrice/(1+_taxRate*0.01);
            var _tax =_excTaxSumPrice*_taxRate*0.01;
            _tr.find('input[name=moneyNoRate]').val(_excTaxSumPrice.toFixed(2));
            _tr.find('input[name=rateMoney]').val(_tax.toFixed(2));
        }).on('blur','input[name=price]',function () {
            var _tr = $(this).parents('tr');
            var _num =parseFloat(_tr.find('input[name=num]').val());
            var _taxRate =parseFloat(_tr.find('input[name=taxRate]').val());
            var _this = $(this);
            var _value = parseFloat(_this.val());
            if (!isNaN(_value)&&!isNaN(_num)&&!isNaN(_taxRate)){
                _this.val(_value.toFixed(2));
            }else{
                return;
            }
            var _sumPrice = _value*_num;
            _tr.find('input[name=inStorageMoney]').val(_sumPrice.toFixed(2));
            var _excTaxSumPrice =_sumPrice/(1+_taxRate*0.01);
            var _tax =_excTaxSumPrice*_taxRate*0.01;
            _tr.find('input[name=moneyNoRate]').val(_excTaxSumPrice.toFixed(2));
            _tr.find('input[name=rateMoney]').val(_tax.toFixed(2));
        }).on('blur','input[name=taxRate]',function () {
            var _this = $(this);
            var _tr = _this.parents('tr');
            var _sumPrice =parseFloat(_tr.find('input[name=inStorageMoney]').val());
            var _value = parseFloat(_this.val());
            if (!isNaN(_value)&&!isNaN(_sumPrice)){
                _this.val(_value);
                var _excTaxSumPrice =_sumPrice/(1+_value*0.01);
                var _tax =_excTaxSumPrice*_value*0.01;
                _tr.find('input[name=moneyNoRate]').val(_excTaxSumPrice.toFixed(2));
                _tr.find('input[name=rateMoney]').val(_tax.toFixed(2));
            }
        });


    });

</script>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<link type="text/css" href="/resources/admin/global/plugins/select2-4.0.3/css/select2.min.css"  rel="stylesheet">
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/admin/global/plugins/bootstrap-datepicker/css/bootstrap-datepicker3.min.css" />"/>

<div class="portlet box blue-hoki">
    <div class="portlet-title">
        <div class="caption">出库详情</div>
    </div>
    <div class="portlet-body">
        <form  role="form" class="js_base_form">
        <div class="row">
            <div class="col-md-12">
                <div class="col-md-3 form-group">
                    <label>出库单号：</label>
                    <input type="text"name="outStorageNo" class="form-control" value="${prItemOutStorage.outStorageNo}" readonly>
                    <input type="hidden" name="outStorageId" value="${prItemOutStorage.id}"/>
                </div>
                <div class="col-md-3 form-group">
                    <label>库房名称：</label>
                    <input type="text" id="repositoryName" name="repositoryName" class="form-control"  value="${prItemOutStorage.repositoryName}" readonly>
                </div>

                <div class="col-md-3 form-group">
                    <label>项目：</label>
                    <input type="text" id="projectName" name="projectName" class="form-control"  value="${prItemOutStorage.projectName}" readonly>
                </div>

                <div class="col-md-3 form-group">
                    <label>库管员：</label>
                    <select class="form-control js_select2" name="checkUser">
                        <option value="">请选择或填写</option>
                        <c:forEach items="${person1}" var="p">
                            <option value="${p.name}" <c:if test="${prItemOutStorage.checkUser eq p.name}">selected</c:if>>${p.name}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
        </div>
        <div class="row">
        <div class="col-md-12">
            <div class="form-group col-md-3">
                <label>出库日期：</label>
                <input class="form-control" id="outStorageDate" name="outStorageDate" size="16" type="text" value="<fmt:formatDate value="${prItemOutStorage.outStorageDate}" pattern="yyyy-MM-dd"/>">
            </div>

            <div class="form-group col-md-3">
                <label>栋号：</label>
                <select class="form-control js_select2" name="projectHouseNum">
                    <option value="">请选择或填写</option>
                    <c:forEach items="${person9}" var="p">
                        <option value="${p.name}"<c:if test="${prItemOutStorage.projectHouseNum eq p.name}">selected</c:if>>${p.name}</option>
                    </c:forEach>
                </select>
            </div>

            <div class="form-group col-md-3">
                <div class="col-md-6">
                    <label>领用人：</label>
                    <select class="form-control js_select2" name="itemReceiver">
                        <c:forEach items="${person7}" var="p">
                            <option value="${p.name}" <c:if test="${prItemOutStorage.itemReceiver eq p.name}">selected</c:if>>${p.name}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-md-6">
                    <label>领用单位：</label>
                    <select class="form-control js_select2" name="itemReceiverUnit">
                        <c:forEach items="${person8}" var="p">
                            <option value="${p.name}" <c:if test="${prItemOutStorage.itemReceiverUnit eq p.name}">selected</c:if>>${p.name}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>

            <div class="form-group col-md-3">
                <label>备注：</label>
                <input type="text" id="remark" name="remark" class="form-control"  value="${prItemOutStorage.remark}">
            </div>
        </div>
    </div>
        </form>
    <div class="navbar-form navbar-left" role="search">
        <button type="button"  class="btn btn-default js_search">新增明细</button>
    </div>
    <div id="myTabContent" class="tab-content">
        <div class="tab-pane fade in active" id="home">
            <div class="portlet-body">
                <div class="row">
                    <div class="col-md-12">
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
                                <%--<th width="8%"data-name="excTaxPrice" data-role="text" data-readonly="readonly">出库不含税单价</th>--%>
                                <th width="8%" data-name="excTaxSumPrice" data-role="text" data-readonly="readonly">出库不含税金额</th>
                                <th width="8%" data-name="sumPrice" data-role="text" data-readonly="readonly">出库含税金额</th>
                                <th data-name="taxRate" data-role="text" data-readonly="readonly">税率</th>
                                <%--<th data-role="html">结存数量</th>--%>
                                <%--<th data-role="html">结存不含税金额</th>--%>
                                <%--<th data-role="html">结存含税金额</th>--%>
                                <th data-role="operate" width="12%">操作</th>
                                <%--<th data-role="html">收货Id</th>--%>
                                <%--<th data-role="html">详情Id</th>--%>
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
                                        <%--<td>${outStorageList.excTaxPrice}</td>--%>
                                        <td data-name="excTaxSumPrice" data-value="${outStorageList.excTaxSumPrice}">${outStorageList.excTaxSumPrice}<c:set var="excTaxSumPrice" value="${excTaxSumPrice+outStorageList.excTaxSumPrice}"/></td>
                                        <td data-name="sumPrice" data-value="${outStorageList.sumPrice}">${outStorageList.sumPrice}<c:set var="sumPrice" value="${sumPrice+outStorageList.sumPrice}"/></td>
                                        <td>${outStorageList.taxRate}</td>
                                        <%--<td>${outStorageList.balanceNum}</td>--%>
                                        <%--<td>${outStorageList.balanceExcTaxSumPrice}</td>--%>
                                        <%--<td>${outStorageList.balanceSumPrice}</td>--%>
                                        <td>
                                            <button class="btn btn-sm blue js_edit" data-role="edit" data-id="${outStorageList.id}"  data-pid ="${outStorageList.outStorageId}" data-deliveryid="${outStorageList.deliveryId}">编辑</button>
                                            <button class="btn btn-sm js_choose btn-primary"  data-status='active'>求和</button>
                                        </td>
                                        <%--<td>${outStorageList.deliveryId}</td>--%>
                                        <%--<td>${outStorageList.id}</td>--%>
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
            </div>
        </div>
        <br/>
        <div class="row">
            <div class="form-group col-md-1">
                <a type="button" class="btn default" href="<c:url value="/admin/inventory/outStorage/index"/>">返回列表</a>
            </div>
            <div class="form-group col-md-1">
                <input type="button" class="form-control" name="toPrintPage"   value="打印">
            </div>
            <div class="form-group col-md-1">
                <button type="button" class="form-control js_save_btn">保存</button>
            </div>
            <div class="form-group col-md-1 hide js_save_group">
                <button type="button" class="btn  btn-primary">保存明细</button>
            </div>
        </div>
    </div>
    </div>
</div>

<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/select2-4.0.3/js/select2.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.zh-CN.js" />"></script>

<script>

    var currRepository = '${prItemOutStorage.repositoryId}';
    var inStoreArray = new Array();
    <c:forEach items="${list}" var="e">
    inStoreArray.push({id:'${e.deliveryId}',added:true});
    </c:forEach>

    $(function () {


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
            if (_name=="checkUser"){
                updateObj.checkUserName = evt.params.data.text;
            }else if (_name=="itemReceiver"){
                updateObj.itemReceiverName = evt.params.data.text;
            } else if (_name == "itemReceiverUnit") {
                updateObj.itemReceiverUnitName = evt.params.data.text;
            }else if (_name == "projectHouseNum") {
                updateObj.projectHouseNumName = evt.params.data.text;
            }
        });

        $('input[name=outStorageDate]').datepicker({
            todayBtn : "linked",
            autoclose : true,
            language: 'zh-CN',
            format : "yyyy-mm-dd",
            todayHighlight : true,
            endDate : new Date()
        });

        var outStorageId = '${prItemOutStorage.id}';

        /**
         * 选择材料
         */
        $('button.js_search').on('click',function () {
            layer.open({
                type: 2,
                title: "",
                area:['80%','60%'],
                content: '<c:url value="/admin/inventory/inStorage/getReceiptedMaterial" />',
                end:function () {
                    $.each(inStoreArray,function (k, v) {
                        if (!v.added){
                            $('button.js_edit').addClass('hide');
                            var _tr = $("<tr class='tr_add' data-deliveryid='"+v.id+"' data-materialid='"+v.materialId+"' data-specificationid='"+v.specificationId+"'>");
                            _tr.append($('<td>'));
                            _tr.append($('<td>').text(v.materialName));
                            _tr.append($('<td>').text(v.specification));
                            _tr.append($('<td>').text(v.unit));
                            var _num = v.num-v.outStorageNum;
                            var _tax=0;
                            var balanceSumPrice = v.sumPrice-v.outStorageSumPrice;
                            var balanceExcTaxSumPrice = v.excTaxSumPrice-v.outStorageExcTaxSumPrice;
                            if(_num != 0){
                                _tax =  balanceSumPrice -balanceExcTaxSumPrice;
                            }
                            _tr.append($('<td data-name="use">').html('<input type="text" class="form-control" name="use" value="" >'));
                            _tr.append($('<td data-name="num" validate data-value="'+_num+'">').html('<input type="text" class="form-control" name="num" value="'+_num.toFixed(4)+'" >'));
                            _tr.append($('<td data-name="price" validate>').html('<input type="text" class="form-control" name="price" value="'+v.price.toFixed(2)+'" >'));
                            _tr.append($('<td data-name="tax" validate data-value="'+0+'">').html('<input type="text" class="form-control" readonly name="tax" value="'+_tax.toFixed(2)+'" >'));
//                            _tr.append($('<td data-name="excTaxPrice" validate>').html('<input type="text" class="form-control" name="excTaxPrice" readonly value="'+v.excTaxPrice.toFixed(2)+'" >'));
                            _tr.append($('<td data-name="excTaxSumPrice" validate data-value="'+balanceExcTaxSumPrice+'">').html('<input type="text" class="form-control" name="excTaxSumPrice" readonly value="'+balanceExcTaxSumPrice.toFixed(2)+'" >'));
                            _tr.append($('<td data-name="sumPrice" validate data-value="'+balanceSumPrice+'">').html('<input type="text" class="form-control" name="sumPrice" readonly value="'+balanceSumPrice.toFixed(2)+'" >'));
                            _tr.append($('<td data-name="taxRate" validate>').html('<input validate class="form-control" name="taxRate" readonly value="'+v.taxRate+'" >'));
//                            _tr.append($('<td data-name="balanceNum">').text(_num.toFixed(4)));
//                            _tr.append($('<td data-name="balanceSumPrice">').text(balanceSumPrice.toFixed(2)));
//                            _tr.append($('<td data-name="balanceExcTaxSumPrice">').text(balanceExcTaxSumPrice.toFixed(2)));
                            _tr.append($('<td>').html('<button type="button"  class="btn btn-default js_remove" data-id="'+v.id+'">删除</button><button class="btn btn-sm js_choose btn-primary"  data-status="active">求和</button>'));
                            v.added = true;
                            $('tr.js_sum').before(_tr);
                        }
                    });
                    buildIndex();
                    if(inStoreArray.length>0){
                        $('select[name=repositoryId]').find('option[value!="'+currRepository+'"]').remove();
                    }
                }
            });
        });

        var buildIndex = function () {
            $('#datatable_ajax tbody').find('tr').each(function (k, v) {
                $(v).find('td:eq(0)').text(k+1);
            });
            if($('tr.tr_add').length>0){
                $('div.js_save_group').removeClass('hide');
            }else{
                $('div.js_save_group').addClass('hide');
            }
        }

        $('#datatable_ajax tbody').on('click','button.js_remove',function () {
            var _this = $(this);
            var _id = _this.data('id');
            layer.confirm('您确定要移除该行数据？',function (_index) {
                _this.parents('tr').remove();
                $(inStoreArray).each(function (k, v) {
                    if (v.id == _id){
                        inStoreArray.splice(k,1);
                    }
                });
                buildIndex();
                layer.close(_index);
                if($('tr.tr_add').length==0){
                    $('button.js_edit').removeClass('hide');
                }
            });
        });

        $('input[name=toPrintPage]').on('click', function () {
            $('#printForm').remove();
            var _form = $('<form>');
            _form.attr('action','<c:url value="/admin/inventory/outStorage/print/${prItemOutStorage.id}"/>');
            _form.attr('id','printForm');
            _form.attr('method','POST');
            _form.attr('target','_blank');
            $(document).find('body').append(_form);
            $('#printForm').submit();
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
                    td = $('<td><button class="btn btn-sm red js_edit" data-role="save">保存</button><button class="btn btn-sm green js_cancel">取消编辑</button><button class="btn btn-sm js_choose"  data-status="disActive">取消</button></td>');
                }else{
                    td= $('<td>');
                }
                tr.append(td);
            });
            _parentTr.hide().after(tr);
            return tr;
        };

        var dataObj;
        $('#datatable_ajax').on('click','button.js_edit',function () {
            var _this = $(this);
            var _action = _this.data('role');
            if (_action == 'edit'){
                $('div.js_save_group').addClass('hide');
                $('button.js_search ').addClass('hide');
                dataObj = {};
                dataObj.id = _this.data('id');
                dataObj.outStorageId = _this.data('pid');
                dataObj.deliveryId = _this.data('deliveryid');
                addRow(_this);
            }else{
                var _tr =_this.parents('tr');
                _this.parents('tr').find('input').each(function (k, v) {
                    dataObj[v.name] = v.value;
                });
                if(dataObj.num=='' || isNaN(dataObj.num) || dataObj.price=='' || isNaN(dataObj.price) || dataObj.tax=='' || isNaN(dataObj.tax)){
                    layer.msg('请输入正确的单价、数量、税额');
                    return;
                }
                layer.confirm("确定要修改？",function (_index) {
                    layer.close(_index);
                    $.ajax({
                        type: 'POST',
                        url: '<c:url value="/admin/inventory/outStorage/updateOutStorageDetail"/>',
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
            if($('button.js_cancel').length==0){
                $('button.js_search').removeClass('hide');
            }
        });

        /**
         * 保存按钮
         */
        $('div.js_save_group').on('click','button',function () {
            var flag = true;
            var _arr = new Array();
            $('tr.tr_add').each(function (k, v) {
                var dataObj = {
                    deliveryId:$(v).data('deliveryid'),
                    materialId:$(v).data('materialid'),
                    specificationId:$(v).data('specificationid')
                };
                $(v).find('input').each(function (i, e) {
                    dataObj[e.name] = e.value;
                });
                if(dataObj.num=='' || isNaN(dataObj.num) || dataObj.price=='' || isNaN(dataObj.price) || dataObj.tax=='' || isNaN(dataObj.tax)){
                    layer.msg('请输入正确的单价、数量、税额');
                    flag = false;
                    return flag;
                }
                _arr.push(dataObj);
            });
          if(flag){
            layer.confirm("确定要新增出库明细？",function (_index) {
                layer.close(_index);
                $.ajax({
                    type: 'POST',
                    url: '<c:url value="/admin/inventory/outStorage/addOutStorageDetail"/>',
                    async: false,
                    data: {outStorageId:outStorageId,detailArray:JSON.stringify(_arr)},
                    dataType: "json",
                    success: function (result) {
                        if(result.success){
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
         * 选择 取消
         */
        $('#datatable_ajax').on('click','button.js_choose',function () {
            var _this = $(this);
            if(_this.data('status')=='active'){
                _this.data('status','disActive');
                _this.removeClass('btn-primary').text('取消');
            }else{
                _this.data('status','active');
                _this.addClass('btn-primary').text('求和');
            }
            calculate();
        });


        var calculate = function () {
            var _allNum =0;
            var _allTax = 0;
            var _allExcTaxSumPrice = 0;
            var _allSumPrice = 0;
            $('button.js_choose:visible').each(function (k,v) {
                var _v = $(v);
                if(_v.data('status')=='disActive'){
                    var _num =parseFloat(_v.parent('td').prevAll('td[data-name="num"]').data('value'));
                    var _sumPrice = parseFloat(_v.parent('td').prevAll('td[data-name="sumPrice"]').data('value'));
                    var _tax = parseFloat(_v.parent('td').prevAll('td[data-name="tax"]').data('value'));
                    var _excTaxSumPrice = parseFloat(_v.parent('td').prevAll('td[data-name="excTaxSumPrice"]').data('value'));
                     _allNum+= !isNaN(_num)?_num:0;;
                    _allSumPrice += !isNaN(_sumPrice)?_sumPrice:0;;
                    _allTax += !isNaN(_tax)?_tax:0;;
                    _allExcTaxSumPrice += !isNaN(_excTaxSumPrice)?_excTaxSumPrice:0;;
                }
            });
            var _sumTr = $('tr.js_sum');
            _sumTr.find('td[role="num"]').text(_allNum.toFixed(4));
            _sumTr.find('td[role="sumPrice"]').text(_allSumPrice.toFixed(2));
            _sumTr.find('td[role="tax"]').text(_allTax.toFixed(2));
            _sumTr.find('td[role="excTaxSumPrice"]').text(_allExcTaxSumPrice.toFixed(2));
            _sumTr.removeClass('hide');
        }

        /**
         * 数量 单价 输入框
         */
        $('#datatable_ajax').on('blur','input[name=num]',function () {
            var _tr = $(this).parents('tr');
            var _price =_tr.find('input[name=price]').val();
            var _value = parseFloat($(this).val());
            if (!isNaN(_value)){
                $(this).val(_value.toFixed(4)).parent().data('value',_value);
            }else{
                return;
            }
            var _sumPrice;
            if (!isNaN(parseFloat(_price))){
                _sumPrice = _value*parseFloat(_price);
                _tr.find('input[name=sumPrice]').val(_sumPrice.toFixed(2)).parent().data('value',_sumPrice);
            }else{
                return;
            }
            var _taxRate = parseFloat(_tr.find('input[name=taxRate]').val())*0.01;
            var _excTaxSumPrice =_sumPrice/(1+_taxRate);
            var _tax =_excTaxSumPrice*_taxRate;
            _tr.find('input[name=excTaxSumPrice]').val(_excTaxSumPrice.toFixed(2)).parent().data('value',_excTaxSumPrice);
            _tr.find('input[name=tax]').val(_tax.toFixed(2)).parent().data('value',_tax);
            calculate();
        }).on('blur','input[name=price]',function () {
            var _tr = $(this).parents('tr');
            var _num =parseFloat(_tr.find('input[name=num]').val());
            var _this = $(this);
            var _value = parseFloat(_this.val());
            if (!isNaN(_value)){
                _this.val(_value.toFixed(2));
            }else{
                return;
            }
            var _sumPrice;
            if (!isNaN(_num)){
                _sumPrice = _value*_num;
                _tr.find('input[name=sumPrice]').val(_sumPrice.toFixed(2)).parent().data('value',_sumPrice);
            }else{
                return;
            }
            var _taxRate = parseFloat(_tr.find('input[name=taxRate]').val())*0.01;
            var _excTaxSumPrice =_sumPrice/(1+_taxRate);
            var _tax =_excTaxSumPrice*_taxRate;
            _tr.find('input[name=excTaxSumPrice]').val(_excTaxSumPrice.toFixed(2)).parent().data('value',_excTaxSumPrice);
            _tr.find('input[name=tax]').val(_tax.toFixed(2)).parent().data('value',_tax);
            calculate();
        });

        var updateObj ={id:'${prItemOutStorage.id}'};
        $('button.js_save_btn').on('click',function () {
                var objArr = $('form.js_base_form').serializeArray();
                for(var e in objArr){
                    var  obj = objArr[e];
                    updateObj[obj.name] = obj.value;
                };
                layer.confirm("确定提交？",function (c) {
                    layer.close(c);
                    var _index = layer.load(2);
                    $.post('<c:url value="/admin/inventory/outStorage/update"/>', updateObj, function (result) {
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
    });
</script>
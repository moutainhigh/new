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
        <div class="caption">出库管理</div>
    </div>
    <div class="portlet-body">
        <div class="row">
            <div class="col-md-12">
                <div class="form-body">
                    <form  role="form" class="js_base_form" id="materialAddForm">
                    <div class="row">
                        <div class="col-md-3 ">
                            <div class="form-group">
                                <label>项目：</label>
                                <c:forEach items="${allProjects}" var="p">
                                    <c:if test="${p.id eq currProject}"><input type="text" class="form-control" name="unit" value="${p.name}" readonly></c:if>
                                </c:forEach>
                            </div>
                        </div>
                        <div class="col-md-3 ">
                            <div class="form-group">
                                <label>仓库名称：</label>
                                <select class="form-control" name="repositoryId">
                                    <option value="">请选择</option>
                                    <c:forEach items="${repositories}" var="r">
                                        <option value="${r.id}" <c:if test="${repositoryId eq r.id}">selected</c:if>>${r.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="col-md-3 ">
                            <div class="form-group">
                                <label>出库日期：</label>
                                <input type="text" class="form-control" name="outStorageDate" value="<fmt:formatDate value="${now}" pattern="yyyy-MM-dd"/>">
                            </div>
                        </div>
                        <div class="col-md-3 ">
                            <div class="form-group">
                                <label>库管员：</label>
                                <select class="form-control js_select2" name="checkUser">
                                    <option value="">请选择或填写</option>
                                    <c:forEach items="${person1}" var="p">
                                        <option value="${p.name}">${p.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-3 ">
                            <div class="col-md-6">
                            <div class="form-group">
                                <label>栋号：</label>
                                <select class="form-control js_select2" name="projectHouseNum">
                                    <option value="">请选择或填写</option>
                                    <c:forEach items="${person9}" var="p">
                                        <option value="${p.name}">${p.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            </div>
                            <div class="col-md-6 ">
                                <div class="form-group">
                                    <label>用途：</label>
                                    <select class="form-control js_select2" name="useStr">
                                        <option value="">请选择或填写</option>
                                        <c:forEach items="${person12}" var="p">
                                            <option value="${p.name}">${p.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>领用人：</label>
                                <select class="form-control js_select2" name="itemReceiver">
                                    <option value="">请选择或填写</option>
                                    <c:forEach items="${person7}" var="p">
                                        <option value="${p.name}">${p.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>领用单位：</label>
                                <select class="form-control js_select2" name="itemReceiverUnit">
                                    <option value="">请选择或填写</option>
                                    <c:forEach items="${person8}" var="p">
                                        <option value="${p.name}">${p.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>备注：</label>
                                <input type="text" class="form-control" name="remark" value="${entity.remark}" >
                            </div>
                        </div>
                    </div>
                    </form>
                    <div class="navbar-form navbar-left" role="search">
                        <button type="button"  class="btn btn-default js_search">选择材料</button>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <table class="table table-width table-striped table-bordered table-advance table-hover table-checkable formdata" id="datatable_ajax">
                                <thead>
                                <tr role="row" class="heading">
                                    <th>序号</th>
                                    <th>材料名称</th>
                                    <th>别名</th>
                                    <th>规格</th>
                                    <th>计量单位</th>
                                    <%--<th width="8%">用途</th>--%>
                                    <th>税率</th>
                                    <th width="8%">出库数量</th>
                                    <th width="8%">出库含税单价</th>
                                    <th>出库含税金额</th>
                                    <th>税额</th>
                                    <th>出库不含税金额</th>
                                    <th>结存数量</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${deliveryList}" var="e" varStatus="setp">
                                        <tr data-id="${e.id}">
                                        <td>${setp.count}</td>
                                        <td>${e.materialName}</td>
                                        <td>${e.alias}</td>
                                        <td>${e.specification}</td>
                                        <td>${e.unit}</td>
                                        <%--<td data-name="use"><input class="form-control" name="use" value="" style="width: 100%"></td>--%>
                                        <td data-name="taxRate" validate><input class="form-control" name="taxRate" readonly value="${e.taxRate}" style="width: 100%"></td>
                                        <td data-name="num" validate><input class="form-control" name="num" value="${e.num-e.outStorageNum}" style="width: 100%"></td>
                                        <td data-name="price" validate><input class="form-control" name="price" value="${e.price}" style="width: 100%"></td>
                                        <td data-name="sumPrice" validate><input class="form-control" name="sumPrice" readonly value="" style="width: 100%"></td>
                                        <td data-name="tax" validate><input class="form-control" name="tax" readonly value="0" style="width: 100%"></td>
                                        <td data-name="excTaxSumPrice" validate><input class="form-control" name="excTaxSumPrice" readonly value="" style="width: 100%"></td>
                                        <td data-name="balanceNum">${e.num-e.outStorageNum}</td>
                                        <td><button type="button" class="btn btn-default js_remove" data-id="${e.id}">删除</button></td>
                                        </tr>
                                    </c:forEach>
                                    <tr class="js_sum">
                                        <td colspan="6"></td>
                                        <td role="num">0</td>
                                        <td></td>
                                        <td role="sumPrice">0</td>
                                        <td role="tax">0</td>
                                        <td role="excTaxSumPrice">0</td>
                                        <td colspan="2"></td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>

                    <button type="button" class="btn green js_save_btn" data-action="save">保存</button>
                    <a type="button" class="btn default" href="<c:url value='/admin/inventory/outStorage/index'/>">返回列表</a>
                </div>
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

    var inStoreArray = new Array();
    <c:if test="${not empty deliveryList}">
        <c:forEach items="${deliveryList}" var="e">
        inStoreArray.push({id:'${e.id}',added:true});
        </c:forEach>
    </c:if>

    var currRepository = ${empty  repositoryId?'null':repositoryId};

    $(function () {



        var dataObj = {id:'${entity.id}',materialId:'${entity.materialId}',specificationId:'${entity.specificationId}'};

        $('select[name=repositoryId]').on('change',function () {
            var _id = $(this).val();
           if(_id!=''){
               currRepository = _id;
           }
        });
        /**
         * 选择材料
         */
        $('button.js_search').on('click',function () {
            if(null == currRepository){
                layer.msg("请先选择仓库");
                return;
            }
            layer.open({
                type: 2,
                title: "",
                area:['80%','60%'],
                content: '<c:url value="/admin/inventory/inStorage/getReceiptedMaterial" />',
                end:function () {
                    $.each(inStoreArray,function (k, v) {
                        if (!v.added){
                            var _tr = $("<tr data-id='"+v.id+"' >");
                            _tr.append($('<td>'));
                            _tr.append($('<td>').text(v.materialName));
                            _tr.append($('<td>').text(v.alias));
                            _tr.append($('<td>').text(v.specification));
                            _tr.append($('<td>').text(v.unit));
//                            _tr.append($('<td data-name="use">').html('<input type="text" class="form-control" name="use" value="" style="width: 100%">'));
                            _tr.append($('<td data-name="taxRate" validate>').html('<input class="form-control" name="taxRate" readonly value="'+v.taxRate+'" style="width: 100%">'));
                            _tr.append($('<td data-name="num" validate>').html('<input type="text" class="form-control" name="num" value="'+(v.num-v.outStorageNum)+'" style="width: 100%">'));
                            _tr.append($('<td data-name="price" validate>').html('<input type="text" class="form-control" name="price" value="'+(v.price.toFixed(2))+'" style="width: 100%">'));
                            _tr.append($('<td data-name="sumPrice" validate>').html('<input type="text" class="form-control" name="sumPrice" readonly value="0" style="width: 100%">'));
                            _tr.append($('<td data-name="tax" validate>').html('<input type="text" class="form-control" name="tax" readonly value="0" style="width: 100%">'));
                            _tr.append($('<td data-name="excTaxSumPrice" validate>').html('<input type="text" class="form-control" name="excTaxSumPrice" readonly value="0" style="width: 100%">'));
                            _tr.append($('<td data-name="balanceNum">').text((v.num-v.outStorageNum).toFixed(4)));
                            _tr.append($('<td>').html('<button type="button"  class="btn btn-default js_remove" data-id="'+v.id+'">删除</button>'));
                            v.added = true;
                            $('tr.js_sum').before(_tr);
                        }
                    });
                    $('#datatable_ajax input[name=num]').trigger('blur');
                    buildIndex();
                    calculate();
                    if(inStoreArray.length>0){
                        $('select[name=repositoryId]').find('option[value!="'+currRepository+'"]').remove();
                    }
                }
            });
        });

        var calculationSumPrice = function (val1,val2,_target) {
            var _num = parseFloat(val1);
            var _price = parseFloat(val2);
            if (!isNaN(_num) && !isNaN(_price)){
                _target.val(parseFloat(_num*_price).toFixed(2));
            }
        }

        $('#datatable_ajax').on('blur','input[name=num]',function () {
            var _this = $(this);
            if(!isNaN(parseFloat(_this.val()))){
                _this.val(parseFloat(_this.val()).toFixed(4));
                var _parent = _this.parent();
                var sumPriceObj = _parent.nextAll('td[data-name=sumPrice]').find('input');
                calculationSumPrice(_this.val(),_parent.next().find('input').val(),sumPriceObj);
                var _taxRate = parseFloat(_parent.prev().find('input').val())*0.01;
                var _excTaxSumPrice =parseFloat(sumPriceObj.val())/(1+_taxRate);
                _parent.nextAll('td[data-name=excTaxSumPrice]').find('input').val(_excTaxSumPrice.toFixed(2));
                _parent.nextAll('td[data-name=tax]').find('input').val((_excTaxSumPrice*_taxRate).toFixed(2));
            }else{
                _this.val('');
            }
            calculate();
        });

        $('#datatable_ajax').on('blur','input[name=price]',function () {
            var _this = $(this);
            if(!isNaN(parseFloat(_this.val()))){
                _this.val(parseFloat(_this.val()).toFixed(2));
                var _parent = _this.parent();
                var sumPriceObj = _parent.next().find('input');
                calculationSumPrice(_parent.prev().find('input').val(),_this.val(),sumPriceObj);
                var _taxRate = parseFloat(_parent.prevAll('td[data-name=taxRate]').find('input').val())*0.01;
                var _excTaxSumPrice = parseFloat(sumPriceObj.val())/(1+_taxRate);
                _parent.nextAll('td[data-name=excTaxSumPrice]').find('input').val(_excTaxSumPrice.toFixed(2));
                _parent.nextAll('td[data-name=tax]').find('input').val((_excTaxSumPrice*_taxRate).toFixed(2));

            }
            calculate();
        });

        var buildIndex = function () {
            $('#datatable_ajax tbody').find('tr:not(:last)').each(function (k, v) {
               $(v).find('td:eq(0)').text(k+1);
            });
        }

        /**
         * 删除
         */
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
            });
        });

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
                dataObj.checkUserName = evt.params.data.text;
            }else if (_name=="itemReceiver"){
                dataObj.itemReceiverName = evt.params.data.text;
            } else if (_name == "itemReceiverUnit") {
                dataObj.itemReceiverUnitName = evt.params.data.text;
            }else if (_name == "projectHouseNum") {
                dataObj.projectHouseNumName = evt.params.data.text;
            }else if (_name == "useStr") {
                console.info(evt.params.data.text);
                dataObj.use = evt.params.data.text;
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


        var validateForm = $('form.js_base_form');
        validateForm.validate({
            errorElement : 'span',
            errorClass : 'help-block help-block-error',
            rules : {
                outStorageDate : {
                    required : true
                },repositoryId : {
                    required : true
                },checkUser : {
                    required : true
                },itemReceiver: {
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
         * 计算合计
         */
        var calculate = function () {
            var _allNum =0;
            var _allTax = 0;
            var _allExcTaxSumPrice = 0;
            var _allSumPrice = 0;
            $('tr.js_sum').prevAll().each(function (k,v) {
                var _v = $(v);
                var _num = parseFloat(_v.find('input[name=num]').val());
                var _sumPrice = parseFloat(_v.find('input[name=sumPrice]').val());
                var _tax = parseFloat(_v.find('input[name=tax]').val());
                var _excTaxSumPrice = parseFloat(_v.find('input[name=excTaxSumPrice]').val());
                _allNum+= !isNaN(_num)?_num:0;
                _allTax += !isNaN(_tax)?_tax:0;
                _allSumPrice += !isNaN(_sumPrice)?_sumPrice:0;
                _allExcTaxSumPrice += !isNaN(_excTaxSumPrice)?_excTaxSumPrice:0;
            });
            var _sumTr = $('tr.js_sum');
            _sumTr.find('td[role="num"]').text(_allNum.toFixed(4));
            _sumTr.find('td[role="tax"]').text(_allTax.toFixed(2));
            _sumTr.find('td[role="sumPrice"]').text(_allSumPrice.toFixed(2));
            _sumTr.find('td[role="excTaxSumPrice"]').text(_allExcTaxSumPrice.toFixed(2));
            _sumTr.removeClass('hide');
        }

        var encodeTbData = function () {
            var result = {};
            var _arr = new Array();
            var flag = true;
            $('tr.js_sum').prevAll().each(function (k, v) {
                if(flag){
                    var obj = {};
                    obj.deliveryId = $(v).data("id");
                    $(v).find('td[data-name]').each(function (i, e) {
                        var _val = $(e).find('input').val();
                        if (_val == undefined){
                            _val = $(e).text();
                        }else{
                            if (!validate($(e),_val)){
                                return flag = false;
                            }
                        }
                        var _key = $(e).data('name');
                        obj[_key] =_val;
                    });
                    _arr.push(obj);
                    if(parseFloat(obj.num)>parseFloat(obj.balanceNum)){
                        result.msg="出库数量不能超过结存数量！";
                        return flag = false;
                    }
                }
            });
            result.flag = flag;
            if (_arr.length == 0){
                result.msg = "请选择出库材料!";
                result.flag = false;
            }else{
                result.arr = _arr;
            }
            return result;
        };



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

        $('button.js_save_btn').on('click',function () {
            if(validateForm.valid()){
                var obj = encodeTbData();
                if (!obj.flag){
                    if(obj.msg){
                        layer.msg(obj.msg,{icon:0});
                    }
                    return;
                }
                dataObj.listStr = JSON.stringify(obj.arr);
                var objArr = $('form.js_base_form').serializeArray();
                for(var e in objArr){
                    var  obj = objArr[e];
                    dataObj[obj.name] = obj.value;
                };
                var url = '<c:url value="/admin/inventory/outStorage/post"/>';
                layer.confirm("确定提交？",function (c) {
                    layer.close(c);
                    var _index = layer.load(2);
                    $.post(url, dataObj, function (result) {
                        layer.close(_index);
                        if (result.success) {
                            layer.msg('操作成功',{time:800},function () {
                                window.location.href='<c:url value="/admin/inventory/outStorage/detail/"/>'+result.rdata;
                            });
                        }else{
                            layer.msg('操作失败：'+result.rmsg,{time:2000});
                        }
                    }, 'json');
                });
            }
        });

        /**
         * 选择了出库材料的时候 触发计算操作
         */
        $('#datatable_ajax input[name=num]').trigger('blur');

    });

</script>


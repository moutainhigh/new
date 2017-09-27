<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<link rel="stylesheet" type="text/css" href="<c:url value="/resources/admin/global/plugins/bootstrap-datepicker/css/bootstrap-datepicker3.min.css" />"/>

<div class="portlet light">
    <div class="portlet-title">
        <div class="caption">
            <i class="icon-layers font-green"></i>
            <span class="caption-subject font-green sbold ">物资盘点</span>
        </div>
        <div class="actions form-inline">
            <a href="<c:url value="/admin/inventory/statistics/balanceEdit"/>" target="_blank" class="btn btn-primary">调价</a>
        </div>
    </div>
    <div class="portlet-body">
        <form class="navbar-form navbar-left" role="search" id="materialSearchForm">
            <div class="form-group">
                仓库：
                <select class="form-control" name="repository">
                    <c:forEach items="${repositories}" var="r" varStatus="step">
                        <option value="${r.id}" <c:if test="${step.index==0}">selected</c:if>>${r.name}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group">
                日期：
                <div class="input-group date form_date" data-date="" data-date-format="yyyy-mm-dd" data-link-field="startDate" data-link-format="yyyy-mm-dd">
                    <input class="form-control" name="startDate" size="16" type="text" placeholder="开始时间">
                </div>
                -
                <div class="input-group date form_date" data-date="" data-date-format="yyyy-mm-dd" data-link-field="endDate" data-link-format="yyyy-mm-dd">
                    <input class="form-control" name="endDate" size="16" type="text" placeholder="结束时间">
                </div>
            </div>
            <div class="form-group">
                <button type="button" class="btn btn-default js_search">搜索</button>
            </div>
            <div class="form-group">
                <button type="button" class="btn btn-default js_print">打印</button>
            </div>
        </form>
        <div id="myTabContent" class="tab-content">
            <div class="tab-pane fade in active" id="home">
                <div class="portlet-body">
                    <div class="row">
                        <div class="col-md-12" style="overflow: auto">
                            <table class="table table-width table-striped table-bordered table-advance table-hover table-checkable table-responsive" id="datatable_ajax">
                                <thead>
                                <tr role="row" class="heading">
                                    <th rowspan="2">分类</th>
                                    <th rowspan="2">名称</th>
                                    <th rowspan="2">规格</th>
                                    <th rowspan="2">单位</th>
                                    <th colspan="5">期初</th>
                                    <th colspan="5">本期增加</th>
                                    <th colspan="5">本期减少</th>
                                    <th colspan="5">期末结存</th>
                                </tr>
                                <tr>
                                    <th>单价</th>
                                    <th>数量</th>
                                    <th>税额</th>
                                    <th>不含税金额</th>
                                    <th>总金额</th>
                                    <th>单价</th>
                                    <th>数量</th>
                                    <th>税额</th>
                                    <th>不含税金额</th>
                                    <th>总金额</th>
                                    <th>单价</th>
                                    <th>数量</th>
                                    <th>税额</th>
                                    <th>不含税金额</th>
                                    <th>总金额</th>
                                    <th>单价</th>
                                    <th>数量</th>
                                    <th>税额</th>
                                    <th>不含税金额</th>
                                    <th>总金额</th>
                                </tr>
                                </thead>
                                <tbody>

                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="modal fade js_balancePrice_div " data-backdrop="static">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title">调价</h4>
            </div>
            <div class="modal-body">
                <div class="container-fluid">
                    <form  role="form" class="js_base_form">
                    <div class="row">
                        <div class="col-md-4">
                            <div class="form-group">
                                <label>日期：</label>
                                <input type="text" class="form-control" name="balanceDate" value="">
                            </div>
                        </div>
                        <div class="col-md-4 ">
                            <div class="form-group">
                                <label>税额：</label>
                                <input type="text" class="form-control" name="sumTax" value="">
                            </div>
                        </div>
                        <div class="col-md-4 ">
                            <div class="form-group">
                                <label>总金额：</label>
                                <input type="text" class="form-control" name="sumPrice" value="">
                            </div>
                        </div>
                    </div>
                    </form>
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

<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.zh-CN.js" />"></script>
<script>

    $(function () {

        var _index = null;
        $('button.js_search').on('click',function () {
            var _r = $('select[name=repository]').val();
            var _startDate = $('input[name=startDate]').val();
            var _endDate = $('input[name=endDate]').val();
            if(_startDate&&_endDate){
                _index = layer.load(2);
                $.ajax({
                    async: false,
                    type: 'POST',
                    dataType: 'json',
                    data:{
                        repositoryId:_r,
                        startDate:_startDate,
                        endDate:_endDate
                    },
                    url: '<c:url value="/admin/inventory/statistics/statisticsData"/>',
                    success: function (result) {
                        layer.close(_index);
                        if(result.success){
                            buildTb(result.rdata);
                        }else{
                            layer.msg(result.rmsg);
                        }
                    }
                });
            }
        });

        $('input[name=balanceDate]').datepicker({
            todayBtn : "linked",
            autoclose : true,
            language: 'zh-CN',
            format : "yyyy-mm-dd",
            todayHighlight : true,
            endDate : new Date()
        });

        $('input[name=startDate]').datepicker({
            todayBtn : "linked",
            autoclose : true,
            language: 'zh-CN',
            format : "yyyy-mm-dd",
            todayHighlight : true,
            endDate : new Date()
        });

        $('input[name=endDate]').datepicker({
            todayBtn : "linked",
            autoclose : true,
            language: 'zh-CN',
            format : "yyyy-mm-dd",
            todayHighlight : true,
            endDate : new Date()
        });

        var  _html = ['<div class="row">',
            '	<div class="col-md-12" style="overflow: auto">',
            '		<table class="table table-width table-striped table-bordered table-advance table-hover table-checkable table-responsive" id="datatable_ajax">',
            '			<thead>',
            '			<tr role="row" class="heading">',
            '				<th rowspan="2">分类</th>',
            '				<th rowspan="2">名称</th>',
            '				<th rowspan="2">规格</th>',
            '				<th rowspan="2">单位</th>',
            '				<th colspan="5">期初</th>',
            '				<th colspan="5">本期增加</th>',
            '				<th colspan="5">本期减少</th>',
            '				<th colspan="5">期末结存</th>',
            '			</tr>',
            '			<tr>',
            '				<th>单价</th>',
            '				<th>数量</th>',
            '				<th>税额</th>',
            '				<th>不含税金额</th>',
            '				<th>总金额</th>',
            '				<th>单价</th>',
            '				<th>数量</th>',
            '				<th>税额</th>',
            '				<th>不含税金额</th>',
            '				<th>总金额</th>',
            '				<th>单价</th>',
            '				<th>数量</th>',
            '				<th>税额</th>',
            '				<th>不含税金额</th>',
            '				<th>总金额</th>',
            '				<th>单价</th>',
            '				<th>数量</th>',
            '				<th>税额</th>',
            '				<th>不含税金额</th>',
            '				<th>总金额</th>',
            '			</tr>',
            '			</thead>',
            '			<tbody>',
            '			</tbody>',
            '		</table>',
            '	</div>',
            '</div>'].join("");

        var entityKey = 'categoryName,materialName,specificationName,unit,startPrice,startNum,startSumTax,startSumExcTaxPrice,startSumPrice,addPrice,' +
            'addNum,addSumTax,addSumExcTaxPrice,addSumPrice,reducePrice,reduceNum,reduceSumTax,reduceSumExcTaxPrice,reduceSumPrice,' +
            'endPrice,endNum,endSumTax,endSumExcTaxPrice,endSumPrice';
        var  buildTb = function (resultList) {
            var _body = $('#datatable_ajax tbody').empty();
            var _lastId = '',_startNum=0,_startSumTax=0, _startSumExcTaxPrice=0, _startSumPrice=0, _addNum=0, _addSumTax=0, _addSumExcTaxPrice=0, _addSumPrice=0, _reduceNum=0,
                _reduceSumTax=0, _reduceSumExcTaxPrice=0, _reduceSumPrice=0, _endNum=0, _endSumTax=0, _endSumExcTaxPrice=0, _endSumPrice=0, _all_startNum=0, _all_startSumTax=0,
                _all_startSumExcTaxPrice=0, _all_startSumPrice=0, _all_addNum=0, _all_addSumTax=0, _all_addSumExcTaxPrice=0, _all_addSumPrice=0, _all_reduceNum=0, _all_reduceSumTax=0,
                _all_reduceSumExcTaxPrice=0, _all_reduceSumPrice=0, _all_endNum=0, _all_endSumTax=0, _all_endSumExcTaxPrice=0, _all_endSumPrice=0;
            var _count = 0;
            var tb =null;
            $.each(resultList,function (k,v) {
                if(_count %10 == 0) {
                    tb = $(_html);
                    //TODO xxxx
                }
                var _tr = $('<tr>');
                if(_lastId!='' && v.categoryId != _lastId ){
                    _tr.append($('<td>').attr('colspan',4).text("小计"));
                    _tr.append($('<td>'));
                    _tr.append($('<td>').text(_startNum.toFixed(2)));
                    _tr.append($('<td>').text(_startSumTax.toFixed(2)));
                    _tr.append($('<td>').text(_startSumExcTaxPrice.toFixed(2)));
                    _tr.append($('<td>').text(_startSumPrice.toFixed(2)));
                    _tr.append($('<td>'));
                    _tr.append($('<td>').text(_addNum.toFixed(2)));
                    _tr.append($('<td>').text(_addSumTax.toFixed(2)));
                    _tr.append($('<td>').text(_addSumExcTaxPrice.toFixed(2)));
                    _tr.append($('<td>').text(_addSumPrice.toFixed(2)));
                    _tr.append($('<td>'));
                    _tr.append($('<td>').text(_reduceNum.toFixed(2)));
                    _tr.append($('<td>').text(_reduceSumTax.toFixed(2)));
                    _tr.append($('<td>').text(_reduceSumExcTaxPrice.toFixed(2)));
                    _tr.append($('<td>').text(_reduceSumPrice.toFixed(2)));
                    _tr.append($('<td>'));
                    _tr.append($('<td>').text(_endNum.toFixed(2)));
                    _tr.append($('<td>').text(_endSumTax.toFixed(2)));
                    _tr.append($('<td>').text(_endSumExcTaxPrice.toFixed(2)));
                    _tr.append($('<td>').text(_endSumPrice.toFixed(2)));
                    _body.append(_tr);
                    _startNum=0;
                    _startSumTax=0;
                    _startSumExcTaxPrice=0;
                    _startSumPrice=0;
                    _addNum=0;
                    _addSumTax=0;
                    _addSumExcTaxPrice=0;
                    _addSumPrice=0;
                    _reduceNum=0;
                    _reduceSumTax=0;
                    _reduceSumExcTaxPrice=0;
                    _reduceSumPrice=0;
                    _endNum=0;
                    _endSumTax=0;
                    _endSumExcTaxPrice=0;
                    _endSumPrice=0;
                }
                _tr = $('<tr>');
                $.each(entityKey.split(','),function (i, e) {
                    if (e=='endSumPrice'){
                        console.info(v);
                        _tr.append($('<td>').html('<a href="javascript:void(0)" class="js_balance_price" data-sumprice="'+v[e]+'" data-tax="'+v['endSumTax']+'" data-mid="'+v['materialId']+'" data-sid="'+v['specificationId']+'">'+v[e]+'</a>'));
                    }else{
                        _tr.append($('<td>').text(v[e]));
                    }
                    if(e == 'startNum'){
                        _startNum+=v[e];
                        _all_startNum+=v[e];
                    }else if(e == 'startSumTax'){
                        _startSumTax+=v[e];
                        _all_startSumTax+=v[e];
                    }else if(e == 'startSumExcTaxPrice'){
                        _startSumExcTaxPrice+=v[e];
                        _all_startSumExcTaxPrice+=v[e];
                    }else if(e == 'startSumPrice'){
                        _startSumPrice+=v[e];
                        _all_startSumPrice+=v[e];
                    }else if(e == 'addNum'){
                        _addNum+=v[e];
                        _all_addNum+=v[e];
                    }else if(e == 'addSumTax'){
                        _addSumTax+=v[e];
                        _all_addSumTax+=v[e];
                    }else if(e == 'addSumExcTaxPrice'){
                        _addSumExcTaxPrice+=v[e];
                        _all_addSumExcTaxPrice+=v[e];
                    }else if(e == 'addSumPrice'){
                        _addSumPrice+=v[e];
                        _all_addSumPrice+=v[e];
                    }else if(e == 'reduceNum'){
                        _reduceNum+=v[e];
                        _all_reduceNum+=v[e];
                    }else if(e == 'reduceSumTax'){
                        _reduceSumTax+=v[e];
                        _all_reduceSumTax+=v[e];
                    }else if(e == 'reduceSumExcTaxPrice'){
                        _reduceSumExcTaxPrice+=v[e];
                        _all_reduceSumExcTaxPrice+=v[e];
                    }else if(e == 'reduceSumPrice'){
                        _reduceSumPrice+=v[e];
                        _all_reduceSumPrice+=v[e];
                    }else if(e == 'endNum'){
                        _endNum+=v[e];
                        _all_endNum+=v[e];
                    }else if(e == 'endSumTax'){
                        _endSumTax+=v[e];
                        _all_endSumTax+=v[e];
                    }else if(e == 'endSumExcTaxPrice'){
                        _endSumExcTaxPrice+=v[e];
                        _all_endSumExcTaxPrice+=v[e];
                    }else if(e == 'endSumPrice'){
                        _endSumPrice+=v[e];
                        _all_endSumPrice+=v[e];
                    }
                });
                _body.append(_tr);
                if(k == resultList.length-1){
                    _tr = $('<tr>');
                    _tr.append($('<td>').attr('colspan',4).text("小计"));
                    _tr.append($('<td>'));
                    _tr.append($('<td>').text(_startNum.toFixed(2)));
                    _tr.append($('<td>').text(_startSumTax.toFixed(2)));
                    _tr.append($('<td>').text(_startSumExcTaxPrice.toFixed(2)));
                    _tr.append($('<td>').text(_startSumPrice.toFixed(2)));
                    _tr.append($('<td>'));
                    _tr.append($('<td>').text(_addNum.toFixed(2)));
                    _tr.append($('<td>').text(_addSumTax.toFixed(2)));
                    _tr.append($('<td>').text(_addSumExcTaxPrice.toFixed(2)));
                    _tr.append($('<td>').text(_addSumPrice.toFixed(2)));
                    _tr.append($('<td>'));
                    _tr.append($('<td>').text(_reduceNum.toFixed(2)));
                    _tr.append($('<td>').text(_reduceSumTax.toFixed(2)));
                    _tr.append($('<td>').text(_reduceSumExcTaxPrice.toFixed(2)));
                    _tr.append($('<td>').text(_reduceSumPrice.toFixed(2)));
                    _tr.append($('<td>'));
                    _tr.append($('<td>').text(_endNum.toFixed(2)));
                    _tr.append($('<td>').text(_endSumTax.toFixed(2)));
                    _tr.append($('<td>').text(_endSumExcTaxPrice.toFixed(2)));
                    _tr.append($('<td>').text(_endSumPrice.toFixed(2)));
                    _body.append(_tr);
                    _tr = $('<tr>');
                    _tr.append($('<td>').attr('colspan',4).text("总计"));
                    _tr.append($('<td>'));
                    _tr.append($('<td>').text(_all_startNum.toFixed(2)));
                    _tr.append($('<td>').text(_all_startSumTax.toFixed(2)));
                    _tr.append($('<td>').text(_all_startSumExcTaxPrice.toFixed(2)));
                    _tr.append($('<td>').text(_all_startSumPrice.toFixed(2)));
                    _tr.append($('<td>'));
                    _tr.append($('<td>').text(_all_addNum.toFixed(2)));
                    _tr.append($('<td>').text(_all_addSumTax.toFixed(2)));
                    _tr.append($('<td>').text(_all_addSumExcTaxPrice.toFixed(2)));
                    _tr.append($('<td>').text(_all_addSumPrice.toFixed(2)));
                    _tr.append($('<td>'));
                    _tr.append($('<td>').text(_all_reduceNum.toFixed(2)));
                    _tr.append($('<td>').text(_all_reduceSumTax.toFixed(2)));
                    _tr.append($('<td>').text(_all_reduceSumExcTaxPrice.toFixed(2)));
                    _tr.append($('<td>').text(_all_reduceSumPrice.toFixed(2)));
                    _tr.append($('<td>'));
                    _tr.append($('<td>').text(_all_endNum.toFixed(2)));
                    _tr.append($('<td>').text(_all_endSumTax.toFixed(2)));
                    _tr.append($('<td>').text(_all_endSumExcTaxPrice.toFixed(2)));
                    _tr.append($('<td>').text(_all_endSumPrice.toFixed(2)));
                    _body.append(_tr);
                }
                if(_count %10 == 0){

                }
                _count++;
                _lastId = v.categoryId;
            });
        }



        /**
         * 打印
         */
        $('button.js_print').on('click',function () {
            var _startDate = $('input[name=startDate]').val();
            var _endDate= $('input[name=endDate]').val();
            var _r = $('select[name=repository]').val();
            if(_startDate&&_endDate&&_r){
                $('#printForm').remove();
                var _form = $('<form>');
                _form.attr('action','<c:url value="/admin/inventory/statistics/print"/>');
                _form.attr('id','printForm');
                _form.attr('method','POST');
                _form.attr('target','_blank');
                _form.append($('<input>').attr('name',"repositoryId").val(_r));
                _form.append($('<input>').attr('name',"repositoryName").val($('select[name=repository] option:selected').text()));
                _form.append($('<input>').attr('name',"startDate").val(_startDate));
                _form.append($('<input>').attr('name',"endDate").val(_endDate));
                $(document).find('body').append(_form);
                $('#printForm').submit();
            }
        });

        var status = 0;
        var startX = null;
        $('table').on('mousedown',function (e) {
            status = 1;
            startX = e.clientX;
        });
        $('table').on('mouseup',function (e) {
            if(status ==1){
                $("table").parent('div').scrollLeft(e.clientX-startX);
            }
            status = 0;
        });
        $('table').on('mousemove',function (e) {
            if(status ==1){
                $("table").parent('div').scrollLeft(e.clientX-startX);
            }
        });

        $('#datatable_ajax').on('click','a.js_balance_price',function (e) {
            var _div = $('div.js_balancePrice_div');
            var _this = $(this);
            _div.find('input[name=sumTax]').val(_this.data('tax'));
            _div.find('input[name=sumPrice]').val(_this.data('sumprice'));
            _div.modal('show');

            dataObj.materialId=_this.data('mid');
            dataObj.specificationId=_this.data('sid');
        });

        var validateForm = $('form.js_base_form');
        validateForm.validate({
            errorElement : 'span',
            errorClass : 'help-block help-block-error',
            rules : {
                balanceDate: {
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

        var dataObj = {};

        $('button.js_save_btn').on('click',function () {
            if(validateForm.valid()) {
                var objArr = $('form.js_base_form').serializeArray();
                for(var e in objArr){
                    var  obj = objArr[e];
                    dataObj[obj.name] = $.trim(obj.value);
                }
                dataObj['repositoryId'] = $('select[name=repository]').val();
                var url = '<c:url value="/admin/inventory/statistics/insertBalance"/>';
                layer.confirm("确定提交？", function (c) {
                    layer.close(c);
                    $('div.js_balancePrice_div').modal('hide');
                    var _index = layer.load(2);
                    $.post(url, dataObj, function (result) {
                        layer.close(_index);
                        if (result.success) {
                            layer.msg('操作成功', {time: 300}, function () {
                                $('button.js_search').trigger('click');
                            });
                        } else {
                            layer.msg('操作失败：' + result.rmsg, {time: 2000});
                        }
                    }, 'json');
                });
            }
        });



    });





</script>
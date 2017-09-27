<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<link rel="stylesheet" type="text/css" href="<c:url value="/resources/admin/global/plugins/bootstrap-datepicker/css/bootstrap-datepicker3.min.css" />"/>

<div class="portlet box blue-hoki">
    <div class="portlet-title">
        <div class="caption">收货未入库明细表</div>
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
                <button type="button" class="btn btn-default js_export">导出</button>
            </div>
        </form>
        <div id="myTabContent" class="tab-content">
            <div class="tab-pane fade in active" id="home">
                <div class="portlet-body">
                    <div class="row">
                        <div class="col-md-12">
                            <table class="table table-width table-striped table-bordered table-advance table-hover table-checkable " id="datatable_ajax">
                                <thead>
                                <tr>
                                    <th width="9%">供货单位</th>
                                    <th width="5%">收货日期</th>
                                    <th width="5%">材料名称</th>
                                    <th width="5%">规格</th>
                                    <th width="5%">计量单位</th>
                                    <th width="5%">收货数量</th>
                                    <th width="5%">收货单价</th>
                                    <th width="5%">收货税率</th>
                                    <th width="5%">收货不含税金额</th>
                                    <th width="5%">收货含税金额</th>
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
                    url: '<c:url value="/admin/inventory/receiptStatistics/statisticsData"/>',
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


        var entityKey = 'itemProviderName,receiptDate,materialName,specification,unit,num,price,taxRate,excTaxSumPrice,sumPrice';
        var  buildTb = function (resultList) {
            var _body = $('#datatable_ajax tbody').empty();
            var _lastId = '',_num=0, _excTaxSumPrice=0, _endNum=0, _endExcTaxSumPrice=0,_sumPrice=0,_endSumPrice=0;
            $.each(resultList,function (k,v) {
                var _tr = $('<tr>');
                if(_lastId!='' && v.itemProviderName != _lastId ){
                    _tr.append($('<td>').attr('colspan',5).text("小计"));
                    _tr.append($('<td>').text(_num.toFixed(4)));
                    _tr.append($('<td>'));
                    _tr.append($('<td>'));
                    _tr.append($('<td>').text(_excTaxSumPrice.toFixed(2)));
                    _tr.append($('<td>').text(_sumPrice.toFixed(2)));
                    _body.append(_tr);
                    _num=0;
                    _excTaxSumPrice=0;
                    _sumPrice=0;
                }
                _tr = $('<tr>');
                $.each(entityKey.split(','),function (i, e) {
                    _tr.append($('<td>').text(v[e]));
                    if(e == 'num'){
                        _num+=v[e];
                        _endNum+=v[e];
                    }else if(e == 'excTaxSumPrice'){
                        _excTaxSumPrice+=v[e];
                        _endExcTaxSumPrice+=v[e];
                    }else if(e == 'sumPrice'){
                        _sumPrice+=v[e];
                        _endSumPrice+=v[e];
                    }
                });
                _body.append(_tr);
                if(k == resultList.length-1){
                   _tr = $('<tr>');
                    _tr.append($('<td>').attr('colspan',5).text("小计"));
                    _tr.append($('<td>').text(_num.toFixed(4)));
                    _tr.append($('<td>'));
                    _tr.append($('<td>'));
                    _tr.append($('<td>').text(_excTaxSumPrice.toFixed(2)));
                    _tr.append($('<td>').text(_sumPrice.toFixed(2)));
                    _body.append(_tr);
                    _tr = $('<tr>');
                    _tr.append($('<td>').attr('colspan',5).text("总计"));
                    _tr.append($('<td>').text(_endNum.toFixed(4)));
                    _tr.append($('<td>'));
                    _tr.append($('<td>'));
                    _tr.append($('<td>').text(_endExcTaxSumPrice.toFixed(2)));
                    _tr.append($('<td>').text(_endSumPrice.toFixed(2)));
                    _body.append(_tr);
                }
                _lastId = v.itemProviderName;
            });
        }

        //导出
        var _index = null;
        $('button.js_export').on('click',function () {
            var _r = $('select[name=repository]').val();
            var _startDate = $('input[name=startDate]').val();
            var _endDate = $('input[name=endDate]').val();
            if(_startDate&&_endDate){
                _index = layer.load(2);

                $('#exportForm').remove();
                var _form = $('<form>');
                _form.attr('action','<c:url value="/admin/inventory/receiptStatistics/exportExcel"/>');
                _form.attr('id','exportForm');
                _form.attr('method','POST');
//                _form.attr('target','_blank');
                _form.append("<input type='hidden' name='startDate' value='"+_startDate+"'/>");
                _form.append("<input type='hidden' name='endDate' value='"+_endDate+"'/>");
                _form.append("<input type='hidden' name='repository' value='"+_r+"'/>");
                $(document).find('body').append(_form);
                $('#exportForm').submit();
                layer.close(_index);
            } else {
                layer.alert("请选择日期!",{icon:0});
            }

        });

    });


</script>
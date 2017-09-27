<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/admin/global/plugins/bootstrap-datepicker/css/bootstrap-datepicker3.min.css" />"/>

<div class="portlet light" id="form_wizard_1">
    <div class="portlet-title">
        <div class="caption">
            <i class="icon-layers font-green"></i>
            <span class="caption-subject font-green sbold uppercase">借款列表</span>
        </div>
    </div>
    <div class="portlet-body">
        <div class="actions form-inline ">
            <div class="form-group">
                是否支付：<select name="payStatus" class="form-control input-sm">
                <option value="">全部</option>
                <option value="0" selected>否</option>
                <option value="1">是</option>
            </select>
            </div>
            <div class="form-group">
                流水号：<input type="text" name="serialsNumber" class="form-control">
            </div>
            <div class="form-group"><button class="btn btn-sm green js_search"><i class="fa fa-search"></i> 搜索 </button></div>
        </div>
        <table class="table table-width table-striped table-bordered table-advance table-hover" id="datatable_ajax">
            <thead>
            <tr role="row" class="heading">
                <th >序号</th>
                <th >申请日期</th>
                <th >借款人</th>
                <th >借款金额</th>
                <th >事由</th>
                <th >流水号</th>
                <th >是否支付</th>
                <th >冲账状态</th>
                <th >待冲账金额</th>
                <th >操作</th>
            </tr>
            </thead>
        </table>
    </div>
</div>

<div class="modal fade js_cost_detail_div">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title">借款详情</h4>
            </div>
            <div class="modal-body">
                <div class="container-fluid js_item_detail">
                    <div class="row">
                        <div class="col-md-4">
                            <label>所属公司：</label>
                            <span class="form-group"><input readonly type="text" name="companyName" class="form-control"></span>
                        </div>
                        <div class="col-md-4">
                            <label>借款部门：</label>
                            <span class="form-group"><input readonly type="text" name="departmentName" class="form-control"></span>
                        </div>
                        <div class="col-md-4">
                            <label>流水号：</label>
                            <span class="form-group"><input readonly type="text" name="serialsNumber" class="form-control"></span>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-4">
                            <label>借款人：</label>
                             <span class="form-group"><input readonly type="text" name="applyName" class="form-control"></span>
                        </div>
                        <div class="col-md-4">
                            <label>申请日期：</label>
                             <span class="form-group"><input readonly type="text" name="applyDate" class="form-control"></span>
                        </div>
                        <div class="col-md-4">
                            <label>用途：</label>
                             <span class="form-group"><input readonly type="text" name="use" class="form-control"></span>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-4">
                            <label>借款金额（小写）：</label>
                             <span class="form-group"><input readonly type="text" name="loanMoney" class="form-control"></span>
                        </div>
                        <div class="col-md-4">
                            <label>借款金额（大写）：</label>
                             <span class="form-group"><input readonly type="text" name="loanMoneyCn" class="form-control"></span>
                        </div>
                        <div class="col-md-4">
                            <label>支付方式：</label>
                             <span class="form-group"><input readonly type="text" name="payWayStr" class="form-control"></span>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-4">
                            <label>收款人：</label>
                             <span class="form-group"><input readonly type="text" name="payee" class="form-control"></span>
                        </div>
                        <div class="col-md-4">
                            <label>开户银行：</label>
                             <span class="form-group"><input readonly type="text" name="bank" class="form-control"></span>
                        </div>
                        <div class="col-md-4">
                            <label>银行帐号：</label>
                             <span class="form-group"><input readonly type="text" name="bankNo" class="form-control"></span>
                        </div>
                    </div>
                    <div class="row js_repay_row">
                        <div class="col-md-4">
                            <label>已冲账金额：</label>
                            <span class="form-group"><input readonly type="text" name="repayMoney" class="form-control"></span>
                        </div>
                        <div class="col-md-4">
                            <label>冲账中金额：</label>
                            <span class="form-group"><input readonly type="text" name="balanceMoney" class="form-control"></span>
                        </div>
                        <div class="col-md-4">
                            <label>待冲账金额：</label>
                            <span class="form-group"><input readonly type="text" name="needRepayMoney" class="form-control"></span>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <label>事由：</label>
                             <span class="form-group"><input readonly type="text" name="remark" class="form-control"></span>
                        </div>
                    </div>
                    <div class="actions form-inline pull-right">
                        <div class="form-group"><button class="btn btn-sm blue js_add">新增</button></div>
                    </div>
                    <div class="row">
                        <table class="table table-width table-striped table-bordered table-advance js_detail_pay_table">
                            <thead>
                            <tr>
                                <th>序号</th>
                                <th>付款金额</th>
                                <th>支付方式</th>
                                <th>日期</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody></tbody>
                        </table>
                        <table class="table table-width table-striped table-bordered table-advance js_detail_repay_table hidden">
                            <thead>
                            <tr>
                                <th>序号</th>
                                <th>类型</th>
                                <th>业务流水号</th>
                                <th>冲账金额</th>
                                <th>支付方式</th>
                                <th>日期</th>
                            </tr>
                            </thead>
                            <tbody></tbody>
                        </table>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary red-mint js_cancel_pay hidden">取消付款</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary js_save_btn hidden">付款</button>
                <button type="button" class="btn btn-primary js_repay_save_btn hidden">确认还款</button>
            </div>
        </div>
    </div>
</div>


<script src="<c:url value="/resources/admin/global/scripts/datatable.js" />"></script>
<script src="<c:url value="/resources/admin/global/plugins/datatables/datatables.min.js" />"></script>
<script src="<c:url value="/resources/admin/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js" />"></script>

<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.zh-CN.js" />"></script>

<script>

    var dataTablesAjax = function() {
        var table = new Datatable;
        table.init({
            src: $("#datatable_ajax"),
            loadingMessage: "Loading...",
            dataTable: {
                bStateSave: !0,
                lengthMenu: [[10, 20, 50, 100], [10, 20, 50, 100]],
                pageLength: 10,
                ajax: {
                    url: "<c:url value="/admin/cost/costApplyListData"/>"
                },
                order: [[1, "asc"]],
                "orderCellsTop": true,
                "columnDefs": [{
                    'orderable': false,
                    'targets': [0,1,2,3,5,7]
                }],
                columns:[
                    {"data":"loanIdStr", "targets": 0},
                    {"data":"applyDate"},
                    {"data":"applyName"},
                    {"data":"loanMoney"},
                    {"data":"remark","render" : function(data, type, full, meta ){
                        return data==undefined?'':data;
                    }},
                    {"data":"serialsNumber"},
                    {"data":"status","render" : function(data, type, full, meta ){
                        if(data ==2){
                            return '未支付';
                        }else if (data==4){
                            return '已支付';
                        }
                    }},
                    {"data":"repayStatus","render" : function(data, type, full, meta ){
                        if(data == 0){
                            return '未冲账';
                        }else if (data == 1){
                            return '部分冲账';
                        }else if (data == 2){
                            return '冲账已完成';
                        }else{
                            return '状态错误';
                        }
                    }},
                    {"data":"needRepayMoney"},
                    {"data":"loanIdStr","render" : function(data, type, full, meta ){
                        if(full.status == 2){
                            return '<a href="javascript:void(0)" class="btn btn-sm btn-outline grey-salsa js_pay" data-id='+data+'><i class="fa fa-reply "></i> 付款</a>';
                        }else if (full.status == 4){
                            if(full.repayStatus==0 || full.repayStatus==1){
                                return '<a href="javascript:void(0)" class="btn btn-sm btn-outline grey-salsa js_repay" data-id='+data+'><i class="fa fa-share"></i> 还款</a>';
                            }else{
                                return '<a href="javascript:void(0)" class="btn btn-sm btn-outline grey-salsa js_show_repay" data-id='+data+'><i class="fa fa-share"></i> 查看还款</a>';
                            }
                        }
                    }}
                ],
                fnDrawCallback: function(){
                    var api = this.api();
                    var startIndex= api.context[0]._iDisplayStart;//获取到本页开始的条数
                    api.column(0).nodes().each(function(cell, i) {
                        cell.innerHTML = startIndex + i + 1;
                    });
                },"initComplete":function () {

                }
            }
        });
        return table;
    };

    $(document).ready(function() {
        var payHtml = ['<tr>',
            '    <td></td>',
            '    <td><div class="form-group"><input type="text" value="" name="money" class="form-control"></div></td>',
            '    <td>',
            '        <div class="form-group">',
            '            <select class="form-control" name="payWay">',
            '                <option value="">请选择</option>',
            '                <option value="3797233980663286561">现金</option>',
            '                <option value="-7385811571410186990">转账</option>',
            '                <option value="-6852318816803126351">打卡</option>',
            '            </select>',
            '        </div>',
            '    </td>',
            '    <td><div class="form-group"><input value=""  class="form-control datepicker" name="payDate"></div></td>',
            '    <td><div class="form-group"><button class="btn btn-sm green js_remove" type="button">删除</button></div></td>',
            '</tr>'].join("");

        var rePayHtml = ['<tr>',
            '    <td></td>',
            '    <td></td>',
            '    <td></td>',
            '    <td><div class="form-group"><input type="text" value="" name="money" class="form-control"></div></td>',
            '    <td>',
            '        <div class="form-group">',
            '            <select class="form-control" name="payWay">',
            '                <option value="">请选择</option>',
            '                <option value="3797233980663286561">现金</option>',
            '                <option value="-7385811571410186990">转账</option>',
            '                <option value="-6852318816803126351">打卡</option>',
            '            </select>',
            '        </div>',
            '    </td>',
            '    <td><div class="form-group"><input value=""  class="form-control datepicker" name="repayDate"></div></td>',
//            '    <td><div class="form-group"><button class="btn btn-sm green js_remove" type="button">删除</button></div></td>',
            '</tr>'].join("");

        var table = dataTablesAjax();
        $('button.js_search').on('click',function () {
            table.clearAjaxParams();
            table.setAjaxParam("payStatus", $('select[name=payStatus]').val());
            table.setAjaxParam("serialsNumber", $.trim($('input[name=serialsNumber]').val()));
            table.getDataTable().ajax.reload();
        });

        var repayDetail = function (obj) {
            var needRayMoney = obj.loanMoney-obj.repayMoney-obj.balanceMoney;
            $('div.js_repay_row').removeClass('hidden').find('input[name=needRepayMoney]').val(needRayMoney);
            var _arr = obj.loanRePayDetailList;
            var _body = $('.js_detail_repay_table tbody');
            _body.empty();
            for(var i in _arr){
                var _tmp = _arr[i];
                var _html = $(rePayHtml);
                _html.data('id',_tmp.balanceIdStr);
                _html.find('td:first').text(parseInt(i)+1);
                if (_tmp.type==1){
                    _html.find('td:eq(1)').text('冲账');
                }else if (_tmp.type==2){
                    _html.find('td:eq(1)').text('还款');
                }
                _html.find('td:eq(2)').text(_tmp.serialsNumber);
                _html.find('td:eq(3)').text(_tmp.theMoney);
                if (_tmp.type==1){
                    _html.find('td:eq(4)').text('冲账');
                }else if (_tmp.type==2){
                    _html.find('select option[value="'+_tmp.payWay+'"]').attr('selected',true);
                }
                _html.find('td:eq(5)').text(_tmp.repayDate);
                _body.append(_html);
            }
        }

        var showDetail = function (obj) {
            var container = popObj.getContainer();
            popObj.setDetail(obj);
            loanObj.loanId = obj.loanIdStr;
            loanObj.serialsNumber = obj.serialsNumber;
            loanObj.loanMoney = obj.loanMoney;
            $('div.js_item_detail input').each(function (k, v) {
                var _name = v.name;
                if(obj[_name]!=undefined){
                    v.value = obj[_name];
                }else{
                    v.value = '';
                }
            });
            if (popObj.getOper() == 'pay'){
                container.find('button.js_add').removeClass('hidden');
                $('div.js_repay_row').addClass('hidden');
                var _arr = obj.loanPayDetailList;
                var _body = $('.js_detail_pay_table tbody');
                _body.empty();
                for(var i in _arr){
                    var _tmp = _arr[i];
                    var _html = $(payHtml);
                    _html.data('id',_tmp.id);
                    _html.find('td:first').text(parseInt(i)+1);
                    _html.find('input[name=money]').val(_tmp.money);
                    _html.find('select[name=payWay] option[value="'+_tmp.payWay+'"]').attr('selected',true);
                    _html.find('input[name=payDate]').val(_tmp.payDate);
                    _body.append(_html);
                }
                if(obj.status == 2){
                    $('.js_save_btn').removeClass('hidden');
                }else if(obj.status == 4){
                    $('.js_cancel_pay').removeClass('hidden');
                }
            }else if (popObj.getOper() == 'repay'){
                container.find('button.js_add').removeClass('hidden');
                repayDetail(obj);
                $('.js_repay_save_btn').removeClass('hidden');
            }else if (popObj.getOper() == 'showRepay'){
                container.find('button.js_add').addClass('hidden');
                repayDetail(obj);
            }
        }

        var getDetail = function (_id,url) {
            $('.js_item_detail .form-group').find('input').val('');
            $('.modal-footer').children().not(':eq(1)').addClass('hidden');
            $.post(url, {loanId:_id}, function (result) {
                if (result.success){
                    showDetail(result.rdata);
                    $('.js_cost_detail_div').modal({'show':true,backdrop: 'static'});
                }else {
                    layer.msg(result.rmsg);
                }
            },'json');
        }

        $('#datatable_ajax').on('click','a.js_pay',function () {
            popObj.setOper('pay');
            var _id = $(this).data('id');
            getDetail(_id,'<c:url value='/admin/cost/getLoanPayOne'/>');
        }).on('click','a.js_repay',function () {
            popObj.setOper('repay');
            var _id = $(this).data('id');
            getDetail(_id,'<c:url value='/admin/cost/getLoanRePayOne'/>');
        }).on('click','a.js_show_repay',function () {
            popObj.setOper('showRepay');
            var _id = $(this).data('id');
            getDetail(_id,'<c:url value='/admin/cost/getLoanRePayOne'/>');
        });

        var loanObj = {};
        loanObj.serialsNumber=null;
        loanObj.loanId=null;
        loanObj.loanMoney=0;



        var popObj = function(){
            var _this = {
                oper : null,
                detail:null
            };
            var container = $('div.js_cost_detail_div');
            var tb = null;

            var rebuildIndex = function () {
                tb.find('tr:gt(0)').each(function (k,v) {
                    $(v).find('td:first').text(k+1);
                });
            }

            var countPayMoney = function (tb) {
                var data = 0;
                tb.find('input[name=money]').each(function (k, v) {
                    data += parseFloat(v.value);
                });
                return data;
            }

            var validateForm = function () {
                var flag = true;
                var removeError = function (obj) {
                    obj.parent('div').removeClass('has-error').find('span').remove();
                }
                var appendError = function (obj,msg) {
                    if(!obj.parent('div').hasClass('has-error')){
                        obj.after('<span id="money-error" class="help-block help-block-error">'+msg+'</span>');
                        obj.parent('div').addClass('has-error');
                    }else{
                        obj.next('span').text(msg);
                    }
                }
                tb.find('tbody tr').find('input,select').each(function (k, v) {
                    var obj = $(v);
                    if (!obj.val()){
                        appendError(obj,'必填字段');
                        flag = false;
                    }else{
                        if(obj.attr('name') == 'money'){
                            var reg =/^\d+(\.\d+)?$/;
                            if (reg.test(obj.val())){
                                removeError(obj);
                            }else{
                                appendError(obj,'请输入正确的金额');
                            }
                        }else{
                            removeError(obj);
                        }
                    }
                });
                return flag;
            }

            var validateMoney = function () {
                var moneyFlag = false;
                var data = countPayMoney(tb);
                if (data == 0){
                    if(_this.oper=='pay'){
                        layer.msg('请填写付款详情');
                    }else if (_this.oper=='repay'){
                        layer.msg('请填写还款详情');
                    }
                }else{
                    if(_this.oper=='pay') {
                        if (data > loanObj.loanMoney) {
                            layer.msg('付款金额已经大于借款金额，请修改付款详情');
                        } else if (data < loanObj.loanMoney) {
                            layer.msg('付款金额小于借款金额，请完善付款详情');
                        } else {
                            moneyFlag = true;
                        }
                    }else if (_this.oper=='repay'){
                        var _detail = _this.detail;
                        if(data>_detail.loanMoney-_detail.repayMoney-_detail.balanceMoney){
                            layer.msg('还款金额已经超过借款金额，请修改还款详情');
                        }else{
                            moneyFlag = true;
                        }
                    }
                }
                return moneyFlag;
            }

            var initTb = function (){
                tb.off('click','button.js_remove');

                tb.on('blur','input',function () {
                    validateForm();
                });

                tb.on('change','select',function () {
                    validateForm();
                });

                tb.on('click','button.js_remove',function () {
                    var _tr = $(this).parents('tr');
                    var _id = _tr.data('id');
                    if(_id){
                        var _index =layer.confirm('该操作将立即生效，您确定要删除？',function () {
                            layer.close(_index);
                            $.post('<c:url value='/admin/cost/deleteLoanPayDetail'/>', {id:_id,loanId:loanObj.loanId}, function (result) {
                                if (result.success){
                                    _tr.remove();
                                    rebuildIndex();
                                }else {
                                    layer.msg(result.rmsg);
                                }
                            },'json');
                        });
                    }else{
                        _tr.remove();
                        rebuildIndex();
                    }
                });
            }

            container.on('click','button.js_add',function () {
                if(_this.oper == 'pay'){
                    var _html = $(payHtml);
                    _html.find('select[name=payWay]>option:eq(1)').attr('selected',true);
                    _html.find('input.datepicker').datepicker({
                        todayBtn : "linked",
                        autoclose : true,
                        language: 'zh-CN',
                        format : "yyyy-mm-dd",
                        todayHighlight : true
                    }).datepicker('setDate',new Date());
                    _html.find('input[name=money]').val($('input[name=loanMoney]').val());
                    tb.append(_html);
                    rebuildIndex();
                }else if(_this.oper == 'repay'){
                    var _html = $(rePayHtml);
                    _html.data('save',true);
                    _html.find('td:eq(1)').text('还款');
                    _html.find('td:eq(2)').text('');
                    _html.find('select[name=payWay]>option:eq(1)').attr('selected',true);
                    _html.find('input.datepicker').datepicker({
                        todayBtn : "linked",
                        autoclose : true,
                        language: 'zh-CN',
                        format : "yyyy-mm-dd",
                        todayHighlight : true
                    }).datepicker('setDate',new Date());
                    _html.find('input[name=money]').val($('input[name=needRepayMoney]').val())
                    tb.append(_html);
                    rebuildIndex();
                }
            });

            container.on('click','button.js_save_btn',function () {
                if (validateForm()&&validateMoney()){
                    var dataArray = new Array();
                    tb.find('tr:gt(0)').each(function (k,v) {
                        var elements = $(v).find('input,select');
                       var obj = {};
                       var _id = $(v).data('id');
                       if(_id){
                           obj.id = _id;
                       }
                       obj['money'] = elements.eq(0).val();
                       obj['payWay'] = elements.eq(1).val();
                       obj['payDate'] = elements.eq(2).val();
                       obj.loanId=loanObj.loanId;
                       obj.serialsNumber=loanObj.serialsNumber;
                       dataArray.push(obj);
                    });
                    var _index =layer.confirm('您确定要付款吗？',function () {
                        layer.close(_index);
                        $.post('<c:url value='/admin/cost/saveLoanPayDetail'/>', {loanId:loanObj.loanId,details:JSON.stringify(dataArray)}, function (result) {
                            if (result.success){
                                layer.msg('操作成功',{ time: 800},function () {
                                    $('.js_cost_detail_div').modal('hide');
                                    table.getDataTable().ajax.reload();
                                });
                            }else {
                                layer.msg(result.rmsg);
                            }
                        },'json');
                    });
                }
            }).on('click','button.js_cancel_pay',function () {
                var _index =layer.confirm('您确定要取消付款吗？',function () {
                    layer.close(_index);
                    $.post('<c:url value='/admin/cost/cancelPay'/>', {loanId:loanObj.loanId}, function (result) {
                        if (result.success){
                            layer.msg('操作成功',{ time: 800},function () {
                                $('.js_cost_detail_div').modal('hide');
                                table.getDataTable().ajax.reload();
                            });
                        }else {
                            layer.msg(result.rmsg);
                        }
                    },'json');
                });
            }).on('click','button.js_repay_save_btn',function () {
                if (validateForm()&&validateMoney()){
                    var dataArray = new Array();
                    tb.find('tr:gt(0)').each(function (k,v) {
                        if($(v).data('save')){
                            var elements = $(v).find('input,select');
                            var obj = {};
                            var _id = $(v).data('id');
                            if(_id){
                                obj.balanceId = _id;
                            }
                            obj['theMoney'] = elements.eq(0).val();
                            obj['payWay'] = elements.eq(1).val();
                            obj['repayDate'] = elements.eq(2).val();
                            obj.loanId=loanObj.loanId;
                            obj.serialsNumber=loanObj.serialsNumber;
                            dataArray.push(obj);
                        }
                    });
                    var _index =layer.confirm('您确定要提交还款信息吗？',function () {
                        layer.close(_index);
                        $.post('<c:url value='/admin/cost/saveLoanRePayDetail'/>', {loanId:loanObj.loanId,details:JSON.stringify(dataArray)}, function (result) {
                            if (result.success){
                                layer.msg('操作成功',{ time: 800},function () {
                                    $('.js_cost_detail_div').modal('hide');
                                    table.getDataTable().ajax.reload();
                                });
                            }else {
                                layer.msg(result.rmsg);
                            }
                        },'json');
                    });
                }
            });

            _this.setOper =  function (oper) {
                _this.oper = oper;
                container.find('table').addClass('hidden');
                if (oper=='pay'){
                    tb = container.find('table.js_detail_pay_table').removeClass('hidden');
                    initTb();
                }else if(oper=='repay'){
                    tb = container.find('table.js_detail_repay_table').removeClass('hidden');
                    initTb();
                }else if(oper=='showRepay'){
                    tb = container.find('table.js_detail_repay_table').removeClass('hidden');
                }
            };

            _this.getOper = function () {
                return _this.oper;
            }

            _this.setDetail = function (detail) {
                _this.detail = detail;
            }

            _this.getContainer = function () {
                return container;
            }

         return  _this;
        }();
    });
</script>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/admin/global/plugins/bootstrap-datepicker/css/bootstrap-datepicker3.min.css" />"/>

<div class="portlet light" id="form_wizard_1">
    <div class="portlet-title">
        <div class="caption">
            <i class="icon-layers font-green"></i>
            <span class="caption-subject font-green sbold uppercase">报销列表</span>
        </div>
    </div>
    <div class="portlet-body">
        <div class="actions form-inline">
            <div class="form-group">
                是否支付：<select name="payStatus" class="form-control input-sm">
                <option value="">全部</option>
                <option value="0" selected>未支付</option>
                <option value="1">已支付</option>
                <option value="2">部分支付</option>
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
                <th >类别</th>
                <th >申请日期</th>
                <th >流水号</th>
                <th >报销人</th>
                <th >事由</th>
                <th >报销金额</th>
                <th >冲账金额</th>
                <th >应付金额</th>
                <th >实付金额</th>
                <th >支付状态</th>
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
                <h4 class="modal-title">报销详情</h4>
            </div>
            <div class="modal-body">
                <div class="container-fluid js_item_detail">
                    <div class="row">
                        <div class="col-md-8">
                            <label>标题/事由：</label>
                            <span class="form-group"><input readonly type="text" name="title" class="form-control"></span>
                        </div>
                        <div class="col-md-4">
                            <label>流水号：</label>
                            <span class="form-group"><input readonly type="text" name="serialsNumber" class="form-control"></span>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-4">
                            <label>报销人/经办人：</label>
                             <span class="form-group"><input readonly type="text" name="applyName" class="form-control"></span>
                        </div>
                        <div class="col-md-4">
                            <label>申请日期：</label>
                             <span class="form-group"><input readonly type="text" name="applyDate" class="form-control"></span>
                        </div>
                        <div class="col-md-4">
                            <label>类别：</label>
                            <span class="form-group"><input readonly type="text" name="typeStr" class="form-control"></span>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-4">
                            <label>所属公司：</label>
                            <span class="form-group"><input readonly type="text" name="companyName" class="form-control"></span>
                        </div>
                        <div class="col-md-4">
                            <label>部门：</label>
                            <span class="form-group"><input readonly type="text" name="departmentName" class="form-control"></span>
                        </div>
                        <div class="col-md-4">
                            <label>所属项目：</label>
                            <span class="form-group"><input readonly type="text" name="projectName" class="form-control"></span>
                        </div>
                    </div>
                    <div class="row js_div_contract hidden">
                        <div class="col-md-4">
                            <label>收款单位：</label>
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
                    <div class="row">
                        <div class="col-md-4">
                            <label>申请金额：</label>
                             <span class="form-group"><input readonly type="text" name="applyMoney" class="form-control"></span>
                        </div>
                        <div class="col-md-4">
                            <label>冲账金额：</label>
                             <span class="form-group"><input readonly type="text" name="balanceMoney" class="form-control"></span>
                        </div>
                        <div class="col-md-4">
                            <label>应付金额（小写）：</label>
                             <span class="form-group"><input readonly type="text" name="finalMoney" class="form-control"></span>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-4">
                            <label>应付金额（大写）：</label>
                            <span class="form-group"><input readonly type="text" name="finalMoneyCn" class="form-control"></span>
                        </div>
                        <div class="col-md-4">
                            <label>实付金额：</label>
                            <span class="form-group"><input readonly type="text" name="actualPay" class="form-control"></span>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <label>备注：</label>
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
                    </div>
                </div>
            </div>
            <div class="modal-footer">
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
                    url: "<c:url value="/admin/cost/reimbPayListData"/>"
                },
                order: [[1, "asc"]],
                "orderCellsTop": true,
                "columnDefs": [{
                    'orderable': false,
                    'targets': [0,1,2,3,5,7]
                }],
                columns:[
                    {"data":"payIdStr", "targets": 0},
                    {"data":"type","render" : function(data, type, full, meta ){
                            if(data==1){
                                return '日常报销';
                            }else if(data==2) {
                                return '差旅费报销';
                            }else if(data==3) {
                                return '费用合同付款';
                            }else if(data==4) {
                                return '非合同付款';
                            }else{
                                return '';
                            }
                    }},
                    {"data":"applyDate"},
                    {"data":"serialsNumber"},
                    {"data":"applyName"},
                    {"data":"title"},
                    {"data":"applyMoney"},
                    {"data":"balanceMoney"},
                    {"data":"finalMoney"},
                    {"data":"actualPay","render" : function(data, type, full, meta ){
                        if(data){
                            return data;
                        }else{
                            return 0;
                        }
                    }},
                    {"data":"status","render" : function(data, type, full, meta ){
                        if(data ==2){
                            return '未支付';
                        }else if (data==4){
                            return '已支付';
                        }else if(data==5){
                            return '部分支付';
                        }
                    }},
                    {"data":"payIdStr","render" : function(data, type, full, meta ){
                        return '<a href="javascript:void(0)" class="btn btn-sm btn-outline grey-salsa js_pay" data-id='+data+'><i class="fa fa-reply "></i> 付款</a>';
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
            '                <option value="1">现金</option>',
            '                <option value="2">转账</option>',
            '                <option value="3">承兑汇票</option>',
            '                <option value="4">打卡</option>',
            '            </select>',
            '        </div>',
            '    </td>',
            '    <td><div class="form-group"><input value=""  class="form-control datepicker" name="payDate"></div></td>',
            '    <td><div class="form-group"><button class="btn btn-sm green js_remove" type="button">删除</button></div></td>',
            '</tr>'].join("");


        var table = dataTablesAjax();
        $('button.js_search').on('click',function () {
            table.clearAjaxParams();
            table.setAjaxParam("payStatus", $('select[name=payStatus]').val());
            table.setAjaxParam("serialsNumber", $.trim($('input[name=serialsNumber]').val()));
            table.getDataTable().ajax.reload();
        });

        var showDetail = function (obj) {
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
            if(obj.type==3 || obj.type ==4){
                $('div.js_div_contract').removeClass('hidden');
            }else{
                $('div.js_div_contract').addClass('hidden');
            }
            $('div.js_repay_row').addClass('hidden');
            var _arr = obj.reimbPayDetailList;
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
            if(obj.status == 2 ){
                $('.js_save_btn').removeClass('hidden');
            }else
                if(obj.status == 5){
                $('.js_save_btn').removeClass('hidden');
            }
        }

        var getDetail = function (_id,url) {
            $('.js_item_detail .form-group').find('input').val('');
            $('.modal-footer').children().filter(':gt(0)').addClass('hidden');
            $.post(url, {payId:_id}, function (result) {
                if (result.success){
                    showDetail(result.rdata);
                    $('.js_cost_detail_div').modal({'show':true,backdrop: 'static'});
                }else {
                    layer.msg(result.rmsg);
                }
            },'json');
        }

        $('#datatable_ajax').on('click','a.js_pay',function () {
            var _id = $(this).data('id');
            getDetail(_id,'<c:url value='/admin/cost/getCostReimbPayOneDetail'/>');
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
            var tb = container.find('table.js_detail_pay_table');

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
                    layer.msg('请填写付款详情');
                }else{
                    if (data > _this.detail.finalMoney) {
                        layer.msg('付款金额不能大于应付金额，请修改');
                    } else {
                        moneyFlag = true;
                    }
                }
                return moneyFlag;
            }

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
                        $.post('<c:url value='/admin/cost/deleteReimbPayDetail'/>', {id:_id,payId:_this.detail.payIdStr,contractId:_this.detail.contractIdStr}, function (result) {
                            if (result.success){
                                $('.js_cost_detail_div').modal('hide');
                                table.getDataTable().ajax.reload();
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


            container.on('click','button.js_add',function () {
                var _html = $(payHtml);
                _html.find('input.datepicker').datepicker({
                    todayBtn : "linked",
                    autoclose : true,
                    language: 'zh-CN',
                    format : "yyyy-mm-dd",
                    todayHighlight : true
                }).datepicker('setDate',new Date());
                _html.find('input[name=money]').val(parseFloat($('input[name=finalMoney]').val())-parseFloat($('input[name=actualPay]').val()));
                tb.append(_html);
                rebuildIndex();

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
                       obj.payId=_this.detail.payIdStr;
                       obj.serialsNumber=_this.detail.serialsNumber;
                       dataArray.push(obj);
                    });
                    var _index =layer.confirm('您确定要付款吗？',function () {
                        layer.close(_index);
                        $.post('<c:url value='/admin/cost/saveReimbPayDetail'/>', {payId:_this.detail.payIdStr,contractId:_this.detail.contractIdStr,details:JSON.stringify(dataArray)}, function (result) {
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
                            obj.payId=_this.detail.payId;
                            obj.serialsNumber=_this.detail.serialsNumber;
                            dataArray.push(obj);
                        }
                    });
                    var _index =layer.confirm('您确定要提交还款信息吗？',function () {
                        layer.close(_index);
                        $.post('<c:url value='/admin/cost/saveReimbPayDetail'/>', {payId:_this.detail.payIdStr,contractId:_this.detail.contractIdStr,details:JSON.stringify(dataArray)}, function (result) {
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

            _this.setDetail = function (detail) {
                _this.detail = detail;
            };
            _this.getDetail = function () {
                return _this.detail;
            }

            _this.addDetailTr = function (v1) {
                container.find('button.js_add').trigger('click');
                tb.find('input[name=money]:last').val(v1);
            }
         return  _this;
        }();
    });
</script>
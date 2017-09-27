<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib uri="/WEB-INF/tlds/lycore.tld" prefix="ly"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/admin/global/plugins/bootstrap-datepicker/css/bootstrap-datepicker3.min.css" />"/>
<div class="portlet light" id="form_wizard_1">
    <div class="portlet-title">
        <div class="caption">
            <i class="icon-layers font-green"></i>
            <span class="caption-subject font-green sbold uppercase">营销费用报销列表</span>
        </div>
    </div>
    <div class="portlet-body">
        <div class="actions form-inline">
            <div class="form-group">
                是否支付：<select id="id_i1" class="form-control input-sm">
                <option value="0">全部</option>
                <option value="2" <c:if test="${entity.i1 eq 2}" >selected</c:if> >未支付</option>
                <option value="4" <c:if test="${entity.i1 eq 4}" >selected</c:if> >已支付</option>
                <option value="5" <c:if test="${entity.i1 eq 5}" >selected</c:if> >部分支付</option>
            </select>
            </div>
            <div class="form-group">
                流水号：<input type="text" id="id_s1" class="form-control" value="${entity.s1}" placeholder="流水号">
            </div>
            <div class="form-group"><button class="btn btn-sm green js_search" onclick="search();"><i class="fa fa-search"></i> 搜索 </button></div>
        </div>
        <div class="table-scrollable fixed-table-container">
            <table class="table table-width table-striped table-bordered table-advance table-hover" id="datatable_ajax">
                <thead>
                <tr role="row" class="heading">
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
                <tbody>
                <c:forEach items="${page.content}" var="e" varStatus="status">
                    <tr>
                        <td>${e.typeName}</td>
                        <td>${fn:substring(e.applyDate,0 , 10)}</td>
                        <td>${e.serialsNumber}</td>
                        <td>${e.applyName}</td>
                        <td>${e.title}</td>
                        <td>${e.applyMoney}</td>
                        <td>${e.balanceMoney}</td>
                        <td>${e.finalMoney}</td>
                        <td>${e.actualPay}</td>
                        <td>${e.statusName}</td>
                        <td>
                            <a href="javascript:;" class="btn btn-sm btn-outline grey-salsa js_pay" data-id="${e.payId}" data-serialsnumber="${e.serialsNumber}"><i class="fa fa-reply "></i> 付款</a>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty page.content}">
                    <tr><td colspan="20">没有对应的数据</td></tr>
                </c:if>
                </tbody>
            </table>
        </div>
        <div class="row">
            <div class="col-md-5 col-sm-5">
                <ly:pageSizeTag link="/admin/marketcost/get${ly:toString(entity,'i1,s1,dt,dtn')}" page="${page}"></ly:pageSizeTag>
            </div>
            <div class="col-md-7 col-sm-7 text-right">
                <ly:pageTag link="/admin/marketcost/get${ly:toString(entity,'i1,s1,dt,dtn')}&pageNo=" page="${page}"></ly:pageTag>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.zh-CN.js" />"></script>
<script type="text/javascript">
    var js_detail_pay_table,global_payId,global_serialsNumber,payHtml;//支付列表对象
    $(function () {
        js_detail_pay_table = $('div.js_cost_detail_div').find('table.js_detail_pay_table')//支付列表对象

        //获取付款详情事件
        $(".js_pay").on("click",function () {
            $('.js_item_detail .form-group').find('input').val('');
            $('.modal-footer').children().filter(':gt(0)').addClass('hidden');
            var _id = $(this).data("id");
            global_payId = _id;
            global_serialsNumber = $(this).data("serialsnumber");
            detail(_id);
        });

        //支付列表动态添加列表事件
        $('button.js_add').on('click',function () {
            var _html = $(payHtml);
            _html.find('input.datepicker').datepicker({
                todayBtn : "linked",
                autoclose : true,
                language: 'zh-CN',
                format : "yyyy-mm-dd",
                todayHighlight : true
            }).datepicker('setDate',new Date());
            _html.find('input[name=money]').val(parseFloat($('.js_item_detail input[name=finalMoney]').val())-parseFloat($('.js_item_detail input[name=actualPay]').val()));
            js_detail_pay_table.append(_html);
            rebuildIndex();
        });

        //支付列表金额填充验证事件
        js_detail_pay_table.on('blur','input[name=money]',function () {
            var _val = $.trim($(this).val());
            if (isNaN(_val)||_val=='') {
                _val = 0.00;
                layer.msg("付款金额只能为数字");
            } else {
                _val = parseFloat(_val).toFixed(2);
            }
            $(this).val(_val);
        });

        //确认付款事件
        $('button.js_save_btn').on('click',function () {
            if (validateForm()&&validateMoney()){
                var dataArray = new Array();
                js_detail_pay_table.find('tr:gt(0)').each(function (k,v) {
                    var elements = $(v).find('input,select');
                    var obj = {};
                    var _id = $(v).data('id');
                    if(_id){
                        obj.id = _id;
                    }
                    obj.money = elements.eq(0).val();
                    obj.payWay = elements.eq(1).val();
                    obj.payDate = elements.eq(2).val();
                    obj.payId=global_payId;
                    obj.serialsNumber=global_payId;
                    dataArray.push(obj);
                });
                layer.confirm("您确定要付款吗？", {icon: 3, title:'付款提示'}, function(){
                    layer.closeAll();
                    $.ajax({
                        url: '<c:url value="/admin/marketcost/pay" />',
                        type: 'POST',
                        data:{payId:global_payId,details:JSON.stringify(dataArray)},
                        dataType: 'json',
                        success: function(data){
                            layer.closeAll('loading');
                            data = JSON.parse(data);
                            if (data.code=="0"){
                                layer.msg(data.msg,{ time: 1000,icon: 1},function () {
                                    $('.js_cost_detail_div').modal('hide');
                                    location.reload(true);
                                });
                            } else {
                                layer.msg(data.msg,{icon: 2});
                            }
                        },
                        beforeSend:function () {layer.load(0,{shade: [0.3,'#000']});},
                        error: function(){layer.closeAll('loading');}
                    });
                });
            }
        });

        //支付列表删除列表事件
        js_detail_pay_table.on('click','button.js_remove',function () {
            var _tr = $(this).parents('tr');
            var _id = _tr.data('id');
            if(_id){
                layer.confirm("该操作将立即生效，您确定要删除？", {icon: 3, title:'删除提示'}, function(){
                    layer.closeAll();
                    $.ajax({
                        url: '<c:url value="/admin/marketcost/delete" />',
                        type: 'POST',
                        data:{payId:global_payId,id:_id},
                        dataType: 'json',
                        success: function(data){
                            layer.closeAll('loading');
                            data = JSON.parse(data);
                            if (data.code=="0"){
                                layer.msg(data.msg,{ time: 1000,icon: 1},function () {
                                    $('.js_cost_detail_div').modal('hide');
                                    location.reload(true);
                                });
                            } else {
                                layer.msg(data.msg,{icon: 2});
                            }
                        },
                        beforeSend:function () {layer.load(0,{shade: [0.3,'#000']});},
                        error: function(){layer.closeAll('loading');}
                    });
                });
            }else{
                _tr.remove();
                rebuildIndex();
            }
        });

        //动态生成支付数据模板
        payHtml = ['<tr>',
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
            '    <td><div class="form-group"><input value=""  class="form-control datepicker" name="payDate" readonly="readonly"></div></td>',
            '    <td><div class="form-group"><button class="btn btn-sm green js_remove" type="button">删除</button></div></td>',
            '</tr>'].join("");

    });

    //搜索
    function search() {
        window.location.href = "<c:url value="/admin/marketcost/get"/>"+ "${ly:toString(entity,'pageSize,dt,dtn')}"+"&s1="+$.trim($("#id_s1").val())+"&i1="+$("#id_i1").val();
    }

    //付款详情
    function detail(id) {
        $.ajax({
            url: '<c:url value="/admin/marketcost/getdetail/" />'+id,
            type: 'POST',
            data:'',
            dataType: 'json',
            success: function(data){
                layer.closeAll('loading');
                data = JSON.parse(data);
                if (data.code=="0"){
                    $(".js_item_detail").setForm(data.data.detail);
                    if (data.data.detail.type==1||data.data.detail.type==2) {
                        $("#id_contractcode,#id_bank").show();
                    }
                    if (data.data.detail.type==3||data.data.detail.type==4) {
                        $("#id_contractcode").hide();
                        if (data.data.detail.type==4) {
                            $("#id_bank").hide();
                        }else {
                            $("#id_bank").show();
                        }
                    }
                    initializePayDate(data.data.detail.costReimbursementPayDetailList,data.data.detail.status);
                    $('.js_cost_detail_div').modal({'show':true,backdrop: 'static'});
                } else {
                    layer.msg(data.msg);
                }
            },
            beforeSend:function () {layer.load(0,{shade: [0.3,'#000']});},
            error: function(){layer.closeAll('loading');}
        });
    }

   /* 初始化付款数据*/
    function initializePayDate (obj,s) {
        var _body = $('.js_detail_pay_table tbody');
        _body.empty();
        $.each(obj,function (index,item) {
            var _html = $(payHtml);
            _html.data('id',item.id);
            _html.find('td:first').text(parseInt(index)+1);
            _html.find('input[name=money]').val(item.money);
            _html.find('select[name=payWay] option[value="'+item.payWay+'"]').attr('selected',true);
            _html.find('input[name=payDate]').val(item.payDate);
            _body.append(_html);
        });
        if(s== 2 ||s == 5){
            $('.js_save_btn').removeClass('hidden');
        }
    }

    //给付款详情支付列表动态排序
    function rebuildIndex() {
        js_detail_pay_table.find('tr:gt(0)').each(function (k,v) {
            $(v).find('td:first').text(k+1);
        });
    }

    //确认付款验证
    function validateForm() {
        var flag = true;
        js_detail_pay_table.find('tbody tr').find('input,select').each(function (k, v) {
            var obj = $(v);
            if (!obj.val()){
                layer.msg("付款数据不能为空");
                flag = false;
                return false;
            }else{
                if(obj.attr('name') == 'money'){
                    var reg =/^\d+(\.\d+)?$/;
                    if (!reg.test(obj.val())){
                        layer.msg("请输入正确的金额");
                        flag = false;
                        return false;
                    }
                }
            }
        });
        return flag;
    }

    //确认付款验证-金额
    function validateMoney() {
        var moneyFlag = true;
        var _data = countPayMoney();
        if (_data == 0){
            layer.msg('付款金额不能为0');
            moneyFlag = false;
        }else{
            var _balance = parseFloat($('.js_item_detail input[name=finalMoney]').val())-parseFloat($('.js_item_detail input[name=actualPay]').val());
            if (parseFloat(_data) > parseFloat(_balance)) {
                layer.msg('付款金额不能大于剩余应付金额');
                moneyFlag = false;
            }
        }
        return moneyFlag;
    }

    //确认付款验证-金额-获取全部支付金额
    function countPayMoney() {
        var data = 0;
        js_detail_pay_table.find('input[name=money]').each(function (k, v) {
            data += parseFloat(v.value);
        });
        return data;
    }

    //获取付款详情后动态填充展示数据
    $.fn.setForm = function(jsonValue) {
        var obj = this;
        $.each(jsonValue,function (name,ival) {
            obj.find("[name="+name+"]").val(ival);
        });
    }

</script>
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
                            <label>合同名称/事由/摘要：</label>
                            <span class="form-group"><input readonly type="text" name="title" class="form-control"></span>
                        </div>
                        <div class="col-md-4">
                            <label>流水号：</label>
                            <span class="form-group"><input readonly type="text" name="serialsNumber" class="form-control"></span>
                        </div>
                    </div>
                    <div id="id_contractcode" class="row">
                        <div class="col-md-8">
                            <label>合同编号：</label>
                            <span class="form-group"><input readonly type="text" name="contractCode" class="form-control"></span>
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
                            <span class="form-group"><input readonly type="text" name="typeName" class="form-control"></span>
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
                            <label>所属项目区域/集团：</label>
                            <span class="form-group"><input readonly type="text" name="projectCompanyName" class="form-control"></span>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-4">
                            <label>所属项目：</label>
                            <span class="form-group"><input readonly type="text" name="projectName" class="form-control"></span>
                        </div>
                        <div class="col-md-4">
                            <label>申请金额：</label>
                             <span class="form-group"><input readonly type="text" name="applyMoney" class="form-control"></span>
                        </div>
                        <div class="col-md-4">
                            <label>冲账金额：</label>
                             <span class="form-group"><input readonly type="text" name="balanceMoney" class="form-control"></span>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-4">
                            <label>应付金额（小写）：</label>
                            <span class="form-group"><input readonly type="text" name="finalMoney" class="form-control"></span>
                        </div>
                        <div class="col-md-4">
                            <label>应付金额（大写）：</label>
                            <span class="form-group"><input readonly type="text" name="finalMoneyCn" class="form-control"></span>
                        </div>
                        <div class="col-md-4">
                            <label>实付金额：</label>
                            <span class="form-group"><input readonly type="text" name="actualPay" class="form-control"></span>
                        </div>
                    </div>
                    <div id="id_bank" class="row">
                        <div class="col-md-4">
                            <label>银行帐号：</label>
                            <span class="form-group"><input readonly type="text" name="bankNo" class="form-control"></span>
                        </div>
                        <div class="col-md-4">
                            <label>收款单位：</label>
                            <span class="form-group"><input readonly type="text" name="payee" class="form-control"></span>
                        </div>
                        <div class="col-md-4">
                            <label>开户银行：</label>
                            <span class="form-group"><input readonly type="text" name="bank" class="form-control"></span>
                        </div>
                    </div>
                    <%--<div class="row">
                        <div class="col-md-4">
                            <label>费用入账单位：</label>
                            <span class="form-group"><input readonly type="text" name="unitName" class="form-control"></span>
                        </div>
                    </div>--%>
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


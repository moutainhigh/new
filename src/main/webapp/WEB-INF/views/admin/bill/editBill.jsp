<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<link type="text/css" href="/resources/admin/global/plugins/select2/css/select2.min.css"  rel="stylesheet">
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/admin/global/plugins/bootstrap-datepicker/css/bootstrap-datepicker3.min.css" />"/>
<!-- BEGIN SAMPLE FORM PORTLET-->
<div class="portlet box green">
    <div class="portlet-title">
        <div class="caption">
        	<i class="icon-settings"></i>
            <a href="<c:url value="/admin/bill/billList"/>">发票台账</a>
            <span><i class="fa fa-angle-right"></i>发票管理</span>
        </div>
    </div>
    <div class="portlet-body form">
        <form class="form-horizontal" id="editForm" method="post" autocomplete="off">
            <input type="hidden" name="id" value="${id}" />
			<div class="form-body">

            <div class="form-group">
                <label class="col-md-1 control-label">经办人：</label>
                <div class="col-md-4">
                    <select class="form-control" name="handleUser"  multiple >
                        <c:forEach items="${userList}" var="u">
                            <option value="${u}" <c:if test="${bill.handleUser eq u}">selected="selected"</c:if>>${u}</option>
                        </c:forEach>
                    </select>
                </div>
                <label class="col-md-1 control-label">区域：</label>
                <div class="col-md-4">
                    <select class="form-control js_getNext" name="areaId" data-next="companyId" data-level="0">
                        <option value="">请选择</option>
                        <c:forEach items="${area}" var="dict">
                            <option value="${dict.id}" <c:if test="${bill.areaId eq dict.id}">selected="selected"</c:if>>${dict.value}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-1 control-label">公司：</label>
                <div class="col-md-4">
                    <select class="form-control js_getNext" name="companyId" data-next="projectId" data-level="1">
                        <option value="">请选择</option>
                        <c:forEach items="${company}" var="dict">
                            <option value="${dict.id}" <c:if test="${bill.companyId eq dict.id}">selected="selected"</c:if>>${dict.value}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-1 control-label">项目：</label>
                <div class="col-md-4">
                    <select class="form-control js_getNext" name="projectId" data-next="stageId" data-level="2">
                        <option value="">请选择</option>
                        <c:forEach items="${project}" var="dict">
                            <option value="${dict.id}" <c:if test="${bill.projectId eq dict.id}">selected="selected"</c:if>>${dict.value}</option>
                        </c:forEach>
                    </select>
                </div>
                <label class="col-md-1 control-label">分期：</label>
                <div class="col-md-4">
                    <select class="form-control" name="stageId" >
                        <option value="">请选择</option>
                        <c:forEach items="${stage}" var="dict">
                            <option value="${dict.id}" <c:if test="${bill.stageId eq dict.id}">selected="selected"</c:if>>${dict.value}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-1 control-label">发票日期：</label>
                <div class="col-md-4">
                    <input type="text" name="invoiceDate" placeholder="年-月-日" value="<fmt:formatDate value="${bill.invoiceDate}" pattern="yyyy-MM-dd"/>" class="form-control"> </div>
                <label class="col-md-1 control-label">对方单位：</label>
                <div class="col-md-4">
                    <select class="form-control" name="targetCompany" multiple>
                        <c:forEach items="${companyList}" var="c">
                            <option value="${c}" <c:if test="${bill.targetCompany eq c}">selected="selected"</c:if>>${c}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-1 control-label">发票代码：</label>
                <div class="col-md-4">
                    <input type="text" name="invoiceCode" value="<c:if test="${not empty id}">${bill.invoiceCode}</c:if>" class="form-control"> </div>
                <label class="col-md-1 control-label">发票编号：</label>
                <div class="col-md-4">
                    <input type="text" name="invoiceNum" value="<c:if test="${not empty id}">${bill.invoiceNum}</c:if>" class="form-control"> </div>
            </div>

            <div class="form-group">
                <label class="col-md-1 control-label">货物及服务：</label>
                <div class="col-md-4">
                    <input type="text" name="subject" value="<c:if test="${not empty id}">${bill.subject}</c:if>" class="form-control"> </div>
                <label class="col-md-1 control-label">价税合计：</label>
                <div class="col-md-4">
                    <input type="text" name="totalMoney" readonly="readonly" value="<c:if test="${not empty id}">${bill.totalMoney}</c:if>" class="form-control"> </div>
            </div>

            <div class="form-group">
                <label class="col-md-1 control-label">价款：</label>
                <div class="col-md-4">
                    <input type="text" name="price" value="<c:if test="${not empty id}">${bill.price}</c:if>" class="form-control"> </div>
                <label class="col-md-1 control-label">税额：</label>
                <div class="col-md-4">
                    <input type="text" name="tax" value="<c:if test="${not empty id}">${bill.tax}</c:if>" class="form-control"> </div>
            </div>

            <div class="form-group">
                <label class="col-md-1 control-label">税率：</label>
                <div class="col-md-4">
                    <input type="text" name="taxRate" readonly="readonly" value="<c:if test="${not empty id}">${bill.taxRate}</c:if>" class="form-control"> </div>
                <label class="col-md-1 control-label">认证情况：</label>
                <div class="col-md-4">
                    <select class="form-control" name="authStatus" >
                        <option value="0" <c:if test="${bill.authStatus eq 0}">selected="selected"</c:if>>未认证</option>
                        <option value="1" <c:if test="${bill.authStatus eq 1}">selected="selected"</c:if>>已认证</option>
                    </select>
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-1 control-label">稽核情况：</label>
                <div class="col-md-4">
                <select class="form-control" name="inspectStatus" >
                    <option value="0" <c:if test="${bill.inspectStatus eq 0}">selected="selected"</c:if>>未核实</option>
                    <option value="1" <c:if test="${bill.inspectStatus eq 1}">selected="selected"</c:if>>已核实</option>
                </select>
                </div>
                <label class="col-md-1 control-label">操作员：</label>
                <div class="col-md-4">
                    <c:if test="${empty bill}">
                        <input type="text" value="${oper}" class="form-control" readonly="readonly">
                    </c:if>
                    <c:if test="${not empty bill}">
                        <input type="text" value="${bill.username}" class="form-control" readonly="readonly">
                    </c:if>
                </div>
            </div>


            <div class="form-group">
                <label class="col-md-1 control-label">稽核备注：</label>
                <div class="col-md-4">
                    <textarea cols="3" rows="3" class="form-control" name="inspectNote">${bill.inspectNote}</textarea>
                </div>
            </div>

            </div>

            <sec:authorize access="hasRole('r7')">
            <div class="form-actions">
	            <div class="row">
	                <div class="col-md-offset-1 col-md-10">
	                    <button class="btn btn-lg blue js_submit" type="button">提  交</button>
	                    <a class="btn btn-lg grey-salsa" href="javascript:history.back(-1);">返回</a>
	                </div>
	            </div>
             </div>
            </sec:authorize>
        </form>
    </div>
</div>
<!-- END SAMPLE FORM PORTLET-->


<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.zh-CN.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/select2/js/select2.full.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/jquery-validation/js/jquery.validate.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/jquery-validation/js/additional-methods.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/jquery-validation/js/messages_zh.min.js" />"></script>


<script type="text/javascript">

    $(function(){



        $('select.js_getNext').on('change',function () {
            var _id = $(this).val();
            var next = $(this).data('next');
            resetSelect($(this));
            var html = '<option  value="">请选择</option>';
            $.post('<c:url value="/admin/bill/childDictList"/>',{id:_id},function (result) {
                if (null!=result){
                    $(result).each(function (k, v) {
                        html += '<option value="' + v.id + '">' + v.value + '</option>';
                    });
                    $('select[name='+next+']').html(html);
                }
            },'json');
        });
        
        var resetSelect = function (obj) {
            var _level = $(obj).data('level');
            var next = $(obj).data('next');
            if (_level!=undefined && next){
                $('select[name='+next+']').find('option:gt(0)').remove();
                if (_level < 3){
                    resetSelect($('select[name='+next+']'));
                }
            }
        }



        var form = $('#editForm');
        form.validate({
            errorElement : 'span',
            errorClass : 'help-block help-block-error',
            focusInvalid : false,
            ignore : "",
            rules : {
                handleUser : {
                    required : true
                },
                title : {
                    required : true
                },
                areaId:{
                    required : true
                },
                companyId : {
                    required : true
                },
                invoiceDate : {
                    dateISO:true,
                    required : true
                },
                targetCompany : {
                    required : true
                },
                invoiceCode : {
                    required : true
                },
                invoiceNum : {
                    required : true
                },
                totalMoney : {
                    number:true,
                    required : true
                },
                price : {
                    number:true,
                    required : true
                },
                tax : {
                    number:true,
                    required : true
                },
                taxRate : {
                    number:true,
                    required : true
                }
            },
            messages : {
                totalMoney : {
                    required : "这是必填字段",
                    digits: "只能输入整数"
                },price : {
                    required : "这是必填字段",
                    digits: "只能输入整数"
                },tax : {
                    required : "这是必填字段",
                    digits: "只能输入整数"
                },taxRate : {
                    required : "这是必填字段",
                    digits: "只能输入整数"
                }
            },
            invalidHandler : function(event, validator) {

            },
            highlight : function(element) {
                $(element).closest('.form-group').addClass('has-error');
            },
            unhighlight : function(element) {
                $(element).closest('.form-group').removeClass('has-error');
            },
            success : function(label) {
                label.closest('.form-group').removeClass('has-error');
            },
            submitHandler : function(form) {
                if (!validateCode()){
                    layer.msg("发票编号重复");
                    return;
                }
                layer.confirm("你确定提交吗！",function () {
                    $.post('<c:url value="/admin/bill/saveBill"/>',$(form).serialize(),function (result) {
                        if (result==0){
                            layer.alert("保存失败");
                        }else {
                            layer.msg('保存成功',{anim:false,time:1000},function () {
                                window.location.reload();
                            });
                        }
                    },'json');
                });
            }
        });


        $('button.js_submit').on('click',function () {
            form.submit();
        });
        
        var oldCode = $('input[name=invoiceNum]').val();
        var validateCode = function () {
            var flag = false;
            var _code = $('input[name=invoiceNum]').val();
            if (oldCode!=_code){
                $.ajax({
                    type: 'POST',
                    url: '<c:url value="/admin/bill/validateInvoiceNum"/>',
                    async: false,
                    data: {num:_code},
                    dataType: "json",
                    success: function (result) {
                        if (result==0){
                            flag = true; //验证通过
                        }
                    }
                });
            }else {
                flag = true;
            }
            return flag;
        }

        $('select[name=handleUser]').select2({
            tags: true
        });

        $('select[name=targetCompany]').select2({
            tags: true
        });

        $('input[name=invoiceDate]').datepicker({
            todayBtn : "linked",
            autoclose : true,
            language: 'zh-CN',
            format : "yyyy-mm-dd",
            todayHighlight : true,
            endDate : new Date()
        });


        $('input[name=price]').on('keyup',function () {
            var price = $(this).val();
            var tax = $('input[name=tax]').val();
            countMoney(price,tax);
            setTaxRate(price,tax);
        });

        $('input[name=tax]').on('keyup',function () {
            var tax = $(this).val();
            var price = $('input[name=price]').val();
            countMoney(price,tax);
            setTaxRate(price,tax);
        });


        var countMoney = function (price, tax) {
            if (price&&tax){
                var sum;
                if(!isNaN(price)&&!isNaN(tax)){
                    sum = parseFloat(price) + parseFloat(tax);
                }else {
                    sum = '';
                }
                $('input[name=totalMoney]').val(sum);
            }
        }

        var setTaxRate = function (price, tax) {
            if (price&&tax){
                var taxRate;
                if(!isNaN(price)&&!isNaN(tax)){
                    taxRate = (parseFloat(tax)/parseFloat(price)).toFixed(4); ;
                }else {
                    taxRate = '';
                }
                $('input[name=taxRate]').val(taxRate);
            }
        }

    });




</script>
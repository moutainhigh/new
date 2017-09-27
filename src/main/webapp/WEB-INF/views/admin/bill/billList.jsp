<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/tlds/lycore.tld" prefix="ly"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<link rel="stylesheet" type="text/css" href="<c:url value="/resources/admin/global/plugins/bootstrap-datepicker/css/bootstrap-datepicker3.min.css" />"/>

<!-- BEGIN EXAMPLE TABLE PORTLET-->
<div class="portlet light" id="form_wizard_1">
    <div class="portlet-title">
        <div class="caption">
        	<i class="icon-layers font-green"></i>
            <span class="caption-subject font-green sbold uppercase">发票台账</span>
       	</div>
        <div class="actions form-inline">
            发票编号：<input name="invoiceNum" class="form-control input-inline input-sm" type="text" value="${entity.invoiceNum}"/>
            发票日期：<input name="invoiceStartDate" class="form-control input-inline input-sm" type="text" value="<fmt:formatDate value="${entity.invoiceStartDate}" pattern="yyyy-MM-dd"/>"/>
            - <input name="invoiceEndDate" class="form-control input-inline input-sm" type="text" value="<fmt:formatDate value="${entity.invoiceEndDate}" pattern="yyyy-MM-dd"/>"/>
            认证情况：<select name="authStatus" class="form-control">
                        <option value="">全部</option>
                        <option value="1"<c:if test="${entity.authStatus eq 1}">selected="selected"</c:if>>已认证</option>
                        <option value="0"<c:if test="${entity.authStatus eq 0}">selected="selected"</c:if>>未认证</option>
                      </select>
            稽核情况：<select name="inspectStatus" class="form-control">
                            <option value="">全部</option>
                            <option value="1"<c:if test="${entity.inspectStatus eq 1}">selected="selected"</c:if>>已核实</option>
                            <option value="0"<c:if test="${entity.inspectStatus eq 0}">selected="selected"</c:if>>未核实</option>
                       </select>
            凭证号：<input name="certificateCode" class="form-control input-inline input-sm" type="text" value="${entity.certificateCode}"/>
                    <button class="btn btn-sm green js_search"><i class="fa fa-search"></i> 搜索 </button>
                    <sec:authorize access="hasRole('r7')">
                    <button class="btn sbold blue js_insert_bill"><i class="fa fa-plus-circle"></i>新增</button>
                    </sec:authorize>
                    <button class="btn sbold blue js_download_bill"><i class="fa fa-plus-circle"></i>导出</button>
        </div>
    </div>
    <div class="portlet-body">
        <div class="table-responsive">
        <table class="table table-striped table-bordered table-advance table-hover" id="tlist">
            <thead>
                <tr>
                    <th>经办人</th>
                    <th>公司</th>
                    <th>项目</th>
                    <th>分期</th>
                    <th>发票日期</th>
                    <th>对方单位</th>
                    <th>发票代码</th>
                    <th>发票编号</th>
                    <th>货物及服务</th>
                    <th>价税合计</th>
                    <th>价款</th>
                    <th>税额</th>
                    <th>税率</th>
                    <th>认证情况</th>
                    <th>稽核情况</th>
                    <th>凭证号</th>
                    <th>创建日期</th>
                    <sec:authorize access="hasRole('r7') or hasRole('r8')">
                        <th> 操作 </th>
                    </sec:authorize>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${page.content}" var="entity" varStatus="status">
                    <tr>
                        <td>${entity.handleUser }</td>
                        <td>${entity.company }</td>
                        <td>${entity.project }</td>
                        <td>${entity.stage }</td>
                        <td><fmt:formatDate value="${entity.invoiceDate}" pattern="yyyy-MM-dd"/></td>
                        <td>${entity.targetCompany }</td>
                        <td>${entity.invoiceCode }</td>
                        <td>${entity.invoiceNum }</td>
                        <td>${entity.subject }</td>
                        <td>${entity.totalMoney }</td>
                        <td>${entity.price }</td>
                        <td>${entity.tax }</td>
                        <td>${entity.taxRate }</td>
                        <td>${entity.authStatusStr }</td>
                        <td>${entity.inspectStatusStr }</td>
                        <td>${entity.certificateCode }</td>
                        <td><fmt:formatDate value="${entity.createtime }" pattern="yyyy-MM-dd"/></td>
                        <sec:authorize access="hasRole('r7') or hasRole('r8')">
                        <td>
                            <button class="btn btn-xs green js_show_bill" data-id="${entity.id}"><i class="fa fa-plus-circle"></i> 查看 </button>
                            <sec:authorize access="hasRole('r7')">
                            <button class="btn btn-xs green js_edit_bill" data-id="${entity.id}"><i class="fa fa-plus-circle"></i> 编辑 </button>
                            <button class="btn btn-xs green js_del_bill" data-id="${entity.id}"><i class="fa fa-plus-circle"></i> 删除 </button>
                            </sec:authorize>
                            <sec:authorize access="hasRole('r8')">
                            <button class="btn btn-xs green js_edit_certificate" data-id="${entity.id}" data-code="${entity.certificateCode }"><i class="fa fa-minus-circle"></i> 录入凭证 </button>
                            </sec:authorize>
                        </td>
                        </sec:authorize>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        </div>
        <div class="row">
	    	<div class="col-md-5 col-sm-5">
	    		<ly:pageSizeTag link="/admin/bill/billList${ly:toString(entity,'invoiceNum,invoiceDate,authStatus,inspectStatus,certificateCode,dt,dtn')}" page="${page}"></ly:pageSizeTag>
	    	</div>
	    	<div class="col-md-7 col-sm-7 text-right">
                <ly:pageTag link="/admin/bill/billList${ly:toString(entity,'invoiceNum,invoiceDate,authStatus,inspectStatus,certificateCode,dt,dtn')}&pageNo=" page="${page}"></ly:pageTag>
	    	</div>
    	</div>
    </div>
</div>


<div class="modal fade js_certificate_code">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title">管理凭证</h4>
            </div>
            <div class="modal-body">
            <p><label class="control-label">凭证号：</label><input type="text" class="form-control" name="code" value=""></p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default js_cancel_btn" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary js_save_btn">保存</button>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.zh-CN.js" />"></script>

<script type="text/javascript">

    $(function () {
        var _id = null;

        $('button.js_insert_bill').on('click',function () {
            window.location.href = "<c:url value="/admin/bill/editBill"/>";
        });


        $('button.js_download_bill').on('click',function () {
            var div = $(this).parent('div');
            var p = [];
            div.find('input').each(function (k,v) {
                p.push($('<input>', {name: v.name, value: v.value}));
            });
            div.find('select').each(function (k,v) {
                p.push($('<input>', {name: v.name, value: $(v).val()}));
            });
            $('<form>', {
                method: 'post',
                action: '<c:url value="/admin/bill/downloadXLS"/>'
            }).append(p).submit();
        });

        $(document).keyup(function (event) {
            if(event.keyCode ==13){
                $('button.js_search').trigger('click');
            }
        })

        $('button.js_search').on('click',function () {
            var div = $(this).parent('div');
            var str = '?';
            div.find('input').each(function (k,v) {
                str+=v.name+"="+v.value+"&";
            });
            div.find('select').each(function (k,v) {
                str+=v.name+"="+$(v).val()+"&";
            });
            window.location.href = "<c:url value="/admin/bill/billList"/>"+str;
        });

        /**
         *凭证管理
         */
        $('button.js_edit_certificate').on('click',function () {
            _id = $(this).data('id');
            var _code = $(this).data('code');
            $('div.js_certificate_code').modal('show').find('input[name=code]').val(_code);
        });

        $('div.js_certificate_code').on('click','button.js_save_btn',function () {
            var code = $('div.js_certificate_code').find('input[name=code]').val();
            if (code){
                $.post('<c:url value="/admin/bill/saveBillCertCode"/>',{id:_id,code:code},function (result) {
                    if (result==0){
                        layer.alert("保存失败");
                    }else {
                        $('div.js_content_edit').modal('hide');
                        window.location.reload();
                    }
                },'json');
            }else {
                layer.msg("凭证号不能为空");
            }
        });


        /**
         * 编辑
         */
        $('button.js_edit_bill').on('click',function () {
            var _id = $(this).data('id');
            window.location.href = '<c:url value="/admin/bill/editBill?id="/>'+_id;
        });

        $('button.js_show_bill').on('click',function () {
            var _id = $(this).data('id');
            window.location.href = '<c:url value="/admin/bill/showBillDetail/"/>'+_id;
        });

        $('button.js_del_bill').on('click',function () {
            var _id = $(this).data('id');
            layer.confirm("确定删除？",function () {
                $.post('<c:url value="/admin/bill/delBillOne"/>',{id:_id},function (result) {
                    if (result==0){
                        layer.alert("删除失败");
                    }else {
                        $('div.js_content_edit').modal('hide');
                        window.location.reload();
                    }
                },'json');
            });
        });


        /**
         * 发票开始时间选择
         */
        $('input[name=invoiceStartDate]').datepicker({
            todayBtn : "linked",
            autoclose : true,
            language: 'zh-CN',
            format : "yyyy-mm-dd",
            todayHighlight : true,
            endDate:new Date()
        }).on('changeDate',function(e){
            var startTime = e.date;
            $('input[name=invoiceEndDate]').datepicker('setStartDate',startTime);
        });

        /**
         * 发票截至时间选择
         */
        $('input[name=invoiceEndDate]').datepicker({
            todayBtn : "linked",
            autoclose : true,
            language: 'zh-CN',
            format : "yyyy-mm-dd",
            todayHighlight : true,
            endDate : new Date()
        }).on('changeDate',function(e){
            var endTime = e.date;
            $('input[name=invoiceStartDate]').datepicker('setEndDate',endTime);
        });


    });


</script>
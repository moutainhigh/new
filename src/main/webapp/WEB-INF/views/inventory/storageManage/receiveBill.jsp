<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<link type="text/css" href="/resources/admin/global/plugins/select2-4.0.3/css/select2.min.css"  rel="stylesheet">
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/admin/global/plugins/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" />" />

<div class="portlet light">
    <div class="portlet-title">
        <div class="caption">
            <i class="icon-layers font-green"></i>
            <span class="caption-subject font-green sbold uppercase">出库列表</span>
        </div>
    </div>

    <div class="row">
        <div class="col-md-6">
            <form class="navbar-form navbar-left" role="search">
                <div class="form-group">
                    <select class="form-control" name="repositoryId">
                        <option value="-1">选择仓库</option>
                        <c:forEach items="${repositories}" var="r" varStatus="step">
                            <option value="${r.id}">${r.name}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    收票日期：
                    <div class="input-group date form_date" data-date="" data-date-format="yyyy-mm-dd" data-link-field="startTime" data-link-format="yyyy-mm-dd">
                        <input class="form-control" name="startTime" size="16" type="text" placeholder="开始时间" value="<fmt:formatDate value="${startDate}" pattern="yyyy-MM-dd"/>">
                    </div>
                </div>
                <div class="form-group">
                    <button type="button" class="btn btn-default js_search">搜索</button>
                    <button type="button" class="btn btn-default js_print">打印</button>
                </div>
            </form>
        </div>
    </div>
    <div id="myTabContent" class="tab-content">
        <div class="tab-pane fade in active" id="home">
            <div class="portlet-body">
                <div class="row">
                    <div class="col-md-6">
                        <table class="table table-width table-striped table-bordered table-advance table-hover table-checkable " id="outTable">
                            <thead>
                            <tr>
                                <th>序号</th>
                                <th>出库单号</th>
                                <th>出库金额</th>
                                <th>收票人</th>
                            </tr>
                            </thead>
                            <tbody></tbody>
                        </table>
                    </div>
                    <div class="col-md-6">
                        <table class="table table-width table-striped table-bordered table-advance table-hover table-checkable " id="inTable">
                            <thead>
                            <tr>
                                <th>序号</th>
                                <th>入库单号</th>
                                <th>入库金额</th>
                                <th>收票人</th>
                            </tr>
                            </thead>
                            <tbody></tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>

<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/select2-4.0.3/js/select2.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.zh-CN.js" />"></script>


<script>
    $(function () {

        //设置开始时间
        $('input[name=startTime]').datetimepicker({
            format:"yyyy-mm-dd",
            language:  'zh-CN',
            weekStart: 1,
            todayBtn:  1,
            autoclose: 1,
            todayHighlight: 1,
            startView: 2,
            minView: 2,
            forceParse: 0
        });

    });


    $(document).ready(function() {

        /**
         * 搜索
         */
        $('button.js_search').click(function () {
            if ($('select[name=repositoryId]').val()==-1){
                layer.msg('请选择仓库');
                return;
            }
            if ($.trim($('input[name=startTime]').val())==''){
                layer.msg('请收票日期');
                return;
            }
            var _index = layer.load(2);
            var dataObj = {};
            dataObj.repositoryId=$('select[name=repositoryId]').val();
            dataObj.billReceiveDate=$('input[name=startTime]').val();
            $.post('<c:url value="/admin/inventory/receiveBill/detailListData"/>', dataObj, function (result) {
                layer.close(_index);
                if (result.success) {
                    var data = result.rdataMap;
                    var _html = '';
                    $(data.outBillListData).each(function (k, v) {
                        _html+='<tr>';
                        _html+='<td>'+(k+1)+'</td>';
                        _html+='<td>'+v.outStorageNo+'</td>';
                        _html+='<td>'+v.outStorageSumPrice+'</td>';
                        _html+='<td>'+v.billReceiver+'</td>';
                        _html+='</tr>';
                    });
                    $('#outTable tbody').empty().append(_html);
                    _html = '';
                    $(data.inBillListData).each(function (k, v) {
                        _html+='<tr>';
                        _html+='<td>'+(k+1)+'</td>';
                        _html+='<td>'+v.inStorageNum+'</td>';
                        _html+='<td>'+v.inStorageSumPrice+'</td>';
                        _html+='<td>'+v.billReceiver+'</td>';
                        _html+='</tr>';
                    });
                    $('#inTable tbody').empty().append(_html);
                }else{
                    layer.msg('获取数据失败：'+result.rmsg,{time:2000});
                }
            }, 'json');
        });



        /**
         * 打印
         */
        $('button.js_print').click(function () {
            $('#printForm').remove();
            var _form = $('<form>');
            _form.attr('action','<c:url value="/admin/inventory/receiveBill/printDetail"/>');
            _form.attr('id','printForm');
            _form.attr('method','POST');
            _form.attr('target','_blank');
            _form.append($('<input>').attr('name','repositoryName').val($('select[name=repositoryId] option:selected').text()));
            _form.append($('<input>').attr('name','billReceiveDate').val($('input[name=startTime]').val()));
            _form.append($('<input>').attr('name','repositoryId').val($('select[name=repositoryId]').val()));
            $(document).find('body').append(_form);
            $('#printForm').submit();
        });


    });
</script>
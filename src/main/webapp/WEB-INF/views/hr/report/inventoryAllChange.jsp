<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/hr/css/hr.css" />"/>


<div class="row">
    <div class="col-md-12">
        <div class="portlet light">
            <div class="portlet-title">
                <div class="caption">
                    <i class="icon-social-dribbble font-green"></i>
                    <span class="caption-subject font-green bold uppercase">公司人员异动情况汇总</span>
                </div>
                <div class="actions">
                    <div class="btn-group btn-group-devided" >
                        <a href="<c:url value="/hr/reports/inventoryAllManagerWorker"/>"  class="btn btn-transparent blue btn-outline btn-circle btn-sm">人力盘点汇总</a>
                        <a href="<c:url value="/hr/reports/inventoryAllManager"/>"  class="btn btn-transparent blue btn-outline btn-circle btn-sm">管理类汇总</a>
                        <a href="<c:url value="/hr/reports/inventoryAllWorker"/>"  class="btn btn-transparent blue btn-outline btn-circle btn-sm">工人类汇总</a>
                        <a href="<c:url value="/hr/reports/inventoryAllDutyLv"/>"  class="btn btn-transparent blue btn-outline btn-circle btn-sm">职级</a>
                        <a href="<c:url value="/hr/reports/inventoryAllChange"/>"  class="btn btn-transparent blue btn-outline btn-circle btn-sm active green">异动汇总</a>
                    </div>
                </div>
            </div>
            <div class="portlet-body">
                <div class="actions form-inline">
                    <div class="form-group">
                        时间：<input class="form-control js_ym_select input-sm" value="${year}/${month}" readonly>
                    </div>
                    <div class="form-group">
                      <button class="btn btn-sm sbold blue js_search"><i class="fa fa-search"></i> 查看</button>
                      <button class="btn btn-sm sbold  js_download"><i class="fa fa-download"></i> 导出 </button>
                    </div>
                </div>
                <div class="table-responsive mar_t20">
                    <table class="table table-width table-center table5 table-bordered table-advance table-striped">
                        <thead>
                        <tr>
                            <th colspan="2" class="text-center">公司/部门分布</th>
                            <th colspan="3" class="text-center"> 实际在岗人数</th>
                            <th colspan="4" class="text-center"> 入职情况 </th>
                            <th colspan="4" class="text-center"> 离职情况 </th>
                            <th colspan="4" class="text-center"> 跨公司调动情况 </th>
                            <th colspan="3" class="text-center"> 其他不占编制人数 </th>
                        </tr>
                        <tr>
                            <th class="percent3"> 序号</th>
                            <th class="percent7"> 公司</th>
                            <th> 管理类 </th>
                            <th> 工人类 </th>
                            <th> 小计 </th>
                            <th> 管理类 </th>
                            <th> 工人类 </th>
                            <th> 小计 </th>
                            <th> 副经理级及以上 </th>
                            <th> 管理类</th>
                            <th> 工人类 </th>
                            <th> 小计 </th>
                            <th> 副经理级及以上 </th>
                            <th> 调入<br>(管理类)</th>
                            <th> 调入<br>(工人类)</th>
                            <th> 调出<br>(管理类) </th>
                            <th> 调出<br>(工人类) </th>
                            <th> 实习</th>
                            <th> 渠道 </th>
                            <th> 车管</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:if test="${empty reportData}"><tr><td colspan="20" class="text-center">没有统计数据</td></tr></c:if>
                        <c:forEach items="${reportData}" var="data"  varStatus="status">
                            <tr data-lv="lv1" id="${data.company}">
                                <td>${status.count}</td>
                                <td>
                                    <a href="javascript:void(0)" data-company="${data.company}" class="js_data_detail">${data.companyName}</a>
                                </td>
                                <td>${data.onDutyM}</td>
                                <td>${data.onDutyW}</td>
                                <c:set var="sumOnDuty" value="${sumOnDuty + data.onDutyM + data.onDutyW}"/>
                                <td><strong>${data.onDutyM+data.onDutyW}</strong></td>
                                <td>${data.inDutyM}</td>
                                <td>${data.inDutyW}</td>
                                <c:set var="sumInDuty" value="${sumInDuty + data.inDutyM + data.inDutyW}"/>
                                <td><strong>${data.inDutyM+data.inDutyW}</strong></td>
                                <td>${data.inDutyGatherM}</td>
                                <td>${data.outDutyM}</td>
                                <td>${data.outDutyW}</td>
                                <c:set var="sumOutDuty" value="${sumOutDuty + data.outDutyM + data.outDutyW}"/>
                                <td><strong>${data.outDutyM+data.outDutyW}</strong></td>
                                <td>${data.outDutyGatherM}</td>
                                <td>${data.turnInCompM}</td>
                                <td>${data.turnInCompW}</td>
                                <td>${data.turnOutCompM}</td>
                                <td>${data.turnOutCompW}</td>
                                <td>${data.practice}</td>
                                <td>${data.channel}</td>
                                <td>${data.carM}</td>
                            </tr>
                        </c:forEach>
                        <c:if test="${not empty reportData}">
                            <tr>
                                <td class="text-left" colspan="2"><strong>合计</strong></td>
                                <td><strong>${countData.onDutyM}</strong></td>
                                <td><strong>${countData.onDutyW}</strong></td>
                                <td><strong>${sumOnDuty}</strong></td>
                                <td><strong>${countData.inDutyM}</strong></td>
                                <td><strong>${countData.inDutyW}</strong></td>
                                <td><strong>${sumInDuty}</strong></td>
                                <td><strong>${countData.inDutyGatherM}</strong></td>
                                <td><strong>${countData.outDutyM}</strong></td>
                                <td><strong>${countData.outDutyW}</strong></td>
                                <td><strong>${sumOutDuty}</strong></td>
                                <td><strong>${countData.outDutyGatherM}</strong></td>
                                <td><strong>${countData.turnInCompM}</strong></td>
                                <td><strong>${countData.turnInCompW}</strong></td>
                                <td><strong>${countData.turnOutCompM}</strong></td>
                                <td><strong>${countData.turnOutCompW}</strong></td>
                                <td><strong>${countData.practice}</strong></td>
                                <td><strong>${countData.channel}</strong></td>
                                <td><strong>${countData.carM}</strong></td>
                            </tr>
                        </c:if>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="<c:url value="/resources/plugIn/My97DatePicker/WdatePicker.js" />"></script>
<script>
    $(document).ready(function () {

        $('table').on('click','a.js_data_detail',function () {
            var _tr = $(this).parents('tr');
            var _id = _tr.attr('id');
            var lv = _tr.data('lv');
            if(lv=='lv1'){
                $('tr[p='+_id+']').toggleClass("hide");
                $('tr[p='+_id+']').each(function (k, v) {
                    if($(v).hasClass('hide')){
                        var cp = $(v).attr('id');
                        $('tr[p='+cp+']').addClass("hide");
                    }
                });
            }else if (lv=='lv2'){
                $('tr[p='+_id+']').toggleClass("hide");
            }
        });


        $('input.js_ym_select').on('focus',function () {
            WdatePicker({dateFmt:'yyyy/MM'})
        });

        $('a.js_data_detail').on('click',function () {
            var _this = $(this);
            var company = _this.data('company');
            if(!_this.data('loaded')){
                $.ajax({
                    type:'post',
                    async: false,
                    url:'<c:url value="/hr/reports/getInventoryAllChangeData"/>',
                    data:{companyId:company,year:'${year}',month:'${month}'},
                    dataType:'json',
                    success:function (result) {
                        var html = '';
                        $(result).each(function (k, v) {
                            html += '<tr class="hide" p='+company+' data-lv="lv2" id='+v.department+'>';
                            html+='<td></td>';
                            if (v.isParent == 1){
                                html+='<td><a href="javascript:void(0)" class="js_data_detail" data-id="'+v.department+'">'+v.deptName+'</a></td>';
                            }else{
                                html+='<td>'+v.deptName+'</td>';
                            }
                            html+='<td>'+v.onDutyM+'</td>';
                            html+='<td>'+v.onDutyW+'</td>';
                            html+='<td>'+(v.onDutyM+v.onDutyW)+'</td>';
                            html+='<td>'+v.inDutyM+'</td>';
                            html+='<td>'+v.inDutyW+'</td>';
                            html+='<td>'+(v.inDutyM+v.inDutyW)+'</td>';
                            html+='<td>'+v.inDutyGatherM+'</td>';
                            html+='<td>'+v.outDutyM+'</td>';
                            html+='<td>'+v.outDutyW+'</td>';
                            html+='<td>'+(v.outDutyM+v.outDutyW)+'</td>';
                            html+='<td>'+v.outDutyGatherM+'</td>';
                            html+='<td>'+v.turnInCompM+'</td>';
                            html+='<td>'+v.turnInCompW+'</td>';
                            html+='<td>'+v.turnOutCompM+'</td>';
                            html+='<td>'+v.turnOutCompW+'</td>';
                            html+='<td>'+v.practice+'</td>';
                            html+='<td>'+v.channel+'</td>';
                            html+='<td>'+v.carM+'</td>';
                            html+='</tr>';
                            if (v.isParent == 1){
                                $(v.childChgData).each(function (i, e) {
                                    html += '<tr class="lv2 hide" company='+company+' p="'+e.departmentPid+'">';
                                    html+='<td></td>';
                                    html+='<td>'+e.deptName+'</td>';
                                    html+='<td>'+e.onDutyM+'</td>';
                                    html+='<td>'+e.onDutyW+'</td>';
                                    html+='<td>'+(e.onDutyM+e.onDutyW)+'</td>';
                                    html+='<td>'+e.inDutyM+'</td>';
                                    html+='<td>'+e.inDutyW+'</td>';
                                    html+='<td>'+(e.inDutyM+e.inDutyW)+'</td>';
                                    html+='<td>'+e.inDutyGatherM+'</td>';
                                    html+='<td>'+e.outDutyM+'</td>';
                                    html+='<td>'+e.outDutyW+'</td>';
                                    html+='<td>'+(e.outDutyM+e.outDutyW)+'</td>';
                                    html+='<td>'+e.outDutyGatherM+'</td>';
                                    html+='<td>'+e.turnInCompM+'</td>';
                                    html+='<td>'+e.turnInCompW+'</td>';
                                    html+='<td>'+e.turnOutCompM+'</td>';
                                    html+='<td>'+e.turnOutCompW+'</td>';
                                    html+='<td>'+e.practice+'</td>';
                                    html+='<td>'+e.channel+'</td>';
                                    html+='<td>'+e.carM+'</td>';
                                    html+='</tr>';
                                });
                            }
                        });
                        _this.parents('tr').after(html);
                        _this.data('loaded',true);
                    }
                });
            }
//            $('tr[company='+company+']').toggleClass("hidden");
        });


        $('button.js_search').on('click',function () {
            window.location.href='<c:url value="/hr/reports/inventoryAllChange/"/>'+$('input.js_ym_select').val();
        });

        $('button.js_download').on('click',function () {
            $('#downloadForm').remove();
            var _form = $('<form>');
            _form.attr('action','<c:url value="/hr/reports/downloadAllStatisticsChgData"/>');
            _form.attr('id','downloadForm');
            _form.attr('method','post');
            _form.attr('target', '_blank');
            var _input = $('<input type="text" name="yearMonth" />');
            _input.val($('input.js_ym_select').val());
            _form.append(_input);
            $(document).find('body').append(_form);
            $('#downloadForm').submit();
        });

    });


</script>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="/WEB-INF/tlds/custom.tld" prefix="custom"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/hr/css/hr.css" />"/>

<div class="row">
    <div class="col-md-12">
        <div class="portlet light">
            <div class="portlet-title">
                <div class="caption">
                    <i class="icon-social-dribbble font-green"></i>
                    <span class="caption-subject font-green bold uppercase">集团人员异动汇总</span>
                </div>
                <div class="actions">
                    <div class="btn-group btn-group-devided" >
                        <a href="<c:url value="/hr/reports/inventoryAllManagerWorkerCenter"/>"  class="btn btn-transparent blue btn-outline btn-circle btn-sm">集团人力盘点汇总</a>
                        <a href="<c:url value="/hr/reports/inventoryAllManagerCenter"/>"  class="btn btn-transparent blue btn-outline btn-circle btn-sm">集团管理类汇总</a>
                        <a href="<c:url value="/hr/reports/inventoryAllWorkerCenter"/>"  class="btn btn-transparent blue btn-outline btn-circle btn-sm">集团工人类汇总</a>
                        <%--<a href="<c:url value="/hr/reports/inventoryDutyLv"/>"  class="btn btn-transparent blue btn-outline btn-circle btn-sm">职级</a>--%>
                        <a href="<c:url value="/hr/reports/inventoryAllChangeCenter"/>"  class="btn btn-transparent blue btn-outline btn-circle btn-sm active green">集团异动汇总</a>
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
                        <%--<button class="btn btn-sm sbold  js_download"><i class="fa fa-download"></i> 导出 </button>--%>
                    </div>
                </div>
                <div class="table-responsive mar_t20">
                    <table class="table table-width table-center table4 table-bordered table-advance table-striped">
                        <thead>
                        <tr>
                            <th colspan="2" class="text-center">公司/部门分布</th>
                            <th colspan="3" class="text-center"> 月初在岗人数</th>
                            <th colspan="4" class="text-center"> 入职情况 </th>
                            <th colspan="4" class="text-center"> 离职情况 </th>
                            <th colspan="4" class="text-center"> 跨公司调动情况 </th>
                            <th colspan="3" class="text-center"> 实际在岗人数</th>
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
                            <th> 管理类 </th>
                            <th> 工人类 </th>
                            <th> 小计 </th>
                            <th> 实习</th>
                            <th> 渠道 </th>
                            <th> 车管</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:if test="${empty reportData}"><tr><td colspan="23" class="text-center">没有统计数据</td></tr></c:if>
                        <c:forEach items="${reportData}" var="b1"  varStatus="status">
                            <c:set var="lv1" value="${b1.statisticsChgData}"/>
                            <tr class="lv1" data-lv="lv1" c="${b1.id}">
                                <td>${status.count}</td>
                                <td>
                                    <a href="javascript:void(0)" data-company="${b1.companyId}" class="js_data_detail">${b1.name}</a>
                                </td>
                                <td>${lv1.beginOnDutyM}</td>
                                <td>${lv1.beginOnDutyW}</td>
                                <c:set var="sumAllBeginOnDuty" value="${sumAllBeginOnDuty + lv1.beginOnDutyM + lv1.beginOnDutyW}"/>
                                <td><strong>${lv1.beginOnDutyM+lv1.beginOnDutyW}</strong></td>
                                <td>${lv1.inDutyM}</td>
                                <td>${lv1.inDutyW}</td>
                                <c:set var="sumAllInDuty" value="${sumAllInDuty + lv1.inDutyM + lv1.inDutyW}"/>
                                <td><strong>${lv1.inDutyM+lv1.inDutyW}</strong></td>
                                <td>${lv1.inDutyGatherM}</td>
                                <td>${lv1.outDutyM}</td>
                                <td>${lv1.outDutyW}</td>
                                <c:set var="sumAllOutDuty" value="${sumAllOutDuty + lv1.outDutyM + lv1.outDutyW}"/>
                                <td><strong>${lv1.outDutyM+lv1.outDutyW}</strong></td>
                                <td>${lv1.outDutyGatherM}</td>
                                <td>${lv1.turnInCompM}</td>
                                <td>${lv1.turnInCompW}</td>
                                <td>${lv1.turnOutCompM}</td>
                                <td>${lv1.turnOutCompW}</td>
                                <td>${lv1.onDutyM}</td>
                                <td>${lv1.onDutyW}</td>
                                <c:set var="sumAllOnDuty" value="${sumAllOnDuty + lv1.onDutyM + lv1.onDutyW}"/>
                                <td><strong>${lv1.onDutyM+lv1.onDutyW}</strong></td>
                                <td>${lv1.practice}</td>
                                <td>${lv1.channel}</td>
                                <td>${lv1.carM}</td>
                            </tr>
                            <c:forEach items="${b1.childBindData}" var="b2"  varStatus="status2">
                                <c:set var="lv2" value="${b2.statisticsChgData}"/>
                                <tr class="hidden lv2"  data-lv="lv2" p="${b2.parentId}" c="${b2.id}">
                                    <td></td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${b2.isParent eq 1}"><a href="javascript:void(0)" data-company="${b2.companyId}" class="js_data_detail">${b2.name}</a></c:when>
                                            <c:otherwise>${b2.name}</c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>${lv2.beginOnDutyM}</td>
                                    <td>${lv2.beginOnDutyW}</td>
                                    <td><strong>${lv2.beginOnDutyM+lv2.beginOnDutyW}</strong></td>
                                    <c:choose>
                                        <c:when test="${custom:hasDataAuth(b2.isParent eq 0, b2.companyCode)}">
                                            <td><a href="<c:url value="/hr/reports/detail/empIndutyDaily/${b2.companyId}/5/${year}/${month}"/>" target="_blank">${lv2.inDutyM}</a></td>
                                            <td><a href="<c:url value="/hr/reports/detail/empIndutyDaily/${b2.companyId}/6/${year}/${month}"/>" target="_blank">${lv2.inDutyW}</a></td>
                                            <td><strong>${lv2.inDutyM+lv2.inDutyW}</strong></td>
                                            <td>${lv2.inDutyGatherM}</td>
                                            <td><a href="<c:url value="/hr/reports/detail/empOutdutyDaily/${b2.companyId}/5/${year}/${month}"/>" target="_blank">${lv2.outDutyM}</a></td>
                                            <td><a href="<c:url value="/hr/reports/detail/empOutdutyDaily/${b2.companyId}/6/${year}/${month}"/>" target="_blank">${lv2.outDutyW}</a></td>
                                            <td><strong>${lv2.outDutyM+lv2.outDutyW}</strong></td>
                                            <td>${lv2.outDutyGatherM}</td>
                                            <td><a href="<c:url value="/hr/reports/detail/empTransferInDaily/${b2.companyId}/5/${year}/${month}"/>" target="_blank">${lv2.turnInCompM}</a></td>
                                            <td><a href="<c:url value="/hr/reports/detail/empTransferInDaily/${b2.companyId}/6/${year}/${month}"/>" target="_blank">${lv2.turnInCompW}</a></td>
                                            <td><a href="<c:url value="/hr/reports/detail/empTransferOutDaily/${b2.companyId}/5/${year}/${month}"/>" target="_blank">${lv2.turnOutCompM}</a></td>
                                            <td><a href="<c:url value="/hr/reports/detail/empTransferOutDaily/${b2.companyId}/6/${year}/${month}"/>" target="_blank">${lv2.turnOutCompW}</a></td>
                                        </c:when>
                                        <c:otherwise>
                                            <td>${lv2.inDutyM}</td>
                                            <td>${lv2.inDutyW}</td>
                                            <td><strong>${lv2.inDutyM+lv2.inDutyW}</strong></td>
                                            <td>${lv2.inDutyGatherM}</td>
                                            <td>${lv2.outDutyM}</td>
                                            <td>${lv2.outDutyW}</td>
                                            <td><strong>${lv2.outDutyM+lv2.outDutyW}</strong></td>
                                            <td>${lv2.outDutyGatherM}</td>
                                            <td>${lv2.turnInCompM}</td>
                                            <td>${lv2.turnInCompW}</td>
                                            <td>${lv2.turnOutCompM}</td>
                                            <td>${lv2.turnOutCompW}</td>
                                        </c:otherwise>
                                    </c:choose>
                                    <td>${lv2.onDutyM}</td>
                                    <td>${lv2.onDutyW}</td>
                                    <td><strong>${lv2.onDutyM+lv2.onDutyW}</strong></td>
                                    <td>${lv2.practice}</td>
                                    <td>${lv2.channel}</td>
                                    <td>${lv2.carM}</td>
                                </tr>

                                <c:if test="${b2.isParent eq 1}">
                                <c:forEach items="${b2.childBindData}" var="b3"  varStatus="status3">
                                    <c:set var="lv3" value="${b3.statisticsChgData}"/>
                                    <tr class="hidden lv3"  data-lv="lv3" p="${b2.id}">
                                        <td></td>
                                        <td>
                                            ${b3.name}
                                        </td>
                                        <td>${lv3.beginOnDutyM}</td>
                                        <td>${lv3.beginOnDutyW}</td>
                                        <td><strong>${lv3.beginOnDutyM+lv3.beginOnDutyW}</strong></td>
                                        <c:choose>
                                            <c:when test="${custom:hasDataAuth(b3.isParent eq 0, b3.companyCode)}">
                                                <td><a href="<c:url value="/hr/reports/detail/empIndutyDaily/${b3.companyId}/5/${year}/${month}"/>" target="_blank">${lv3.inDutyM}</a></td>
                                                <td><a href="<c:url value="/hr/reports/detail/empIndutyDaily/${b3.companyId}/6/${year}/${month}"/>" target="_blank">${lv3.inDutyW}</a></td>
                                                <td><strong>${lv3.inDutyM+lv3.inDutyW}</strong></td>
                                                <td>${lv3.inDutyGatherM}</td>
                                                <td><a href="<c:url value="/hr/reports/detail/empOutdutyDaily/${b3.companyId}/5/${year}/${month}"/>" target="_blank">${lv3.outDutyM}</a></td>
                                                <td><a href="<c:url value="/hr/reports/detail/empOutdutyDaily/${b3.companyId}/6/${year}/${month}"/>" target="_blank">${lv3.outDutyW}</a></td>
                                                <td><strong>${lv3.outDutyM+lv3.outDutyW}</strong></td>
                                                <td>${lv3.outDutyGatherM}</td>
                                                <td><a href="<c:url value="/hr/reports/detail/empTransferInDaily/${b3.companyId}/5/${year}/${month}"/>" target="_blank">${lv3.turnInCompM}</a></td>
                                                <td><a href="<c:url value="/hr/reports/detail/empTransferInDaily/${b3.companyId}/6/${year}/${month}"/>" target="_blank">${lv3.turnInCompW}</a></td>
                                                <td><a href="<c:url value="/hr/reports/detail/empTransferOutDaily/${b3.companyId}/5/${year}/${month}"/>" target="_blank">${lv3.turnOutCompM}</a></td>
                                                <td><a href="<c:url value="/hr/reports/detail/empTransferOutDaily/${b3.companyId}/6/${year}/${month}"/>" target="_blank">${lv3.turnOutCompW}</a></td>
                                            </c:when>
                                            <c:otherwise>
                                                <td>${lv3.inDutyM}</td>
                                                <td>${lv3.inDutyW}</td>
                                                <td><strong>${lv3.inDutyM+lv3.inDutyW}</strong></td>
                                                <td>${lv3.inDutyGatherM}</td>
                                                <td>${lv3.outDutyM}</td>
                                                <td>${lv3.outDutyW}</td>
                                                <td><strong>${lv3.outDutyM+lv3.outDutyW}</strong></td>
                                                <td>${lv3.outDutyGatherM}</td>
                                                <td>${lv3.turnInCompM}</td>
                                                <td>${lv3.turnInCompW}</td>
                                                <td>${lv3.turnOutCompM}</td>
                                                <td>${lv3.turnOutCompW}</td>
                                            </c:otherwise>
                                        </c:choose>
                                        <td>${lv3.onDutyM}</td>
                                        <td>${lv3.onDutyW}</td>
                                        <td><strong>${lv3.onDutyM+lv3.onDutyW}</strong></td>
                                        <td>${lv3.practice}</td>
                                        <td>${lv3.channel}</td>
                                        <td>${lv3.carM}</td>
                                    </tr>
                                </c:forEach>
                                </c:if>
                            </c:forEach>
                        </c:forEach>

                        <c:if test="${not empty reportData}">
                            <tr>
                                <td class="text-left" colspan="2"><strong>合计</strong></td>
                                <td>${countData.beginOnDutyM}</td>
                                <td>${countData.beginOnDutyW}</td>
                                <td><strong>${sumAllBeginOnDuty}</strong></td>
                                <td><a href="<c:url value="/hr/reports/detail/empIndutyDaily/5/${year}/${month}"/>" target="_blank">${countData.inDutyM}</a></td>
                                <td><a href="<c:url value="/hr/reports/detail/empIndutyDaily/6/${year}/${month}"/>" target="_blank">${countData.inDutyW}</a></td>
                                <td><strong>${sumAllInDuty}</strong></td>
                                <td>${countData.inDutyGatherM}</td>
                                <td><a href="<c:url value="/hr/reports/detail/empOutdutyDaily/5/${year}/${month}"/>" target="_blank">${countData.outDutyM}</a></td>
                                <td><a href="<c:url value="/hr/reports/detail/empOutdutyDaily/6/${year}/${month}"/>" target="_blank">${countData.outDutyW}</a></td>
                                <td><strong>${sumAllOutDuty}</strong></td>
                                <td>${countData.outDutyGatherM}</td>
                                <td><a href="<c:url value="/hr/reports/detail/empTransferInDaily/5/${year}/${month}"/>" target="_blank">${countData.turnInCompM}</a></td>
                                <td><a href="<c:url value="/hr/reports/detail/empTransferInDaily/6/${year}/${month}"/>" target="_blank">${countData.turnInCompW}</a></td>
                                <td><a href="<c:url value="/hr/reports/detail/empTransferOutDaily/5/${year}/${month}"/>" target="_blank">${countData.turnOutCompM}</a></td>
                                <td><a href="<c:url value="/hr/reports/detail/empTransferOutDaily/6/${year}/${month}"/>" target="_blank">${countData.turnOutCompW}</a></td>
                                <td>${countData.onDutyM}</td>
                                <td>${countData.onDutyW}</td>
                                <td><strong>${sumAllOnDuty}</strong></td>
                                <td>${countData.practice}</td>
                                <td>${countData.channel}</td>
                                <td>${countData.carM}</td>
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

        $('input.js_ym_select').on('focus',function () {
            WdatePicker({dateFmt:'yyyy/MM'})
        });

        $('a.js_data_detail').on('click',function () {
            var _this = $(this);
            var _tr = _this.parents('tr');
            var _c = _tr.attr('c');
            var lv = _tr.data('lv');
            if(lv=='lv1'){
                $('tr[p='+_c+']').toggleClass("hidden");
                $('tr[p='+_c+']').each(function (k, v) {
                    if($(v).hasClass('hidden')){
                    var cp = $(v).attr('c');
                    $('tr[p='+cp+']').addClass("hidden");
                    }
                });
            }else if (lv=='lv2'){
                $('tr[p='+_c+']').toggleClass("hidden");
            }
        });

        $('button.js_search').on('click',function () {
            window.location.href='<c:url value="/hr/reports/inventoryAllChangeCenter/"/>'+$('input.js_ym_select').val();
        });

        $('button.js_download').on('click',function () {
            $('#downloadForm').remove();
            var _form = $('<form>');
            _form.attr('action','<c:url value="/hr/reports/downloadAllStatisticsData"/>');
            _form.attr('id','downloadForm');
            _form.attr('method','post');
            _form.attr('target', '_blank');
            var _input = $('<input type="text" name="yearMonth" />');
            _input.val($('input.js_ym_select').val());
            _form.append(_input);
            _input = $('<input type="text" name="statisticsType" />');
            _input.val("0");
            _form.append(_input);
            $(document).find('body').append(_form);
            $('#downloadForm').submit();
        });
    });


</script>
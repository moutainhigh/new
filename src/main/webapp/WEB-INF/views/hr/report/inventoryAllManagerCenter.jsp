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
                    <span class="caption-subject font-green bold uppercase">集团人力盘点汇总(管理类)</span>
                </div>
                <div class="actions">
                    <div class="btn-group btn-group-devided" >
                        <a href="<c:url value="/hr/reports/inventoryAllManagerWorkerCenter"/>"  class="btn btn-transparent blue btn-outline btn-circle btn-sm">集团人力盘点汇总</a>
                        <a href="<c:url value="/hr/reports/inventoryAllManagerCenter"/>"  class="btn btn-transparent blue btn-outline btn-circle btn-sm active green">集团管理类汇总</a>
                        <a href="<c:url value="/hr/reports/inventoryAllWorkerCenter"/>"  class="btn btn-transparent blue btn-outline btn-circle btn-sm">集团工人类汇总</a>
                        <%--<a href="<c:url value="/hr/reports/inventoryDutyLv"/>"  class="btn btn-transparent blue btn-outline btn-circle btn-sm">职级</a>--%>
                        <a href="<c:url value="/hr/reports/inventoryAllChangeCenter"/>"  class="btn btn-transparent blue btn-outline btn-circle btn-sm">集团异动汇总</a>
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
                    <table class="table table-width table-center table434 table-bordered table-advance table-striped">
                        <thead>
                        <tr>
                            <th colspan="2" class="text-center">板块/公司</th>
                            <th rowspan="2" class="text-center"> 在职<br>人数 </th>
                            <th colspan="4" class="text-center"> 学历构成 </th>
                            <th colspan="5" class="text-center"> 年龄结构 </th>
                            <th colspan="5" class="text-center"> 司龄结构 </th>
                            <th colspan="2" class="text-center"> 性别结构 </th>
                            <th colspan="4" class="text-center"> 盘点数据核对 </th>
                        </tr>
                        <tr>
                            <th  class="percent3"> 序号</th>
                            <th  class="percent6"> 名称</th>
                            <th> 硕士及以上 </th>
                            <th> 本科 </th>
                            <th> 大专 </th>
                            <th> 大专<br>以下 </th>
                            <th> 30岁及以下 </th>
                            <th> 31~40岁 </th>
                            <th> 41~50岁 </th>
                            <th> 51~60岁 </th>
                            <th> 60岁<br>以上</th>
                            <th> 1年以下 </th>
                            <th> 1~3年 </th>
                            <th> 4~5年 </th>
                            <th> 6~10年 </th>
                            <th> 10年<br>以上 </th>
                            <th> 男 </th>
                            <th> 女 </th>
                            <th> 学历<br>结构 </th>
                            <th> 年龄<br>结构 </th>
                            <th> 司龄<br>结构 </th>
                            <th> 性别<br>结构 </th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:if test="${empty reportData}"><tr><td colspan="23" class="text-center">没有统计数据</td></tr></c:if>
                        <c:forEach items="${reportData}" var="b1"  varStatus="status">
                            <c:set var="lv1" value="${b1.statisticsData}"/>
                            <tr class="lv1" data-lv="lv1" c="${b1.id}">
                                <td>${status.count}</td>
                                <td>
                                    <a href="javascript:void(0)" data-company="${b1.companyId}" class="js_data_detail">${b1.name}</a>
                                </td>
                                <td>${lv1.sum}</td>
                                <td>${lv1.edu1}</td>
                                <td>${lv1.edu2}</td>
                                <td>${lv1.edu3}</td>
                                <td>${lv1.edu4}</td>
                                <td>${lv1.age1}</td>
                                <td>${lv1.age2}</td>
                                <td>${lv1.age3}</td>
                                <td>${lv1.age4}</td>
                                <td>${lv1.age5}</td>
                                <td>${lv1.ctime1}</td>
                                <td>${lv1.ctime2}</td>
                                <td>${lv1.ctime3}</td>
                                <td>${lv1.ctime4}</td>
                                <td>${lv1.ctime5}</td>
                                <td>${lv1.male}</td>
                                <td>${lv1.female}</td>
                                <c:set var="sumEdu" value="${lv1.edu1+lv1.edu2+lv1.edu3+lv1.edu4}"/>
                                <c:set var="sumAllEdu" value="${sumAllEdu+sumEdu}"/>
                                <td><strong>${sumEdu}</strong></td>
                                <c:set var="sumAge" value="${lv1.age1+lv1.age2+lv1.age3+lv1.age4+lv1.age5}"/>
                                <c:set var="sumAllAge" value="${sumAllAge+sumAge}"/>
                                <td><strong>${sumAge}</strong></td>
                                <c:set var="sumCtime" value="${lv1.ctime1+lv1.ctime2+lv1.ctime3+lv1.ctime4+lv1.ctime5}"/>
                                <c:set var="sumAllCtime" value="${sumAllCtime+sumCtime}"/>
                                <td><strong>${sumCtime}</strong></td>
                                <c:set var="sumSex" value="${lv1.male+lv1.female}"/>
                                <c:set var="sumAllSex" value="${sumAllSex+sumSex}"/>
                                <td><strong>${sumSex}</strong></td>
                            </tr>

                            <c:forEach items="${b1.childBindData}" var="b2"  varStatus="status2">
                                <c:set var="lv2" value="${b2.statisticsData}"/>
                                <tr class="hidden lv2"  data-lv="lv2" p="${b2.parentId}" c="${b2.id}">
                                    <td></td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${b2.isParent eq 1}"><a href="javascript:void(0)" data-company="${b2.companyId}" class="js_data_detail">${b2.name}</a></c:when>
                                            <c:otherwise>${b2.name}</c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>${lv2.sum}</td>
                                    <td>${lv2.edu1}</td>
                                    <td>${lv2.edu2}</td>
                                    <td>${lv2.edu3}</td>
                                    <td>${lv2.edu4}</td>
                                    <td>${lv2.age1}</td>
                                    <td>${lv2.age2}</td>
                                    <td>${lv2.age3}</td>
                                    <td>${lv2.age4}</td>
                                    <td>${lv2.age5}</td>
                                    <td>${lv2.ctime1}</td>
                                    <td>${lv2.ctime2}</td>
                                    <td>${lv2.ctime3}</td>
                                    <td>${lv2.ctime4}</td>
                                    <td>${lv2.ctime5}</td>
                                    <td>${lv2.male}</td>
                                    <td>${lv2.female}</td>
                                    <td><strong>${lv2.edu1+lv2.edu2+lv2.edu3+lv2.edu4}</strong></td>
                                    <td><strong>${lv2.age1+lv2.age2+lv2.age3+lv2.age4+lv2.age5}</strong></td>
                                    <td><strong>${lv2.ctime1+lv2.ctime2+lv2.ctime3+lv2.ctime4+lv2.ctime5}</strong></td>
                                    <td><strong>${lv2.male+lv2.female}</strong></td>
                                </tr>

                                <c:if test="${b2.isParent eq 1}">
                                <c:forEach items="${b2.childBindData}" var="b3"  varStatus="status3">
                                    <c:set var="lv3" value="${b3.statisticsData}"/>
                                    <tr class="hidden lv3"  data-lv="lv3" p="${b2.id}">
                                        <td></td>
                                            <td>
                                            ${b3.name}
                                            </td>
                                            <td>${lv3.sum}</td>
                                            <td>${lv3.edu1}</td>
                                            <td>${lv3.edu2}</td>
                                            <td>${lv3.edu3}</td>
                                            <td>${lv3.edu4}</td>
                                            <td>${lv3.age1}</td>
                                            <td>${lv3.age2}</td>
                                            <td>${lv3.age3}</td>
                                            <td>${lv3.age4}</td>
                                            <td>${lv3.age5}</td>
                                            <td>${lv3.ctime1}</td>
                                            <td>${lv3.ctime2}</td>
                                            <td>${lv3.ctime3}</td>
                                            <td>${lv3.ctime4}</td>
                                            <td>${lv3.ctime5}</td>
                                            <td>${lv3.male}</td>
                                            <td>${lv3.female}</td>
                                            <td><strong>${lv3.edu1+lv3.edu2+lv3.edu3+lv3.edu4}</strong></td>
                                            <td><strong>${lv3.age1+lv3.age2+lv3.age3+lv3.age4+lv3.age5}</strong></td>
                                            <td><strong>${lv3.ctime1+lv3.ctime2+lv3.ctime3+lv3.ctime4+lv3.ctime5}</strong></td>
                                            <td><strong>${lv3.male+lv3.female}</strong></td>
                                    </tr>
                                </c:forEach>
                                </c:if>
                            </c:forEach>
                        </c:forEach>

                        <c:if test="${not empty reportData}">
                            <tr>
                                <td class="text-left" colspan="2"><strong>合计</strong></td>
                                <td>${countData.sum}</td>
                                <td>${countData.edu1}</td>
                                <td>${countData.edu2}</td>
                                <td>${countData.edu3}</td>
                                <td>${countData.edu4}</td>
                                <td>${countData.age1}</td>
                                <td>${countData.age2}</td>
                                <td>${countData.age3}</td>
                                <td>${countData.age4}</td>
                                <td>${countData.age5}</td>
                                <td>${countData.ctime1}</td>
                                <td>${countData.ctime2}</td>
                                <td>${countData.ctime3}</td>
                                <td>${countData.ctime4}</td>
                                <td>${countData.ctime5}</td>
                                <td>${countData.male}</td>
                                <td>${countData.female}</td>
                                <td><strong>${sumAllEdu}</strong></td>
                                <td><strong>${sumAllAge}</strong></td>
                                <td><strong>${sumAllCtime}</strong></td>
                                <td><strong>${sumAllSex}</strong></td>
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
            window.location.href='<c:url value="/hr/reports/inventoryAllManagerCenter/"/>'+$('input.js_ym_select').val();
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
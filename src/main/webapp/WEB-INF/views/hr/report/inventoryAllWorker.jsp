<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/hr/css/hr.css" />"/>

<%--<div class="page-bar">--%>
    <%--<ul class="page-breadcrumb hidden">--%>
        <%--<li>--%>
            <%--<i class="icon-home"></i>--%>
            <%--<a href="index.html">报表管理</a>--%>
            <%--<i class="fa fa-angle-right"></i>--%>
        <%--</li>--%>
        <%--<li>--%>
            <%--<span>人力资源盘点</span>--%>
        <%--</li>--%>
    <%--</ul>--%>
<%--</div>--%>

<div class="row">
    <div class="col-md-12">
        <div class="portlet light">
            <div class="portlet-title">
                <div class="caption">
                    <i class="icon-social-dribbble font-green"></i>
                    <span class="caption-subject font-green bold uppercase">公司人力盘点汇总(工人类)</span>
                </div>
                <div class="actions">
                    <div class="btn-group btn-group-devided" >
                        <a href="<c:url value="/hr/reports/inventoryAllManagerWorker"/>"  class="btn btn-transparent blue btn-outline btn-circle btn-sm">人力盘点汇总</a>
                        <a href="<c:url value="/hr/reports/inventoryAllManager"/>"  class="btn btn-transparent blue btn-outline btn-circle btn-sm">管理类汇总</a>
                        <a href="<c:url value="/hr/reports/inventoryAllWorker"/>"  class="btn btn-transparent blue btn-outline btn-circle btn-sm active green">工人类汇总</a>
                        <a href="<c:url value="/hr/reports/inventoryAllDutyLv"/>"  class="btn btn-transparent blue btn-outline btn-circle btn-sm">职级</a>
                        <a href="<c:url value="/hr/reports/inventoryAllChange"/>"  class="btn btn-transparent blue btn-outline btn-circle btn-sm">异动汇总</a>
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
                    <table class="table table-width table-center table434 table-bordered table-advance table-striped">
                        <thead>
                        <tr>
                            <th colspan="2" class="text-center">公司/部门</th>
                            <th rowspan="2" class="text-center"> 在职<br>人数 </th>
                            <th colspan="4" class="text-center"> 学历构成 </th>
                            <th colspan="5" class="text-center"> 年龄结构 </th>
                            <th colspan="5" class="text-center"> 司龄结构 </th>
                            <th colspan="2" class="text-center"> 性别结构 </th>
                            <th colspan="4" class="text-center"> 盘点数据核对 </th>
                        </tr>
                        <tr>
                            <th class="percent3"> 序号</th>
                            <th class="percent6"> 名称</th>
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
                        <c:forEach items="${reportData}" var="data"  varStatus="status">
                            <tr data-lv="lv1" id="${data.company}">
                                <td>${status.count}</td>
                                <td>
                                    <a href="javascript:void(0)" data-company="${data.company}" class="js_data_detail">${data.companyStr}</a>
                                </td>
                                <td>${data.sum}</td>
                                <td>${data.edu1}</td>
                                <td>${data.edu2}</td>
                                <td>${data.edu3}</td>
                                <td>${data.edu4}</td>
                                <td>${data.age1}</td>
                                <td>${data.age2}</td>
                                <td>${data.age3}</td>
                                <td>${data.age4}</td>
                                <td>${data.age5}</td>
                                <td>${data.ctime1}</td>
                                <td>${data.ctime2}</td>
                                <td>${data.ctime3}</td>
                                <td>${data.ctime4}</td>
                                <td>${data.ctime5}</td>
                                <td>${data.male}</td>
                                <td>${data.female}</td>
                                <c:set var="sumEdu" value="${data.edu1+data.edu2+data.edu3+data.edu4}"/>
                                <c:set var="sumAllEdu" value="${sumAllEdu+sumEdu}"/>
                                <td><strong>${sumEdu}</strong></td>
                                <c:set var="sumAge" value="${data.age1+data.age2+data.age3+data.age4+data.age5}"/>
                                <c:set var="sumAllAge" value="${sumAllAge+sumAge}"/>
                                <td><strong>${sumAge}</strong></td>
                                <c:set var="sumCtime" value="${data.ctime1+data.ctime2+data.ctime3+data.ctime4+data.ctime5}"/>
                                <c:set var="sumAllCtime" value="${sumAllCtime+sumCtime}"/>
                                <td><strong>${sumCtime}</strong></td>
                                <c:set var="sumSex" value="${data.male+data.female}"/>
                                <c:set var="sumAllSex" value="${sumAllSex+sumSex}"/>
                                <td><strong>${sumSex}</strong></td>
                            </tr>
                        </c:forEach>
                        <c:if test="${not empty reportData}">
                            <tr>
                                <td class="text-left" colspan="2"><strong>合计</strong></td>
                                <td><strong>${countData.sum}</strong></td>
                                <td><strong>${countData.edu1}</strong></td>
                                <td><strong>${countData.edu2}</strong></td>
                                <td><strong>${countData.edu3}</strong></td>
                                <td><strong>${countData.edu4}</strong></td>
                                <td><strong>${countData.age1}</strong></td>
                                <td><strong>${countData.age2}</strong></td>
                                <td><strong>${countData.age3}</strong></td>
                                <td><strong>${countData.age4}</strong></td>
                                <td><strong>${countData.age5}</strong></td>
                                <td><strong>${countData.ctime1}</strong></td>
                                <td><strong>${countData.ctime2}</strong></td>
                                <td><strong>${countData.ctime3}</strong></td>
                                <td><strong>${countData.ctime4}</strong></td>
                                <td><strong>${countData.ctime5}</strong></td>
                                <td><strong>${countData.male}</strong></td>
                                <td><strong>${countData.female}</strong></td>
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
                    url:'<c:url value="/hr/reports/getInventoryWorkerData"/>',
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
                            html+='<td>'+v.sum+'</td>';
                            html+='<td>'+v.edu1+'</td>';
                            html+='<td>'+v.edu2+'</td>';
                            html+='<td>'+v.edu3+'</td>';
                            html+='<td>'+v.edu4+'</td>';
                            html+='<td>'+v.age1+'</td>';
                            html+='<td>'+v.age2+'</td>';
                            html+='<td>'+v.age3+'</td>';
                            html+='<td>'+v.age4+'</td>';
                            html+='<td>'+v.age5+'</td>';
                            html+='<td>'+v.ctime1+'</td>';
                            html+='<td>'+v.ctime2+'</td>';
                            html+='<td>'+v.ctime3+'</td>';
                            html+='<td>'+v.ctime4+'</td>';
                            html+='<td>'+v.ctime5+'</td>';
                            html+='<td>'+v.male+'</td>';
                            html+='<td>'+v.female+'</td>';
                            html+='<td><strong>'+(parseInt(v.edu1)+parseInt(v.edu2)+parseInt(v.edu3)+parseInt(v.edu4))+'</strong></td>';
                            html+='<td><strong>'+(parseInt(v.age1)+parseInt(v.age2)+parseInt(v.age3)+parseInt(v.age4)+parseInt(v.age5))+'</strong></td>';
                            html+='<td><strong>'+(parseInt(v.ctime1)+parseInt(v.ctime2)+parseInt(v.ctime3)+parseInt(v.ctime4)+parseInt(v.ctime5))+'</strong></td>';
                            html+='<td><strong>'+(parseInt(v.male)+parseInt(v.female))+'</strong></td>';
                            html+='</tr>';
                            if (v.isParent == 1){
                                $(v.childStatisticsData).each(function (i, e) {
                                    html += '<tr class="lv2 hide" company='+company+' p="'+e.departmentPid+'">';
                                    html+='<td></td>';
                                    html+='<td>'+e.deptName+'</td>';
                                    html+='<td>'+e.sum+'</td>';
                                    html+='<td>'+e.edu1+'</td>';
                                    html+='<td>'+e.edu2+'</td>';
                                    html+='<td>'+e.edu3+'</td>';
                                    html+='<td>'+e.edu4+'</td>';
                                    html+='<td>'+e.age1+'</td>';
                                    html+='<td>'+e.age2+'</td>';
                                    html+='<td>'+e.age3+'</td>';
                                    html+='<td>'+e.age4+'</td>';
                                    html+='<td>'+e.age5+'</td>';
                                    html+='<td>'+e.ctime1+'</td>';
                                    html+='<td>'+e.ctime2+'</td>';
                                    html+='<td>'+e.ctime3+'</td>';
                                    html+='<td>'+e.ctime4+'</td>';
                                    html+='<td>'+e.ctime5+'</td>';
                                    html+='<td>'+e.male+'</td>';
                                    html+='<td>'+e.female+'</td>';
                                    html+='<td><strong>'+(parseInt(e.edu1)+parseInt(e.edu2)+parseInt(e.edu3)+parseInt(e.edu4))+'</strong></td>';
                                    html+='<td><strong>'+(parseInt(e.age1)+parseInt(e.age2)+parseInt(e.age3)+parseInt(e.age4)+parseInt(e.age5))+'</strong></td>';
                                    html+='<td><strong>'+(parseInt(e.ctime1)+parseInt(e.ctime2)+parseInt(e.ctime3)+parseInt(e.ctime4)+parseInt(e.ctime5))+'</strong></td>';
                                    html+='<td><strong>'+(parseInt(e.male)+parseInt(e.female))+'</strong></td>';
                                    html+='</tr>';
                                });
                            }
                        });
                        _this.parents('tr').after(html);
                        _this.data('loaded',true);
                    }
                });
            }
        });

        $('button.js_search').on('click',function () {
            window.location.href='<c:url value="/hr/reports/inventoryAllWorker/"/>'+$('input.js_ym_select').val();
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
            _input.val("1");
            _form.append(_input);
            $(document).find('body').append(_form);
            $('#downloadForm').submit();
        });

    });


</script>
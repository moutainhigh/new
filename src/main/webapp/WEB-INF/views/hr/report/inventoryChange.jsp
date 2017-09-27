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
                    <span class="caption-subject font-green bold uppercase">人力资源人员异动情况</span>
                </div>
                <div class="actions">
                    <div class="btn-group btn-group-devided" >
                        <a href="<c:url value="/hr/reports/inventoryManager"/>"  class="btn btn-transparent blue btn-outline btn-circle btn-sm">管理类</a>
                        <a href="<c:url value="/hr/reports/inventoryWorker"/>"  class="btn btn-transparent blue btn-outline btn-circle btn-sm">工人类</a>
                        <a href="<c:url value="/hr/reports/inventoryDutyLv"/>"  class="btn btn-transparent blue btn-outline btn-circle btn-sm">职级</a>
                        <a href="<c:url value="/hr/reports/inventoryChange"/>"  class="btn btn-transparent blue btn-outline btn-circle btn-sm active green">异动</a>
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
                    <div class="form-group pull-right">
                        <c:if test="${empty reportData}"><button class="btn btn-sm sbold blue js_build"><i class="fa fa-calculator"></i> 开始统计</button></c:if>
                        <c:if test="${! empty reportData}"><button class="btn btn-sm sbold green blue js_rebuild"><i class="fa fa-refresh"></i> 重新统计</button></c:if>
                    </div>
                </div>
                <div class="table-responsive mar_t20">
                    <table class="table table-width table-center table4 table-bordered table-advance table-striped">
                        <thead>
                        <tr>
                            <th colspan="2" class="text-center">部门分布</th>
                            <th colspan="3" class="text-center"> 实际在岗人数</th>
                            <th colspan="4" class="text-center"> 入职情况 </th>
                            <th colspan="4" class="text-center"> 离职情况 </th>
                            <th colspan="4" class="text-center"> 跨公司调动情况 </th>
                            <th colspan="4" class="text-center"> 部门调动情况 </th>
                            <th colspan="3" class="text-center"> 其他不占编制人数 </th>
                        </tr>
                        <tr>
                            <th class="percent3"> 序号</th>
                            <th class="percent7"> 部门名称</th>
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
                            <tr>
                                <td>${status.count}</td>
                                <td>
                                <c:choose>
                                    <c:when test="${data.isParent eq 1}"><a href="javascript:void(0)" class="js_data_detail" data-id="${data.department}">${data.deptName}</a></c:when>
                                    <c:otherwise>${data.deptName}</c:otherwise>
                                </c:choose>
                                </td>
                                <td>${data.onDutyM}</td>
                                <td>${data.onDutyW}</td>
                                <td><strong>${data.onDutyM+data.onDutyW}</strong></td>
                                <td>${data.inDutyM}</td>
                                <td>${data.inDutyW}</td>
                                <td><strong>${data.inDutyM+data.inDutyW}</strong></td>
                                <td>${data.inDutyGatherM}</td>
                                <td>${data.outDutyM}</td>
                                <td>${data.outDutyW}</td>
                                <td><strong>${data.outDutyM+data.outDutyW}</strong></td>
                                <td>${data.outDutyGatherM}</td>
                                <td>${data.turnInCompM}</td>
                                <td>${data.turnInCompW}</td>
                                <td>${data.turnOutCompM}</td>
                                <td>${data.turnOutCompW}</td>
                                <td>${data.turnInDeptM}</td>
                                <td>${data.turnInDeptW}</td>
                                <td>${data.turnOutDeptM}</td>
                                <td>${data.turnOutDeptW}</td>
                                <td>${data.practice}</td>
                                <td>${data.channel}</td>
                                <td>${data.carM}</td>
                            </tr>
                            <c:if test="${data.isParent eq 1}">
                                <c:forEach items="${data.childChgData}" var="data"  varStatus="status">
                                <tr class="hide lv2" p="${data.departmentPid}">
                                    <td>${status.count}</td>
                                    <td>
                                            ${data.deptName}
                                    </td>
                                    <td>${data.onDutyM}</td>
                                    <td>${data.onDutyW}</td>
                                    <td><strong>${data.onDutyM+data.onDutyW}</strong></td>
                                    <td>${data.inDutyM}</td>
                                    <td>${data.inDutyW}</td>
                                    <td><strong>${data.inDutyM+data.inDutyW}</strong></td>
                                    <td>${data.inDutyGatherM}</td>
                                    <td>${data.outDutyM}</td>
                                    <td>${data.outDutyW}</td>
                                    <td><strong>${data.outDutyM+data.outDutyW}</strong></td>
                                    <td>${data.outDutyGatherM}</td>
                                    <td>${data.turnInCompM}</td>
                                    <td>${data.turnInCompW}</td>
                                    <td>${data.turnOutCompM}</td>
                                    <td>${data.turnOutCompW}</td>
                                    <td>${data.turnInDeptM}</td>
                                    <td>${data.turnInDeptW}</td>
                                    <td>${data.turnOutDeptM}</td>
                                    <td>${data.turnOutDeptW}</td>
                                    <td>${data.practice}</td>
                                    <td>${data.channel}</td>
                                    <td>${data.carM}</td>
                                </tr>
                                </c:forEach>
                            </c:if>
                        </c:forEach>
                        <c:if test="${not empty reportData}">
                            <tr>
                                <td class="text-left" colspan="2"><strong>合计</strong></td>
                                <td><strong>${countData.onDutyM}</strong></td>
                                <td><strong>${countData.onDutyW}</strong></td>
                                <td><strong>${countData.onDutyM+countData.onDutyW}</strong></td>
                                <td><strong>${countData.inDutyM}</strong></td>
                                <td><strong>${countData.inDutyW}</strong></td>
                                <td><strong>${countData.inDutyM+countData.inDutyW}</strong></td>
                                <td><strong>${countData.inDutyGatherM}</strong></td>
                                <td><strong>${countData.outDutyM}</strong></td>
                                <td><strong>${countData.outDutyW}</strong></td>
                                <td><strong>${countData.outDutyM+countData.outDutyW}</strong></td>
                                <td><strong>${countData.outDutyGatherM}</strong></td>
                                <td><strong>${countData.turnInCompM}</strong></td>
                                <td><strong>${countData.turnInCompW}</strong></td>
                                <td><strong>${countData.turnOutCompM}</strong></td>
                                <td><strong>${countData.turnOutCompW}</strong></td>
                                <td><strong>${countData.turnInDeptM}</strong></td>
                                <td><strong>${countData.turnInDeptW}</strong></td>
                                <td><strong>${countData.turnOutDeptM}</strong></td>
                                <td><strong>${countData.turnOutDeptW}</strong></td>
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

        $('a.js_data_detail').on('click',function () {
            var _this = $(this);
            var _id =_this.data('id');
            $('tr[p='+_id+']').toggleClass('hide');
        });

        $('input.js_ym_select').on('focus',function () {
            WdatePicker({dateFmt:'yyyy/MM'})
        });

        var build = function (url,_data,year,month) {
            var _index;
            $.ajax({
                async: true,
                beforeSend: function () {
                    _index = layer.load(2);
                },
                complete: function () {
                    layer.close(_index);
                },
                type : 'POST' ,
                data:_data==undefined?{}:_data,
                url : url,
                success: function (result) {
                    if (result.success){
                        if(year&&month){
                            window.location.href='<c:url value="/hr/reports/inventoryChange/"/>'+year+"/"+month;
                        }else{
                            window.location.reload();
                        }
                    }else{
                        layer.alert(result.rmsg);
                    }
                }
            });
        }

        $('button.js_build').on('click',function () {
            var ym = $('input.js_ym_select').val();
            var _year = ym.split('/')[0];
            var _month =  ym.split('/')[1];
            var _index = layer.confirm("确定要开始统计？当前选择的时间："+_year+"年"+_month+"月",function () {
                layer.close(_index);
                build('<c:url value="/hr/reports/startStatisticsChange"/>',{forceStatistics:false,year:_year,month:_month},_year,_month);
            });
        });

        $('button.js_rebuild').on('click',function () {
            var ym = $('input.js_ym_select').val();
            var _year = ym.split('/')[0];
            var _month =  ym.split('/')[1];
            var _index = layer.confirm("确定要重新统计？当前选择的时间："+_year+"年"+_month+"月",function () {
                layer.close(_index);
                build('<c:url value="/hr/reports/startStatisticsChange"/>',{forceStatistics:true,year:_year,month:_month},_year,_month);
            });
        });

        $('button.js_search').on('click',function () {
            window.location.href='<c:url value="/hr/reports/inventoryChange/"/>'+$('input.js_ym_select').val();
        });

        $('button.js_download').on('click',function () {
            $('#downloadForm').remove();
            var _form = $('<form>');
            _form.attr('action','<c:url value="/hr/reports/downloadStatisticsChgData"/>');
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
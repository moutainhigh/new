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
                    <span class="caption-subject font-green bold uppercase">人力资源盘点（工人类）</span>
                </div>
                <div class="actions">
                    <div class="btn-group btn-group-devided" >
                        <a href="<c:url value="/hr/reports/inventoryManager"/>"  class="btn btn-transparent blue btn-outline btn-circle btn-sm">管理类</a>
                        <a href="<c:url value="/hr/reports/inventoryWorker"/>"  class="btn btn-transparent blue btn-outline btn-circle btn-sm active green">工人类</a>
                        <a href="<c:url value="/hr/reports/inventoryDutyLv"/>"  class="btn btn-transparent blue btn-outline btn-circle btn-sm">职级</a>
                        <a href="<c:url value="/hr/reports/inventoryChange"/>"  class="btn btn-transparent blue btn-outline btn-circle btn-sm">异动</a>
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
                        <c:if test="${empty reportData}"><c:if test="${currYY eq year and currMM eq month}"><button class="btn btn-sm sbold blue js_build"><i class="fa fa-calculator"></i> 开始统计</button></c:if></c:if>
                        <c:if test="${! empty reportData && currYY eq year and currMM eq month}"><button class="btn btn-sm sbold green blue js_rebuild"><i class="fa fa-refresh"></i> 重新统计</button></c:if>
                    </div>
                </div>
                <div class="table-responsive mar_t20">
                    <table class="table table-width table-center table434 table-bordered table-advance table-striped">
                        <thead>
                        <tr>
                            <th colspan="2" class="text-center">部门分布</th>
                            <th rowspan="2" class="text-center"> 在职<br>人数 </th>
                            <th colspan="4" class="text-center"> 学历构成 </th>
                            <th colspan="5" class="text-center"> 年龄结构 </th>
                            <th colspan="5" class="text-center"> 司龄结构 </th>
                            <th colspan="2" class="text-center"> 性别结构 </th>
                            <th colspan="4" class="text-center"> 盘点数据核对 </th>
                        </tr>
                        <tr>
                            <th class="percent3"> 序号</th>
                            <th class="percent6"> 部门名称</th>
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
                            <tr>
                                <td>${status.count}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${data.isParent eq 1}"><a href="javascript:void(0)" class="js_data_detail" data-id="${data.department}">${data.deptName}</a></c:when>
                                        <c:otherwise>${data.deptName}</c:otherwise>
                                    </c:choose>
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
                            <c:if test="${data.isParent eq 1}">
                                <c:forEach items="${data.childStatisticsData}" var="data"  varStatus="status">
                                    <tr class="hide lv2" p="${data.departmentPid}">
                                        <td>${status.count}</td>
                                        <td>
                                                ${data.deptName}
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
                                        <td><strong>${sumEdu}</strong></td>
                                        <c:set var="sumAge" value="${data.age1+data.age2+data.age3+data.age4+data.age5}"/>
                                        <td><strong>${sumAge}</strong></td>
                                        <c:set var="sumCtime" value="${data.ctime1+data.ctime2+data.ctime3+data.ctime4+data.ctime5}"/>
                                        <td><strong>${sumCtime}</strong></td>
                                        <c:set var="sumSex" value="${data.male+data.female}"/>
                                        <td><strong>${sumSex}</strong></td>
                                    </tr>
                                </c:forEach>
                            </c:if>
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

<div class="modal fade js_department_div">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title">部门/人员</h4>
            </div>
            <div class="modal-body">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-md-4">
                            <label>部门</label>
                            <ul id="departmentTree" class="ztree"></ul>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default js_cancel_btn" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary js_save_btn">保存</button>
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

        var companyId = '${companyId}';
        var departmentTreeObj;
        var deptId;
        var build = function (url) {
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
                url : url,
                success: function (result) {
                    if (result.success){
                        window.location.reload();
                    }else{
                        layer.alert(result.rmsg);
                    }
                }
            });
        }

        $('button.js_build').on('click',function () {
            var _index = layer.confirm("确定要开始统计？",function () {
                layer.close(_index);
                build('<c:url value="/hr/reports/startStatisticsWorker"/>');
            });
        });

        $('button.js_rebuild').on('click',function () {
            var _index = layer.confirm("确定要重新统计？",function () {
                layer.close(_index);
                build('<c:url value="/hr/reports/startStatisticsWorker?forceStatistics=true"/>');
            });
        });

        $('button.js_search').on('click',function () {
            window.location.href='<c:url value="/hr/reports/inventoryWorker/"/>'+$('input.js_ym_select').val();
        });

        $('button.js_download').on('click',function () {
            $('#downloadForm').remove();
            var _form = $('<form>');
            _form.attr('action','<c:url value="/hr/reports/downloadStatisticsData"/>');
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
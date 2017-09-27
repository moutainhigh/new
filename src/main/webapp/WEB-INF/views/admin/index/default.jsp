<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link href="<c:url value="/resources/hr/css/menu.css" />" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="/resources/hr/front/fonts/iconfont/iconfont.css">

<style>
    .remind{
        padding: 12px 20px 15px;
        background-color: #fff;
    }
    .remind .renav{
        position: relative;
    }
    .remind .renav .export-btn{
        position: absolute;
        right:0;
        top:5px;
        display: block;
    }
    .feeds li{
        background-color: inherit;
        border-bottom: 1px dashed #ddd;
    }
</style>

<c:if test="${user.regSource eq 'hr'}">
<div class="row">
    <!--菜单-->
    <div class="col-lg-7 col-xs-12 col-sm-12">
      <div class="menu-nav">
          <h4>劳动关系</h4>
          <ul>
              <li><a href="<c:url value="/hr/chg/turnFormal"/>"><i class="iconfont">&#xe616;</i>转正</a></li>
              <li><a href="<c:url value="/hr/outDuty/edit"/>"><i class="iconfont">&#xe611;</i>离职</a></li>
              <li><a href="<c:url value="/hr/chg/edit"/>"><i class="iconfont">&#xe613;</i>调动</a></li>
              <%--<li><a href="#"><i class="iconfont">&#xe61b;</i>续约</a></li>--%>
              <li><a href="<c:url value="/hr/contract/list"/>"><i class="iconfont">&#xe61e;</i>劳动合同</a></li>
          </ul>
          <h4>人员信息</h4>
          <ul>
              <li><a href="<c:url value="/hr/org/jobEdit"/>"><i class="iconfont">&#xe612;</i>岗位配置</a></li>
              <li><a href="<c:url value="/hr/recruitmentStore/index"/>"><i class="iconfont">&#xe617;</i>人才库</a></li>
              <%--<li><a href="<c:url value="/hr/contract/list"/>"><i class="iconfont">&#xe60b;</i>人才需求</a></li>--%>
              <li><a href="<c:url value="/hr/edit"/>"><i class="iconfont">&#xe61f;</i>入职</a></li>
          </ul>
          <h4>日常考勤</h4>
          <ul>
              <li><a href="javascript:void(0)"><i class="iconfont">&#xe619;</i>请休假</a></li>
              <li><a href="javascript:void(0)"><i class="iconfont">&#xe60f;</i>加班</a></li>
              <li><a href="javascript:void(0)"><i class="iconfont">&#xe60d;</i>缺勤补录</a></li>
              <li><a href="javascript:void(0)"><i class="iconfont">&#xe61d;</i>员工考勤</a></li>
              <li><a href="javascript:void(0)"><i class="iconfont">&#xe614;</i>假期管理</a></li>
          </ul>
          <h4>绩效考核</h4>
          <ul>
              <li><a href="javascript:void(0)"><i class="iconfont">&#xe615;</i>奖罚记录</a></li>
              <li><a href="javascript:void(0)"><i class="iconfont">&#xe61c;</i>绩效考核</a></li>
              <li><a href="javascript:void(0)"><i class="iconfont">&#xe610;</i>KPI库</a></li>
          </ul>
          <h4>培训开发</h4>
          <ul>
              <li><a href="<c:url value="/hr/train/list"/>"><i class="iconfont">&#xe61a;</i>培训活动</a></li>
              <%--<li><a href="<c:url value="/hr/contract/list"/>"><i class="iconfont">&#xe60c;</i>培训计划</a></li>--%>
              <li><a href="<c:url value="/hr/train/edit"/>"><i class="iconfont">&#xe618;</i>培训新增</a></li>
          </ul>
          <h4>薪资福利</h4>
          <ul>
              <li><a href="javascript:void(0)"><i class="iconfont">&#xe620;</i>薪资表</a></li>
              <li><a href="javascript:void(0)"><i class="iconfont">&#xe60e;</i>薪资调整</a></li>
          </ul>
      </div>
    </div>
    <!--提醒-->
    <div class="col-lg-4 col-xs-12 col-sm-12" style="float: right">
        <div class="remind light ">
            <div class="portlet-title">
                <div class="">
                    <i class="icon-globe font-dark hide"></i>
                    <span class="caption-subject font-dark bold uppercase">提醒</span>
                </div>
                <div class="renav">
                  <ul class="nav nav-tabs js_ul">
                      <li class="active" data-id="2">
                        <a href="#tab_1_1" data-toggle="tab" aria-expanded="false"> 转正提醒 </a>
                      </li>
                      <li class="" data-id="1">
                        <a href="#tab_1_2" class="active" data-toggle="tab" aria-expanded="true"> 生日提醒 </a>
                      </li>
                      <li class="" data-id="3">
                        <a href="#tab_1_3" data-toggle="tab" aria-expanded="false"> 合同到期提醒 </a>
                      </li>
                      <li class="">
                          <a href="#tab_1_4" data-toggle="tab" aria-expanded="false"> 未录入学历人员 </a>
                      </li>
                   </ul>
                    <a class="export-btn btn btn-transparent grey-salsa btn-outline btn-circle btn-sm js_export" href="javascript:void (0)" target="_blank">导出</a>
                </div>

            </div>
            <div class="portlet-body">
                <div class="tab-content">
                    <div class="tab-pane active" id="tab_1_1">
                        <div id="remindTurnFormal">
                            <ul class="feeds">
                                <c:forEach items="${turnFormalRemindData}" var="b" varStatus="step">
                                    <li>
                                        <h5><span>${step.count}.</span><span>${b.empName}</span><span><fmt:formatDate value="${b.date}" pattern="yyyy-MM-dd"/></span><span>${b.companyName}</span></h5>
                                    </li>
                                </c:forEach>
                            </ul>
                        </div>
                    </div>
                    <div class="tab-pane" id="tab_1_2">
                        <div id="remindBirth">
                            <ul class="feeds">
                                <c:forEach items="${birthRemindData}" var="b" varStatus="step">
                                    <li>
                                        <h5><span>${step.count}.</span><span>${b.empName}</span><span><fmt:formatDate value="${b.date}" pattern="yyyy-MM-dd"/></span><span>${b.companyName}</span></h5>
                                    </li>
                                </c:forEach>
                            </ul>
                        </div>
                    </div>
                    <div class="tab-pane" id="tab_1_3">
                        <div id="remindContractEnd">
                            <ul class="feeds">
                                <c:forEach items="${contractEndRemindData}" var="b" varStatus="step">
                                    <li>
                                        <h5><span>${step.count}.</span><span>${b.empName}</span><span><fmt:formatDate value="${b.date}" pattern="yyyy-MM-dd"/></span><span>${b.companyName}</span></h5>
                                    </li>
                                </c:forEach>
                            </ul>
                        </div>
                    </div>
                    <div class="tab-pane" id="tab_1_4">
                        <div id="noEducationEmpList">
                            <ul class="feeds">
                                <c:forEach items="${noEducationEmpList}" var="b" varStatus="step">
                                    <li>
                                        <h5><span>${step.count}.</span><span>${b.plate}-${b.companyStr}-${b.departmentStr}-${b.empName}</span></h5>
                                    </li>
                                </c:forEach>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    $(document).ready(function () {
        $('#remindTurnFormal').slimScroll({height:"339px"});
        $('#remindBirth').slimScroll({height:"339px"});
        $('#remindContractEnd').slimScroll({height:"339px"});
        $('#noEducationEmpList').slimScroll({height:"339px"});
        $('a.js_export').on('click',function () {
            var _id =$('ul.js_ul').find('li.active').data('id');
            window.location.href = '<c:url value="/hr/exportRemindData/"/>'+_id;
        });
    });
</script>

</c:if>

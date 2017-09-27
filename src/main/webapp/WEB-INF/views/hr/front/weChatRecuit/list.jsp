<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
    <meta name="format-detection" content="telephone=no" />
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta http-equiv="pragma" content="no-cache" />
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/hr/front/css/style.css"/>">
    <script src="<c:url value="/resources/hr/front/js/jquery-1.11.1.min.js"/>" type="text/javascript"></script>
    <title>华宇招聘</title>
</head>
<body>
<div class="content_box">
    <div class="hr_box">
        <div class="hr_top">
            <h1><label class="blue">招聘信息</label>华宇集团最新招聘职位</h1>
            <p><label class="hr_name blue">华宇集团人力资源部</label></p>
            <div class="hr_weixin"><img src="<c:url value="/resources/hr/front/images/hr_logo.png"/>"></div>
            <ul class="hr_nav js_plateId">
                <li <c:if test="${action eq 0}">class="active"</c:if>><a href="javascript:void(0)">集团总部</a></li>
                <li <c:if test="${action eq 1}">class="active"</c:if>><a href="javascript:void(0)">地产集团</a></li>
                <li <c:if test="${action eq 2}">class="active"</c:if>><a href="javascript:void(0)">商业集团</a></li>
                <li <c:if test="${action eq 3}">class="active"</c:if>><a href="javascript:void(0)">金控集团</a></li>
                <li <c:if test="${action eq 4}">class="active"</c:if>><a href="javascript:void(0)">建设集团</a></li>
                <li <c:if test="${action eq 5}">class="active"</c:if>><a href="javascript:void(0)">物业集团</a></li>
                <li <c:if test="${action eq 6}">class="active"</c:if>><a href="javascript:void(0)">海外公司</a></li>
            </ul>

            <c:if test="${action ne 0 && action ne 6}">
                工作区域：
                <div class="js_area similate_select">
                    <div>
                        <c:choose>
                            <c:when test="${area eq 1}"><a href="javascript:void(0)" data-value="1"><span>重庆区域</span></a></c:when>
                            <c:when test="${area eq 2}"><a href="javascript:void(0)" data-value="2"><span>四川区域</span></a></c:when>
                            <c:when test="${area eq 3}"><a href="javascript:void(0)" data-value="3"><span>华东区域</span></a></c:when>
                            <c:when test="${area eq 4}"><a href="javascript:void(0)" data-value="4"><span>上海区域</span></a></c:when>
                            <c:when test="${area eq 5}"><a href="javascript:void(0)" data-value="5"><span>长沙城市公司</span></a></c:when>
                            <c:when test="${area eq 6}"><a href="javascript:void(0)" data-value="6"><span>沈阳城市公司</span></a></c:when>
                        </c:choose>
                    </div>
                    <ul name="area">
                        <li data-value="1" <c:if test="${area eq 1}">class="cuur"</c:if>>重庆区域</li>
                        <c:choose>
                            <c:when test="${action eq 3}">
                                <li data-value="4" <c:if test="${area eq 4}">class="cuur"</c:if>>上海区域</li>
                            </c:when>
                            <c:when test="${action eq 1}">
                                <li data-value="5" <c:if test="${area eq 5}">class="cuur"</c:if>>长沙城市公司</li>
                                <li data-value="6" <c:if test="${area eq 6}">class="cuur"</c:if>>沈阳城市公司</li>
                            </c:when>
                            <c:otherwise>
                                <li data-value="2" <c:if test="${area eq 2}">class="cuur"</c:if>>四川区域</li>
                                <li data-value="3" <c:if test="${area eq 3}">class="cuur"</c:if>>华东区域</li>
                            </c:otherwise>
                        </c:choose>
                    </ul>
                </div>
            </c:if>

        </div>

        <!--地产集团招聘信息-->
        <div class="hr_job">
            <ul>
                <c:forEach items="${contentList}" var="entity" varStatus="stetp">
                    <li>
                        <div class="job_title">
                          <h2>${entity.title}</h2>
                          <div class="job_no"><span>工作地点：${entity.addr}</span><span>招聘人数：${entity.count}</span><span>发布时间：<fmt:formatDate value="${entity.updatetime}" pattern="yyyy-MM-dd"/></span></div>
                        </div>
                        <div class="job_details"  <c:if test="${stetp.index eq 0}">style="display:block"</c:if>>
                            ${entity.allText}
                                <a href="javascript:void (0)" class="js_post" data-id="${entity.id}" data-cid="${entity.plateId}">立即应聘该岗位>></a>
                        </div>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </div>
</div>

<script>
    $(function(){
        $(".hr_job li .job_title").click(function(){
            $(this).siblings().slideToggle("slow");
        });


        $(document).click(function (e) {
            var _con = $(' .similate_select ul '); // 设置目标区域
            if(!_con.is(e.target) && _con.has(e.target).length === 0){
                $(".similate_select ul").hide();
            }
        });
        $(".similate_select span").click(function (e) {
            $(this).parent("a").parent("div").siblings("ul").toggle();
            return false;//防止冒泡
        });

        $('.similate_select ul li').click(function(){
            $(this).parent('ul').siblings('div').children('a').children('span').text($(this).text()).data('area',$(this).data('value'));
            $(this).parent('ul').hide();
            window.location.href =  '<c:url value="/position/list?cid="/>'+$("ul.js_plateId").find('li.active').index()+'&area='+$(this).data('value');
        });

        
        $('ul.js_plateId li').on('click',function () {
            var _index = $(this).index();
            window.location.href =  '<c:url value="/position/list?cid="/>'+_index+'&area=1';
        });

        $('a.js_post').on('click',function () {
            var _area = $("ul[name=area]").prev('div').find('a').data('value');
            if (!_area){
                _area = 1;
            }
            window.location.href =  '<c:url value="/position/request"/>'+"?cid="+$(this).data("cid")+"&jid="+$(this).data("id")+"&area="+_area;
        });


    })
</script>
</body>
</html>

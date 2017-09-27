<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>华宇集团招聘</title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/hr/front/css/recruit_style.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/hr/front/css/font-awesome.css"/>">
    <link rel="icon" type="image/ico" href="<c:url value="/resources/hr/front/images/favicon.ico"/>">
    <script src="<c:url value="/resources/hr/front/js/jquery-1.11.1.min.js"/>" type="text/javascript"></script>
</head>

<body>
<!--header-->
<div class="r_header wrapper-width">
    <div class="container">
        <div class="r_logo"><a href="http://www.cqhyrc.com.cn/talent"><img src="<c:url value="/resources/hr/front/images/r_logo.png"/>"></a></div>
        <div class="r_nav">
            <ul>
                <li><a class="active" href="<c:url value="/recruitment/society"/>"><i class="sociology"></i>社会招聘</a></li>
                <li><a href="<c:url value="/recruitment/school"/>"><i class="campus"></i>校园招聘</a></li>
                <li><a href="<c:url value="/recruitment/practice"/>"><i class="internship"></i>实习生招聘</a></li>
                <li><a href="<c:url value="/recruitment/aboutUs"/>"><i class="stay"></i>人在华宇</a></li>
                <li><a href="http://www.cqhyrc.com.cn/"><i class="return"></i>返回首页</a></li>
            </ul>
        </div>
    </div>
</div>
<!--sider-->
<div class="r_sider_wrapper wrapper-width">
    <div class="r_slide_box1">
        <div class="ggBox"><img src="<c:url value="/resources/hr/front/images/bannar1.jpg"/>"></div>
    </div>
</div>
<!--招聘-->
<div class="center_box wrapper-width">
    <div class="container">
        <div class="r_url"><i class="icon-home"></i>首页 > 人才发展 > 社会招聘</div>
        <div class="content_box">
            <div class="left_nav accordion">
                <ul>
                    <li <c:if test="${p eq 0}">class="current"</c:if>>
                        <a href="<c:url value="/recruitment/society/${0}/${1}/0"/>"><span class="menu_link">集团总部</span></a>
                        <div class="sub_menu"></div>
                    </li>
                    <li <c:if test="${p eq 1}">class="current"</c:if>>
                        <a href="javascript:void(0)"><span class="menu_link">地产集团</span></a>
                        <div class="sub_menu" <c:if test="${p eq 1}">style="display:block;"</c:if>>
                            <a <c:if test="${p eq 1 && a eq 1}">class="curr"<c:set value="重庆区域" var="areaStr"/></c:if> href="<c:url value="/recruitment/society/${1}/${1}/0"/>">重庆区域公司</a>
                            <a <c:if test="${p eq 1 && a eq 2}">class="curr"<c:set value="四川区域" var="areaStr"/></c:if> href="<c:url value="/recruitment/society/${1}/${2}/0"/>">四川区域公司</a>
                            <a <c:if test="${p eq 1 && a eq 3}">class="curr"<c:set value="华东区域" var="areaStr"/></c:if> href="<c:url value="/recruitment/society/${1}/${3}/0"/>">华东区域公司</a>
                            <a <c:if test="${p eq 1 && a eq 5}">class="curr"<c:set value="长沙城市公司" var="areaStr"/></c:if> href="<c:url value="/recruitment/society/${1}/${5}/0"/>">长沙城市公司</a>
                            <a <c:if test="${p eq 1 && a eq 6}">class="curr"<c:set value="沈阳城市公司" var="areaStr"/></c:if> href="<c:url value="/recruitment/society/${1}/${6}/0"/>">沈阳城市公司</a>
                        </div>
                    </li>
                    <li <c:if test="${p eq 2}">class="current"</c:if>>
                        <a href="javascript:void(0)"><span class="menu_link">商业集团</span></a>
                        <div class="sub_menu" <c:if test="${p eq 2}">style="display:block;"</c:if>>
                            <a <c:if test="${p eq 2 && a eq 1}">class="curr"<c:set value="重庆区域" var="areaStr"/></c:if> href="<c:url value="/recruitment/society/${2}/${1}/0"/>">重庆区域公司</a>
                            <a <c:if test="${p eq 2 && a eq 2}">class="curr"<c:set value="四川区域" var="areaStr"/></c:if> href="<c:url value="/recruitment/society/${2}/${2}/0"/>">四川区域公司</a>
                            <a <c:if test="${p eq 2 && a eq 3}">class="curr"<c:set value="华东区域" var="areaStr"/></c:if> href="<c:url value="/recruitment/society/${2}/${3}/0"/>">华东区域公司</a>
                        </div>
                    </li>
                    <li <c:if test="${p eq 3}">class="current"</c:if>>
                        <a href="<c:url value="/recruitment/society/${3}/${1}/0"/>"><span class="menu_link">金控集团</span></a>
                        <div class="sub_menu"></div>
                    </li>
                    <li <c:if test="${p eq 4}">class="current"</c:if>>
                        <a href="<c:url value="/recruitment/society/${4}/${1}/0"/>"><span class="menu_link">建设集团</span></a>
                        <div class="sub_menu"></div>
                    </li>
                    <li <c:if test="${p eq 5}">class="current"</c:if>>
                        <a href="<c:url value="/recruitment/society/${5}/${1}/0"/>"><span class="menu_link">物业集团</span></a>
                        <div class="sub_menu"></div>
                    </li>
                    <li <c:if test="${p eq 6}">class="current"</c:if>>
                        <a href="<c:url value="/recruitment/society/${6}/${1}/0"/>"><span class="menu_link">海外公司</span></a>
                        <div class="sub_menu"></div>
                    </li>
                </ul>
            </div>
            <div class="main_box">
            <div class="recruit">
                <div class="re_th">
                    <span style="width: 20%;">招聘公司</span>
                    <span style="width: 45%;">招聘职位</span>
                    <span style="width: 15%;">工作地点</span>
                    <span style="width: 20%;">发布时间</span>
                </div>
                <ul>
                    <c:forEach items="${page.content}" var="entity">
                        <li>
                            <div class="re_tr">
                                <span style="width: 20%;">
                                    <c:choose>
                                        <c:when test="${entity.plateId eq 0}">集团总部</c:when>
                                        <c:when test="${entity.plateId eq 1}">地产集团-${areaStr}</c:when>
                                        <c:when test="${entity.plateId eq 2}">商业集团-${areaStr}</c:when>
                                        <c:when test="${entity.plateId eq 3}">金控集团</c:when>
                                        <c:when test="${entity.plateId eq 4}">建设集团</c:when>
                                        <c:when test="${entity.plateId eq 5}">物业集团</c:when>
                                        <c:when test="${entity.plateId eq 6}">海外公司</c:when>
                                    </c:choose>
                                </span>
                                <span style="width: 45%;">${entity.title}</span>
                                <span style="width: 15%;">${entity.addr}</span>
                                <span style="width: 20%;"><fmt:formatDate value="${entity.updatetime}" pattern="yyyy-MM-dd"/></span>
                            </div>
                            <div class="re_text">
                                    ${entity.allText}
                                <div class="resume"><b>请发送简历至hyrencai@126.com或者<a class="orange" href="<c:url value="/recruitment/applyJob?cid=${entity.plateId}&jid=${entity.id}&area=${entity.area}"/>">在线投递简历</a></b></div>
                            </div>
                        </li>
                    </c:forEach>
                </ul>
            </div>
            <div class="re_page">
                <ul>
                    <li><a href="<c:url value="/recruitment/society"/>"><i class="icon-step-backward"></i></a></li>
                    <c:if test="${page.hasPrevious()}">
                        <li><a href="<c:url value="/recruitment/society/${p}/${a}/${page.number-1}"/>"><i class="icon-backward"></i></a></li>
                    </c:if>
                    <c:if test="${!page.hasPrevious()}">
                        <li><a href="javascript:void(0)"><i class="icon-backward"></i></a></li>
                    </c:if>
                    <li><a href="javascript:void(0)">${page.totalElements==0?0:page.number+1}/${page.totalPages}页（${page.totalElements}条）</a></li>
                    <c:if test="${page.hasNext()}">
                        <li><a href="<c:url value="/recruitment/society/${p}/${a}/${page.number+1}"/>"><i class="icon-forward"></i></a></li>
                    </c:if>
                    <c:if test="${!page.hasNext()}">
                        <li><a href="javascript:void(0)"><i class="icon-forward"></i></a></li>
                    </c:if>
                    <li><a href="<c:url value="/recruitment/society/${p}/${a}/${page.totalPages-1}"/>"><i class="icon-step-forward"></i></a></li>
                </ul>
            </div>
        </div>
        </div>
        </div>
</div>
<!--footer-->
<div class="footer wrapper-width">
    <div class="container">
        © 1995 - 2017 重庆华宇集团有限公司　版权所有　　渝ICP备13001850号
    </div>
</div>

<script>
    $(function(){
        $(".recruit .re_tr").click(function(){
            $(this).siblings().slideToggle("slow");
            $(this).toggleClass('active');
        });

        var Accordion = function(el, multiple) {
            this.el = el || {};
            this.multiple = multiple || false;
            var links = this.el.find('.menu_link');
            links.on('click', {el: this.el, multiple: this.multiple}, this.dropdown)
        }

        Accordion.prototype.dropdown = function(e) {
            var $el = e.data.el,$this = $(this), $next = $this.parent().next();
            $this.parents('li').addClass('current');
            if ($next.children('a').length>0){
                $next.slideToggle();
            }
            if (!e.data.multiple) {
                $el.find('.sub_menu').not($next).slideUp().parents('li').removeClass('current');
            };
        }
        var accordion = new Accordion($('.accordion'), false);

//        //二级菜单的点击效果
//        $('.sub_menu a').click(function(){
//            $(this).addClass('curr').siblings().removeClass('curr');
//        })
    })
</script>
</body>
</html>

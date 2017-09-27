<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles-extras" prefix="tilesx" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<tilesx:useAttribute id="menus" name="menus" classname="java.util.List" />

<div class="page-sidebar-wrapper">
    <div class="page-sidebar navbar-collapse collapse">
        <ul class="page-sidebar-menu  page-header-fixed page-sidebar-menu-hover-submenu " data-keep-expanded="false" data-auto-scroll="true" data-slide-speed="200">
	        <c:forEach items="${menus}" var="entity">
				
				<li id="menu_li_${entity.menuId }" class="nav-item">
					<c:choose>
						<c:when test="${not empty entity.url}"><c:set var="url" value="${entity.url}"/></c:when>
						<c:otherwise><c:set var="url" value="javascript:;"/></c:otherwise>
					</c:choose>
	                <a href="<c:url value="${url}" />" class="nav-link nav-toggle">
	                    <i class="${entity.icon }"></i>
	                    <span class="title">${entity.name }</span>
	                    <span class="selected"></span>
	                    <span class="arrow open"></span>
	                </a>
	                <ul class="sub-menu">
	                	<c:forEach items="${entity.list}" var="sub">
	                    <li class="nav-item" id="sub_li_${sub.menuId}">
	                        <a href="<c:url value="${sub.url }" />" class="nav-link ">
	                            <i class="${sub.icon }"></i>
	                            <span class="title">${sub.name}</span>
	                            <span class="selected"></span>
	                        </a>
							<c:if test="${not empty sub.list}">
								<ul class="sub-menu">
									<c:forEach items="${sub.list}" var="sub2">
										<li class="nav-item" id="sub_li_${sub.menuId}">
											<a href="<c:url value="${sub2.url }" />" class="nav-link ">
												<i class="${sub2.icon }"></i>
												<span class="title">${sub2.name}</span>
												<span class="selected"></span>
											</a>
											<c:if test="${not empty sub2.list}">
												<ul class="sub-menu">
													<c:forEach items="${sub2.list}" var="sub3">
														<li class="nav-item" id="sub_li_${sub.menuId}">
															<a href="<c:url value="${sub3.url }" />" class="nav-link ">
																<i class="${sub3.icon }"></i>
																<span class="title">${sub3.name}</span>
																<span class="selected"></span>
															</a>
														</li>
													</c:forEach>
												</ul>
											</c:if>
										</li>
									</c:forEach>
								</ul>
							</c:if>
	                    </li>
	                    </c:forEach>
	                </ul>
	            </li>
			</c:forEach>
		</ul>
    </div>
</div>
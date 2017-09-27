<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="en">
    <!--<![endif]-->
    <!-- BEGIN HEAD -->

    <head>
        <meta charset="utf-8" />
        <title>400 错误</title>
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta content="width=device-width, initial-scale=1" name="viewport" />
        <meta content="" name="description" />
        <meta content="" name="author" />

		
		<!-- 开始主题CSS -->
		<link href="<c:url value="/resources/admin/global/css/components.min.css" />" rel="stylesheet" id="style_components" type="text/css" />
		<link href="<c:url value="/resources/admin/global/css/plugins.min.css" />" rel="stylesheet" type="text/css" />
		<link href="<c:url value="/resources/admin/layouts/layout2/css/layout.min.css" />" rel="stylesheet" type="text/css" />
		<link href="<c:url value="/resources/admin/layouts/layout2/css/themes/blue.min.css" />" rel="stylesheet" type="text/css" id="style_color" />
		<link href="<c:url value="/resources/admin/layouts/layout2/css/custom.min.css" />" rel="stylesheet" type="text/css" />
		<!-- 结束主题CSS -->
		<link href="<c:url value="/resources/admin/pages/css/error.min.css" />" rel="stylesheet" type="text/css" />
		<script src="<c:url value="/resources/admin/global/plugins/jquery.min.js" />" type="text/javascript"></script>
		<link rel="shortcut icon" href="<c:url value="/resources/comm/img/favicon.ico" />"/>
	 </head>
    <!-- END HEAD -->

<body class=" page-404-full-page">
	<div class="row">
		<div class="col-md-12 page-404">
			<div class="number font-red"> 404 </div>
			<div class="details">
				<h3>出错了.</h3>
				<p> 你返回的页面不存在.</p>
					<%--<br/>--%>
					<%--<a class="btn red btn-outline" href="<c:url value="/"/>"> 返回首页 </a>--%>
			</div>
		</div>
	</div>

</body>
</html>
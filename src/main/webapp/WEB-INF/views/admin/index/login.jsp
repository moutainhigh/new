<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<!DOCTYPE html>

<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
    <!--<![endif]-->
    <!-- BEGIN HEAD -->

    <head>
        <meta charset="utf-8" />
        <title>华宇ERP管理系统</title>
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta content="width=device-width, initial-scale=1" name="viewport" />
        <meta content="" name="description" />
        <meta content="" name="author" />
        <!-- BEGIN GLOBAL MANDATORY STYLES -->
        <link href="<c:url value="/resources/admin/global/plugins/font-awesome/css/font-awesome.min.css" />" rel="stylesheet" type="text/css" />
		<link href="<c:url value="/resources/admin/global/plugins/simple-line-icons/simple-line-icons.min.css" />" rel="stylesheet" type="text/css" />
		<link href="<c:url value="/resources/admin/global/plugins/bootstrap/css/bootstrap.min.css" />" rel="stylesheet" type="text/css" />
		<link href="<c:url value="/resources/admin/global/plugins/uniform/css/uniform.default.css" />" rel="stylesheet" type="text/css" />
		<link href="<c:url value="/resources/admin/global/plugins/bootstrap-switch/css/bootstrap-switch.min.css" />" rel="stylesheet" type="text/css" />
        <!-- END GLOBAL MANDATORY STYLES -->
        <!-- BEGIN PAGE LEVEL PLUGINS -->
        <link href="<c:url value="/resources/admin/global/plugins/select2/css/select2.min.css" />" rel="stylesheet" type="text/css" />
        <link href="<c:url value="/resources/admin/global/plugins/select2/css/select2-bootstrap.min.css" />" rel="stylesheet" type="text/css" />

        <link href="<c:url value="/resources/admin/global/css/components.min.css" />" rel="stylesheet" id="style_components" type="text/css" />
		<link href="<c:url value="/resources/admin/global/css/plugins.min.css" />" rel="stylesheet" type="text/css" />
        <link href="<c:url value="/resources/hr/css/login.css" />" rel="stylesheet" type="text/css" />



        <link rel="shortcut icon" href="<c:url value="/resources/comm/img/favicon.ico" />"/>

    <!-- END HEAD -->

    <body class=" login">
        <div class="login-head">
          <img src="<c:url value="/resources/hr/front/images/login-logo.png"/>">
            <h1>华宇ERP 管理系统</h1>
        </div>
        <!-- BEGIN LOGIN -->
        <div class="content">
            <!-- BEGIN LOGIN FORM -->
            <form class="login-form" action="<c:url value="/j_spring_security_check" />" method="post">
                <div class="alert alert-danger 		
                <c:if test="${sessionScope['SPRING_SECURITY_LAST_EXCEPTION'].message==null}">
		 		display-hide
				</c:if>">
                    <button class="close" data-close="alert"></button>
                    <span> ${sessionScope['SPRING_SECURITY_LAST_EXCEPTION'].message}. </span>
                </div>
                <div class="form-group">
                    <!--ie8, ie9 does not support html5 placeholder, so we just show field title for that-->
                    <label class="control-label visible-ie8 visible-ie9">帐号</label>
                    <input class="form-control form-control-solid placeholder-no-fix" type="text" autocomplete="off" placeholder="帐号" name="j_username" value=""  /> </div>
                <div class="form-group">
                    <label class="control-label visible-ie8 visible-ie9">密码</label>
                    <input class="form-control form-control-solid placeholder-no-fix" type="password" autocomplete="off" placeholder="密码" name="j_password"  value="" /> </div>
                <div class="form-group">
					<!--ie8, ie9 does not support html5 placeholder, so we just show field title for that-->
					<label class="control-label visible-ie8 visible-ie9">验证码</label>
					<div class="input-group ">
						<input class="form-control form-control-solid placeholder-no-fix" type="text" placeholder="验证码(4位)" name="j_code">
						<span class="input-group-btn">
						<img id="kImage" alt="" src="<c:url value="/admin/login/startCaptcha" />"/>
						</span>
						<!--
						<a id="refreshBtn" class="input-group-addon">
						<i class="fa fa-refresh"></i>
						</a>
						-->
					</div>
				</div>
                     
                <div class="form-actions">
                    <button type="submit" class="btn green uppercase">登录</button>
                   
                </div>
            </form>
            <!-- END LOGIN FORM -->
        </div>
        <div class="copyright">${applicationScope.commConfig["admin_site_copyright"] }  </div>
        <!--[if lt IE 9]>
		<script src="../assets/global/plugins/respond.min.js"></script>
		<script src="../assets/global/plugins/excanvas.min.js"></script> 
		<![endif]-->
        <!-- BEGIN CORE PLUGINS -->
		<script src="<c:url value="/resources/admin/global/plugins/jquery.min.js" />" type="text/javascript"></script>
		<script src="<c:url value="/resources/admin/global/plugins/bootstrap/js/bootstrap.min.js" />" type="text/javascript"></script>
		<script src="<c:url value="/resources/admin/global/plugins/js.cookie.min.js" />" type="text/javascript"></script>
		<script src="<c:url value="/resources/admin/global/plugins/bootstrap-hover-dropdown/bootstrap-hover-dropdown.min.js" />" type="text/javascript"></script>
		<script src="<c:url value="/resources/admin/global/plugins/jquery-slimscroll/jquery.slimscroll.min.js" />" type="text/javascript"></script>
		<script src="<c:url value="/resources/admin/global/plugins/jquery.blockui.min.js" />" type="text/javascript"></script>
		<script src="<c:url value="/resources/admin/global/plugins/uniform/jquery.uniform.min.js" />" type="text/javascript"></script>
		<script src="<c:url value="/resources/admin/global/plugins/bootstrap-switch/js/bootstrap-switch.min.js" />" type="text/javascript"></script>
        <!-- END CORE PLUGINS -->
        <!-- BEGIN PAGE LEVEL PLUGINS -->
        <script src="<c:url value="/resources/admin/global/plugins/jquery-validation/js/jquery.validate.min.js" />" type="text/javascript"></script>
        <script src="<c:url value="/resources/admin/global/plugins/jquery-validation/js/additional-methods.min.js" />" type="text/javascript"></script>
        <script src="<c:url value="/resources/admin/global/plugins/select2/js/select2.full.min.js" />" type="text/javascript"></script>
        <!-- END PAGE LEVEL PLUGINS -->
        <!-- BEGIN THEME GLOBAL SCRIPTS -->
        <script src="<c:url value="/resources/admin/global/scripts/app.min.js" />" tppabs="http://www.keenthemes.com/preview/metronic/theme/assets/global/scripts/app.min.js" type="text/javascript"></script>
        <!-- END THEME GLOBAL SCRIPTS -->
        <!-- BEGIN PAGE LEVEL SCRIPTS -->
        <script src="<c:url value="/resources/admin/pages/scripts/login.min.js" />" tppabs="http://www.keenthemes.com/preview/metronic/theme/assets/pages/scripts/login.min.js" type="text/javascript"></script>
        <!-- END PAGE LEVEL SCRIPTS -->
        <!-- BEGIN THEME LAYOUT SCRIPTS -->
        <!-- END THEME LAYOUT SCRIPTS -->
        
        <script>
        jQuery(document).ready(function() {     

			$('#kImage').click(function(){
		    	$('#kImage').hide().attr('src', '<c:url value="/admin/login/startCaptcha/" />?' + Math.floor(Math.random()*100) ).fadeIn();
		    });
			$('#refreshBtn').click(function(){
		    	$('#kImage').hide().attr('src', '<c:url value="/admin/login/startCaptcha/" />?' + Math.floor(Math.random()*100) ).fadeIn();
		    });
		});
        </script>
</body>


</html>
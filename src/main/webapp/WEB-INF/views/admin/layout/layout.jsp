<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="en">
<!-- BEGIN HEAD -->
<head>
<meta charset="utf-8" />
<title>华宇ERP管理系统</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1" name="viewport" />
<meta content="" name="description" />
<meta content="" name="author" />

<!-- 开始全局CSS -->
<link href="<c:url value="/resources/admin/global/plugins/font-awesome/css/font-awesome.min.css" />" rel="stylesheet" type="text/css" />
<link href="<c:url value="/resources/admin/global/plugins/simple-line-icons/simple-line-icons.min.css" />" rel="stylesheet" type="text/css" />
<link href="<c:url value="/resources/admin/global/plugins/bootstrap/css/bootstrap.min.css" />" rel="stylesheet" type="text/css" />
<link href="<c:url value="/resources/admin/global/plugins/uniform/css/uniform.default.css" />" rel="stylesheet" type="text/css" />
<link href="<c:url value="/resources/admin/global/plugins/bootstrap-switch/css/bootstrap-switch.min.css" />" rel="stylesheet" type="text/css" />
<!-- 结束全局CSS -->

<!-- 开始主题CSS -->
<link href="<c:url value="/resources/admin/global/css/components.min.css" />" rel="stylesheet" id="style_components" type="text/css" />
<link href="<c:url value="/resources/admin/global/css/plugins.min.css" />" rel="stylesheet" type="text/css" />
<link href="<c:url value="/resources/admin/layouts/layout2/css/layout.min.css" />" rel="stylesheet" type="text/css" />
<link href="<c:url value="/resources/admin/layouts/layout2/css/themes/blue.min.css" />" rel="stylesheet" type="text/css" id="style_color" />
<link href="<c:url value="/resources/admin/layouts/layout2/css/custom.min.css" />" rel="stylesheet" type="text/css" />
<!-- 结束主题CSS -->

<script src="<c:url value="/resources/admin/global/plugins/jquery.min.js" />" type="text/javascript"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/bootstrap-table/bootstrap-table.js" />"></script>
<%--<link rel="stylesheet" type="text/css" href="<c:url value="/resources/plugIn/zTree/css/metroStyle/metroStyle.css" />"/>--%>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/plugIn/zTree/css/zTreeStyle/zTreeStyle.css" />"/>
<script type="text/javascript" src="<c:url value="/resources/plugIn/zTree/js/jquery.ztree.core.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/plugIn/zTree/js/jquery.ztree.exedit.js" />"></script>
<link rel="shortcut icon" href="<c:url value="/resources/comm/img/favicon.ico" />"/>
<link href="<c:url value="/resources/admin/global/plugins/bootstrap-table/bootstrap-table.min.css" />" rel="stylesheet" type="text/css" />
</head>

<!-- BEGIN BODY -->
<body class="page-header-fixed page-sidebar-closed-hide-logo page-container-bg-solid">

<!-- BEGIN HEADER -->
<tiles:insertAttribute name="header" />
<!-- END HEADER -->

<!-- BEGIN CONTAINER -->
<div class="page-container">

<!-- BEGIN SIDEBAR -->
<tiles:insertAttribute name="sidebar"/>
<!-- END SIDEBAR -->

<!-- BEGIN CONTENT -->
<div class="page-content-wrapper">
<div class="page-content">
<tiles:insertAttribute name="content" />
</div>
</div>
<!-- END CONTENT -->

</div>

<tiles:insertAttribute name="footer" />
<!-- END FOOTER -->

<!--[if lt IE 9]>
<script src="global/plugins/respond.min.js"></script>
<script src="global/plugins/excanvas.min.js"></script> 
<![endif]-->

<!-- BEGIN CORE PLUGINS -->
<script src="<c:url value="/resources/admin/global/plugins/bootstrap/js/bootstrap.min.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/admin/global/plugins/jquery.cokie.min.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/admin/global/plugins/bootstrap-hover-dropdown/bootstrap-hover-dropdown.min.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/admin/global/plugins/jquery-slimscroll/jquery.slimscroll.min.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/admin/global/plugins/jquery.blockui.min.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/admin/global/plugins/uniform/jquery.uniform.min.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/admin/global/plugins/bootstrap-switch/js/bootstrap-switch.min.js" />" type="text/javascript"></script>
<!-- END CORE PLUGINS -->

 <!-- BEGIN THEME GLOBAL SCRIPTS -->
<script src="<c:url value="/resources/admin/global/scripts/app.min.js" />" type="text/javascript"></script>
<!-- END THEME GLOBAL SCRIPTS -->

<!-- BEGIN THEME LAYOUT SCRIPTS -->
<script src="<c:url value="/resources/admin/layouts/layout2/scripts/layout.min.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/admin/layouts/layout2/scripts/demo.min.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/admin/layouts/global/scripts/quick-sidebar.min.js" />" type="text/javascript"></script>
<!-- END THEME LAYOUT SCRIPTS -->

<script src="<c:url value="/resources/admin/global/plugins/layer/layer.js"/>"></script>

<script>
jQuery(document).ready(function() { 
   
   if($.cookie("menu_li")!=null && $.cookie("sub_li")!=null) {
	   $("#"+$.cookie("sub_li")).addClass("active");
	   $("#"+$.cookie("menu_li")).addClass("start active open");
   }

    $("[id^=menu_li]").click(function(){
        $.cookie('menu_li', $(this).attr("id"),{path:'/'});
        $.cookie('sub_li',null);
    });

    $("[id^=sub_li]").click(function(){
        $.cookie('sub_li', $(this).attr("id"),{path:'/'});
        $.cookie('menu_li', $(this).parent().parent().attr("id"),{path:'/'});
   }); 
});
</script>
<!-- END JAVASCRIPTS -->
<!-- 百度统计 -->
</body>
<!-- END BODY -->
</html>
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
  </head>
  <body>

  </body>

  <script>
      $(function () {
          if (window.parent.myRefreshCount == undefined){
              window.parent.myRefreshCount = 0;
              var iframe = $('#deeIframe')[0];
              if (iframe){
                  if (iframe.attachEvent) {
                      iframe.attachEvent('onload', function() {
                          window.location.reload();
                      });
                  } else {
                      iframe.onload = function () {
                          console.info(44444);
                          window.location.reload();
                      };
                  }
              }
          }
      });
  </script>
</html>

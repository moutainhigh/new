<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/tlds/lycore.tld" prefix="ly"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!-- 开始全局CSS -->
<link href="<c:url value="/resources/admin/global/plugins/font-awesome/css/font-awesome.min.css" />" rel="stylesheet" type="text/css" />
<link href="<c:url value="/resources/admin/global/plugins/simple-line-icons/simple-line-icons.min.css" />" rel="stylesheet" type="text/css" />
<link href="<c:url value="/resources/admin/global/plugins/bootstrap/css/bootstrap.min.css" />" rel="stylesheet" type="text/css" />
<link href="<c:url value="/resources/admin/global/plugins/uniform/css/uniform.default.css" />" rel="stylesheet" type="text/css" />
<link href="<c:url value="/resources/admin/global/plugins/bootstrap-switch/css/bootstrap-switch.min.css" />" rel="stylesheet" type="text/css" />
<!-- 结束全局CSS -->

<!-- 开始主题CSS -->
<link href="<c:url value="/resources/admin/global/css/components.min.css" />" rel="stylesheet" id="style_components" type="text/css" />
<style>
    .actions * {
        margin-top: 0px;
    }
    body {
        font-size: 10px;
    }
</style>
<!-- BEGIN EXAMPLE TABLE PORTLET-->
<div class="portlet light" id="form_wizard_1">
    <%--<div class="portlet-title">
        <div class="caption">
            <span class="caption-subject font-green sbold uppercase">选择项目</span>
       	</div>
    </div>--%>
    <div class="portlet-body">
        <div class="table-scrollable fixed-table-container" style="height: 460px;overflow:auto;">
        <table class="table table-striped table-bordered table-advance table-hover" id="tlist">
            <thead>
                <tr>
                    <th>选择</th>
                    <th>集团/区域</th>
                    <th>项目</th>
                    <th>是否独立预算</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${projectList}" var="e" varStatus="status">
                    <tr>
                        <td class="bs-checkbox"><label class="mt-radio mt-radio-single mt-radio-outline"><input name="btSelectItem" type="radio" value="${e.projectId}" data-isownname="${e.isOwnName}" data-projectname="${e.name}"><span></span></label></td>
                        <td>${e.companyName}</td>
                        <td>${e.name}</td>
                        <td>${e.isOwnName}</td>
                    </tr>
                </c:forEach>
                <c:if test="${empty projectList}">
                	<tr><td colspan="20">没有对应的数据</td></tr>
                </c:if>
            </tbody>
        </table>
        </div>
        <div class="row">
            <div class="col-md-7 col-sm- text-right" >
                <button type="button" class="btn blue" id="id_transmit" style="margin-right: 20px;">确定</button>
                <button type="button" class="btn default" id="id_cancel">取消</button>
            </div>
        </div>
    </div>
</div>
<!-- END EXAMPLE TABLE PORTLET-->
<script src="<c:url value="/resources/admin/global/plugins/jquery.min.js" />" type="text/javascript"></script>
<script type="text/javascript">
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    $(function () {
        //给父页面传值
        $('#id_transmit').on('click', function(){
            if (${empty projectList}){
                parent.layer.msg('您还未选择项目');
            } else {
                var obj=$('input:radio[name="btSelectItem"]:checked');
                var val = obj.val();
                if (val==null) {
                    parent.layer.msg('您还未选择项目');
                } else {
                    var isOwnName=obj.data('isownname');
                    var projectName=obj.data('projectname');
                    parent.$('#id_projectId').val(val);
                    parent.$('#id_searchProject').val(projectName);
                    parent.$('#id_isOwnName').val(isOwnName);
                    parent.layer.close(index);
                }
           }
        });

        $('#id_cancel').on('click', function(){
            parent.layer.close(index);
        });
    });

</script>
<%@ page import="com.huayu.material.domain.CityEnum" %>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles-extras" prefix="tilesx" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<tilesx:useAttribute id="user" name="user" classname="java.lang.Object" />

<style>
    .navbar-nav > li > a.js_switch_company{
        padding-top: 24px;
        padding-bottom: 24px;
        color: #7f96ac;
    }
    .navbar-nav > li > a.js_switch_company:hover{
        background-color: #f9fafc;
    }
</style>
        
<div class="page-header navbar navbar-fixed-top">
    <div class="page-header-inner ">
        <div class="page-logo">
            <a href="<c:url value="/admin/index/default"/>" >
                <img src="<c:url value="/resources/cms/images/logo.png" />" alt="logo" class="logo-default" style="margin: 20px 0 0;"  />
            </a>
            <div class="menu-toggler sidebar-toggler">
            </div>
        </div>

        <a href="javascript:;" class="menu-toggler responsive-toggler" data-toggle="collapse" data-target=".navbar-collapse"> </a>
        <div class="page-actions">
        <c:choose>
            <c:when test="${user.regSource eq 'bill'}">
                <h4 class="font-blue-hoki">发票管理系统</h4>
            </c:when>
            <c:when test="${user.regSource eq 'hr'}">
            <h4 class="font-blue-hoki">人力资源管理系统</h4>
            </c:when>
            <c:when test="${user.regSource eq 'material'}">
                <h4 class="font-blue-hoki">材料价格管理系统</h4>
            </c:when>
            <c:when test="${user.regSource eq 'inventory'}">
                <h4 class="font-blue-hoki">材料库存管理系统</h4>
            </c:when>
            <c:when test="${user.regSource eq 'marketcost'}">
                <h4 class="font-blue-hoki">营销费用管理系统</h4>
            </c:when>
            <c:when test="${user.regSource eq 'projectcost'}">
                <h4 class="font-blue-hoki">成本数据沉淀库系统</h4>
            </c:when>
        </c:choose>
        </div>
        <div class="page-top">
            <div class="top-menu">
                <ul class="nav navbar-nav pull-right">
                    <c:choose>
                        <c:when test="${user.regSource eq 'hr'}">
                        <li class="dropdown">
                            <a href="javascript:;" class="js_switch_company">
                                <span class="username username-hide-on-mobile">当前公司：${user.companyName}</span>
                                <i class="fa fa-exchange"></i>
                                <span class="username username-hide-on-mobile">切换公司</span>
                            </a>
                        </li>
                        </c:when>
                        <c:when test="${user.regSource eq 'material'}">
                            <li class="dropdown">
                                <c:set var="cityEnum" value="<%= com.huayu.material.domain.CityEnum.getAvailableCities()%>"/>
                                <c:set value="请选择" var="cname"/>
                                <c:forEach var="c" items="${cityEnum}">
                                        <c:if test="${user.currDataId eq c.getCid()}">
                                            <c:set value="${c.cname}" var="cname"/>
                                        </c:if>
                                </c:forEach>
                                <a href="javascript:;" class="js_switch_city">
                                    <span class="username username-hide-on-mobile">所在城市：${cname}</span>
                                        <i class="fa fa-exchange"></i>
                                        <span class="username username-hide-on-mobile">切换城市</span>
                                </a>
                            </li>
                        </c:when>

                        <c:when test="${user.regSource eq 'inventory'}">
                            <li class="dropdown">
                                <a href="javascript:;" class="js_switch_project">
                                    <span class="username username-hide-on-mobile">当前项目：${user.currDataValue}</span>
                                    <i class="fa fa-exchange"></i>
                                    <span class="username username-hide-on-mobile">切换项目</span>
                                </a>
                            </li>
                        </c:when>
                        <c:when test="${user.regSource eq 'projectcost'}">
                            <li class="dropdown">
                                <a href="javascript:;" class="js_switch_company">
                                    <span class="username username-hide-on-mobile">当前公司：${user.companyName}</span>
                                    <i class="fa fa-exchange"></i>
                                    <span class="username username-hide-on-mobile">切换公司</span>
                                </a>
                            </li>
                        </c:when>
                    </c:choose>
                    <li class="dropdown dropdown-user">
                        <a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-close-others="true">
                            <img alt="" class="img-circle" src="<c:url value="/resources/admin/layouts/layout2/img/avatar3_small.jpg" />" />
                            <span class="username username-hide-on-mobile">${user.name}</span>
                        </a>
                    </li>
					<li class="dropdown dropdown-extended quick-sidebar-toggler">
                        <span class="sr-only">Toggle Quick Sidebar</span>
                    	<i class="icon-logout"></i>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</div>
<div class="clearfix">
</div>

<c:choose>
    <c:when test="${user.regSource eq 'hr'}">

        <div class=" js_switch_comp_div" style="display: none">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                    <h4 class="modal-title">选择单位</h4>
                </div>
                <div class="modal-body">
                    <div class="container-fluid">
                        <div class="row">
                            <div class="col-md-4">
                                <ul id="globalCompanyTree" class="ztree"></ul>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-action="cancel">取消</button>
                        <button type="button" class="btn btn-primary" data-action="save">确定</button>
                    </div>
                </div>
            </div>
        </div>

        <script>

            $(function () {
                var switchCompTreeObj,layerIndex,currComp="${user.currCompanyCode}";
                var initTree = function (_arr,treeId,callback) {
                    var setting = {callback:{
                        onClick:function (event, treeId, treeNode) {
                            if ("function" == typeof(callback)){
                                callback(event,treeId,treeNode);
                            }
                        }
                    },data:{
                        key:{
                            children:"childList"
                        }
                    },view:{
                        expandSpeed:''
                    }};
                    return $.fn.zTree.init($(treeId), setting, _arr);
                }

                var showSwitchDiv = function (cid) {
                    $.post('<c:url value="/hr/org/getAllCompanies"/>', function (result) {
                        if (result.success) {
                            switchCompTreeObj = initTree(result.rdata, "#globalCompanyTree", function (event, treeId, treeNode) {

                            });
                            var nodes = switchCompTreeObj.getNodes();
                            switchCompTreeObj.expandNode(nodes[0],true,false,false);
                            layerIndex = layer.open({
                                type: 1,
                                title:false,
                                closeBtn:0,
                                area: ['600px'],
                                content:$('div.js_switch_comp_div')
                            });
                        }else {
                            layer.msg("获取公司信息失败");
                        }
                    }, 'json');
                }

                $('a.js_switch_company').on('click',function () {
                    showSwitchDiv();
                });

                $('div.js_switch_comp_div').on('click','button',function () {
                    var action = $(this).data('action');
                    if (action){
                        if (action=='save'){
                            var nodes = switchCompTreeObj.getSelectedNodes();
                            if (nodes.length==1){
                                var node = nodes[0];
//                            if(!node.isParent){
                                $.post('<c:url value="/hr/switchOperCompany"/>', {companyId:node.id,companyName:node.name},function (result) {
                                    if (result.success){
                                        layer.msg('切换成功',{time:800},function () {
                                            $('div.js_switch_comp_div').modal('hide');
                                            window.location.href='<c:url value="/admin/index/default"/>';
                                        });
                                    }else{
                                        layer.msg(result.rmsg);
                                    }
                                }, 'json');
//                            }else {
//                                layer.msg("请选择单位");
//                            }
                            }else{
                                layer.msg("请选择单位");
                            }
                        }else if(action=="cancel"){
                            if (!currComp){
                                layer.msg("请选择单位");
                            }else{
                                layer.close(layerIndex);
                            }
                        }
                    }
                });
                if (!currComp){
                    layerIndex = showSwitchDiv();
                }
            });

        </script>
    </c:when>

    <c:when test="${user.regSource eq 'material'}">
        <div class=" js_switch_city_div" style="display: none">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                    <h4 class="modal-title">选择城市</h4>
                </div>
                <div class="modal-body">
                    <div class="container-fluid">
                        <div class="row">
                            <div class="col-md-12">
                                <c:set var="cityEnum" value="<%= com.huayu.material.domain.CityEnum.getAvailableCities() %>"/>
                                <c:forEach var="c" items="${cityEnum}">
                                <label>
                                    <input type="radio" value="${c.getCid()}" name="switchCity" <c:if test="${user.cid eq c.getCid()}">checked</c:if>>${c.cname}
                                </label>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-action="cancel">取消</button>
                        <button type="button" class="btn btn-primary" data-action="save">确定</button>
                    </div>
                </div>
            </div>
        </div>

        <script>

            $(function () {
                var layerIndex,currCity='${user.currDataId}';

                function showSwitchDiv() {
                    layerIndex = layer.open({
                        type: 1,
                        title:false,
                        closeBtn:0,
                        area: ['600px'],
                        content:$('div.js_switch_city_div')
                    });
                }
                $('a.js_switch_city').on('click',function () {
                    showSwitchDiv();
                });

                $('div.js_switch_city_div').on('click','button',function () {
                    var action = $(this).data('action');
                    if (action) {
                        if (action == 'save') {
                            var ck = $('input[name=switchCity]:checked');
                            if (ck.length!=1){
                                layer.msg("请选择城市！",{time:800});
                            }else{
                                $.post('<c:url value="/admin/materialCategory/setCurrentCity"/>', {currDataId:ck[0].value},function (result) {
                                    if (result.success){
                                        layer.msg('切换成功',{time:800},function () {
                                            $('div.js_switch_comp_div').modal('hide');
                                            window.location.href='<c:url value="/admin/index/default"/>';
                                        });
                                    }else{
                                        layer.msg(result.rmsg);
                                    }
                                }, 'json');
                            }
                        }else if(action=="cancel"){
                            if (!currCity){
                                layer.msg("请选择单位");
                            }else{
                                layer.close(layerIndex);
                            }
                        }
                    }
                });

                if (currCity=='' || currCity==0){
                    showSwitchDiv();
                }
            });
        </script>

    </c:when>

    <c:when test="${user.regSource eq 'inventory'}">
            <div class=" js_switch_project_div" style="display: none">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                        <h4 class="modal-title">选择项目</h4>
                    </div>
                    <div class="modal-body">
                        <div class="container-fluid">
                            <div class="row">
                                <div class="col-md-12">

                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-action="cancel">取消</button>
                            <button type="button" class="btn btn-primary" data-action="save">确定</button>
                        </div>
                    </div>
                </div>
            </div>

            <script>
                $(function () {
                    var layerIndex,currProject='${user.currDataId}';

                    var showSwitchDiv = function () {
                        $.post('<c:url value="/admin/inventory/storage/getAllProjects"/>', function (result) {
                            if (result.success) {
                                var _html = "";
                                $.each(result.rdata,function (k, v) {
                                    _html+="<label>";
//                                    if (currProject == ''){
//                                        _html+='<input type="radio" value="'+v.id+'" name="switchCity">'+v.name;
//                                    }else{
                                        if(currProject == v.id){
                                            _html+='<input type="radio" value="'+v.id+'" name="switchCity" checked data-name="'+v.name+'">'+v.name;
                                        }else{
                                            _html+='<input type="radio" value="'+v.id+'" name="switchCity" data-name="'+v.name+'">'+v.name;
                                        }
//                                    }
                                    _html+="</label>"
                                });
                                $('div.js_switch_project_div').find('div.col-md-12').html(_html);
                                layerIndex = layer.open({
                                    type: 1,
                                    title:false,
                                    closeBtn:0,
                                    area: ['600px'],
                                    content:$('div.js_switch_project_div')
                                });
                            }else {
                                layer.msg("获取项目失败");
                            }
                        }, 'json');
                    }

                    $('a.js_switch_project').on('click',function () {
                        showSwitchDiv();
                    });

                    $('div.js_switch_project_div').on('click','button',function () {
                        var action = $(this).data('action');
                        if (action) {
                            if (action == 'save') {
                                var ck = $('input[name=switchCity]:checked');
                                if (ck.length!=1){
                                    layer.msg("请选择一个项目！",{time:800});
                                }else{
                                    $.post('<c:url value="/admin/inventory/setCurrentProject"/>', {currDataId:ck[0].value,currDataValue:$(ck[0]).data('name')},function (result) {
                                        if (result.success){
                                            layer.msg('切换成功',{time:800},function () {
                                                $('div.js_switch_project_div').modal('hide');
                                                window.location.href='<c:url value="/admin/index/default"/>';
                                            });
                                        }else{
                                            layer.msg(result.rmsg);
                                        }
                                    }, 'json');
                                }
                            }else if(action=="cancel"){
                                if (!currProject){
                                    layer.msg("请选择一个项目");
                                }else{
                                    layer.close(layerIndex);
                                }
                            }
                        }
                    });

                    if (currProject==''){
                        showSwitchDiv();
                    }
                });
            </script>
    </c:when>

    <c:when test="${user.regSource eq 'projectcost'}">

        <div class=" js_switch_comp_div" style="display: none">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                    <h4 class="modal-title">选择公司</h4>
                </div>
                <div class="modal-body">
                    <div class="container-fluid">
                        <div class="row">
                            <div class="col-md-4">
                                <ul id="globalCompanyTree1" class="ztree"></ul>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-action="cancel">取消</button>
                        <button type="button" class="btn btn-primary" data-action="save">确定</button>
                    </div>
                </div>
            </div>
        </div>

        <script>

            $(function () {
                var switchCompTreeObj,layerIndex,currComp="${user.currCompanyCode}";
                var initTree = function (_arr,treeId,callback) {
                    var setting = {callback:{
                        onClick:function (event, treeId, treeNode) {
                            if ("function" == typeof(callback)){
                                callback(event,treeId,treeNode);
                            }
                        }
                    },data:{
                        key:{
                            children:"childList"
                        }
                    },view:{
                        expandSpeed:''
                    }};
                    return $.fn.zTree.init($(treeId), setting, _arr);
                }

                var showSwitchDiv = function (cid) {
                    $.post('<c:url value="/hr/org/getAllCompanies"/>', function (result) {
                        if (result.success) {
                            switchCompTreeObj = initTree(result.rdata, "#globalCompanyTree1", function (event, treeId, treeNode) {

                            });
                            var nodes = switchCompTreeObj.getNodes();
                            switchCompTreeObj.expandNode(nodes[0],true,false,false);
                            layerIndex = layer.open({
                                type: 1,
                                title:false,
                                closeBtn:0,
                                area: ['600px'],
                                content:$('div.js_switch_comp_div')
                            });
                        }else {
                            layer.msg("获取公司信息失败");
                        }
                    }, 'json');
                }

                $('a.js_switch_company').on('click',function () {
                    showSwitchDiv();
                });

                $('div.js_switch_comp_div').on('click','button',function () {
                    var action = $(this).data('action');
                    if (action){
                        if (action=='save'){
                            var nodes = switchCompTreeObj.getSelectedNodes();
                            if (nodes.length==1){
                                var node = nodes[0];
//                            if(!node.isParent){
                                $.post('<c:url value="/hr/switchOperCompany"/>', {companyId:node.id,companyName:node.name},function (result) {
                                    if (result.success){
                                        layer.msg('切换成功',{time:800},function () {
                                            $('div.js_switch_comp_div').modal('hide');
                                            window.location.href='<c:url value="/admin/index/default"/>';
                                        });
                                    }else{
                                        layer.msg(result.rmsg);
                                    }
                                }, 'json');
//                            }else {
//                                layer.msg("请选择单位");
//                            }
                            }else{
                                layer.msg("请选择单位");
                            }
                        }else if(action=="cancel"){
                            if (!currComp){
                                layer.msg("请选择单位");
                            }else{
                                layer.close(layerIndex);
                            }
                        }
                    }
                });
                if (!currComp){
                    layerIndex = showSwitchDiv();
                }
            });

        </script>
    </c:when>

</c:choose>


<script>

    <c:if test="${not empty broadcastMsg}">
    $(function () {
        var localHandler = function(){
            var msg = '${broadcastMsg}';
            if (msg=='close'){
                layer.alert("服务器将于一分钟后重启");
            }else{
                layer.alert(msg);
            }
        }
        localHandler();
    });
    </c:if>

    $(".quick-sidebar-toggler").click(function(i){
        if(confirm("你确信要退出吗？")) {
            location.href = "<c:url value="/j_spring_security_logout" />";
         }
    });

    $(".dropdown-user").click(function(){
        window.location.href='<c:url value="/admin/user/toUpdatePassword"/>';
    });

</script>
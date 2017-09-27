<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/tlds/lycore.tld" prefix="ly"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<style>
    .actions * {
        margin-top: 0px;
    }
    body {
        font-size: 10px;
    }
    .search-icon {
        border: 1px solid #C6C6C6;
        background-image: url(<c:url value="/resources/admin/global/img/search.png"/> );
        line-height: 34px;
        padding-right: 20px;
        background-repeat: no-repeat;
        background-position: right center;
        cursor: pointer;
    }
    tr td,tr th{
        font-size: small !important;
    }
    label{
        font-size: small;
    }
    .form_update{
        height: 25px;
        line-height: inherit!important;
    }
    .stateC{
        position:relative;
        display:inline-block;
        width:20px;
        height:20px;
        border-radius: 50% !important;
        -webkit-border-radius: 50%;
        -moz-border-radius: 50%;
        -o-border-radius: 50%;
        -ms-border-radius: 50%;
        vertical-align:middle;
    }
    .not_Begin{
        background-color:#cecece;
    }
    .s_Normal{
        background-color:#328a79;
    }
    .s_Delay{
        background-color:#f73a41;
    }
    .hook:after{
        position:absolute;
        top: 10%;
        left: 30%;
        content:"";
        width: 25%;
        height: 50%;
        border: solid #fff;
        border-width: 0 2px 2px 0;
        transform: rotate(45deg);
        -ms-transform:rotate(45deg);
        -moz-transform:rotate(45deg);
        -webkit-transform:rotate(45deg);
        -o-transform:rotate(45deg);
    }
</style>
<div class="page-bar">
    <ul class="page-breadcrumb">
        <li>
            <i class="fa fa-home"></i>
            <a href="<c:url value="/admin/index/default" />">首页</a>
            <i class="fa fa-angle-right"></i>
        </li>
        <li>
            <span>项目进度</span>
            <i class="fa fa-angle-right"></i>
        </li>
        <li>
            <a href="<c:url value="/project/plan/get" />">编制计划</a>
            <i class="fa fa-angle-right"></i>
        </li>
        <li>
            <span>项目计划编制及汇报列表</span>
        </li>
    </ul>
    <div class="page-toolbar">
        <div class="btn-group pull-right">
            <div class="mt-action-buttons">
                <div class="btn-group btn-group-circle">
                    <button type="button" class="btn btn-outline blue-hoki active" onclick="javascript:history.go(-1);">返回</button>
                    <span></span>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- BEGIN EXAMPLE TABLE PORTLET-->
<div class="portlet light" id="form_wizard_1">
    <div class="portlet-title">
        <div class="caption">
            <span class="caption-subject font-green sbold uppercase">项目计划编制及汇报列表</span>
        </div>
    </div>
    <div class="portlet-title">
        <div class="actions" style="float: left;">
            <label>项目名称：</label>
            <input type="text" class="form-control input-inline input-sm" value="${projectArchives.projectName}" disabled="disabled"/>&nbsp;&nbsp;&nbsp;&nbsp;
            <label>版本：</label>
            <input type="text" class="form-control input-inline input-sm" value="${projectArchives.versions}" disabled="disabled" style="width: 30px;"/>
        </div>
    </div>
    <div class="portlet-body" >
        <div class="table-scrollable fixed-table-container" style="height: 460px;overflow:auto;">
            <table class="table table-striped table-bordered table-advance table-hover" id="tlist" >
                <thead>
                    <tr>
                        <th >序号</th>
                        <th >阶段</th>
                        <th >任务名称</th>
                        <th >关键节点</th>
                        <th >计划开始日期</th>
                        <th >计划截至日期</th>
                        <th >开始前预警天数</th>
                        <th >结束前预警天数</th>
                        <th class="hide">权重</th>
                        <th >主办部门</th>
                        <th >预警人</th>
                        <th >备注</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${projectPlanCompileLogList}" var="e" varStatus="status">
                        <tr>
                            <form action="#">
                                <td >${e.templateId}
                                    <input type="hidden" name="id" value="${e.id}"/>
                                    <input type="hidden" name="compileId" value="${e.compileId}"/>
                                    <input type="hidden" name="projectId" value="${e.projectId}"/>
                                    <input type="hidden" name="templateId" value="${e.templateId}"/>
                                </td>
                                <td style="width: 100px;">${e.stepName}</td>
                                <td style="width: 300px;">${e.taskName}</td>
                                <%--<td class="bs-checkbox" style="text-align: center !important;" ><span class="${e.iskeyNode eq 2?'stateC s_Delay':'stateC not_Begin'}"></span></td>--%>
                                <td style="<c:if test="${e.iskeyNode eq 2}" >color: red;</c:if>" >${e.iskeyNodeName}</td>
                                <td ><input type="text" name="startDate" class="form-control input-inline form_datetime laydate-icon form_update" style="width: 120px;" value="${fn:substring(e.startDate,0,10)}" readonly="readonly" onclick="laydate({istime: true, format: 'YYYY-MM-DD'})" autocomplete="off"/></td>
                                <td ><input type="text" name="endDate" class="form-control input-inline form_datetime laydate-icon form_update" style="width: 120px;" value="${fn:substring(e.endDate,0,10)}" readonly="readonly" onclick="laydate({istime: true, format: 'YYYY-MM-DD'})" autocomplete="off"/></td>
                                <td ><input type="text" type="number" min="1" name="preWarnDay" class="form-control input-inline input-sm form_update" style="width: 50px;" value="${e.preWarnDay}" /></td>
                                <td ><input type="text" type="number" min="1" name="sufWarnDay" class="form-control input-inline input-sm form_update" style="width: 50px;" value="${e.sufWarnDay}" /></td>
                                <td class="hide"><input type="text" type="number" min="1" name="weight" class="form-control input-inline input-sm form_update" style="width: 50px;" value="${e.weight}" readonly="readonly"/></td>
                                <td >
                                    <input type="text" class="form-control input-inline input-sm" style="width: 150px;" value="${e.shortDepartment}" readonly="readonly" title="${e.department}"/>
                                    <input type="hidden" name="department" value="${e.department}"/>
                                    <input type="hidden" name="departmentId" value="${e.departmentId}"/>
                                </td>
                                <td >
                                    <input type="text" class="form-control input-inline input-sm search-icon js_choose_emp2 form_update" style="width: 150px;" value="${e.shortWarnName}" readonly="readonly" title="${e.warnName}"/>
                                    <input type="hidden" name="warnName" value="${e.warnName}"/>
                                    <input type="hidden" name="warnUserId" value="${e.warnUserId}"/>
                                </td>
                               <%-- <td >
                                    <input type="text" class="form-control input-inline input-sm search-icon js_choose_emp form_update" style="width: 150px;" value="事业拓展部" readonly="readonly" title="事业拓展部"/>
                                    <input type="hidden" name="department" value="集团事业拓展部"/>
                                    <input type="hidden" name="departmentId" value="1685"/>
                                </td>
                                <td >
                                    <input type="text" class="form-control input-inline input-sm search-icon js_choose_emp2 form_update" style="width: 150px;" value="郑兴林" readonly="readonly" title="郑兴林"/>
                                    <input type="hidden" name="warnName" value="郑兴林"/>
                                    <input type="hidden" name="warnUserId" value="19423"/>
                                </td>--%>
                                <td ><input type="text" name="remark" class="form-control input-inline input-sm form_update" style="width: 150px;" value="${e.remark}" placeholder="备注"/></td>
                            </form>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="row">
            <div class="col-md-7 col-sm- text-right" >
                <button type="button" class="btn blue" id="id_transmit" style="margin-right: 20px;">保存</button>
                <a class="btn grey-salsa" href="<c:url value="/project/plan/get"/>" >返回列表</a>
            </div>
        </div>
    </div>
</div>

<div class="modal fade js_department_ps_div">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title">公司/部门/人员</h4>
            </div>
            <div class="modal-body">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-md-4">
                            <label>公司</label>
                            <ul id="empCompanyTree" class="ztree"></ul>
                        </div>
                        <div class="col-md-4">
                            <label>部门</label>
                            <ul id="empDepartmentTree" class="ztree"></ul>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default js_cancel_btn" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary js_save_btn">保存</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade js_department_ps_div2">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title">公司/部门/人员</h4>
            </div>
            <div class="modal-body">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-md-4">
                            <label>公司</label>
                            <ul id="empCompanyTree2" class="ztree"></ul>
                        </div>
                        <div class="col-md-4">
                            <label>部门</label>
                            <ul id="empDepartmentTree2" class="ztree"></ul>
                        </div>
                        <div class="col-md-4">
                            <label>人员</label>
                            <ul id="empTree2" class="ztree"></ul>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default js_cancel_btn2" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary js_save_btn2">保存</button>
            </div>
        </div>
    </div>
</div>

<!-- END EXAMPLE TABLE PORTLET-->
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/laydate/laydate.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/plugIn/zTree/js/jquery.ztree.excheck.js" />"></script>
<script type="text/javascript">
    var currentObj,idMap,nameMap,b;

    $(function () {
        var empTreeObj={
            companyTreeObj:null
            ,departmentTreeObj:null
            ,endTreeObj:null
        };

        var initTree = function (_arr,treeId,callback,settings) {
            var setting = {callback:{
                onClick:function (event, treeId, treeNode) {
                    if ("function" == typeof(callback)){
                        callback(event,treeId,treeNode);
                    }
                },onCheck:function (event, treeId, treeNode) {
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
            if ( typeof settings == "object"){
                for(var k in settings){
                    setting[k]=settings[k];
                }
            }
            return $.fn.zTree.init($('#'+treeId), setting, _arr);
        }

        var getDepartments = function (rootTreeObj,orgId,deptTreeId,endTreeId) {
            $.post('<c:url value="/hr/org/getAllDepartments"/>',{companyId:orgId},function (result) {
                if (result.success){
                    var rdata = result.rdata;
                    $.each(rdata,function (k,v) {
                        if (compareData(idMap,v.id)) {
                            v.checked=true;
                        }
                    });
                    var deptTreeObj = initTree(rdata,deptTreeId,function (event,treeId,treeNode) {
                        if (treeNode.checked) {
                            nameMap.push(treeNode.name);
                            idMap.push(treeNode.id);
                        } else {
                            nameMap = removeArrayData(nameMap,treeNode.name);
                            idMap = removeArrayData(idMap,treeNode.id);
                        }
                    },{check:{
                        enable: true,
                        chkStyle:"checkbox",
                        autoCheckTrigger: true
                    }});
                    rootTreeObj.departmentTreeObj = deptTreeObj;
                }
            },'json');
        }

        var getCompanies = function (rootTreeObj,compTreeId,deptTreeId,endTreeId) {
            $.ajax({
                async: false,
                type : 'POST' ,
                dataType:'json',
                url : '<c:url value="/hr/org/getAllCompanies"/>',
                success: function (result) {
                    if (result.success){
                        var treeObj = initTree(result.rdata, compTreeId, function (event, treeId, treeNode) {
                            $.fn.zTree.destroy(deptTreeId);
                            getDepartments(rootTreeObj,treeNode.id,deptTreeId,endTreeId);
                        });
                        var nodes = treeObj.getNodes();
                        treeObj.expandNode(nodes[0],true,false,false);
                        rootTreeObj.companyTreeObj = treeObj;
                    }
                }
            });
        }

        var encodeEmpData = function () {
            var objJson = new Array();
            b = false;
            $('#tlist').find('tr:gt(0)').each(function (k, v) {
                var serializeObj={};
                var array=$(v).find('form:eq(0)').serializeArray();
                $(array).each(function(){
                    if ((this.name=='endDate'||this.name=='preWarnDay'||this.name=='sufWarnDay'||this.name=='weight')&&$.trim(this.value)=='') {
                        layer.msg('表单序号【'+(k+1)+'】项有数据为空:计划截至日期，开始前预警天数，结束前预警天数，权重都为必填项');
                        b=true;
                        return false;
                    }
                    serializeObj[this.name]=this.value;
                });
                if (b){
                    return false;
                }
                objJson.push(serializeObj);
            });
            return JSON.stringify(objJson);
        }


        $('input.js_choose_emp').on('click',function () {
            currentObj = $(this);
            nameMap =currentObj.prop("title").split(',');
            idMap =currentObj.next('input').next('input').val().split(',');
            $.fn.zTree.destroy('empDepartmentTree');
            if(!empTreeObj.companyTreeObj) {
                getCompanies(empTreeObj, 'empCompanyTree', 'empDepartmentTree', 'empTree');
            }
            $('div.js_department_ps_div').modal('show');
        });

        $('.js_save_btn').on('click',function () {
            var name = nameMap.join(",");
            if (name.substring(0,1)==',') {
                name = name.substring(1,name.length);
            }
            var id = idMap.join(",");
            if (id.substring(0,1)==',') {
                id = id.substring(1,id.length);
            }
            currentObj.prop("title",name).prop("value",name.length>8?(name.substr(0,8)+'...'):name).next('input').prop("value",name).next('input').prop("value",id);
            $('div.js_department_ps_div').modal('hide');
        });

        $('#id_transmit').on('click',function () {
            var data = {};
            data.empListStr=encodeEmpData();
            data.projectId='${projectArchives.projectId}';
            if (!b) {
                layer.confirm("确定保存吗？", {icon: 3, title:'保存提示'}, function(){
                    layer.closeAll();
                    $.ajax({
                        url: '<c:url value="/project/plan/bput" />',
                        type: 'POST',
                        data:data,
                        dataType: 'json',
                        success: function(data){
                            layer.closeAll('loading');
                            data = JSON.parse(data);
                            if (data.code=="0"){
                                layer.msg(data.msg,{icon: 1});
                                window.setTimeout(function () {
                                    location.href='<c:url value="/project/plan/get"/>';
                                },1000);
                            } else {
                                layer.msg(data.msg,{icon: 2});
                            }
                        },
                        beforeSend:function () {layer.load(0,{shade: [0.3,'#000']});},
                        error: function(){layer.closeAll('loading');}
                    });
                });
            }
        });

    });

    function removeArrayData(ad,v) {
        var arr = [];
       $.each(ad, function(k,value) {
            if (value!=v){
                arr.push(value);
            }
        });
        return arr;
    }

    function compareData(ad,v) {
        var b = false;
        $.each(ad, function(k,value) {
            if (value==v){
                b=true;
            }
        });
        return b;
    }

</script>

<script type="text/javascript">
    $(function () {
        var empTreeObj2={
            companyTreeObj:null,
            departmentTreeObj:null,
            endTreeObj:null,
        };

        var initTree = function (_arr,treeId,callback,settings) {
            var setting = {callback:{
                onClick:function (event, treeId, treeNode) {
                    if ("function" == typeof(callback)){
                        callback(event,treeId,treeNode);
                    }
                },onCheck:function (event, treeId, treeNode) {
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
            if ( typeof settings == "object"){
                for(var k in settings){
                    setting[k]=settings[k];
                }
            }
            return $.fn.zTree.init($('#'+treeId), setting, _arr);
        }

        var getEmpByDepartment2=function(rootTreeObj, orgId, deptId, psnTreeId){
            $.post('<c:url value="/hr/getAllEmpListData"/>',{company:orgId,department:deptId},function (result) {
                if (result){
                    $.each(result,function (k,v) {
                        if (compareData(idMap,v.id)) {
                            v.checked=true;
                        }
                    });
                    console.log(result)
                    rootTreeObj.endTreeObj = initTree(result,psnTreeId,function (event, treeId, treeNode) {
                        if (treeNode.checked) {
                            nameMap.push(treeNode.name);
                            idMap.push(treeNode.id);
                        } else {
                            nameMap = removeArrayData(nameMap,treeNode.name);
                            idMap = removeArrayData(idMap,treeNode.id);
                        }
                    },{check:{
                        enable: true,
                        chkStyle:"checkbox",
                        autoCheckTrigger: true
                    }});
                }
            },'json');
        }

        var getDepartments2 = function (rootTreeObj,orgId,deptTreeId,endTreeId) {
            $.post('<c:url value="/hr/org/getAllDepartments"/>',{companyId:orgId},function (result) {
                if (result.success){
                    var deptTreeObj = initTree(result.rdata,deptTreeId,function (event,treeId,treeNode) {
                        $.fn.zTree.destroy(endTreeId);
                        getEmpByDepartment2(rootTreeObj,orgId,treeNode.id,endTreeId);
                    });
                    rootTreeObj.departmentTreeObj = deptTreeObj;
                }
            },'json');
        }

        var getCompanies2 = function (rootTreeObj,compTreeId,deptTreeId,endTreeId) {
            $.ajax({
                async: false,
                type : 'POST' ,
                dataType:'json',
                url : '<c:url value="/hr/org/getAllCompanies"/>',
                success: function (result) {
                    if (result.success){
                        var treeObj = initTree(result.rdata, compTreeId, function (event, treeId, treeNode) {
                            $.fn.zTree.destroy(deptTreeId);
                            $.fn.zTree.destroy(endTreeId);
                            getDepartments2(rootTreeObj,treeNode.id,deptTreeId,endTreeId);
                        });
                        var nodes = treeObj.getNodes();
                        treeObj.expandNode(nodes[0],true,false,false);
                        rootTreeObj.companyTreeObj = treeObj;
                    }
                }
            });
        }

        $('input.js_choose_emp2').on('click',function () {
            currentObj = $(this);
            nameMap =currentObj.prop("title").split(',');
            idMap =currentObj.next('input').next('input').val().split(',');
            $.fn.zTree.destroy('empDepartmentTree2');
            $.fn.zTree.destroy('empTree2');
            if(!empTreeObj2.companyTreeObj){
                getCompanies2(empTreeObj2,'empCompanyTree2','empDepartmentTree2','empTree2');
            }
            $('div.js_department_ps_div2').modal('show');
        });

    });

    $('.js_save_btn2').on('click',function () {
        var name = nameMap.join(",");
        if (name.substring(0,1)==',') {
            name = name.substring(1,name.length);
        }
        var id = idMap.join(",");
        if (id.substring(0,1)==',') {
            id = id.substring(1,id.length);
        }
        currentObj.prop("title",name).prop("value",name.length>8?(name.substr(0,8)+'...'):name).next('input').prop("value",name).next('input').prop("value",id);
        $('div.js_department_ps_div2').modal('hide');
    });

</script>
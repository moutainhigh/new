<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<link rel="stylesheet" type="text/css" href="<c:url value="/resources/admin/global/plugins/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" />"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/webuploader/css/webuploader.css" />">
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/hr/css/hr.css" />"/>


<style>
    .form-control{
        border: #d3d6db 1px solid;
        color: #999;
        border-radius: 2px !important;
        -webkit-border-radius: 2px !important;
        -moz-border-radius: 2px !important;
        -o-border-radius: 2px !important;
        -ms-border-radius: 2px !important;
        height:28px;
        padding: 0px 12px;
        line-height: 26px;
    }
    .table-bordered > tbody > tr > td{
        border: none;
        padding: 3px;
    }
    .js_tab_control>li>a{
        padding-left: 9px;
        padding-right: 9px;
    }
    .btn{
        padding: 3px 12px;
    }
</style>

<div class="panel panel-default">
    <div class="panel-heading">
        <h3 class="panel-title">培训活动管理</h3>
    </div>
    <div class="panel-body">
        <form  role="form" class="js_base_form">
            <input type="hidden" value="${entity.id}" name="id">
            <table class="table table-condensed table-bordered table-none">
                <tr>
                    <td>培训活动编码<input type="text" class="form-control " name="code" value="${empty entity.code ? code : entity.code}"></td>
                    <td>培训活动名称<input type="text" class="form-control " name="name" value="${entity.name}"></td>
                    <td>培训级别
                        <select class="form-control" name="lv">
                        <option value="0" <c:if test="${entity.lv eq 0}">selected="selected"</c:if>>集团级</option>
                        <option value="1" <c:if test="${entity.lv eq 1}">selected="selected"</c:if>>公司级</option>
                        <option value="2" <c:if test="${entity.lv eq 2}">selected="selected"</c:if>>部门级</option>
                    </select></td>
                    <td>培训年度
                        <select class="form-control" name="year">
                            <option value="${empty entity.year ? year : entity.year}">${empty entity.year ? year : entity.year}年</option>
                        </select>
                    </td>
                    <td>培训季度
                        <select class="form-control" name="quarter">
                            <c:forEach begin="1" end="4" var="q">
                                <option value="${q}" <c:if test="${(empty entity.quarter ? quarter : entity.quarter) eq q}">selected</c:if>>${q}季度</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>培训月份
                        <select class="form-control" name="month">
                            <c:forEach begin="1" end="12" var="m">
                                <option value="${m}" <c:if test="${(empty entity.month ? month : entity.month) eq m}">selected</c:if>>${m}月</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td>培训类别
                        <select class="form-control" name="type">
                            <option value="0" <c:if test="${entity.type eq 0}">selected="selected"</c:if>>内训</option>
                            <option value="1" <c:if test="${entity.type eq 1}">selected="selected"</c:if>>外训</option>
                        </select>
                    </td>
                    <td>培训方式<input type="text"  class="form-control " name="way" value="${entity.way}"></td>
                    <td>计划培训人数<input type="text"  class="form-control " name="planCount" value="${entity.planCount}"></td>
                    <td>实际培训人数<input type="text"  class="form-control " name="actualCount" value="${entity.actualCount}" readonly></td>
                </tr>
                <tr>
                    <td  colspan="2">培训内容<input type="text"  class="form-control  " name="content" value="${entity.content}"></td>
                    <td  colspan="3">培训目标<input type="text"  class="form-control " name="target" value="${entity.target}"></td>
                </tr>
                <tr>
                    <td  colspan="2">培训地点<input type="text"  class="form-control " name="addr" value="${entity.addr}"></td>
                    <td style="width: 25%;">培训部门
                        <div class="input-group">
                        <input type="hidden"  class="form-control " name="compLv1" value="${entity.compLv1}">
                        <input type="hidden"  class="form-control " name="compLv1Str" value="${entity.compLv1Str}">
                        <input type="hidden"  class="form-control " name="compLv2" value="${entity.compLv2}">
                        <input type="hidden"  class="form-control " name="compLv2Str" value="${entity.compLv2Str}">
                        <input type="hidden"  class="form-control " name="compLv3" value="${entity.compLv3}">
                        <input type="hidden"  class="form-control " name="compLv3Str" value="${entity.compLv3Str}">
                        <input type="hidden"  class="form-control " name="company" value="${entity.company}">
                        <input type="hidden"  class="form-control " name="department" value="${entity.department}">
                        <input type="text" readonly class="form-control " name="departmentStr" value="${entity.departmentStr}">
                        <span class="input-group-btn"><button type="button" class="btn btn-primary js_department_ps_choose">选择</button></span>
                        </div>
                    </td>
                    <td style="width: 25%;">培训负责人
                        <div class="input-group">
                        <input type="hidden"  class="form-control " name="principal" value="${entity.principal}">
                        <input type="text" readonly class="form-control " name="principalStr" value="${entity.principalStr}">
                        <span class="input-group-btn"><button type="button" class="btn btn-primary js_department_ps_choose">选择</button></span>
                        </div>
                    </td>
                    <td>培训负责人电话<input type="text"  class="form-control" name="mobile" value="${entity.mobile}"></td>
                </tr>
                <tr>
                    <td>计划费用总额<input type="text"  class="form-control " name="planCost" value="${entity.planCost}"></td>
                    <td>实际费用总额<input type="text"  class="form-control" name="actualCost" value="${entity.actualCost}">
                    </td>
                    <td>培训学时<input type="text"  class="form-control " name="timeCount" value="${entity.timeCount}"></td>
                    <td>培训开始时间<input type="text"  class="form-control " name="startTime" value="<fmt:formatDate value="${entity.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"></td>
                    <td>培训结束时间<input type="text"  class="form-control" name="endTime" value="<fmt:formatDate value="${entity.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"></td>
                </tr>
                <tr>
                    <td>培训学时合计<input type="text" readonly  class="form-control" name="totalTimeCount" value="${entity.totalTimeCount}"></td>
                    <td>讲师姓名<input type="text"  class="form-control" name="teacherName" value="${entity.teacherName}"></td>
                    <td>讲师电话<input type="text"  class="form-control " name="teacherMobile" value="${entity.teacherMobile}"></td>
                    <td>培训机构<input type="text"  class="form-control " name="teacherComp" value="${entity.teacherComp}"></td>
                </tr>
                <tr>
                    <td  colspan="4">备注<input type="text"  class="form-control " name="note" value="${entity.note}"></td>
                </tr>
            </table>
        </form>
    </div>
</div>
<div class="panel panel-default">
    <div class="panel-heading">
        <h3 class="panel-title">附件列表</h3>
    </div>
    <div class="panel-body">
        <div class="row">
            <div class="col-md-4">
                <div class="list-group js_file_list">
                    <c:forEach items="${attachList}" varStatus="step" var="file">
                        <div class="list-group-item" data-id="${file.id}"><a href="${fileUrl}${file.attachUrl}" target="_blank">${file.sourceName}</a><button class="btn btn-sm pull-right" data-id="${file.id}" data-trainid="${entity.id}">删除</button></div>
                    </c:forEach>
                </div>
                <c:if test="${not empty entity.id}">
                    <button class="btn blue hidden" type="button" data-action="saveFile" style="margin-bottom: 10px" >保存附件</button>
                </c:if>
            </div>
        </div>
        <div id="uploader">选择附件</div>
    </div>
    </div>


<div class="panel panel-default">
    <div class="panel-heading">
        <h3 class="panel-title">参训人员信息</h3>
    </div>
    <div class="panel-body">
        <form class="js_emp_list">
            <button type="button" class="btn green js_choose_emp">新增培训人员</button>
            <button type="button" class="btn green js_cost_recal">计算当前平均费用</button>
            <table class="table table-bordered table-striped js_emp_table">
                <thead>
                    <tr>
                        <th>序号</th>
                        <th>姓名</th>
                        <th>公司</th>
                        <th>部门</th>
                        <th>培训学时</th>
                        <th>培训费用</th>
                        <th>培训成绩</th>
                        <th>培训证书编码</th>
                        <th>培训证书名称</th>
                        <th>备注</th>
                        <th>操作</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${empList}" var="emp" varStatus="step">
                    <tr>
                        <td data-empid="${emp.empId}" data-id="${emp.id}" data-trainid="${entity.id}">${step.count}</td>
                        <td>${emp.empName}</td>
                        <td>${emp.companyStr}</td>
                        <td>${emp.departmentStr}</td>
                        <td data-name="studyTime">${emp.studyTime}</td>
                        <td data-name="cost">${emp.cost}</td>
                        <td data-name="score">${emp.score}</td>
                        <td data-name="certificateNo">${emp.certificateNo}</td>
                        <td data-name="certificateName">${emp.certificateName}</td>
                        <td data-name="empId">${emp.note}</td>
                        <td>
                            <button type="button" class="btn btn-sm" data-id="${emp.id}" data-trainid="${entity.id}" data-action="edit">编辑</button>
                            <button type="button" class="btn btn-sm" data-id="${emp.id}" data-trainid="${entity.id}" data-action="del">删除</button>
                        </td>
                    </tr>
                    </c:forEach>
                </tbody>
            </table>
        </form>
        <div class="js_oper_form">
            <button class="btn blue " type="button" data-action="save">保存</button>
            <a class="btn default "  href="${base}/hr/train/list">返回列表</a>
        </div>
    </div>
</div>

<div class="modal fade js_department_ps_div">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title">部门/人员</h4>
            </div>
            <div class="modal-body">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-md-4">
                            <label>公司</label>
                            <ul id="leaderCompanyTree" class="ztree"></ul>
                        </div>
                        <div class="col-md-4">
                            <label>部门</label>
                            <ul id="leaderDepartmentTree" class="ztree"></ul>
                        </div>
                        <div class="col-md-4">
                            <label>负责人</label>
                            <ul id="leaderTree" class="ztree"></ul>
                        </div>
                    </div>
                    <div class="row hidden">
                        <div class="col-md-4">
                            <label>公司</label>
                            <ul id="empCompanyTree" class="ztree"></ul>
                        </div>
                        <div class="col-md-4">
                            <label>部门</label>
                            <ul id="empDepartmentTree" class="ztree"></ul>
                        </div>
                        <div class="col-md-4">
                            <label>人员</label>
                            <ul id="empTree" class="ztree"></ul>
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


<script type="text/javascript" src="<c:url value="/resources/webuploader/js/webuploader.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/plugIn/zTree/js/jquery.ztree.excheck.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.zh-CN.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/jquery-validation/js/jquery.validate.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/jquery-validation/js/additional-methods.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/jquery-validation/js/messages_zh.min.js" />"></script>
<script>

    $(document).ready(function () {

        var fileLi = '<div class="list-group-item" ><a href="" target="_blank"></a><button class="btn btn-sm pull-right">删除</button></div>';

        var globalHtml =['<tr>',
            '    <td>1<input name="empId" type="hidden" value="1"></td>',
            '    <td></td>',
            '    <td></td>',
            '    <td></td>',
            '    <td data-name="studyTime"><input name="studyTime" type="text" value=""></td>',
            '    <td data-name="cost"><input name="cost" type="text" value=""></td>',
            '    <td data-name="score"><input name="score" type="text" value=""></td>',
            '    <td data-name="certificateNo"><input name="certificateNo" type="text" value=""></td>',
            '    <td data-name="certificateName"><input name="certificateName" type="text" value=""></td>',
            '    <td data-name="empId"><input name="empId" type="text" value=""></td>',
            '    <td><button type="button" class="btn btn-sm" data-action="del">删除</button></td>',
            '</tr>'].join("");


        var fileUrl = '${fileUrl}';
        var trainId = '${entity.id}';
        var leaderCompanyTreeObj;
        var empCompanyTreeObj;
        var leaderDepartmentTreeObj;
        var empDepartmentTreeObj;
        var empTreeObj;
        var leaderTreeObj;
        var currOperType;

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

        var getEmpByDepartment=function(orgId, deptId,psnTreeObj,psnTreeId){
            $.post('<c:url value="/hr/getAllEmpListData"/>',{company:orgId,department:deptId},function (result) {
                if (result){
                    var obj = null;
                    if (currOperType == 'emp'){
                        obj={check:{
                            enable: true,
                            chkStyle:"checkbox",
                            autoCheckTrigger: true
                        }};
                    }else{
                        obj = {};
                    }
                    psnTreeObj = initTree(result,psnTreeId,function (event, treeId, treeNode) {
                        psnTreeObj.checkNode(treeNode);
                    },obj);
                    if (currOperType == "leader"){
                        leaderTreeObj = psnTreeObj;
                    }else if (currOperType == "emp"){
                        empTreeObj = psnTreeObj;
                    }
                }
            },'json');
        }

        var getDepartments = function (orgId,deptTreeObj,psnTreeObj,deptTreeId,psnTreeId) {
            $.post('<c:url value="/hr/org/getAllDepartments"/>',{companyId:orgId},function (result) {
                if (result.success){
                    deptTreeObj = initTree(result.rdata,deptTreeId,function (event,treeId,treeNode) {
                        $.fn.zTree.destroy(psnTreeId);
                        getEmpByDepartment(orgId,treeNode.id,psnTreeObj,psnTreeId);
                    });
                    if (currOperType == "leader"){
                        leaderDepartmentTreeObj = deptTreeObj;
                    }else if (currOperType == "emp"){
                        empDepartmentTreeObj = deptTreeObj;
                    }
                }
            },'json');
        }

        var getCompanies = function (treeObj,deptTreeObj,psnTreeObj,compTreeId,deptTreeId,psnTreeId) {
            if(!treeObj){
                $.post('<c:url value="/hr/org/getAllCompanies"/>', function (result) {
                    if (result.success) {
                        treeObj = initTree(result.rdata, compTreeId, function (event, treeId, treeNode) {
                            $.fn.zTree.destroy(deptTreeId);
                            $.fn.zTree.destroy(psnTreeId);
                            getDepartments(treeNode.id,deptTreeObj,psnTreeObj,deptTreeId,psnTreeId);
                        });
                        var nodes = treeObj.getNodes();
                        treeObj.expandNode(nodes[0],true,false,false);
                        if (currOperType == "leader"){
                            leaderCompanyTreeObj = treeObj;
                        }else if (currOperType == "emp"){
                            empCompanyTreeObj = treeObj;
                        }
                    }
                }, 'json');
            }
        }

        /**
         * 选择 部门 负责人
         */
        $('button.js_department_ps_choose').on('click',function () {
            currOperType = 'leader';
            if(!leaderCompanyTreeObj){
                getCompanies(leaderCompanyTreeObj,leaderDepartmentTreeObj,leaderTreeObj,'leaderCompanyTree','leaderDepartmentTree','leaderTree');
            }
            $('div.js_department_ps_div').find('div.row:eq(0)').removeClass('hidden');
            $('div.js_department_ps_div').find('div.row:eq(1)').addClass('hidden');
            $('div.js_department_ps_div').modal('show');
        });

        /**
         * 选择培训人员
         */
        $('button.js_choose_emp').on('click',function () {
            if (!$('input[name=timeCount]').val()){
                layer.msg("请先填写培训学时");
                return;
            }
            if (!$('input[name=actualCost]').val()){
                layer.msg("实际费用总额");
                return;
            }
            currOperType = 'emp';
            if(!empCompanyTreeObj){
                getCompanies(empCompanyTreeObj,empDepartmentTreeObj,empTreeObj,'empCompanyTree','empDepartmentTree','empTree');
            }
            $('div.js_department_ps_div').find('div.row:eq(0)').addClass('hidden');
            $('div.js_department_ps_div').find('div.row:eq(1)').removeClass('hidden');
            $('div.js_department_ps_div').modal('show');
        });

        /**
         * 树保存
         */
        $('button.js_save_btn').on('click',function () {
            if (currOperType == "leader"){//选择部门负责人
                var cnodes = leaderCompanyTreeObj.getSelectedNodes();
                if(cnodes.length==1) {
                    $('input[name=company]').val(cnodes[0].id);
                    if(cnodes[0].level==1){
                        var p1 = cnodes[0].getParentNode();
                        $('input[name=compLv1]').val(p1.id);
                        $('input[name=compLv1Str]').val(p1.name);
                        $('input[name=compLv2]').val(cnodes[0].id);
                        $('input[name=compLv2Str]').val(cnodes[0].name);
                    }else  if(cnodes[0].level==2){
                        var p1 = cnodes[0].getParentNode();
                        $('input[name=compLv2]').val(p1.id);
                        $('input[name=compLv2Str]').val(p1.name);
                        var p2 = p1.getParentNode();
                        $('input[name=compLv1]').val(p2.id);
                        $('input[name=compLv1Str]').val(p2.name);
                    }else  if(cnodes[0].level==3){
                        var p1 = cnodes[0].getParentNode();
                        $('input[name=compLv3]').val(p1.id);
                        $('input[name=compLv3Str]').val(p1.name);
                        var p2 = p1.getParentNode();
                        $('input[name=compLv2]').val(p2.id);
                        $('input[name=compLv2Str]').val(p2.name);
                        var p3 = p2.getParentNode();
                        $('input[name=compLv1]').val(p3.id);
                        $('input[name=compLv1Str]').val(p3.name);
                    }

                    var dnodes = leaderDepartmentTreeObj.getSelectedNodes();
                    if(dnodes.length==1) {
                        $('input[name=departmentStr]').val(dnodes[0].name);
                        $('input[name=department]').val(dnodes[0].id);
                        var enodes = leaderTreeObj.getSelectedNodes();
                        if(enodes.length==1) {
                            $('input[name=principalStr]').val(enodes[0].name);
                            $('input[name=principal]').val(enodes[0].id);
                            $('div.js_department_ps_div').modal('hide');
                        }else{
                            layer.msg("请选择负责人");
                        }
                    }else{
                        layer.msg("请选择培训部门");
                    }
                }else{
                    layer.msg("请选择公司");
                }
            }else if (currOperType == "emp"){//选择需要培训的人员
                var cnodes = empCompanyTreeObj.getSelectedNodes();
                if(cnodes.length==1) {
                    var dnodes = empDepartmentTreeObj.getSelectedNodes();
                    if(dnodes.length==1) {
                        var enodes = empTreeObj.getCheckedNodes(true);
                        if(enodes.length>0) {
                            var _timeCount = $('input[name=timeCount]').val();
                            var _empids = new Array();
                            $('table.js_emp_table').find('tr:gt(0)').each(function (i, e) {
                                _empids.push($(e).find('td:eq(0)').data('empid'));
                            });
                            $(enodes).each(function (k, v) {
                                if(_empids.length==0||_empids.indexOf(v.id)==-1){
                                    var html = $(globalHtml);
                                    html.find('td:eq(0)').data('empid',v.id).data('cid',cnodes[0].id).data('did',dnodes[0].id).data('edit',true).data('jobSequence',v.jobSequence).data('job',v.job);
                                    html.find('td:eq(1)').text(v.name);
                                    html.find('td:eq(2)').text(cnodes[0].name);
                                    html.find('td:eq(3)').text(dnodes[0].name);
                                    html.find('td:eq(4)').find('input').val(_timeCount);
                                    $('table.js_emp_table tbody').append(html);
                                }
                                empTreeObj.checkNode(v);
                            });
                            $('input[name=actualCount]').val($('table.js_emp_table tbody').find('tr').length);
                            buildTrIndex();
                            countStudyTime();
                            $('div.js_department_ps_div').modal('hide');
                        }else{
                            layer.msg("请选择人员");
                        }
                    }else{
                        layer.msg("请选择部门");
                    }
                }else{
                    layer.msg("请选择公司");
                }
            }
        });

        var buildTrIndex = function () {
            $('table.js_emp_table tbody').find('tr').each(function (k, v) {
                $(v).find('td:eq(0)').text(k+1);
            });
        }

        /**
         * 删除人员
         */
        var removeEmp = function(_this, _id, _trainId){
            if(_id){
                var _index = layer.confirm("您确定要删除该行数据?此操作将立即生效！",function () {
                    layer.close(_index);
                    var _tr = $(_this).parents('tr');
                    $.post('<c:url value="/hr/train/delOneTrainEmp"/>', {id:_id,trainId:_trainId}, function (result) {
                        resp = false;
                        if (result.success){
                            layer.msg("操作成功",{icon:1,time:1000},function () {
                                $('input[name=actualCount]').val(_tr.siblings().length);
                                _tr.remove();
                                buildTrIndex();
                            });
                        }else{
                            layer.alert(result.rmsg);
                        }
                    },'json');
                });
            }else{
                var _index = layer.confirm("您确定要删除?",function () {
                    layer.close(_index);
                    var _tr = $(_this).parents('tr');
                    $('input[name=actualCount]').val(_tr.siblings().length);
                    _tr.remove();
                    buildTrIndex();
                });
            }
        }

        /**
         *修改人员信息
         */
        var toUpdateEmp = function(_this, _id, _trainId){
            $(_this).text('保存');
            $(_this).data('action','save');
            $(_this).parents('tr').find('td').each(function (k,v) {
                var _td = $(v);
                if(k==0){
                    _td.data('edit',true);
                }
                var _name = _td.data('name');
                if(_name){
                    var _input = $('<input>');
                    _input.attr('name',_name);
                    _input.attr('value',_td.text());
                    _td.html(_input);
                }
            });
        }
        

        var updateEmp = function (_this) {
            var _arr = encodeEmpData(_this);
            $.post('<c:url value="/hr/train/putTrainEmp"/>', _arr[0], function (result) {
                resp = false;
                if (result.success){
                    var _id =result.rdata;
                    layer.msg("操作成功",{icon:1,time:1000},function () {
                        $(_this).parents('tr').find('td:eq(0)').data('edit',false);
                        $(_this).parents('tr').find('input').each(function () {
                            var txt = $(this).val();
                            $(this).parent('td').html(txt);
                        });
                        $(_this).text('编辑').data('action','edit');
                    });
                }
            },'json');
        }

        var encodeEmpData = function (obj) {
            var _empArr = new Array();
            var tr=null;
            if(obj==undefined){
                tr = $('table.js_emp_table').find('tr:gt(0)');
            }else{
                tr = $(obj).parents('tr');
            }
            tr.each(function (k, v) {
                var td0 = $(v).find('td:eq(0)');
                var edit = td0.data('edit');
                if (edit){
                    var obj = {};
                    var _id = td0.data('id');
                    if (_id){
                        obj.id = _id;
                        obj.trainId = td0.data('trainid');
                    }else{
                        obj.empId = td0.data('empid');
                        obj.company =td0.data('cid');
                        obj.department =td0.data('did');
                        obj.jobSequence =td0.data('jobSequence');
                        obj.job =td0.data('job');
                    }
                    obj.empName = $(v).find('td:eq(1)').text();
                    obj.companyStr = $(v).find('td:eq(2) ').text();
                    obj.departmentStr = $(v).find('td:eq(3)').text();
                    obj.studyTime=$(v).find('td:eq(4) input').val();
                    obj.cost=$(v).find('td:eq(5) input').val();
                    obj.score=$(v).find('td:eq(6) input').val();
                    obj.certificateNo=$(v).find('td:eq(7) input').val();
                    obj.certificateName=$(v).find('td:eq(8) input').val();
                    obj.note=$(v).find('td:eq(9) input').val();
                    _empArr.push(obj);
                }
            });
            return _empArr;
        }

        /**
         * 点击操作按钮
         */
        $('table.js_emp_table').on('click',"button",function () {
            var _this = this;
            var _id = $(this).data('id');
            var _trainId = $(this).data('trainid');
            var _action = $(this).data('action');
           if(_action == 'edit'){
               toUpdateEmp(_this);
           }else if (_action == 'del'){
               removeEmp(_this,_id,_trainId)
           }else if (_action == 'save'){
               updateEmp(_this);
           }
        });


        $('input[name=startTime]').datetimepicker({
            todayBtn : "linked",
            autoclose : true,
            language: 'zh-CN',
            format : "yyyy-mm-dd hh:ii:ss",
            todayHighlight : true
        }).on('changeDate',function(e){
            var time = new Date(e.date.getTime() + (e.date.getTimezoneOffset() * 60000));
            $('input[name=endTime]').datetimepicker('setStartDate',time);
        });

        $('input[name=endTime]').datetimepicker({
            todayBtn : "linked",
            autoclose : true,
            language: 'zh-CN',
            format : "yyyy-mm-dd hh:ii:ss",
            todayHighlight : true
        }).on('changeDate',function(e){
            var time = new Date(e.date.getTime() + (e.date.getTimezoneOffset() * 60000));
            $('input[name=startTime]').datetimepicker('setEndDate',time);
        });

        var countStudyTime = function () {
            var totalTime=0;
            $('table.js_emp_table').find('td[data-name="studyTime"]').each(function (k, v) {
                var e = $(v);
                if(e.find('input').length){
                    totalTime+=parseFloat(e.children('input').val());
                }else{
                    totalTime+=parseFloat(e.text());
                }
            });
            $('input[name=totalTimeCount]').val(totalTime);
        }

        $('table.js_emp_table').on('blur','input[name=studyTime]',function () {
            countStudyTime();
        });


        $('button.js_cost_recal').on('click',function () {
            var _len = $('table.js_emp_table tr').length-1;
            var _actualCost = $('input[name=actualCost]').val();
            if(_len>0){
                if($.isNumeric(_actualCost)){
                    var cost = (_actualCost/_len).toFixed(2);
                    $('table.js_emp_table tr:gt(0)').each(function (k, v) {
                        var _tr = $(v);
                        var edit = _tr.find('td:eq(0)').data('edit');
                        if(edit==undefined||edit==false){
                            _tr.find('button[data-action=edit]').click();
                        }
                        _tr.find('td:eq(5)').find('input').val(cost);
                    });
                }
            }
        });


        $('table.js_emp_table').on('change','input[name=cost]',function () {
            var total = 0;
            $('input[name=cost]').each(function (k, v) {
                var count = parseFloat($(v).val());
                if(!isNaN(count)){
                    total+=count;
                }
            });
            $('td[data-name=cost]').each(function (k, v) {
                var count = parseFloat($(v).text());
                if(!isNaN(count)){
                    total+=count;
                }
            });
            $('input[name=actualCost]').val(total);
        });



        // 初始化Web Uploader
        var single = WebUploader.create({
            fileVal:'imgFile',
            auto: true,
            compress: false,
            swf: '<c:url value="/resources/webuploader/js/Uploader.swf" />',
            server: '<c:url value="/hr/train/uploadFile" />',
            pick: {
                id: '#uploader',
                label: '上传活动资料',
                multiple:true
            },
            fileSingleSizeLimit: 5 * 1024 * 1024//,    // 5M
        });

        single.on( 'uploadSuccess', function( file ,json) {
            var item = $(fileLi);
            item.find('a').attr('href',fileUrl+json.rdata).text(file.name);
            item.data('url',json.rdata);
            $('div.js_file_list').append(item);
        });

        single.on( 'uploadError', function( file ) {
            alert("附件上传失败！");
        });
        single.on( 'error', function( e) {
            if (e =="F_EXCEED_SIZE"){
                alert("附件大小超过最大限制！");
            }
        });

        $('div.js_file_list').on('click','button',function () {
            var _this = this;
            var _id = $(this).data('id');
            var _trainId = $(this).data('trainid');
            if(_id){
                var _index = layer.confirm("您确定要删除该附件?此操作将立即生效！",function () {
                    layer.close(_index);
                    $.post('<c:url value="/hr/train/delAttachment"/>', {id:_id,trainId:_trainId}, function (result) {
                        resp = false;
                        if (result.success){
                            layer.msg("操作成功",{icon:1,time:1000},function () {
                                $(_this).parent('div').remove();
                            });
                        }else{
                            layer.alert(result.rmsg);
                        }
                    },'json');
                });
            }else{
                var _index = layer.confirm("您确定要删除?",function () {
                    layer.close(_index);
                    $(_this).parent('div').remove();
                });
            }

        });

        var validateForm = $('form.js_base_form');
        validateForm.validate({
            errorElement : 'span',
            errorClass : 'help-block help-block-error',
            rules : {
                code : {
                    required : true
                },name: {
                    required : true
                },type: {
                    required : true
                },way: {
                    required : true
                },planCount: {
                    required : true,
                    digits:true
                },actualCount: {
                    required : true,
                    digits:true
                },content: {
                    required : true
                },departmentStr: {
                    required : true
                },principalStr: {
                    required : true
                },planCost: {
                    number:true
                },actualCost: {
                    required : true,
                    number:true
                },timeCount: {
                    required : true,
                    number:true
                },startTime: {
                    required : true
                },endTime : {
                    required : true
                }
            },
            messages : {

            },
            highlight : function(element) {
                $(element).parents('td').addClass('has-error');
            },
            unhighlight : function(element) {
                $(element).parents('td').removeClass('has-error');
            },
            success : function(label) {
                label.remove();
            },errorPlacement: function(error, element) {
                element.parents('td').append(error);
            }
        });
        
        var saveEmp = function () {
            var _arr = encodeEmpData();
            if(_arr.length==0){
                return true;
            }
            var flag = false;
            var data = {};
            data.trainId = trainId;
            data.empListStr=JSON.stringify(_arr);
            $.ajax({
                type:"post",
                url:'<c:url value="/hr/train/saveTrainEmp"/>',
                dataType:"json",
                async:false,
                data:data,
                success: function (result) {
                    if (result.success){
                        flag = true;
                    }
                }
            });
            return flag;
        }

        var saveFile = function () {
            var files = new Array();
            $('div.js_file_list div').each(function (k, v) {
                if(!$(v).data('id')){
                    var obj = {};
                    obj.attachUrl=$(v).data('url');
                    obj.sourceName=$(v).find('a').text();
                    files.push(obj);
                }
            });
            if(files.length==0){
                return true;
            }
            var flag = false;
            var data = {};
            data.trainId = trainId;
            data.attachmentListStr=JSON.stringify(files);
            $.ajax({
                type:"post",
                url:'<c:url value="/hr/train/saveAttachment"/>',
                dataType:"json",
                async:false,
                data:data,
                success: function (result) {
                if (result.success){
                    flag = true;
                }
                }
            });
            return flag;
        }

        var resp = false;
        $('div.js_oper_form button').on('click',function () {
            var action = $(this).data('action');
            if(action == 'save'){
                if(validateForm.valid()){
                    var data =  validateForm.serialize();
                    if(!resp){
                        resp = true;
                        if (trainId){
                            if (saveFile()&&saveEmp()){
                                data+='&id='+trainId;
                                $.post('<c:url value="/hr/train/putOne"/>', data, function (result) {
                                    resp = false;
                                    if (result.success){
                                        layer.msg("操作成功",{icon:1,time:1000},function () {
                                            window.location.reload();
                                        });
                                    }
                                },'json');
                            }else{
                                layer.msg("图片保存失败",{icon:2,time:1000});
                            }
                        }else {
                            var _empArr = encodeEmpData();
                            data += ("&empListStr="+JSON.stringify(_empArr));
                            var files = new Array();
                            $('div.js_file_list div').each(function (k, v) {
                                var obj = {};
                                obj.attachUrl=$(v).data('url');
                                obj.sourceName=$(v).find('a').text();
                                files.push(obj);
                            });
                            data +=("&attachmentListStr="+JSON.stringify(files));
                            $.post('<c:url value="/hr/train/postOne"/>', data, function (result) {
                                resp = false;
                                if (result.success){
                                    var _id =result.rdata;
                                    layer.msg("操作成功",{icon:1,time:1000},function () {
                                        window.location.reload();
                                    });
                                }
                            },'json');
                        }
                    }
                }
            }
        });

    });

</script>

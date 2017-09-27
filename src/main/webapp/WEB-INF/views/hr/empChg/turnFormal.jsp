<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<link rel="stylesheet" type="text/css" href="<c:url value="/resources/admin/global/plugins/bootstrap-datepicker/css/bootstrap-datepicker3.min.css" />"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/hr/css/hr.css" />"/>


<div class="portlet box blue-hoki">
    <div class="portlet-title">
        <div class="caption">
            <i class="fa fa-gift"></i>
            人员转正
        </div>
    </div>
    <div class="portlet-body">
        <div class="row">
            <div class="col-md-6 ">
                <form  role="form" class="js_base_form">
                    <div class="form-body">
                        <div class="row">
                            <div class="col-md-6 ">
                                <div class="form-group">
                                    <label>单位</label>
                                    <input type="text" class="form-control" readonly name="company">
                                </div>
                            </div>
                            <div class="col-md-6 ">
                                <div class="form-group">
                                    <label>部门</label>
                                    <input type="text" class="form-control" readonly name="department">
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6 ">
                                <div class="form-group">
                                    <label>选择人员</label>
                                    <div class="input-group">
                                        <input type="text" class="form-control" name="empName" readonly>
                                        <span class="input-group-btn"><button type="button" class="btn btn-primary js_choose_emp">选择</button></span>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-6 ">
                                <div class="form-group">
                                    <label>转正日期</label>
                                    <input type="text" class="form-control" name="chgTime">
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6 ">
                                <div class="form-group">
                                    <label>转正类型</label>
                                    <select class="form-control" name="chgType">
                                        <option value="">请选择</option>
                                        <option value="1">到期转正</option>
                                        <option value="2">提前转正</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>
                    <button type="button" class="btn green js_save_chg" data-action="save">保存</button>
                    <button type="button" class="btn default" data-action="cancel">返回列表</button>
                </form>
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


<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.zh-CN.js" />"></script>


<script>

    $(document).ready(function () {

        var empTreeObj={
            companyTreeObj:null,
            departmentTreeObj:null,
            endTreeObj:null,
        };


        var emp={lastEmpJobId:null,empId:null,company:null,department:null,chgTime:null,chgType:null};
        var errorMsg={
            lastEmpJobId:"工作记录不能为空",
            empId:"请选择人员",
            company:"公司不能为空",
            department:"部门不能为空",
            chgTime:"转正日期不能为空",
            chgType:"转正类型不能为空"
        }

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

        var getEmpByDepartment=function(rootTreeObj, orgId, deptId, psnTreeId){
            $.post('<c:url value="/hr/getNotFormalEmpData"/>',{company:orgId,department:deptId},function (result) {
                if (result) {
                    var setting = {
                        data: {
                            key: {
                                name: 'empName'
                            }
                        }, view: {
                            expandSpeed: ''
                        }
                    }
                    rootTreeObj.endTreeObj = $.fn.zTree.init($('#' + psnTreeId), setting, result);
                }
            },'json');
        }

        var getDepartments = function (rootTreeObj,orgId,deptTreeId,endTreeId) {
            $.post('<c:url value="/hr/org/getAllDepartments"/>',{companyId:orgId},function (result) {
                if (result.success){
                    var deptTreeObj = initTree(result.rdata,deptTreeId,function (event,treeId,treeNode) {
                        $.fn.zTree.destroy(endTreeId);
                        if(endTreeId=='empTree'){
                            getEmpByDepartment(rootTreeObj,orgId,treeNode.id,endTreeId);
                        }
                    });
                    rootTreeObj.departmentTreeObj = deptTreeObj;
                }
            },'json');
        }

        var getCompanies = function (rootTreeObj,compTreeId,deptTreeId,endTreeId) {
            $.post('<c:url value="/hr/org/getAllCompanies"/>', function (result) {
                if (result.success) {
                    var treeObj = initTree(result.rdata, compTreeId, function (event, treeId, treeNode) {
                        $.fn.zTree.destroy(deptTreeId);
                        $.fn.zTree.destroy(endTreeId);
                        getDepartments(rootTreeObj,treeNode.id,deptTreeId,endTreeId);
                    });
                    var nodes = treeObj.getNodes();
                    treeObj.expandNode(nodes[0],true,false,false);
                    rootTreeObj.companyTreeObj = treeObj;
                }
            }, 'json');
        }

        $('button.js_choose_emp').on('click',function () {
            if(!empTreeObj.companyTreeObj){
                getCompanies(empTreeObj,'empCompanyTree','empDepartmentTree','empTree');
            }
            $('div.js_department_ps_div').modal('show');
        });


        /**
         * 保存岗位信息
         */
        $('button.js_save_btn').on('click',function () {
            var cnodes = empTreeObj.companyTreeObj.getSelectedNodes();
            if(cnodes.length==1) {
                $('input[name=company]').val(cnodes[0].name);
                emp.company=cnodes[0].id;
                var dnodes = empTreeObj.departmentTreeObj.getSelectedNodes();
                if(dnodes.length==1) {
                    $('input[name=department]').val(dnodes[0].name);
                    emp.department=dnodes[0].id;
                    var enodes = empTreeObj.endTreeObj.getSelectedNodes();
                    if(enodes.length==1) {
                        $('input[name=empName]').val(enodes[0].empName);
                        emp.empId=enodes[0].id;
                        emp.lastEmpJobId=enodes[0].lastEmpJobId;
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
        });



        $('input[name=chgTime]').datepicker({
            todayBtn : "linked",
            autoclose : true,
            language: 'zh-CN',
            format : "yyyy-mm-dd",
            todayHighlight : true,
            endDate : new Date()
        });

        var checkObj = function (emp) {
            var flag = true;
            for(var e in emp){
                if (e == 'description'){
                    continue;
                }
                if(!emp[e]){
                    layer.msg(errorMsg[e],{time:800});
                    flag = false;
                    break;
                }
            }
            return flag;
        }

        var _index=null;
        $('button.js_save_chg').on('click',function () {
            emp.chgTime = $('input[name=chgTime]').val();
            emp.chgType = $('select[name=chgType]').val();
            if(checkObj(emp)){
                if (null==_index){
                    _index = layer.load(2);
                    $.post('<c:url value="/hr/chg/postTurnFormal"/>',emp, function (result) {
                        if (result.success) {
                            layer.close(_index);
                            layer.msg('操作成功',{time:800},function () {
                                window.location.reload();
                            });
                        }else{
                            layer.close(_index);
                            layer.msg('操作失败：'+result.rmsg,{time:800});
                        }
                    }, 'json');
                }
            }
        });

    });

</script>
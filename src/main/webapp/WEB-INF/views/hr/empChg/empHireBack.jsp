<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<link rel="stylesheet" type="text/css" href="<c:url value="/resources/admin/global/plugins/bootstrap-datepicker/css/bootstrap-datepicker3.min.css" />"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/hr/css/hr.css" />"/>


<div class="portlet box blue-hoki">
    <div class="portlet-title">
        <div class="caption">
            <i class="fa fa-gift"></i>
            人员返聘
        </div>
    </div>
    <div class="portlet-body">
        <div class="row">
            <div class="col-md-12 ">
                <form  role="form" class="js_base_form">
                    <div class="form-body">
                        <table class="table table-condensed table-bordered table25 table-none">
                            <tr>
                                <td>
                                    <label>原单位</label>
                                    <input type="text" class="form-control" readonly name="companyTxt">
                                </td>
                                <td>
                                    <label>原部门</label>
                                    <input type="text" class="form-control" readonly name="departmentTxt">
                                </td>

                                <td>
                                    <label>人员</label>
                                    <div class="input-group">
                                        <input type="text" class="form-control" name="empName" readonly>
                                        <span class="input-group-btn"><button type="button" class="btn btn-primary js_choose_emp">选择</button></span>
                                    </div>
                                </td>

                                <td>
                                    <label>员工编号 </label>
                                    <input type="text" class="form-control " name="empNo" value="${jobInfo.empNo}">
                                </td>
                            </tr>
                            <tr>
                                <td>人员类别
                                    <select class="form-control" name="empGroup">
                                        <option value="15" >返聘人员</option>
                                    </select>
                                </td>
                                <td>
                                    单位
                                    <div>
                                        <input type="hidden"  class="form-control" name="company" value="${jobInfo.company}">
                                        <input type="text"  class="form-control" name="companyText" readonly value="${jobInfo.companyName}">
                                    </div>
                                </td>
                                <td>所在部门
                                    <div class="input-group">
                                        <input type="hidden"  class="form-control" name="department" value="${jobInfo.department}">
                                        <input type="text"  class="form-control" name="departmentText" readonly value="${jobInfo.departmentName}">
                                        <span class="input-group-btn"><button type="button" class="btn btn-primary js_choose_department">选择</button></span>
                                    </div>
                                </td>
                                <td>岗位
                                    <select class="form-control" name="job">
                                        <option value="">请选择</option>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <td>剩余编制<input type="text" class="form-control " name="plait" value="" readonly></td>
                                <td>岗位序列
                                    <select class="form-control" name="jobSequence">
                                        <option value="">请选择</option>
                                        <c:forEach items="${jobSequence}" var="data">
                                            <option value="${data.dictDataValue}">${data.dictDataKey}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                                <td>招聘来源
                                    <select class="form-control" name="recruitmentSource">
                                        <option value="">请选择</option>
                                        <c:forEach items="${recruitmentSource}" var="data">
                                            <option value="${data.dictDataValue}">${data.dictDataKey}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                                <td>职级
                                    <select class="form-control" name="dutyLevel">
                                        <option value="">请选择</option>
                                        <c:forEach items="${dutyLevel}" var="data">
                                            <option value="${data.dictDataValue}">${data.dictDataKey}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <td>到职日期<input type="text" class="form-control " name="startTime" value=""></td>
                                <td>是否在岗
                                    <select class="form-control" name="onGuard">
                                        <option value="1" selected="selected">是</option>
                                    </select>
                                </td>
                                <td>是否转正
                                    <select class="form-control" name="isFormal">
                                        <option value="0">否</option>
                                        <option value="1">是</option>
                                    </select>
                                </td>
                                <td>工作地点<input type="text" class="form-control " name="workAddress" value="${jobInfo.workAddress}"></td>
                            </tr>
                            <tr>
                                <td role="turnFormalDate"class="hidden">转正日期<input type="text" class="form-control js_date_picker" name="turnFormalDate" value=""></td>
                                <td role="formalType" class="hidden">转正类型
                                    <select class="form-control" name="formalType">
                                        <option value="1">到期转正</option>
                                        <option value="2" >提前转正</option>
                                    </select>
                                </td>
                            </tr>
                        </table>
                    </div>
                    <button type="button" class="btn green js_save_chg" data-action="save">保存</button>
                </form>
            </div>
        </div>
    </div>
</div>

<div class="modal fade js_tree_div">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title">单位/部门</h4>
            </div>
            <div class="modal-body">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-md-6">
                            <label>单位</label>
                            <ul id="companyTree" class="ztree"></ul></div>
                        <div class="col-md-6">
                            <label>部门</label>
                            <ul id="departmentTree" class="ztree"></ul></div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default js_cancel_btn" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary js_comp_dept_save_btn">保存</button>
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

<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/jquery-validation/js/jquery.validate.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/jquery-validation/js/additional-methods.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/jquery-validation/js/messages_zh.min.js" />"></script>


<script>

    $(document).ready(function () {

        var empTreeObj={
            companyTreeObj:null,
            departmentTreeObj:null,
            endTreeObj:null,
        };

        var compDeptTreeObj={
            companyTreeObj:null,
            departmentTreeObj:null
        };

        var emp={empId:null,company:null,department:null};
        var errorMsg={
            empId:"返聘人员不能为空",
            company:"原单位不能为空",
            department:"原部门不能为空"
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

        var getJobs = function (departmentId) {
            var date = new Date($('input[name=startTime]').val());
            $.post('<c:url value="/hr/org/getAllJobWithPlait"/>',{departmentId:departmentId,year:date.getFullYear(),month:date.getMonth()+1},function (result) {
                var html='<option value="">请选择</option>';
                if (result.success){
                    $('input[name=plaitCount]').val('');
                    var data = result.rdata;
                    var html='<option value="" data-count="">请选择</option>';
                    for(var i = 0; i<data.length; i++){
                        html+='<option value="'+data[i].id+'" data-count="'+data[i].count+'">'+data[i].name+'</option>';
                    }
                }else{
                    layer.msg("获取岗位列表失败");
                }
                $('select[name=job]').html(html);
            },'json');
        }

        var getEmpByDepartment=function(rootTreeObj, orgId, deptId, psnTreeId){
            $.post('<c:url value="/hr/getAllOutDutyEmpListData"/>',{company:orgId,department:deptId},function (result) {
                if (result){
                    var setting = {
                        data:{
                        key:{
                            name:'empName'
                        }
                    },view:{
                        expandSpeed:''
                    }}
                    rootTreeObj.endTreeObj = $.fn.zTree.init($('#'+psnTreeId), setting, result);
                }
            },'json');
        }

        var getDepartments = function (rootTreeObj,orgId,deptTreeId,endTreeId) {
            $.post('<c:url value="/hr/org/getAllDepartments"/>',{companyId:orgId},function (result) {
                if (result.success){
                    var deptTreeObj = initTree(result.rdata,deptTreeId,function (event,treeId,treeNode) {
                        if (null!=endTreeId){
                            $.fn.zTree.destroy(endTreeId);
                        }
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
                        if (endTreeId!=null){
                            $.fn.zTree.destroy(endTreeId);
                        }
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
         * 选择 返聘人员
         */
        $('button.js_save_btn').on('click',function () {
            var cnodes = empTreeObj.companyTreeObj.getSelectedNodes();
            if(cnodes.length==1) {
                $('input[name=companyTxt]').val(cnodes[0].name);
                emp.company=cnodes[0].id;
                var dnodes = empTreeObj.departmentTreeObj.getSelectedNodes();
                if(dnodes.length==1) {
                    $('input[name=departmentTxt]').val(dnodes[0].name);
                    emp.department=dnodes[0].id;
                    var enodes = empTreeObj.endTreeObj.getSelectedNodes();
                    if(enodes.length==1) {
                        $('input[name=empName]').val(enodes[0].empName);
                        emp.empId=enodes[0].id;
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


        $('div.js_tree_div').on('click','button.js_comp_dept_save_btn',function () {
            var nodes = compDeptTreeObj.companyTreeObj.getSelectedNodes();
            var flag = false;
            if(nodes.length==1){
                var node = nodes[0];
                $('input[name=company]').val(node.id).next().val(node.name);
                flag = false;
                var dnodes =  compDeptTreeObj.departmentTreeObj.getSelectedNodes();
                if(dnodes.length==1) {
                    var node = dnodes[0];
                    if (!node.isParent) {
                        $('input[name=department]').val(node.id).next().val(node.name);
                        getJobs(node.id);
                        flag = true;
                    }
                }
                if (flag){
                    $('div.js_tree_div').modal('hide');
                }else {
                    layer.msg('请选择部门');
                }
            }
        });

        /**
         * 选择部门
         */
        $('button.js_choose_department').on('click',function () {
            var date = $('input[name=startTime]').val();
            if(date != ''){
                if(!compDeptTreeObj.companyTreeObj){
                    getCompanies(compDeptTreeObj,'companyTree','departmentTree',null);
                }
                $('div.js_tree_div').modal('show');
            }else{
                layer.msg('请先选定到职日期');
            }
        });

        $('select[name=job]').on('change',function () {
            $('input[name=plait]').val($(this).find('option:selected').data('count'));
        });


        var validateForm = $('form.js_base_form');
        validateForm.validate({
            errorElement : 'span',
            errorClass : 'help-block help-block-error',
            rules : {
                empNo : {
                    required : true
                },empGroup : {
                    required : true
                },companyText : {
                    required : true
                },departmentText : {
                    required : true
                },job : {
                    required : true
                },jobSequence : {
                    required : true
                },dutyLevel : {
                    required : true
                },turnFormalDate : {
                    required : true
                },startTime : {
                    required : true
                }
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

        $('select[name=isFormal]').on('change',function () {
            var value = $(this).val();
            if(value==1){
                $('td[role=turnFormalDate]').removeClass('hidden');
                $('td[role=formalType]').removeClass('hidden').find('select').attr('name','formalType');
            }else{
                $('td[role=turnFormalDate]').addClass('hidden').find('input').val('');
                $('td[role=formalType]').addClass('hidden').find('select').removeAttr('name');
            }
        });

        var timeInitArgs = {
            todayBtn : "linked",
            autoclose : true,
            language: 'zh-CN',
            format : "yyyy-mm-dd",
            todayHighlight : true,
            endDate : new Date()
        };

        $('input.js_date_picker').datepicker(timeInitArgs);
        $('input[name=startTime]').datepicker( {
            todayBtn : "linked",
            autoclose : true,
            language: 'zh-CN',
            format : "yyyy-mm-dd",
            todayHighlight : true,
            <sec:authorize access="!hasAnyRole('hr_dev','hrkylr')"> startDate:' ${monthFirstDay}',</sec:authorize>
            endDate : new Date()
        });

        var checkObj = function (emp) {
            var flag = true;
            for(var e in emp){
                if(!emp[e]){
                    layer.msg(errorMsg[e],{time:800});
                    flag = false;
                    break;
                }
            }
            return flag;
        }

        var _index;
        $('button.js_save_chg').on('click',function () {
            if(checkObj(emp)&&validateForm.valid()){
                _index = layer.load(2);
                var data = $('form.js_base_form').serialize();
                $.post('<c:url value="/hr/chg/postHireBack"/>',data+'&empId='+emp.empId, function (result) {
                    layer.close(_index);
                    if (result.success) {
                        layer.msg('操作成功',{time:800},function () {
                            window.location.reload();
                        });
                    }else{
                        layer.msg('操作失败：'+result.rmsg,{time:800});
                    }
                }, 'json');
            }
        });

    });

</script>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<link rel="stylesheet" type="text/css" href="<c:url value="/resources/admin/global/plugins/bootstrap-datepicker/css/bootstrap-datepicker3.min.css" />"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/hr/css/hr.css" />"/>

<style>
    .datepicker-dropdown{
        z-index: 9999;
    }
</style>
<div class="portlet box blue-hoki">
    <div class="portlet-title">
        <div class="caption">
            <i class="fa fa-gift"></i>
            出差管理
        </div>
    </div>
    <div class="portlet-body">
        <div class="row">
            <div class="col-md-8 ">
                <form  role="form" class="js_base_form">
                    <div class="form-body">
                        <div class="row">
                            <div class="col-md-4 ">
                                <div class="form-group">
                                    <label>公司：</label>
                                    <input type="text" class="form-control" name="companyName" value="${entity.companyName}" readonly>
                                </div>
                            </div>
                            <div class="col-md-4 ">
                                <div class="form-group">
                                    <label>部门：</label>
                                    <input type="text" class="form-control" name="departmentName" value="${entity.departmentName}" readonly>
                                </div>
                            </div>
                            <div class="col-md-4 ">
                                <div class="form-group">
                                    <label>人员：</label>
                                    <div class="input-group">
                                        <input type="text" class="form-control" name="empName"  value="${entity.empName}" readonly>
                                        <span class="input-group-btn">
                                        <button class="btn btn-primary js_choose_emp" type="button">选择</button>
                                    </span>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-4 ">
                                <div class="form-group">
                                    <label>开始日期：</label>
                                    <div class="input-group">
                                        <input type="text" class="form-control" name="startDate"  value="<fmt:formatDate value="${entity.startDate}" pattern="yyyy-MM-dd"/>">
                                        <span class="input-group-btn">
                                        <button class="btn default" type="button">
                                            <i class="fa fa-calendar"></i>
                                        </button>
                                    </span>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-4 ">
                                <div class="form-group">
                                    <label>结束日期：</label>
                                    <div class="input-group">
                                        <input type="text" class="form-control " name="endDate"  value="<fmt:formatDate value="${entity.endDate}" pattern="yyyy-MM-dd"/>">
                                        <span class="input-group-btn">
                                        <button class="btn default" type="button">
                                            <i class="fa fa-calendar"></i>
                                        </button>
                                    </span>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-4 ">
                                <div class="form-group">
                                    <label>出差地点：</label>
                                    <input type="text" class="form-control" name="addr" value="${entity.addr}">
                                </div>
                            </div>

                        </div>
                        <div class="row">
                            <div class="col-md-4 ">
                                <div class="form-group">
                                    <label>出差类型：</label>
                                    <select class="form-control" name="travelType">
                                        <option value="0" <c:if test="${entity.travelType eq 0}">selected</c:if>>本地出差</option>
                                        <option value="1" <c:if test="${entity.travelType eq 1}">selected</c:if>>外地出差</option>
                                    </select>
                                </div>
                            </div>
                            <div class="col-md-4 ">
                                <div class="form-group">
                                    <label>出差时长（天数）：</label>
                                    <input type="text" class="form-control" name="duration" value="${entity.duration}">
                                </div>
                            </div>
                            <div class="col-md-4 ">
                                <div class="form-group">
                                    <label>单据日期：</label>
                                    <div class="input-group">
                                        <input type="text" class="form-control " name="billDate"  value="<fmt:formatDate value="${entity.billDate}" pattern="yyyy-MM-dd"/>">
                                        <span class="input-group-btn">
                                        <button class="btn default" type="button">
                                            <i class="fa fa-calendar"></i>
                                        </button>
                                    </span>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6 ">
                                <div class="form-group">
                                    <label>出差原因：</label>
                                    <input type="text" class="form-control" name="reason" value="${entity.reason}">
                                </div>
                            </div>
                        </div>
                    </div>
                    <button type="button" class="btn green js_save_btn" data-action="save">保存</button>
                    <a type="button" class="btn default" href="<c:url value="/hr/atte/travelList"/>">返回列表</a>
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
                <button type="button" class="btn btn-primary js_save_emp_btn">保存</button>
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


        var startDate = '${entity.startDate}', endDate='${entity.endDate}';
        if (startDate){
            startDate = new Date(startDate);
        }else{
            startDate=null;
        }
        if (endDate){
            endDate = new Date(endDate);
        }else{
            endDate=null;
        }
        $('input[name=startDate]').datepicker({
            todayBtn : "linked",
            autoclose : true,
            language: 'zh-CN',
            format : "yyyy-mm-dd",
            todayHighlight : true
        }).on('changeDate',function(e){
            startDate = e.date.getTime();
            $('input[name=endDate]').datepicker('setStartDate',new Date(startDate));
            if (null!=startDate && null!=endDate){
               $('input[name=duration]').val(Math.floor((endDate-startDate)/((24*3600*1000)))+1);
            }
        }).parent(".input-group").on("click", ".input-group-btn", function (t) {
            t.preventDefault(), $(this).prev('input').datepicker("show")
        });

        $('input[name=endDate]').datepicker({
            todayBtn : "linked",
            autoclose : true,
            language: 'zh-CN',
            format : "yyyy-mm-dd"
        }).on('changeDate',function(e){
            endDate = e.date.getTime();
            $('input[name=startDate]').datepicker('setEndDate',new Date(endDate));
            if (null!=startDate && null!=endDate){
                $('input[name=duration]').val(Math.floor((endDate-startDate)/((24*3600*1000)))+1);
            }
        }).parent(".input-group").on("click", ".input-group-btn", function (t) {
            t.preventDefault(), $(this).prev('input').datepicker("show")
        });

        $('input[name=billDate]').datepicker({
            todayBtn : "linked",
            autoclose : true,
            language: 'zh-CN',
            format : "yyyy-mm-dd"
        }).parent(".input-group").on("click", ".input-group-btn", function (t) {
            t.preventDefault(), $(this).prev('input').datepicker("show")
        });


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
            $.post('<c:url value="/hr/getAllEmpListData"/>',{company:orgId,department:deptId},function (result) {
                if (result){
                    rootTreeObj.endTreeObj = initTree(result,psnTreeId,function (event, treeId, treeNode) {

                    });
                }
            },'json');
        }

        var getDepartments = function (rootTreeObj,orgId,deptTreeId,endTreeId) {
            $.post('<c:url value="/hr/org/getAllDepartments"/>',{companyId:orgId},function (result) {
                if (result.success){
                    var deptTreeObj = initTree(result.rdata,deptTreeId,function (event,treeId,treeNode) {
                        $.fn.zTree.destroy(endTreeId);
                        getEmpByDepartment(rootTreeObj,orgId,treeNode.id,endTreeId);
                    });
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
                            $.fn.zTree.destroy(endTreeId);
                            getDepartments(rootTreeObj,treeNode.id,deptTreeId,endTreeId);
                        });
                        var nodes = treeObj.getNodes();
                        treeObj.expandNode(nodes[0],true,false,false);
                        rootTreeObj.companyTreeObj = treeObj;
                    }
                }
            });
        }

        $('button.js_choose_emp').on('click',function () {
            if(!empTreeObj.companyTreeObj){
                getCompanies(empTreeObj,'empCompanyTree','empDepartmentTree','empTree');
            }
            $('div.js_department_ps_div').modal('show');
        });

        /**
         * 保存人员
         */
        $('button.js_save_emp_btn').on('click',function () {
            var cnodes = empTreeObj.companyTreeObj.getSelectedNodes();
            if(cnodes.length==1) {
                $('input[name=companyName]').val(cnodes[0].name);
                dataObj.company = cnodes[0].id;
                var dnodes = empTreeObj.departmentTreeObj.getSelectedNodes();
                if(dnodes.length==1) {
                    $('input[name=departmentName]').val(dnodes[0].name);
                    dataObj.department = dnodes[0].id;
                    var enodes = empTreeObj.endTreeObj.getSelectedNodes();
                    if(enodes.length==1) {
                        $('input[name=empName]').val(enodes[0].name);
                        dataObj.empId=enodes[0].id;
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

        var validateForm = $('form.js_base_form');
        validateForm.validate({
            errorElement : 'span',
            errorClass : 'help-block help-block-error',
            rules : {
                company : {
                    required : true
                },department : {
                    required : true
                },empName : {
                    required : true
                },startDate: {
                    required : true
                },endDate : {
                    required : true
                },address : {
                    required : true
                },travelType : {
                    required : true
                },billDate : {
                    required : true
                },duration: {
                    required : true,
                    digits:true
                }
            },
            highlight : function(element) {
                $(element).parents('div.form-group').addClass('has-error');
            },
            unhighlight : function(element) {
                $(element).parents('div.form-group').removeClass('has-error');
            },
            success : function(label) {
                label.remove();
            },errorPlacement: function(error, element) {
                element.parents('div.form-group').append(error);
            }
        });

        var dataObj={id:'${entity.id}',empId:'${entity.empId}',company:'${company}',department:'${department}'};
        var _index=null;
        $('button.js_save_btn').on('click',function () {
            if(validateForm.valid()&&null==_index){
                var objArr = validateForm.serializeArray();
                for(var e in objArr){
                    var  obj = objArr[e];
                    dataObj[obj.name] = obj.value;
                }
//                _index = layer.load(2);
                $.post('<c:url value="/hr/atte/postTravelEdit"/>', dataObj, function (result) {
                    if (result.success) {
                        layer.close(_index);
                        layer.msg('操作成功',{time:800},function () {
                            window.location.reload();
                        });
                    }else{
                        layer.close(_index);
                        layer.msg('操作失败：'+result.rmsg,{time:2000});
                    }
                }, 'json');
            }
        });

    });

</script>
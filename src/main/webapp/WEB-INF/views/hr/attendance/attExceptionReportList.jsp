<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/hr/css/hr.css" />"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/admin/global/plugins/bootstrap-datepicker/css/bootstrap-datepicker3.min.css" />"/>



<div class="portlet light">
    <div class="portlet-title">
        <div class="caption">
            <i class="icon-layers font-green"></i>
            <span class="caption-subject font-green sbold uppercase">考勤异常列表</span>
        </div>
    </div>
    <div class="portlet-body">
        <div class="row">
            <div class="col-md-12 table-responsive">
                <div class="actions form-inline">
                    <div class="form-group">
                        公司：<div class="input-group">
                                <input type="text" readonly class="form-control input-sm" name="companyName" value="">
                                </div>
                    </div>
                    <div class="form-group">
                        部门：<div class="input-group">
                                <input type="text" readonly class="form-control input-sm" name="departmentName" value="">
                                </div>
                    </div>
                    <div class="form-group">
                        人员：<div class="input-group">
                                <input type="text" readonly class="form-control input-sm" name="empName" value="">
                                <span class="input-group-btn"><button type="button" class="btn btn-sm btn-primary js_choose_emp">选择</button></span>
                            </div>
                    </div>
                    <div class="form-group">
                        日期：
                        <input name="startDate" class="form-control input-inline input-sm" type="text" value="<fmt:formatDate value="${startDate}" pattern="yyyy-MM-dd"/>" readonly/>
                        - <input name="endDate" class="form-control input-inline input-sm" type="text" value="<fmt:formatDate value="${endDate}" pattern="yyyy-MM-dd"/>" readonly/>
                    </div>
                    <div class="form-group">
                        <button class="btn btn-sm green js_search"><i class="fa fa-search"></i> 搜索 </button>
                    </div>
                </div>

                <table class="table table-width table-striped table-bordered table-advance table-hover table-checkable " id="datatable_ajax">
                    <thead>
                    <tr role="row" class="heading">
                        <th>
                            <label class="mt-checkbox mt-checkbox-single mt-checkbox-outline">
                                <input type="checkbox" class="group-checkable">
                                <span></span>
                            </label>
                        </th>
                        <th>公司</th>
                        <th>部门</th>
                        <th>姓名</th>
                        <th>日期</th>
                        <th>迟到（分）</th>
                        <th>早退（分）</th>
                        <th>旷工（分）</th>
                    </tr>
                    </thead>
                    <tbody></tbody>
                </table>
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


<script src="<c:url value="/resources/admin/global/scripts/datatable.js" />"></script>
<script src="<c:url value="/resources/admin/global/plugins/datatables/datatables.min.js" />"></script>
<script src="<c:url value="/resources/admin/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.zh-CN.js" />"></script>
<script>

    var TableDatatablesAjax = function() {
        var  e = function(args) {
            var table = new Datatable;
            for(var i in args){
                table.setAjaxParam(i, args[i]);
            }
            table.init({
                src: $("#datatable_ajax"),
                loadingMessage: "Loading...",
                dataTable: {
                    bStateSave: !0,
                    lengthMenu: [[10, 20, 50, 100], [10, 20, 50, 100]],
                    pageLength: 10,
                    ajax: {
                        url: "<c:url value="/hr/atte/attExceptionReportListData"/>"
                    },
                    order: [[1, "asc"]],
                    columns:[
                        {"data":"id","render" : function(data, type, full, meta ){
                            return '<label class="mt-checkbox mt-checkbox-single mt-checkbox-outline"> <input type="checkbox" class="checkboxes" value="'+data+'"> <span></span> </label>';
                        }},
                        {"data":"companyName","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;
                        }},
                        {"data":"departmentName","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;
                        }},
                        {"data":"empName","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;
                        }},
                        {"data":"workDay","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;
                        }},
                        {"data":"realWorkDay","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;
                        }},
                        {"data":"late","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;
                        }},
                        {"data":"early","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;
                        }},
                        {"data":"absent","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;
                        }},
                        {"data":"noIn","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;
                        }},
                        {"data":"noOut","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;
                        }},
                        {"data":"lateCount","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;
                        }},
                        {"data":"earlyCount","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;
                        }}
                    ],
                    "initComplete":function () {

                    }
                }
            });
            return table;
        };
        return {
            init: function(args) {
                return e(args);
            }
        }
    } ();
    $(document).ready(function() {
        var dataObj = {};
        var table = null;

        /**
         * 搜索
         */
        $('button.js_search').on('click',function () {
            var param = {startDate:$('input[name=startDate]').val(),endDate:$('input[name=endDate]').val()}
            var count = 0;
            if (dataObj.empId){
                param.empId = dataObj.empId;
                count++;
            }
            if (dataObj.department){
                param.department = dataObj.department;
                count++;
            }
            if (dataObj.company){
                param.company = dataObj.company;
                count++;
            }
            if (count==0){
                layer.msg('请选择一个公司、部门、人员');
                return;
            }
            if (table == null){
                table = TableDatatablesAjax.init(param);
            }else{
                table.clearAjaxParams();
                for(var e in param){
                    table.setAjaxParam(e, param[e]);
                }
                table.getDataTable().ajax.reload();
            }
        });

        var startDate=null,endDate =null;
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
        });

        var empTreeObj={
            companyTreeObj:null,
            departmentTreeObj:null,
            endTreeObj:null
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
            dataObj = {};
            var cnodes = empTreeObj.companyTreeObj.getSelectedNodes();
            if(cnodes.length==1) {
                $('input[name=companyName]').val(cnodes[0].name);
                dataObj.company = cnodes[0].id;
            }
            if (empTreeObj.departmentTreeObj){
                var dnodes = empTreeObj.departmentTreeObj.getSelectedNodes();
                if(dnodes.length==1) {
                    $('input[name=departmentName]').val(dnodes[0].name);
                    dataObj.department = dnodes[0].id;
                }
            }

            if(empTreeObj.endTreeObj){
                var enodes = empTreeObj.endTreeObj.getSelectedNodes();
                if(enodes.length==1) {
                    $('input[name=empName]').val(enodes[0].name);
                    dataObj.empId=enodes[0].id;
                }
            }
            $('div.js_department_ps_div').modal('hide');
        });





    });
</script>
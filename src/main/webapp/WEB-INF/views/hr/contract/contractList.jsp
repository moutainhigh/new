<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/hr/css/hr.css" />"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/admin/global/plugins/bootstrap-datepicker/css/bootstrap-datepicker3.min.css" />"/>


<div class="portlet light">
    <div class="portlet-title">
        <div class="caption">
            <i class="icon-layers font-green"></i>
            <span class="caption-subject font-green sbold uppercase">合同列表</span>
        </div>
        <div class="actions form-inline">
            <button class="btn green js_oper_action" data-url="<c:url value="/hr/contract/register/"/>"><i class="fa fa-plus-circle"></i>签订</button>
            <button class="btn blue js_oper_action" data-url="<c:url value="/hr/contract/change/"/>"><i class="fa fa-search"></i> 变更</button>
            <button class="btn blue js_oper_action"  data-url="<c:url value="/hr/contract/renewed/"/>"><i class="fa fa-search"></i> 续签</button>
            <button class="btn yellow js_oper_action"  data-url="<c:url value="/hr/contract/release/"/>"><i class="fa fa-search"></i> 解除</button>
            <button class="btn red js_oper_action"  data-url="<c:url value="/hr/contract/end/"/>"><i class="fa fa-search"></i> 终止</button>
            <button class="btn btn-primary js_oper_action"  data-url="<c:url value="/hr/contract/replenish/"/>"><i class="fa fa-history"></i> 补录</button>
        </div>
    </div>
    <div class="portlet-body">
        <div class="row">
            <div class="col-md-12">
                <div class="actions form-inline">
                    <div class="form-group">员工编号：<input name="empNo" class="form-control input-inline input-sm" type="text" value=""/></div>
                    <div class="form-group">姓名：<input name="empName" class="form-control input-inline input-sm" type="text"/></div>
                    <div class="form-group">部门：<select name="department" class="form-control input-sm">
                        <option value="">请选择</option>
                        <c:forEach items="${departmentList}" var="dept">
                            <option value="${dept.id}">${dept.name}</option>
                        </c:forEach>
                    </select>
                    </div>
                    <div class="form-group">业务类型：<select name="operType" class="form-control input-sm">
                        <option value="">全部</option>
                        <option value="1">签订</option>
                        <option value="2">变更</option>
                        <option value="3">续签</option>
                        <option value="4">解除</option>
                        <option value="5">终止</option>
                    </select>
                    </div>
                </div>
                <div class="actions form-inline">
                    <div class="form-group">
                        合同开始日期：<input name="startTime1" class="form-control input-inline input-sm js_date" type="text" value=""/>- <input name="startTime2" class="form-control input-inline input-sm js_date" type="text"/>
                    </div>
                    <div class="form-group">
                        合同结束日期：<input name="endTime1" class="form-control input-inline input-sm js_date" type="text" value=""/>- <input name="endTime2" class="form-control input-inline input-sm js_date" type="text"/>
                    </div>
                    <div class="form-group">
                        <button class="btn btn-sm green js_search"><i class="fa fa-search"></i> 搜索 </button>
                    </div>
                </div>
                <table class="table table-width table-striped table-bordered table-advance table-hover table-checkable th-weight" id="datatable_ajax">
                    <thead>
                    <tr role="row">
                        <th width="1%">序号</th>
                        <th width="3%">员工编号</th>
                        <th width="5%">员工姓名</th>
                        <th width="5%">公司名称</th>
                        <th width="5%">部门名称</th>
                        <th width="5%">岗位名称</th>
                        <th width="5%">到职日期</th>
                        <th width="2%">操作</th>
                    </tr>
                    </thead>
                </table>
            </div>
        </div>
    </div>
</div>

<div style="display: none">
<c:forEach items="${contractCompany}" var="data">
    <span data-id="${data.dictDataValue}">${data.dictDataKey}</span>
</c:forEach>
</div>

<div class="portlet light">
    <div class="portlet-body">
        <div class="row">
            <div class="col-md-12">
                <div class="table-responsive">
                  <table class="table table-width table-striped table-bordered table-advance table-hover table-checkable mar_t10 th-weight"  id="contract_datatable_ajax">
                    <thead>
                      <tr role="row">
                        <th width="1%">序号</th>
                        <th width="4%">合同编码</th>
                        <th width="2%">业务类型</th>
                        <th width="3%">合同期限类型</th>
                        <th width="3%">合同期限</th>
                        <th width="5%">合同开始日期</th>
                        <th width="5%">合同结束日期</th>
                        <th width="5%">人员所属公司</th>
                        <th width="5%">合同主体单位</th>
                        <th width="5%">操作时间</th>
                        <th width="5%">操作</th>
                      </tr>
                    </thead>
                  </table>
                </div>
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

    var contractCompanyObj= {};
    <c:forEach items="${contractCompany}" var="data">
        contractCompanyObj[${data.dictDataValue}]='${data.dictDataKey}';
    </c:forEach>

    var TableDatatablesAjax = function() {
        var  e = function() {
            var table = new Datatable;
            table.init({
                src: $("#datatable_ajax"),
                loadingMessage: "Loading...",
                dataTable: {
                    "dom": "<'table-responsive't><'row'<'col-md-8 col-sm-12'pli><'col-md-4 col-sm-12'>>",
                    bStateSave: !0,
                    lengthMenu: [[10, 20, 50, 100], [10, 20, 50, 100]],
                    pageLength: 10,
                    ajax: {
                        url: "<c:url value="/hr/contract/getContractEmpListData"/>"
                    },
                    order: [[1, "asc"]],
                    columns:[
                        {"data":"empId","render" : function(data, type, full, meta ){
                            return '<label class="mt-radio"> <input type="radio" value="'+data+'" name="checkPsn"> <span></span> </label>';
                        }},
                        {"data":"empNo","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;
                        }},
                        {"data":"empName"},
                        {"data":"companyName","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;
                        }},
                        {"data":"departmentName","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;
                        }},
                        {"data":"jobName","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;
                        }},
                        {"data":"arrivalDate","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;
                        }},
                        {"data":"empId","render" : function(data, type, full, meta ){
                            return  '<button class="btn btn-sm btn-outline js_contract_detail" data-id='+data+'><i class="fa fa-search"></i> 查看</button>';
                        }}
                    ]
                }
            });
            return table;
        };
        return {
            init: function() {
                return e();
            }
        }
    } ();

    var contractDatatablesAjax = function() {
        var  e = function(args) {
            var table = new Datatable;
            table.setAjaxParam("empId", args);
            table.init({
                src: $("#contract_datatable_ajax"),
                loadingMessage: "Loading...",
                dataTable: {
                    "dom": "<'table-responsive't><'row'<'col-md-8 col-sm-12'pli><'col-md-4 col-sm-12'>>",
                    bStateSave: !0,
                    lengthMenu: [[10, 20, 50, 100], [10, 20, 50, 100]],
                    pageLength: 10,
                    ajax: {
                        url: "<c:url value="/hr/contract/getContractListData"/>"
                    },order: [[1, "asc"]],
                    columns:[
                        {"data":"id", "targets": 0},
                        {"data":"contractNo","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;
                        }},
                        {"data":"operTypeStr"},
                        {"data":"periodTypeStr"},
                        {"data":"periodCount","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;
                        }},
                        {"data":"contractStartTime","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;
                        }},
                        {"data":"contractEndTime","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;
                        }},
                        {"data":"companyName","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;
                        }},
                        {"data":"contractCompany","render" : function(data, type, full, meta ){
                            var str = contractCompanyObj[data];
                            if(str==undefined){
                                return '';
                            }else{
                                return str;
                            }
                        }},
                        {"data":"createtime"},
                        {"data":"id","render" : function(data, type, full, meta ){
                            var html  =  '<a class="btn btn-sm btn-outline" href="/hr/contract/show/'+full.empId+'/'+data+'" ><i class="fa fa-search"></i> 查看</a>';
                            if(full.status){
                                html+='<a class="btn btn-sm blue " href="/hr/contract/edit/'+full.empId+'/'+data+'" target="_blank"><i class="fa fa-edit"></i> 编辑</a>';
                            }
                            return html;
                        }}
                    ],fnDrawCallback: function(){
                        var api = this.api();
                        var startIndex= api.context[0]._iDisplayStart;//获取到本页开始的条数
                        api.column(0).nodes().each(function(cell, i) {
                            cell.innerHTML = startIndex + i + 1;
                        });
                    },initComplete:function () {

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
    }();

    $(document).ready(function() {

        var timeInitArgs = {
            todayBtn : "linked",
            autoclose : true,
            language: 'zh-CN',
            format : "yyyy-mm-dd",
            todayHighlight : true
        };

        $('input.js_date').datepicker(timeInitArgs);

        /**
         * 查看合同
         */
        var contractDatatables = null;
        $('#datatable_ajax').on('click','button.js_contract_detail',function () {
            var _id = $(this).data('id');
            if (contractDatatables == null){
                contractDatatables = contractDatatablesAjax.init(_id);
            }else{
                contractDatatables.setAjaxParam("empId", _id);
                contractDatatables.getDataTable().ajax.reload();
            }
            $(this).parents('tr').find('td:eq(0)').find('input').prop('checked',true);
        });

        var table = TableDatatablesAjax.init();
        $('button.js_search').on('click',function () {
            if(null!=contractDatatables){
                contractDatatables.clearAjaxParams()
                contractDatatables.getDataTable().draw();
            }
            table.clearAjaxParams();
            table.setAjaxParam("empNo", $('input[name=empNo]').val());
            table.setAjaxParam("empName", $('input[name=empName]').val());
            var department = $('select[name=department]').val();
            if (department){
                table.setAjaxParam("department", department);
            }
            var startTime1 = $('input[name=startTime1]').val();
            var startTime2 = $('input[name=startTime2]').val();
            if (startTime1&&startTime2){
                table.setAjaxParam("startTime1", startTime1);
                table.setAjaxParam("startTime2", startTime2);
            }
            var endTime1 = $('input[name=endTime1]').val();
            var endTime2 = $('input[name=endTime2]').val();
            if (startTime1&&startTime2){
                table.setAjaxParam("endTime1", endTime1);
                table.setAjaxParam("endTime2", endTime2);
            }
            var operType = $('select[name=operType]').val();
            if (operType){
                table.setAjaxParam("operType", operType);
            }
            table.getDataTable().ajax.reload();
        });



        $('button.js_download').on('click',function () {
            var _arr = new Array();
            $('input.checkboxes').each(function (k, v) {
                if($(v).prop('checked')){
                    _arr.push($(v).val());
                }
            });
            if(_arr.length>0){
                $('#downloadForm').remove();
                var _form = $('<form>');
                _form.attr('action','<c:url value="/hr/downloadEmpInfo"/>');
                _form.attr('id','downloadForm');
                _form.attr('method','post');
                _form.attr('target', '_blank');
                var _input = $('<input type="text" name="ids" />');
                _input.val(JSON.stringify(_arr));
                _form.append(_input);
                $(document).find('body').append(_form);
                $('#downloadForm').submit();
            }else{
                layer.msg('请选择要导出的人员');
            }
        });

        var empCompanyTreeObj;
        var empDepartmentTreeObj;
        var empTreeObj;

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
            $.post('<c:url value="/hr/getAllOutDutyEmpListData"/>',{company:orgId,department:deptId},function (result) {
                if (result){
                    psnTreeObj = initTree(result,psnTreeId,function (event, treeId, treeNode) {

                    });
                    empTreeObj = psnTreeObj;
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
                    empDepartmentTreeObj = deptTreeObj;
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
                        empCompanyTreeObj = treeObj;
                    }
                }, 'json');
            }
        }


        $('button.js_oper_action').on('click',function () {
            var _id = $('input[name=checkPsn]:checked').val();
            if(_id){
                window.location.href= $(this).data('url')+_id;
            }else{
                layer.msg('请选择一个员工');
            }
        });


    });
</script>
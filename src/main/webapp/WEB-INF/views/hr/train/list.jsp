<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/hr/css/hr.css" />"/>


<div class="portlet light" id="form_wizard_1">
    <div class="portlet-title">
        <div class="caption">
            <i class="icon-layers font-green"></i>
            <span class="caption-subject font-green sbold uppercase">培训活动列表</span>
        </div>
        <div class="actions form-inline">
            <a href="<c:url value="/hr/train/edit"/>"><button class="btn sbold blue js_emp_add"><i class="fa fa-plus-circle"></i>新增</button></a>
        </div>
    </div>
    <div class="portlet-body">
        <div class="row">
            <div class="col-md-12">
                <div class="actions form-inline">
                    <div class="form-group">培训活动名称：<input name="name" class="form-control input-inline input-sm" type="text" value=""/></div>
                    <%--<div class="form-group">--%>
                        <%--培训部门：<div class="input-group">--%>
                        <%--<input name="department" class="form-control input-inline input-sm" type="hidden"/>--%>
                        <%--<input type="text" readonly class="form-control input-sm" name="departmentStr" value="">--%>
                        <%--<span class="input-group-btn"><button type="button" class="btn btn-sm btn-primary js_comp_dept_choose">选择</button></span>--%>
                       <%--</div>--%>
                    <%--</div>--%>
                    <div class="form-group">
                        培训类别：<select name="type" class="form-control input-sm">
                                    <option value="">请选择</option>
                                    <option value="0">内训</option>
                                    <option value="1">外训</option>
                                </select>
                    </div>
                    <div class="form-group"><button class="btn btn-sm green js_search"><i class="fa fa-search"></i> 搜索 </button></div>
                </div>
                <table class="table table-width table-striped table-bordered table-advance table-hover th-weight" id="datatable_ajax">
                    <thead>
                    <tr role="row">
                        <th width="3%">序号</th>
                        <th width="5%">培训活动编码</th>
                        <th width="5%">培训活动名称</th>
                        <th width="5%">培训级别</th>
                        <th width="5%">培训类别</th>
                        <th width="5%">培训方式</th>
                        <th width="5%">培训内容</th>
                        <th width="5%">培训开始时间</th>
                        <th width="5%">培训截止时间</th>
                        <th width="5%">操作</th>
                    </tr>
                    </thead>
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
                <h4 class="modal-title">部门/人员</h4>
            </div>
            <div class="modal-body">
                <div class="container-fluid">
                    <div class="row">
                        <%--<div class="col-md-4">--%>
                            <%--<label>公司</label>--%>
                            <%--<ul id="empCompanyTree" class="ztree"></ul>--%>
                        <%--</div>--%>
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


<script src="<c:url value="/resources/admin/global/scripts/datatable.js" />"></script>
<script src="<c:url value="/resources/admin/global/plugins/datatables/datatables.min.js" />"></script>
<script src="<c:url value="/resources/admin/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js" />"></script>

<script>

    var TableDatatablesAjax = function() {
        var  e = function() {
            var table = new Datatable;
            table.init({
                src: $("#datatable_ajax"),
                loadingMessage: "Loading...",
                dataTable: {
                    bStateSave: !0,
                    lengthMenu: [[10, 20, 50, 100], [10, 20, 50, 100]],
                    pageLength: 10,
                    ajax: {
                        url: "<c:url value="/hr/train/getListData"/>"
                    },
                    order: [[1, "asc"]],
                    columns:[
                        {"data":"id", "targets": 0},
                        {"data":"code","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;
                        }},
                        {"data":"name"},
                        {"data":"lvStr","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;
                        }},
                        {"data":"typeStr","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;
                        }},
                        {"data":"way","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;
                        }},
                        {"data":"content","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;
                        }},
                        {"data":"startTime","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;
                        }},
                        {"data":"endTime","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;
                        }},
                        {"data":"id","render" : function(data, type, full, meta ){
                            var editUrl = "<c:url value='/hr/train/edit/'/>"+data;
                            return '<a href="'+editUrl+'" class="btn btn-sm btn-outline grey-salsa" target="_blank"><i class="fa fa-edit"></i> 编辑</a>';//+
                        }}
                    ],
                    fnDrawCallback: function(){
                        var api = this.api();
                        var startIndex= api.context[0]._iDisplayStart;//获取到本页开始的条数
                        api.column(0).nodes().each(function(cell, i) {
                            cell.innerHTML = startIndex + i + 1;
                        });
                    },"initComplete":function () {
                    }
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
    $(document).ready(function() {
        var table = TableDatatablesAjax.init();
        var _compId = "${currCmpId}";
        var empDepartmentTreeObj;

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


        var getDepartments = function (orgId,deptTreeId) {
            $.post('<c:url value="/hr/org/getAllDepartments"/>',{companyId:orgId},function (result) {
                if (result.success){
                    empDepartmentTreeObj = initTree(result.rdata,deptTreeId,function (event,treeId,treeNode) {

                    });
                    var nodes = empDepartmentTreeObj.getNodes();
                    empDepartmentTreeObj.expandNode(nodes[0],true,false,false);
                }
            },'json');
        }


        $('button.js_comp_dept_choose').on('click',function () {
            if(!empDepartmentTreeObj){
                getDepartments(_compId,'empDepartmentTree');
            }
            $('div.js_department_ps_div').modal('show');
        });

        $('button.js_save_btn').on('click',function () {
            var dnodes = empDepartmentTreeObj.getSelectedNodes();
            if(dnodes.length==1) {
                $('input[name=department]').val(dnodes[0].id);
                $('input[name=departmentStr]').val(dnodes[0].name);
            }
            $('div.js_department_ps_div').modal('hide');
        });

        $('button.js_search').on('click',function () {
            table.clearAjaxParams();
            table.setAjaxParam("name", $('input[name=name]').val());
//            table.setAjaxParam("company", _compId);
//            table.setAjaxParam("department", $('input[name=department]').val());
            var type = $('select[name=type]').val();
            if (type){
                table.setAjaxParam("type", type);
            }
            table.getDataTable().ajax.reload();
        });

    });
</script>
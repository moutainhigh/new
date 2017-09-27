<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<link rel="stylesheet" type="text/css" href="<c:url value="/resources/admin/global/plugins/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" />"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/hr/css/hr.css" />"/>

<div class="portlet light" id="form_wizard_1">
    <div class="portlet-title">
        <div class="caption">
            <i class="icon-layers font-green"></i>
            <span class="caption-subject font-green sbold uppercase">培训活动统计（公司）</span>
        </div>
        <div class="actions form-inline">
            <a href="<c:url value="/hr/train/edit"/>"><button class="btn sbold blue js_emp_add"><i class="fa fa-plus-circle"></i>新增</button></a>
        </div>
    </div>
    <div class="portlet-body">
        <div class="row">
            <div class="col-md-12">
                <div class="actions form-inline">
                    <div class="form-group">
                        公司：<div class="input-group">
                        <input name="company" class="form-control input-inline input-sm" type="hidden"/>
                        <input type="text" readonly class="form-control input-sm" name="companyStr" value="">
                        <%--<span class="input-group-btn"><button type="button" class="btn btn-sm btn-primary js_comp_dept_choose">选择</button></span>--%>
                      </div>
                    </div>
                    <div class="form-group">
                        部门：<div class="input-group">
                        <input name="department" class="form-control input-inline input-sm" type="hidden"/>
                        <input type="text" readonly class="form-control input-sm" name="departmentStr" value="">
                        <span class="input-group-btn"><button type="button" class="btn btn-sm btn-primary js_comp_dept_choose">选择</button></span>
                      </div>
                    </div>
                    <div class="form-group">
                        培训日期：
                        <input name="startDate" class="form-control input-inline input-sm" type="text" value=""/>
                        - <input name="endDate" class="form-control input-inline input-sm" type="text" value=""/>
                    </div>
                    <div class="form-group">
                        <button class="btn btn-sm green js_search"><i class="fa fa-search"></i> 搜索 </button>
                        <button class="btn btn-sm sbold  js_download"><i class="fa fa-download"></i> 导出 </button>
                    </div>
                </div>
                <table class="table table-width table-striped table-bordered table-advance table-hover th-weight" id="datatable_ajax">
                    <thead>
                    <tr role="row">
                        <th width="3%" rowspan="2">序号</th>
                        <th width="5%" rowspan="2">一级</th>
                        <th width="5%" rowspan="2">二级</th>
                        <th width="5%" rowspan="2">三级</th>
                        <th width="5%" rowspan="2">公司</th>
                        <th width="5%" rowspan="2">部门</th>
                        <th width="5%" rowspan="2">培训类别</th>
                        <th width="5%" rowspan="2">培训项目名称</th>
                        <th width="5%" rowspan="2">培训开始时间</th>
                        <th width="5%" rowspan="2">培训截止时间</th>
                        <th width="5%" rowspan="2">培训级别</th>
                        <th width="4%" rowspan="2">培训方式</th>
                        <th width="6%" colspan="2">参训人数</th>
                        <th width="3%" rowspan="2">培训时间（小时）</th>
                        <th width="3%" rowspan="2">培训总时间</th>
                        <th width="2%" rowspan="2">培训费用</th>
                        <th width="5%" rowspan="2">备注</th>
                    </tr>
                    <tr>
                        <td>管理类</td>
                        <td>工人类</td>
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

<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.zh-CN.js" />"></script>

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
                        url: "<c:url value="/hr/train/getStatisticsCompData"/>"
                    },
                    order: [[1, "asc"]],
                    columns:[
                        {"data":"id", "targets": 0},
                        {"data":"compLv1Str","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;
                        }},
                        {"data":"compLv2Str","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;
                        }},
                        {"data":"compLv3Str","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;
                        }},
                        {"data":"companyName"},
                        {"data":"departmentName"},
                        {"data":"typeStr","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;
                        }},
                        {"data":"name"},
                        {"data":"startTime"},
                        {"data":"endTime"},
                        {"data":"lvStr","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;
                        }},
                        {"data":"way","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;
                        }},
                        {"data":"countM"},
                        {"data":"countW"},
                        {"data":"timeCount"},
                        {"data":"totalTime"},
                        {"data":"actualCost"},
                        {"data":"note","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;
                        }}
                    ],
                    fnDrawCallback: function(){
                        var api = this.api();
                        var startIndex= api.context[0]._iDisplayStart;//获取到本页开始的条数
                        api.column(0).nodes().each(function(cell, i) {
                            cell.innerHTML = startIndex + i + 1;
                        });

                        var arr = table.getDataTable().data();
                        var countM = 0;
                        var countW = 0;
                        var timeCount = 0;
                        var totalTime = 0;
                        var actualCost = 0;
                        arr.each( function (d) {
                            countM += d.countM;
                            countW += d.countW;
                            timeCount += d.timeCount;
                            totalTime += d.totalTime;
                            actualCost += d.actualCost;
                        });
                        var tr = $('<tr role="row" class="even"><td colspan="12"></td><td>'+countM+'</td><td>'+countW+'</td><td>'+timeCount+'</td><td class="sorting_1">'+totalTime+'</td><td>'+actualCost+'</td><td></td></tr>');
                        $(this).append(tr);
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
        var empCompanyTreeObj;
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


        var getDepartments = function (orgId,deptTreeObj,deptTreeId) {
            $.post('<c:url value="/hr/org/getAllDepartments"/>',{companyId:orgId},function (result) {
                if (result.success){
                    deptTreeObj = initTree(result.rdata,deptTreeId,function (event,treeId,treeNode) {

                    });
                    empDepartmentTreeObj = deptTreeObj;
                }
            },'json');
        }

        var getCompanies = function (treeObj,deptTreeObj,compTreeId,deptTreeId) {
            if(!treeObj){
                $.post('<c:url value="/hr/org/getAllCompanies"/>', function (result) {
                    if (result.success) {
                        treeObj = initTree(result.rdata, compTreeId, function (event, treeId, treeNode) {
                            $.fn.zTree.destroy(deptTreeId);
                            getDepartments(treeNode.id,deptTreeObj,deptTreeId);
                        });
                        var nodes = treeObj.getNodes();
                        treeObj.expandNode(nodes[0],true,false,false);
                        empCompanyTreeObj = treeObj;
                    }
                }, 'json');
            }
        }

        $('button.js_comp_dept_choose').on('click',function () {
            if(!empCompanyTreeObj){
                getCompanies(empCompanyTreeObj,empDepartmentTreeObj,'empCompanyTree','empDepartmentTree');
            }
            $('div.js_department_ps_div').modal('show');
        });

        $('button.js_save_btn').on('click',function () {
            var cnodes = empCompanyTreeObj.getSelectedNodes();
            if(cnodes.length==1) {
                $('input[name=company]').val(cnodes[0].id);
                $('input[name=companyStr]').val(cnodes[0].name);
                var dnodes = empDepartmentTreeObj.getSelectedNodes();
                if(dnodes.length==1) {
                    $('input[name=department]').val(dnodes[0].id);
                    $('input[name=departmentStr]').val(dnodes[0].name);
                }
                $('div.js_department_ps_div').modal('hide');
            }else{
                layer.msg("请选择公司");
            }
        });

        $('button.js_search').on('click',function () {
            table.clearAjaxParams();
            table.setAjaxParam("company", $('input[name=company]').val());
            table.setAjaxParam("department", $('input[name=department]').val());
            var startDate = $('input[name=startDate]').val();
            var endDate = $('input[name=endDate]').val();
            if (startDate&&endDate){
                table.setAjaxParam("startTime", startDate);
                table.setAjaxParam("endTime", endDate);
            }
            table.getDataTable().ajax.reload();
        });

        $('input[name=startDate]').datetimepicker({
            todayBtn : "linked",
            autoclose : true,
            language: 'zh-CN',
            format : "yyyy-mm-dd hh:ii:ss",
            todayHighlight : true
        }).on('changeDate',function(e){
            var startTime = e.date;
            $('input[name=endDate]').datetimepicker('setStartDate',startTime);
        });

        $('input[name=endDate]').datetimepicker({
            todayBtn : "linked",
            autoclose : true,
            language: 'zh-CN',
            format : "yyyy-mm-dd hh:ii:ss",
            todayHighlight : true
        }).on('changeDate',function(e){
            var endTime = e.date;
            $('input[name=startDate]').datetimepicker('setEndDate',endTime);
        });

        $('button.js_download').on('click',function () {
            $('#downloadForm').remove();
            var _form = $('<form>');
            _form.attr('action','<c:url value="/hr/train/downloadTrainCompStatisticsData"/>');
            _form.attr('id','downloadForm');
            _form.attr('method','post');
            _form.attr('target', '_blank');
            var _input = $('<input type="text" name="company" />');
            _input.val($('input[name=company]').val());
            _form.append(_input);
            _input = $('<input type="text" name="department" />');
            _input.val($('select[name=department]').val());
            _form.append(_input);
            var startDate = $('input[name=startDate]').val();
            var endDate = $('input[name=endDate]').val();
            if (startDate&&endDate){
                _input = $('<input type="text" name="startTime" />');
                _input.val(startDate);
                _form.append(_input);
                _input = $('<input type="text" name="endTime" />');
                _input.val(endDate);
                _form.append(_input);
            }
            $(document).find('body').append(_form);
            $('#downloadForm').submit();
        });

    });
</script>
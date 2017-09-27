<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/hr/css/hr.css" />"/>
<div class="portlet light" id="form_wizard_1">
    <div class="portlet-title">
        <div class="caption">
            <i class="icon-layers font-green"></i>
            <span class="caption-subject font-green sbold uppercase">人员变动</span>
        </div>
        <div class="actions form-inline">
            <a href="<c:url value="/hr/chg/edit"/>"><button class="btn sbold blue js_emp_add"><i class="fa fa-plus-circle"></i>新增调动</button></a>
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
                    <div class="form-group">调动类型：
                        <select name="department" class="form-control input-sm">
                        <option value="">请选择</option>
                            <option value="1">单位调动</option>
                            <option value="2">内部调动</option>
                        </select>
                    </div>
                    <div class="form-group">调动状态：
                        <select name="department" class="form-control input-sm">
                            <option value="">请选择</option>
                            <option value="1">晋升</option>
                            <option value="2">平调</option>
                            <option value="3">降职</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <button class="btn btn-sm green js_search"><i class="fa fa-search"></i> 查询 </button>
                    </div>
                </div>
                <table class="table table-width table-striped table-bordered table-advance table-hover table-checkable " id="datatable_ajax">
                    <thead>
                    <tr role="row" class="heading">
                        <th width="3%">序号</th>
                        <th width="5%">员工编号</th>
                        <th width="5%">员工姓名</th>
                        <th width="5%">公司名称</th>
                        <th width="5%">部门名称</th>
                        <th width="5%">职务级别</th>
                        <th width="5%">调动类型</th>
                        <th width="5%">调动状态</th>
                        <th width="5%">是否在岗</th>
                        <th width="5%">登记日期</th>
                    </tr>
                    </thead>
                </table>
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
                            url: "<c:url value="/hr/getEmpListData"/>"
                        },
                        order: [[1, "asc"]],
                        columns:[
                            {"data":"id","render" : function(data, type, full, meta ){
                                return '<label class="mt-checkbox mt-checkbox-single mt-checkbox-outline"> <input type="checkbox" class="checkboxes" value="'+data+'"> <span></span> </label>';
                            }},
                            {"data":"empNo","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"empName"},
                            {"data":"companyStr","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"departmentStr","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"job","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"dutyLevel","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"createtime","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"id","render" : function(data, type, full, meta ){
                                var editUrl = "<c:url value='/hr/edit?id='/>"+data;
                                var showUrl = "<c:url value='/hr/show/'/>"+data;
                                var exportUrl = "<c:url value='/hr/export/'/>"+data;
                                return '<a href="'+editUrl+'" class="btn btn-sm btn-outline grey-salsa"  target="_blank"><i class="fa fa-edit"></i> 编辑</a>'+
                                    '<a href="'+showUrl+'" class="btn btn-sm btn-outline grey-salsa"  target="_blank"><i class="fa fa-search"></i> 查看</a>'+
                                    '<a href="'+exportUrl+'" class="btn btn-sm btn-outline grey-salsa"  target="_blank"><i class="fa fa-search"></i> 打印</a>';
                            }}
                        ],
                        "initComplete":function () {

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
//            var table = TableDatatablesAjax.init();

            $('button.js_search').on('click',function () {
                table.clearAjaxParams();
                table.setAjaxParam("empNo", $('input[name=empNo]').val());
                table.setAjaxParam("empName", $('input[name=empName]').val());
                var department = $('select[name=department]').val();
                if (department){
                    table.setAjaxParam("department", department);
                }
                var levelCompare = $('select[name=levelCompare]').val();
                var levelData = $('select[name=levelData]').val();
                var levelData = $('select[name=department]').val();
                if (levelCompare&&levelData){
                    table.setAjaxParam("levelCompare", levelCompare);
                    table.setAjaxParam("levelData", levelData);
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
                if(_arr){
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
                }
            });

        });
    </script>
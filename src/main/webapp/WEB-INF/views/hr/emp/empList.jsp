<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/hr/css/hr.css" />"/>
<div class="portlet light" id="form_wizard_1">
    <div class="portlet-title">
        <div class="caption">
            <i class="icon-layers font-green"></i>
            <span class="caption-subject font-green sbold uppercase">用户列表</span>
        </div>
        <div class="actions form-inline">
            <a href="<c:url value="/hr/edit"/>"><button class="btn blue js_emp_add"><i class="fa fa-plus-circle"></i>新增</button></a>
            <a href="<c:url value="/hr/exportEmpInfo"/>"><button class="btn red-flamingo"><i class="fa fa-cloud-download"></i>导出当前公司人员</button></a>
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
                    <div class="form-group">职务级别：<select name="levelCompare" class="form-control input-sm">
                                    <option value="">请选择</option>
                                    <option value="0">等于</option>
                                    <option value="1">大于</option>
                                    <option value="10">大于等于</option>
                                    <option value="2">小于</option>
                                    <option value="20">小于等于</option>
                                </select>
                                <select name="levelData" class="form-control input-sm">
                                    <option value="">请选择</option>
                                    <c:forEach items="${dutyLevel}" var="l">
                                        <option value="${l.rank}">${l.dictDataKey}</option>
                                    </c:forEach>
                                </select>
                    </div>
                    <div class="form-group">岗位序列：<select name="jobSequence" class="form-control input-sm">
                        <option value="">全部</option>
                        <option value="5">管理类</option>
                        <option value="6">工人类</option>
                        <option value="7">实习</option>
                        <option value="8">渠道</option>
                        <option value="9">车管</option>
                        <option value="10">默认序列</option>
                        <option value="0">其他</option>
                    </select>
                    </div>
                    <div class="form-group">在职：<select name="onGuard" class="form-control input-sm">
                        <option value="1">是</option>
                        <option value="0">否</option>
                        </select>
                    </div>
                    <div class="form-group">查看子级：<select name="showChildren" class="form-control input-sm">
                        <option value="0">否</option>
                        <option value="1">是</option>
                    </select>
                    </div>
                    <div class="form-group">
                        <button class="btn btn-sm green js_search"><i class="fa fa-search"></i> 搜索 </button>
                        <button class="btn btn-sm green js_download" role="print"><i class="fa fa-download"></i> 导出打印</button>
                        <button class="btn btn-sm green js_download" role="list"><i class="fa fa-download"></i> 导出台帐</button>
                    </div>
                </div>
                <table class="table table-width table-striped table-bordered table-advance table-hover table-checkable th-weight" id="datatable_ajax">
                    <thead>
                    <tr role="row">
                        <th width="3%">
                            <label class="mt-checkbox mt-checkbox-single mt-checkbox-outline">
                                <input type="checkbox" class="group-checkable">
                            </label>
                        </th>
                        <th width="5%">员工编号</th>
                        <th width="5%">员工姓名</th>
                        <th width="5%">公司名称</th>
                        <th width="5%">部门名称</th>
                        <th width="5%">岗位名称</th>
                        <th width="5%">职务级别</th>
                        <th width="5%">岗位序列</th>
                        <th width="5%">登记日期</th>
                        <th width="7%">操作</th>
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
                                return '<label class="mt-checkbox mt-checkbox-single mt-checkbox-outline"> <input type="checkbox" class="checkboxes" value="'+data+'"></label>';
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
                            {"data":"jobSequenceStr","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"createtime","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"id","render" : function(data, type, full, meta ){
                                var editUrl = "<c:url value='/hr/edit?id='/>"+data;
                                var showUrl = "<c:url value='/hr/show/'/>"+data;
                                var printUrl = "<c:url value='/hr/print/'/>"+data;
                                return '<a href="'+editUrl+'" class="btn btn-sm btn-outline grey-salsa"  target="_blank"><i class="fa fa-edit"></i> 编辑</a>'+
                                    '<a href="'+showUrl+'" class="btn btn-sm btn-outline grey-salsa"  target="_blank"><i class="fa fa-search"></i> 查看</a>'+
                                    '<a href="'+printUrl+'" class="btn btn-sm btn-outline grey-salsa"  target="_blank"><i class="fa fa-search"></i> 打印</a>';
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
            var table = TableDatatablesAjax.init();

            var dealCompareLevelCss = function (_compare) {
                if(_compare.next().val()){
                    if(_compare.val()){
                        _compare.css('border-color','');
                    }else{
                        _compare.css('border-color','red');
                    }
                }else{
                    if(_compare.val()){
                        _compare.css('border-color','red');
                    }else{
                        _compare.css('border-color','');
                    }
                }
            }

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
                if (levelCompare&&levelData){
                    table.setAjaxParam("levelCompare", levelCompare);
                    table.setAjaxParam("levelData", levelData);
                }else if(levelCompare||levelData){
                    return;
                }
                var jobSequence = $('select[name=jobSequence]').val();
                if (jobSequence){
                    table.setAjaxParam("jobSequence", jobSequence);
                }
                var onGuard = $('select[name=onGuard]').val();
                if (onGuard){
                    table.setAjaxParam("onGuard", onGuard);
                }
                table.setAjaxParam("showChildren", $('select[name=showChildren]').val());
                table.getDataTable().ajax.reload();
            });

            $('select[name=levelCompare]').on('change',function () {
                var _this = $(this);
                dealCompareLevelCss(_this);
            });

            $('select[name=levelData]').on('change',function () {
                dealCompareLevelCss($(this).prev());
            });

            $('button.js_download').on('click',function () {
                var _type = $(this).attr('role');
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
                    _input = $('<input type="text" name="type" />');
                    _input.val(_type);
                    _form.append(_input);
                    $(document).find('body').append(_form);
                    $('#downloadForm').submit();
                }else{
                    layer.msg('请选择要导出的人员');
                }
            });

        });
    </script>
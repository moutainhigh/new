<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<link rel="stylesheet" type="text/css" href="<c:url value="/resources/admin/global/plugins/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" />" media="screen"/>

<div class="portlet light">
    <div class="portlet-title">
        <div class="caption">
            <i class="icon-layers font-green"></i>
            <span class="caption-subject font-green sbold uppercase">招聘申请</span>
        </div>
    </div>
    <form class="navbar-form navbar-left" role="search">

        <div class="form-group">
            <div class="input-group date form_date" data-date="" data-date-format="yyyy-mm-dd" data-link-field="startTime" data-link-format="yyyy-mm-dd">
                <input class="form-control" id="startTime" name="startTime" size="16" type="text" placeholder="开始时间">
            </div>
        </div>

        <div class="form-group">
            <div class="input-group date form_date" data-date="" data-date-format="yyyy-mm-dd" data-link-field="endTime" data-link-format="yyyy-mm-dd">
                <input class="form-control" id="endTime" name="endTime" size="16" type="text" placeholder="结束时间">
            </div>
        </div>
            <div class="form-group">
                <button type="button" id="buttonSearch1" class="btn btn-default">搜索</button>
                <button type="button" class="btn btn-default js_reset">重置</button>
                <button type="button" class="btn btn-default js_export">导出</button>
                <a href="<c:url value="/admin/hr/recruitmentNeeds/toNeedsDutyLevel"/>"><button type="button" class="btn btn-default">按职级汇总</button></a>
                <a href="<c:url value="/admin/hr/recruitmentNeeds/toNeedsManageGroup"/>"><button type="button" class="btn btn-default">按管线汇总</button></a>
                <a href="<c:url value="/admin/hr/recruitmentNeeds/toNeedsCompanyLevel"/>"><button type="button" class="btn btn-default">按架构层级汇总</button></a>
                <a href="<c:url value="/admin/hr/recruitmentNeeds/toNeedsDepartmentLevel"/>"><button type="button" class="btn btn-default">按层级汇总</button></a>
            </div>
    </form>

    <div id="myTabContent" class="tab-content">
        <div class="tab-pane fade in active" id="home">
            <div class="portlet-body">
                <div class="row">
                    <div class="col-md-12">
                        <table class="table table-width table-striped table-bordered table-advance table-hover table-checkable " style="min-width: 5000px;" id="datatable_ajax">
                            <thead>
                            <tr role="row" class="heading">
                                <th width="2%">
                                    序号
                                </th>
                                <th>申请单位</th>
                                <th>申请部门</th>
                                <th>岗位</th>
                                <th>补增数量</th>
                                <th>申请日期</th>
                                <th>架构层级</th>
                                <th>管线</th>
                                <th>层级</th>
                                <th>职级</th>
                                <th>计划到岗时间</th>
                                <th>学历</th>
                                <th>专业</th>
                                <th>性别</th>
                                <th>年龄</th>
                                <th>外语能力</th>
                                <th>专业职称要求</th>
                                <th>管理能力要求</th>
                                <th>工作经验</th>
                                <th>是否接受外派</th>
                                <th>岗位职责</th>
                                <th>汇报关系</th>
                                <th>薪酬范围</th>
                                <th>该岗位计划编制</th>
                                <th>现有在岗人数</th>
                                <th>计划内补增</th>
                                <th>理由</th>
                                <th>计划外补增</th>
                                <th>理由</th>
                                <th>备注</th>
                            </tr>
                            </thead>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>


</div>

<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.zh-CN.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/scripts/datatable.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/datatables/datatables.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js" />"></script>

<script>
    $(function () {

        //设置开始时间
        $('#startTime').datetimepicker({
            format:"yyyy-mm-dd",
            language:  'zh-CN',
            weekStart: 1,
            todayBtn:  1,
            autoclose: 1,
            todayHighlight: 1,
            startView: 2,
            minView: 2,
            forceParse: 0
        });

        $('#endTime').datetimepicker({
            format:"yyyy-mm-dd",
            language:  'zh-CN',
            weekStart: 1,
            todayBtn:  1,
            autoclose: 1,
            todayHighlight: 1,
            startView: 2,
            minView: 2,
            forceParse: 0
        });
    });

    var TableDatatablesAjax = function() {
        var  e = function() {
            var table = new Datatable;
            table.init({
                src: $("#datatable_ajax"),
                loadingMessage: "Loading...",
                dataTable: {
                    bStateSave: !0,
                    lengthMenu: [[10, 20, 50, 100], [10, 20, 50, 100]],
                    searching:true,
                    pageLength: 10,

                    ajax: {
                        url: "<c:url value="/admin/hr/recruitmentNeeds/listNeedsData"/>"
                    },
                    order: [[1, "asc"]],
                    columns:[
                        {"data":"id","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;                        }},
                        {"data":"company","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;
                        }},
                        {"data":"department","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;
                        }},
                        {"data":"job","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;
                        }},
                        {"data":"addNum","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;
                        }},
                        {"data":"askDate","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;
                        }},
                        {"data":"companyLevel","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;
                        }},
                        {"data":"manageGroup","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;
                        }},
                        {"data":"departmentLevel","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;
                        }},
                        {"data":"dutyLevel","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;
                        }},
                        {"data":"planJoinDate","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;
                        }},
                        {"data":"education","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;
                        }},
                        {"data":"profession","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;
                        }},
                        {"data":"sex","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;
                        }},
                        {"data":"age","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;
                        }},
                        {"data":"foreignLang","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;
                        }},
                        {"data":"profesRequire","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;
                        }},
                        {"data":"managePower","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;
                        }},
                        {"data":"workExperience","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;
                        }},
                        {"data":"acceptOutWork","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;
                        }},
                        {"data":"jobResponsi","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;
                        }},
                        {"data":"reportRelationship","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;
                        }},
                        {"data":"salaryRange","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;
                        }},
                        {"data":"jobPrepared","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;
                        }},
                        {"data":"onDutyNum","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;
                        }},
                        {"data":"planAdd","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;
                        }},
                        {"data":"reason","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;
                        }},
                        {"data":"outPlanAdd","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;
                        }},
                        {"data":"reason2","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;
                        }},
                        {"data":"note","render" : function(data, type, full, meta ){
                            return data==undefined?'':data;
                        }}

                    ],fnDrawCallback: function(){
                        var api = this.api();
                        var startIndex= api.context[0]._iDisplayStart;//获取到本页开始的条数
                        api.column(0).nodes().each(function(cell, i) {
                            cell.innerHTML = startIndex + i + 1;
                        });
                    },
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
        $('#datatable_ajax').on('click','button',function () {

        });

        $('#buttonSearch1').click(function () {
            table.clearAjaxParams();
            table.setAjaxParam('startTime',$('#startTime').val().trim());
            table.setAjaxParam('endTime',$('#endTime').val().trim());
            table.getDataTable().ajax.reload();
        });

        $('button.js_reset').click(function () {
            table.clearAjaxParams();
            $('input[name=startTime]').val('');
            $('input[name=endTime]').val('');
            table.setAjaxParam('start',0);
            table.getDataTable().ajax.reload();
        });

        /**
         * 导出招聘需求
         */
        $("button.js_export").on('click',function () {
            var _startTime = $('input[name=startTime]').val();
            var _endTime= $('input[name=endTime]').val();
            $('#exportForm').remove();
            var _form = $('<form>');
            _form.attr('action','<c:url value="/admin/hr/recruitmentNeeds/exportNeedsData"/>');
            _form.attr('id','exportForm');
            _form.attr('method','POST');
            _form.append($('<input>').attr('name',"startTime").val(_startTime));
            _form.append($('<input>').attr('name',"endTime").val(_endTime));
            $(document).find('body').append(_form);
            $('#exportForm').submit();
        });

    });
</script>
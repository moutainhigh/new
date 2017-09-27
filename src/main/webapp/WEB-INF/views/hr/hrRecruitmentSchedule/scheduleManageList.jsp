<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<link rel="stylesheet" type="text/css" href="<c:url value="/resources/admin/global/plugins/bootstrap-select/css/bootstrap-select.min.css" />" media="screen"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/hr/css/hr.css" />"/>

<style>
    .bootstrap-select{
        width:auto !important;
    }
</style>
<div class="portlet light">
    <div class="portlet-title">
        <div class="caption">
            <i class="icon-layers font-green"></i>
            <span class="caption-subject font-green sbold uppercase">招聘岗位进程列表</span>
        </div>
        <div class="actions form-inline">
            <a href="<c:url value="/hr/recruitmentSchedule/edit"/>"><button class="btn blue"><i class="fa fa-plus-circle"></i>新增</button></a>
        </div>
    </div>
    <div class="portlet-body">
        <form class="navbar-form" name="search" role="search" id="materialSearchForm">
            <div class="form-group">
                <label class="control-label">板块：</label>
                <select name="plat" class="selectPicker  js_search_select" multiple data-live-search="false">
                </select>
            </div>

            <div class="form-group">
                <label class="control-label">单位：</label>
                <select name="company" class="selectPicker  js_search_select" multiple data-live-search="true">
                </select>
            </div>
            <div class="form-group">
                <label class="control-label">部门：</label>
                <select name="department" class="selectPicker  js_search_select" multiple data-live-search="true">
                </select>
            </div>
            <div class="form-group">
                <label class="control-label">进程状态：</label>
                <select class="form-control js_search_select selectPicker" name="processStatusStr" multiple>
                    <option value="1">招聘入职</option>
                    <option value="2">紧急</option>
                    <option value="3">面试中</option>
                    <option value="4">储备</option>
                    <option value="5">暂停</option>
                </select>
            </div>
            <div class="form-group">
                <button type="button"  class="btn btn-default js_search">搜索</button>
                <button type="button" class="btn btn-default js_reset">重置</button>
                <button type="button" class="btn btn-default js_export">导出</button>
            </div>
        </form>

        <div class="row">
            <div class="col-md-12">
                <table class="table table-width table-striped table-bordered table-advance table-hover table-checkable th-weight" id="datatable_ajax" style="min-width:2500px;">
                    <thead>
                    <tr role="row">
                        <th width="3%">
                            <label class="mt-checkbox mt-checkbox-single mt-checkbox-outline">
                                <input type="checkbox" class="group-checkable">
                                <span></span>
                            </label>
                        </th>
                        <th>状态</th>
                        <th>板块</th>
                        <th>公司</th>
                        <th>部门</th>
                        <th>职位名称</th>
                        <th>姓名</th>
                        <th>联系方式</th>
                        <th>人数</th>
                        <th>优先级</th>
                        <th>预计到岗<br>日期</th>
                        <th>招聘需求<br>提出日期</th>
                        <th>进展</th>
                        <th>简历筛选<br>（提交份数）</th>
                        <th>简历提<br>交时间</th>
                        <th>简历确认</th>
                        <th>简历确<br>认时间</th>
                        <th>电话通知</th>
                        <th>初试日期</th>
                        <th>通过人员</th>
                        <th>复试</th>
                        <th>复试日期</th>
                        <th>复试通过人员</th>
                        <th>终试</th>
                        <th>终试日期</th>
                        <th>拟录用<br>人员</th>
                        <th>拟到岗时间</th>
                        <th>负责人</th>
                        <th>实际到<br>岗时间</th>
                        <th width="6%">相关操作</th>
                    </tr>
                    </thead>
                </table>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/datatables/datatables.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/scripts/datatable.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/bootstrap-select/js/bootstrap-select.min.js" />"></script>

<script>

    $(function () {

        var statusObj = {
            1:"招聘入职",
            2:"紧急",
            3:"面试中",
            4:"储备",
            5:"暂停"
        }

        $('select.js_search_select').selectpicker({
            size: 5,
            width:'100%',
            title:"",
            showContent:true,
            showTick:false
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
                            url: "<c:url value="/hr/recruitmentSchedule/listData"/>"
                        },
                        order: [[1, "asc"]],
                        columns:[
                            {"data":"id","render" : function(data, type, full, meta ){
                                return '<label class="mt-checkbox mt-checkbox-single mt-checkbox-outline"> <input type="checkbox" class="checkboxes js_checkBox" value="'+data+'"/></label>';
                            }},
                            {"data":"processStatus","render" : function(data, type, full, meta ){
                                if(data==1){
                                    return '<div class="bg-green-seagreen text-center"><span class="bg-font-green-seagreen"> '+(data==undefined?'':statusObj[data])+' </span></div>'
                                }else if(data==2){
                                    return '<div class="bg-red-intense text-center"><span class="bg-font-red-intense"> '+(data==undefined?'':statusObj[data])+' </span></div>'
                                }else if(data==3){
                                    return '<div class="bg-yellow-crusta text-center"><span class="bg-font-yellow-crusta"> '+(data==undefined?'':statusObj[data])+' </span></div>'
                                }else if(data==4){
                                    return '<div class="bg-blue-hoki text-center"><span class="bg-font-blue-hoki"> '+(data==undefined?'':statusObj[data])+' </span></div>'
                                }else if(data==5){
                                    return '<div class="bg-grey-silver text-center"><span class="bg-font-grey-silver"> '+(data==undefined?'':statusObj[data])+' </span></div>'
                                }
                            }},
                            {"data":"plat","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"company","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"department","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"position","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"name","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"telephone","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"numberOfHiring","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"level","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"planOnDutyDate","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"hireDate","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"description","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"numberOfResumes","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"receiveResumeDate","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"resumePassedNumber","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"resumePassedDate","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"personNumByCall","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"firstAuditionDate","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"firstPassedPerson","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"secondAuditonNumber","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"secondAuditionDate","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"secondPassedPerson","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"finalAuditionNumber","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"finalAuditionDate","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"personNameToBeHired","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"planComePositionDate","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"responsiblePerson","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"comePositionDate","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"id","render" : function(data, type, full, meta ){
                                var editUrl = "<c:url value='/hr/recruitmentSchedule/edit/'/>"+data;
                                return  '<a href="'+editUrl+'" class="btn btn-sm btn-outline grey-salsa" style="width: 45%" ><i class="fa fa-edit"></i> 编辑</a>'+
                                    '<button class="btn btn-sm btn-outline grey-salsa"  target="_blank" style="width: 45%" data-id="'+data+'"><i class="fa fa-close"></i> 删除</button>';
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


        var table = TableDatatablesAjax.init();

        $('#datatable_ajax').on('click','button',function () {
            var _id = $(this).data('id');
            var _specificationId = $(this).data('specification');
            layer.confirm("确定要删除？",function (_index) {
                layer.close(_index);
                $.post('<c:url value="/hr/recruitmentSchedule/deleteOne"/>', {id:_id}, function (result) {
                    if (result.success) {
                        layer.msg('操作成功',{time:1000},function () {
                            table.getDataTable().ajax.reload();
                        });
                    }else {
                        layer.msg('删除失败！：'+result.rmsg,{time:2000});
                    }
                }, 'json');
            });
        });

        $('button.js_search').click(function () {
            table.clearAjaxParams();
            table.setAjaxParam('plat',$.trim($('select[name=plat]').val()));
            table.setAjaxParam('region',$.trim($('select[name=region]').val()));
            table.setAjaxParam('company',$.trim($('select[name=company]').val()));
            table.setAjaxParam('department',$.trim($('select[name=department]').val()));
            table.setAjaxParam('processStatusStr',$.trim($('select[name=processStatusStr]').val()));
            table.setAjaxParam('start',0);
            table.getDataTable().ajax.reload();
        });

        $('button.js_reset').click(function () {
            table.clearAjaxParams();
            $('select[name=plat]').val('');
            $('select[name=region]').val('');
            $('select[name=company]').val('');
            $('select[name=department]').val('');
            $('select[name=processStatusStr]').val('');
            table.setAjaxParam('start',0);
            $('select.js_search_select').selectpicker('refresh');
            table.getDataTable().ajax.reload();
        });

        $('button.js_export').click(function () {
            var ids = [];
            var inputs= $("tbody span[class=checked] input");
            $.each(inputs,function (k, v) {
                ids.push(v.value);
            })
            if (ids.length==0){
                layer.msg('请至少选择一行数据', {icon: 0});
                return;
            }
            window.location.href = "/hr/recruitmentSchedule/exportExcel/"+ids.join(",");
        });


        /**
         * 加载下拉框
         * @type {{init}}
         */
        $.post("/hr/recruitmentSchedule/getAll", {"field":"plat"}, function (data) {
            for (var i in data) {
                var plat = data[i].plat;
                $('select[name=plat]').append("<option value='"+plat+"'>"+plat+"</option>");
            }
            $('select[name=plat]').selectpicker('refresh');
        });

        $.post("/hr/recruitmentSchedule/getAll", {"field":"company"}, function (data) {
            for (var i in data) {
                var company = data[i].company;
                $('select[name=company]').append("<option value='"+company+"'>"+company+"</option>");
            }
            $('select[name=company]').selectpicker('refresh');
        });

        $.post("/hr/recruitmentSchedule/getAll", {"field":"department"}, function (data) {
            for (var i in data) {
                var department = data[i].department;
                $('select[name=department]').append("<option value='"+department+"'>"+department+"</option>");
            }
            $('select[name=department]').selectpicker('refresh');
        });
    });
</script>
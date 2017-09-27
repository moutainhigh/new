<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<link rel="stylesheet" type="text/css" href="<c:url value="/resources/admin/global/plugins/bootstrap-datepicker/css/bootstrap-datepicker3.min.css" />"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/hr/css/hr.css" />"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/webuploader/css/webuploader.css" />">

<style>
    .bootstrap-select{
        width:auto !important;
    }
</style>
<div class="portlet light">
    <div class="portlet-title">
        <div class="caption">
            <i class="icon-layers font-green"></i>
            <span class="caption-subject font-green sbold uppercase">人才库列表</span>
        </div>
        <div class="actions form-inline">
            <a href="<c:url value="/hr/recruitmentStore/edit"/>"><button class="btn blue"><i class="fa fa-plus-circle"></i>新增</button></a>
        </div>
    </div>
    <div class="portlet-body">
    <form class="navbar-form" role="search">
        <div class="form-group">
            提交日期：
            <input name="startDate" class="form-control input-inline input-sm" type="text" value=""/>
            - <input name="endDate" class="form-control input-inline input-sm" type="text" value=""/>
        </div>
        <div class="form-group">
            <button type="button"  class="btn btn-default js_search">搜索</button>
            <button type="button" class="btn btn-default js_reset">重置</button>
            <button type="button" class="btn btn-default js_download">导出</button>
        </div>
        <div id="uploader"  class="form-group">导入</div>
    </form>

        <div class="row">
            <div class="col-md-12">
                <table class="table table-width table-striped table-bordered table-advance table-hover table-checkable th-weight" id="datatable_ajax">
                    <thead>
                    <tr role="row" class="heading">
                        <th>序号</th>
                        <th>板块</th>
                        <th>姓名</th>
                        <th>身份证号码</th>
                        <th>性别</th>
                        <th>毕业院校</th>
                        <th>学历</th>
                        <th>专业</th>
                        <th>现任职单位</th>
                        <th>开始工作时间</th>
                        <th>就职岗位</th>
                        <th>简历来源</th>
                        <th>应聘职位</th>
                        <th>面试时间</th>
                        <th>联系电话</th>
                        <th>邮箱</th>
                        <th>提交时间</th>
                        <th>相关操作</th>
                    </tr>
                    </thead>
                </table>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript" src="<c:url value="/resources/webuploader/js/webuploader.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/datatables/datatables.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/scripts/datatable.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.zh-CN.js" />"></script>

<script>

    $(function () {


        $('input[name=startDate]').datepicker({
            todayBtn : "linked",
            autoclose : true,
            language: 'zh-CN',
            format : "yyyy-mm-dd",
            todayHighlight : true
        }).on('changeDate',function(e){
            var startTime = e.date;
            $('input[name=endDate]').datepicker('setStartDate',startTime);
        });

        $('input[name=endDate]').datepicker({
            todayBtn : "linked",
            autoclose : true,
            language: 'zh-CN',
            format : "yyyy-mm-dd",
            todayHighlight : true
        }).on('changeDate',function(e){
            var endTime = e.date;
            $('input[name=startDate]').datepicker('setEndDate',endTime);
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
                            url: "<c:url value="/hr/recruitmentStore/listData"/>"
                        },
                        order: [[1, "asc"]],
                        columns:[
                            {"data":"id"},
                            {"data":"plat","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"name","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"idCard","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"sex","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"school","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"education","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"profession","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"companyName","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"joinWorkDate","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"job","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"resumeSrc","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"askJob","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"auditionDate","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"mobile","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"email","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"createtime","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"id","render" : function(data, type, full, meta ){
                                var editUrl = "<c:url value='/hr/recruitmentStore/edit/'/>"+data;
                                return  '<a href="'+editUrl+'" class="btn btn-sm btn-outline grey-salsa"><i class="fa fa-edit"></i> 编辑</a>'+
                                    '<button class="btn btn-sm btn-outline grey-salsa"  target="_blank" data-id="'+data+'"><i class="fa fa-close"></i> 删除</button>';
                            }}
                        ],"initComplete":function () {

                        },fnDrawCallback: function(){
                            var api = this.api();
                            var startIndex= api.context[0]._iDisplayStart;//获取到本页开始的条数
                            api.column(0).nodes().each(function(cell, i) {
                                cell.innerHTML = startIndex + i + 1;
                            });
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


        var table = TableDatatablesAjax.init();

        $('#datatable_ajax').on('click','button',function () {
            var _id = $(this).data('id');
            var _specificationId = $(this).data('specification');
            layer.confirm("确定要删除？",function (_index) {
                layer.close(_index);
                $.post('<c:url value="/hr/recruitmentStore/delete"/>', {id:_id}, function (result) {
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
            table.setAjaxParam('startDate',$('input[name=startDate]').val());
            table.setAjaxParam('endDate',$('input[name=endDate]').val());
            table.setAjaxParam('start',0);
            table.getDataTable().ajax.reload();
        });

        $('button.js_reset').click(function () {
            table.clearAjaxParams();
            table.setAjaxParam('start',0);
            $('input[name=startDate]').val('');
            $('input[name=endDate]').val('');

            table.getDataTable().ajax.reload();
        });

        $('button.js_download').on('click',function () {
            $('#downloadForm').remove();
            var _form = $('<form>');
            _form.attr('action','<c:url value="/hr/recruitmentStore/downloadData"/>');
            _form.attr('id','downloadForm');
            _form.attr('method','post');
            _form.attr('target', '_blank');
            var _input = $('<input type="text" name="startDate" />').val($('input[name=startDate]').val());;
            _form.append(_input);
            _input = $('<input type="text" name="endDate" />').val($('input[name=endDate]').val());
            _form.append(_input);
            $(document).find('body').append(_form);
            $('#downloadForm').submit();
        });

        var _index = null;
        // 初始化Web Uploader
        var single = WebUploader.create({
            fileVal:'uploadFile',
            auto: true,
            compress: false,
            swf: '<c:url value="/resources/webuploader/js/Uploader.swf" />',
            server: '<c:url value="/hr/recruitmentStore/upload" />',
            pick: {
                id: '#uploader',
                label: '导入',
                multiple:true
            },
            fileSingleSizeLimit: 5 * 1024 * 1024//,    // 5M
        });

        single.on( 'uploadSuccess', function( file ,json) {
            layer.close(_index);
            if(json.success){
                layer.msg('上传成功',function () {
                    window.location.reload();
                });
            }else{
                layer.msg(json.rmsg,function () {
                    window.location.reload();
                });
            }
        });

        single.on( 'startUpload', function( file ,json) {
            _index = layer.load(2);
        });

        single.on( 'uploadError', function( file ) {
            alert("附件上传失败！");
        });
        single.on( 'error', function( e) {
            if (e =="F_EXCEED_SIZE"){
                alert("附件大小超过最大限制！");
            }
        });

    });
</script>
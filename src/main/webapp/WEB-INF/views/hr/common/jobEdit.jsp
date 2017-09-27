<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="portlet box blue-hoki">
    <div class="portlet-title">
        <div class="caption">
            <i class="fa fa-gift"></i>职位管理
        </div>
    </div>
    <div class="portlet-body">
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-3">
                    <label>选择单位</label>
                    <ul id="companyTree" class="ztree"></ul>
                </div>
                <div class="col-md-3">
                    <label>部门</label>
                    <ul id="departmentTree" class="ztree"></ul>
                </div>
                <div class="col-md-3">
                    <label>岗位</label>
                    <ul id="jobTree" class="ztree"></ul>
                    <button type="button" class="btn btn-primary btn-sm js_add_job hidden">新增</button>
                    <button type="button" class="btn btn-danger btn-sm js_delete_job hide">删除</button>
                    <button type="button" class="btn btn-info btn-sm js_add_plait hide">增编</button>
                    <button type="button" class="btn btn-warning btn-sm js_edit_plait hide">改编</button>
                </div>
                <div class="col-md-3 js_job_group hide">
                    <form id="jobEditForm" method="post" >
                    <div class="row">
                        <div class="form-group">
                            <label>岗位名称</label>
                            <input type="text" class="form-control" name="name" placeholder="岗位名称">
                        </div>
                        <div class="form-group">
                            <label>年份</label>
                            <input type="text" class="form-control" name="year" placeholder="例如：2017" value="">
                        </div>
                        <div class="form-group">
                            <label>开始月份</label>
                            <input type="text" class="form-control" name="startMonth" placeholder="例如：1" value="">
                        </div>
                        <div class="form-group">
                            <label>结束月份</label>
                            <input type="text" class="form-control" name="endMonth" placeholder="例如：12" value="">
                        </div>
                        <div class="form-group">
                            <label>职级</label>
                            <select class="form-control" name="dutyLevel">
                                <option value="">请选择</option>
                                <c:forEach items="${dutyLevel}" var="data">
                                    <option value="${data.dictDataKey}">${data.dictDataKey}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>管线</label>
                            <select class="form-control" name="manageLine">
                                <option value="">请选择</option>
                                <c:forEach items="${manageLine}" var="data">
                                    <option value="${data.dictDataKey}">${data.dictDataKey}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>编制人数</label>
                            <input type="text" class="form-control" name="count" placeholder="请输入数字">
                        </div>
                        <%--<div class="form-group">--%>
                            <%--<label>备注</label>--%>
                            <%--<input type="text" class="form-control" name="note" value="">--%>
                        <%--</div>--%>
                        <div class="form-group">
                            <button type="button" class="btn btn-primary js_save_btn">保存</button>
                        </div>
                    </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="portlet light">
    <%--<div class="portlet-title">--%>
        <%--<div class="caption">--%>
            <%--编制详情--%>
        <%--</div>--%>
    <%--</div>--%>
    <form class="navbar-form navbar-left" role="search" id="materialSearchForm">
        <div class="form-group">
            <input type="text" name="year" class="form-control" value="" placeholder="年份">
        </div>
        <div class="form-group">
            <select class="form-control" name="month">
                <option value="">请选择</option>
            <c:forEach begin="1" end="12" var="m">
                <option value="${m}">${m}月</option>
            </c:forEach>
            </select>
        </div>
        <button type="button"  class="btn btn-default js_search">搜索</button>
        <button type="button" class="btn btn-default js_reset">重置</button>
    </form>
    <div class="portlet-body">
        <table class="table table-width table-striped table-bordered table-advance table-hover table-checkable th-weight" id="datatable_ajax">
            <thead>
            <tr role="row">
                <th width="3%">
                    <label class="mt-checkbox mt-checkbox-single mt-checkbox-outline">
                        <input type="checkbox" class="group-checkable">
                    </label>
                </th>
                <th>年份</th>
                <th>月份</th>
                <th>公司</th>
                <th>部门</th>
                <th>岗位名称</th>
                <th>职级</th>
                <th>管线</th>
                <th>编制数</th>
                <th>实际数</th>
                <th>任职者</th>
                <th>身份证号</th>
                <%--<th>备注</th>--%>
                <th width="10%">操作</th>
            </tr>
            </thead>
        </table>

    </div>
</div>


<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/jquery-validation/js/jquery.validate.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/jquery-validation/js/additional-methods.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/jquery-validation/js/messages_zh.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/datatables/datatables.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/scripts/datatable.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js" />"></script>
<script>

    $(function () {
        var jobInfoObj = {jobId:null};
        var companyTreeObj;
        var departmentTreeObj;
        var jobTreeObj;
        var operate = null;
        var initTree = function (_arr,treeId,callback) {
            var setting = {
                callback:{
                    onClick:function (event, treeId, treeNode) {
                        if ("function" == typeof(callback)){
                            callback(event,treeId,treeNode);
                        }
                    },
                    beforeRename:confirmRenameNode,
                    beforeEditName:cacheOldNodeName
                },
                data:{
                    key:{
                        children:"childList",
                        title:"name"
                    }
                }
            };
            if (treeId=="#jobTree"){
                setting. view={
                    selectedMulti: false
                };
                setting.edit={
                    enable: true,
                    showRemoveBtn:false
                }
            }
            return $.fn.zTree.init($(treeId), setting, _arr);
        }

        var validateForm = $('#jobEditForm');
        validateForm.validate({
            errorElement : 'span',
            errorClass : 'help-block help-block-error',
            rules : {
                name : {
                    required : true
                },year : {
                    required : true,
                    digits:true
                },startMonth : {
                    required : true,
                    digits:true
                },endMonth: {
                    required : true,
                    digits:true
                },dutyLevel: {
                    required : true
                },manageLine: {
                    required : true
                },count: {
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


        /**
         * 保存岗位信息
         */
        $("button.js_save_btn").on('click',function () {
            var _arr = $('#jobEditForm').serializeArray();
            if (validateForm.valid()){
                for(var i in _arr){
                    var obj = _arr[i];
                    jobInfoObj[obj.name] = $.trim(obj.value);
                }
                layer.confirm('确定要提交？',function (_index) {
                    layer.close(_index);
                    _index = layer.load(3);
                    var url = null;
                    if(operate == 'addJob'){
                        url = '<c:url value="/hr/org/addJob"/>';
                    }else  if(operate == 'addPlait'){
                        url = '<c:url value="/hr/org/addJobPlait"/>';
                    }else  if(operate == 'editPlait'){
                        url = '<c:url value="/hr/org/editJobPlait"/>';
                    }
                    $.post(url,jobInfoObj,function (result) {
                        layer.close(_index);
                        if (result.success) {
                            var node = result.rdata;
                            if(jobInfoObj.jobId==null){
                                jobTreeObj.addNodes(null, node);
                            }else{
                                table.getDataTable().ajax.reload();
                            }
                            $('#jobEditForm')[0].reset();
                            if(operate == 'addPlait'||operate == 'editPlait'){
                                $('input[name=name]').val(jobInfoObj.name).prop('readonly',true);
                            }
                        }else{
                            layer.msg(result.rmsg);
                        }
                    }, 'json');
                })
            }
        });

        /**
         * 新增岗位
         */
        $("button.js_add_job").on('click',function () {
            if (companyTreeObj&&departmentTreeObj){
                var deptNodes = departmentTreeObj.getSelectedNodes();
                var cmpNodes = companyTreeObj.getSelectedNodes();
                if (cmpNodes.length==1){
                    jobInfoObj.companyId=cmpNodes[0].id;
                }else {
                    return;
                }
                if (deptNodes.length==1){
                    jobInfoObj.departmentId=deptNodes[0].id;
                }else{
                    return;
                }
                if (null!=jobTreeObj.getNodeByParam('id',-1,null)){
                    layer.msg('请先保存刚才添加的岗位');
                    return;
                }
                jobInfoObj.jobId=null;
                $('#jobEditForm')[0].reset();
                $('input[name=count]').parent().removeClass('hide');
                $('input[name=name]').prop('readonly',false);
                $('div.js_job_group').removeClass('hide');
                operate='addJob';
            }
        });

        /**
         * 删除岗位
         */
        $("button.js_delete_job").on('click',function () {
            var jobNodes = jobTreeObj.getSelectedNodes();
            if (jobNodes.length == 1){
                layer.confirm('确定删除该职位？编制信息也将一并删除',function (index) {
                    var deptNodes = departmentTreeObj.getSelectedNodes();
                    var obj = {
                        id:jobNodes[0].id,
                        departmentId:deptNodes[0].id
                    };
                    $.post('<c:url value="/hr/org/delJob"/>', obj,function (result) {
                        if (result.success) {
                            layer.close(index);
                            jobTreeObj.removeNode(jobNodes[0]);
                        }else {
                            layer.msg("删除职位失败:"+result.rmsg);
                        }
                    }, 'json');
                });
            }
        });

        /**
         * 改编
         */
        $("button.js_edit_plait").on('click',function () {
            $('div.js_job_group').removeClass('hide');
            $('#jobEditForm')[0].reset();
            var jobNodes = jobTreeObj.getSelectedNodes();
            $('input[name=name]').val(jobNodes[0].name).prop('readonly',true);
            $('input[name=count]').parent().addClass('hide');
            operate='editPlait';
        });

        /**
         * 增编
         */
        $("button.js_add_plait").on('click',function () {
            $('div.js_job_group').removeClass('hide');
            $('#jobEditForm')[0].reset();
            var jobNodes = jobTreeObj.getSelectedNodes();
            $('input[name=name]').val(jobNodes[0].name).prop('readonly',true);
            $('input[name=count]').parent().removeClass('hide');
            operate='addPlait';
        });

        /**
         * 缓存原有借点名称
         */
        var oldName = null;
        function cacheOldNodeName(treeId, treeNode) {
            oldName=treeNode.name;
        }

       /**
        * 修改岗位名称
        */
        function confirmRenameNode(treeId, treeNode,newName, isCancel) {
            if (oldName!=newName){
                var deptNodes = departmentTreeObj.getSelectedNodes();
                $.post('<c:url value="/hr/org/updateJob"/>', {departmentId:deptNodes[0].id,id:treeNode.id,name:newName},function (result) {
                    if (!result.success) {
                        layer.msg("修改职位名称失败");
                    }else{
                        jobTreeObj.cancelSelectedNode(treeNode);
                    }
                }, 'json');
            }
        }


        var getJobs = function (departmentId) {
            $.post('<c:url value="/hr/org/getAllJob"/>',{departmentId:departmentId},function (result) {
                if (result.success){
                    jobTreeObj = initTree(result.rdata,"#jobTree", function (event, treeId, treeNode) {
                        $("button.js_delete_job").removeClass('hide');
                        $("button.js_add_plait").removeClass('hide');
                        $("button.js_edit_plait").removeClass('hide');
                        $('#jobEditForm')[0].reset();
                        $('input[name=name]').val(treeNode.name).prop('readonly',true);
                        jobInfoObj.jobId = treeNode.id;
                        var deptNodes = departmentTreeObj.getSelectedNodes();
                        var cmpNodes = companyTreeObj.getSelectedNodes();
                        jobInfoObj.companyId = cmpNodes[0].id;
                        jobInfoObj.departmentId = deptNodes[0].id;
                    });
                }
                $('button.js_add_job').removeClass('hidden');
            },'json');
        }

        var getDepartments = function (orgId) {
            $.post('<c:url value="/hr/org/getAllDepartments"/>',{companyId:orgId},function (result) {
                if (result.success){
                    departmentTreeObj = initTree(result.rdata,"#departmentTree", function (event, treeId, treeNode) {
                        $.fn.zTree.destroy("jobTree");
                        $('div.js_job_group').addClass('hide');
                        $("button.js_delete_job").addClass('hide');
                        $("button.js_add_plait").addClass('hide');
                        $("button.js_edit_plait").addClass('hide');
                        getJobs(treeNode.id);
                    });
                }
            },'json');
        };

        (function () {

            $.post('<c:url value="/hr/org/getAllCompanies"/>', function (result) {
                if (result.success) {
                    companyTreeObj = initTree(result.rdata, "#companyTree", function (event, treeId, treeNode) {
                        $.fn.zTree.destroy("jobTree");
                        $('button.js_add_job').addClass('hidden');
                        $("button.js_delete_job").addClass('hide');
                        $("button.js_add_plait").addClass('hide');
                        $("button.js_edit_plait").addClass('hide');
                        getDepartments(treeNode.id);
                    });
                    var nodes = companyTreeObj.getNodes();
                    companyTreeObj.expandNode(nodes[0],true,false,false);
                }
            }, 'json');
        })();


        /**
         * 编制人数 输入
         */
        $('input[name=count]').on('blur',function () {
            var _val = parseInt($(this).val());
            if(!isNaN(_val)&&_val<=0){
                $(this).val('');
            }
        });


        var TableDatatablesAjax = function() {
            var  e = function(args) {
                var table = new Datatable;
                for(var i in args){
                    table.setAjaxParam(i,args[i]);
                }
                table.init({
                    src: $("#datatable_ajax"),
                    loadingMessage: "Loading...",
                    dataTable: {
                        bStateSave: !0,
                        lengthMenu: [[10, 20, 50, 100], [10, 20, 50, 100]],
                        searching:true,
                        pageLength: 10,
                        ajax: {
                            url: "<c:url value="/hr/org/jobPlaitListData"/>"
                        },
                        order: [[1, "asc"]],
                        columns:[
                            {"data":"id", "targets": 0},
                            {"data":"year","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"month","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"companyName","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"departmentName","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"jobName","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"dutyLevel","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"manageLine","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"count","render" : function(data, type, full, meta ){
                                return 1;
                            }},
                            {"data":"empId","render" : function(data, type, full, meta ){
                                return data==undefined?0:1;
                            }},
                            {"data":"empName","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"idCard","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
//                            {"data":"note","render" : function(data, type, full, meta ){
//                                return data==undefined?'':data;
//                            }},
                            {"data":"id","render" : function(data, type, full, meta ){
                                return '<button class="btn btn-sm btn-outline grey-salsa js_delete"  target="_blank" data-id="'+data+'" ><i class="fa fa-close"></i> 删除</button>';
                            }}
                        ],fnDrawCallback: function(){
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
                init: function(args) {
                    return e(args);
                }
            }
        } ();

        /**
         * 编制查询
         */
        var table;
        $('button.js_search').on('click',function () {
            if (jobInfoObj.jobId==undefined){
                layer.msg('请先选择岗位');
                return;
            }
            var _year = $.trim($('input[name=year]').val());
            var _month = $.trim($('select[name=month]').val());
            if (table==undefined){
                table = TableDatatablesAjax.init({jobId:jobInfoObj.jobId,year:_year,month:_month});
            }else{
                table.clearAjaxParams();
                table.setAjaxParam('jobId',jobInfoObj.jobId);
                table.setAjaxParam('year',_year);
                table.setAjaxParam('month',_month);
                table.getDataTable().ajax.reload();
            }
        });

        /**
         * 删除编制
         */
        $('#datatable_ajax').on('click','button.js_delete',function () {
            var _id = $(this).data('id');
            layer.confirm('确定删除该编制？',function (index) {
                layer.close(index);
                $.post('<c:url value="/hr/org/delJobPlait"/>', {id: _id}, function (result) {
                    if (result.success) {
                        table.getDataTable().ajax.reload();
                    } else {
                        layer.msg(result.rmsg);
                    }
                }, 'json');
            });
        });


    });



</script>
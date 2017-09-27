<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/admin/global/plugins/bootstrap-datepicker/css/bootstrap-datepicker3.min.css" />"/>
<style>
    .md-radio .radio{
        padding-top: 1px;
    }
</style>

<h1 class="page-title"> 考勤数据导入</h1>
<div class="row">
    <div class="col-md-5">
        <div class="portlet light ">
            <div class="portlet-title">选择排班人员</div>
            <div class="portlet-body">
                <div class="row">
                    <div class="col-md-4 col-sm-4">
                        <label>单位</label>
                        <ul id="companyTree" class="ztree"></ul>
                    </div>
                    <div class="col-md-4 col-sm-4">
                        <label>部门</label>
                        <ul id="departmentTree" class="ztree"></ul>
                    </div>
                    <div class="col-md-4 col-sm-4">
                        <label><input type="checkbox" class="checkboxes js-choose-ps" value="0">选择人员</label>
                        <div id="psnScrollContainer">
                            <ul id="empTree" class="ztree"></ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="col-md-1">
        <div>
            <button class="btn btn-default js-psn-add">添加</button>
        </div>

    </div>
    <div class="col-md-6">
        <div class="portlet light ">
            <div class="portlet-title">已选人员</div>
            <div class="portlet-body">
                <div class="psnTableScroll">
                    <table class="table table-width table-striped table-bordered table-advance table-hover table-checkable " id="js-psn-table">
                        <tr>
                            <th>序号</th>
                            <th>公司</th>
                            <th>部门</th>
                            <th>员工姓名</th>
                            <th>操作</th>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="row">
    <div class="col-md-12">
        <div class="portlet light ">
            <div class="portlet-title">选定时间范围</div>
            <div class="portlet-body">
                <div class="row">
                    <div class="col-md-6">
                        <form  role="form" class="js_base_form form-horizontal">
                            <div class="form-body">
                                <div class="form-group">
                                    <label class="col-md-3 control-label">开始日期：</label>
                                    <div class="col-md-9">
                                        <div class="input-group bootstrap-timepicker timepicker">
                                            <input type="text" class="form-control js_datepicker" name="startDate"  value="">
                                            <span class="input-group-btn">
                                                <button class="btn default" type="button">
                                                    <i class="fa fa-calendar"></i>
                                                </button>
                                            </span>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 control-label">结束日期：</label>
                                    <div class="col-md-9">
                                        <div class="input-group bootstrap-timepicker timepicker">
                                            <input type="text" class="form-control js_datepicker" name="endDate"  value="">
                                            <span class="input-group-btn">
                                            <button class="btn default" type="button">
                                                <i class="fa fa-calendar"></i>
                                            </button>
                                        </span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </form>
                        <div class="js_oper_form">
                            <button class="btn blue " type="button" data-action="save">保存</button>
                            <a class="btn default "  href="${base}/hr/train/attDetailList">返回列表</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>



<script type="text/javascript" src="<c:url value="/resources/plugIn/zTree/js/jquery.ztree.excheck.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.zh-CN.js" />"></script>

<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/jquery-validation/js/jquery.validate.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/jquery-validation/js/additional-methods.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/jquery-validation/js/messages_zh.min.js" />"></script>

<script>

    $(function () {

        $('#psnScrollContainer').slimScroll({height:"650px"});
        $('div.psnTableScroll').slimScroll({height:"675px"});

        var companyTreeObj;
        var departmentTreeObj;
        var psnTreeObj;
        var initTree = function (_arr,treeId,callback) {
            var setting = {
                callback:{
                    onClick:function (event, treeId, treeNode) {
                        if ("function" == typeof(callback)){
                            callback(event,treeId,treeNode);
                        }
                    }
                },
                data:{
                    key:{
                        children:"childList",
                        title:"name"
                    }
                },view:{
                    expandSpeed:''
                }
            };
            return $.fn.zTree.init($(treeId), setting, _arr);
        }


        var getEmpByDepartment=function(orgId, deptId){
            $('input.js-choose-ps').prop('checked',false).parent('span').removeClass('checked');
            $.post('<c:url value="/hr/getAllEmpListData"/>',{company:orgId,department:deptId},function (result) {
                if (result){
                    var setting = {
                        check:{
                            enable: true,
                            chkStyle:"checkbox",
                            autoCheckTrigger: true
                        },callback:{
                            onClick:function (event, treeId, treeNode) {
                                psnTreeObj.checkNode(treeNode);
                            }
                        },data:{
                            key:{
                                children:"childList",
                                title:"name"
                            }
                        }
                    };
                    psnTreeObj = $.fn.zTree.init($('#empTree'), setting, result);
                }
            },'json');
        }

        var getDepartments = function (orgId) {
            $('input.js-choose-ps').prop('checked',false).parent('span').removeClass('checked');
            $.post('<c:url value="/hr/org/getAllDepartments"/>',{companyId:orgId},function (result) {
                if (result.success){
                    departmentTreeObj = initTree(result.rdata,"#departmentTree", function (event, treeId, treeNode) {
                        getEmpByDepartment(orgId,treeNode.id);
                    });
                }
            },'json');
        };

        (function () {
            $.post('<c:url value="/hr/org/getAllCompanies"/>', function (result) {
                if (result.success) {
                    companyTreeObj = initTree(result.rdata, "#companyTree", function (event, treeId, treeNode) {
                        $.fn.zTree.destroy('empTree');
                        getDepartments(treeNode.id);
                    });
                    var nodes = companyTreeObj.getNodes();
                    companyTreeObj.expandNode(nodes[0],true,false,false);
                }
            }, 'json');
        })();

        $('input.js-choose-ps').on('click',function () {
            var nodes = psnTreeObj.getNodes();
            for(var i = 0; i<nodes.length;i++){
                psnTreeObj.checkNode(nodes[i]);
            }
        });

        $('button.js-psn-add').on('click',function () {
            if (psnTreeObj){
                var enodes = psnTreeObj.getCheckedNodes(true);
                var _arr = new Array();
                $('#js-psn-table tr:gt(0)').each(function () {
                        _arr.push($(this).data('id'));
                });
                $(enodes).each(function (k, v) {
                    if (_arr.length==0||_arr.indexOf(v.id)==-1){
                        var _tr = $('<tr>');
                        _tr.data('id',v.id);
                        _tr.data('badge',v.badgenumber);
                        _tr.append($('<td>').text(k+1));
                        _tr.append($('<td>').text(companyTreeObj.getSelectedNodes()[0].name));
                        _tr.append($('<td>').text(departmentTreeObj.getSelectedNodes()[0].name));
                        _tr.append($('<td>').text(v.name));
                        _tr.append($('<td>').append('<button class="btn btn-default btn-sm">删除</button>'));
                        $('#js-psn-table').append(_tr);
                    }
                });
            }
        });

        $('#js-psn-table').on('click','button',function () {
            var _index = null;
            var _this = this;
            _index = layer.confirm('是否确定删除？',function () {
                layer.close(_index);
                $(_this).parents('tr').remove();
            });
        });

        var startDate=null,endDate =null;
        $('input[name=startDate]').datepicker({
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
        }).parent(".input-group").on("click", ".input-group-btn", function (t) {
            t.preventDefault(), $(this).prev('input').datepicker("show")
        });

        $('input[name=endDate]').datepicker({
            autoclose : true,
            language: 'zh-CN',
            format : "yyyy-mm-dd"
        }).on('changeDate',function(e){
            endDate = e.date.getTime();
            $('input[name=startDate]').datepicker('setEndDate',new Date(endDate));
            if (null!=startDate && null!=endDate){
                $('input[name=duration]').val(Math.floor((endDate-startDate)/((24*3600*1000)))+1);
            }
        }).parent(".input-group").on("click", ".input-group-btn", function (t) {
            t.preventDefault(), $(this).prev('input').datepicker("show")
        });


        var validateForm = $('form.js_base_form');
        validateForm.validate({
            errorElement : 'span',
            errorClass : 'help-block help-block-error',
            rules : {
                startDate : {
                    required : true
                },endDate: {
                    required : true
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
                element.parents('div.col-md-9').append(error);
            }
        });

        /**
         * 保存排班
         */
        var _index=null;
        $('div.js_oper_form').on('click','button',function () {
            var _len = $('#js-psn-table').find('tr:gt(0)').length;
            if(_len){
                if (validateForm.valid()){
                    var dataObj = {};
                    var _arr = validateForm.serializeArray();
                    for(var e in  _arr){
                        dataObj[_arr[e].name]=_arr[e].value;
                    }
                    var  empIds = new Array();
                    var  badgenumbers = new Array();
                    $('#js-psn-table').find('tr:gt(0)').each(function () {
                        empIds.push($(this).data('id'));
                        badgenumbers.push($(this).data('badge'));
                    });
                    dataObj.empIdsStr = JSON.stringify(empIds);
                    dataObj.badgenumbersStr = JSON.stringify(badgenumbers);
                    _index = layer.load(2);
                    $.post('<c:url value="/hr/atte/importAttDetail"/>', dataObj, function (result) {
                        if (result.success) {
                            layer.close(_index);
                            layer.msg('操作成功',{time:800},function () {
                                window.location.reload();
                            });
                        }else{
                            layer.close(_index);
                            layer.msg('操作失败：'+result.rmsg,{time:2000});
                        }
                    }, 'json');
                }
            }else{
                layer.msg('请选定需要排班的人员');
            }
        });


    });
</script>
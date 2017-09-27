<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/admin/global/plugins/bootstrap-datepicker/css/bootstrap-datepicker3.min.css" />"/>
<style>
    .md-radio .radio{
        padding-top: 1px;
    }
</style>

<h1 class="page-title"> 排班管理</h1>
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
        <div class="">
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
            <div class="portlet-title">排班</div>
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
                            <div class="form-group">
                                <label class="control-label col-md-3">遇公共假日，排班照旧</label>
                                <div class="col-md-9">
                                    <div class="md-radio-inline">
                                        <label class="md-radio"><input type="radio" value="1" name="forced">是 </label>
                                        <label class="md-radio"><input type="radio" value="0" name="forced">否</label>
                                    </div>
                                </div>
                            </div>
                            <%--<div class="form-group">--%>
                                <%--<label class="control-label col-md-3">覆盖已有工作日历</label>--%>
                                <%--<div class="col-md-9">--%>
                                    <%--<div class="md-radio-inline">--%>
                                        <%--<label class="md-radio"><input type="radio" value="1"  name="cover">是<span></span></label>--%>
                                        <%--<label class="md-radio"><input type="radio" value="0"  name="cover">否<span></span></label>--%>
                                    <%--</div>--%>
                                <%--</div>--%>
                            <%--</div>--%>
                            <div class="form-group">
                                <label class="control-label col-md-3">工作周期(天)</label>
                                <div class="col-md-9">
                                    <input type="text" class="form-control" value="1" name="cycle">
                                    <%--<div class="input-group">--%>
                                        <%--<input type="text" class="form-control" value="1" name="cycle">--%>
                                        <%--<span class="input-group-btn js-cycle-set"><button type="button" class="btn btn-primary">设置</button></span>--%>
                                    <%--</div>--%>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-3">设置班次</label>
                                <div class="col-md-9">
                                    <ul class="list-group js_shift_list">
                                        <li class="list-group-item">
                                            <div class="input-group">
                                                <input type="text" class="form-control" readonly name="cycleDetail">
                                                <span class="input-group-btn"><button type="button" class="btn green">选择</button></span>
                                            </div>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </form>
                    <div class="js_oper_form">
                        <button class="btn blue " type="button" data-action="save">保存</button>
                        <a class="btn default "  href="${base}/hr/train/list">返回列表</a>
                    </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>



<div class="modal fade js_shift_div">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title">班次选择</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-12">
                        <table class="table table-striped table-bordered  table-hover">
                            <tr>
                                <th></th>
                                <th>班次名称</th>
                                <th>上班时间</th>
                                <th>下班时间</th>
                            </tr>
                            <c:forEach var="s" items="${shiftList}">
                                <tr>
                                    <td><input type="radio" value="${s.id}" name="shift" data-name="${s.schName}"></td>
                                    <td>${s.schName}</td>
                                    <td>${s.startTime}</td>
                                    <td>${s.endTime}</td>
                                </tr>
                            </c:forEach>
                        </table>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default js_cancel_btn" data-dismiss="modal">取消</button>
                    <button type="button" class="btn btn-primary js_save_btn">确定</button>
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


        $('input.js_datepicker').datepicker({
            autoclose : true,
            language: 'zh-CN',
            format : "yyyy-mm-dd",
            todayHighlight : true
        }).parent(".input-group").on("click", ".input-group-btn", function (t) {
            t.preventDefault(), $(this).prev('input').datepicker("show")
        });

        /**
         * 设置周期
         */

        $('input[name=cycle]').on('blur',function () {
            var _len = $(this).val();
            if(_len&&_len>0){
                var _oldLen = $('ul.js_shift_list li').length;
                if(_len > _oldLen){
                    for(var i=0;i<_len-_oldLen;i++){
                        var _html = ['<li class="list-group-item">',
                            '	<div class="input-group">',
                            '		<input type="text" class="form-control" readonly name="cycleDetail">',
                            '		<span class="input-group-btn"><button type="button" class="btn green">选择</button></span>',
                            '	</div>',
                            '</li>'].join("");
                        $('ul.js_shift_list').append(_html);
                    }
                }else{
                    for(var i=0;i<_oldLen-_len;i++){
                        $('ul.js_shift_list li:last').remove();
                    }
                }
            }
        });

//        $('span.js-cycle-set').on('click',function () {
//            var _len = $(this).prev('input').val();
//            if(_len&&_len>0){
//                var _oldLen = $('ul.js_shift_list li').length;
//                if(_len > _oldLen){
//                    for(var i=0;i<_len-_oldLen;i++){
//                        var _html = ['<li class="list-group-item">',
//                            '	<div class="input-group">',
//                            '		<input type="text" class="form-control" readonly name="cycleDetail">',
//                            '		<span class="input-group-btn"><button type="button" class="btn green">选择</button></span>',
//                            '	</div>',
//                            '</li>'].join("");
//                        $('ul.js_shift_list').append(_html);
//                    }
//                }else{
//                    for(var i=0;i<_oldLen-_len;i++){
//                        $('ul.js_shift_list li:last').remove();
//                    }
//                }
//            }
//        });

        var _currCycle=null;
        /**
         * 打开选择班次弹窗
         */
        $('ul.js_shift_list').on('click','button',function () {
            _currCycle = $(this).parents('li').index();
            $('div.js_shift_div').modal('show');
        });

        /**
         * 选中班次
         */
        $('div.js_shift_div table').on('click','tr:gt(0)',function () {
            $('input[name=shift]').prop('checked',false).parent('span').removeClass('checked');
            $(this).find('input[name=shift]').prop('checked',true).parent('span').addClass('checked');
        });

        $('div.js_shift_div').on('click','button.js_save_btn',function () {
            var _checked = $('input[name=shift]:checked');
            $('ul.js_shift_list li:eq("'+_currCycle+'")').find('input').val(_checked.data('name')).data('id',_checked.val());
            $('div.js_shift_div').modal('hide');
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
                },forced : {
                    required : true
//                },cover: {
//                    required : true
                },cycle : {
                    required : true,
                    digits:true
                },cycleDetail : {
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
                    if($('ul.js_shift_list li').length==$('input[name=cycle]').val()){
                        var dataObj = {};
                        var _arr = validateForm.serializeArray();
                        for(var e in  _arr){
                            dataObj[_arr[e].name]=_arr[e].value;
                        }
                        var  empIds = new Array();
                        $('#js-psn-table').find('tr:gt(0)').each(function () {
                            empIds.push($(this).data('id'));
                        });
                        dataObj.empIds = JSON.stringify(empIds);
                        var cycleDetail = new Array();
                        $('input[name=cycleDetail]').each(function () {
                            cycleDetail.push($(this).data('id'));
                        })
                        dataObj.cycleDetail = JSON.stringify(cycleDetail);
                        _index = layer.load(2);
                        $.post('<c:url value="/hr/atte/postSchedulEdit"/>', dataObj, function (result) {
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
                    }else{
                        layer.msg('工作周期和排班班次不匹配');
                    }
                }
            }else{
                layer.msg('请选定需要排班的人员');
            }
        });


    });
</script>
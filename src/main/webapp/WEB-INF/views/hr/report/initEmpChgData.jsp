<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<link rel="stylesheet" type="text/css" href="<c:url value="/resources/admin/global/plugins/bootstrap-datepicker/css/bootstrap-datepicker3.min.css" />"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/hr/css/hr.css" />"/>


<div class="portlet box blue-hoki">
    <div class="portlet-title">
        <div class="caption">
            <i class="fa fa-gift"></i>
            异动数据初始化
        </div>
    </div>
    <div class="portlet-body">
        <div class="row">
            <div class="col-md-6 ">
                <form  role="form" class="js_base_form">
                    <div class="form-body">
                        <div class="row">
                            <div class="col-md-6 ">
                                <div class="form-group">
                                    <label>初始化年份（如：2017）</label>
                                    <input type="text" class="form-control" name="year"  value="2017">
                                </div>
                            </div>
                            <div class="col-md-6 ">
                                <div class="form-group">
                                    <label>初始化月份（如： 03，10）</label>
                                    <input type="text" class="form-control" name="month" value="" placeholder="月份为需要统计月份的上一个月">
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6 ">
                                <div class="form-group">
                                    <label>公司</label>
                                    <input type="text" class="form-control" name="company" readonly>
                                </div>
                            </div>
                            <div class="col-md-6 ">
                                <div class="form-group">
                                    <label>部门</label>
                                    <div class="input-group">
                                        <input type="text" class="form-control" name="department" readonly>
                                        <span class="input-group-btn"><button type="button" class="btn btn-primary js_choose_emp">选择</button></span>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6 ">
                                <div class="form-group">
                                    <label>历史在职人数（管理类）</label>
                                    <input type="text" class="form-control" name="oldOnDutyM" value="" readonly>
                                </div>
                            </div>
                            <div class="col-md-6 ">
                                <div class="form-group">
                                    <label>历史在职人数（工人类）</label>
                                    <input type="text" class="form-control" name="oldOnDutyW" value="" readonly>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6 ">
                                <div class="form-group">
                                    <label>在职人数（管理类）</label>
                                    <input type="text" class="form-control" name="onDutyM" value="">
                                </div>
                            </div>
                            <div class="col-md-6 ">
                                <div class="form-group">
                                    <label>在职人数（工人类）</label>
                                    <input type="text" class="form-control" name="onDutyW" value="">
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-8 ">
                                <div class="form-group">
                                    <label>强制初始化：</label>
                                    <label><input type="radio" value="0" checked name="force">否</label>
                                    <label><input type="radio" value="1" name="force">是</label>
                                    （注意：没有特殊需要，请不要勾选是）
                                </div>
                            </div>
                        </div>
                    </div>
                    <button type="button" class="btn green js_save_chg" data-action="save">保存</button>
                    <button type="button" class="btn default" data-action="cancel">返回列表</button>
                </form>
            </div>
            <div class="col-md-6">
                <div class="row">
                    <div class="col-md-6">
                        当前历史操作数据：
                        <div class="list-group js_group_list">

                        </div>
                    </div>
                </div>
            </div>
        </div>

    </div>
</div>

<div class="modal fade js_department_ps_div">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title">公司/部门/人员</h4>
            </div>
            <div class="modal-body">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-md-6">
                            <label>公司</label>
                            <ul id="companyTree" class="ztree"></ul>
                        </div>
                        <div class="col-md-6">
                            <label>部门</label>
                            <ul id="departmentTree" class="ztree"></ul>
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

<script>
    $(document).ready(function () {

        var treesObj={
            companyTreeObj:null,
            departmentTreeObj:null
        };


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

        var getDepartments = function (rootTreeObj,orgId,deptTreeId) {
            $.post('<c:url value="/hr/org/getAllDepartments"/>',{companyId:orgId},function (result) {
                if (result.success){
                    var deptTreeObj = initTree(result.rdata,deptTreeId,function (event,treeId,treeNode) {

                    });
                    rootTreeObj.departmentTreeObj = deptTreeObj;
                }
            },'json');
        }

        var getCompanies = function (rootTreeObj,compTreeId,deptTreeId) {
            $.post('<c:url value="/hr/org/getAllCompanies"/>', function (result) {
                if (result.success) {
                    var treeObj = initTree(result.rdata, compTreeId, function (event, treeId, treeNode) {
                        $.fn.zTree.destroy(deptTreeId);
                        getDepartments(rootTreeObj,treeNode.id,deptTreeId);
                    });
                    var nodes = treeObj.getNodes();
                    treeObj.expandNode(nodes[0],true,false,false);
                    rootTreeObj.companyTreeObj = treeObj;
                }
            }, 'json');
        }

        $('button.js_choose_emp').on('click',function () {
            if(!treesObj.companyTreeObj){
                getCompanies(treesObj,'companyTree','departmentTree');
            }
            $('div.js_department_ps_div').modal('show');
        });



        /**
         * 保存岗位信息
         */
        $('button.js_save_btn').on('click',function () {
            var cnodes = treesObj.companyTreeObj.getSelectedNodes();
            if(cnodes.length==1) {
                $('input[name=company]').val(cnodes[0].name);
                initData.company=cnodes[0].id;
                var dnodes = treesObj.departmentTreeObj.getSelectedNodes();
                if(dnodes.length==1) {
                    $('input[name=department]').val(dnodes[0].name);
                    initData.department=dnodes[0].id;
                    var month =$('input[name=month]').val();
                    var year = $('input[name=year]').val();
                    if(!year||!month){
                        layer.msg('请先填写年份和月份！');
                        return;
                    }
                    if (year.length !=4 || month.length !=2){
                        layer.msg('年份或月份格式错误');
                        return;
                    }

                    $.ajax({
                        type:"post",
                        url:'<c:url value="/hr/reports/getEmpChgData"/>',
                        dataType:"json",
                        data:{company:initData.company,department:initData.department,year:year,month:month},
                        success: function (result) {
                            if (result.success){
                                var data = result.rdata;
                                $('input[name=oldOnDutyM]').val(data.onDutyM);
                                $('input[name=oldOnDutyW]').val(data.onDutyW);
                            }else {
                                layer.msg("未获取到初始化数据",{icon:0})
                                $('input[name=oldOnDutyM]').val(0);
                                $('input[name=oldOnDutyW]').val(0);
                            }
                        }
                    });

                    var url = '<c:url value="/hr/reports/queryInitEmpChgData/"/>';
                    url+=initData.company+'/'+initData.department+'/'+year+'/'+month;
                    $.ajax({
                        type:"get",
                        url:url,
                        dataType:"json",
                        async:false,
                        success: function (result) {
                            if (result.success) {
                                var data = result.rdataMap;
                                $('input[name=onDutyM]').val(data.m);
                                $('input[name=onDutyW]').val(data.w);
                            }
                        }
                    });
                    $('div.js_department_ps_div').modal('hide');

                }else{
                    layer.msg("请选择部门");
                }
            }else{
                layer.msg("请选择公司");
            }
        });

        var initData = {year:null,month:null,company:null,department:null,onDutyM:null,onDutyW:null};
        var initDataErrorMsg = {year:"年份不能为空",month:"月份不能为空，例：05",company:"公司不能为空",department:"部门不能为空",onDutyM:"在职人数（管理类）不能为空",onDutyW:"在职人数（工人类）不能为空"};

        var checkObj = function (initData) {
            var flag = true;
            for(var e in initData){
                if(!initData[e]){
                    layer.msg(initDataErrorMsg[e],{time:1500});
                    flag = false;
                    break;
                }
            }
            return flag;
        }

        var _index=null;
        $('button.js_save_chg').on('click',function () {
            var year = $('input[name=year]').val();
            var month =$('input[name=month]').val();
            if(!year||!month){
                layer.msg('请先填写年份和月份！');
                return;
            }
            if (year.length !=4 || month.length !=2){
                layer.msg('年份或月份格式错误');
                return;
            }

            initData.year = year;
            initData.month = month;
            initData.onDutyM = $('input[name=onDutyM]').val();
            initData.onDutyW = $('input[name=onDutyW]').val();
            initData.force = $('input[name=force]:checked').val();


            if(checkObj(initData)){
                if(initData.force==1){
                    var _index = layer.confirm('注意！您已经选择强制初始化，确定要这样？',function () {
                        layer.close(_index);
                        postData();
                    });
                }else{
                    postData();
                }
            }
        });

        var postData = function () {
            _index = layer.load(2);
            $.post('<c:url value="/hr/reports/initEmpChgData"/>',initData, function (result) {
                if (result.success) {
                    layer.close(_index);
                    layer.msg('操作成功',{time:800},function () {
                        var _html = $('<span class="list-group-item"></span>');
                        _html.text($('input[name=company]').val()+'-'+$('input[name=department]').val()+'');
                        $('div.js_group_list').append(_html);
                    });
                }else{
                    layer.close(_index);
                    layer.msg('操作失败：'+result.rmsg,{time:2500});
                }
            }, 'json');
        }


    });


</script>
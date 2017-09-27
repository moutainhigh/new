<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<link rel="stylesheet" type="text/css" href="<c:url value="/resources/admin/global/plugins/bootstrap-select/css/bootstrap-select.min.css" />" media="screen"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/admin/global/plugins/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" />" media="screen"/>
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
            <span class="caption-subject font-green sbold uppercase">员工考勤明细表</span>
        </div>
    </div>
    <div class="row">
    <form class="navbar-form" role="search" id="signSearchForm">

            <div class="form-group col-md-2">
                <div class="input-group date form_date col-md-12" data-date="" data-date-format="yyyy-mm-dd" data-link-field="startTime1" data-link-format="yyyy-mm-dd" style="width: 100%">
                    <input class="form-control" name="startTime1" size="16" type="text" placeholder="开始时间" value="<fmt:formatDate value="${startTime}" pattern="yyyy-MM-dd"/>" >
                    <input class="form-control" name="startTime" size="16" type="hidden" placeholder="开始时间" value="<fmt:formatDate value="${startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>" >
                </div>
            </div>

            <div class="form-group col-md-2">
                <div class="input-group date form_date col-md-12" data-date="" data-date-format="yyyy-mm-dd" data-link-field="endTime1" data-link-format="yyyy-mm-dd" style="width: 100%">
                    <input class="form-control" name="endTime1" size="16" type="text" placeholder="结束时间" value="<fmt:formatDate value="${endTime}" pattern="yyyy-MM-dd"/>">
                    <input class="form-control" name="endTime" size="16" type="hidden" placeholder="结束时间" value="<fmt:formatDate value="${endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>">
                </div>
            </div>

        <div class="col-md-3">
            <div class="form-group col-md-12">
                <div class="col-md-2" style="padding-left: 0px;padding-right: 0px;padding-top: 5px;margin-bottom: 20px"><label>单位部门</label></div>
                <div class="col-md-8"><textarea class="form-control" rows="1" name="empName" readonly></textarea></div>
                <div class="col-md-2"><button type="button" class="btn btn-primary js_choose_emp">选择</button></div>
                <input type="hidden" name="code"/>
                <input type="hidden" name="departmentId"/>
            </div>
        </div>

            <div class="form-group col-md-2">
                <div class="input-group col-md-12">
                    <input type="text"  name="username" class="form-control" value="" placeholder="姓名">
                </div>
            </div>

            <div class="form-group col-md-2">
                <div class="input-group col-md-12" id="js_btn_div">
                    <button type="button"  class="btn btn-default js_search">搜索</button>
                    <button type="button" class="btn btn-default js_reset">重置</button>
                </div>
            </div>



    </form>
    </div>

    <br/>
    <ul id="myTab" class="nav nav-tabs">
           <li><a href="#1" onclick="clickTab(1);" title="1" data-toggle="tab">打卡明细</a></li>
           <li><a href="#3" onclick="clickTab(3);" title="3" data-toggle="tab">异常打卡明细</a></li>
        <li><a href="#2" onclick="clickTab(2);" title="2" data-toggle="tab">OA外出明细</a></li>
        <li><a href="#4" onclick="clickTab(4);" title="4" data-toggle="tab">OA请假明细</a></li>
           <li><a href="#5" onclick="clickTab(5);" title="5" data-toggle="tab">OA补登明细</a></li>
    </ul>

    <div id="myTabContent" class="tab-content">
        <div class="tab-pane fade in active" id="1" style="display: none">
            <div class="portlet-body">
                <div class="row" style="float: right;position: relative;left: -100px">
                    <button class="btn green js_export_sign_record"><i class="fa fa-edit"></i>导出考勤明细</button>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <table class="table table-width table-striped table-bordered table-advance table-hover table-checkable" id="datatable_ajax1">
                            <thead>
                                <tr role="row" class="heading">
                                    <th width="10%">员工编号</th>
                                    <th width="5%">姓名</th>
                                    <th width="10%">管理单位</th>
                                    <th width="10%">管理部门</th>
                                    <th width="10%">手机号</th>
                                    <th width="10%">日期</th>
                                    <th width="10%">打卡时间</th>
                                    <th width="10%">打卡类型</th>
                                    <th width="10%">星期</th>
                                    <th width="10%">打卡方式</th>
                                    <th width="5%">描述</th>
                                </tr>
                            </thead>
                        </table>
                    </div>
                </div>
            </div>
        </div>

        <div class="tab-pane fade in active" id="2" style="display: none">
            <div class="portlet-body">
                <div class="row" style="float: right;position: relative;left: -100px">
                    <button class="btn green js_export_egress_record"><i class="fa fa-edit"></i>导出外出明细</button>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <table class="table table-width table-striped table-bordered table-advance table-hover table-checkable" id="datatable_ajax2">
                            <thead>
                                <tr role="row" class="heading">
                                    <th width="3%">员工编号</th>
                                    <th width="4%">姓名</th>
                                    <th width="4%">管理单位</th>
                                    <th width="5%">管理部门</th>
                                    <th width="5%">身份证号码</th>
                                    <th width="5%">手机号码</th>
                                    <th width="5%">日期</th>
                                    <th width="5%">外出时间</th>
                                    <th width="5%">返回时间</th>
                                    <th width="5%">外出地点</th>
                                    <th width="5%">外出摘要</th>
                                </tr>
                            </thead>
                        </table>
                    </div>
                </div>
            </div>
        </div>

        <div class="tab-pane fade in active" id="3" style="display: none">
            <div class="portlet-body">
                <div class="row" style="float: right;position: relative;left: -100px">
                    <%--<a href="<c:url value="/admin/sign/exportUnusualSignDetail"/>"><button class="btn green js_patch_record"><i class="fa fa-edit"></i>导出异常明细</button></a>--%>
                    <button class="btn green js_export_unusual_record"><i class="fa fa-edit"></i>导出异常明细</button>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <table class="table table-width table-striped table-bordered table-advance table-hover table-checkable" id="datatable_ajax3">
                            <thead>
                                <tr role="row" class="heading">
                                    <th width="3%">员工编码</th>
                                    <th width="4%">姓名</th>
                                    <th width="4%">单位</th>
                                    <th width="4%">部门</th>
                                    <th width="5%">职位</th>
                                    <th width="5%">身份证号码</th>
                                    <th width="5%">手机号码</th>
                                    <th width="5%">日期</th>
                                    <th width="5%">打卡时间</th>
                                    <th width="5%">打卡类型</th>
                                    <th width="5%">异常状态</th>
                                    <%--<th width="5%">操作</th>--%>
                                </tr>
                            </thead>
                        </table>
                    </div>
                </div>
            </div>
        </div>

        <div class="tab-pane fade in active" id="4" style="display: none">
            <div class="portlet-body">
                <div class="row" style="float: right;position: relative;left: -100px">
                    <button class="btn green js_export_leave_record"><i class="fa fa-edit"></i>导出请假明细</button>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <table class="table table-width table-striped table-bordered table-advance table-hover table-checkable" id="datatable_ajax4">
                            <thead>
                                <tr role="row" class="heading">
                                    <th width="3%">员工编号</th>
                                    <th width="4%">姓名</th>
                                    <th width="4%">管理单位</th>
                                    <th width="5%">管理部门</th>
                                    <th width="5%">身份证号</th>
                                    <th width="5%">请假类别</th>
                                    <th width="5%">开始时间</th>
                                    <th width="5%">结束时间</th>
                                    <th width="5%">请假天数</th>
                                    <th width="5%">调休天数</th>
                                    <th width="5%">请假事由</th>
                                    <th width="5%">备注</th>
                                </tr>
                            </thead>
                        </table>
                    </div>
                </div>
            </div>
        </div>

        <div class="tab-pane fade in active" id="5" style="display: none">
            <div class="portlet-body">
                <div class="row" style="float: right;position: relative;left: -100px">
                    <button class="btn green js_export_patch_record"><i class="fa fa-edit"></i>导出补登明细</button>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <table class="table table-width table-striped table-bordered table-advance table-hover table-checkable" id="datatable_ajax5">
                            <thead>
                            <tr role="row" class="heading">
                                <th width="3%">员工编号</th>
                                <th width="4%">姓名</th>
                                <th width="4%">管理单位</th>
                                <th width="5%">管理部门</th>
                                <th width="5%">职位</th>
                                <th width="5%">手机号码</th>
                                <th width="5%">补登日期</th>
                                <th width="5%">补登类型</th>
                                <th width="5%">补登说明</th>
                            </tr>
                            </thead>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="editModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="myModalLabelAdd">异常补签</h4>
            </div>
            <div class="modal-body">
                <form  role="form" class="js_base_form" id="unusualEditForm" method="post">
                    <div class="form-body">
                        <div class="row">
                            <div class="col-md-6 ">
                                <div class="form-group">
                                    <label>姓名：</label>
                                    <input type="hidden" class="form-control" name="userid" />
                                    <input type="text" readonly class="form-control" name="username" />
                                </div>
                            </div>

                            <div class="col-md-6 ">
                                <div class="form-group">
                                    <label>单位：</label>
                                    <input type="text" readonly class="form-control" name="company" />
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6 ">
                                <div class="form-group">
                                    <label>部门：</label>
                                    <input type="text" readonly class="form-control" name="department" />
                                </div>
                            </div>

                            <div class="col-md-6 ">
                                <div class="form-group">
                                    <label>职位：</label>
                                    <input type="text" readonly class="form-control" name="position" />
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-6 ">
                                <div class="form-group">
                                    <label>电话：</label>
                                    <input type="text" readonly class="form-control" name="mobile" />
                                </div>
                            </div>

                            <div class="col-md-6 ">
                                <div class="form-group">
                                    <label>日期：</label>
                                    <input type="text" readonly class="form-control" name="checkDate" />
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-6 ">
                                <div class="form-group">
                                    <label>补签类型：</label>
                                    <input type="hidden" readonly class="form-control" name="checkin_type" />
                                    <input type="text" readonly class="form-control" name="checkin_type_name" />
                                </div>
                            </div>

                            <div class="col-md-6 ">
                                <div class="form-group">
                                    <label>补签时间：</label>
                                    <%--<input type="text" class="form-control" name="checkin_time" />--%>
                                    <div class="input-group date form_date col-md-12" data-date="" data-date-format="yyyy-mm-dd hh:ii:ss" data-link-field="update_checkin_time" data-link-format="yyyy-mm-dd hh:ii:ss" style="width: 100%">
                                        <input class="form-control" name="update_checkin_time" size="16" type="text" placeholder="补签时间">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary">补签</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade js_department_ps_div" data-backdrop="static">
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
                            <div class="form-group">
                                <label>搜索</label>
                                <div class="input-group">
                                    <input type="text" class="form-control" name="searchPsn">
                                    <span class="input-group-btn">
                                        <button type="button" class="btn btn-primary js_search_psn">确定</button>
                                    </span>
                                </div>
                            </div>
                        </div>
                    </div>
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

<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/bootstrap-select/js/bootstrap-select.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.zh-CN.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/datatables/datatables.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/scripts/datatable.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/plugIn/zTree/js/jquery.ztree.exhide.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/plugIn/zTree/js/jquery.ztree.excheck.js" />"></script>



<script>
    var currCompany = "${company}";
    var currCompanyId ="${companyId}";
    var _temp = 1;
    var table;


    function clickTab(id) {

        //点击选项卡就显示该选项卡,其他隐藏
       var tab = $('#myTab li');
        $.each(tab, function (i, j) {
            var _id = $(j).find('a').attr("title");
            if(_id != id){
                var adDiv = document.getElementById(_id);
                adDiv.style.display="none";
            }else {
                var adDiv = document.getElementById(_id);
                adDiv.style.display="block";
            }
        });

        //再次点击就不再初始化这个table
        if ($('#'+id).attr("flag")) {
            return;
        }
        $('#'+id).attr("flag",true);

        /*if ($.fn.dataTable.isDataTable('#datatable_ajax'+id)) {
                _table = $('#datatable_ajax'+id).DataTable();
                _table.destroy();
        }*/

        var listUrl = "";
        var columns = "" ;
        if (1==id) {
            //打卡明细
            listUrl = "<c:url value="/admin/sign/listSignDetailData"/>";
            columns = [
                {"data":"userNum","render" : function(data, type, full, meta ){
                    return data==undefined?'':data;
                }},
                {"data":"username","render" : function(data, type, full, meta ){
                    return data==undefined?'':data;
                }},
                {"data":"company","render" : function(data, type, full, meta ){
                    return data==undefined?'':data;
                }},
                {"data":"department","render" : function(data, type, full, meta ){
                    return data==undefined?'':data;
                }},
                {"data":"telephone","render" : function(data, type, full, meta ){
                    return data==undefined?'':data;
                }},
                {"data":"checkDate","render" : function(data, type, full, meta ){
                    return data==undefined?'':data;
                }},
                {"data":"checkinTimeStr","render" : function(data, type, full, meta ){
                    if (full.status == 0 || full.status == "" || full.status == "undefined" || full.status == 10) {
                        return data==undefined?'':data;
                    } else if (full.status == 1) {
                        return data==undefined?'':data+"(迟到)";
                    } else if (full.status == 2) {
                        return data==undefined?'':data+"(早退)";
                    } else if (full.status == 3 || full.status == 4 || full.status == 8 || full.status == 9) {
                        return "无";
                    } else if (full.status == 7) {
                        return data==undefined?'':data+"(地点异常)";
                    }
                    if(full.exception_type == 2 && (full.status == 6 || full.status == 5)) {
                        if(full.checkin_type == 1) {
                            return data==undefined?'':data+"(迟到)";
                        } else if(full.checkin_type == 2) {
                            return data==undefined?'':data+"(早退)";
                        }
                    }
                }},
                {"data":"checkin_type","render" : function(data, type, full, meta ){
                    if (data == 1) {
                        return "上班打卡";
                    } else {
                        return "下班打卡";
                    }
                }},
                {"data":"week","render" : function(data, type, full, meta ){
                    return data==undefined?'':data;
                }},
                {"data":"groupname","render" : function(data, type, full, meta ){
                    return data==undefined?'':data;
                }},
                {"data":"status","render" : function(data, type, full, meta ){
                    if (data == 3) {
                        return "未签到";
                    } else if (data == 4) {
                        return "未签退";
                    } else if (data == 5 || data == 6) {
                        return "旷工";
                    } else if (data == 7) {
                        return "地点异常";
                    } else if (data == 8) {
                        return "请假";
                    } else if (data == 9) {
                        return "外出";
                    }  else if (data == 1) {
                        return "迟到";
                    }  else if (data == 2) {
                        return "早退";
                    }  else if (data == 10) {
                        return "补登";
                    } else {
                        return '正常';
                    }
                }}
            ];
        } else if (2 == id) {
            //外出登记
            listUrl = "<c:url value="/admin/sign/egressListData"/>";
            columns = [
                {"data":"userNum","render" : function(data, type, full, meta ){
                    return data==undefined?'':data;
                }},
                {"data":"username","render" : function(data, type, full, meta ){
                    return data==undefined?'':data;
                }},
                {"data":"company","render" : function(data, type, full, meta ){
                    return data==undefined?'':data;
                }},
                {"data":"department","render" : function(data, type, full, meta ){
                    return data==undefined?'':data;
                }},
                {"data":"idCard","render" : function(data, type, full, meta ){
                    return data==undefined?'':data;
                }},
                {"data":"telephone","render" : function(data, type, full, meta ){
                    return data==undefined?'':data;
                }},
                {"data":"egressDate","render" : function(data, type, full, meta ){
                    return data==undefined?'':data;
                }},
                {"data":"startTime","render" : function(data, type, full, meta ){
                    return data==undefined?'':data;
                }},
                {"data":"endTime","render" : function(data, type, full, meta ){
                    return data==undefined?'':data;
                }},
                {"data":"locationDetail","render" : function(data, type, full, meta ){
                    return data==undefined?'':data;
                }},
                {"data":"notes","render" : function(data, type, full, meta ){
                    return data==undefined?'':data;
                }}
            ];
        } else if (3 == id) {
            //异常考勤
            listUrl = "<c:url value="/admin/sign/unusualListData"/>";
            columns = [
                {"data":"userNum","render" : function(data, type, full, meta ){
                    return data==undefined?'':data;
                }},
                {"data":"username","render" : function(data, type, full, meta ){
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
                {"data":"idCard","render" : function(data, type, full, meta ){
                    return data==undefined?'':data;
                }},
                {"data":"mobile","render" : function(data, type, full, meta ){
                    return data==undefined?'':data;
                }},
                {"data":"checkDate","render" : function(data, type, full, meta ){
                    return data==undefined?'':data;
                }},
                {"data":"checkin_time","render" : function(data, type, full, meta ){
                    if (full.exception_type=="1") {
                        return "无"
                    } else {
                        return data==undefined?'':data;
                    }
                }},
                {"data":"checkin_type","render" : function(data, type, full, meta ){
                    if ("1"==data) {
                        return "上班打卡";
                    } else if("2"==data) {
                        return "下班打卡";
                    } else {
                        return "";
                    }
                }},
                {"data":"status","render" : function(data, type, full, meta ){
                    if ("1" == data) {
                        return "迟到";
                    } else if ("2" == data) {
                        return "早退";
                    } else if ("3" == data) {
                        return "未签到";
                    } else if ("4" == data) {
                        return "未签退";
                    } else if ("5" == data) {
                        return "旷工半天";
                    } else if ("6" == data) {
                        return "旷工一天";
                    } else if ("7" == data) {
                        return "地点异常";
                    } else if ("8" == data) {
                        return "请假";
                    } else if ("9" == data) {
                        return "外出";
                    }
                }},
            /*{"data":"id","render" : function(data, type, full, meta ){
                    var checkDate = full.checkDate;
                    var checkin_type_name;
                    if (full.checkin_type == 1) {
                        checkin_type_name = "上班打卡";
                    } else if(full.checkin_type == 2) {
                        checkin_type_name = "下班打卡";
                    }
                    return '<a href="javascript:void(0)" class="btn btn-sm btn-outline grey-salsa editUnusual"' +
                        ' data-username="'+full.username+'" data-company="'+full.company+'" data-department="'+full.department+'"' +
                        ' data-position="'+full.position+'" data-mobile="'+full.mobile+'" data-checkdate="'+checkDate+'"' +
                        ' data-checkin_type_name="'+checkin_type_name+'" data-checkin_type="'+full.checkin_type+'" data-userid="'+full.userid+'" ><i class="fa fa-search-minus"></i> 补签</a>';
                }}*/
            ];
        }else if (4 == id) {
            //请假
            listUrl = "<c:url value="/admin/sign/listLeaveDetailData"/>";
            columns = [
                {"data":"userNum","render" : function(data, type, full, meta ){
                    return data==undefined?'':data;
                }},
                {"data":"username","render" : function(data, type, full, meta ){
                    return data==undefined?'':data;
                }},
                {"data":"company","render" : function(data, type, full, meta ){
                    return data==undefined?'':data;
                }},
                {"data":"department","render" : function(data, type, full, meta ){
                    return data==undefined?'':data;
                }},
                {"data":"idCard","render" : function(data, type, full, meta ){
                    return data==undefined?'':data;
                }},
                {"data":"leaveType","render" : function(data, type, full, meta ){
                    return data==undefined?'':data;
                }},
                {"data":"startLeave","render" : function(data, type, full, meta ){
                    return data==undefined?'':data;
                }},
                {"data":"endLeave","render" : function(data, type, full, meta ){
                    return data==undefined?'':data;
                }},
                {"data":"leaveDayCount","render" : function(data, type, full, meta ){
                    return data==undefined?'':data;
                }},
                {"data":"restChanged","render" : function(data, type, full, meta ){
                    return data==undefined?'':data;
                }},
                {"data":"reason","render" : function(data, type, full, meta ){
                    return data==undefined?'':data;
                }},
                {"data":"notes","render" : function(data, type, full, meta ){
                    return data==undefined?'':data;
                }}
            ];
        } else if (5 == id) {
            //补登
            listUrl = "<c:url value="/admin/sign/listPatchDetailData"/>";
            columns = [
                {"data":"userNum","render" : function(data, type, full, meta ){
                    return data==undefined?'':data;
                }},
                {"data":"username","render" : function(data, type, full, meta ){
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
                {"data":"telephone","render" : function(data, type, full, meta ){
                    return data==undefined?'':data;
                }},
                {"data":"patchDate","render" : function(data, type, full, meta ){
                    return data==undefined?'':data;
                }},
                {"data":"patchType","render" : function(data, type, full, meta ){
                    if (data == 1) {
                        return "上班补登";
                    } else if(data == 2) {
                        return "下班补登";
                    }
                }},
                {"data":"notes","render" : function(data, type, full, meta ){
                    return data==undefined?'':data;
                }}
            ];
        }
        TableDatatablesAjax = function() {
            var  e = function() {
                var table = new Datatable;
                table.init({
                    src: $("#datatable_ajax"+id),
                    loadingMessage: "Loading...",
                    dataTable: {
                        bStateSave: !0,
                        lengthMenu: [[10, 20, 50, 100], [10, 20, 50, 100]],
                        searching:true,
                        pageLength: 10,

                        ajax: {
                            url: listUrl,
                            type:'POST',
                            data:function (d) {
                                d.username = $.trim($('input[name=username]').val());
                                d.startTime = $.trim($('input[name=startTime]').val());
                                d.endTime = $.trim($('input[name=endTime]').val());
                                d.mobile = $.trim($('input[name=mobile]').val());
                                d.company = $.trim($('select[name=company]').val());
                                d.department = $.trim($('select[name=department]').val());
                                d.code = $.trim($('input[name=code]').val());
                                d.departmentId = $.trim($('input[name=departmentId]').val());
                            }
                        },
                        order: [[1, "asc"]],
                        columns:columns,
                        "initComplete":function () {
                            //alert($("input[name=checkId]").length);
                        }
                    }
                });
                return table;
            };
            return {
                init: function() {
                    _temp++;
                    return e();
                }
            }
        } ();

        $(document).ready(function() {
            var _startTime = $('input[name=startTime]').val();
            var _endTime = $('input[name=endTime]').val();
            var table = TableDatatablesAjax.init();
            $('#datatable_ajax3').on('click','a',function () {
                var _this =$(this);
                var _model=$('#editModal');
                _model.find('input[name=username]').val(_this.data('username'));
                _model.find('input[name=company]').val(_this.data('company'));
                _model.find('input[name=department]').val(_this.data('department'));
                _model.find('input[name=position]').val(_this.data('position'));
                _model.find('input[name=mobile]').val(_this.data('mobile'));
                _model.find('input[name=checkDate]').val(_this.data('checkdate'));
                _model.find('input[name=checkin_type_name]').val(_this.data('checkin_type_name'));
                _model.find('input[name=checkin_type]').val(_this.data('checkin_type'));
                _model.find('input[name=userid]').val(_this.data('userid'));

                _model.modal('show').find('div.row :eq(0)').removeClass('hidden');
            });

            $('input[name=update_checkin_time]').datetimepicker({
                todayBtn : "linked",
                autoclose : true,
                language: 'zh-CN',
                format : "yyyy-mm-dd hh:ii:ss",
                todayHighlight : true,
                endDate : new Date()
            });

            $("#editModal").on('click',"button:eq(1)",function () {
                $('#editModal').modal('hide');
                $.post("/admin/sign/updateUnusual",$("#unusualEditForm").serialize(),function (data) {
                    if(data.success){
                        table.getDataTable().ajax.reload();
                    }else{
                        layer.msg(data.rmsg);
                    }
                });
            });

            /**
             * 导出异常明细
             */
            $("#myTabContent").on('click','button.js_export_unusual_record',function () {
                console.info("aa");
                var _startTime = $('input[name=startTime]').val();
                var _endTime= $('input[name=endTime]').val();
                var _username= $('input[name=username]').val();
                var _mobile= $('input[name=mobile]').val();
                var _code= $('input[name=code]').val();
                var _departmentId= $('input[name=departmentId]').val();

                $('#exportForm').remove();
                var _form = $('<form>');
                _form.attr('action','<c:url value="/admin/sign/exportUnusualSignDetail"/>');
                _form.attr('id','exportForm');
                _form.attr('method','POST');
//                    _form.attr('target','_blank');
                _form.append($('<input>').attr('name',"startTime").val(_startTime));
                _form.append($('<input>').attr('name',"endTime").val(_endTime));
                _form.append($('<input>').attr('name',"username").val(_username));
                _form.append($('<input>').attr('name',"mobile").val(_mobile));
                _form.append($('<input>').attr('name',"code").val(_code));
                _form.append($('<input>').attr('name',"departmentId").val(_departmentId));
                $(document).find('body').append(_form);
                $('#exportForm').submit();

            });

            /**
             * 导出请假明细
             */
            $("#myTabContent").on('click','button.js_export_leave_record',function () {
                console.info("aa");
                var _startTime = $('input[name=startTime]').val();
                var _endTime= $('input[name=endTime]').val();
                var _username= $('input[name=username]').val();
                var _mobile= $('input[name=mobile]').val();
                var _code= $('input[name=code]').val();
                var _departmentId= $('input[name=departmentId]').val();

                $('#exportForm').remove();
                var _form = $('<form>');
                _form.attr('action','<c:url value="/admin/sign/exportLeaveSignDetail"/>');
                _form.attr('id','exportForm');
                _form.attr('method','POST');
//                    _form.attr('target','_blank');
                _form.append($('<input>').attr('name',"startTime").val(_startTime));
                _form.append($('<input>').attr('name',"endTime").val(_endTime));
                _form.append($('<input>').attr('name',"username").val(_username));
                _form.append($('<input>').attr('name',"mobile").val(_mobile));
                _form.append($('<input>').attr('name',"code").val(_code));
                _form.append($('<input>').attr('name',"departmentId").val(_departmentId));
                $(document).find('body').append(_form);
                $('#exportForm').submit();

            });

            /**
             * 导出补登明细
             */
            $("#myTabContent").on('click','button.js_export_patch_record',function () {
                console.info("aa");
                var _startTime = $('input[name=startTime]').val();
                var _endTime= $('input[name=endTime]').val();
                var _username= $('input[name=username]').val();
                var _mobile= $('input[name=mobile]').val();
                var _code= $('input[name=code]').val();
                var _departmentId= $('input[name=departmentId]').val();

                $('#exportForm').remove();
                var _form = $('<form>');
                _form.attr('action','<c:url value="/admin/sign/exportPatchSignDetail"/>');
                _form.attr('id','exportForm');
                _form.attr('method','POST');
//                    _form.attr('target','_blank');
                _form.append($('<input>').attr('name',"startTime").val(_startTime));
                _form.append($('<input>').attr('name',"endTime").val(_endTime));
                _form.append($('<input>').attr('name',"username").val(_username));
                _form.append($('<input>').attr('name',"mobile").val(_mobile));
                _form.append($('<input>').attr('name',"code").val(_code));
                _form.append($('<input>').attr('name',"departmentId").val(_departmentId));
                $(document).find('body').append(_form);
                $('#exportForm').submit();

            });

            /**
             * 导出外出明细
             */
            $("#myTabContent").on('click','button.js_export_egress_record',function () {
                console.info("aa");
                var _startTime = $('input[name=startTime]').val();
                var _endTime= $('input[name=endTime]').val();
                var _username= $('input[name=username]').val();
                var _mobile= $('input[name=mobile]').val();
                var _code= $('input[name=code]').val();
                var _departmentId= $('input[name=departmentId]').val();

                $('#exportForm').remove();
                var _form = $('<form>');
                _form.attr('action','<c:url value="/admin/sign/exportEgressSignDetail"/>');
                _form.attr('id','exportForm');
                _form.attr('method','POST');
//                    _form.attr('target','_blank');
                _form.append($('<input>').attr('name',"startTime").val(_startTime));
                _form.append($('<input>').attr('name',"endTime").val(_endTime));
                _form.append($('<input>').attr('name',"username").val(_username));
                _form.append($('<input>').attr('name',"mobile").val(_mobile));
                _form.append($('<input>').attr('name',"code").val(_code));
                _form.append($('<input>').attr('name',"departmentId").val(_departmentId));
                $(document).find('body').append(_form);
                $('#exportForm').submit();

            });

            /**
             * 导出打卡明细
             */
            $("#myTabContent").on('click','button.js_export_sign_record',function () {
                console.info("aa");
                var _startTime = $('input[name=startTime]').val();
                var _endTime= $('input[name=endTime]').val();
                var _username= $('input[name=username]').val();
                var _mobile= $('input[name=mobile]').val();
                var _code= $('input[name=code]').val();
                var _departmentId= $('input[name=departmentId]').val();

                $('#exportForm').remove();
                var _form = $('<form>');
                _form.attr('action','<c:url value="/admin/sign/exportSignDetail"/>');
                _form.attr('id','exportForm');
                _form.attr('method','POST');
//                    _form.attr('target','_blank');
                _form.append($('<input>').attr('name',"startTime").val(_startTime));
                _form.append($('<input>').attr('name',"endTime").val(_endTime));
                _form.append($('<input>').attr('name',"username").val(_username));
                _form.append($('<input>').attr('name',"mobile").val(_mobile));
                _form.append($('<input>').attr('name',"code").val(_code));
                _form.append($('<input>').attr('name',"departmentId").val(_departmentId));
                $(document).find('body').append(_form);
                $('#exportForm').submit();

            });

            $('button.js_search').click(function () {
                var _startTime = $('input[name=startTime]').val();
                var _endTime = $('input[name=endTime]').val();
                if (_startTime!=" 00:00:00" && _endTime !=" 23:59:59") {
                    table.clearAjaxParams();
                    table.setAjaxParam('username',$('input[name=username]').val().trim());
                    table.setAjaxParam('startTime',$('input[name=startTime]').val().trim());
                    table.setAjaxParam('endTime',$('input[name=endTime]').val().trim());
                    table.setAjaxParam('mobile',$('input[name=mobile]').val().trim());
                    table.setAjaxParam('code',$('input[name=code]').val());
                    table.setAjaxParam('departmentId',$('input[name=departmentId]').val());
                    table.getDataTable().ajax.reload();

                } else {
                    layer.alert("请选择开始时间和结束时间",{icon:0});
                }
            });

            $('#js_btn_div').on('click','button.js_reset',function () {
                if (_temp == 1) {
                    $('input[name=username]').val('');
                    $('input[name=startTime]').val('');
                    $('input[name=startTime1]').val('');
                    $('input[name=endTime]').val('');
                    $('input[name=endTime1]').val('');
                    $('input[name=code]').val('');
                    $('input[name=departmentId]').val('');
                    emp.empObjArr.length = 0;
                    $('textarea[name=empName]').val('');
                } else {
                    table.clearAjaxParams();
                    $('input[name=username]').val('');
                    $('input[name=startTime]').val('');
                    $('input[name=startTime1]').val('');
                    $('input[name=endTime]').val('');
                    $('input[name=endTime1]').val('');
                    $('input[name=code]').val('');
                    $('input[name=departmentId]').val('');
                    emp.empObjArr.length = 0;
                    $('textarea[name=empName]').val('');
                    table.setAjaxParam('start',0);
                    table.getDataTable().ajax.reload();
                }
            });

        });

    }

    $(function () {

        /*if (_temp == 1) {
            clickTab(1)
            $("a[href='#1']").parent().attr("class","active");
        }*/

        var empTreeObj={
            companyTreeObj:null,
            departmentTreeObj:null,
            endTreeObj:null,
        };

        var emp={
            empObjArr:new Array(),
            chgTime:null,
            chgType:null,
            chgReason:null,
            empGroup:null
        };

        $('input[name=startTime1]').datetimepicker({
            format:"yyyy-mm-dd",
            language:  'zh-CN',
            weekStart: 0,
            todayBtn:  1,
            autoclose: 1,
            todayHighlight: 1,
            startView: 2,
            minView: 2,
            forceParse: 0
        }).change(function () {
            console.info($(this).val()+" 00:00:00");
            $('input[name=startTime]').val($(this).val()+" 00:00:00");
        });

        $('input[name=endTime1]').datetimepicker({
            format:"yyyy-mm-dd",
            language:  'zh-CN',
            weekStart: 0,
            todayBtn:  1,
            autoclose: 1,
            todayHighlight: 1,
            startView: 2,
            minView: 2,
            forceParse: 0
        }).change(function () {
            console.info($(this).val()+" 23:59:59");
            $('input[name=endTime]').val($(this).val()+" 23:59:59");
        });

        var msg = "${requestScope.msg}";
        _index = layer.load(2);
        if(msg != null && msg != "" && msg != "undefined") {
            layer.close(_index);
            layer.alert(msg,{icon:1});
        } else {
            layer.close(_index);
        }

        var initTree = function (_arr,treeId,callback,settings) {
            var _setting = {callback:{
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
            },check:{
                chkboxType:{"Y":"","N":""}
                }
            };
            if ( typeof settings == "object"){
                for(var k in settings){
                    var _set = _setting[k];
                    if(_set==undefined){
                        _setting[k]=settings[k];
                    }else{
                        var tmp = settings[k];
                        if ( typeof tmp == "object") {
                            for (var i in tmp) {
                                _set[i] = tmp[i];
                            }
                        }
                    }
                }
            }
            return $.fn.zTree.init($('#'+treeId), _setting, _arr);
        }

        var getCompanies = function (rootTreeObj,compTreeId,deptTreeId,endTreeId) {
            $.post('<c:url value="/admin/sign/getCompanys"/>', function (result) {
                if (result.success) {
                    var treeObj = initTree(result.rdata, compTreeId, function (event, treeId, treeNode) {
                        $.fn.zTree.destroy(deptTreeId);
                        $.fn.zTree.destroy(endTreeId);
                        getDepartments(rootTreeObj,treeNode.id,deptTreeId,endTreeId);
                    },{check:{
                        enable: true,
                        chkStyle:"checkbox",
                        autoCheckTrigger: false
                    },callback:{
                        beforeCheck:function (treeId, treeNode, clickFlag) {
                            rootTreeObj.companyTreeObj.checkNode(treeNode,treeNode.checked);
                        }
                    }});
                    var nodes = treeObj.getNodes();
                    treeObj.expandNode(nodes[0],true,false,false);
                    rootTreeObj.companyTreeObj = treeObj;
                }
            }, 'json');
        }


        var getDepartments = function (rootTreeObj,orgId,deptTreeId,endTreeId) {
            $.post('<c:url value="/hr/org/getAllDepartments"/>',{companyId:orgId},function (result) {
                if (result.success){
                    var deptTreeObj = initTree(result.rdata,deptTreeId,function (event,treeId,treeNode) {
                        $.fn.zTree.destroy(endTreeId);
                    },{check:{
                        enable: true,
                        chkStyle:"checkbox",
                        autoCheckTrigger: false
                    },callback:{
                        beforeCheck:function (treeId, treeNode, clickFlag) {
                            rootTreeObj.departmentTreeObj.checkNode(treeNode,treeNode.checked);
                        }
                    }});
                    rootTreeObj.departmentTreeObj = deptTreeObj;
                }
            },'json');
        }

        $('button.js_choose_emp').on('click',function () {
            $('textarea[name=empName]').val('');
            $('input[name=code]').val('');
            $('input[name=departmentId]').val('');
            emp.empObjArr.length = 0;
            if(!empTreeObj.companyTreeObj){
                getCompanies(empTreeObj,'empCompanyTree','empDepartmentTree','empTree');
            }
            $('div.js_department_ps_div').modal('show');
        });

        /**
         * 点击保存
         */
        $('button.js_save_btn').on('click',function () {
            encodeEmp()
            if(emp.empObjArr.length>0){
                var _names = new Array();
                var _code = new Array();
                var _departId = new Array();
                $.each(emp.empObjArr,function (k, v) {
                    _names.push(v.name);
                    _code.push(v.company);
                    _departId.push(v.department)
                });
                $('textarea[name=empName]').val(_names.join('，'));
                $('input[name=code]').val(_code.join(','));
                $('input[name=departmentId]').val(_departId.join(','));
                $('div.js_department_ps_div').modal('hide');
            }
        });

        var encodeEmp = function () {
            var cnodes = empTreeObj.companyTreeObj.getCheckedNodes();
            var dnodes = empTreeObj.departmentTreeObj.getCheckedNodes();
            if(dnodes.length>0) {
                //选了部门就不让公司多选
                var _arr = emp.empObjArr;
                var _lastLevelNodes = new Array();
                var leafNodes;
                $(cnodes).each(function (m, n) {
                    leafNodes = getLastLevelNodes(n,_lastLevelNodes);
                })
                if(leafNodes.length==1) {
                    $(dnodes).each(function (k, v) {
                        var allnodes = getParentNodeByLeaf(leafNodes[0],new Array(),"");
                        if (!listContainsKey(_arr,'empId',v.id)){
                            _arr.push({empId:v.id,name:allnodes+'-'+leafNodes[0].name+'-'+v.name,company:leafNodes[0].code,department:dnodes[k].id});
                        }
                    });
                } else {
                    layer.msg("部门只能对应一个单位!请选择一个单位");
                }
            } else {
                if(cnodes.length>0){
                    var _arr = emp.empObjArr;
                    var _lastLevelNodes = new Array();
                    $(cnodes).each(function (m, n) {
                        var _tem = getLastLevelNodes(n, _lastLevelNodes);
                        console.info(_lastLevelNodes);
                        $(_tem).each(function (k, v) {
                            var allnodes = getParentNodeByLeaf(v,new Array(),"");
                            if (!listContainsKey(_arr,'empId',v.id)){
                                _arr.push({empId:v.id,name:allnodes+'-'+v.name,company:_tem[k].code});
                            }
                        })
                    })

                }
            }

        };

        /**
         * 获去所有叶子
         * @param nodes
         * @returns {Array}
         */
        var getLeafNodes = function (nodes) {
            var leafNodes = new Array();
            $(nodes).each(function (k, v) {
                if(!v.isParent) {
                    leafNodes.push(v);
                }
            })
            return leafNodes;
        };

        /**
         *  获取所有选中的末级节点
         */
        var getLastLevelNodes = function (node, lastLevelNodes) {
            if (!node.isParent) {
                lastLevelNodes.push(node);
            } else {
                var _child = node.childList;
                var flag = haveCheckedChild(_child);
                if(flag) {
                    $(_child).each(function (i, j) {
                        if (j.checked) {
                            if (!j.isParent) {
                                lastLevelNodes.push(j);
                            } else {
                                lastLevelNodes = getLastLevelNodes(j, lastLevelNodes);
                            }
                        }
                    })
                } else {
                    lastLevelNodes.push(_child[0].getParentNode());
                }
            }
            return lastLevelNodes;
        }

        /**
         *  判断有没有被选中的子节点
         */
        var haveCheckedChild = function (_child) {
            var flag = false;
            for (var i = 0;i<_child.length;i++) {
                if (_child[i].checked) {
                    flag =  true;
                    break
                }
            }
            return flag;
        }

        /**
         * 获取叶子节点的所有父节点
         * @param treeNode
         * @param parentNodes
         * @param str
         * @returns {*}
         */
        var getParentNodeByLeaf = function (treeNode, parentNodes,str) {
            var _parentNode = treeNode.getParentNode();
            if(_parentNode) {
                parentNodes.push(_parentNode.name);
                str = getParentNodeByLeaf(_parentNode,parentNodes,str);
            } else {
                parentNodes.reverse();
                str =  parentNodes.join("-");
            }
            return str;
        }

        var listContainsKey= function(listMap,defKey,key) {
            var i = 0;
            var flag = false ;
            for(; i < listMap.length; i++) {
                if(listMap[i][defKey] == key){
                    flag = true;
                    break
                }
            }
            return flag;
        };





    });

</script>
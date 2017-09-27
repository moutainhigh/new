<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/admin/global/plugins/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" />" media="screen"/>

<div class="portlet light">
    <div class="portlet-title">
        <div class="caption">
            <i class="icon-layers font-green"></i>
            <span class="caption-subject font-green sbold uppercase">异常管理</span>
        </div>
    </div>
    <div class="row">
    <form class="navbar-form" role="search" id="materialSearchForm">

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

        <div class="form-group col-md-2">
            <div class="input-group col-md-12">
                <button type="button"  class="btn btn-default js_search">搜索</button>
                <button type="button" class="btn btn-default js_reset">重置</button>
            </div>
        </div>

    </form>
    </div>

    <br/>
    <ul id="myTab" class="nav nav-tabs">
           <li><a href="#1" onclick="clickTab(1);" title="1" data-toggle="tab">请假异常</a></li>
           <li><a href="#2" onclick="clickTab(2);" title="2" data-toggle="tab">补登异常</a></li>
           <li><a href="#3" onclick="clickTab(3);" title="3" data-toggle="tab">外出异常</a></li>
    </ul>

    <div id="myTabContent" class="tab-content">
        <div class="tab-pane fade in active" id="1" style="display: none">
            <div class="portlet-body">
                <div class="row" style="float: right;position: relative;left: -100px">
                    <a href="/admin/sign/toleaveEditPage"><button class="btn green js_patch_record"><i class="fa fa-edit"></i>新增请假</button></a>
                </div>
                <div style="clear: both"></div>
                <div class="row">
                    <div class="col-md-12">
                        <table class="table table-width table-striped table-bordered table-advance table-hover table-checkable" id="datatable_ajax1">
                            <thead>
                            <th width="2%">
                                序号
                            </th>
                            <th width="4%">姓名</th>
                            <th width="4%">单位</th>
                            <th width="4%">部门</th>
                            <th width="4%">职位</th>
                            <th width="4%">身份证号码</th>
                            <th width="4%">手机</th>
                            <th width="4%">请假类型</th>
                            <th width="4%">开始日期</th>
                            <th width="4%">结束日期</th>
                            <th width="4%">请假天数</th>
                            <th width="4%">流程状态</th>
                            <th width="5%">操作</th>
                            </thead>
                        </table>
                    </div>
                </div>
            </div>
        </div>

        <div class="tab-pane fade in active" id="2" style="display: none">
            <div class="portlet-body">
                <div class="row">
                    <div class="col-md-12">
                        <table class="table table-width table-striped table-bordered table-advance table-hover table-checkable" id="datatable_ajax2">
                            <thead>
                                <tr role="row" class="heading">
                                    <th width="3%">序号</th>
                                    <th width="4%">姓名</th>
                                    <th width="4%">管理单位</th>
                                    <th width="5%">管理部门</th>
                                    <th width="5%">职位</th>
                                    <th width="5%">手机号码</th>
                                    <th width="5%">补登类型</th>
                                    <th width="5%">补登时间</th>
                                    <th width="5%">补登说明</th>
                                    <th width="5%">操作</th>
                                </tr>
                            </thead>
                        </table>
                    </div>
                </div>
            </div>
        </div>

        <div class="tab-pane fade in active" id="3" style="display: none">
            <div class="portlet-body">
                <div class="row">
                    <div class="col-md-12">
                        <table class="table table-width table-striped table-bordered table-advance table-hover table-checkable" id="datatable_ajax3">
                            <thead>
                                <tr role="row" class="heading">
                                    <th width="3%">序号</th>
                                    <th width="3%">员工编码</th>
                                    <th width="4%">姓名</th>
                                    <th width="4%">单位</th>
                                    <th width="4%">部门</th>
                                    <th width="5%">身份证号码</th>
                                    <th width="4%">手机号码</th>
                                    <th width="4%">日期</th>
                                    <th width="5%">外出时间</th>
                                    <th width="5%">返回时间</th>
                                    <th width="5%">外出地点</th>
                                    <th width="5%">外出摘要</th>
                                    <th width="6%">操作</th>
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

<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.zh-CN.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/datatables/datatables.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/scripts/datatable.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js" />"></script>


<script>
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

        var listUrl = "";
        var columns = "" ;
        if (1==id) {
            //请假异常管理
            listUrl = "<c:url value="/admin/sign/listLeavePatchData"/>";
            columns=[
                {"data":"id", "targets": 0},
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
                {"data":"telephone","render" : function(data, type, full, meta ){
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
                {"data":"status","render" : function(data, type, full, meta ){
                    if (1 == data) {
                        return "流程未结束";
                    } else if(2 == data) {
                        return "流程已结束";
                    }
                }},
                {"data":"id","render" : function(data, type, full, meta ){
                    var editLeave = "<c:url value='/admin/sign/toLeaveEditPage/'/>"+data;
                    var changeLeaveState = "<c:url value='/admin/sign/toLeaveEditPage/'/>"+data;
                    var retruanStr="";
                    if (full.finishedFlag != 1) {
                        retruanStr = '<button class="btn btn-sm btn-outline grey-salsa js_change_leave_flag" data-id="'+data+'" ><i class="fa fa-edit"></i>确认</button>';
                    }
                    if (full.username == undefined || full.telephone == undefined){
                        retruanStr = retruanStr + '<a href="'+editLeave+'" class="btn btn-sm btn-outline grey-salsa" ><i class="fa fa-edit"></i> 补录</a>';
                    }
                    return retruanStr;
                }}
            ];
        } else if (2 == id) {
            //补登异常
            listUrl = "<c:url value="/admin/sign/patchUnusualListData"/>";
            columns = [
                {"data":"id","render" : function(data, type, full, meta ){
                    return data==undefined?'':data;
                }},
                {"data":"name","render" : function(data, type, full, meta ){
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
                    if(data == 1) {
                        return "上班补登";
                    } else {
                        return "下班补登";
                    }
                }},
                {"data":"notes","render" : function(data, type, full, meta ){
                    return data==undefined?'':data;
                }},{"data":"id","render" : function(data, type, full, meta ){
                    var retruanStr = '<button class="btn btn-sm btn-outline grey-salsa js_change_patch_flag" data-id="'+data+'" ><i class="fa fa-edit"></i>确认</button>';
                    console.info(full);
                    return retruanStr;
                }}
            ];
        } else if (3 == id) {
            //外出异常管理
            listUrl = "<c:url value="/admin/sign/listUnusualPatchData"/>";
            columns = [
                {"data":"id","render" : function(data, type, full, meta ){
                    return data==undefined?'':data;
                }},
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
                }},
                {"data":"id","render" : function(data, type, full, meta ){
                    var editEgress = "<c:url value='/admin/sign/toEgressEditPage/'/>"+data;
                    var retruanStr="";
                    if (full.finishedFlag != 1) {
                        retruanStr = '<button class="btn btn-sm btn-outline grey-salsa js_change_egress_flag" data-id="'+data+'" ><i class="fa fa-edit"></i>确认</button>';
                    }
                    if (full.username == undefined || full.telephone == undefined){
                        retruanStr = retruanStr + '<a href="'+editEgress+'" class="btn btn-sm btn-outline grey-salsa" ><i class="fa fa-edit"></i> 补录</a>';
                    }
                    return retruanStr;
                }}
            ];
        }
        var TableDatatablesAjax = function() {
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
                                d.startTime = $.trim($('input[name=startTime]').val());
                                d.endTime = $.trim($('input[name=endTime]').val());
                            }
                        },
                        order: [[1, "asc"]],
                        columns:columns,
                        fnDrawCallback: function(){
                            var api = this.api();
                            var startIndex= api.context[0]._iDisplayStart;//获取到本页开始的条数
                            api.column(0).nodes().each(function(cell, i) {
                                cell.innerHTML = startIndex + i + 1;
                            });
                        },
                        "initComplete":function () {
                            //alert($("input[name=checkId]").length);
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

            $('button.js_search').click(function () {
                table.clearAjaxParams();
                table.setAjaxParam('startTime',$('input[name=startTime]').val().trim());
                table.setAjaxParam('endTime',$('input[name=endTime]').val().trim());
                table.getDataTable().ajax.reload();
            });

            $('button.js_reset').click(function () {
                table.clearAjaxParams();
                $('input[name=startTime]').val('');
                $('input[name=endTime]').val('');
                table.setAjaxParam('start',0);
                table.getDataTable().ajax.reload();
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

            $(document).on('click', 'button.js_change_leave_flag', function() {
                var _id = $(this).data('id');
                layer.confirm("该流程未结束,确定使用该条数据?",function (c) {
                    layer.close(c);
                    $.post("<c:url value='/admin/sign/changeLeaveFlag'/>",{id:_id}, function (data) {
                        if(data.success){
                            layer.alert("操作成功!",{icon:1});
                            table.getDataTable().ajax.reload();
                        }else{
                            layer.msg(data.rmsg);
                        }
                    });
                });
            });
            $(document).on('click', 'button.js_change_egress_flag', function() {
                var _id = $(this).data('id');
                layer.confirm("该流程未结束,确定使用该条数据?",function (c) {
                    layer.close(c);
                    $.post("<c:url value='/admin/sign/changeEgressFlag'/>",{id:_id}, function (data) {
                        if(data.success){
                            layer.alert("操作成功!",{icon:1});
                            table.getDataTable().ajax.reload();
                        }else{
                            layer.msg(data.rmsg);
                        }
                    });
                });
            });

            $(document).on('click', 'button.js_change_patch_flag', function() {
                var _id = $(this).data('id');
                layer.confirm("该流程未结束,确定使用该条数据?",function (c) {
                    layer.close(c);
                    $.post("<c:url value='/admin/sign/changePatchUnusualStatus'/>",{id:_id}, function (data) {
                        if(data.success){
                            layer.alert("操作成功!",{icon:1});
                            table.getDataTable().ajax.reload();
                        }else{
                            layer.msg(data.rmsg);
                        }
                    });
                });
            });

        });
    }

    $(function () {
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
        var a1 = "${pageContext.request.getHeader('Referer')}";
        console.info(a1.indexOf("Egress"));
        if (a1.indexOf("Egress") != -1) {
            clickTab(3);
            $("a[href='#3']").parent().attr("class","active");
        } else if (a1.indexOf("Leave") != -1) {
            clickTab(1);
            $("a[href='#1']").parent().attr("class","active");
        } else if (a1.indexOf("Unusual") != -1) {
            clickTab(2);
            $("a[href='#2']").parent().attr("class","active");
        }
    });




</script>
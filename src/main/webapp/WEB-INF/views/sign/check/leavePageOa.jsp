
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<link rel="stylesheet" type="text/css" href="<c:url value="/resources/admin/global/plugins/bootstrap-select/css/bootstrap-select.min.css" />" media="screen"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/admin/global/plugins/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" />" media="screen"/>

<style>
    .bootstrap-select{
        width:auto !important;
    }
</style>

<div class="portlet light">
    <div class="portlet-title">
        <div class="caption">
            <i class="icon-layers font-green"></i>
            <span class="caption-subject font-green sbold uppercase">OA请假</span>
        </div>
    </div>
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

        <div class="form-group col-md-1">
            <div class="input-group col-md-12">
                <input type="text"  name="username" class="form-control" value="" placeholder="姓名">
            </div>
        </div>
        <div class="form-group col-md-2">
            <div class="input-group col-md-12">
                <select class="form-control" name="status">
                    <option value="-1">请选择状态</option>
                    <option value="0">正常</option>
                    <option value="1">未打卡</option>
                    <option value="2">外出打卡</option>
                    <option value="3">地点异常-未打卡</option>
                    <option value="4">OA外出</option>
                    <option value="5">OA补登</option>
                    <option value="6">OA请假</option>
                </select>
            </div>
        </div>

        <div class="form-group col-md-2">
            <div class="input-group col-md-12" id="js_btn_div">
                <button type="button"  class="btn btn-default js_search">搜索</button>
                <button type="button" class="btn btn-default js_reset">重置</button>
            </div>
        </div>
    </form>

    <div id="myTabContent" class="tab-content">
        <div class="tab-pane fade in active" id="home">
            <div class="portlet-body">
                <div class="row">
                    <div class="col-md-12">
                        <table class="table table-width table-striped table-bordered table-advance table-hover table-checkable " id="datatable_ajax">
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
        };

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
                        url: "<c:url value="/admin/sign/listSignDetailData"/>",
                        type:"POST",
                        data:function (d) {
                            d.username = $.trim($('input[name=username]').val());
                            d.code = $.trim($('input[name=code]').val());
                            d.departmentId = $.trim($('input[name=departmentId]').val());
                            d.startTime = $.trim($('input[name=startTime]').val());
                            d.endTime = $.trim($('input[name=endTime]').val());
                            d.status = $.trim($('select[name=status]').val());
                        }
                    },
                    order: [[1, "asc"]],
                    columns : [
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
        var table;
        var flag = 1;
        $('button.js_search').click(function () {
            var _startTime = $.trim($('input[name=startTime]').val());
            var _endTime = $.trim($('input[name=endTime]').val());
            if(_startTime != "00:00:00" && _endTime != "23:59:59") {
                if (flag == 1) {
                    table = TableDatatablesAjax.init();
                } else {
                    table.clearAjaxParams();
                    table.setAjaxParam('username',$.trim($('input[name=username]').val()));
                    table.setAjaxParam('code',$.trim($('input[name=code]').val()));
                    table.setAjaxParam('departmentId',$.trim($('input[name=departmentId]').val()));
                    table.setAjaxParam('startTime',$.trim($('input[name=startTime]').val()));
                    table.setAjaxParam('endTime',$.trim($('input[name=endTime]').val()));
                    table.setAjaxParam('status',$.trim($('select[name=status]').val()));
                    table.getDataTable().ajax.reload();
                }
                flag++;
            } else {
                layer.alert("请选择开始时间和结束时间",{icon:0})
            }
        });

        $('button.js_reset').click(function () {
            if (flag == 1) {
                $('input[name=username]').val('');
                $('input[name=code]').val('');
                $('input[name=departmentId]').val('');
                emp.empObjArr.length = 0;
                $('textarea[name=empName]').val('');
                $('input[name=startTime]').val('');
                $('input[name=startTime1]').val('');
                $('input[name=endTime]').val('');
                $('input[name=endTime1]').val('');
                $('select[name=status] option:eq(0)').prop("selected",'true');
            } else {
                table.clearAjaxParams();
                $('input[name=username]').val('');
                $('input[name=code]').val('');
                $('input[name=departmentId]').val('');
                emp.empObjArr.length = 0;
                $('textarea[name=empName]').val('');
                $('input[name=startTime]').val('');
                $('input[name=startTime1]').val('');
                $('input[name=endTime]').val('');
                $('input[name=endTime1]').val('');
                $('select[name=status] option :eq(0)').prop("selected",'true');
                table.setAjaxParam('start',0);
                table.getDataTable().ajax.reload();
            }
        });

        $('#datatable_ajax').on('click','button.js_outside_sign_check',function () {
            var _id = $(this).data('id');
            layer.confirm("该数据是外出数据,确定用作考勤数据?",function (c) {
                layer.close(c);
                $.post("<c:url value='/admin/sign/outsideDataToSignData'/>",{id:_id}, function (data) {
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
</script>
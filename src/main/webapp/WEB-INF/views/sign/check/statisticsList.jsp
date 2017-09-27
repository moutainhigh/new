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
            <span class="caption-subject font-green sbold ">员工考勤汇总报表</span>
        </div>
    </div>
    <div class="portlet-body">
        <form class="navbar-form" role="search" id="materialSearchForm">

            <div class="form-group col-md-2">
                <div class="input-group date form_date col-md-12" data-date="" data-date-format="yyyy-mm-dd" data-link-field="startTime1" data-link-format="yyyy-mm-dd" style="width: 100%">
                    <input class="form-control" name="startDate1" size="16" type="text" placeholder="开始时间" value="<fmt:formatDate value="${startTime}" pattern="yyyy-MM-dd"/>" >
                    <input class="form-control" name="startDate" size="16" type="hidden" placeholder="开始时间" value="<fmt:formatDate value="${startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>" >
                </div>
            </div>

            <div class="form-group col-md-2">
                <div class="input-group date form_date col-md-12" data-date="" data-date-format="yyyy-mm-dd" data-link-field="endTime1" data-link-format="yyyy-mm-dd" style="width: 100%">
                    <input class="form-control" name="endDate1" size="16" type="text" placeholder="结束时间" value="<fmt:formatDate value="${endTime}" pattern="yyyy-MM-dd"/>">
                    <input class="form-control" name="endDate" size="16" type="hidden" placeholder="结束时间" value="<fmt:formatDate value="${endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>">
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

            <div class="form-group">
                <button type="button" class="btn btn-default js_search">搜索</button>
            </div>
            <div class="form-group">
                <button type="button" class="btn btn-default js_export">导出</button>
            </div>
        </form>
        <div id="myTabContent" class="tab-content">
            <div class="tab-pane fade in active" id="home">
                <div class="portlet-body">
                    <div class="row">
                        <div class="col-md-12" style="overflow: auto">
                            <table class="table table-width table-striped table-bordered table-advance table-hover table-checkable table-responsive" <%--style="min-width: 3000px;"--%> id="datatable_ajax">
                                <thead>
                                <tr>
                                    <th>序号</th>
                                    <th>合同单位</th>
                                    <th>管理模块</th>
                                    <th>管理单位</th>
                                    <th>管理部门</th>
                                    <th>人员编码</th>
                                    <th>出生日期</th>
                                    <th>年龄</th>
                                    <th>入司时间</th>
                                    <th>转正时间</th>
                                    <th>用工状态</th>
                                    <th>姓名</th>
                                    <th>性别</th>
                                    <th>岗位</th>
                                    <th>缺勤日</th>
                                    <th>实际发放日</th>
                                    <th>出勤</th>
                                    <th>休息</th>
                                    <th>外出</th>
                                    <th>其他假</th>
                                    <th>事假</th>
                                    <th>病假</th>
                                    <th>换休</th>
                                    <th>身份证号</th>
                                    <th>职级</th>
                                    <th>分类</th>
                                    <th>在职状态</th>
                                    <th>绩效体系</th>
                                    <th>未出勤</th>
                                </tr>
                                </thead>
                                <tbody>

                                </tbody>
                            </table>
                        </div>
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

<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.zh-CN.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/bootstrap-select/js/bootstrap-select.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/plugIn/zTree/js/jquery.ztree.exhide.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/plugIn/zTree/js/jquery.ztree.excheck.js" />"></script>


<script>

    $(function () {
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

        var _index = null;
        $('button.js_search').on('click',function () {
            var _startDate = $('input[name=startDate]').val();
            var _endDate = $('input[name=endDate]').val();
            var _code = $.trim($('input[name=code]').val());
            var _departmentId = $.trim($('input[name=departmentId]').val());
            if(_startDate&&_endDate){
                _index = layer.load(2);
                $.ajax({
                    async: true,
                    type: 'POST',
                    dataType: 'json',
                    data:{
                        startDate:_startDate,
                        endDate:_endDate,
                        code:_code,
                        departmentIds:_departmentId
                    },
                    url: '<c:url value="/admin/sign/listStatisticsData"/>',
                    success: function (result) {
                        layer.close(_index);
                        if(result.success){
                            buildTb(result.rdata);
                        }else{
                            layer.msg(result.rmsg);
                        }
                    }
                });
            } else {
                layer.alert("请选择开始日期和结束日期!",{icon:0});
            }
        });


        $('input[name=startDate1]').datetimepicker({
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
            $('input[name=startDate]').val($(this).val()+" 00:00:00");
        });

        $('input[name=endDate1]').datetimepicker({
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
            $('input[name=endDate]').val($(this).val()+" 23:59:59");
        });

        var entityKey = 'id,pactCompanyName,plateName,companyName,departmentName,userNum,birthdate,age,joinCompDate,inDueFormDate,useStatus,' +
            'name,sex,position,absenceDayNum,factPayDayNum,workDutyDayNum,restDayNum,egressDayNum,elseDeduct,affairsLeave,sickLeave,restChanged,' +
            'idCard,technicalLevel,userCategory,workStatus,performanceSystem,noWork';
        var  buildTb = function (resultList) {
            var _body = $('#datatable_ajax tbody').empty();
            var _lastId = '',_birthdate='';
            $.each(resultList,function (k,v) {
                var _tr = $('<tr>');
                _tr = $('<tr>');
                $.each(entityKey.split(','),function (i, e) {
                    _tr.append($('<td>').text(v[e]));
                });
                _body.append(_tr);
            });
        }


        var status = 0;
        var startX = null;
        $('table').on('mousedown',function (e) {
            status = 1;
            startX = e.clientX;
        });
        $('table').on('mouseup',function (e) {
            if(status ==1){
                $("table").parent('div').scrollLeft(e.clientX-startX);
            }
            status = 0;
        });
        $('table').on('mousemove',function (e) {
            if(status ==1){
                $("table").parent('div').scrollLeft(e.clientX-startX);
            }
        });

        /**
         *  导出
         */
        $('button.js_export').on('click', function () {
            var _startDate = $('input[name=startDate]').val();
             var _endDate= $('input[name=endDate]').val();
            var _code = $.trim($('input[name=code]').val());
            var _departmentId = $.trim($('input[name=departmentId]').val());
             if(_startDate&&_endDate){
             $('#printForm').remove();
             var _form = $('<form>');
             _form.attr('action','<c:url value="/admin/sign/exportSignStatistics"/>');
             _form.attr('id','printForm');
             _form.attr('method','POST');
             //            _form.attr('target','_blank');
             _form.append($('<input>').attr('name',"startDate").val(_startDate));
             _form.append($('<input>').attr('name',"endDate").val(_endDate));
             _form.append($('<input>').attr('name',"code").val(_code));
             _form.append($('<input>').attr('name',"departmentIds").val(_departmentId));
             $(document).find('body').append(_form);
             $('#printForm').submit();
             } else {
             layer.alert("请选择开始日期和结束日期!",{icon:0});
             }
        });

        /**
         *  導出
         * @type {Array}
         * @private
         */
        /*var _arr = new Array();
         $('tbody').find('tr:gt(0)').each(function (k, v) {
         console.info(k);
         console.info(v);
         var obj = {};
         $(v).find('td').each(function (i, e) {

         })

         });*/

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


</script>
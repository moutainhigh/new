<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<link rel="stylesheet" type="text/css" href="<c:url value="/resources/admin/global/plugins/bootstrap-datepicker/css/bootstrap-datepicker3.min.css" />"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/hr/css/hr.css" />"/>
<style>
    .datepicker-dropdown{
        z-index: 9999;
    }
</style>

<div class="portlet box blue-hoki">
    <div class="portlet-title">
        <div class="caption">
            <i class="fa  fa-random"></i>
            人员调动
        </div>
    </div>
    <div class="portlet-body">
        <div class="row">
            <div class="col-md-8 ">
                <form  role="form" class="js_base_form">
                    <div class="form-body">
                        <div class="row">
                            <div class="col-md-12 ">
                                <div class="form-group">
                                    <label>选择人员</label>
                                    <textarea class="form-control" name="empName" readonly></textarea>
                                    <button type="button" class="btn btn-primary js_choose_emp">选择</button>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-4">
                                <div class="form-group">
                                    <label>调动类别</label>
                                    <select class="form-control" name="chgType">
                                        <option value="1">跨公司调动</option>
                                        <option value="2">内部调动</option>
                                    </select>
                                </div>
                            </div>
                            <div class="col-md-4 ">
                                <div class="form-group">
                                    <label>调动原因</label>
                                    <select class="form-control" name="chgReason">
                                        <option value="">请选择</option>
                                        <option value="1">人员编制变化</option>
                                        <option value="2">工作岗位变化</option>
                                        <option value="3">工作地点变化</option>
                                        <option value="4">组织结构调整</option>
                                    </select>
                                </div>
                            </div>
                            <div class="col-md-4 ">
                                <div class="form-group">
                                    <label>调动时间</label>
                                    <input type="text" class="form-control" name="chgTime">
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-4 ">
                                <div class="form-group">
                                    <label>调动状态</label>
                                    <select class="form-control" name="chgStatus">
                                        <option value="">请选择</option>
                                        <option value="2">平调</option>
                                        <option value="5">晋升</option>
                                        <option value="1">降职</option>
                                        <option value="3">轮岗</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-4 ">
                                <div class="form-group">
                                    <label>目标单位</label>
                                    <input type="text" class="form-control" readonly name="company">
                                </div>
                            </div>
                            <div class="col-md-4 ">
                                <div class="form-group">
                                    <label>目标部门</label>
                                    <input type="text" class="form-control" readonly name="department">
                                </div>
                            </div>
                            <div class="col-md-4 ">
                                <div class="form-group">
                                    <label>目标岗位</label>
                                    <div class="input-group">
                                        <input type="text" class="form-control" readonly name="job">
                                        <span class="input-group-btn"><button type="button" class="btn btn-primary js_choose_job">选择</button></span>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-4 ">
                                <div class="form-group">
                                    <label>目标职级</label>
                                    <select class="form-control" name="dutyLevel">
                                        <option value="">请选择</option>
                                        <c:forEach items="${dutyLevel}" var="data">
                                            <option value="${data.dictDataValue}">${data.dictDataKey}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="col-md-4 ">
                                <div class="form-group">
                                    <label>剩余编制</label>
                                    <input type="text" class="form-control" readonly name="plait">
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12 ">
                                <div class="form-group">
                                    <label>调配说明</label>
                                    <textarea class="form-control" name="description"></textarea>
                                </div>
                            </div>
                        </div>
                    </div>
                    <button type="button" class="btn green js_save_chg" data-action="save">确定</button>
                </form>
            </div>
        </div>
    </div>
</div>

<div class="modal fade js_department_ps_div " data-backdrop="static">
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
                        <div class="col-md-4">
                            <label>人员</label>
                            <ul id="empTree" class="ztree"></ul>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <div class="pull-left">
                    <button type="button" class="btn btn-success js_add_btn">添加</button>
                    <button type="button" class="btn btn-danger js_remove_btn">删除</button>
                </div>
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary js_save_btn">保存</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade js_cmp_dept_job_div">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title">公司/部门/岗位</h4>
            </div>
            <div class="modal-body">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-md-4">
                            <label>公司</label>
                            <ul id="companyTree" class="ztree"></ul>
                        </div>
                        <div class="col-md-4">
                            <label>部门</label>
                            <ul id="departmentTree" class="ztree"></ul>
                        </div>
                        <div class="col-md-4">
                            <label>岗位</label>
                            <ul id="jobTree" class="ztree"></ul>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary js_job_save_btn">保存</button>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript" src="<c:url value="/resources/plugIn/zTree/js/jquery.ztree.exhide.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/plugIn/zTree/js/jquery.ztree.excheck.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.zh-CN.js" />"></script>


<script>

    $(document).ready(function () {
        var empTreeObj={
            companyTreeObj:null,
            departmentTreeObj:null,
            endTreeObj:null,
        };

        var jobTreeObj={
            companyTreeObj:null,
            departmentTreeObj:null,
            endTreeObj:null,
        };

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
            }};
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

        var getEmpByDepartment=function(rootTreeObj, orgId, deptId, psnTreeId){
            $.post('<c:url value="/hr/getAllEmpListData"/>',{company:orgId,department:deptId},function (result) {
                if (result){
                    if (emp.empObjArr.length>0){
                        for(var i =0; i < result.length; i++) {
                            $.each(emp.empObjArr,function (k, v) {
                                if (result[i].id == v.empId){
                                    result[i].checked = true;
                                }
                            });
                        }
                    }
                    rootTreeObj.endTreeObj = initTree(result,psnTreeId,function (event, treeId, treeNode) {


                    },{check:{
                        enable: true,
                        chkStyle:"checkbox",
                        autoCheckTrigger: true
                    },callback:{
                        beforeClick:function (treeId, treeNode, clickFlag) {
                            rootTreeObj.endTreeObj.checkNode(treeNode,!treeNode.checked)
                        }
                    },data:{key:{name:'empAndJobName',children:"childList"}}});
                }
            },'json');
        }

        var getJobByDepartment=function(rootTreeObj, orgId, deptId, endTreeId){
            var date = new Date($('input[name=chgTime]').val());
            $.post('<c:url value="/hr/org/getAllJobWithPlait"/>',{company:orgId,departmentId:deptId,year:date.getFullYear(),month:date.getMonth()+1},function (result) {
                if (result.success){
                    rootTreeObj.endTreeObj = initTree(result.rdata,endTreeId,function (event, treeId, treeNode) {

                    });
                }
            },'json');
        }

        var getDepartments = function (rootTreeObj,orgId,deptTreeId,endTreeId) {
            $.post('<c:url value="/hr/org/getAllDepartments"/>',{companyId:orgId,ignore:true},function (result) {
                if (result.success){
                    var deptTreeObj = initTree(result.rdata,deptTreeId,function (event,treeId,treeNode) {
                        $.fn.zTree.destroy(endTreeId);
                        if(endTreeId=='empTree'){
                            getEmpByDepartment(rootTreeObj,orgId,treeNode.id,endTreeId);
                        }else if (endTreeId=="jobTree"){
                            getJobByDepartment(rootTreeObj,orgId,treeNode.id,endTreeId);
                        }
                    });
                    rootTreeObj.departmentTreeObj = deptTreeObj;
                }
            },'json');
        }

        var getCompanies = function (rootTreeObj,compTreeId,deptTreeId,endTreeId,args) {
            $.ajax({
                async: false,
                type : 'POST' ,
                dataType:'json',
                url : '<c:url value="/hr/org/getAllCompanies"/>',
                data:args,
                success: function (result) {
                    if (result.success){
                        var treeObj = initTree(result.rdata, compTreeId, function (event, treeId, treeNode) {
                            $.fn.zTree.destroy(deptTreeId);
                            $.fn.zTree.destroy(endTreeId);
                            if (compTreeId=='companyTree'){
                                if($('select[name=chgType]').val()==1){
                                    if(treeNode.id==emp.srcCompany){
                                        jobTreeObj.companyTreeObj.cancelSelectedNode(treeNode);
                                        layer.msg('跨公司调动，不能选择原单位');
                                        return;
                                    }
                                }else{
                                    if(treeNode.id!=emp.srcCompany){
                                        jobTreeObj.companyTreeObj.cancelSelectedNode(treeNode);
                                        layer.msg('内部调动，目标单位必需与原单位一致');
                                        return;
                                    }
                                }
                            }
                            getDepartments(rootTreeObj,treeNode.id,deptTreeId,endTreeId);
                        });
                        var nodes = treeObj.getNodes();
                        treeObj.expandNode(nodes[0],true,false,false);
                        rootTreeObj.companyTreeObj = treeObj;
                    }
                }
            });
        }

        $('select[name=chgType]').on('change',function () {
            if(emp.empObjArr.length>0){
                $('input[name=company]').val('');
                $('input[name=department]').val('');
                $('input[name=job]').val('');
                $('select[name=dutyLevel] option:eq(0)').attr('selected',true);
                emp.company=null;
                emp.department=null;
                emp.job=null;
                emp.dutyLevel =null;
                $.fn.zTree.destroy('departmentTree');
                $.fn.zTree.destroy('jobTree');
            }
        });

        $('button.js_choose_emp').on('click',function () {
            if(!empTreeObj.companyTreeObj){
                getCompanies(empTreeObj,'empCompanyTree','empDepartmentTree','empTree');
            }
            $('div.js_department_ps_div').modal('show');//.data('backdrop','static');
        });

        /**
         * 选择调入公司/部门/岗位
         */
        $('button.js_choose_job').on('click',function () {
            if (emp.empObjArr.length>0){
                var dateStr = $('input[name=chgTime]').val();
                if(dateStr!=''){
                    if(!jobTreeObj.companyTreeObj){
                        getCompanies(jobTreeObj,'companyTree','departmentTree','jobTree',{ignore:true});
                    }
                    $('div.js_cmp_dept_job_div').modal('show');
                }else{
                    layer.msg('请先选定调动时间');
                }
            }else{
                layer.msg('请先选择人员');
            }
        });


        /**
         * 保存岗位信息
         */
        $('button.js_job_save_btn').on('click',function () {
            var cnodes = jobTreeObj.companyTreeObj.getSelectedNodes();
            if(cnodes.length==1) {
                $('input[name=company]').val(cnodes[0].name);
                emp.company=cnodes[0].id;
                var dnodes = jobTreeObj.departmentTreeObj.getSelectedNodes();
                if(dnodes.length==1) {
                    $('input[name=department]').val(dnodes[0].name);
                    emp.department=dnodes[0].id;
                    var jnodes = jobTreeObj.endTreeObj.getSelectedNodes();
                    if(jnodes.length==1) {
                        $('input[name=job]').val(jnodes[0].name);
                        $('input[name=plait]').val(jnodes[0].count);
                        emp.job=jnodes[0].id;
                        $('div.js_cmp_dept_job_div').modal('hide');
                    }else{
                        layer.msg("请选择岗位");
                    }
                }else{
                    layer.msg("请选择部门");
                }
            }else{
                layer.msg("请选择公司");
            }
        });

        /**
         * 搜人
         */
        $('button.js_search_psn').on('click',function () {
            var _tree = empTreeObj.endTreeObj;
           if(_tree){
               var value = $.trim($('input[name=searchPsn]').val());
               if (value){
                   var result = _tree.getNodesByParamFuzzy('name',value,null);
                   if (result.length>0){
                        for(var i=0;i<result.length;i++){
                            _tree.checkNode(result[i],true);
                            _tree.selectNode(result[i],true);
                        }
                   }
               }
           }
        });

        /**
         * 保存人员
         */
        $('button.js_save_btn').on('click',function () {
            encodeEmp();
            if(emp.empObjArr.length>0){
                var _names = new Array();
                $.each(emp.empObjArr,function (k, v) {
                    _names.push(v.name);
                });
                $('textarea[name=empName]').val(_names.join(','));
                $('div.js_department_ps_div').modal('hide');
            }else{
                layer.msg('请选择人员');
            }
        });

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

        $('button.js_remove_btn').on('click',function () {
            var enodes = empTreeObj.endTreeObj.getCheckedNodes();
            if(enodes.length>0) {
                var _arr = emp.empObjArr;
                var treeObj = empTreeObj.endTreeObj;
                $(enodes).each(function (k, v) {
                    for( var i = 0; i < _arr.length; i++) {
                        var obj = _arr[i];
                        if(obj.empId==v.id){
                            _arr.splice(i, 1);
                            var node = treeObj.getNodeByParam('id',obj.empId,null);
                            treeObj.checkNode(node,false);
                            treeObj.cancelSelectedNode(node);
                            break;
                        }
                    }
                });
            }
        });

        var encodeEmp = function () {
            var cnodes = empTreeObj.companyTreeObj.getSelectedNodes();
            if(cnodes.length==1) {
                $('input[name=companyOld]').val(cnodes[0].name);
                emp.srcCompany = cnodes[0].id;
                var dnodes = empTreeObj.departmentTreeObj.getSelectedNodes();
                if(dnodes.length==1) {
                    $('input[name=departmentOld]').val(dnodes[0].name);
                    var enodes = empTreeObj.endTreeObj.getCheckedNodes();
                    if(enodes.length>0) {
                        var _arr = emp.empObjArr;
                        $(enodes).each(function (k, v) {
                            if (!listContainsKey(_arr,'empId',v.id)){
                                _arr.push({empId:v.id,name:cnodes[0].name+'-'+dnodes[0].name+'-'+v.name});
                            }
                        });
                    }else{
                        layer.msg("请选择人员");
                    }
                }else{
                    layer.msg("请选择部门");
                }
            }else{
                layer.msg("请选择公司");
            }
        }

        $('button.js_add_btn').on('click',function () {
            encodeEmp();
        });



        $('input[name=chgTime]').datepicker({
            todayBtn : "linked",
            autoclose : true,
            language: 'zh-CN',
            format : "yyyy-mm-dd",
            todayHighlight : true,
            <sec:authorize access="!hasAnyRole('hr_dev','hrkylr')"> startDate:' ${monthFirstDay}',</sec:authorize>
            endDate : new Date()
        });

        var checkObj = function (errorMsg) {
            var flag = true;
            for(var e in errorMsg){
                if(e == 'empObjArr'){
                    if(emp['empObjArr'].length==0){
                        layer.msg(errorMsg[e],{time:800});
                        flag = false;
                        break;
                    }
                }else{
                    if(!emp[e]){
                        layer.msg(errorMsg[e],{time:800});
                        flag = false;
                        break;
                    }
                }
            }
            return flag;
        }

        var emp={
            empObjArr:new Array(),
            chgTime:null,
            chgStatus:null,
            company:null,
            department:null,
            job:null,
            chgReason:null,
            dutyLevel:null
        };
        var errorMsg={
            empObjArr:"调动人员不能为空",
            chgTime:"调动时间不能为空",
            chgStatus:"调动状态不能为空",
            company:"目标公司不能为空",
            department:"目标部门不能为空",
            job:"目标岗位不能为空",
            chgReason:"调动原因不能为空",
            dutyLevel:"目标职级不能为空"
        };
        var _index=null;
        $('button.js_save_chg').on('click',function () {
            emp.chgTime = $('input[name=chgTime]').val();
            emp.chgType = $('select[name=chgType]').val();
            emp.chgStatus = $('select[name=chgStatus]').val();
            emp.chgReason = $('select[name=chgReason]').val();
            emp.dutyLevel = $('select[name=dutyLevel]').val();
            if(checkObj(errorMsg)){
                emp.empObjArrStr = JSON.stringify(emp.empObjArr);
                emp.companyName = $('input[name=company]').val();
                emp.description = $('textarea[name=description]').val();
                _index = layer.load(2);
                $.post('<c:url value="/hr/chg/postChg"/>',emp, function (result) {
                    if (result.success) {
                        layer.close(_index);
                        layer.msg('操作成功',{time:800},function () {
                            window.location.reload();
                        });
                    }else{
                        layer.close(_index);
                        layer.msg('操作失败：'+result.rmsg,{time:5000});
                    }
                }, 'json');
            }
        });

    });

</script>
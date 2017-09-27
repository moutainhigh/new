<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="portlet box blue-hoki">
    <div class="portlet-title">
        <div class="caption">
            <i class="fa fa-gift"></i>人员速查
        </div>
    </div>
    <div class="portlet-body">
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-4">
                    <label>单位</label>
                    <ul id="companyTree" class="ztree"></ul>
                </div>
                <div class="col-md-4">
                    <label>部门</label>
                    <ul id="departmentTree" class="ztree"></ul>
                </div>
                <div class="col-md-4">
                    <label>人员</label>
                    <ul id="empTree" class="ztree"></ul>
                </div>
            </div>
        </div>
    </div>
</div>

<script>

    $(function () {
        var companyTreeObj;
        var departmentTreeObj;
        var psnTreeObj;
        var initTree = function (_arr,treeId,callback,settings) {
            var _setting = {
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
            return $.fn.zTree.init($(treeId), _setting, _arr);
        }



        var getEmpByDepartment=function(orgId, deptId){
            $.post('<c:url value="/hr/getAllEmpListData"/>',{company:orgId,department:deptId},function (result) {
                if (result){
                    psnTreeObj = initTree(result,'#empTree',function (event, treeId, treeNode) {
                        window.open('<c:url value="/hr/show/"/>'+treeNode.id);
                    });
                }
            },'json');
        }

        var getDepartments = function (orgId) {
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


    });



</script>
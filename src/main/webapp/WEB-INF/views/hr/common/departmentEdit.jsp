<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="portlet box blue-hoki">
    <div class="portlet-title">
        <div class="caption">
            <i class="fa fa-gift"></i>部门管理
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
                    <button type="button" class="btn btn-default btn-sm js_add_dept hidden">添加</button>
                </div>
            </div>
        </div>
    </div>
</div>

<script>

    $(function () {
        var companyTreeObj;
        var departmentTreeObj;
        var initTree = function (_arr,treeId,callback) {
            var setting = {
                callback:{
                    onClick:function (event, treeId, treeNode) {
                        if ("function" == typeof(callback)){
                            callback(event,treeId,treeNode);
                        }
                    },
                    beforeRemove:confirmRemoveNode,
                    onRename:confirmRenameNode
                },
                data:{
                    key:{
                        children:"childList",
                        title:"name"
                    }
                }
            };
            if (treeId=="#departmentTree"){
                setting. view={
                    selectedMulti: false
                };
                setting.edit={
                    enable: true,
                    editNameSelectAll: true,
                    showRemoveBtn:showRemoveBtn
                }
            }
            return $.fn.zTree.init($(treeId), setting, _arr);
        }

        $("button.js_add_dept").on('click',function () {
            if (departmentTreeObj){
                var deptNodes = departmentTreeObj.getSelectedNodes();
                var cmpNodes = companyTreeObj.getSelectedNodes();
                var obj ={
                    name:"请输入名称"
                }
                if (cmpNodes.length==1){
                    obj.companyId=cmpNodes[0].id;
                    obj.isParent=0;
                }else {
                    return;
                }
                if (deptNodes.length==1){
                    obj.parentId=deptNodes[0].id;
                }else{
                    obj.parentId=0;
                }
                $.post('<c:url value="/hr/org/addDepartment"/>',obj,function (result) {
                    if (result.success) {
                        var node = result.rdata;
                        departmentTreeObj.addNodes(deptNodes.length==1?deptNodes[0]:null, {id:node.id, name:"请输入名称",companyId:node.companyId});
                    }
                }, 'json');
            }
        });

        function confirmRemoveNode(treeId, treeNode) {
            layer.confirm('确定删除该部门？',function (index) {
                var obj = {
                    companyId:treeNode.companyId,
                    id:treeNode.id,
                    status:1
                };
                var node = treeNode.getParentNode();
                if (null!=node&&node.childList.length==1){
                    obj.parentId=node.id;
                }
                $.post('<c:url value="/hr/org/updateDepartment"/>', obj,function (result) {
                    if (result.success) {
                        layer.close(index);
                        departmentTreeObj.removeNode(treeNode);
                    }else {
                        var rmsg = result.rmsg;
                        if(rmsg){
                            layer.msg(rmsg);
                        }else{
                            layer.msg("删除部门失败");
                        }
                    }
                }, 'json');
            });
            return false;
        };

        function showRemoveBtn(treeId, treeNode) {
            return !treeNode.isParent;
        }

        function confirmRenameNode(event, treeId, treeNode, isCancel) {
            var cmpNodes = companyTreeObj.getSelectedNodes();
            $.post('<c:url value="/hr/org/updateDepartment"/>', {companyId:cmpNodes[0].id,id:treeNode.id,name:treeNode.name},function (result) {
                if (!result.success) {
                    layer.msg("修改部门名称失败");
                }else{
                    departmentTreeObj.cancelSelectedNode(treeNode);
                }
            }, 'json');
        }


        var getDepartments = function (orgId) {
            $.post('<c:url value="/hr/org/getAllDepartments"/>',{companyId:orgId},function (result) {
                if (result.success){
                    departmentTreeObj = initTree(result.rdata,"#departmentTree");
                    $('button.js_add_dept').removeClass('hidden');
                }
            },'json');
        };
        (function () {
            $.post('<c:url value="/hr/org/getAllCompanies"/>', function (result) {
                if (result.success) {
                    companyTreeObj = initTree(result.rdata, "#companyTree", function (event, treeId, treeNode) {
                        getDepartments(treeNode.id);
                    });
                    var nodes = companyTreeObj.getNodes();
                    companyTreeObj.expandNode(nodes[0],true,false,false);
                }
            }, 'json');
        })();


    });



</script>
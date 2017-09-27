<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="portlet box blue-hoki">
    <div class="portlet-title">
        <div class="caption">
            <i class="fa fa-gift"></i>公司管理
        </div>
    </div>
    <div class="portlet-body">
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-3">
                    <label>选择单位</label>
                    <ul id="companyTree" class="ztree"></ul>
                    <button type="button" class="btn btn-default btn-sm js_add_comp">添加</button>
                </div>
            </div>
        </div>
    </div>
</div>

<script>

    $(function () {
        var companyTreeObj;
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
                        title:"code"
                    }
                }
            };
            setting. view={
                selectedMulti: false
            };
            setting.edit={
                enable: true,
                editNameSelectAll: true,
                showRemoveBtn:showRemoveBtn
            }
            return $.fn.zTree.init($(treeId), setting, _arr);
        }

        var index = 1200;
        $("button.js_add_comp").on('click',function () {
            if (companyTreeObj){
                var cmpNodes = companyTreeObj.getSelectedNodes();
                var obj ={
                    name:"请输入名称"
                }
                if (cmpNodes.length==1){
                    obj.parentId=cmpNodes[0].id;
                    obj.isParent=0;
                    var children = cmpNodes[0].childList;
                    if(children){
                        if(children.length<9){
                            obj.code=cmpNodes[0].code+0+(parseInt(children.length)+1);
                        }else if(children.length==9){
                            obj.code=cmpNodes[0].code+10;
                        }else{
                            obj.code=parseInt(children[children.length-1].code)+1;
                        }
                    }else{
                        obj.code=cmpNodes[0].code+"01";
                    }
                }else {
                    return;
                }
                $.post('<c:url value="/hr/org/addCompany"/>',obj,function (result) {
                    if (result.success) {
                        var node = result.rdata;
                        companyTreeObj.addNodes(cmpNodes.length==1?cmpNodes[0]:null, {id:node.id, name:"请输入名称",code:node.code});
                    }
                }, 'json');
            }
        });

        function confirmRemoveNode(treeId, treeNode) {
            layer.confirm('确定删除该公司？',function (index) {
                var obj = {
                    id:treeNode.id,
                    status:1
                };
                var node = treeNode.getParentNode();
                if (null!=node&&node.childList.length==1){
                    obj.parentId=node.id;
                }
                $.post('<c:url value="/hr/org/delCompany"/>', obj,function (result) {
                    if (result.success) {
                        layer.close(index);
                        companyTreeObj.removeNode(treeNode);
                    }else {
                        if (result.rmsg){
                            layer.msg(result.rmsg);
                        }else{
                            layer.msg("删除公司失败");
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
            $.post('<c:url value="/hr/org/updateCompany"/>', {id:treeNode.id,name:treeNode.name},function (result) {
                if (!result.success) {
                    layer.msg("修改公司名称失败");
                }else{
                    companyTreeObj.cancelSelectedNode(treeNode);
                }
            }, 'json');
        }


        (function () {
            $.post('<c:url value="/hr/org/getAllCompanies"/>', function (result) {
                if (result.success) {
                    companyTreeObj = initTree(result.rdata, "#companyTree", function (event, treeId, treeNode) {

                    });
                    var nodes = companyTreeObj.getNodes();
                    companyTreeObj.expandNode(nodes[0],true,false,false);
                }
            }, 'json');
        })();


    });



</script>
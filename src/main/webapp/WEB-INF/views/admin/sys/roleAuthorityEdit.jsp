<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="portlet box blue-hoki">
    <div class="portlet-title">
        <div class="caption">
            <i class="fa fa-user"></i>
            角色：${role.name}
        </div>
    </div>
    <div class="portlet-body">
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-4">
                    <h4>菜单关联</h4>
                    <label><button type="button" class="btn btn-sm js_choose" role="menuTree">全选</button></label>
                    <ul id="menuTree" class="ztree"></ul>
                    <div class="form-actions">
                        <button type="button" class="btn btn-sm green js_save_chg">保存</button>
                    </div>
                </div>
                <div class="col-md-4">
                    <h4>权限关联</h4>
                    <label><button type="button" class="btn btn-sm js_choose" role="resourceTree">全选</button></label>
                    <ul id="resourceTree" class="ztree"></ul>
                    <div class="form-actions">
                        <button type="button" class="btn btn-sm green js_save_role_resource">保存</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript" src="<c:url value="/resources/plugIn/zTree/js/jquery.ztree.excheck.js" />"></script>


<script>

    $(document).ready(function () {

        var initTree = function (_arr, treeId, callback, _id) {
            var setting = {
                callback:{
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
                        id:_id,
                        children:"list",
                        title:"name",
                        icon:null,
                        url:null
                    }
                },check:{
                    enable: true,
                    chkboxType :{"Y": "ps", "N": "ps"}
                },view:{
                    expandSpeed:''
                }};
            return $.fn.zTree.init($('#'+treeId), setting, _arr);
        }

        var menuTreeObj = null;
        $.post('<c:url value="/admin/sys/getRoleMenus"/>',{roleId:'${roleId}'},function (result) {
            if (result){
                menuTreeObj = initTree(result.rdata,'menuTree',function (event, treeId, treeNode) {
                    if(!treeNode.isParent){
                        <%--$.post('<c:url value="/admin/sys/getResourceByRoleMenu"/>',{menuId:treeNode.menuId,roleId:'${roleId}'},function (result) {--%>
                            if(!treeNode.checked){
                                var node = resourceTreeObj.getNodeByParam("menuId",treeNode.menuId,null);
                                if (node!=null){
                                    resourceTreeObj.checkNode(node,false,true);
                                }
                            }else{
                                var node = resourceTreeObj.getNodeByParam("menuId",treeNode.menuId,null);
                                if (node!=null){
                                    resourceTreeObj.checkNode(node,true,true);
                                }
                            }
//                        },'json');
                    }
                },"menuId");
            }
        },'json');

        /**
         *
         * 保存
         */
        var _index = null;
        $('button.js_save_chg').on('click',function () {
            var chgList = menuTreeObj.getChangeCheckedNodes();
            var _arr = new Array();
            for (var i in chgList){
                var obj = chgList[i];
                if (obj.checked){
                    _arr.push({roleId:${roleId},menuId:obj.menuId,checked:true});
                }else{
                    _arr.push({roleId:${roleId},menuId:obj.menuId,checked:false});
                }
            }
            if (_arr.length>0){
                _index = layer.load(2);
                $.post('<c:url value="/admin/sys/saveRoleMenus"/>',{roleMenus:JSON.stringify(_arr)},function (result) {
                    layer.close(_index);
                    if (result.success){
                        layer.msg('操作成功');
                    }else{
                        layer.msg('操作失败,'+result.rmsg);
                    }
                },'json');
            }
        });

        var resourceTreeObj = null;
        $.post('<c:url value="/admin/sys/getResourceByRoleMenu"/>',{roleId:'${roleId}'},function (result) {
            if (result.success){
                resourceTreeObj = initTree(result.rdata,'resourceTree',function (event, treeId, treeNode) {
                    var mNode = menuTreeObj.getNodeByParam("menuId",treeNode.menuId,null);
                    if(treeNode.checked){
                        if (mNode != null){
                            menuTreeObj.checkNode(mNode,true,true);
                        }
                    }else{
                        var rNodes = resourceTreeObj.getNodesByParam("menuId",treeNode.menuId,null);
                        if (rNodes.length>1){
                            var flag = true;
                            $.each(rNodes,function (k, v) {
                                if(v.checked){
                                    flag = false;
                                    return flag;
                                }
                            });
                            if (flag){
                                menuTreeObj.checkNode(mNode, false,true);
                            }
                        }else{
                            menuTreeObj.checkNode(mNode, false,true,true);
                        }
                    }
                },"rid");
            }
        },'json');

        /**
         *
         * 保存
         */
        var _index = null;
        $('button.js_save_role_resource').on('click',function () {
            var chgList = resourceTreeObj.getChangeCheckedNodes();
            var _arr = new Array();
            for (var i in chgList){
                var obj = chgList[i];
                if (obj.checked){
                    _arr.push({roleId:${roleId},rid:obj.rid,checked:true});
                }else{
                    _arr.push({roleId:${roleId},rid:obj.rid,checked:false});
                }
            }
            if (_arr.length>0){
                _index = layer.load(2);
                $.post('<c:url value="/admin/sys/saveRoleResource"/>',{roleResources:JSON.stringify(_arr)},function (result) {
                    layer.close(_index);
                    if (result.success){
                        layer.msg('操作成功');
                    }else{
                        layer.msg('操作失败,'+result.rmsg);
                    }
                },'json');
            }
        });

        $('button.js_choose').on('click',function () {
           var _id = $(this).attr('role');
            var treeObj = $.fn.zTree.getZTreeObj(_id);
            var nodes = treeObj.getNodes();
            $.each(nodes,function (k, v) {
                if(!v.checked){//选中
                    treeObj.checkNode(v,!v.checked,true,true,true);
                }else{
                    if(_id=='resourceTree'){
                        var nodes = treeObj.getNodesByParam("menuId",v.menuId,null);
                        if (nodes.length>1){
                            treeObj.checkNode(v, !v.checked,true,false);
                        }else{
                            treeObj.checkNode(v, false,true,true);
                        }
                    }else{
                        treeObj.checkNode(v, !v.checked,true,true);
                    }
                }
            });
        });

    });

</script>
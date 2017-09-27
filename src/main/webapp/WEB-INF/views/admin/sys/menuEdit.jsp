<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="portlet box blue-hoki">
    <div class="portlet-title">
        <div class="caption">菜单管理</div>
    </div>
    <div class="portlet-body">
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-3">
                    <ul id="menuTree" class="ztree"></ul>
                    <button type="button" class="btn btn-default btn-sm js_add_menu">添加</button>
                </div>
                <div class="col-md-6 hidden js_menu_detail">
                    <form  role="form" class="js_base_form">
                        <div class="form-body">
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label>名称</label>
                                        <input type="text" class="form-control" name="name">
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label>链接</label>
                                        <input type="text" class="form-control"  name="url">
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label>Id</label>
                                        <input type="text" class="form-control" name="menuId">
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label>父级Id</label>
                                        <input type="text" class="form-control"  name="parentId">
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label>图标代码</label>
                                        <input type="text" class="form-control" name="icon">
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label>所属系统</label>
                                        <input type="text" class="form-control" name="regSystem">
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label>排序</label>
                                        <input type="text" class="form-control" name="sort">
                                    </div>
                                </div>
                                <div class="col-md-6 ">
                                    <div class="form-group">
                                        <label>状态</label>
                                        <div>
                                            <label><input type="radio" name="status" value="1">显示</label>
                                            <label><input type="radio" name="status" value="0"> 隐藏</label>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="form-actions">
                            <button type="button" class="btn green js_save_menu" data-action="save">保存</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<script>

    $(function () {
        var menuTreeObj;
        var initMenuTree = function (_arr,treeId,callback) {
            var setting = {
                callback:{
                    onClick:function (event, treeId, treeNode) {
                        if ("function" == typeof(callback)){
                            callback(event,treeId,treeNode);
                        }
                    },
                    beforeRemove:confirmRemoveNode
                },
                data:{
                    key:{
                        id:"menuId",
                        children:"list",
                        title:"name",
                        icon:null
                    }
                },view:{
                    selectedMulti: false
                },edit:{
                    enable: true,
                    editNameSelectAll: false,
                    showRemoveBtn:showRemoveBtn,
                    showRenameBtn:false
                }
            };
            return $.fn.zTree.init($(treeId), setting, _arr);
        }


        function confirmRemoveNode(treeId, treeNode) {
            layer.confirm('确定删除该菜单？',function (index) {
                layer.close(index);
                var obj = {
                    menuId:treeNode.menuId
                };
                var node = treeNode.getParentNode();
                if (null!=node&&node.list.length==1){
                    obj.parentId=node.menuId;
                }
                $.post('<c:url value="/admin/sys/deleteMenus"/>', obj,function (result) {
                    if (result.success) {
                        menuTreeObj.removeNode(treeNode);
                    }else {
                        if (result.rmsg){
                            layer.msg(result.rmsg);
                        }else{
                            layer.msg("删除菜单失败");
                        }
                    }
                }, 'json');
            });
            return false;
        };

        function showRemoveBtn(treeId, treeNode) {
            return !treeNode.isParent;
        }

        $.post('<c:url value="/admin/sys/getAllMenus"/>', function (result) {
            if (result.success) {
                menuTreeObj = initMenuTree(result.rdata, "#menuTree", function (event, treeId, treeNode) {
                    $('div.js_menu_detail').removeClass('hidden');
                    $('input[name=name]').val(treeNode.name);
                    $('input[name=url]').val(treeNode.url);
                    $('input[name=menuId]').val(treeNode.menuId);
                    $('input[name=parentId]').val(treeNode.parentId);
                    $('input[name=icon]').val(treeNode.icon);
                    $('input[name=regSystem]').val(treeNode.regSystem);
                    $('input[name=sort]').val(treeNode.sort);
                    $('input[name=status]').removeAttr('checked').parent('span').removeClass('checked');
                    $('input[name=status][value="'+treeNode.status+'"]').prop('checked',true).parent('span').addClass('checked');
                });
            }
        }, 'json');


        /**
         * 保存菜单
         */
        var _index = null;
        $('button.js_save_menu').on('click',function () {
            _index = layer.confirm('确定保存？',function () {
                layer.close(_index);
                var obj = {};
                var _arr = $('form.js_base_form').serializeArray();;
                for(var i in _arr ){
                    var t = _arr[i];
                    obj[t.name]=t.value;
                }
                var nodes = menuTreeObj.getSelectedNodes();
                var node = nodes[0];
                if(-1 == node.menuId){
                    obj.flag = true;
                }
                $.post('<c:url value="/admin/sys/saveMenus"/>',obj,function (result) {
                    if (result.success){
                        layer.msg('操作成功',{time:1000},function () {
                            for(var i in obj ){
                                node[i] = obj[i];
                            }
                            menuTreeObj.updateNode(node);
                        });
                    }else{
                        layer.msg('操作失败,'+result.rmsg);
                    }
                },'json');
            });
        });

        $("button.js_add_menu").on('click',function () {
            if (menuTreeObj){
                var nodes = menuTreeObj.getSelectedNodes();
                var pNode = null;
                var node = {menuId:-1, name:"新菜单",parentId:0};
                if(nodes.length==1){
                    pNode = nodes[0];
                    node.parentId = pNode.menuId;
                    node.regSystem = pNode.regSystem;
                    node.status = 1;
                    node.url = 'javascript:void(0)';
                }
                menuTreeObj.addNodes(pNode, node);
            }
        });

    });



</script>
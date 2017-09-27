<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="portlet box blue-hoki">
    <div class="portlet-title">
        <div class="caption">资源管理</div>
    </div>
    <div class="portlet-body">
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-6  js_menu_detail">
                    <form  role="form" class="js_base_form">
                        <div class="form-body">
                            <div class="row">
                                <div class="col-md-10">
                                    <div class="row">
                                        <div class="col-md-6">
                                            <div class="form-group">
                                                <label>资源名称</label>
                                                <input type="text" class="form-control" name="name" value="${entity.name}">
                                            </div>
                                        </div>
                                        <div class="col-md-6">
                                            <div class="form-group">
                                                <label>URL</label>
                                                <input type="text" class="form-control"  name="data" value="${entity.data}">
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-6">
                                            <div class="form-group">
                                                <label>类型</label>
                                                <select class="form-control" name="type">
                                                    <option value="url" <c:if test="${entity.type eq 'url'}">selected</c:if>>URL</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="col-md-6">
                                            <div class="form-group">
                                                <label>所属系统</label>
                                                <input type="text" class="form-control"  name="regSystem" value="${entity.regSystem}" readonly>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-6">
                                            <div class="form-group">
                                                <label>所属菜单</label>
                                                <input type="text" class="form-control" name="menu" readonly>
                                            </div>
                                        </div>
                                        <div class="col-md-6">
                                            <div class="form-group">
                                                <label>所属菜单ID</label>
                                                <input type="text" class="form-control" name="menuId" readonly>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-6 ">
                                            <div class="form-group">
                                                <label>状态(变更此项需要重启服务)</label>
                                                <div>
                                                    <label><input type="radio" name="status" value="1" <c:if test="${empty entity || entity.status eq 1}">checked</c:if>> 启用</label>
                                                    <label><input type="radio" name="status" value="0" <c:if test="${entity.status eq 0}">checked</c:if>> 停用</label>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-md-6">
                                            <div class="form-group">
                                                <label>描述</label>
                                                <input type="text" class="form-control" name="description" value="${entity.description}">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-2">
                                    <ul id="menuTree" class="ztree"></ul>
                                </div>
                            </div>
                        </div>
                        <div class="form-actions">
                            <button type="button" class="btn green js_save" data-action="save">保存</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript" src="<c:url value="/resources/plugIn/zTree/js/jquery.ztree.excheck.js" />"></script>
<script>

    $(function () {

        var menuId='${entity.menuId}';

        var checkField={
            name:"资源名称不能为空",
            type:"类型不能为空",
            regSystem:"所属系统不能为空",
            status:"状态不能为空",
            menuId:"所属菜单ID不能为空"
        };
        var checkObj = function (emp) {
            var flag = true;
            for(var e in checkField){
                if(!emp[e]){
                    layer.msg(checkField[e],{time:800});
                    flag = false;
                    break;
                }
            }
            return flag;
        }

        var _index=null;
        $('button.js_save').on('click',function () {
            var obj={};
            obj.rid = '${entity.rid}';
            obj.name = $('input[name=name]').val();
            obj.regSystem = $('input[name=regSystem]').val();
            obj.type = $('select[name=type]').val();
            obj.status = $('input[name=status]:checked').val();
            obj.data = $('input[name=data]').val();
            obj.description = $('input[name=description]').val();
            obj.menuId = $('input[name=menuId]').val();
            if(checkObj(obj)){
                _index = layer.load(2);
                var url
                if (obj.rid!=''){
                    url =  '<c:url value="/admin/sys/updateResource"/>';
                }else{
                    url ='<c:url value="/admin/sys/addResource"/>';
                }
                $.post(url, obj, function (result) {
                    layer.close(_index);
                    if (result.success) {
                        layer.msg('操作成功',{time:800},function () {
                            window.location.reload();
                        });
                    }else{
                        layer.msg('操作失败：'+result.rmsg,{time:2000});
                    }
                }, 'json');
            }
        });


        var menuTreeObj;
        var initMenuTree = function (_arr,treeId,callback) {
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
                },
                data:{
                    key:{
                        id:"menuId",
                        children:"list",
                        title:"name",
                        icon:null
                    }
                },edit:{
                    enable: true,
                    editNameSelectAll: false,
                    showRemoveBtn:false,
                    showRenameBtn:false
                },check:{
                    enable: true,
                    chkboxType :{"Y": "p", "N": "ps"}
                },view: {
                    expandSpeed: ""
                }
            };
            return $.fn.zTree.init($(treeId), setting, _arr);
        }


        $.post('<c:url value="/admin/sys/getAllMenus"/>', function (result) {
            if (result.success) {
                menuTreeObj = initMenuTree(result.rdata, "#menuTree", function (event, treeId, treeNode) {
                    if (treeNode.checked){
                        $('input[name=menuId]').val(treeNode.menuId);
                        $('input[name=menu]').val(treeNode.name);
                        $('input[name=regSystem]').val(treeNode.regSystem);
                        var nodes = menuTreeObj.getCheckedNodes(true);
                        for(var i =0;i<nodes.length;i++){
                            menuTreeObj.checkNode(nodes[i],false,true);
                        }
                        menuTreeObj.checkNode(treeNode,true,true);
                    }else{
                        $('input[name=menuId]').val('');
                        $('input[name=menu]').val('');
                        $('input[name=regSystem]').val('');
                    }
                });
                if(menuId){
                    var node = menuTreeObj.getNodeByParam("menuId",menuId,null);
                    menuTreeObj.checkNode(node,true,true);
                    menuTreeObj.expandNode(node.getParentNode(), true, true, true);
                    $('input[name=menuId]').val(node.menuId);
                    $('input[name=menu]').val(node.name);
                }

            }
        }, 'json');


    });



</script>
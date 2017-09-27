<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="portlet light">
    <div class="portlet-title">
        <div class="caption">
            <i class="icon-layers font-green"></i>
            <span class="caption-subject font-green sbold uppercase">业态列表</span>
        </div>
        <div class="actions form-inline">
            <button class="btn blue js_add_type"><i class="fa fa-plus-circle"></i>新增业态</button>
        </div>
    </div>
    <div class="portlet-body">
        <div class="row">
            <div class="col-md-12">
                <table class="table table-width table-striped table-bordered table-advance table-hover table-checkable " id="datatable_ajax">
                    <thead>
                    <tr role="row" class="heading">
                        <th width="3%">序号</th>
                        <th width="4%">名称</th>
                        <th width="5%">相关操作</th>
                    </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${requestScope.entity}" var="entity" varStatus="m">
                            <tr>
                                <td>${m.count}</td>
                                <td>${entity.name}</td>
                                <td>
                                    <a href="javascript:void(0)" class="doedit" data-id="${entity.id}" data-name="${entity.name}">编辑</a>
                                    <a class="dodel" href="javascript:void(0)"  data-id="${entity.id}" >删除</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<div class="modal fade js_type_div " data-backdrop="static">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title">业态</h4>
            </div>
            <div class="modal-body">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-md-12">
                            <label>业态名称</label>
                            <input type="text" name="name" />
                            <input type="hidden" name="id" />
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary js_save_btn">确定</button>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/datatables/datatables.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/scripts/datatable.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js" />"></script>

<script>

$(function () {

    $('button.js_add_type').on('click',function () {
        $('div.js_type_div').modal('show');
    });

    $('button.js_save_btn').on('click',function () {
        var _name = $.trim($('input[name=name]').val());
        var _id = $.trim($('input[name=id]').val());
        var _url ;
        if (_id) {
            _url = "<c:url value="/admin/projectcost/setting/updateType"/>";
        } else {
            _url = "<c:url value="/admin/projectcost/setting/addType"/>";
        }
        $.post(_url, {"name":_name,"id":_id}, function (result) {
            if (result.success) {
                $('div.js_tree_div').modal('hide');
                layer.msg('操作成功',{time:800},function () {
                    window.location.reload();
                });
            } else {
                layer.msg('操作失败：'+result.rmsg,{time:2000});
            }

        });
    });

    $('a.doedit').click(function () {
        var _this =$(this);
        var _modal =$('div.js_type_div');
        _modal.find('input[name=id]').val(_this.data('id'));
        _modal.find('input[name=name]').val(_this.data('name'));
        _modal.modal('show').find('div.row :eq(0)').removeClass('hidden');
    });

    $('a.dodel').click(function () {
        var id = $(this).data('id');
        layer.confirm('你确定要删除吗？', {
            btn: ['确定','取消']
        }, function(_index){
            layer.close(_index);
            $.post("<c:url value="/admin/projectcost/setting/deleteType"/>", {"id":id}, function (data) {
                if (data.success){
                    layer.msg('操作成功',{time:800},function () {
                        window.location.reload();
                    });
                }else {
                    layer.msg(data.rmsg, {time: 1000});
                }
            });
        });

    });


});
</script>
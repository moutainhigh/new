<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!-- BEGIN EXAMPLE TABLE PORTLET-->
<div class="portlet light" id="form_wizard_1">
    <div class="portlet-title">
        <div class="caption">
        	<i class="icon-layers font-green"></i>
            <span class="caption-subject font-green sbold uppercase">用户列表</span>
       	</div>
        <div class="actions form-inline">
            <a href="<c:url value="/admin/bill/user/edit"/>" class="btn sbold blue"><i class="fa fa-plus-circle"></i> 新增</a>
        </div>
    </div>
    <div class="portlet-body">
        <div class="actions form-inline">
            <div class="form-group">
                用户姓名：<input type="text" name="name">
            </div>
            <div class="form-group">
                帐号：<input type="text" name="username">
            </div>
            <div class="form-group"><button class="btn btn-sm green js_search"><i class="fa fa-search"></i> 搜索 </button></div>
        </div>
        <table class="table table-striped table-bordered table-advance table-hover" id="datatable_ajax">
            <thead>
                <tr>
                    <th>序号</th>
                    <th>所属系统</th>
                    <th>用户代码</th>
                    <th>用户名称</th>
                    <th>创建日期</th>
                    <th> 操作 </th>
                </tr>
            </thead>
            <tbody>

            </tbody>
        </table>
    </div>
</div>

<script src="<c:url value="/resources/admin/global/scripts/datatable.js" />"></script>
<script src="<c:url value="/resources/admin/global/plugins/datatables/datatables.min.js" />"></script>
<script src="<c:url value="/resources/admin/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js" />"></script>
<script type="text/javascript">

    $(function () {

        var dataTablesAjax = function() {
            var table = new Datatable;
            table.init({
                src: $("#datatable_ajax"),
                loadingMessage: "Loading...",
                dataTable: {
                    bStateSave: !0,
                    lengthMenu: [[10, 20, 50, 100], [10, 20, 50, 100]],
                    pageLength: 10,
                    ajax: {
                        url: "<c:url value="/admin/bill/user/listData"/>"
                    },
                    order: [[1, "asc"]],
                    "orderCellsTop": true,
                    "columnDefs": [{
                        'orderable': false,
                    }],
                    columns:[
                        {"data":"userId", "targets": 0},
                        {"data":"regSource"},
                        {"data":"username"},
                        {"data":"name"},
                        {"data":"createDate"},
                        {"data":"userId","render" : function(data, type, full, meta ){
                            return ' <button class="btn btn-xs green js_user_edit" data-id="'+data+'" data-reg="'+full.regSource+'"><i class="fa fa-plus-circle"></i>修改</button>'+
                                       '<button class="btn btn-xs green js_user_del" data-id="'+data+'" data-reg="'+full.regSource+'"><i class="fa fa-plus-circle"></i> 删除 </button>';
                        }}
                    ],
                    fnDrawCallback: function(){
                        var api = this.api();
                        var startIndex= api.context[0]._iDisplayStart;
                        api.column(0).nodes().each(function(cell, i) {
                            cell.innerHTML = startIndex + i + 1;
                        });
                    },"initComplete":function () {

                    }
                }
            });
            return table;
        };

        var table = dataTablesAjax();
        $('button.js_search').on('click',function () {
            table.clearAjaxParams();
            table.setAjaxParam("name", $('input[name=name]').val());
            table.setAjaxParam("username", $('input[name=username]').val());
            table.getDataTable().ajax.reload();
        });


        /**
         * 删除用户
         */
        $('button.js_user_del').on('click',function () {
            var _id = $(this).data('id');
            var _reg = $(this).data('reg');
            layer.confirm("确定删除该用户？",function () {
                $.post('<c:url value="/admin/bill/user/delete"/>',{id:_id,reg:_reg},function (result) {
                    if (result==0){
                        layer.msg("保存失败");
                    }else if(result==2){
                        layer.msg("参数异常");
                    }else {
                        window.location.reload();
                    }
                },'json');
            });
        });

        /**
         * 修改
         */
        $('#datatable_ajax').on('click','button.js_user_edit',function () {
            var _userId = $(this).data('id');
            var _reg = $(this).data('reg');
            window.location.href='<c:url value="/admin/bill/user/edit/"/>'+_userId+"/"+_reg;
        });

    });


</script>
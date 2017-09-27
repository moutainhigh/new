<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/hr/css/hr.css" />"/>
<div class="portlet light" id="form_wizard_1">
    <div class="portlet-title">
        <div class="caption">
            <i class="icon-layers font-green"></i>
            <span class="caption-subject font-green sbold uppercase">招聘列表</span>
        </div>
        <div class="actions form-inline">
            <a href="<c:url value="/hr/hrContent/edit"/>"><button class="btn sbold blue js_emp_add"><i class="fa fa-plus-circle"></i>新增</button></a>
        </div>
    </div>
    <div class="portlet-body">
        <div class="actions form-inline">
            <div class="form-group">姓名：<input name="name" class="form-control input-inline input-sm" type="text" value=""/></div>
            <div class="form-group">电话：<input name="mobile" class="form-control input-inline input-sm" type="text"/></div>
            <div class="form-group">
                应聘板块：<select name="plateId" class="form-control input-sm">
                                <c:if test="${user.plateId ==1 }"><option value="1" selected>地产集团</option></c:if>
                                <c:if test="${user.plateId ==2 }"><option value="2" selected>商业集团</option></c:if>
                                <c:if test="${user.plateId ==3 }"><option value="3" selected>金控集团</option></c:if>
                                <c:if test="${user.plateId ==4 }"><option value="4" selected>建设集团</option></c:if>
                                <c:if test="${user.plateId ==5 }"><option value="5" selected>物业集团</option></c:if>
                                <c:if test="${user.plateId ==0}">
                                    <option value="0" >集团总部</option>
                                    <option value="6" >海外公司</option>
                                </c:if>
                            </select>
            </div>
            <div class="form-group">
                区域：<select name="area" class="form-control input-sm">
                        <option value="1">重庆区域</option>
                        <option value="2">四川区域</option>
                        <option value="3">华东区域</option>
                        <option value="4">上海区域</option>
                        <option value="5">长沙城市公司</option>
                        <option value="6">沈阳城市公司</option>
                    </select>
            </div>
            <div class="form-group">
                状态：<select name="status" class="form-control input-sm">
                        <option value="0">未处理</option>
                        <option value="1">已处理</option>
                     </select>
            </div>
            <div class="form-group"><button class="btn btn-sm green js_search"><i class="fa fa-search"></i> 搜索 </button></div>
        </div>
        <table class="table table-width table-striped table-bordered table-advance table-hover th-weight" id="datatable_ajax">
            <thead>
            <tr role="row">
                <th width="3%">序号</th>
                <th width="5%">姓名</th>
                <th width="5%">电话</th>
                <th width="5%">应聘职位</th>
                <th width="5%">应聘板块</th>
                <th width="3%">区域</th>
                <th width="3%">申请时间</th>
                <th width="5%">状态</th>
                <th width="5%">操作</th>
            </tr>
            </thead>
        </table>
    </div>
</div>
<script src="<c:url value="/resources/admin/global/scripts/datatable.js" />"></script>
<script src="<c:url value="/resources/admin/global/plugins/datatables/datatables.min.js" />"></script>
<script src="<c:url value="/resources/admin/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js" />"></script>
<script>

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
                    url: "<c:url value="/hr/hrContent/getJobRequestListData"/>"
                },
                order: [[1, "asc"]],
                columns:[
                    {"data":"id", "targets": 0},
                    {"data":"name"},
                    {"data":"mobile"},
                    {"data":"jobStr"},
                    {"data":"plateId","render" : function(data, type, full, meta ){
                        var text;
                        switch (data) {
                            case 0:text='集团总部';break;
                            case 1:text='地产集团';break;
                            case 2:text='商业集团';break;
                            case 3:text='金控集团';break;
                            case 4:text='建设集团';break;
                            case 5:text='物业集团';break;
                            case 6:text='海外公司';break;
                        }
                        return text;
                    }},
                    {"data":"area","render" : function(data, type, full, meta ){
                        var text;
                        switch (data) {
                            case 1:text='重庆区域';break;
                            case 2:text='四川区域';break;
                            case 3:text='华东区域';break;
                            case 4:text='上海区域';break;
                        }
                        return text;
                    }},
                    {"data":"createtime"},
                    {"data":"status","render" : function(data, type, full, meta ){
                        return data==undefined?'':(data==0?'未处理':'已处理');
                    }},
                    {"data":"id","render" : function(data, type, full, meta ){
                        var editUrl = "<c:url value='/hr/hrContent/jobRequestOne?id='/>"+data;
                        return '<a href="'+editUrl+'" class="btn btn-sm btn-outline grey-salsa" target="_blank"><i class="fa fa-edit"></i> 查看</a>'+
                            '<a href="javascript:void(0)" class="btn btn-sm btn-outline grey-salsa js_remove" data-id="'+data+'"><i class="fa fa-remove"></i> 删除</a>';
                    }}
                ],
                fnDrawCallback: function(){
                    var api = this.api();
                    var startIndex= api.context[0]._iDisplayStart;//获取到本页开始的条数
                    api.column(0).nodes().each(function(cell, i) {
                        cell.innerHTML = startIndex + i + 1;
                    });
                },"initComplete":function () {


                }
            }
        });
        return table;
    };

    $(document).ready(function() {
        var table = dataTablesAjax();
        $('button.js_search').on('click',function () {
            table.clearAjaxParams();
            table.setAjaxParam("name", $('input[name=name]').val());
            table.setAjaxParam("mobile", $('input[name=mobile]').val());
            table.setAjaxParam("plateId", $('select[name=plateId]').val());
            table.setAjaxParam("area", $('select[name=area]').val());
            table.setAjaxParam("status", $('select[name=status]').val());
            table.getDataTable().ajax.reload();
        });

        $('#datatable_ajax').on('click','a.js_remove',function () {
            var _id = $(this).data('id');
            var _index = layer.confirm("您确定要删除该记录？",function () {
                $.post('<c:url value='/hr/hrContent/delJobRequestOne'/>', {id:_id}, function (result) {
                    if (result.success){
                        layer.close(_index);
                        table.getDataTable().ajax.reload();
                    }else {
                        layer.close(_index);
                        layer.msg(result.rmsg);
                    }
                },'json');
            });
        });
    });
</script>
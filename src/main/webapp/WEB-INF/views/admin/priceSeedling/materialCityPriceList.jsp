<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>



<style>
    td {
        text-align:center;
    }
</style>

<div class="portlet light">
    <div class="portlet-title">
        <div class="caption">
            <i class="icon-layers font-green"></i>
            <span class="caption-subject font-green sbold uppercase">各城市材料价格列表</span>
        </div>
    </div>
    <div class="actions form-inline">
        <form class="navbar-form navbar-left" role="search" id="materialSearchForm">
        <div class="row">
            <div class="form-group">
                <input type="hidden" value="" name="categoryId">
                <button type="button" class="btn btn-primary js_choose_category">选择类别</button>
            </div>
            <div class="form-group">
                <input type="text" name="mcode" class="form-control" value="" placeholder="苗木编码">
            </div>
            <div class="form-group">
                <input type="text" name="name" class="form-control" value="" placeholder="苗木名称">
            </div>
            <div class="form-group">
                <input type="text"  name="specification" class="form-control" value="" placeholder="冠幅">
            </div>
            <div class="form-group">
                <input type="text"  name="specification2" class="form-control" value="" placeholder="米径">
            </div>
            <div class="form-group">
                <input type="text"  name="specification3" class="form-control" value="" placeholder="高度">
            </div>
        </div>
        <div class="row">
            <div class="form-group">
                <input type="text"  name="specification4" class="form-control" value="" placeholder="分枝点">
            </div>
            <div class="form-group">
                <input type="text"  name="specification5" class="form-control" value="" placeholder="树形及要求">
            </div>
            <div class="form-group">
                <input type="text"  name="specification6" class="form-control" value="" placeholder="土球直径">
            </div>
            <div class="form-group">
                <input type="text"  name="specification7" class="form-control" value="" placeholder="土球厚度">
            </div>
            <div class="form-group">
                <input type="text"  name="extend" class="form-control" value="" placeholder="苗木特点">
            </div>
            <div class="form-group">
                <input type="text"  name="extend2" class="form-control" value="" placeholder="种植密度标准">
            </div>

            <button type="button"  class="btn btn-default js_search">搜索</button>
            <button type="button" class="btn btn-default js_reset">重置</button>
        </div>
        </form>
    </div>
    <div class="portlet-body">
        <div class="row">
            <div class="col-md-12">
                <table class="table table-width table-striped table-bordered table-advance table-hover table-checkable " id="datatable_ajax">
                    <thead>
                    <tr role="row" class="heading" align="center">
                        <%--<th width="2%" rowspan="2" align="center" valign="middle">编号</th>--%>
                        <%--<th width="2%" rowspan="2" align="center" valign="middle">材料名称</th>--%>
                        <%--<th width="2%" rowspan="2">规格</th>--%>
                        <%--<th width="2%" rowspan="2">单位</th>--%>
                        <th rowspan="2" align="center" valign="middle">苗木类别</th>
                        <th rowspan="2" align="center" valign="middle">苗木编码</th>
                        <th rowspan="2" align="center" valign="middle">苗木名称</th>
                        <th rowspan="2" align="center" valign="middle">冠幅</th>
                        <th rowspan="2" align="center" valign="middle">米径</th>
                        <th rowspan="2" align="center" valign="middle">高度</th>
                        <th rowspan="2" align="center" valign="middle">分枝点</th>
                        <th rowspan="2" align="center" valign="middle">土球直径</th>
                        <th rowspan="2" align="center" valign="middle">土球厚度</th>
                        <th rowspan="2" align="center" valign="middle">苗木特点</th>
                        <th rowspan="2" align="center" valign="middle">种植密度标准</th>
                        <th rowspan="2" align="center" valign="middle">单位</th>
                        <th width="7%" colspan="5">各城市价格(元)</th>
                    </tr>
                    <tr role="row" class="heading" align="center">
                        <th width="2%">重庆</th>
                        <th width="2%">成都</th>
                        <th width="2%">江苏</th>
                        <th width="2%">合肥</th>
                        <th width="2%">苏州</th>
                    </tr>
                    </thead>
                </table>
            </div>
        </div>
    </div>
</div>


<script src="<c:url value="/resources/admin/global/scripts/datatable.js" />"></script>
<script src="<c:url value="/resources/admin/global/plugins/datatables/datatables.min.js" />"></script>
<script src="<c:url value="/resources/admin/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js" />"></script>

<script>
    //表单校验
    $(function () {

        var TableDatatablesAjax = function() {
            var  e = function() {
                var table = new Datatable;
                table.init({
                    src: $("#datatable_ajax"),
                    loadingMessage: "Loading...",
                    dataTable: {
                        bStateSave: !0,
                        lengthMenu: [[10, 20, 50, 100], [10, 20, 50, 100]],
                        searching:true,
                        pageLength: 10,
                        ajax: {
                            url: "<c:url value="/admin/priceSeedlingPrice/cityPrice/listData"/>"
                        },
                        order: [[1, "asc"]],
                        columns:[
                            {"data":"categoryName","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"mcode","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"name","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"specification","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"specification2","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"specification3","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"specification4","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"specification6","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"specification7","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"extend","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"extend2","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"unit","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"pcq","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"pcd","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},

                            {"data":"pjs","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"phf","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"psz","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }}
                        ],
                        "initComplete":function () {

                        }
                    }
                });
                return table;
            };
            return {
                init: function() {
                    return e();
                }
            }
        } ();

        var table = TableDatatablesAjax.init();

        $('button.js_search').click(function () {
            table.clearAjaxParams();
            var _arr = $('#materialSearchForm').serializeArray();
            for (var i in _arr){
                table.setAjaxParam(_arr[i].name,$.trim(_arr[i].value));
            }
            table.getDataTable().ajax.reload();
        });

        $('button.js_reset').click(function () {
            table.clearAjaxParams();
            var _arr = $('#materialSearchForm').serializeArray();
            for (var i in _arr){
                $('input[name='+_arr[i].name+']').val('');
            }
            table.getDataTable().ajax.reload();
        });


    });

</script>
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
            <span class="caption-subject font-green sbold uppercase">各城市分项分类价格列表</span>
        </div>
    </div>
    <div class="actions form-inline">
        <div class="form-group">
            <input type="text" name="mcode" class="form-control" value="" placeholder="分项分类编码">
        </div>
        <div class="form-group">
            <input type="text"  name="name" class="form-control"  placeholder="分项分类名称">
        </div>
        <div class="form-group">
            <input type="text"  name="specification" class="form-control" value="" placeholder="特征描述">
        </div>
        <button type="button" id="buttonSearch1" class="btn btn-default js_search">搜索</button>
        <button type="button" class="btn btn-default js_reset">重置</button>
    </div>
    <div class="portlet-body">
        <div class="row">
            <div class="col-md-12">
                <table class="table table-width table-striped table-bordered table-advance table-hover table-checkable " id="datatable_ajax">
                    <thead>
                    <tr role="row" class="heading" align="center">
                        <th width="2%" rowspan="2" align="center" valign="middle">分项分类编码</th>
                        <th width="2%" rowspan="2" align="center" valign="middle">分项分类名称</th>
                        <th width="2%" rowspan="2">特征描述</th>
                        <th width="2%" rowspan="2">单位</th>
                        <th width="7%" colspan="5">各城市包干单价(元)</th>
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
                            url: "<c:url value="/admin/workMaterialPrice/cityPrice/listData"/>"
                        },
                        order: [[1, "asc"]],
                        columns:[
                            {"data":"mcode","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"name","render" : function(data, type, full, meta ){
                                return data==undefined?'':data;
                            }},
                            {"data":"specification","render" : function(data, type, full, meta ){
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
            table.setAjaxParam('mcode',$.trim($('input[name=mcode]').val()));
            table.setAjaxParam('name',$.trim($('input[name=name]').val()));
            table.setAjaxParam('specification',$.trim($('input[name=specification]').val()));
            table.setAjaxParam('start',0);
            table.getDataTable().ajax.reload();
        });

        $('button.js_reset').click(function () {
            table.clearAjaxParams();
            $('input[name=mcode]').val('');
            $('input[name=name]').val('');
            $('input[name=specification]').val('');
            table.setAjaxParam('start',0);
            table.getDataTable().ajax.reload();
        });


    });

</script>
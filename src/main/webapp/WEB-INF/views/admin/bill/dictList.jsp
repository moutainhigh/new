<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/tlds/lycore.tld" prefix="ly"%>

<!-- BEGIN EXAMPLE TABLE PORTLET-->
<div class="portlet light" id="form_wizard_1">
    <div class="portlet-title">
        <div class="caption">
        	<i class="icon-layers font-green"></i>
            <span class="caption-subject font-green sbold uppercase">发票台账</span>
       	</div>
        <div class="actions form-inline">
            <button class="btn btn-sm green"><i class="fa fa-search"></i> 搜索 </button>
			<button class="btn sbold blue js_insert_bill"><i class="fa fa-plus-circle"></i>新增</button>
        </div>
    </div>
    <div class="portlet-body">
        <ul class="nav nav-tabs" role="tablist">
            <li role="presentation" <c:if test="${entity.type eq 'area'}">class="active"</c:if>><a href="<c:url value="/admin/bill/dictList?type=area"/>">区域</a></li>
            <li role="presentation" <c:if test="${entity.type eq 'company'}">class="active"</c:if>><a href="<c:url value="/admin/bill/dictList?type=company"/>">公司</a></li>
            <li role="presentation" <c:if test="${entity.type eq 'project'}">class="active"</c:if>><a href="<c:url value="/admin/bill/dictList?type=project"/>">项目</a></li>
            <li role="presentation" <c:if test="${entity.type eq 'stage'}">class="active"</c:if>><a href="<c:url value="/admin/bill/dictList?type=stage"/>">分期</a></li>
        </ul>
        <c:if test="${entity.type eq 'area'}">
        <table class="table table-striped table-bordered table-advance table-hover">
            <thead>
                <tr>
                    <th >编号</th>
                    <th >区域名称</th>
                    <th >操作</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${page.content}" var="entity" varStatus="status">
                    <tr>
                        <td>${entity.code}</td>
                        <td>${entity.value}</td>
                        <td>
                            <button class="btn btn-xs green js_edit_dict" data-id="${entity.id}"><i class="fa fa-dot-circle-o"></i> 修改 </button>
                            <button class="btn btn-xs green js_del_dict" data-id="${entity.id}"><i class="fa fa-minus-circle"></i> 删除 </button>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        </c:if>
        <c:if test="${entity.type eq 'company'}">
            <table class="table table-striped table-bordered table-advance table-hover">
                <thead>
                <tr>
                    <th >编号</th>
                    <th >所属区域</th>
                    <th >公司名称</th>
                    <th >操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${page.content}" var="entity" varStatus="status">
                    <tr>
                        <td>${entity.code}</td>
                        <td>${entity.parentName}</td>
                        <td>${entity.value}</td>
                        <td>
                            <button class="btn btn-xs green js_edit_dict" data-id="${entity.id}"><i class="fa fa-dot-circle-o"></i> 修改 </button>
                            <button class="btn btn-xs green js_del_dict" data-id="${entity.id}"><i class="fa fa-minus-circle"></i> 删除 </button>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:if>
        <c:if test="${entity.type eq 'project'}">
            <table class="table table-striped table-bordered table-advance table-hover">
                <thead>
                <tr>
                    <th >编号</th>
                    <th >所属公司</th>
                    <th >项目名称</th>
                    <th >操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${page.content}" var="entity" varStatus="status">
                    <tr>
                        <td>${entity.code}</td>
                        <td>${entity.parentName}</td>
                        <td>${entity.value}</td>
                        <td>
                            <button class="btn btn-xs green js_edit_dict" data-id="${entity.id}"><i class="fa fa-dot-circle-o"></i> 修改 </button>
                            <button class="btn btn-xs green js_del_dict" data-id="${entity.id}"><i class="fa fa-minus-circle"></i> 删除 </button>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:if>
        <c:if test="${entity.type eq 'stage'}">
            <table class="table table-striped table-bordered table-advance table-hover">
                <thead>
                <tr>
                    <th >编号</th>
                    <th >项目名称</th>
                    <th >分期</th>
                    <th >操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${page.content}" var="entity" varStatus="status">
                    <tr>
                        <td>${entity.code}</td>
                        <td>${entity.value}</td>
                        <td>${entity.parentName}</td>
                        <td>
                            <button class="btn btn-xs green js_edit_dict" data-id="${entity.id}"><i class="fa fa-dot-circle-o"></i> 修改 </button>
                            <button class="btn btn-xs green js_del_dict" data-id="${entity.id}"><i class="fa fa-minus-circle"></i> 删除 </button>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:if>


        <div class="row">
	    	<div class="col-md-5 col-sm-5">
	    		<ly:pageSizeTag link="/admin/bill/dictList${ly:toString(entity,'dt,dtn')}" page="${page}"></ly:pageSizeTag>
	    	</div>
	    	<div class="col-md-7 col-sm-7 text-right">
                <ly:pageTag link="/admin/bill/dictList${ly:toString(entity,'type,dt,dtn')}&pageNo=" page="${page}"></ly:pageTag>
	    	</div>
    	</div>
    </div>
</div>
<!-- END EXAMPLE TABLE PORTLET-->

<div class="modal fade js_content_edit">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title">管理</h4>
            </div>
            <div class="modal-body">

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default js_cancel_btn" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary js_save_btn">保存</button>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">

    $(function () {
        var options = null;
        var _id = null;
        var _type = '${entity.type}';

        var base = {
            areaHtml : ['<p>编号:<input type="text" value="" name="code"></p>',
            '<p>区域名称:<input type="text" value="" name="value"></p>'].join(""),
            companyHtml : ['<p>编号:<input type="text" value="" name="code"></p>',
            '<p>所属区域:<select name="parentId"></select></p>',
            '<p>公司名称:<input type="text" value="" name="value"></p>'].join(""),
            projectHtml : ['<p>编号:<input type="text" value="" name="code"></p>',
            '<p>所属公司:<select name="parentId"></select></p>',
            '<p>项目名称:<input type="text" value="" name="value"></p>'].join(""),
            stageHtml : ['<p>编号:<input type="text" value="" name="code"></p>',
            '<p>所属项目:<select name="parentId"></select></p>',
            '<p>分期:<input type="text" value="" name="value"></p>'].join(""),
        }


        /**
         * 新增
         */
        $('button.js_insert_bill').on('click',function () {
            _id = null;
            var html = $(base[_type+"Html"]);
            buildSelect(html);
            $('div.js_content_edit .modal-body').html(html);
            $('div.js_content_edit').modal('show');
        });

        /**
         * 编辑
         */
        $('button.js_edit_dict').on('click',function () {
            _id = $(this).data('id');
            $.post('<c:url value="/admin/bill/getBillDicOne"/>',{id:_id},function (result) {
                if (result){
                    var html = $(base[_type+"Html"]);
                    buildSelect(html);
                    html.find('input[name=code]').val(result.code);
                    html.find('input[name=value]').val(result.value);
                    html.find('select[name=parentId]').find('option[value='+result.parentId+']').prop('selected',true);
                    $('div.js_content_edit .modal-body').html(html);
                    $('div.js_content_edit').modal('show');

                }else {
                   layer.alert("获取数据失败");
                }
            },'json');
        });

        /**
         * 删除
         */
        $('button.js_del_dict').on('click',function () {
            var _id = $(this).data('id');
            layer.confirm("确定要删除？",function () {
                $.post('<c:url value="/admin/bill/delBillDicOne"/>',{id:_id},function (result) {
                    if (result==0){
                        layer.alert("删除失败");
                    }else if(result==2){
                        layer.alert("存在子级数据不能删除");
                    }else {
                        window.location.reload();
                    }
                },'json');
            });
        });


        $('div.js_content_edit').on('click','button.js_cancel_btn',function () {
            $('div.js_content_edit').modal('hide');
        });

        $('div.js_content_edit').on('click','button.js_save_btn',function () {
            var args = buildData();
            if (null!=args){
                $.post('<c:url value="/admin/bill/saveBillDic"/>',args,function (result) {
                    if (result==0){
                        layer.alert("保存失败");
                    }else {
                        $('div.js_content_edit').modal('hide');
                        window.location.reload();
                    }
                },'json');
            }
        });

        var buildData = function () {
            var _code =  $('input[name=code]').val();
            var _parentId =  $('select[name=parentId]').val();
            var _value =  $('input[name=value]').val();
            if(_code&&_value){
                var obj = {};
                obj.id = _id;
                if (_parentId){
                    obj.parentId = _parentId;
                }else {
                    obj.parentId = 0;
                }
                obj.code = _code;
                obj.value = _value;
                obj.type = _type;
                return obj;
            }
            return null;
        }

        var buildSelect = function (htmlObj) {
            if (null==options) {
                var html = '<option>请选择</option>';
                $.ajax({
                    type: 'POST',
                    url: '<c:url value="/admin/bill/parentDictList"/>',
                    async: false,
                    data: {type: _type},
                    dataType: "json",
                    success: function (result) {
                        $(result).each(function (k, v) {
                            html += '<option value="' + v.id + '">' + v.value + '</option>';
                        });
                        options = html;
                    }
                });
            }
            htmlObj.find('select').html(options);

        }










    });
</script>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/tlds/lycore.tld" prefix="ly"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<style>
    .actions * {
        margin-top: 0px;
    }
    body {
        font-size: 10px;
    }
    label{
        font-size: small;
    }
</style>
<div class="page-bar">
    <ul class="page-breadcrumb">
        <li>
            <i class="fa fa-home"></i>
            <a href="<c:url value="/admin/index/default" />">首页</a>
            <i class="fa fa-angle-right"></i>
        </li>
        <li>
            <span>组织架构</span>
            <i class="fa fa-angle-right"></i>
        </li>
        <li>
            <span>组织架构添加</span>
        </li>
    </ul>
</div>
<!-- BEGIN EXAMPLE TABLE PORTLET-->
<div class="portlet light" id="form_wizard_1">
    <div class="portlet-title">
        <div class="caption">
        	<i class="icon-layers font-green"></i>
            <span class="caption-subject font-green sbold uppercase">组织架构添加</span>
       	</div>
        <div class="actions">
            <form >
                <label>类型：</label>
                <select id="id_type" class="form-control input-inline input-sm" style="width: 200px;" autocomplete="off">
                    <option value="1" >公司</option>
                    <option value="2" >部门</option>
                </select>
                <label>所属公司：</label>
                <select id="id_parentId" class="form-control input-inline input-sm" style="width: 200px;" autocomplete="off">
                    <option value="0" >选择所属公司</option>
                    <c:forEach items="${companyMapList}" var="company">
                        <option value="${company.id}" >${company.name}</option>
                    </c:forEach>
                </select>
                <label>架构ID：</label>
                <input id="id_id" class="form-control input-inline input-sm" type="text" value="" placeholder="架构ID"/>
                <label>架构名称：</label>
                <input id="id_name" class="form-control input-inline input-sm" type="text" value="" placeholder="架构名称"/>
            </form>
			<button class="btn btn-sm green" id="id_transmit"><i class="fa fa-search"></i> 添加 </button>
        </div>
    </div>
</div>
<!-- END EXAMPLE TABLE PORTLET-->
<script type="text/javascript">

    $('#id_transmit').on('click',function () {
        var type = $('#id_type').val();
        var parentId = $('#id_parentId').val();
        var id = $.trim($('#id_id').val());
        var name = $.trim($('#id_name').val());
        if (id==""||name=="") {
            layer.msg('架构ID和架构名称都不能为空');
            return;
        }
        if (type==2&&parentId==0) {
            layer.msg("类型为部门时，必须选择所属公司");
            return;
        }
        layer.confirm("确定保存吗？", {icon: 3, title:'保存提示'}, function(){
            layer.closeAll();
            $.ajax({
                url: '<c:url value="/market/budget/put"/>',
                type: 'POST',
                data:{type:type,parentId:parentId,id:id,name:name},
                dataType: 'json',
                success: function(data){
                    layer.closeAll('loading');
                    data = JSON.parse(data);
                    if (data.code=="0"){
                        layer.msg(data.msg,{icon: 1});
                        window.setTimeout(function () {
                            location.reload(true);
                        },1000);
                    } else {
                        layer.msg(data.msg,{icon: 2});
                    }
                },
                beforeSend:function () {layer.load(0,{shade: [0.3,'#000']});},
                error: function(){layer.closeAll('loading');}
            });
        });
    });

</script>
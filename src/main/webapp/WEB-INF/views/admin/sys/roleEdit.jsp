<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<link rel="stylesheet" type="text/css" href="<c:url value="/resources/admin/global/plugins/bootstrap-datepicker/css/bootstrap-datepicker3.min.css" />"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/hr/css/hr.css" />"/>


<div class="portlet box blue-hoki">
    <div class="portlet-title">
        <div class="caption">
            角色管理
        </div>
    </div>
    <div class="portlet-body">
        <div class="row">
            <div class="col-md-8 ">
                <form  role="form" class="js_base_form">
                    <div class="form-body">
                        <div class="row">
                            <div class="col-md-4 ">
                                <div class="form-group">
                                    <label>角色名称</label>
                                    <input type="text" class="form-control" name="name" value="${entity.name}">
                                </div>
                            </div>
                            <div class="col-md-4 ">
                                <div class="form-group">
                                    <label>所属系统</label>
                                    <input type="text" class="form-control" name="regSystem" value="${entity.regSystem}">
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-4 ">
                                <div class="form-group">
                                    <label>角色标识</label>
                                    <input type="text" class="form-control" name="mark" value="${entity.mark}">
                                </div>
                            </div>
                            <div class="col-md-4 ">
                                <div class="form-group">
                                    <label>状态</label>
                                    <div>
                                        <label><input type="radio" name="status" value="1" <c:if test="${empty entity || entity.status eq 1}">checked</c:if>> 正常</label>
                                        <label><input type="radio" name="status" value="0" <c:if test="${entity.status eq 0}">checked</c:if>> 禁用</label>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-4 ">
                                <div class="form-group">
                                    <label>备注</label>
                                    <input type="text" class="form-control" name="note" value="${entity.note}">
                                </div>
                            </div>
                            <div class="col-md-4 ">
                                <div class="form-group">
                                    <label>数据权限标识</label>
                                    <textarea class="form-control" name="dataAuthority">${entity.dataAuthority}</textarea>
                                </div>
                            </div>
                        </div>
                    </div>
                    <button type="button" class="btn green js_save" data-action="save">确定</button>
                </form>
            </div>
        </div>
    </div>
</div>


<script type="text/javascript" src="<c:url value="/resources/plugIn/zTree/js/jquery.ztree.exhide.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.zh-CN.js" />"></script>


<script>

    $(document).ready(function () {

        var checkField={
            name:"角色名称不能为空",
            regSystem:"所属系统不能为空",
            mark:"角色标识不能为空",
            status:"状态不能为空"
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
            obj.roleId = '${entity.roleId}';
            obj.name = $('input[name=name]').val();
            obj.regSystem = $('input[name=regSystem]').val();
            obj.mark = $('input[name=mark]').val();
            obj.status = $('input[name=status]:checked').val();
            obj.dataAuthority = $('textarea[name=dataAuthority]').val();
            obj.note = $('input[name=note]').val();
            if(checkObj(obj)){
                _index = layer.load(2);
                var url
                if (obj.roleId!=''){
                    url =  '<c:url value="/admin/sys/updateRole"/>';
                }else{
                    url ='<c:url value="/admin/sys/addRole"/>';
                }
                $.post(url, obj, function (result) {
                    if (result.success) {
                        layer.close(_index);
                        layer.msg('操作成功',{time:800},function () {
                            window.location.reload();
                        });
                    }else{
                        layer.close(_index);

                        layer.msg('操作失败：'+result.rmsg,{time:2000});
                    }
                }, 'json');
            }
        });

    });

</script>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link type="text/css" href="/resources/admin/global/plugins/select2-4.0.3/css/select2.min.css"  rel="stylesheet">

<div class="portlet box blue-hoki">
    <div class="portlet-title">
        <div class="caption">
            <i class="fa fa-gift"></i>
            仓库管理
        </div>
    </div>
    <div class="portlet-body">
        <div class="row">
            <div class="col-md-4 ">
                <form  role="form" class="js_base_form">
                    <div class="form-body">
                        <div class="row">
                            <div class="col-md-12 ">
                                <div class="form-group">
                                    <label>项目名称：</label>
                                    <select class="form-control" id="projectName" name="pname">
                                        <c:if test="${empty entity.id}">
                                            <option value="" selected="selected">请选择</option>
                                            <c:forEach items="${projects}" var="p">
                                                <c:if test="${currProjectId eq p.id}">
                                                <option value="${p.id}" data-code="${p.pcode}">${p.name}</option>
                                                </c:if>
                                            </c:forEach>
                                        </c:if>
                                        <c:if test="${not empty entity.id}">
                                            <c:forEach items="${projects}" var="p">
                                                <c:if test="${entity.projectId eq p.id}"><option value="${p.id}" selected="selected">${p.name}</option></c:if>
                                            </c:forEach>
                                        </c:if>
                                    </select>
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-12 ">
                                <div class="form-group">
                                    <label>仓库名称：</label>
                                    <input type="text" class="form-control" name="name" value="${entity.name}">
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-12 ">
                                <div class="form-group">
                                    <label>仓库地址：</label>
                                    <input type="text" class="form-control" name="address" value="${entity.address}">
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-12 ">
                                <div class="form-group">
                                    <label>备注：</label>
                                    <input type="text" class="form-control" name="remark" value="${entity.remark}">
                                </div>
                            </div>
                        </div>

                    <button type="button" class="btn green js_save_btn" data-action="save">保存</button>
                    <a type="button" class="btn default" href="<c:url value='/admin/inventory/storage/index'/>">返回列表</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>


<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/select2-4.0.3/js/select2.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/jquery-validation/js/jquery.validate.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/jquery-validation/js/additional-methods.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/jquery-validation/js/messages_zh.min.js" />"></script>


<script>

    $(function () {

        var dataObj={id:'${entity.id}',projectId:'${entity.projectId}'};
        if (dataObj.id==''){
            $('#projectName').select2({
                tags: true,
                createTag: function (params) {
                    var tmp = $.trim(params.term);
                    if (tmp === '') {
                        return null;
                    }
                    return {
                        id: "-1",
                        text: tmp
                    }
                }
            }).on('select2:select', function (evt) {
                if (evt.params.data.id == -1){
                    dataObj.needAdd = true;
                    dataObj.projectId=evt.params.data.id;
                    dataObj.projectName=evt.params.data.text;
                    dataObj.pcode= '';
                }else{
                    dataObj.needAdd = false;
                    dataObj.projectId=evt.params.data.id;
                    dataObj.projectName='';
                    dataObj.pcode=$(this).find("option:selected").data('code');
                }
            });
        }

        var validateForm = $('form.js_base_form');
        validateForm.validate({
            errorElement : 'span',
            errorClass : 'help-block help-block-error',
            rules : {
                pname : {
                    required : true
                },name : {
                    required : true
                },address : {
                    required : true
                }
            },
            highlight : function(element) {
                $(element).parents('div.form-group').addClass('has-error');
            },
            unhighlight : function(element) {
                $(element).parents('div.form-group').removeClass('has-error');
            },
            success : function(label) {
                label.remove();
            },errorPlacement: function(error, element) {
                element.parents('div.form-group').append(error);
            }
        });


        var _index=null;
        $('button.js_save_btn').on('click',function () {
            if(validateForm.valid()){
                var objArr = $('form.js_base_form').serializeArray();
                for(var e in objArr){
                    var  obj = objArr[e];
                    dataObj[obj.name] = obj.value;
                }
                var url = null;
                if(dataObj.id == ''){
                    url = '<c:url value="/admin/inventory/storage/insert"/>';
                }else{
                    url = '<c:url value="/admin/inventory/storage/update"/>';
                }
                layer.confirm("确定提交？",function (c) {
                    layer.close(c);
                    _index = layer.load(2);
                    $.post(url, dataObj, function (result) {
                        layer.close(_index);
                        if (result.success) {
                            layer.msg('操作成功',{time:800},function () {
                                window.location.href='<c:url value="/admin/inventory/storage/index"/>';
                            });
                        }else{
                            layer.msg('操作失败：'+result.rmsg,{time:2000});
                        }
                    }, 'json');
                });
            }
        });


    });

</script>


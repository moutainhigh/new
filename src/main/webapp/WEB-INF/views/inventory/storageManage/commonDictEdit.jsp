<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/6/29
  Time: 10:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<link type="text/css" href="/resources/admin/global/plugins/select2-4.0.3/css/select2.min.css"  rel="stylesheet">

<div class="portlet box blue-hoki">
    <div class="portlet-title">
        <div class="caption">单位维护</div>
    </div>
    <div class="portlet-body">
        <div class="row">
            <div class="col-md-6">
                <form  role="form" class="js_base_form" id="materialAddForm">
                    <div class="form-body">
                        <div class="row">
                            <div class="col-md-6 ">
                                <div class="form-group">
                                    <label>类型：</label>
                                    <%--<input type="text" class="form-control" name="dictGroup" value="${prItemDelivery.categoryName}" readonly>--%>
                                    <c:if test="${not empty commonDict}">
                                        <input type="text" class="form-control" name="dictGroup" value="${commonDict.dictGroup}" readonly>
                                        <input type="hidden" name="dictId" value="${commonDict.id}"/>
                                    </c:if>
                                    <c:if test="${empty commonDict}">
                                        <select class="form-control" name="dictGroup">
                                            <option value="unit">单位</option>
                                        </select>
                                    </c:if>
                                </div>
                            </div>
                            <div class="col-md-6 ">
                                <div class="form-group">
                                    <label>单位名称：</label>
                                    <%--<input type="text" class="form-control" name="dictValue" value="${prItemDelivery.categoryName}">--%>
                                    <c:if test="${not empty commonDict}">
                                        <input type="text" class="form-control" name="dictValue" value="${commonDict.dictValue}">
                                        <input type="hidden" class="form-control" name="dictKey" value="${commonDict.id}">
                                    </c:if>
                                    <c:if test="${empty commonDict}">
                                        <select class="form-control" name="dictValue">
                                            <c:forEach items="${list}" var="commonDict">
                                                <option value="${commonDict.dictValue}">${commonDict.dictValue}</option>
                                            </c:forEach>
                                        </select>
                                    </c:if>
                                </div>
                            </div>


                        </div>
                        <button type="button" class="btn green js_save_btn" data-action="save">保存</button>
                        <a type="button" class="btn default" href="<c:url value='/admin/inventory/dict/index'/>">返回列表</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/select2-4.0.3/js/select2.min.js" />"></script>

<script>
    $(function () {
        $('select[name=dictValue]').select2({
            tags: true,
            createTag: function (params) {
                var term = $.trim(params.term);
                if (term === '') {
                    return null;
                }
                return {
                    id: term,
                    text: term
                }
            }
        }).on('change',function () {
            var _value = $('select[name=dictValue]').val();
            $.post('/admin/inventory/dict/checkValue',{"dictValue":_value},function (data) {
                if (data.success == false) {
                    layer.alert(data.rmsg, {icon:0});
                    return;
                }

            });

        });

        $('button.js_save_btn').on('click', function () {
            layer.confirm("确定提交？",function () {
                var _value = $('select[name=dictValue]').val();
                var id = $('input[name=dictId]').val();
                if (id != null && id != "" && id != "undefined") {
                    _value = $('input[name=dictValue]').val();
                }
                $.post('/admin/inventory/dict/save',{"dictValue":_value,"id":id},function (data) {
                    if (data.success) {
                        layer.msg('操作成功',{time:800},function () {
                            window.location.href='<c:url value="/admin/inventory/dict/index"/>';
                        });
                    } else {
                        layer.msg('操作失败：'+data.rmsg,{time:2000});
                    }
                });
            });

        });
    });
</script>
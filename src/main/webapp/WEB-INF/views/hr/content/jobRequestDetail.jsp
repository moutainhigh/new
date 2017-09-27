<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

    <div class="portlet light">
        <div class="portlet-title">
            <div class="caption">
                <i class="icon-layers font-green"></i>
                <span class="caption-subject font-green sbold uppercase">求职者信息</span>
            </div>
        </div>

            <div class="portlet-body">
                <table class="table table-bordered table-striped">
                    <tr>
                        <th>应聘板块</th>
                        <th>区域</th>
                        <th>应聘职位</th>
                        <th>申请时间</th>
                        <th>姓名</th>
                        <th>性别</th>
                        <th>出生年月</th>
                        <th>附件</th>
                    </tr>
                    <tr>
                        <td>
                            <c:choose>
                                <c:when test="${entity.plateId eq 0}">集团总部</c:when>
                                <c:when test="${entity.plateId eq 1}">地产集团</c:when>
                                <c:when test="${entity.plateId eq 2}">商业集团</c:when>
                                <c:when test="${entity.plateId eq 3}">金控集团</c:when>
                                <c:when test="${entity.plateId eq 4}">建设集团</c:when>
                                <c:when test="${entity.plateId eq 5}">物业集团</c:when>
                                <c:when test="${entity.plateId eq 6}">海外公司</c:when>
                            </c:choose>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${entity.area eq 1}">重庆区域</c:when>
                                <c:when test="${entity.area eq 2}">四川区域</c:when>
                                <c:when test="${entity.area eq 3}">华东区域</c:when>
                                <c:when test="${entity.area eq 4}">上海区域</c:when>
                                <c:when test="${entity.area eq 5}">长沙城市公司</c:when>
                                <c:when test="${entity.area eq 6}">沈阳城市公司</c:when>
                            </c:choose>
                        </td>
                        <td>${entity.jobStr}</td>
                        <td><fmt:formatDate value="${entity.createtime}" pattern="yyyy-MM-dd hh:mm:ss"/></td>
                        <td> ${entity.name} </td>
                        <td> ${entity.sex eq 0?'女':'男'} </td>
                        <td> ${entity.birth}  </td>
                        <td><a href="http://hyhr.cqhyrc.com.cn/hr/hrContent/downloadFile?fileName=${entity.attachment}">点击下载</a></td>
                    </tr>
                    <tr>
                        <th >学校</th>
                        <th>学历</th>
                        <th>专业</th>
                        <th>最近一份工作时间</th>
                        <th>最近工作单位</th>
                        <th>状态</th>
                        <c:if test="${not empty entity.note}"><th>备注</th></c:if>
                    </tr>
                    <tr>
                        <td> ${entity.school}  </td>
                        <td> ${entity.edu}  </td>
                        <td> ${entity.profession}  </td>
                        <td> ${entity.recentJobTime} </td>
                        <td> ${entity.recentJob} </td>
                        <td>${entity.status eq 0?"未处理":"已处理"}</td>
                        <c:if test="${not empty entity.note}"><td>${entity.note}</td></c:if>
                    </tr>
                </table>
                <c:if test="${entity.status eq 0}"><button class="btn btn-sm green js_operate" type="button" data-action="status" data-id="${entity.id}">标记为已处理</button></c:if>
                <button class="btn btn-sm  js_operate" type="button" data-action="note" data-id="${entity.id}">设置备注</button>
                <a class="btn btn-sm grey-salsa" href="<c:url value="/hr/hrContent/jobRequestList" />">返回</a>
            </div>

    </div>

<script>
    $(document).ready(function () {
        $("button.js_operate").on('click',function () {
            var _action = $(this).data('action');
            var _id = $(this).data('id');
            if (_action=='status'){
                $.post('<c:url value='/hr/hrContent/updateJobRequest'/>', {id:_id,status:1}, function (result) {
                    if (result.success){
                        window.location.reload();
                    }else {
                        layer.msg(result.rmsg);
                    }
                },'json');
            }else if (_action=='note'){
                layer.prompt({title: '请填写备注，并确认', formType: 2,value:'${entity.note}'},function(text, index){
                    layer.close(index);
                    $.post('<c:url value='/hr/hrContent/updateJobRequest'/>', {id:_id,note:text}, function (result) {
                        if (result.success){
                            window.location.reload();
                        }else {
                            layer.msg(result.rmsg);
                        }
                    },'json');
                });
            }
        });



    });

</script>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>



<style>
    *{
        margin:0px;
        padding:0px;
        box-sizing: border-box;
    }
    .apply_print h3{
        text-align:center;
    }
    .apply_print p{
        text-align:right;
    }
    table{
        border-collapse: collapse;
    }
    .table_hy{
        font-size:13px;
        border: 1px solid #ddd;
    }
    .table_hy td{
        padding:0 2px;
        height:30px;
        text-align:center;
        border: 1px solid #ddd;
    }
    .table_hy td span{
        display:inline-block;
        margin-right:10px;
    }
    .table_hy td.text_left{
        text-align:left;
    }
    <%-- .w_80{
        width:80px
    }
    .w_110{
        width:120px
    }
    --%>
    @media print{
        .table_hy{
            border: 1px solid #000;
        }
        .table_hy td{
            border: 1px solid #000;
        }
    }
</style>

<div class="apply_print">
    <h3>人员基本信息</h3>
    <p>员工编号：${empInfo.empNo}</p>
    <table class="table_hy" width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td class="w_80">姓名</td>
            <td style="width:50px;">${empInfo.empName}</td>
            <td class="w_80">性别</td>
            <td style="width:50px;">${empInfo.sex==0?"女":empInfo.sex==1?"男":""}</td>
            <td class="w_80">民族</td>
            <td>${nation}</td>
            <td class="w_80">出生日期</td>
            <td colspan="3"><fmt:formatDate value="${empInfo.birthdate}" pattern="yyyy-MM-dd"/></td>
            <td class="w_110" rowspan="5"><img src="${photoServerUrl}${empInfo.photoUrl}" width="116" height="161" ></td>
        </tr>
        <tr>
            <td>学历</td>
            <td>${education}</td>
            <td>专业</td>
            <td colspan="2">${profession}</td>
            <td class="w_80">毕业院校</td>
            <td colspan="4">${school}</td>
        </tr>
        <tr>
            <td colspan="2">职称</td>
            <td>${jobTitle}</td>
            <td>政治面貌</td>
            <td>${politicalStatus}</td>
            <td>婚姻状况</td>
            <td>${maritalStatus}</td>
            <td>健康现状</td>
            <td  colspan="2">${empInfo.healthStatus}</td>
            <%--<td>职业资格</td>--%>
            <%--<td colspan="3">&nbsp;</td>--%>
            <%--<td>执业资格</td>--%>
            <%--<td colspan="3">&nbsp;</td>--%>
        </tr>
        <tr>
            <%--<td>政治面貌</td>--%>
            <%--<td>${politicalStatus}</td>--%>
            <%--<td>婚姻状况</td>--%>
            <%--<td>${maritalStatus}</td>--%>
            <%--<td>健康现状</td>--%>
            <%--<td>${healthStatus}</td>--%>
            <%--<td>身高</td>--%>
            <%--<td>&nbsp;</td>--%>
            <%--<td class="w_80">体重</td>--%>
            <%--<td style="width:50px;">&nbsp;</td>--%>
        </tr>
        <tr>
            <td>身份证号</td>
            <td colspan="3">${empInfo.idCard}</td>
            <td>手机号码</td>
            <td colspan="2">${empInfo.mobile}</td>
            <td class="w_80">家庭号码</td>
            <td colspan="2">${empInfo.homePhone}</td>
        </tr>
        <tr>
            <td>户籍地址</td>
            <td colspan="4">${empInfo.registrationAddress}</td>
            <td>户口性质</td>
            <td>${householdType}</td>
            <td>电子邮箱</td>
            <td colspan="3">${empInfo.email}</td>
        </tr>
        <tr>
            <td>家庭住址</td>
            <td colspan="4">${empInfo.liveAddress}</td>
            <%--<td>&nbsp;</td>--%>
            <%--<td>房屋性质</td>--%>
            <%--<td>&nbsp;</td>--%>
            <td>QQ号码</td>
            <td colspan="5">&nbsp;</td>
        </tr>
        <tr>
            <td rowspan="${fn:length(relationList)+1}">家庭<br>主要<br>成员</td>
            <td colspan="2">关系</td>
            <td colspan="2">姓名</td>
            <td colspan="2">职务</td>
            <td colspan="2">年龄</td>
            <td colspan="2">联系电话</td>
        </tr>
        <c:forEach items="${relationList}" var="r">
            <tr>
                <td colspan="2">${r.relationship}</td>
                <td colspan="2">${r.name}</td>
                <td colspan="2">${r.duty}</td>
                <td colspan="2">${r.job}</td>
                <td colspan="2">${r.phone}</td>
            </tr>
        </c:forEach>
        <tr>
            <td rowspan="${fn:length(resumeInfoList)+1}">主要<br>工作<br>经历<br>(倒序)</td>
            <td colspan="2">起止时间</td>
            <td>岗位</td>
            <td colspan="2">单位名称</td>
            <td colspan="2">离职原因</td>
            <td>联系人</td>
            <td colspan="2">联系电话</td>
        </tr>
        <c:forEach items="${resumeInfoList}" var="r">
        <tr>
            <td colspan="2"><fmt:formatDate value="${r.startTime}" pattern="yyyy-MM-dd"/> 至 <c:if test="${empty r.endTime}">今</c:if> <fmt:formatDate value="${r.endTime}" pattern="yyyy-MM-dd"/></td>
            <td>${r.duty}</td>
            <td colspan="2">${r.company}</td>
            <td colspan="2">${r.leaveReason}</td>
            <td>${r.witness}</td>
            <td colspan="2">${r.witnessPhone}</td>
        </tr>
        </c:forEach>
        <tr>
            <td rowspan="${fn:length(eduList)+1}">教育<br>经历<br>(倒序)</td>
            <td colspan="2">起止时间</td>
            <td colspan="3">就读学校</td>
            <td colspan="2">专业</td>
            <td>学历</td>
            <td colspan="2">学位</td>
        </tr>
        <c:forEach var="e" items="${eduList}">
        <tr>
            <td colspan="2"><fmt:formatDate value="${e.admissionDate}" pattern="yyyy-MM-dd"/> 至 <c:if test="${empty e.graduationDate}">今</c:if> <fmt:formatDate value="${e.graduationDate}" pattern="yyyy-MM-dd"/></td>
            <td colspan="3">${e.school}</td>
            <td colspan="2">${e.professionStr}</td>
            <td>${e.educationStr}</td>
            <td colspan="2">${e.degreeStr}</td>
        </tr>
        </c:forEach>
        <tr>
            <td rowspan="4">主要<br>培训<br>经历<br>(倒序)</td>
            <td colspan="2">起止时间</td>
            <td colspan="3">培训名称/主要内容/项目</td>
            <td colspan="3">培训机构</td>
            <td colspan="2">有无培训认证</td>
        </tr>
        <c:forEach items="${trains}" var="t">
            <tr>
                <td colspan="2"><fmt:formatDate value="${t.startTime}" pattern="yyyy-MM-dd"/> 至 <fmt:formatDate value="${t.endTime}" pattern="yyyy-MM-dd"/></td>
                <td colspan="3">${t.name}/${t.content}</td>
                <td colspan="3">&nbsp;</td>
                <td colspan="2">&nbsp;</td>
            </tr>
        </c:forEach>
    </table>
</div>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>

<body>
<style>
    @media print{
        div.receipt-box {page-break-after:always;}
        @page {
            margin: 2%;
        }

        body{
            width: 100%;
            margin: 0;
            padding: 0;
        }
    }

    *{
        margin:0;
        padding:0;
        box-sizing:border-box;
    }

    body{
        font-family:'黑体';
        color: #000000;
        line-height:20px;
        font-size:12px;
    }

    li{
        list-style:none;
    }
    .text-center{text-align:center;}
    .text-right{text-align:right;}
    .receipt-box{
        margin:0 auto;
        font-size:12px;
    }
    .receipt-box h1{
        font-size:16px;
        text-align:center;
        letter-spacing:15px;
    }
    .receipt-box .title li{
        float: left;
        width:33.33%;
    }
    .receipt-box .sign li {
        width:50%;
    }
    .receipt-box .sign{
        margin-top: 20px;
    }

    .receipt-nav li{
        float:left;
        width:33.33%;
        line-height:16px;
        word-break:break-all;
        word-wrap:break-word;
    }
    .receipt-box table{
        border-bottom: #000 1px solid;
        border-left: #000 1px solid;
    }
    .receipt-box table th , .receipt-box table td{
        padding:0px 2px;
        font-size: 12px;
        border-top: #000 1px solid;
        border-right: #000 1px solid;
    }
    .receipt-sign li{
        float:left;
        width:16.66%;
    }
    .adopt-sign li{
        width:20%;
    }
</style>


<c:set var="pageIndex" value="1"/>
<c:forEach items="${listData}" var="o" varStatus="step">
    <c:if test="${step.count%65 eq 1}">
        <div class="receipt-box">
        <h1>材料入库、出库单据移交签收单</h1>
        <ul class="title">
            <li>库房名称：${repositoryName}</li>
            <li class="text-center">收票日期：<fmt:formatDate value="${billReceiveDate}" pattern="yyyy-MM-dd"/></li>
            <li class="text-center">${pageIndex}/${pageCount}</li>
        </ul>
        <table  class="outStorageList" width="100%" border="0" cellspacing="1" cellpadding="0">
        <tr>
            <td class="text-center">序号</td>
            <td class="text-center">出库单号</td>
            <td class="text-center">出库金额</td>
            <td class="text-center">收票人</td>
            <td class="text-center">入库单号</td>
            <td class="text-center">入库金额</td>
            <td class="text-center">收票人</td>
        </tr>
        <c:set var="pageIndex" value="${pageIndex+1}"/>
    </c:if>
    <tr>
        <td class="text-center">${step.count}</td>
        <td class="text-center">${o.outBillListData.outStorageNo}</td>
        <c:if test="${not empty o.outBillListData.outStorageNo}"><c:set var="sum1" value="${sum1+1}"/></c:if>
        <td class="text-center">${o.outBillListData.outStorageSumPrice}<c:set var="sum2" value="${sum2+o.outBillListData.outStorageSumPrice}"/></td>
        <td class="text-center">${o.outBillListData.billReceiver}</td>
        <td class="text-center">${o.inBillListData.inStorageNum}</td>
        <c:if test="${not empty o.inBillListData.inStorageNum}"><c:set var="sum3" value="${sum3+1}"/></c:if>
        <td class="text-center">${o.inBillListData.inStorageSumPrice}<c:set var="sum4" value="${sum4+o.inBillListData.inStorageSumPrice}"/></td>
        <td class="text-center">${o.inBillListData.billReceiver}</td>
    </tr>
    <c:choose>
        <c:when test="${step.count%65 eq 0}">
            <c:if test="${step.count eq fn:length(listData)}">
                <tr style="border: 0px">
                    <td></td>
                    <td class="text-center">合计：${sum1} 份</td>
                    <td class="text-center">${sum2}</td>
                    <td></td>
                    <td class="text-center">合计：${sum3} 份</td>
                    <td class="text-center">${sum4}</td>
                    <td></td>
                </tr>
            </c:if>
            </table>
            <ul class="title sign">
                <li>库管（交票）：</li>
                <li>财务（收票）：</li>
            </ul>
            </div>
        </c:when>
        <c:otherwise>
            <c:if  test="${step.count eq fn:length(listData)}">
                <tr style="border: 0px">
                    <td></td>
                    <td class="text-center">合计：${sum1} 份</td>
                    <td class="text-center">${sum2}</td>
                    <td></td>
                    <td class="text-center">合计：${sum3} 份</td>
                    <td class="text-center">${sum4}</td>
                    <td></td>
                </tr>
                </table>
                <ul class="title sign">
                    <li>库管（交票）：</li>
                    <li>财务（收票）：</li>
                </ul>
                </div>
            </c:if>
        </c:otherwise>
    </c:choose>
</c:forEach>

</body>
</html>
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
            margin: 2.5%;
            margin-top: 5%;
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
        color:#000000;
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
        width:16%;
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

<!--材料入库验收单-->

<c:set var="pageIndex" value="1"/>
<c:forEach items="${inStoragelist}" var="p" varStatus="step">
    <c:if test="${step.count%5 eq 1}">
    <div class="receipt-box">
    <h1>材料入库验收单</h1>
    <ul class="title">
        <li>供货单位：${inStorage.itemProviderName}</li>
        <li>库房名称：${inStorage.repositoryName}</li>
        <li>入库时间：<fmt:formatDate value="${inStorage.inStoreDate}" pattern="yyyy-MM-dd"/></li>
        <li>发票编号：${inStorage.billNum}</li>
        <li>入库单号：${inStorage.inStorageNum}&nbsp;(${pageIndex}/${pageCount})</li>
    </ul>


    <table width="100%" border="0" cellspacing="1" cellpadding="0">
        <tr>
            <th class="text-center">价、量审批来源</th>
            <th class="text-center">分类</th>
            <th class="text-center">入库材料名称\规格</th>
            <th class="text-center">计量单位</th>
            <th class="text-center">数量</th>
            <th class="text-center">单价</th>
            <th class="text-center">税额</th>
            <th class="text-center">不含税金额</th>
            <th class="text-center">含税金额</th>
        </tr>
        <c:set var="pageIndex" value="${pageIndex+1}"/>
    </c:if>
        <tr>
            <td>${p.priceSrc}</td>
            <td class="">${p.materialCategory}</td>
            <td>${p.materialName}\ ${p.specification}</td>
            <td class="text-center">${p.unit}</td>
            <td class="text-center">${p.num}</td>
            <td class="text-right">${p.price}</td>
            <td class="text-right">${p.rateMoney}</td>
            <c:set value="${sumPrice1+p.rateMoney}" var="sumPrice1"/>
            <td class="text-right">${p.moneyNoRate}</td>
            <c:set value="${sumPrice2+p.moneyNoRate}" var="sumPrice2"/>
            <td class="text-right">${p.inStorageMoney}</td>
            <c:set value="${sumPrice3 + p.inStorageMoney}" var="sumPrice3"/>
        </tr>

    <c:choose>
    <c:when test="${step.count%5 eq 0}">
        <c:if test="${step.count eq fn:length(inStoragelist)}">
            <tr>
                <td colspan="6"class="text-right">合计：</td>
                <td class="text-right">${sumPrice1}</td>
                <td class="text-right">${sumPrice2}</td>
                <td class="text-right">${sumPrice3}</td>
            </tr>
        </c:if>
        </table>
        <ul class="title sign">
            <li>主管：</li>
            <li>审核：</li>
            <li>监收：</li>
            <li>验收：</li>
            <li>采购：</li>
            <li>制单：</li>
        </ul>
        </div>

    </c:when>
    <c:otherwise>
        <c:if  test="${step.count eq fn:length(inStoragelist)}">
        <tr>
            <td colspan="6"class="text-right">合计：</td>
            <td class="text-right">${sumPrice1}</td>
            <td class="text-right">${sumPrice2}</td>
            <td class="text-right">${sumPrice3}</td>
        </tr>
        </table>
        <ul class="title sign">
            <li>主管：</li>
            <li>审核：</li>
            <li>监收：</li>
            <li>验收：</li>
            <li>采购：</li>
            <li>制单：</li>
        </ul>
        </div>
        </c:if>
    </c:otherwise>
    </c:choose>
</c:forEach>

</body>
</html>


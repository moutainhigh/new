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
        width:25%;
    }
    .receipt-box .sign li {
        width:20%;
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

<!--材料领用单-->
<c:set var="pageIndex" value="1"/>
<c:forEach items="${outStoragelist}" var="o" varStatus="step">
    <c:if test="${step.count%5 eq 1}">
        <div class="receipt-box">
            <h1>材料领用单</h1>
            <ul class="title">
                <li style="width: 100%;">库名：${outStorage.repositoryName}</li>
                <li>领用单位：${outStorage.itemReceiverUnit}</li>
                <li>领用人：${outStorage.itemReceiver}</li>
                <li class="text-center">领用时间：<fmt:formatDate value="${outStorage.outStorageDate}" pattern="yyyy-MM-dd"/></li>
                <li class="text-right">出库单号：${outStorage.outStorageNo}&nbsp;(${pageIndex}/${pageCount})</li>
            </ul>
            <table  class="outStorageList" width="100%" border="0" cellspacing="1" cellpadding="0">
                <tr>
                    <td>分类</td>
                    <td width="18%">材料名称\规格</td>
                    <td width="8%">单位</td>
                    <td>用途</td>
                    <td>数量</td>
                    <td>单价</td>
                    <td>税额</td>
                    <td>不含税金额</td>
                    <td>含税金额</td>
                </tr>
            <c:set var="pageIndex" value="${pageIndex+1}"/>
        </c:if>
            <tr>
                <td class="text-center">${o.categoryName}</td>
                <td class="text-center">${o.materialName} ${o.specification}</td>
                <td>${o.unit}</td>
                <td class="text-center">${o.use}</td>
                <td class="text-center">${o.num}</td>
                <td class="text-right">${o.price}</td>
                <td class="text-right">${o.sumPrice-o.excTaxSumPrice}</td>
                <c:set var="sum1" value="${sum1 +o.sumPrice-o.excTaxSumPrice}"/>
                <td class="text-right">${o.excTaxSumPrice}</td>
                <c:set var="sum2" value="${sum2 +o.excTaxSumPrice}"/>
                <td class="text-right">${o.sumPrice}</td>
                <c:set var="sum3" value="${sum3 +o.sumPrice}"/>
            </tr>
        <c:choose>
            <c:when test="${step.count%5 eq 0}">
                <c:if test="${step.count eq fn:length(outStoragelist)}">
                <tr style="border: 0px">
                    <td colspan="6" class="text-right">合计：</td>
                    <td class="text-right">${sum1}</td>
                    <td class="text-right">${sum2}</td>
                    <td class="text-right">${sum3}</td>
                </tr>
                </c:if>
                </table>
                <ul class="title sign">
                    <li>单位主管：</li>
                    <li>部门：</li>
                    <li>审核：</li>
                    <li>发料：</li>
                    <li>领料人：</li>
                </ul>
                </div>
            </c:when>
            <c:otherwise>
            <c:if  test="${step.count eq fn:length(outStoragelist)}">
                <tr style="border: 0px">
                    <td colspan="6" class="text-right">合计：</td>
                    <td class="text-right">${sum1}</td>
                    <td class="text-right">${sum2}</td>
                    <td class="text-right">${sum3}</td>
                </tr>
                </table>
                <ul class="title sign">
                    <li>单位主管：</li>
                    <li>部门：</li>
                    <li>审核：</li>
                    <li>发料：</li>
                    <li>领料人：</li>
                </ul>
                </div>
            </c:if>
        </c:otherwise>
    </c:choose>
</c:forEach>

</body>
</html>
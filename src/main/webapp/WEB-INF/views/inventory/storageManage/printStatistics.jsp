<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
<head>
    <link href="<c:url value="/resources/inventory/main.css" />" rel="stylesheet" type="text/css" />
    <link href="<c:url value="/resources/admin/global/plugins/bootstrap/css/bootstrap.min.css" />" rel="stylesheet" type="text/css" />
    <link href="<c:url value="/resources/admin/global/css/components.min.css" />" rel="stylesheet" type="text/css" />
    <style>
        @media print{
            div.portlet-body {page-break-after:always;}
            @page {
                margin: 2.5%;
                margin-top: 3%;
            }

            body{
                width: 100%;
                margin: 0;
                padding: 0;
            }
        }

        body{
            color:#000000;
            line-height:15px;
            font-size:12px;
        }
        .table  td, .table  th{
            font-size: 12px;
            /*padding: 3px;*/
        }
        .table>tbody>tr>td, .table>tbody>tr>th, .table>tfoot>tr>td, .table>tfoot>tr>th, .table>thead>tr>td, .table>thead>tr>th{
            padding:5px 2px 1px 2px;
        }

        .portlet-body  .title li{
            float: left;
            width:25%;
            list-style: none;
        }

        /*.portlet-body .sign li {*/
            /*width:16%;*/
        /*}*/

        .portlet-body .sign{
            margin-top: 20px;
        }

    </style>
</head>
<body>
<c:set var="pageIndex" value="1"/>
<c:set var="index" value="0"/>
<c:forEach items="${statisticsData}" var="data" varStatus="step">
    <c:if test="${index%20 eq 0}">
    <div class="portlet-body">
        <h1 class="text-center print-title">物资盘点表</h1>
        <ul class="print-tab">
            <li class="text-left">库房名称：<strong>${entity.repositoryName}</strong></li>
            <li class="text-center"><fmt:formatDate value="${entity.startDate}" pattern="yyyy-MM-dd"/>—<fmt:formatDate value="${entity.endDate}" pattern="yyyy-MM-dd"/></li>
            <li class="text-right">第${pageIndex}页，共<span name="pageCount"></span>页</li>
            <c:set var="pageIndex" value="${pageIndex+1}"/>
        </ul>
        <div class="table-scrollable">
            <table class="table table-bordered table-hover">
                <tr>
                    <th class="text-center" style="vertical-align: middle" rowspan="2">名称</th>
                    <th class="text-center" style="vertical-align: middle" rowspan="2">规格</th>
                    <th class="text-center" rowspan="2">计<br>量</th>
                    <th class="text-center" colspan="5">期初</th>
                    <th class="text-center" colspan="5">本期增加</th>
                    <th class="text-center" colspan="5">本期减少</th>
                    <th class="text-center" colspan="5">期末结存</th>
                </tr>
                <tr>
                    <th class="text-center">单价</th>
                    <th class="text-center">数量</th>
                    <th class="text-center">税额</th>
                    <th class="text-center">不含税金额</th>
                    <th class="text-center">总金额</th>
                    <th class="text-center">单价</th>
                    <th class="text-center">数量</th>
                    <th class="text-center">税额</th>
                    <th class="text-center">不含税金额</th>
                    <th class="text-center">总金额</th>
                    <th class="text-center">单价</th>
                    <th class="text-center">数量</th>
                    <th class="text-center">税额</th>
                    <th class="text-center">不含税金额</th>
                    <th class="text-center">总金额</th>
                    <th class="text-center">单价</th>
                    <th class="text-center">数量</th>
                    <th class="text-center">税额</th>
                    <th class="text-center">不含税金额</th>
                    <th class="text-center">总金额</th>
                </tr>
        </c:if>

    <c:if test="${data.categoryId ne catgoryId}">
        <c:if test="${not empty catgoryId}">
            <tr>
                <td class="text-center" colspan="3">${lastCatgoryName}类小计</td>
                <td class="text-right">&nbsp;</td>
                <td class="text-right">&nbsp;</td>
                <td class="text-right">${startSumTax}</td>
                <td class="text-right">${startSumExcTaxPrice}</td>
                <td class="text-right">${startSumPrice}</td>
                <td class="text-right">&nbsp;</td>
                <td class="text-right">&nbsp;</td>
                <td class="text-right">${addSumTax}</td>
                <td class="text-right">${addSumExcTaxPrice}</td>
                <td class="text-right">${addSumPrice}</td>
                <td class="text-right">&nbsp;</td>
                <td class="text-right">&nbsp;</td>
                <td class="text-right">${reduceSumTax}</td>
                <td class="text-right">${reduceSumExcTaxPrice}</td>
                <td class="text-right">${reduceSumPrice}</td>
                <td class="text-right">&nbsp;</td>
                <td class="text-right">&nbsp;</td>
                <td class="text-right">${endSumTax}</td>
                <td class="text-right">${endSumExcTaxPrice}</td>
                <td class="text-right">${endSumPrice}</td>
            </tr>
            <c:set value="${startSumTax + all_startSumTax}" var="all_startSumTax"/>
            <c:set value="${startSumExcTaxPrice + all_startSumExcTaxPrice}" var="all_startSumExcTaxPrice"/>
            <c:set value="${startSumPrice + all_startSumPrice}" var="all_startSumPrice"/>
            <c:set value="${addSumTax + all_addSumTax}" var="all_addSumTax"/>
            <c:set value="${addSumExcTaxPrice + all_addSumExcTaxPrice}" var="all_addSumExcTaxPrice"/>
            <c:set value="${addSumPrice + all_addSumPrice}" var="all_addSumPrice"/>
            <c:set value="${reduceSumTax + all_reduceSumTax}" var="all_reduceSumTax"/>
            <c:set value="${reduceSumExcTaxPrice+  all_reduceSumExcTaxPrice}" var="all_reduceSumExcTaxPrice"/>
            <c:set value="${reduceSumPrice + all_reduceSumPrice}" var="all_reduceSumPrice"/>
            <c:set value="${endSumTax + all_endSumTax}" var="all_endSumTax"/>
            <c:set value="${endSumExcTaxPrice + all_endSumExcTaxPrice}" var="all_endSumExcTaxPrice"/>
            <c:set value="${endSumPrice + all_endSumPrice}" var="all_endSumPrice"/>

            <c:set value="0" var="startSumTax"/>
            <c:set value="0" var="startSumExcTaxPrice"/>
            <c:set value="0" var="startSumPrice"/>
            <c:set value="0" var="addSumTax"/>
            <c:set value="0" var="addSumExcTaxPrice"/>
            <c:set value="0" var="addSumPrice"/>
            <c:set value="0" var="reduceSumTax"/>
            <c:set value="0" var="reduceSumExcTaxPrice"/>
            <c:set value="0" var="reduceSumPrice"/>
            <c:set value="0" var="endSumTax"/>
            <c:set value="0" var="endSumExcTaxPrice"/>
            <c:set value="0" var="endSumPrice"/>
            <c:set var="index" value="${index+1}"/>
            <c:if test="${index%20 eq 0}">
                </table>
                </div>
                <ul class="title sign">
                    <li>审核：</li>
                    <li>财务：</li>
                    <li>部门负责人：</li>
                    <li>制表：</li>
                </ul>
                </div>
                <div class="portlet-body">
                <h1 class="text-center print-title">物资盘点表</h1>
                <ul class="print-tab">
                    <li class="text-left">库房名称：<strong>${entity.repositoryName}</strong></li>
                    <li class="text-center"><fmt:formatDate value="${entity.startDate}" pattern="yyyy-MM-dd"/>—<fmt:formatDate value="${entity.endDate}" pattern="yyyy-MM-dd"/></li>
                        <li class="text-right">第${pageIndex}页，共<span name="pageCount"></span>页</li>
                    <c:set var="pageIndex" value="${pageIndex+1}"/>
                </ul>
                <div class="table-scrollable">
                <table class="table table-bordered table-hover">
                <tr>
                    <th class="text-center" style="vertical-align: middle" rowspan="2">名称</th>
                    <th class="text-center" style="vertical-align: middle" rowspan="2">规格</th>
                    <th class="text-center" rowspan="2">计<br>量</th>
                    <th class="text-center" colspan="5">期初</th>
                    <th class="text-center" colspan="5">本期增加</th>
                    <th class="text-center" colspan="5">本期减少</th>
                    <th class="text-center" colspan="5">期末结存</th>
                </tr>
                <tr>
                    <th class="text-center">单价</th>
                    <th class="text-center">数量</th>
                    <th class="text-center">税额</th>
                    <th class="text-center">不含税金额</th>
                    <th class="text-center">总金额</th>
                    <th class="text-center">单价</th>
                    <th class="text-center">数量</th>
                    <th class="text-center">税额</th>
                    <th class="text-center">不含税金额</th>
                    <th class="text-center">总金额</th>
                    <th class="text-center">单价</th>
                    <th class="text-center">数量</th>
                    <th class="text-center">税额</th>
                    <th class="text-center">不含税金额</th>
                    <th class="text-center">总金额</th>
                    <th class="text-center">单价</th>
                    <th class="text-center">数量</th>
                    <th class="text-center">税额</th>
                    <th class="text-center">不含税金额</th>
                    <th class="text-center">总金额</th>
                </tr>
            </c:if>
        </c:if>
        <tr>
            <td colspan="23"><strong>分类：${data.categoryName}</strong></td>
        </tr>
        <c:set var="index" value="${index+1}"/>
        <c:if test="${index%20 eq 0}">
            </table>
            </div>
            <ul class="title sign">
                <li>审核：</li>
                <li>财务：</li>
                <li>部门负责人：</li>
                <li>制表：</li>
            </ul>
            </div>
            <div class="portlet-body">
            <h1 class="text-center print-title">物资盘点表</h1>
            <ul class="print-tab">
                <li class="text-left">库房名称：<strong>${entity.repositoryName}</strong></li>
                <li class="text-center"><fmt:formatDate value="${entity.startDate}" pattern="yyyy-MM-dd"/>—<fmt:formatDate value="${entity.endDate}" pattern="yyyy-MM-dd"/></li>
                    <li class="text-right">第${pageIndex}页，共<span name="pageCount"></span>页</li>
                <c:set var="pageIndex" value="${pageIndex+1}"/>
            </ul>
            <div class="table-scrollable">
            <table class="table table-bordered table-hover">
            <tr>
                <th class="text-center" style="vertical-align: middle" rowspan="2">名称</th>
                <th class="text-center" style="vertical-align: middle" rowspan="2">规格</th>
                <th class="text-center" rowspan="2">计<br>量</th>
                <th class="text-center" colspan="5">期初</th>
                <th class="text-center" colspan="5">本期增加</th>
                <th class="text-center" colspan="5">本期减少</th>
                <th class="text-center" colspan="5">期末结存</th>
            </tr>
            <tr>
                <th class="text-center">单价</th>
                <th class="text-center">数量</th>
                <th class="text-center">税额</th>
                <th class="text-center">不含税金额</th>
                <th class="text-center">总金额</th>
                <th class="text-center">单价</th>
                <th class="text-center">数量</th>
                <th class="text-center">税额</th>
                <th class="text-center">不含税金额</th>
                <th class="text-center">总金额</th>
                <th class="text-center">单价</th>
                <th class="text-center">数量</th>
                <th class="text-center">税额</th>
                <th class="text-center">不含税金额</th>
                <th class="text-center">总金额</th>
                <th class="text-center">单价</th>
                <th class="text-center">数量</th>
                <th class="text-center">税额</th>
                <th class="text-center">不含税金额</th>
                <th class="text-center">总金额</th>
            </tr>
        </c:if>
    </c:if>

    <tr>
            <td class="text-center">${data.materialName}</td>
            <td class="text-center">${data.specificationName}</td>
            <td class="text-center">${data.unit}</td>
            <td class="text-right">${data.startPrice}</td>
            <td class="text-right">${data.startNum}</td>
            <td class="text-right">${data.startSumTax}</td>
            <c:set value="${startSumTax + data.startSumTax}" var="startSumTax"/>
            <td class="text-right">${data.startSumExcTaxPrice}</td>
            <c:set value="${startSumExcTaxPrice + data.startSumExcTaxPrice}" var="startSumExcTaxPrice"/>
            <td class="text-right">${data.startSumPrice}</td>
            <c:set value="${startSumPrice + data.startSumPrice}" var="startSumPrice"/>
            <td class="text-right">${data.addPrice}</td>
            <td class="text-right">${data.addNum}</td>
            <td class="text-right">${data.addSumTax}</td>
            <c:set value="${addSumTax + data.addSumTax}" var="addSumTax"/>
            <td class="text-right">${data.addSumExcTaxPrice}</td>
            <c:set value="${addSumExcTaxPrice + data.addSumExcTaxPrice}" var="addSumExcTaxPrice"/>
            <td class="text-right">${data.addSumPrice}</td>
            <c:set value="${addSumPrice + data.addSumPrice}" var="addSumPrice"/>
            <td class="text-right">${data.reducePrice}</td>
            <td class="text-right">${data.reduceNum}</td>
            <td class="text-right">${data.reduceSumTax}</td>
            <c:set value="${reduceSumTax + data.reduceSumTax}" var="reduceSumTax"/>
            <td class="text-right">${data.reduceSumExcTaxPrice}</td>
            <c:set value="${reduceSumExcTaxPrice + data.reduceSumExcTaxPrice}" var="reduceSumExcTaxPrice"/>
            <td class="text-right">${data.reduceSumPrice}</td>
            <c:set value="${reduceSumPrice + data.reduceSumPrice}" var="reduceSumPrice"/>
            <td class="text-right">${data.endPrice}</td>
            <td class="text-right">${data.endNum}</td>
            <td class="text-right">${data.endSumTax}</td>
            <c:set value="${endSumTax + data.endSumTax}" var="endSumTax"/>
            <td class="text-right">${data.endSumExcTaxPrice}</td>
            <c:set value="${endSumExcTaxPrice + data.endSumExcTaxPrice}" var="endSumExcTaxPrice"/>
            <td class="text-right">${data.endSumPrice}</td>
            <c:set value="${endSumPrice + data.endSumPrice}" var="endSumPrice"/>
        </tr>
    <c:set var="index" value="${index+1}"/>
    <c:set var="catgoryId" value="${data.categoryId}"/>
    <c:set var="lastCatgoryName" value="${data.categoryName}"/>
    <c:if test="${step.count eq fn:length(statisticsData)}">
        <tr>
            <td class="text-center" colspan="3">${lastCatgoryName}类小计</td>
            <td class="text-right">&nbsp;</td>
            <td class="text-right">&nbsp;</td>
            <td class="text-right">${startSumTax}</td>
            <td class="text-right">${startSumExcTaxPrice}</td>
            <td class="text-right">${startSumPrice}</td>
            <td class="text-right">&nbsp;</td>
            <td class="text-right">&nbsp;</td>
            <td class="text-right">${addSumTax}</td>
            <td class="text-right">${addSumExcTaxPrice}</td>
            <td class="text-right">${addSumPrice}</td>
            <td class="text-right">&nbsp;</td>
            <td class="text-right">&nbsp;</td>
            <td class="text-right">${reduceSumTax}</td>
            <td class="text-right">${reduceSumExcTaxPrice}</td>
            <td class="text-right">${reduceSumPrice}</td>
            <td class="text-right">&nbsp;</td>
            <td class="text-right">&nbsp;</td>
            <td class="text-right">${endSumTax}</td>
            <td class="text-right">${endSumExcTaxPrice}</td>
            <td class="text-right">${endSumPrice}</td>
            <c:set value="${startSumTax + all_startSumTax}" var="all_startSumTax"/>
            <c:set value="${startSumExcTaxPrice + all_startSumExcTaxPrice}" var="all_startSumExcTaxPrice"/>
            <c:set value="${startSumPrice + all_startSumPrice}" var="all_startSumPrice"/>
            <c:set value="${addSumTax + all_addSumTax}" var="all_addSumTax"/>
            <c:set value="${addSumExcTaxPrice + all_addSumExcTaxPrice}" var="all_addSumExcTaxPrice"/>
            <c:set value="${addSumPrice + all_addSumPrice}" var="all_addSumPrice"/>
            <c:set value="${reduceSumTax + all_reduceSumTax}" var="all_reduceSumTax"/>
            <c:set value="${reduceSumExcTaxPrice+  all_reduceSumExcTaxPrice}" var="all_reduceSumExcTaxPrice"/>
            <c:set value="${reduceSumPrice + all_reduceSumPrice}" var="all_reduceSumPrice"/>
            <c:set value="${endSumTax + all_endSumTax}" var="all_endSumTax"/>
            <c:set value="${endSumExcTaxPrice + all_endSumExcTaxPrice}" var="all_endSumExcTaxPrice"/>
            <c:set value="${endSumPrice + all_endSumPrice}" var="all_endSumPrice"/>
        </tr>
        <c:set var="index" value="${index+1}"/>
    </c:if>
    <c:if test="${step.count eq fn:length(statisticsData)}">
        <tr>
            <td class="text-center" colspan="3">总&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;计</td>
            <td class="text-right">&nbsp;</td>
            <td class="text-right">&nbsp;</td>
            <td class="text-right">${all_startSumTax}</td>
            <td class="text-right">${all_startSumExcTaxPrice}</td>
            <td class="text-right">${all_startSumPrice}</td>
            <td class="text-right">&nbsp;</td>
            <td class="text-right">&nbsp;</td>
            <td class="text-right">${all_addSumTax}</td>
            <td class="text-right">${all_addSumExcTaxPrice}</td>
            <td class="text-right">${all_addSumPrice}</td>
            <td class="text-right">&nbsp;</td>
            <td class="text-right">&nbsp;</td>
            <td class="text-right">${all_reduceSumTax}</td>
            <td class="text-right">${all_reduceSumExcTaxPrice}</td>
            <td class="text-right">${all_reduceSumPrice}</td>
            <td class="text-right">&nbsp;</td>
            <td class="text-right">&nbsp;</td>
            <td class="text-right">${all_endSumTax}</td>
            <td class="text-right">${all_endSumExcTaxPrice}</td>
            <td class="text-right">${all_endSumPrice}</td>
        </tr>
        <c:set var="index" value="${index+1}"/>
    </c:if>
    <c:choose>
            <c:when test="${index%20 eq 0}">
                </table>
                    </div>
                <ul class="title sign">
                    <li>审核：</li>
                    <li>财务：</li>
                    <li>部门负责人：</li>
                    <li>制表：</li>
                </ul>
                </div>
            </c:when>
            <c:otherwise>
                <c:if test="${step.count eq fn:length(statisticsData)}">
                    </table>
                    </div>
                    <ul class="title sign">
                        <li>审核：</li>
                        <li>财务：</li>
                        <li>部门负责人：</li>
                        <li>制表：</li>
                    </ul>
                    </div>
                </c:if>
            </c:otherwise>
        </c:choose>

</c:forEach>


</body>

<script src="<c:url value="/resources/admin/global/plugins/jquery.min.js"/>" type="text/javascript"></script>
<script>

    $(function () {
       $('span[name=pageCount]').text('${pageIndex-1}')
    });
</script>
</html>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script src="<c:url value="/resources/admin/global/plugins/jquery.cokie.min.js"/>"  type="text/javascript"></script>

<div class="portlet light">
    <div class="portlet-title">
        <div class="caption">
            <i class="icon-layers font-green"></i>
            <span class="caption-subject font-green sbold uppercase">材料价格趋势图</span>
        </div>
    </div>

    <div class="portlet-body">
        <div id="chartable"  style="width: 800px;height:400px"></div>
    </div>
    <a type="button" class="btn default" href="<c:url value='/admin/materialPrice/historyPrice/${id}'/>">返回历史价格查询</a>
    <a type="button" class="btn default" href="<c:url value='/admin/materialPrice/index'/>">返回价格列表</a>
</div>


<script src="<c:url value="/resources/admin/global/plugins/echarts/echarts.js"/>"></script>
<script>
    $(function () {

        var priceData = [];
        var dateData = [];
        var maxPrice = null;
        var minPrice = null;
        var currentYear = null;
       $.post("/admin/materialPrice/historyPriceChart",{"materialId":'${id}'},function (data) {
            var listM = data.list;
            for (var i in listM ){
                priceData.push(listM[i].price);
                dateData.push(listM[i].updateTime);
            }
            priceData.join(",");
            dateData.join(",");
            maxPrice = data.max;
            minPrice = data.min;
            currentYear = data.year;

           require.config({
               paths: {
                   echarts: '/resources/admin/global/plugins/echarts/dist'
               }
           });

           require(
               [
                   'echarts',
                   'echarts/chart/bar', // 使用柱状图就加载bar模块，按需加载
                   'echarts/chart/line'
               ],
               function (ec) {
                   // 基于准备好的dom，初始化echarts图表
                   var myChart = ec.init(document.getElementById('chartable'));

                   option = {
                       legend: {
                           data:['当年时间(t)与价格(p)变化关系']
                       },
                       toolbox: {
                           show : true,
                           feature : {
                               mark : {show: true},
                               dataView : {show: true, readOnly: false},
                               magicType : {show: true, type: ['line', 'bar']},
                               restore : {show: true},
                               saveAsImage : {show: true}
                           }
                       },
                       calculable : true,
                       tooltip : {
                           trigger: 'axis',
                           formatter: "Price : {c}元 <br/> 日期 : "+currentYear+"-{b}月 "
                       },
                       xAxis : [
                           {
                               type : 'category',
                               axisLabel : {
                                   formatter: '{value} 月'
                               },
                               boundaryGap : false,
                               name:'日期',
                               data : /*dateData*/ ['1', '2', '3', '4', '5', '6', '7', '8', '9','10','11','12']
                           }
                       ],
                       yAxis : [
                           {
                               type : 'value',
                               axisLine : {onZero: false},
                               name:'价格/元',
                               /* axisLabel : {
                                formatter: '{value} '
                                },
                                boundaryGap : false,*/
                               min:minPrice,
                               max:maxPrice
                           }
                       ],
                       series : [
                           {
                               name:'当年时间(t)与价格(p)变化关系',
                               type:'line',
                               smooth:true,
                               itemStyle: {
                                   normal: {
                                       lineStyle: {
                                           shadowColor : 'rgba(0,0,0,0.4)'
                                       }
                                   }
                               },
                               data:priceData/*[1, 14, 28, 59, 70, 64, 56, 45, 80,"-","-","-"]*/
                           }
                       ]
                   };


                   // 为echarts对象加载数据
                   myChart.setOption(option);
               }
           );
        });



    });

</script>
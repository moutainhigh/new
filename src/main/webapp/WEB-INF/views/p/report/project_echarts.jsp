<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/tlds/lycore.tld" prefix="ly"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<div class="row">
    <div class="col-md-12">
        <div class="portlet">
            <div class="portlet-title">
                <div class="caption">
                    <label>所属区域：</label>
                    <select id="id_i1" class="form-control input-inline input-sm" style="width: 200px;" autocomplete="off">
                        <option value="0">请选择区域</option>
                        <c:forEach items="${projectAreaList}" var="projectArea">
                            <option value="${projectArea.id}" <c:if test="${projectArea.id eq entity.i1}">selected</c:if> >${projectArea.name}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="actions">
                </div>
            </div>
            <div id="js_index_content" class="portlet-body">
                <div class="table-scrollable fixed-table-container">
                    <!-- 为ECharts准备一个具备大小（宽高）的Dom -->
                    <div id="main" style="height:600px;min-width: 600px;"></div>
                </div>
                <!--//End Pricing -->
            </div>
        </div>
        <!-- END INLINE NOTIFICATIONS PORTLET-->
    </div>
</div>
<!-- END EXAMPLE TABLE PORTLET-->
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/echarts/dist/echarts.js"/>"></script>
<!-- ECharts单文件引入 -->
<script type="text/javascript">

    //准备数据
    var xprojectName_data=[],wcjd_data=[],jdwcl_data=[],qzdfl_data=[],count=0;
    <c:forEach items='${projectArchivesList}' var='projectArchives' varStatus="p">
    xprojectName_data.push('${projectArchives.projectName}');
    wcjd_data.push('${projectArchives.successNodeNum}');
    jdwcl_data.push('${projectArchives.successNodeNumRate2}');
    qzdfl_data.push('${projectArchives.successWeightRate2}');
    count=parseInt(count)+parseInt('${p.count}');
    </c:forEach>

    $('#main').css("width",(count*120)+"px");
    // 路径配置
    require.config({
        paths: {
            echarts: '/resources/admin/global/plugins/echarts/dist'
        }
    });

    // 使用
    require(
        [
            'echarts',
            'echarts/chart/bar' // 使用柱状图就加载bar模块，按需加载
        ],
        function (ec) {
            // 基于准备好的dom，初始化echarts图表
            var myChart = ec.init(document.getElementById('main'));
            var ecConfig = require('echarts/config');
            myChart.on(ecConfig.EVENT.CLICK, eConsole);
            var option = {
                title : {
                    //text: '项目完成概况'
                    //,subtext: '数据来自网络'
                },
                tooltip: {
                    trigger: 'item'
                },
                legend: {
                    data:['完成节点（个数）','节点完成率（%）','权重得分率（%）']
                },
                grid: {
                    x:'120px'
                    ,x2:'120px'
                },
                toolbox: {
                    show : true,
                    feature : {
                        mark : {show: true},
                        dataView : {show: true, readOnly: false},
                        magicType : {show: false, type: ['line', 'bar']},
                        restore : {show: false},
                        saveAsImage : {show: false}
                    }
                },
                calculable : true,
                yAxis : [
                    {
                        type : 'value'
                        ,name :'节点（个数）'
                        ,max:50
                        ,axisLine:{
                            show:true
                            ,onZero:true
                        }
                        ,axisLabel:{
                            show:true
                            ,margin:25
                            ,textStyle:{
                                baseline:'top'
                            }
                        }
                    },
                    {
                        type : 'value'
                        ,name :'节点完成/权重得分率（%）'
                        ,max:100
                        ,splitNumber:10
                        ,axisLine:{
                            show:true
                            ,onZero:true
                        }
                        ,axisLabel:{
                            show:true
                            ,margin:10
                            ,textStyle:{
                                baseline:'top'
                            }
                        }
                    }
                ],
                xAxis : [
                    {
                        axisLabel : {clickable:true},
                        type : 'category',
                        data : xprojectName_data
                    }
                ],
                series : [
                    {
                        name:'完成节点（个数）',
                        type:'bar',
                        itemStyle : { normal: {label : {show: true, position: 'top'}}},
                        clickable:false,
                        data:wcjd_data
                    },
                    {
                        name:'节点完成率（%）',
                        type:'bar',
                        itemStyle : { normal: {label : {show: true, position: 'top'}}},
                        clickable:false,
                        yAxisIndex: 1,
                        data:jdwcl_data
                    },
                    {
                        name:'权重得分率（%）',
                        type:'bar',
                        itemStyle : { normal: {label : {show: true, position: 'top'}}},
                        clickable:false,
                        yAxisIndex: 1,
                        data:qzdfl_data
                    }
                ]
            };

            // 为echarts对象加载数据
            myChart.setOption(option);
        }
    );

    function eConsole(param) {
        if (typeof param.seriesIndex == 'undefined') {
            return;
        }
        if (param.type == 'click') {
            window.location.href = encodeURI("<c:url value="/project/report/getchartsdtl"/>"+"?n="+param.name);
        }
    }

</script>
<script type="text/javascript">

    $(function () {
        $('#id_i1').on('change',function () {
            window.location.href = "<c:url value="/project/report/getcharts"/>"+"?i1="+$('#id_i1').val();
        });
    });

</script>
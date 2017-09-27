<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/tlds/lycore.tld" prefix="ly"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<style type="text/css">
    *{
        margin:0;
        padding:0;
        box-sizing:border-box;
    }
    body{
        font-family:'microsoft Yahei';
        color:#333;
        font-size:14px;
    }
    .wrapper-box{
        text-align:center;
    }
    .wrapper-box h1{
        font-size:18px;
        text-align:center;
        font-weight:normal;
    }
    .identification{
        padding:20px 0;
    }
    .identification span{
        margin:0 20px;
    }
    .identification i , .step-box li i{
        display:inline-block;
        margin-right:5px;
        vertical-align:middle;
    }
    .dot{
        width:10px;
        height:10px;
        border-radius: 50%;
        -webkit-border-radius: 50%;
        -moz-border-radius: 50%;
        -o-border-radius: 50%;
        -ms-border-radius: 50%;
    }
    .identification .square{
        width:15px;
        height:10px;
    }
    .primary-node{
        background-color:#cfd3d7;
    }
    .key-node{
        background-color:#33c0cd;
    }
    .complete{
        background-color:#cfd3d7;
    }
    .no-complete{
        background-color:#f5011a;
    }
    .no-complete_wait{
        background-color: #e7b719;
    }
    .no-complete_ok{
        background-color: #328a79;
    }
    .itemStep{
        position:relative;
        margin:30px auto 0;
        width:1100px;
    }
    .step-box{
        position:relative;
        width:1100px;
        overflow:hidden;
    }
    .step-box:after{
        content:"";
        position:absolute;
        top:32px;
        left:0;
        width:100%;
        height:1px;
        border-bottom:#cfd3d7 1px dashed;
        z-index:1;
    }
    .step-box ul{
        margin-left:0;
        width: 99999px;
    }
    .step-box li{
        position:relative;
        float:left;
        width:110px;
        list-style:none;
    }
    .step-box li label{
        position:relative;
        display:inline-block;
        padding:2px 5px;
        font-size:12px;
        border-radius: 2px;
        -webkit-border-radius: 2px;
        -moz-border-radius: 2px;
        -o-border-radius: 2px;
        -ms-border-radius: 2px;
    }
    .step-box li > div{
        margin:25px auto 0;
        width:20px;
        line-height:14px;
        word-wrap:break-word;
    }
    .step-box li i{
        position:absolute;
        top:28px;
        left:50%;
        margin-left:-5px;
        z-index:2;
    }
    .step-box li .complete ,
    .step-box li .no-complete,
    .step-box li .no-complete_wait,
    .step-box li .no-complete_ok{
        color:#fff;
    }
    .step-box li .complete:after{
        content:"";
        position:absolute;
        top:21px;
        left:50%;
        width:0px;
        height:0px;
        margin-left:-4px;
        border-left: 4px solid transparent;
        border-right: 4px solid transparent;
        border-top: 4px solid #cfd3d7;
    }
    .step-box li .no-complete:after{
        content:"";
        position:absolute;
        top:21px;
        left:50%;
        width:0px;
        height:0px;
        margin-left:-4px;
        border-left: 4px solid transparent;
        border-right: 4px solid transparent;
        border-top: 4px solid #f5011a;
    }
    .step-box li .no-complete_wait:after{
        content:"";
        position:absolute;
        top:21px;
        left:50%;
        width:0px;
        height:0px;
        margin-left:-4px;
        border-left: 4px solid transparent;
        border-right: 4px solid transparent;
        border-top: 4px solid #e7b719;
    }
    .step-box li .no-complete_ok:after{
        content:"";
        position:absolute;
        top:21px;
        left:50%;
        width:0px;
        height:0px;
        margin-left:-4px;
        border-left: 4px solid transparent;
        border-right: 4px solid transparent;
        border-top: 4px solid #328a79;
    }
    .btna{
        display:block;
        position:absolute;
        top:20px;
        width:30px;
        height:30px;
    }
    .last-btn{
        left:-30px;
    }
    .next-btn{
        right:-30px;
    }

</style>
</head>

<body>
<div class="wrapper-box">
    <h1>${n}</h1>
    <div class="identification">
        <span><i class="dot primary-node"></i>一级节点</span>
        <span><i class="dot key-node"></i>关键节点</span>
        <span><i class="square complete"></i>未超期未完成</span>
        <span><i class="square no-complete_wait"></i>超期未完成</span>
        <span><i class="square no-complete_ok"></i>按时完成</span>
        <span><i class="square no-complete"></i>未按时完成</span>
    </div>
    <div class="itemStep">
        <div class="step-box">
            <ul>
                <c:forEach items="${projectPlanCompileList}" var="e">
                    <li>
                        <label class="${e.completeStatus eq 1?'no-complete_ok':(e.completeStatus eq 2?'no-complete':(e.completeStatus eq 3?'no-complete_wait':'complete'))}">${fn:substring(e.endDate,0,10)}</label>
                        <i class="dot ${e.iskeyNode eq 2?'key-node':'primary-node'}"></i>
                        <div>${fn:replace(fn:replace(fn:replace(fn:replace(e.taskName,'）','︶'),'（','︵'),'》','︾'),'《','︽')}</div>
                    </li>
                </c:forEach>
            </ul>
            <%--<ul>
                <li>
                    <label class="complete">2017-5-30</label>
                    <i class="dot primary-node"></i>
                    <div>办理︽国有土地使用权证︾</div>
                </li>
                <li>
                    <label class="complete">2017-5-30</label>
                    <i class="dot key-node"></i>
                    <div>办理︽国有土地使用权证︾</div>
                </li>
                <li>
                    <label class="complete">2017-5-30</label>
                    <i class="dot primary-node"></i>
                    <div>办理︽国有土地使用权证︾</div>
                </li>
                <li>
                    <label class="complete">2017-5-30</label>
                    <i class="dot primary-node"></i>
                    <div>办理︽国有土地使用权证︾</div>
                </li>
                <li>
                    <label class="complete">2017-5-30</label>
                    <i class="dot key-node"></i>
                    <div>办理︽国有土地使用权证︾</div>
                </li>
                <li>
                    <label class="no-complete">2017-5-30</label>
                    <i class="dot primary-node"></i>
                    <div>办理︽国有土地使用权证︾</div>
                </li>
                <li>
                    <label class="complete">2017-5-30</label>
                    <i class="dot primary-node"></i>
                    <div>办理︽国有土地使用权证︾</div>
                </li>
                <li>
                    <label>2017-5-30</label>
                    <i class="dot key-node"></i>
                    <div>办理︽国有土地使用权证︾</div>
                </li>
                <li>
                    <label>2017-5-30</label>
                    <i class="dot key-node"></i>
                    <div>办理︽国有土地使用权证︾</div>
                </li>
                <li>
                    <label>2017-5-30</label>
                    <i class="dot key-node"></i>
                    <div>办理︽国有土地使用权证︾</div>
                </li>
                <li>
                    <label>2017-5-30</label>
                    <i class="dot key-node"></i>
                    <div>办理︽国有土地使用权证︾</div>
                </li>
                <li>
                    <label>2017-5-30</label>
                    <i class="dot key-node"></i>
                    <div>办理︽国有土地使用权证︾</div>
                </li>
                <li>
                    <label>2017-5-30</label>
                    <i class="dot key-node"></i>
                    <div>办理︽国有土地使用权证︾</div>
                </li>
            </ul>--%>
        </div>
        <a class="btna last-btn" href="#"><i class="fa fa-chevron-left"></i></a>
        <a class="btna next-btn" href="#"><i class="fa fa-chevron-right"></i></a>
    </div>
</div>
<script type="text/javascript">
    $(function(){

        var page = 1;
        var i = 10; //每版放10个
        var len = $(".step-box ul li").length;
        var page_count = Math.ceil(len / i) ;   //只要不是整数，就往大的方向取最小的整数
        var none_unit_width = $(".itemStep").width(); //获取框架内容的宽度,不带单位
        var $parent = $(".step-box ul");
        //向右 按钮
        $(".itemStep .next-btn").click(function(){
            if( !$parent.is(":animated") ){
                if( page == page_count ){  //已经到最后一个版面了,如果再向后，必须跳转到第一个版面（或者不跳转）。
                    /*$parent.animate({ top : 0}, 800); //通过改变top值，跳转到第一个版面*/
                    $parent.animate.stop();
                    page = 1;
                }else{
                    $parent.animate({ marginLeft : '-='+none_unit_width}, 800);  //通过改变top值，达到每次换一个版面
                    page++;
                }
            }
        });
        //往左 按钮
        $(".itemStep .last-btn").click(function(){
            if( !$parent.is(":animated") ){
                if( page == 1 ){  //已经到第一个版面了,如果再向前，必须跳转到最后一个版面(或者不跳转)。
                    /*$parent.animate({ top : '-='+none_unit_height*(page_count-1)}, 800); //通过改变left值，跳转到最后一个版面 */
                    $parent.animate.stop();
                    page = page_count;
                }else{
                    $parent.animate({ marginLeft : '+='+none_unit_width}, 800);  //通过改变left值，达到每次换一个版面
                    page--;
                }
            }
        });


    })
</script>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>华宇集团招聘</title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/hr/front/css/recruit_style.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/hr/front/css/font-awesome.css"/>">
    <link rel="icon" type="image/ico" href="<c:url value="/resources/hr/front/images/favicon.ico"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/webuploader/css/webuploader.css" />">
    <style>
        .laydate_box, .laydate_top, .laydate_ym, .laydate_bottom {
            box-sizing: content-box;
        }

        /*.help-block{*/
        /*color: red;*/
        /*}*/
    </style>
</head>
<style>
    .webuploader-pick {
        padding: 5px 15px;
        background-color: #c1c1c1;
    }
</style>
<body>
<!--header-->
<div class="r_header wrapper-width">
    <div class="container">
        <div class="r_logo"><a href="http://www.cqhyrc.com.cn/talent"><img
                src="<c:url value="/resources/hr/front/images/r_logo.png"/>"></a></div>
        <div class="r_nav">
            <ul>
                <li><a href="<c:url value="/recruitment/society"/>"><i class="sociology"></i>社会招聘</a></li>
                <li><a href="<c:url value="/recruitment/school"/>"><i class="campus"></i>校园招聘</a></li>
                <li><a href="<c:url value="/recruitment/practice"/>"><i class="internship"></i>实习生招聘</a></li>
                <li><a href="<c:url value="/recruitment/aboutUs"/>"><i class="stay"></i>人在华宇</a></li>
                <li><a href="http://www.cqhyrc.com.cn/"><i class="return"></i>返回首页</a></li>
            </ul>
        </div>
    </div>
</div>
<!--sider-->
<div class="r_sider_wrapper wrapper-width">
    <div class="r_slide_box1">
        <div class="ggBox"><img src="<c:url value="/resources/hr/front/images/bannar4.jpg"/>"></div>
    </div>
</div>
<!--招聘-->
<div class="center_box wrapper-width">
    <div class="container">
        <div class="r_url"><i class="icon-home"></i>首页 > 人才发展 > 简历投递</div>
        <div class="main_box" style="width: 1170px;">
            <form id="requestForm">
                <div class="delivery_resume">
                    <h3><b>简历投递流程:</b></h3>
                    <ul>
                        <li><b>1、下载并填写：<a class="orange" href="<c:url value="/resources/hr/front/file/应聘登记表.doc"/>">《应聘登记表》</a></b>
                        </li>
                        <li><b>2、填写您的基本信息并提交《应聘登记表》</b></li>
                        <li><label>应聘岗位：</label>
                            <select name="job" title="应聘岗位">
                                <option value="">请选择</option>
                                <c:forEach items="${titleList}" var="entity">
                                    <c:if test="${jid eq entity.id}">
                                        <option value="${entity.id}" selected>${entity.title}</option>
                                    </c:if>
                                </c:forEach>
                            </select>
                        </li>
                        <li><label>应聘公司：</label>
                            <select name="plateId" title="应聘公司">
                                <c:if test="${cid eq 0}">
                                    <option value="0" selected="selected">集团总部</option>
                                </c:if>
                                <c:if test="${cid eq 1}">
                                    <option value="1" selected="selected">地产集团</option>
                                </c:if>
                                <c:if test="${cid eq 2}">
                                    <option value="2" selected="selected">商业集团</option>
                                </c:if>
                                <c:if test="${cid eq 3}">
                                    <option value="3" selected="selected">金控集团</option>
                                </c:if>
                                <c:if test="${cid eq 4}">
                                    <option value="4" selected="selected">建设集团</option>
                                </c:if>
                                <c:if test="${cid eq 5}">
                                    <option value="5" selected="selected">物业集团</option>
                                </c:if>
                                <c:if test="${cid eq 6}">
                                    <option value="6" selected="selected">海外公司</option>
                                </c:if>
                            </select>
                        </li>
                        <c:if test="${cid ne 0 && cid ne 6}">
                            <li>
                                <label>区域：</label>
                                <select class="select" name="area">
                                    <c:if test="${area eq 1}">
                                        <option value="1" selected="selected">重庆区域</option>
                                    </c:if>
                                    <c:choose>
                                        <c:when test="${cid eq 3}">
                                            <c:if test="${area eq 4}">
                                                <option value="4" selected="selected">上海区域</option>
                                            </c:if>
                                        </c:when>
                                        <c:when test="${cid eq 1}">
                                            <c:if test="${area eq 5}">
                                                <option value="5" selected="selected">长沙城市公司</option>
                                            </c:if>
                                            <c:if test="${area eq 6}">
                                                <option value="6" selected="selected">沈阳城市公司</option>
                                            </c:if>
                                        </c:when>
                                        <c:otherwise>
                                            <c:if test="${area eq 2}">
                                                <option value="2" selected="selected">四川区域</option>
                                            </c:if>
                                            <c:if test="${area eq 3}">
                                                <option value="3" selected="selected">华东区域</option>
                                            </c:if>
                                        </c:otherwise>

                                    </c:choose>
                                </select>
                            </li>
                        </c:if>
                        <c:if test="${cid eq 0 || cid eq 6}">
                            <input type="hidden" value="1" name="area">
                        </c:if>
                        <li><label>您的姓名：</label>
                            <input name="name" type="text" title="姓名"></li>
                        <li><label>性别：</label>
                            <select name="sex" title="性别">
                                <option value="1">男</option>
                                <option value="0">女</option>
                            </select>
                        </li>
                        <li><label>出生年月：</label>
                            <input name="birth" type="text" id="birth" class="laydate-icon" value="" readonly
                                   title="出生年月"></li>
                        <li><label>您的电话：</label>
                            <input name="mobile" type="text" title="电话"></li>
                        <li>
                            <label>学历：</label>
                            <input name="edu" type="text" value="">
                        </li>
                        <li>
                            <label>专业：</label>
                            <input name="profession" type="text" value="">
                        </li>
                        <li>
                            <label>毕业院校：</label>
                            <input name="school" type="text" value="">
                        </li>
                        <li><label>最近的工作单位：</label>
                            <input name="recentCompany" type="text" title="最近的工作单位">
                        </li>
                        <li><label>最近工作时间：</label>
                            <input name="recentJobTime" type="text" placeholder="例如：2014-5-2到2016-12-20" title="最近工作时间">
                        </li>
                        <li><label>最近的工作职位：</label>
                            <input name="recentJob" type="text" title="最近的工作职位">
                        </li>
                        <li><label>《应聘登记表》：</label>
                            <div id="uploader" style="display: inline-block">选择附件</div>
                            <input value="" name="attachment" type="hidden"/>
                        </li>
                        <li class="orange" style="display:none"><label>&nbsp;</label>信息填写错误，请检查！</li>
                        <li><label>&nbsp;</label><input class="btn rad4 js_subform" type="button" value="提交"></li>
                    </ul>
                </div>
            </form>
        </div>
    </div>
</div>
<!--footer-->
<div class="footer wrapper-width">
    <div class="container">
        © 1995 - 2017 重庆华宇集团有限公司　版权所有　　渝ICP备13001850号
    </div>
</div>
<script src="<c:url value="/resources/hr/front/js/jquery-1.11.1.min.js"/>" type="text/javascript"></script>
<script type="text/javascript" src="<c:url value="/resources/webuploader/js/webuploader.js" />"></script>
<script src="<c:url value="/resources/plugIn/laydate/laydate.js"/>" type="text/javascript"></script>
<script type="text/javascript"
        src="<c:url value="/resources/admin/global/plugins/jquery-validation/js/jquery.validate.min.js" />"></script>
<script type="text/javascript"
        src="<c:url value="/resources/admin/global/plugins/jquery-validation/js/additional-methods.min.js" />"></script>
<script type="text/javascript"
        src="<c:url value="/resources/admin/global/plugins/jquery-validation/js/messages_zh.min.js" />"></script>
<script>

    $(function () {

        laydate({
            elem: '#birth'
        });

        // 初始化Web Uploader
        var single = WebUploader.create({
            fileVal: 'imgFile',
            auto: true,
            compress: false,
            swf: '<c:url value="/resources/webuploader/js/Uploader.swf" />',
            server: '<c:url value="/position/uploadFile" />',
            pick: {
                id: '#uploader',
                label: '选择文件',
                multiple: false
            },
            fileNumLimit: 1,
            fileSingleSizeLimit: 5 * 1024 * 1024//,    // 5M
        });

        single.on('uploadSuccess', function (file, json) {
            $('#uploader').prev('span').text(file.name);
            $('input[name=attachment]').val(json.rdata);
            alert("上传成功！");
        });

        single.on('uploadError', function (file) {
            alert("附件上传失败！");
        });
        single.on('error', function (e) {
            if (e == "F_EXCEED_SIZE") {
                alert("附件大小超过最大限制！");
            }
        });

        $('#requestForm').validate({
            errorElement: 'span',
            errorClass: 'help-block',
            focusInvalid: false,
            ignore: "",
            rules: {
                name: {
                    required: true
                }, sex: {
                    required: true
                }, birth: {
                    required: true
                }, mobile: {
                    required: true,
                    phone: true
                }, plateId: {
                    required: true
                }, job: {
                    required: true
                }, area: {
                    required: true
                }, edu: {
                    required: true
                }, profession: {
                    required: true
                }, school: {
                    required: true
                }, recentCompany: {
                    required: true
                }, recentJobTime: {
                    required: true
                }, recentJob: {
                    required: true
                }
            },
            errorPlacement: function (error, element) {

            },
            submitHandler: function (form) {

            }
        });

        $('input.js_subform').on('click', function () {
            if ($('#requestForm').valid()) {
                $('li.orange').hide();
                if (confirm("你确定提交吗！")) {
                    $('#subBtn').attr('disabled', true);
                    var data = $('#requestForm').serialize();
                    data.way = 1;
                    $.post('<c:url value='/position/postJobRequest'/>', data, function (result) {
                        if (result.success) {
                            alert("提交成功！");
                            window.location.reload();
                        } else {
                            layer.msg(result.rmsg);
                        }
                    }, 'json');
                }
            } else {
                $('li.orange').show();
            }
        })


    });
</script>
</body>
</html>

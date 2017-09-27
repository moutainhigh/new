<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport"
          content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
    <meta name="format-detection" content="telephone=no"/>
    <meta name="apple-mobile-web-app-capable" content="yes"/>
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta http-equiv="pragma" content="no-cache"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/hr/front/css/style.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/webuploader/css/webuploader.css" />">
    <title>华宇招聘</title>
    <style>
        body {
            background-color: #fff;
        }
        .laydate_box,.laydate_top , .laydate_ym , .laydate_bottom {
            box-sizing: content-box;
        }
        .help-block{
            display: block;
            color: red;
        }
    </style>
</head>
<body>
<div class="content_box">
    <div class="hr_sider"><img src="<c:url value="/resources/hr/front/images/hr_sider.jpg"/>"></div>
    <div class="hr_title">
        <h1>华宇集团职位投递</h1>
        <p>华载未来</p>
        <p>宇你共精彩</p>
    </div>
    <div class="application_box">
        <div class="recruit_form">
            <form id="requestForm">
            <ul>
                <li>
                    <label>姓名</label>
                    <input name="name" type="text" class="form-control" value="">
                </li>
                <li>
                    <label>性别</label>
                    <div class="item">
                        <label class="form-radio">
                          <span class="icon">
                            <input name="sex" class="input" checked="checked" type="radio" value="1">
                            <i class="radio"></i>
                          </span>
                            <span class="label">男</span>
                        </label>
                        <label class="form-radio">
                          <span class="icon">
                            <input name="sex" class="input" type="radio" value="0">
                            <i class="radio"></i>
                          </span>
                            <span class="label">女</span>
                        </label>
                    </div>
                </li>
                <li>
                    <label>出生年月</label>
                    <input name="birth" type="text" id="birth" class="laydate-icon" value="" readonly>
                </li>
                <li>
                    <label>联系电话</label>
                    <input name="mobile" type="text" value="">
                </li>
                <li>
                    <label>学历</label>
                    <input name="edu" type="text" value="">
                </li>
                <li>
                    <label>专业</label>
                    <input name="profession" type="text" value="">
                </li>
                <li>
                    <label>毕业院校</label>
                    <input name="school" type="text" value="">
                </li>
                <li>
                    <label>应聘版块</label>
                    <div class="item">
                        <c:choose>
                            <c:when test="${cid eq 0}">
                                <label class="form-radio">
                                  <span class="icon">
                                      <input name="plateId" class="input"  type="radio" value="0" checked="checked">
                                    <i class="radio"></i>
                                  </span>
                                    <span class="label">集团总部</span>
                                </label>
                            </c:when>
                            <c:when test="${cid eq 1}">
                                <label class="form-radio">
                                  <span class="icon">
                                      <input name="plateId" class="input"  type="radio" value="1" checked="checked">
                                    <i class="radio"></i>
                                  </span>
                                    <span class="label">地产集团</span>
                                </label>
                            </c:when>
                            <c:when test="${cid eq 2}">
                                <label class="form-radio">
                                      <span class="icon">
                                        <input name="plateId" class="input" type="radio" value="2" checked="checked">
                                        <i class="radio"></i>
                                      </span>
                                    <span class="label">商业集团</span>
                                </label>
                            </c:when>
                            <c:when test="${cid eq 3}">
                                <label class="form-radio">
                                  <span class="icon">
                                    <input name="plateId" class="input" type="radio" value="3" checked="checked">
                                    <i class="radio"></i>
                                  </span>
                                    <span class="label">金控集团</span>
                                </label>
                            </c:when>
                            <c:when test="${cid eq 4}">
                                <label class="form-radio">
                              <span class="icon">
                                <input name="plateId" class="input" type="radio" value="4" checked="checked">
                                <i class="radio"></i>
                              </span>
                                    <span class="label">建设集团</span>
                                </label>
                            </c:when>

                            <c:when test="${cid eq 5}">
                                <label class="form-radio">
                                  <span class="icon">
                                    <input name="plateId" class="input" type="radio" value="5" checked="checked">
                                    <i class="radio"></i>
                                  </span>
                                    <span class="label">物业集团</span>
                                </label>
                            </c:when>
                            <c:when test="${cid eq 6}">
                                <label class="form-radio">
                                  <span class="icon">
                                    <input name="plateId" class="input" type="radio" value="6" checked="checked">
                                    <i class="radio"></i>
                                  </span>
                                    <span class="label">海外公司</span>
                                </label>
                            </c:when>
                        </c:choose>
                    </div>
                </li>
                <c:if test="${cid ne 0 && cid ne 6}">
                    <li>
                        <select class="select" name="area">
                            <c:if test="${area eq 1}"><option value="1" selected="selected">重庆区域</option></c:if>
                        <c:choose>
                            <c:when test="${cid eq 3}">
                                <c:if test="${area eq 4}"><option value="4" selected="selected">上海区域</option></c:if>
                            </c:when>
                            <c:when test="${cid eq 1}">
                                <c:if test="${area eq 5}"><option value="5" selected="selected">长沙城市公司</option></c:if>
                                <c:if test="${area eq 6}"><option value="6" selected="selected">沈阳城市公司</option></c:if>
                            </c:when>
                            <c:otherwise>
                                <c:if test="${area eq 2}"><option value="2" selected="selected">四川区域</option></c:if>
                                <c:if test="${area eq 3}"><option value="3" selected="selected">华东区域</option></c:if>
                            </c:otherwise>
                        </c:choose>
                        </select>
                    </li>
                </c:if>
                <c:if test="${cid eq 0 || cid eq 6}">
                    <input type="hidden" value="1" name="area">
                </c:if>
                <li>
                    <label>应聘职位</label>
                    <div class="item js_job_list">
                        <c:forEach items="${titleList}" var="entity">
                            <c:if test="${entity.id eq jid}">
                                <label class="form-radio">
                                  <span class="icon">
                                    <input name="job" class="input" type="radio" value="${entity.id}" checked="checked">
                                    <i class="radio"></i>
                                  </span>
                                    <span class="label">${entity.title}</span>
                                </label>
                            </c:if>
                        </c:forEach>
                    </div>
                </li>
                <li>
                    <label>最近工作单位</label>
                    <input name="recentCompany" type="text" value="">
                </li>
                <li>
                    <label>最近工作时间</label>
                    <input name="startTime" id="startTime" type="text" readonly class="laydate-icon">
                </li>
                <li>
                    <label>至</label>
                    <input name="endTime" id="endTime" readonly type="text" class="laydate-icon">
                </li>
                <li>
                    <label>最近工作职位</label>
                    <input name="recentJob" type="text">
                </li>
                <li>
                    <label>简历附件(备注：安卓系统手机可以直接上传word版简历原件，IOS系统请讲简历拍照后上传)</label>
                    <input  value="" name="attachment" type="hidden"/>
                    <span></span>
                    <div id="uploader">选择附件</div>
                <li>
                <li><input class="submit btn rad4" id="subBtn" type="submit" value="提交申请"></li>
            </ul>
            </form>
        </div>
    </div>
</div>
</body>
</html>
<script src="<c:url value="/resources/hr/front/js/jquery-1.11.1.min.js"/>" type="text/javascript"></script>
<script src="<c:url value="/resources/plugIn/laydate/laydate.js"/>" type="text/javascript"></script>

<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/jquery-validation/js/jquery.validate.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/jquery-validation/js/additional-methods.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/jquery-validation/js/messages_zh.min.js" />"></script>

<script type="text/javascript" src="<c:url value="/resources/webuploader/js/webuploader.js" />"></script>

<script>

    $(document).ready(function () {
        laydate({
            elem: '#birth'
        });
        laydate({
            elem: '#startTime'
        });
        laydate({
            elem: '#endTime'
        });
    });

    // 初始化Web Uploader
    var single = WebUploader.create({
        fileVal:'imgFile',
        auto: true,
        compress: false,
        swf: '<c:url value="/resources/webuploader/js/Uploader.swf" />',
        server: '<c:url value="/position/uploadFile" />',
        pick: {
            id: '#uploader',
            label: '选择文件',
            multiple:false
        },
        fileNumLimit: 1,
        fileSingleSizeLimit: 5 * 1024 * 1024//,    // 5M
    });

    single.on( 'uploadSuccess', function( file ,json) {
        $('#uploader').prev('span').text(file.name);
        $('input[name=attachment]').val(json.rdata);
        alert("上传成功！");
    });

    single.on( 'uploadError', function( file ) {
        alert("附件上传失败！");
    });
    single.on( 'error', function( e) {
        if (e =="F_EXCEED_SIZE"){
            alert("附件大小超过最大限制！");
        }
    });

    $('#requestForm').validate({
        errorElement : 'span',
        errorClass : 'help-block',
        focusInvalid : false,
        ignore : "",
        rules : {
            name : {
                required : true
            },
            sex : {
                required : true
            },
            birth : {
                required : true
            },
            mobile : {
                required : true,
                phone:true
            },
            school : {
                required : true
            },
            edu : {
                required : true
            },
            profession : {
                required : true
            },
            plateId : {
                required : true
            },
            job : {
                required : true
            },
            recentCompany : {
                required : true
            },
            startTime : {
                required : true
            },
            endTime : {
                required : true
            },
            recentJob : {
                required : true
            }
        },
        errorPlacement: function(error, element) {
            element.parents('li').append(error);
        },
        submitHandler : function(form) {
            var _url = $('input[name=attachment]').val();
            if(!_url){
                alert("请上传简历附件");
                return;
            }
            if(confirm("你确定提交吗！")) {
                $('#subBtn').attr('disabled',true);
                var data = $(form).serialize();
                data+='&way=0&recentJobTime='+$('#startTime').val()+"至"+$('#endTime').val();
                $.post('<c:url value='/position/postJobRequest'/>', data, function (result) {
                    if (result.success){
                        alert("提交成功！");
                        window.location.reload();
                    }else {
                        layer.msg(result.rmsg);
                    }
                },'json');
            }
        }
    });
    var getData=function (_cid,_aid) {
        $('div.js_job_list').empty();
        $.post('<c:url value='/position/getJobList'/>', {plateId:_cid,area:_aid}, function (result) {
            if (result){
                var html = '';
                $(result).each(function (k, v) {
                    html= ['<label class="form-radio">',
                        '  <span class="icon">',
                        '	<input name="job" class="input" type="radio" value="'+v.id+'">',
                        '	<i class="radio"></i>',
                        '  </span>',
                        '	<span class="label">'+v.title+'</span>',
                        '</label>'].join("");
                    $('div.js_job_list').append(html);
                });
            }else {
                alert("获取公司失败！");
            }
        },'json');
    }


    $('select[name=area]').on('change',function () {
        getData($('input[name=plateId]:checked').val(),$(this).val());
    });



</script>

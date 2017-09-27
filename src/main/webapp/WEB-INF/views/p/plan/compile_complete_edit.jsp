<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/tlds/lycore.tld" prefix="ly"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!-- 开始全局CSS -->
<link href="<c:url value="/resources/admin/global/plugins/font-awesome/css/font-awesome.min.css" />" rel="stylesheet" type="text/css" />
<link href="<c:url value="/resources/admin/global/plugins/simple-line-icons/simple-line-icons.min.css" />" rel="stylesheet" type="text/css" />
<link href="<c:url value="/resources/admin/global/plugins/bootstrap/css/bootstrap.min.css" />" rel="stylesheet" type="text/css" />
<link href="<c:url value="/resources/admin/global/plugins/uniform/css/uniform.default.css" />" rel="stylesheet" type="text/css" />
<link href="<c:url value="/resources/admin/global/plugins/bootstrap-switch/css/bootstrap-switch.min.css" />" rel="stylesheet" type="text/css" />
<!-- 结束全局CSS -->

<!-- 开始主题CSS -->
<link href="<c:url value="/resources/admin/global/css/components.min.css" />" rel="stylesheet" id="style_components" type="text/css" />
<link href="<c:url value="/resources/uploadify/css/uploadify.css" />" rel="stylesheet" type="text/css" />
<style>
    .actions * {
        margin-top: 0px;
    }
    body {
        font-size: 10px;
    }
    .select-delete{
        background: url(<c:url value="/resources/uploadify/img/uploadify-cancel.png"/> ) 0 0 no-repeat;
        float: right;
        text-indent: -9999px;
        width: 16px;
    }
</style>
	<!-- BEGIN SAMPLE FORM PORTLET-->
    <div class="portlet light" style="margin-bottom: -38px;">
        <div class="portlet-title" style="min-height:29px;">
            <div class="caption" style="line-height: 10px;">
                <span class="caption-subject font-green sbold uppercase">编辑完成情况</span>
            </div>
        </div>
    </div>
		<div class="portlet-body form">
            <form id="addForm" class="form-horizontal" action="#" method="POST" autocomplete="off" >
				<input type="hidden" name="compileId" value="${entity.compileId}"/>
				<input type="hidden" name="projectId" value="${entity.projectId}"/>
				<div class="form-body">
                    <%--<div class="form-group">
                        <label class="col-md-2 control-label">是否完成：</label>
                        <div class="col-md-2">
                            <select name="isComplete" class="form-control" id="id_isComplete">
                                <option value="2" <c:if test="${2 eq entity.isComplete}">selected</c:if> >是</option>
                                <option value="3" <c:if test="${3 eq entity.isComplete}">selected</c:if> >否</option>
                            </select>
                        </div>
                        <label class="col-md-2 control-label">完成时间：</label>
                        <div class="col-md-2">
                            <%
                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                String date= format.format(new Date());
                            %>
                            <c:set var="defalutdata" value="<%=date%>"/>
                            <input type="text" name="completeDate" id="id_completeDate" onclick="laydate({istime: true, format: 'YYYY-MM-DD'})" value="${empty entity.completeDate?defalutdata:(fn:substring(entity.completeDate,0,10))}" class="form-control laydate-icon" readonly="readonly" />
                        </div>
                    </div>--%>
					<div class="form-group">
						<label  class="col-md-2 control-label">完成情况描述：</label>
						<div class="form-div-content col-md-6">
						 <textarea name="completeRemark" style="height:60px;font-size:14px;width: 400px;">${entity.completeRemark}</textarea>
						</div>
					</div>
                    <div class="form-group">
                        <label  class="col-md-2 control-label">超期完成情况说明：</label>
                        <div class="form-div-content col-md-6">
                            <textarea name="noCompleteRemark" style="height:60px;font-size:14px;width: 400px;">${entity.noCompleteRemark}</textarea>
                        </div>
                    </div>
                    <div class="form-group">
                        <label  class="col-md-2 control-label">上传活动附件：</label>
                        <div class="form-div-content col-md-6">
                            <div id="queue">
                                <c:forEach items="${projectPlanAccessoryList}" var="e">
                                    <div class="uploadify-queue-item" id="select_aid_${e.id}">
                                        <div class="">
                                            <a class="select-delete" href="javascript:;" data-aid="${e.id}" data-deletefileid="${e.id}">X</a>
                                        </div>
                                        <span class="fileName">${e.title}</span>
                                        <a class="select-see" href="javascript:;" data-url="${applicationScope.commConfig['img_projectplan_url']}${e.url}" data-title="${e.title}">查看</a>
                                        <div class="uploadify-progress">
                                            <div class="uploadify-progress-bar" style="width: 100%;"></div>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                            <input id="file_upload" name="file_upload" type="file" multiple="true" />
                            <a href="javascript:$('#file_upload').uploadify('upload','*')">开始上传</a>&nbsp;
                        </div>
                    </div>
				</div>
				<div class="form-actions" style="background-color:transparent;">
					<div class="row">
						<div class="col-md-offset-1 col-md-10">
							<button class="btn btn-sm blue" type="button" id="subBtn" style="margin-right: 60px;">保  存</button>
						</div>
					</div>
				</div>
			</form>
	</div>
	<!-- END SAMPLE FORM PORTLET-->
<script src="<c:url value="/resources/admin/global/plugins/jquery.min.js" />" type="text/javascript"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/jquery-validation/js/jquery.validate.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/jquery-validation/js/additional-methods.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/layer/layer.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/laydate/laydate.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/uploadify/js/jquery.uploadify.min.js" />"></script>
<script type="text/javascript">

	$(function(){

	    $('.select-see').on('click',function () {
            parent.open($(this).data("url"),$(this).data("title"),"fullscreen=1");
        });

        $('.select-delete').on('click',function () {
            uploadify_delete2($(this));
        });

        $('#subBtn').on('click',function () {
            data_submit();
        });

        var timestamp = new Date().getTime();
        var single = $('#file_upload').uploadify({//http://www.uploadify.com/documentation/uploadify/onselecterror/
            'swf' : '<c:url value="/resources/uploadify/js/uploadify.swf" />',// 指定swf文件
            'uploader' : '<c:url value="/project/image/saveplanimgs" />;jsessionid=${pageContext.session.id}',// 后台处理的页面
            "queueID" : 'queue',// 上传文件页面中，你想要用来作为文件队列的元素的id, 默认为false  自动生成,  不带#
            'fileObjName' : 'file',//文件对象名称,用于后台获取文件对象时使用,详见下面的java代码
            'method' : 'post',// 设置上传格式
            'auto' : false,// 当选中文件后是否自动提交
            'multi' : true,// 是否支持多个文件上传
            'uploadLimit' : 20,
            'buttonText' : '选择上传附件'// 按钮显示的文字
            ,'queueSizeLimit' : 2//队列数
            ,'removeCompleted' : false//设置为false以将已完成上载的文件保留在队列中。
            ,'requeueErrors' : true//如果设置为true，则会重新确定在上传期间返回错误的文件，并重复尝试上传。
            ,'formData':{ 'cid':'${entity.compileId}','pid':'${entity.projectId}'}//向服务器上传参数
            ,'overrideEvents' : ['onDialogClose','onCancel']//过滤方法
            ,'onUploadSuccess': function (file, data, response) {// 上传成功后执行
                var data1 = JSON.parse(data);
                if (data1.state=='SUCCESS') {
                    $('#' + file.id).find('.data').html(' 上传完毕');
                    var cancel=$('#'+file.id).find("a:eq(0)");
                    var see=$('#'+file.id).find("a:eq(1)");
                    //see.prop("href",data1.url).show();
                    see.data("url",data1.url).data("title",data1.name).show();
                    if (cancel) {
                        cancel.data("deletefileid",file.id);
                        cancel.data("aid",data1.type);
                        cancel.prop("href",'javascript:;');
                        cancel.click(function () {
                            //我的处理逻辑
                            //1.首先调用ajax 传递文件名到后台,后台删除对应的文件(这个我就不写了)
                            //2.从后台返回的为true,表明删除成功;返回false,表明删除失败
                            uploadify_delete(cancel);
                        });
                        see.click(function () {
                            parent.open(see.data("url"),see.data("title"),"fullscreen=1");
                        });
                    }

                }else {
                    layer.msg("上传失败,请重新选择上传");
                    $('#file_upload').uploadify('cancel',file.id);
                }
            }
            ,'onFallback' : function() {//检测FLASH失败调用
                layer.msg("您未安装FLASH控件，无法上传图片！请安装FLASH控件后再试。");
            }
            ,'onUploadComplete' : function(file,data) {
            }
            ,'onCancel' : function(file) {
            }
            ,'onQueueComplete' : function(queueData) {//当处理队列中的所有文件时触发。
                //layer.msg(queueData.uploadsSuccessful + '文件上传成功');
            }
            ,'onUploadError' : function(file, errorCode, errorMsg, errorString) {//当文件上传但返回错误时触发。
                uploadify_onUploadError(file, errorCode, errorMsg, errorString);
            }
            ,'onSelectError' : function(file, errorCode, errorMsg) {//在选择文件时返回错误时触发。对于返回错误的每个文件触发此事件。
                uploadify_onSelectError(file, errorCode, errorMsg);
            }
        });

        var uploadify_onSelectError = function(file, errorCode, errorMsg) {
            var msgText = "上传失败\n";
            switch (errorCode) {
                case SWFUpload.QUEUE_ERROR.QUEUE_LIMIT_EXCEEDED:
                    msgText += "上传的文件数量已经超出系统限制的" + $('#file_upload').uploadify('settings', 'queueSizeLimit') + "个文件！";
                    break;
                case SWFUpload.QUEUE_ERROR.FILE_EXCEEDS_SIZE_LIMIT:
                    msgText += "文件 [" + file.name + "] 大小超出系统限制的" + $('#file_upload').uploadify('settings', 'fileSizeLimit') + "大小！";
                    break;
                case SWFUpload.QUEUE_ERROR.ZERO_BYTE_FILE:
                    msgText += "文件大小为0";
                    break;
                case SWFUpload.QUEUE_ERROR.INVALID_FILETYPE:
                    msgText += "文件格式不正确，仅限 " + this.settings.fileTypeExts;
                    break;
                default:
                    msgText += "错误代码：" + errorCode + "\n" + errorMsg;
            }
            layer.msg(msgText);
        };

        var uploadify_onUploadError = function(file, errorCode, errorMsg, errorString) {
            // 手工取消不弹出提示
            if (errorCode == SWFUpload.UPLOAD_ERROR.FILE_CANCELLED || errorCode == SWFUpload.UPLOAD_ERROR.UPLOAD_STOPPED) {
                return;
            }
            var msgText = "上传失败\n";
            switch (errorCode) {
                case SWFUpload.UPLOAD_ERROR.HTTP_ERROR:
                    msgText += "HTTP 错误\n" + errorMsg;
                    break;
                case SWFUpload.UPLOAD_ERROR.MISSING_UPLOAD_URL:
                    msgText += "上传文件丢失，请重新上传";
                    break;
                case SWFUpload.UPLOAD_ERROR.IO_ERROR:
                    msgText += "IO错误";
                    break;
                case SWFUpload.UPLOAD_ERROR.SECURITY_ERROR:
                    msgText += "安全性错误\n" + errorMsg;
                    break;
                case SWFUpload.UPLOAD_ERROR.UPLOAD_LIMIT_EXCEEDED:
                    msgText += "每次最多上传 " + this.settings.uploadLimit + "个";
                    break;
                case SWFUpload.UPLOAD_ERROR.UPLOAD_FAILED:
                    msgText += errorMsg;
                    break;
                case SWFUpload.UPLOAD_ERROR.SPECIFIED_FILE_ID_NOT_FOUND:
                    msgText += "找不到指定文件，请重新操作";
                    break;
                case SWFUpload.UPLOAD_ERROR.FILE_VALIDATION_FAILED:
                    msgText += "参数错误";
                    break;
                default:
                    msgText += "文件:" + file.name + "\n错误码:" + errorCode + "\n" + errorMsg + "\n" + errorString;
            }
            layer.msg(msgText);
        }

        var uploadify_delete = function (cancel) {
            var index = parent.layer.confirm("确定删除吗？", {icon: 3, title:'删除提示'}, function(){
                parent.layer.close(index);
                $.ajax({
                    url: '<c:url value="/project/image/delplanimgs" />',
                    type: 'POST',
                    data:{cid:'${entity.compileId}',pid:'${entity.projectId}',id:cancel.data("aid")},
                    dataType: 'json',
                    success: function(data){
                        parent.layer.closeAll('loading');
                        data = JSON.parse(data);
                        if (data.state=="SUCCESS"){
                            parent.layer.msg(data.name,{icon: 1});
                            $("#file_upload").uploadify("cancel",cancel.data("deletefileid"));//将上传队列中的文件删除.
                        } else {
                            parent.layer.msg(data.name,{icon: 2});
                        }
                    },
                    beforeSend:function () {parent.layer.load(0,{shade: [0.3,'#000']});},
                    error: function(){parent.layer.closeAll('loading');}
                });
            });
        }

        var uploadify_delete2 = function (cancel) {
            var index = parent.layer.confirm("确定删除吗？", {icon: 3, title:'删除提示'}, function(){
                parent.layer.close(index);
                $.ajax({
                    url: '<c:url value="/project/image/delplanimgs" />',
                    type: 'POST',
                    data:{cid:'${entity.compileId}',pid:'${entity.projectId}',id:cancel.data("aid")},
                    dataType: 'json',
                    success: function(data){
                        parent.layer.closeAll('loading');
                        data = JSON.parse(data);
                        if (data.state=="SUCCESS"){
                            parent.layer.msg(data.name,{icon: 1});
                            $('#select_aid_'+cancel.data("aid")).remove();
                        } else {
                            parent.layer.msg(data.name,{icon: 2});
                        }
                    },
                    beforeSend:function () {parent.layer.load(0,{shade: [0.3,'#000']});},
                    error: function(){parent.layer.closeAll('loading');}
                });
            });
        }

        var data_submit = function () {
            if ($('#id_isComplete').val()==2&&$('#id_completeDate').val()==''){
                parent.layer.msg('选择完成时，完成时间不能为空');
                return;
            }
            var index = parent.layer.confirm("确定提交吗？", {icon: 3, title:'提交提示'}, function(){
                parent.layer.close(index);
                $.ajax({
                    url:  '<c:url value="/project/plan/completeput" />',
                    type: 'POST',
                    data:$('#addForm').serialize(),
                    dataType: 'json',
                    success: function(data){
                        parent.layer.closeAll('loading');
                        data = JSON.parse(data);
                        if (data.code=="0"){
                            parent.layer.msg(data.msg,{icon: 1});
                            window.setTimeout(function () {
                                parent.location.reload(true);
                            },1000);
                        } else {
                            parent.layer.msg(data.msg,{icon: 2});
                        }
                    },
                    beforeSend:function () {parent.layer.load(0,{shade: [0.3,'#000']});},
                    error: function(){parent.layer.closeAll('loading');}
                });
            });
        }

    });

</script>


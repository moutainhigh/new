<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!--引入CSS-->
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/webuploader/css/webuploader.css" />">

<link rel="stylesheet" type="text/css" href="<c:url value="/resources/webuploader/css/single.css" />">
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/ueditor/themes/default/css/ueditor.min.css" />">

<style>
    .portlet.box.blue-hoki > .portlet-title > .actions .btn-default{
        width:27px;
        height: 27px;
    }
</style>

<!-- BEGIN SAMPLE FORM PORTLET-->
<div class="portlet box blue-hoki">
    <div class="portlet-title">
        <div class="caption">
            <i class="fa fa-gift"></i>
            招聘发布
        </div>
        <div class="actions">
            <a class="btn btn-circle btn-icon-only btn-default" href="<c:url value="/hr/hrContent/list"/>">
                <i class="icon-cloud-upload"></i>
            </a>
        </div>
    </div>
    <div class="portlet-body form">
        <form class="form-horizontal" id="editForm"  autocomplete="off">
            <input type="hidden" name="id" id="id" value="${entity.id }" />
            <input type="hidden" name="imgUrl" id="imgUrl" value="${entity.imgUrl}" />
			<div class="form-body">

            <div class="form-group">
                <label class="col-md-2 control-label">招聘类型</label>
                <div class="col-md-4">
                    <select class="form-control" name="type">
                        <option value="1" <c:if test="${entity.type ==1 }">selected</c:if>>社会招聘</option>
                        <option value="2" <c:if test="${entity.type ==2 }">selected</c:if>>校园招聘</option>
                        <option value="3" <c:if test="${entity.type ==3 }">selected</c:if>>实习生招聘</option>
                    </select>
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-2 control-label">公司</label>
                <div class="col-md-4">
                <select class="form-control" name="plateId">
                    <c:if test="${user.plateId ==1 }"><option value="1" selected>地产集团</option></c:if>
                    <c:if test="${user.plateId ==2 }"><option value="2" selected>商业集团</option></c:if>
                    <c:if test="${user.plateId ==3 }"><option value="3" selected>金控集团</option></c:if>
                    <c:if test="${user.plateId ==4 }"><option value="4" selected>建设集团</option></c:if>
                    <c:if test="${user.plateId ==5 }"><option value="5" selected>物业集团</option></c:if>
                    <c:if test="${user.plateId ==0}">
                    <c:choose>
                        <c:when test="${empty entity}">
                            <option value="0">集团总部</option>
                            <option value="6">海外公司</option>
                        </c:when>
                        <c:otherwise>
                            <c:if test="${entity.plateId ==0}"><option value="0">集团总部</option></c:if>
                            <c:if test="${entity.plateId ==6}"><option value="6">海外公司</option></c:if>
                        </c:otherwise>
                    </c:choose>
                    </c:if>

                </select>
                </div>
            </div>
            <div class="form-group">
                <label class="col-md-2 control-label">区域</label>
                <div class="col-md-4">
                    <select class="form-control" name="area">
                        <option value="1" <c:if test="${entity.area ==1 }">selected</c:if>>重庆区域</option>
                        <option value="2" <c:if test="${entity.area ==2 }">selected</c:if>>四川区域</option>
                        <option value="3" <c:if test="${entity.area ==3 }">selected</c:if>>华东区域</option>
                        <option value="4" <c:if test="${entity.area ==4 }">selected</c:if>>上海区域</option>
                        <option value="5" <c:if test="${entity.area ==5 }">selected</c:if>>长沙城市公司</option>
                        <option value="6" <c:if test="${entity.area ==6 }">selected</c:if>>沈阳城市公司</option>
                    </select>
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-2 control-label">岗位名称</label>
                <div class="col-md-4">
                    <input type="text" placeholder="岗位名称" name="title" value="${entity.title}" class="form-control"> </div>
            </div>

            <div class="form-group">
                <label class="col-md-2 control-label">工作地点</label>
                <div class="col-md-4">
                    <input type="text" placeholder="工作地点" name="addr" value="${entity.addr}" class="form-control"> </div>
            </div>

            <div class="form-group">
                <label class="col-md-2 control-label">招聘人数</label>
                <div class="col-md-4">
                    <input type="text" placeholder="招聘人数" name="count" value="${entity.count}" class="form-control"> </div>
            </div>

            <div class="form-group">
                <label class="col-md-2 control-label">排序</label>
                <div class="col-md-4">
                    <input type="text" placeholder="排序" name="sort" value="${entity.sort}"  class="form-control"> </div>
            </div>

            <div class="form-group">
                <label class="col-md-2 control-label">状态</label>
                <div class="col-md-4">
                        <div class="md-radio-inline">
                            <label><input type="radio" name="status" value="0" class="md-radiobtn" <c:if test="${entity.status ==0 || empty entity.status }">checked</c:if>>正常</label>
                            <label><input type="radio" name="status" value="1" class="md-radiobtn"  <c:if test="${entity.status ==1 }">checked</c:if>>失效</label>
                        </div>
                </div>
            </div>

            <c:if test='false'>
                <div class="form-group">
                    <label class="col-md-2 control-label">${menu.imgUrlAlias}</label>
                    <div class="col-md-4">
                        <div id="uploader-single">
                            <div style="width: 150px; height: 100px;" class="thumbnail" id="filelist">
                                <img src="${applicationScope.commConfig['img_comm_url'] }${entity.imgUrl }" style="width: 150px; height: 90px;" alt="" id="imgUrl2" name="imgUrl2" >
                            </div>
                            <div id="singleBtn">选择图片</div>
                        </div>
                    </div>
                </div>
            </c:if>

            <div class="form-group">
                <label class="control-label col-md-2">内容</label>
                <div class="form-div-content col-md-7">
                    <textarea id="allText" name="allText" style="height:300px;font-size:14px;">${entity.allText}</textarea>
                </div>
            </div>

            </div>
            
            <div class="form-actions">
	            <div class="row">
	                <div class="col-md-offset-2 col-md-10">
	                    <button class="btn green" type="submit" id="subBtn">提  交</button>
	                    <a class="btn default" href="<c:url value="/hr/hrContent/list" />">返回</a>
	                </div>
	            </div>
             </div>
            
        </form>
    </div>
</div>


<!-- END SAMPLE FORM PORTLET-->

<!--引入JS-->
<script type="text/javascript" charset="utf-8" src="<c:url value="/resources/ueditor/ueditor.config.js" />"></script>
<script type="text/javascript" charset="utf-8" src="<c:url value="/resources/ueditor/ueditor.all.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/ueditor/lang/zh-cn/zh-cn.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/jquery-validation/js/jquery.validate.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/jquery-validation/js/additional-methods.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/webuploader/js/webuploader.js" />"></script>

<script type="text/javascript">
    // 图片上传
    $(document).ready(function(){

        var html =['<p helvetica="" hiragino="" sans="" microsoft="" background-color:="" box-sizing:="" border-box="" word-wrap:="" break-word="" style="margin-top: 0px; margin-bottom: 0px; white-space: normal; padding: 0px; max-width: 100%; clear: both; min-height: 1em; color: rgb(62, 62, 62);">',
            '    <strong><span style="margin: 0px; padding: 0px; max-width: 100%; font-size: 9px; font-family: 宋体; color: rgb(51, 103, 153); box-sizing: border-box !important; word-wrap: break-word !important;">岗位职责</span></strong><span style="margin: 0px; padding: 0px; max-width: 100%; font-size: 9px; font-family: 宋体; box-sizing: border-box !important; word-wrap: break-word !important;">：</span>',
            '</p>',
            '<p helvetica="" hiragino="" sans="" microsoft="" background-color:="" box-sizing:="" border-box="" word-wrap:="" break-word="" style="margin-top: 0px; margin-bottom: 0px; white-space: normal; padding: 0px; max-width: 100%; clear: both; min-height: 1em; color: rgb(62, 62, 62);">',
            '    <br/>',
            '</p>',
            '<p helvetica="" hiragino="" sans="" microsoft="" background-color:="" box-sizing:="" border-box="" word-wrap:="" break-word="" style="margin-top: 0px; margin-bottom: 0px; white-space: normal; padding: 0px; max-width: 100%; min-height: 1em; color: rgb(62, 62, 62);">',
            '    <strong><span style="margin: 0px; padding: 0px; max-width: 100%; font-size: 9px; font-family: 宋体; color: rgb(51, 103, 153); box-sizing: border-box !important; word-wrap: break-word !important;">任职要求</span></strong><span style="margin: 0px; padding: 0px; max-width: 100%; font-size: 9px; font-family: 宋体; box-sizing: border-box !important; word-wrap: break-word !important;">：</span>',
            '</p>',
            '<p helvetica="" hiragino="" sans="" microsoft="" background-color:="" box-sizing:="" border-box="" word-wrap:="" break-word="" style="margin-top: 0px; margin-bottom: 0px; white-space: normal; padding: 0px; max-width: 100%; min-height: 1em; color: rgb(62, 62, 62);">',
            '    <span style="margin: 0px; padding: 0px; max-width: 100%; font-size: 9px; font-family: 宋体; box-sizing: border-box !important; word-wrap: break-word !important;"><br/></span>',
            '</p>'].join("");

        var hasContent = ${empty entity.allText};

        $opt={
            serverUrl:'<c:url value="/admin/image/saveEditorImg" />',
            imageActionName: "uploadimage", /* 执行上传图片的action名称 */
            imageFieldName: "upfile", /* 提交的图片表单名称 */
            imageMaxSize: 2048000, /* 上传大小限制，单位B */
            imageAllowFiles: [".png", ".jpg", ".jpeg", ".gif", ".bmp"], /* 上传图片格式显示 */
            //imageCompressEnable: true, /* 是否压缩图片,默认是true */
           // imageCompressBorder: 1600, /* 图片压缩最长边限制 */
            imageInsertAlign: "none", /* 插入的图片浮动方式 */
            imageUrlPrefix: "", /* 图片访问路径前缀 */
            imagePathFormat: ""
        };
        var editor = UE.getEditor('allText',$opt);
        if(hasContent){
            editor.ready(function() {
            editor.setContent(html);
            });
        }
        <c:if test='false'>
        // 初始化Web Uploader
        var single = WebUploader.create({
            // 自动上传。
            fileVal:'imgFile',
            // 自动上传。
            auto: true,
            compress: false,
            // swf文件路径
            swf: '<c:url value="/resources/webuploader/js/Uploader.swf" />',
            // 文件接收服务端。
            server: '<c:url value="/admin/image/saveCommImg" />',
            // 选择文件的按钮。可选。
            // 内部根据当前运行是创建，可能是input元素，也可能是flash.
            pick: {
                id: '#singleBtn',
                label: '点击选择图片'
            },
            //文件数量
            fileNumLimit: 2,
            fileSingleSizeLimit: 5 * 1024 * 1024,    // 5M
            // 只允许选择文件，可选。
            accept: {
                title: 'Images',
                extensions: 'gif,jpg,jpeg,bmp,png',
                mimeTypes: 'image/*'
            }
        });

        // 当有文件添加进来的时候
        single.on( 'fileQueued', function( file ) {
            single.makeThumb( file, function( error, src ) {
                if ( error ) {
                    $("#imgUrl2").replaceWith('<span>不能预览</span>');
                    return;
                }
                $.each(single.getFiles(),function() {
                    if(file.name!=this.name)
                        single.removeFile( this );
                });
              //  alert(file.size)
                $("#imgUrl2").attr( 'src', src );
            });
        });

        // 文件上传成功，提交表单
        single.on( 'uploadSuccess', function( file ,json) {
            $('#imgUrl').val(json.name);
        });

        // 文件上传失败，现实上传出错。
        single.on( 'uploadError', function( file ) {
            alert("图片上传失败.")
        });
        </c:if>

        $('#editForm').validate({
            errorElement : 'span',
            errorClass : 'help-block help-block-error',
            focusInvalid : false,
            ignore : "",
            rules : {
                sort : {
                    required : true,
                    digits: true
                },
                title : {
                    required : true
                },
                count : {
                    required : true,
                    digits:true
                }
            },
            messages : {
                sort : {
                    required : "必须输入排序值",
                    digits: "只能输入整数"
                },title : {
                    required : "必须输入标题"
                },count : {
                    required : "招聘人数必填",
                    digits: "只能输入整数"
                }
            },
            invalidHandler : function(event, validator) {

            },
            highlight : function(element) {
                $(element).closest('.form-group').addClass('has-error');
            },
            unhighlight : function(element) {
                $(element).closest('.form-group').removeClass('has-error');
            },
            success : function(label) {
                label.closest('.form-group').removeClass('has-error');
            },
            submitHandler : function(form) {
                if(confirm("你确定提交吗！")) {
                    $('#subBtn').attr('disabled',true);
                    var data = $(form).serialize();
                    $.post('<c:url value='/hr/hrContent/saveContent'/>', data, function (result) {
                        if (result.success){
                            window.location.href='<c:url value='/hr/hrContent/list'/>';
                        }else {
                            layer.msg(result.rmsg);
                        }
                    },'json');
                }
            }

        });

    });





</script>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!--引入CSS-->
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/webuploader/css/webuploader.css" />">

<link rel="stylesheet" type="text/css" href="<c:url value="/resources/webuploader/css/single.css" />">
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/ueditor/themes/default/css/ueditor.min.css" />">

<!-- BEGIN SAMPLE FORM PORTLET-->
<div class="portlet box green">
    <div class="portlet-title">
        <div class="caption">
        	<i class="icon-settings"></i>
        	
        	${menu.name}
        	<c:if test="${entity.title!=null }">
				<span><i class="fa fa-angle-right"></i>
				${entity.title}</span>
			</c:if>
        </div>
        <div class="actions">
			<a class="btn btn-circle btn-icon-only btn-default" href="<c:url value="/admin/cms/content/get?mid=${menu.mid }" />">
				<i class="icon-cloud-upload"></i>
			</a>
    	</div>
    </div>
    <div class="portlet-body form">
        <form class="form-horizontal" id="editForm" method="post" action="<c:url value='/admin/cms/content/post'/>" autocomplete="off">
            <input type="hidden" name="mid" id="mid" value="${menu.mid }" />
            <input type="hidden" name="cid" id="cid" value="${entity.cid }" />
            <input type="hidden" name="imgUrl" id="imgUrl" value="${entity.imgUrl}" />
			<div class="form-body">
            <c:if test='${menu.isTitleShow>0}'>
            <div class="form-group">
                <label class="col-md-1 control-label">${menu.titleAlias}</label>
                <div class="col-md-4">
                    <input type="text" placeholder="${menu.titleAlias}" name="title" value="${entity.title}" class="form-control"> </div>
            </div>
            </c:if>

            <c:if test='${menu.isShortTextShow>0}'>
                <div class="form-group">
                    <label class="col-md-1 control-label" for="shortText">${menu.shortTextAlias}</label>
                    <div class="col-md-4">
                        <input type="text" placeholder="${menu.shortTextAlias}" name="shortText"  value="${entity.shortText}"  class="form-control"> </div>
                </div>
            </c:if>

            <c:if test='${menu.isSortShow>0}'>
                <div class="form-group">
                    <label class="col-md-1 control-label" for="sort">${menu.sortAlias}</label>
                    <div class="col-md-4">
                        <input type="text" placeholder="${menu.sortAlias}" name="sort" value="${entity.sort}"  class="form-control"> </div>
                </div>
            </c:if>

            <c:if test='${menu.isF1show>0}'>
                <div class="form-group">
                    <label class="col-md-1 control-label" for="f1">${menu.f1alias}</label>
                    <div class="col-md-4">
                        <input type="text" placeholder="${menu.f1alias}" name="f1" value="${entity.f1}" class="form-control"> </div>
                </div>
            </c:if>

            <c:if test='${menu.isF2show>0}'>
                <div class="form-group">
                    <label class="col-md-1 control-label" for="f2">${menu.f2alias}</label>
                    <div class="col-md-4">
                        <input type="text" placeholder="${menu.f2alias}" name="f2"  value="${entity.f2}" class="form-control"> </div>
                </div>
            </c:if>

            <c:if test='${menu.isF3show>0}'>
                <div class="form-group">
                    <label class="col-md-1 control-label" for="f3">${menu.f3alias}</label>
                    <div class="col-md-4">
                        <input type="text" placeholder="${menu.f3alias}" name="f3" value="${entity.f3}" class="form-control"> </div>
                </div>
            </c:if>

            <c:if test='${menu.isImgUrlShow>0}'>
                <div class="form-group">
                    <label class="col-md-1 control-label" for="imgUrl">${menu.imgUrlAlias}</label>
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

            <c:if test='${menu.isAllTextShow>0}'>
                <div class="form-group">
                    <label class="control-label col-md-1"  for="imgUrl">${menu.allTextAlias}</label>
                    <div class="form-div-content col-md-7">
                        <textarea id="allText" name="allText" style="height:300px;font-size:14px;">${entity.allText}</textarea>
                    </div>
                </div>
            </c:if>
            </div>
            
            <div class="form-actions">
	            <div class="row">
	                <div class="col-md-offset-1 col-md-10">
	                    <button class="btn btn-lg blue" type="submit" id="subBtn">提  交</button>
	                    <a class="btn btn-lg grey-salsa" href="<c:url value="/admin/cms/content/get?mid=${menu.mid }" />">返回</a>
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

        handleValidation();
		<c:if test='${menu.isAllTextShow>0}'>
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
			var $js_loan_windInfo = UE.getEditor('allText',$opt);
        </c:if>
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
        
    });


    // basic validation
    var handleValidation = function() {

        var form1 = $('#editForm');
        var error1 = $('.alert-danger', form1);
        var success1 = $('.alert-success', form1);

        form1.validate({
            errorElement : 'span', //default input error message container
            errorClass : 'help-block help-block-error', // default input error message class
            focusInvalid : false, // do not focus the last invalid input
            ignore : "", // validate all fields including form hidden input
            rules : {
            	<c:if test='${menu.isSortShow>0}'>
            	sort : {
                    required : true,
                    digits: true
              },
            	</c:if>
                title : {
                    required : true
              }
            },
			messages : {
				<c:if test='${menu.isSortShow>0}'>
            	sort : {
                    required : "必须输入序号",
                    digits: "只能输入整数"
              	},
            	</c:if>
                title : {
                    required : "必须输入标题"
              	}
			},
            invalidHandler : function(event, validator) { //display error alert on form submit
                success1.hide();
                error1.show();
            },

            highlight : function(element) { // hightlight error inputs
                $(element).closest('.form-group').addClass('has-error'); // set error class to the control group
            },

            unhighlight : function(element) { // revert the change done by hightlight
                $(element).closest('.form-group').removeClass('has-error'); // set error class to the control group
            },

            success : function(label) {
                label.closest('.form-group').removeClass('has-error'); // set success class to the control group
            },

            submitHandler : function(form) {
                success1.show();
                error1.hide();
                if(confirm("你确定提交吗！")) {
                    if($("#cid").val()!="") {
                        form1.attr('action','<c:url value='/admin/cms/content/put'/>');
                    }
                    form.submit();
                    $('#subBtn').attr('disabled',true);
                }
            }
        });
    }

</script>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!--引入CSS-->
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/webuploader/css/webuploader.css" />">

<link rel="stylesheet" type="text/css" href="<c:url value="/resources/webuploader/css/single.css" />">
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/webuploader/css/style.css" />">

<!-- BEGIN SAMPLE FORM PORTLET-->
<div class="portlet light ">
    <div class="portlet-title">
        <div class="caption font-green">
            <i class="icon-settings font-green"></i>
            <span class="caption-subject bold"><a class="font-green" href="<c:url value="/admin/cms/content/list?mid=${menu.mid }" />">${menu.name}</a></span>
            <c:if test="${entity.title!=null }">
                <span class="caption-subject font-red-sunglo bold">(${entity.title})</span>
            </c:if>
        </div>
        <div class="actions">
            <div class="btn-group">
                <a class="btn btn-sm green dropdown-toggle" href="javascript:;" data-toggle="dropdown"> Actions
                    <i class="fa fa-angle-down"></i>
                </a>
                <ul class="dropdown-menu pull-right">
                    <li>
                        <a href="javascript:;">
                            <i class="fa fa-pencil"></i> Edit </a>
                    </li>
                    <li>
                        <a href="javascript:;">
                            <i class="fa fa-trash-o"></i> Delete </a>
                    </li>
                    <li>
                        <a href="javascript:;">
                            <i class="fa fa-ban"></i> Ban </a>
                    </li>
                    <li class="divider"> </li>
                    <li>
                        <a href="javascript:;"> Make admin </a>
                    </li>
                </ul>
            </div>
        </div>
    </div>
    <div class="portlet-body form">
        <form role="form" class="form-horizontal">
            <input type="hidden" name="mid" id="mid" value="${menu.mid }" />
            <input type="hidden" name="cid" id="cid" value="${entity.cid }" />
            <c:if test='${menu.isTitleShow>0}'>
            <div class="form-group">
                <label class="col-md-2 control-label" for="title">${menu.titleAlias}</label>
                <div class="col-md-4">
                    <input type="text" placeholder="${menu.titleAlias}" name="title" class="form-control"> </div>
            </div>
            </c:if>
            <c:if test='${menu.isShortTextShow>0}'>
                <div class="form-group">
                    <label class="col-md-2 control-label" for="shortText">${menu.shortTextAlias}</label>
                    <div class="col-md-4">
                        <input type="text" placeholder="${menu.shortTextAlias}" name="shortText" class="form-control"> </div>
                </div>
            </c:if>
            <c:if test='${menu.isSortShow>0}'>
                <div class="form-group">
                    <label class="col-md-2 control-label" for="sort">${menu.sortAlias}</label>
                    <div class="col-md-4">
                        <input type="text" placeholder="${menu.sortAlias}" name="sort" class="form-control"> </div>
                </div>
            </c:if>
            <c:if test='${menu.isF1show>0}'>
                <div class="form-group">
                    <label class="col-md-2 control-label" for="f1">${menu.f1Alias}</label>
                    <div class="col-md-4">
                        <input type="text" placeholder="${menu.f1Alias}" name="f1" class="form-control"> </div>
                </div>
            </c:if>
            <c:if test='${menu.isF2show>0}'>
                <div class="form-group">
                    <label class="col-md-2 control-label" for="f2">${menu.f2Alias}</label>
                    <div class="col-md-4">
                        <input type="text" placeholder="${menu.f2Alias}" name="f2" class="form-control"> </div>
                </div>
            </c:if>
            <c:if test='${menu.isF3show>0}'>
                <div class="form-group">
                    <label class="col-md-2 control-label" for="f3">${menu.f3Alias}</label>
                    <div class="col-md-4">
                        <input type="text" placeholder="${menu.f3Alias}" name="f3" class="form-control"> </div>
                </div>
            </c:if>




                <div class="form-group">

                    <label class="col-md-2 control-label" for="imgUrl">${menu.imgUrlAlias}</label>
                    <div class="col-md-8">

                        <div id="uploader-single">
                            <div style="width: 200px; height: 150px;" class="thumbnail" id="filelist">
                                <img src="${applicationScope.commConfig['img_comm_url'] }${entity.imgUrl }" style="width: 200px; height: 140px;" alt="" id="imgUrl2" name="imgUrl2" >
                            </div>
                            <div id="filePicker">选择图片2</div>
                        </div>

                    </div>
                </div>


            <div class="form-group">
                <div class="col-md-offset-2 col-md-10">
                    <button class="btn blue" type="submit" id="subBtn">提  交</button>
                </div>
            </div>
        </form>
    </div>
</div>
<!-- END SAMPLE FORM PORTLET-->

<!--引入JS-->
<script src="<c:url value="/resources/admin/global/plugins/jquery.min.js" />" type="text/javascript"></script>
<script type="text/javascript" src="<c:url value="/resources/webuploader/js/webuploader.js" />"></script>
<script>
// 图片上传demo
jQuery(function() {
    var $ = jQuery,
    $list = $('#filelist'),
    // 优化retina, 在retina下这个值是2
    ratio = window.devicePixelRatio || 1,
    // 缩略图大小
    thumbnailWidth = 200,
    thumbnailHeight = 150,
    // Web Uploader实例
    uploader;
    // 初始化Web Uploader
    uploader = WebUploader.create({
    // 自动上传。
    auto: false,
    // swf文件路径
    swf: '<c:url value="/resources/webuploader/js/Uploader.swf" />',
    // 文件接收服务端。
    server: 'http://webuploader.duapp.com/server/fileupload.php',
    // 选择文件的按钮。可选。
    // 内部根据当前运行是创建，可能是input元素，也可能是flash.
    pick: '#filePicker',

    // 只允许选择文件，可选。
    accept: {
        title: 'Images',
        extensions: 'gif,jpg,jpeg,bmp,png',
        mimeTypes: 'image/*'
        }
    });

    // 当有文件添加进来的时候
    uploader.on( 'fileQueued', function( file ) {
        uploader.makeThumb( file, function( error, src ) {
            if ( error ) {
                $("#imgUrl2").replaceWith('<span>不能预览</span>');
                return;
            }
            var $btns = $('<div class="file-panel">' +
                    '<span class="cancel">删除</span>' +
                    '<span class="rotateRight">向右旋转</span>' +
                    '<span class="rotateLeft">向左旋转</span></div>').appendTo( $("#filelist") );
            $("#filePicker").next().addClass( 'disabled' );
            $("#imgUrl2").attr( 'src', src );
        });
    });

    // 文件上传过程中创建进度条实时显示。
    uploader.on( 'uploadProgress', function( file, percentage ) {
    var $li = $( '#'+file.id ),
    $percent = $li.find('.progress span');

    // 避免重复创建
    if ( !$percent.length ) {
    $percent = $('<p class="progress"><span></span></p>')
    .appendTo( $li )
    .find('span');
    }

    $percent.css( 'width', percentage * 100 + '%' );
    });

    // 文件上传成功，给item添加成功class, 用样式标记上传成功。
    uploader.on( 'uploadSuccess', function( file ) {
    $( '#'+file.id ).addClass('upload-state-done');
    });

    // 文件上传失败，现实上传出错。
    uploader.on( 'uploadError', function( file ) {
    var $li = $( '#'+file.id ),
    $error = $li.find('div.error');

    // 避免重复创建
    if ( !$error.length ) {
    $error = $('<div class="error"></div>').appendTo( $li );
    }

    $error.text('上传失败');
    });

// 完成上传完了，成功或者失败，先删除进度条。
    uploader.on( 'uploadComplete', function( file ) {
    $( '#'+file.id ).find('.progress').remove();
    });

    // 当有文件添加进来时执行，负责view的创建
    function addFile( file ) {
        var $li = $( '<div id="' + file.id + '">' +
                        '<p class="title">' + file.name + '</p>' +
                        '<p class="imgWrap"></p>'+
                        '<p class="progress"><span></span></p>' +
                        '</div>' ),

                $btns = $('<div class="file-panel">' +
                        '<span class="rotateRight">向右旋转</span>' +
                        '<span class="rotateLeft">向左旋转</span></div>').appendTo( $li ),
                $prgress = $li.find('p.progress span'),
                $wrap = $li.find( 'p.imgWrap' ),
                $info = $('<p class="error"></p>'),

                showError = function( code ) {
                    switch( code ) {
                        case 'exceed_size':
                            text = '文件大小超出';
                            break;

                        case 'interrupt':
                            text = '上传暂停';
                            break;

                        default:
                            text = '上传失败，请重试';
                            break;
                    }

                    $info.text( text ).appendTo( $li );
                };

        if ( file.getStatus() === 'invalid' ) {
            showError( file.statusText );
        } else {
            // @todo lazyload
            $wrap.text( '预览中' );
            uploader.makeThumb( file, function( error, src ) {
                if ( error ) {
                    $wrap.text( '不能预览' );
                    return;
                }

                var img = $('<img src="'+src+'">');
                $wrap.empty().append( img );
            }, thumbnailWidth, thumbnailHeight );

            percentages[ file.id ] = [ file.size, 0 ];
            file.rotation = 0;
        }

        file.on('statuschange', function( cur, prev ) {
            if ( prev === 'progress' ) {
                $prgress.hide().width(0);
            } else if ( prev === 'queued' ) {
                $li.off( 'mouseenter mouseleave' );
                $btns.remove();
            }

            // 成功
            if ( cur === 'error' || cur === 'invalid' ) {
                console.log( file.statusText );
                showError( file.statusText );
                percentages[ file.id ][ 1 ] = 1;
            } else if ( cur === 'interrupt' ) {
                showError( 'interrupt' );
            } else if ( cur === 'queued' ) {
                percentages[ file.id ][ 1 ] = 0;
            } else if ( cur === 'progress' ) {
                $info.remove();
                $prgress.css('display', 'block');
            } else if ( cur === 'complete' ) {
                $li.append( '<span class="success"></span>' );
            }

            $li.removeClass( 'state-' + prev ).addClass( 'state-' + cur );
        });

        $li.on( 'mouseenter', function() {
            $btns.stop().animate({height: 30});
        });

        $li.on( 'mouseleave', function() {
            $btns.stop().animate({height: 0});
        });

        $btns.on( 'click', 'span', function() {
            var index = $(this).index(),
                    deg;

            switch ( index ) {
                case 0:
                    uploader.removeFile( file );
                    return;

                case 1:
                    file.rotation += 90;
                    break;

                case 2:
                    file.rotation -= 90;
                    break;
            }

            if ( supportTransition ) {
                deg = 'rotate(' + file.rotation + 'deg)';
                $wrap.css({
                    '-webkit-transform': deg,
                    '-mos-transform': deg,
                    '-o-transform': deg,
                    'transform': deg
                });
            } else {
                $wrap.css( 'filter', 'progid:DXImageTransform.Microsoft.BasicImage(rotation='+ (~~((file.rotation/90)%4 + 4)%4) +')');
                // use jquery animate to rotation
                // $({
                //     rotation: rotation
                // }).animate({
                //     rotation: file.rotation
                // }, {
                //     easing: 'linear',
                //     step: function( now ) {
                //         now = now * Math.PI / 180;

                //         var cos = Math.cos( now ),
                //             sin = Math.sin( now );

                //         $wrap.css( 'filter', "progid:DXImageTransform.Microsoft.Matrix(M11=" + cos + ",M12=" + (-sin) + ",M21=" + sin + ",M22=" + cos + ",SizingMethod='auto expand')");
                //     }
                // });
            }


        });

        $li.appendTo( $("#filelist"));
    }
});
</script>
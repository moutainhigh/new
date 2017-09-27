<%@page language="java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!--引入CSS-->
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/webuploader/css/webuploader.css" />">
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/webuploader/css/demox.css" />">
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

                        <div class="wu-example" id="uploader">
                            <div class="queueList">
                                <div class="placeholder" id="dndArea">
                                    <div id="filePicker" class="webuploader-container"><div class="webuploader-pick">点击选择图片</div><div id="rt_rt_1agui2lj4k2k1fop7ksmgh1vsl1" style="position: absolute; top: 0px; left: 448px; width: 168px; height: 44px; overflow: hidden; bottom: auto; right: auto;"><input type="file" name="file" class="webuploader-element-invisible" multiple="multiple" accept="image/*"><label style="opacity: 0; width: 100%; height: 100%; display: block; cursor: pointer; background: rgb(255, 255, 255) none repeat scroll 0% 0%;"></label></div></div>
                                    <p>或将照片拖到这里，单次最多可选300张</p>
                                </div>
                                <ul class="filelist"></ul><ul class="filelist"></ul></div>
                            <div style="display:none;" class="statusBar">
                                <div class="progress" style="display: none;">
                                    <span class="text">0%</span>
                                    <span class="percentage" style="width: 0%;"></span>
                                </div><div class="info">共0张（0B），已上传0张</div>
                                <div class="btns">
                                    <div id="filePicker2" class="webuploader-container"><div class="webuploader-pick">继续添加</div><div id="rt_rt_1agui2ljfc2t11aou12gge1j96" style="position: absolute; top: 0px; left: 0px; width: 38px; height: 2px; overflow: hidden; bottom: auto; right: auto;"><input type="file" name="file" class="webuploader-element-invisible" multiple="multiple" accept="image/*"><label style="opacity: 0; width: 100%; height: 100%; display: block; cursor: pointer; background: rgb(255, 255, 255) none repeat scroll 0% 0%;"></label></div></div><div class="uploadBtn state-pedding">开始上传</div>
                                </div>
                            </div>
                        </div>

                    </div>
                </div>

            <div class="form-group">
                <div class="col-md-offset-2 col-md-10">
                    <button class="btn blue" type="submit">Sign in</button>
                </div>
            </div>
        </form>
    </div>
</div>
<!-- END SAMPLE FORM PORTLET-->

<!--引入JS-->
<script src="<c:url value="/resources/admin/global/plugins/jquery.min.js" />" type="text/javascript"></script>
<script type="text/javascript" src="<c:url value="/resources/webuploader/js/webuploader.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/webuploader/js/demox.js" />"></script>
<script type="text/javascript">
        webups.init("<c:url value="/admin/image/saveCommImg" />",1); // init metronic core components
</script>
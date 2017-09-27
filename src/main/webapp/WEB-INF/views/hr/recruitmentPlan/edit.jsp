<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<link rel="stylesheet" type="text/css" href="<c:url value="/resources/admin/global/plugins/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" />" media="screen"/>

<div class="portlet box blue-hoki">
    <div class="portlet-title">
        <div class="caption">
            <i class="fa fa-gift"></i>
            招聘计划
        </div>
    </div>
    <div class="portlet-body">
        <div class="row">
            <div class="col-md-11">
                <form  role="form" class="js_base_form" id="materialAddForm">
                    <div class="form-body">
                        <div class="row">
                            <div class="col-md-3 ">
                                <div class="form-group">
                                    <label>职位名称：</label>
                                    <input type="text" class="form-control" name="jobName" value="${entity.jobName}">
                                </div>
                            </div>

                            <div class="col-md-3 ">
                                <div class="form-group">
                                    <label>单位：</label>
                                    <div class="input-group">
                                        <input type="hidden" class="form-control" name="plat" value="${entity.plat}">
                                        <input type="hidden" class="form-control" name="companyId" value="${entity.companyId}">
                                        <input type="text" class="form-control" value="${entity.companyName}" name="companyName" readonly>
                                        <span class="input-group-btn">
                                            <button type="button" class="btn btn-primary js_choose_company">请选择</button>
                                        </span>
                                    </div>
                                </div>
                            </div>

                            <div class="col-md-3 ">
                                <div class="form-group">
                                    <label>部门：</label>
                                    <input type="text" class="form-control" name="departmentName" value="${entity.departmentName}">
                                </div>
                            </div>

                            <div class="col-md-3 ">
                                <div class="form-group">
                                    <label>职级：</label>
                                    <input type="hidden" class="form-control" name="dutyLevelName" value="${entity.dutyLevelName}">
                                    <select name="dutyLevel" class="form-control">
                                        <option value="">请选择</option>
                                        <c:forEach var="d" items="${dutyLevel}">
                                            <option value="${d.dictDataValue}" <c:if test="${entity.dutyLevel eq d.dictDataValue}">selected</c:if>>${d.dictDataKey}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-3 ">
                                <div class="form-group">
                                    <label>空缺人数：</label>
                                    <input type="text" class="form-control" name="vacancyCount" value="${entity.vacancyCount}">
                                </div>
                            </div>

                            <div class="col-md-3 ">
                                <div class="form-group">
                                    <label>申请招聘日期：</label>
                                    <input type="text" class="form-control js_date" name="askDate" value="<fmt:formatDate value="${entity.askDate}" pattern="yyyy-MM-dd"/>">
                                </div>
                            </div>

                            <div class="col-md-3 ">
                                <div class="form-group">
                                    <label>拟完成日期：</label>
                                    <input type="text" class="form-control js_date" name="overDate" value="<fmt:formatDate value="${entity.overDate}" pattern="yyyy-MM-dd"/>">
                                </div>
                            </div>

                            <div class="col-md-3 ">
                                <div class="form-group">
                                    <label>填补人数：</label>
                                    <input type="text" class="form-control" name="fileCount" value="${entity.fileCount}">
                                </div>
                            </div>
                        </div>

                        <div class="row">

                            <div class="col-md-3 ">
                                <div class="form-group">
                                    <label>用人部门需求：</label>
                                    <input type="text" class="form-control" name="requirement" value="${entity.requirement}">
                                </div>
                            </div>

                            <div class="col-md-3 ">
                                <div class="form-group">
                                    <label>目前进展情况：</label>
                                    <input type="text" class="form-control" name="progress" value="${entity.progress}">
                                </div>
                            </div>

                            <div class="col-md-3 ">
                                <div class="form-group">
                                    <label>填补人员姓名：</label>
                                    <input type="text" class="form-control" name="fileName" value="${entity.fileName}">
                                </div>
                            </div>

                            <div class="col-md-3 ">
                                <div class="form-group">
                                    <label>尚空缺人数：</label>
                                    <input type="text" class="form-control" name="vacancyCount2" value="${entity.vacancyCount2}" readonly>
                                </div>
                            </div>
                        </div>
                        <div class="row">

                            <div class="col-md-3 ">
                                <div class="form-group">
                                    <label>拟入职日期：</label>
                                    <input type="text" class="form-control js_date" name="planJoinDate" value="<fmt:formatDate value="${entity.planJoinDate}" pattern="yyyy-MM-dd"/>">
                                </div>
                            </div>

                            <div class="col-md-3 ">
                                <div class="form-group">
                                    <label>已入职日期：</label>
                                    <input type="text" class="form-control js_date" name="overJoinDate" value="<fmt:formatDate value="${entity.overJoinDate}" pattern="yyyy-MM-dd"/>">
                                </div>
                            </div>

                            <div class="col-md-3 ">
                                <div class="form-group">
                                    <label>确定年度薪酬（元）：</label>
                                    <input type="text" class="form-control" name="payment" value="${entity.payment}">
                                </div>
                            </div>

                            <div class="col-md-3 ">
                                <div class="form-group">
                                    <label>直接汇报上级岗位名称：</label>
                                    <input type="text" class="form-control" name="reportJobName" value="${entity.reportJobName}">
                                </div>
                            </div>
                        </div>

                    <button type="button" class="btn green js_save_chg" data-action="save">保存</button>
                    <a type="button" class="btn default" href="<c:url value='/hr/recruitmentPlan/index'/>">返回列表</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<div class="modal fade js_tree_div">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title">单位/部门</h4>
            </div>
            <div class="modal-body">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-md-6">
                            <label>单位</label>
                            <ul id="companyTree" class="ztree"></ul></div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default js_cancel_btn" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary js_save_btn">保存</button>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/jquery-validation/js/jquery.validate.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/jquery-validation/js/additional-methods.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/jquery-validation/js/messages_zh.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.zh-CN.js" />"></script>


<script>

    $(function () {

        var companyTreeObj;

        var initTree = function (_arr,treeId,callback) {
            var setting = {callback:{
                onClick:function (event, treeId, treeNode) {
                    if ("function" == typeof(callback)){
                        callback(event,treeId,treeNode);
                    }
                }
            },data:{
                key:{
                    children:"childList"
                }
            },view:{
                expandSpeed:''
            }};
            return $.fn.zTree.init($(treeId), setting, _arr);
        }

        var getCompanies = function () {
            $.post('<c:url value="/hr/org/getAllCompanies"/>',{ignore:true}, function (result) {
                if (result.success) {
                    companyTreeObj = initTree(result.rdata, "#companyTree", function (event, treeId, treeNode) {

                    });
                    var nodes = companyTreeObj.getNodes();
                    companyTreeObj.expandNode(nodes[0],true,false,false);
                }
            }, 'json');
        }

        /**
         * 选择公司
         */
        $('button.js_choose_company').on('click',function () {
            getCompanies();
            $('div.js_tree_div').modal('show');
        });

        /**
         * 树 保存
         */
        $('div.js_tree_div').on('click','button.js_save_btn',function () {
            var nodes = companyTreeObj.getSelectedNodes();
            if(nodes.length==1){
                var node = nodes[0];
                $('input[name=plat]').val(node.plate);
                $('input[name=companyId]').val(node.id).next().val(node.name);
                $('div.js_tree_div').modal('hide');
            }else {
                layer.msg('请选择单位');
            }
        });

        $('.js_date').datetimepicker({
            format:"yyyy-mm-dd",
            language:  'zh-CN',
            weekStart: 1,
            todayBtn:  1,
            autoclose: 1,
            todayHighlight: 1,
            startView: 2,
            minView: 2,
            forceParse: 0
        });

        $('select[name=dutyLevel]').on('change',function () {
            $(this).prev().val($(this).find('option:selected').text());

        });

        var dataObj={id:'${entity.id}'};

        var validateForm = $('form.js_base_form');
        validateForm.validate({
            errorElement : 'span',
            errorClass : 'help-block help-block-error',
            rules : {
                jobName : {
                    required : true
                },companyName : {
                    required : true
                },departmentName: {
                    required : true
                },dutyLevel: {
                    required : true
                },vacancyCount: {
                    required : true,
                    digits:true
                },fileCount: {
                    required : true,
                    digits:true
                }
            },
            highlight : function(element) {
                $(element).parents('div.form-group').addClass('has-error');
            },
            unhighlight : function(element) {
                $(element).parents('div.form-group').removeClass('has-error');
            },
            success : function(label) {
                label.remove();
            },errorPlacement: function(error, element) {
                element.parents('div.form-group').append(error);
            }
        });


        var _index=null;
        $('button.js_save_chg').on('click',function () {
            if(validateForm.valid()){
                var objArr = $('form.js_base_form').serializeArray();
                for(var e in objArr){
                    var  obj = objArr[e];
                    dataObj[obj.name] = obj.value;
                }
                var url = null;
                if(dataObj.id == ''){
                    url = '<c:url value="/hr/recruitmentPlan/insertPlan"/>';
                }else{
                    url = '<c:url value="/hr/recruitmentPlan/updatePlan"/>';
                }

                layer.confirm("确定提交？",function (c) {
                    layer.close(c);
                    _index = layer.load(2);
                    $.post(url, dataObj, function (result) {
                        layer.close(_index);
                        if (result.success) {
                            layer.msg('操作成功',{time:800},function () {
                                window.location.href='<c:url value="/hr/recruitmentPlan/index"/>';
                            });
                        }else{
                            layer.msg('操作失败：'+result.rmsg,{time:2000});
                        }
                    }, 'json');
                });
            }
        });

        $('input[name=vacancyCount]').on('blur',function () {
            var _fileCount =$('input[name=fileCount]').val();
            var _value = $(this).val();
            if (!isNaN(parseInt(_fileCount)) && !isNaN(parseInt(_value))){
                $('input[name=vacancyCount2]').val(parseInt(_value)-parseInt(_fileCount))
            }
        });

        $('input[name=fileCount]').on('blur',function () {
            var _vacancyCount =$('input[name=vacancyCount]').val();
            var _value = $(this).val();
            if (!isNaN(parseInt(_vacancyCount)) && !isNaN(parseInt(_value))){
                $('input[name=vacancyCount2]').val(parseInt(_vacancyCount)-parseInt(_value))
            }
        });

    });

</script>


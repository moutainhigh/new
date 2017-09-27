<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<link rel="stylesheet" type="text/css" href="<c:url value="/resources/admin/global/plugins/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" />" media="screen"/>

<div class="portlet box blue-hoki">
    <div class="portlet-title">
        <div class="caption">
            <i class="fa fa-gift"></i>
            招聘岗位进程
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
                                    <label>板块：</label>
                                    <input type="hidden" name="plat" value="${entity.plat}"/>
                                    <select name="plat" class="form-control">
                                        <option value="">请选择板块</option>
                                    </select>
                                </div>
                            </div>

                            <div class="col-md-3 ">
                                <div class="form-group">
                                    <label>单位：</label>
                                    <div class="input-group">
                                        <input type="hidden" class="form-control" name="companyId" value="${entity.companyId}">
                                        <input type="text" class="form-control" value="${entity.company}" name="company" readonly>
                                        <span class="input-group-btn">
                                            <button type="button" class="btn btn-primary js_choose_company">请选择</button>
                                        </span>
                                    </div>
                                </div>
                            </div>

                            <div class="col-md-3 ">
                                <div class="form-group">
                                    <label>部门：</label>
                                    <input type="text" class="form-control" name="department" value="${entity.department}">
                                </div>
                            </div>

                        </div>
                        <div class="row">

                            <div class="col-md-3 ">
                                <div class="form-group">
                                    <label>职位名称：</label>
                                    <input type="text" class="form-control" name="position" value="${entity.position}">
                                </div>
                            </div>

                            <div class="col-md-3 ">
                                <div class="form-group">
                                    <label>姓名：</label>
                                    <input type="text" class="form-control" name="name" value="${entity.name}">
                                </div>
                            </div>

                            <div class="col-md-3 ">
                                <div class="form-group">
                                    <label>联系电话：</label>
                                    <input type="text" class="form-control" name="telephone" value="${entity.telephone}">
                                </div>
                            </div>
                            <div class="col-md-3 ">
                                <div class="form-group">
                                    <label>招聘人数：</label>
                                    <input type="text" class="form-control" name="numberOfHiring" value="${entity.numberOfHiring}">
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-3 ">
                                <div class="form-group">
                                    <label>优先级：</label>
                                    <input type="text" class="form-control" name="level" value="${entity.level}">
                                </div>
                            </div>

                            <div class="col-md-3 ">
                                <div class="form-group">
                                    <label>预计到岗日期：</label>
                                    <input type="text" class="form-control js_date" name="planOnDutyDate" value="<fmt:formatDate value="${entity.planOnDutyDate}" pattern="yyyy-MM-dd"/>">
                                </div>
                            </div>

                            <div class="col-md-3 ">
                                <div class="form-group">
                                    <label>招聘需求提出：</label>
                                    <input type="text" class="form-control js_date" name="hireDate" value="<fmt:formatDate value="${entity.hireDate}" pattern="yyyy-MM-dd"/>">
                                </div>
                            </div>

                            <div class="col-md-3 ">
                                <div class="form-group">
                                    <label>简历筛选（提交份数）：</label>
                                    <input type="text" class="form-control" name="numberOfResumes" value="${entity.numberOfResumes}">
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-3 ">
                                <div class="form-group">
                                    <label>简历提交时间：</label>
                                    <input type="text" class="form-control js_date" name="receiveResumeDate" value="<fmt:formatDate value="${entity.receiveResumeDate}" pattern="yyyy-MM-dd"/>">
                                </div>
                            </div>

                            <div class="col-md-3 ">
                                <div class="form-group">
                                    <label>简历确认份数：</label>
                                    <input type="text" class="form-control" name="resumePassedNumber" value="${entity.resumePassedNumber}">
                                </div>
                            </div>

                            <div class="col-md-3 ">
                                <div class="form-group">
                                    <label>简历确认时间：</label>
                                    <input type="text" class="form-control js_date" name="resumePassedDate" value="<fmt:formatDate value="${entity.resumePassedDate}" pattern="yyyy-MM-dd"/>">
                                </div>
                            </div>

                            <div class="col-md-3 ">
                                <div class="form-group">
                                    <label>电话通知人数：</label>
                                    <input type="text" class="form-control" name="personNumByCall" value="${entity.personNumByCall}">
                                </div>
                            </div>
                        </div>
                        <div class="row">

                            <div class="col-md-3 ">
                                <div class="form-group">
                                    <label>初试时间：</label>
                                    <input type="text" class="form-control js_date" name="firstAuditionDate" value="<fmt:formatDate value="${entity.firstAuditionDate}" pattern="yyyy-MM-dd"/>">
                                </div>
                            </div>

                            <div class="col-md-3 ">
                                <div class="form-group">
                                    <label>初试通过人员：</label>
                                    <input type="text" class="form-control" name="firstPassedPerson" value="${entity.firstPassedPerson}">
                                </div>
                            </div>

                            <div class="col-md-3 ">
                                <div class="form-group">
                                    <label>复试人数：</label>
                                    <input type="text" class="form-control" name="secondAuditonNumber" value="${entity.secondAuditonNumber}">
                                </div>
                            </div>

                            <div class="col-md-3 ">
                                <div class="form-group">
                                    <label>复试时间：</label>
                                    <input type="text" class="form-control js_date" name="secondAuditionDate" value="<fmt:formatDate value="${entity.secondAuditionDate}" pattern="yyyy-MM-dd"/>">
                                </div>
                            </div>
                        </div>

                        <div class="row">

                            <div class="col-md-3 ">
                                <div class="form-group">
                                    <label>复试通过人员：</label>
                                    <input type="text" class="form-control" name="secondPassedPerson" value="${entity.secondPassedPerson}">
                                </div>
                            </div>

                            <div class="col-md-3 ">
                                <div class="form-group">
                                    <label>终试人数：</label>
                                    <input type="text" class="form-control" name="finalAuditionNumber" value="${entity.finalAuditionNumber}">
                                </div>
                            </div>
                            <div class="col-md-3 ">
                                <div class="form-group">
                                    <label>终试时间：</label>
                                    <input type="text" class="form-control js_date" name="finalAuditionDate" value="<fmt:formatDate value="${entity.finalAuditionDate}" pattern="yyyy-MM-dd"/>">
                                </div>
                            </div>

                            <div class="col-md-3 ">
                                <div class="form-group">
                                    <label>拟录用人员姓名：</label>
                                    <input type="text" class="form-control" name="PersonNameToBeHired" value="${entity.personNameToBeHired}">
                                </div>
                            </div>
                        </div>


                        <div class="row">


                            <div class="col-md-3 ">
                                <div class="form-group">
                                    <label>拟到岗时间：</label>
                                    <input type="text" class="form-control js_date" name="planComePositionDate" value="<fmt:formatDate value="${entity.planComePositionDate}" pattern="yyyy-MM-dd"/>">
                                </div>
                            </div>

                            <div class="col-md-3 ">
                                <div class="form-group">
                                    <label>实际到岗时间：</label>
                                    <input type="text" class="form-control js_date" name="comePositionDate" value="<fmt:formatDate value="${entity.comePositionDate}" pattern="yyyy-MM-dd"/>">
                                </div>
                            </div>

                            <div class="col-md-3 ">
                                <div class="form-group">
                                    <label>负责人：</label>
                                    <input type="text" class="form-control" name="responsiblePerson" value="${entity.responsiblePerson}">
                                </div>
                            </div>
                            <div class="col-md-3 ">
                                <div class="form-group">
                                    <label>进程状态：</label>
                                    <select class="form-control" name="processStatus">
                                        <option value="" >请选择</option>
                                        <option value="1" <c:if test="${entity.processStatus eq 1}">selected</c:if>>招聘入职</option>
                                        <option value="2" <c:if test="${entity.processStatus eq 2}">selected</c:if>>紧急</option>
                                        <option value="3" <c:if test="${entity.processStatus eq 3}">selected</c:if>>面试中</option>
                                        <option value="4" <c:if test="${entity.processStatus eq 4}">selected</c:if>>储备</option>
                                        <option value="5" <c:if test="${entity.processStatus eq 5}">selected</c:if>>暂停</option>
                                    </select>
                                </div>
                            </div>
                        </div>

                        <div class="row">

                            <div class="col-md-12 ">
                                <div class="form-group">
                                    <label>进展：</label>
                                    <textarea class="form-control" name="description">${entity.description}</textarea>
                                </div>
                            </div>

                        </div>

                    <button type="button" class="btn green js_save_chg" data-action="save">保存</button>
                    <a type="button" class="btn default" href="<c:url value='/hr/recruitmentSchedule/index'/>">返回列表</a>
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

        var cid = "${entity.company}";

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

        var getCompanies = function (cid) {
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
            getCompanies(cid);
            $('div.js_tree_div').modal('show');
        });

        /**
         * 树 保存
         */
        $('div.js_tree_div').on('click','button.js_save_btn',function () {
            var nodes = companyTreeObj.getSelectedNodes();
            if(nodes.length==1){
                var node = nodes[0];
                $('input[name=companyId]').val(node.id).next().val(node.name);
                $('div.js_tree_div').modal('hide');
            }else {
                layer.msg('请选择单位');
            }
        });

        //加载板块
        $.post("/hr/org/getAllPlat",{},function (data) {////////////////////
            var rdata = data.rdata;
            var _plat = $('input[name=plat]').val();
            for (var i in rdata) {
                if (null != rdata[i].plate) {
                    if (_plat == rdata[i].plate) {
                        $('select[name=plat]').append("<option value='"+rdata[i].plate+"' selected>"+rdata[i].plate+"</option>");
                    } else {
                        $('select[name=plat]').append("<option value='"+rdata[i].plate+"'>"+rdata[i].plate+"</option>")
                    }

                }

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

        var dataObj={id:'${entity.id}'};

        var validateForm = $('form.js_base_form');
        validateForm.validate({
            errorElement : 'span',
            errorClass : 'help-block help-block-error',
            rules : {
                position : {
                    required : true
                },plat : {
                    required : true
                },department: {
                    required : true
                },numberOfHiring: {
                    required : true
                },company: {
                    required : true
                },level: {
                    required : true
                },processStatus: {
                    required : true
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

                if ("" == $('select[name=plat]').val()) {
                    layer.alert("请选择板块!",{'icon':0});
                    return;
                }

                var url = null;
                if(dataObj.id == ''){
                    url = '<c:url value="/hr/recruitmentSchedule/insert"/>';
                }else{
                    url = '<c:url value="/hr/recruitmentSchedule/update"/>';
                }

                layer.confirm("确定提交？",function (c) {
                    layer.close(c);
                    _index = layer.load(2);
                    $.post(url, dataObj, function (result) {
                        layer.close(_index);
                        if (result.success) {
                            layer.msg('操作成功',{time:800},function () {
                                window.location.href='<c:url value="/hr/recruitmentSchedule/index"/>';
                            });
                        }else{
                            layer.msg('操作失败：'+result.rmsg,{time:2000});
                        }
                    }, 'json');
                });
            }
        });


    });

</script>


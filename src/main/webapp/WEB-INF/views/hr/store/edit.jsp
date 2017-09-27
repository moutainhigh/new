<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<link rel="stylesheet" type="text/css" href="<c:url value="/resources/admin/global/plugins/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" />" media="screen"/>

<div class="portlet box blue-hoki">
    <div class="portlet-title">
        <div class="caption">
            <i class="fa fa-gift"></i>
            人才库人员信息
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
                                    <label>姓名：</label>
                                    <input type="text" class="form-control" name="name" value="${entity.name}">
                                </div>
                            </div>
                            <div class="col-md-3 ">
                                <div class="form-group">
                                    <label>性别：</label>
                                    <select name="sex" class="form-control">
                                        <option value="">请选择</option>
                                        <option value="女" <c:if test="${entity.sex eq '女'}">selected</c:if>>女</option>
                                        <option value="男" <c:if test="${entity.sex eq '男'}">selected</c:if>>男</option>
                                    </select>
                                </div>
                            </div>
                            <div class="col-md-3 ">
                                <div class="form-group">
                                    <label>应聘职位：</label>
                                    <input type="text" class="form-control" name="askJob" value="${entity.askJob}">
                                </div>
                            </div>

                            <div class="col-md-3 ">
                                <div class="form-group">
                                    <label>联系电话：</label>
                                    <input type="text" class="form-control" name="mobile" value="${entity.mobile}">
                                </div>
                            </div>
                        </div>

                        <div class="row">

                            <div class="col-md-3 ">
                                <div class="form-group">
                                    <label>邮箱：</label>
                                    <input type="text" class="form-control" name="email" value="${entity.email}">
                                </div>
                            </div>

                            <div class="col-md-3 ">
                                <div class="form-group">
                                    <label>居住地址：</label>
                                    <input type="text" class="form-control" name="liveAddress" value="${entity.liveAddress}">
                                </div>
                            </div>

                            <div class="col-md-3 ">
                                <div class="form-group">
                                    <label>身份证号码：</label>
                                    <input type="text" class="form-control" name="idCard" value="${entity.idCard}">
                                </div>
                            </div>

                            <div class="col-md-3 ">
                                <div class="form-group">
                                    <label>出生日期：</label>
                                    <input type="text" class="form-control" name="birth" value="${entity.birth}">
                                </div>
                            </div>



                        </div>

                        <div class="row">

                            <div class="col-md-3 ">
                                <div class="form-group">
                                    <label>毕业院校：</label>
                                    <input type="text" class="form-control" name="school" value="${entity.school}">
                                </div>
                            </div>

                            <div class="col-md-3 ">
                                <div class="form-group">
                                    <label>学历：</label>
                                    <input type="text" class="form-control" name="education" value="${entity.education}">
                                </div>
                            </div>

                            <div class="col-md-3 ">
                                <div class="form-group">
                                    <label>专业：</label>
                                    <input type="text" class="form-control" name="profession" value="${entity.profession}">
                                </div>
                            </div>
                            <div class="col-md-3 ">
                                <div class="form-group">
                                    <label>开始工作时间：</label>
                                    <input type="text" class="form-control js_date" name="joinWorkDate" value="${entity.joinWorkDate}">
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-3 ">
                                <div class="form-group">
                                    <label>现任职单位：</label>
                                    <input type="text" class="form-control" name="companyName" value="${entity.companyName}">
                                </div>
                            </div>
                            <div class="col-md-3 ">
                                <div class="form-group">
                                    <label>现任职岗位：</label>
                                    <input type="text" class="form-control" name="job" value="${entity.job}">
                                </div>
                            </div>
                            <div class="col-md-3 ">
                                <div class="form-group">
                                    <label>现任职工作起始时间：</label>
                                    <input type="text" class="form-control" name="recentWorkTime" value="${entity.recentWorkTime}">
                                </div>
                            </div>
                            <div class="col-md-3 ">
                                <div class="form-group">
                                    <label>离职原因：</label>
                                    <input type="text" class="form-control" name="leaveReason" value="${entity.leaveReason}">
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-3 ">
                                <div class="form-group">
                                    <label>简历来源：</label>
                                    <input type="text" class="form-control" name="resumeSrc" value="${entity.resumeSrc}">
                                </div>
                            </div>
                            <div class="col-md-3 ">
                                <div class="form-group">
                                    <label>面试时间：</label>
                                    <input type="text" class="form-control js_date" name="auditionDate" value="${entity.auditionDate}">
                                </div>
                            </div>

                            <div class="col-md-3 ">
                                <div class="form-group">
                                    <label>面试官：</label>
                                    <input type="text" class="form-control" name="auditionUser" value="${entity.auditionUser}">
                                </div>
                            </div>

                            <div class="col-md-3 ">
                                <div class="form-group">
                                    <label>面试评估意见：</label>
                                    <textarea name="note" class="form-control" >${entity.note}</textarea>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-3 ">
                                <div class="form-group">
                                    <label>其他工作经历：</label>
                                    <textarea name="otherExperience" class="form-control" >${entity.otherExperience}</textarea>
                                </div>
                            </div>
                        </div>

                    <button type="button" class="btn green js_save_chg" data-action="save">保存</button>
                    <a type="button" class="btn default" href="<c:url value='/hr/recruitmentStore/index'/>">返回列表</a>
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
                $('input[name=company]').val(node.id).next().val(node.name);
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
                name : {
                    required : true
                },sex : {
                    required : true
                },companyStr : {
                    required : true
                },askJob: {
                    required : true
                },mobile: {
                    required : true
                },note: {
                    required : true
                },idCard: {
                    chinaId : true
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


        $('input[name=idCard]').on('blur',function () {
            var data = $(this).val();
            if(data){
                var regex = /^[1-9]\d{5}[1-9]\d{3}(((0[13578]|1[02])(0[1-9]|[12]\d|3[0-1]))|((0[469]|11)(0[1-9]|[12]\d|30))|(02(0[1-9]|[12]\d)))(\d{4}|\d{3}[xX])$/;
                if (regex.test(data)){
                    var birth = data.substring(6,14);
                    var format = birth.substr(0,4)+'-'+birth.substr(4,2)+'-'+birth.substr(6);
                    $('input[name=birth]').val(format);
                    if(data.length==18){
                        if (data.substring(16,17)%2==0){
                            $('select[name=sex] option:eq(1)').attr('selected',true);
                        }else{
                            $('select[name=sex] option:eq(2)').attr('selected',true);
                        }
                    }
                }
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
                    url = '<c:url value="/hr/recruitmentStore/insert"/>';
                }else{
                    url = '<c:url value="/hr/recruitmentStore/update"/>';
                }

                layer.confirm("确定提交？",function (c) {
                    layer.close(c);
                    _index = layer.load(2);
                    $.post(url, dataObj, function (result) {
                        layer.close(_index);
                        if (result.success) {
                            layer.msg('操作成功',{time:800},function () {
                                window.location.href='<c:url value="/hr/recruitmentStore/index"/>';
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


<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<link rel="stylesheet" type="text/css" href="<c:url value="/resources/admin/global/plugins/bootstrap-datepicker/css/bootstrap-datepicker3.min.css" />"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/webuploader/css/webuploader.css" />">
<link type="text/css" href="/resources/admin/global/plugins/select2/css/select2.min.css"  rel="stylesheet">
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/hr/css/hr.css" />"/>

<style>
    .form-control{
        border: #d3d6db 1px solid;
        color: #999;
        border-radius: 2px !important;
        -webkit-border-radius: 2px !important;
        -moz-border-radius: 2px !important;
        -o-border-radius: 2px !important;
        -ms-border-radius: 2px !important;
        height:28px;
        padding: 0px 12px;
        line-height: 26px;
    }
    .table-bordered > tbody > tr > td{
        border: none;
        padding: 3px;
    }
    .js_tab_control>li>a{
        padding-left: 9px;
        padding-right: 9px;
    }
    .btn{
        padding: 3px 12px;
    }
    .datepicker-dropdown{
        z-index: 9999;
    }
    .table25 td{
        width:25%;
    }
    .page-header-fixed .page-container{
        overflow: hidden;
    }
</style>

<div class="portlet box blue-hoki">
    <div class="portlet-title">
        <div class="caption">
            <i class="icon-layers"></i>
            人员信息录入
        </div>
    </div>
    <div class="portlet-body">
        <ul class="nav nav-tabs js_nav" role="tablist">
            <li role="presentation" class="active"><a href="javascript:void(0)">基本信息</a></li>
            <li role="presentation"><a href="javascript:void(0)">工作信息</a></li>
        </ul>
        <form  role="form" class="js_base_form">
            <input type="hidden" value="${employeeInfo.id}" name="empId">
            <input type="hidden" value="${jobInfo.id}" name="empJobId">
        <table class="table table-condensed table-bordered table-none">
            <tr>
                <td>姓名<input type="text" class="form-control " name="empName" value="${employeeInfo.empName}"></td>
                <td>身份证号<input type="text" class="form-control " name="idCard" value="${employeeInfo.idCard}"></td>
                <td>社会保障号<input type="text" class="form-control " name="ssNum" value="${employeeInfo.ssNum}"></td>
                <td>性别
                    <select class="form-control" name="sex">
                        <option value="">请选择</option>
                        <option value="0" <c:if test="${employeeInfo.sex eq 0}">selected="selected"</c:if>>女</option>
                        <option value="1" <c:if test="${employeeInfo.sex eq 1}">selected="selected"</c:if>>男</option>
                    </select>
                </td>
                <td>出生日期<input type="text" class="form-control " name="birthdate" value="<fmt:formatDate value="${employeeInfo.birthdate}" pattern="yyyy-MM-dd"/>"></td>
                <td rowspan="5">
                    <img width="192" height="268" id="photo" src="${photoServerUrl}${employeeInfo.photoUrl}">
                    <input type="hidden" value="${employeeInfo.photoUrl}" name="photoUrl">
                    <div id="uploader">选择附件</div>
                </td>
            </tr>
            <tr>
                <td>民族
                    <select class="form-control" name="nationId">
                        <c:forEach items="${dictMap.nation}" var="data">
                            <option value="${data.dictDataValue}" <c:if test="${employeeInfo.nationId eq data.dictDataValue}">selected="selected"</c:if>>${data.dictDataKey}</option>
                        </c:forEach>
                    </select>
                </td>
                <td>婚姻状况
                    <select class="form-control" name="maritalStatus">
                        <c:forEach items="${dictMap.maritalStatus}" var="data">
                            <option value="${data.dictDataValue}" <c:if test="${employeeInfo.maritalStatus eq data.dictDataValue}">selected="selected"</c:if>>${data.dictDataKey}</option>
                        </c:forEach>
                    </select>
                </td>
                <td>健康状况<input type="text"  class="form-control " name="healthStatus" value="${employeeInfo.healthStatus}"></td>
                <td>参加工作日期<input type="text"  class="form-control " name="joinWorkDate" value="<fmt:formatDate value="${employeeInfo.joinWorkDate}" pattern="yyyy-MM-dd"/>"></td>
                <td>年龄<input type="text"  class="form-control js_age" readonly></td>
            </tr>
            <tr>
                <td>进入集团时间<input type="text"  class="form-control  " name="joinCompDate" value="<fmt:formatDate value="${employeeInfo.joinCompDate}" pattern="yyyy-MM-dd"/>" <c:if test="${ !enableEditJob }">readonly</c:if>></td>
                <td>邮编<input type="text"  class="form-control " name="zipcode" value="${employeeInfo.zipcode}"></td>
                <td>居住地址<input type="text"  class="form-control " name="liveAddress" value="${employeeInfo.liveAddress}"></td>
                <td>户籍地址<input type="text"  class="form-control " name="registrationAddress" value="${employeeInfo.registrationAddress}"></td>
                <td>籍贯
                    <select class="form-control" name="origin" style="width: 100%">
                        <option value="">请选择</option>
                        <c:forEach items="${dictMap.regional}" var="data">
                            <option value="${data.dictDataValue}"<c:if test="${employeeInfo.origin eq data.dictDataValue}">selected="selected"</c:if>>${data.dictDataKey}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td>电子邮箱<input type="text"  class="form-control " name="email" value="${employeeInfo.email}"></td>
                <td>办公电话<input type="text"  class="form-control " name="compPhone" value="${employeeInfo.compPhone}"></td>
                <td>手机<input type="text"  class="form-control " name="mobile" value="${employeeInfo.mobile}"></td>
                <td>家庭电话<input type="text"  class="form-control" name="homePhone" value="${employeeInfo.homePhone}"></td>
                <td>曾用名<input type="text"  class="form-control" name="usedName" value="${employeeInfo.usedName}"></td>
            </tr>
            <tr>
                <td>户口所在地
                    <select class="form-control" name="householdAddress" style="width: 100%">
                        <option value="">请选择</option>
                        <c:forEach items="${dictMap.regional}" var="data">
                            <option value="${data.dictDataValue}"<c:if test="${employeeInfo.householdAddress eq data.dictDataValue}">selected="selected"</c:if>>${data.dictDataKey}</option>
                        </c:forEach>
                    </select>

                </td>
                <td>户口性质
                    <select class="form-control" name="householdType">
                        <c:forEach items="${dictMap.householdType}" var="data">
                            <option value="${data.dictDataValue}"<c:if test="${employeeInfo.householdType eq data.dictDataValue}">selected="selected"</c:if>>${data.dictDataKey}</option>
                        </c:forEach>
                    </select>
                </td>
                <td>省/自治区<input type="text"  class="form-control " name="province" value="${employeeInfo.province}"></td>
                <td>城市/地区<input type="text"  class="form-control " name="city" value="${employeeInfo.city}"></td>
                <td>技术职称
                    <select class="form-control" name="technicalLevel" style="width: 100%">
                        <option value="">请选择</option>
                    <c:forEach items="${dictMap.technicalLevel}" var="data">
                        <option value="${data.dictDataValue}"<c:if test="${employeeInfo.technicalLevel eq data.dictDataValue}">selected="selected"</c:if>>${data.dictDataKey}</option>
                    </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td>政治面貌
                    <select class="form-control" name="politicalStatus">
                        <option value="">请选择</option>
                        <c:forEach items="${dictMap.politicalStatus}" var="data">
                            <option value="${data.dictDataValue}"<c:if test="${employeeInfo.politicalStatus eq data.dictDataValue}">selected="selected"</c:if>>${data.dictDataKey}</option>
                        </c:forEach>
                    </select>
                </td>
                <td>个人身份
                    <select class="form-control" name="personalIdentity">
                        <option value="">请选择</option>
                        <c:forEach items="${dictMap.personalIdentity}" var="data">
                            <option value="${data.dictDataValue}"<c:if test="${employeeInfo.personalIdentity eq data.dictDataValue}">selected="selected"</c:if>>${data.dictDataKey}</option>
                        </c:forEach>
                    </select>
                </td>
                <td>管理分组<input type="text"  class="form-control " name="managerGroup" value="${employeeInfo.managerGroup}"></td>
                <td>辅助核算<input type="text"  class="form-control " name="auxiliaryCheck" value="${employeeInfo.auxiliaryCheck}"></td>
                <td>银行名称<input type="text"  class="form-control " name="bankName" value="${employeeInfo.bankName}"></td>
            </tr>
            <tr>
                <td>银行帐号<input type="text"  class="form-control " name="bankCardNo" value="${employeeInfo.bankCardNo}"></td>
                <td>考核分组<input type="text"  class="form-control " name="assessmentGroup" value="${employeeInfo.assessmentGroup}"></td>
                <td>绩效体系<input type="text"  class="form-control " name="performanceSys" value="${employeeInfo.performanceSys}"></td>
                <td>紧急联系人<input type="text"  class="form-control " name="emergencyName" value="${employeeInfo.emergencyName}"></td>
                <td>紧急联系方式<input type="text"  class="form-control " name="emergencyPhone" value="${employeeInfo.emergencyPhone}"></td>
            </tr>
            <tr>
                <td>五险缴纳单位<input type="text"  class="form-control " name="socialInsuranceCompany" value="${employeeInfo.socialInsuranceCompany}"></td>
                <td>五险缴纳地<input type="text"  class="form-control " name="socialInsuranceAddress" value="${employeeInfo.socialInsuranceAddress}"></td>
                <td>公积金缴纳单位<input type="text"  class="form-control " name="houseProvidentFundCompany" value="${employeeInfo.houseProvidentFundCompany}"></td>
                <td>公积金缴纳地<input type="text"  class="form-control " name="houseProvidentFundAddress" value="${employeeInfo.houseProvidentFundAddress}"></td>
                <td>工资发放单位<input type="text"  class="form-control " name="wagePayCompany" value="${employeeInfo.wagePayCompany}"></td>
            </tr>
            <tr>
                <td>是否两地办法
                    <select class="form-control" name="twoPlaces">
                        <option value="">请选择</option>
                        <option value="0" <c:if test="${employeeInfo.twoPlaces eq 0}">selected="selected"</c:if>>否</option>
                        <option value="1" <c:if test="${employeeInfo.twoPlaces eq 1}">selected="selected"</c:if>>是</option>
                    </select>
                </td>
                <td>最高学历
                    <select  class="form-control" name="education">
                        <option value="">请选择</option>
                        <c:forEach items="${education}" var="pro">
                            <option value="${pro.dictDataValue}" <c:if test="${pro.dictDataValue eq employeeInfo.education}">selected</c:if>>${pro.dictDataKey}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
        </table>
        <table class="table table-condensed table-bordered hidden table25 table-none">
            <tr>
                <td>员工编号<input type="text" class="form-control " name="empNo" value="${jobInfo.empNo}"></td>
                <td>人员类别
                    <select class="form-control" name="empGroup">
                        <c:choose>
                            <c:when test="${empty jobInfo.empGroup || jobInfo.isFormal eq 0}">
                                <option value="1" <c:if test="${jobInfo.empGroup eq 1}">selected="selected"</c:if>>正式人员</option>
                                <option value="2" <c:if test="${jobInfo.empGroup eq 2}">selected="selected"</c:if>>试用人员</option>
                                <option value="3" <c:if test="${jobInfo.empGroup eq 3}">selected="selected"</c:if>>实习人员</option>
                                <option value="4" <c:if test="${jobInfo.empGroup eq 4}">selected="selected"</c:if>>外派人员</option>
                            </c:when>
                            <c:otherwise>
                                <c:forEach items="${dictMap.empGroup}" var="data">
                                    <c:if test="${jobInfo.empGroup eq data.dictDataValue}"> <option value="${data.dictDataValue}"selected="selected">${data.dictDataKey}</option></c:if>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>
                    </select>
                </td>
                <td>
                    所在单位
                    <div <c:if test="${ enableEditJob }">class="input-group"</c:if>>
                        <c:if test="${ enableEditJob }">
                        <input type="hidden"  class="form-control" name="company" value="${jobInfo.company}">
                        </c:if>
                        <input type="text"  class="form-control" name="companyText" readonly value="${jobInfo.companyName}">
                        <c:if test="${ enableEditJob }">
                            <span class="input-group-btn"><button type="button" class="btn btn-primary js_choose_company">选择</button></span>
                        </c:if>
                    </div>

                </td>
                <td>所在部门
                    <div <c:if test="${ enableEditJob }"> class="input-group"</c:if>>
                        <c:if test="${ enableEditJob }">
                        <input type="hidden"  class="form-control" name="department" value="${jobInfo.department}">
                        </c:if>
                        <input type="text"  class="form-control" name="departmentText" readonly value="${jobInfo.departmentName}">
                        <c:if test="${ enableEditJob }">
                        <span class="input-group-btn"><button type="button" class="btn btn-primary js_choose_department">选择</button></span>
                        </c:if>
                    </div>
                </td>
            </tr>
            <tr>
                <td>岗位
                    <input type="hidden" value="${jobInfo.job}" name="oldJob">
                        <select class="form-control" name="job">
                            <option value="">请选择</option>
                            <c:forEach items="${jobs}" var="j">
                                <option value="${j.id}" <c:if test="${ j.id  eq jobInfo.job}">selected</c:if>>${j.name}</option>
                            </c:forEach>
                        </select>
                </td>
                <td>剩余编制<input type="text" class="form-control " name="plaitCount" value="" readonly></td>
                <td>岗位序列
                    <select class="form-control" name="jobSequence">
                        <c:if test="${ enableEditJob }"> <option value="">请选择</option></c:if>
                        <c:forEach items="${dictMap.jobSequence}" var="data">
                            <c:choose>
                                <c:when test="${ enableEditJob }">
                                    <option value="${data.dictDataValue}"<c:if test="${jobInfo.jobSequence eq data.dictDataValue}">selected="selected"</c:if>>${data.dictDataKey}</option>
                                </c:when>
                                <c:otherwise>
                                    <c:if test="${jobInfo.jobSequence eq data.dictDataValue}"><option value="${data.dictDataValue}"selected="selected">${data.dictDataKey}</option></c:if>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>
                </td>
                    <td>职级
                        <select class="form-control" name="dutyLevel">
                            <c:if test="${ enableEditJob }"> <option value="">请选择</option></c:if>
                            <c:forEach items="${dictMap.dutyLevel}" var="data">
                                <c:choose>
                                    <c:when test="${ enableEditJob }">
                                        <option value="${data.dictDataValue}"<c:if test="${jobInfo.dutyLevel eq data.dictDataValue}">selected="selected"</c:if>>${data.dictDataKey}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <c:if test="${jobInfo.dutyLevel eq data.dictDataValue}"><option value="${data.dictDataValue}"selected="selected">${data.dictDataKey}</option></c:if>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
                    </td>
            </tr>
            <tr>
                <td>到职日期<input type="text" class="form-control " name="startTime" value="<fmt:formatDate value="${jobInfo.startTime}" pattern="yyyy-MM-dd"/>"<c:if test="${ !enableEditJob }">readonly</c:if>></td>
                <td>是否在岗
                    <select class="form-control">
                        <c:if test="${empty jobInfo.onGuard}"><option value="1" selected="selected">是</option></c:if>
                        <c:if test="${jobInfo.onGuard eq 1}"><option value="1" selected="selected">是</option></c:if>
                        <c:if test="${jobInfo.onGuard eq 0}"><option value="0" selected="selected">否</option></c:if>
                    </select>
                </td>
                <td>是否转正
                    <select class="form-control" name="isFormal">
                        <c:choose>
                            <c:when test="${empty jobInfo.isFormal || jobInfo.isFormal eq 0}">
                                <option value="0" <c:if test="${jobInfo.isFormal eq 0}">selected="selected"</c:if>>否</option>
                                <option value="1" <c:if test="${jobInfo.isFormal eq 1}">selected="selected"</c:if>>是</option>
                            </c:when>
                            <c:otherwise>
                                <c:if test="${jobInfo.isFormal eq 0}"><option value="0" selected="selected">否</option></c:if>
                                <c:if test="${jobInfo.isFormal eq 1}"><option value="1" selected="selected">是</option></c:if>
                            </c:otherwise>
                        </c:choose>
                    </select>
                </td>
                <td>招聘来源
                    <select class="form-control" name="recruitmentSource">
                        <option value="">请选择</option>
                        <c:forEach items="${dictMap.recruitmentSource}" var="data">
                            <option value="${data.dictDataValue}"<c:if test="${jobInfo.recruitmentSource eq data.dictDataValue}">selected="selected"</c:if>>${data.dictDataKey}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td role="turnFormalDate" <c:if test="${empty jobInfo.isFormal || jobInfo.isFormal eq 0}">class="hidden"</c:if>>转正日期<input type="text" class="form-control " name="turnFormalDate" value="<fmt:formatDate value="${jobInfo.turnFormalDate}" pattern="yyyy-MM-dd"/>"></td>
                <td role="formalType" <c:if test="${empty jobInfo.isFormal || jobInfo.isFormal eq 0}">class="hidden"</c:if>>转正类型
                    <select class="form-control" <c:if test="${jobInfo.isFormal eq 1}">name="formalType"</c:if>>
                        <option value="1" <c:if test="${jobInfo.formalType eq 1}">selected="selected"</c:if>>到期转正</option>
                        <option value="2" <c:if test="${jobInfo.formalType eq 2}">selected="selected"</c:if>>提前转正</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>离职日期<input type="text" class="form-control " name="endTime" value="<fmt:formatDate value="${jobInfo.endTime}" pattern="yyyy-MM-dd"/>" readonly></td>
                <td>司龄<input type="text" class="form-control js_comp_time" readonly></td>
                <td>转正时长
                    <input type="text" class="form-control js_formal_time" readonly>
                </td>
                <td>工龄<input type="text" class="form-control js_work_time" readonly></td>
            </tr>
            <tr>
                <td>工作地点<input type="text" class="form-control " name="workAddress" value="${jobInfo.workAddress}"></td>
            </tr>
        </table>

        </form>
        <div class="js_oper_form">
            <c:if test="${enableEdit}"><button type="button" class="btn green " data-action="save">保存</button></c:if>
            <button type="button" class="btn default" data-action="cancel">返回列表</button>
        </div>
    </div>
</div>


<c:if test="${ not empty employeeInfo.id}">
<div class="panel panel-default">
    <div class="panel-body">
        <ul class="nav nav-tabs js_tab_control" role="tablist">
            <li role="presentation"><a href="javascript:void(0) " data-url="<c:url value="/hr/indutyInfo/${employeeInfo.id}"/>">任职信息</a></li>
            <li role="presentation"><a href="javascript:void(0)">兼职借用信息</a></li>
            <li role="presentation"><a href="javascript:void(0)" data-url="<c:url value="/hr/outdutyInfo/${employeeInfo.id}"/>">离职信息</a></li>
            <li role="presentation"><a href="javascript:void(0)">离退休情况</a></li>
            <li role="presentation"><a href="javascript:void(0)" data-url="<c:url value="/hr/empChg/${employeeInfo.id}"/>">流动情况</a></li>
            <li role="presentation"><a href="javascript:void(0)" data-url="<c:url value="/hr/resumeInfo/${enableEdit}/${employeeInfo.id}"/>">履历记录</a></li>
            <li role="presentation"><a href="javascript:void(0)" data-url="<c:url value="/hr/educationInfo/${enableEdit}/${employeeInfo.id}"/>">学历信息</a></li>
            <li role="presentation"><a href="javascript:void(0)" data-url="<c:url value="/hr/train/trainInfo/${employeeInfo.id}"/>">人员教育培训</a></li>
            <li role="presentation"><a href="javascript:void(0)" data-url="<c:url value="/hr/homeInfo/${enableEdit}/${employeeInfo.id}"/>">家庭信息</a></li>
            <li role="presentation"><a href="javascript:void(0)" data-url="<c:url value="/hr/contract/${employeeInfo.id}"/>">合同信息</a></li>
            <li role="presentation"><a href="javascript:void(0)">考核信息</a></li>
            <li role="presentation"><a href="javascript:void(0)">奖励情况</a></li>
            <li role="presentation"><a href="javascript:void(0)">员工能力素质</a></li>
            <li role="presentation"><a href="javascript:void(0)">职称记录</a></li>
            <li role="presentation"><a href="javascript:void(0)">专业技术职务</a></li>
            <li role="presentation"><a href="javascript:void(0)">专业技术(项目)工作</a></li>
        </ul>
        <iframe id="detailInfo" name="detailInfo" width="100%" HEIGHT="300px" style="border: none"></iframe>
        <hr>
    </div>
</div>
</c:if>
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
                        <div class="col-md-6">
                            <label>部门</label>
                            <ul id="departmentTree" class="ztree"></ul></div>
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

<script type="text/javascript" src="<c:url value="/resources/webuploader/js/webuploader.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/plugIn/zTree/js/jquery.ztree.core.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/select2/js/select2.full.min.js" />"></script>

<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.zh-CN.js" />"></script>

<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/jquery-validation/js/jquery.validate.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/jquery-validation/js/additional-methods.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/admin/global/plugins/jquery-validation/js/messages_zh.min.js" />"></script>
<script>

    /**
     * 子iframe 变换 高度
     */
    function  changeFrameHeight() {
        var iframe = $('#detailInfo')[0];
        var bHeight = iframe.contentWindow.document.body.scrollHeight;
        var dHeight = iframe.contentWindow.document.documentElement.scrollHeight;
        iframe.height  = Math.max(bHeight, dHeight);
    }

    function operSaveBtn(flag) {
        $('#detailInfo').find('div.js_oper button:eq(2)').addClass('hidden');
        if(flag==0){
            $('div.js_oper_form button:eq(0)').addClass('hidden');
            $('#detailInfo').contents().find('div.js_oper button:eq(2)').removeClass('hidden');
        }else if (flag==1){
            $('div.js_oper_form button:eq(0)').removeClass('hidden');
            $('#detailInfo').contents().find('div.js_oper button:eq(2)').addClass('hidden');
        }
    }

    /**
     *子 iframe 确定操作
     **/
    function confirmOper(fn) {
        var index =  layer.confirm('确定删除？该操作将立即生效',function () {
            fn();
            layer.close(index);
        });
    }

    $(document).ready(function () {

        $('select[name=origin]').select2();
        $('select[name=householdAddress]').select2();
        $('select[name=technicalLevel]').select2();

        /**
         * iframe 切换
         */
        $("ul.js_tab_control li").on("click",function () {
            $(this).addClass('active').siblings().removeClass('active');
            var url = $(this).find('a').data('url');
            if (url){
                $('#detailInfo').attr("src",url);
            }else {
                $('#detailInfo').attr("src",'');
            }
        });

        /**
         * tab 切换
         */
        $('ul.js_nav li').on('click',function () {
            $(this).addClass('active').siblings().removeClass('active');
            var index = $(this).index();
            $('form.js_base_form').find('table').addClass('hidden').eq(index).removeClass('hidden');
        });
        
        var checkIdCardRepeat = function (idCard) {
            var flag = false;
            $.ajax({
                type: 'POST',
                url: '<c:url value="/hr/checkIdCardRepeat"/>',
                async: false,
                data: {idCardNo: idCard},
                dataType: "json",
                success: function (result) {
                    if(result.success){
                        if(result.rdata==true){
                            flag = true;
                        }else{
                            layer.msg('身份证号码已经存在');
                        }
                    }else{
                        layer.msg('身份证号码不能为空');
                    }
                }
            });
            return flag;
        }

        var checkPsNoRepeat = function (psNo) {
            var flag = false;
            $.ajax({
                type: 'POST',
                url: '<c:url value="/hr/checkEmpNoRepeat"/>',
                async: false,
                data: {empNo: psNo},
                dataType: "json",
                success: function (result) {
                    if(result.success){
                        if(result.rdata==true){
                            flag = true;
                        }else{
                            layer.msg('该员工编号已经存在');
                        }
                    }else{
                        layer.msg('员工编号不能为空');
                    }
                }
            });
            return flag;
        }

        /**
         * 保存信息
         *
         */
        var resp = false;
        $('div.js_oper_form').on('click','button',function () {
            var action  = $(this).data('action');
            if(action=='save'){
                var result = false;
                $('ul.js_nav li').each(function (k, v) {
                    $(this).trigger('click');
                    if (validateForm.valid()){
                        result = true;
                    }else {
                        result = false;
                        return false;
                    }
                });
                if(result&&!$('input[name=empId]').val()){
                    result = false;
                    if(checkIdCardRepeat($('input[name=idCard]').val())&&checkPsNoRepeat($('input[name=empNo]').val())){
                        result = true;
                    }
                }
                if (result&&!resp){
                    resp = true;
                    var data = $('form.js_base_form').serialize();
                    var _index = layer.load(2);
                    $.post('<c:url value="/hr/saveEmpInfo"/>', data, function (result) {
                        layer.close(_index);
                        resp = false;
                        if (result.success){
                            var _id =result.rdata;
                            layer.msg("操作成功",function () {
                                window.location.href ='<c:url value="/hr/edit?id="/>'+_id;
                            });
                        }else{
                            layer.msg("保存失败:"+result.rmsg);
                        }
                    },'json');
                }
            }else if(action=="cancel"){
                window.location.href='<c:url value="/hr/empList"/>';
            }
        });

        /**
         * 单位/部门选择
         */
        (function () {
            var cid = "${jobInfo.company}";
            var did = "${jobInfo.department}";
            var jid = "${jobInfo.job}";
            var companyTreeObj;
            var departmentTreeObj;

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

            var getJobs = function (departmentId) {
                var date = new Date($('input[name=startTime]').val());
                $.post('<c:url value="/hr/org/getAllJobWithPlait"/>',{departmentId:departmentId,year:date.getFullYear(),month:date.getMonth()+1},function (result) {
                    var html='<option value="">请选择</option>';
                    if (result.success){
                        $('input[name=plaitCount]').val('');
                        var data = result.rdata;
                        var html='<option value="" data-count="">请选择</option>';
                        for(var i = 0; i<data.length; i++){
                            if (jid == data[i].id){
                                html+='<option value="'+data[i].id+'" selected data-count="'+data[i].count+'">'+data[i].name+'</option>';
                            }else {
                                html+='<option value="'+data[i].id+'" data-count="'+data[i].count+'">'+data[i].name+'</option>';
                            }
                        }
                    }else{
                        layer.msg("获取岗位列表失败");
                    }
                    $('select[name=job]').html(html);
                },'json');
            }

            var getDepartments = function (orgId) {
                $.post('<c:url value="/hr/org/getAllDepartments"/>',{companyId:orgId},function (result) {
                    if (result.success){
                        departmentTreeObj = initTree(result.rdata,"#departmentTree");
                        if (did){
                            var node = departmentTreeObj.getNodeByParam('id',did,null);
                            if (null!=node){
                                departmentTreeObj.selectNode(node);
                                $('input[name=department]').next().val(node.name);
                                getJobs(did);
                            }
                        }
                    }
                },'json');
            }

            var getCompanies = function (cid) {
                $.post('<c:url value="/hr/org/getAllCompanies"/>', function (result) {
                    if (result.success) {
                        companyTreeObj = initTree(result.rdata, "#companyTree", function (event, treeId, treeNode) {
                            getDepartments(treeNode.id);
                        });
                        companyTreeObj.expandNode(companyTreeObj.getNodes()[0],true,false,false);
                        if (cid&&companyTreeObj) {
                            var cnode = companyTreeObj.getNodeByParam("id", cid, null);
                            companyTreeObj.selectNode(cnode);
                            $('input[name=company]').next().val(cnode.name);
                            getDepartments(cid);
                        }
                    }
                }, 'json');
            }

            $('select[name=job]').on('change',function () {
                $('input[name=plaitCount]').val($(this).find('option:selected').data('count'));
            });

            /**
             * 初始化树
             * */
            <c:if test="${ enableEditJob }">
            getCompanies(cid);
            </c:if>


            /**
             * 选择公司、部门
             */
            $('button.js_choose_department,button.js_choose_company').on('click',function () {
                var date = $('input[name=startTime]').val();
                if(date != ''){
                    $('div.js_tree_div').modal('show');
                }else{
                    layer.msg('请先选定到职日期');
                }
            });

            /**
             * 树 保存
             */
            $('div.js_tree_div').on('click','button.js_save_btn',function () {
                var nodes = companyTreeObj.getSelectedNodes();
                var flag = false;
                if(nodes.length==1){
                    var node = nodes[0];
                    $('input[name=company]').val(node.id).next().val(node.name);
                    flag = false;
                    var dnodes =  departmentTreeObj.getSelectedNodes();
                    if(dnodes.length==1) {
                        var node = dnodes[0];
                        $('input[name=department]').val(node.id).next().val(node.name);
                        getJobs(node.id);
                        flag = true;
                    }
                    if (flag){
                        $('div.js_tree_div').modal('hide');
                    }else {
                        layer.msg('请选择部门');
                    }
                }
            });

        })();

        var timeInitArgs = {
            todayBtn : "linked",
            autoclose : true,
            language: 'zh-CN',
            format : "yyyy-mm-dd",
            todayHighlight : true,
            endDate : new Date()
        };

        $('input.js_datetime_picker').datepicker(timeInitArgs);

        /**
         * 生日
         */
        $('input[name=birthdate]').datepicker(timeInitArgs).on('changeDate', function(e){
            var date = new Date(e.date);
            var now = new Date();
            var year = now.getYear();
            $('input.js_age').val(year-date.getYear());
        });

        var birth = $('input[name=birthdate]').val();
        if (birth){
            var date = new Date(birth);
            var now = new Date();
            var year = now.getYear();
            $('input.js_age').val(year-date.getYear());
        };

        var countMonth = function (selectTime) {
            var date = new Date(selectTime);
            var now = new Date();
            return (now.getYear()-date.getYear())*12+now.getMonth()-date.getMonth();
        }

        $('select[name=isFormal]').on('change',function () {
            var value = $(this).val();
            if(value==1){
                $('td[role=turnFormalDate]').removeClass('hidden');
                $('td[role=formalType]').removeClass('hidden').find('select').attr('name','formalType');
            }else{
                $('td[role=turnFormalDate]').addClass('hidden').find('input').val('');
                $('td[role=formalType]').addClass('hidden').find('select').removeAttr('name');
            }
        });

        <c:if test="${ enableEditJob }">
            $('input[name=startTime]').datepicker({
                todayBtn : "linked",
                autoclose : true,
                language: 'zh-CN',
                format : "yyyy-mm-dd",
                todayHighlight : true,
                <sec:authorize access="!hasAnyRole('hr_dev','hrkylr')"> startDate:' ${monthFirstDay}',</sec:authorize>
                endDate : new Date()
            });

            /**
             * 进入集团时间
             */

            var joinCompDate= $('input[name=joinCompDate]').datepicker({
                todayBtn : "linked",
                autoclose : true,
                language: 'zh-CN',
                format : "yyyy-mm-dd",
                todayHighlight : true,
                <sec:authorize access="!hasAnyRole('hr_dev','hrkylr')"> startDate:' ${monthFirstDay}',</sec:authorize>
                endDate : new Date()
            }).on('changeDate', function(e){
                var count = countMonth(e.date);
                $('input.js_comp_time').val(count);
            }).val();
            if (joinCompDate){
                var count = countMonth(joinCompDate);
                $('input.js_comp_time').val(count);
            }
        </c:if>
        /**
         * 参加工作日期
         */
        var joinWorkDate = $('input[name=joinWorkDate]').datepicker(timeInitArgs).on('changeDate', function(e){
            var count = countMonth(e.date);
            $('input.js_work_time').val(count);
        }).val();
        if (joinWorkDate){
            var count = countMonth(joinWorkDate);
            $('input.js_work_time').val(count);
        }

        /**
         * 转正日期
         */
        var turnFormalDate = $('input[name=turnFormalDate]').datepicker(timeInitArgs).on('changeDate', function(e){
            var count = countMonth(e.date);
            $('input.js_formal_time').val(count);
        }).val();
        if (turnFormalDate){
            var count = countMonth(turnFormalDate);
            $('input.js_formal_time').val(count);
        }

        var validateForm = $('form.js_base_form');
        validateForm.validate({
            errorElement : 'span',
            errorClass : 'help-block help-block-error',
            rules : {
                empName : {
                    required : true
                },
                 idCard: {
                    required : true,
                     chinaId : true
                },
                ssNum : {
                    required : true
                },sex: {
                    required : true
                },mobile: {
                    required : true
                },birthdate: {
                    required : true
                },healthStatus: {
                    required : true
                },liveAddress: {
                    required : true
                },registrationAddress: {
                    required : true
                },origin: {
                    required : true
                },householdAddress: {
                    required : true
//                },managerGroup: {
//                    required : true
//                },auxiliaryCheck: {
//                    required : true
//                },bankName: {
//                    required : true
//                },bankCardNo: {
//                    required : true
//                },assessmentGroup: {
//                    required : true
//                },performanceSys: {
//                    required : true
                },emergencyName: {
                    required : true
                },emergencyPhone: {
                    required : true
                },education: {
                    required : true
                },joinCompDate: {
                    required : true
                },empNo : {
                    required : true
                },companyText : {
                    required : true
                },departmentText : {
                    required : true
                },job : {
                    required : true
                },jobSequence : {
                    required : true
                },dutyLevel : {
                    required : true
                },jobLevel : {
                    required : true
                },startTime : {
                    required : true
                }
            },
            messages : {

            },
            highlight : function(element) {
                $(element).parents('td').addClass('has-error');
            },
            unhighlight : function(element) {
                $(element).parents('td').removeClass('has-error');
            },
            success : function(label) {
                label.remove();
            },errorPlacement: function(error, element) {
                element.parents('td').append(error);
            }
        });

        $('input[name=idCard]').on('blur',function () {
            var data = $(this).val();
            if(data){
                var regex = /^[1-9]\d{5}[1-9]\d{3}(((0[13578]|1[02])(0[1-9]|[12]\d|3[0-1]))|((0[469]|11)(0[1-9]|[12]\d|30))|(02(0[1-9]|[12]\d)))(\d{4}|\d{3}[xX])$/;
                if (regex.test(data)){
                    var birth = data.substring(6,14);
                    var format = birth.substr(0,4)+'-'+birth.substr(4,2)+'-'+birth.substr(6);
                    $('input[name=birthdate]').val(format).datepicker( "setDate", format);
                    $('input[name=ssNum]').val(data);
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

        var photoServerUrl = '${photoServerUrl}';

        // 初始化Web Uploader
        var single = WebUploader.create({
            fileVal:'imgFile',
            auto: true,
            compress: false,
            swf: '<c:url value="/resources/webuploader/js/Uploader.swf" />',
            server: '<c:url value="/hr/uploadPhoto" />',
            pick: {
                id: '#uploader',
                label: '上传照片',
                multiple:true
            },
            accept: {
                title: 'Images',
                extensions: 'gif,jpg,jpeg,bmp,png',
                mimeTypes: 'image/*'
            },
            fileSingleSizeLimit: 5 * 1024 * 1024
        });

        single.on( 'uploadSuccess', function( file ,json) {
            $("#photo").attr("src",photoServerUrl+json.rdata);
            $('input[name=photoUrl]').val(json.rdata);
        });

        single.on( 'uploadError', function( file ) {
            alert("附件上传失败！");
        });

        single.on( 'error', function( e) {
            if (e =="F_EXCEED_SIZE"){
                alert("附件大小超过最大限制！");
            }
        });

    });

</script>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/tlds/lycore.tld" prefix="ly"%>
 <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<!-- BEGIN EXAMPLE TABLE PORTLET-->
<div class="portlet light" id="form_wizard_1">
    <div class="portlet-title">
        <div class="caption">
        	<i class="icon-layers font-green"></i>
            <span class="caption-subject font-green sbold uppercase">评分卡</span>
       	</div>
      </div>
    <div class="portlet-title">	 
	        <div class="caption">
	        	申请人：${entity.name }  &nbsp;&nbsp;总得分：${userCredit.wyScore+userCredit.zysrScore+userCredit.jtjzScore+userCredit.jtxyScore+userCredit.fuxfScore }
	       	</div>
    </div>
    <div class="portlet-body">
        <table class="table table-striped table-bordered table-advance table-hover" id="tlist">
            <thead>
                <tr>
                    <th width="20%" colspan="2"> 家庭档案（10%）</th>
                    <th width="20%" colspan="2"> 车辆档案（5%） </th>
                    <th width="20%" colspan="2"> 车位档案（5%） </th>
                    <th width="20%" colspan="2"> 费用清缴（30%） </th>
                    <th width="20%" colspan="2"> 服务与消费（50%） </th>
                </tr>
            </thead>
            <tbody>
            <c:set var="score_a" value="0"  />  
            <c:set var="score_b" value="0"  /> 
            <c:set var="score_c" value="0"  /> 
            <c:set var="score_d" value="0"  /> 
            <c:set var="score_e" value="0"  /> 
            
               	<c:forEach  begin="1" end="12" varStatus="vs" var="x" >
		              <tr >
               		 	<c:forEach  items="${list1 }"  var="credit"  varStatus="vs">
		               			<c:if test="${vs.count eq x }">
		               			<td>${credit.name }</td>
		               			<td>${credit.score }</td>
		               			<c:set var="score_a" value="${score_a+credit.score }"  /> 
		               			</c:if>
		               	</c:forEach>
		               	<c:if test="${x>fn:length(list1) }">
		               			<td></td>
		               			<td></td>
		               	</c:if>
		               	
		               	<c:forEach  items="${list2 }"  var="credit"  varStatus="vs">
		               			<c:if test="${vs.count eq x }">
		               			<td>${credit.name }</td>
		               			<td>${credit.score }</td>
		               			<c:set var="score_b" value="${score_b+credit.score }"  /> 
		               			</c:if>
		               	</c:forEach>
		               	
		               	<c:if test="${x>fn:length(list2) }">
		               			<td></td>
		               			<td></td>
		               	</c:if>
		               	
		               	<c:forEach  items="${list3 }"  var="credit"  varStatus="vs">
		               			<c:if test="${vs.count eq x }">
		               			<td>${credit.name }</td>
		               			<td>${credit.score }</td>
		               			<c:set var="score_c" value="${score_c+credit.score }"  /> 
		               			</c:if>
		               	</c:forEach>
		               	
		               	 	
		               	<c:if test="${x>fn:length(list3) }">
		               			<td></td>
		               			<td></td>
		               	</c:if>
		               	<c:forEach  items="${list4 }"  var="credit"  varStatus="vs">
		               			<c:if test="${vs.count eq x }">
		               			<td>${credit.name }</td>
		               			<td>${credit.score }</td>
		               			<c:set var="score_d" value="${score_d+credit.score }"  /> 
		               			</c:if>
		               	</c:forEach>
		               	<c:if test="${x>fn:length(list4) }">
		               			<td></td>
		               			<td></td>
		               	</c:if>
		               	
		               	<c:forEach  items="${list5 }"  var="credit"  varStatus="vs">
		               			<c:if test="${vs.count eq x }">
		               			<td>${credit.name }</td>
		               			<td>${credit.score }</td>
		               			<c:set var="score_e" value="${score_e+credit.score }" /> 
		               			</c:if>
		               	</c:forEach>
		               	<c:if test="${x>fn:length(list5) }">
		               			<td></td>
		               			<td></td>
		               	</c:if>
		               	
               		</tr>
               	</c:forEach>
               	<tr >
               			<td>得分</td>
               			<td>${score_a*10/100 }</td>
               			<td>得分</td>
               			<td>${score_b*5/100}</td>
               			<td>得分</td>
               			<td>${score_c*5/100 }</td>
               			<td>得分</td>
               			<td>${score_d *30/100}</td>
               			<td>得分</td>
               			<td>${score_e *50/100}</td>
               	</tr>
               	
             </tbody>  	
               	<%--  <tbody>
               <c:forEach  items="${list }"  var="credit"  varStatus="vs">
               		<c:if test="${credit.type eq '2' }">
               			<tr >
               			<td>${credit.name }</td>
               			<td>${credit.score }</td>
               			</tr>
               		</c:if>
               	</c:forEach>
               </tbody> --%> 
               	
            
        </table>
        <div class="row">
	    	<div class="col-md-5 col-sm-5">
	    	</div>
	    	<div class="col-md-7 col-sm-7 text-right">
	    	</div>
    	</div>
    	
    	<%-- <div class="form-actions">
	            <div class="row">
	                <div class="col-md-offset-4 col-md-10">
	                    <button class="btn btn-lg blue" type="submit" id="subBtn">提  交</button>
	                    <a class="btn btn-lg grey-salsa" href="<c:url value="/admin/cms/content/get?mid=${menu.mid }" />">返回</a>
	                </div>
	            </div>
             </div> --%>
    	
    </div>
</div>
<!-- END EXAMPLE TABLE PORTLET-->

<form  action="<c:url value="/admin/cms/loan/addgrade"/>"  id="form_grade" method="post"  >

<input type="hidden" id="userId" value="${user.userId }" name="userId" >
<input type="hidden" id="wyScore" name="wyScore"  >
<input type="hidden" id="zysrScore" name="zysrScore" >
<input type="hidden" id="jtjzScore" name="jtjzScore" >
<input type="hidden" id="jtxyScore" name="jtxyScore" >
<input type="hidden" id="content" name="content" >
</form>


<script type="text/javascript">
$(document).ready(function(){ 
	$("select[name^='pageSelect']").change(function(){
		addAllSelectValue();
	});
	$("input[type='checkbox'][id^='checkbox1_']").change(function(){
		addAllSelectValue();
	});
  
  	$("#subBtn").click(function(){
  		var v1=0;
  		var v2=0;
  		var v3=0;
  		var v4=0;
		$.each($("select[name='pageSelect_1'] option:selected"), function(i, n){
			 v1+=parseFloat($(this).attr("data"))
		});
		$.each($("select[name='pageSelect_2'] option:selected"), function(i, n){
			 v2+=parseFloat($(this).attr("data"))
		});
		$.each($("select[name='pageSelect_3'] option:selected"), function(i, n){
			 v3+=parseFloat($(this).attr("data"))
		});
		$.each($("select[name='pageSelect_4'] option:selected"), function(i, n){
			 v4+=parseFloat($(this).attr("data"))
		});
		//多选
		$.each($("input[type='checkbox'][id^='checkbox1_']:checked"), function(i, n){
			 v1+=parseFloat($(this).attr("data"))
		});
		//填入值
		$.each($("input[type='text'][name='wy_score']"), function(i, n){
			 if($(this).val().length>0){
				 v1+=parseFloat($(this).val())
			 }
		});
		$.each($("input[type='text'][name='zysr_score']"), function(i, n){
			 if($(this).val().length>0){
				 v2+=parseFloat($(this).val())
			 }
		});
		$.each($("input[type='text'][name='jtxy_score']"), function(i, n){
			 if($(this).val().length>0){
				 v4+=parseFloat($(this).val())
			 }
		});
		$("#wyScore").val(v1.toFixed(1))
		$("#zysrScore").val(v2.toFixed(1))
		$("#jtjzScore").val(v3.toFixed(1))
		$("#jtxyScore").val(v4.toFixed(1))
		$("#content").val($("#tlist").html());
  		$("#form_grade").submit();
  	});
  
  
});

	function addAllSelectValue(){
		var v=0;
		$.each($("select[name^='pageSelect'] option:selected"), function(i, n){
			//  alert($(this).attr("data"));
			 v+=parseFloat($(this).attr("data"))
		});
		
		$.each($("input[type='checkbox'][id^='checkbox1_']:checked"), function(i, n){
			 v+=parseFloat($(this).attr("data"))
		});
		$("#dq_score").html("当前得分："+v.toFixed(1))
	
	}


</script>

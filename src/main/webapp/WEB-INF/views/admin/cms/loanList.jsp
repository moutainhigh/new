<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/tlds/lycore.tld" prefix="ly"%>

<!-- BEGIN EXAMPLE TABLE PORTLET-->
<div class="portlet light" id="form_wizard_1">
    <div class="portlet-title">
        <div class="caption">
        	<i class="icon-layers font-green"></i>
            <span class="caption-subject font-green sbold uppercase">${menu.name }</span>
       	</div>
        <div class="actions">
                  姓名<input id="searchStr" name="searchStr" class="form-control input-inline input-sm" type="text" placeholder="${entity.name}"/>
			<button class="btn btn-sm green" onclick="search();"><i class="fa fa-search"></i> 搜索 </button>   
			<%--<div class="btn-group">
	            <button data-toggle="dropdown" class="btn green dropdown-toggle" aria-expanded="false">排序
	                <i class="fa fa-angle-down"></i>
	            </button>
	            <ul class="dropdown-menu pull-right">
	                <li>
	                    <a href="<c:url value="/admin/cms/content/get"/>${ly:toString(entity,'mid,pageSize,field1')}&dt=desc&dtn=sid">
	                    <i class="fa fa-long-arrow-down"></i>编号降序</a>
	                </li>	     
	                 <li>
	                    <a href="<c:url value="/admin/cms/content/get"/>${ly:toString(entity,'mid,pageSize,field1')}&dt=desc&dtn=field1">
	                    <i class="fa fa-long-arrow-down"></i>${menu.titleAlias}降序</a>
	                </li>
	                <li>
	                    <a href="<c:url value="/admin/cms/content/get"/>${ly:toString(entity,'mid,pageSize,field1s')}&dt=asc&dtn=field1">
	                    <i class="fa fa-long-arrow-up"></i>${menu.titleAlias}升序</a>
	                </li> 
	            </ul>
             </div>  --%>
			<%-- <div class="btn-group">
	            <button data-toggle="dropdown" class="btn green dropdown-toggle" aria-expanded="false">工具
	                <i class="fa fa-angle-down"></i>
	            </button>
	            <ul class="dropdown-menu pull-right">
	                <li>
	                    <a href="<c:url value="/admin/cms/content/xls"/>">
	                    <i class="fa fa-file-excel-o"></i>导出Excel</a>
	                </li>
	                <li>
	                    <a href="javascript:;">
	                    <i class="fa fa-file-pdf-o"></i>导出Pdf</a>
	                </li>
	                <li>
	                    <a href="javascript:;">
	                    <i class="fa fa-print"></i>打印</a>
	                </li>
	            </ul>
             </div>  --%>                    	
        </div>
    </div>
    <div class="portlet-body">
        <table class="table table-striped table-bordered table-advance table-hover" id="tlist">
            <thead>
                <tr>
                    <th width="4%">编号</th>
                    <th width="5%">姓名</th>
                    <th width="10%">身份证</th>
                    <th width="10%">手机号码</th>
                    <th width="6%">职业身份</th>
                    <th width="8%">贷款分类</th>
                    <th width="6%">年利率</th>
                    <th width="10%">还款方式</th>
                    <th width="10%">贷款金额</th>
                    <th width="5%">贷款期限</th>
                    <th width="10%">日期 </th>
                    <th width="5%">状态</th>
                    <th width="8%">操作 </th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${page.content}" var="entity" varStatus="status">
                    <tr>
                        <td>${entity.id }</td>
                        <td class="highlight">
                            <div class="warning"></div>&nbsp;
                            <a href="#">${entity.name}</a>
                        </td>
                        <td>${entity.idCard}</td>
                        <td>${entity.mobile}</td>
                        <td>${entity.profession}</td>
                        <td>${entity.loanType}</td>
                        <td>${entity.loanRate}</td>
                        <td>${entity.repayment}</td>
                        <td>${entity.loanAmount}&nbsp;元</td>
                        <td>${entity.loanPeriod}&nbsp;月</td>
                        <td>${entity.createDate}</td>
                        <td>
                        <c:if test="${entity.status eq 1 }">
                        <span class="label label-sm label-info">待处理</span>
                        </c:if>
                        <c:if test="${entity.status eq 3 }">
                        <span class="label label-sm label-success">已处理</span>
                        </c:if>
                        <c:if test="${entity.status eq 2 }">
                        <span class="label label-sm label-danger">已删除</span>
                        </c:if>
                        </td>
                        <td>
                        	<button class="btn btn-xs blue" onclick="luru(${entity.id})"><i class="fa fa-minus-circle"></i> 录入</button>
                            <button class="btn btn-xs blue" onclick="put(${entity.id});"><i class="fa fa-dot-circle-o"></i>处理 </button>
                            <button class="btn btn-xs green" onclick="del(${entity.id},'${entity.name }');"><i class="fa fa-minus-circle"></i> 删除 </button>
                        	<button class="btn btn-xs green" onclick="pingfen('${entity.id}','${entity.name}','${entity.idCard}')"><i class="fa fa-minus-circle"></i> 评分 </button>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <div class="row">
	    	 <div class="col-md-5 col-sm-5">
	    		<ly:pageSizeTag link="/admin/cms/submit/get${ly:toString(entity,'mid,name,dt,dtn')}" page="${page}"></ly:pageSizeTag>
	    	</div>
	    	<div class="col-md-7 col-sm-7 text-right">
                <ly:pageTag link="/admin/cms/submit/get${ly:toString(entity,'mid,name,dt,dtn')}&pageNo=" page="${page}"></ly:pageTag>
	    	</div> 
    	</div>
    </div>
</div>
<!-- END EXAMPLE TABLE PORTLET-->

<div id="div_flow_model" class="modal fade" tabindex="-1" data-width="760" data-backdrop="static" aria-hidden="true"  >
	<div class="modal-dialog"   id="modal_width" style="width: 820px !important;" >
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title">信用评分</h4>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="col-md-12" id="div_pic" style="width: auto;" >
						<div class="score_text_box">
							  <div class="score_icon" style="margin-top: 25px;">
							    <div class="pointer point03" id="pf_point" ></div>
							    <span class="fraction" id="pf_score">68</span>
							    <span class="grade" id="pf_grade">信用良好</span>
							  </div>
							  <div class="score_txt">
							    <ul>
							      <li>
							        <label>姓名：</label><span id="pf_name">张三</span>
							      </li>
							      <li>
							        <label>身份证号码：</label><span  id="pf_idcard">500112199009041010</span>
							      </li>
							      <li>
							        <label>评分明细：</label>
							        <div id="penfen_info">
							        <span>家庭档案：9.3分</span><br>
							        <span>车辆档案：3分</span><br>
							        <span>车位档案：4.25分</span><br>
							        <span>费用清缴：21分</span><br>
							        <span>服务与消费：30分</span><br>
							        <a href="<c:url value="/admin/cms/loan/getDetail"/>" id="a_loan_info" >详情>>></a>
							        </div>
							      </li>
							      <li>
							        <label>评估时间：</label><span id="pf_date"></span>
							      </li>
							    </ul>
							    
							  </div>
							</div>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button class="btn red" type="button" onclick="setUserSurvey();">纳入调查</button>
				<button type="button" class="btn default" data-dismiss="modal" id="js_pop_defer_close">关闭</button>
			</div>
		</div>
	</div>
</div>


<script type="text/javascript">
	
	function pingfen(userId,name,idcard){
		//$("")
		$("#pf_name").html(name);
		$("#pf_idcard").html(idcard);
		var xq="<c:url value="/admin/cms/loan/getDetail?userId="/>"+userId;
		$("#a_loan_info").attr("href",xq);
		var url="<c:url value="/admin/cms/loan/ajaxgrade"/>";
		var json=ajaxList(url,userId);
		if(json!=null&&json!=-1){
		var v=parseFloat(json.wyScore)+parseFloat(json.zysrScore)+parseFloat(json.jtjzScore)+parseFloat(json.jtxyScore)+parseFloat(json.fuxfScore);
		$("div#penfen_info span:eq(0)").html("家庭档案："+json.wyScore+"分");
		$("div#penfen_info span:eq(1)").html("车辆档案："+json.zysrScore+"分");
		$("div#penfen_info span:eq(2)").html("车位档案："+json.jtjzScore+"分");
		$("div#penfen_info span:eq(3)").html("费用清缴："+json.jtxyScore+"分");
		$("div#penfen_info span:eq(4)").html("服务于消费："+json.fuxfScore+"分");
		$("#pf_date").html(json.ex_createDate);
		$("#pf_score").html(v);
			if(v<50){
				$("#pf_grade").html("信用较差");
				$("#pf_point").removeClass();
				$("#pf_point").addClass("pointer point01");
			}else if(v<60&&v>=50){
				$("#pf_grade").html("信用中等");
				$("#pf_point").removeClass();
				$("#pf_point").addClass("pointer point02");
			}else if(v<70&&v>=60){
				$("#pf_grade").html("信用良好");
				$("#pf_point").removeClass();
				$("#pf_point").addClass("pointer point03");
			}else if(v<85&&v>=70){
				$("#pf_grade").html("信用优秀");
				$("#pf_point").removeClass();
				$("#pf_point").addClass("pointer point04");
			}else if(v>=85){
				$("#pf_grade").html("信用极好");
				$("#pf_point").removeClass();
				$("#pf_point").addClass("pointer point05");
			}
			$("#div_flow_model").modal();	
		}else{
			alert("该用户没有评分")
		}
		
	}

	function luru(id){
		window.location.href = "<c:url value="/admin/cms/loan/grade?userId="/>"+id;
	}
	function setUserSurvey(){
		var s=$("#pf_score").html();
		if(s>50){
			if(confirm("确认把该客户纳入贷款调查？")){
				alert("操作成功")
			}else{
			
			}
		}else{
			var flg = confirm("该客户信用等级为:较差，不允许纳入贷款调查");
	        if(flg) {
	        }
		}
	}


    function search() {
        window.location.href = "<c:url value="/admin/cms/loan/get"/>"+ "${ly:toString(entity,'mid,pageSize,dt,dtn')}"+ "&name="+$("#searchStr").val();
    }
    
     function put(cid) {
        window.location.href = "<c:url value="/admin/cms/loan/put?id="/>" + cid + "&mid=${menu.mid}";
    }

    function del(cid,title) {
        var flg = confirm("确定要刪除["+title+"]嗎?");
        if(flg) {
            window.location.href = "<c:url value="/admin/cms/loan/delete?id="/>" + cid + "&mid=${menu.mid}";
        }
    }
    
    	//ajax获取数据
	function ajaxList(url,userId){
		var json='';
		$.ajax({
	        type:"POST",
	        url:url,
			data:{userId:userId},
			datatype:"json",
			async:false,
	        success: function(data){
	        	if(data!=null){
	        		json=JSON.parse(data); 
	        	}
	        }
		});
		return json;
	}
   
</script>

<style>
*{
	margin:0px;
	padding:0px;
	list-style:none;
	border:0px;
	box-sizing:border-box;
}
.score_text_box{
	width:800px;
	padding:50px;
	border:#eee 1px solid;
}
.score_text_box:after{
	display:table;
	content:"";
	clear:both;
}
.score_icon{
	float:left;
	width:220px;
	height:220px;
	text-align:center;
	background:url(<c:url value="/resources/cms/images/score_icon_bg.png" />) no-repeat top;
}
.score_icon .pointer{
	margin:42px auto 10px;
	width:156px;
	height:78px;
	background:url(<c:url value="/resources/cms/images/pointer.png" />) no-repeat;
}
.score_icon .point01{
	background-position:0 0;
}
.score_icon .point02{
	background-position:0 -78px;
}
.score_icon .point03{
	background-position:0 -156px;
}
.score_icon .point04{
	background-position:0 -234px;
}
.score_icon .point05{
	background-position:0 -312px;
}
.score_icon span{
	display:block;
}
.score_icon .fraction{
	font-size:36px;
	color:#eb6300;
}
.score_icon .grade{
	font-size:18px;
	color:#eb6300;
}
.score_txt{
	margin-left:260px;
	font-size:14px;
}
.score_txt li{
	margin-top:8px;
	line-height:30px;
}
.score_txt li label{
	float:left;
	display:block;
	width:90px;
	color:#999;
	text-align:right;
}
.score_txt li > div{
	margin-left:90px;
	overflow:hidden;
}
.score_txt li > div span{
	display:inline-block;
	margin-right:10px;
}
</style>
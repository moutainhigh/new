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
        	<button class="btn sbold blue" id="insertBtn" onclick="post();"><i class="fa fa-plus-circle"></i>增加</button>
            <input id="searchStr" name="searchStr" class="form-control input-inline input-sm" type="text" placeholder="${menu.titleAlias}"/>
			<button class="btn btn-sm green" onclick="search();"><i class="fa fa-search"></i> 搜索 </button>   
			<div class="btn-group">
	            <button data-toggle="dropdown" class="btn green dropdown-toggle" aria-expanded="false">排序
	                <i class="fa fa-angle-down"></i>
	            </button>
             </div>  
			<div class="btn-group">
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
             </div>                     	
        </div>
    </div>
    <div class="portlet-body">
        <table class="table table-striped table-bordered table-advance table-hover" id="tlist">
            <thead>
                <tr>
                    <th width="20%"> 类别</th>
                    <th width="30%"> 指标 </th>
                    <th width="50%"> 选择备选项及得分 </th>
                </tr>
            </thead>
            <tbody>
               	<tr >
               		<td rowspan="16" align="center"  style="vertical-align:middle;"  >物业信息</td>
               		<td>物业类别</td>
               		<td>
               			<select id="pageSelect" name="pageSelect_1" class="form-control input-large" >
							<option value="商住或loft公寓：2分" data="2" >商住或loft公寓</option>
							<option value="普通住宅：3分" data="3">普通住宅</option>
							<option value="洋房：4分" data="4">洋房</option>
							<option value="叠拼或联排：6分"data="6">叠拼或联排</option>
							<option value="独栋或类独栋：8分" data="8">独栋或类独栋</option>
	    				</select>
               		</td>
               	</tr>
               	<tr>
               		<td>家庭车辆总数</td>
               		<td>
               			<select id="pageSelect" name="pageSelect_1" class="form-control input-large" >
			    			        <option value="1辆:1分" data="1">1辆</option>
									<option value="2辆:2分" data="2">2辆</option>
									<option value="3辆及以上:3分" data="3">3辆及以上</option>
	    				</select>
               		</td>
               	</tr>
               	<tr>
               		<td>物业总户数</td>
               		<td>
               		<div class="col-md-5" style="padding-left:0px;"><input type="text" placeholder="物业总户数" name="wy_score" value="" class="form-control"> </div>	
               		</td>
               	</tr>
               	<tr>
               		<td>产权车位数</td>
               		<td>
               		<div class="col-md-5" style="padding-left:0px;"><input type="text" placeholder="产权车位数" name="wy_score" value="" class="form-control"> </div>	
               		</td>
               	</tr>
               	<tr>
               	 	<td>使用权车位数</td>
               		<td>
               		<div class="col-md-5" style="padding-left:0px;"><input type="text" placeholder="使用权车位数" name="wy_score" value="" class="form-control"> </div>	
               		</td>
               	</tr>
               	<tr>
               	    <td>楼盘周边停车位</td>
               		<td>
               		<div class="col-md-5" style="padding-left:0px;"><input type="text" placeholder="楼盘周边停车位" name="wy_score" value="" class="form-control"> </div>	
               		</td>
               	</tr>
               	<tr>
               		<td>配套资源</td>
               		<td>
               					<div class="col-md-9">
                                                    <div class="md-checkbox-inline">
                                                        <div class="md-checkbox">
                                                            <input type="checkbox" id="checkbox1_1" class="md-check"  data="1" >
                                                            <label for="checkbox1_1">
                                                                <span></span>
                                                                <span class="check"></span>
                                                                <span class="box"></span >农贸市场或大型生活超市</label>
                                                        </div>
                                                        <div class="md-checkbox">
                                                            <input type="checkbox" id="checkbox1_2" class="md-check" data="0.5" >
                                                            <label for="checkbox1_2">
                                                                <span></span>
                                                                <span class="check"></span>
                                                                <span class="box"></span>银行网点（不含自助银行）</label>
                                                        </div>
                                                        <div class="md-checkbox">
                                                            <input type="checkbox" id="checkbox1_3" class="md-check" data="2"   >
                                                            <label for="checkbox1_3">
                                                                <span></span>
                                                                <span class="check"></span>
                                                                <span class="box"></span>商业综合体</label>
                                                        </div>
                                                        <div class="md-checkbox">
                                                            <input type="checkbox" id="checkbox1_4" class="md-check" data="1"   >
                                                            <label for="checkbox1_4">
                                                                <span></span>
                                                                <span class="check"></span>
                                                                <span class="box"></span>图书馆、博物馆</label>
                                                        </div>
                                                        <div class="md-checkbox">
                                                            <input type="checkbox" id="checkbox1_5" class="md-check" data="2" >
                                                            <label for="checkbox1_5">
                                                                <span></span>
                                                                <span class="check"></span>
                                                                <span class="box"></span>社区以上级别的医疗机构</label>
                                                        </div>
                                                        <div class="md-checkbox">
                                                            <input type="checkbox" id="checkbox1_6" class="md-check" data="0.5"  >
                                                            <label for="checkbox1_6">
                                                                <span></span>
                                                                <span class="check"></span>
                                                                <span class="box"></span>市政公园</label>
                                                        </div>
                                                        <div class="md-checkbox">
                                                            <input type="checkbox" id="checkbox1_7" class="md-check" data="1"  >
                                                            <label for="checkbox1_7">
                                                                <span></span>
                                                                <span class="check"></span>
                                                                <span class="box"></span>零售餐饮</label>
                                                        </div>
                                                        
                                                    </div>
                                                </div>
               		</td>
               	</tr>
               	<tr>
               		<td>轨道交通</td>
               		<td>
               		 <select id="pageSelect" name="pageSelect_1" class="form-control input-large" >
			    			        <option value="已开通轨道线：4分" data="4"   >已开通轨道线</option>
									<option value="2年内开通：3分" data="3"  >2年内开通</option>
									<option value="4年内开通：2分" data="2"  >4年内开通</option>
									<option value="远期规划轨道线：1分" data="1"  >远期规划轨道线</option>
	    				</select>
               		</td>
               	</tr>
               	<tr>
               		<td>已通车公交线路</td>
               		<td>
               			<select id="pageSelect" name="pageSelect_1" class="form-control input-large" >
			    			        <option value="1条：1分" data="1"  >1条</option>
									<option value="2条：2分" data="2" >2条</ption>
									<option value="3条：3分" data="3" >3条</option>
									<option value="4条：4分" data="4" >4条</option>
									<option value="5条及以上：5分" data="5" >5条及以上</option>
	    				</select>
               		</td>
               	</tr>
               	<tr>
               		<td>开发资质</td>
               		<td>
               			<select id="pageSelect" name="pageSelect_1" class="form-control input-large" >
			    			<option value="上年度全国100强：3分"  data="3" >上年度全国100强</option>
							<option value="央企或当地知名房企且无负面信息：2分" data="2" >央企或当地知名房企且无负面信息</option>
							<option value="二级及以上开发资质：1.5分"  data="1.5" >二级及以上开发资质</option>
							<option value="有负面信息开发企业：0.5分" data="0.5" >有负面信息开发企业</option>
	    				</select>
               		</td>
               	</tr>
               	<tr>
               		<td>开发企业保障方式</td>
               		<td>
               			<select id="pageSelect" name="pageSelect_1" class="form-control input-large" >
			    			<option value="连带保证责任：5分" data="5" >连带保证责任</option>
							<option value="锁车位或其他协助管理：3分" data="3" >锁车位或其他协助管理</option>
							<option value="暂缓办理产权证：2分" data="2" >暂缓办理产权证</option>
	    				</select>
               		</td>
               	</tr>
               	<tr>
               		<td>车位面积</td>
               		<td>
               			<div class="col-md-5" style="padding-left:0px;"><input type="text" placeholder="车位面积" name="wy_score" value="" class="form-control"> </div>	
               		</td>
               	</tr>
               	<tr>
               		<td>车位类型</td>
               		<td>
               			<select id="pageSelect" name="pageSelect_1" class="form-control input-large" >
			    			<option value="自用：1分" data="1"  >自用</option>
							<option value="出租：0.5分" data="0.5"  >出租</option>
	    				</select>
               		</td>
               	</tr>
               	<tr>
               		<td>车辆排量</td>
               		<td>
               			<div class="col-md-5" style="padding-left:0px;"><input type="text" placeholder="车辆排量" name="wy_score" value="" class="form-control"> </div>	
               		</td>
               	</tr>
               	<tr>
               		<td>车辆进出频率</td>
               		<td>
               			<div class="col-md-5" style="padding-left:0px;"><input type="text" placeholder="车辆进出频率" name="wy_score" value="" class="form-control"> </div>	
               		</td>
               	</tr>
               	 <tr>
               		<td> 车牌号</td>
               		<td>
               			<select id="pageSelect" name="pageSelect_1" class="form-control input-large" >
			    		        <option value="三连号：1.5分" data="1.5" >三连号</option>
								<option value="顺号或重号：1分" data="1" >顺号或重号</option>
								<option value="普通号：0.5分" data="0.5" >普通号</option>
	    				</select>
               		</td>
               	</tr>
               	<tr>
               	 	<td  rowspan="4" align="center"  style="vertical-align:middle;"   >职业和收入</td>
               		<td>就职单位类型</td>
               		<td>
               			<select id="pageSelect" name="pageSelect_2" class="form-control input-large" >
			    		        <option value="行政、事业或金融企业：10分" data="10" >行政、事业或金融企业</option>
								<option value="央企、世界500强企业：9分" data="9" >央企、世界500强企业</option>
								<option value="一般国企：6分" data="6" >一般国企</option>
	    				</select>
               		</td>
               	</tr>
               	<tr>
               		<td>职级</td>
               		<td>
               			<select id="pageSelect" name="pageSelect_2" class="form-control input-large" >
			    		        <option value="股东或合伙人：5分" data="5" >股东或合伙人</option>
								<option value="处级以上或高管层（副总、总监以上）" data="3" >处级以上或高管层（副总、总监以上）</option>
								<option value="部门负责人：1.2分" data="1.2" >部门负责人</option>
								<option value="部门主管：1分" data="1" >部门主管</option>
								<option value="基层员工：0.8分" data="0.8" >基层员工</option>
	    				</select>
               		</td>
               	</tr>
               	<tr>
               		<td>连续工作时间</td>
               		<td>
               			<select id="pageSelect" name="pageSelect_2" class="form-control input-large" >
			    		       <option value="10年以上：4分" data="4">10年以上</option>
							   <option value="2年以下：1分" data="1">2年以下</option>
	    				</select>
               		</td>
               	</tr>
               	<tr>
               		<td>近6个月的平均月收入（以工资卡流水为准）</td>
               		<td><div class="col-md-5" style="padding-left:0px;"><input type="text" placeholder="近6个月的平均月收入" name="zysr_score" value="" class="form-control"> </div>	
               		</td>
               	</tr>
               	<tr>
               		<td rowspan="6"  align="center"  style="vertical-align:middle;"  >家庭及居住现状</td>
               		<td>性别</td>
               		<td>
               			<select id="pageSelect" name="pageSelect_3" class="form-control input-large" >
			    		       <option value="男：0分" data="0">男</option>
							   <option value="女：1分" data="1">女</option>
	    				</select>
               		</td>
               	</tr>
               	<tr>
               		<td>年龄</td>
               		<td>
               			<select id="pageSelect" name="pageSelect_3" class="form-control input-large" >
			    		        <option value="18-25：0.5分" data="0.5">18-25</option>
								<option value="26-35：1分" data="1">26-35</option>
								<option value="36-55：2分" data="1">36-55</option>
								<option value="55以上：1.5分" data="1.5">55以上</option>
	    				</select>
               		</td>
               	</tr>
               	<tr>
               		<td>婚姻状况</td>
               		<td>
               			<select id="pageSelect" name="pageSelect_3" class="form-control input-large" >
			    		        <option value="已婚有子女：5分" data="0.5">已婚有子女</option>
								<option value="已婚无子女：4分" data="4">已婚无子女</option>
								<option value="单身有子女：3分" data="3">单身有子女</option>
								<option value="单身或未婚：2分" data="2">单身或未婚</option>
	    				</select>
               		</td>
               	</tr>
               	<tr>
               		<td>文化程度</td>
               		<td>
               			<select id="pageSelect" name="pageSelect_3" class="form-control input-large" >
			    		     <option value="硕士研究生以上学历：3分" data="3">硕士研究生以上学历</option>
							 <option value="本科：2分" data="2">本科</option>
							 <option value="专科及以上：1分" data="1">专科及以上</option>
	    				</select>
               		</td>
               	</tr>
               	<tr>
               		<td>健康程度</td>
               		<td>
               			<select id="pageSelect" name="pageSelect_3" class="form-control input-large" >
			    		     <option value="近半年内有体检且无重疾：3分" data="3">近半年内有体检且无重疾</option>
							 <option value="近2年内有体检且无重疾：2分" data="2">近2年内有体检且无重疾</option>
							 <option value="近2年内有体检但状况不佳：1.5分" data="1.5">近2年内有体检但状况不佳</option>
							 <option value="近2年未参与体检：1分" data="1">近2年未参与体检</option>
	    				</select>
               		</td>
               	</tr>
               	<tr>
               		<td>现有住房条件</td>
               		<td>
               			<select id="pageSelect" name="pageSelect_3" class="form-control input-large" >
			    		     	<option value="单配或一居室：1分" data="1">单配或一居室</option>
								<option value="二居室：1.5分" data="1.5">二居室</option>
								<option value="三居及以上：2分" data="2">三居及以上</option>
	    				</select>
               		</td>
               	</tr>
               	<tr>
               		<td rowspan="4" align="center"  style="vertical-align:middle;"  >家庭信用记录</td>
               		<td>个人征信情况</td>
               		<td>
               			<select id="pageSelect" name="pageSelect_4" class="form-control input-large" >
			    		     	<option value="有贷款无逾期：6分" data="6">有贷款无逾期</option>
								<option value="无贷款记录：4分" data="4">无贷款记录</option>
								<option value="有贷款有逾期：3分" data="3">有贷款有逾期</option>
								<option value="累计逾期不超过3次且最长逾期为1期：1.8分" data="1.8">累计逾期不超过3次且最长逾期为1期</option>
								<option value="累计逾期不超过3次且最长逾期未超过2的：1.5分" data="0.5">累计逾期不超过3次且最长逾期未超过2的</option>
								<option value="累计超过3次且最长逾期未超过3的：1分" data="1">累计超过3次且最长逾期未超过3的</option>
								<option value="累计超过3次且最长逾期大于3的：0.3分" data="0.5">累计超过3次且最长逾期大于3的</option>
	    				</select>
               		</td>
               	</tr>
               	<tr>
               		<td>负面信用记录</td>
               		<td>
               			<select id="pageSelect" name="pageSelect_4" class="form-control input-large" >
			    		     	<option value="有担保人代偿记录：-0.5分" data="-0.5">有担保人代偿记录</option>
								<option value="信用卡被注销记录：-1分" data="-1">信用卡被注销记录</option>
								<option value="执行记录：-5分" data="-5">执行记录</option>
								<option value="涉案已结案：0.2分" data="0.2">涉案已结案</option>
								<option value="其他民事纠纷：0.4分" data="0.4">其他民事纠纷</option>
								<option value="债务纠纷：1分" data="1">债务纠纷</option>
						</select>
               		</td>
               	</tr>
               	<tr>
               		<td>社保信息相符</td>
               		<td>
               			<select id="pageSelect" name="pageSelect_4" class="form-control input-large" >
			    		     	<option value="相符：2分" data="2">相符</option>
								<option value="不相符但有其他证明：1分"  data="1">不相符但有其他证明</option>
						</select>
               		</td>
               	</tr>
               	<tr>
               		<td>芝麻信用</td>
               		<td>
               		<div class="col-md-5" style="padding-left:0px;"  ><input type="text" placeholder="芝麻信用" name="jtxy_score" value="" class="form-control"> </div>	
               		</td>
               	</tr>
            <!--  <tr>
               		<td></td>
               		<td>汇总</td>
               		<td>90</td>
               		<td id="dq_score">当前得分：0</td>
            </tr>  -->	
               	
            </tbody>
        </table>
        <div class="row">
	    	<div class="col-md-5 col-sm-5">
	    	</div>
	    	<div class="col-md-7 col-sm-7 text-right">
	    	</div>
    	</div>
    	
    	<div class="form-actions">
	            <div class="row">
	                <div class="col-md-offset-4 col-md-10">
	                    <button class="btn btn-lg blue" type="submit" id="subBtn">提  交</button>
	                    <a class="btn btn-lg grey-salsa" href="<c:url value="/admin/cms/content/get?mid=${menu.mid }" />">返回</a>
	                </div>
	            </div>
             </div>
    	
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

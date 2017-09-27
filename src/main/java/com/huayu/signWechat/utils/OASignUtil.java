package com.huayu.signWechat.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huayu.signWechat.domain.HrSignDepartment;
import com.huayu.signWechat.domain.HrSignDetail;
import com.huayu.signWechat.domain.HrSignUser;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * OA企业号接口开发 打卡
 * 详情见:https://work.weixin.qq.com/api/doc
 * @author yangjing
 *
 */
public class OASignUtil {

	/**
	 * 企业号获取token请求地址
	 * 详情见：https://work.weixin.qq.com/api/doc#10013/第三步：获取access_token
	 */
	private static String gettoken = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=CORPID&corpsecret=CORPSECRET";
	
	/**
	 * 获取OA企业号accessToken信息
	 * @return
	 */
	public static String getAccessToken(){
		InputStream resourceAsStream = OASignUtil.class.getClassLoader().getResourceAsStream("config/global.properties");
		Properties properties = new Properties();
		JSONObject tokenJson = null;
		try {
			properties.load(resourceAsStream);
			String oAcorpSecret = properties.getProperty("OAcorpSecret");
			String oaCorpID = properties.getProperty("OACorpID");
			String url = gettoken.replace("CORPID", oaCorpID).replace("CORPSECRET", oAcorpSecret);
			tokenJson = HttpClientUtil.httpRequest(url, "GET",null);
			if(0!=tokenJson.getIntValue("errcode")){
				return null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return tokenJson.getString("access_token");
	}

	/**
	 * 获取部门信息请求路径
	 */
	private static String deptUrl = "https://qyapi.weixin.qq.com/cgi-bin/department/list?id=&access_token=";
	/**
	 * 获取OA企业号部门信息
	 * 详情见：https://work.weixin.qq.com/api/doc#10093
	 * @return
	 */
	public static List<HrSignDepartment> getDepartmentList(){
		List<HrSignDepartment> deptList = new ArrayList<>();
		String token = getAccessToken();
		if(null==token){
			return deptList;
		}
		JSONObject deptJson = HttpClientUtil.httpRequest(deptUrl+token, "GET", null);
		if(!"ok".equals(deptJson.getString("errmsg"))){
			return deptList;
		}
		JSONArray deptArray = deptJson.getJSONArray("department");
		for(int i =0;i<deptArray.size();i++){
			JSONObject object = deptArray.getJSONObject(i);
			HrSignDepartment departments = JSON.toJavaObject(object, HrSignDepartment.class);
			System.out.println(departments.toString());
			deptList.add(departments);
		}
		return deptList;
	}
	
	/**
	 * 获取部门成员详情
	 * 详情见:https://work.weixin.qq.com/api/doc#10063
	 * 请求方式：GET（HTTPS）
	 * fetch_child	1/0：是否递归获取子部门下面的成员
	 * 请求地址：https://qyapi.weixin.qq.com/cgi-bin/user/list?access_token=ACCESS_TOKEN&department_id=DEPARTMENT_ID&fetch_child=FETCH_CHILD
	 */
	private static String deptUserUrl = "https://qyapi.weixin.qq.com/cgi-bin/user/list?access_token=ACCESS_TOKEN&department_id=DEMPID&fetch_child=0";
	
	/**
	 * 获取所有员工信息
	 * @param deptid
	 * @return
	 */
	public static List<HrSignUser> getUserList(String deptid){
		List<HrSignUser> userList = new ArrayList<HrSignUser>();
		String token = getAccessToken();
		if(null==token){
			return userList;
		}
		String url = deptUserUrl.replace("ACCESS_TOKEN", token);
		url = url.replace("DEMPID", deptid);
		JSONObject userJson = HttpClientUtil.httpRequest(url, "GET", null);
		if(!"ok".equals(userJson.getString("errmsg"))){
			return userList;
		}
		JSONArray userArray = userJson.getJSONArray("userlist");
		for(int i =0;i<userArray.size();i++){
			JSONObject object = userArray.getJSONObject(i);
			HrSignUser user = JSON.toJavaObject(object, HrSignUser.class);
//			System.out.println(user.toString());
			userList.add(user);
		}
		return userList;
	}
	
	/**
	 * 签到信息请求路径
	 */
	private static String souqurl = "https://qyapi.weixin.qq.com/cgi-bin/checkin/getcheckindata?access_token=";
	/**
	 * 获取所有员工打卡信息
	 * 详情见:https://work.weixin.qq.com/api/doc#11196
	 * @param start 获取打卡记录的开始时间。UTC时间戳
	 * @param end 获取打卡记录的结束时间。UTC时间戳
	 * @param userIdList 员工id集合
	 * @param opencheckindatatype 打卡类型。1：上下班打卡；2：外出打卡；3：全部打卡
	 * @return
	 */
	public static List<HrSignDetail> getSignList(Date start, Date end, List<String> userIdList, Integer opencheckindatatype){
		List<HrSignDetail> SignList =new ArrayList<HrSignDetail>();
		String token = getAccessToken();
		if (null == token) {
			return SignList;
		}
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("opencheckindatatype", opencheckindatatype);
		map.put("starttime", start.getTime()/1000);
		map.put("endtime", end.getTime()/1000);
		map.put("useridlist", userIdList);
		JSONObject jsonObject1 = HttpClientUtil.httpRequest(souqurl+token, "POST", JSON.toJSONString(map));
		String errmsg = jsonObject1.getString("errmsg");
		if("ok".equals(errmsg)){
			JSONArray jsonArr = jsonObject1.getJSONArray("checkindata");
			for(int i =0 ;i<jsonArr.size();i++){
				JSONObject json = jsonArr.getJSONObject(i);
				HrSignDetail sign = JSON.toJavaObject(json, HrSignDetail.class);
				SignList.add(sign);
//				System.out.println(sign.toString());
			}
		}
		return SignList;
	}

	public static void getSignList() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<HrSignDepartment> departmentList = getDepartmentList();
		List<HrSignDetail> hrSignDetails = new ArrayList<>();	//所有考勤记录
		List<HrSignUser> users = new ArrayList<>();		//所有用户
		for (HrSignDepartment d : departmentList) {
			Integer deptid = d.getId();
			List<HrSignUser> userList = getUserList(deptid+"");
			users.addAll(userList);
		}
		List<String> userIdList = new ArrayList<>();
		for (int i = 0;i< users.size();i++) {
			HrSignUser hrSignUser = users.get(i);
			if (i == users.size()-1 || userIdList.size() == 100) {
				Date startdate = sdf.parse("2017-09-25 00:00:00");
				Date enddate = sdf.parse("2017-09-25 23:00:00");
				List<HrSignDetail> signList = getSignList(startdate, enddate, userIdList, 1);
				hrSignDetails.addAll(signList);
				userIdList.clear();
			}
			userIdList.add(hrSignUser.getUserid());
		}
	}
	
	public static void main(String[] args) throws ParseException {
		/*List<HrSignDepartment> departmentList = getDepartmentList();
		for (HrSignDepartment d : departmentList) {
			System.out.println(d.getId() + " -- "+d.getName());
		}*/
		getUserList("1");
//		getSignList();
//		getAccessToken();
		/*StringBuilder sql = new StringBuilder();
		sql.append("SELECT * from hr_employee WHERE empName = :empName");
		String mobileStr = "18183062150,18183062151,";
		if (StringUtils.isNotBlank(mobileStr)) {
			sql.append(" and (mobile = :mobile");
			String[] mobiles = mobileStr.split(",|，");
			for (int i = 0;i<mobiles.length;i++) {
				String s = mobiles[i];
				if (StringUtils.isNotBlank(s)) {
					if (i == mobiles.length - 1) {
						sql.append(" or mobile="+ s+")");
					} else {
						sql.append(" or mobile="+ s);
					}
				}
			}
		} else {
			sql.append(" and mobile = :mobile");
		}
		System.out.println(sql.toString());*/
//		System.out.println(DateTimeUtil.convertStringToDate("2017-09-26 04:00:00",DateTimeUtil.YYYY_MM_DD_HH_MM_SS).getTime()/1000);
//		System.out.println(DateTimeUtil.convertStringToDate("2017-09-27 04:00:00",DateTimeUtil.YYYY_MM_DD_HH_MM_SS).getTime()/1000);

	}
}

package com.huayu.common;


import com.alibaba.fastjson.JSON;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class BaseResult<T> implements Serializable{

	private static final long serialVersionUID = 8758272932002721530L;

	protected  Log log =LogFactory.getLog(this.getClass());
	
	private String rcode;
	private String rmsg;
	private T rdata;
	private Map<String,Object> rdataMap ;


	public void setRcode(String rcode) {
		this.rcode = rcode;
	}

	public void setRmsg(String rmsg) {
		this.rmsg = rmsg;
	}

	public void setRdata(T rdata) {
		this.rdata = rdata;
	}

	public void initRdataMap(){
		this.rdataMap = new HashMap<>();
	}

	public BaseResult putRdataMap(String key, Object object){
		this.rdataMap.put(key,object);
		return this;
	}

	public Map<String, Object> getRdataMap() {
		return rdataMap;
	}

	public Object getRdataMapValue(String key) {
		return rdataMap.get(key);
	}

	public boolean isContainsKey(String key){
		return this.rdataMap.containsKey(key);
	}

	public boolean isContainsValue(String value){
		return this.rdataMap.containsValue(value);
	}

	public boolean isEmptyMap(){
		return CollectionUtils.isEmpty(rdataMap);
	}

	public void setRdataMap(Map<String, Object> rdataMap) {
		this.rdataMap = rdataMap;
	}

	public void putAllRdataMap(Map<String, Object> map){
		this.rdataMap.putAll(map);
	}

	public T getRdata() {
		return rdata;
	}

	public String getRcode() {
		return rcode;
	}

	public String getRmsg() {
		return rmsg;
	}


	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

	public static BaseResult initBaseResult(boolean initRdataMap){
		BaseResult result = initBaseResult();
		if (initRdataMap){
			result.rdataMap = new HashMap<>();
		}
		return result;
	}

	public static BaseResult initBaseResult(){

		return new BaseResult();
	}

	public static BaseResult initBaseResult(String rmsg){

		return new BaseResult(rmsg);
	}

    public BaseResult(){
        this.rcode = Constants.R_OPERATION_FAILED;
        this.rmsg = Constants.R_OPERATION_FAILED_STR;
    }

	public BaseResult(String rmsg){
		this.rcode = Constants.R_OPERATION_FAILED;
		this.rmsg = rmsg;
	}

	public BaseResult(String rcode, String rmsg){
		this.rcode = rcode;
		this.rmsg = rmsg;
	}
	
	/**
	 * 缺少参数
	 */
	public void setArgumentsMiss(){
		this.rcode = Constants.R_ARGUMENTS_MISS;
		this.rmsg = Constants.R_ARGUMENTS_MISS_STR;
	}
	
	/**
	 * 参数类型错误
	 */
	public void setArgumentsTypeErro(){
		this.rcode = Constants.R_ARGUMENTS_TYPEERROR;
		this.rmsg = Constants.R_ARGUMENTS_TYPEERROR_STR;
	}
	
	/**
	 * 操作成功
	 */
	public BaseResult setSuccess(){
		this.rcode = Constants.R_OPERATION_SUCCESS;
		this.rmsg = Constants.R_OPERATION_SUCCESS_STR;
		return this;
	}
	
	/**
	 * 操作失败
	 */
	public void setFail(){
		this.rcode = Constants.R_OPERATION_FAILED;
		this.rmsg = Constants.R_OPERATION_FAILED_STR;
	}

	public void setFail(String msg){
		this.rcode = Constants.R_OPERATION_FAILED;
		this.rmsg = msg;
	}
	
	/**
	 * 服务器错误
	 */
	public void setServerErrorCode(){
		this.rcode = Constants.R_SERVER_ERROR;
		this.rmsg = Constants.R_SERVER_ERROR_STR;
	}
	
	public boolean isSuccess(){
		if(Constants.R_OPERATION_SUCCESS.equals(this.rcode)){
			return true;
		}else{
			return false;
		}
	}
	public boolean isFail(){
		if(Constants.R_OPERATION_FAILED.equals(this.rcode)){
			return true;
		}else{
			return false;
		}
	}
	
}

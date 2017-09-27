package com.huayu.a.service;

import java.util.Date;

import com.huayu.a.dao.CommLogDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huayu.a.domain.CommLog;

/**
 * desc：公共日志service
 * ref ：
 * user：刘咏
 * date：2016/4/27
 * time：11:49
 */
@Service
public class CommLogService {

	@Autowired
	private CommLogDao commLogDao;

	/***
	 * 查找一个
	 * @param logId Long
	 * @return CommLog
     */
	public CommLog getOne(Long logId) {
		CommLog entity = new CommLog();
		entity.setLogId(logId);
		entity.setIdName("logId");
		return commLogDao.getOne(entity);
	}

	/***
	 * 分页查询所有数据
	 * @param entity CommLog
	 * @param pageable Pageable
     * @return Page
     */
	public Page<CommLog> get(CommLog entity,Pageable pageable) {
		//String sql = "select * from comm_log where locate(:username,username)>0 or locate(:username,logIp)>0 or locate(:username,DATE_FORMAT(logDate,'%Y-%m-%d %T'))>0";
		StringBuilder sb = new StringBuilder();
		sb.append("select * from comm_log");
		if(entity!=null && entity.getUsername()!= null) {
			sb.append(" where locate(:username,username)>0 or locate(:username,logIp)>0 or locate(:username,DATE_FORMAT(logDate,'%Y-%m-%d %T'))>0");
		}
		return commLogDao.get(sb.toString(),entity, pageable);
	}

	/***
	 * 增加
	 * @param entity CommLog
	 * @return int
     */
	@Transactional
	public int post(CommLog entity) {
		entity.setType("access");
		entity.setLogId(commLogDao.getKey("comm_log", entity));
		entity.setLogDate(new Date());
		return commLogDao.post(entity);
	}

	/***
	 * 修改
	 * 
	 * @param entity CommLog
	 * @return int
	 */

	public int update(CommLog entity) {
		entity.setIdName("logId");
		return commLogDao.put(entity);
	}

	/***
	 * 删除
	 * @param logId Long
	 * @return int
     */
	public int delete(Long logId) {
		CommLog entity = new CommLog();
		entity.setLogId(logId);
		entity.setIdName("logId");
		return commLogDao.delete("logId", entity);
	}

    /**
     * 发送短信日志
     * @param url String
     * @param mobile String
     * @param content String
     * @param retContent String
     * void
     */
    @Transactional
    public void smsLog(String url,String mobile,String content,String retContent){
        CommLog commLog = new CommLog();
        commLog.setLogId(commLogDao.getKey("comm_log", commLog));
        commLog.setType("sms");
        commLog.setLogUrl(url);
        commLog.setLogIp(content);
        commLog.setLogData(retContent);
        commLog.setUserId(0L);
        commLog.setUsername(mobile);
        commLog.setLogDate(new Date());
        commLogDao.post(commLog);
    }

}

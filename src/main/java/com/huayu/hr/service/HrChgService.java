package com.huayu.hr.service;

import com.alibaba.fastjson.JSON;
import com.huayu.common.BaseResult;
import com.huayu.common.BusinessException;
import com.huayu.common.ThreadManager;
import com.huayu.common.tool.DateTimeUtil;
import com.huayu.hr.dao.*;
import com.huayu.hr.domain.*;
import com.huayu.hr.web.args.HrChgArgs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * 人员变动
 * Created by DengPeng on 2017/2/22.
 */
@Service
public class HrChgService {

    private static Logger logger = LoggerFactory.getLogger(HrChgService.class);

    @Autowired
    private HrEmployeePsnChgDao psnChgDao;

    @Autowired
    private HrEmployeeOutdutyDao outdutyDao;

    @Autowired
    private HrEmployeeIndutyDao indutyDao;

    @Autowired
    private HrEmployeeJobDao empJobDao;

    @Autowired
    private HrEmployeeDao employeeDao;

    @Autowired
    private HrResumeInfoDao resumeInfoDao;

    @Autowired
    private HrRemindDao hrRemindDao;

    @Autowired
    private OrgService orgService;

    @Autowired
    private HrJobPlaitDao hrJobPlaitDao;

    /**
     * 批量调动
     * @param args
     */
    @Transactional(propagation= Propagation.NOT_SUPPORTED)
    public void postChgBatch(HrChgArgs args){
        if (StringUtils.hasText(args.getEmpObjArrStr())){
            List<HrChgArgs> list = JSON.parseArray(args.getEmpObjArrStr(), HrChgArgs.class);
            if (list.size()>0){
                HrChgService _this = this;
                Date now = new Date();
                ExecutorService executorService = ThreadManager.getInstance().getExecutorService(5);
                HrChgService proxy = (HrChgService) AopContext.currentProxy();
                List<Callable<String>> futureList = new ArrayList<>();
                for (int i=0;i<list.size();i++){
                    HrChgArgs tmp = list.get(i);
                    BeanUtils.copyProperties(args,tmp,"empId");
                    HrEmployeeJob oldJobInfo = empJobDao.getEmployeeJobAndCompInfo(new HrEmployeeJob(tmp.getEmpId()));
                    if (null == oldJobInfo){
                        throw new BusinessException("获取原工作信息失败");
                    }
                    futureList.add(()->{
                        try {
                            proxy.postChg(proxy, _this, oldJobInfo, tmp, now);
                            return null;
                        } catch (Exception e) {
                            return oldJobInfo.getEmpName()+":"+e.getMessage();
                        }
                    });
                }
                try {
                    List<Future<String>> futures = executorService.invokeAll(futureList);
                    executorService.shutdown();
                    StringBuilder errorMsg = new StringBuilder();
                    for(int i=0; i<futures.size(); i++){
                        String s = futures.get(i).get();
                        if (null!=s){
                            errorMsg.append(s).append(",");
                        }
                    }
                    if (errorMsg.length()>0){
                        errorMsg.delete(errorMsg.length()-1,errorMsg.length());
                        throw new BusinessException(errorMsg.toString());
                    }
                } catch (InterruptedException e) {
                    throw new BusinessException(e.getMessage());
                } catch (ExecutionException e) {
                    throw new BusinessException(e.getMessage());
                }
            }
        }
    }

    @Transactional
    public void postChg(HrChgService proxy, HrChgService _this, HrEmployeeJob oldJobInfo, HrChgArgs tmp, Date now){
        HrEmployeeJob newJobInfo = proxy.handleEmpJob(_this,oldJobInfo,tmp);
        proxy.handleInduty(_this,newJobInfo,oldJobInfo,tmp,now);
        proxy.handleChg(_this,newJobInfo.getId(),oldJobInfo,tmp,now);
        proxy.handleOther(_this,newJobInfo,oldJobInfo,tmp,now);
    }


    @Transactional
    HrEmployeeJob handleEmpJob(HrChgService _this, HrEmployeeJob oldJobInfo, HrChgArgs args){
        if (DateTimeUtil.isAfter(args.getChgTime(), DateTimeUtil.stringToDateTime("yyyy-MM-dd","2017-09-01").toDate())) {
            if (oldJobInfo.getCompany() == 1033) {
                HrJobPlait jobPlait = new HrJobPlait();
                jobPlait.setYear(DateTimeUtil.getYear(args.getChgTime()));
                jobPlait.setMonth(DateTimeUtil.getMonth(args.getChgTime()));
                jobPlait.setEmpId(args.getEmpId());
                jobPlait.setJobId(args.getJob());
                orgService.occupyJobPlait(jobPlait);//占用编制

                jobPlait.setJobId(oldJobInfo.getJob());
                orgService.cancelOccupyJobPlait(jobPlait);//取消原有编制占用
            }
        }

        Integer oldEmpGroup =oldJobInfo.getEmpGroup();
        Date now = new Date();
        oldJobInfo.setUpdatetime(now);
        oldJobInfo.setOnGuard(0);
        oldJobInfo.setEmpGroup(9);//修改原来的工作记录为调出人员
        oldJobInfo.setEndTime(args.getChgTime());
        if (_this.empJobDao.offGuard(oldJobInfo)!=1){
            throw new BusinessException("修改工作记录失败");
        }
        HrEmployeeJob newJobInfo = new HrEmployeeJob();
        newJobInfo.setEmpId(args.getEmpId());
        newJobInfo.setEmpNo(oldJobInfo.getEmpNo());
        newJobInfo.setEmpGroup(oldEmpGroup);
        newJobInfo.setCompany(args.getCompany());
        newJobInfo.setDepartment(args.getDepartment());
        newJobInfo.setJob(args.getJob());
        newJobInfo.setDutyLevel(args.getDutyLevel());
        newJobInfo.setJobSequence(oldJobInfo.getJobSequence());
        newJobInfo.setStartTime(args.getChgTime());
        newJobInfo.setRecruitmentSource(oldJobInfo.getRecruitmentSource());
        newJobInfo.setWorkAddress(oldJobInfo.getWorkAddress());
        newJobInfo.setTurnFormalDate(oldJobInfo.getTurnFormalDate());
        newJobInfo.setOnGuard(1);
        newJobInfo.setIsFormal(oldJobInfo.getIsFormal());
        newJobInfo.setCreatetime(now);
        if (_this.empJobDao.post(newJobInfo)!=1){
            throw new BusinessException("添加工作记录失败");
        }
        return newJobInfo;
    }

    @Transactional
    Integer handleInduty(HrChgService _this, HrEmployeeJob newJobInfo, HrEmployeeJob oldJobInfo, HrChgArgs args, Date now){
        HrEmployeeInduty oldIndutyOne = _this.indutyDao.getIndutyOne(new HrEmployeeInduty(args.getEmpId(),oldJobInfo.getId()));
        if (null==oldIndutyOne){
            throw new BusinessException("未找到入职记录");
        }
        oldIndutyOne.setEnddate(args.getChgTime());
        oldIndutyOne.setOnGuard(0);
        oldIndutyOne.setChgCause(args.getChgReason());
        oldIndutyOne.setChgType(args.getChgType());
        oldIndutyOne.setUpdatetime(now);
        if (_this.indutyDao.updateIndutyInfoOne(oldIndutyOne)!=1){
            throw new BusinessException("修改入职记录失败");
        }

        HrEmployeeInduty newIndutyOne = new HrEmployeeInduty();
        newIndutyOne.setBegindate(args.getChgTime());
        newIndutyOne.setCompany(args.getCompany());
        newIndutyOne.setDepartment(args.getDepartment());
        newIndutyOne.setDutyLevel(args.getDutyLevel());
        newIndutyOne.setJobSequence(oldJobInfo.getJobSequence());
        newIndutyOne.setJob(args.getJob());
        newIndutyOne.setEmpId(args.getEmpId());
        newIndutyOne.setEmpGroup(newJobInfo.getEmpGroup());
        newIndutyOne.setEmpJobId(newJobInfo.getId());
        newIndutyOne.setOnGuard(1);
        newIndutyOne.setPosttype(2);//调入
        newIndutyOne.setChgCause(args.getChgReason());
        newIndutyOne.setChgType(args.getChgType());
        newIndutyOne.setChgStatus(args.getChgStatus());
        newIndutyOne.setChgNote(args.getDescription());
        if (_this.indutyDao.post(newIndutyOne)!=1){
            throw new BusinessException("入职记录失败");
        }
        return newIndutyOne.getId();
    }

    @Transactional
    void handleChg(HrChgService _this, Integer newJobId, HrEmployeeJob oldJobInfo, HrChgArgs args, Date now){
        HrEmployeePsnChg oldChgIn = _this.psnChgDao.getPsnChgInOne(new HrEmployeePsnChg(args.getEmpId()));
        if (null!=oldChgIn){
            oldChgIn.setEndDate(args.getChgTime());
            oldChgIn.setLastFlag(1);
            if (_this.psnChgDao.endEmpChg(oldChgIn)!=1){
                throw new BusinessException("修改原始记录失败");
            }
        }

        HrEmployeePsnChg psnChg = new HrEmployeePsnChg();
        psnChg.setCreatetime(now);
        psnChg.setEmpId(args.getEmpId());
        psnChg.setBeginDate(oldJobInfo.getStartTime());
        psnChg.setChgCause(args.getChgReason());
        psnChg.setChgDate(args.getChgTime());
        psnChg.setChgType(args.getChgType());
        psnChg.setChgState(args.getChgStatus());

        psnChg.setLastFlag(1);
        psnChg.setEndDate(args.getChgTime());
        psnChg.setChgFlag(2);//调出
        psnChg.setCompany(oldJobInfo.getCompany());
        psnChg.setDepartment(oldJobInfo.getDepartment());
        psnChg.setRelaCompanyName(args.getCompanyName());
        psnChg.setEmpJobId(oldJobInfo.getId());
        if (_this.psnChgDao.post(psnChg)!=1){
            throw new BusinessException("添加调出记录失败");
        }
        psnChg.setLastFlag(0);
        psnChg.setEndDate(null);
        psnChg.setChgFlag(1);//调入
        psnChg.setBeginDate(args.getChgTime());
        psnChg.setCompany(args.getCompany());
        psnChg.setDepartment(args.getDepartment());
        psnChg.setRelaCompanyName(oldJobInfo.getCompanyName());
        psnChg.setEmpJobId(newJobId);
        if (_this.psnChgDao.post(psnChg)!=1){
            throw new BusinessException("添加调入记录失败");
        }
    }

    @Transactional
    void handleOther(HrChgService _this, HrEmployeeJob newJobInfo, HrEmployeeJob oldJobInfo, HrChgArgs args, Date now){
        if (args.getChgType()==1){
            HrEmployeeOutduty outduty = new HrEmployeeOutduty();
            outduty.setLeavedate(args.getChgTime());
            outduty.setCompanyBefore(oldJobInfo.getCompany());
            outduty.setCompanyAfter(args.getCompany());
            outduty.setEmpId(args.getEmpId());
            outduty.setEmpJobId(oldJobInfo.getId());
            outduty.setDeptAfter(args.getDepartment());
            outduty.setDeptBefore(oldJobInfo.getDepartment());
            outduty.setJobBefore(oldJobInfo.getJob());
            outduty.setEmpGroupBefore(newJobInfo.getEmpGroup());
            outduty.setEmpGroupAfter(oldJobInfo.getEmpGroup());
            outduty.setType(8);//垮公司调动
            outduty.setReason(args.getChgReason());
            outduty.setDescription(args.getDescription());
            outduty.setCreatetime(now);
            if (_this.outdutyDao.post(outduty)!=1){
                throw new BusinessException("添加离职记录失败");
            }
        }
        HrEmployee employee = new HrEmployee(args.getCompany(),args.getDepartment());
        employee.setId(args.getEmpId());
        employee.setLastEmpJobId(newJobInfo.getId());
        if (_this.employeeDao.updateEmployeeWorkInfo(employee)!=1){
            throw new BusinessException("更新所在个人信息公司部门失败");
        }
        HrResumeInfo resumeInfo = new HrResumeInfo(args.getEmpId());
        resumeInfo.setStartTime(oldJobInfo.getStartTime());
        resumeInfo.setEndTime(oldJobInfo.getEndTime());
        resumeInfo.setCompany(oldJobInfo.getCompanyName());
        resumeInfo.setDuty(oldJobInfo.getJobName());
        resumeInfo.setLeaveReason("跨公司调动");
        resumeInfo.setStatus(0);
        resumeInfo.setCreatetime(new Date());
        if (_this.resumeInfoDao.addOne(resumeInfo)!=1){
            throw new BusinessException("添加个人履历记录失败");
        }
    }


    /**
     *  已经被上面的批量调动替代
     * 人员调动
     * @param args
     * @return
     */
    @Transactional
    @Deprecated
    public void postChg(HrChgArgs args) {
        HrEmployeeJob oldJobInfo = empJobDao.getEmployeeJobAndCompInfo(new HrEmployeeJob(args.getEmpId()));
        Integer oldEmpGroup =oldJobInfo.getEmpGroup();
        Date now = new Date();
        oldJobInfo.setUpdatetime(now);
        oldJobInfo.setOnGuard(0);
        oldJobInfo.setEmpGroup(9);//修改原来的工作记录为调出人员
        oldJobInfo.setEndTime(args.getChgTime());
        if (empJobDao.offGuard(oldJobInfo)!=1){
            throw new BusinessException("修改工作记录失败");
        }

        HrEmployeeJob newJobInfo = new HrEmployeeJob();
        newJobInfo.setEmpId(args.getEmpId());
        newJobInfo.setEmpNo(oldJobInfo.getEmpNo());
        newJobInfo.setEmpGroup(oldEmpGroup);
        newJobInfo.setCompany(args.getCompany());
        newJobInfo.setDepartment(args.getDepartment());
        newJobInfo.setJob(args.getJob());
        newJobInfo.setDutyLevel(args.getDutyLevel());
        newJobInfo.setJobSequence(oldJobInfo.getJobSequence());
        newJobInfo.setStartTime(args.getChgTime());
        newJobInfo.setRecruitmentSource(oldJobInfo.getRecruitmentSource());
        newJobInfo.setWorkAddress(oldJobInfo.getWorkAddress());
        newJobInfo.setTurnFormalDate(oldJobInfo.getTurnFormalDate());
        newJobInfo.setOnGuard(1);
        newJobInfo.setIsFormal(oldJobInfo.getIsFormal());
        newJobInfo.setCreatetime(now);
        if (empJobDao.post(newJobInfo)!=1){
            throw new BusinessException("添加工作记录失败");
        }

        HrEmployeeInduty oldIndutyOne = indutyDao.getIndutyOne(new HrEmployeeInduty(args.getEmpId(),oldJobInfo.getId()));
        if (null==oldIndutyOne){
            throw new BusinessException("未找到入职记录");
        }
        oldIndutyOne.setEnddate(args.getChgTime());
        oldIndutyOne.setOnGuard(0);
        oldIndutyOne.setChgCause(args.getChgReason());
        oldIndutyOne.setChgType(args.getChgType());
        oldIndutyOne.setUpdatetime(now);
        if (indutyDao.updateIndutyInfoOne(oldIndutyOne)!=1){
            throw new BusinessException("修改入职记录失败");
        }

        HrEmployeeInduty newIndutyOne = new HrEmployeeInduty();
        newIndutyOne.setBegindate(args.getChgTime());
        newIndutyOne.setCompany(args.getCompany());
        newIndutyOne.setDepartment(args.getDepartment());
        newIndutyOne.setDutyLevel(args.getDutyLevel());
        newIndutyOne.setJobSequence(oldJobInfo.getJobSequence());
        newIndutyOne.setJob(args.getJob());
        newIndutyOne.setEmpId(args.getEmpId());
        newIndutyOne.setEmpGroup(newJobInfo.getEmpGroup());
        newIndutyOne.setEmpJobId(newJobInfo.getId());
        newIndutyOne.setOnGuard(1);
        newIndutyOne.setChgCause(args.getChgReason());
        newIndutyOne.setChgType(args.getChgType());
        newIndutyOne.setChgStatus(args.getChgStatus());
        newIndutyOne.setChgNote(args.getDescription());
        if (indutyDao.post(newIndutyOne)!=1){
            throw new BusinessException("入职记录失败");
        }

        HrEmployeePsnChg oldChgIn = psnChgDao.getPsnChgInOne(new HrEmployeePsnChg(args.getEmpId()));
        if (null!=oldChgIn){
            oldChgIn.setEndDate(args.getChgTime());
            oldChgIn.setLastFlag(1);
            if (psnChgDao.endEmpChg(oldChgIn)!=1){
                throw new BusinessException("修改原始记录失败");
            }
        }

        HrEmployeePsnChg psnChg = new HrEmployeePsnChg();
        psnChg.setCreatetime(now);
        psnChg.setEmpId(args.getEmpId());
        psnChg.setBeginDate(args.getChgTime());
        psnChg.setChgCause(args.getChgReason());
        psnChg.setChgDate(args.getChgTime());
        psnChg.setChgType(args.getChgType());
        psnChg.setChgState(args.getChgStatus());

        psnChg.setLastFlag(1);
        psnChg.setEndDate(args.getChgTime());
        psnChg.setChgFlag(2);//调出
        psnChg.setCompany(oldJobInfo.getCompany());
        psnChg.setDepartment(oldJobInfo.getDepartment());
        psnChg.setRelaCompanyName(args.getCompanyName());
        psnChg.setEmpJobId(oldJobInfo.getId());
        if (psnChgDao.post(psnChg)!=1){
            throw new BusinessException("添加调出记录失败");
        }

        psnChg.setLastFlag(0);
        psnChg.setEndDate(null);
        psnChg.setChgFlag(1);//调入
        psnChg.setCompany(args.getCompany());
        psnChg.setDepartment(args.getDepartment());
        psnChg.setRelaCompanyName(oldJobInfo.getCompanyName());
        psnChg.setEmpJobId(newIndutyOne.getId());
        if (psnChgDao.post(psnChg)!=1){
            throw new BusinessException("添加调入记录失败");
        }

        if (args.getChgType()==1){
            HrEmployeeOutduty outduty = new HrEmployeeOutduty();
            outduty.setLeavedate(args.getChgTime());
            outduty.setCompanyBefore(oldJobInfo.getCompany());
            outduty.setCompanyAfter(args.getCompany());
            outduty.setEmpId(args.getEmpId());
            outduty.setEmpJobId(oldJobInfo.getId());
            outduty.setDeptAfter(args.getDepartment());
            outduty.setDeptBefore(oldJobInfo.getDepartment());
            outduty.setJobBefore(oldJobInfo.getJob());
            outduty.setEmpGroupBefore(oldEmpGroup);
            outduty.setEmpGroupAfter(oldJobInfo.getEmpGroup());
            outduty.setType(8);
            outduty.setReason(args.getChgReason());
            outduty.setDescription(args.getDescription());
            outduty.setCreatetime(now);
            if (outdutyDao.post(outduty)!=1){
                throw new BusinessException("添加离职记录失败");
            }
        }
        HrEmployee employee = new HrEmployee(args.getCompany(),args.getDepartment());
        employee.setId(args.getEmpId());
        employee.setLastEmpJobId(newJobInfo.getId());
        if (employeeDao.updateEmployeeWorkInfo(employee)!=1){
            throw new BusinessException("更新所在个人信息公司部门失败");
        }
        HrResumeInfo resumeInfo = new HrResumeInfo(args.getEmpId());
        resumeInfo.setStartTime(oldJobInfo.getStartTime());
        resumeInfo.setEndTime(oldJobInfo.getEndTime());
        resumeInfo.setCompany(oldJobInfo.getCompanyName());
        resumeInfo.setDuty(oldJobInfo.getJobName());
        resumeInfo.setLeaveReason("跨公司调动");
        resumeInfo.setStatus(0);
        resumeInfo.setCreatetime(new Date());
        if (resumeInfoDao.addOne(resumeInfo)!=1){
            throw new BusinessException("添加个人履历记录失败");
        }
    }

    public List<HrEmployeePsnChg> getPsnChgList(HrChgArgs args){
        HrEmployeePsnChg entity = new HrEmployeePsnChg();
        entity.setEmpId(args.getEmpId());
        return psnChgDao.getPsnChgList(entity);
    }


    public Page<HrEmployeePsnChg> getEmpChgData(HrChgArgs args, Pageable pageable) {
        HrEmployeePsnChg psnChg = new HrEmployeePsnChg();
        psnChg.setCompany(args.getCompany());
        psnChg.setDepartment(args.getDepartment());
        return psnChgDao.getEmpChgData(psnChg,pageable);
    }

    public List<HrEmployeeInduty> getPsnIndutyList(Integer empId){
        HrEmployeeInduty entity = new HrEmployeeInduty();
        entity.setEmpId(empId);
        return indutyDao.getPsnIndutyList(entity);
    }


    public HrChgArgs buildEmpOutDuty(){
        String sql = "SELECT e.id empId,CONCAT(c.`name`,'-',d.`name`,'-',e.empName) name,c.id company,d.id department " +
                " FROM sheet1 s INNER JOIN hr_employee e ON s.empId = e.id INNER JOIN hr_company c ON c.id = e.company INNER JOIN hr_department d ON e.department = d.id INNER JOIN hr_employee_job ej ON ej.empId = s.empId where ej.onGuard = 1";
        List<Map<String, Object>> maps = indutyDao.get(sql, new HashMap<>());
        HrChgArgs args = new HrChgArgs();
        args.setChgReason(1);
        args.setChgTime(DateTimeUtil.stringToDateTime("yyyy-MM-dd","2016-12-31").toDate());
        args.setChgType(4);
        args.setDescription("组织架构变化");
        args.setEmpGroup(7);
        args.setEmpObjArrStr(JSON.toJSONString(maps));
        return args;
    }

    public void postOutDutyBatch(HrChgArgs args) {
        if (StringUtils.hasText(args.getEmpObjArrStr())){
            List<HrChgArgs> list = JSON.parseArray(args.getEmpObjArrStr(), HrChgArgs.class);
            if (list.size()>0){
                HrChgService proxy = (HrChgService) AopContext.currentProxy();
                HrChgService _this = this;
                ExecutorService executorService = ThreadManager.getInstance().getExecutorService(5);
                for (int i=0;i<list.size();i++){
                    HrChgArgs tmp = list.get(i);
                    BeanUtils.copyProperties(args,tmp,"company","department","empId");
                    executorService.execute(() -> {
                        try {
                            proxy.postOutDuty(_this,tmp);
                        } catch (Exception e) {
                            logger.error("empId:{}",tmp.getEmpId(),e);
                            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        }
                    });
                }
                executorService.shutdown();
            }
        }
    }

    @Transactional
    public void postOutDuty(HrChgService _this, HrChgArgs args) {
        HrEmployeeJob oldJobInfo = _this.empJobDao.getEmployeeJobAndCompInfo(new HrEmployeeJob(args.getEmpId()));
        if (DateTimeUtil.isAfter(args.getChgTime(), DateTimeUtil.stringToDateTime("yyyy-MM-dd","2017-09-01").toDate())) {
            if (oldJobInfo.getCompany() == 1033) {
                HrJobPlait jobPlait = new HrJobPlait();
                jobPlait.setYear(DateTimeUtil.getYear(args.getChgTime()));
                jobPlait.setMonth(DateTimeUtil.getMonth(args.getChgTime()));
                jobPlait.setEmpId(args.getEmpId());
                jobPlait.setJobId(oldJobInfo.getJob());
                if (_this.hrJobPlaitDao.cancelOccupyJobPlait(jobPlait) != 1) {
                    throw new BusinessException("取消占用编制失败");
                }
            }
        }
        Date now = new Date();
        oldJobInfo.setUpdatetime(now);
        oldJobInfo.setOnGuard(0);
        oldJobInfo.setEmpGroup(args.getEmpGroup());
        oldJobInfo.setEndTime(args.getChgTime());
        if (_this.empJobDao.offGuard(oldJobInfo)!=1){
            throw new BusinessException("修改工作记录失败");
        }
        HrEmployeeInduty oldIndutyOne = _this.indutyDao.getIndutyOne(new HrEmployeeInduty(args.getEmpId(),oldJobInfo.getId()));
        if (null==oldIndutyOne){
            throw new BusinessException("未找到入职记录");
        }
        //TODO 调动记录
        oldIndutyOne.setEnddate(args.getChgTime());
        oldIndutyOne.setOnGuard(0);
        oldIndutyOne.setChgCause(args.getChgReason());
        oldIndutyOne.setChgType(args.getChgType());
        oldIndutyOne.setUpdatetime(now);
        if (_this.indutyDao.updateIndutyInfoOne(oldIndutyOne)!=1){
            throw new BusinessException("修改入职记录失败");
        }
        HrEmployeeOutduty outduty = new HrEmployeeOutduty();
        outduty.setLeavedate(args.getChgTime());
        outduty.setCompanyBefore(args.getCompany());
        outduty.setEmpId(args.getEmpId());
        outduty.setEmpJobId(oldJobInfo.getId());
        outduty.setDeptAfter(args.getDepartment());
        outduty.setDeptBefore(oldJobInfo.getDepartment());
        outduty.setJobBefore(oldJobInfo.getJob());
        outduty.setEmpGroupBefore(oldJobInfo.getEmpGroup());
        outduty.setEmpGroupAfter(args.getEmpGroup());
        outduty.setReason(args.getChgReason());
        outduty.setDescription(args.getDescription());
        outduty.setType(args.getChgType());
        outduty.setCreatetime(now);
        if (_this.outdutyDao.post(outduty)!=1){
            throw new BusinessException("添加离职记录失败");
        }
    }




    public List<HrEmployeeOutduty> getPsnOutdutyList(Integer empId, Integer company){
        HrEmployeeOutduty entity = new HrEmployeeOutduty();
        entity.setEmpId(empId);
        entity.setCompanyBefore(company);
        return outdutyDao.getPsnOutdutyList(entity);
    }

    @Transactional
    public BaseResult postHireBack(HrEmployeeJob employeeJob) {
        BaseResult baseResult = BaseResult.initBaseResult();
        if (DateTimeUtil.isAfter(employeeJob.getStartTime(), DateTimeUtil.stringToDateTime("yyyy-MM-dd","2017-09-01").toDate())) {
            if (employeeJob.getCompany() == 1033) {
                HrJobPlait jobPlait = new HrJobPlait();
                jobPlait.setYear(DateTimeUtil.getYear(employeeJob.getStartTime()));
                jobPlait.setMonth(DateTimeUtil.getMonth(employeeJob.getStartTime()));
                jobPlait.setEmpId(employeeJob.getEmpId());
                jobPlait.setJobId(employeeJob.getJob());
                if (hrJobPlaitDao.occupyJobPlait(jobPlait) != 1) {
                    throw new BusinessException("该岗位编制数不足");
                }
            }
        }
        employeeJob.setCreatetime(new Date());
        if (empJobDao.post(employeeJob)!=1){
            throw new BusinessException("添加工作记录失败");
        }
        Integer empJobId = employeeJob.getId();
        HrEmployeeInduty induty = new HrEmployeeInduty();
        BeanUtils.copyProperties(employeeJob,induty);
        induty.setEmpJobId(empJobId);
        induty.setBegindate(employeeJob.getStartTime());
        if (indutyDao.post(induty)!=1){
            throw new BusinessException("添加入职记录失败");
        }
        HrEmployee employee = new HrEmployee(employeeJob.getCompany(),employeeJob.getDepartment());
        employee.setId(employeeJob.getEmpId());
        employee.setLastEmpJobId(empJobId);
        if (employeeDao.updateEmployeeWorkInfo(employee)!=1){
            throw new BusinessException("更新个人信息所在公司部门失败");
        }
        baseResult.setSuccess();
        return baseResult;
    }

    @Transactional
    public BaseResult postTurnFormal(HrChgArgs chgArgs) {
        BaseResult baseResult = BaseResult.initBaseResult();
        HrEmployeeJob entity = new HrEmployeeJob();
        entity.setId(chgArgs.getLastEmpJobId());
        entity.setEmpGroup(1);
        entity.setTurnFormalDate(chgArgs.getChgTime());
        entity.setIsFormal(1);
        entity.setFormalType(chgArgs.getChgType());
        entity.setUpdatetime(new Date());
        entity.setEmpId(chgArgs.getEmpId());
        if (empJobDao.turnFormal(entity)==1){
            baseResult.setSuccess();
        }
        HrRemind remind = new HrRemind();
        remind.setType(2);
        remind.setEmpId(chgArgs.getEmpId());
        HrRemind remindData = hrRemindDao.getRemindDataOne(remind);
        if (null!=remindData){
            if (hrRemindDao.deleteData(remindData)!=1){
                throw new BusinessException("删除提醒数据失败");
            }
        }
        return baseResult;
    }
}

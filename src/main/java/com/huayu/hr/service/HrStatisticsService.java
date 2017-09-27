package com.huayu.hr.service;

import com.huayu.a.dao.CommSequenceDao;
import com.huayu.a.domain.CommSequence;
import com.huayu.a.service.tools.SpringSecurityUtil;
import com.huayu.common.BaseResult;
import com.huayu.common.BusinessException;
import com.huayu.common.ThreadManager;
import com.huayu.common.tool.Authority;
import com.huayu.common.tool.DateTimeUtil;
import com.huayu.common.tool.ExcelOperateUtil;
import com.huayu.common.tool.ExcelUtil;
import com.huayu.hr.dao.HrEmployeeDao;
import com.huayu.hr.dao.HrStatisticsDataDao;
import com.huayu.hr.dao.HrStatisticsEmpChgDataDao;
import com.huayu.hr.dao.HrStatisticsEmpChgDataDetailDao;
import com.huayu.hr.domain.*;
import com.huayu.hr.web.args.HrStatisticsArgs;
import com.huayu.user.domain.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * Created by DengPeng on 2017/1/12.
 */
@Service
public class HrStatisticsService {

    private Logger logger = LoggerFactory.getLogger(HrStatisticsService.class);

    @Autowired
    private HrEmployeeDao employeeDao;

    @Autowired
    private HrStatisticsDataDao statisticsDataDao;

    @Autowired
    private HrStatisticsEmpChgDataDao statisticsEmpChgDataDao;

    @Autowired
    private HrStatisticsEmpChgDataDetailDao statisticsEmpChgDataDetailDao;

    @Autowired
    private OrgService orgService;

    @Autowired
    private HrStatisticsCompanyBindService bindService;

    @Autowired
    private CommSequenceDao commSequenceDao;

    @Autowired
    private HrDictService hrDictService;


    /**
     * 人力资源盘点表（管理类）
     */
    @Transactional
    public BaseResult statisticsManager(Integer company, boolean forceStatistics){
        List<HrDepartment> departments = orgService.getAllDepartments(company);
        ExecutorService executorService = ThreadManager.getInstance().getExecutorService(5);
        BaseResult baseResult;
        try{
            String yyyy = DateTimeUtil.dateTimeToString(DateTimeUtil.YYYY);
            String mm = DateTimeUtil.dateTimeToString(DateTimeUtil.MM);
            baseResult = statisticsManager(executorService, company, departments, forceStatistics,yyyy,mm,new Date());
        }finally {
            executorService.shutdown();
        }
        return baseResult;
    }

    @Transactional
    public BaseResult statisticsManager(ExecutorService executorService, Integer company, List<HrDepartment> departments, boolean forceStatistics, String yyyy, String mm, Date now ){
        HrStatisticsService _this = this;
        BaseResult baseResult = BaseResult.initBaseResult();
        HrStatisticsService proxy = (HrStatisticsService) AopContext.currentProxy();
        if (forceStatistics){
            proxy.removeHrStatisticsData(0,company,yyyy,mm);
        }
        if (!CollectionUtils.isEmpty(departments)){
            List<Callable<HrStatisticsData>> mTaskList= new ArrayList<>();
            for (int i = 0; i < departments.size(); i++) {
                HrDepartment hrDepartment = departments.get(i);
                Callable<HrStatisticsData> mCallable = () -> {
                    logger.info("开始统计管理类,公司：{},部门：{}", company, hrDepartment.getId());
                    return _this.statisticsBaseInfo(0, null, 5, company, hrDepartment.getId(), yyyy, mm);
                };
                mTaskList.add(mCallable);
            }
            List<Future<HrStatisticsData>>  mFutures;
            try {
                mFutures = executorService.invokeAll(mTaskList);
                proxy.saveStatistics(0,_this,mFutures,now,company);
            } catch (InterruptedException e) {
                throw new BusinessException(e.getMessage());
            } catch (ExecutionException e) {
                throw new BusinessException(e.getMessage());
            }
            baseResult.setSuccess();
        }else{
            baseResult.setRmsg("没有获取到当前公司下属部门，无法统计！");
        }
        return baseResult;
    }


    /**
     * 人力资源盘点表（工人类）
     */
    @Transactional
    public BaseResult statisticsWorker(ExecutorService executorService, Integer company, List<HrDepartment> departments, boolean forceStatistics, String yyyy, String mm, Date now){
        HrStatisticsService _this = this;
        BaseResult baseResult = BaseResult.initBaseResult();
        HrStatisticsService proxy = (HrStatisticsService) AopContext.currentProxy();
        if (forceStatistics){
            proxy.removeHrStatisticsData(1,company,yyyy,mm);
        }
        if (!CollectionUtils.isEmpty(departments)){
            List<Callable<HrStatisticsData>> mTaskList= new ArrayList<>();
            for (int i = 0; i < departments.size(); i++) {
                HrDepartment hrDepartment = departments.get(i);
                Callable<HrStatisticsData> mCallable = () -> {
                    logger.info("开始统计工人类,公司：{},部门：{}", company, hrDepartment.getId());
                    return _this.statisticsBaseInfo(1, null, 6, company, hrDepartment.getId(), yyyy, mm);
                };
                mTaskList.add(mCallable);
            }
            List<Future<HrStatisticsData>>  mFutures;
            try {
                mFutures = executorService.invokeAll(mTaskList);
                proxy.saveStatistics(1,_this,mFutures,now,company);
            } catch (InterruptedException e) {
                throw new BusinessException(e.getMessage());
            } catch (ExecutionException e) {
                throw new BusinessException(e.getMessage());
            }
            baseResult.setSuccess();
        }else{
            baseResult.setRmsg("没有获取到当前公司下属部门，无法统计！");
        }
        return baseResult;
    }


    @Transactional
    public BaseResult statisticsWorker(Integer company, boolean forceStatistics){
        List<HrDepartment> departments = orgService.getAllDepartments(company);
        ExecutorService executorService = ThreadManager.getInstance().getExecutorService(5);
        BaseResult baseResult;
        try{
            String yyyy = DateTimeUtil.dateTimeToString(DateTimeUtil.YYYY);
            String mm = DateTimeUtil.dateTimeToString(DateTimeUtil.MM);
            baseResult = statisticsWorker(executorService, company, departments, forceStatistics,yyyy,mm,new Date());
        }finally {
            executorService.shutdown();
        }
        return baseResult;
    }


    /**
     *
     * 人力资源盘点表（职级现状盘点）
     */
    @Transactional
    public BaseResult statisticsDutyLevel(Integer company, boolean forceStatistics){
        ExecutorService executorService = ThreadManager.getInstance().getExecutorService(5);
        BaseResult baseResult;
        try{
            String yyyy = DateTimeUtil.dateTimeToString(DateTimeUtil.YYYY);
            String mm = DateTimeUtil.dateTimeToString(DateTimeUtil.MM);
            baseResult = statisticsDutyLevel(executorService, company, forceStatistics,yyyy,mm,new Date());
        }finally {
            executorService.shutdown();
        }
        return baseResult;
    }


    @Transactional
    public BaseResult statisticsDutyLevel(ExecutorService executorService, Integer company, boolean forceStatistics, String yyyy, String mm, Date now){
        BaseResult baseResult = BaseResult.initBaseResult();
        HrStatisticsService _this = this;
        HrStatisticsService proxy = (HrStatisticsService) AopContext.currentProxy();
        if (forceStatistics){
            proxy.removeHrStatisticsData(2,company,yyyy,mm);
        }
        final Integer[] dutyLevel = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        Integer len = dutyLevel.length;
        List<Callable<HrStatisticsData>> taskList= new ArrayList<>();
        for(int i=0; i<len; i++){
            Integer t = dutyLevel[i];
            Callable<HrStatisticsData> dtCallable = () -> {
                logger.info("开始统计职级, 公司：{},职级：{}", company,t);
                return  _this.statisticsBaseInfo(2, t,null, company,null, yyyy, mm);
            };
            taskList.add(dtCallable);
        }
        try {
            List<Future<HrStatisticsData>> dtFutures = executorService.invokeAll(taskList);
            proxy.saveStatistics(2,_this,dtFutures,now,company);
        } catch (InterruptedException e) {
            throw new BusinessException(e.getMessage());
        } catch (ExecutionException e) {
            throw new BusinessException(e.getMessage());
        }
        return baseResult.setSuccess();
    }

    @Transactional
    void saveStatistics(Integer statisticsType, HrStatisticsService _this, List<Future<HrStatisticsData>> mFutures, Date now, Integer companyId) throws ExecutionException, InterruptedException {
        Long[] ids = _this.commSequenceDao.getKey(new CommSequence("hr_statistics_data", mFutures.size()));
        SqlParameterSource[] mSqlParameterSources = new SqlParameterSource[mFutures.size()];
        for(int i=0; i<mFutures.size(); i++){
            HrStatisticsData hrStatisticsData = mFutures.get(i).get();
            hrStatisticsData.setId(ids[0].intValue() + i + 1);
            hrStatisticsData.setCreatetime(now);
            mSqlParameterSources[i]=new BeanPropertySqlParameterSource(hrStatisticsData);
        }
        int[] flags = _this.statisticsDataDao.batchInsertStatisticsData(mSqlParameterSources);
        for (int i=0;i<flags.length;i++) {
            if (flags[i] == 0) {
                if (statisticsType ==0){
                    logger.error("批量添加管理类统计结果失败，公司Id:{}");
                    throw new BusinessException("批量添加管理类统计结果失败，公司Id:"+companyId);
                }else if (statisticsType ==1){
                    logger.error("批量添加工人类统计结果失败，公司Id:{}",companyId);
                    throw new BusinessException("批量添加工人类统计结果失败,公司Id:"+companyId);
                }else{
                    logger.error("批量添加职级统计结果失败，公司Id:{}，名称：{}",companyId);
                    throw new BusinessException("批量添加职级统计结果失败，公司：:"+companyId);
                }
            }
        }
    }

    public List<HrStatisticsData> getStatisticsData(HrStatisticsData statisticsData){
        List<HrStatisticsData> data = statisticsDataDao.getStatisticsData(statisticsData);
        if (statisticsData.getStatisticsType()==0 || statisticsData.getStatisticsType()==1) {
            Map<Integer, HrStatisticsData> dataMap = new LinkedHashMap<>();
            data.forEach(e -> {
                dataMap.put(e.getId(), e);
            });
            return buildStatisticsDataTree(0, data);
        }else{
            return data;
        }
    }

    private List<HrStatisticsData> buildStatisticsDataTree(Integer parentId, List <HrStatisticsData> dataMap) {
        List<HrStatisticsData> list = new ArrayList<>();
        for(HrStatisticsData data : dataMap) {
            if (!data.isContain()){
                if (parentId.equals(data.getDepartmentPid())){
                    data.setContain(true);
                    list.add(data);
                }
            }
        }
        if (!CollectionUtils.isEmpty(list)){
            for (HrStatisticsData data : list){
                if (data.getIsParent()==1){
                    List<HrStatisticsData> child = buildStatisticsDataTree(data.getDepartment(),dataMap);
                    HrStatisticsData hrStatisticsData = countStatisticsData(child);
                    addData(data,hrStatisticsData);
                    data.setChildStatisticsData(child);
                }
            }
        }
        return list;
    }

    public HrStatisticsData countStatisticsData(List<HrStatisticsData> list){
        HrStatisticsData result = new HrStatisticsData();
        for(HrStatisticsData data : list){
            result.addSum(data.getSum());
            result.addEdu1(data.getEdu1());
            result.addEdu2(data.getEdu2());
            result.addEdu3(data.getEdu3());
            result.addEdu4(data.getEdu4());
            result.addAge1(data.getAge1());
            result.addAge2(data.getAge2());
            result.addAge3(data.getAge3());
            result.addAge4(data.getAge4());
            result.addAge5(data.getAge5());
            result.addCtime1(data.getCtime1());
            result.addCtime2(data.getCtime2());
            result.addCtime3(data.getCtime3());
            result.addCtime4(data.getCtime4());
            result.addCtime5(data.getCtime5());
            result.addMale(data.getMale());
            result.addFemale(data.getFemale());
        }
        return result;
    }

    public HrStatisticsData countBindStatisticsData(List<HrStatisticsCompanyBind> list){
        HrStatisticsData result = new HrStatisticsData();
        for(HrStatisticsCompanyBind tmp : list){
            HrStatisticsData data = tmp.getStatisticsData();
            result.addSum(data.getSum());
            result.addEdu1(data.getEdu1());
            result.addEdu2(data.getEdu2());
            result.addEdu3(data.getEdu3());
            result.addEdu4(data.getEdu4());
            result.addAge1(data.getAge1());
            result.addAge2(data.getAge2());
            result.addAge3(data.getAge3());
            result.addAge4(data.getAge4());
            result.addAge5(data.getAge5());
            result.addCtime1(data.getCtime1());
            result.addCtime2(data.getCtime2());
            result.addCtime3(data.getCtime3());
            result.addCtime4(data.getCtime4());
            result.addCtime5(data.getCtime5());
            result.addMale(data.getMale());
            result.addFemale(data.getFemale());
        }
        return result;
    }

    /**
     * 获取所有盘点数据
     * @param type
     * @param year
     * @param month
     * @param regexp
     * @return
     */
    public List<HrStatisticsData> getAllStatisticsData(Integer type, String year, String month, String regexp){
        HrStatisticsData sum = new HrStatisticsData();
        sum.setStatisticsType(type);
        sum.setYear(year);
        sum.setMonth(month);
        sum.setAuthorityRegexp(regexp);
        return statisticsDataDao.getStatisticsDataSum(sum);
    }





    /**
     * 统计 人力资源人员异动情况
     */
    @Transactional
    public BaseResult startStatisticsChange(Integer company, boolean forceStatistics, String year, String month) {
        String yyyy = DateTimeUtil.dateTimeToString(DateTimeUtil.YYYY);
        String mm = DateTimeUtil.dateTimeToString(DateTimeUtil.MM);
        if (StringUtils.isNotBlank(year)&&StringUtils.isNotBlank(month)){
            if (Integer.valueOf(yyyy+mm)<Integer.valueOf(year+month)){
                throw new BusinessException("您选择的时间超过了当前时间");
            }else{
                yyyy = year;
                mm = month;
            }
        }
        ExecutorService executorService = ThreadManager.getInstance().getExecutorService(5);
        Date now = new Date();
        StringBuilder stringBuilder = new StringBuilder();
        try{
            statisticsChComp(executorService,company,yyyy,mm,now,stringBuilder);
        }finally {
            executorService.shutdown();
        }
        BaseResult baseResult = BaseResult.initBaseResult();
        if (stringBuilder.length()==0){
            baseResult.setSuccess();
        }else{
            stringBuilder.insert(0,"未获取到上一月统计的数据公司（部门）：");
            baseResult.setRmsg(stringBuilder.toString());
        }
        return baseResult;
    }


    /**
     * 单行统计
     * 人力资源盘点表（管理类）
     * 人力资源盘点表（工人类）
     * 人力资源盘点表（职级现状盘点）
     * 统计职级现状盘点传入dutyLevel参数，其他两个参数不传
     * 统计其他两个报表时传入 companyCode 和  jobSequence，dutyLevel不传
     * @param dutyLevel
     * @param jobSequence
     */
    @Transactional
    public HrStatisticsData statisticsBaseInfo(Integer statisticsType, Integer dutyLevel, Integer jobSequence, Integer company, Integer department, String yyyy, String mm){
        HrStatisticsData statisticsData = new HrStatisticsData();
        statisticsData.setStatisticsType(statisticsType);
        statisticsData.setCompany(company);
        statisticsData.setDepartment(department);
        statisticsData.setDutyLevel(dutyLevel);
        statisticsData.setStatus(0);

        Integer integer = countOnDuty(dutyLevel, jobSequence,company,department);
        statisticsData.setSum(integer);

        Integer[] integers = countEdu(dutyLevel,jobSequence,company,department);
        statisticsData.setEdu1(integers[0]);
        statisticsData.setEdu2(integers[1]);
        statisticsData.setEdu3(integers[2]);
        statisticsData.setEdu4(integers[3]);

        HrStatisticsData ageData = statisticsDataDao.countAge(dutyLevel, jobSequence, company, department);
        statisticsData.setAge1(ageData.getAge1());
        statisticsData.setAge2(ageData.getAge2());
        statisticsData.setAge3(ageData.getAge3());
        statisticsData.setAge4(ageData.getAge4());
        statisticsData.setAge5(ageData.getAge5());

        HrStatisticsData ctimeData = statisticsDataDao.countCompTime(dutyLevel, jobSequence, company, department);
        statisticsData.setCtime1(ctimeData.getCtime1());
        statisticsData.setCtime2(ctimeData.getCtime2());
        statisticsData.setCtime3(ctimeData.getCtime3());
        statisticsData.setCtime4(ctimeData.getCtime4());
        statisticsData.setCtime5(ctimeData.getCtime5());

        HrStatisticsData sexData = statisticsDataDao.countSex(dutyLevel, jobSequence, company, department);
        statisticsData.setMale(sexData.getMale());
        statisticsData.setFemale(sexData.getFemale());
        statisticsData.setYear(yyyy);
        statisticsData.setMonth(mm);
        return statisticsData;
    }


    /**
     * 单行统计
     * 统计人员异动
     */
    @Transactional
    public HrStatisticsEmpChgData statisticsBaseEmpTrans(HrStatisticsService proxy, Integer dataId, Integer companyId, Integer department, String year, String month){
        HrStatisticsEmpChgData data = new HrStatisticsEmpChgData();
        data.setId(dataId);
        data.setCompany(companyId);
        data.setDepartment(department);
        data.setYear(year);
        data.setMonth(month);
        data.setStatus(0);
        HrStatisticsEmpChgData old = new HrStatisticsEmpChgData();
        old.setCompany(companyId);
        old.setDepartment(department);
        String[] ym = DateTimeUtil.getLastMonth(Integer.parseInt(year), Integer.parseInt(month));
        old.setYear(ym[0]);
        old.setMonth(ym[1]);

        HrStatisticsEmpChgData one = statisticsEmpChgDataDao.getStatisticsDataOne(old);
        if (null==one){
            logger.error("未获取到上一月({}-{})统计的数据，公司ID:{}，部门ID:{}",year,month,companyId,department);
            return null;
        }
        data.setBeginOnDutyM(one.getOnDutyM());
        data.setBeginOnDutyW(one.getOnDutyW());

        List<HrStatisticsEmpChgDataDetail> chgDataDetails = new ArrayList<>();
        int[] inDutyMW = proxy.countInDutyMW(proxy, chgDataDetails, dataId,companyId , department, year,month);
        data.setInDutyM(inDutyMW[0]);
        data.setInDutyGatherM(inDutyMW[1]);
        data.setInDutyW(inDutyMW[2]);

        int[] outDutyMW = proxy.countOutDutyMW(proxy, chgDataDetails, dataId, companyId, department, year,month);
        data.setOutDutyM(outDutyMW[0]);
        data.setOutDutyGatherM(outDutyMW[1]);
        data.setOutDutyW(outDutyMW[2]);

        int[] turnInMW = proxy.countCompChgInMW(proxy, chgDataDetails, dataId, companyId, department, year, month);
        data.setTurnInCompM(turnInMW[0]);
        data.setTurnInCompW(turnInMW[1]);
        data.setTurnInDeptM(turnInMW[2]);
        data.setTurnInDeptW(turnInMW[3]);

        int[] turnOutMW = proxy.countCompChgOutMW(proxy, chgDataDetails, dataId, companyId, department, year, month);
        data.setTurnOutCompM(turnOutMW[0]);
        data.setTurnOutCompW(turnOutMW[1]);
        data.setTurnOutDeptW(turnOutMW[2]);
        data.setTurnOutDeptW(turnOutMW[3]);


        if (chgDataDetails.size()>0){
            data.setChgDataDetails(chgDataDetails);
        }

        data.setOnDutyM(one.getOnDutyM()+inDutyMW[0]-outDutyMW[0]+turnInMW[0]+turnInMW[2]-turnOutMW[0]-turnOutMW[2]);
        data.setOnDutyW(one.getOnDutyW()+inDutyMW[2]-outDutyMW[2]+turnInMW[1]+turnInMW[3]-turnOutMW[1]-turnOutMW[3]);
        int[] pcc = proxy.countOnDutyPCC(companyId, department);
        data.setPractice(pcc[0]);
        data.setChannel(pcc[1]);
        data.setCarM(pcc[2]);
        return data;
    }

    public List<HrStatisticsEmpChgData> getStatisticsChangeData(HrStatisticsEmpChgData statisticsData) {
        List<HrStatisticsEmpChgData> data = statisticsEmpChgDataDao.getStatisticsData(statisticsData);
        Map<Integer, HrStatisticsEmpChgData> dataMap = new LinkedHashMap<>();
        data.forEach(e -> {
            dataMap.put(e.getId(), e);
        });
        return buildChgDataTree(0,dataMap);
    }

    private List<HrStatisticsEmpChgData> buildChgDataTree(Integer parentId, Map<Integer,HrStatisticsEmpChgData> dataMap) {
        List<HrStatisticsEmpChgData> list = new ArrayList<>();
        for(HrStatisticsEmpChgData data : dataMap.values()) {
            if (!data.isContain()){
                if (parentId.equals(data.getDepartmentPid())){
                    data.setContain(true);
                    list.add(data);
                }
            }
        }
        if (!CollectionUtils.isEmpty(list)){
            for (HrStatisticsEmpChgData data : list){
                if (data.getIsParent()==1){
                    List<HrStatisticsEmpChgData> child = buildChgDataTree(data.getDepartment(),dataMap);
                    HrStatisticsEmpChgData hrStatisticsData = countChgStatisticsData(child);
                    addData(data,hrStatisticsData);
                    data.setChildChgData(child);
                }
            }
        }
        return list;
    }

    public List<HrStatisticsEmpChgData> getStatisticsAllChangeData(HrStatisticsEmpChgData statisticsData) {
        User user = SpringSecurityUtil.getUser();
        Authority authority = user.getDataAuthorityMap();
        statisticsData.setAuthorityRegexp(Authority.getAuthority(authority,user.getCurrCompanyCode()).authoritiesRegexp());
        return statisticsEmpChgDataDao.getAllStatisticsChgData(statisticsData);
    }

    public HrStatisticsEmpChgData countChgStatisticsData(List<HrStatisticsEmpChgData> list){
        HrStatisticsEmpChgData result = new HrStatisticsEmpChgData();
        for(HrStatisticsEmpChgData data : list){
            addData(result,data);
        }
        return result;
    }


    public HrStatisticsEmpChgData countBindChgStatisticsData(List<HrStatisticsCompanyBind> list){
        HrStatisticsEmpChgData result = new HrStatisticsEmpChgData();
        for(HrStatisticsCompanyBind tmp : list){
            HrStatisticsEmpChgData data = tmp.getStatisticsChgData();
            addData(result,data);
        }
        return result;
    }

    /**
     * 在职人数
     * @return
     */
    @Transactional
    public Integer countOnDuty(Integer dutyLevel,Integer jobSequence,Integer company,Integer department){
        return statisticsDataDao.countOnDuty(dutyLevel, jobSequence, company, department).intValue();
    }

    @Transactional
    public int[] countOnDutyPCC(Integer company,Integer department){
        List<HrStatisticsData> list = statisticsDataDao.countOnDutyPCC(company, department);
        Integer countP = 0;
        Integer countCH = 0;
        Integer countCA = 0;
        for (int i  = 0; i<list.size();i++){
            HrStatisticsData hrStatisticsData = list.get(i);
            Integer jobSequence = hrStatisticsData.getJobSequence();
            if (null!=jobSequence){
                if (jobSequence== 7){
                    countP+=hrStatisticsData.getSum();
                }else if (jobSequence == 8){
                    countCH+=hrStatisticsData.getSum();
                }else if (jobSequence == 9){
                    countCA+=hrStatisticsData.getSum();
                }
            }
        }
        return new int[]{countP,countCH,countCA};
    }

    /**
     * 学历统计
     * @return
     */
    @Transactional
    public Integer[] countEdu(Integer dutyLevel,Integer jobSequence, Integer company,Integer department){
        Integer[] countData = new Integer[4];
        List<HrEmployee> lv1 = employeeDao.countEdu(1, dutyLevel, jobSequence, company, department);
        countData[0] = lv1.size();
        List<HrEmployee> lv2 = employeeDao.countEdu(2, dutyLevel, jobSequence, company, department);
        countData[1] = lv2.size();
        List<HrEmployee> lv3 = employeeDao.countEdu(3, dutyLevel, jobSequence, company, department);
        countData[2] = lv3.size();
        List<HrEmployee> lv4 = employeeDao.countEdu(4, dutyLevel, jobSequence, company, department);
        countData[3] = lv4.size();
        return countData;
    }


    /**
     * 入职统计
     * @return
     */
    @Transactional
    public int[] countInDutyMW(HrStatisticsService proxy, List<HrStatisticsEmpChgDataDetail> sqlParameterSources, Integer statisticsId, Integer companyId, Integer department, String year, String month){
        List<HrEmployee> hrEmployees = employeeDao.countInDutyMW(companyId, department, year+month);
        Integer countM = 0;
        Integer countW = 0;
        Integer gatherM = 0;
        for(int i=0, size=hrEmployees.size(); i<size; i++){
            HrEmployee emp = hrEmployees.get(i);
            if (null!=emp.getDutyLevelId() && emp.getDutyLevelId()<=8){
                gatherM++;
            }
            HrStatisticsEmpChgDataDetail dataDetail = buildDetailData(emp, statisticsId, 0, companyId, department, year, month);
            sqlParameterSources.add(dataDetail);
            Integer jobSequence = emp.getJobSequence();
            if (null!=jobSequence){
                if (jobSequence == 5){
                    countM++;
                }else {
                    countW++;
                }
            }else{
                logger.error("该人员（{}）没有岗位序列，为纳入统计！！",emp.getId());
            }
        }
        return new int[]{countM, gatherM,countW};
    }

    private int[] countInDutyMW(Integer companyId,Integer department,String year, String month){
        List<HrEmployee> hrEmployees = employeeDao.countInDutyMW(companyId, department, year+month);
        Integer countM = 0;
        Integer countW = 0;
        Integer gatherM = 0;
        for(int i=0, size=hrEmployees.size(); i<size; i++){
            HrEmployee emp = hrEmployees.get(i);
            if (null!=emp.getDutyLevelId() && emp.getDutyLevelId()<=8){
                gatherM++;
            }
            Integer jobSequence = emp.getJobSequence();
            if (null!=jobSequence){
                if (jobSequence == 5){
                    countM++;
                }else {
                    countW++;
                }
            }else{
                logger.error("该人员（{}）没有岗位序列，为纳入统计！！",emp.getId());
            }
        }
        return new int[]{countM, gatherM,countW};
    }


    /**
     * 离职统计
     * @return
     */
    @Transactional
    public int[] countOutDutyMW(HrStatisticsService proxy, List<HrStatisticsEmpChgDataDetail> sqlParameterSources, Integer statisticsId, Integer companyId, Integer department, String year, String month){
        List<HrEmployee> hrEmployees = employeeDao.countOutDutyMW(companyId, department,  year+month);
        Integer countM = 0;
        Integer countW = 0;
        Integer gatherM = 0;
        for(int i=0, size=hrEmployees.size(); i<size; i++){
            HrEmployee emp =  hrEmployees.get(i);
            if (null!=emp.getDutyLevelId() && emp.getDutyLevelId()<=8){
                gatherM++;
            }
            HrStatisticsEmpChgDataDetail dataDetail = buildDetailData(emp, statisticsId,1, companyId, department, year,month);
            sqlParameterSources.add(dataDetail);
            Integer jobSequence = emp.getJobSequence();
            if (null!=jobSequence){
                if (jobSequence == 5){
                    countM++;
                }else {
                    countW++;
                }
            }else{
                logger.error("该人员（{}）没有岗位序列，为纳入统计！！",emp.getId());
            }
        }
        return new  int[] {countM,gatherM,countW};
    }

    private  int[] buildOutDutyMW(Integer companyId,Integer department,String year, String month){
        List<HrEmployee> hrEmployees = employeeDao.countOutDutyMW(companyId, department,  year+month);
        Integer countM = 0;
        Integer countW = 0;
        Integer gatherM = 0;
        for(int i=0, size=hrEmployees.size(); i<size; i++){
            HrEmployee emp =  hrEmployees.get(i);
            if (null!=emp.getDutyLevelId() && emp.getDutyLevelId()<=8){
                gatherM++;
            }
            Integer jobSequence = emp.getJobSequence();
            if (null!=jobSequence){
                if (jobSequence == 5){
                    countM++;
                }else {
                    countW++;
                }
            }else{
                logger.error("该人员（{}）没有岗位序列，为纳入统计！！",emp.getId());
            }
        }
        return new  int[] {countM,gatherM,countW};
    }


    /**
     * 跨公司调动（调入）统计
     * @return
     */
    @Transactional
    public int[] countCompChgInMW(HrStatisticsService proxy, List<HrStatisticsEmpChgDataDetail> sqlParameterSources, Integer statisticsId, Integer companyId, Integer department, String year, String month){
        List<HrEmployee> hrEmployees = employeeDao.countCompChgInMW(companyId, department, year+month);
        Integer countM = 0;
        Integer countW = 0;
        Integer innerCountM = 0;
        Integer innerCountW = 0;
        if (hrEmployees.size()>0){
            for(int i=0, size=hrEmployees.size(); i<size; i++){
                HrEmployee emp = hrEmployees.get(i);
                if (emp.getChgType()==1){//跨公司调动
                    HrStatisticsEmpChgDataDetail dataDetail = buildDetailData(emp, statisticsId,2, companyId, department, year, month) ;
                    sqlParameterSources.add(dataDetail);
                }

                Integer jobSequence = emp.getJobSequence();
                if (null!=jobSequence){
                    if (jobSequence == 5){
                        if (emp.getChgType()==1) {
                            countM++;
                        }else if(emp.getChgType()==2){
                            innerCountM++;
                        }
                    }else {
                        if (emp.getChgType()==1) {
                            countW++;
                        }else if(emp.getChgType()==2){
                            innerCountW++;
                        }
                    }
                }else{
                    logger.error("该人员（{}）没有岗位序列，为纳入统计！！",emp.getId());
                }
            }
        }
        return new  int[] {countM,countW,innerCountM,innerCountW};
    }

    public int[] countCompChgInMW(Integer companyId,Integer department, String year, String month){
        List<HrEmployee> hrEmployees = employeeDao.countCompChgInMW(companyId, department, year+month);
        Integer countM = 0;
        Integer countW = 0;
        Integer innerCountM = 0;
        Integer innerCountW = 0;
        if (hrEmployees.size()>0){
            for(int i=0, size=hrEmployees.size(); i<size; i++){
                HrEmployee emp = hrEmployees.get(i);
                Integer jobSequence = emp.getJobSequence();
                if (null!=jobSequence){
                    if (jobSequence == 5){
                        if (emp.getChgType()==1) {
                            countM++;
                        }else if(emp.getChgType()==2){
                            innerCountM++;
                        }
                    }else {
                        if (emp.getChgType()==1) {
                            countW++;
                        }else if(emp.getChgType()==2){
                            innerCountW++;
                        }
                    }
                }else{
                    logger.error("该人员（{}）没有岗位序列，为纳入统计！！",emp.getId());
                }
            }
        }
        return new  int[] {countM,countW,innerCountM,innerCountW};
    }


    /**
     * 调动（调出）统计
     * @param proxy
     * @param sqlParameterSources
     * @param statisticsId
     * @param companyId
     * @param department
     * @param year
     * @param month
     * @return
     */
    @Transactional
    public int[] countCompChgOutMW(HrStatisticsService proxy, List<HrStatisticsEmpChgDataDetail> sqlParameterSources, Integer statisticsId, Integer companyId, Integer department, String year, String month){
        List<HrEmployee> hrEmployees = employeeDao.countCompChgOutMW(companyId, department, year+month);
        Integer countM = 0;
        Integer countW = 0;
        Integer innerCountM = 0;
        Integer innerCountW = 0;
        for (int i = 0, size = hrEmployees.size(); i < size; i++) {
            HrEmployee emp = hrEmployees.get(i);
            if (emp.getChgType()==1) {//跨公司调动
                HrStatisticsEmpChgDataDetail dataDetail = buildDetailData(emp, statisticsId, 3, companyId, department, year, month);
                sqlParameterSources.add(dataDetail);
            }
            Integer jobSequence = emp.getJobSequence();
            if (null!=jobSequence){
                if (jobSequence == 5){
                    if (emp.getChgType()==1) {
                        countM++;
                    }else if(emp.getChgType()==2){
                        innerCountM++;
                    }
                }else {
                    if (emp.getChgType()==1) {
                        countW++;
                    }else if(emp.getChgType()==2){
                        innerCountW++;
                    }
                }
            }else{
                logger.error("该人员（{}）没有岗位序列，为纳入统计！！",emp.getId());
            }
        }
        return new  int[] {countM,countW,innerCountM,innerCountW};
    }

    public int[] countCompChgOutMW(Integer companyId,Integer department,String year, String month){
        List<HrEmployee> hrEmployees = employeeDao.countCompChgOutMW(companyId, department, year+month);
        Integer countM = 0;
        Integer countW = 0;
        Integer innerCountM = 0;
        Integer innerCountW = 0;
        for (int i = 0, size = hrEmployees.size(); i < size; i++) {
            HrEmployee emp = hrEmployees.get(i);
            Integer jobSequence = emp.getJobSequence();
            if (null!=jobSequence){
                if (jobSequence == 5){
                    if (emp.getChgType()==1) {
                        countM++;
                    }else if(emp.getChgType()==2){
                        innerCountM++;
                    }
                }else {
                    if (emp.getChgType()==1) {
                        countW++;
                    }else if(emp.getChgType()==2){
                        innerCountW++;
                    }
                }
            }else{
                logger.error("该人员（{}）没有岗位序列，为纳入统计！！",emp.getId());
            }
        }
        return new  int[] {countM,countW,innerCountM,innerCountW};
    }

    /**
     * 添加结果
     * @param emp
     * @param statisticsType
     */
    @Transactional
    public void addDetailData(HrEmployee emp, Integer statisticsId, Integer statisticsType, Integer companyId, Integer department, String year, String month){
        HrStatisticsEmpChgDataDetail detail = new HrStatisticsEmpChgDataDetail();
        detail.setStatisticsId(statisticsId);
        detail.setEmpId(emp.getId());
        detail.setDutyLevel(emp.getDutyLevelId());
        detail.setCompany(companyId);
        detail.setDepartment(department);
        detail.setYear(year);
        detail.setMonth(month);
        detail.setStatisticsType(statisticsType);
        detail.setCreatetime(new Date());
        if (statisticsEmpChgDataDetailDao.postData(detail)<=0){
            throw new BusinessException("添加统计结果出错");
        }
    }

    private HrStatisticsEmpChgDataDetail buildDetailData(HrEmployee emp, Integer statisticsId, Integer statisticsType, Integer companyId, Integer department, String year, String month){
        HrStatisticsEmpChgDataDetail detail = new HrStatisticsEmpChgDataDetail();
        detail.setStatisticsId(statisticsId);
        detail.setEmpId(emp.getId());
        detail.setDutyLevel(emp.getDutyLevelId());
        detail.setBusinessId(emp.getBusinessId());
        detail.setCompany(companyId);
        detail.setDepartment(department);
        detail.setYear(year);
        detail.setMonth(month);
        detail.setStatus(0);
        detail.setStatisticsType(statisticsType);
        detail.setCreatetime(new Date());
        return detail;
    }

    private void createCell(Row row, CellStyle cs, int cellIndex, String ... values){
        for (int i=0;i<values.length;i++){
            Cell cell = row.createCell(cellIndex+i);
            cell.setCellValue(values[i]);
            cell.setCellStyle(cs);
        }
    }

    private void createNullCell(Row row, CellStyle cs, int cellIndex, int length){
        String NULL = null;
        for (int i=0;i<length;i++){
            Cell cell = row.createCell(cellIndex+i);
            cell.setCellValue(NULL);
            cell.setCellStyle(cs);
        }
    }

    /**
     * 导出盘点表 （总计）
     * @param statisticsData
     * @param title
     * @param response
     */
    public void downloadAllStatisticsData(HrStatisticsData statisticsData, String title, HttpServletResponse response) {
        Workbook wb = new HSSFWorkbook();
        CellStyle cs = ExcelUtil.createCellStyleTocolumn(wb);
        CellStyle cs2 = ExcelUtil.createCellStyleToValue(wb);
        Sheet sheet = wb.createSheet("主表");
        List<HrStatisticsData> reportData = statisticsDataDao.getStatisticsDataSum(statisticsData);
        HrStatisticsData countData = this.countStatisticsData(reportData);
        if (!statisticsData.getStatisticsType().equals(2)){
            buildStatisticsDataExcel(sheet, cs, cs2, title, 2, reportData, countData);
        }else{
            buildStatisticsDataExcel(sheet, cs, cs2, title, 1, reportData, countData);
        }
        sheet.setColumnWidth( 1,50*100);
        if (!statisticsData.getStatisticsType().equals(2)){
            List<HrStatisticsData> children ;
            HrStatisticsData childrenCountData;
            for (int i=0; i<reportData.size(); i++){
                HrStatisticsData data = reportData.get(i);
                sheet = wb.createSheet(data.getCompanyStr());
                sheet.setColumnWidth( 1,40*100);
                statisticsData.setCompany(data.getCompany());
                children = this.getStatisticsData(statisticsData);
                childrenCountData = this.countStatisticsData(reportData);
                buildStatisticsDataExcel(sheet, cs, cs2, data.getCompanyStr(), 0, children, childrenCountData);
            }
        }
        writeFiles(response,wb,title);
    }

    /**
     * 导出盘点表（单表）
     * @param statisticsData
     * @param fileName
     * @param type
     * @param response
     */
    public void downloadStatisticsData(HrStatisticsData statisticsData , String fileName, Integer type, HttpServletResponse response) {
        Workbook wb = new HSSFWorkbook();
        CellStyle cs = ExcelUtil.createCellStyleTocolumn(wb);
        CellStyle cs2 = ExcelUtil.createCellStyleToValue(wb);
        Sheet sheet = wb.createSheet("sheet");
        List<HrStatisticsData> reportData = this.getStatisticsData(statisticsData);
        HrStatisticsData countData = this.countStatisticsData(reportData);
        buildStatisticsDataExcel(sheet, cs, cs2, fileName, type, reportData, countData);
        writeFiles(response,wb,fileName);
    }

    private void buildStatisticsDataExcel(Sheet sheet, CellStyle cs, CellStyle cs2, String title, Integer type, List<HrStatisticsData> reportData, HrStatisticsData countData){
        for(int i=0;i<22;i++) {
            sheet.setColumnWidth((short) i, (short) (27 * 100));
            sheet.setDefaultRowHeight((short) 350);
        }
        int baseRowIndex = 0;
        int rowIndex = 0+baseRowIndex;
        int colIndex = 0;
        Row row = sheet.createRow((short) rowIndex);
        createCell(row,cs,colIndex,title);
        createNullCell(row,cs,colIndex+1,21);
        sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, colIndex,colIndex+22));
        rowIndex=1+baseRowIndex;
        row = sheet.createRow((short) rowIndex);
        createCell(row,cs,colIndex, type ==0?"部门分布":type ==1?"职级分布":type ==2?"公司/部门":null,null);
        sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, colIndex,colIndex+1));
        createCell(row,cs,colIndex+2,"在职人数");
        sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex+1, colIndex+2,colIndex+2));
        createCell(row,cs,colIndex+3,"学历构成",null,null,null);
        sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, colIndex+3,colIndex+6));
        createCell(row,cs,colIndex+7,"年龄结构",null,null,null,null);
        sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, colIndex+7,colIndex+11));
        createCell(row,cs,colIndex+12,"司龄结构",null,null,null,null);
        sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, colIndex+12,colIndex+16));
        createCell(row,cs,colIndex+17,"性别结构",null);
        sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, colIndex+17,colIndex+18));
        createCell(row,cs,colIndex+19,"盘点数据核对",null,null,null);
        sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, colIndex+19,colIndex+22));

        rowIndex=2+baseRowIndex;
        row = sheet.createRow((short) rowIndex);
        createCell(row,cs,colIndex,"序号",type ==0?"部门":type ==1?"职级":type ==2?"名称":null);
        createCell(row,cs,colIndex+3,"硕士及以上","本科","大专","大专以下");
        createCell(row,cs,colIndex+7,"30岁及以下","31~40岁","41~50岁","51~60岁","60岁以上");
        createCell(row,cs,colIndex+12,"1年以下","1~3年","4~5年","6~10年","10年以上");
        createCell(row,cs,colIndex+17,"男","女");
        createCell(row,cs,colIndex+19,"学历结构","年龄结构","工作年限","性别结构");

        int eduSum;
        int ageSum;
        int ctimeSum;
        int sexSum;
        if (null!=reportData&&reportData.size()>0){
            for (int i=0; i<reportData.size();i++){
                rowIndex=baseRowIndex+3+i;
                row = sheet.createRow((short) rowIndex);
                HrStatisticsData data = reportData.get(i);
                createCell(row,cs2,colIndex,String.valueOf(i+1), type ==0?data.getDeptName():type ==1?data.getDutyLevelStr():type ==2?data.getCompanyStr():null);
                createCell(row,cs2,colIndex+2,String.valueOf(data.getSum()));
                createCell(row,cs2,colIndex+3,String.valueOf(data.getEdu1()),String.valueOf(data.getEdu2()),String.valueOf(data.getEdu3()),String.valueOf(data.getEdu4()));
                createCell(row,cs2,colIndex+7,String.valueOf(data.getAge1()),String.valueOf(data.getAge2()),String.valueOf(data.getAge3()),String.valueOf(data.getAge4()),String.valueOf(data.getAge5()));
                createCell(row,cs2,colIndex+12,String.valueOf(data.getCtime1()),String.valueOf(data.getCtime2()),String.valueOf(data.getCtime3()),String.valueOf(data.getCtime4()),String.valueOf(data.getCtime5()));
                createCell(row,cs2,colIndex+17,String.valueOf(data.getMale()),String.valueOf(data.getFemale()));
                eduSum = data.getEdu1()+data.getEdu2()+data.getEdu3()+data.getEdu4();
                ageSum = data.getAge1()+data.getAge2()+data.getAge3()+data.getAge4()+data.getAge5();
                ctimeSum =  data.getCtime1()+data.getCtime2()+data.getCtime3()+data.getCtime4()+data.getCtime5();
                sexSum = data.getMale() + data.getFemale();
                createCell(row,cs,colIndex+19,String.valueOf(eduSum),String.valueOf(ageSum),String.valueOf(ctimeSum),String.valueOf(sexSum));
            }
        }

        if (null!=countData){
            rowIndex=baseRowIndex+3+reportData.size();
            row = sheet.createRow((short) rowIndex);
            createCell(row,cs2,colIndex,"合计", null);
            sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, colIndex,colIndex+1));
            createCell(row,cs,colIndex+2,String.valueOf(countData.getSum()));
            createCell(row,cs,colIndex+3,String.valueOf(countData.getEdu1()),String.valueOf(countData.getEdu2()),String.valueOf(countData.getEdu3()),String.valueOf(countData.getEdu4()));
            createCell(row,cs,colIndex+7,String.valueOf(countData.getAge1()),String.valueOf(countData.getAge2()),String.valueOf(countData.getAge3()),String.valueOf(countData.getAge4()),String.valueOf(countData.getAge5()));
            createCell(row,cs,colIndex+12,String.valueOf(countData.getCtime1()),String.valueOf(countData.getCtime2()),String.valueOf(countData.getCtime3()),String.valueOf(countData.getCtime4()),String.valueOf(countData.getCtime5()));
            createCell(row,cs,colIndex+17,String.valueOf(countData.getMale()),String.valueOf(countData.getFemale()));
            eduSum = countData.getEdu1()+countData.getEdu2()+countData.getEdu3()+countData.getEdu4();
            ageSum = countData.getAge1()+countData.getAge2()+countData.getAge3()+countData.getAge4()+countData.getAge5();
            ctimeSum =  countData.getCtime1()+countData.getCtime2()+countData.getCtime3()+countData.getCtime4()+countData.getCtime5();
            sexSum = countData.getMale() + countData.getFemale();
            createCell(row,cs,colIndex+19,String.valueOf(eduSum),String.valueOf(ageSum),String.valueOf(ctimeSum),String.valueOf(sexSum));
        }
    }

    private void buildStatisticsChgExcel(Sheet sheet, CellStyle cs, CellStyle cs2, String title, Integer type, List<HrStatisticsEmpChgData> reportData, HrStatisticsEmpChgData countData) {
        for (int i = 0; i < 18; i++) {
            sheet.setColumnWidth((short) i, (short) (27 * 100));
            sheet.setDefaultRowHeight((short) 350);
        }
        int baseRowIndex = 0;
        int rowIndex = 0 + baseRowIndex;
        int colIndex = 0;
        sheet.setColumnWidth(colIndex + 8,35*100);
        sheet.setColumnWidth(colIndex + 12,35*100);
        sheet.setColumnWidth(colIndex + 13,35*100);
        sheet.setColumnWidth(colIndex + 14,35*100);
        sheet.setColumnWidth(colIndex + 15,35*100);
        sheet.setColumnWidth(colIndex + 16,35*100);
        Row row = sheet.createRow((short) rowIndex);
        createCell(row, cs, colIndex, title);
        createNullCell(row, cs, colIndex + 1, 19);
        sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, colIndex, colIndex + 19));
        rowIndex = 1 + baseRowIndex;
        row = sheet.createRow((short) rowIndex);
        createCell(row, cs, colIndex,type==0?"公司/部门分布":type==1?"部门":null , null);
        sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, colIndex, colIndex + 1));
        createCell(row, cs, colIndex + 2, "实际在岗人数", null, null);
        sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, colIndex + 2, colIndex + 4));
        createCell(row, cs, colIndex + 5, "入职情况", null, null, null, null);
        sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, colIndex + 5, colIndex + 8));
        createCell(row, cs, colIndex + 9, "离职情况", null, null, null, null);
        sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, colIndex + 9, colIndex + 12));
        createCell(row, cs, colIndex + 13, "跨公司调动情况", null, null, null, null);
        sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, colIndex + 13, colIndex + 16));
        createCell(row, cs, colIndex + 17, "其他不占编制人数", null, null);
        sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, colIndex + 17, colIndex + 19));

        rowIndex = 2 + baseRowIndex;
        row = sheet.createRow((short) rowIndex);
        createCell(row, cs, colIndex, "序号", type==0?"公司名称":type==1?"部门名称":null);
        createCell(row, cs, colIndex + 2, "管理类", "工人类", "小计");
        createCell(row, cs, colIndex + 5, "管理类", "工人类", "小计", "副经理级及以上");
        createCell(row, cs, colIndex + 9, "管理类", "工人类", "小计", "副经理级及以上");
        createCell(row, cs, colIndex + 13, "调入(管理类)", "调入(工人类)", "调出(管理类)", "调出(工人类)");
        createCell(row, cs, colIndex + 17, "实习", "渠道", "车管");

        if (null!=reportData){
            for (int i=0;i<reportData.size();i++){
                rowIndex = baseRowIndex+3 + i ;
                row = sheet.createRow((short) rowIndex);
                HrStatisticsEmpChgData data = reportData.get(i);
                createCell(row, cs2, colIndex, String.valueOf(rowIndex), type==0?data.getCompanyName():type==1?data.getDeptName():null);
                createCell(row, cs2, colIndex + 2, String.valueOf(data.getOnDutyM()), String.valueOf(data.getOnDutyW()), String.valueOf(data.getOnDutyM()+data.getOnDutyW()));
                createCell(row, cs2, colIndex + 5, String.valueOf(data.getInDutyM()), String.valueOf(data.getInDutyW()), String.valueOf(data.getInDutyM()+data.getInDutyW()), String.valueOf(data.getInDutyGatherM()));
                createCell(row, cs2, colIndex + 9, String.valueOf(data.getOutDutyM()), String.valueOf(data.getOutDutyW()), String.valueOf(data.getOutDutyM()+data.getOutDutyW()), String.valueOf(data.getOutDutyGatherM()));
                createCell(row, cs2, colIndex + 13, String.valueOf(data.getTurnInCompM()), String.valueOf(data.getTurnInCompW()), String.valueOf(data.getTurnOutCompM()), String.valueOf(data.getTurnOutCompW()));
                createCell(row, cs2, colIndex + 17, String.valueOf(data.getPractice()), String.valueOf(data.getChannel()),String.valueOf(data.getCarM()));
            }
        }
        if (null!=countData){
            rowIndex = baseRowIndex+3 + reportData.size() ;
            row = sheet.createRow((short) rowIndex);
            createCell(row,cs2,colIndex,"合计", null);
            sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, colIndex,colIndex+1));
            createCell(row, cs2, colIndex + 2, String.valueOf(countData.getOnDutyM()), String.valueOf(countData.getOnDutyW()), String.valueOf(countData.getOnDutyM()+countData.getOnDutyW()));
            createCell(row, cs2, colIndex + 5, String.valueOf(countData.getInDutyM()), String.valueOf(countData.getInDutyW()), String.valueOf(countData.getInDutyM()+countData.getInDutyW()), String.valueOf(countData.getInDutyGatherM()));
            createCell(row, cs2, colIndex + 9, String.valueOf(countData.getOutDutyM()), String.valueOf(countData.getOutDutyW()), String.valueOf(countData.getOutDutyM()+countData.getOutDutyW()), String.valueOf(countData.getOutDutyGatherM()));
            createCell(row, cs2, colIndex + 13, String.valueOf(countData.getTurnInCompM()), String.valueOf(countData.getTurnInCompW()), String.valueOf(countData.getTurnOutCompM()), String.valueOf(countData.getTurnOutCompW()));
            createCell(row, cs2, colIndex + 17, String.valueOf(countData.getPractice()), String.valueOf(countData.getChannel()),String.valueOf(countData.getCarM()));
        }
    }

    public void downloadStatisticsChgData(HrStatisticsEmpChgData statisticsData, String title, HttpServletResponse response) {
        Workbook wb = new HSSFWorkbook();
        CellStyle cs = ExcelUtil.createCellStyleTocolumn(wb);
        CellStyle cs2 = ExcelUtil.createCellStyleToValue(wb);
        Sheet sheet = wb.createSheet("sheet");
        List<HrStatisticsEmpChgData> reportData = this.getStatisticsChangeData(statisticsData);
        HrStatisticsEmpChgData countData = this.countChgStatisticsData(reportData);
        buildStatisticsChgExcel(sheet,cs,cs2,title,1,reportData,countData);
        writeFiles(response,wb,title);
    }


    public void downloadAllStatisticsChgData(HrStatisticsEmpChgData statisticsData, String title, HttpServletResponse response) {
        Workbook wb = new HSSFWorkbook();
        CellStyle cs = ExcelUtil.createCellStyleTocolumn(wb);
        CellStyle cs2 = ExcelUtil.createCellStyleToValue(wb);
        Sheet sheet = wb.createSheet("主表");
        List<HrStatisticsEmpChgData> reportData = this.getStatisticsAllChangeData(statisticsData);
        HrStatisticsEmpChgData countData = this.countChgStatisticsData(reportData);
        buildStatisticsChgExcel(sheet, cs, cs2, title,0, reportData, countData);
        sheet.setColumnWidth( 1,50*100);
        List<HrStatisticsEmpChgData> children ;
        HrStatisticsEmpChgData childrenCountData;
        for (int i=0; i<reportData.size(); i++){
            HrStatisticsEmpChgData data = reportData.get(i);
            sheet = wb.createSheet(data.getCompanyName());
            sheet.setColumnWidth( 1,50*100);
            statisticsData.setCompany(data.getCompany());
            children = this.getStatisticsChangeData(statisticsData);
            childrenCountData = this.countChgStatisticsData(children);
            buildStatisticsChgExcel(sheet, cs, cs2, data.getCompanyName(), 1,children, childrenCountData);
        }
        writeFiles(response,wb,title);
    }

    private void writeFiles(HttpServletResponse response, Workbook workbook, String fileName){
        try {
            response.reset();
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;fileName="+ new String((fileName+".xls").getBytes(),"iso-8859-1"));
            ServletOutputStream outputStream = response.getOutputStream();
            int len;
            byte[] buff = new byte[2048];
            BufferedOutputStream bfo = new BufferedOutputStream(outputStream);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            workbook.write(os);
            InputStream is = new ByteArrayInputStream(os.toByteArray());
            while ((len=is.read(buff))>0){
                bfo.write(buff, 0, len);
            }
            bfo.flush();
            bfo.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public List<HrStatisticsData> getInventoryData(Integer type, Integer companyId, String year, String month){
        HrStatisticsData statisticsData = new HrStatisticsData();
        statisticsData.setCompany(companyId);
        statisticsData.setStatisticsType(type);
        statisticsData.setYear(year);
        statisticsData.setMonth(month);
        return this.getStatisticsData(statisticsData);
    }

    /**
     * 合并 管理工人类 tr
     * @param companyId
     * @param year
     * @param month
     * @return
     */
    public List<HrStatisticsData> getInventoryData(Integer companyId, String year, String month){
        HrStatisticsData statisticsData = new HrStatisticsData();
        statisticsData.setCompany(companyId);
        statisticsData.setStatisticsType(0);
        statisticsData.setYear(year);
        statisticsData.setMonth(month);
        List<HrStatisticsData> dataM = this.getStatisticsData(statisticsData);
        statisticsData.setStatisticsType(1);
        List<HrStatisticsData> dataW = statisticsDataDao.getStatisticsData(statisticsData);
        Map<String,HrStatisticsData> wMap = new HashMap<>();
        dataW.forEach(e -> {
            wMap.put(String.valueOf(e.getDepartment()), e);
        });
        for (HrStatisticsData m : dataM){
            addData(m,wMap.get(String.valueOf(m.getDepartment())));
        }
        return dataM;
    }


    /**
     * 公司 人力盘点合计
     * @param year
     * @param month
     * @return
     */
    public List<HrStatisticsData> inventoryAllManagerWorker(String year, String month) {
        User user = SpringSecurityUtil.getUser();
        Authority authority = user.getDataAuthorityMap();
        List<HrStatisticsData> dataM = this.getAllStatisticsData(0, year, month, Authority.getAuthority(authority,user.getCurrCompanyCode()).authoritiesRegexp());
        List<HrStatisticsData> dataW = this.getAllStatisticsData(1, year, month, Authority.getAuthority(authority,user.getCurrCompanyCode()).authoritiesRegexp());
        Map<String,HrStatisticsData> wMap = new HashMap<>();
        dataW.forEach(e -> {
            wMap.put(String.valueOf(e.getDepartment()), e);
        });
        for (HrStatisticsData m : dataM){
            addData(m,wMap.get(String.valueOf(m.getDepartment())));
        }
        return dataM;
    }

    /**
     * 人力盘点 合计 （集团）
     * 0 管理
     * 1 工人
     * null 管理 工人一起
     * @param type
     * @param year
     * @param month
     * @return
     */
    public List<HrStatisticsCompanyBind> inventoryAllManagerWorkerCenter(Integer type, String year, String month) {
        User user = SpringSecurityUtil.getUser();
        List<HrStatisticsData> allStatisticsData;
        if (null == type){
            allStatisticsData = this.getAllStatisticsData(0, year, month,user.getDataAuthoritiesRegexp());
            allStatisticsData.addAll(this.getAllStatisticsData(1, year, month,user.getDataAuthoritiesRegexp()));
        }else{
            allStatisticsData = this.getAllStatisticsData(type, year, month,user.getDataAuthoritiesRegexp());
        }
        Map<String,HrStatisticsData> map = new HashMap<>();
        for (HrStatisticsData data : allStatisticsData){
            String c = String.valueOf(data.getCompany());
            HrStatisticsData oldData = map.get(c);
            if (null!=oldData){
                addData(oldData,data);
                map.put(c,oldData);
            }else{
                map.put(c,data);
            }
        }
        List<HrStatisticsCompanyBind> bind1 = bindService.getBaseBindData(0);//获取一级目录
        for (HrStatisticsCompanyBind data1 :bind1){
            HrStatisticsData sumLv1 = new HrStatisticsData();//数据
            List<HrStatisticsCompanyBind> bind2 = bindService.getBaseBindData(data1.getId());//获取二级目录
            for (HrStatisticsCompanyBind data2 : bind2){
                HrStatisticsData sumLv2 = new HrStatisticsData();
                if (data2.getIsParent()==1){
                    HrStatisticsData tmpSumLv2 = map.get(String.valueOf(data2.getCompanyId()));
                    addData(sumLv2,tmpSumLv2);
                    List<HrStatisticsCompanyBind> bind3 = bindService.getBaseBindData(data2.getId());//获取三级目录
                    HrStatisticsData sumLv3 = new HrStatisticsData();
                    for (HrStatisticsCompanyBind data3 : bind3){
                        HrStatisticsData data = map.get(String.valueOf(data3.getCompanyId()));
                        data3.setStatisticsData(data);
                        addData(sumLv3,data);
                    }
                    addData(sumLv2,sumLv3);
                    data2.setStatisticsData(sumLv2);
                    data2.setChildBindData(bind3);
                }else{
                    HrStatisticsData data = map.get(String.valueOf(data2.getCompanyId()));
                    addData(sumLv2,data);
                }
                addData(sumLv1,sumLv2);
                data2.setStatisticsData(sumLv2);
            }
            data1.setChildBindData(bind2);
            data1.setStatisticsData(sumLv1);
        }
        return bind1;
    }

    private void addData(HrStatisticsData result, HrStatisticsData data){
        if (null==data){
            return;
        }
        result.addSum(data.getSum());
        result.addEdu1(data.getEdu1());
        result.addEdu2(data.getEdu2());
        result.addEdu3(data.getEdu3());
        result.addEdu4(data.getEdu4());
        result.addAge1(data.getAge1());
        result.addAge2(data.getAge2());
        result.addAge3(data.getAge3());
        result.addAge4(data.getAge4());
        result.addAge5(data.getAge5());
        result.addCtime1(data.getCtime1());
        result.addCtime2(data.getCtime2());
        result.addCtime3(data.getCtime3());
        result.addCtime4(data.getCtime4());
        result.addCtime5(data.getCtime5());
        result.addMale(data.getMale());
        result.addFemale(data.getFemale());
    }

    /**
     * 人员异动  合计（集团）
     */
    public List<HrStatisticsCompanyBind> inventoryAllEmpChgCenter(String year, String month) {
        User user = SpringSecurityUtil.getUser();
        HrStatisticsEmpChgData statisticsData = new HrStatisticsEmpChgData();
        statisticsData.setYear(year);
        statisticsData.setMonth(month);
        statisticsData.setAuthorityRegexp(user.getDataAuthoritiesRegexp());
        List<HrStatisticsEmpChgData> allStatisticsData = statisticsEmpChgDataDao.getAllStatisticsChgData(statisticsData);
        Map<String,HrStatisticsEmpChgData> map = new HashMap<>();
        for (HrStatisticsEmpChgData data : allStatisticsData){
            map.put(String.valueOf(data.getCompany()),data);
        }
        List<HrStatisticsCompanyBind> bind1 = bindService.getBaseBindData(0);//获取一级目录
        for (HrStatisticsCompanyBind data1 :bind1){
            HrStatisticsEmpChgData sumLv1 = new HrStatisticsEmpChgData();//数据
            List<HrStatisticsCompanyBind> bind2 = bindService.getBaseBindData(data1.getId());//获取二级目录
            for (HrStatisticsCompanyBind data2 : bind2){
                HrStatisticsEmpChgData sumLv2 = new HrStatisticsEmpChgData();
                if (data2.getIsParent()==1){
                    HrStatisticsEmpChgData tmpSumLv2 = map.get(String.valueOf(data2.getCompanyId()));
                    addData(sumLv2,tmpSumLv2);
                    List<HrStatisticsCompanyBind> bind3 = bindService.getBaseBindData(data2.getId());//获取三级目录
                    HrStatisticsEmpChgData sumLv3 = new HrStatisticsEmpChgData();
                    for (HrStatisticsCompanyBind data3 : bind3){
                        HrStatisticsEmpChgData data = map.get(String.valueOf(data3.getCompanyId()));
                        data3.setStatisticsChgData(data);
                        addData(sumLv3,data);
                    }
                    addData(sumLv2,sumLv3);
                    data2.setStatisticsChgData(sumLv2);
                    data2.setChildBindData(bind3);
                }else{
                    HrStatisticsEmpChgData data = map.get(String.valueOf(data2.getCompanyId()));
                    addData(sumLv2,data);
                }
                addData(sumLv1,sumLv2);
                data2.setStatisticsChgData(sumLv2);
            }
            data1.setChildBindData(bind2);
            data1.setStatisticsChgData(sumLv1);
        }
        return bind1;
    }

    private void addData(HrStatisticsEmpChgData result, HrStatisticsEmpChgData data){
        if (null==data){
            return;
        }
        result.addBeginOnDutyM(data.getBeginOnDutyM());
        result.addBeginOnDutyW(data.getBeginOnDutyW());
        result.addOnDutyM(data.getOnDutyM());
        result.addOnDutyW(data.getOnDutyW());
        result.addInDutyM(data.getInDutyM());
        result.addInDutyW(data.getInDutyW());
        result.addInDutyGatherM(data.getInDutyGatherM());
        result.addOutDutyM(data.getOutDutyM());
        result.addOutDutyW(data.getOutDutyW());
        result.addOutDutyGatherM(data.getOutDutyGatherM());
        result.addTurnInCompM(data.getTurnInCompM());
        result.addTurnInCompW(data.getTurnInCompW());
        result.addTurnOutCompM(data.getTurnOutCompM());
        result.addTurnOutCompW(data.getTurnOutCompW());
        result.addTurnInDeptM(data.getTurnInDeptM());
        result.addTurnInDeptW(data.getTurnInDeptW());
        result.addTurnOutDeptM(data.getTurnOutDeptM());
        result.addTurnOutDeptW(data.getTurnOutDeptW());
        result.addPractice(data.getPractice());
        result.addChannel(data.getChannel());
        result.addCarM(data.getCarM());
    }

    @Transactional
    public BaseResult initEmpChgData(String year , String month, Integer company, Integer department, Integer onDutyM, Integer onDutyW, Integer force) {
        BaseResult result = BaseResult.initBaseResult();
        try{
            Assert.noNullElements(new Integer[]{Integer.valueOf(year), Integer.valueOf(month), company, department, onDutyM, onDutyW});
        } catch (IllegalArgumentException e) {
            result.setRmsg("参数错误");
            return result;
        }
        HrStatisticsEmpChgData oldOne = statisticsEmpChgDataDao.getOne(new HrStatisticsEmpChgData(year, month, company, department));
        HrStatisticsEmpChgData data = new HrStatisticsEmpChgData();
        if (null!=oldOne){
            if (null==force || force==0){
                result.setRmsg("已经有初始化数据，如果需要强制初始化，请勾选强制初始化。");
                return result;
            } else if (force==1) {
                oldOne.setNote("强制初始化该条数据");
                if (statisticsEmpChgDataDao.disableData(oldOne)!=1){
                    throw new BusinessException("强制初始化数据失败！");
                }
            }else{
                result.setRmsg("参数错误");
                return result;
            }
        }
        Long longKey = statisticsEmpChgDataDao.getKey("hr_statistics_emp_chg_data", data);
        data.setId(longKey.intValue());
        data.setOnDutyM(onDutyM);
        data.setOnDutyW(onDutyW);
        data.setCompany(company);
        data.setDepartment(department);
        data.setYear(year);
        data.setMonth(month);
        data.setStatus(0);
        data.setNote("初始化数据");
        data.setCreatetime(new Date());
        if (statisticsEmpChgDataDao.post(data)<=0){
            result.setRmsg("初始化数据错误");
        }else{
            result.setSuccess();
        }
        return result;
    }

    /**
     * 默认初始化 当前时间的上一月
     * 全集团重新初始化
     * @param year
     * @param month
     * @return
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public BaseResult initAllEmpChgData(String year , String month) {
        BaseResult result = BaseResult.initBaseResult();
        String yyyy = DateTimeUtil.dateTimeToString(DateTimeUtil.YYYY);
        String mm = DateTimeUtil.dateTimeToString(DateTimeUtil.MM);
        if (StringUtils.isNotBlank(year)&&StringUtils.isNotBlank(month)){
            if (Integer.valueOf(yyyy+mm)<Integer.valueOf(year+month)){
                throw new BusinessException("您选择的时间超过了当前时间");
            }else{
                yyyy = year;
                mm = month;
            }
        }
        ExecutorService executorService = ThreadManager.getInstance().getExecutorService(9);
        try{
            Date now = new Date();
            List<HrCompany> companies = orgService.getAllCompanies();
            for (HrCompany company : companies){
                initEmpChgData(executorService,company.getId(),yyyy,mm,now);
            }
            result.setSuccess();
        }finally {
            executorService.shutdown();
        }
        return result;
    }


    public BaseResult getInitEmpChgData(Integer companyId, Integer departmentId, String yyyy, String mm){
        BaseResult baseResult = BaseResult.initBaseResult();
        HrStatisticsEmpChgData data = new HrStatisticsEmpChgData(yyyy,mm,companyId,departmentId);
        HrStatisticsEmpChgData one = statisticsEmpChgDataDao.getStatisticsDataOne(data);
        if (null != one){
            baseResult.setRdata(one);
            baseResult.setSuccess();
        }
        return baseResult;
    }

    @Transactional
    public BaseResult calculateInitEmpChgData(Integer companyId, Integer departmentId, String yyyy, String mm){
        BaseResult baseResult = BaseResult.initBaseResult(true);
        String[] lastMonth = DateTimeUtil.getNextMonth(Integer.parseInt(yyyy), Integer.parseInt(mm));
        yyyy = lastMonth[0];
        mm = lastMonth[1];
        Integer onDutyM = countOnDuty(null, 5,companyId,departmentId);//管理类
        Integer onDutyW = countOnDuty(null, 6,companyId,departmentId);//工人类

        int[] inDutyMW = countInDutyMW(companyId, departmentId, yyyy, mm);//入职
        int[] outDutyMW = buildOutDutyMW(companyId, departmentId, yyyy, mm);//离职

        int[] turnInMW = countCompChgInMW(companyId, departmentId, yyyy, mm);
        int[] turnOutMW = countCompChgOutMW(companyId, departmentId, yyyy, mm);
        int m = onDutyM-inDutyMW[0]+outDutyMW[0]-turnInMW[0]-turnInMW[2]+turnOutMW[0]+turnOutMW[2];
        int w = onDutyW-inDutyMW[2]+outDutyMW[2]-turnInMW[1]-turnInMW[3]+turnOutMW[1]+turnOutMW[3];
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("管理类在职："+onDutyM+" - "+inDutyMW[0]+" + "+outDutyMW[0]+" - "+turnInMW[0]+" - "+turnInMW[2]+" + "+turnOutMW[0]+" + "+turnOutMW[2]);
        stringBuilder.append(" = ").append(m);
        stringBuilder.append("\r\n");
        stringBuilder.append("工人类在职："+onDutyW+" - "+inDutyMW[2]+" + "+outDutyMW[2]+" - "+turnInMW[1]+" - "+turnInMW[3]+" + "+turnOutMW[1]+" + "+turnOutMW[3]);
        stringBuilder.append(" = ").append(w);
        baseResult.setRmsg(stringBuilder.toString());
        baseResult.putRdataMap("m",m);
        baseResult.putRdataMap("w",w);
        baseResult.setSuccess();
        return baseResult;
    }

    @Transactional
    public void initEmpChgData(ExecutorService executorService, Integer companyId, String yyyy, String mm,  Date now){
        HrStatisticsService proxy = (HrStatisticsService) AopContext.currentProxy();
        try {
            String[] lastMonth = DateTimeUtil.getLastMonth(Integer.valueOf(yyyy), Integer.valueOf(mm));
            proxy.removeEmpChgData(companyId,lastMonth[0],lastMonth[1]);
            proxy.removeEmpChgDataDetail(companyId,lastMonth[0],lastMonth[1]);
            List<HrDepartment> departments = orgService.getAllDepartments(companyId);
            if (!CollectionUtils.isEmpty(departments)) {
                List<Callable<HrStatisticsEmpChgData>> chTaskList = new ArrayList<>();
                Long[] ids = commSequenceDao.getKey(new CommSequence("hr_statistics_emp_chg_data", departments.size()));
                for (int i = 0; i < departments.size(); i++) {
                    HrDepartment department = departments.get(i);
                    final int id = ids[0].intValue() + i + 1;
                    Callable<HrStatisticsEmpChgData> chCallable = () -> {
                        HrStatisticsEmpChgData data = new HrStatisticsEmpChgData();
                        data.setId(id);
                        Integer onDutyM = countOnDuty(null, 5,companyId,department.getId());//管理类
                        Integer onDutyW = countOnDuty(null, 6,companyId,department.getId());//工人类

                        int[] inDutyMW = countInDutyMW(companyId, department.getId(), yyyy, mm);//入职
                        int[] outDutyMW = buildOutDutyMW(companyId, department.getId(), yyyy, mm);//离职

                        int[] turnInMW = countCompChgInMW(companyId, department.getId(), yyyy, mm);
                        int[] turnOutMW = countCompChgOutMW(companyId, department.getId(), yyyy, mm);

                        data.setOnDutyM(onDutyM-inDutyMW[0]+outDutyMW[0]-turnInMW[0]-turnInMW[2]+turnOutMW[0]+turnOutMW[2]);
                        data.setOnDutyW(onDutyW-inDutyMW[2]+outDutyMW[2]-turnInMW[1]-turnInMW[3]+turnOutMW[1]+turnOutMW[3]);
                        data.setCompany(companyId);
                        data.setDepartment(department.getId());
                        data.setYear(lastMonth[0]);
                        data.setMonth(lastMonth[1]);
                        data.setStatus(0);
                        data.setNote("初始化数据");
                        data.setCreatetime(now);
                        return data;
                    };
                    chTaskList.add(chCallable);
                }
                List<Future<HrStatisticsEmpChgData>> chFutures = executorService.invokeAll(chTaskList);
                proxy.saveInitEmpChgData(this, chFutures, companyId);
            }
        }catch (InterruptedException e) {
            throw new BusinessException(e);
        }catch (ExecutionException e) {
            logger.error("",e);
            throw new BusinessException(e);
        }
    }

    @Transactional
    void saveInitEmpChgData(HrStatisticsService _this, List<Future<HrStatisticsEmpChgData>> chFutures, Integer companyId) throws ExecutionException, InterruptedException {
        List<SqlParameterSource> list = new ArrayList();
        for(int i=0; i<chFutures.size(); i++){
            HrStatisticsEmpChgData hrStatisticsEmpChgData = chFutures.get(i).get();
            if (null!=hrStatisticsEmpChgData){
                list.add(new BeanPropertySqlParameterSource(hrStatisticsEmpChgData));
            }else{
                _this.logger.info("未获取到统计数据，数据回滚");
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return;
            }
        }
        if (chFutures.size()>0&&list.size()==chFutures.size()){
            _this.statisticsEmpChgDataDao.batchInsertStatisticsData(list.toArray(new SqlParameterSource[list.size()]));
        }
    }

    public BaseResult getEmpChangeDailyData(HrStatisticsArgs args) {
        BaseResult result = BaseResult.initBaseResult(true);
        HrEmployee entity = new HrEmployee();
        entity.setDepartment(args.getDepartment());
        entity.setStartDate(DateTimeUtil.dateToString(args.getStartTime()));
        entity.setEndDate(DateTimeUtil.dateToString(args.getEndTime()));
        entity.setJobSequence(args.getJobSequence());
        entity.setCompany(args.getCompanyId());
        if (args.getType()==1){
            List<HrEmployee> inDutyDaily;
            if (args.isSearchNow()){
                inDutyDaily = employeeDao.countCurrentInDutyDaily(entity,args.isSearchAll());
            }else{
                inDutyDaily = employeeDao.countInDutyDaily(entity,args.isSearchAll());
            }
            result.putRdataMap("inDuty",inDutyDaily);
        }else if (args.getType()==2){
            List<HrEmployee> outDutyDaily;
            if (args.isSearchNow()){
                outDutyDaily = employeeDao.countCurrentOutDutyDaily(entity,args.isSearchAll());
            }else{
                outDutyDaily = employeeDao.countOutDutyDaily(entity,args.isSearchAll());
            }
            result.putRdataMap("outDuty",outDutyDaily);
        }else if (args.getType()==3||args.getType()==30) {
            List<HrEmployee> psnChgDaily;
            if (args.isSearchNow()){
                psnChgDaily = employeeDao.countCurrentPsnChgDaily(entity,1,args.isSearchAll(), args.getChgType());
            }else{
                psnChgDaily = employeeDao.countPsnChgDaily(entity,1,args.isSearchAll(), args.getChgType());
            }
            result.putRdataMap("psnChg", psnChgDaily);
        }else if (args.getType()==31) {
            List<HrEmployee> psnChgDaily;
            if (args.isSearchNow()){
                psnChgDaily = employeeDao.countCurrentPsnChgDaily(entity,2,args.isSearchAll(), args.getChgType());
            }else{
                psnChgDaily = employeeDao.countPsnChgDaily(entity,2,args.isSearchAll(), args.getChgType());
            }
            result.putRdataMap("psnChg", psnChgDaily);
        }
        result.setSuccess();
        return result;
    }

    /**
     * 移除数据
     * @param statisticsType 统计类型
     * @param company 公司
     */
    @Transactional
    public void removeHrStatisticsData(Integer statisticsType, Integer company, String yyyy, String mm){
        logger.info("removeHrStatisticsData，statisticsType：{},company：{}, yyyy：{}, mm：{}", statisticsType,company,yyyy,mm);
        HrStatisticsData statisticsData = new HrStatisticsData();
        statisticsData.setStatisticsType(statisticsType);
        statisticsData.setYear(yyyy);
        statisticsData.setMonth(mm);
        statisticsData.setCompany(company);
        statisticsDataDao.removeData(statisticsData);
    }

    /**
     * 移除人员异动数据
     * @param company
     */
    @Transactional
    public void removeEmpChgData( Integer company, String yyyy, String mm){
        logger.info("removeEmpChgData， company：{}, yyyy：{}, mm：{}",company,yyyy,mm);
        HrStatisticsEmpChgData statisticsData = new HrStatisticsEmpChgData();
        statisticsData.setCompany(company);
        statisticsData.setYear(yyyy);
        statisticsData.setMonth(mm);
        statisticsEmpChgDataDao.removeData(statisticsData);
    }

    /**
     * 移除人员异动详情数据
     * @param company
     */
    @Transactional
    public void removeEmpChgDataDetail(Integer company, String yyyy, String mm){
        logger.info("removeEmpChgDataDetail， company：{}, yyyy：{}, mm：{}",company,yyyy,mm);
        HrStatisticsEmpChgDataDetail statisticsData = new HrStatisticsEmpChgDataDetail();
        statisticsData.setCompany(company);
        statisticsData.setYear(yyyy);
        statisticsData.setMonth(mm);
        statisticsEmpChgDataDetailDao.removeData(statisticsData);
    }


    /**
     * 统计盘点数据
     * @param m
     * @param w
     * @param dt
     */
    @Transactional(propagation= Propagation.NOT_SUPPORTED)
    public void manualStatisticsAllInv(boolean m, boolean w, boolean dt,Date now) {
        HrStatisticsService proxy = (HrStatisticsService) AopContext.currentProxy();
        String yyyy = DateTimeUtil.dateToString(now, DateTimeUtil.YYYY);
        String mm = DateTimeUtil.dateToString(now, DateTimeUtil.MM);
        ExecutorService executorService = ThreadManager.getInstance().getExecutorService(9);
        try {
            List<HrCompany> companies = orgService.getAllCompanies();
            for (HrCompany company : companies){
                List<HrDepartment> departments = orgService.getAllDepartments(company.getId());
                if (!CollectionUtils.isEmpty(departments)){
                    if (m){
                        proxy.statisticsManager(executorService,company.getId(),departments,true,yyyy,mm,now);
                    }
                    if (w) {
                        proxy.statisticsWorker(executorService,company.getId(),departments,true,yyyy,mm,now);
                    }
                }
                if (dt){
                    proxy.statisticsDutyLevel(executorService,company.getId(),true,yyyy,mm,now);
                }
            }
        } finally {
            executorService.shutdown();
        }
    }

    /**
     * 统计所有异动情况
     * @return
     */
    @Transactional(propagation= Propagation.NOT_SUPPORTED)
    public BaseResult manualStatisticsAllChg(String year, String month, Date now) {
        BaseResult baseResult = BaseResult.initBaseResult();
        StringBuilder stringBuilder = new StringBuilder();
        String yyyy = DateTimeUtil.dateToString(now, DateTimeUtil.YYYY);
        String mm = DateTimeUtil.dateToString(now, DateTimeUtil.MM);
        if (StringUtils.isNotBlank(year)&&StringUtils.isNotBlank(month)){
            if (Integer.valueOf(yyyy+mm)<Integer.valueOf(year+month)){
                throw new BusinessException("您选择的时间超过了当前时间");
            }else{
                yyyy = year;
                mm = month;
            }
        }
        ExecutorService executorService = ThreadManager.getInstance().getExecutorService(9);
        try {
            List<HrCompany> companies = orgService.getAllCompanies();
            for (HrCompany company : companies) {
                statisticsChComp(executorService,company.getId(),yyyy,mm,now,stringBuilder);
            }
            baseResult.setRmsg(stringBuilder.toString());
        }finally {
            executorService.shutdown();
        }
        return baseResult;
    }



    @Transactional
    public void statisticsChComp(ExecutorService executorService, Integer companyId, String yyyy, String mm,  Date now, StringBuilder stringBuilder){
        HrStatisticsService proxy = (HrStatisticsService) AopContext.currentProxy();
        try {
            proxy.removeEmpChgData(companyId,yyyy,mm);
            proxy.removeEmpChgDataDetail(companyId,yyyy,mm);
            List<HrDepartment> departments = orgService.getAllDepartments(companyId);
            if (!CollectionUtils.isEmpty(departments)) {
                List<Callable<HrStatisticsEmpChgData>> chTaskList = new ArrayList<>();
                Long[] ids = commSequenceDao.getKey(new CommSequence("hr_statistics_emp_chg_data", departments.size()));
                for (int i = 0; i < departments.size(); i++) {
                    HrDepartment entity = departments.get(i);
                    final int id = ids[0].intValue() + i + 1;
                    Callable<HrStatisticsEmpChgData> chCallable = () -> {
                        logger.info("开始统计异动, 公司：{}, 部门：{}", companyId, entity.getId());
                        HrStatisticsEmpChgData result = proxy.statisticsBaseEmpTrans(proxy, id, companyId, entity.getId(), yyyy, mm);
                        if (null == result){
                            HrCompany companyOne = orgService.getCompanyOne(companyId);
                            HrDepartment departmentOne = orgService.getDepartmentOne(entity.getId());
                            stringBuilder.append(" ").append(companyOne.getName()+"-"+departmentOne.getName()).append("\r\n");
                        }
                        return result;
                    };
                    chTaskList.add(chCallable);
                }
                List<Future<HrStatisticsEmpChgData>> chFutures = executorService.invokeAll(chTaskList);
                proxy.saveStatisticsCH(this, chFutures, now, companyId);
            }
        }catch (InterruptedException e) {
            throw new BusinessException(e);
        }catch (ExecutionException e) {
            throw new BusinessException(e);
        }
    }



    @Transactional
    void saveStatisticsCH(HrStatisticsService _this, List<Future<HrStatisticsEmpChgData>> chFutures, Date now, Integer companyId) throws ExecutionException, InterruptedException {
        List<SqlParameterSource> list = new ArrayList();
        List<HrStatisticsEmpChgDataDetail> detailList = new ArrayList();
        for(int i=0; i<chFutures.size(); i++){
            HrStatisticsEmpChgData hrStatisticsEmpChgData = chFutures.get(i).get();
            if (null!=hrStatisticsEmpChgData){
                List<HrStatisticsEmpChgDataDetail> chgDataDetails = hrStatisticsEmpChgData.getChgDataDetails();
                if (null != chgDataDetails){
                    detailList.addAll(chgDataDetails);
                }
                hrStatisticsEmpChgData.setCreatetime(now);
                list.add(new BeanPropertySqlParameterSource(hrStatisticsEmpChgData));
            }else{
                _this.logger.info("未获取到统计数据，数据回滚");
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return;
            }
        }
        if (chFutures.size()>0&&list.size()==chFutures.size()){
            _this.statisticsEmpChgDataDao.batchInsertStatisticsData(list.toArray(new SqlParameterSource[list.size()]));
            SqlParameterSource[] sqlParameterSources = new SqlParameterSource[detailList.size()];
            Long[] ids = _this.commSequenceDao.getKey(new CommSequence("hr_statistics_emp_chg_data_detail", detailList.size()));
            for(int i=0; i<detailList.size(); i++){
                HrStatisticsEmpChgDataDetail dataDetail = detailList.get(i);
                dataDetail.setId(ids[0].intValue()+i+1);
                sqlParameterSources[i] = new BeanPropertySqlParameterSource(dataDetail);
            }
            _this.statisticsEmpChgDataDetailDao.batchUpdate(sqlParameterSources);
        }
    }


    /**
     * 月初 复制 一份 统计数据
     */
    @Transactional
    public void copyStatisticsData( Date now){
        String[] yearMonth = DateTimeUtil.getYearMonth(now);
        String[] nextMonth = DateTimeUtil.getNextMonth(Integer.valueOf(yearMonth[0]), Integer.valueOf(yearMonth[1]));
        List<HrStatisticsData> dataList = statisticsDataDao.getStatisticsDataSimple(new HrStatisticsData(yearMonth[0],yearMonth[1]));
        int size = dataList.size();
        SqlParameterSource[] sqlParameterSources = new SqlParameterSource[size];
        if (size>0){
            System.out.println(size);
            System.out.println(dataList.get(0).getId());
            for (int i = 0; i<size; i++){
                HrStatisticsData data = dataList.get(i);
                data.setId(data.getId()+size);
                data.setCreatetime(now);
                data.setYear(nextMonth[0]);
                data.setMonth(nextMonth[1]);
                sqlParameterSources[i] = new BeanPropertySqlParameterSource(data);
            }
            statisticsDataDao.batchInsertStatisticsData(sqlParameterSources);
            CommSequence sequence =  new CommSequence();
            sequence.setName("hr_statistics_data");
            sequence.setId(Long.valueOf(dataList.get(size-1).getId()));
            commSequenceDao.updateKey(sequence);
        }

        List<HrStatisticsEmpChgData> chgDataList = statisticsEmpChgDataDao.getStatisticsChgDataSimple(new HrStatisticsEmpChgData(yearMonth[0],yearMonth[1]));
        size = chgDataList.size();
        if (size>0){
            sqlParameterSources = new SqlParameterSource[size];
            for (int i = 0; i<size; i++){
                HrStatisticsEmpChgData chgData = chgDataList.get(i);
                HrStatisticsEmpChgData nextChgData = new HrStatisticsEmpChgData(nextMonth[0],nextMonth[1]);
                nextChgData.setId(chgData.getId()+size);
                nextChgData.setCompany(chgData.getCompany());
                nextChgData.setDepartment(chgData.getDepartment());
                nextChgData.setBeginOnDutyM(chgData.getOnDutyM());
                nextChgData.setBeginOnDutyW(chgData.getOnDutyW());
                nextChgData.setOnDutyM(chgData.getOnDutyM());
                nextChgData.setOnDutyW(chgData.getOnDutyW());
                nextChgData.setCreatetime(now);
                nextChgData.setPractice(chgData.getPractice());
                nextChgData.setChannel(chgData.getChannel());
                nextChgData.setCarM(chgData.getCarM());
                nextChgData.setStatus(0);
                sqlParameterSources[i] = new BeanPropertySqlParameterSource(nextChgData);
            }
            statisticsEmpChgDataDao.batchInsertStatisticsData(sqlParameterSources);
            CommSequence sequence =  new CommSequence();
            sequence.setName("hr_statistics_emp_chg_data");
            sequence.setId(Long.valueOf(chgDataList.get(size-1).getId()+size));
            commSequenceDao.updateKey(sequence);
        }

    }

    /**
     * 获取统计数据
     * @param args
     * @return
     */
    public List<HrEmployee> getEmpChangeDailyDataForExport(HrStatisticsArgs args) {
        HrEmployee entity = new HrEmployee();
        entity.setDepartment(args.getDepartment());
        entity.setStartDate(DateTimeUtil.dateToString(args.getStartTime()));
        entity.setEndDate(DateTimeUtil.dateToString(args.getEndTime()));
        entity.setJobSequence(args.getJobSequence());
        entity.setCompany(args.getCompanyId());
        if (args.getType()==1){
            return employeeDao.countInDutyDaily(entity,args.isSearchAll());
        }else if (args.getType()==2){
            return employeeDao.countOutDutyDaily(entity,args.isSearchAll());
        }else if (args.getType()==30) {
            return employeeDao.countPsnChgDaily(entity,1,args.isSearchAll(),args.getChgType());
        }else if (args.getType()==31) {
            return  employeeDao.countPsnChgDaily(entity,2,args.isSearchAll(),args.getChgType());
        }
        return Collections.emptyList();
    }

    /**
     * 构造导出统计信息
     * @param type
     * @param list
     * @return
     */
    public Workbook exportEmpChangeDailyData(Integer type, List<HrEmployee> list) {
        Workbook wb = new HSSFWorkbook();
        CellStyle cs = ExcelUtil.createCellStyleTocolumn(wb);
        CellStyle cs2 = ExcelUtil.createCellStyleToValue(wb);
        Map<String,String> jobSequenceMap = new HashMap<>();
        hrDictService.getDictDataByDictValue("jobSequence").forEach(e->{
            jobSequenceMap.put(e.getDictDataValue().toString(),e.getDictDataKey());
        });
        Map<String,String> dutyLevelMap = new HashMap<>();
        hrDictService.getDictDataByDictValue("dutyLevel").forEach(e->{
            dutyLevelMap.put(e.getDictDataValue().toString(),e.getDictDataKey());
        });
        Map<String,String> educationMap = new HashMap<>();
        hrDictService.getDictDataByDictValue("education").forEach(e->{
            educationMap.put(e.getDictDataValue().toString(),e.getDictDataKey());
        });
        Sheet sheet = wb.createSheet("sheet");
        for(int i=0;i<80;i++) {
            sheet.setColumnWidth((short) i, (short) (40 * 100));
            sheet.setDefaultRowHeight((short) 350);
        }
        if (type == 1){
            sheet.setColumnWidth((short) 2, (short) (60 * 100));
            sheet.setColumnWidth((short) 12, (short) (60 * 100));
            sheet.setColumnWidth((short) 15, (short) (60 * 100));
            Row row= sheet.createRow( 0);
            ExcelOperateUtil.createCell(row,cs,0,"入职情况统计");
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0,0+18));
            row= sheet.createRow( 1);
            ExcelOperateUtil.createCell(row,cs,0,"区域","单位","部门","管理分组","人员编码","出生日期","年龄","入司时间","用工状态","姓名","性别","岗位","身份证号码","职级","岗位序列","毕业院校","学历","年薪标准 (万元/年）","备注");
            for (int i = 0; i<list.size(); i++){
                row= sheet.createRow( i+2);
                HrEmployee e = list.get(i);
                ExcelOperateUtil.createCell(row,cs2,0,e.getArea(),e.getCompanyStr(),e.getDepartmentStr(),e.getManagerGroup(),e.getEmpNo(),e.getBirthdate()==null?"": DateTimeUtil.dateToString(e.getBirthdate()),
                        String.valueOf(e.getAge()),e.getJoinCompDate()==null?"": DateTimeUtil.dateToString(e.getJoinCompDate()),e.getIsFormalStr(),e.getEmpName(),e.getSexStr(),e.getJob(),e.getIdCard(),
                        dutyLevelMap.get(String.valueOf(e.getDutyLevelId())),jobSequenceMap.get(String.valueOf(e.getJobSequence())),e.getSchool(),educationMap.get(String.valueOf(e.getEducation())),"","");
            }
        }else if (type == 2){
            sheet.setColumnWidth((short) 2, (short) (60 * 100));
            sheet.setColumnWidth((short) 14, (short) (60 * 100));
            Row row= sheet.createRow( 0);
            ExcelOperateUtil.createCell(row,cs,0,"离职情况统计");
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0,0+21));
            row= sheet.createRow( 1);
            ExcelOperateUtil.createCell(row,cs,0,"区域","单位","部门","管理分组","人员编码","出生日期","年龄","入司时间","司龄","转正时间","用工状态","姓名","性别","岗位","身份证号码","职级","岗位序列","毕业院校","学历","年薪标准(万元/年）","离职日期","离职原因");
            for (int i = 0; i<list.size(); i++){
                row= sheet.createRow( i+2);
                HrEmployee e = list.get(i);
                ExcelOperateUtil.createCell(row,cs2,0,e.getArea(),e.getCompanyStr(),e.getDepartmentStr(),e.getManagerGroup(),e.getEmpNo(),e.getBirthdate()==null?"": DateTimeUtil.dateToString(e.getBirthdate()),
                        String.valueOf(e.getAge()),e.getJoinCompDate()==null?"": DateTimeUtil.dateToString(e.getJoinCompDate()),String.valueOf(e.getCompAge()),e.getTurnFormalDate()==null?"": DateTimeUtil.dateToString(e.getTurnFormalDate()),e.getIsFormalStr(),e.getEmpName(),e.getSexStr(),e.getJob(),e.getIdCard(),
                        dutyLevelMap.get(String.valueOf(e.getDutyLevelId())),jobSequenceMap.get(String.valueOf(e.getJobSequence())),e.getSchool(),educationMap.get(String.valueOf(e.getEducation())),"",e.getLeavedate()==null?"": DateTimeUtil.dateToString(e.getLeavedate()),String.valueOf(e.getReason()));
            }
        }else if (type == 30||type == 31){
            sheet.setColumnWidth((short) 11, (short) (60 * 100));
            sheet.setColumnWidth((short) 14, (short) (60 * 100));
            Row row= sheet.createRow( 0);
            ExcelOperateUtil.createCell(row,cs,0,"调动情况统计");
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0,0+21));
            row= sheet.createRow( 1);
            ExcelOperateUtil.createCell(row,cs,0,"区域","管理分组","人员编码","出生日期","年龄","入司时间","司龄","转正时间","用工状态","姓名","性别","身份证号码","职级","岗位序列","毕业院校","学历","调动前单位","调动前部门","调动前岗位","调动后单位","调动后部门","调动后岗位");
            for (int i = 0; i<list.size(); i++){
                row= sheet.createRow( i+2);
                HrEmployee e = list.get(i);
                ExcelOperateUtil.createCell(row,cs2,0,e.getArea(),e.getManagerGroup(),e.getEmpNo(),e.getBirthdate()==null?"": DateTimeUtil.dateToString(e.getBirthdate()),
                        String.valueOf(e.getAge()),e.getJoinCompDate()==null?"": DateTimeUtil.dateToString(e.getJoinCompDate()),String.valueOf(e.getCompAge()),e.getTurnFormalDate()==null?"": DateTimeUtil.dateToString(e.getTurnFormalDate()),e.getIsFormalStr(),e.getEmpName(),e.getSexStr(),e.getIdCard(),
                        dutyLevelMap.get(String.valueOf(e.getDutyLevelId())),jobSequenceMap.get(String.valueOf(e.getJobSequence())),e.getSchool(),educationMap.get(String.valueOf(e.getEducation())),e.getCompanyStr(),e.getDepartmentStr(),e.getJob(),e.getCompanyStr2(),e.getDepartmentStr2(),e.getJob2());
            }
        }
        return wb;
    }
}

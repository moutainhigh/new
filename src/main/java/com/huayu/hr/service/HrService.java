package com.huayu.hr.service;

import com.alibaba.fastjson.JSONArray;
import com.huayu.a.service.tools.SpringSecurityUtil;
import com.huayu.common.BaseResult;
import com.huayu.common.BusinessException;
import com.huayu.common.PageArgs;
import com.huayu.common.tool.Authority;
import com.huayu.common.tool.DateTimeUtil;
import com.huayu.common.tool.ExcelUtil;
import com.huayu.hr.dao.*;
import com.huayu.hr.domain.*;
import com.huayu.user.domain.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by DengPeng on 2016/12/7.
 */
@Service
public class HrService {

    private static Logger logger = LoggerFactory.getLogger(HrService.class);


    @Autowired
    private HrEmployeeDao hrEmployeeDao;

    @Autowired
    private HrEmployeeJobDao hrEmployeeJobDao;

    @Autowired
    private HrEmployeeIndutyDao hrEmployeeIndutyDao;

    @Autowired
    private OrgService orgService;

    @Autowired
    private HrDictService hrDictService;

    @Autowired
    private TrainingService trainingService;

    @Autowired
    private HrHomeRelationDao homeRelationDao;

    @Autowired
    private HrEducationInfoDao educationInfoDao;

    @Autowired
    private HrResumeInfoDao resumeInfoDao;

    @Autowired
    private HrJobDao hrJobDao;


    /**
     * 保存基本信息、工作信息
     * @param employee
     * @param jobInfo
     * @return
     */
    @Transactional
    public BaseResult saveEmployeeInfoAndJobInfo(HrEmployee employee, HrEmployeeJob jobInfo){
        BaseResult result = BaseResult.initBaseResult();
        Date now = new Date();
        User user = SpringSecurityUtil.getUser();
        int year = DateTimeUtil.getYear(jobInfo.getStartTime());
        int month = DateTimeUtil.getMonth(jobInfo.getStartTime());
        if (null == employee.getId()){
            employee.setCreatetime(now);
            employee.setIsDelete(0);
            if (hrEmployeeDao.addEmployeeBaseInfo(employee)!=1){
                throw new BusinessException("添加人员基本信息失败");
            }
            try {
                hrEmployeeDao.addSalaryStandard(employee);
            } catch (Exception e) {
                logger.error("添加人员(id:{})工资基础信息失败：{}",employee.getId(),e.getMessage());
            }
            if (DateTimeUtil.isAfter(jobInfo.getStartTime(),DateTimeUtil.stringToDateTime("yyyy-MM-dd","2017-09-01").toDate())){
                if (jobInfo.getCompany()==1033){
                    HrJobPlait p = new HrJobPlait();
                    p.setEmpId(employee.getId());
                    p.setYear(year);
                    p.setMonth(month);
                    p.setJobId(jobInfo.getJob());
                    orgService.occupyJobPlait(p);
                }
            }
            HrEducationInfo one = new HrEducationInfo();
            one.setEmpId(employee.getId());
            one.setTopEducation(1);
            one.setEducation(employee.getEducation());
            one.setStatus(0);
            one.setCreatetime(new Date());
            one.setDegree(93);//学位 无
            if (educationInfoDao.add(one)==0){
                throw new BusinessException("添加最高学历失败");
            }
            jobInfo.setEmpId(employee.getId());
            jobInfo.setOnGuard(1);
            jobInfo.setCreatetime(now);
            jobInfo.setCreateUser(user.getUserId().intValue());
            jobInfo.setCreatetime(new Date());
            if (hrEmployeeJobDao.post(jobInfo)!=1){
                throw new BusinessException("添加工作信息失败");
            }
            HrEmployeeInduty induty = new HrEmployeeInduty();
            BeanUtils.copyProperties(jobInfo,induty);
            induty.setEmpJobId(jobInfo.getId());
            induty.setBegindate(jobInfo.getStartTime());
            induty.setPosttype(1);//入职
            if (hrEmployeeIndutyDao.post(induty)!=1){
                throw new BusinessException("添加入职信息失败");
            }
            HrEmployee emp = new HrEmployee(jobInfo.getCompany(),jobInfo.getDepartment());
            emp.setLastEmpJobId(jobInfo.getId());
            emp.setId(employee.getId());
            if (hrEmployeeDao.updateEmployeeWorkInfo(emp)!=1){
                throw new BusinessException("修改员工信息对应工作Id失败");
            }
            result.setRdata(employee.getId());
            result.setSuccess();
        }else {
            employee.setUpdatetime(now);
            if (hrEmployeeDao.updateEmployeeBaseInfo(employee)!=1){
                throw new BusinessException("修改基本信息失败");
            }

            HrEducationInfo one = new HrEducationInfo();
            one.setEmpId(employee.getId());
            one.setUpdatetime(new Date());
            one.setEducation(employee.getEducation());
            if (educationInfoDao.updateTopEducation(one)==0){
                throw new BusinessException("修改最高学历失败");
            }
            jobInfo.setEmpId(employee.getId());
            jobInfo.setUpdatetime(now);
            jobInfo.setUpdateUser(user.getUserId().intValue());
            if (hrEmployeeJobDao.updateEmployeeJobInfo(jobInfo)!=1){
                throw new BusinessException("修改工作信息失败");
            }
            HrEmployeeInduty entity = new HrEmployeeInduty();
            entity.setEmpId(employee.getId());
            entity.setEmpJobId(jobInfo.getId());
            HrEmployeeInduty indutyOne = hrEmployeeIndutyDao.getIndutyOne(entity);
            if (null==indutyOne){
                throw new BusinessException("未找到入职信息");
            }
            indutyOne.setBegindate(jobInfo.getStartTime());
            if (null!=jobInfo.getCompany()&&null!=jobInfo.getDepartment()) {
                indutyOne.setCompany(jobInfo.getCompany());
                indutyOne.setDepartment(jobInfo.getDepartment());
            }
            indutyOne.setDutyLevel(jobInfo.getDutyLevel());
            indutyOne.setJobSequence(jobInfo.getJobSequence());
            indutyOne.setJob(jobInfo.getJob());
            indutyOne.setEmpGroup(jobInfo.getEmpGroup());
            indutyOne.setUpdatetime(now);
            if (hrEmployeeIndutyDao.updateIndutyBaseInfo(indutyOne)<=0){
                throw new BusinessException("修改入职信息失败");
            }
            if (null!=jobInfo.getCompany()&&null!=jobInfo.getDepartment()){
                HrEmployee emp = new HrEmployee(jobInfo.getCompany(),jobInfo.getDepartment());
                emp.setId(employee.getId());
                if (hrEmployeeDao.updateEmployeeWorkInfo(emp)!=1){
                    throw new BusinessException("修改员工信息对应工作Id失败");
                }
            }
            if (DateTimeUtil.isAfter(jobInfo.getStartTime(),DateTimeUtil.stringToDateTime("yyyy-MM-dd","2017-09-01").toDate())) {
                if (indutyOne.getCompany() == 1033) {
                    if (!jobInfo.getOldJob().equals(jobInfo.getJob())){
                        HrJobPlait p = new HrJobPlait();
                        p.setEmpId(employee.getId());
                        p.setYear(year);
                        p.setMonth(month);
                        p.setJobId(jobInfo.getOldJob());
                        orgService.cancelOccupyJobPlait(p);
                        p.setJobId(jobInfo.getJob());
                        p.setEmpId(employee.getId());
                        orgService.occupyJobPlait(p);
                    }
                }
            }
            result.setRdata(employee.getId());
            result.setSuccess();
        }
        return result;
    }


    /**
     * 获取基本信息 工作信息
     * @param id
     * @return
     */
    public BaseResult getEmployeeInfo(Integer id) {
        BaseResult baseResult = BaseResult.initBaseResult(true);
        HrEmployee employeeInfo = hrEmployeeDao.getEmployeeInfo(id);
        if (null != employeeInfo){
            baseResult.putRdataMap("employeeInfo",employeeInfo);
            HrEmployeeJob jobInfo = hrEmployeeJobDao.getEmployeeJobInfo(new HrEmployeeJob(id));
            baseResult.putRdataMap("jobInfo",jobInfo);
            baseResult.setSuccess();
        }
        return baseResult;
    }

    public HrEmployee getEmpBaseInfoAndCompById(Integer empId){
        HrEmployee employee = new HrEmployee();
        employee.setId(empId);
        return hrEmployeeDao.getEmpBaseInfoAndCompById(employee);
    }

    public List<HrEmployee> getEmpListData(Integer company, Integer department,List<Integer> ids){
        HrEmployee employee = new HrEmployee(company, department);
        employee.setIds(ids);
        return hrEmployeeDao.getEmpListData(employee);
    }


    public BaseResult getEmpListData(HrEmployee employee, PageArgs pageArgs) {
        BaseResult baseResult = BaseResult.initBaseResult(true);
        Page<HrEmployee> page = hrEmployeeDao.getEmpListData(employee, pageArgs);
        List<HrEmployee> content = page.getContent();
        for (HrEmployee e : content){
            if (null!=e.getDutyLevelId()){
                HrDictData dictData =  hrDictService.getDictDataOne("dutyLevel",e.getDutyLevelId());
                e.setDutyLevel(dictData.getDictDataKey());
                HrDictData jobSequenceData =  hrDictService.getDictDataOne("jobSequence",e.getJobSequence());
                e.setJobSequenceStr(jobSequenceData.getDictDataKey());
            }
        }
        baseResult.putRdataMap("data",content);
        baseResult.putRdataMap("recordsTotal",page.getTotalElements());
        baseResult.putRdataMap("recordsFiltered",page.getTotalElements());
        baseResult.setSuccess();
        return baseResult;
    }


    /**
     * 根据公司 部门 获取人员
     * @param employee
     * @return
     */
    public List<HrEmployee> getAllEmpListData(HrEmployee employee) {
        return hrEmployeeDao.getAllEmpListData(employee);
    }

    /**
     * 获取离职人员
     * @param employee
     * @return
     */
    public List<HrEmployee> getAllOutDutyEmpListData(HrEmployee employee) {
        return hrEmployeeDao.getOutDutyEmpList(employee);
    }

    /**
     * 获取在职 非正式人员
     * @param employee
     * @return
     */
    public List<HrEmployee> getNotFormalEmpData(HrEmployee employee) {
        return hrEmployeeDao.getNotFormalEmpData(employee);
    }


    @Transactional
    public BaseResult saveHomeInfo(String homeRelationArray) {
        BaseResult baseResult = BaseResult.initBaseResult();
        List<HrHomeRelation> hrHomeRelations = JSONArray.parseArray(homeRelationArray, HrHomeRelation.class);
        for (HrHomeRelation  homeRelation: hrHomeRelations){
            if (null==homeRelation.getId()){
                homeRelation.setStatus(0);
                homeRelation.setCreatetime(new Date());
                if (homeRelationDao.addHomeInfo(homeRelation)==0){
                    throw new BusinessException("添加失败");
                }
            }else {
                homeRelation.setUpdatetime(new Date());
                if (homeRelationDao.updateHomeInfo(homeRelation)==0){
                    throw new BusinessException("修改失败");
                }
            }
        }
        baseResult.setSuccess();
        return baseResult;
    }

    public List<HrHomeRelation> getHomeInfoAll(Integer empId) {
        HrHomeRelation relation = new HrHomeRelation();
        relation.setEmpId(empId);
        return homeRelationDao.getHomeInfoAll(relation);
    }

    @Transactional
    public BaseResult delHomeInfo(Integer[] relationIdArray, Integer empId) {
        BaseResult baseResult = BaseResult.initBaseResult();
        for (Integer id: relationIdArray){
            HrHomeRelation relation = new HrHomeRelation();
            relation.setId(id);
            relation.setEmpId(empId);
            relation.setDeletetime(new Date());
           if (homeRelationDao.delHomeInfo(relation)==0){
               throw new BusinessException("删除失败");
           }
        }
        baseResult.setSuccess();
        return baseResult;
    }

    @Transactional
    public BaseResult saveEducationInfo(String detailArray) {
        BaseResult baseResult = BaseResult.initBaseResult();
        List<HrEducationInfo> details = JSONArray.parseArray(detailArray, HrEducationInfo.class);
        for (HrEducationInfo one: details){
            if (null==one.getId()){
                one.setStatus(0);
                one.setCreatetime(new Date());
                if (educationInfoDao.add(one)==0){
                    throw new BusinessException("添加失败");
                }
            }else {
                one.setUpdatetime(new Date());
                if (educationInfoDao.updateOne(one)==0){
                    throw new BusinessException("修改失败");
                }
            }
        }
        baseResult.setSuccess();
        return baseResult;
    }


    @Transactional
    public BaseResult delEducationInfo(Integer[] detailArray, Integer empId) {
        BaseResult baseResult = BaseResult.initBaseResult();
        for (Integer id: detailArray){
            HrEducationInfo one = new HrEducationInfo();
            one.setId(id);
            one.setEmpId(empId);
            one.setDeletetime(new Date());
            if (educationInfoDao.delOne(one)==0){
                throw new BusinessException("删除失败");
            }
        }
        baseResult.setSuccess();
        return baseResult;
    }

    public List<HrEducationInfo> getEducationInfoAll(Integer empId) {
        HrEducationInfo one = new HrEducationInfo();
        one.setEmpId(empId);
        return educationInfoDao.getEducationInfoAll(one);
    }


    @Transactional
    public BaseResult saveResumeInfo(String detailArray) {
        BaseResult baseResult = BaseResult.initBaseResult();
        List<HrResumeInfo> details = JSONArray.parseArray(detailArray, HrResumeInfo.class);
        for (HrResumeInfo one: details){
            if (null==one.getId()){
                one.setStatus(0);
                one.setCreatetime(new Date());
                if (resumeInfoDao.addOne(one)==0){
                    throw new BusinessException("添加失败");
                }
            }else {
                one.setUpdatetime(new Date());
                if (resumeInfoDao.updateOne(one)==0){
                    throw new BusinessException("修改失败");
                }
            }
        }
        baseResult.setSuccess();
        return baseResult;
    }

    @Transactional
    public BaseResult delResumeInfo(Integer[] detailArray, Integer empId) {
        BaseResult baseResult = BaseResult.initBaseResult();
        for (Integer id: detailArray){
            HrResumeInfo one = new HrResumeInfo();
            one.setId(id);
            one.setEmpId(empId);
            one.setDeletetime(new Date());
            if (resumeInfoDao.delOne(one)==0){
                throw new BusinessException("删除失败");
            }
        }
        baseResult.setSuccess();
        return baseResult;
    }

    public List<HrResumeInfo> getResumeInfoAll(Integer empId) {
        HrResumeInfo info = new HrResumeInfo();
        info.setEmpId(empId);
        return resumeInfoDao.getResumeInfoByEmpId(info);
    }


    /**
     * 切换公司
     *
    * @param companyId
     * @param companyName
     * @return
     */
    public BaseResult switchOperCompany(Integer companyId, String companyName) {
        BaseResult baseResult = BaseResult.initBaseResult();
        HrCompany companyOne = orgService.getCompanyOne(companyId);
        if (null!=companyOne){
            String code = companyOne.getCode();
            User user = SpringSecurityUtil.getUser();
            Authority authority = user.getDataAuthorityMap();
            if (StringUtils.isNotBlank(code)&&authority.hasAuthority(code,true)){
                user.setPlateId(companyOne.getPlateId());
                user.setCurrCompanyCode(code);
                String authorityRegexp = Authority.getAuthority(authority, code).authorityRegexp();
                user.setDataAuthorityRegexp(authorityRegexp);//设置当前查询条件
                user.setCompanyName(companyName);
                user.setCurrCompanyId(companyId);
                baseResult.setSuccess();
            }else{
                baseResult.setRmsg("切换公司失败，请检查权限");
            }
        }else{
            baseResult.setRmsg("切换公司失败，未找到公司");
        }
        return baseResult;
    }

    public void buildEmpInfo2Download(HttpServletResponse response, List<Integer> ids, String type) {
        List<Workbook> workbookList = new ArrayList<>();
        List<String> nameList = new ArrayList<>();
        for (Integer id :ids){
            HrEmployee employeeInfo = hrEmployeeDao.getEmployeeInfo(id);
            HrEmployeeJob employeeJobInfo = hrEmployeeJobDao.getEmployeeJobInfo(new HrEmployeeJob(id));
            if (null==employeeInfo){
                continue;
            }
            nameList.add(employeeInfo.getEmpName()+".xls");
            Workbook wb = new HSSFWorkbook();
            CellStyle cs = ExcelUtil.createCellStyleTocolumn(wb);
            CellStyle cs2 = ExcelUtil.createCellStyleToValue(wb);
            Sheet sheet = wb.createSheet("sheet");
            for(int i=0;i<10;i++) {
                sheet.setColumnWidth((short) i, (short) (40 * 100));
                sheet.setDefaultRowHeight((short) 350);
            }

            Row row = sheet.createRow((short) 0);
            createCell(row,cs,0,"人员基本信息");
            sheet.addMergedRegion(new CellRangeAddress(0,0,0,11));

            int baseRowIndex = 1;
            int rowIndex = baseRowIndex;
            row =sheet.createRow((short) rowIndex);
            createCell(row,cs,0,"姓名");
            createCell(row,cs2,1,employeeInfo.getEmpName());
            createCell(row,cs,2,"性别");
            createCell(row,cs2,3,employeeInfo.getSex()==0?"女":employeeInfo.getSex()==1?"男":null);
            createCell(row,cs,4,"民族");
            HrDictData dictData = hrDictService.getDictDataOne("nation", employeeInfo.getNationId());
            createCell(row,cs2,5,null!=dictData?dictData.getDictDataKey():null);
            createCell(row,cs,6,"出生年月");
            createCell(row,cs2,7, DateTimeUtil.dateToString(employeeInfo.getBirthdate(),"yyyy-MM-dd"));
            createCell(row,cs,8,null);
            createCell(row,cs2,9,null);
            sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex,7,9));
            createCell(row,cs,10,"照片");
            createCell(row,cs2,11,null);
            sheet.addMergedRegion(new CellRangeAddress(rowIndex,rowIndex+6,10,11));

            /**
             * 学历
             */
            List<HrEducationInfo> eduList = educationInfoDao.getEducationInfoAll(new HrEducationInfo(id));
            HrEducationInfo topEdu = null;
            if (!CollectionUtils.isEmpty(eduList)){
                topEdu = eduList.get(0);
            }
            rowIndex = baseRowIndex+1;
            row = sheet.createRow((short) rowIndex);
            createCell(row,cs,0,"学历");
            if (null != topEdu){
                dictData = hrDictService.getDictDataOne("education", topEdu.getEducation());
            }else{
                dictData = null;
            }
            createCell(row,cs2,1,null!=dictData?dictData.getDictDataKey():null);
            createCell(row,cs,2,"专业");
            createCell(row,cs2,3,null!=topEdu.getProfession()?topEdu.getProfession():null);
            sheet.addMergedRegion(new CellRangeAddress(rowIndex,rowIndex,3,4));
            createCell(row,cs,5,"毕业院校");
            createCell(row,cs2,6,null != topEdu?topEdu.getSchool():null);
            createCell(row,cs,7,null);
            createCell(row,cs,8,null);
            createCell(row,cs,9,null);
            sheet.addMergedRegion(new CellRangeAddress(rowIndex,rowIndex,6,9));

            rowIndex = baseRowIndex+2;
            row = sheet.createRow((short) rowIndex);
            createCell(row,cs,0,"员工编号");
            createCell(row,cs2,1,employeeJobInfo.getEmpNo());
            createCell(row,cs,2,"岗位");
            HrJob job = hrJobDao.getOne(new HrJob(employeeJobInfo.getJob()));
            createCell(row,cs2,3,null!=job?job.getName():null);
            createCell(row,cs,4,"职称");
            dictData = hrDictService.getDictDataOne("technicalLevel", employeeInfo.getTechnicalLevel());
            createCell(row,cs2,5,null!=dictData?dictData.getDictDataKey():null);
            createCell(row,cs,6,"职业资格");
            createCell(row,cs2,7,null);
            createCell(row,cs,8,"执业资格");
            createCell(row,cs2,9,null);

            rowIndex = baseRowIndex+3;
            row = sheet.createRow((short) rowIndex);
            createCell(row,cs,0,"政治面貌");
            dictData = hrDictService.getDictDataOne("politicalStatus", employeeInfo.getPoliticalStatus());
            createCell(row,cs2,1,null!=dictData?dictData.getDictDataKey():null);
            createCell(row,cs,2,"婚姻状况");
            dictData = hrDictService.getDictDataOne("maritalStatus", employeeInfo.getMaritalStatus());
            createCell(row,cs2,3,null!=dictData?dictData.getDictDataKey():null);
            createCell(row,cs,4,"健康现状");
            createCell(row,cs2,5,employeeInfo.getHealthStatus());
            createCell(row,cs,6,"身高");
            createCell(row,cs2,7,null);
            createCell(row,cs,8,"体重");
            createCell(row,cs2,9,null);

            rowIndex = baseRowIndex+4;
            row = sheet.createRow((short) rowIndex);
            createCell(row,cs,0,"身份证号码");
            createCell(row,cs2,1,employeeInfo.getIdCard());
            sheet.addMergedRegion(new CellRangeAddress(rowIndex,rowIndex,1,3));
            createCell(row,cs,4,"手机号码");
            createCell(row,cs2,5,employeeInfo.getMobile());
            sheet.addMergedRegion(new CellRangeAddress(rowIndex,rowIndex,5,6));
            createCell(row,cs,7,"家庭号码");
            createCell(row,cs2,8,employeeInfo.getHomePhone());
            sheet.addMergedRegion(new CellRangeAddress(rowIndex,rowIndex,8,9));

            rowIndex = baseRowIndex+5;
            row = sheet.createRow((short) rowIndex);
            createCell(row,cs,0,"户籍地址");
            createCell(row,cs2,1,employeeInfo.getRegistrationAddress());
            sheet.addMergedRegion(new CellRangeAddress(rowIndex,rowIndex,1,4));
            createCell(row,cs,5,"户口性质");
            dictData = hrDictService.getDictDataOne("householdType", employeeInfo.getHouseholdType());
            createCell(row,cs2,6,null!=dictData?dictData.getDictDataKey():null);
            createCell(row,cs,7,"电子邮箱");
            createCell(row,cs2,8,employeeInfo.getEmail());
            sheet.addMergedRegion(new CellRangeAddress(rowIndex,rowIndex,8,9));

            rowIndex = baseRowIndex+6;
            row = sheet.createRow((short) rowIndex);
            createCell(row,cs,0,"家庭住址");
            createCell(row,cs2,1,employeeInfo.getLiveAddress());
            sheet.addMergedRegion(new CellRangeAddress(rowIndex,rowIndex,1,4));
            createCell(row,cs,5,"房屋性质");
            createCell(row,cs,6,null);
            createCell(row,cs,7,"QQ号码");
            createCell(row,cs,8,null);
            sheet.addMergedRegion(new CellRangeAddress(rowIndex,rowIndex,8,9));

            List<HrHomeRelation> relationList = homeRelationDao.getHomeInfoAll(new HrHomeRelation(id));
            rowIndex = baseRowIndex+7;
            row = sheet.createRow((short) rowIndex);
            createCell(row,cs,0,"家庭主要成员");
            sheet.addMergedRegion(new CellRangeAddress(rowIndex,rowIndex+relationList.size(),0,0));
            createCell(row,cs,1,"关系");
            createCell(row,cs,2,"姓名");
            createCell(row,cs,3,"工作单位（或居住地址）");
            sheet.addMergedRegion(new CellRangeAddress(rowIndex,rowIndex,3,6));
            createCell(row,cs,7,"职务");
            createCell(row,cs,8,"职业");
            createCell(row,cs,9,"联系电话");
            createCell(row,cs2,11,null);
            sheet.addMergedRegion(new CellRangeAddress(rowIndex,rowIndex,9,11));


            /**
             * 获取家庭关系
             */
            rowIndex++;
            if (!CollectionUtils.isEmpty(relationList)){
                for (int i=0;i<relationList.size();i++){
                    HrHomeRelation relation = relationList.get(i);
                    row = sheet.createRow((short) rowIndex);
                    createCell(row,cs2,1,null==relation?null:relation.getRelationship());
                    createCell(row,cs2,2,null==relation?null:relation.getName());
                    createCell(row,cs2,3,null);
                    sheet.addMergedRegion(new CellRangeAddress(rowIndex,rowIndex,3,6));
                    createCell(row,cs2,7,null==relation?null:relation.getDuty());
                    createCell(row,cs2,8,null==relation?null:relation.getJob());
                    createCell(row,cs2,9,null==relation?null:relation.getPhone());
                    createCell(row,cs2,11,null);
                    sheet.addMergedRegion(new CellRangeAddress(rowIndex,rowIndex,9,11));
                    rowIndex ++;
                }
            }

            List<HrResumeInfo> resumeInfoList = resumeInfoDao.getResumeInfoByEmpId(new HrResumeInfo(id));
            row = sheet.createRow((short) rowIndex);
            createCell(row,cs,0,"主要工作\n经历(倒序)");
            sheet.addMergedRegion(new CellRangeAddress(rowIndex,rowIndex+resumeInfoList.size(),0,0));
            createCell(row,cs,1,"起止时间");
            createCell(row,cs,2,null);
            sheet.addMergedRegion(new CellRangeAddress(rowIndex,rowIndex,1,2));
            createCell(row,cs,3,"岗位");
            createCell(row,cs,4,"税前年薪(万)");
            createCell(row,cs,5,"单位名称");
            sheet.addMergedRegion(new CellRangeAddress(rowIndex,rowIndex,5,6));
            createCell(row,cs,7,"离职原因");
            sheet.addMergedRegion(new CellRangeAddress(rowIndex,rowIndex,7,8));
            createCell(row,cs,9,"联系人");
            createCell(row,cs,10,"联系电话");
            createCell(row,cs,11,null);
            sheet.addMergedRegion(new CellRangeAddress(rowIndex,rowIndex,10,11));

            rowIndex ++;
            if (!CollectionUtils.isEmpty(resumeInfoList)){
                for (int i=0;i<resumeInfoList.size();i++){
                    HrResumeInfo resumeInfo = resumeInfoList.get(i);
                    row = sheet.createRow((short) rowIndex);
                    createCell(row,cs2,1,null==resumeInfo?null:DateTimeUtil.dateToString(resumeInfo.getStartTime(),"yyyy-MM-dd")+"至"+(resumeInfo.getEndTime()==null?"今":DateTimeUtil.dateToString(resumeInfo.getEndTime(),"yyyy-MM-dd")));
                    sheet.addMergedRegion(new CellRangeAddress(rowIndex,rowIndex,1,2));
                    createCell(row,cs2,3,resumeInfo.getDuty());
                    createCell(row,cs2,4,null);
                    createCell(row,cs2,5,null==resumeInfo?null:resumeInfo.getCompany());
                    sheet.addMergedRegion(new CellRangeAddress(rowIndex,rowIndex,5,6));
                    createCell(row,cs2,7,null==resumeInfo?null:resumeInfo.getLeaveReason());
                    sheet.addMergedRegion(new CellRangeAddress(rowIndex,rowIndex,7,8));
                    createCell(row,cs2,9,null==resumeInfo?null:resumeInfo.getWitness());
                    createCell(row,cs2,10,null==resumeInfo?null:resumeInfo.getWitnessPhone());
                    createCell(row,cs2,11,null);
                    sheet.addMergedRegion(new CellRangeAddress(rowIndex,rowIndex,10,11));
                    rowIndex ++;
                }
            }

            row = sheet.createRow((short) rowIndex);
            createCell(row,cs,0,"教育经历(倒序)");
            sheet.addMergedRegion(new CellRangeAddress(rowIndex,rowIndex+eduList.size(),0,0));
            createCell(row,cs,1,"起止时间");
            sheet.addMergedRegion(new CellRangeAddress(rowIndex,rowIndex,1,2));
            createCell(row,cs,3,"就读学校");
            sheet.addMergedRegion(new CellRangeAddress(rowIndex,rowIndex,3,6));
            createCell(row,cs,7,"专业");
            createCell(row,cs,8,"学历");
            createCell(row,cs,9,"学位");
            createCell(row,cs2,11,null);
            sheet.addMergedRegion(new CellRangeAddress(rowIndex,rowIndex,9,11));

            rowIndex ++;
            if (!CollectionUtils.isEmpty(eduList)){
                for (int i=0;i<eduList.size();i++){
                    HrEducationInfo educationInfo = eduList.get(i);
                    row = sheet.createRow((short) rowIndex);
                    createCell(row,cs2,1,null!=educationInfo?DateTimeUtil.dateToString(educationInfo.getAdmissionDate(),"yyyy-MM-dd")+"至"+DateTimeUtil.dateToString(educationInfo.getGraduationDate(),"yyyy-MM-dd"):null);
                    sheet.addMergedRegion(new CellRangeAddress(rowIndex,rowIndex,1,2));
                    createCell(row,cs2,3,null!=educationInfo?educationInfo.getSchool():null);
                    sheet.addMergedRegion(new CellRangeAddress(rowIndex,rowIndex,3,6));
                    createCell(row,cs2,7,null!=educationInfo.getProfession()?educationInfo.getProfession():null);
                    if (null != educationInfo){
                        dictData = hrDictService.getDictDataOne("education", educationInfo.getEducation());
                    }else{
                        dictData = null;
                    }
                    createCell(row,cs2,8,null!=dictData?dictData.getDictDataKey():null);
                    if (null != educationInfo){
                        dictData = hrDictService.getDictDataOne("degree", educationInfo.getDegree());
                    }else{
                        dictData = null;
                    }
                    createCell(row,cs2,9,null!=dictData?dictData.getDictDataKey():null);
                    createCell(row,cs2,11,null);
                    sheet.addMergedRegion(new CellRangeAddress(rowIndex,rowIndex,9,11));
                    rowIndex ++;
                }
            }

            row = sheet.createRow((short) rowIndex);
            createCell(row,cs,0,"主要培训\n经历(倒序)");
            sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 3, 0, 0));
            createCell(row,cs,1,"起止时间");
            createCell(row,cs,2,null);
            sheet.addMergedRegion(new CellRangeAddress(rowIndex,rowIndex,1,2));
            createCell(row,cs,3,"培训名称/主要内容/项目");
            createCell(row,cs,4,null);
            createCell(row,cs,5,null);
            createCell(row,cs,6,null);
            sheet.addMergedRegion(new CellRangeAddress(rowIndex,rowIndex,3,6));
            createCell(row,cs,7,"培训机构");
            createCell(row,cs,8,null);
            sheet.addMergedRegion(new CellRangeAddress(rowIndex,rowIndex,7,8));
            createCell(row,cs,9,"有无培训认证");
            createCell(row,cs,10,null);
            createCell(row,cs,11,null);
            sheet.addMergedRegion(new CellRangeAddress(rowIndex,rowIndex,9,11));

            rowIndex ++;
            row = sheet.createRow((short) rowIndex);
            createCell(row,cs2,1,null);
            createCell(row,cs2,2,null);
            sheet.addMergedRegion(new CellRangeAddress(rowIndex,rowIndex,1,2));
            createCell(row,cs2,3,null);
            createCell(row,cs2,4,null);
            createCell(row,cs2,5,null);
            createCell(row,cs2,6,null);
            sheet.addMergedRegion(new CellRangeAddress(rowIndex,rowIndex,3,6));
            createCell(row,cs2,7,null);
            createCell(row,cs2,8,null);
            sheet.addMergedRegion(new CellRangeAddress(rowIndex,rowIndex,7,8));
            createCell(row,cs2,9,null);
            createCell(row,cs2,10,null);
            createCell(row,cs2,11,null);
            sheet.addMergedRegion(new CellRangeAddress(rowIndex,rowIndex,9,11));

            rowIndex ++;
            row = sheet.createRow((short) rowIndex);
            createCell(row,cs2,1,null);
            createCell(row,cs2,2,null);
            sheet.addMergedRegion(new CellRangeAddress(rowIndex,rowIndex,1,2));
            createCell(row,cs2,3,null);
            createCell(row,cs2,4,null);
            createCell(row,cs2,5,null);
            createCell(row,cs2,6,null);
            sheet.addMergedRegion(new CellRangeAddress(rowIndex,rowIndex,3,6));
            createCell(row,cs2,7,null);
            createCell(row,cs2,8,null);
            sheet.addMergedRegion(new CellRangeAddress(rowIndex,rowIndex,7,8));
            createCell(row,cs2,9,null);
            createCell(row,cs2,10,null);
            createCell(row,cs2,11,null);
            sheet.addMergedRegion(new CellRangeAddress(rowIndex,rowIndex,9,11));

            rowIndex ++;
            row = sheet.createRow((short) rowIndex);
            createCell(row,cs2,0,null);
            createCell(row,cs2,1,null);
            createCell(row,cs2,2,null);
            sheet.addMergedRegion(new CellRangeAddress(rowIndex,rowIndex,1,2));
            createCell(row,cs2,3,null);
            createCell(row,cs2,4,null);
            createCell(row,cs2,5,null);
            createCell(row,cs2,6,null);
            sheet.addMergedRegion(new CellRangeAddress(rowIndex,rowIndex,3,6));
            createCell(row,cs2,7,null);
            createCell(row,cs2,8,null);
            sheet.addMergedRegion(new CellRangeAddress(rowIndex,rowIndex,7,8));
            createCell(row,cs2,9,null);
            createCell(row,cs2,10,null);
            createCell(row,cs2,11,null);
            sheet.addMergedRegion(new CellRangeAddress(rowIndex,rowIndex,9,11));
            workbookList.add(wb);
        }
        try {
            ExcelUtil.writeExcel(response,"人员信息附件",workbookList,nameList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createCell(Row row, CellStyle cs, int cellIndex, Object value){
        Cell cell = row.createCell(cellIndex);
        cell.setCellValue(value!=null?value.toString():"");
        cell.setCellStyle(cs);
    }
    private void createCell(Row row, CellStyle cs, Object value){
        Cell cell = row.createCell(row.getLastCellNum()==-1?0:row.getLastCellNum());
        cell.setCellValue(value!=null?value.toString():"");
        cell.setCellStyle(cs);
    }
    private void createCell(Row row, CellStyle cs, String ... value){
        for(int i=0;i<value.length;i++){
            Cell cell = row.createCell(row.getLastCellNum()==-1?i:row.getLastCellNum());
            cell.setCellValue(value[i]);
            cell.setCellStyle(cs);
        }
    }

    public void buildEmpInfo2Download(Model model, Integer empId) {
        HrEmployee employeeInfo = hrEmployeeDao.getEmployeeInfo(empId);
        if (null!=employeeInfo){
            model.addAttribute("empInfo", employeeInfo);
            HrEmployeeJob jobInfo = hrEmployeeJobDao.getEmployeeJobInfo(new HrEmployeeJob(empId));
            model.addAttribute("jobInfo", jobInfo);
            HrDictData dictData = hrDictService.getDictDataOne("nation", employeeInfo.getNationId());
            model.addAttribute("nation", null!=dictData?dictData.getDictDataKey():null);

            List<HrEducationInfo> eduList = educationInfoDao.getEducationInfoAll(new HrEducationInfo(empId));
            for (HrEducationInfo edu: eduList){
                edu.setProfessionStr(edu.getProfession());
                dictData = hrDictService.getDictDataOne("education", edu.getEducation());
                if (null!=dictData){
                    edu.setEducationStr(dictData.getDictDataKey());
                }
                dictData = hrDictService.getDictDataOne("degree", edu.getDegree());
                if (null!=dictData){
                    edu.setDegreeStr(dictData.getDictDataKey());
                }
            }
            model.addAttribute("eduList", eduList);

            HrEducationInfo topEdu = null!=eduList?eduList.get(0):null;
            dictData = hrDictService.getDictDataOne("education", null==topEdu?null:topEdu.getEducation());
            model.addAttribute("education", null!=dictData?dictData.getDictDataKey():null);

            model.addAttribute("profession", null!=topEdu.getProfession()?topEdu.getProfession():null);
            model.addAttribute("school", null != topEdu?topEdu.getSchool():null);

            HrJob job = hrJobDao.getOne(new HrJob(jobInfo.getJob()));
            model.addAttribute("jobTitle", null!=job?job.getName():null);

            dictData = hrDictService.getDictDataOne("technicalLevel", employeeInfo.getTechnicalLevel());
            model.addAttribute("technicalLevel", null!=dictData?dictData.getDictDataKey():null);

            dictData = hrDictService.getDictDataOne("politicalStatus", employeeInfo.getPoliticalStatus());
            model.addAttribute("politicalStatus", null!=dictData?dictData.getDictDataKey():null);

            dictData = hrDictService.getDictDataOne("maritalStatus", employeeInfo.getMaritalStatus());
            model.addAttribute("maritalStatus", null!=dictData?dictData.getDictDataKey():null);

            dictData = hrDictService.getDictDataOne("householdType", employeeInfo.getHouseholdType());
            model.addAttribute("householdType", null!=dictData?dictData.getDictDataKey():null);

            List<HrHomeRelation> relationList = homeRelationDao.getHomeInfoAll(new HrHomeRelation(empId));
            model.addAttribute("relationList", relationList);

            List<HrResumeInfo> resumeInfoList = resumeInfoDao.getResumeInfoByEmpId(new HrResumeInfo(empId));
            model.addAttribute("resumeInfoList", resumeInfoList);

            List<HrTrain> trains = trainingService.getTrainsByEmpId(new HrTrain(empId));
            model.addAttribute("trains", trains);
        }

    }

    public BaseResult checkIdCardRepeat(String idCardNo) {
        BaseResult baseResult = BaseResult.initBaseResult();
        if (StringUtils.isNotBlank(idCardNo)){
            HrEmployee employee = new HrEmployee();
            employee.setIdCard(idCardNo);
            Long count = hrEmployeeDao.countByIdCardNo(employee);
            if (count==0){
                baseResult.setRdata(true);
            }else{
                baseResult.setRdata(false);
            }
            baseResult.setSuccess();
        }
        return baseResult;
    }

    public BaseResult checkEmpNoRepeat(String empNo) {
        BaseResult baseResult = BaseResult.initBaseResult();
        if (StringUtils.isNotBlank(empNo)){
            HrEmployeeJob employeeJob = new HrEmployeeJob();
            employeeJob.setEmpNo(empNo);
            Long count = hrEmployeeJobDao.checkEmpNoRepeat(employeeJob);
            if (count==0){
                baseResult.setRdata(true);
            }else{
                baseResult.setRdata(false);
            }
            baseResult.setSuccess();
        }
        return baseResult;
    }

    /**
     * 根据员工编号 获取 部分员工信息
     * @param emp
     * @return
     */
    public BaseResult findEmpByEmpNo(HrEmployee emp) {
        BaseResult baseResult = BaseResult.initBaseResult();
        HrEmployee employee = hrEmployeeDao.getEmployeeByEmpNo(emp);
        if (null!=employee){
            baseResult.setSuccess();
            baseResult.setRdata(employee);
        }
        return baseResult;
    }

    public Workbook buildEmpInfo2Download(List<HrEmployee> list) {
        Workbook wb = new HSSFWorkbook();
        CellStyle cs = ExcelUtil.createCellStyleTocolumn(wb);
        CellStyle cs2 = ExcelUtil.createCellStyleToValue(wb);
        Sheet sheet = wb.createSheet("sheet");
        for(int i=0;i<80;i++) {
            sheet.setColumnWidth((short) i, (short) (40 * 100));
            sheet.setDefaultRowHeight((short) 350);
        }
        sheet.setColumnWidth((short) 2, (short) (60 * 100));
        sheet.setColumnWidth((short) 3, (short) (60 * 100));
        sheet.setColumnWidth((short) 13, (short) (65 * 100));
        sheet.setColumnWidth((short) 15, (short) (65 * 100));
        sheet.setColumnWidth((short) 21, (short) (65 * 100));
        List<HrDictData> contractCompany = hrDictService.getDictDataByDictValue("contractCompany");
        Map<Integer, String> contractCompanyMap = convert(contractCompany);
        List<HrDictData> nation = hrDictService.getDictDataByDictValue("nation");
        Map<Integer, String> nationMap = convert(nation);
        List<HrDictData> education = hrDictService.getDictDataByDictValue("education");
        Map<Integer, String> educationMap = convert(education);
        List<HrDictData> profession = hrDictService.getDictDataByDictValue("profession");
        Map<Integer, String> professionMap = convert(profession);
        List<HrDictData> maritalStatus = hrDictService.getDictDataByDictValue("maritalStatus");
        Map<Integer, String> maritalStatusMap = convert(maritalStatus);
        List<HrDictData> politicalStatus = hrDictService.getDictDataByDictValue("politicalStatus");
        Map<Integer, String> politicalStatusMap = convert(politicalStatus);
        List<HrDictData> dutyLevel = hrDictService.getDictDataByDictValue("dutyLevel");
        Map<Integer, String> dutyLevelMap = convert(dutyLevel);
        List<HrDictData> jobSequence = hrDictService.getDictDataByDictValue("jobSequence");
        Map<Integer, String> jobSequenceMap = convert(jobSequence);
        Row row= sheet.createRow( 0);
        createCell(row, cs, "序号" ,"区域","参保单位","合同单位","合同/协议","管理模块","管理单位","管理部门","管理分组","人员编码","出生日期","年龄","入司时间","司龄（自入司之日起算）","转正时间","司龄（自转正之日起算）","用工状态","姓名","性别","岗位","其他（备注）","身份证号码","职级","分类");
        createCell(row, cs, "在职状态" ,"工资","期限","起始时间","终止时间","保密","合同/协议","合同编号","备注","民族","最高学历","专业","毕业院校","证书编号","毕业时间","备注","户别","户籍地址","身份证住址","现住地址","联系电话","家庭电话","紧急联系人","紧急联系电话","婚姻" ,"身高","政治面貌");
        Date now = new Date();
        for (int i = 0; i<list.size();i++) {
            Integer rowIndex = i+1;
            HrEmployee employee = list.get(i);
            row = sheet.createRow( rowIndex.shortValue());
            createCell(row, cs2, rowIndex );
            createCell(row, cs2,  employee.getArea());
            createCell(row, cs2,  employee.getSocialInsuranceCompany());
            createCell(row, cs2,  contractCompanyMap.get(employee.getContractCompany()));
            createCell(row, cs2, employee.getContractTypeStr());
            createCell(row, cs2,  employee.getPlate());
            createCell(row, cs2,  employee.getCompanyStr());
            createCell(row, cs2,  employee.getDepartmentStr());
            createCell(row, cs2,  employee.getManagerGroup());
            createCell(row, cs2, employee.getEmpNo());
            createCell(row, cs2,  formatDate(employee.getBirthdate()));
            createCell(row, cs2,  DateTimeUtil.getYearSpace(employee.getBirthdate(),now));
            createCell(row, cs2, formatDate(employee.getJoinCompDate()));
            String companyAge;
            if (null==employee.getJoinCompDate()){
                companyAge =null;
            }else{
                companyAge = DateTimeUtil.getMonthSpace(employee.getJoinCompDate(), now)+"个月";
            }
            createCell(row, cs2,  companyAge);
            createCell(row, cs2,  formatDate(employee.getTurnFormalDate()));
            String workAge;
            if (null==employee.getTurnFormalDate()){
                workAge = null;
            }else{
                workAge = DateTimeUtil.getMonthSpace(employee.getTurnFormalDate(),now)+"个月";
            }
            createCell(row, cs2,  workAge);
            createCell(row, cs2,  null==employee.getIsFormal()?"":employee.getIsFormal()==0?"试用":"转正");
            createCell(row, cs2,  employee.getEmpName());
            createCell(row, cs2, null==employee.getSex()?"":employee.getSex()==0?"女":"男");
            createCell(row, cs2, employee.getJob());
            createCell(row, cs2,  "");
            createCell(row, cs2,  employee.getIdCard());
            createCell(row, cs2,  dutyLevelMap.get(employee.getDutyLevelId()));
            createCell(row, cs2, jobSequenceMap.get(employee.getJobSequence()));
            createCell(row, cs2,  "在职");
            createCell(row, cs2, "");
            createCell(row, cs2, employee.getPeriodCount());
            createCell(row, cs2,  formatDate(employee.getContractStartTime()));
            createCell(row, cs2,  formatDate(employee.getContractEndTime()));
            createCell(row, cs2, employee.getIsSecret());
            createCell(row, cs2,  employee.getContractTypeStr());
            createCell(row, cs2, employee.getContractNo());
            createCell(row, cs2, employee.getContractNo());
            createCell(row, cs2, nationMap.get(employee.getNationId()));
            createCell(row, cs2, educationMap.get(employee.getEducation()));
            createCell(row, cs2, professionMap.get(employee.getProfession()));
            createCell(row, cs2, employee.getSchool());
            createCell(row, cs2, "");
            createCell(row, cs2, formatDate(employee.getGraduationDate()));
            createCell(row, cs2, employee.getEducationNote());
            createCell(row, cs2, null==employee.getHouseholdType()?"":employee.getHouseholdType()==1?"非农业户口":"农业户口");
            createCell(row, cs2, employee.getRegistrationAddress());
            createCell(row, cs2, employee.getRegistrationAddress());
            createCell(row, cs2, employee.getLiveAddress());
            createCell(row, cs2, employee.getMobile());
            createCell(row, cs2, employee.getHomePhone());
            createCell(row, cs2, "");
            createCell(row, cs2, "");
            createCell(row, cs2, maritalStatusMap.get(employee.getMaritalStatus()));
            createCell(row, cs2, "");
            createCell(row, cs2, politicalStatusMap.get(employee.getPoliticalStatus()));
        }
        return wb;
    }

    private String formatDate(Date date){
        if (null==date){
            return "";
        }else{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(date);
        }
    }

    private Map<Integer,String> convert(List<HrDictData> contractCompany){
        Map<Integer,String> stringMap = new HashMap<>();
        for (HrDictData c : contractCompany){
            stringMap.put(c.getDictDataValue(),c.getDictDataKey());
        }
        return stringMap;
    }

    /**
     * 获取所有未啥子学历的人员
     * @return
     */
    public List<HrEmployee> getAllNoEducationEmp(){
        return hrEmployeeDao.getAllNoEducationEmp();
    }


}

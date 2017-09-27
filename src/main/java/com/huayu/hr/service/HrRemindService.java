package com.huayu.hr.service;

import com.huayu.a.dao.CommSequenceDao;
import com.huayu.a.domain.CommSequence;
import com.huayu.a.service.ApplicationUtil;
import com.huayu.common.BusinessException;
import com.huayu.common.tool.DateTimeUtil;
import com.huayu.common.tool.ExcelUtil;
import com.huayu.hr.dao.HrContractDao;
import com.huayu.hr.dao.HrEmployeeDao;
import com.huayu.hr.dao.HrRemindDao;
import com.huayu.hr.domain.HrContract;
import com.huayu.hr.domain.HrDictData;
import com.huayu.hr.domain.HrEmployee;
import com.huayu.hr.domain.HrRemind;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by DengPeng on 2017/5/23.
 */
@Service
public class HrRemindService {

    private Logger logger = LoggerFactory.getLogger(HrRemindService.class);

    @Autowired
    private HrEmployeeDao employeeDao;

    @Autowired
    private HrRemindDao remindDao;

    @Autowired
    private HrContractDao contractDao;

    @Autowired
    private CommSequenceDao commSequenceDao;

    @Autowired
    private HrDictService hrDictService;


    /**
     * 生日提醒
     */
    @Transactional
    public void remindBirthDate(){
        HrEmployee employee = new HrEmployee();
        Date now = new Date();
        Date startDate = DateTimeUtil.getNextMonthFirstDate(now);
        Date endDate = DateTimeUtil.getNextMonthLastDate(now);
        employee.setStartDate(DateTimeUtil.dateToString(startDate,"MMdd"));
        employee.setEndDate(DateTimeUtil.dateToString(endDate,"MMdd"));
        List<HrEmployee> birthRemindData = employeeDao.getBirthRemindData(employee);
        HrRemind remind;
        SqlParameterSource[] sqlParameterSources = new SqlParameterSource[birthRemindData.size()];
        Long[] ids = commSequenceDao.getKey(new CommSequence("hr_remind", birthRemindData.size()));
        for (int i =0; i<birthRemindData.size(); i++){
            HrEmployee emp = birthRemindData.get(i);
            remind = new HrRemind();
            remind.setId(ids[0]+i+1);
            remind.setType(1);
            remind.setEmpId(emp.getId());
            remind.setYear(DateTimeUtil.getYear(now));
            remind.setMonth(DateTimeUtil.getMonth(now));
            remind.setDate(emp.getBirthdate());
            remind.setCreatetime(now);
            remind.setStatus(0);
            sqlParameterSources[i] = new BeanPropertySqlParameterSource(remind);
        }
        saveData(1,sqlParameterSources);
    }

    /**
     * 转正统计
     */
    @Transactional
    public void remindTurnFormalDate(){
        HrContract contract = new HrContract();
        Date now = new Date();
        Date startDate = DateTimeUtil.getNextMonthFirstDate(now);
        Date endDate = DateTimeUtil.getNextMonthLastDate(now);
        contract.setStartDate(DateTimeUtil.dateToString(startDate,"yyyyMMdd"));
        contract.setEndDate(DateTimeUtil.dateToString(endDate,"yyyyMMdd"));
        List<HrContract> remindData = contractDao.getTurnFormalRemindData(contract);
        HrRemind remind;
        SqlParameterSource[] sqlParameterSources = new SqlParameterSource[remindData.size()];
        Long[] ids = commSequenceDao.getKey(new CommSequence("hr_remind", remindData.size()));
        for (int i =0; i<remindData.size(); i++){
            HrContract data = remindData.get(i);
            remind = new HrRemind();
            remind.setId(ids[0]+i+1);
            remind.setType(2);
            remind.setEmpId(data.getEmpId());
            remind.setYear(DateTimeUtil.getYear(now));
            remind.setMonth(DateTimeUtil.getMonth(now));
            remind.setDate(data.getProbEndTime());
            remind.setCreatetime(now);
            remind.setStatus(0);
            sqlParameterSources[i] = new BeanPropertySqlParameterSource(remind);
        }
        saveData(2,sqlParameterSources);
    }

    /**
     * 合同到期 统计
     */
    @Transactional
    public void remindContractEndDate(){
        HrContract contract = new HrContract();
        Date now = new Date();
        Date startDate = DateTimeUtil.getNextMonthFirstDate(now);
        Date endDate = DateTimeUtil.getNextMonthLastDate(now);
        contract.setStartDate(DateTimeUtil.dateToString(startDate,"yyyyMMdd"));
        contract.setEndDate(DateTimeUtil.dateToString(endDate,"yyyyMMdd"));
        List<HrContract> remindData = contractDao.getContractEndRemindData(contract);
        HrRemind remind;
        SqlParameterSource[] sqlParameterSources = new SqlParameterSource[remindData.size()];
        Long[] ids = commSequenceDao.getKey(new CommSequence("hr_remind", remindData.size()));
        for (int i =0; i<remindData.size(); i++){
            HrContract data = remindData.get(i);
            remind = new HrRemind();
            remind.setId(ids[0]+i+1);
            remind.setType(3);
            remind.setEmpId(data.getEmpId());
            remind.setYear(DateTimeUtil.getYear(now));
            remind.setMonth(DateTimeUtil.getMonth(now));
            remind.setDate(data.getContractEndTime());
            remind.setCreatetime(now);
            remind.setStatus(0);
            sqlParameterSources[i] = new BeanPropertySqlParameterSource(remind);
        }
        ((HrRemindService) AopContext.currentProxy()).saveData(3,sqlParameterSources);
    }

    @Transactional
    void saveData(Integer type, SqlParameterSource[] sqlParameterSources){
        HrRemindDao hrRemindDao = (HrRemindDao) ApplicationUtil.getBean("hrRemindDao");
        String sql = "INSERT INTO hr_remind (`id`, `empId`, `type`, `year`, `month`, `date`, `status`, `createtime`) VALUES (:id, :empId, :type, :year, :month, :date, :status, :createtime);";
        int[] flags= hrRemindDao.batchUpdate(sql, sqlParameterSources);
        for (int i=0;i<flags.length;i++){
            if (flags[i]==0){
                if (type==1){
                    logger.error("保存生日提醒数据失败");
                }else if (type==2){
                    logger.error("保存转正提醒数据失败");
                }else if (type==3){
                    logger.error("保存合同到期提醒数据失败");
                }
                throw new BusinessException("保存提醒数据失败");
            }
        }
    }

    /**
     * 删除提醒数据
     */
    @Transactional
    public void disableLastData(){
        Date now = new Date();
        String[] lastMonth = DateTimeUtil.getLastMonth(now);
        HrRemind remind = new HrRemind();
        remind.setYear(Integer.parseInt(lastMonth[0]));
        remind.setMonth(Integer.parseInt(lastMonth[1]));
        remind.setType(1);
        remindDao.disableLastData(remind);
    }


    public List<HrRemind> getRemindData(Integer type){
        HrRemind remind = new HrRemind();
        remind.setType(type);
        return remindDao.getRemindData(remind);
    }


    public HrRemind getRemindData(Integer type,Integer empId){
        HrRemind remind = new HrRemind();
        remind.setType(type);
        remind.setEmpId(empId);
        return remindDao.getRemindDataOne(remind);
    }

    /**
     * 导出 提醒数据
     * @param type
     * @return
     */
    public Workbook exportRemindData(Integer type) {
        HrRemind remind = new HrRemind();
        remind.setType(type);
        if (type==1){
            return buildBirthRemindData();
        }else if (type==2){
            return buildTurnFormalRemindData();
        }else if(type==3) {
            return buildContractEndRemindData();
        }else{
            return null;
        }
    }

    private Workbook buildBirthRemindData(){
        HrRemind remind = new HrRemind();
        remind.setType(1);
        List<HrRemind> reminds = remindDao.getRemindData4BirthOrTurnFormal(remind);
        Workbook wb = new HSSFWorkbook();
        CellStyle cs = ExcelUtil.createCellStyleTocolumn(wb);
        CellStyle cs2 = ExcelUtil.createCellStyleToValue(wb);
        Sheet sheet = wb.createSheet("sheet");
        for(int i=0;i<17;i++) {
            sheet.setColumnWidth((short) i, (short) (35 * 100));
            sheet.setDefaultRowHeight((short) 350);
        }
        sheet.setColumnWidth((short) 1, (short) (60 * 100));
        sheet.setColumnWidth((short) 11, (short) (60 * 100));
        sheet.setColumnWidth((short) 16, (short) (50 * 100));
        Row row= sheet.createRow( 0);
        ExcelUtil.createCell(row,cs,"生日提醒统计表");
        sheet.addMergedRegion(new CellRangeAddress(0,0,0,17));
        row= sheet.createRow( 1);
        ExcelUtil.createCell(row,cs,"序号","合同单位","管理模块","管理单位","管理部门","管理分组","人员编码","出生日期","年龄","入司时间","转正时间","工龄（从转正之日计算）","用工状态","姓名","性别","岗位","身份证号码","职级");
        List<HrDictData> dictData = hrDictService.getDictDataByDictValue("contractCompany");
        List<HrDictData> dutyLevelData = hrDictService.getDictDataByDictValue("dutyLevel");
        Map<Integer, String> convert = hrDictService.convert(dictData);
        Map<Integer, String> dutyLevelConvert = hrDictService.convert(dutyLevelData);
        Date now = new Date();
        String workAge;
        for (int i = 0; i<reminds.size();i++) {
            Integer rowIndex = i+2;
            HrRemind r = reminds.get(i);
            if (null==r.getTurnFormalDate()){
                workAge = null;
            }else{
                workAge = DateTimeUtil.getMonthSpace(r.getTurnFormalDate(),now)+"个月";
            }
            row = sheet.createRow( rowIndex.shortValue());
            ExcelUtil.createCell(row,cs2,i+1,convert.get(r.getContractCompany()),null,r.getCompanyName(),r.getDepartmentName(),r.getManagerGroup(),r.getEmpNo(),DateTimeUtil.dateToString(r.getDate()),r.getAge());
            ExcelUtil.createCell(row,cs2,DateTimeUtil.dateToString(r.getJoinCompDate()),DateTimeUtil.dateToString(r.getTurnFormalDate()),workAge,r.getIsFormalStr());
            ExcelUtil.createCell(row,cs2,r.getEmpName(),null==r.getSex()?"":r.getSex()==1?"男":"女",r.getJobName(),r.getIdCard(),dutyLevelConvert.get(r.getDutyLevel()));
        }
        return wb;
    }

    private Workbook buildTurnFormalRemindData(){
        HrRemind remind = new HrRemind();
        remind.setType(2);
        List<HrRemind> reminds = remindDao.getRemindData4BirthOrTurnFormal(remind);
        Workbook wb = new HSSFWorkbook();
        CellStyle cs = ExcelUtil.createCellStyleTocolumn(wb);
        CellStyle cs2 = ExcelUtil.createCellStyleToValue(wb);
        Sheet sheet = wb.createSheet("sheet");
        for(int i=0;i<16;i++) {
            sheet.setColumnWidth((short) i, (short) (35 * 100));
            sheet.setDefaultRowHeight((short) 350);
        }
        sheet.setColumnWidth((short) 1, (short) (60 * 100));
        sheet.setColumnWidth((short) 10, (short) (60 * 100));
        sheet.setColumnWidth((short) 15, (short) (50 * 100));
        Row row= sheet.createRow( 0);
        ExcelUtil.createCell(row,cs,"转正人员提醒统计表");
        sheet.addMergedRegion(new CellRangeAddress(0,0,0,16));
        row= sheet.createRow( 1);
        ExcelUtil.createCell(row,cs,"序号","合同单位","管理模块","管理单位","管理部门","管理分组","人员编码","出生日期","年龄","入司时间","司龄（从入职之日计算）","用工状态","姓名","性别","岗位","身份证号码","职级");
        List<HrDictData> dictData = hrDictService.getDictDataByDictValue("contractCompany");
        List<HrDictData> dutyLevelData = hrDictService.getDictDataByDictValue("dutyLevel");
        Map<Integer, String> convert = hrDictService.convert(dictData);
        Map<Integer, String> dutyLevelConvert = hrDictService.convert(dutyLevelData);
        Date now = new Date();
        String companyAge;
        for (int i = 0; i<reminds.size();i++) {
            Integer rowIndex = i+2;
            HrRemind r = reminds.get(i);
            if (null==r.getJoinCompDate()){
                companyAge =null;
            }else{
                companyAge = DateTimeUtil.getMonthSpace(r.getJoinCompDate(), now)+"个月";
            }
            row = sheet.createRow( rowIndex.shortValue());
            ExcelUtil.createCell(row,cs2,i+1,convert.get(r.getContractCompany()),null,r.getCompanyName(),r.getDepartmentName(),r.getManagerGroup(),r.getEmpNo(),DateTimeUtil.dateToString(r.getDate()),r.getAge());
            ExcelUtil.createCell(row,cs2,DateTimeUtil.dateToString(r.getJoinCompDate()),companyAge,r.getIsFormalStr());
            ExcelUtil.createCell(row,cs2,r.getEmpName(),null==r.getSex()?"":r.getSex()==1?"男":"女",r.getJobName(),r.getIdCard(),dutyLevelConvert.get(r.getDutyLevel()));
        }
        return wb;
    }

    private Workbook buildContractEndRemindData(){
        HrRemind remind = new HrRemind();
        remind.setType(3);
        List<HrRemind> reminds = remindDao.getRemindData4Contract(remind);
        Workbook wb = new HSSFWorkbook();
        CellStyle cs = ExcelUtil.createCellStyleTocolumn(wb);
        CellStyle cs2 = ExcelUtil.createCellStyleToValue(wb);
        Sheet sheet = wb.createSheet("sheet");
        for(int i=0;i<19;i++) {
            sheet.setColumnWidth((short) i, (short) (35 * 100));
            sheet.setDefaultRowHeight((short) 350);
        }
        sheet.setColumnWidth((short) 7, (short) (60 * 100));
        sheet.setColumnWidth((short) 10, (short) (70 * 100));
        sheet.setColumnWidth((short) 11, (short) (80 * 100));
        Row row = sheet.createRow(0);
        ExcelUtil.createCell(row,cs,"员工合同到期提醒统计表");
        sheet.addMergedRegion(new CellRangeAddress(0,0,0,19));
        row= sheet.createRow( 1);
        ExcelUtil.createCell(row,cs,"序号","姓名","性别","用工单位","用工部门","岗位","入职时间","身份证号码","年龄","原签订合同期限","原签订合同单位","原签订合同时间","续签期限","续签合同单位","续签时间","截止时间","合同/协议","保密","工资","备注");
        List<HrDictData> dictData = hrDictService.getDictDataByDictValue("contractCompany");
        Map<Integer, String> convert = hrDictService.convert(dictData);
        for (int i = 0; i<reminds.size();i++) {
            Integer rowIndex = i+2;
            HrRemind r = reminds.get(i);
            row = sheet.createRow( rowIndex.shortValue());
            ExcelUtil.createCell(row,cs2,i+1,r.getEmpName(),null==r.getSex()?"":r.getSex()==1?"男":"女",r.getCompanyName(),r.getDepartmentName(),r.getJobName(),DateTimeUtil.dateToString(r.getJoinCompDate()),r.getIdCard(),r.getAge());
            ExcelUtil.createCell(row,cs2,r.getPeriodCount(),convert.get(r.getContractCompany()),DateTimeUtil.dateToString(r.getContractStartTime())+"到"+DateTimeUtil.dateToString(r.getContractEndTime()),null,null,null,null,null,null,null,null);
        }
        return wb;
    }




}

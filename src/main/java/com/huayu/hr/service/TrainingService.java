package com.huayu.hr.service;

import com.huayu.a.service.tools.SpringSecurityUtil;
import com.huayu.hr.dao.*;
import com.huayu.hr.domain.HrTrain;
import com.huayu.hr.domain.HrTrainAttachment;
import com.huayu.hr.domain.HrTrainEmp;
import com.huayu.hr.domain.HrTrainStatistics;
import com.huayu.common.BaseResult;
import com.huayu.common.BusinessException;
import com.huayu.common.tool.DateTimeUtil;
import com.huayu.common.tool.ExcelOperateUtil;
import com.huayu.common.tool.ExcelUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by DengPeng on 2017/1/18.
 */
@Service
public class TrainingService {

    @Autowired
    private HrTrainDao trainDao;

    @Autowired
    private HrTrainEmpDao trainEmpDao;

    @Autowired
    private HrTrainAttachmentDao trainAttachmentDao;

    @Autowired
    private HrTrainStatisticsDao hrTrainStatisticsDao;

    @Autowired
    private HrStatisticsDataDao hrStatisticsDataDao;

    @Transactional
    public void post(HrTrain train, List<HrTrainEmp> empList, List<HrTrainAttachment> attachments) {
        train.setStatus(0);
        train.setCreateUser(SpringSecurityUtil.getUser().getUserId().intValue());
        if (trainDao.post(train)==1){
            if (null!=empList){
                for (HrTrainEmp emp : empList){
                    emp.setTrainId(train.getId());
                    emp.setStatus(0);
                    if (trainEmpDao.post(emp)<1){
                        throw new BusinessException("保存参训人员失败");
                    }
                }
            }
            if (null!=attachments){
                for (HrTrainAttachment attachment : attachments){
                    attachment.setTrainId(train.getId());
                    attachment.setStatus(0);
                    if (trainAttachmentDao.post(attachment)<1){
                        throw new BusinessException("保存培训附件失败");
                    }
                }
            }
        }
    }

    @Transactional
    public void putOne(HrTrain train) {
        train.setUpdateUser(SpringSecurityUtil.getUser().getUserId().intValue());
        if (trainDao.put(train)!=1){
            throw new BusinessException("修改培训详情失败");
        }
    }

    @Transactional
    public void saveAttachment(Integer trainId, List<HrTrainAttachment> attachments) {
        if (null!=attachments){
            for (HrTrainAttachment attachment : attachments){
                attachment.setTrainId(trainId);
                attachment.setStatus(0);
                if (trainAttachmentDao.post(attachment)<1){
                    throw new BusinessException("保存培训附件失败");
                }
            }
        }
    }

    @Transactional
    public void saveTrainEmp(Integer trainId, List<HrTrainEmp> empList) {
        if (null!=empList){
            for (HrTrainEmp emp : empList){
                if (null==emp.getId()){
                    emp.setTrainId(trainId);
                    emp.setStatus(0);
                    if (trainEmpDao.post(emp)<1){
                        throw new BusinessException("保存参训人员失败");
                    }
                }else{
                    this.putTrainEmp(emp);
                }
            }
        }
    }

    public Page<HrTrain> getTrainListData(HrTrain train, Pageable pageable) {

        return trainDao.getTrainListData(train,pageable);
    }

    public HrTrain getTrainOne(Integer id) {
        HrTrain train = new HrTrain();
        train.setStatus(0);
        train.setId(id);
        return trainDao.getOne(train);
    }

    public List<HrTrainEmp> getTrainEmpListByTrainId(Integer id) {
        HrTrainEmp emp = new HrTrainEmp();
        emp.setTrainId(id);
        emp.setStatus(0);
        return trainEmpDao.getTrainEmpList(emp);
    }


    public List<HrTrainAttachment> getAttachmentByTrain(Integer id) {
        HrTrainAttachment attach = new HrTrainAttachment();
        attach.setTrainId(id);
        attach.setStatus(0);
        return trainAttachmentDao.getAttachmentList(attach);
    }

    @Transactional
    public void delAttachment(Integer id, Integer trainId) {
        HrTrainAttachment attach = new HrTrainAttachment();
        attach.setId(id);
        attach.setTrainId(trainId);
        attach.setStatus(1);
        if (trainAttachmentDao.delAttachment(attach)!=1){
            throw new BusinessException("删除培训附件失败");
        }
    }

    @Transactional
    public void delOneTrainEmp(Integer id, Integer trainId) {
        HrTrainEmp emp = new HrTrainEmp();
        emp.setId(id);
        emp.setTrainId(trainId);
        emp.setStatus(1);
        if (trainEmpDao.delOneTrainEmp(emp)!=1){
            throw new BusinessException("删除参训人员失败");
        }
    }

    @Transactional
    public void putTrainEmp(HrTrainEmp trainEmp) {
        trainEmp.setUpdatetime(new Date());
        if (trainEmpDao.putTrainEmp(trainEmp)!=1){
            throw new BusinessException("修改参训人员失败");
        }
    }


    public Page<HrTrain> getStatisticsCompData(HrTrain train, Pageable pageable) {
        train.setStatus(0);
        return trainDao.getStatisticsCompData(train,pageable);
    }

    public Page<HrTrainEmp> getStatisticsEmpData(HrTrainEmp trainEmp, Pageable pageable) {
        trainEmp.setStatus(0);
        return trainEmpDao.getStatisticsEmpData(trainEmp,pageable);
    }

    public List<HrTrainEmp> getAllEmpTrainData(HrTrainEmp trainEmp) {
        trainEmp.setStatus(0);
        return trainEmpDao.getAllEmpTrainData(trainEmp);
    }



    public String getNextTrainNo(String code) {
        StringBuilder stringBuilder = new StringBuilder();
        String date = DateTimeUtil.dateTimeToString(DateTimeUtil.YYYYMMDD);
        stringBuilder.append(code).append(date).append(trainDao.getNextTrainNo());
        return stringBuilder.toString();
    }

    public void downloadTrainCompStatisticsData(HrTrain param, Pageable pageable, HttpServletResponse response) {
        param.setStatus(0);
        Page<HrTrain> statisticsCompData = trainDao.getStatisticsCompData(param, pageable);
        List<HrTrain> contents = statisticsCompData.getContent();
        Workbook wb = new HSSFWorkbook();
        CellStyle cs = ExcelUtil.createCellStyleTocolumn(wb);
        CellStyle cs2 = ExcelUtil.createCellStyleToValue(wb);
        Sheet sheet = wb.createSheet("培训统计");
        for (int i = 0; i < 13; i++) {
            sheet.setColumnWidth((short) i, (short) (33 * 100));
            sheet.setDefaultRowHeight((short) 350);
        }
        int baseRowIndex = 0;
        int rowIndex = 0 + baseRowIndex;
        int colIndex = 0;
        sheet.setColumnWidth(colIndex + 4,60*100);
        sheet.setColumnWidth(colIndex + 9,38*100);
        sheet.setColumnWidth(colIndex + 10,38*100);
        Row row = sheet.createRow((short) rowIndex);
        ExcelOperateUtil.createCell(row,cs,colIndex,"培训活动统计（公司）");
        ExcelOperateUtil.createNullCell(row,cs,colIndex+1,13);
        sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, colIndex,colIndex+14));
        rowIndex = 1+baseRowIndex;
        row = sheet.createRow((short) rowIndex);
        ExcelOperateUtil.createCell(row,cs,colIndex,"序号","公司","部门","培训类别","培训项目名称","培训开始时间","培训截止时间","培训级别","培训方式","参训人数(管理类)","参训人数(工人类)","培训时间(小时)","培训总时间","备注");
        for(int i=0;i<contents.size();i++){
            HrTrain train = contents.get(i);
            rowIndex = 2+i+baseRowIndex;
            row = sheet.createRow((short) rowIndex);
            ExcelOperateUtil.createCell(row,cs2,colIndex,String.valueOf(rowIndex),train.getCompanyName(),train.getDepartmentName(),train.getTypeStr(),train.getName(),
                    DateTimeUtil.dateToString(train.getStartTime(),"yyyy-MM-dd"),DateTimeUtil.dateToString(train.getEndTime(),"yyyy-MM-dd"),
                    train.getLvStr(),train.getWay(),String.valueOf(train.getCountM()),String.valueOf(train.getCountW()),String.valueOf(train.getTimeCount()),String.valueOf(train.getTotalTime()),train.getNote());
        }
        try {
            ExcelOperateUtil.writeFiles(response,wb,"培训活动统计（公司）");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void downloadTrainEmpStatisticsData(HrTrainEmp param, Pageable pageable, HttpServletResponse response) {
        param.setStatus(0);
        Page<HrTrainEmp> statisticsEmpData = trainEmpDao.getStatisticsEmpData(param, pageable);
        List<HrTrainEmp> contents = statisticsEmpData.getContent();
        Workbook wb = new HSSFWorkbook();
        CellStyle cs = ExcelUtil.createCellStyleTocolumn(wb);
        CellStyle cs2 = ExcelUtil.createCellStyleToValue(wb);
        Sheet sheet = wb.createSheet("培训统计");
        for (int i = 0; i < 12; i++) {
            sheet.setColumnWidth((short) i, (short) (33 * 100));
            sheet.setDefaultRowHeight((short) 350);
        }
        int baseRowIndex = 0;
        int rowIndex = 0 + baseRowIndex;
        int colIndex = 0;
        sheet.setColumnWidth(colIndex + 6,60*100);
        Row row = sheet.createRow((short) rowIndex);
        ExcelOperateUtil.createCell(row,cs,colIndex,"培训活动统计（个人）");
        ExcelOperateUtil.createNullCell(row,cs,colIndex+1,12);
        sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, colIndex,colIndex+13));
        rowIndex = 1+baseRowIndex;
        row = sheet.createRow((short) rowIndex);
        ExcelOperateUtil.createCell(row,cs,colIndex,"序号","公司","部门","姓名","岗位","培训类别","培训项目名称","培训讲师","培训开始日期","培训截止日期","培训方式","培训时间(小时)","培训费用","备注");
        for(int i=0;i<contents.size();i++){
            HrTrainEmp entity = contents.get(i);
            rowIndex = 2+i+baseRowIndex;
            row = sheet.createRow((short) rowIndex);
            ExcelOperateUtil.createCell(row,cs2,colIndex,String.valueOf(rowIndex),entity.getCompanyStr(),entity.getDepartmentStr(),entity.getEmpName(),entity.getJobStr(),entity.getTypeStr(),entity.getTrainName(),entity.getTeacherName(),
                    DateTimeUtil.dateToString(entity.getStartTime(),"yyyy-MM-dd"),DateTimeUtil.dateToString(entity.getEndTime(),"yyyy-MM-dd"),
                    entity.getWay(),String.valueOf(entity.getStudyTime()),String.valueOf(entity.getCost()),entity.getNote());
        }
        try {
            ExcelOperateUtil.writeFiles(response,wb,"培训活动统计（个人）");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 根据人员ID获取培训记录
     * @param train
     * @return
     */
    public List<HrTrain> getTrainsByEmpId(HrTrain train){
        train.setStatus(0);
        return trainDao.getTrainsByEmpId(train);
    }


    public BaseResult getTrainStatisticsOne(String yearMonth,Integer quarter, String year) {
        BaseResult baseResult = BaseResult.initBaseResult();
        HrTrainStatistics entity = new HrTrainStatistics();
        entity.setStatus(0);
        if (StringUtils.isNotBlank(yearMonth)){
            String[] ym = yearMonth.split("-");
            if (ym.length!=2){
                return null;
            }
            entity.setYear(ym[0]);
            entity.setMonth(ym[1]);
            baseResult.setRdata(hrTrainStatisticsDao.getList(entity));
            baseResult.setSuccess();
            return baseResult;
        }else if (null!=quarter&&StringUtils.isNotBlank(year)){
            entity.setYear(year);
            entity.setQuarter(quarter);
            baseResult.setRdata(hrTrainStatisticsDao.getList(entity));
            baseResult.setSuccess();
            return baseResult;
        }else{
            entity.setYear(year);
            baseResult.setRdata(hrTrainStatisticsDao.getList(entity));
            baseResult.setSuccess();
            return baseResult;
        }
    }

    /**
     * 统计当前时间的 培训情况
     * @return
     */
    public BaseResult statisticsTrainData() {
        BaseResult baseResult = BaseResult.initBaseResult();
        HrTrain train = new HrTrain();
        for (int i=0;i<6;i++){
            train.setPlateId(i);
            Date now = new Date();
            train.setStartTime(now);
            List<HrTrain> trainListData = trainDao.getTrainListData2Statistics(train);
            Long empSum = hrStatisticsDataDao.countOnDuty(i);
            String yy = DateTimeUtil.dateToString(now, DateTimeUtil.YYYY);
            String mm = DateTimeUtil.dateToString(now, DateTimeUtil.MM);
            HrTrainStatistics trainStatistics = new HrTrainStatistics();
            trainStatistics.setQuarter(DateTimeUtil.getSeason(now));
            trainStatistics.setEmpSum(empSum.intValue());
            BigDecimal timeCount = countStudyTime(trainListData);
            trainStatistics.setStudyTimeSum(timeCount);
            trainStatistics.setPlateId(i);
            trainStatistics.setYear(yy);
            trainStatistics.setMonth(mm);
            trainStatistics.setStatus(0);
            trainStatistics.setCreatetime(new Date());
            hrTrainStatisticsDao.deleteData(trainStatistics);
            if (hrTrainStatisticsDao.post(trainStatistics)!=1){
                throw new BusinessException("添加统计结果失败");
            }
        }
        baseResult.setSuccess();

        return baseResult;
    }

    private BigDecimal countStudyTime(List<HrTrain> trainListData){
        BigDecimal count = new BigDecimal("0");
        for (HrTrain train : trainListData){
            if (null!=train.getTotalTimeCount()){
                count = count.add(train.getTotalTimeCount());
            }
        }
        return count;
    }

}

package com.huayu.hr.web;

import com.huayu.a.service.tools.SpringSecurityUtil;
import com.huayu.common.BaseResult;
import com.huayu.common.BusinessException;
import com.huayu.common.tool.Authority;
import com.huayu.common.tool.DateTimeUtil;
import com.huayu.common.tool.ExcelUtil;
import com.huayu.hr.domain.HrEmployee;
import com.huayu.hr.domain.HrStatisticsCompanyBind;
import com.huayu.hr.domain.HrStatisticsData;
import com.huayu.hr.domain.HrStatisticsEmpChgData;
import com.huayu.hr.service.HrDictService;
import com.huayu.hr.service.HrStatisticsCompanyBindService;
import com.huayu.hr.service.HrStatisticsService;
import com.huayu.hr.service.OrgService;
import com.huayu.hr.web.args.HrStatisticsArgs;
import com.huayu.user.domain.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 报表统计
 * Created by DengPeng on 2017/1/19.
 */
@Controller
@RequestMapping("/hr/reports")
public class HrStatisticsController {

    private Logger logger = LoggerFactory.getLogger(HrStatisticsController.class);

    @Autowired
    private HrStatisticsService statisticsService;

    @Autowired
    private HrStatisticsCompanyBindService bindService;

    @Autowired
    private OrgService orgService;

    @Autowired
    private HrDictService hrDictService;


    /**
     * 公司人力盘点合计
     * @param model
     * @return
     */
    @RequestMapping("/inventoryAllManagerWorker")
    public String inventoryAllManagerWorker( Model model){
        return inventoryAllManagerWorker(DateTimeUtil.dateTimeToString(DateTimeUtil.YYYY),DateTimeUtil.dateTimeToString(DateTimeUtil.MM),model);
    }

    @RequestMapping("/inventoryAllManagerWorker/{year}/{month}")
    public String inventoryAllManagerWorker(@PathVariable String year,@PathVariable String month, Model model){
        model.addAttribute("year",year);
        model.addAttribute("month",month);
        List<HrStatisticsData> statisticsData = statisticsService.inventoryAllManagerWorker(year,month);
        HrStatisticsData countData = statisticsService.countStatisticsData(statisticsData);
        model.addAttribute("countData",countData);
        model.addAttribute("reportData",statisticsData);
        return ".hr.report.inventoryAllManagerWorker";
    }



    /**
     * 管理类 小计
     * @param model
     * @return
     */
    @RequestMapping("/inventoryAllManager")
    public String inventoryAllManager( Model model){
        return inventoryAllManager(DateTimeUtil.dateTimeToString(DateTimeUtil.YYYY),DateTimeUtil.dateTimeToString(DateTimeUtil.MM),model);
    }
    @RequestMapping("/inventoryAllManager/{year}/{month}")
    public String inventoryAllManager(@PathVariable String year,@PathVariable String month, Model model){
        inventoryAll(0,year,month,model);
        return ".hr.report.inventoryAllManager";
    }


    /**
     * 工人类 小计
     * @param model
     * @return
     */
    @RequestMapping("/inventoryAllWorker")
    public String inventoryAllWorker(Model model){
        return inventoryAllWorker(DateTimeUtil.dateTimeToString(DateTimeUtil.YYYY),DateTimeUtil.dateTimeToString(DateTimeUtil.MM),model);
    }

    @RequestMapping("/inventoryAllWorker/{year}/{month}")
    public String inventoryAllWorker(@PathVariable String year,@PathVariable String month, Model model){
        inventoryAll(1,year,month,model);
        return ".hr.report.inventoryAllWorker";
    }

    private void inventoryAll(Integer type, String year, String month, Model model){
        model.addAttribute("year",year);
        model.addAttribute("month",month);
        User user = SpringSecurityUtil.getUser();
        Authority authority = user.getDataAuthorityMap();
        List<HrStatisticsData> reportData = statisticsService.getAllStatisticsData(type, year,month,Authority.getAuthority(authority,user.getCurrCompanyCode()).authoritiesRegexp());
        HrStatisticsData countData = statisticsService.countStatisticsData(reportData);
        model.addAttribute("countData",countData);
        model.addAttribute("reportData",reportData);
    }


    /**
     * 职级
     */
    @RequestMapping("/inventoryAllDutyLv")
    public String inventoryAllDutyLv(Model model){
        return inventoryAllDutyLv(DateTimeUtil.dateTimeToString(DateTimeUtil.YYYY),DateTimeUtil.dateTimeToString(DateTimeUtil.MM),model);
    }


    @RequestMapping("/inventoryAllDutyLv/{year}/{month}")
    public String inventoryAllDutyLv(@PathVariable String year,@PathVariable String month, Model model){
        model.addAttribute("year",year);
        model.addAttribute("month",month);
        User user = SpringSecurityUtil.getUser();
        Authority authority = user.getDataAuthorityMap();
        List<HrStatisticsData> reportData = statisticsService.getAllStatisticsData(2, year,month,user.getDataAuthorityMap().getAuthority(authority,user.getCurrCompanyCode()).authoritiesRegexp());
        HrStatisticsData countData = statisticsService.countStatisticsData(reportData);
        model.addAttribute("countData",countData);
        model.addAttribute("reportData",reportData);
        return ".hr.report.inventoryAllDutyLv";
    }



    /**
     * 管理类 集团 查看
     * @param model
     * @return
     */
    @RequestMapping("/inventoryAllManagerCenter")
    public String inventoryAllManagerCenter( Model model){
        return inventoryAllManagerCenter(DateTimeUtil.dateTimeToString(DateTimeUtil.YYYY),DateTimeUtil.dateTimeToString(DateTimeUtil.MM),model);
    }

    @RequestMapping("/inventoryAllManagerCenter/{year}/{month}")
    public String inventoryAllManagerCenter(@PathVariable String year,@PathVariable String month, Model model){
        model.addAttribute("year",year);
        model.addAttribute("month",month);
        List<HrStatisticsCompanyBind> baseBindData = statisticsService.inventoryAllManagerWorkerCenter(0,year,month);
        HrStatisticsData countData = statisticsService.countBindStatisticsData(baseBindData);
        model.addAttribute("countData",countData);
        model.addAttribute("reportData",baseBindData);
        return ".hr.report.inventoryAllManagerCenter";
    }

    /**
     * 工人类 集团查看
     * @param model
     * @return
     */
    @RequestMapping("/inventoryAllWorkerCenter")
    public String inventoryAllWorkerCenter(Model model){
         return  inventoryAllWorkerCenter(DateTimeUtil.dateTimeToString(DateTimeUtil.YYYY),DateTimeUtil.dateTimeToString(DateTimeUtil.MM),model);
    }

    @RequestMapping("/inventoryAllWorkerCenter/{year}/{month}")
    public String inventoryAllWorkerCenter(@PathVariable String year,@PathVariable String month, Model model){
        model.addAttribute("year",year);
        model.addAttribute("month",month);
        List<HrStatisticsCompanyBind> baseBindData = statisticsService.inventoryAllManagerWorkerCenter(1,year,month);
        HrStatisticsData countData = statisticsService.countBindStatisticsData(baseBindData);
        model.addAttribute("countData",countData);
        model.addAttribute("reportData",baseBindData);
        return ".hr.report.inventoryAllWorkerCenter";
    }

    /**
     * 集团管理 工人类汇总
     * @param model
     * @return
     */
    @RequestMapping("/inventoryAllManagerWorkerCenter")
    public String inventoryAllManagerWorkerCenter( Model model){
        return inventoryAllManagerWorkerCenter(DateTimeUtil.dateTimeToString(DateTimeUtil.YYYY),DateTimeUtil.dateTimeToString(DateTimeUtil.MM),model);
    }

    @RequestMapping("/inventoryAllManagerWorkerCenter/{year}/{month}")
    public String inventoryAllManagerWorkerCenter(@PathVariable String year,@PathVariable String month, Model model){
        model.addAttribute("year",year);
        model.addAttribute("month",month);
        List<HrStatisticsCompanyBind> baseBindData = statisticsService.inventoryAllManagerWorkerCenter(null,year,month);
        HrStatisticsData countData = statisticsService.countBindStatisticsData(baseBindData);
        model.addAttribute("countData",countData);
        model.addAttribute("reportData",baseBindData);
        return ".hr.report.inventoryAllManagerWorkerCenter";
    }



    /**
     * 人员异动 合计 集团
     * @param model
     * @return
     */
    @RequestMapping("/inventoryAllChangeCenter")
    public String inventoryAllChangeCenter(Model model){
        return inventoryAllChangeCenter(DateTimeUtil.dateTimeToString(DateTimeUtil.YYYY),DateTimeUtil.dateTimeToString(DateTimeUtil.MM),model);
    }
    @RequestMapping("/inventoryAllChangeCenter/{year}/{month}")
    public String inventoryAllChangeCenter(@PathVariable String year,@PathVariable String month, Model model){
        model.addAttribute("year",year);
        model.addAttribute("month",month);
        List<HrStatisticsCompanyBind> baseBindData = statisticsService.inventoryAllEmpChgCenter(year,month);
        HrStatisticsEmpChgData countData = statisticsService.countBindChgStatisticsData(baseBindData);
        model.addAttribute("countData",countData);
        model.addAttribute("reportData",baseBindData);
        return ".hr.report.inventoryAllChangeCenter";
    }

    /**
     * table 展开 tr 获取详细数据
     * @param companyId
     * @param year
     * @param month
     * @return
     */
    @RequestMapping("/getInventoryManagerData")
    @ResponseBody
    public List<HrStatisticsData> getInventoryManagerData(Integer companyId, String year, String month){
        return statisticsService.getInventoryData(0,companyId,year,month);
    }

    /**
     * table 展开 tr 获取详细数据
     * @param companyId
     * @param year
     * @param month
     * @return
     */
    @RequestMapping("/getInventoryWorkerData")
    @ResponseBody
    public List<HrStatisticsData> getInventoryWorkerData(Integer companyId, String year, String month){
        return statisticsService.getInventoryData(1,companyId,year,month);
    }

    @RequestMapping("/getInventoryManagerWorkerData")
    @ResponseBody
    public List<HrStatisticsData> getInventoryManagerWorkerData(Integer companyId, String year, String month){
        return statisticsService.getInventoryData(companyId,year,month);
    }



    /**
     * 管理类 按部门分
     * @param model
     * @return
     */

    @RequestMapping("/inventoryManager")
    public String inventoryIndex(Model model){
        return inventoryManager(DateTimeUtil.dateTimeToString(DateTimeUtil.YYYY),DateTimeUtil.dateTimeToString(DateTimeUtil.MM),model);
    }

    @RequestMapping("/inventoryManager/{year}/{month}")
    public String inventoryManager(@PathVariable String year,@PathVariable String month, Model model){
        User user = SpringSecurityUtil.getUser();
        model.addAttribute("companyId",user.getCurrCompanyId());
        HrStatisticsData statisticsData = new HrStatisticsData();
        statisticsData.setCompany(user.getCurrCompanyId());
        statisticsData.setStatisticsType(0);
        statisticsData.setYear(year);
        statisticsData.setMonth(month);
        model.addAttribute("year",year);
        model.addAttribute("month",month);
        setCurrYM(model);
        List<HrStatisticsData> reportData = statisticsService.getStatisticsData(statisticsData);
        HrStatisticsData countData = statisticsService.countStatisticsData(reportData);
        model.addAttribute("countData",countData);
        model.addAttribute("reportData",reportData);
        return ".hr.report.inventoryManager";
    }



    /**
     * 查看工人类
     * @param model
     * @return
     */
    @RequestMapping("/inventoryWorker")
    public String inventoryWork(Model model){
        return inventoryWork(DateTimeUtil.dateTimeToString(DateTimeUtil.YYYY),DateTimeUtil.dateTimeToString(DateTimeUtil.MM),model);
    }

    @RequestMapping("/inventoryWorker/{year}/{month}")
    public String inventoryWork(@PathVariable String year,@PathVariable  String month, Model model){
        User user = SpringSecurityUtil.getUser();
        model.addAttribute("companyId",user.getCurrCompanyId());
        HrStatisticsData statisticsData = new HrStatisticsData();
        statisticsData.setCompany(user.getCurrCompanyId());
        statisticsData.setStatisticsType(1);
        statisticsData.setYear(year);
        statisticsData.setMonth(month);
        model.addAttribute("year",year);
        model.addAttribute("month",month);
        setCurrYM(model);
        List<HrStatisticsData> reportData = statisticsService.getStatisticsData(statisticsData);
        HrStatisticsData countData = statisticsService.countStatisticsData(reportData);
        model.addAttribute("countData",countData);
        model.addAttribute("reportData",reportData);
        return ".hr.report.inventoryWorker";
    }

    /**
     * 查看职级
     * @param model
     * @return
     */
    @RequestMapping("/inventoryDutyLv")
    public String inventoryDutyLv(Model model){
        return inventoryDutyLv(DateTimeUtil.dateTimeToString(DateTimeUtil.YYYY),DateTimeUtil.dateTimeToString(DateTimeUtil.MM),model);
    }

    @RequestMapping("/inventoryDutyLv/{year}/{month}")
    public String inventoryDutyLv(@PathVariable String year,@PathVariable String month,  Model model){
        User user = SpringSecurityUtil.getUser();
        HrStatisticsData statisticsData = new HrStatisticsData();
        statisticsData.setCompany(user.getCurrCompanyId());
        statisticsData.setStatisticsType(2);
        statisticsData.setYear(year);
        statisticsData.setMonth(month);
        model.addAttribute("year",year);
        model.addAttribute("month",month);
        setCurrYM(model);
        List<HrStatisticsData> reportData = statisticsService.getStatisticsData(statisticsData);
        HrStatisticsData countData = statisticsService.countStatisticsData(reportData);
        model.addAttribute("countData",countData);
        model.addAttribute("reportData",reportData);
        return ".hr.report.inventoryDutyLv";
    }


    /**
     * 异动情况表
     * @param model
     * @return
     */
    @RequestMapping("/inventoryChange")
    public String inventoryChange(Model model){
        return inventoryChange(DateTimeUtil.dateTimeToString(DateTimeUtil.YYYY),DateTimeUtil.dateTimeToString(DateTimeUtil.MM),model);
    }

    @RequestMapping("/inventoryChange/{year}/{month}")
    public String inventoryChange(@PathVariable String year,@PathVariable String month, Model model){
        User user = SpringSecurityUtil.getUser();
        model.addAttribute("companyId",user.getCurrCompanyId());
        HrStatisticsEmpChgData statisticsData = new HrStatisticsEmpChgData();
        statisticsData.setCompany(user.getCurrCompanyId());
        statisticsData.setYear(year);
        statisticsData.setMonth(month);
        model.addAttribute("year",year);
        model.addAttribute("month",month);
        List<HrStatisticsEmpChgData> reportData = statisticsService.getStatisticsChangeData(statisticsData);
        HrStatisticsEmpChgData countData = statisticsService.countChgStatisticsData(reportData);
        model.addAttribute("countData",countData);
        model.addAttribute("reportData",reportData);
        return ".hr.report.inventoryChange";
    }

    /**
     * 异动小计
     * @return
     */
    @RequestMapping(value = "/inventoryAllChange")
    public String inventoryAllChange(Model model) {
        return inventoryAllChange(DateTimeUtil.dateTimeToString(DateTimeUtil.YYYY),DateTimeUtil.dateTimeToString(DateTimeUtil.MM),model);
    }

    /**
     * 异动小计
     * @param model
     * @return
     */
    @RequestMapping( "/inventoryAllChange/{year}/{month}")
    public String inventoryAllChange(@PathVariable String year,@PathVariable String month, Model model){
        HrStatisticsEmpChgData statisticsData = new HrStatisticsEmpChgData();
        statisticsData.setYear(year);
        statisticsData.setMonth(month);
        model.addAttribute("year",year);
        model.addAttribute("month",month);
        List<HrStatisticsEmpChgData> reportData = statisticsService.getStatisticsAllChangeData(statisticsData);
        HrStatisticsEmpChgData countData = statisticsService.countChgStatisticsData(reportData);
        model.addAttribute("countData",countData);
        model.addAttribute("reportData",reportData);
        return ".hr.report.inventoryAllChange";
    }

    /**
     * 获取异动展开数据
     * @param companyId
     * @param year
     * @param month
     * @return
     */
    @RequestMapping("/getInventoryAllChangeData")
    @ResponseBody
    public List<HrStatisticsEmpChgData> getInventoryAllChangeData(Integer companyId, String year, String month){
        HrStatisticsEmpChgData statisticsData = new HrStatisticsEmpChgData();
        statisticsData.setYear(year);
        statisticsData.setMonth(month);
        statisticsData.setCompany(companyId);
        return  statisticsService.getStatisticsChangeData(statisticsData);
    }


    /**
     * 统计管理类
     * @param forceStatistics
     * @return
     */
    @RequestMapping("/startStatisticsManager")
    @ResponseBody
    public BaseResult startStatisticsManager(boolean forceStatistics){
        try {
            User user = SpringSecurityUtil.getUser();
            return statisticsService.statisticsManager(user.getCurrCompanyId(),forceStatistics);
        } catch (BusinessException e) {
            BaseResult baseResult = BaseResult.initBaseResult();
            baseResult.setRmsg(e.getMessage());
            return baseResult;
        }
    }

    /**
     * 统计工人类
     * @param forceStatistics
     * @return
     */
    @RequestMapping("/startStatisticsWorker")
    @ResponseBody
    public BaseResult startStatisticsWorker(boolean forceStatistics){
        try {
            User user = SpringSecurityUtil.getUser();
            return statisticsService.statisticsWorker(user.getCurrCompanyId(), forceStatistics);
        } catch (BusinessException e) {
            BaseResult baseResult = BaseResult.initBaseResult();
            baseResult.setRmsg(e.getMessage());
            return baseResult;
        }
    }

    /**
     * 统计职级
     * @param forceStatistics
     * @return
     */
    @RequestMapping("/startStatisticsDutyLevel")
    @ResponseBody
    public BaseResult startStatisticsDutyLevel(boolean forceStatistics){
        try {
            User user = SpringSecurityUtil.getUser();
            return statisticsService.statisticsDutyLevel(user.getCurrCompanyId(), forceStatistics);
        } catch (BusinessException e) {
            BaseResult baseResult = BaseResult.initBaseResult();
            baseResult.setRmsg(e.getMessage());
            return baseResult;
        }
    }

    /**
     * 统计异动
     * @param forceStatistics
     * @param year
     * @param month
     * @return
     */
    @RequestMapping("/startStatisticsChange")
    @ResponseBody
    public BaseResult startStatisticsChange(boolean forceStatistics,String year,String month){
        User user = SpringSecurityUtil.getUser();
        return statisticsService.startStatisticsChange(user.getCurrCompanyId(), forceStatistics,year,month);
    }

    private void setCurrYM(Model model){
        String yy = DateTimeUtil.dateTimeToString(DateTimeUtil.YYYY);
        String mm = DateTimeUtil.dateTimeToString(DateTimeUtil.MM);
        model.addAttribute("currYY",yy);
        model.addAttribute("currMM",mm);
    }


    @RequestMapping("/downloadStatisticsData")
    public String downloadStatisticsData(String yearMonth,Integer statisticsType, HttpServletResponse response) throws IOException {
        String[] ym;
        if (StringUtils.isBlank(yearMonth) || (ym=yearMonth.split("/")).length!=2 || null==statisticsType){
            return null;
        }
        User user = SpringSecurityUtil.getUser();
        HrStatisticsData statisticsData = new HrStatisticsData();
        statisticsData.setCompany(user.getCurrCompanyId());
        statisticsData.setStatisticsType(statisticsType);
        statisticsData.setYear(ym[0]);
        statisticsData.setMonth(ym[1]);
        StringBuilder title =new StringBuilder();
        int type = 0;
        if (statisticsType==0){
            title.append(ym[0]).append("年").append(ym[1]).append("月人力资源盘点表（管理类）");
        }else if (statisticsType==1){
            title.append(ym[0]).append("年").append(ym[1]).append("月人力资源盘点表（工人类）");
        }else if (statisticsType==2){
            title.append(ym[0]).append("年").append(ym[1]).append("月人力资源盘点表（职级现状盘点）");
            type = 1;
        }
        statisticsService.downloadStatisticsData(statisticsData, title.toString(), type,response);
        return null;
    }


    @RequestMapping("/downloadStatisticsChgData")
    public String downloadStatisticsChgData(String yearMonth,  HttpServletResponse response) throws IOException {
        String[] ym;
        if (StringUtils.isBlank(yearMonth)||(ym=yearMonth.split("/")).length!=2){
            return null;
        }
        User user = SpringSecurityUtil.getUser();
        HrStatisticsEmpChgData statisticsData = new HrStatisticsEmpChgData();
        statisticsData.setCompany(user.getCurrCompanyId());
        statisticsData.setYear(ym[0]);
        statisticsData.setMonth(ym[1]);
        StringBuilder fileName =new StringBuilder();
        fileName.append(ym[0]).append("年").append(ym[1]).append("月人力资源人员异动情况");
        statisticsService.downloadStatisticsChgData(statisticsData, fileName.toString(), response);
        return null;
    }


    @RequestMapping("/downloadAllStatisticsData")
    public String downloadAllStatisticsData(String yearMonth,Integer statisticsType, HttpServletResponse response) throws IOException {
        String[] ym ;
        if (StringUtils.isBlank(yearMonth) || null==statisticsType||(ym=yearMonth.split("/")).length!=2){
            return null;
        }
        User user = SpringSecurityUtil.getUser();
        HrStatisticsData statisticsData = new HrStatisticsData();
        statisticsData.setStatisticsType(statisticsType);
        Authority authority = user.getDataAuthorityMap();
        statisticsData.setAuthorityRegexp(Authority.getAuthority(authority,user.getCurrCompanyCode()).authoritiesRegexp());
        statisticsData.setYear(ym[0]);
        statisticsData.setMonth(ym[1]);
        StringBuilder fileName =new StringBuilder();
        if (statisticsType==0){
            fileName.append(ym[0]).append("年").append(ym[1]).append("月人力资源盘点表（管理类公司汇总）");
        }else if (statisticsType==1){
            fileName.append(ym[0]).append("年").append(ym[1]).append("月人力资源盘点表（工人类公司汇总）");
        }else if (statisticsType==2){
            fileName.append(ym[0]).append("年").append(ym[1]).append("月人力资源盘点表（职级汇总）");
        }
        statisticsService.downloadAllStatisticsData(statisticsData,fileName.toString(), response);
        return null;
    }



    @RequestMapping("/downloadAllStatisticsChgData")
    public String downloadAllStatisticsChgData(String yearMonth, HttpServletResponse response) throws IOException {
        String[] ym;
        if (StringUtils.isBlank(yearMonth) || (ym=yearMonth.split("/")).length!=2){
            return null;
        }
        HrStatisticsEmpChgData statisticsData = new HrStatisticsEmpChgData();
        statisticsData.setYear(ym[0]);
        statisticsData.setMonth(ym[1]);
        StringBuilder title =new StringBuilder();
        title.append(ym[0]).append("年").append(ym[1]).append("月人力资源人员异动情况(公司汇总)");
        statisticsService.downloadAllStatisticsChgData(statisticsData, title.toString(), response);
        return null;
    }

    /**
     * 集团报表公司目录管理
     * @param model
     * @return
     */
    @RequestMapping("/companyBind")
    public String companyBind(Model model){
        model.addAttribute("baseData",bindService.getBaseBindData(0));
        return ".hr.report.companyBind";
    }

    @RequestMapping("/getBaseBindDataByPid")
    @ResponseBody
    public List<HrStatisticsCompanyBind> getBaseBindDataByPid(Integer parentId){

        return bindService.getBaseBindData(parentId);
    }


    /**
     * 人员异动统计数据 初始化
     * @return
     */
    @RequestMapping(value = "/initEmpChgData",method = RequestMethod.GET)
    public String initEmpChgData(){

        return ".hr.report.initEmpChgData";
    }

    @RequestMapping(value = "/getEmpChgData",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult getEmpChgData(String year ,String month, Integer company, Integer department){
        return statisticsService.getInitEmpChgData(company,department,year,month);
    }


    /**
     * 初始化异动数据
     * @param year
     * @param month
     * @param company
     * @param department
     * @param onDutyM
     * @param onDutyW
     * @return
     */
    @RequestMapping(value = "/initEmpChgData",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult initEmpChgData(String year ,String month, Integer company, Integer department, Integer onDutyM, Integer onDutyW ,Integer force){
        BaseResult baseResult;
        try {
            baseResult = statisticsService.initEmpChgData(year,month,company,department,onDutyM,onDutyW,force);
        } catch (BusinessException e) {
            baseResult = BaseResult.initBaseResult(e.getMessage());
        }
        return baseResult;
    }

    @RequestMapping(value = "/queryInitEmpChgData/{companyId}/{departmentId}/{year}/{month}",method = RequestMethod.GET)
    @ResponseBody
    public BaseResult queryInitEmpChgData(@PathVariable Integer companyId,@PathVariable Integer departmentId,@PathVariable String year ,@PathVariable String month){
        return statisticsService.calculateInitEmpChgData(companyId,departmentId,year,month);
    }

    @RequestMapping(value = "/initAllEmpChgData",method = RequestMethod.GET)
    @ResponseBody
    public BaseResult initAllEmpChgData(String year ,String month){
        BaseResult baseResult;
        try {
            baseResult = statisticsService.initAllEmpChgData(year,month);
        } catch (BusinessException e) {
            baseResult = BaseResult.initBaseResult(e.getMessage());
        }
        return baseResult;
    }

    /**
     * 临时
     * 清除公司目录绑定缓存
     * @return
     */
    @RequestMapping(value = "/clearBindCache",method = RequestMethod.GET)
    @ResponseBody
    public BaseResult clearBindCache(){
        return bindService.clearCache();
    }

    /**
     * 人员异动查看每天
     * @param model
     * @return
     */
    @RequestMapping("/empIndutyDaily")
    public String empIndutyDaily(Model model){
        User user = SpringSecurityUtil.getUser();
        model.addAttribute("companyId",user.getCurrCompanyId());
        model.addAttribute("departmentList",orgService.getAllBaseDepartments(user.getCurrCompanyId()));
        model.addAttribute("jobSequence",hrDictService.getDictDataByDictValue("jobSequence"));
        model.addAttribute("dutyLevel",hrDictService.getDictDataByDictValue("dutyLevel"));
        return ".hr.report.empIndutyDaily";
    }

    @RequestMapping("/empOutdutyDaily")
    public String empOutdutyDaily(Model model){
        User user = SpringSecurityUtil.getUser();
        model.addAttribute("companyId",user.getCurrCompanyId());
        model.addAttribute("departmentList",orgService.getAllBaseDepartments(user.getCurrCompanyId()));
        model.addAttribute("jobSequence",hrDictService.getDictDataByDictValue("jobSequence"));
        model.addAttribute("dutyLevel",hrDictService.getDictDataByDictValue("dutyLevel"));
        return ".hr.report.empOutdutyDaily";
    }

    @RequestMapping("/empTransferDaily")
    public String empTransferDaily(Model model){
        User user = SpringSecurityUtil.getUser();
        model.addAttribute("companyId",user.getCurrCompanyId());
        model.addAttribute("departmentList",orgService.getAllBaseDepartments(user.getCurrCompanyId()));
        model.addAttribute("jobSequence",hrDictService.getDictDataByDictValue("jobSequence"));
        model.addAttribute("dutyLevel",hrDictService.getDictDataByDictValue("dutyLevel"));
        model.addAttribute("type",30);
        return ".hr.report.empTransferDaily";
    }

    @RequestMapping("/detail/{toPage}/{companyId}/{jobSequenceId}/{yyyy}/{mm}")
    public String empDaily(Model model, @PathVariable String toPage, @PathVariable Integer companyId, @PathVariable Integer jobSequenceId, @PathVariable Integer yyyy, @PathVariable Integer mm){
        if ("empIndutyDaily".equals(toPage)){
            model.addAttribute("type",1);
        }else if ("empOutdutyDaily".equals(toPage)){
            model.addAttribute("type",2);
        }else if ("empTransferInDaily".equals(toPage)){
            model.addAttribute("type",30);
            toPage = "empTransferDaily";
        }else if ("empTransferOutDaily".equals(toPage)){
            model.addAttribute("type",31);
            toPage = "empTransferDaily";
        }else{
            return ".admin.index.404";
        }
        model.addAttribute("companyId",companyId);
        model.addAttribute("jobSequenceId",jobSequenceId);
        model.addAttribute("startDate",DateTimeUtil.dateToString(DateTimeUtil.getFirstDateOfMonth(yyyy,mm)));
        model.addAttribute("endDate",DateTimeUtil.dateToString(DateTimeUtil.getLastDateOfMonth(yyyy,mm)));
        model.addAttribute("departmentList",orgService.getAllBaseDepartments(companyId));
        model.addAttribute("jobSequence",hrDictService.getDictDataByDictValue("jobSequence"));
        model.addAttribute("dutyLevel",hrDictService.getDictDataByDictValue("dutyLevel"));
        model.addAttribute("initSearch",true);
        return ".hr.report."+toPage;
    }

    @RequestMapping("/detail/{toPage}/{jobSequenceId}/{yyyy}/{mm}")
    public String empAllDaily(Model model, @PathVariable String toPage, @PathVariable Integer jobSequenceId,@PathVariable Integer yyyy,@PathVariable Integer mm){
        if ("empIndutyDaily".equals(toPage)){
            model.addAttribute("type",1);
        }else if ("empOutdutyDaily".equals(toPage)){
            model.addAttribute("type",2);
        }else if ("empTransferInDaily".equals(toPage)){
            model.addAttribute("type",30);
            toPage = "empTransferDaily";
        }else if ("empTransferOutDaily".equals(toPage)){
            model.addAttribute("type",31);
            toPage = "empTransferDaily";
        }else{
            return ".admin.index.404";
        }
        model.addAttribute("jobSequenceId",jobSequenceId);
        model.addAttribute("startDate",DateTimeUtil.dateToString(DateTimeUtil.getFirstDateOfMonth(yyyy,mm)));
        model.addAttribute("endDate",DateTimeUtil.dateToString(DateTimeUtil.getLastDateOfMonth(yyyy,mm)));
        model.addAttribute("jobSequence",hrDictService.getDictDataByDictValue("jobSequence"));
        model.addAttribute("dutyLevel",hrDictService.getDictDataByDictValue("dutyLevel"));
        model.addAttribute("initSearch",true);
        model.addAttribute("searchAll",true);
        model.addAttribute("searchNow",false);
        return ".hr.report."+toPage;
    }

    @RequestMapping(value = "/getEmpChangeDailyData",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult getEmpChangeDailyData(HrStatisticsArgs args){
        return statisticsService.getEmpChangeDailyData(args);
    }

    /**
     * 导出异动详情
     * @param args
     * @return
     */
    @RequestMapping(value = "/exportEmpChangeDailyData",method = RequestMethod.POST)
    public String exportEmpChangeDailyData(HrStatisticsArgs args, HttpServletResponse response){
        List<HrEmployee> list = statisticsService.getEmpChangeDailyDataForExport(args);
        try {
            Workbook workbook = statisticsService.exportEmpChangeDailyData(args.getType(),list);
            String fileName = null;
            if (args.getType() == 1){
                fileName = "入职统计";
            }else if (args.getType() == 2){
                fileName = "离职统计";
            }else if (args.getType() == 30){
                fileName = "调入统计";
            }else if (args.getType() == 31){
                fileName = "调出统计";
            }
            ExcelUtil.writeExcel(response,workbook,fileName);
        } catch (IOException e) {
            logger.error("",e);
        }
        return null;
    }

}

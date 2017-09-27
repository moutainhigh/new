package com.huayu.hr.web;

import com.huayu.common.BaseResult;
import com.huayu.common.PageArgs;
import com.huayu.common.tool.ExcelUtil;
import com.huayu.hr.domain.HrRecruitmentNeeds;
import com.huayu.hr.service.HrRecruitmentNeedsService;
import com.huayu.hr.web.args.HrRecruitmentNeedsArgs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/15.
 */
@Controller
@RequestMapping("/admin/hr/recruitmentNeeds")
public class HrRecruitmentNeedsController {

    private static Logger logger = LoggerFactory.getLogger(HrRecruitmentNeedsController.class);
    @Autowired
    private HrRecruitmentNeedsService hrRecruitmentNeedsService;

    @RequestMapping("/toNeedsList")
    public String toNeedsList() {
        return ".hr.recruitment.hrRecruitmentNeedsList";
    }

    /**
     * 招聘需求列表
     * @param param
     * @param hrRecruitmentNeedsArgs
     * @param args
     * @return
     */
    @RequestMapping(value = "/listNeedsData", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> listNeedsData(@RequestParam Map<String,Object> param, HrRecruitmentNeedsArgs hrRecruitmentNeedsArgs, PageArgs args){
        Pageable pageable = new PageRequest(args.getPageIndex(),args.getLength(), Sort.Direction.DESC,"createtime");
        Page<HrRecruitmentNeeds> page = hrRecruitmentNeedsService.listNeedsData(hrRecruitmentNeedsArgs,pageable);
        param.put("data",page.getContent());
        param.put("recordsTotal",page.getTotalElements());
        param.put("recordsFiltered",page.getTotalElements());
        return param;
    }

    /**
     * 导出招聘需求
     * @param response
     * @param hrRecruitmentNeedsArgs
     * @param args
     * @return
     */
    @RequestMapping(value = "/exportNeedsData", method = RequestMethod.POST)
    public String exportUnusualSignDetail(HttpServletResponse response, HrRecruitmentNeedsArgs hrRecruitmentNeedsArgs, PageArgs args) {
        try {
            args.setStart(1);
            args.setLength(999999);
            Pageable pageable = new PageRequest(args.getPageIndex(),args.getLength(), Sort.Direction.DESC,"askDate");
            Page<HrRecruitmentNeeds> page = hrRecruitmentNeedsService.listNeedsData(hrRecruitmentNeedsArgs,pageable);
            List<HrRecruitmentNeeds> list = page.getContent();
            for (int i = 0;i < list.size();i++) {
                HrRecruitmentNeeds hrRecruitmentNeeds = list.get(i);
                hrRecruitmentNeeds.setIndex(i+1);
            }
            String fileName = "招聘需求汇总";
            String[] columnNames = {"序号","中心/公司","部门","职务","架构层级","管线","层级","职级","招聘人数","需求提出时间","计划到岗时间","岗位职责","性别","年龄",
                    "学历","专业","工作经验","资格证书"};
            String[] keys = {"index","company","department","job","companyLevel","manageGroup","departmentLevel","dutyLevel","addNum","askDate","planJoinDate",
                    "jobResponsi","sex","age","education","profession","workExperience","profesRequire"};
            ExcelUtil.downloadExcel(response, list,fileName,columnNames,keys);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(e.getMessage());
        }
        return null;
    }

    /**
     * 跳转到按职级汇总页面
     * @return
     */
    @RequestMapping("/toNeedsDutyLevel")
    public String toNeedsStatisticsPage() {
        return ".hr.recruitment.needsStatisticsByDutyLevel";
    }

    /**
     * 按职级汇总
     * @param entity
     * @return
     */
    @RequestMapping(value = "/statisticsDataByDutyLevel",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult statisticsDataByDutyLevel(HrRecruitmentNeedsArgs entity) {
        BaseResult baseResult = BaseResult.initBaseResult();
        try {
            List<HrRecruitmentNeeds> statistics = hrRecruitmentNeedsService.statisticsDataByDutyLevel(entity);
            baseResult.setRdata(statistics);
            baseResult.setSuccess();
        }catch (Exception e){
            baseResult.setRmsg(e.getMessage());
        }
        return baseResult;
    }

    @RequestMapping(value = "/exportDutyLevel", method = RequestMethod.POST)
    public String exportDutyLevel(HttpServletResponse response, HrRecruitmentNeedsArgs hrRecruitmentNeedsArgs) {
        try {
            List<HrRecruitmentNeeds> list = hrRecruitmentNeedsService.statisticsDataByDutyLevel(hrRecruitmentNeedsArgs);
            HrRecruitmentNeeds hrRecruitmentNeeds = new HrRecruitmentNeeds();
            Integer topManage=0, subsidiaryTopManage=0, topInspector=0, subsidiaryTopInspector=0, manage=0, subsidiaryManage=0, charge=0, director=0, staff=0, summation=0;
            for (int i=list.size()-1;i>=0;i--) {
                HrRecruitmentNeeds h = list.get(i);
                if (null != h.getTopManage()) {
                    topManage += h.getTopManage();
                }
                if (null != h.getSubsidiaryTopManage()) {
                    subsidiaryTopManage += h.getSubsidiaryTopManage();
                }
                if (null != h.getTopInspector()) {
                    topInspector += h.getTopInspector();
                }
                if (null != h.getSubsidiaryTopInspector()) {
                    subsidiaryTopInspector += h.getSubsidiaryTopInspector();
                }
                if (null != h.getManage()) {
                    manage += h.getManage();
                }
                if (null != h.getSubsidiaryManage()) {
                    subsidiaryManage += h.getSubsidiaryManage();
                }
                if (null != h.getCharge()) {
                    charge += h.getCharge();
                }
                if (null != h.getDirector()) {
                    director += h.getDirector();
                }
                if (null != h.getStaff()) {
                    staff += h.getStaff();
                }
                if (null != h.getSummation()) {
                    summation += h.getSummation();
                }
            }
            hrRecruitmentNeeds.setCompany("合计");
            hrRecruitmentNeeds.setTopManage(topManage);
            hrRecruitmentNeeds.setSubsidiaryTopManage(subsidiaryTopManage);
            hrRecruitmentNeeds.setTopInspector(topInspector);
            hrRecruitmentNeeds.setSubsidiaryTopInspector(subsidiaryTopInspector);
            hrRecruitmentNeeds.setManage(manage);
            hrRecruitmentNeeds.setSubsidiaryManage(subsidiaryManage);
            hrRecruitmentNeeds.setCharge(charge);
            hrRecruitmentNeeds.setDirector(director);
            hrRecruitmentNeeds.setStaff(staff);
            hrRecruitmentNeeds.setSummation(summation);
            list.add(hrRecruitmentNeeds);
            String fileName = "按职级汇总";
            String[] columnNames = {"中心(公司)\\职级","总经理级","副总经理级","总监级","副总监级","经理级","副经理级","主管级","主办级","员级","合计"};
            String[] keys = {"company","topManage","subsidiaryTopManage","topInspector","subsidiaryTopInspector","manage","subsidiaryManage","charge","director","staff","summation"};
            ExcelUtil.downloadExcel(response, list,fileName,columnNames,keys);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(e.getMessage());
        }
        return null;
    }

    /**
     * 跳转到按管线汇总页面
     * @return
     */
    @RequestMapping("/toNeedsManageGroup")
    public String toNeedsManageGroup() {
        return ".hr.recruitment.needsStatisticsByManageGroup";
    }

    /**
     * 管线列表
     * @param entity
     * @return
     */
    @RequestMapping(value = "/statisticsDataByManageGroup",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult statisticsDataByManageGroup(HrRecruitmentNeedsArgs entity) {
        BaseResult baseResult = BaseResult.initBaseResult();
        List<HrRecruitmentNeeds> statistics = hrRecruitmentNeedsService.statisticsDataByManageGroup(entity);
        try {
            baseResult.setRdata(statistics);
            baseResult.setSuccess();
        }catch (Exception e){
            baseResult.setRmsg(e.getMessage());
        }
        return baseResult;
    }

    /**
     * 导出管线汇总
     * @param response
     * @param hrRecruitmentNeedsArgs
     * @return
     */
    @RequestMapping(value = "/exportManageGroup", method = RequestMethod.POST)
    public String exportManageGroup(HttpServletResponse response, HrRecruitmentNeedsArgs hrRecruitmentNeedsArgs) {
        try {
            List<HrRecruitmentNeeds> list = hrRecruitmentNeedsService.statisticsDataByManageGroup(hrRecruitmentNeedsArgs);
            HrRecruitmentNeeds hrRecruitmentNeeds = new HrRecruitmentNeeds();
            Integer sum = 0;
            for (HrRecruitmentNeeds h :list) {
                sum += h.getManageGroupNum();
            }
            hrRecruitmentNeeds.setManageGroup("汇总");
            hrRecruitmentNeeds.setManageGroupNum(sum);
            list.add(hrRecruitmentNeeds);
            String fileName = "按管线汇总";
            String[] columnNames = {"管线","汇总"};
            String[] keys = {"manageGroup","manageGroupNum"};
            ExcelUtil.downloadExcel(response, list,fileName,columnNames,keys);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(e.getMessage());
        }
        return null;
    }

    /**
     * 跳转到按层级汇总页面
     * @return
     */
    @RequestMapping("/toNeedsDepartmentLevel")
    public String toNeedsDepartmentLevel() {
        return ".hr.recruitment.needsStatisticsByDepartmentLevel";
    }

    @RequestMapping(value = "/statisticsDataByDepartmentLevel",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult statisticsDataByDepartmentLevel(HrRecruitmentNeedsArgs entity) {
        BaseResult baseResult = BaseResult.initBaseResult();
        List<HrRecruitmentNeeds> statistics = hrRecruitmentNeedsService.statisticsDataByDepartmentLevel(entity);
        try {
            baseResult.setRdata(statistics);
            baseResult.setSuccess();
        }catch (Exception e){
            baseResult.setRmsg(e.getMessage());
        }
        return baseResult;
    }

    /**
     * 导出层级汇总
     * @param response
     * @param hrRecruitmentNeedsArgs
     * @return
     */
    @RequestMapping(value = "/exportDepartmentLevel", method = RequestMethod.POST)
    public String exportDepartmentLevel(HttpServletResponse response, HrRecruitmentNeedsArgs hrRecruitmentNeedsArgs) {
        try {
            List<HrRecruitmentNeeds> list = hrRecruitmentNeedsService.statisticsDataByDepartmentLevel(hrRecruitmentNeedsArgs);
            HrRecruitmentNeeds hrRecruitmentNeeds = new HrRecruitmentNeeds();
            Integer sum = 0;
            for (HrRecruitmentNeeds h :list) {
                sum += h.getManageGroupNum();
            }
            hrRecruitmentNeeds.setDepartmentLevel("汇总");
            hrRecruitmentNeeds.setManageGroupNum(sum);
            list.add(hrRecruitmentNeeds);
            String fileName = "按层级汇总";
            String[] columnNames = {"层级","汇总"};
            String[] keys = {"departmentLevel","manageGroupNum"};
            ExcelUtil.downloadExcel(response, list,fileName,columnNames,keys);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(e.getMessage());
        }
        return null;
    }

    /**
     * 跳转到按架构层级汇总页面
     * @return
     */
    @RequestMapping("/toNeedsCompanyLevel")
    public String toNeedsCompanyLevel() {
        return ".hr.recruitment.needsStatisticsByCompanyLevel";
    }

    /**
     * 架构层级汇总列表
     * @param entity
     * @return
     */
    @RequestMapping(value = "/statisticsDataByCompanyLevel",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult statisticsDataByCompanyLevel(HrRecruitmentNeedsArgs entity) {
        BaseResult baseResult = BaseResult.initBaseResult();
        List<HrRecruitmentNeeds> statistics = hrRecruitmentNeedsService.statisticsDataByCompanyLevel(entity);
        try {
            baseResult.setRdata(statistics);
            baseResult.setSuccess();
        }catch (Exception e){
            baseResult.setRmsg(e.getMessage());
        }
        return baseResult;
    }

    /**
     * 导出架构层级汇总
     * @param response
     * @param hrRecruitmentNeedsArgs
     * @return
     */
    @RequestMapping(value = "/exportCompanyLevel", method = RequestMethod.POST)
    public String exportCompanyLevel(HttpServletResponse response, HrRecruitmentNeedsArgs hrRecruitmentNeedsArgs) {
        try {
            List<HrRecruitmentNeeds> list = hrRecruitmentNeedsService.statisticsDataByCompanyLevel(hrRecruitmentNeedsArgs);
            HrRecruitmentNeeds hrRecruitmentNeeds = new HrRecruitmentNeeds();
            Integer sum = 0;
            for (HrRecruitmentNeeds h :list) {
                sum += h.getManageGroupNum();
            }
            hrRecruitmentNeeds.setCompanyLevel("汇总");
            hrRecruitmentNeeds.setManageGroupNum(sum);
            list.add(hrRecruitmentNeeds);
            String fileName = "按架构层级汇总";
            String[] columnNames = {"架构层级","汇总"};
            String[] keys = {"companyLevel","manageGroupNum"};
            ExcelUtil.downloadExcel(response, list,fileName,columnNames,keys);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(e.getMessage());
        }
        return null;
    }

}

package com.huayu.hr.service;

import com.huayu.hr.dao.HrContentDao;
import com.huayu.hr.dao.HrJobRequestDao;
import com.huayu.hr.domain.HrJobRequest;
import com.huayu.hr.domain.HrRecruitmentContent;
import com.huayu.a.service.tools.SpringSecurityUtil;
import com.huayu.common.BaseResult;
import com.huayu.common.PageArgs;
import com.huayu.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by DengPeng on 2016/12/28.
 */
@Service
public class HrContentService {

    @Autowired
    private HrContentDao hrContentDao;


    @Autowired
    private HrJobRequestDao jobRequestDao;


    public Page<HrRecruitmentContent> getContentList(HrRecruitmentContent content, Pageable pageable){

        return hrContentDao.getContentList(content, pageable);
    }

    public List<HrRecruitmentContent> getContentList(HrRecruitmentContent content){
        content.setStatus(0);
        return hrContentDao.getContentList(content);
    }

    public List<HrRecruitmentContent> getContentTitleList(HrRecruitmentContent content){
        content.setStatus(0);
        return hrContentDao.getContentTitleList(content);
    }
    /**
     * 修改 添加内容
     * @param content
     * @return
     */
    public BaseResult saveContent(HrRecruitmentContent content) {
        BaseResult baseResult = BaseResult.initBaseResult();
        if (null == content.getId()){
            User user = SpringSecurityUtil.getUser();
            content.setCreatetime(new Date());
            content.setUpdatetime(new Date());
            content.setStatus(0);
            content.setCreateUser(user.getUserId());
            if (hrContentDao.addContent(content)==1){
                baseResult.setSuccess();
            }
        } else {
            content.setUpdatetime(new Date());
            if (hrContentDao.updateContent(content)==1){
                baseResult.setSuccess();
            }
        }
        return baseResult;
    }

    /**
     * 获取单条
     * @param id
     * @return
     */
    public HrRecruitmentContent getContentOne(Integer id) {
        HrRecruitmentContent content = new HrRecruitmentContent();
        content.setId(id);
        content.setIdName("id");
        return hrContentDao.getOne(content);
    }

    /**
     *删除 状态2
     * @param id
     * @return
     */
    public BaseResult delContent(Integer id) {
        BaseResult baseResult = BaseResult.initBaseResult();
        HrRecruitmentContent content = new HrRecruitmentContent();
        content.setId(id);
        content.setIdName("id");
        content.setDeletetime(new Date());
        content.setStatus(2);
        if (hrContentDao.delContent(content)==1){
            baseResult.setSuccess();
        }
        return baseResult;
    }


    /**
     * 提交求职申请
     * @param jobRequest
     * @return
     */
    public BaseResult postJobRequest(HrJobRequest jobRequest){
        BaseResult baseResult = BaseResult.initBaseResult();
        jobRequest.setCreatetime(new Date());
        jobRequest.setStatus(0);
        if (jobRequestDao.postJobRequest(jobRequest)==1){
            baseResult.setSuccess();
        }
        return baseResult;
    }

    /**
     * 获取求职信息列表
     * @param hrJobRequest
     * @param pageArgs
     * @return
     */
    public Page<HrJobRequest> getJobRequestListData(HrJobRequest hrJobRequest, PageArgs pageArgs) {
        Pageable pageable = new PageRequest(pageArgs.getPageIndex(), pageArgs.getLength(), Sort.Direction.ASC,"createtime");
        return jobRequestDao.getJobRequestListData(hrJobRequest, pageable);
    }

    /**
     * 获取单个信息
     * @param jobRequest
     * @return
     */
    public HrJobRequest getJobRequestOne(HrJobRequest jobRequest){

        return jobRequestDao.getJobRequestOne(jobRequest);
    }

    /**
     * 删除求职信息（更新状态）
     * @param hrJobRequest
     * @return
     */
    public BaseResult delJobRequestOne(HrJobRequest hrJobRequest) {
        BaseResult baseResult = BaseResult.initBaseResult();
        hrJobRequest.setStatus(2);
        hrJobRequest.setOperateuser(SpringSecurityUtil.getUser().getUserId());
        if (jobRequestDao.updateJobRequestOneStatus(hrJobRequest)==1){
            baseResult.setSuccess();
        }
        return baseResult;
    }

    /**
     * 设置求职信息状态
     * @param hrJobRequest
     * @return
     */
    public BaseResult updateJobRequest(HrJobRequest hrJobRequest) {
        BaseResult baseResult = BaseResult.initBaseResult();
        hrJobRequest.setOperateuser(SpringSecurityUtil.getUser().getUserId());
        if (jobRequestDao.updateJobRequestOneStatus(hrJobRequest)==1){
            baseResult.setSuccess();
        }
        return baseResult;
    }


}

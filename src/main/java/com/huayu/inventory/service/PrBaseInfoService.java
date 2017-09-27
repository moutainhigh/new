package com.huayu.inventory.service;

import com.huayu.a.service.tools.SpringSecurityUtil;
import com.huayu.common.BusinessException;
import com.huayu.common.ConstantsHolder;
import com.huayu.inventory.dao.PrItemDeliveryDao;
import com.huayu.inventory.dao.PrProjectDao;
import com.huayu.inventory.dao.PrRepositoryDao;
import com.huayu.inventory.domain.PrItemDelivery;
import com.huayu.inventory.domain.PrProject;
import com.huayu.inventory.domain.PrRepository;
import com.huayu.inventory.web.args.PrProjectArgs;
import com.huayu.inventory.web.args.PrRepositoryArgs;
import com.huayu.user.domain.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by DengPeng on 2017/6/12.
 */
@Service
public class PrBaseInfoService {

    @Autowired
    private PrProjectDao prProjectDao;

    @Autowired
    private PrRepositoryDao prRepositoryDao;

    @Autowired
    private PrItemDeliveryDao prItemDeliveryDao;

    public List<PrProject> getAllPrProject(){
        return  prProjectDao.getAll();
    }

    @Transactional
    public void insertStorage(PrRepositoryArgs repository) {
        User user = SpringSecurityUtil.getUser();
        Date now = new Date();
        if (repository.getProjectId() == -1 && StringUtils.isNotBlank(repository.getProjectName())){
            PrProject prProject = new PrProject();
            prProject.setName(repository.getProjectName());
            prProject.setCreatetime(now);
            prProject.setCreateUser(user.getUserId().intValue());
            if (prProjectDao.addPrProject(prProject)!=1){
                throw new BusinessException("添加项目失败!");
            }
            repository.setProjectId(prProject.getId());
            repository.setPcode(prProject.getPcode());

        }
        List<PrRepository> repositories = getAllRepositories(repository.getProjectId());
        repository.setCreatetime(now);
        repository.setCreateUser(user.getUserId().intValue());
        repository.setStatus(0);
        repository.setIndex(repositories.size()+1);
        if (prRepositoryDao.postData(repository)!=1){
            throw new BusinessException("添加仓库失败!");
        }
    }

    @Transactional
    public void updateStorage(PrRepositoryArgs repository) {
        User user = SpringSecurityUtil.getUser();
        Date now = new Date();
        repository.setUpdatetime(now);
        repository.setUpdateUser(user.getUserId().intValue());
        if (prRepositoryDao.updateStorage(repository)!=1){
            throw new BusinessException("修改仓库失败!");
        }
    }

    public Page<PrRepository> repositoryListData(PrRepositoryArgs repositoryArgs, Pageable pageable) {

        return prRepositoryDao.repositoryListData(repositoryArgs, pageable);
    }

    public PrRepository getRepositoryOne(Integer id) {
        PrRepository repository = new PrRepository();
        repository.setId(id);
        return prRepositoryDao.getRepositoryOne(repository);
    }

    @Transactional
    public List<PrRepository> getAllRepositories(Integer projectId) {
        PrRepository repository = new PrRepository();
        repository.setProjectId(projectId);
        return prRepositoryDao.getAllRepositories(repository);
    }

    public List<PrRepository> getAllRepositories() {
        PrRepository repository = new PrRepository();
        return prRepositoryDao.getAllRepositories(repository);
    }

    public List<PrProject> getAllProjects(String dataAuthoritiesRegexp) {
        PrProjectArgs prProject = new PrProjectArgs();
        prProject.setAuthoritiesRegexp(dataAuthoritiesRegexp);
        return prProjectDao.getAllProjects(prProject);
    }

    @Transactional
    public void deleteStorage(PrRepositoryArgs repository) {
        PrItemDelivery delivery = new PrItemDelivery();
        delivery.setRepositoryId(repository.getId());
        if (!CollectionUtils.isEmpty(prItemDeliveryDao.getItemDelivery(delivery))){
            throw new BusinessException("该仓库已经有材料入库，不能删除");
        }
        repository.setStatus(1);
        repository.setProjectId(ConstantsHolder.getContext().getCurrDataId());
        repository.setUpdatetime(new Date());
        repository.setUpdateUser(SpringSecurityUtil.getUser().getUserId().intValue());
        if (prRepositoryDao.deleteStorage(repository)!=1){
            throw new BusinessException("删除仓库失败");
        }
    }
}

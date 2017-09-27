package com.huayu.inventory.dao;

import com.huayu.a.service.tools.SpringSecurityUtil;
import com.huayu.common.tool.NumberUtil;
import com.huayu.inventory.domain.PrRepository;
import com.huayu.inventory.web.args.PrRepositoryArgs;
import com.huayu.user.domain.User;
import com.ly.dao.base.BasePageDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by DengPeng on 2017/6/12.
 */
@Repository
public class PrRepositoryDao extends BasePageDao<PrRepository,Integer>{

    public int postData(PrRepositoryArgs repository) {
        Long key = super.getKey("pr_repository",repository);
        repository.setId(key.intValue());
        repository.setRcode(repository.getPcode()+ NumberUtil.buildNum(repository.getIndex(),5));
        return super.post(repository);
    }

    public Page<PrRepository> repositoryListData(PrRepositoryArgs repositoryArgs, Pageable pageable) {
        User user = SpringSecurityUtil.getUser();
        String sql = "select r.*,p.name projectName from pr_repository r inner join pr_project p on r.projectId = p.id where status=0 and p.pcode REGEXP "+user.getDataAuthorityRegexp();
        return super.get(sql,repositoryArgs,pageable);
    }

    public PrRepository getRepositoryOne(PrRepository repository) {
        String sql  = "select r.* from pr_repository r where status=0 and r.id=:id ";
        return super.getOne(sql,repository);
    }

    public int updateStorage(PrRepositoryArgs repository) {
        String sql = "update pr_repository set name=:name,address=:address,remark=:remark  where status=0 and id=:id and projectId=:projectId";
        return super.put(sql,repository);
    }

    public List<PrRepository> getAllRepositories(PrRepository repository) {
        String sql = "select r.* from pr_repository r where status=0 ";
        if (null != repository.getProjectId()){
            sql+= " and r.projectId=:projectId ";
        }
        return super.get(sql,repository);
    }

    public int deleteStorage(PrRepositoryArgs repository) {
        String sql = "update pr_repository set  status=:status  where  id=:id and projectId=:projectId ";
        return super.put(sql,repository);
    }
}

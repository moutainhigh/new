package com.huayu.inventory.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.huayu.a.dao.CommSequenceDao;
import com.huayu.a.domain.CommSequence;
import com.huayu.a.service.tools.SpringSecurityUtil;
import com.huayu.common.BusinessException;
import com.huayu.common.ConstantsHolder;
import com.huayu.common.tool.NumberUtil;
import com.huayu.inventory.dao.PrItemDeliveryDao;
import com.huayu.inventory.dao.PrMaterialCategoryDao;
import com.huayu.inventory.dao.PrMaterialDao;
import com.huayu.inventory.dao.PrMaterialSpecificationDao;
import com.huayu.inventory.domain.PrItemDelivery;
import com.huayu.inventory.domain.PrMaterial;
import com.huayu.inventory.domain.PrMaterialCategory;
import com.huayu.inventory.domain.PrMaterialSpecification;
import com.huayu.inventory.web.args.PrMaterialArgs;
import com.huayu.user.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/4/28.
 */
@Service
public class PrMaterialService {

    private static Logger logger = LoggerFactory.getLogger(PrMaterialService.class);

    @Autowired
    private PrMaterialDao prMaterialDao;

    @Autowired
    private PrMaterialCategoryDao prMaterialCategoryDao;

    @Autowired
    private CommSequenceDao commSequenceDao;

    @Autowired
    private PrMaterialSpecificationDao prMaterialSpecificationDao;

    @Autowired
    private PrItemDeliveryDao prItemDeliveryDao;


    @Transactional
    public void updateMaterial(PrMaterial material) {
        material.setProjectId(ConstantsHolder.getContext().getCurrDataId());
        PrMaterialSpecification specification = new PrMaterialSpecification(material.getSpecificationId(),material.getId());
        specification.setSpecification(material.getSpecification());
        List<PrMaterial> materials = prMaterialDao.getMaterials(material);
        if (!CollectionUtils.isEmpty(materials)&&materials.size()==1){
            if (!materials.get(0).getId().equals(material.getId())){
                throw new BusinessException("材料重复");
            }
        }
        if (prMaterialSpecificationDao.update(specification)!=1){
            throw new BusinessException("修改规格失败");
        }
        if (prMaterialDao.updateMaterial(material)!=1){
            throw new BusinessException("更新失败");
        }
    }

    public Page<PrMaterial> materialSpecificationListData(PrMaterialArgs material, Pageable pageable) {
        return prMaterialDao.materialSpecificationListData(material, pageable);
    }

    public Page<PrMaterial> materialListData(PrMaterialArgs material, Pageable pageable) {
        return prMaterialDao.materialListData(material, pageable);
    }

    /**
     * 获取材料详情
     *
     * @param id
     * @param specificationId
     * @return
     */
    public PrMaterial getMaterialResultById(Integer id, Integer specificationId) {
        PrMaterial material = new PrMaterial();
        material.setId(id);
        material.setSpecificationId(specificationId);
        material.setProjectId(ConstantsHolder.getContext().getCurrDataId());
        return prMaterialDao.getOneMaterial(material);
    }

    @Transactional
    public void deleteOneMaterial(PrMaterialArgs materialArgs) {
        List<PrItemDelivery> list = prItemDeliveryDao.getItemDelivery(new PrItemDelivery(materialArgs.getId(), materialArgs.getSpecificationId()));
        if (!CollectionUtils.isEmpty(list)){
            throw new BusinessException("材料已经入库不能删除");
        }
        Date now = new Date();
        User user = SpringSecurityUtil.getUser();
        PrMaterialSpecification specification = new PrMaterialSpecification();
        specification.setMatId(materialArgs.getId());
        specification.setId(materialArgs.getSpecificationId());
        specification.setDeletetime(now);
        specification.setDeleteUser(user.getUserId().intValue());
        if (prMaterialSpecificationDao.deleteOne(specification)!=1){
            throw new BusinessException("删除该规格的材料失败");
        }
        List<PrMaterialSpecification> materialSpecification = prMaterialSpecificationDao.getMaterialSpecification(specification);
        if (materialSpecification.size()==0){
            PrMaterial material = new PrMaterial();
            material.setId(materialArgs.getId());
            material.setDeletetime(now);
            material.setDeleteUser(user.getUserId().intValue());
            material.setProjectId(ConstantsHolder.getContext().getCurrDataId());
            if (prMaterialDao.deleteMaterial(material)!=1){
                throw new BusinessException("删除材料失败");
            }
        }
    }

    @Transactional
    public void insertMaterial(PrMaterialArgs material) {
        if (!CollectionUtils.isEmpty(prMaterialDao.getMaterials(material))){
            throw new BusinessException("该材料已经存在");
        }
        PrMaterialCategory one = prMaterialCategoryDao.getOne(new PrMaterialCategory(material.getCategoryId()));
        material.setMcCode(one.getCode());
        material.setCreatetime(new Date());
        material.setCreateUser(SpringSecurityUtil.getUser().getUserId().intValue());
        material.setStatus(0);
        material.setProjectId(ConstantsHolder.getContext().getCurrDataId());
        int count = prMaterialDao.getMaterialCountByCatId(material.getCategoryId());
        material.setMcode(material.getMcCode()+ NumberUtil.buildNum(++count,5));
        if (prMaterialDao.postData(material) != 1){
            throw new BusinessException("添加材料失败");
        }
        insertMaterialSpecification(material,false);  //添加规格
    }

    @Transactional
    public void  updateMaterialCode(){
        Integer[] ss = new Integer[]{3,12,20,21,24,30,40,44,49,56,57,62,63,96};
        for (int i = 0; i < ss.length; i++) {
            Integer categoryId = ss[i];
            PrMaterial m = new PrMaterial();
            m.setCategoryId(categoryId);
            List<PrMaterial> materials = prMaterialDao.getMaterials(m,"'00010'");
            for (PrMaterial material : materials) {
                PrMaterialCategory one = prMaterialCategoryDao.getOne(new PrMaterialCategory(categoryId));
                String mcCode =one.getCode();
                int count = prMaterialDao.getMaterialCountByCatId(material.getCategoryId());
                material.setMcode(mcCode+ NumberUtil.buildNum(++count,5));
                prMaterialDao.updateMaterialCode(material);
            }
        }
    }

    public List<PrMaterial> getMaterials(Integer categoryId) {
        PrMaterial material = new PrMaterial();
        material.setCategoryId(categoryId);
        return prMaterialDao.getMaterials(material);
    }

    public List<PrMaterialSpecification> getMaterialSpecification(Integer matId) {
        PrMaterialSpecification specification = new PrMaterialSpecification();
        specification.setMatId(matId);
        return prMaterialSpecificationDao.getMaterialSpecification(specification);
    }

    @Transactional
    public void insertMaterialSpecification(PrMaterialArgs material,boolean checkRepeat) {
        List<String> strings = JSON.parseArray(material.getSpecificationArrayStr(), String.class);
        SqlParameterSource[] sqlParameterSources = new SqlParameterSource[strings.size()];
        Long[] ids = commSequenceDao.getKey(new CommSequence("pr_material_specification", strings.size()));
        for (int i =0; i<strings.size(); i++){
            Long key = ids[0] + i + 1;
            PrMaterialSpecification specification = new PrMaterialSpecification();
            specification.setId(key.intValue());
            specification.setMatId(material.getId());
            specification.setStatus(0);
            JSONObject jsonObject = JSON.parseObject(strings.get(i));
            Object specificationStr = jsonObject.get("specification");
            if (null != specificationStr&& StringUtils.hasText(specificationStr.toString())){
                specification.setSpecification(specificationStr.toString());
            }else{
                throw new BusinessException("材料规格不能为空");
            }
            if (checkRepeat){
                if (null!=prMaterialSpecificationDao.getMaterialBySpecification(specification)){
                        throw new BusinessException("材料规格重复");
                }
            }
            Object budget = jsonObject.get("budget");
            if (null != budget&& StringUtils.hasText(budget.toString())){
                specification.setBudget(new BigDecimal(budget.toString()));
            }
            sqlParameterSources[i] = new BeanPropertySqlParameterSource(specification);
        }
        int[] flags = prMaterialSpecificationDao.batchUpdate(sqlParameterSources);
        for (int i=0;i<flags.length;i++) {
            if (flags[i] == 0) {
                throw new BusinessException("添加失败");
            }
        }
    }

    public Page<PrMaterial> getInStorageMaterialListData(PrMaterialArgs materialArgs, Pageable pageable) {
        return prMaterialDao.getInStorageMaterial(materialArgs, pageable);
    }

    public Page<PrMaterial> receiptMaterialListData(PrMaterialArgs materialArgs, Pageable pageable) {
        return prMaterialDao.receiptMaterialListData(materialArgs,pageable);
    }

    public List<PrMaterial> receiptMaterialListData(PrMaterialArgs materialArgs) {
        return prMaterialDao.receiptMaterialListData(materialArgs);
    }


}

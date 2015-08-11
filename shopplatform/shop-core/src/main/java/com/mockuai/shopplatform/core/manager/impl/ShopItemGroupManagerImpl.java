package com.mockuai.shopplatform.core.manager.impl;

import com.mockuai.shopplatform.constant.ResponseCode;
import com.mockuai.shopplatform.core.util.ExceptionUtil;
import com.mockuai.shopplatform.core.util.TimeUtil;
import com.mockuai.shopplatform.domain.dto.ShopItemGroupDTO;
import com.mockuai.shopplatform.core.dao.ShopItemGroupDAO;
import com.mockuai.shopplatform.core.domain.ShopItemGroupDO;
import com.mockuai.shopplatform.core.exception.ShopException;
import com.mockuai.shopplatform.core.manager.ShopItemGroupManager;
import com.mockuai.shopplatform.domain.qto.ShopItemGroupQTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by luliang on 15/7/27.
 */
@Service
public class ShopItemGroupManagerImpl implements ShopItemGroupManager {

    @Resource
    private ShopItemGroupDAO shopItemGroupDAO;

    @Override
    public Long addShopItemGroup(ShopItemGroupDTO shopItemGroupDTO) throws ShopException {
        ShopItemGroupDO shopItemGroupDO = new ShopItemGroupDO();
        BeanUtils.copyProperties(shopItemGroupDTO, shopItemGroupDO);
        return shopItemGroupDAO.addShopItemGroup(shopItemGroupDO);
    }

    @Override
    public Boolean updateShopItemGroup(ShopItemGroupDTO shopItemGroupDTO) throws ShopException {
        ShopItemGroupDO shopItemGroupDO = new ShopItemGroupDO();
        BeanUtils.copyProperties(shopItemGroupDTO, shopItemGroupDO);
        Integer count = shopItemGroupDAO.updateShopItemGroup(shopItemGroupDO);
        if (count > 0) {
            return true;
        } else {
            throw ExceptionUtil.getException(ResponseCode.SYS_E_DB_UPDATE, "update item group error");
        }
    }

    @Override
    public Boolean deleteShopItemGroup(Long id, Long sellerId) throws ShopException {

        Integer count = shopItemGroupDAO.deleteShopItemGroup(id, sellerId);
        if (count > 0) {
            return true;
        } else {
            throw ExceptionUtil.getException(ResponseCode.SYS_E_DB_UPDATE, "update shop error");
        }
    }

    @Override
    public List<ShopItemGroupDTO> queryShopItemGroup(ShopItemGroupQTO shopItemGroupQTO) throws ShopException {

        List<ShopItemGroupDO> shopItemGroupDOs = null;
        List<ShopItemGroupDTO> shopItemGroupDTOs = new ArrayList<ShopItemGroupDTO>();
        shopItemGroupDOs = shopItemGroupDAO.queryShopItemGroup(shopItemGroupQTO);
        for(ShopItemGroupDO shopItemGroupDO: shopItemGroupDOs) {
            ShopItemGroupDTO shopItemGroupDTO1 = new ShopItemGroupDTO();
            BeanUtils.copyProperties(shopItemGroupDO, shopItemGroupDTO1);
            shopItemGroupDTO1.setCreateTime(TimeUtil.getFormatTime(shopItemGroupDO.getGmtCreated(),
                    TimeUtil.FORMAT_TIME));
            shopItemGroupDTOs.add(shopItemGroupDTO1);
        }
        return shopItemGroupDTOs;
    }

    @Override
    public ShopItemGroupDTO getShopItemGroup(Long id, Long sellerId) throws ShopException {
        ShopItemGroupDO shopItemGroupDO = shopItemGroupDAO.getShopItemGroup(id, sellerId);
        ShopItemGroupDTO shopItemGroupDTO = new ShopItemGroupDTO();
        BeanUtils.copyProperties(shopItemGroupDO, shopItemGroupDTO);
        return shopItemGroupDTO;
    }
}

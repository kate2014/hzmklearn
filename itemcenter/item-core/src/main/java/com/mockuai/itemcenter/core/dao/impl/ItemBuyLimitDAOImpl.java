package com.mockuai.itemcenter.core.dao.impl;

import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.core.dao.ItemBuyLimitDAO;
import com.mockuai.itemcenter.core.domain.ItemBuyLimitDO;
import com.mockuai.itemcenter.core.domain.ItemDO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.util.ExceptionUtil;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.util.ArrayList;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by luliang on 15/7/17.
 */
@Service
public class ItemBuyLimitDAOImpl extends SqlMapClientDaoSupport implements ItemBuyLimitDAO {
    @Override
    public Long addItemBuyLimit(ItemBuyLimitDO itemBuyLimitDO) throws ItemException {
        Long newInsertedId = 0L;
        try {
            newInsertedId = (Long) getSqlMapClientTemplate().insert("ItemBuyLimit.addItemBuyLimit", itemBuyLimitDO);
        } catch (Throwable e) {
            throw ExceptionUtil.getException(ResponseCode.SYS_E_SERVICE_EXCEPTION, "ItemBuyLimit.addItemBuyLimit error");
        }
        return newInsertedId;
    }

    @Override
    public List<ItemBuyLimitDO> queryItemBuyLimit(Long itemId, Long sellerId) throws ItemException {
        ItemBuyLimitDO itemBuyLimitDO = new ItemBuyLimitDO();
        itemBuyLimitDO.setItemId(itemId);
        itemBuyLimitDO.setSellerId(sellerId);
        List<ItemBuyLimitDO> list = null;
        try {
            list = getSqlMapClientTemplate().queryForList("ItemBuyLimit.queryItemBuyLimit", itemBuyLimitDO);
        } catch (Throwable e) {
            throw ExceptionUtil.getException(ResponseCode.SYS_E_SERVICE_EXCEPTION, "ItemBuyLimit.queryItemBuyLimit error");
        }
        return list;
    }

    @Override
    public int deleteItemBuyLimit(ItemBuyLimitDO itemBuyLimitDO) throws ItemException {

        try {
            int rows = getSqlMapClientTemplate().update("ItemBuyLimit.deleteItemBuyLimit", itemBuyLimitDO);
            return rows;
        } catch (Throwable e) {
            throw ExceptionUtil.getException(ResponseCode.SYS_E_DB_UPDATE, "ItemBuyLimit.deleteItemBuyLimit error");
        }
    }
}

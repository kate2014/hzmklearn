package com.mockuai.itemcenter.core.service.action;

import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemQTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemManager;
import com.mockuai.itemcenter.core.manager.ItemSearchManager;
import com.mockuai.itemcenter.core.util.ResponseUtil;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by wanghailong on 15-7-23.
 */
public class ItemServiceAction {
    private static final Logger log = LoggerFactory.getLogger(ItemServiceAction.class);

    @Resource
    private ItemManager itemManager;

    @Resource
    private ItemSearchManager itemSearchManager;

    @Resource
    TransactionTemplate transactionTemplate;

    private volatile boolean started = false;

    public void execute() throws JobExecutionException {
        if(!started) {
            started = true;
            transactionTemplate.execute(new TransactionCallback() {
                @Override
                public Object doInTransaction(TransactionStatus status) {
                    try {
                        ItemQTO itemQTO = new ItemQTO();
                        itemQTO.setNeedPaging(true);
                        itemQTO.setOffset(0);
                        itemQTO.setPageSize(1000);
                        List<ItemDTO> upList = itemManager.queryItemSaleUp(itemQTO); //上架状态返回
                        itemManager.updateItemSaleUp();
                        List<ItemDTO> downList = itemManager.queryItemSaleDown(itemQTO); //下架状态返回
                        itemManager.updateItemSaleDown();

                        //更新上架商品索引
                        for (ItemDTO itemDTO : upList) {
                            itemSearchManager.setItemIndex(itemDTO);
                        }
                        //删除下架商品索引
                        for (ItemDTO itemDTO : downList) {
                            itemSearchManager.deleteItemIndex(itemDTO.getId(), itemDTO.getSellerId());
                        }
                    } catch (ItemException e) {
                        status.setRollbackOnly();
                        log.error(e.toString());
                        return ResponseUtil.getErrorResponse(e.getResponseCode(), e.getMessage());
                    } finally {
                        started = false;
                    }
                    return null;
                }
            });
        }
    }
}
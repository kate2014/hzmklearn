package com.mockuai.itemcenter.core.manager;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSearchDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemSearchQTO;
import com.mockuai.itemcenter.core.exception.ItemException;

import java.util.List;

/**
 * Created by zengzhangqiang on 5/4/15.
 */
public interface ItemSearchManager {

    /**
     * 往搜索引擎中添加或更新item索引
     * @param itemDTO
     * @throws ItemException
     */
    public void setItemIndex(ItemDTO itemDTO) throws ItemException;


    /**
     * 搜索item文档
     * @param itemSearchQTO
     * @return
     * @throws ItemException
     */
    public ItemResponse<List<ItemSearchDTO>> searchItemIndex(ItemSearchQTO itemSearchQTO) throws ItemException;

    /**
     * 删除item索引
     * @param itemId
     * @param sellerId
     * @return
     * @throws ItemException
     */
    public boolean deleteItemIndex(Long itemId, Long sellerId) throws ItemException;

}

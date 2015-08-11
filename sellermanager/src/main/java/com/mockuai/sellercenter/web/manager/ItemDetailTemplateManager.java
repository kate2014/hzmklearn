package com.mockuai.sellercenter.web.manager;

import com.mockuai.itemcenter.common.domain.dto.ItemDetailTemplateDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemDetailTemplateQTO;
import com.mockuai.sellercenter.web.common.exception.ServiceException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品详情模板;
 * Created by luliang on 15/7/23.
 */
@Service
public interface ItemDetailTemplateManager {

    /**
     * 添加模板;
     * @param itemDetailTemplateDTO
     * @return
     * @throws ServiceException
     */
    public Long addItemDetailTemplate(ItemDetailTemplateDTO itemDetailTemplateDTO) throws ServiceException;

    /**
     * 查询模板
     * @param itemDetailTemplateQTO
     * @return
     * @throws ServiceException
     */
    public List<ItemDetailTemplateDTO> queryItemDetailTemplate(ItemDetailTemplateQTO itemDetailTemplateQTO) throws ServiceException;

    public ItemDetailTemplateDTO getItemDetailTemplate(Long id, Long sellerId) throws ServiceException;

    public Integer updateItemDetailTemplate(ItemDetailTemplateDTO itemDetailTemplateDTO) throws ServiceException;

    public Integer deleteItemDetailTemplate(Long id, Long sellerId) throws ServiceException;

}

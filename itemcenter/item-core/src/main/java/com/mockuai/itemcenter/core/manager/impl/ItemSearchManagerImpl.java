package com.mockuai.itemcenter.core.manager.impl;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.*;
import com.mockuai.itemcenter.common.domain.qto.CornerIconQTO;
import com.mockuai.itemcenter.common.domain.qto.ItemSearchQTO;
import com.mockuai.itemcenter.common.domain.qto.ItemSkuQTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.CornerIconManager;
import com.mockuai.itemcenter.core.manager.ItemCategoryManager;
import com.mockuai.itemcenter.core.manager.ItemSearchManager;
import com.mockuai.itemcenter.core.manager.ItemSkuManager;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.FetchProfile;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by zengzhangqiang on 5/4/15.
 * TODO 索引的异步批量更新，以及索引的优化(server.optimize())
 */
public class ItemSearchManagerImpl implements ItemSearchManager{
    private static final Logger log = LoggerFactory.getLogger(ItemSearchManagerImpl.class);

    private SolrServer solrServer;
    private String searchServerUrl;

    public void init(){
        //TODO 上线前这里改成配置
        solrServer = new HttpSolrServer(searchServerUrl);
    }

    @Resource
    private CornerIconManager cornerIconManager;

    @Resource
    private ItemSkuManager itemSkuManager;

    @Override
    public void setItemIndex(ItemDTO itemDTO) throws ItemException{
        ItemSearchDTO itemSearchDTO = new ItemSearchDTO();
        itemSearchDTO.setItemUid(""+itemDTO.getSellerId()+"_"+itemDTO.getId());
        itemSearchDTO.setItemName(itemDTO.getItemName());
        itemSearchDTO.setCategoryId(itemDTO.getCategoryId().longValue());
        itemSearchDTO.setBrandId(itemDTO.getItemBrandId().longValue());
        itemSearchDTO.setIconUrl(itemDTO.getIconUrl());
        //TODO 搜索引擎中的价格数据处理逻辑需要考虑（包括价格的更新等等）
        itemSearchDTO.setMarketPrice(itemDTO.getMarketPrice());
        itemSearchDTO.setPromotionPrice(itemDTO.getPromotionPrice());
        //开始售卖时间
        itemSearchDTO.setSaleBegin(itemDTO.getSaleBegin());
        // item catagory name
        itemSearchDTO.setCategoryName(itemDTO.getCategoryName());
        // sku barcode
        ItemSkuQTO itemSkuQTO = new ItemSkuQTO();
        itemSkuQTO.setSellerId(itemDTO.getSellerId());
        itemSkuQTO.setItemId(itemDTO.getId());
        List<ItemSkuDTO> itemSkuDTOs = itemSkuManager.queryItemSku(itemSkuQTO);
        StringBuilder sb = new StringBuilder();
        if(CollectionUtils.isNotEmpty(itemSkuDTOs)) {
            for(ItemSkuDTO itemSkuDTO: itemSkuDTOs) {
                sb.append(itemSkuDTO.getBarCode()).append(" ");
            }
        }
        itemSearchDTO.setBarCode(sb.toString());

        //TODO 兼容无线价不存在的情况，后续需要从源头上来兼容
        if(itemDTO.getWirelessPrice() != null){
            itemSearchDTO.setWirelessPrice(itemDTO.getWirelessPrice());
        }else{
            itemSearchDTO.setWirelessPrice(itemDTO.getPromotionPrice());
        }

        itemSearchDTO.setSellerId(itemDTO.getSellerId());

        //设置货源地
        if(itemDTO.getItemPropertyList() != null){
            for(ItemPropertyDTO itemPropertyDTO: itemDTO.getItemPropertyList()){
                if("IC_APP_P_ITEM_000002".equals(itemPropertyDTO.getCode())){//货源地
                    itemSearchDTO.setSupplyBase(itemPropertyDTO.getValue());
                }
            }
        }

        //设置角标ID
        itemSearchDTO.setCornerIconId(itemDTO.getCornerIconId());

        //设置bizCode
        //TODO 这里临时先用最粗暴的方式来判断洋东西的卖家。后续需要重构成通过app_key来识别。需要商家中心层面也进行重构
        if(itemDTO.getSellerId()!=null && itemDTO.getSellerId().longValue()==91L){
            itemSearchDTO.setBizCode("yangdongxi");
        }else{
            itemSearchDTO.setBizCode("mk_test");
        }

        List<ItemSearchDTO> itemSearchDTOs = new ArrayList<ItemSearchDTO>();
        itemSearchDTOs.add(itemSearchDTO);
        try{
            this.setItem(itemSearchDTOs);
        }catch(Exception e){
            //TODO error handle
            log.error("", e);
        }
    }

    @Override
    public ItemResponse<List<ItemSearchDTO>> searchItemIndex(ItemSearchQTO itemSearchQTO) throws ItemException {
        //TODO param check

        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery("*:*");

        if(itemSearchQTO.getBrandId() != null){
            solrQuery.addFilterQuery("brand_id:"+itemSearchQTO.getBrandId());
        }

        if(itemSearchQTO.getCategoryId() != null){
            solrQuery.addFilterQuery("category_id:"+itemSearchQTO.getCategoryId());
        }

        if(StringUtils.isNotBlank(itemSearchQTO.getKeyword())){
            solrQuery.add("text", itemSearchQTO.getKeyword());
            solrQuery.addFilterQuery("text:"+itemSearchQTO.getKeyword());
        }

        if(itemSearchQTO.getOrderBy() != null){
            SolrQuery.ORDER order = SolrQuery.ORDER.asc;
            if(itemSearchQTO.getAsc()!=null && itemSearchQTO.getAsc().intValue()==0){
                order = SolrQuery.ORDER.desc;
            }

            if(itemSearchQTO.getOrderBy().intValue() == 2){
                solrQuery.addSortField("promotion_price", order);
            }
            // 按照最新排序;
            if(itemSearchQTO.getOrderBy().intValue() == 3) {
                solrQuery.addSortField("sale_begin", SolrQuery.ORDER.desc);
            }
        }

        //TODO 暂时控制只搜索洋东西的数据，后续要立马重构，通过bizCode来隔离
        if(itemSearchQTO.getBizCode() != null){
            solrQuery.add("biz_code", itemSearchQTO.getBizCode());
        }else{
            solrQuery.add("biz_code", "yangdongxi");
        }

        solrQuery.setStart(itemSearchQTO.getOffset());
        solrQuery.setRows(itemSearchQTO.getCount());

        try{
            QueryResponse response = solrServer.query(solrQuery);
            List<ItemSearchDTO> itemSearchDTOs = response.getBeans(ItemSearchDTO.class);

            //填充角标url
            fillCornerIconUrl(itemSearchDTOs);

            ItemResponse<List<ItemSearchDTO>> itemResponse = new ItemResponse<List<ItemSearchDTO>>(itemSearchDTOs);
            itemResponse.setTotalCount(response.getResults().getNumFound());
            return itemResponse;
        }catch(Exception e){
            //TODO error handle
            log.error("", e);
            throw new ItemException(ResponseCode.SYS_E_SERVICE_EXCEPTION, e);
        }
    }

    /**
     * 获取cornerIconUrl
     * @param itemSearchDTOs
     */
    private void fillCornerIconUrl(List<ItemSearchDTO> itemSearchDTOs) {
        if(itemSearchDTOs==null || itemSearchDTOs.isEmpty()){
            return;
        }

        Map<Long, List<ItemSearchDTO>> cornerIdMap = new HashMap<Long, List<ItemSearchDTO>>();
        for(ItemSearchDTO itemSearchDTO: itemSearchDTOs) {
            if(itemSearchDTO.getCornerIconId()!=null && itemSearchDTO.getCornerIconId()>0){
                if(cornerIdMap.containsKey(itemSearchDTO.getCornerIconId()) == false){
                    cornerIdMap.put(itemSearchDTO.getCornerIconId(), new CopyOnWriteArrayList<ItemSearchDTO>());
                }

                cornerIdMap.get(itemSearchDTO.getCornerIconId()).add(itemSearchDTO);
            }
        }

        if(cornerIdMap.isEmpty()){
            return;
        }

        try {
            //TODO 这里后续如果做了数据库分表之后，就不能直接这么查询了，需要重构
            //批量查询角标信息
            CornerIconQTO cornerIconQTO = new CornerIconQTO();
            cornerIconQTO.setIdList(new CopyOnWriteArrayList<Long>(cornerIdMap.keySet()));
            List<CornerIconDTO> cornerIconDTOs = cornerIconManager.queryCornerIcon(cornerIconQTO);
            if(cornerIconDTOs != null){
                for(CornerIconDTO cornerIconDTO: cornerIconDTOs){
                    List<ItemSearchDTO> itemSearchDTOList = cornerIdMap.get(cornerIconDTO.getId());
                    for(ItemSearchDTO itemSearchDTO: itemSearchDTOList){
                        itemSearchDTO.setCornerIconUrl(cornerIconDTO.getIconUrl());
                    }
                }
            }else{
                //TODO error handle
            }
        } catch (ItemException e) {
            //TODO log
            log.error("", e);
        }
    }

    public boolean deleteItemIndex(Long itemId, Long sellerId) throws ItemException {
        try{
            //TODO itemUid组装逻辑封装到工具类中
            UpdateResponse updateResponse = solrServer.deleteById(sellerId+"_"+itemId);
            //TODO updateRespose处理
            solrServer.commit(true, true);
            return true;
        }catch(Exception e){
            //TODO error handle
            log.error("", e);
            throw new ItemException(ResponseCode.SYS_E_SERVICE_EXCEPTION, e);
        }

    }

    public String getSearchServerUrl() {
        return searchServerUrl;
    }

    public void setSearchServerUrl(String searchServerUrl) {
        this.searchServerUrl = searchServerUrl;
    }


    private boolean setItem(List<ItemSearchDTO> itemDocs) throws ItemException {
        //TODO param check

        try{
            solrServer.addBeans(itemDocs);
            solrServer.commit(true, true);
        }catch(Exception e){
            //TODO error handle
            log.error("", e);
            throw new ItemException(ResponseCode.SYS_E_SERVICE_EXCEPTION, e);
        }

        return true;
    }

    public static void main(String[] args){
        ItemSearchManagerImpl itemSearchManager = new ItemSearchManagerImpl();
        try{
            List<String> itemUidList = new ArrayList<String>();
            itemUidList.add("1_502");
            itemUidList.add("2_503");
            SolrServer solrServer = new HttpSolrServer("http://114.215.190.87:8080/solr/item_search");
            UpdateResponse updateResponse = solrServer.deleteById(itemUidList);
            //TODO updateRespose处理
            solrServer.commit(true, true);
        }catch(Exception e){
            //TODO error handle
            e.printStackTrace();
        }

    }
}

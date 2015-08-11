package com.mockuai.datacenter.core.manager.impl;

import com.mockuai.datacenter.common.domain.dto.PageViewDTO;
import com.mockuai.datacenter.common.domain.qto.PageViewQTO;
import com.mockuai.datacenter.core.dao.PageViewDAO;
import com.mockuai.datacenter.core.domain.PageViewDO;
import com.mockuai.datacenter.core.exception.DataException;
import com.mockuai.datacenter.core.manager.PageViewManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanghailong on 15-8-5.
 */
@Service
public class PageViewManagerImpl implements PageViewManager {

    private static final Logger logger = LoggerFactory.getLogger(PageViewManagerImpl.class);

    @Resource
    private PageViewDAO pageViewDAO;

    @Override
    public int addPageView(PageViewDO pageViewDO) throws DataException {
        int result = pageViewDAO.addPageView(pageViewDO);
        return result;
    }

    @Override
    public List<PageViewDTO> queryItemTop(PageViewQTO pageViewQTO) throws DataException {
        List<PageViewDO> list = pageViewDAO.queryItemTop(pageViewQTO);
        List<PageViewDTO> DTOList = new ArrayList<PageViewDTO>();
        for (PageViewDO itemCommentDO : list) {
            PageViewDTO itemCommentDTO = new PageViewDTO();
            BeanUtils.copyProperties(itemCommentDO, itemCommentDTO);
            DTOList.add(itemCommentDTO);
        }
        return DTOList;
    }

    @Override
    public List<PageViewDTO> queryVisitorArea(PageViewQTO pageViewQTO) throws DataException {
        List<PageViewDO> pageViewDOList = pageViewDAO.queryVisitorArea(pageViewQTO);
        List<PageViewDTO> pageViewDTOList = new ArrayList<PageViewDTO>();
        for(PageViewDO pageViewDO: pageViewDOList) {
            PageViewDTO pageViewDTO = new PageViewDTO();
            BeanUtils.copyProperties(pageViewDO, pageViewDTO);
            pageViewDTOList.add(pageViewDTO);
        }
        return pageViewDTOList;
    }

    @Override
    public List<PageViewDTO> queryDeviceType(PageViewQTO pageViewQTO) throws DataException {
        List<PageViewDO> pageViewDOList = pageViewDAO.queryDeviceType(pageViewQTO);
        List<PageViewDTO> pageViewDTOList = new ArrayList<PageViewDTO>();
        for(PageViewDO pageViewDO : pageViewDOList) {
            PageViewDTO pageViewDTO = new PageViewDTO();
            BeanUtils.copyProperties(pageViewDO, pageViewDTO);
            pageViewDTOList.add(pageViewDTO);
        }
        return pageViewDTOList;
    }

    @Override
    public Integer countShopPv(PageViewQTO pageViewQTO) {
        Integer pv = pageViewDAO.countShopPv(pageViewQTO);
        return pv;
    }

    @Override
    public Integer countShopUv(PageViewQTO pageViewQTO) {
        Integer uv = pageViewDAO.countShopUv(pageViewQTO);
        return uv;
    }

    @Override
    public Integer countShopUvTotal(PageViewQTO pageViewQTO) {
        Integer uv = pageViewDAO.countShopUvTotal(pageViewQTO);
        return uv;
    }

    @Override
    public List<PageViewDTO> queryShopPv(PageViewQTO pageViewQTO) {
        List<PageViewDO> pvList = pageViewDAO.queryShopPv(pageViewQTO);
        List<PageViewDTO> pageViewDTOList = new ArrayList<PageViewDTO>();
        for(PageViewDO pageViewDO : pvList) {
            PageViewDTO pageViewDTO = new PageViewDTO();
            BeanUtils.copyProperties(pageViewDO, pageViewDTO);
            pageViewDTOList.add(pageViewDTO);
        }
        return pageViewDTOList;
    }

    @Override
    public List<PageViewDTO> queryShopUv(PageViewQTO pageViewQTO) {
        List<PageViewDO> uvList = pageViewDAO.queryShopUv(pageViewQTO);
        List<PageViewDTO> pageViewDTOList = new ArrayList<PageViewDTO>();
        for(PageViewDO pageViewDO : uvList) {
            PageViewDTO pageViewDTO = new PageViewDTO();
            BeanUtils.copyProperties(pageViewDO, pageViewDTO);
            pageViewDTOList.add(pageViewDTO);
        }
        return pageViewDTOList;
    }

    @Override
    public List<PageViewDTO> queryShopHourUv(PageViewQTO pageViewQTO) throws DataException {
        List<PageViewDO> hourUvList = pageViewDAO.queryShopHourUv(pageViewQTO);
        List<PageViewDTO> pageViewDTOList = new ArrayList<PageViewDTO>();
        for(PageViewDO pageViewDO : hourUvList) {
            PageViewDTO pageViewDTO = new PageViewDTO();
            BeanUtils.copyProperties(pageViewDO, pageViewDTO);
            pageViewDTOList.add(pageViewDTO);
        }
        return pageViewDTOList;
    }

    @Override
    public PageViewDTO countNewVisitor(PageViewQTO pageVieQTO) throws DataException {
        PageViewDO pageViewDO = pageViewDAO.countNewVisitor(pageVieQTO);
        PageViewDTO pageViewDTO = new PageViewDTO();
        BeanUtils.copyProperties(pageViewDO, pageViewDTO);
        return pageViewDTO;
    }

}

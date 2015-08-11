package com.mockuai.datacenter.core.dao.impl;

import com.mockuai.datacenter.common.domain.qto.PageViewQTO;
import com.mockuai.datacenter.core.dao.PageViewDAO;
import com.mockuai.datacenter.core.domain.PageViewDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by wanghailong on 15-8-5.
 */
@Service
public class PageViewDAOImpl extends SqlMapClientDaoSupport implements PageViewDAO {

    private static final Logger log = LoggerFactory.getLogger(PageViewDAOImpl.class);

    @Override
    public int addPageView(PageViewDO pageViewDO) {
    	getSqlMapClientTemplate().insert("page_view.addPageView", pageViewDO);
        return 1;
    }

    @Override
    public List<PageViewDO> queryItemTop(PageViewQTO pageViewQTO) {
        List<PageViewDO> list = getSqlMapClientTemplate()
                .queryForList("PageViewDAO.queryItemTop", pageViewQTO);
        return list;
    }

    @Override
    public List<PageViewDO> queryVisitorArea(PageViewQTO pageViewQTO) {
        List<PageViewDO> pageViewDOList = new ArrayList<PageViewDO>();
        pageViewDOList = getSqlMapClientTemplate().queryForList("PageViewDAO.queryVisitorArea", pageViewQTO);
        return pageViewDOList;
    }

    @Override
    public List<PageViewDO> queryDeviceType(PageViewQTO pageViewQTO) {
        List<PageViewDO> pageViewDOList = new ArrayList<PageViewDO>();
        pageViewDOList = getSqlMapClientTemplate().queryForList("PageViewDAO.queryDeviceType", pageViewQTO);
        return pageViewDOList;
    }

    @Override
    public Integer countShopPv(PageViewQTO pageViewQTO) {
        Integer pv = (Integer) getSqlMapClientTemplate().queryForObject("PageViewDAO.countShopPv", pageViewQTO);
        return pv;
    }

    @Override
    public Integer countShopUv(PageViewQTO pageViewQTO) {
        Integer pv = (Integer) getSqlMapClientTemplate().queryForObject("PageViewDAO.countShopUv", pageViewQTO);
        return pv;
    }

    @Override
    public Integer countShopUvTotal(PageViewQTO pageViewQTO) {
        Integer pv = (Integer) getSqlMapClientTemplate().queryForObject("PageViewDAO.countShopUvTotal", pageViewQTO);
        return pv;
    }

    @Override
    public List<PageViewDO> queryShopPv(PageViewQTO pageViewQTO) {
        List<PageViewDO> pvList = new ArrayList<PageViewDO>();
        pvList = getSqlMapClientTemplate().queryForList("PageViewDAO.queryShopPv", pageViewQTO);
        return pvList;
    }

    @Override
    public List<PageViewDO> queryShopUv(PageViewQTO pageViewQTO) {
        List<PageViewDO> uvList = new ArrayList<PageViewDO>();
        uvList = getSqlMapClientTemplate().queryForList("PageViewDAO.queryShopUv", pageViewQTO);
        return uvList;
    }

    @Override
    public List<PageViewDO> queryShopHourUv(PageViewQTO pageViewQTO) {
        List<PageViewDO> hourUvList = new ArrayList<PageViewDO>();
        hourUvList = getSqlMapClientTemplate().queryForList("PageViewDAO.queryShopHourUv", pageViewQTO);
        return hourUvList;
    }

    @Override
    public PageViewDO countNewVisitor(PageViewQTO pageViewQTO) {
        PageViewDO pageViewDO = (PageViewDO)getSqlMapClientTemplate().queryForObject("PageViewDAO.countNewVisitor", pageViewQTO);
        return pageViewDO;
    }

}

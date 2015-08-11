package com.mockuai.itemcenter.core.manager.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.SellerBrandDTO;
import com.mockuai.itemcenter.common.domain.qto.SellerBrandQTO;
import com.mockuai.itemcenter.core.dao.SellerBrandDAO;
import com.mockuai.itemcenter.core.domain.SellerBrandDO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.SellerBrandManager;
import com.mockuai.itemcenter.core.util.ExceptionUtil;

@Service
public class SellerBrandManagerImpl implements SellerBrandManager{
	
	@Resource
	private SellerBrandDAO sellerBrandDao;
	
	@Override
	public List<SellerBrandDTO> querySellerBrand(SellerBrandQTO sellerBrandQTO)throws ItemException{
		try {
			List<SellerBrandDO> list = this.sellerBrandDao
					.querySellerBrand(sellerBrandQTO);
			List<SellerBrandDTO> result = new ArrayList<SellerBrandDTO>();
			if (!CollectionUtils.isEmpty(list)) {
				for (SellerBrandDO item : list) {
					SellerBrandDTO dto = new SellerBrandDTO();
					BeanUtils.copyProperties(item, dto);
					result.add(dto);
				}
			}
			return result;
		} catch (Exception e) {
			throw ExceptionUtil.getException(ResponseCode.SYS_E_DEFAULT_ERROR, e.getMessage());
		}
		
	}

	@Override
	public Long addSellerBrand(SellerBrandDTO sellerBrandDTO)
			throws ItemException {
		SellerBrandDO sellerBrandDO = new SellerBrandDO();
		BeanUtils.copyProperties(sellerBrandDTO, sellerBrandDO);
		return this.sellerBrandDao.addSellerBrand(sellerBrandDO);
	}

	@Override
	public int deleteSellerBrand(Long id,Long supplierId)
			throws ItemException {
		SellerBrandDO sellerBrandDO = new SellerBrandDO();
		sellerBrandDO.setSellerId(supplierId);
		sellerBrandDO.setId(id);
		return this.sellerBrandDao.deleteSellerBrand(sellerBrandDO);
	}

	@Override
	public SellerBrandDTO getSellerBrand(Long id, Long supplierId)
			throws ItemException {
		SellerBrandDO sellerBrandDO = new SellerBrandDO();
		sellerBrandDO.setId(id);
		sellerBrandDO.setSellerId(supplierId);
		
		SellerBrandDO brandDO  = this.sellerBrandDao.getSellerBrand(sellerBrandDO);
		if(brandDO == null){
			return null;
		}
		SellerBrandDTO dto =new SellerBrandDTO();
		dto.setBrandName(brandDO.getBrandName());
		dto.setBrandDesc(brandDO.getBrandDesc());
		dto.setCategoryClass(brandDO.getCategoryClass());
		dto.setLogo(brandDO.getLogo());
		dto.setId(brandDO.getId());
		return dto;
	}

	@Override
	public int updateSellerBrand(SellerBrandDTO sellerBrandDTO)
			throws ItemException {
		SellerBrandDO sellerBrandDO =new SellerBrandDO();
		BeanUtils.copyProperties(sellerBrandDTO, sellerBrandDO);
		int result = this.sellerBrandDao.updateSellerBrand(sellerBrandDO);
		return result;
	}
}

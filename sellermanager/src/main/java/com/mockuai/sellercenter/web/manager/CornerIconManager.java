package com.mockuai.sellercenter.web.manager;

import java.util.List;

import com.mockuai.itemcenter.common.domain.dto.CornerIconDTO;
import com.mockuai.itemcenter.common.domain.qto.CornerIconQTO;
import com.mockuai.sellercenter.web.common.exception.ServiceException;

public interface CornerIconManager {
	
	public CornerIconDTO addCornerIcon(CornerIconDTO cornerIconDTO)throws ServiceException;
	
	public Boolean deleteCornerIcon(Long id,Long sellerId)throws ServiceException;
	
	public List<CornerIconDTO> queryCornerIcon(CornerIconQTO cornerIconQTO)throws ServiceException;
	
	public CornerIconDTO getCornerIcon(Long id)throws ServiceException;
	
}

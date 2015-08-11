package com.mockuai.itemcenter.core.service.action.comment;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.CommentImageDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemCommentDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
import com.mockuai.itemcenter.core.domain.ItemCommentDO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.CommentImageManager;
import com.mockuai.itemcenter.core.manager.ItemCommentManager;
import com.mockuai.itemcenter.core.manager.ItemSkuManager;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.Action;
import com.mockuai.itemcenter.core.util.ModelUtil;
import com.mockuai.itemcenter.core.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 增加商品评论Action
 * 
 * @author chen.huang
 *
 */
@Service
public class AddItemCommentAction implements Action {
	private static final Logger log = LoggerFactory.getLogger(AddItemCommentAction.class);
	@Resource
	private ItemCommentManager itemCommentManager;

	@Resource
	private CommentImageManager commentImageManager;

	@Resource
	private ItemSkuManager itemSkuManager;

	@Resource
	private TransactionTemplate transactionTemplate;


	@Override
	public ItemResponse execute(RequestContext context) throws ItemException {
		final ItemRequest request = context.getRequest();
		//TODO 这里bizCode先写死，上线前，需要接入appcenter
		final String bizCode = "yangdongxi";
		// 验证DTO是否为空
		if (request.getParam("itemCommentList") == null) {
			return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "itemCommentList is null");
		}
		final List<ItemCommentDTO> itemCommentDTOList = (List<ItemCommentDTO>) request.getParam("itemCommentList");

		//添加商品评论事务处理
		ItemResponse itemResponse = transactionTemplate.execute(new TransactionCallback<ItemResponse>() {
			public ItemResponse doInTransaction(TransactionStatus transactionStatus) {
				ItemResponse response = null;
				try {
					List<ItemCommentDTO> itemCommentResult = new ArrayList<ItemCommentDTO>();
					List<Long> itemSkuIdList = new ArrayList<Long>();
					for(ItemCommentDTO itemCommentDTO : itemCommentDTOList) {
						if(itemCommentDTO.getSkuId() != null) {
							itemSkuIdList.add(itemCommentDTO.getSkuId());
						}
					}
					Map<Long, ItemSkuDTO> skuDTOMap = itemSkuManager.queryItemSkuMap(itemSkuIdList, itemCommentDTOList.get(0).getSellerId());

					//TODO 重构成批量操作
					for (ItemCommentDTO itemCommentDTO : itemCommentDTOList) {
						ItemCommentDO itemCommentDO = new ItemCommentDO();
						BeanUtils.copyProperties(itemCommentDTO, itemCommentDO);
						ItemSkuDTO itemSkuDTO = skuDTOMap.get(itemCommentDTO.getSkuId());
						if(itemSkuDTO != null) {
							itemCommentDO.setSkuCode(itemSkuDTO.getSkuCode());
						}
						Long itemCommentId = itemCommentManager.addItemComment(itemCommentDO);// 新增加的itemCommentDTO
						itemCommentDTO.setId(itemCommentId);
						itemCommentResult.add(itemCommentDTO);

						//如果评论是带图片的，则保存评论图片
						if (itemCommentDTO.getCommentImageDTOs()!=null
								&& itemCommentDTO.getCommentImageDTOs().isEmpty()==false) {
							for(CommentImageDTO commentImageDTO: itemCommentDTO.getCommentImageDTOs()){
								commentImageDTO.setBizCode(bizCode);
								commentImageDTO.setItemCommentId(itemCommentId);
								commentImageDTO.setSellerId(itemCommentDO.getSellerId());
								commentImageDTO.setUserId(itemCommentDO.getUserId());
							}
							commentImageManager.addCommentImages(
									ModelUtil.genCommentImageDOList(itemCommentDTO.getCommentImageDTOs()));
						}
					}

					response = ResponseUtil.getSuccessResponse(itemCommentResult);
					return response;
				} catch (ItemException e) {
					//回滚事务
					transactionStatus.setRollbackOnly();
					response = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
					log.error("do action:" + request.getCommand() + " occur Exception:" + e.getMessage(), e);
					return response;
				} catch (Exception e) {
					//回滚事务
					transactionStatus.setRollbackOnly();
					log.error("error to add item comment", e);
					return new ItemResponse(ResponseCode.SYS_E_SERVICE_EXCEPTION);
				}
			}
		});

		return itemResponse;

	}

	@Override
	public String getName() {
		return ActionEnum.ADD_ITEMCOMMENT.getActionName();
	}
}

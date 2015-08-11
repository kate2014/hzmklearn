package com.mockuai.itemcenter.core.util;

import com.mockuai.itemcenter.common.domain.dto.CommentImageDTO;
import com.mockuai.itemcenter.core.domain.CommentImageDO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by zengzhangqiang on 7/18/15.
 */
public class ModelUtil {
    public static List<CommentImageDO> genCommentImageDOList(List<CommentImageDTO> commentImageDTOs){
        if(commentImageDTOs == null){
            return null;
        }

        List<CommentImageDO> commentImageDOs = new CopyOnWriteArrayList<CommentImageDO>();
        for(CommentImageDTO commentImageDTO: commentImageDTOs){
            commentImageDOs.add(genCommentImageDO(commentImageDTO));
        }
        return commentImageDOs;
    }

    public static CommentImageDO genCommentImageDO(CommentImageDTO commentImageDTO){
        if(commentImageDTO == null){
            return null;
        }

        CommentImageDO commentImageDO = new CommentImageDO();
        BeanUtils.copyProperties(commentImageDTO, commentImageDO);
        return commentImageDO;
    }

    public static List<CommentImageDTO> genCommentImageDTOList(List<CommentImageDO> commentImageDOs){
        if(commentImageDOs == null){
            return null;
        }

        List<CommentImageDTO> commentImageDTOs = new CopyOnWriteArrayList<CommentImageDTO>();
        for(CommentImageDO commentImageDO: commentImageDOs){
            commentImageDTOs.add(genCommentImageDTO(commentImageDO));
        }
        return commentImageDTOs;
    }

    public static CommentImageDTO genCommentImageDTO(CommentImageDO commentImageDO){
        if(commentImageDO == null){
            return null;
        }

        CommentImageDTO commentImageDTO = new CommentImageDTO();
        BeanUtils.copyProperties(commentImageDO, commentImageDTO);
        return commentImageDTO;
    }
}

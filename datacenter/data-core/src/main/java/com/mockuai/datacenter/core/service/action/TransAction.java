package com.mockuai.datacenter.core.service.action;

import com.mockuai.datacenter.common.api.DataResponse;
import com.mockuai.datacenter.core.exception.DataException;
import com.mockuai.datacenter.core.service.RequestContext;
import com.mockuai.datacenter.core.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;

@Service
public abstract class TransAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(TransAction.class);

    @Resource
    TransactionTemplate transactionTemplate;


    protected abstract DataResponse doTransaction(RequestContext context)
            throws DataException;


    public DataResponse execute(final RequestContext context) throws DataException {

        return (DataResponse) this.transactionTemplate.execute(new TransactionCallback() {

            public Object doInTransaction(TransactionStatus status) {
                try {
                    DataResponse itemResponse = TransAction.this.doTransaction(context);
                    if(itemResponse.isSuccess() == false){
                        log.error("", itemResponse.getMessage());
                        status.setRollbackOnly();
                    }
                    return itemResponse;
                } catch (DataException e) {
                    log.error(e.getMessage(), e);
                    status.setRollbackOnly();
                    return ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
                }

            }

        });

    }

}
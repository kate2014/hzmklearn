package com.mockuai.datacenter.core.exception;


import com.mockuai.datacenter.common.constant.ResponseCode;

/**
 * Created by luliang on 15/7/26.
 */
public class DataException extends Exception {
    private static final long serialVersionUID = 1L;
    private int code;
    private String message;
    private ResponseCode responseCode;

    public DataException() {
        super();
        this.code = ResponseCode.SYS_E_DEFAULT_ERROR.getCode();
    }

    public DataException(String message) {
        super(message);
        this.code = ResponseCode.SYS_E_DEFAULT_ERROR.getCode();
    }

    public DataException(Throwable cause) {
        super(cause);
        this.code = ResponseCode.SYS_E_DEFAULT_ERROR.getCode();
    }

    public DataException(String message, Throwable cause) {
        super(message, cause);
        this.code = ResponseCode.SYS_E_DEFAULT_ERROR.getCode();
    }

    public DataException(int code, String message) {
        super(message);
        this.code = code;
    }

    public DataException(ResponseCode responseCode, Throwable cause) {
        super(responseCode.getComment(), cause);
        this.code = responseCode.getCode();
        this.responseCode = responseCode;
    }

    public DataException(ResponseCode responseCode, String message) {
        this.message = message;
        this.code = responseCode.getCode();
        this.responseCode = responseCode;
    }

    public DataException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResponseCode getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(ResponseCode responseCode) {
        this.responseCode = responseCode;
    }
}

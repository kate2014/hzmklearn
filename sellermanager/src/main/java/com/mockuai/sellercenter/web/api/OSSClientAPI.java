package com.mockuai.sellercenter.web.api;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;
import com.mockuai.sellercenter.web.common.constant.ResponseEnum;
import com.mockuai.sellercenter.web.common.exception.ServiceException;
import com.mockuai.sellercenter.web.util.OSSUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by luliang on 15/7/1.
 */
public class OSSClientAPI {

    private static final Logger logger = LoggerFactory.getLogger(OSSClientAPI.class);

    private OSSClient ossClient;

    private String accessKeyId;
    private String accessKeySecret;
    private String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
    private String bucketName = "mockuai-test";

    public OSSClientAPI(String endpoint, String bucketName, String accessKeyId, String accessKeySecret) {
        this.endpoint = endpoint;
        this.accessKeyId = accessKeyId;
        this.accessKeySecret = accessKeySecret;
        this.bucketName = bucketName;
    }

    public OSSClientAPI(String accessKeyId, String accessKeySecret) {
        this.accessKeyId = accessKeyId;
        this.accessKeySecret = accessKeySecret;
    }

    private void init() {
        ClientConfiguration conf = new ClientConfiguration();
        // 设置HTTP最大连接数为10
        conf.setMaxConnections(10);
        // 设置TCP连接超时为5000毫秒
        conf.setConnectionTimeout(5000);
        // 设置最大的重试次数为3
        conf.setMaxErrorRetry(3);
        // 设置Socket传输数据超时的时间为2000毫秒
        conf.setSocketTimeout(2000);
        ossClient = new OSSClient(endpoint, getAccessKeyId(), getAccessKeySecret(), conf);
        if(!ossClient.doesBucketExist(bucketName)) {
            ossClient.createBucket(bucketName);
        }
    }

    public void multipartUpload(String key, String filePath) {
        try {
            OSSUtil.multipartUpload(this.ossClient, bucketName, key, filePath);
        } catch (IOException e) {
            logger.error("multipartUpload failed, path: " + filePath + ", dest bucketName: " + bucketName, e);
        }
    }

    public OSSObject getObject(String bucketName, String key) throws ServiceException {
        OSSObject ossObject = null;
        try {
            ossObject = OSSUtil.getObject(ossClient, bucketName, key);
        } catch (IOException e) {
            ResponseEnum responseEnum = ResponseEnum.S_E_OSS_FILE_ERROR;
            throw new ServiceException(responseEnum.getCode(), responseEnum.getMsg());
        }
        if(ossObject == null) {
            ResponseEnum responseEnum = ResponseEnum.S_E_OSS_FILE_NOT_EXISTS_ERROR;
            throw new ServiceException(responseEnum.getCode(), responseEnum.getMsg());
        }
        return ossObject;

    }


    public OSSClient getOssClient() {
        return ossClient;
    }

    public void setOssClient(OSSClient ossClient) {
        this.ossClient = ossClient;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

}

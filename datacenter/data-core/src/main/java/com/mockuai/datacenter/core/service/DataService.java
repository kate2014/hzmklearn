package com.mockuai.datacenter.core.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.mockuai.datacenter.common.domain.DataQueue;
import com.mockuai.datacenter.common.domain.dto.DataDTO;
import com.mockuai.datacenter.common.domain.dto.PageViewDTO;
import com.mockuai.datacenter.common.domain.qto.PageViewQTO;
import com.mockuai.datacenter.core.domain.PageViewDO;
import com.mockuai.datacenter.core.exception.DataException;
import com.mockuai.datacenter.core.manager.IpAreaManager;
import com.mockuai.datacenter.core.manager.PageViewManager;
import com.mockuai.datacenter.core.util.HttpUtil;
import com.mockuai.datacenter.core.util.JsonUtil;

public class DataService {
	
//	@Resource
	IpAreaManager ipAreaManager;
	
//	@Resource
	PageViewManager pageViewManager;
	
	
	
	public void setIpAreaManager(IpAreaManager ipAreaManager) {
		this.ipAreaManager = ipAreaManager;
	}

	public void setPageViewManager(PageViewManager pageViewManager) {
		this.pageViewManager = pageViewManager;
	}

	private Logger log = LoggerFactory.getLogger(DataService.class);
	
	public void dealData(){
		if(!DataQueue.isEmpty()){
			DataDTO data = DataQueue.get();
			SimpleDateFormat sdf = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
			Date time = new Date();
			 try {
				time = sdf.parse(sdf.format(time));
			} catch (ParseException e1) {
				log.error("dataService", e1);
			}
			
			PageViewDTO pageViewDTO = new PageViewDTO();
			pageViewDTO.setVisitorIp(data.getIp());
			pageViewDTO.setVisitTime(time);
			pageViewDTO.setDeviceType(data.getAppType());
			pageViewDTO.setUserId(data.getUserId());
			pageViewDTO.setVid(data.getVid());
			pageViewDTO.setVisitType(data.getVisitType());
			pageViewDTO.setSellerId(data.getSellerId());
			
			PageViewQTO pageViewQTO = new PageViewQTO();
			pageViewQTO.setVisitorIp(data.getIp());
			
			String area;
			area = ipAreaManager.getArea(pageViewQTO);
			
			try {
				if(area==null){
					String result=null;
					try {
						String strURL = "http://ip.taobao.com/service/getIpInfo.php?ip=" + data.getIp();
						URL url = new URL(strURL);  
						HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();  
						InputStreamReader input = new InputStreamReader(httpConn.getInputStream(), "utf-8");  
						BufferedReader bufReader = new BufferedReader(input);  
						String line = "";  
						StringBuilder contentBuf = new StringBuilder();  
						while ((line = bufReader.readLine()) != null) {  
						    contentBuf.append(line);  
						}  
						result = contentBuf.toString();
						System.out.println(result);
					} catch (MalformedURLException e) {
						log.error("dataService", e);
					} catch (IOException e) {
						log.error("dataService", e);
					}
					
					JSONObject dataJson = JSON.parseObject(result);
					JSONObject response = dataJson.getJSONObject("data");
					area =URLDecoder.decode((String)response.get("city"), "UTF-8");
					System.out.println(area);
					
					PageViewDO pageViewDO = new PageViewDO();
					pageViewDO.setVisitorIp(data.getIp());
					pageViewDO.setVisitorArea(area);
					pageViewDO.setVisitTime(time);
					ipAreaManager.addIpArea(pageViewDO);
				}
			} catch (UnsupportedEncodingException e) {
				log.error("dataService", e);
			}
			pageViewDTO.setVisitorArea(area);
			
			PageViewDO pageViewDO = new PageViewDO();
			BeanUtils.copyProperties(pageViewDTO, pageViewDO);
			try {
				pageViewManager.addPageView(pageViewDO);
			} catch (DataException e) {
				log.error("dataService", e);
			}
		}
	}
}

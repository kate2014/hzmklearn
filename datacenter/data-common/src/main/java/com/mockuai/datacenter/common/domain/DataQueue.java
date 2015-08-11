package com.mockuai.datacenter.common.domain;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.mockuai.datacenter.common.domain.dto.DataDTO;
import com.mockuai.datacenter.common.domain.dto.PageViewDTO;

public class DataQueue {
	public static BlockingQueue<DataDTO> dataQueue = new LinkedBlockingQueue<DataDTO>(64);
	
	public static boolean put(DataDTO dataDTO){
		return dataQueue.offer(dataDTO);
	}
	
	public static DataDTO get(){
		return dataQueue.poll();
	}
	
	public static boolean isEmpty(){
		return dataQueue.isEmpty();
	}
	
}

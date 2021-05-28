package com.skt.treal.openavatar.build.api.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skt.treal.openavatar.build.api.model.vm.JobMessageCreator;
import com.skt.treal.openavatar.build.api.model.vo.queue.ReqReleaseVO;

@Service
public class RequestReleaseJobService extends BaseService {

	@Autowired
	private JmsTemplate jmsTemplate;
	
	@Async
	public Map<String, Object> requestReleaseJob() throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			ReqReleaseVO reqReleaseVO = new ReqReleaseVO();
			reqReleaseVO.setTime(10000);
			
			ObjectMapper objMapper = new ObjectMapper();
			JobMessageCreator messageCreator = new JobMessageCreator( objMapper.writeValueAsString(reqReleaseVO) );
			log.info("#### SEND REQUEST_RELEASE_JOB_QUEUE ####");
			jmsTemplate.send("REQUEST_RELEASE_JOB_QUEUE", messageCreator);

			resultMap.put("respCd", "0000");
		} catch (Exception e) {
			e.printStackTrace();
			log.info( "#####  REQUEST_RELEASE_JOB_QUEUE  ##### exception......" );
			throw new Exception(e);
		}
		
		return resultMap;
	}
}

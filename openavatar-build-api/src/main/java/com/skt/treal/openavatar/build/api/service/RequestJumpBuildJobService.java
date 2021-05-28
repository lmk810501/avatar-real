package com.skt.treal.openavatar.build.api.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skt.treal.openavatar.build.api.model.vm.JobMessageCreator;
import com.skt.treal.openavatar.build.api.model.vo.queue.ReqJumpBuildVO;

@Service
public class RequestJumpBuildJobService extends BaseService {

	@Autowired
	private JmsTemplate jmsTemplate;

	@Async
	public Map<String, Object> requestJumpBuildJob( ReqJumpBuildVO reqJumpBuildVo ) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			ObjectMapper objMapper = new ObjectMapper();
			JobMessageCreator messageCreator = new JobMessageCreator(objMapper.writeValueAsString(reqJumpBuildVo));
			log.info("#### SEND REQUEST_BUILD_JOB_QUEUE ####");
			jmsTemplate.send("REQUEST_BUILD_JOB_QUEUE", messageCreator);

			resultMap.put("respCd", "0000");
		} catch (Exception e) {
			e.printStackTrace();
			log.info( "#####  REQUEST_BUILD_JOB_QUEUE  ##### exception......" );
			throw new Exception(e);
		}
		
		return resultMap;
	}
}

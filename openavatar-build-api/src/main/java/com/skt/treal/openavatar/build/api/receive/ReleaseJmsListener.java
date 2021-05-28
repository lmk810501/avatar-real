package com.skt.treal.openavatar.build.api.receive;

import javax.jms.Message;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skt.treal.openavatar.build.api.model.enums.EnApi;
import com.skt.treal.openavatar.build.api.model.vo.queue.ReqReleaseVO;
import com.skt.treal.openavatar.build.api.service.ApiCallService;
import com.skt.treal.openavatar.build.api.service.TroService;

@Component
public class ReleaseJmsListener {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private TroService troService;
	
	@Autowired
	private ApiCallService apiCallService;
	
	@JmsListener( destination = "REQUEST_RELEASE_JOB_QUEUE" )
	public void receiveQueue( Message message ) {
		try {
			String str = ((TextMessage) message).getText();
			ReqReleaseVO release = null;
			
			log.info( "#### Object Message : {} ####", str);
			ObjectMapper objMapper = new ObjectMapper();
			release = objMapper.readValue(str, new TypeReference<ReqReleaseVO>(){});
			log.info( "#### ReqReleaseVO Object : {} ####", release.toString());
			// Trp 파일 생성
			troService.makeTrpFile();
			// API 호출
			String response = apiCallService.requestApiPost(EnApi.TEST, null, false, false);
			log.info("#### Api call Response Data : {} ####", response);
			log.info( "#### Job is the end To REQUEST_RELEASE_JOB_QUEUE ####");
		} catch (Exception e) {
			log.info( "#### REQUEST_RELEASE_JOB_QUEUE Exception Make Trp File : {} ####", e.getMessage());
			e.printStackTrace();
		}
	}
	
}

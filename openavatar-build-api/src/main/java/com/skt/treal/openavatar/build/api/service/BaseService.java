package com.skt.treal.openavatar.build.api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.skt.treal.openavatar.build.api.config.OpenAvatarProperties;

public class BaseService {

	protected final Logger log = LoggerFactory.getLogger( this.getClass() );
	
	protected String getCentralServerUrl( OpenAvatarProperties openAvatarProperties ) {
		return openAvatarProperties.getApiUrl();
	}
}

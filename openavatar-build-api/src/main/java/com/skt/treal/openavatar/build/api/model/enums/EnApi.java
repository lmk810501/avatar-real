package com.skt.treal.openavatar.build.api.model.enums;

import org.springframework.http.HttpMethod;

import lombok.Getter;

@Getter
public enum EnApi {

	TEST 	(HttpMethod.POST, "/open-avatar/test", "테스트")
	;
	
	private HttpMethod httpMethod;
	private String path;
	private String desc;
	
	private EnApi(HttpMethod httpMethod, String path, String desc) {
		this.httpMethod = httpMethod;
		this.path = path;
		this.desc = desc;
	}
	
}

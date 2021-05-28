package com.skt.treal.openavatar.build.api.service;

import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.skt.treal.openavatar.build.api.config.OpenAvatarProperties;
import com.skt.treal.openavatar.build.api.model.enums.EnApi;
import com.skt.treal.openavatar.build.api.utils.ApiClient;

@Service
public class ApiCallService extends BaseService {

	@Autowired
	private OpenAvatarProperties openAvatarProperties;
	
	/**
	 * API 호출 POST
	 */
	public String requestApiPost( EnApi enApi, LinkedHashMap<String, String> bodyParams, boolean isFormData, boolean isMultipart ) {
		ResponseEntity<String> response = null;
		ApiClient client = new ApiClient.Builder( getCentralServerUrl( openAvatarProperties ), enApi )
//				.header(openAvatarProperties.getCentralApiHeaderKey(), openAvatarProperties.getCentralApiHeaderValue())
				.bodyParams(bodyParams)
				.useFormUrlencoded(isFormData)
				.useMultipart(isMultipart)
				.build();
		response = client.exchange(String.class);
		return response.getBody();

	}
	
	/**
	 * API 호출 GET
	 */
	public String requestApiGet( EnApi enApi, LinkedHashMap<String, String> queryParams ) {
		ResponseEntity<String> response = null;
		ApiClient client = new ApiClient.Builder( getCentralServerUrl( openAvatarProperties ), enApi )
//				.header(openAvatarProperties.getCentralApiHeaderKey(), openAvatarProperties.getCentralApiHeaderValue())
				.queryParams(queryParams)
				.build();
		response = client.exchange(String.class);
		return response.getBody();
	}

}

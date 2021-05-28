package com.skt.treal.openavatar.build.api.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Arrays;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.skt.treal.openavatar.build.api.model.enums.EnApi;

public class ApiClient {

	private String url;
	private HttpMethod method;
	private HttpEntity<?> requestEntity;
		
	private ApiClient( Builder builder ) {
		// url, method
		this.url = builder.defaultUrl + builder.api.getPath();
		if( MapUtils.isNotEmpty( builder.queryParams ) )
			this.url += "?" + builder.queryParams.entrySet().stream().map(e -> e.getKey() + "=" + e.getValue() ).collect(Collectors.joining("&"));
		if( CollectionUtils.isNotEmpty( builder.pathVariables ) )
			this.url = String.format( this.url, builder.pathVariables.toArray() );
		this.method = builder.api.getHttpMethod();
		
		// headers
		MediaType mediaType = MediaType.APPLICATION_JSON_UTF8;
		if( builder.useMultipart )
			mediaType = MediaType.MULTIPART_FORM_DATA;
		if( builder.useFormUrlencoded )
			mediaType = MediaType.APPLICATION_FORM_URLENCODED;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType( mediaType );
		
		if( StringUtils.isNotEmpty(builder.headerAuthKey) && StringUtils.isNotEmpty(builder.headerAuthValue)  )
			headers.set( builder.headerAuthKey,  builder.headerAuthValue );
		
		if( MapUtils.isNotEmpty( builder.headers ) ) {
			builder.headers.entrySet().stream().forEach(e -> headers.set( e.getKey(), e.getValue() ));
		}
		
		// body
		if( MapUtils.isNotEmpty( builder.bodyParams ) )
			requestEntity = new HttpEntity<>( builder.bodyParams, headers );
		else if( builder.body != null )
			requestEntity = new HttpEntity<>( builder.body, headers );
		else 
			requestEntity = new HttpEntity<>( headers );
	}
	
	public <T> ResponseEntity<T> exchange( Class<T> responseType ) throws RestClientException {
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.exchange( url, method, requestEntity, responseType);
	}
	
	public <T> ResponseEntity<T> exchangeTest( Class<T> responseType ) throws RestClientException {
		RestTemplate restTemplate = new RestTemplate();
		List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>();
		converters.add(new FormHttpMessageConverter());
		converters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
		restTemplate.setMessageConverters(converters);
		return restTemplate.exchange( url, method, requestEntity, responseType);
	}
	
	public <T> ResponseEntity<T> exchange( ParameterizedTypeReference<T> responseType ) throws RestClientException {
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.exchange( url, method, requestEntity, responseType);
	}	
	
	public static class Builder {
		
		private String defaultUrl;
		private EnApi api;
		private String headerAuthKey;
		private String headerAuthValue;
		private boolean useMultipart = false;
		private boolean useFormUrlencoded = false;
		private MultiValueMap<String, Object> bodyParams = new LinkedMultiValueMap<>();
		private Map<String, String> queryParams = new HashMap<>();
		private Map<String, String> headers = new HashMap<>();
		private String body;
		private List<Object> pathVariables = new ArrayList<>();
		
		public Builder( String url, EnApi api ) {
			this.defaultUrl = url;
			this.api = api;
		}
		
		public Builder authorization( String headerAuthKey, String headerAuthValue ) {
			this.headerAuthKey = headerAuthKey;
			this.headerAuthValue = headerAuthValue;
			return this;
		}
		
		public Builder useFormUrlencoded( boolean useFormUrlencoded ) {
			this.useFormUrlencoded = useFormUrlencoded;
			return this;
		}
		
		public Builder useMultipart( boolean useMultipart ) {
			this.useMultipart = useMultipart;
			return this;
		}
		
		public Builder bodyParam( String key, Object value ) {
			if( value != null)
				this.bodyParams.add( key, value );
			return this;
		}
		
		public Builder bodyParams( LinkedHashMap<String, String> bodyParams  ) {
			if( !ObjectUtils.isEmpty(bodyParams)) {
				Set<String> set = bodyParams.keySet();
				Iterator<String> it = set.iterator();
				while(it.hasNext()) {
					String key = it.next();
					this.bodyParams.add( key, bodyParams.get(key) );
				}
			}
			return this;
		}
		
		public Builder queryParams( LinkedHashMap<String, String> queryParams  ) {
			if( !ObjectUtils.isEmpty(queryParams)) {
				Set<String> set = queryParams.keySet();
				Iterator<String> it = set.iterator();
				while(it.hasNext()) {
					String key = it.next();
					this.queryParams.put( key, queryParams.get(key) );
				}
			}
			return this;
		}
		
		public Builder bodyParamStringArray( String key, List<String> strList ) {
			if( strList != null)
				for(String str : strList) {
					this.bodyParams.add( key, str );
				}
			return this;
		}
				
		public Builder queryParam( String key, String value ) {
			if( value != null)
				this.queryParams.put( key, value );
			return this;
		}
		
		public Builder queryParam( String key, Object value ) {
			if( value != null)
				this.queryParams.put( key, String.valueOf( value ) );
			return this;
		}
		
		public Builder bodyParam( String key, MultipartFile file ) throws IOException {
			if( file != null)
				this.bodyParams.add( key, new FileResource( file.getInputStream(), file.getOriginalFilename() ) );
			return this;
		}
		
		public Builder bodyParamMultipartArray( String key, List<MultipartFile> fileList ) throws IOException {
			if( !ObjectUtils.isEmpty(fileList) ) {
				for( MultipartFile file : fileList ) {
					 ByteArrayResource resource = new ByteArrayResource(file.getBytes()) { 
					        @Override 
					        public String getFilename() { 
					            return file.getOriginalFilename(); 
					        } 
					    }; 
					    this.bodyParams.add( key, resource );
				}
			}
			return this;
		}
		
		public Builder header( String headerName, String headerValue ) {
			this.headers.put( headerName, headerValue );
			return this;
		}
		
		public Builder body( Object json ) throws JsonProcessingException {
			ObjectMapper mapper = new ObjectMapper();
			this.body = mapper.writeValueAsString( json );
			return this;
		}
		
		public Builder pathVars( Object... args ) {
			this.pathVariables = Arrays.asList( args );
			return this;
		}
		
		public ApiClient build() {
			return new ApiClient( this );
		}
		
		private class FileResource extends InputStreamResource {
			
			private final String filename;

			public FileResource( InputStream is, String filename ) {
				super( is );
				this.filename = filename;
			}

			@Override
			public String getFilename() {
				return this.filename;
			}

			@Override
			public long contentLength() throws IOException {
				// we do not want to generally read the whole stream into memory ...
				return -1; 
			}
		}
	}

	
}

package com.skt.treal.openavatar.build.api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties( prefix = "open-avatar" )
@Validated
@Getter
@Setter
public class OpenAvatarProperties {

	private String apiUrl;
	private Storage storage = new Storage();
	
	@Getter
	@Setter
	public class Storage {
		private String tempZipDir;
		private String trpFileName;
		private String defaultPath;
		private String templateDir;
		private String input;
		private String output;
	}
}

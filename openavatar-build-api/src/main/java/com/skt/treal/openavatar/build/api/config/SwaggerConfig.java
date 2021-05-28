package com.skt.treal.openavatar.build.api.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.ModelMap;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	private static String ACCESS_ALLOW_KEY = "X-AVATAR-BUILD-API-KEY x-AVATAR-BUILD-API-KEY";
	
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.skt.treal.openavatar.build.api"))
				.paths(PathSelectors.any())
				.build()
				.apiInfo( apiInfo() )
				.ignoredParameterTypes( ModelMap.class )
				.useDefaultResponseMessages(false)
				.forCodeGeneration( true )
				.securitySchemes( Arrays.asList(
						new ApiKey( ACCESS_ALLOW_KEY, "X-AVATAR-BUILD-API-KEY", "header")
				) )
//				.securityContexts(null)
				;
	}
	
	private ApiInfo apiInfo() {
		ApiInfoBuilder builder = new ApiInfoBuilder();
		builder.title( "Open Avatar Build API" ).version( "1.0" ).license( "(C) Copyright" )
		.description( "Open Avatar Build API ( Swagger UI )" );
		return builder.build();
	}
	
}

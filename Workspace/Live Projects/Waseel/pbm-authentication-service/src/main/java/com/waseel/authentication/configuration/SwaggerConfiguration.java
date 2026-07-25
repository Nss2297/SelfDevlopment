package com.waseel.authentication.configuration;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//@EnableSwagger2
//@Configuration
public class SwaggerConfiguration {

	@Bean
	public Docket api() {                
	    return new Docket(DocumentationType.SWAGGER_2)          
	      .select()
	      .apis(RequestHandlerSelectors.basePackage("com.waseel.authentication"))
	      .paths(PathSelectors.any())
	      .build()
	      .securitySchemes(Arrays.asList(token()))
	      .apiInfo(apiInfo());
	}
	 
	@SuppressWarnings("deprecation")
	private ApiInfo apiInfo() {
	    return new ApiInfo(
	      "Authentication API", 
	      "Token Generation APIs", 
	      "API TOS", 
	      "Terms of service","Waseel", "Waseel ASP","--");
	}
	
	private ApiKey token() {
		return new ApiKey("Bearer", "Authorization", "header");
	}
	
	
}

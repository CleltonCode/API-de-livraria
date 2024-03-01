package com.clelton.gl.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiVersionConfig {

	@Value("${api.version}")
	private String apiVersion;
	
	@Bean
	public String apiVersion() {
		return apiVersion;
	}
	
}

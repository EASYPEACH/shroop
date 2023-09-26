package com.easypeach.shroop.infra.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
			.allowedMethods("*")
			.allowCredentials(true)
			.maxAge(3000)
			.allowedOriginPatterns("http://13.209.16.112", "http://shroop.shop");
	}
}

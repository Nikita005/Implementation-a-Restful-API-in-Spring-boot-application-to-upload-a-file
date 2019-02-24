package com.example.demo.test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.example.demo.controller.FileUploadController;
import com.example.demo.model.UploadMetaData;


@Configuration
@EnableWebMvc
@ComponentScan(basePackages="com.example.demo.model")
public class WebConfig  {
	
	    @Bean
	    public UploadMetaData uploadController() {
	        return new UploadMetaData();
	    }

}

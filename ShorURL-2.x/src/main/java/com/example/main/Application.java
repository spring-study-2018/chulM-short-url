package com.example.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

//The first step in producing a deployable war file
//is to provide a SpringBootServletInitializer subclass and override its configure() method. 
//This makes use of Spring Framework’s Servlet 3.0 support and allows you to configure your application 
//when it’s launched by the servlet container.

@EnableAutoConfiguration
@SpringBootApplication
@ComponentScan(basePackages = { "com.example.*" })
public class Application extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}



}

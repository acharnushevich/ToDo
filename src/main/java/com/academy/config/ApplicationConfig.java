package com.academy.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Import(PersistanceConfig.class)
@PropertySource("classpath:application.properties")
@ComponentScan({"com.academy.model"})
public class ApplicationConfig {

}

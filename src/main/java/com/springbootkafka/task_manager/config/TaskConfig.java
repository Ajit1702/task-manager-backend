package com.springbootkafka.task_manager.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TaskConfig {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}

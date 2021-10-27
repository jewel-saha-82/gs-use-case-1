package org.chart.data.processing;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class Configs {

	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}
}

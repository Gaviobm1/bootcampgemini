package com.example.ioc;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;

@Configuration
public class Dependencias {
	@Bean
	Repositorio repositorio() {
		return new RepositorioMock();
	}
}

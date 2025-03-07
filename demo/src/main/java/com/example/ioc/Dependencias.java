package com.example.ioc;

import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;

@Configuration
public class Dependencias {
	@Bean
	Repositorio repositorio(Configuracion config, Registro registro) {
		return new RepositorioImpl(config, registro);
	}
	
	@Bean
	Repositorio repo1() {
		return new RepositorioMock();
	}
	
	@Bean
	Repositorio repo2(Configuracion config, Registro registro) {
		return new RepositorioImpl(config, registro);
	}
}

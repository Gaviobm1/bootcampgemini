package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import com.example.domains.contracts.repositories.ActoresRepository;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

import org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode;

@EnableSpringDataWebSupport(pageSerializationMode = PageSerializationMode.VIA_DTO)
@EnableAspectJAutoProxy
@SpringBootApplication
@EnableDiscoveryClient
@SecurityScheme(name = "bearerAuth", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT")
public class CatalogoApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(CatalogoApplication.class, args);
	}

	@Autowired
	ActoresRepository dao;

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Arrancada...");
	}

}

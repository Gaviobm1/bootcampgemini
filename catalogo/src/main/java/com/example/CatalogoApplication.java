package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import com.example.domains.contracts.repositories.ActoresRepository;

@SpringBootApplication
@EnableDiscoveryClient
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

package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.example.ioc.Rango;
import com.example.ioc.Repositorio;
import com.example.ioc.Servicio;


@SpringBootApplication
@ComponentScan(basePackages = "com.example.ioc")
public class DemoApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
		
	}
	
	@Autowired
	Servicio srv;
	
	@Autowired
	Repositorio repo1;
	
	@Autowired
	Repositorio repo2;
	
	@Value("${mi.valor:valor por defecto}")
	String valor;
	
	@Autowired
	Rango rango;

	@Override
	public void run(String... args) throws Exception {
		System.err.println("Aplicacion arrancada");
		//Servicio srv = new Servicio(new Repositorio(new Configuracion()));
		//AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		System.err.println(valor);
		System.err.println(rango.toString());
		srv.guardar();
		repo1.guardar();
		repo2.guardar();
	}
	
//	@Bean
//  	CommandLineRunner demo() {
//		return (args) -> {
//			System.err.println("Aplicacion arrancadaaaaaaaaaaaaaa");
//		};
//	}

}
package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.example.util.Calculadora;


@SpringBootApplication
@ComponentScan(basePackages = "com.example.ioc")
public class DemoApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
		
	}
	
//	@Autowired
//	Servicio srv;
//	
//	@Autowired
//	Repositorio repo1;
//	
//	@Autowired
//	Repositorio repo2;
//	
//	@Value("${mi.valor:valor por defecto}")
//	String valor;
//	
//	@Autowired
//	Rango rango;

	@Override
	public void run(String... args) throws Exception {
		//Servicio srv = new Servicio(new Repositorio(new Configuracion()));
		//AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ejemplosDePruebas();
	}
	private void ejemplosDePruebas() {
		var calc = new Calculadora();
		System.out.println(calc.suma(4.5, 6.80));
	}
	
//	@Bean
//  	CommandLineRunner demo() {
//		return (args) -> {
//			System.err.println("Aplicacion arrancadaaaaaaaaaaaaaa");
//		};
//	}

}
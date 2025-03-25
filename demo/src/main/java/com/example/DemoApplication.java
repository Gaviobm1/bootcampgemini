package com.example;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


import jakarta.transaction.Transactional;

@SpringBootApplication
@EnableDiscoveryClient
public class DemoApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
		
	}
	
	@Override
	@Transactional
	public void run(String... args) throws Exception {
		System.err.println("Aplicacion arrancada");
	}
	

	

//		var item = dao.findById(198);
//		if(item.isPresent()) {
//			var actor = item.get();
//			actor.setFirstName("Pepito");
//			actor.setLastName(actor.getLastName().toUpperCase());
//			dao.save(actor);
//		} else {
//			System.err.println("no se ha encontrado el actor");
//		}
//		dao.findAll((root, query, builder) -> builder.greaterThan(root.get("actorId"), 5))
//		.forEach(System.err::println);
//		srv.getAll().forEach(System.err::println);
	
	
////	@Autowired //(required = false)-
////	Servicio srv;
//	
//	@Autowired //(required = false)
////	@Qualifier("verdad")
//	Repositorio repo1;
//	@Autowired //(required = false)
////	@Qualifier("mentira")
//	Repositorio repo2;
////	@Autowired //(required = false)
////	Repositorio repo;
//
//	@Value("${mi.valor:valor por defecto}")
//	String valor;
//	
//	@Autowired
//	Rango rango;
//	
//	private void ejemplosIOC() {
//		//Servicio srv = new Servicio(new Repositorio(new Configuracion()));
//		//AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
//
////		srv.guardar();
////		repo.guardar();
//		repo1.guardar();
//		repo2.guardar();
//		System.err.println("Valor: " + valor);
//		System.err.println("Rango: " + rango);
//	}
	
	
//	@Bean
//  	CommandLineRunner demo() {
//		return (args) -> {
//			System.err.println("Aplicacion arrancadaaaaaaaaaaaaaa");
//		};
//	}

}

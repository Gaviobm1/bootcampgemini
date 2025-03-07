package com.example.ioc;

import org.springframework.stereotype.Component;

@Component
public class Configuracion {
	public Configuracion() {
		System.err.println("Servicio creada");
	}
	
	public void init() {
		System.err.println("Servicio initilizada");
	}
}

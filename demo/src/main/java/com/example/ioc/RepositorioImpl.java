package com.example.ioc;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;


@Repository
public class RepositorioImpl implements Repositorio {
	public RepositorioImpl(Configuracion config, Registro registro) {
		
	}
	
	@Override
	public void guardar() {
		System.err.println("Guardando");
	}
}

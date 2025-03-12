package com.example.util;

import com.example.ioc.Repositorio;

public class Factura {
	private Calculadora calc;
	Repositorio repositorio;
	
	public Factura(Calculadora calc, Repositorio repositorio) {
		this.calc = calc;
		this.repositorio = repositorio;
	}
	
	public double calcularTotal(int a, int b) {
		return calc.suma(a, b);
	}
	
	public void emitir() {
		repositorio.guardar();
	}
}

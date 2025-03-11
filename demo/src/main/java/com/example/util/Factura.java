package com.example.util;

public class Factura {
	private Calculadora calc;
	
	public Factura(Calculadora calc) {
		this.calc = calc;
	}
	
	public double calcularTotal(int a, int b) {
		return calc.suma(a, b);
	}
}

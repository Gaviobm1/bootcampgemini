package com.example.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CalculadoraTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testSuma() {
		var calc = new Calculadora();
		double resultado = calc.suma(3.5, 4.6);
		assertEquals(8.1, resultado);
	}
	
	@Test
	void testSumaInt() {
		var calc = new Calculadora();
		var resultado = calc.suma(Integer.MAX_VALUE, 1);
		assertEquals(5, resultado);
		assertTrue(1.0 / 0 > 0);
		assertEquals(5, 1.0 / 0);
		assertTrue(resultado > 0);
	}
	
	@Test
	@DisplayName("Divide por cero")
	void testDivide() {
		var calc = new Calculadora();
		var ex = assertThrows(ArithmeticException.class, () -> calc.divide(1, 0));
		assertEquals("/ by zero", ex.getMessage());
	}

}

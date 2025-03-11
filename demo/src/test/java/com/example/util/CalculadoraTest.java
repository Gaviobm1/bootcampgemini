package com.example.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class CalculadoraTest {
	
	private Calculadora calc;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		this.calc = new Calculadora();
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
	@Nested
	@DisplayName("Metodo: suma")
	class Suma {
		@Nested
		@DisplayName("Casos válidos")
		class OK {
			@Test
			void testSuma() {
				double resultado = calc.suma(3.5, 4.6);
				assertEquals(8.1, resultado);
			}
			@Test
			void testSumaSmall() {
				double resultado = calc.suma(0.1, 0.2);
				assertEquals(0.3, resultado);
			}
			@Test
			void testSumaInt() {
				var resultado = calc.suma(13, 5);
				assertEquals(18, resultado);
			}
		}
		@Nested
		@DisplayName("Casos inválidos")
		class KO {
			@Test
			void testSumaInt() {
				var resultado = calc.suma(Integer.MAX_VALUE, 1);
				assertEquals(-2147483648, resultado, "Integer overflow");
			}
		}
		
	}
	
	@Nested
	@DisplayName("Metodos: divide")
	class Divide {
		@Nested
		@DisplayName("Casos válidos")
		class OK {
			void testDivide() {
				var resultado = calc.divide(15, 3);
				assertEquals(5, resultado);
			}
			void testDivide2() {
				var resultado = calc.divide(1, 2);
				assertEquals(0.5, resultado);
			}
		}
		@Nested
		@DisplayName("Casos inválidos")
		class KO {
			@Test
			@DisplayName("Divide por cero")
			void testDivide() {
				var ex = assertThrows(ArithmeticException.class, () -> calc.divide(1, 0));
				assertEquals("/ by zero", ex.getMessage());
			}
			
			@Test 
			@DisplayName("Divide por cero: try")
			void testDivide2() {
				try {
					calc.divide(1,  0);
					fail("No se ha lanzado la excepcion");
				} catch (ArithmeticException e) {
					assertEquals("/ by zero", e.getMessage());
				}
			}
		}
		
	}
	
	
	
	
	

}

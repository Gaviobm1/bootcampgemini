package com.example.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.example.ioc.Repositorio;

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
			@DisplayName("Suma dos enteros")
			@Smoke
			void testSumaInt() {
				var resultado = calc.suma(13, 5);
				assertEquals(18, resultado);
			}
			@DisplayName("Suma dos numeros")
			@ParameterizedTest(name = "{0} + {1} = {2}")
			@CsvSource({"1,2,3", "2,-1,1", "-1,2,1", "-2,-1,-3", "0,0,0", "0.1, 0.2, 0.3"})
			void testSumaParametrizado(double operando1, double operando2, double esperado) {
				var resultado = calc.suma(operando1, operando2);
				assertEquals(esperado, resultado);
			}
		}
		@Nested
		@DisplayName("Casos inválidos")
		@Disabled
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
		
		@Nested
		@DisplayName("Suplanta")
		class Suplantaciones {
			@Test
			void suplanta() {
				var mock = mock(Calculadora.class);
				when(mock.suma(anyInt(), anyInt())).thenReturn(3).thenReturn(5);
				
				var actual = mock.suma(2,  2);
				assertEquals(3, actual);
				assertEquals(5, mock.suma(2,  2));
				assertEquals(5, mock.suma(22,  2));
			}
			@Test
			void suplant2() {
				var calc = mock(Calculadora.class);
				when(calc.suma(anyInt(), anyInt())).thenReturn(4);
				var repo = mock(Repositorio.class);
				doNothing().when(repo).guardar();
				var obj = new Factura(calc, repo);
				var actual = obj.calcularTotal(2, 2);
				assertEquals(4, actual);
				verify(calc).suma(2,2);
			}
			@Test
			void Integracion() {
				var obj = new Factura(new Calculadora());
				var actual = obj.calcularTotal(2,2);
				assertEquals(4, actual);
			}
		}
		
	}
	
	
	
	
	

}

package com.example.util;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PersonTest {
	
	private Persona p;
	
	@BeforeEach
	void setUp() {
		this.p = new Persona(1, "Pepe");
	}
	
	@Test
	void createPersona() {
		assertNotNull(p);
		assertAll("Constructor", 
				() -> assertEquals(1, p.id, "id"),
				() -> assertEquals("Pepe", p.nombre, "nombre"),
				() -> assertEquals("Pepes", p.apellidos, "appellidos")
				);
	}
}

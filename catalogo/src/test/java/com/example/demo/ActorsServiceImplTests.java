package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.domains.contracts.repositories.ActoresRepository;
import com.example.domains.entities.Actor;
import com.example.domains.services.ActorsServiceImpl;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;

//@SpringBootTest
class ActorsServiceImplTests {
	
	@Mock
	private ActoresRepository repo;
	
	@InjectMocks
	private ActorsServiceImpl srv;
	
	private List<Actor> actores;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		actores = new ArrayList<Actor>();
		actores.add(new Actor(1, "JACOB", "ELORDI"));
		actores.add(new Actor(2, "TOM", "HOLLAND"));
		actores.add(new Actor(3, "BRIAN", "ALVAREZ"));
		actores.add(new Actor(4, "JOSHA", "STRADOWSKI"));
		actores.add(new Actor(5, "BARRY", "KEOGHAN"));
	}
	
	@Nested
	@DisplayName("Exceptions")
	class ExceptionTests {
		
		
		@Test
		void modifyThrowsWhenActorNull() {
			InvalidDataException ex = assertThrows(InvalidDataException.class, () -> srv.modify(null));
			assertEquals("El actor no pueded ser nulo", ex.getMessage());
		}
		
		@Test
		void deleteThrowsWhenActorNull() {
			InvalidDataException ex = assertThrows(InvalidDataException.class, () -> srv.delete(null));
			assertEquals("El actor no pueded ser nulo", ex.getMessage());
		}
		
		@Test
		void deleteThrowsWhenActorNotExist() {
			when(repo.existsById(6)).thenReturn(false);
			Actor actor = new Actor(6, "Daryl", "McCormack");
			NotFoundException ex = assertThrows(NotFoundException.class, () -> srv.delete(actor));
			assertEquals("El actor no existe", ex.getMessage());
			verify(repo).existsById(6);
		}
		
		@Test
		void deleteByIdThrowsWhenActorNotExist() {
			when(repo.existsById(6)).thenReturn(false);
			NotFoundException ex = assertThrows(NotFoundException.class, () -> srv.deleteById(6));
			assertEquals("El actor no existe", ex.getMessage());
			verify(repo).existsById(6);
		}
	}
	
	@Nested
	@DisplayName("add") 
	class Add {
		@Test
		void addThrowsWhenActorNull() {
			InvalidDataException ex = assertThrows(InvalidDataException.class, () -> srv.add(null));
			assertEquals("El actor no pueded ser nulo", ex.getMessage());
		}
		
		@ParameterizedTest(name="{index} => id: {0} -> \"El actor ya existe\"")
		@CsvSource({"1", "3", "5"})
		void addThrowsWhenActorExists(int id) {
			when(repo.existsById(id)).thenReturn(true);
			Actor actor = new Actor(id, "JACOB", "ELORDI");
			DuplicateKeyException  ex = assertThrows(DuplicateKeyException.class, () -> srv.add(actor));
			assertEquals("El actor ya existe", ex.getMessage());
			verify(repo).existsById(id);
		}
		
		@ParameterizedTest(name="{index} => id: {0} firstName: {1} lastName: {2} -> message: {3}")
		@DisplayName("Validation")
		@CsvSource({
			"6, Phil, BROOKS, ERRORES: firstName: First name must be capitalized.",
			"6, PHIL, Brooks, ERRORES: lastName: Last name must be capitalized.",
			"6, , BROOKS, ERRORES: firstName: First name must not be blank.",
			"6, PHIL, , ERRORES: lastName: Last name must not be blank.",
			"6, P, BROOKS, ERRORES: firstName: First name must be between 2 and 45 characters.",
			"6, PHIL, B, ERRORES: lastName: Last name must be between 2 and 45 characters.",
			})
		void throwsWhenValidationFails(int id, String firstName, String lastName, String message) {
			Actor actor = new Actor(id, firstName, lastName);
			InvalidDataException ex = assertThrows(InvalidDataException.class, () -> srv.add(actor));
			assertEquals(message, ex.getMessage());
		}
	}
	
	@Nested
	@DisplayName("find")
	class Finds {
		@Test
		void findsAll() {
			when(repo.findAll()).thenReturn(actores);
			List<Actor> value = srv.getAll();
			assertEquals(actores, value);
			verify(repo).findAll();
		}
		
		@ParameterizedTest(name="{index} => id: {0} arrayListIndex: {1} -> {2}") 
		@CsvSource({
			"1, 0, Jacob Elordi", 
			"2, 1, Tom Holland",
			"5, 4, Barry Keoghan"})
		void findsById(int id, int arrayListIndex, String actor) {
			when(repo.findById(id)).thenReturn(Optional.of(actores.get(arrayListIndex)));
			Optional<Actor> value = srv.getOne(id);
			assertEquals(Optional.of(actores.get(arrayListIndex)), value);
			verify(repo).findById(id);
		}
	}

}

package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.domains.contracts.repositories.ActoresRepository;
import com.example.domains.entities.Actor;
import com.example.domains.services.ActorsServiceImpl;
import com.example.exceptions.InvalidDataException;

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
		actores.add(new Actor(1, "Jacob", "Elordi"));
		actores.add(new Actor(2, "Tom", "Holland"));
		actores.add(new Actor(3, "Brian", "Alvarez"));
		actores.add(new Actor(4, "Ryan", "Gosling"));
		actores.add(new Actor(5, "Barry", "Keoghan"));
	}

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
	
	@Test
	void throwsWhenNull() {
		InvalidDataException ex = assertThrows(InvalidDataException.class, () -> srv.add(null));
		assertEquals("El actor no pueded ser nulo", ex.getMessage());
	}

}

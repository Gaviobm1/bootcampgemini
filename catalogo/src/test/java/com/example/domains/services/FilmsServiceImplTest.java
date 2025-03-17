package com.example.domains.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.domains.contracts.repositories.FilmsRepository;
import com.example.domains.entities.Film;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;

class FilmsServiceImplTest {
	
	@Mock 
	private FilmsRepository repo;
	
	@InjectMocks
	private FilmsServiceImpl srv;
	
	private List<Film> films;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		films = new ArrayList<Film>();
		films.add(new Film(1, "The Gorge", (short) 2025));
		films.add(new Film(2, "The Parenting", (short) 2025));
		films.add(new Film(3, "Gladiator II", (short) 2024));
		films.add(new Film(4, "Deadpool and Wolverine", (short) 2024));
		films.add(new Film(5, "Mulholland Drive", (short) 2001));
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Nested
	class Find {
		@Test
		void testGetAll() {
			when(repo.findAll()).thenReturn(films);
			List<Film> value = srv.getAll();
			assertEquals(films, value);
			verify(repo).findAll();
		}

		@ParameterizedTest(name="{index} => id: {0} arrayListIndex: {1} -> {2}")
		@CsvSource({
			"1, 0, The Gorge", 
			"2, 1, The Parenting",
			"5, 4, Mulholland Drive"})
		void testGetOne(int id, int arrayListIndex, String actor) {
			when(repo.findById(anyInt())).thenReturn(Optional.of(films.get(arrayListIndex)));
			Optional<Film> value = srv.getOne(id);
			assertEquals(Optional.of(films.get(arrayListIndex)), value);
			verify(repo).findById(id);
		}
	}
	

	@Nested
	class Add {
		@Test
		void addsFilm() throws DuplicateKeyException, InvalidDataException {
			Film film = new Film(6, "Eraserhead", (short) 1977);
			when(repo.save(film)).thenReturn(film);
			assertEquals(film, srv.add(film));
			verify(repo).save(film);
		}
		
		@Test
		void addThrowsWhenFilmNull() {
			InvalidDataException ex = assertThrows(InvalidDataException.class, () -> srv.add(null));
			assertEquals("El film no puede ser nulo", ex.getMessage());
		}
		
		@ParameterizedTest(name="{index} => id: {0} -> \"El film ya existe\"")
		@CsvSource({"1", "3", "5"})
		void addThrowsWhenFilmExists(int id) {
			when(repo.existsById(id)).thenReturn(true);
			Film film = new Film(id, "Mulholland Drive", (short) 2001);
			DuplicateKeyException  ex = assertThrows(DuplicateKeyException.class, () -> srv.add(film));
			assertEquals("El film ya existe", ex.getMessage());
			verify(repo).existsById(id);
		}
		
	}
	
	@Nested
	class Modify {
		@Test
		void testModify() {
			fail("Not yet implemented");
		}
	}

	
	@Nested 
	class Delete {
		@Test
		void testDelete() {
			fail("Not yet implemented");
		}

		@Test
		void testDeleteById() {
			fail("Not yet implemented");
		}
	}
	

}

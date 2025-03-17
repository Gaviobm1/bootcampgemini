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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;

import com.example.domains.contracts.repositories.FilmsRepository;
import com.example.domains.entities.Film;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;

@DataJpaTest
@ComponentScan(basePackages = "com.example")
class FilmsServiceImplTest {
	
	@MockBean 
	private FilmsRepository repo;
	
	@Autowired
	private FilmsServiceImpl srv;
	
	private List<Film> films;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		films = new ArrayList<Film>();
		films.add(new Film(1, "THE GORGE", (short) 2025));
		films.add(new Film(2, "THE PARENTING", (short) 2025));
		films.add(new Film(3, "GLADIATOR II", (short) 2024));
		films.add(new Film(4, "DEADPOOL AND WOLVERINE", (short) 2024));
		films.add(new Film(5, "MULHOLLAND DRIVE", (short) 2001));
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
			"1, 0, THE GORGE", 
			"2, 1, THE PARENTING",
			"5, 4, MULHOLLAND DRIVE"})
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
			Film film = new Film(6, "ERASERHEAD", (short) 1977);
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
			Film film = new Film(id, "MULHOLLAND DRIVE", (short) 2001);
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

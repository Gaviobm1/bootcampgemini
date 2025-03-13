package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.domains.contracts.repositories.ActoresRepository;
import com.example.domains.entities.Actor;
import com.example.domains.services.ActorsServiceImpl;

@SpringBootTest
class CatalogoApplicationTests {
	
	@Mock
	private ActoresRepository repo;
	
	@InjectMocks
	private ActorsServiceImpl srv;
	
	private List<Actor> actores;
	
	@BeforeEach
	void setUp() {
		actores = new ArrayList<Actor>();
		actores.add(new Actor(1, "Jacob", "Elordi"));
		actores.add(new Actor(2, "Tom", "Holland"));
		actores.add(new Actor(3, "Brian", "Alvarez"));
		actores.add(new Actor(4, "Ryan", "Gosling"));
		actores.add(new Actor(5, "Barry", "Keoghan"));
		when(repo.findAll()).thenReturn(actores);
		when(repo.findById(5)).thenReturn(Optional.of(actores.get(4)));
	}

	@Test
	void findsAll() {
		List<Actor> value = srv.getAll();
		assertEquals(actores, value);
	}
	
	@Test
	void findsById() {
		Optional<Actor> value = srv.getOne(5);
		assertEquals(Optional.of(actores.get(4)), value);
	}

}

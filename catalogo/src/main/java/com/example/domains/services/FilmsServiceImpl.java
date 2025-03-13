package com.example.domains.services;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import com.example.domains.contracts.repositories.FilmsRepository;
import com.example.domains.contracts.services.FilmsService;
import com.example.domains.entities.Film;
import com.example.exceptions.InvalidDataException;

@Service
public class FilmsServiceImpl implements FilmsService {
	
	private FilmsRepository dao;
	
	public FilmsServiceImpl(FilmsRepository dao) {
		this.dao = dao;
	}

	@Override
	public List<Film> getAll() {
		return dao.findAll();
	}

	@Override
	public Optional<Film> getOne(Integer id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public Film add(Film item) throws DuplicateKeyException, InvalidDataException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Film modify(Film item) throws NotFoundException, InvalidDataException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Film item) throws InvalidDataException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
	}

	
	
	

}

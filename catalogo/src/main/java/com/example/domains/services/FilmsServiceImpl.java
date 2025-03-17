package com.example.domains.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.domains.contracts.repositories.FilmsRepository;
import com.example.domains.contracts.services.FilmsService;
import com.example.domains.entities.Film;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;

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
		return dao.findById(id);
	}

	@Override
	public Film add(Film item) throws DuplicateKeyException, InvalidDataException {
		if (item == null) {
			throw new InvalidDataException("El film no puede ser nulo");
		}
		if (item.isInvalid()) {
			throw new InvalidDataException(item.getErrorsMessage());
		}
		if (item.getFilmId() > 0 && dao.existsById(item.getFilmId())) {
			throw new DuplicateKeyException("El film ya existe");
		}
		return dao.save(item);
	}

	@Override
	public Film modify(Film item) throws NotFoundException, InvalidDataException {
		if (item == null) {
			throw new InvalidDataException("El film no puede ser nulo");
		}
		if (!dao.existsById(item.getFilmId())) {
			throw new NotFoundException("El film no existe");
		}
		return dao.save(item);
	}

	@Override
	public void delete(Film item) throws InvalidDataException, NotFoundException {
		if (item == null) {
			throw new InvalidDataException("El actor no pueded ser nulo");
		}
		if (!dao.existsById(item.getFilmId())) {
			throw new NotFoundException("El film no existe");
		}
		dao.delete(item);	
	}

	@Override
	public void deleteById(Integer id) throws NotFoundException {
		if (!dao.existsById(id)) {
			throw new NotFoundException("El film no existe");
		}
		dao.deleteById(id);
	}

	
	
	

}

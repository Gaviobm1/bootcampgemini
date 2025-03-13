package com.example.domains.services;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import com.example.domains.contracts.repositories.ActoresRepository;
import com.example.domains.contracts.services.ActorsService;
import com.example.domains.entities.Actor;
import com.example.exceptions.InvalidDataException;

@Service
public class ActorsServiceImpl implements ActorsService {
 
	private ActoresRepository dao;
	
	public ActorsServiceImpl(ActoresRepository dao) {
		this.dao = dao;
	}

	@Override
	public List<Actor> getAll() {
		return dao.findAll();
	}

	@Override
	public Optional<Actor> getOne(Integer id) {
		return dao.findById(id);
	}

	@Override
	public Actor add(Actor item) throws DuplicateKeyException, InvalidDataException {
		if (item == null) {
			throw new InvalidDataException("El actor no pueded ser nulo");
		}
		if (item.getActorId() > 0 && dao.existsById(item.getActorId())) {
			throw new DuplicateKeyException("El actor ya existe");
		}
		return dao.save(item);
	}

	@Override
	public Actor modify(Actor item) throws NotFoundException, InvalidDataException {
		if (item == null) {
			throw new InvalidDataException("El actor no pueded ser nulo");
		}
		if (item.getActorId() > 0 &&)
		
		
		return null;
	}

	@Override
	public void delete(Actor item) throws InvalidDataException {
		if (item == null) {
			throw new InvalidDataException("El actor no pueded ser nulo");
		}
		dao.delete(item);
	}

	@Override
	public void deleteById(Integer id) {
		dao.deleteById(id);
	}

	@Override
	public void repartePremios() {
		// TODO Auto-generated method stub

	}

}

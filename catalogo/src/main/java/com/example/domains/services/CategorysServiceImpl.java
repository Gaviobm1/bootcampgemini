package com.example.domains.services;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.domains.contracts.repositories.CategorysRepository;
import com.example.domains.contracts.services.CategorysService;
import com.example.domains.entities.Category;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;

import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;

@Service
public class CategorysServiceImpl implements CategorysService {

    private CategorysRepository dao;

	public CategorysServiceImpl(CategorysRepository dao) {
		this.dao = dao;
	}

	@Override
	public List<Category> getAll() {
		return dao.findAllByOrderByName();
	}

	@Override
	public Iterable<Category> getAll(Sort sort) {
		return dao.findAll(sort);
	}

	public Page<Category> getAll(Pageable pageable) {
		return dao.findAll(pageable);
	}

	@Override
	public <T> List<T> getByProjection(@NotNull Class<T> type) {
		return dao.findAllBy(type);
	}

	@Override
	public <T> List<T> getByProjection(@NonNull Sort sort, @NonNull Class<T> type) {
		return dao.findAllBy(sort, type);
	}

	@Override
	public <T> Page<T> getByProjection(@NonNull Pageable pageable, @NonNull Class<T> type) {
		return dao.findAllBy(pageable, type);
	}

	@Override
	public Optional<Category> getOne(Integer id) {
		return dao.findById(id);
	}

	@Override
	@Transactional
	public Category add(Category item) throws DuplicateKeyException, InvalidDataException {
		if(item == null)
			throw new InvalidDataException("No puede ser nulo");
		if(item.isInvalid())
			throw new InvalidDataException(item.getErrorsMessage(), item.getErrorsFields());
		if(item.getCategoryId() != 0 && dao.existsById(item.getCategoryId()))
			throw new DuplicateKeyException("Ya existe");
		return dao.save(item);
	}

	@Override
	@Transactional
	public Category modify(Category item) throws NotFoundException, InvalidDataException {
		if(item == null)
			throw new InvalidDataException("No puede ser nulo");
		if(item.isInvalid())
			throw new InvalidDataException(item.getErrorsMessage(), item.getErrorsFields());
		if(!dao.existsById(item.getCategoryId()))
			throw new NotFoundException();
		return dao.save(item);
	}

	@Override
	@Transactional
	public void delete(Category item) throws InvalidDataException {
		if(item == null)
			throw new InvalidDataException("No puede ser nulo");
		dao.delete(item);
	}

	@Override
	@Transactional
	public void deleteById(Integer id) {
		dao.deleteById(id);
	}

	@Override
	public List<Category> novedades(Timestamp fecha) {
		return dao.findByLastUpdateGreaterThanEqualOrderByLastUpdate(fecha);
	}

}

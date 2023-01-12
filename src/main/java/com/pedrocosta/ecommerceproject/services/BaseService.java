package com.pedrocosta.ecommerceproject.services;

import java.util.ArrayList;
import java.util.List;

import com.pedrocosta.ecommerceproject.entities.BaseEntity;
import com.pedrocosta.ecommerceproject.repositories.BaseRepository;

public abstract class BaseService<T extends BaseEntity<T>> {

	protected BaseRepository<T> repository;

	public BaseService(BaseRepository<T> repository) {
		this.repository = repository;
	}

	// CRUD
	
	// CREATE
	public T save(T entity) {
		return repository.save(entity);
	}

	public List<T> saveAll(List<T> entities) {
		List<T> tempList = new ArrayList<>();
		repository.saveAll(entities).forEach(entity -> tempList.add(entity));
		return tempList;
	}
	
	// READ
	public List<T> getAll() {
		List<T> tempList = repository.findAll();
		return tempList;
	}
	
	public T get(int id) {
		return repository.findById(id).orElse(null);
	}
	
	// UPDATE
	public T update(T entity) {
		T entityToUpdate = repository.findById(entity.getId()).orElse(null);

		if (entityToUpdate == null) {
			return null;
		}

		return repository.save(entity);
	}
	
	// DELETE
	public T delete(T entity) {
		T entityToDelete = repository.findById(entity.getId()).orElse(null);

		if (entityToDelete == null) {
			return null;
		}

		repository.delete(entity);
		return entityToDelete;
	}
	
	public T delete(int id) {
		T entityToDelete = repository.findById(id).orElse(null);

		if (entityToDelete == null) {
			return null;
		}

		repository.deleteById(id);
		return entityToDelete;
	}
	
}

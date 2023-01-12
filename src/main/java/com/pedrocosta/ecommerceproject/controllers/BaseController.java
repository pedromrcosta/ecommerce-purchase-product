package com.pedrocosta.ecommerceproject.controllers;

import java.util.List;

import com.pedrocosta.ecommerceproject.services.BaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pedrocosta.ecommerceproject.entities.BaseEntity;


import jakarta.validation.Valid;

public abstract class BaseController<T extends BaseEntity<T>> {

	protected BaseService<T> baseService;

	public BaseController(BaseService<T> baseService) {
		this.baseService = baseService;
	}

	// CRUD
	
	// CREATE
	@PostMapping(value = "/save")
	public @ResponseBody ResponseEntity<T> save(@Valid @RequestBody T entity) {
		T savedEntity = baseService.save(entity);

		if (savedEntity == null) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(savedEntity, HttpStatus.OK);
	}

	@PostMapping("/saveAll")
	public @ResponseBody ResponseEntity<List<T>> saveAll(@Valid @RequestBody List<T> entities) {
		List<T> savedEntities = baseService.saveAll(entities);

		if (savedEntities.size() == 0) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(savedEntities, HttpStatus.OK);
	}
	
	// READ
	@GetMapping("/all")
	public @ResponseBody ResponseEntity<List<T>> showAll() {
		List<T> list = baseService.getAll();

		if (list.size() == 0 ) {
			return new ResponseEntity<>(list, HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(list, HttpStatus.FOUND);
	}
	
	@GetMapping("/{id}")
	public @ResponseBody ResponseEntity<T> getById(@PathVariable int id) {
		T getEntity = baseService.get(id);

		if (getEntity == null) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(getEntity, HttpStatus.FOUND);
	}
	
	// UPDATE
	@PutMapping("/")
	public @ResponseBody ResponseEntity<T> update(@RequestBody T entity) {
		T updatedEntity = baseService.update(entity);

		if (updatedEntity == null) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(updatedEntity, HttpStatus.OK);
	}
	
	// DELETE
	@DeleteMapping("/{id}")
	public @ResponseBody ResponseEntity<T> delete(@PathVariable int id) {
		T deletedEntity = baseService.delete(id);

		if (deletedEntity == null) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(deletedEntity, HttpStatus.OK);
	}
	
	@DeleteMapping("/")
	public @ResponseBody ResponseEntity<T> delete(@RequestBody T entity) {
		T deletedEntity = baseService.delete(entity);

		if (deletedEntity == null) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(deletedEntity, HttpStatus.OK);
	}
}

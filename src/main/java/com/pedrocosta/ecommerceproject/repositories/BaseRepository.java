package com.pedrocosta.ecommerceproject.repositories;


import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.pedrocosta.ecommerceproject.entities.BaseEntity;

import java.util.List;

@NoRepositoryBean
public interface BaseRepository<T extends BaseEntity<T>> extends CrudRepository<T, Integer> {
        List<T> findAll();
}

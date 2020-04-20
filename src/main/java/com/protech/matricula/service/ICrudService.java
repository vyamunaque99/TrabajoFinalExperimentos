package com.protech.matricula.service;

import java.util.List;
import java.util.Optional;

public interface ICrudService<T> {

	T saveOrUpdate(T entity);

	List<T> findAll();
	
	Optional<T> findById(Long id);
	
}

package com.protech.matricula.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.protech.matricula.entity.Profesor;
import com.protech.matricula.repository.IProfesorRepository;
import com.protech.matricula.service.IProfesorService;

@Service
public class ProfesorService implements IProfesorService{
	
	@Autowired
	private IProfesorRepository profesorRepository;

	@Transactional
	@Override
	public Profesor saveOrUpdate(Profesor entity) {
		// TODO Auto-generated method stub
		return profesorRepository.save(entity);
	}

	@Override
	public List<Profesor> findAll() {
		// TODO Auto-generated method stub
		return profesorRepository.findAll();
	}

	@Override
	public Optional<Profesor> findById(Long id) {
		// TODO Auto-generated method stub
		return profesorRepository.findById(id);
	}
	
	public void deleteById(Long id) {
		profesorRepository.deleteById(id);
	}

}

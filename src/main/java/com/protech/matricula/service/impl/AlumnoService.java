package com.protech.matricula.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.protech.matricula.entity.Alumno;
import com.protech.matricula.repository.IAlumnoRepository;
import com.protech.matricula.service.IAlumnoService;

@Service
public class AlumnoService implements IAlumnoService {

	@Autowired
	private IAlumnoRepository alumnoRepository;
	
	@Override
	public Alumno saveOrUpdate(Alumno entity) {
		// TODO Auto-generated method stub
		return alumnoRepository.save(entity);
	}

	@Override
	public List<Alumno> findAll() {
		// TODO Auto-generated method stub
		return alumnoRepository.findAll();
	}

	@Override
	public Optional<Alumno> findById(Long id) {
		// TODO Auto-generated method stub
		return alumnoRepository.findById(id);
	}

}
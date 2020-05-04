package com.protech.matricula.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.protech.matricula.entity.Matricula;
import com.protech.matricula.repository.IMatriculaRepository;
import com.protech.matricula.service.IMatriculaService;

@Service
public class MatriculaService implements IMatriculaService {

	@Autowired
	private IMatriculaRepository matriculaRepository;
	
	@Transactional
	@Override
	public Matricula saveOrUpdate(Matricula entity) {
		// TODO Auto-generated method stub
		return matriculaRepository.save(entity);
	}

	@Override
	public List<Matricula> findAll() {
		// TODO Auto-generated method stub
		return matriculaRepository.findAll();
	}

	@Override
	public Optional<Matricula> findById(Long id) {
		// TODO Auto-generated method stub
		return matriculaRepository.findById(id);
	}
	
	void deleteMatricula(Long id) {
		matriculaRepository.deleteById(id);
	}
	
	public boolean isCursoLleno(Long id) {
		System.out.println(matriculaRepository.cantidadAlumnos(id));
		if(matriculaRepository.cantidadAlumnos(id)<10) {
			return false;
		}else {
			return true;
		}
	}

}

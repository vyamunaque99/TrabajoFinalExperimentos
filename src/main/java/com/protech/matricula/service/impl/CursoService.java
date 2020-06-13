package com.protech.matricula.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.protech.matricula.entity.Curso;
import com.protech.matricula.repository.ICursoRepository;
import com.protech.matricula.repository.IMatriculaRepository;
import com.protech.matricula.service.ICursoService;

@Service
public class CursoService implements ICursoService{

	@Autowired
	private ICursoRepository cursoRepository;
	
	@Autowired
	private IMatriculaRepository matriculaRepository;
	
	@Transactional
	@Override
	public Curso saveOrUpdate(Curso entity) {
		// TODO Auto-generated method stub
		return cursoRepository.save(entity);
	}

	@Override
	public List<Curso> findAll() {
		// TODO Auto-generated method stub
		return cursoRepository.findAll();
	}

	@Override	
	public Optional<Curso> findById(Long id) {
		// TODO Auto-generated method stub
		return cursoRepository.findById(id);
	}
	
	public void deleteById(Long id) {
		cursoRepository.deleteById(id);
	}
	
	public boolean existeCursoByCodigo(String codigo) {
		if(cursoRepository.existeCursobyCodigo(codigo)==1) {
			return true;
		}else {
			return true;
		}
	}
	
	public boolean existeAlumnos(Long id) {
		if(matriculaRepository.cantidadAlumnos(id)>=1) {
			return true;
		}else {
			 return false;
		}
	}

}
